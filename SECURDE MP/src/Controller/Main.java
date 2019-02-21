package Controller;

import Model.User;
import View.Frame;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;
import org.apache.log4j.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private SQLite sqlite;
    public int logInAttempts = 0;
    private int passLength = 0;
    private User LoggedInUsername;
    private String password; //text that was saved
    private static InetAddress ipAddress;

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
        try {
            String log4jConfigFile = System.getProperty("user.dir")
                    + File.separator + "log4j.properties";
            PropertyConfigurator.configure(log4jConfigFile);
            ipAddress = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        // Initialize a driver object
        sqlite = new SQLite();

        // Create a database
        sqlite.createNewDatabase();

        // Drop users table if needed
//        sqlite.dropUserTable();
        // Create users table if not exist
        sqlite.createUserTable();

        // Add users
        sqlite.addUser("admin", hashPassword("qwerty1234"), 5);
        sqlite.addUser("manager", hashPassword("qwerty1234"), 4);
        sqlite.addUser("staff", hashPassword("qwerty1234"), 3);
        sqlite.addUser("client1", hashPassword("qwerty1234"), 2);
        sqlite.addUser("client2", hashPassword("qwerty1234"), 2);
        sqlite.addUser("disabled", hashPassword("qwerty1234"), 1);
        sqlite.addUser("client2", hashPassword("qwerty1234"), 2);
        sqlite.addUser("disabled", hashPassword("qwerty1234"), 1);
        sqlite.addUser("client2", hashPassword("qwerty1234"), 2);
        sqlite.addUser("disabled", hashPassword("qwerty1234"), 1);
        // Get users
        ArrayList<User> users = sqlite.getUsers();
        for (int nCtr = 0; nCtr < users.size(); nCtr++) {
            System.out.println("===== User " + users.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + users.get(nCtr).getUsername());
            System.out.println(" Password: " + users.get(nCtr).getPassword());
            System.out.println(" Role: " + users.get(nCtr).getRole());
        }

        // Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);

    }

    private byte[] generateSalt() { // generates random salt
        Random random = new Random();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

    private String hashPassword(String password) {
        try {
            int iterations = 1; // random number of iterations to perform
            char[] chars = password.toCharArray();
            byte[] salt = generateSalt();
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 512); // hash password iterations slow down log in
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return iterations + ":" + bytesToHex(salt) + ":" + bytesToHex(hash);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) { // check if equal password
        try {
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();
            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) { // slow function to compare the byte arrays
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0; // true if the arrays are equal false if not
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String bytesToHex(byte[] input) { // convert hexadecimal to byte[]
        return DatatypeConverter.printHexBinary(input);
    }

    private static byte[] fromHex(String hex) { // convert byte[] to hexadecimal
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /*    private boolean checkRequiredMinPassword(String password) { // salcy

        boolean hasLetter = false;
        boolean hasDigit = false;

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isLetter(c)) {
                    hasLetter = true;
                } else if (Character.isDigit(c)) {
                    hasDigit = true;
                }
                if (hasLetter && hasDigit) {
                    break;
                }
            }
            if (hasLetter && hasDigit) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        
    }*/
    public boolean checkRequiredMinPassword(String password) {

        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasSpecialChar = false;
        boolean hasDigit = false;

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    hasUpperCase = true;
                } else if (Character.isLowerCase(c)) {
                    hasLowerCase = true;
                } else if (Character.isDigit(c)) {
                    hasDigit = true;
                } else if (specialChars.contains(String.valueOf(c))) {
                    hasSpecialChar = true;
                }
                if (hasUpperCase && hasLowerCase && hasSpecialChar && hasDigit) {
                    break;
                }
            }
            if (hasUpperCase && hasLowerCase && hasSpecialChar && hasDigit) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean addUser(String username, String password) {
        ArrayList<User> users = sqlite.getUsers();
        User user = new User(username, password);
        if (checkRequiredMinPassword(password)) {
            if (sqlite.checkExistingUsers(username)) {
                sqlite.addUser(username, hashPassword(password));
                return true;
            } else {
                return false;
            }
        } else {
            LOGGER.error("Error in registering, pc user is : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress);

            return false;
        }
    }

    public boolean loginUser(String username, String password) { // check login
        ArrayList<User> users = sqlite.getUsers();
        for (int ctr = 0; ctr < users.size(); ctr++) {
            if (username.equals(users.get(ctr).getUsername())) { // check if username in list
                if (validatePassword(password, users.get(ctr).getPassword())) { // check if password matches user password
                    saveLoggedInUser(users.get(ctr).getId(), users.get(ctr).getUsername(), users.get(ctr).getPassword(), users.get(ctr).getRole());
                    LOGGER.info( username + " Logging in, pc user : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress + " account " + logInAttempts + " attempts already");
                    return true;
                }
            }
        }
        LOGGER.error("Error in loggin in, pc user : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress + "tried to login to " + username + " account " + logInAttempts + " attempts already");

        return false;
    }

    public void saveLoggedInUser(int id, String username, String password, int role) {
        User user = new User(id, username, password, role);
        this.LoggedInUsername = user;
    }

    public User getLoggedInUser() {
        return this.LoggedInUsername;
    }

    public void removeLoggedInUser() {
        this.LoggedInUsername = null;
    }

    public String hidePassword(String rawPass) {
        passLength = rawPass.length();
        String asterisks = "";
        for (int i = 0; i < passLength; i++) {
            asterisks += "*";
        }
        System.out.println(password);
        return asterisks;
    }

    public String savePassword(String rawPass) {
        if (rawPass.length() > passLength) {
            for (int i = 0; i < rawPass.length(); i++) {
                if (rawPass.charAt(i) != '*') {
                    password += rawPass.charAt(i);
                }
            }
        } else if (rawPass.length() < passLength) {
            password = password.substring(0, rawPass.length());
        }
        return password;
    }
}
//    public String decrypt(byte[] encrypted, SecretKey secretKey){
//        Cipher cipher;
//        try {
//            cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decrypted = cipher.doFinal(encrypted);
//            return new String(decrypted);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//    public byte[] encryptPassword(String password, SecretKey secretKey){
//        Cipher cipher;
//        try {
//            cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encrypted = cipher.doFinal(password.getBytes()); // encrypt hashedPassword
//            return encrypted;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//    public SecretKey getSecretKey() {
//        KeyGenerator keyGen;
//        SecretKey secretKey;
//        try {
//            keyGen = KeyGenerator.getInstance("AES"); // encrypt in AES
//            keyGen.init(128); // encrypt in AES 128 bit
//            secretKey = keyGen.generateKey();
//            return secretKey;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//        public byte[] hashPassword(String password, byte[] salt) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.reset();
//            md.update(salt);
//            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8)); // digest password
//            return hashed;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

