package electronicshop;

public class Product {
    private final int productId;
    private final String name;
    private final double price;
    private int stockQuantity;
    private final String category;
    private final DiscountStrategy discountStrategy;

    // Full constructor
    public Product(int productId, String name, double price, int stockQuantity, String category, DiscountStrategy discountStrategy) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.discountStrategy = discountStrategy;
    }

    // Getters and Setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public int getProductId() { return productId; }
    public String getCategory() { return category; }
    public DiscountStrategy getDiscountStrategy() { return discountStrategy; }

    public void updateStockQuantity(int quantity) {
        if (quantity >= 0) {
            this.stockQuantity = quantity;
        } else {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    public boolean isInStock(int requestedQuantity) {
        return stockQuantity >= requestedQuantity;
    }

    public double applyDiscount(int quantity) {
        return discountStrategy.applyDiscount(this, quantity);
    }
}