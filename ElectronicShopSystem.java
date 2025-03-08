package electronicshop;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.Console;
import java.util.Map;


public class ElectronicShopSystem {
    private static Inventory inventory;
    private static AuthenticationManager authManager;
    private static Scanner scanner;
    private static double totalRevenue = 0.0; // Track total revenue for restocking

    public static void main(String[] args) {
        inventory = new Inventory();
        authManager = new AuthenticationManager();
        scanner = new Scanner(System.in);

        // Initial setup
        setupInitialUsers();
        mainMenu();
    }

    private static void setupInitialUsers() {
        User customer = new User("user", "password123", "user@gmail.com");
        Admin admin = new Admin("admin", "admin123", "admin@chrisshop.com", "MANAGER");

        authManager.registerUser(customer);
        authManager.registerAdmin(admin);
    }

    // Helper method to safely get integer input
    private static int getIntInput() {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
        scanner.nextLine(); // Consume newline
        return choice;
    }

    private static String getPasswordInput() {
        Console console = System.console();

        // If running in a terminal with proper console support (JAR File)
        if (console != null) {
            System.out.print("Enter password: ");
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        }
        // Fallback for environments without Console support
        else {
            System.out.println("WARNING: In IDE password will be visible as you type.");
            System.out.print("Enter password: ");
            return scanner.nextLine(); // Fallback to Scanner
        }
    }


