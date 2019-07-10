package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DBConnection {
    private Connection conn;
    private Statement stm;
    private PreparedStatement pstm;
    
    public DBConnection(){
    	doConnect("spring");
    }
    public DBConnection(String prefixDB){
    	doConnect(prefixDB);
    }
    private void doConnect(String prefixDB) {
        String conn_class;
        String conn_url;
        String conn_user;
        String conn_password;
    	
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties dbProps = new Properties();
        try {
            dbProps.load(is);
            conn_url = dbProps.getProperty(prefixDB+".datasource.url");
            conn_class = dbProps.getProperty(prefixDB+".datasource.driver-class-name");
            conn_user = dbProps.getProperty(prefixDB+".datasource.username");
            conn_password = dbProps.getProperty(prefixDB+".datasource.password");      
            Class.forName(conn_class);
            conn = DriverManager.getConnection(conn_url,conn_user,conn_password);
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void doDisconnect(){
    	try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public Connection getConnection(){
    	return this.conn;
    }
    public void doCommit(){
    	try {
			this.conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public void setPrepareStatement(String sql){
    	try {
			pstm = conn.prepareStatement(sql);
		} catch (SQLException e) {
			System.out.println(e);
		}
    }
    public PreparedStatement getPrepareStatement(){
    	return this.pstm;
    }
    public ArrayList<HashMap<String,String>> getData(String sql) {
		ArrayList<HashMap<String,String>> lsQueryData = new ArrayList<HashMap<String,String>>();
		ResultSet rs = null;
		try {
	        stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			ResultSetMetaData rsMetaData = rs.getMetaData();
			while (rs.next()) {
				HashMap<String, String> rtnData = new HashMap<String, String>();
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
					String value = "";
					if (rs.getString(i) != null && !rs.getString(i).equals("")) {
						value = rs.getString(i);
						rtnData.put(rsMetaData.getColumnName(i), value);
					} else {
						rtnData.put(rsMetaData.getColumnName(i), value);
					}
				}
				lsQueryData.add(rtnData);
			}
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		} finally {
			try {
				if (rs != null) { 
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				System.out.println("Error " + e.getMessage());
			}
		}
		return lsQueryData;
	}
    public boolean doSave(String sql){
        try {
            stm = conn.createStatement();    	    		
			stm.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
    }
}