package driver;

import java.sql.*;

public class DatabaseSeeder {
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Initialize the connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/DrinkOrderingDB", "root", "");
            
            CreateTables();
            // Seed roles
            seedRole("Bartender");
            seedRole("Manager");

            // Seed users (bartenders/managers)
            seedUser("bartender1", "password123", 1); // Bartender
            seedUser("manager1", "password123", 2); // Manager

            // Seed guests
            seedGuest("John Doe", "Orange");
            seedGuest("Jane Doe", "Other");

            // Seed drinks
            seedDrink("Coca Cola", false, 1.50);
            seedDrink("Whiskey", true, 5.00);

            // Seed orders and order drinks
            seedOrder("John Doe", 1, 1, 1, "open");
            seedOrderDrink(1, 1, 2); // Adding "Coca Cola" to John's order with quantity 2

            System.out.println("Database seeding completed!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedRole(String roleName) throws SQLException {
        String query = "SELECT COUNT(*) FROM Role WHERE RoleName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insert = "INSERT INTO Role (RoleName) VALUES (?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setString(1, roleName);
                    insertStmt.executeUpdate();
                    System.out.println("Inserted role: " + roleName);
                }
            }
        }
    }

    private static void seedUser(String username, String password, int roleID) throws SQLException {
        String query = "SELECT COUNT(*) FROM User WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insert = "INSERT INTO User (Username, Password, RoleID) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, password);
                    insertStmt.setInt(3, roleID);
                    insertStmt.executeUpdate();
                    System.out.println("Inserted user: " + username);
                }
            }
        }
    }

    private static void seedGuest(String guestName, String armbandColor) throws SQLException {
        String query = "SELECT COUNT(*) FROM Guest WHERE Name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, guestName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insert = "INSERT INTO Guest (Name, ArmbandColor) VALUES (?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setString(1, guestName);
                    insertStmt.setString(2, armbandColor);
                    insertStmt.executeUpdate();
                    System.out.println("Inserted guest: " + guestName);
                }
            }
        }
    }

    private static void seedDrink(String drinkName, boolean isAlcoholic, double cost) throws SQLException {
        String query = "SELECT COUNT(*) FROM Drink WHERE DrinkName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, drinkName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insert = "INSERT INTO Drink (DrinkName, IsAlcoholic, Cost) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setString(1, drinkName);
                    insertStmt.setBoolean(2, isAlcoholic);
                    insertStmt.setDouble(3, cost);
                    insertStmt.executeUpdate();
                    System.out.println("Inserted drink: " + drinkName);
                }
            }
        }
    }

    private static void seedOrder(String guestName, int tableNumber, int kioskID, int bartenderID, String orderStatus) throws SQLException {
        String query = "SELECT COUNT(*) FROM `Order` WHERE GuestName = ? AND TableNumber = ? AND KioskID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, guestName);
            stmt.setInt(2, tableNumber);
            stmt.setInt(3, kioskID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insert = "INSERT INTO `Order` (GuestName, TableNumber, KioskID, BartenderID, OrderStatus) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setString(1, guestName);
                    insertStmt.setInt(2, tableNumber);
                    insertStmt.setInt(3, kioskID);
                    insertStmt.setInt(4, bartenderID);
                    insertStmt.setString(5, orderStatus);
                    insertStmt.executeUpdate();
                    System.out.println("Inserted order for guest: " + guestName);
                }
            }
        }
    }

    private static void seedOrderDrink(int orderID, int drinkID, int quantity) throws SQLException {
        String query = "SELECT COUNT(*) FROM OrderDrink WHERE OrderID = ? AND DrinkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderID);
            stmt.setInt(2, drinkID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insert = "INSERT INTO OrderDrink (OrderID, DrinkID, Quantity) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setInt(1, orderID);
                    insertStmt.setInt(2, drinkID);
                    insertStmt.setInt(3, quantity);
                    insertStmt.executeUpdate();
                    System.out.println("Inserted order drink for OrderID: " + orderID + " and DrinkID: " + drinkID);
                }
            }
        }
    }
    
    private static void CreateTables() {

        // Define the SQL statements
        String createRoleTable = """
            CREATE TABLE IF NOT EXISTS Role (
                RoleID INT AUTO_INCREMENT PRIMARY KEY,
                RoleName VARCHAR(50) NOT NULL
            );
        """;

        String createUserTable = """
            CREATE TABLE IF NOT EXISTS User (
                UserID INT AUTO_INCREMENT PRIMARY KEY,
                Username VARCHAR(50) NOT NULL UNIQUE,
                Password VARCHAR(255) NOT NULL,
                RoleID INT,
                FOREIGN KEY (RoleID) REFERENCES Role(RoleID)
            );
        """;

        String createGuestTable = """
            CREATE TABLE IF NOT EXISTS Guest (
                GuestID INT AUTO_INCREMENT PRIMARY KEY,
                Name VARCHAR(100) NOT NULL,
                ArmbandColor ENUM('Orange', 'Other') NOT NULL
            );
        """;

        String createDrinkTable = """
            CREATE TABLE IF NOT EXISTS Drink (
                DrinkID INT AUTO_INCREMENT PRIMARY KEY,
                DrinkName VARCHAR(100) NOT NULL,
                IsAlcoholic BOOLEAN NOT NULL,
                Cost DECIMAL(10, 2) NOT NULL
            );
        """;

        String createOrderTable = """
            CREATE TABLE IF NOT EXISTS `Order` (
                OrderID INT AUTO_INCREMENT PRIMARY KEY,
                GuestName VARCHAR(100) NOT NULL,
                TableNumber INT NOT NULL,
                KioskID INT NOT NULL,
                OrderStatus ENUM('open', 'processing', 'completed') DEFAULT 'open',
                BartenderID INT,
                OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (BartenderID) REFERENCES User(UserID)
            );
        """;

        String createOrderDrinkTable = """
            CREATE TABLE IF NOT EXISTS OrderDrink (
                OrderDrinkID INT AUTO_INCREMENT PRIMARY KEY,
                OrderID INT,
                DrinkID INT,
                Quantity INT NOT NULL CHECK (Quantity > 0),
                FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID),
                FOREIGN KEY (DrinkID) REFERENCES Drink(DrinkID)
            );
        """;

        try (Statement statement = connection.createStatement()){

            // Execute each statement one by one
            statement.execute(createRoleTable);
            statement.execute(createUserTable);
            statement.execute(createGuestTable);
            statement.execute(createDrinkTable);
            statement.execute(createOrderTable);
            statement.execute(createOrderDrinkTable);

            System.out.println("Tables created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
