package com.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryDAOImpl;
import com.member.model.MemberDAOImpl;
import com.order.model.OrderDAOImpl;
import com.orderitem.model.OrderItemDAOImpl;
import com.product.model.ProductDAOImpl;
import com.productimage.model.ProductImageDAOImpl;
import com.property.model.PropertyDAOImpl;
import com.propertyvalue.model.PropertyValueDAOImpl;
import com.review.model.ReviewDAOImpl;


public class BaseForeServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
	protected OrderDAOImpl orderDAO = new OrderDAOImpl();
	protected OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
	protected ProductDAOImpl productDAO = new ProductDAOImpl();
	protected ProductImageDAOImpl productImageDAO = new ProductImageDAOImpl();
	protected PropertyDAOImpl propertyDAO = new PropertyDAOImpl();
	protected PropertyValueDAOImpl propertyValueDAO = new PropertyValueDAOImpl();
	protected ReviewDAOImpl reviewDAO = new ReviewDAOImpl();
	protected MemberDAOImpl memberDAO = new MemberDAOImpl();
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			
			String method = (String) request.getAttribute("method");
			//取方法名反射調用該物件方法
			Method m;
			try {
				m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
						javax.servlet.http.HttpServletResponse.class);
				String redirect = m.invoke(this,request, response).toString();
				//重導該路徑(去除第一個char[])
				if(redirect.startsWith("@"))
					response.sendRedirect(redirect.substring(1));
				else if(redirect.startsWith("%"))
					//直接印出(去除第一個char[])
					response.getWriter().print(redirect.substring(1));
				else//轉發該路徑(去除第一個char[])
					request.getRequestDispatcher(redirect).forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}