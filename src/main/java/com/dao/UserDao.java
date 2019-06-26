package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.model.User;
import com.util.DBConnector;

@Repository
public class UserDao {

	public User user_verify(String email, String password, String company, String role) {
		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		User login = null;
		
		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			
			if (connect != null) {
				String sql = "SELECT * FROM LOGIN WHERE EMAIL='"+ email +"'"
							+ " AND PASSWORD ='"+ password +"'"
							+ " AND COMPANYCODE = '"+ company +"'"
							+ " AND ROLECODE = '"+ role +"'"
							+ " AND ACTIVE = 'Y'";
				
				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					login = new User();
					login.setEmail(rs.getString("EMAIL"));
			        login.setPassword(rs.getString("PASSWORD"));
			        login.setFirstname(rs.getString("FIRSTNAME"));
			        login.setLastname(rs.getString("LASTNAME"));
			        login.setCompany_code(rs.getString("COMPANYCODE"));
			        login.setRole_code(rs.getString("ROLECODE"));
			        login.setActive(rs.getString("ACTIVE"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			dbConnector.disConnect(connect, ps);
		}
		return login;

	}
}