package electronicshop;

public class User {
    private final String username;
    private final String password;
    private final String email;
    private boolean isLoggedIn;
    private final ShoppingCart shoppingCart;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
        this.shoppingCart = new ShoppingCart();
    }

    public boolean login(String enteredPassword) {
        if (this.password.equals(enteredPassword)) {
            this.isLoggedIn = true;
            System.out.println("User " + username + " logged in successfully.");
            return true;
        }
        return false;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}