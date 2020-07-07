package com.product.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryVO;
import com.orderitem.model.OrderItemDAOImpl;
import com.productimage.model.ProductImageDAOImpl;
import com.productimage.model.ProductImageVO;
import com.review.model.ReviewDAOImpl;
import com.uti.tool.JDBCUtilites;

public class ProductDAOImpl {
	
	private static final String GET_TOTAL = "select count(*) from Product where cid = ";
	private static final String ADD_STMT = "insert into Product values(PRODUCTID_SEQ.nextval,?,?,?,?,?,?,?)";
	private static final String UPATE_STMT = "update Product set name= ?, subTitle=?, orignalPrice=?,promotePrice=?,stock=?, cid = ?, createDate=? where PRODUCTID = ?";
	private static final String DEL_STMT = "delete from Product where Productid = ";
	private static final String GET_ONE = "select * from Product where Productid = ";
	private static final String GET_ALL = "select * from Product where cid = ? order by Productid desc limit ?,? ";
	private static final String GET_ALL2 = "select * from Product limit ?,? ";
	private static final String SEARCH = "select * from Product where name like ? limit ?,? ";
	
	 public int getTotal(int cid) {
	        int total = 0;
	        try (Connection c = JDBCUtilites.getConnection();
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
	  
	    public void add(ProductVO bean) {
	 
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(ADD_STMT);
	        	 ) 
	        	 {
	  
	            ps.setString(1, bean.getName());
	            ps.setString(2, bean.getSubTitle());
	            ps.setFloat(3, bean.getOrignalPrice());
	            ps.setFloat(4, bean.getPromotePrice());
	            ps.setInt(5, bean.getStock());
	            ps.setInt(6, bean.getCategory().getCategoryId());
	            ps.setDate(7, JDBCUtilites.u2s(bean.getCreateDate()));
	            ps.execute();
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	    }
	  
	    public void update(ProductVO bean) {
	 
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(UPATE_STMT);
	        	 ) 
	        	{
	 
	            ps.setString(1, bean.getName());
	            ps.setString(2, bean.getSubTitle());
	            ps.setFloat(3, bean.getOrignalPrice());
	            ps.setFloat(4, bean.getPromotePrice());
	            ps.setInt(5, bean.getStock());
	            ps.setInt(6, bean.getCategory().getCategoryId());
	            ps.setDate(7, JDBCUtilites.u2s(bean.getCreateDate()));
	            ps.setInt(8, bean.getProductId());
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
	  
	    public ProductVO get(int id) {
	        ProductVO bean = new ProductVO();
	  
	        try (Connection c = JDBCUtilites.getConnection();
	        		 PreparedStatement ps = c.prepareStatement(GET_ONE);
	        	) 
	        	{
	        	ps.setInt(1,id);
	  
	            ResultSet rs = ps.executeQuery(GET_ONE);
	  
	            if (rs.next()) {
	 
	                String name = rs.getString("name");
	                String subTitle = rs.getString("subTitle");
	                float orignalPrice = rs.getFloat("orignalPrice");
	                float promotePrice = rs.getFloat("promotePrice");
	                int stock = rs.getInt("stock");
	                int cid = rs.getInt("cid");
	                Date createDate = JDBCUtilites.s2u( rs.getDate("createDate"));
	               
	                bean.setName(name);
	                bean.setSubTitle(subTitle);
	                bean.setOrignalPrice(orignalPrice);
	                bean.setPromotePrice(promotePrice);
	                bean.setStock(stock);
	                CategoryVO category = new CategoryDAOImpl().get(cid);
	                bean.setCategory(category);
	                bean.setCreateDate(createDate);
	                bean.setProductId(id);
	                setFirstProductImage(bean);
	            }
	  
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return bean;
	    }
	  
	    public List<ProductVO> list(int cid) {
	        return list(cid,0, Short.MAX_VALUE);
	    }
	  
	    public List<ProductVO> list(int cid, int start, int count) {
	        List<ProductVO> beans = new ArrayList<ProductVO>();
	        CategoryVO category = new CategoryDAOImpl().get(cid);
	  
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL);
	            ) 
	        	{
	            ps.setInt(1, cid);
	            ps.setInt(2, start);
	            ps.setInt(3, count);
	  
	            ResultSet rs = ps.executeQuery();
	  
	            while (rs.next()) {
	                ProductVO bean = new ProductVO();
	                int id = rs.getInt(1);
	                String name = rs.getString("name");
	                String subTitle = rs.getString("subTitle");
	                float orignalPrice = rs.getFloat("orignalPrice");
	                float promotePrice = rs.getFloat("promotePrice");
	                int stock = rs.getInt("stock");
	                Date createDate = JDBCUtilites.s2u( rs.getDate("createDate"));
	 
	                bean.setName(name);
	                bean.setSubTitle(subTitle);
	                bean.setOrignalPrice(orignalPrice);
	                bean.setPromotePrice(promotePrice);
	                bean.setStock(stock);
	                bean.setCreateDate(createDate);
	                bean.setProductId(id);
	                bean.setCategory(category);
	                setFirstProductImage(bean);
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
	    public List<ProductVO> list() {
	        return list(0,Short.MAX_VALUE);
	    }
	    public List<ProductVO> list(int start, int count) {
	        List<ProductVO> beans = new ArrayList<ProductVO>();
	 
	        try (Connection c = JDBCUtilites.getConnection();
	        	 PreparedStatement ps = c.prepareStatement(GET_ALL2);
	        	) 
	        	{
	 
	            ps.setInt(1, start);
	            ps.setInt(2, count);
	  
	            ResultSet rs = ps.executeQuery();
	  
	            while (rs.next()) {
	                ProductVO bean = new ProductVO();
	                int id = rs.getInt(1);
	                int cid = rs.getInt("cid");
	                String name = rs.getString("name");
	                String subTitle = rs.getString("subTitle");
	                float orignalPrice = rs.getFloat("orignalPrice");
	                float promotePrice = rs.getFloat("promotePrice");
	                int stock = rs.getInt("stock");
	                Date createDate = JDBCUtilites.s2u( rs.getDate("createDate"));
	 
	                bean.setName(name);
	                bean.setSubTitle(subTitle);
	                bean.setOrignalPrice(orignalPrice);
	                bean.setPromotePrice(promotePrice);
	                bean.setStock(stock);
	                bean.setCreateDate(createDate);
	                bean.setProductId(id);
	 
	                CategoryVO category = new CategoryDAOImpl().get(cid);
	                bean.setCategory(category);
	                beans.add(bean);
	            }
	        } catch (Exception e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }   
	 
	    public void fill(List<CategoryVO> cs) {
	        for (CategoryVO c : cs)
	            fill(c);
	    }
	    public void fill(CategoryVO c) {
	            List<ProductVO> ps = this.list(c.getCategoryId());
	            c.setProducts(ps);
	    }
	 
	    public void fillByRow(List<CategoryVO> cs) {
	        int productNumberEachRow = 8;
	        for (CategoryVO c : cs) {
	            List<ProductVO> products =  c.getProducts();
	            List<List<ProductVO>> productsByRow =  new ArrayList<>();
	            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
	                int size = i+productNumberEachRow;
	                size= size>products.size()?products.size():size;
	                List<ProductVO> productsOfEachRow =products.subList(i, size);
	                productsByRow.add(productsOfEachRow);
	            }
	            c.setProductsByRow(productsByRow);
	        }
	    }
	     
	    public void setFirstProductImage(ProductVO p) {
	        List<ProductImageVO> pis= new ProductImageDAOImpl().list(p, ProductImageDAOImpl.type_single);
	        if(!pis.isEmpty())
	            p.setFirstProductImage(pis.get(0));    
	    }
	     
	    public void setSaleAndReviewNumber(ProductVO p) {
	        int saleCount = new OrderItemDAOImpl().getSaleCount(p.getProductId());
	        p.setSaleCount(saleCount);         
	 
	        int reviewCount = new ReviewDAOImpl().getCount(p.getProductId());
	        p.setReviewCount(reviewCount);
	         
	    }
	 
	    public void setSaleAndReviewNumber(List<ProductVO> products) {
	        for (ProductVO p : products) {
	            setSaleAndReviewNumber(p);
	        }
	    }
	 
	    public List<ProductVO> search(String keyword, int start, int count) {
	         List<ProductVO> beans = new ArrayList<ProductVO>();
	          
	         if(null==keyword||0==keyword.trim().length())
	             return beans;
	      
	            try (Connection c = JDBCUtilites.getConnection();
	            	 PreparedStatement ps = c.prepareStatement(SEARCH);
	            	) 
	            	{
	                ps.setString(1, "%"+keyword.trim()+"%");
	                ps.setInt(2, start);
	                ps.setInt(3, count);
	      
	                ResultSet rs = ps.executeQuery();
	      
	                while (rs.next()) {
	                    ProductVO bean = new ProductVO();
	                    int id = rs.getInt(1);
	                    int cid = rs.getInt("cid");
	                    String name = rs.getString("name");
	                    String subTitle = rs.getString("subTitle");
	                    float orignalPrice = rs.getFloat("orignalPrice");
	                    float promotePrice = rs.getFloat("promotePrice");
	                    int stock = rs.getInt("stock");
	                    Date createDate = JDBCUtilites.s2u( rs.getDate("createDate"));
	 
	                    bean.setName(name);
	                    bean.setSubTitle(subTitle);
	                    bean.setOrignalPrice(orignalPrice);
	                    bean.setPromotePrice(promotePrice);
	                    bean.setStock(stock);
	                    bean.setCreateDate(createDate);
	                    bean.setProductId(id);
	 
	                    CategoryVO category = new CategoryDAOImpl().get(cid);
	                    bean.setCategory(category);
	                    setFirstProductImage(bean);               
	                    beans.add(bean);
	                }
	            } catch (Exception e) {
	      
	                e.printStackTrace();
	            }
	            return beans;
	    }
	}