package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.util.DBConnector;

public class LoginDao {

	public void loginConnect(String email, String password) {

		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			if (connect != null) {
				String sql = "select * from LOGIN where EMAIL='" + email + "' and PASSWORD='" + password + "'";
				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					String company = rs.getString("COMPANY");
					String role = rs.getString("ROLE");
				}

			} else {
				System.out.println("Database Connect Failed.");
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

	}

	public JSONObject getCompany() {

		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		JSONArray jsonArrData = new JSONArray();

		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			if (connect != null) {
				String sql = "select * from COMPANY";
				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					jsonArrData = new JSONArray();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {

						String value = "";
						value = rs.getString(i);

						if (value == null) {
							jsonArrData.put("");
						} else {
							jsonArrData.put(value);
						}

					}
					jsonArr.put(jsonArrData);
				}
				jsonObj.put("data", jsonArr);

			} else {
				System.out.println("Database Connect Failed.");
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

		return jsonObj;
	}

	public JSONObject getCompanyByEmail(String email) {

		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		JSONArray jsonArrData = new JSONArray();

		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			if (connect != null) {
				String sql = "select DISTINCT COMPANY.COMPANYCODE,COMPANYNAME from COMPANY "
						+ "LEFT JOIN LOGIN ON LOGIN.COMPANYCODE=COMPANY.COMPANYCODE where LOGIN.EMAIL='" + email + "'";

				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					jsonArrData = new JSONArray();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {

						String value = "";
						value = rs.getString(i);

						if (value == null) {
							jsonArrData.put("");
						} else {
							jsonArrData.put(value);
						}

					}
					jsonArr.put(jsonArrData);
				}
				jsonObj.put("data", jsonArr);

			} else {
				System.out.println("Database Connect Failed.");
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

		return jsonObj;
	}

	public List<Map<String, String>> getRole(String company) {
		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Map<String, String>> role = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();

		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			if (connect != null) {
				String sql = "select * from ROLE where COMPANYCODE='" + company + "'";
				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					map = new HashMap<String, String>();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {

						String value = "";
						value = rs.getString(i);

						if (value == null) {
							map.put(rsm.getColumnName(i), "");
						} else {
							map.put(rsm.getColumnName(i), value);
						}

					}
					role.add(map);
				}

			} else {
				System.out.println("Database Connect Failed.");
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

		return role;
	}

	public JSONObject getRoleByEmail(String email, String company) {

		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		JSONArray jsonArrData = new JSONArray();

		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			if (connect != null) {
				String sql = "SELECT ROLE.ROLECODE,ROLENAME FROM ROLE "
						+ " LEFT JOIN LOGIN ON LOGIN.ROLECODE=ROLE.ROLECODE where LOGIN.EMAIL='" + email + "'"
						+ " AND LOGIN.COMPANYCODE='" + company + "'";

				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					jsonArrData = new JSONArray();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {

						String value = "";
						value = rs.getString(i);

						if (value == null) {
							jsonArrData.put("");
						} else {
							jsonArrData.put(value);
						}

					}
					jsonArr.put(jsonArrData);
				}
				jsonObj.put("data", jsonArr);

			} else {
				System.out.println("Database Connect Failed.");
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

		return jsonObj;
	}

	public boolean checkAccount(String email, String password, String company, String role) {
		boolean b = false;
		DBConnector dbConnector = null;

		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			dbConnector = new DBConnector();
			connect = dbConnector.getConnect();
			
			if (connect != null) {
				String sql = "SELECT * FROM LOGIN WHERE EMAIL='"+ email +"'"
							+ " AND PASSWORD ='"+ password +"'"
							+ " AND COMPANYCODE = '"+ company +"'"
							+ " AND ROLECODE = '"+ role +"'";
				
				ps = connect.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					String em = rs.getString("EMAIL");
			        String pass = rs.getString("PASSWORD");
			        String com = rs.getString("COMPANYCODE");
			        String ro = rs.getString("ROLECODE");
			        b = true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
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

		return b;

	}
}
