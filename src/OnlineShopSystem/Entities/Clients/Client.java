package OnlineShopSystem.Entities.Clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import OnlineShopSystem.Entities.User.User;
import OnlineShopSystem.Database.DatabaseConnection;

public class Client extends User {
    private final int id;
    private final String username;
    private final String password;
    private double balance;

    public Client(int id, String username, String password, double balance) {
        super(id, username, password);
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Get a client from the database by ID
    public static Client getUserById(int id) throws SQLException {
        // Get database connection
        Connection conn = DatabaseConnection.getConnection();

        // Create a prepared statement
        String sql = "SELECT * FROM clients WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        // Execute the query
        ResultSet rs = stmt.executeQuery();

        // Get the client information from the result set
        if (rs.next()) {
            int clientId = rs.getInt("id");
            String clientUsername = rs.getString("username");
            String clientPassword = rs.getString("password");
            double clientBalance = rs.getDouble("balance");
            return new Client(clientId, clientUsername, clientPassword, clientBalance);
        } else {
            return null;
        }
    }
}