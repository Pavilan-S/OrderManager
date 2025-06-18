package model;
import java.util.Random;
import java.util.ArrayList;
public class Cart {
    private String cartId;
    private String userId;
    private ArrayList<Products> products;
    public Cart(String userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
        this.cartId = "CRT" + new Random().nextInt(9000) + 1000;
    }
    public void addToCart(Products product) {
        products.add(product);
    }

    public void removeFromCart(Products product) {
        products.remove(product);
    }

    public void emptyCart() {
        products.clear();
    }

    public Order orderFromCart() {
        Order order = new Order(userId);
        for (Products product : products) {
            order.addProduct(product, 1); // Assuming quantity is 1 for simplicity
        }
        emptyCart();
        return order;
    }
    public void viewCart() {
        System.out.println("Cart ID: " + cartId);
        for (Products product : products) {
            System.out.println("Product: " + product.getName() + ", Price: " + product.getPrice());
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public ArrayList<Products> getProducts() {
return products;
    }
}