package dao;

import model.UserDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public static ArrayList<UserDetails> users = new ArrayList<>();

    public static void addUser(UserDetails user) {
        users.add(user);
    }

    public static boolean userExists(String userName) {
        return users.stream().anyMatch(u -> u.getUsername().equals(userName));
    }

    public static boolean validateLogin(String userName, String password) {
        Connection con = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con.setCatalog("user_details");
            String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static UserDetails getUserByUsername(String userName) {
        Connection con = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            con.setCatalog("user_details");
            String sql = "SELECT * FROM users WHERE username = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new UserDetails(name, phone, address, email, username, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }


    public static void deleteUser(String userName) {
        UserDetails user = getUserByUsername(userName);
        if (user != null) {
            users.remove(user);
        }
    }


    public static ArrayList<UserDetails> getAllUsers() {
        return users;
    }
}