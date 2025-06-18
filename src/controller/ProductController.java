package controller;
import java.util.ArrayList;
import dao.ProductsDAO;
import model.Products;

public class ProductController {
    public static void addProduct(Products product) {
        ProductsDAO.addProduct(product);
    }

    public static void removeProduct(Products product) {
        ProductsDAO.removeProduct(product);
    }

    public static ArrayList<Products> getInventory() {
        return ProductsDAO.getInventory();
    }
}
//package controller;
//
//import model.Products;
//import java.sql.*;
//import java.util.ArrayList;
//
//public class ProductController {
//
//    // Add product to DB
//    public static void addProduct(Products product) {
//        String sql = "INSERT INTO Products (productId, name, price, quantity, type) VALUES (?, ?, ?, ?, ?)";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, product.getProductId());
//            ps.setString(2, product.getName());
//            ps.setDouble(3, product.getPrice());
//            ps.setInt(4, product.getQuantity());
//            ps.setString(5, product.getType());
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Remove product from DB by productId
//    public static void removeProduct(Products product) {
//        String sql = "DELETE FROM Products WHERE productId = ?";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, product.getProductId());
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Get all products from DB
//    public static ArrayList<Products> getInventory() {
//        ArrayList<Products> inventory = new ArrayList<>();
//        String sql = "SELECT * FROM Products";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                String productId = rs.getString("productId");
//                String name = rs.getString("name");
//                double price = rs.getDouble("price");
//                int quantity = rs.getInt("quantity");
//                String type = rs.getString("type");
//
//                Products product = new Products(name, price, quantity, type);
//                // Since productId is generated in constructor, override it here
//                // or better, create a constructor accepting productId
//                product.setProductId(productId);
//
//                inventory.add(product);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return inventory;
//    }
//}
