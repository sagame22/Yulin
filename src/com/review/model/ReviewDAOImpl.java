package com.review.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.member.model.MemberDAOImpl;
import com.member.model.MemberVO;
import com.product.model.ProductDAOImpl;
import com.product.model.ProductVO;
import com.uti.tool.JDBCUtilites;

public class ReviewDAOImpl {
	
	private static final String GET_TOTAL = "select count(*) from Review";
	private static final String GET_TOTAL2 = "select count(*) from Review where pid = ?";
	private static final String ADD_STMT = "insert into Review values(REVIEWID_SEQ.nextval,?,?,?,?)";
	private static final String UPATE_STMT = "update Review set content= ?, mid=?, pid=? , reviewDate = ? where reviewid = ?";
	private static final String DEL_STMT = "delete from Review where Reviewid = ?";
	private static final String GET_ONE = "select * from Review where Reviewid = ?";
	private static final String GET_COUNT = "select count(*) from Review where pid = ? ";
	private static final String GET_ALL = "select * from Review where pid = ? order by rownum desc ";
	private static final String GET_EXIST = "select * from Review where content = ? and pid = ?";

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
    public int getTotal(int pid) {
        int total = 0;
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        		PreparedStatement ps = c.prepareStatement(GET_TOTAL2);
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
  
    public void add(ReviewVO bean) {
 
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(ADD_STMT);
        	) 
        	{
            ps.setString(1, bean.getContent());
            ps.setInt(2, bean.getMember().getMemberId());
            ps.setInt(3, bean.getProduct().getProductId());
            ps.setTimestamp(4, JDBCUtilites.u2s(bean.getReviewDate()));
             
            ps.execute();
  
           
        } catch (Exception e) {
  
            e.printStackTrace();
        }
    }
  
    public void update(ReviewVO bean) {
 
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        	{
            ps.setString(1, bean.getContent());
            ps.setInt(2, bean.getMember().getMemberId());
            ps.setInt(3, bean.getProduct().getProductId());
            ps.setTimestamp(4, JDBCUtilites.u2s( bean.getReviewDate()) );
            ps.setInt(5, bean.getReviewId());
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
  
    public ReviewVO get(int id) {
        ReviewVO bean = new ReviewVO();
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(GET_ONE);
        	) 
        	{
        	ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                Date createDate = rs.getTimestamp("reviewDate");
                 
                String content = rs.getString("content");
                 
                ProductVO ProductVO = new ProductDAOImpl().get(pid);
                MemberVO user = new MemberDAOImpl().get(uid);
                 
                bean.setContent(content);
                bean.setReviewDate(createDate);
                bean.setProduct(ProductVO);
                bean.setMember(user);
                bean.setReviewId(id);
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return bean;
    }
  

  
    public int getCount(int pid) {
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(GET_COUNT);
        	) 
        	{
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
               return rs.getInt(1);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return 0;      
    }
    public List<ReviewVO> list(int pid) {
        List<ReviewVO> beans = new ArrayList<ReviewVO>();
  
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(GET_ALL);
        	) 
        	{
  
            ps.setInt(1, pid);
           
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                ReviewVO bean = new ReviewVO();
                int id = rs.getInt(1);
 
                int uid = rs.getInt("mid");
                Date createDate = rs.getTimestamp("ReviewDate");
                String content = rs.getString("content");
                ProductVO ProductVO = new ProductDAOImpl().get(pid);
                MemberVO user = new MemberDAOImpl().get(uid);
                		
                bean.setContent(content);
                bean.setReviewDate(createDate);
                bean.setReviewId(id);    
                bean.setProduct(ProductVO);
                bean.setMember(user);
                beans.add(bean);
            }
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return beans;
    }
    public boolean isExist(String content, int pid) {
         
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	 PreparedStatement ps = c.prepareStatement(GET_EXIST)
        	) 
        	{
            ps.setString(1, content);
            ps.setInt(2, pid);
             
            ResultSet rs = ps.executeQuery();
  
            if (rs.next()) {
                return true;
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
 
        return false;
    }
  
}
