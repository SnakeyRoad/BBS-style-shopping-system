package electronicshop;

// Discount Strategy Interface
public interface DiscountStrategy {
    double applyDiscount(Product product, int quantity);
    String getDescription();
}

// BOGO (Buy One Get One) Discount
record BOGODiscount(int discountThreshold) implements DiscountStrategy {

    @Override
    public double applyDiscount(Product product, int quantity) {
        if (quantity >= discountThreshold) {
            int freeItems = quantity / 2;
            return product.getPrice() * (quantity - freeItems);
        }
        return product.getPrice() * quantity;
    }

    @Override
    public String getDescription() {
        return "Buy one get one free (Buy " + discountThreshold + " or more)";
    }
}

// Percentage Discount
record PercentageDiscount(double discountPercentage) implements DiscountStrategy {

    @Override
    public double applyDiscount(Product product, int quantity) {
        double originalPrice = product.getPrice() * quantity;
        return originalPrice * (1 - discountPercentage);
    }

    @Override
    public String getDescription() {
        return String.format("%.0f%% off regular price", discountPercentage * 100);
    }
}

// Bulk Discount
record BulkDiscount(int bulkThreshold, double discountPercentage) implements DiscountStrategy {

    @Override
    public double applyDiscount(Product product, int quantity) {
        double originalPrice = product.getPrice() * quantity;
        return quantity >= bulkThreshold ?
                originalPrice * (1 - discountPercentage) :
                originalPrice;
    }

    @Override
    public String getDescription() {
        return String.format("%.0f%% off when buying %d or more", discountPercentage * 100, bulkThreshold);
    }
}