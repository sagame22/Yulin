package com.productimage.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.product.model.ProductDAOImpl;
import com.product.model.ProductVO;
import com.uti.tool.JDBCUtilites;

public class ProductImageDAOImpl implements ProductImageDAO {

	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";

	private static final String GET_TOTAL = "select count(*) from ProductImage";
	private static final String ADD_STMT = "insert into ProductImage values(PRODUCTIMAGEID_SEQ.nextval,?,?)";
	private static final String DEL_STMT = "delete from ProductImage where imageid = ?";
	private static final String GET_ONE = "select * from ProductImage where imageid = ?";
	private static final String GET_ALL = "select * from ProductImage where pid =? and type =? order by rownum desc ";

	public int getTotal() {
		int total = 0;
		try (Connection c = JDBCUtilites.getConnectionJNDI(); PreparedStatement ps = c.prepareStatement(GET_TOTAL);) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return total;
	}

	public int add(ProductImageVO bean) {
		int[] cols = { 1 };
		try (Connection c = JDBCUtilites.getConnectionJNDI(); PreparedStatement ps = c.prepareStatement(ADD_STMT, cols);) {
			ps.setInt(1, bean.getProduct().getProductId());
			ps.setString(2, bean.getType());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int key = rs.getInt(1);
				return key;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}		return 0;
	}

	public void update(ProductImageVO bean) {

	}

	public void delete(int id) {

		try (Connection c = JDBCUtilites.getConnectionJNDI(); PreparedStatement ps = c.prepareStatement(DEL_STMT);) {
			ps.setInt(1, id);
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public ProductImageVO get(int id) {
		ProductImageVO bean = new ProductImageVO();

		try (Connection c = JDBCUtilites.getConnectionJNDI(); PreparedStatement ps = c.prepareStatement(GET_ONE);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int pid = rs.getInt("pid");
				String type = rs.getString("type");
				ProductVO product = new ProductDAOImpl().get(pid);
				bean.setProduct(product);
				bean.setType(type);
				bean.setImageId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return bean;
	}

	public List<ProductImageVO> list(ProductVO p, String type) {
		List<ProductImageVO> beans = new ArrayList<ProductImageVO>();

		try (Connection c = JDBCUtilites.getConnectionJNDI(); 
			PreparedStatement ps = c.prepareStatement(GET_ALL);) {
			ps.setInt(1, p.getProductId());
			ps.setString(2, type);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				ProductImageVO bean = new ProductImageVO();
				int id = rs.getInt(1);

				bean.setProduct(p);
				bean.setType(type);
				bean.setImageId(id);

				beans.add(bean);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return beans;
	}

}