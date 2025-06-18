package view;

import controller.ProductController;
import model.Products;
import java.util.Scanner;

public class ProductView {
    public static void modifyProducts() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Modify Products:");
            System.out.println("1. Add Product\n2. Update Product\n3. Remove Product\n4. View Products\n5. Back");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    updateProduct(scanner);
                    break;
                case 3:
                    removeProduct(scanner);
                    break;
                case 4:
                    viewProducts();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        Products product = new Products(name, price, quantity, type);
        ProductController.addProduct(product);
        System.out.println("Product added successfully. Product ID: " + product.getProductId());
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter product ID to update: ");
        String productId = scanner.nextLine();
        Products product = null;
        for (Products p : ProductController.getInventory()) {
            if (p.getProductId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Select attribute to update:");
        System.out.println("1. Price\n2. Quantity\n3. Type");
        int attr = scanner.nextInt();
        scanner.nextLine();

        switch (attr) {
            case 1:
                System.out.print("Enter new price: ");
                product.setPrice(scanner.nextDouble());
                scanner.nextLine();
                System.out.println("Price updated.");
                break;
            case 2:
                System.out.print("Enter new quantity: ");
                product.setQuantity(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Quantity updated.");
                break;
            case 3:
                System.out.print("Enter new type: ");
                product.setType(scanner.nextLine());
                System.out.println("Type updated.");
                break;
            default:
                System.out.println("Invalid attribute.");
        }
    }

    private static void removeProduct(Scanner scanner) {
        System.out.print("Enter product ID to remove: ");
        String productId = scanner.nextLine();
        Products product = null;
        for (Products p : ProductController.getInventory()) {
            if (p.getProductId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product != null) {
            ProductController.removeProduct(product);
            System.out.println("Product removed.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void viewProducts() {
        System.out.println("Products in Inventory:");
        for (Products product : ProductController.getInventory()) {
            System.out.println("Product ID: " + product.getProductId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity() + ", Type: " + product.getType());
        }
    }
}
//package view;
//
//import controller.ProductController;
//import model.Products;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class ProductView {
//    public static void modifyProducts() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("\nModify Products:");
//            System.out.println("1. Add Product\n2. Update Product\n3. Remove Product\n4. View Products\n5. Back");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1 -> addProduct(scanner);
//                case 2 -> updateProduct(scanner);
//                case 3 -> removeProduct(scanner);
//                case 4 -> viewProducts();
//                case 5 -> { return; }
//                default -> System.out.println("Invalid choice.");
//            }
//        }
//    }
//
//    private static void addProduct(Scanner scanner) {
//        System.out.print("Enter product name: ");
//        String name = scanner.nextLine();
//        System.out.print("Enter price: ");
//        double price = scanner.nextDouble();
//        System.out.print("Enter quantity: ");
//        int quantity = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Enter type: ");
//        String type = scanner.nextLine();
//
//        Products product = new Products(name, price, quantity, type);
//        if (ProductController.addProduct(product)) {
//            System.out.println("Product added successfully. Product ID: " + product.getProductId());
//        } else {
//            System.out.println("Error: Product not added.");
//        }
//    }
//
//    private static void updateProduct(Scanner scanner) {
//        System.out.print("Enter product ID to update: ");
//        String productId = scanner.nextLine();
//
//        Products product = ProductController.getProductById(productId);
//        if (product == null) {
//            System.out.println("Product not found.");
//            return;
//        }
//
//        System.out.println("Select attribute to update:");
//        System.out.println("1. Price\n2. Quantity\n3. Type");
//        int attr = scanner.nextInt();
//        scanner.nextLine();
//
//        boolean success = false;
//
//        switch (attr) {
//            case 1 -> {
//                System.out.print("Enter new price: ");
//                double newPrice = scanner.nextDouble();
//                scanner.nextLine();
//                success = ProductController.updateProductPrice(productId, newPrice);
//            }
//            case 2 -> {
//                System.out.print("Enter new quantity: ");
//                int newQty = scanner.nextInt();
//                scanner.nextLine();
//                success = ProductController.updateProductQuantity(productId, newQty);
//            }
//            case 3 -> {
//                System.out.print("Enter new type: ");
//                String newType = scanner.nextLine();
//                success = ProductController.updateProductType(productId, newType);
//            }
//            default -> System.out.println("Invalid attribute.");
//        }
//
//        if (success) {
//            System.out.println("Product updated.");
//        } else {
//            System.out.println("Update failed.");
//        }
//    }
//
//    private static void removeProduct(Scanner scanner) {
//        System.out.print("Enter product ID to remove: ");
//        String productId = scanner.nextLine();
//
//        boolean removed = ProductController.removeProduct(productId);
//        if (removed) {
//            System.out.println("Product removed.");
//        } else {
//            System.out.println("Product not found or could not be removed.");
//        }
//    }
//
//    public static void viewProducts() {
//        List<Products> products = ProductController.getAllProducts();
//        if (products.isEmpty()) {
//            System.out.println("No products in inventory.");
//            return;
//        }
//
//        System.out.println("Products in Inventory:");
//        for (Products product : products) {
//            System.out.printf("ID: %s | Name: %s | Price: %.2f | Qty: %d | Type: %s%n",
//                    product.getProductId(), product.getName(), product.getPrice(),
//                    product.getQuantity(), product.getType());
//        }
//    }
//}
