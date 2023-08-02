package OnlineShopSystem.Entities.User;

import OnlineShopSystem.Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistrationManager {
    public static User registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        String password = null;
        while (password == null) {
            System.out.println("Enter password (at least 8 characters with 2 letters): ");
            password = scanner.nextLine();

            // Check password length
            if (password.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                password = null;
                continue;
            }

            // Check for at least 2 letters
            int letterCount = 0;
            for (char c : password.toCharArray()) {
                if (Character.isLetter(c)) {
                    letterCount++;
                }
            }
            if (letterCount < 2) {
                System.out.println("Password must contain at least 2 letters.");
                password = null;
            }
        }

        // Get database connection
        Connection conn = DatabaseConnection.getConnection();

        // Check if the username already exists
        try {
            PreparedStatement checkUsernameStmt = conn.prepareStatement("SELECT * FROM clients WHERE username = ?");
            checkUsernameStmt.setString(1, username);
            ResultSet resultSet = checkUsernameStmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Username already exists, please choose another username.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error checking username in the database: " + e.getMessage());
            return null;
        }

        // Insert the new user into the database
        try {
            PreparedStatement insertUserStmt = conn.prepareStatement("INSERT INTO clients (username, password) VALUES (?, ?)");
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password);
            insertUserStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting user into the database: " + e.getMessage());
            return null;
        }

        int id = 0;

        // Get the id and balance of the new user
        try {
            PreparedStatement getUserStmt = conn.prepareStatement("SELECT * FROM clients WHERE username = ? AND password = ?");
            getUserStmt.setString(1, username);
            getUserStmt.setString(2, password);
            ResultSet resultSet = getUserStmt.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error getting user from the database: " + e.getMessage());
            return null;
        }

        return new User(id, username, password);
    }
}