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

public class OrderDAOImpl {

	public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";
    
    private static final String GET_TOTAL = "select count(*) from Order_";
	private static final String ADD_STMT = "insert into Order_ values(ORDERID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPATE_STMT = "update Order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=? ,orderDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , OrderVOCode =?, uid=?, status=? where orderid = ?";
	private static final String DEL_STMT = "delete from Order_ where Orderid = ";
	private static final String GET_ONE = "select * from Order_ where Orderid = ";
	private static final String GET_ALL = "select * from Order_ Order by Orderid desc limit ?,? ";
	private static final String GET_ALL2 = "select * from Order_ where uid = ? and status != ? Order by orderid desc limit ?,? ";
	
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
  
    public void add(OrderVO bean) {
 
        try (Connection c = JDBCUtilites.getConnection();
        	PreparedStatement ps = c.prepareStatement(ADD_STMT);
        	) 
        	{
            ps.setString(1, bean.getOrderCode());
            ps.setString(2, bean.getAddress());
            ps.setString(3, bean.getPost());
            ps.setString(4, bean.getReceiver());
            ps.setString(5, bean.getMobile());
            ps.setString(6, bean.getUserMessage());
             
            ps.setDate(7,  JDBCUtilites.u2s(bean.getOrderDate()));
            ps.setDate(8,  JDBCUtilites.u2s(bean.getPayDate()));
            ps.setDate(9,  JDBCUtilites.u2s(bean.getDeliveryDate()));
            ps.setDate(10,  JDBCUtilites.u2s(bean.getConfirmDate()));
            ps.setInt(11, bean.getMember().getMemberId());
            ps.setString(12, bean.getStatus());
 
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
  
    public void update(OrderVO bean) {
 
        try (Connection c = JDBCUtilites.getConnection();
        	PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        	{
  
            ps.setString(1, bean.getAddress());
            ps.setString(2, bean.getPost());
            ps.setString(3, bean.getReceiver());
            ps.setString(4, bean.getMobile());
            ps.setString(5, bean.getUserMessage());
            ps.setDate(6, JDBCUtilites.u2s(bean.getOrderDate()));;
            ps.setDate(7, JDBCUtilites.u2s(bean.getPayDate()));;
            ps.setDate(8, JDBCUtilites.u2s(bean.getDeliveryDate()));;
            ps.setDate(9, JDBCUtilites.u2s(bean.getConfirmDate()));;
            ps.setString(10, bean.getOrderCode());
            ps.setInt(11, bean.getMember().getMemberId());
            ps.setString(12, bean.getStatus());
            ps.setInt(13, bean.getId());
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
  
    public OrderVO get(int id) {
        OrderVO bean = new OrderVO();
  
        try (Connection c = JDBCUtilites.getConnection();
        	PreparedStatement ps = c.prepareStatement(GET_ONE);
        	) 
        	{
  
            ResultSet rs = ps.executeQuery();
  
            if (rs.next()) {
                String OrderCode =rs.getString("OrderVOCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int uid =rs.getInt("uid");
                Date createDate = JDBCUtilites.s2u( rs.getDate("orderDate"));
                Date payDate = JDBCUtilites.s2u( rs.getDate("payDate"));
                Date deliveryDate = JDBCUtilites.s2u( rs.getDate("deliveryDate"));
                Date confirmDate = JDBCUtilites.s2u( rs.getDate("confirmDate"));
                 
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
                 
                bean.setId(id);
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return bean;
    }
  
    public List<OrderVO> list() {
        return list(0, Short.MAX_VALUE);
    }
  
    public List<OrderVO> list(int start, int count) {
        List<OrderVO> beans = new ArrayList<OrderVO>();
  
        try (Connection c = JDBCUtilites.getConnection();
        	PreparedStatement ps = c.prepareStatement(GET_ALL);
        	) 
        	{
            ps.setInt(1, start);
            ps.setInt(2, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                OrderVO bean = new OrderVO();
                String OrderCode =rs.getString("OrderVOCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = JDBCUtilites.s2u( rs.getDate("orderDate"));
                Date payDate = JDBCUtilites.s2u( rs.getDate("payDate"));
                Date deliveryDate = JDBCUtilites.s2u( rs.getDate("deliveryDate"));
                Date confirmDate = JDBCUtilites.s2u( rs.getDate("confirmDate"));
                int uid =rs.getInt("uid");               
                 
                int id = rs.getInt("id");
                bean.setId(id);
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
                beans.add(bean);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return beans;
    }
     
    public List<OrderVO> list(int uid,String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }
      
    public List<OrderVO> list(int uid, String excludedStatus, int start, int count) {
        List<OrderVO> beans = new ArrayList<OrderVO>();
         
        try (Connection c = JDBCUtilites.getConnection();
        	PreparedStatement ps = c.prepareStatement(GET_ALL2);
        	) 
        	{
            ps.setInt(1, uid);
            ps.setString(2, excludedStatus);
            ps.setInt(3, start);
            ps.setInt(4, count);
             
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
                Date createDate = JDBCUtilites.s2u( rs.getDate("orderDate"));
                Date payDate = JDBCUtilites.s2u( rs.getDate("payDate"));
                Date deliveryDate = JDBCUtilites.s2u( rs.getDate("deliveryDate"));
                Date confirmDate = JDBCUtilites.s2u( rs.getDate("confirmDate"));
                
                int id = rs.getInt("id");
                bean.setId(id);
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

