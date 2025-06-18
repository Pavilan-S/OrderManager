package controller;

import dao.UserDAO;
import model.UserDetails;
import java.util.Random;

import static dao.UserDAO.users;

public class UserController {
    private static int adminHierarchyCounter = 1;

    public static void addUser(UserDetails user) {
        UserDAO.addUser(user);
    }
//    static {
//        // Create the default admin user
//        UserDetails admin = new UserDetails("AdminPavilan", "0000000000", "Admin Address", "admin@example.com", "AdminPavilan", "Pavi@123");
//        admin.setAdmin(true); // Set as admin
//        admin.setAdminId(generateAdminId(admin.getPassword(), adminHierarchyCounter++)); // Generate admin ID
//        users.add(admin); // Add the admin to the users list
//    }

    public static boolean userExists(String userName) {
        return UserDAO.userExists(userName);
    }

    public static boolean validateLogin(String userName, String password) {
        return UserDAO.validateLogin(userName, password);
    }

    public static UserDetails getUserByUsername(String userName) {
        return UserDAO.getUserByUsername(userName);
    }

    public static void deleteUser(String userName) {
        UserDAO.deleteUser(userName);
    }

    public static void viewAllUsers() {
        for (UserDetails user : UserDAO.getAllUsers()) {
            System.out.println("Username: " + user.getUsername() + ", UserID: " + user.getId() + ", Admin: " + (user.isAdmin() ? "Yes, AdminID: " + user.getAdminId() : "No"));
        }
    }

    // Check if a user is an admin
    public static boolean isAdmin(String userName) {
        UserDetails user = getUserByUsername(userName);
        return user != null && user.isAdmin();
    }

    // Promote or demote a user to/from admin
    public static void setAdmin(String userName, boolean isAdmin) {
        UserDetails user = getUserByUsername(userName);
        if (user != null) {
            user.setAdmin(isAdmin);
            if (isAdmin) {
                user.setAdminId(generateAdminId(user.getPassword(), adminHierarchyCounter++));
            } else {
                user.setAdminId(null);
            }
        }
    }

    // Generate a unique admin ID
    public static String generateAdminId(String password, int counter) {
        return "ADM" + password.substring(0, Math.min(3, password.length())) + counter;
    }

    // Check if the current admin can delete another admin
    public static boolean canDeleteAdmin(UserDetails currentAdmin, UserDetails targetAdmin) {
        return currentAdmin.getAdminId().compareTo(targetAdmin.getAdminId()) > 0;
    }

    public static void promoteToAdmin(String userName) {
        UserDetails user = UserController.getUserByUsername(userName);
        if (user != null && !user.isAdmin()) {
            user.setAdmin(true);
            user.setAdminId(UserController.generateAdminId(user.getPassword(), UserController.adminHierarchyCounter++));
            System.out.println("User " + userName + " promoted to admin. AdminID: " + user.getAdminId());
        } else if (user == null) {
            System.out.println("User not found.");
        } else {
            System.out.println("User is already an admin.");
        }
    }
}
//package controller;
//
//import model.UserDetails;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//public class UserController {
//    private static int adminHierarchyCounter = 1;
//
//    // Add a new user to the DB
//    public static void addUser(UserDetails user) {
//        String sql = "INSERT INTO users (client_name, phone_number, address, email, username, password, user_id, isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, user.getName());
//            ps.setString(2, user.getPhone());
//            ps.setString(3, user.getAddress());
//            ps.setString(4, user.getEmail());
//            ps.setString(5, user.getUsername());
//            ps.setString(6, user.getPassword());
//            ps.setString(7, user.getId());
//            ps.setString(8, user.isAdmin() ? "yes" : "no");
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Check if a username exists in DB
//    public static boolean userExists(String userName) {
//        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, userName);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Validate login credentials
//    public static boolean validateLogin(String userName, String password) {
//        String sql = "SELECT password FROM users WHERE username = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, userName);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                String storedPassword = rs.getString("password");
//                return storedPassword.equals(password);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Get UserDetails by username
//    public static UserDetails getUserByUsername(String userName) {
//        String sql = "SELECT * FROM users WHERE username = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, userName);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                UserDetails user = new UserDetails(
//                        rs.getString("client_name"),
//                        rs.getString("phone_number"),
//                        rs.getString("address"),
//                        rs.getString("email"),
//                        rs.getString("username"),
//                        rs.getString("password")
//                );
//                user.setUserId(rs.getString("user_id"));
//                user.setAdmin("yes".equalsIgnoreCase(rs.getString("isAdmin")));
//                return user;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // Delete user by username
//    public static void deleteUser(String userName) {
//        String sql = "DELETE FROM users WHERE username = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, userName);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Get all users from DB
//    public static ArrayList<UserDetails> getAllUsers() {
//        ArrayList<UserDetails> users = new ArrayList<>();
//        String sql = "SELECT * FROM users";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                UserDetails user = new UserDetails(
//                        rs.getString("client_name"),
//                        rs.getString("phone_number"),
//                        rs.getString("address"),
//                        rs.getString("email"),
//                        rs.getString("username"),
//                        rs.getString("password")
//                );
//                user.setUserId(rs.getString("user_id"));
//                user.setAdmin("yes".equalsIgnoreCase(rs.getString("isAdmin")));
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
//
//    // Check if user is admin
//    public static boolean isAdmin(String userName) {
//        String sql = "SELECT isAdmin FROM users WHERE username = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, userName);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return "yes".equalsIgnoreCase(rs.getString("isAdmin"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Promote a user to admin
//    public static void promoteToAdmin(String userName) {
//        String sql = "UPDATE users SET isAdmin = 'yes' WHERE username = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, userName);
//            int rowsUpdated = ps.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("User " + userName + " promoted to admin.");
//            } else {
//                System.out.println("User not found or already admin.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Initialize default admin on first run
//    static {
//        // Check if admin exists
//        if (!userExists("AdminPavilan")) {
//            UserDetails admin = new UserDetails("AdminPavilan", "0000000000", "Admin Address", "admin@example.com", "AdminPavilan", "Pavi@123");
//            admin.setUserId(generateRandomUserId());
//            admin.setAdmin(true);
//            addUser(admin);
//            System.out.println("Default admin created: AdminPavilan");
//        }
//    }
//
//    // Generate random user_id (8 digit alphanumeric)
//    public static String generateRandomUserId() {
//        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuilder sb = new StringBuilder();
//        java.util.Random rand = new java.util.Random();
//        for (int i = 0; i < 8; i++) {
//            sb.append(chars.charAt(rand.nextInt(chars.length())));
//        }
//        return sb.toString();
//    }
//}
//
