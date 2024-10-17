package model;

public class Guest {
    private int guestID;
    private String name;
    private String armbandColor; // Orange = over 18, Other = under 18

    // Constructor
    public Guest(int guestID, String name, String armbandColor) {
        this.guestID = guestID;
        this.name = name;
        this.armbandColor = armbandColor;
    }

    // Getters and setters
    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArmbandColor() {
        return armbandColor;
    }

    public void setArmbandColor(String armbandColor) {
        this.armbandColor = armbandColor;
    }
}
