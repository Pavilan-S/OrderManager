package dao;
import model.Cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DBConnection.getConnection;

public class CartDAO{
    private static List<Cart> carts = new ArrayList<>();

    public static void addCart(Cart cart) {
        carts.add(cart);
    }
    public static Cart getCartByUserId(String userId) {
        getConnection();
        for (Cart cart : carts) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        {
            int a;
        }
        return null;
    }
    public static void updateCart(Cart cart) {
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getUserId().equals(cart.getUserId())) {
                carts.set(i, cart);
                break;
            }
        }
    }
    public static void deleteCart(String userId) {
        carts.removeIf(cart -> cart.getUserId().equals(userId));
    }
}