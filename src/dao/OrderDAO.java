package dao;

import model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final List<Order> orders = new ArrayList<>();

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static List<Order> getOrdersByUserId(String userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    public static void deleteOrder(String orderId) {
        orders.removeIf(order -> order.getOrderId().equals(orderId));
    }
}