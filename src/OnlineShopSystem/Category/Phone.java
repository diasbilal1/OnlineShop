package OnlineShopSystem.Category;

import OnlineShopSystem.Category.CategoryMethods.Product;
import OnlineShopSystem.Category.CategoryMethods.ProductMethod;
import OnlineShopSystem.Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Phone extends Product implements ProductMethod {
    public Phone() {}

    public Phone(int id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public void showProducts() {
        try {
            // Get database connection
            Connection conn = DatabaseConnection.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM phones");
            System.out.println("ID\tName\t\t\t\t\t\t\tPrice");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Phone phone = new Phone(id, name, price);
                System.out.printf("%d\t%-30s\t$%.2f\n", phone.getId(), phone.getName(), phone.getPrice());
            }
        } catch (SQLException e) {
            System.out.println("Error displaying phones: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
