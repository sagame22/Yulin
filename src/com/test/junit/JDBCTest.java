package com.test.junit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;




public class JDBCTest {
	
	
	
//	public static void main (String [] args) throws IOException {
		
//	CouponDAO cpml = new CouponDAOIpml();

	// �s�W
//	CouponVO cp = new CouponVO();
//	cp.setCouponName("������");
//	cp.setCouponDiscount(7);
//	
//	byte[] couponPic = getPictureByteArray("WebContent/images/CP1.jpg");
//	cp.setCouponPicture(couponPic);
//	
//	
//	cpml.insert(cp);
//	System.out.print(cp);

//	// �ק�
//	CouponVO cp1 = new CouponVO();
//	cp1.setCouponName("������");
//	cp1.setCouponDiscount(77);
//	cp1.setCouponno("C0114");
//	byte[] couponPic1 = getPictureByteArray("WebContent/images/CP2.jpg");
//	cp1.setCouponPicture(couponPic1);
//	
//	cpml.update(cp1);
	


	// �R��
//	cpml.delete("C0114");
	
//
//	// �d��
//	CouponVO CVO1 = cpml.findByPrimaryKey("C0100");
//	System.out.print(CVO1.getCouponName() + ",");
//	System.out.print(CVO1.getCouponno() + ",");
//	System.out.print(CVO1.getCouponDiscount() + ",");
//	System.out.print(CVO1.getCouponPic());
//	
//	System.out.println("---------------------");
//
	// �d��
//	List<CouponVO> list = cpml.getAll();
//	list.forEach(System.out::println);
//}

	
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
}
