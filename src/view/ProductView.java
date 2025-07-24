package view;

import controller.ProductController;
import dao.DBConnection;
import model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        System.out.print("Enter quantity:   ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Unit");
        String unit=scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        Products product = new Products(name, price, quantity,unit, type);
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
            Connection con= DBConnection.getConnection();
            PreparedStatement preparedStatement=null;
            ResultSet rs=null;
            try{
               String sql="SELECT * from Products";
               preparedStatement=con.prepareStatement(sql);
               rs=preparedStatement.executeQuery();
               while(rs.next()){
                  String name=rs.getString("productId");
                  double price=rs.getDouble("price");
                  int quantity=rs.getInt("quantity");
                  String unit=rs.getString("unit");
                  String type=rs.getString("type");
                  System.out.println("Name: "+name+" Price: "+price+" Quantity: "+quantity+" "+unit+" type: "+type);
               }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }
}
