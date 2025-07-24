package view;

import controller.ProductController;
import model.Cart;
import model.Order;
import model.Products;
import dao.CartDAO;
import dao.OrderDAO;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    public static void userMenu(String userId) {
        Scanner scanner = new Scanner(System.in);
        Cart cart = CartDAO.getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart(userId);
            CartDAO.addCart(cart);
        }


        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. View Products\n2. Add to Cart\n3. View Cart\n4. Place Order\n5. View Orders\n6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ProductView.viewProducts();
                    break;
                case 2:
                    addToCart(scanner, cart);
                    break;
                case 3:
                    cart.viewCart();
                    break;
                case 4:
                    placeOrder(cart);
                    break;
                case 5:
                    viewOrders(userId);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addToCart(Scanner scanner, Cart cart) {
        System.out.print("Enter product ID to add to cart: ");
        String productId = scanner.nextLine();
        Products product = null;
        for (Products p : ProductController.getInventory()) {
            if (p.getProductId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product != null) {
            cart.addToCart(product);
            CartDAO.updateCart(cart);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void placeOrder(Cart cart) {
        if (cart.getProducts().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        Order order = cart.orderFromCart();
        OrderDAO.addOrder(order);
        CartDAO.updateCart(cart);
        System.out.println("Order placed successfully. Order ID: " + order.getOrderId());
    }

    private static void viewOrders(String userId) {
        List<Order> orders = OrderDAO.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orders) {
                order.viewOrder();
            }
        }
    }


}
