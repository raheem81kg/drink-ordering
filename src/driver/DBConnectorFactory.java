package driver;
import java.sql.*;

public class DBConnectorFactory {
	private static String url = "jdbc:mysql://localhost: 3307/DrinkOrderingDB";
	private static Connection myConn = null;
	
	public static Connection getDatabaseConnection() {
		if(myConn == null) {
			try {
				myConn = DriverManager.getConnection(url, "root", "");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return myConn;
	}
}
