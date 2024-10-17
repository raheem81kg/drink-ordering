package controller;

import java.sql.SQLException;

import repository.UserRepository;

public class AuthenticationController {
    private final UserRepository userRepository;

    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Handles user login
    public boolean login(String username, String password) {
        try {
			return userRepository.login(username, password); // If a user is found, login is successful
		} catch (SQLException e) {
			e.printStackTrace();
		}  
        return false;
    }

    // Retrieves the role for a specific user
    public String getUserRole(String username) {
        try {
			return userRepository.getUserRole(username);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return null;
    }
}
