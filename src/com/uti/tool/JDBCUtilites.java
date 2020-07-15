package com.uti.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCUtilites {
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// get connection for JNDI
	public static Connection getConnectionJNDI() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
			return con;
	}
	// get connection for JDBC
	public static Connection getConnection() throws Exception {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		Properties pro = new Properties();
		pro.load(is);
		String user = pro.getProperty("user");
		String password = pro.getProperty("password");
		String url = pro.getProperty("url");
		String driverClass = pro.getProperty("driverClass");	
		Class.forName(driverClass);	
		Connection con = DriverManager.getConnection(url, user, password);
		return con;
	}

	//close connection
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	//close connection
	public static void close(Connection con, PreparedStatement pstmt) {
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}
	//inputstram to byte[]
	public static byte[] toByteArray(InputStream is) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = is.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
	//util date to sql date 
	public static java.sql.Timestamp u2s(java.util.Date date){
		if(date==null) {
			return null;
		}else {
			java.sql.Timestamp sdate = new java.sql.Timestamp(date.getTime());
			return sdate;
		}
	} 
	//sql date to util date 
	public static java.util.Date s2u(java.sql.Date date){
		if(date==null) {
			return null;
		}else {
			java.util.Date udate = new java.util.Date(date.getTime());
			return udate;
		}
	}
	
	
}
