package electronicshop;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private final Map<String, User> registeredUsers = new HashMap<>();
    private final Map<String, Admin> registeredAdmins = new HashMap<>();

    public boolean registerUser(User user) {
        // Check if the username is "admin"
        if (user.getUsername().equalsIgnoreCase("admin")) {
            return false;
        }

        if (registeredUsers.containsKey(user.getUsername())) {
            return false;
        }
        registeredUsers.put(user.getUsername(), user);
        return true;
    }

    public void registerAdmin(Admin admin) {
        if (registeredAdmins.containsKey(admin.getUsername())) {
            return;
        }
        registeredAdmins.put(admin.getUsername(), admin);
    }

    public User authenticateUser(String username, String password) {
        User user = registeredUsers.get(username);
        return (user != null && user.login(password)) ? user : null;
    }

    public Admin authenticateAdmin(String username, String password) {
        Admin admin = registeredAdmins.get(username);
        return (admin != null && admin.login(password)) ? admin : null;
    }

    // Method to get all registered users for admin view
    public Map<String, User> getRegisteredUsers() {
        return new HashMap<>(registeredUsers);
    }
}