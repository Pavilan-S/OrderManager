package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.cj.protocol.Resultset;
import dao.DBConnection;
import dao.ProductsDAO;
import model.Products;

public class ProductController {
    public static void addProduct(Products product) {
        ProductsDAO.addProduct(product);
        Connection con= DBConnection.getConnection();
        String productId=product.getProductId();
        double price=product.getPrice();
        String type=product.getType();
        String unit=product.getUnit();
        String name=product.getName();
        int quantity=product.getQuantity();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        try{
            con.setCatalog("pavilan_userdetails");
            String sql = "INSERT INTO Products (productId, name, price, quantity, unit, type) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1,productId);
            preparedStatement.setString(2,name);
            preparedStatement.setDouble(3,price);
            preparedStatement.setInt(4,quantity);
            preparedStatement.setString(5,unit);
            preparedStatement.setString(6,type);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeProduct(Products product) {
        ProductsDAO.removeProduct(product);
    }

    public static ArrayList<Products> getInventory() {
        return ProductsDAO.getInventory();
    }
}