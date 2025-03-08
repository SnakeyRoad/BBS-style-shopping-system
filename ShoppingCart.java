package electronicshop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<CartItem> cartItems;
    private double totalPrice;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public boolean addItem(Product product, int quantity) {
        // Check if product is in stock
        if (!product.isInStock(quantity)) {
            System.out.println("Insufficient stock for " + product.getName());
            return false;
        }

        // Check if product already in cart
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                try {
                    item.updateQuantity(item.getQuantity() + quantity);
                    recalculateTotalPrice();
                    return true;
                } catch (IllegalStateException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }

        // Add new item to cart
        CartItem newItem = new CartItem(product, quantity);
        cartItems.add(newItem);
        recalculateTotalPrice();
        return true;
    }

    public void removeItem(Product product, int quantity) {
        cartItems.removeIf(item -> {
            if (item.getProduct().getProductId() == product.getProductId()) {
                if (item.getQuantity() <= quantity) {
                    return true;
                } else {
                    item.updateQuantity(item.getQuantity() - quantity);
                    return false;
                }
            }
            return false;
        });
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        totalPrice = cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public double calculateTotalPrice() {
        return totalPrice;
    }

    public void clearCart() {
        cartItems.clear();
        totalPrice = 0.0;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(cartItems);
    }
}