package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {
	
	 public static Connection getMySQLConnection() {
	     String hostName = "localhost";
	     String dbName = "phonebook";
	     String userName = "root";
	     String password = "admin";	 
	     Connection conn= null;
	     try
			{
	     Class.forName("com.mysql.jdbc.Driver");
	     String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?user=" + userName + "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
	     conn = DriverManager.getConnection(connectionURL, userName, password);
	     return conn;
			}
	     
	    catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	     System.out.println("null on getDBData()!");
	     return null;
	 }
	 
}
