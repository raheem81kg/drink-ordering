package model;

import java.util.UUID;

public class Drink {
    private int drinkID;
    private String drinkName;
    private boolean isAlcoholic; // true = alcoholic, false = non-alcoholic
    private double cost;

    // Constructor
    public Drink(int drinkID, String drinkName, boolean isAlcoholic, double cost) {
        this.drinkID = drinkID;
        this.drinkName = drinkName;
        this.isAlcoholic = isAlcoholic;
        this.cost = cost;
    }
    
 // Constructor
    public Drink(String drinkName, boolean isAlcoholic, double cost) {
        this.drinkID = UUID.randomUUID().variant();
        this.drinkName = drinkName;
        this.isAlcoholic = isAlcoholic;
        this.cost = cost;
    }


    // Getters and setters
    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
