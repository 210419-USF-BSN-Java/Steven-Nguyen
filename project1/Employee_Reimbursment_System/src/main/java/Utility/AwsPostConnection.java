package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AwsPostConnection {

	private static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		String url = System.getenv("AwsPost_url");
		String username = System.getenv("AwsPost_username");
		String password = System.getenv("AwsPost_password");
		if(connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url,username,password);
		}
		return connection;
		
	}
}
