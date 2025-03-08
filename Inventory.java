package electronicshop;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Product> products;

    public Inventory() {
        products = new ArrayList<>();
        initializeProducts();
    }

    private void initializeProducts() {
        // Initialize products with different discount strategies according to the updated list
        addProduct(new Product(1, "Smartphone", 799.99, 15, "Phones",
                new PercentageDiscount(0.15))); // 15% off

        addProduct(new Product(2, "Laptop", 1299.99, 10, "Electronics",
                new PercentageDiscount(0.20))); // 20% off

        addProduct(new Product(3, "Tablet", 499.99, 12, "Electronics",
                new BulkDiscount(3, 0.10))); // 10% off when buying 3 or more

        addProduct(new Product(4, "Wireless Earbuds", 199.99, 30, "Audio",
                new PercentageDiscount(0.20))); // 20% off

        addProduct(new Product(5, "Smartwatch", 249.99, 20, "Wearables",
                new PercentageDiscount(0.15))); // 15% off

        addProduct(new Product(6, "Gaming Console", 499.99, 8, "Gaming",
                new BulkDiscount(2, 0.15))); // 15% off when buying 2 or more

        addProduct(new Product(7, "Mechanical Keyboard", 129.99, 25, "Accessories",
                new BOGODiscount(2))); // Buy one, get one free

        addProduct(new Product(8, "Bluetooth Speaker", 79.99, 18, "Audio",
                new BulkDiscount(3, 0.20))); // 20% off when buying 3 or more

        addProduct(new Product(9, "External SSD Hard Drive 1TB", 99.99, 22, "Storage",
                new BOGODiscount(2))); // Buy one, get one free

        addProduct(new Product(10, "Monitor", 299.99, 14, "Display",
                new PercentageDiscount(0.25))); // 25% off
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProductList() {
        return new ArrayList<>(products);
    }

    public Product findProductById(int productId) {
        return products.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }

    public void updateProductStock(Product product, int quantity) {
        product.updateStockQuantity(quantity);
    }
}