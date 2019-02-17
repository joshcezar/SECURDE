package Controller;


import Model.User;
import View.Frame;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest; 
import javax.xml.bind.DatatypeConverter; 
public class Main {
    
    public SQLite sqlite;
    
    public static void main(String[] args) {
        Main m = new Main();
        m.init();
        System.out.println(m.hashPassword("test"));
    }
    
    public void init(){
        // Initialize a driver object
        sqlite = new SQLite();

        // Create a database
        sqlite.createNewDatabase();
        
        // Drop users table if needed
        sqlite.dropUserTable();
        
        // Create users table if not exist
        sqlite.createUserTable();
        
        // Add users
        sqlite.addUser("admin", "qwerty1234" , 5);
        sqlite.addUser("manager", "qwerty1234", 4);
        sqlite.addUser("staff", "qwerty1234", 3);
        sqlite.addUser("client1", "qwerty1234", 2);
        sqlite.addUser("client2", "qwerty1234", 2);
        
        // Get users
        ArrayList<User> users = sqlite.getUsers();
        System.out.println(users.get(1).getUsername());
        System.out.println(users.get(2).getUsername());
        System.out.println(users.get(3).getUsername());
        for(int nCtr = 0; nCtr < users.size(); nCtr++){
            System.out.println("===== User " + users.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + users.get(nCtr).getUsername());
            System.out.println(" Password: " + users.get(nCtr).getPassword());
            System.out.println(" Role: " + users.get(nCtr).getRole());
        }
        
        // Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);
        
    }
    public String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String encoded;
            encoded = DatatypeConverter.printHexBinary(hash);
            return encoded;
        }   
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
    }
    public boolean searchUser(String username, String password){
        ArrayList<User> users = sqlite.getUsers();
        System.out.println(users.size());
        for(int ctr = 0; ctr < users.size(); ctr++){
            System.out.println(users.get(ctr).getUsername());
            if(username.equals(users.get(ctr).getUsername())){
                if(password.equals(users.get(ctr).getPassword())){
                    return true;
                }
            }
        }
        return false;
    }

}
