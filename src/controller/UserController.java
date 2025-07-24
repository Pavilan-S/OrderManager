package controller;

import dao.DBConnection;
import dao.UserDAO;
import model.UserDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static dao.UserDAO.users;

public class UserController {
    private static int adminHierarchyCounter = 1;

    public static void addUser(UserDetails user) {
        UserDAO.addUser(user);
    }
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
    public static boolean isAdmin(String userName,String password) throws SQLException {
        PreparedStatement preparedStatement=null;
        Connection con= DBConnection.getConnection();
        ResultSet rs=null;
        try {
            con.setCatalog("pavilan_userdetails");
            String sql="SELECT 1 from users where username=? and password=? and isAdmin= 'yes'";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            rs=preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            return rs.isBeforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
