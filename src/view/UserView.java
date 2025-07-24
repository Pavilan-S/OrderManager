package view;
import Menu.Menu;
import com.mysql.cj.protocol.Resultset;
import controller.UserController;
import dao.DBConnection;
import model.UserDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class UserView {
    public static void signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        Connection con= DBConnection.getConnection();
        if (UserController.userExists(userName)) {
            System.out.println("Username already exists. Please choose another username.");
        } else {
            UserDetails user = new UserDetails(name, phone, address, email, userName, password);
            UserController.addUser(user);
            System.out.println("User registered successfully!");

            PreparedStatement preparedStatement = null;
            PreparedStatement checkStatement = null;
            ResultSet rs = null;

            try {
                con.setCatalog("pavilan_userdetails");
                String userId = "";
                Random rand = new Random();
                boolean isUnique = false;

                while (!isUnique) {
                    int userIdInt = rand.nextInt(100000) + 10000;
                    userId = Integer.toString(userIdInt);

                    String checkSql = "SELECT 1 FROM users WHERE userId = ?";
                    checkStatement = con.prepareStatement(checkSql);
                    checkStatement.setString(1, userId);
                    rs = checkStatement.executeQuery();
                    if (!rs.next()) {
                        isUnique = true;
                    }
                }

                String sql = "INSERT INTO users (userId, name, phone, address, email, username, password, isAdmin, adminId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = con.prepareStatement(sql);

                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, userName);
                preparedStatement.setString(7, password);
                preparedStatement.setString(8, "no");
                preparedStatement.setString(9, null);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (checkStatement != null) checkStatement.close();
                    if (preparedStatement != null) preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        }
    }

    public static void signIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (UserController.validateLogin(userName, password)) {
            System.out.println("Login successful!");
            try {
                if (UserController.isAdmin(userName,password)) {
                    System.out.println("Enter Your choice\n1.User Access\n2.Admin Access\n3.Back");
                    int choice=scanner.nextInt();
                    switch (choice){
                        case 1:
                            UserMenu.userMenu(UserController.getUserByUsername(userName).getId());
                            break;
                        case 2:
                            AdminView.adminMenu(userName);
                            break;
                        default:
                            Menu.menu();

                    }
                } else {
                    UserMenu.userMenu(UserController.getUserByUsername(userName).getId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid username or password.");
        }

    }
}
