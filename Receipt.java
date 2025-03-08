package electronicshop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Receipt {
    private final User user;
    private final List<CartItem> items;
    private final double totalAmount;
    private final Date purchaseDate;

    // Full constructor
    public Receipt(User user, List<CartItem> items, double totalAmount) {
        this.user = user;
        this.items = items;
        this.totalAmount = totalAmount;
        this.purchaseDate = new Date();
    }

    public void printReceipt() {
        System.out.println("==== ELECTRONIC SHOP RECEIPT ====");
        System.out.println("User: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("Purchase Date: " + dateFormat.format(purchaseDate));

        System.out.println("\nPurchased Items:");

        double totalBeforeDiscount = 0.0;
        double totalSavings = 0.0;

        for (CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double originalPrice = product.getPrice() * quantity;
            double discountedPrice = item.getSubtotal();
            double savings = originalPrice - discountedPrice;

            System.out.printf("%s x%d: $%.2f each\n",
                    product.getName(),
                    quantity,
                    product.getPrice());

            if (savings > 0) {
                System.out.printf("  Original: €%.2f, After Discount: €%.2f (Saved: €%.2f)\n",
                        originalPrice, discountedPrice, savings);
                System.out.println("  Applied Discount: " + product.getDiscountStrategy().getDescription());
            } else {
                System.out.printf("  Subtotal: €%.2f\n", discountedPrice);
            }

            totalBeforeDiscount += originalPrice;
            totalSavings += savings;
        }

        System.out.println("\nSubtotal before discounts: €" + String.format("%.2f", totalBeforeDiscount));
        if (totalSavings > 0) {
            System.out.println("Total Savings: €" + String.format("%.2f", totalSavings));
        }
        System.out.println("Final Amount: €" + String.format("%.2f", totalAmount));
        System.out.println("=================================");
    }
}