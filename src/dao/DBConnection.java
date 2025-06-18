package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    final private static String url=System.getenv("url");
    final private static String user=System.getenv("username");
    final private static String password=System.getenv("password");
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        }
    }
}
