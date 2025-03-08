package electronicshop;

public class CartItem {
    private final Product product;
    private int quantity;
    private double subtotal;

    public CartItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity");
        }
        this.product = product;
        this.quantity = quantity;
        calculateSubtotal();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!product.isInStock(newQuantity)) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.quantity = newQuantity;
        calculateSubtotal();
    }

    private void calculateSubtotal() {
        this.subtotal = product.applyDiscount(quantity);
    }
}