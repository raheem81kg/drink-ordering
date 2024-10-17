package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Drink;

public class DrinkRepository {
    private Connection connection;

    public DrinkRepository(Connection connection) {
        this.connection = connection;
    }

    public void addDrink(Drink drink) throws SQLException {
        String query = "INSERT INTO Drink (DrinkName, IsAlcoholic, Cost) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, drink.getDrinkName());
            stmt.setBoolean(2, drink.isAlcoholic());
            stmt.setDouble(3, drink.getCost());
            stmt.executeUpdate();
        }
    }

    public void updateDrink(Drink drink) throws SQLException {
        String query = "UPDATE Drink SET DrinkName = ?, IsAlcoholic = ?, Cost = ? WHERE DrinkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setString(1, drink.getDrinkName());
            stmt.setBoolean(2, drink.isAlcoholic());
            stmt.setDouble(3, drink.getCost());
            stmt.setInt(4, drink.getDrinkID());
            stmt.executeUpdate();
        }
    }

    public void removeDrink(int drinkID) throws SQLException {
        String query = "DELETE FROM Drink WHERE DrinkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, drinkID);
            stmt.executeUpdate();
        }
    }

    public Drink getDrinkById(int drinkID) throws SQLException {
        String query = "SELECT DrinkID, DrinkName, IsAlcoholic, Cost FROM Drink WHERE DrinkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, drinkID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String drinkName = rs.getString("DrinkName");
                    boolean isAlcoholic = rs.getBoolean("IsAlcoholic");
                    double cost = rs.getDouble("Cost");
                    return new Drink(drinkID, drinkName, isAlcoholic, cost);
                }
            }
        }
        return null; // Return null if no drink found
    }
    
    public int getDrinkBydrinkName(String searchDrinkName){
        String query = "SELECT DrinkID FROM Drink WHERE DrinkName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, searchDrinkName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	int drinkID = rs.getInt("DrinkID");
                    return drinkID;
                }
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return -1; 
    }

    public List<Drink> getAllDrinks() throws SQLException {
        List<Drink> drinks = new ArrayList<>();
        String query = "SELECT DrinkID, DrinkName, IsAlcoholic, Cost FROM Drink";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int drinkID = rs.getInt("DrinkID");
                String drinkName = rs.getString("DrinkName");
                boolean isAlcoholic = rs.getBoolean("IsAlcoholic");
                double cost = rs.getDouble("Cost");
                drinks.add(new Drink(drinkID, drinkName, isAlcoholic, cost));
            }
        }
        return drinks;
    }

}
