package com.orderitem.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.member.model.MemberDAOImpl;
import com.member.model.MemberVO;
import com.order.model.OrderDAOImpl;
import com.order.model.OrderVO;
import com.product.model.ProductDAOImpl;
import com.product.model.ProductVO;
import com.uti.tool.JDBCUtilites;

public class OrderItemDAOImpl implements OrderItemDAO {

	private static final String GET_TOTAL = "select count(*) from OrderItem";
	private static final String ADD_STMT = "insert into OrderItem values(ORDERITEMID_SEQ.nextval,?,?,?,?)";
	private static final String UPATE_STMT = "update OrderItem set pid= ?, oid=?, uid=?,number=?  where OrderItemid = ?";
	private static final String DEL_STMT = "delete from OrderItem where OrderItemid = ?";
	private static final String GET_ONE = "select * from OrderItem where OrderItemid = ?";
	private static final String GET_ONE2 = "select sum(number) from OrderItem where pid = ?";
	private static final String GET_ALL = "select * from OrderItem where uid = ? and oid=-1 order by OrderItemid desc limit ?,? ";
	private static final String GET_ALL2 = "select * from OrderItem where oid = ? order by OrderItemid desc limit ?,? ";
	private static final String GET_ALL3 = "select * from OrderItem where pid = ? order by OrderItemid desc limit ?,? ";
	
