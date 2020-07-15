package com.order.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.member.model.MemberDAOImpl;
import com.member.model.MemberVO;
import com.uti.tool.JDBCUtilites;

public class OrderDAOImpl implements OrderDAO {

	public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";
    
    private static final String GET_TOTAL = "select count(*) from Order_";
	private static final String ADD_STMT = "insert into Order_ values(ORDERID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPATE_STMT = "update Order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=? ,orderDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , OrderCode =?, mid=?, status=? where orderid = ?";
	private static final String DEL_STMT = "delete from Order_ where Orderid = ?";
	private static final String GET_ONE = "select * from Order_ where Orderid = ?";
	private static final String GET_ALL = "select * from Order_ Order by Orderid desc ";
	private static final String GET_ALL2 = "select * from Order_ where mid = ? and status != ? Order by orderid desc";
	
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
  
    public int add(OrderVO bean) {
    	int[]cols= {1};
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(ADD_STMT,cols);
        	) 
        	{
            ps.setString(1, bean.getOrderCode());
            ps.setString(2, bean.getAddress());
            ps.setString(3, bean.getPost());
            ps.setString(4, bean.getReceiver());
            ps.setString(5, bean.getMobile());
            ps.setString(6, bean.getUserMessage());
             
            ps.setTimestamp(7,  JDBCUtilites.u2s(bean.getOrderDate()));
            ps.setTimestamp(8,  JDBCUtilites.u2s(bean.getPayDate()));
            ps.setTimestamp(9,  JDBCUtilites.u2s(bean.getDeliveryDate()));
            ps.setTimestamp(10,  JDBCUtilites.u2s(bean.getConfirmDate()));
            ps.setInt(11, bean.getMember().getMemberId());
            ps.setString(12, bean.getStatus());
 
            ps.execute();
            ResultSet key = ps.getGeneratedKeys();
            if(key.next()) {
            	return key.getInt(1);
            }
            
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }		return 0;
    }
  
    public void update(OrderVO bean) {
 
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        	{
  
            ps.setString(1, bean.getAddress());
            ps.setString(2, bean.getPost());
            ps.setString(3, bean.getReceiver());
            ps.setString(4, bean.getMobile());
            ps.setString(5, bean.getUserMessage());
            ps.setTimestamp(6, JDBCUtilites.u2s(bean.getOrderDate()));;
            ps.setTimestamp(7, JDBCUtilites.u2s(bean.getPayDate()));;
            ps.setTimestamp(8, JDBCUtilites.u2s(bean.getDeliveryDate()));;
            ps.setTimestamp(9, JDBCUtilites.u2s(bean.getConfirmDate()));;
            ps.setString(10, bean.getOrderCode());
            ps.setInt(11, bean.getMember().getMemberId());
            ps.setString(12, bean.getStatus());
            ps.setInt(13, bean.getOrderId());
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
  
    public OrderVO get(int id) {
        OrderVO bean = new OrderVO();
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_ONE);
        	) 
        	{
        	ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
  
            if (rs.next()) {
                String OrderCode =rs.getString("OrderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int uid =rs.getInt("mid");
                Date createDate = rs.getTimestamp("orderDate");
                Date payDate = rs.getTimestamp("payDate");
                Date deliveryDate = rs.getTimestamp("deliveryDate");
                Date confirmDate = rs.getTimestamp("confirmDate");
                 
                bean.setOrderCode(OrderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setOrderDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                MemberVO user = new MemberDAOImpl().get(uid);
                bean.setMember(user);
                bean.setStatus(status);
                 
                bean.setOrderId(id);
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return bean;
    }
  
  
    public List<OrderVO> list() {
        List<OrderVO> beans = new ArrayList<OrderVO>();
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_ALL);
        	) 
        	{
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                OrderVO bean = new OrderVO();
                String OrderCode =rs.getString("OrderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = rs.getTimestamp("orderDate");
                Date payDate = rs.getTimestamp("payDate");
                Date deliveryDate =  rs.getTimestamp("deliveryDate");
                Date confirmDate =  rs.getTimestamp("confirmDate");
                int mid =rs.getInt("mid");               
                int orderId = rs.getInt("orderid");
                
                bean.setOrderId(orderId);
                bean.setOrderCode(OrderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setOrderDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                MemberVO member = new MemberDAOImpl().get(mid);
                bean.setMember(member);
                bean.setStatus(status);
                beans.add(bean);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return beans;
    }
     
 
      
    public List<OrderVO> list(int mid, String excludedStatus) {
        List<OrderVO> beans = new ArrayList<OrderVO>();
         
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_ALL2);
        	) 
        	{
            ps.setInt(1, mid);
            ps.setString(2, excludedStatus);
            
            ResultSet rs = ps.executeQuery();
             
            while (rs.next()) {
                OrderVO bean = new OrderVO();
                String OrderCode =rs.getString("OrderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = rs.getTimestamp("orderDate");
                Date payDate = rs.getTimestamp("payDate");
                Date deliveryDate =  rs.getTimestamp("deliveryDate");
                Date confirmDate =  rs.getTimestamp("confirmDate");
                int orderId = rs.getInt("orderId");
                
                bean.setOrderId(orderId);
                bean.setOrderCode(OrderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setOrderDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                MemberVO user = new MemberDAOImpl().get(mid);
                bean.setStatus(status);
                bean.setMember(user);
                beans.add(bean);
            }
        } catch (Exception e) {
             
            e.printStackTrace();
        }
        return beans;
    }
  
}

