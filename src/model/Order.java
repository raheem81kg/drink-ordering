package model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Order {
    private int orderID;
    private String guestName; // refers to Guest name
    private int tableNumber;
    private int kioskID;
    private String orderStatus; // open, processing, completed
    private int bartenderID; // refers to User ID of the bartender processing the order
    private List<OrderDrink> orderDrinks; // list of drinks in the order
    private Timestamp orderDate;
    
    // Constructor
    public Order(int orderID, String guestName, int tableNumber, int kioskID, String orderStatus, int bartenderID, List<OrderDrink> orderDrinks) {
        this.orderID = orderID;
        this.guestName = guestName;
        this.tableNumber = tableNumber;
        this.kioskID = kioskID;
        this.orderStatus = orderStatus;
        this.bartenderID = bartenderID;
        this.orderDrinks = orderDrinks;
    }
    
 // Constructor
    public Order(int orderID, String guestName, int tableNumber, int kioskID, String orderStatus, int bartenderID, Timestamp orderDate) {
        this.orderID = orderID;
        this.guestName = guestName;
        this.tableNumber = tableNumber;
        this.kioskID = kioskID;
        this.orderStatus = orderStatus;
        this.bartenderID = bartenderID;
        this.setOrderDate(orderDate);
    }
    
 // Constructor
    public Order(String guestName, int tableNumber, int kioskID, String orderStatus) {
        this.orderID = UUID.randomUUID().variant();
        this.guestName = guestName;
        this.tableNumber = tableNumber;
        this.kioskID = kioskID;
        this.orderStatus = orderStatus;
        this.setOrderDate(orderDate);
    }

    // Constructor
    public Order(int orderID, String guestName, String orderStatus, Timestamp orderDate) {
        this.orderID = orderID;
        this.guestName = guestName;
        this.orderStatus = orderStatus;
        this.setOrderDate(orderDate);
    }
	// Getters and setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getKioskID() {
        return kioskID;
    }

    public void setKioskID(int kioskID) {
        this.kioskID = kioskID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getBartenderID() {
        return bartenderID;
    }

    public void setBartenderID(int bartenderID) {
        this.bartenderID = bartenderID;
    }

    public List<OrderDrink> getOrderDrinks() {
        return orderDrinks;
    }

    public void setOrderDrinks(List<OrderDrink> orderDrinks) {
        this.orderDrinks = orderDrinks;
    }

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
}
