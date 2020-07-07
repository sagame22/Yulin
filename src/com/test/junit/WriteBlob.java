package com.test.junit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.uti.tool.JDBCUtilites;

public class WriteBlob {
	
	
	public static void main(String [] args) throws Exception {
		Connection con=null;
		PreparedStatement pstmt = null;
		int j=8;
		for(int i=0;i<9;i++) {
		
		con = JDBCUtilites.getConnection();
		pstmt=con.prepareStatement("UPDATE COUPON SET COUPONPIC=?WHERE COUPONNO=?");
		
			
		byte[] pic = getPictureByteArray("WebContent/images/CP"+i+".jpg");
		pstmt.setBytes(1, pic);
		pstmt.setString(2, "C010"+j);
		
		j--;
		
		pstmt.executeUpdate();
		
		}JDBCUtilites.close(con, pstmt);
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
	
	public static InputStream getPictureStream(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}
	
}
