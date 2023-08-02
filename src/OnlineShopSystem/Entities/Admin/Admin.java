package OnlineShopSystem.Entities.Admin;

import OnlineShopSystem.Database.DatabaseConnection;
import OnlineShopSystem.Entities.User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User{
    private final int id;
    private final String username;
    private final String password;
    private final boolean isAdmin;

    public Admin(int id, String username, String password) {
        super(id, username, password);
        this.id = id;
        this.isAdmin = true;
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin() {
        return this.isAdmin;
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

    // Get a client from the database by ID
    public static Admin getUserById(int id) throws SQLException {
        // Get database connection
        Connection conn = DatabaseConnection.getConnection();

        // Create a prepared statement
        String sql = "SELECT * FROM admin WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        // Execute the query
        ResultSet rs = stmt.executeQuery();

        // Get the admin information from the result set
        if (rs.next()) {
            int adminId = rs.getInt("id");
            String adminUsername = rs.getString("username");
            String adminPassword = rs.getString("password");
            return new Admin(adminId, adminUsername, adminPassword);
        } else {
            return null;
        }
    }
}