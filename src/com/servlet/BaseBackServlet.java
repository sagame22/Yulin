package com.servlet;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.reflect.Method;
import com.category.model.CategoryDAOImpl;
import com.order.model.OrderDAOImpl;
import com.orderitem.model.OrderItemDAOImpl;
import com.product.model.ProductDAOImpl;
import com.productimage.model.ProductImageDAOImpl;
import com.property.model.PropertyDAOImpl;
import com.propertyvalue.model.PropertyValueDAOImpl;
import com.review.model.ReviewDAOImpl;
import com.member.model.MemberDAOImpl;


public abstract class BaseBackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public abstract String add(HttpServletRequest request, HttpServletResponse response) ;
	public abstract String delete(HttpServletRequest request, HttpServletResponse response) ;
	public abstract String edit(HttpServletRequest request, HttpServletResponse response) ;
	public abstract String update(HttpServletRequest request, HttpServletResponse response) ;
	public abstract String list(HttpServletRequest request, HttpServletResponse response) ;
	
	
	protected CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();
	protected OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
	protected OrderItemDAOImpl orderItemDAOImpl = new OrderItemDAOImpl();
	protected ProductDAOImpl productDAOImpl = new ProductDAOImpl();
	protected ProductImageDAOImpl productImageDAOImpl = new ProductImageDAOImpl();
	protected PropertyDAOImpl propertyDAOImpl = new PropertyDAOImpl();
	protected PropertyValueDAOImpl propertyValueDAOImpl = new PropertyValueDAOImpl();
	protected ReviewDAOImpl reviewDAOImpl = new ReviewDAOImpl();
	protected MemberDAOImpl MemberDAOImpl = new MemberDAOImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);

		}
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			/*用反射調用相對應方法*/
			String method = (String) request.getAttribute("method");
			System.out.println(method);
			String redirect =null;
			try {
				Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class);
				redirect = m.invoke(this,request,response).toString();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			/*根據方法的返回值，進行對應的客户端跳轉，服務端跳轉，或輸出字串*/	
			if(redirect.startsWith("@"))
				response.sendRedirect(redirect.substring(1));
			else if(redirect.startsWith("%"))
				response.getWriter().print(redirect.substring(1));
			else
				request.getRequestDispatcher(redirect).forward(request, response);
			
	}
	public InputStream parseUpload(HttpServletRequest request)  {
		
			InputStream is = null;
			try {
				//處理圖檔
				is = request.getPart("filepath").getInputStream();
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return is; 
				
		}
	
	 	
}
