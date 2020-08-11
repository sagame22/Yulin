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
	private static final String UPATE_STMT = "update OrderItem set pid= ?, oid=?, mid=?,count=?  where OrderItemid = ?";
	private static final String DEL_STMT = "delete from OrderItem where OrderItemid = ?";
	private static final String GET_ONE = "select * from OrderItem where OrderItemid = ?";
	private static final String GET_ONE2 = "select sum(count) from OrderItem where pid = ?";
	private static final String GET_ALL = "select * from OrderItem where mid = ? and oid=-1 order by OrderItemid desc ";
	private static final String GET_ALL2 = "select * from OrderItem where oid = ? order by OrderItemid desc";
	private static final String GET_ALL3 = "select * from OrderItem where pid = ? order by OrderItemid desc";
	
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
	  
	    public int add(OrderItemVO bean) {
	    	int []cols = {1};
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	PreparedStatement ps = c.prepareStatement(ADD_STMT,cols);
	        	) 
	        	{
	            ps.setInt(1, bean.getProduct().getProductId());
	             
	            //訂單明細創建,無訊息
	            if(null==bean.getOrder())
	                ps.setInt(2, -1);
	            else
	                ps.setInt(2, bean.getOrder().getOrderId()); 
	             
	            ps.setInt(3, bean.getMember().getMemberId());
	            ps.setInt(4, bean.getCount());
	            ps.execute();
	            ResultSet key = ps.getGeneratedKeys();
	            if(key.next()) {
	            	return key.getInt(1);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
			return 0;
	    }
	  
	    public void update(OrderItemVO bean) {
	 
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	PreparedStatement ps = c.prepareStatement(UPATE_STMT);
	        	) 
	        	{
	 
	            ps.setInt(1, bean.getProduct().getProductId());
	            if(null==bean.getOrder())
	                ps.setInt(2, -1);
	            else
	            ps.setInt(2, bean.getOrder().getOrderId());             
	            ps.setInt(3, bean.getMember().getMemberId());
	            ps.setInt(4, bean.getCount());
	             
	            ps.setInt(5, bean.getOrderItemId());
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
	  
	    public OrderItemVO get(int id) {
	        OrderItemVO bean = new OrderItemVO();
	  
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	PreparedStatement ps = c.prepareStatement(GET_ONE);

	        	) 
	        	{
	        	ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();
	  
	            if (rs.next()) {
	                int pid = rs.getInt("pid");
	                int oid = rs.getInt("oid");
	                int uid = rs.getInt("mid");
	                int number = rs.getInt("count");
	                ProductVO product = new ProductDAOImpl().get(pid);
	                MemberVO user = new MemberDAOImpl().get(uid);
	                bean.setProduct(product);
	                bean.setMember(user);
	                bean.setCount(number);
	                 
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	                 
	                bean.setOrderItemId(id);
	            }
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return bean;
	    }
	  
	    public List<OrderItemVO> listByUser(int uid) {
	        List<OrderItemVO> beans = new ArrayList<OrderItemVO>();
	  
	  
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL);
	        	) 
	        	{
	            ps.setInt(1, uid);
	  
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                OrderItemVO bean = new OrderItemVO();
	                int id = rs.getInt(1);
	 
	                int pid = rs.getInt("pid");
	                int oid = rs.getInt("oid");
	                int number = rs.getInt("count");
	                 
	                ProductVO product = new ProductDAOImpl().get(pid);
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	 
	                MemberVO user = new MemberDAOImpl().get(uid);
	                bean.setProduct(product);
	 
	                bean.setMember(user);
	                bean.setCount(number);
	                bean.setOrderItemId(id);               
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
	    public List<OrderItemVO> listByOrder(int oid) {
	        List<OrderItemVO> beans = new ArrayList<OrderItemVO>();
	         
	         
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL2);
	            ) 
	        	{
	             
	            ps.setInt(1, oid);
	            ResultSet rs = ps.executeQuery();
	             
	            while (rs.next()) {
	                OrderItemVO bean = new OrderItemVO();
	                int id = rs.getInt(1);
	                 
	                int pid = rs.getInt("pid");
	                int mid = rs.getInt("mid");
	                int number = rs.getInt("count");
	                 
	                ProductVO product = new ProductDAOImpl().get(pid);
	                if(-1!=oid){
	                    OrderVO order= new OrderDAOImpl().get(oid);
	                    bean.setOrder(order);                  
	                }
	                MemberVO user = new MemberDAOImpl().get(mid);
	                
	                bean.setProduct(product);
	                bean.setMember(user);
	                bean.setCount(number);
	                bean.setOrderItemId(id);               
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	             
	            e.printStackTrace();
	        }
	        return beans;
	    }
	 
	    public void fill(List<OrderVO> os) {
	    	//取出每筆訂單的訂單detail,把總價和總量算出來
	        for (OrderVO o : os) {
	            List<OrderItemVO> ois=listByOrder(o.getOrderId());
	            float total = 0;
	            int totalNumber = 0;
	            for (OrderItemVO oi : ois) {
	                 total+=oi.getCount()*oi.getProduct().getPromotePrice();
	                 totalNumber+=oi.getCount();
	            }
	            o.setTotal(total);
	            o.setOrderItems(ois);
	            o.setTotalNumber(totalNumber);
	        }
	         
	    }
	 
	    public void fill(OrderVO o) {
	    	//取出該訂單的所有訂單明細,得到產品總價
	        List<OrderItemVO> ois=listByOrder(o.getOrderId());
	        float total = 0;
	        for (OrderItemVO oi : ois) {
	             total+=oi.getCount()*oi.getProduct().getPromotePrice();
	        }
	        o.setTotal(total);
	        o.setOrderItems(ois);
	    }
	 
	  
	    public List<OrderItemVO> listByProduct(int pid) {
	        List<OrderItemVO> beans = new ArrayList<OrderItemVO>();
	  
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL3);
	        	) 
	        	{
	  
	            ps.setInt(1, pid);
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
	 
	                bean.setMember(user);
	                bean.setCount(number);
	                bean.setOrderItemId(id);               
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
	 
	    public int getSaleCount(int pid) {
	         int total = 0;
	            try (Connection c = JDBCUtilites.getConnectionJNDI();
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

