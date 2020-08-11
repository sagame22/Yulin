package com.category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.uti.tool.JDBCUtilites;

public class CategoryDAOImpl implements CategoryDAO {
	
	private static final String GET_TOTAL = "select count(*) from category";
	private static final String ADD_STMT = "insert into category values(CATEGORYID_SEQ.nextval,?)";
	private static final String UPATE_STMT = "update category set name= ? where categoryid = ?";
	private static final String DEL_STMT = "delete from category where categoryid = ? ";
	private static final String GET_ONE = "select * from category where categoryid = ?";
	private static final String GET_ALL = "select * from category order by rownum desc ";
	@Override
	public int getTotal() {
        int total = 0;
        try (Connection c = JDBCUtilites.getConnectionJNDI(); 
        	 Statement s = c.createStatement();
        	) 
        
        	{
            ResultSet rs = s.executeQuery(GET_TOTAL);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return total;
    }

	@Override
	public int add(CategoryVO bean) {
		int []cols = {1};
		
        try (Connection c = JDBCUtilites.getConnectionJNDI(); 
        	PreparedStatement ps = c.prepareStatement(ADD_STMT,cols);
        	
        	
        	) 
            {
            ps.setString(1, bean.getName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
        		int key = rs.getInt(1);
        		return key;
        	}
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
			return 0;
    }

	@Override
	public void update(CategoryVO bean) {
		
        try (Connection c = JDBCUtilites.getConnectionJNDI(); 
        	 PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        { 
            ps.setString(1, bean.getName());
            ps.setInt(2, bean.getCategoryId());
            ps.execute();
        } catch (Exception e) {
  
            e.printStackTrace();
        }
  
    }

	@Override
	public void delete(int id) {
		try (Connection c = JDBCUtilites.getConnectionJNDI();
			PreparedStatement s = c.prepareStatement(DEL_STMT);
			) 
		
			{
			s.setInt(1, id);
            s.execute();
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
    }

	@Override
	public CategoryVO get(int id) {
		 CategoryVO bean = null;
		  
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        		PreparedStatement s = c.prepareStatement(GET_ONE);
	        	) 
	        	{
	        	s.setInt(1, id);	      
	            ResultSet rs = s.executeQuery();	  
	            if (rs.next()) {
	                bean = new CategoryVO();
	                String name = rs.getString(2);
	                bean.setName(name);
	                bean.setCategoryId(id);
	            }
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return bean;
	    }
	

	@Override
	public List<CategoryVO> list() {
		  List<CategoryVO> beans = new ArrayList<CategoryVO>();

	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL);
	        	) 
	        	{
	            ResultSet rs = ps.executeQuery();
	  
	            while (rs.next()) {
	                CategoryVO bean = new CategoryVO();
	                int id = rs.getInt(1);
	                String name = rs.getString(2);
	                bean.setCategoryId(id);
	                bean.setName(name);
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        
	        return beans;
	    }

}
