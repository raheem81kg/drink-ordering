package model;

public class OrderDrink {
    private int orderDrinkID;
    private int orderID; // foreign key to Order
    private int drinkID; // foreign key to Drink
    private int quantity;

    // Constructor
    public OrderDrink(int orderDrinkID, int orderID, int drinkID, int quantity) {
        this.orderDrinkID = orderDrinkID;
        this.orderID = orderID;
        this.drinkID = drinkID;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getOrderDrinkID() {
        return orderDrinkID;
    }

    public void setOrderDrinkID(int orderDrinkID) {
        this.orderDrinkID = orderDrinkID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
