package Controller;

import Model.User;
import View.Frame;
import java.util.ArrayList;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
public class Main {

    public SQLite sqlite;
    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }

    public void init() {
        // Initialize a driver object
        sqlite = new SQLite();

        // Create a database
        sqlite.createNewDatabase();

        // Drop users table if needed
        sqlite.dropUserTable();

        // Create users table if not exist
        sqlite.createUserTable();

        // Add users
        sqlite.addUser("admin", hashPassword("qwerty1234"), 5);
        sqlite.addUser("manager", hashPassword("qwerty1234"), 4);
        sqlite.addUser("staff", hashPassword("qwerty1234"), 3);
        sqlite.addUser("client1", hashPassword("qwerty1234"), 2);
        sqlite.addUser("client2", hashPassword("qwerty1234"), 2);
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
    public byte[] generateSalt() { // generates random salt
            Random random = new Random();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            return bytes;
        }
    public String hashPassword(String password) {
        try {
            int iterations = 853715; // random number of iterations to perform
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
    private static boolean validatePassword(String originalPassword, String storedPassword){ // check if equal password
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
        }   
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    private String bytesToHex(byte[] input){ // convert hexadecimal to byte[]
        return DatatypeConverter.printHexBinary(input);
    }
    private static byte[] fromHex(String hex) { // convert byte[] to hexadecimal
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    public boolean addUser(String username, String password){
        ArrayList<User> users = sqlite.getUsers();
        User user = new User(username, password);
        if(sqlite.checkExistingUsers(username)){
            sqlite.addUser(username, hashPassword(password));
            return true;
        }
        else{
            return false;
        }
    }
    public boolean loginUser(String username, String password) { // check login
        ArrayList<User> users = sqlite.getUsers();
        for (int ctr = 0; ctr < users.size(); ctr++) {
            if (username.equals(users.get(ctr).getUsername())) { // check if username in list
                if (validatePassword(password, users.get(ctr).getPassword())) { // check if password matches user password
                    saveLoggedInUser(users.get(ctr).getId(), users.get(ctr).getUsername(), users.get(ctr).getPassword(), users.get(ctr).getRole());
                    return true;
                }
            }
        }
        return false;
    }
    public User saveLoggedInUser(int id, String username, String password, int role){
        User user = new User(id, username, password, role);
        return user;
    }
    public int getLoggedInUser(String username, String password) {
        ArrayList<User> users = sqlite.getUsers();
        for (int ctr = 0; ctr < users.size(); ctr++) {
            if (username.equals(users.get(ctr).getUsername())) { // check if username in list
                if (validatePassword(password, users.get(ctr).getPassword())) { // check if password matches user password
                    return users.get(ctr).getRole();
                }
            }
        }
        return 0;
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

