package com.AMT.dao.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class postgresConnection {

	private static Connection connection;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		String url = System.getenv("db_url");
		String username = System.getenv("db_username");
		String password = System.getenv("db_password");
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);

		}
		return connection;
	}
}