package view;

import controller.UserController;
import controller.ProductController;
import model.UserDetails;
import java.util.Scanner;

public class AdminView {
    public static void adminMenu(String currentAdminUsername) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. View All Users\n2. Promote User to Admin\n3. Delete User\n4. Modify Inventory\n5. Log-Out");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    UserController.viewAllUsers();
                    break;
                case 2:
                    System.out.print("Enter username to promote: ");
                    String userNameToPromote = scanner.nextLine();
                    UserController.promoteToAdmin(userNameToPromote);
                    break;
                case 3:
                    System.out.print("Enter username to delete: ");
                    String userNameToDelete = scanner.nextLine();
                    deleteUser(currentAdminUsername, userNameToDelete);
                    break;
                case 4:
                    ProductView.modifyProducts();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void deleteUser(String currentAdminUsername, String targetUserName) {
        UserDetails currentAdmin = UserController.getUserByUsername(currentAdminUsername);
        UserDetails targetUser = UserController.getUserByUsername(targetUserName);
        if (targetUser == null) {
            System.out.println("User not found.");
            return;
        }
        if (targetUser.isAdmin()) {
            if (UserController.canDeleteAdmin(currentAdmin, targetUser)) {
                UserController.deleteUser(targetUserName);
                System.out.println("Admin " + targetUserName + " deleted.");
            } else {
                System.out.println("You cannot delete this admin.");
            }
        } else {
            UserController.deleteUser(targetUserName);
            System.out.println("User " + targetUserName + " deleted.");
        }
    }
}