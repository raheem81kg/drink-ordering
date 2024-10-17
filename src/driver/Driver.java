package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import controller.AuthenticationController;
import controller.DrinkController;
import controller.OrderController;
import model.Drink;
import repository.OrderRepository;
import repository.UserRepository;
import repository.DrinkRepository;

public class Driver {
	public static void main(String[] args) {
		
		UserRepository userRepository = new UserRepository(DBConnectorFactory.getDatabaseConnection());
		AuthenticationController authenticationController = new AuthenticationController(userRepository);
		
		System.out.println(authenticationController.login("bartender1", "password123"));
		
	}
}
