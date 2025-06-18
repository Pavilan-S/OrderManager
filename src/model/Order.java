package model;
import java.util.Random;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private String userId;
    private ArrayList<Products> products;
    private double totalPrice;

    public Order(String userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
        this.orderId = "ORD" + new Random().nextInt(9000) + 1000;
        this.totalPrice = 0.0;
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void addProduct(Products product, int quantity) {
        if (product.getQuantity() >= quantity) {
            product.setQuantity(product.getQuantity() - quantity);
            products.add(product);
            totalPrice += product.getPrice() * quantity;
        } else {
            System.out.println("Insufficient stock for product: " + product.getName());
        }
    }

    public void removeProduct(Products product) {
        products.remove(product);
        totalPrice -= product.getPrice();
    }

    public void cancelOrder() {
        for (Products product : products) {
            product.setQuantity(product.getQuantity() + 1); // Assuming quantity was reduced by 1 when added
        }
        products.clear();
        totalPrice = 0.0;
    }

    public void viewOrder() {
        System.out.println("Order ID: " + orderId);
        for (Products product : products) {
            System.out.println("Product: " + product.getName() + ", Price: " + product.getPrice());
        }
        System.out.println("Total Price: " + totalPrice);
    }

    public Object getUserId() {
        return userId;
    }
}