    private static void mainMenu() {
        while (true) {
            System.out.println("""
                    
                    
                    
                                   __    __     _                            _            ___ _          _     _   _            _           _           _                   _            _                              \s
                     _____ _____  / / /\\ \\ \\___| | ___ ___  _ __ ___   ___  | |_ ___     / __\\ |__  _ __(_)___| |_(_) __ _ _ __( )__    ___| | ___  ___| |_ _ __ ___  _ __ (_) ___   ___| |__   ___  _ __    _____ _____\s
                    |_____|_____| \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\   / /  | '_ \\| '__| / __| __| |/ _` | '_ \\/ __|  / _ \\ |/ _ \\/ __| __| '__/ _ \\| '_ \\| |/ __| / __| '_ \\ / _ \\| '_ \\  |_____|_____|
                    |_____|_____|  \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | || (_) | / /___| | | | |  | \\__ \\ |_| | (_| | | | \\__ \\ |  __/ |  __/ (__| |_| | | (_) | | | | | (__  \\__ \\ | | | (_) | |_) | |_____|_____|
                                    \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/  \\____/|_| |_|_|  |_|___/\\__|_|\\__,_|_| |_|___/  \\___|_|\\___|\\___|\\__|_|  \\___/|_| |_|_|\\___| |___/_| |_|\\___/| .__/              \s
                                                                                                                                                                                                    |_|                 \s
                                  _                                        _           _            _                                            _                                                                      \s
                      /\\/\\   __ _| | _____   _   _  ___  _   _ _ __    ___| |__   ___ (_) ___ ___  | |_ ___    _ __  _ __ ___   ___ ___  ___  __| |_                                                                    \s
                     /    \\ / _` | |/ / _ \\ | | | |/ _ \\| | | | '__|  / __| '_ \\ / _ \\| |/ __/ _ \\ | __/ _ \\  | '_ \\| '__/ _ \\ / __/ _ \\/ _ \\/ _` (_)                                                                   \s
                    / /\\/\\ \\ (_| |   <  __/ | |_| | (_) | |_| | |    | (__| | | | (_) | | (_|  __/ | || (_) | | |_) | | | (_) | (_|  __/  __/ (_| |_                                                                    \s
                    \\/    \\/\\__,_|_|\\_\\___|  \\__, |\\___/ \\__,_|_|     \\___|_| |_|\\___/|_|\\___\\___|  \\__\\___/  | .__/|_|  \\___/ \\___\\___|\\___|\\__,_(_)                                                                   \s
                                             |___/                                                            |_|                                                                                                       \s
                    
                    """);

            System.out.println("==== Main Menu ====");
            System.out.println("1⃣  User Login");
            System.out.println("2⃣  Admin Login");
            System.out.println("3⃣  Register New User");
            System.out.println("4⃣  Exit");
            System.out.println("===================");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: userLoginMenu(); break;
                case 2: adminLoginMenu(); break;
                case 3: registerNewUser(); break;
                case 4:
                    System.out.println("Thank you for using Christian's Electronic Shopping System!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void registerNewUser() {
        System.out.println("\n==== User Registration ====");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // Check if username is "admin"
        if (username.equalsIgnoreCase("admin")) {
            System.out.println("admin is a protected account! Please choose a different user name.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        User newUser = new User(username, password, email);
        boolean registered = authManager.registerUser(newUser);

        if (registered) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
    }

    private static void userLoginMenu() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        String password = getPasswordInput(); // Use the new password input method

        User user = authManager.authenticateUser(username, password);
        if (user != null) {
            userShoppingMenu(user);
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void userShoppingMenu(User user) {
        ShoppingCart cart = user.getShoppingCart();

        while (true) {
            System.out.println("\n==== Shopping Menu ====");
            System.out.println("1⃣ View Products");
            System.out.println("2⃣ Add Product to Cart");
            System.out.println("3⃣ Remove Product from Cart");
            System.out.println("4⃣ View Cart");
            System.out.println("5⃣ Checkout");
            System.out.println("6⃣ Logout");
            System.out.println("=======================");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: displayProducts(); break;
                case 2: addProductToCart(cart); break;
                case 3: removeProductFromCart(cart); break;
                case 4: viewCart(cart); break;
                case 5: checkout(user, cart); break;
                case 6: return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void displayProducts() {
        System.out.println("\n==== Available Products ====");
        inventory.getProductList().forEach(product -> {
            System.out.printf("ID: %d, Name: %s, Price: €%.2f, Stock: %d, Category: %s\n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getCategory());

            // Display discount information
            String discountInfo = getDiscountInfo(product);
            if (!discountInfo.isEmpty()) {
                System.out.println("    Discount: " + discountInfo);
            }
        });
    }

    private static String getDiscountInfo(Product product) {
        if (product.getDiscountStrategy() instanceof PercentageDiscount(double discountPercentage)) {
            double percentage = discountPercentage * 100;
            if (percentage > 0) {
                return String.format("%.0f%% off the regular price", percentage);
            }
        } else if (product.getDiscountStrategy() instanceof BOGODiscount(int discountThreshold)) {
            return String.format("Buy one get one free (when buying %d or more)", discountThreshold);
        } else if (product.getDiscountStrategy() instanceof BulkDiscount(int bulkThreshold, double discountPercentage)) {
            double percentage = discountPercentage * 100;
            return String.format("%.0f%% off when buying %d or more", percentage, bulkThreshold);
        }
        return "";
    }

    private static void addProductToCart(ShoppingCart cart) {
        System.out.print("Enter Product ID to add: ");
        int productId = getIntInput();
        System.out.print("Enter Quantity: ");
        int quantity = getIntInput();

        Product product = inventory.findProductById(productId);
        if (product != null) {
            if (quantity <= product.getStockQuantity()) {
                boolean added = cart.addItem(product, quantity);
                if (added) {
                    System.out.println("Product added to cart!");
                    // Display discount information if applicable
                    String discountInfo = getDiscountInfo(product);
                    if (!discountInfo.isEmpty()) {
                        System.out.println("Discount applied: " + discountInfo);
                    }
                } else {
                    System.out.println("Failed to add product.");
                }
            } else {
                System.out.println("Not enough stock available! Available: " + product.getStockQuantity());
            }
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void removeProductFromCart(ShoppingCart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }

        viewCart(cart);
        System.out.print("Enter Product ID to remove: ");
        int productId = getIntInput();
        System.out.print("Enter Quantity to remove (0 for all): ");
        int quantity = getIntInput();

        Product product = inventory.findProductById(productId);
        if (product != null) {
            if (quantity == 0) {
                // Remove all of this product
                cart.removeItem(product, Integer.MAX_VALUE);
                System.out.println("Product removed from cart.");
            } else {
                cart.removeItem(product, quantity);
                System.out.println("Quantity updated in cart.");
            }
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    private static void viewCart(ShoppingCart cart) {
        System.out.println("\n==== Your Shopping Cart ====");
        List<CartItem> items = cart.getItems();

        if (items.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }

        for (CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double subtotal = item.getSubtotal();
            double originalPrice = product.getPrice() * quantity;

            System.out.printf("ID: %d, Name: %s, Price: €%.2f, Quantity: %d\n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    quantity);

            // Show discount if applicable
            if (subtotal < originalPrice) {
                double savings = originalPrice - subtotal;
                System.out.printf("  Original: €%.2f, Discounted: €%.2f (You save: €%.2f)\n",
                        originalPrice, subtotal, savings);
            } else {
                System.out.printf("  Subtotal: €%.2f\n", subtotal);
            }
        }

        System.out.printf("\nCart Total: €%.2f\n", cart.calculateTotalPrice());
    }

    private static void checkout(User user, ShoppingCart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty! Nothing to checkout.");
            return;
        }

        // Verify stock availability one more time before checkout
        boolean stockAvailable = true;
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (item.getQuantity() > product.getStockQuantity()) {
                System.out.printf("Not enough stock for %s. Available: %d, In cart: %d\n",
                        product.getName(), product.getStockQuantity(), item.getQuantity());
                stockAvailable = false;
            }
        }

        if (!stockAvailable) {
            System.out.println("Checkout failed due to stock availability issues.");
            return;
        }

        // Generate receipt
        Receipt receipt = new Receipt(user, cart.getItems(), cart.calculateTotalPrice());

        // Update inventory
        updateInventory(cart);

        // Add to total revenue
        totalRevenue += cart.calculateTotalPrice();

        // Display receipt
        receipt.printReceipt();

        // Clear cart
        cart.clearCart();

        System.out.println("Thank you for your purchase!");
    }

    private static void updateInventory(ShoppingCart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int newStock = product.getStockQuantity() - item.getQuantity();
            product.updateStockQuantity(newStock);
        }
    }

    private static void adminLoginMenu() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();

        String password = getPasswordInput(); // Use the new password input method

        Admin admin = authManager.authenticateAdmin(username, password);
        if (admin != null) {
            adminManagementMenu(admin);
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }


    private static void adminManagementMenu(Admin admin) {
        while (true) {
            System.out.println("\n==== Admin Management Menu ====");
            System.out.println("1⃣  View Inventory");
            System.out.println("2⃣ Add New Product");
            System.out.println("3⃣ Update Product Stock");
            System.out.println("4⃣ Remove Product");
            System.out.println("5⃣ View Total Revenue");
            System.out.println("6⃣ Restock Products");
            System.out.println("7⃣ Generate Revenue Report");
            System.out.println("8⃣ View Registered Users");
            System.out.println("9⃣ Logout");
            System.out.println("===============================");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: displayInventoryWithTotals(); break;
                case 2: addNewProduct(); break;
                case 3: updateProductStock(); break;
                case 4: removeProduct(); break;
                case 5: viewRevenue(); break;
                case 6: restockProducts(admin); break;
                case 7: admin.generateRevenueReport(); break;
                case 8: viewRegisteredUsers(); break;
                case 9: return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void displayInventoryWithTotals() {
        displayProducts(); // Display the list of products

        List<Product> products = inventory.getProductList();
        int totalProductCount = 0; // Initialize total item count
        double totalInventoryValue = 0.0;

        // Calculate total number of items and total inventory value
        for (Product product : products) {
            totalProductCount += product.getStockQuantity(); // Sum up stock quantities
            totalInventoryValue += product.getPrice() * product.getStockQuantity();
        }

        System.out.println("\nTotal number of items in inventory: " + totalProductCount);
        System.out.printf("Combined retail value of current inventory: €%.2f\n", totalInventoryValue);
    }

    private static void viewRegisteredUsers() {
        System.out.println("\n==== Registered Users ====");
        Map<String, User> users = authManager.getRegisteredUsers();

        if (users.isEmpty()) {
            System.out.println("No registered users found.");
            return;
        }

        for (User user : users.values()) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Logged in: " + (user.isLoggedIn() ? "Yes" : "No"));
            System.out.println("------------------------");
        }
    }

    private static void addNewProduct() {
        System.out.println("\n==== Add New Product ====");
        System.out.print("Enter Product ID: ");
        int id = getIntInput();

        // Check if product ID already exists
        if (inventory.findProductById(id) != null) {
            System.out.println("Product ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = 0;
        try {
            price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid price format. Using default value of 0.0");
            scanner.nextLine(); // Clear the scanner buffer
        }

        System.out.print("Enter Initial Stock Quantity: ");
        int stock = getIntInput();
        System.out.print("Enter Product Category: ");
        String category = scanner.nextLine();

        System.out.println("Select Discount Type:");
        System.out.println("1. Percentage Discount");
        System.out.println("2. Buy One Get One (BOGO) Discount");
        System.out.println("3. Bulk Purchase Discount");
        System.out.println("4. No Discount");
        System.out.print("Choose discount type: ");
        int discountType = getIntInput();

        DiscountStrategy discount;
        switch (discountType) {
            case 1:
                System.out.print("Enter discount percentage (0.0-1.0): ");
                double percentage = 0;
                try {
                    percentage = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Invalid percentage format. Using default value of 0.0");
                    scanner.nextLine(); // Clear the scanner buffer
                }
                discount = new PercentageDiscount(percentage);
                break;
            case 2:
                System.out.print("Enter BOGO threshold (e.g., 2 for buy one get one): ");
                int threshold = getIntInput();
                discount = new BOGODiscount(threshold);
                break;
            case 3:
                System.out.print("Enter bulk purchase threshold: ");
                int bulkThreshold = getIntInput();
                System.out.print("Enter bulk discount percentage (0.0-1.0): ");
                double bulkPercentage = 0;
                try {
                    bulkPercentage = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Invalid percentage format. Using default value of 0.0");
                    scanner.nextLine(); // Clear the scanner buffer
                }
                discount = new BulkDiscount(bulkThreshold, bulkPercentage);
                break;
            case 4:
            default:
                // No discount (identity discount)
                discount = new PercentageDiscount(0);
                break;
        }

        Product newProduct = new Product(id, name, price, stock, category, discount);
        inventory.addProduct(newProduct);
        System.out.println("Product added successfully!");
    }

    private static void updateProductStock() {
        System.out.println("\n==== Update Product Stock ====");
        displayProducts();
        System.out.print("Enter Product ID to update: ");
        int id = getIntInput();

        Product product = inventory.findProductById(id);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter new stock quantity: ");
        int newStock = getIntInput();

        try {
            product.updateStockQuantity(newStock);
            System.out.println("Stock updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeProduct() {
        System.out.println("\n==== Remove Product ====");
        displayProducts();
        System.out.print("Enter Product ID to remove: ");
        int id = getIntInput();

        Product product = inventory.findProductById(id);
        if (product != null) {
            inventory.removeProduct(product);
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void viewRevenue() {
        System.out.printf("\n==== Total Revenue ====\n€%.2f\n", totalRevenue);
    }

    private static void restockProducts(Admin admin) {
        System.out.println("\n==== Restock Products ====");
        System.out.printf("Available Revenue for Restocking: €%.2f\n", totalRevenue);

        if (totalRevenue <= 0) {
            System.out.println("No revenue available for restocking!");
            return;
        }

        displayProducts();
        System.out.print("Enter Product ID to restock: ");
        int id = getIntInput();

        Product product = inventory.findProductById(id);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter quantity to restock: ");
        int quantity = getIntInput();

        double cost = quantity * (product.getPrice() * 0.7); // Assume wholesale price is 70% of retail

        if (cost > totalRevenue) {
            System.out.println("Not enough revenue for this restock!");
            return;
        }

        admin.manageInventory(inventory, product, product.getStockQuantity() + quantity);
        totalRevenue -= cost;

        System.out.printf("Restocked %d units of %s. Remaining revenue: €%.2f\n",
                quantity, product.getName(), totalRevenue);
    }

    // Getter for totalRevenue (needed for revenue report)
    public static double getTotalRevenue() {
        return totalRevenue;
    }
}