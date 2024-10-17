package model;

public class User {
    private int userID;
    private String username;
    private String password;
    private int roleID; // foreign key for role

    // Constructor
    public User(int userID, String username, String password, int roleID) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.roleID = roleID;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
