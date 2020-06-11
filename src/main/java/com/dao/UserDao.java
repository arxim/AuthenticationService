package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.util.DBConnection;
import com.util.DBConnector;

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

	public boolean createUser(String email, String password, String firstName, String lastName, String companyCode, String roleCode, String active) {
		boolean status = true;
		DBConnection dbConn = new DBConnection("mysql");
		try {
			if (dbConn.getConnection() != null) {
				String sql = " INSERT INTO tb_mst_users (email, password, first_name, last_name, company_code, role_code, active)" 
						   + " VALUES (?, ?, ?, ?, ?, ?, ?)";
				dbConn.setPrepareStatement(sql);
				dbConn.getPrepareStatement().setString(1, email);
				dbConn.getPrepareStatement().setString(2, password);
				dbConn.getPrepareStatement().setString(3, firstName);
				dbConn.getPrepareStatement().setString(4, lastName);
				dbConn.getPrepareStatement().setString(5, companyCode);
				dbConn.getPrepareStatement().setString(6, roleCode);
				dbConn.getPrepareStatement().setString(7, active);
				dbConn.getPrepareStatement().executeUpdate();
				dbConn.doCommit();
			} else {
				System.out.println("Database Connect Failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		} finally {
			dbConn.doDisconnect();
		}
		return status;
	}

	public boolean resetPassword(String email, String password, String companyCode, String roleCode) {
		boolean status = true;
		DBConnection dbConn = new DBConnection("mysql");
		String sql = "";
		try {
			if (dbConn.getConnection() != null) {
				sql = "UPDATE tb_mst_users SET password = ? "
					+ "WHERE email = ? "
					+ "AND company_code = ? "
					+ "AND role_code = ?";
				dbConn.setPrepareStatement(sql);
				dbConn.getPrepareStatement().setString(1, password);
				dbConn.getPrepareStatement().setString(2, email);
				dbConn.getPrepareStatement().setString(3, companyCode);
				dbConn.getPrepareStatement().setString(4, roleCode);
				dbConn.getPrepareStatement().executeUpdate();
				dbConn.doCommit();
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		} finally {
			try {
				dbConn.getPrepareStatement().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbConn.doDisconnect();
		}
		return status;	
	}

	public boolean activateUser(String email, String companyCode, String roleCode) {
		boolean b = true;
		DBConnector dbConnector = null;
		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			
			if (connect != null) {
				String sql = "UPDATE tb_mst_user SET active = 'Y' "
							+ "WHERE email = '"+ email +"'"
							+ " AND company_code ='"+ companyCode +"'"
							+ " AND role_code ='"+ roleCode +"'"
							+ " AND active = 'N' ";
				ps = connect.prepareStatement(sql);
				ps.executeUpdate();
				
				b = true; 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		} finally {
			dbConnector.disConnect(connect, ps);
		}
		return b;	
	}
}