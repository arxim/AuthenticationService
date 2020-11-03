package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;
import com.util.DBConnection;

@Repository
public class UserDao {

	public boolean verifyUser(String email, String password, String company, String role) {
		DBConnection dbConn = new DBConnection("mysql");
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean status = true;
		String sql = "";
		
		try {
			sql = "SELECT email FROM tb_mst_users"
				+ " WHERE email = ?"
				+ " AND password =?"
				+ " AND company_code = ?"
				+ " AND role_code = ?"
				+ " AND active = 'Y'";
			
			dbConn.setPrepareStatement(sql);
			dbConn.getPrepareStatement().setString(1, email);
			dbConn.getPrepareStatement().setString(2, password);
			dbConn.getPrepareStatement().setString(3, company);
			dbConn.getPrepareStatement().setString(4, role);			
			rs = dbConn.getPrepareStatement().executeQuery();
			
			if(!rs.next()){
				status = false;
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			dbConn.doDisconnect();
			}
		}
		return status;
	}
}