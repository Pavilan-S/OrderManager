package dao;

import model.Products;
import java.util.ArrayList;

public class ProductsDAO {
    private static ArrayList<Products> inventory = new ArrayList<>();

    public static void addProduct(Products product) {
        inventory.add(product);
    }

    public static void removeProduct(Products product) {
        inventory.remove(product);
    }

    public static ArrayList<Products> getInventory() {
        return inventory;
    }
}