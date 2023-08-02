package OnlineShopSystem.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn;
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
                System.out.println("Connected to database successfully.");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error connecting to database: " + e.getMessage());
            }
        }
        return conn;
    }
}