package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * This class creates an Oracle Database connection
 */

public class DBConnection {
	public Connection con;	
	public Statement st;
	public String dataBase;
	public String OracleDatabase;
	private String user, pass;
	
	public DBConnection(String OracleDatabase, String user, String pass) {
		this.OracleDatabase = OracleDatabase;
		this.user = user;
		this.pass = pass;
	}

	public void connection () throws ClassNotFoundException, SQLException {
		System.out.println("Connecting to Oracle server...");
		try {
			System.out.println("JDBC-ODBC Class Search");
			Class.forName("oracle.jdbc.OracleDriver");
		} catch(ClassNotFoundException cnfe) { System.out.println("Class not found"); }
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + OracleDatabase, user, pass);
			System.out.println("Done connecting to <jdbc:oracle:thin:@localhost:1521:" + OracleDatabase + ":" + user + ">");
		} catch(SQLException se) { System.out.println("error"); se.printStackTrace(); }
	}
	
	public void disconnection () throws ClassNotFoundException, SQLException {
		System.out.println("Disconnecting from Oracle server...");
		try {
			con.close();
			System.out.println("Done disconnecting from <jdbc:oracle:thin:@localhost:1521:" + dataBase + ":" + user + ">");
		}
		catch(SQLException se) { System.out.println("error"); se.printStackTrace(); }
	}
	
	public void getDatabaseTables() {
		String SQLStatement = "show tables;";
		ResultSet rs;
		
		try {
			st = con.createStatement();
			st.executeUpdate(SQLStatement);
			rs = st.executeQuery(SQLStatement);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			rs.close();
			st.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
