package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean login(String username, String password) throws SQLException {
        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If a record exists, login is successful
        }
    }

    public String getUserRole(String username) throws SQLException {
        String query = "SELECT RoleName FROM Role INNER JOIN User ON Role.RoleID = User.RoleID WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("RoleName");
            }
        }
        return null;
    }

    // Additional methods for User interactions
}