	 public int getTotal() {
	        int total = 0;
	        try (Connection c = JDBCUtilites.getConnection();
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
	  
	    public void add(OrderItemVO bean) {
	 
	        try (Connection c = JDBCUtilites.getConnection();
	        	PreparedStatement ps = c.prepareStatement(ADD_STMT);
	        	) 
	        	{
	            ps.setInt(1, bean.getProduct().getProductId());
	             
	            //訂單創建,無訊息
	            if(null==bean.getOrder())
	                ps.setInt(2, -1);
	            else
	                ps.setInt(2, bean.getOrder().getId()); 
	             
	            ps.setInt(4, bean.getNumber());
	            ps.execute();
	  
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                bean.setId(id);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	    }
	  
	    public void update(OrderItemVO bean) {
	 
	        try (Connection c = JDBCUtilites.getConnection();
	        	PreparedStatement ps = c.prepareStatement(UPATE_STMT);
	        	) 
	        	{
	 
	            ps.setInt(1, bean.getProduct().getProductId());
	            if(null==bean.getOrder())
	                ps.setInt(2, -1);
	            else
	                ps.setInt(2, bean.getOrder().getId());             
	            ps.setInt(3, bean.getUser().getMemberId());
	            ps.setInt(4, bean.getNumber());
	             
	            ps.setInt(5, bean.getId());
	            ps.execute();
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	  
	    }
	  
	    public void delete(int id) {
	  
	        try (Connection c = JDBCUtilites.getConnection();
	        		PreparedStatement ps = c.prepareStatement(DEL_STMT);
	        	 ) 
	        	{
	        	ps.setInt(1, id);
	  
	            ps.execute();
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	    }
	  
	    public OrderItemVO get(int id) {
	        OrderItemVO bean = new OrderItemVO();
	  
	        try (Connection c = JDBCUtilites.getConnection();
	        	PreparedStatement ps = c.prepareStatement(GET_ONE);

	        	) 
	        	{
	            ResultSet rs = ps.executeQuery();
	  
	            if (rs.next()) {
	                int pid = rs.getInt("pid");
	                int oid = rs.getInt("oid");
	                int uid = rs.getInt("uid");
	                int number = rs.getInt("number");
	                ProductVO product = new ProductDAOImpl().get(pid);
	                MemberVO user = new MemberDAOImpl().get(uid);
	                bean.setProduct(product);
	                bean.setUser(user);
	                bean.setNumber(number);
	                 
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	                 
	                bean.setId(id);
	            }
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return bean;
	    }
	  
	    public List<OrderItemVO> listByUser(int uid) {
	        return listByUser(uid, 0, Short.MAX_VALUE);
	    }
	  
	    public List<OrderItemVO> listByUser(int uid, int start, int count) {
	        List<OrderItemVO> beans = new ArrayList<OrderItemVO>();
	  
	  
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL);
	        	) 
	        	{
	            ps.setInt(1, uid);
	            ps.setInt(2, start);
	            ps.setInt(3, count);
	  
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                OrderItemVO bean = new OrderItemVO();
	                int id = rs.getInt(1);
	 
	                int pid = rs.getInt("pid");
	                int oid = rs.getInt("oid");
	                int number = rs.getInt("number");
	                 
	                ProductVO product = new ProductDAOImpl().get(pid);
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	 
	                MemberVO user = new MemberDAOImpl().get(uid);
	                bean.setProduct(product);
	 
	                bean.setUser(user);
	                bean.setNumber(number);
	                bean.setId(id);               
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
	    public List<OrderItemVO> listByOrder(int oid) {
	        return listByOrder(oid, 0, Short.MAX_VALUE);
	    }
	     
	    public List<OrderItemVO> listByOrder(int oid, int start, int count) {
	        List<OrderItemVO> beans = new ArrayList<OrderItemVO>();
	         
	         
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL2);
	            ) 
	        	{
	             
	            ps.setInt(1, oid);
	            ps.setInt(2, start);
	            ps.setInt(3, count);
	             
	            ResultSet rs = ps.executeQuery();
	             
	            while (rs.next()) {
	                OrderItemVO bean = new OrderItemVO();
	                int id = rs.getInt(1);
	                 
	                int pid = rs.getInt("pid");
	                int uid = rs.getInt("uid");
	                int number = rs.getInt("number");
	                 
	                ProductVO product = new ProductDAOImpl().get(pid);
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	                 
	                MemberVO user = new MemberDAOImpl().get(uid);
	                bean.setProduct(product);
	                 
	                bean.setUser(user);
	                bean.setNumber(number);
	                bean.setId(id);               
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	             
	            e.printStackTrace();
	        }
	        return beans;
	    }
	 
	    public void fill(List<OrderVO> os) {
	        for (OrderVO o : os) {
	            List<OrderItemVO> ois=listByOrder(o.getId());
	            float total = 0;
	            int totalNumber = 0;
	            for (OrderItemVO oi : ois) {
	                 total+=oi.getNumber()*oi.getProduct().getPromotePrice();
	                 totalNumber+=oi.getNumber();
	            }
	            o.setTotal(total);
	            o.setOrderItems(ois);
	            o.setTotalNumber(totalNumber);
	        }
	         
	    }
	 
	    public void fill(OrderVO o) {
	        List<OrderItemVO> ois=listByOrder(o.getId());
	        float total = 0;
	        for (OrderItemVO oi : ois) {
	             total+=oi.getNumber()*oi.getProduct().getPromotePrice();
	        }
	        o.setTotal(total);
	        o.setOrderItems(ois);
	    }
	 
	    public List<OrderItemVO> listByProduct(int pid) {
	        return listByProduct(pid, 0, Short.MAX_VALUE);
	    }
	  
	    public List<OrderItemVO> listByProduct(int pid, int start, int count) {
	        List<OrderItemVO> beans = new ArrayList<OrderItemVO>();
	  
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL3);
	        	) 
	        	{
	  
	            ps.setInt(1, pid);
	            ps.setInt(2, start);
	            ps.setInt(3, count);
	  
	            ResultSet rs = ps.executeQuery();
	  
	            while (rs.next()) {
	                OrderItemVO bean = new OrderItemVO();
	                int id = rs.getInt(1);
	 
	                int uid = rs.getInt("uid");
	                int oid = rs.getInt("oid");
	                int number = rs.getInt("number");
	                 
	                ProductVO product = new ProductDAOImpl().get(pid);
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	 
	                MemberVO user = new MemberDAOImpl().get(uid);
	                bean.setProduct(product);
	 
	                bean.setUser(user);
	                bean.setNumber(number);
	                bean.setId(id);               
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
	 
	    public int getSaleCount(int pid) {
	         int total = 0;
	            try (Connection c = JDBCUtilites.getConnection();
	            	 PreparedStatement ps = c.prepareStatement(GET_ONE2);
	            	) 
	            	{
	                ps.setInt(1, pid);
	                ResultSet rs = ps.executeQuery();
	                while (rs.next()) {
	                    total = rs.getInt(1);
	                }
	            } catch (Exception e) {
	      
	                e.printStackTrace();
	            }
	            return total;
	    }

		
	}

