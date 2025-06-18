package model;

import java.util.Random;

public class Products {
    private String name;
    private double price;
    private int quantity;
    private String productId;
    private String type;

    public Products(String name, double price, int quantity, String type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.productId = String.valueOf(type.charAt(0)) + String.valueOf(name.charAt(0)) + setProductId();
    }

    private int setProductId() {
        Random rand = new Random();
        return rand.nextInt(900) + 100;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }
}