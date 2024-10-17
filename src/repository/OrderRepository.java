package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderDrink;

public class OrderRepository {
    private Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }

    public int createOrder(Order order) throws SQLException {
        String query = "INSERT INTO `Order` (GuestName, TableNumber, KioskID, OrderStatus) VALUES (?, ?, ?, 'open')";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, order.getGuestName());
            stmt.setInt(2, order.getTableNumber());
            stmt.setInt(3, order.getKioskID());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return generated OrderID
            }
        }
        return -1;
    }

    public void addDrinksToOrder(int orderID, List<Integer> drinkIDs, List<Integer> quantities) throws SQLException {
        String query = "INSERT INTO OrderDrink (OrderID, DrinkID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < drinkIDs.size(); i++) {
                stmt.setInt(1, orderID);
                stmt.setInt(2, drinkIDs.get(i));
                stmt.setInt(3, quantities.get(i));
                stmt.executeUpdate();
            }
        }
    }

    public boolean selectOrderForProcessing(int orderID, int bartenderID) throws SQLException {
        connection.setAutoCommit(false); // Start a transaction

        String selectQuery = "SELECT * FROM `Order` WHERE OrderID = ? AND OrderStatus = 'open' FOR UPDATE";
        String updateQuery = "UPDATE `Order` SET OrderStatus = 'processing', BartenderID = ? WHERE OrderID = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            selectStmt.setInt(1, orderID);
            ResultSet rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                updateStmt.setInt(1, bartenderID);
                updateStmt.setInt(2, orderID);
                updateStmt.executeUpdate();
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false; // Another bartender may have already processed it
            }
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true); // End transaction
        }
    }

    public void updateOrderStatus(int orderID, String status) throws SQLException {
        String query = "UPDATE `Order` SET OrderStatus = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderID);
            stmt.executeUpdate();
        }
    }

    public List<Order> getOpenOrders() throws SQLException {
        String query = "SELECT * FROM `Order` WHERE OrderStatus = 'open'";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getString("GuestName"),
                    rs.getInt("TableNumber"),
                    rs.getInt("KioskID"),
                    rs.getString("OrderStatus"),
                    rs.getInt("BartenderID"),
                    rs.getTimestamp("OrderDate")
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String updateOrderQuery = "UPDATE `Order` SET GuestName = ?, TableNumber = ?, KioskID = ?, OrderStatus = ?, OrderDate = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateOrderQuery)) {
            stmt.setString(1, order.getGuestName());
            stmt.setInt(2, order.getTableNumber());
            stmt.setInt(3, order.getKioskID());
            stmt.setString(4, order.getOrderStatus());
            stmt.setTimestamp(5, order.getOrderDate());
            stmt.setInt(6, order.getOrderID());
            stmt.executeUpdate();
        }

        // Update associated drinks
        // You can implement a method to handle this logic, e.g. remove old drinks and add new ones.
        // For simplicity, assuming drinks are managed externally.
    }

    public Order getOrderById(int orderID) throws SQLException {
        String query = "SELECT * FROM `Order` WHERE OrderID = ?";
        Order order = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                order = new Order(
                    rs.getInt("OrderID"),
                    rs.getString("GuestName"),
                    rs.getInt("TableNumber"),
                    rs.getInt("KioskID"),
                    rs.getString("OrderStatus"),
                    rs.getInt("BartenderID"),
                    rs.getTimestamp("OrderDate")
                );

                // Get associated drinks
                List<OrderDrink> orderDrinks = getDrinksByOrderId(orderID);
                order.setOrderDrinks(orderDrinks);
            }
        }
        return order;
    }

    public List<Order> getOrdersByStatus(String status) throws SQLException {
        String query = "SELECT * FROM `Order` WHERE OrderStatus = ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getString("GuestName"),
                    rs.getInt("TableNumber"),
                    rs.getInt("KioskID"),
                    rs.getString("OrderStatus"),
                    rs.getInt("BartenderID"),
                    rs.getTimestamp("OrderDate")
                );

                // Get associated drinks
                List<OrderDrink> orderDrinks = getDrinksByOrderId(order.getOrderID());
                order.setOrderDrinks(orderDrinks);

                orders.add(order);
            }
        }
        return orders;
    }

    private List<OrderDrink> getDrinksByOrderId(int orderID) throws SQLException {
        String query = "SELECT * FROM OrderDrink WHERE OrderID = ?";
        List<OrderDrink> orderDrinks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderDrink orderDrink = new OrderDrink(
                    rs.getInt("OrderDrinkID"),
                    rs.getInt("OrderID"),
                    rs.getInt("DrinkID"),
                    rs.getInt("Quantity")
                );
                orderDrinks.add(orderDrink);
            }
        }
        return orderDrinks;
    }
    
    public List<Order> getOrdersForDate(String date) throws SQLException {
        String query = "SELECT * FROM `Order` WHERE DATE(OrderDate) = ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getString("GuestName"),
                    rs.getString("OrderStatus"),
                    rs.getTimestamp("OrderDate")
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public List<Order> getOrdersBetweenDates(String startDate, String endDate) throws SQLException {
        String query = "SELECT * FROM `Order` WHERE OrderDate BETWEEN ? AND ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getString("GuestName"),
                        rs.getString("OrderStatus"),
                        rs.getTimestamp("OrderDate")
                    );
                    orders.add(order);
            }
        }
        return orders;
    }
}