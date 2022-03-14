package com.revature.util;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {

	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	
private static Connection conn = null;
	
	public static Connection getConnection() {
		
		try { // first we check if an instance exists already
			
			if (conn != null && !conn.isClosed()) {
				// if an instance exists, we return the static connection declared on line 20
				logger.info("returned the re-used connection object");
				return conn;
			}
		} catch (SQLException e) {
			logger.warn("we failed to re-use the connection");
			e.printStackTrace();
			return null;
		}
	
	//return a connection object
	
		String dir = System.getProperty("user.dir");

		Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		try {
			prop.load(new FileReader(dir + "\\src\\main\\resources\\application.properties"));
			
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url, username, password);
			logger.info("Established connection to the Database");
			
		} catch (SQLException e) {
			logger.warn("Can't establish connection to DB with given credentials");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.warn("couldn't read from application.properties at specified path");
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn("something wrong with application.props file");
			e.printStackTrace();
		}
		return conn;
	}
}