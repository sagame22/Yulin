package com.member.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.uti.tool.JDBCUtilites;

public class MemberDAOImpl implements MemberDAO {

	private static final String GET_TOTAL = "select count(*) from member";
	private static final String ADD_STMT = "insert into member values(MEMBERID_SEQ.nextval ,? ,?)";
	private static final String UPATE_STMT = "update member set name= ? , password = ? where memberid = ? ";
	private static final String DEL_STMT = "delete from member where memberid = ?";
	private static final String GET_ONE = "select * from member where memberid = ?";
	private static final String GET_ALL = "select * from member order by rownum desc";
	private static final String GET_BY_NAME = "select * from member where name = ?";
    private static final String PASSWORD_NAME = "select * from member where name = ? and password=?";
	@Override
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

	@Override
	public void add(MemberVO bean) {
		
        try (Connection c = JDBCUtilites.getConnectionJNDI(); 
        	PreparedStatement ps = c.prepareStatement(ADD_STMT);
        	) 
        
            {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.execute();
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
    }

	@Override
	public void update(MemberVO bean) {
		
        try (Connection c = JDBCUtilites.getConnectionJNDI(); 
        	 PreparedStatement ps = c.prepareStatement(UPATE_STMT);
        	) 
        { 
        	  ps.setString(1, bean.getName());
              ps.setString(2, bean.getPassword());
              ps.setInt(3, bean.getMemberId());
    
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
	public MemberVO get(int id) {
		 MemberVO bean = null;
		  
	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        		PreparedStatement s = c.prepareStatement(GET_ONE);
	        	) 
	        	{
	        	s.setInt(1, id);	      
	            ResultSet rs = s.executeQuery();	  
	            if (rs.next()) {
	            	 bean = new MemberVO();
	                 String name = rs.getString("name");
	                 bean.setName(name);
	                 String password = rs.getString("password");
	                 bean.setPassword(password);
	                 bean.setMemberId(id);
	             }
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return bean;
	    }

	@Override
	public List<MemberVO> list() {
		  List<MemberVO> beans = new ArrayList<MemberVO>();

	        try (Connection c = JDBCUtilites.getConnectionJNDI();
	        		PreparedStatement ps = c.prepareStatement(GET_ALL);
	        	) 
	        	{
	  
	            ResultSet rs = ps.executeQuery();
	  
	            while (rs.next()) {
	            	  MemberVO bean = new MemberVO();
	                  int id = rs.getInt(1);
	   
	                  String name = rs.getString("name");
	                  bean.setName(name);
	                  String password = rs.getString("password");
	                  bean.setPassword(password);
	                   
	                  bean.setMemberId(id);
	                  beans.add(bean);
	              }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
		
	@Override
	public MemberVO get(String name) {
		MemberVO bean = null;
             
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        		PreparedStatement ps = c.prepareStatement(GET_BY_NAME)
        	) 
        	{
            ps.setString(1, name);
            ResultSet rs =ps.executeQuery();
  
            if (rs.next()) {
                bean = new MemberVO();
                int id = rs.getInt("id");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setMemberId(id);
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return bean;
    }

	@Override
	public MemberVO get(String name, String password) {
		MemberVO bean = null;
        
        try (Connection c = JDBCUtilites.getConnectionJNDI();
        	PreparedStatement ps = c.prepareStatement(PASSWORD_NAME)
        	) 
        	{
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
  
            if (rs.next()) {
                bean = new MemberVO();
                int id = rs.getInt("MemberId");
                bean.setName(name);
                bean.setPassword(password);
                bean.setMemberId(id);
            }
  
        } catch (Exception e) {
  
            e.printStackTrace();
        }
        return bean;
    }
	
	   public boolean isExist(String name) {
	        MemberVO member = get(name);
	        return member!=null;
	 
	    }
		 

}

