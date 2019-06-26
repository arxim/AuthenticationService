package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnector {
	private Connection connect = null;

	public DBConnector() throws SQLException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect = DriverManager.getConnection(
					"" + "jdbc:sqlserver://DESKTOP-736IGTS;databaseName=LOGINBASE;user=sa;password=0819636514");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Connection getConnect() {
		return this.connect;
	}

	public void disConnect(Connection connect,PreparedStatement ps) {
		try {
			if (connect != null) {
				connect.close();
			}
			if (ps != null) {
				ps.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
