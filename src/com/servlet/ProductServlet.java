package com.servlet;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryVO;
import com.product.model.ProductVO;
import com.property.model.PropertyVO;
import com.propertyvalue.model.PropertyValueVO;

@WebServlet("/productServlet")
public class ProductServlet extends BaseBackServlet {

	private static final long serialVersionUID = 1L;


	public String add(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		CategoryVO c = categoryDAOImpl.get(cid);
		
		String name=null;
		String subTitle=null;
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			subTitle= new String(request.getParameter("subTitle").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Double orignalPrice = Double.parseDouble(request.getParameter("orignalPrice"));
		Double promotePrice = Double.parseDouble(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		ProductVO p = new ProductVO();

		p.setCategory(c);
		p.setName(name);
		p.setSubTitle(subTitle);
		p.setOrignalPrice(orignalPrice);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		p.setCreateDate(new Date());

		productDAOImpl.add(p);
		return "@admin_product_list?cid="+cid;
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		ProductVO p = productDAOImpl.get(id);
		productDAOImpl.delete(id);
		return "@admin_product_list?cid="+p.getCategory().getCategoryId();
	}

	
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductVO p = productDAOImpl.get(id);
		request.setAttribute("p", p);
		return "admin/editProduct.jsp";		
	}
	
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductVO p = productDAOImpl.get(id);
		request.setAttribute("p", p);
		
		List<PropertyVO> pts= propertyDAOImpl.list(p.getCategory().getCategoryId());
		propertyValueDAOImpl.init(p);
		
		List<PropertyValueVO> pvs = propertyValueDAOImpl.list(p.getProductId());
		
		request.setAttribute("pvs", pvs);
		
		return "admin/editProductValue.jsp";		
	}

	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		
		PropertyValueVO pv =propertyValueDAOImpl.get(pvid);
		pv.setValue(value);
		propertyValueDAOImpl.update(pv);
		return "%success";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		CategoryVO c = categoryDAOImpl.get(cid);

		int id = Integer.parseInt(request.getParameter("id"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		Double orignalPrice = Double.parseDouble(request.getParameter("orignalPrice"));
		Double promotePrice = Double.parseDouble(request.getParameter("promotePrice"));
		String subTitle=null;
		String name=null;
		try {
			subTitle = new String(request.getParameter("subTitle").getBytes("ISO-8859-1"),"utf-8");
			name= new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ProductVO p = new ProductVO();

		p.setName(name);
		p.setSubTitle(subTitle);
		p.setOrignalPrice(orignalPrice);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		p.setProductId(id);
		p.setCategory(c);		

		productDAOImpl.update(p);
		
		return "@admin_product_list?cid="+p.getCategory().getCategoryId();
	}

	
	public String list(HttpServletRequest request, HttpServletResponse response) {
		String scid = request.getParameter("cid");
		
		if(scid!=null) {
		int cid = Integer.parseInt(scid);
		CategoryVO c = categoryDAOImpl.get(cid);
		List<ProductVO> ps = productDAOImpl.list(cid);
		request.getSession().setAttribute("ps2", ps);
		request.getSession().setAttribute("c", c);
		}
		return "admin/listProduct.jsp";
	}
}
