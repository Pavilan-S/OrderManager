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
//package view;
//
//import controller.UserController;
//import controller.ProductController;
//import model.UserDetails;
//
//import java.util.Scanner;
//
//public class AdminView {
//
//    public static void adminMenu(String currentAdminUsername) {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\nAdmin Menu:");
//            System.out.println("1. View All Users");
//            System.out.println("2. Promote User to Admin");
//            System.out.println("3. Delete User");
//            System.out.println("4. Modify Inventory");
//            System.out.println("5. Log-Out");
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // consume newline
//
//            switch (choice) {
//                case 1 -> UserController.viewAllUsers();
//                case 2 -> {
//                    System.out.print("Enter username to promote: ");
//                    String userNameToPromote = scanner.nextLine();
//                    boolean success = UserController.promoteToAdmin(userNameToPromote);
//                    if (success) {
//                        System.out.println("User promoted to admin.");
//                    } else {
//                        System.out.println("Failed to promote user. Make sure the username exists.");
//                    }
//                }
//                case 3 -> {
//                    System.out.print("Enter username to delete: ");
//                    String userNameToDelete = scanner.nextLine();
//                    deleteUser(currentAdminUsername, userNameToDelete);
//                }
//                case 4 -> ProductView.modifyProducts();
//                case 5 -> {
//                    System.out.println("Logging out...");
//                    return;
//                }
//                default -> System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }
//
//    private static void deleteUser(String currentAdminUsername, String targetUserName) {
//        UserDetails currentAdmin = UserController.getUserByUsername(currentAdminUsername);
//        UserDetails targetUser = UserController.getUserByUsername(targetUserName);
//
//        if (targetUser == null) {
//            System.out.println("User not found.");
//            return;
//        }
//
//        if (targetUser.isAdmin()) {
//            if (UserController.canDeleteAdmin(currentAdmin, targetUser)) {
//                if (UserController.deleteUser(targetUserName)) {
//                    System.out.println("Admin '" + targetUserName + "' deleted successfully.");
//                } else {
//                    System.out.println("Failed to delete admin.");
//                }
//            } else {
//                System.out.println("You do not have permission to delete this admin.");
//            }
//        } else {
//            if (UserController.deleteUser(targetUserName)) {
//                System.out.println("User '" + targetUserName + "' deleted successfully.");
//            } else {
//                System.out.println("Failed to delete user.");
//            }
//        }
//    }
//}
