package Controller;

import Model.User;
import java.io.File;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class SQLite {

    String driverURL = "jdbc:sqlite:" + "database.db";
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private InetAddress ipAddress;

    void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {

        }
    }

    void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " username TEXT NOT NULL,\n"
                + " password TEXT NOT NULL,\n"
                + " role INTEGER DEFAULT 2\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
        }
    }

    void dropUserTable() {
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        String sql = "DROP TABLE users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
            ipAddress = InetAddress.getLocalHost();
            LOGGER.info("dropping table current user is : " + System.getProperty("user.name") + " IP ADDRESS IS: " + ipAddress);
        } catch (Exception ex) {
            LOGGER.error("Error in dropping table current user is : " + System.getProperty("user.name") + " IP ADDRESS IS: " + ipAddress);
        }
    }

    ArrayList<User> getUsers() {
        String sql = "SELECT id, username, password, role FROM users";
        ArrayList<User> users = new ArrayList<User>();
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);

        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role")));

            }
        } catch (Exception ex) {
            LOGGER.info("Error in retrieving table current user is : " + System.getProperty("user.name") + " IP ADDRESS IS: " + ipAddress);

        }
        return users;
    }

    void addUser(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES('" + username + "','" + password + "')";
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
//  For this activity, we would not be using prepared statements first.
//      String sql = "INSERT INTO users(username,password) VALUES(?,?)";
//      PreparedStatement pstmt = conn.prepareStatement(sql)) {
//      pstmt.setString(1, username);
//      pstmt.setString(2, password);
//      pstmt.executeUpdate();
        } catch (Exception ex) {
            LOGGER.info("Error in adding users, current user is : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress);

        }
    }

    void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username,password,role) VALUES('" + username + "','" + password + "','" + role + "')";
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (Exception ex) {
            LOGGER.info("Error in registering, current user is : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress);

        }
    }

    void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username='" + username + "');";
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            LOGGER.info("Error in removing users, current user is : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress);

        }
    }

    boolean checkExistingUsers(String username) {
        String sql = "SELECT username FROM users";
        ArrayList<User> users = new ArrayList<User>();
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        try (Connection conn = DriverManager.getConnection(driverURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (username.equals(rs.getString("username"))) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            LOGGER.info("Error in checking users, current user is : " + System.getProperty("user.name") + "|" + " IP ADDRESS IS: " + ipAddress);

        }
        return true;
    }

//    public String searchUser(String username, String password) {
//        String sql = "SELECT username FROM users WHERE username='" + username + "');";
//        String sq2 = "SELECT password FROM users WHERE password='" + password + "');";
//        try (Connection conn = DriverManager.getConnection(driverURL);
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(sql)) {
//                    String user = rs.getString("username");
//                    return user;
//        } catch (Exception ex) {
//        }
//        return "";
//    }
}
