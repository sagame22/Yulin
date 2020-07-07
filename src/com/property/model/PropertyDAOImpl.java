package com.property.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryVO;
import com.uti.tool.JDBCUtilites;
public class PropertyDAOImpl implements PropertyDAO {

	private static final String GET_TOTAL = "select count(*) from Property where cid = ?";
	private static final String ADD_STMT = "insert into Property values(PROPERTYID_SEQ.nextval,?,?)";
	private static final String UPATE_STMT = "update Property set cid= ?, name=? where propertyid = ?";
	private static final String DEL_STMT = "delete from Property where propertyid = ?";
	private static final String GET_ONE = "select * from Property where propertyid = ?";
	private static final String GET_ALL = "select * from Property where cid = ? order by rownum desc";
	@Override
	public int getTotal(int cid) {
        int total = 0;
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_TOTAL);
        	) 
        	{
        	ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return total;
    }

	@Override
	public int add(PropertyVO bean) {
		int []cols = {1};
        try (Connection c = JDBCUtilites.getConnectionJNDI(); 
        	PreparedStatement ps = c.prepareStatement(ADD_STMT,cols);
        	) 
        
            {
        	ps.setInt(1, bean.getCategory().getCategoryId());
            ps.setString(2, bean.getName());
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
	public void update(PropertyVO bean) {
		
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        	{
 
            ps.setInt(1, bean.getCategory().getCategoryId());
            ps.setString(2, bean.getName());
            ps.setInt(3, bean.getPropertyId());
            ps.execute();
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
  
    }

	@Override
	public void delete(int id) {
		try (Connection c = JDBCUtilites.getConnectionJNDI();
			PreparedStatement ps = c.prepareStatement(DEL_STMT);
			) 
			{		  
            ps.setInt(1, id);
  
            ps.execute();
        } catch (Exception e) {
  
            e.printStackTrace();
        }
    }

	@Override
	public PropertyVO get(int id) {
		 PropertyVO bean = new PropertyVO();
		  
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	 PreparedStatement ps = c.prepareStatement(GET_ONE);
	        	) 
	        	{
	        	ps.setInt(1, id);
	  
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	 
	                String name = rs.getString("name");
	                int cid = rs.getInt("cid");
	                bean.setName(name);
	                CategoryVO category = new CategoryDAOImpl().get(cid);
	                bean.setCategory(category);
	                bean.setPropertyId(id);
	            }
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return bean;
	    }
	

	@Override
	public List<PropertyVO> list(int cid) {
        List<PropertyVO> beans = new ArrayList<PropertyVO>();
    
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_ALL);
        	) 
        	{
            ps.setInt(1, cid);
        
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
            	PropertyVO bean = new PropertyVO();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                bean.setName(name);
                CategoryVO category = new CategoryDAOImpl().get(cid);
                bean.setCategory(category);
                bean.setPropertyId(id);
                 
                beans.add(bean);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return beans;
    }
}

