package OnlineShopSystem.Entities.Admin;

import OnlineShopSystem.Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class AdminMethods {
    static Connection conn = DatabaseConnection.getConnection();
    static Scanner scanner = new Scanner(System.in);
    static PreparedStatement ps = null;
    public static void AddProduct(Admin admin) {
        if (!admin.isAdmin()) {
            System.out.println("You don't have permission to add a product.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Select product category: \n1. Headphones\n2. Laptops\n3. Phones\n4. TVs");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the name of the headphone:");
                    String headphoneName = scanner.nextLine();

                    System.out.println("Enter the price of the headphone:");
                    String headphonePrice = scanner.nextLine();

                    // Prepare statement to insert the new headphone into the database
                    ps = conn.prepareStatement("INSERT INTO headphones(name, price) VALUES (?, ?)");
                    ps.setString(1, headphoneName);
                    ps.setString(2, headphonePrice);

                    // Execute the statement to insert the new headphone
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Headphone added successfully.");
                    } else {
                        System.out.println("Failed to add headphone.");
                    }
                }
                case 2 -> {
                    System.out.println("Enter the name of the laptop:");
                    String laptopName = scanner.nextLine();

                    System.out.println("Enter the price of the laptop:");
                    String laptopPrice = scanner.nextLine();

                    // Prepare statement to insert the new laptop into the database
                    ps = conn.prepareStatement("INSERT INTO laptops(name, price) VALUES (?, ?)");
                    ps.setString(1, laptopName);
                    ps.setString(2, laptopPrice);

                    // Execute the statement to insert the new laptop
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Laptop added successfully.");
                    } else {
                        System.out.println("Failed to add laptop.");
                    }
                }
                case 3 -> {
                    System.out.println("Enter the name of the phone:");
                    String phoneName = scanner.nextLine();

                    System.out.println("Enter the price of the phone:");
                    String phonePrice = scanner.nextLine();

                    // Prepare statement to insert the new phone into the database
                    ps = conn.prepareStatement("INSERT INTO phones(name, price) VALUES (?, ?)");
                    ps.setString(1, phoneName);
                    ps.setString(2, phonePrice);

                    // Execute the statement to insert the new phone
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Phone added successfully.");
                    } else {
                        System.out.println("Failed to add phone.");
                    }
                }
                case 4 -> {
                    System.out.println("Enter the name of the TV:");
                    String tvName = scanner.nextLine();

                    System.out.println("Enter the price of the TV:");
                    String tvPrice = scanner.nextLine();

                    // Prepare statement to insert the new TV into the database
                    ps = conn.prepareStatement("INSERT INTO tvs(name, price) VALUES (?, ?)");
                    ps.setString(1, tvName);
                    ps.setString(2, tvPrice);

                    // Execute the statement to insert the new TV
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("TV added successfully.");
                    } else {
                        System.out.println("Failed to add TV.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ShowAllUsers(Admin admin) throws SQLException {
        if (!admin.isAdmin()) {
            System.out.println("You don't have permission to view all users.");
            return;
        }

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients");
        ResultSet rs = ps.executeQuery();

        if (!rs.isBeforeFirst()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("All users:");
        System.out.printf("%-5s %-15s %-15s %s%n", "ID", "Username", "Password", "Balance");

        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            double balance = rs.getDouble("balance");

            System.out.printf("%-5d %-15s %-15s $%.2f%n", id, username, password, balance);
        }
    }


    public static void RemoveUser(Admin admin) throws SQLException {
        if (!admin.isAdmin()) {
            System.out.println("You don't have permission to remove a product.");
            return;
        }
        ShowAllUsers(admin);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the user to be removed:");
        int userId = scanner.nextInt();
        ps = conn.prepareStatement("DELETE FROM clients WHERE id = ?");
        ps.setInt(1, userId);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("User with ID " + userId + " removed successfully.");
        } else {
            System.out.println("Failed to remove user with ID " + userId + ".");
        }
    }

    public static void showStatus(Admin admin) throws SQLException {
        Admin dbClient = Admin.getUserById(admin.getId());
        if (dbClient != null) {
            System.out.println("ID: " + dbClient.getId()  + "\nUsername: " + dbClient.getUsername()  + "\nPassword: " + dbClient.getPassword());
        } else {
            System.out.println("Admin not found in the database.");
        }    }

    public static void SearchByCategory() throws SQLException {
        System.out.println("Write the number of category:");
        System.out.println("1. Headphones");
        System.out.println("2. Laptops");
        System.out.println("3. Phones");
        System.out.println("4. TVs");

        int category = scanner.nextInt();
        String categoryStr;

        switch (category) {
            case 1 -> categoryStr = "headphones";
            case 2 -> categoryStr = "laptops";
            case 3 -> categoryStr = "phones";
            case 4 -> categoryStr = "tvs";
            default -> {
                System.out.println("There is no such category!");
                return;
            }
        }
        String query = "SELECT * FROM " + categoryStr;
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (!rs.isBeforeFirst()) {
            System.out.println("No products found in the " + categoryStr + " category.");
            return;
        }

        System.out.println("Products in the " + categoryStr + " category:");
        System.out.println("ID\tName\tPrice");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            System.out.println(id + "\t" + name + "\t$" + price);
        }
    }
}