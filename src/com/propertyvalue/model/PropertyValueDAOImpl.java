package com.propertyvalue.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.product.model.ProductDAOImpl;
import com.product.model.ProductVO;
import com.property.model.PropertyDAOImpl;
import com.property.model.PropertyVO;
import com.uti.tool.JDBCUtilites;

public class PropertyValueDAOImpl implements PropertyValueDAO {

	private static final String GET_TOTAL = "select count(*) from PropertyValue";
	private static final String ADD_STMT = "insert into PropertyValue values(PROPERTYVALID_SEQ.nextval,?,?,?)";
	private static final String UPATE_STMT = "update PropertyValue set pid= ?, ptid=?, value=?  where propertyvalID = ?";
	private static final String DEL_STMT = "delete from PropertyValue where propertyvalID = ?";
	private static final String GET_ONE = "select * from PropertyValue where propertyvalID = ?";
	private static final String GET_BY_PID$PTID = "select * from PropertyValue where ptid = ? and pid = ?";
	private static final String GET_ALL = "select * from PropertyValue order by propertyvalID desc ";
	private static final String GET_ALL2 ="select * from PropertyValue where pid = ? order by ptid desc";
	
	
	public int getTotal() {
        int total = 0;
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        		PreparedStatement ps = c.prepareStatement(GET_TOTAL);
        	) 
        	{
  
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return total;
    }
  
    public void add(PropertyValueVO bean) {
 
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(ADD_STMT);
        	) 
        	{
  
            ps.setInt(1, bean.getProduct().getProductId());
            ps.setInt(2, bean.getProperty().getPropertyId());
            ps.setString(3, bean.getValue());
            ps.execute();
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
    }
  
    public void update(PropertyValueVO bean) {
 
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        	{
            ps.setInt(1, bean.getProduct().getProductId());
            ps.setInt(2, bean.getProperty().getPropertyId());
            ps.setString(3, bean.getValue());
            ps.setInt(4, bean.getPropertyValId());
            ps.execute();
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
  
    }
  
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
  
    public PropertyValueVO get(int id) {
        PropertyValueVO bean = new PropertyValueVO();
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_ONE);
        	) 
        	{
        	ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                 
                ProductVO product = new ProductDAOImpl().get(pid);
                PropertyVO property = new PropertyDAOImpl().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setPropertyValId(id);
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return bean;
    }
    public PropertyValueVO get(int ptid, int pid ) {
        PropertyValueVO bean = null;
         
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(GET_BY_PID$PTID);
        	) 
        	{
        	ps.setInt(1, ptid);
        	ps.setInt(2, pid);
            ResultSet rs = ps.executeQuery();
             
            if (rs.next()) {
                bean= new PropertyValueVO();
                int id = rs.getInt("propertyValId");
                String value = rs.getString("value");
                 
                ProductVO product = new ProductDAOImpl().get(pid);
                PropertyVO property = new PropertyDAOImpl().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setPropertyValId(id);
            }
             
        } catch (Exception e) {
             
            e.printStackTrace();
        }
        return bean;
    }
  

    public List<PropertyValueVO> list() {
        List<PropertyValueVO> beans = new ArrayList<PropertyValueVO>();
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(GET_ALL);
        	) 
        	{
            
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                PropertyValueVO bean = new PropertyValueVO();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                 
                ProductVO product = new ProductDAOImpl().get(pid);
                PropertyVO property = new PropertyDAOImpl().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setPropertyValId(id);          
                beans.add(bean);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return beans;
    }
 
    public void init(ProductVO p) {
        List<PropertyVO> pts= new PropertyDAOImpl().list(p.getCategory().getCategoryId());
         
        for (PropertyVO pt: pts) {
            PropertyValueVO pv = get(pt.getPropertyId(),p.getProductId());
            if(null==pv){
                pv = new PropertyValueVO();
                pv.setProduct(p);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }
 
    public List<PropertyValueVO> list(int pid) {
        List<PropertyValueVO> beans = new ArrayList<PropertyValueVO>();
         
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(GET_ALL2);
        	) 
        	{
  
            ps.setInt(1, pid);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                PropertyValueVO bean = new PropertyValueVO();
                int id = rs.getInt(1);
 
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                 
                ProductVO product = new ProductDAOImpl().get(pid);
                PropertyVO property = new PropertyDAOImpl().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setPropertyValId(id);          
                beans.add(bean);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return beans;
    }
  
}
