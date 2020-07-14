package com.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.model.OrderDAOImpl;
import com.order.model.OrderVO;
@WebServlet("/orderServlet")
public class OrderServlet extends BaseBackServlet {

	private static final long serialVersionUID = 1L;


	public String add(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	public String delivery(HttpServletRequest request, HttpServletResponse response) {
		//接收哪個會員的訂單
		int id = Integer.parseInt(request.getParameter("id"));
		OrderVO o = orderDAOImpl.get(id);
		o.setDeliveryDate(new Date());
		//修改訂單狀態
		o.setStatus(OrderDAOImpl.waitConfirm);
		orderDAOImpl.update(o);
		return "@admin_order_list";
	}

	
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		return null;	
	}

	
	public String update(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<OrderVO> os = orderDAOImpl.list();
		
		orderItemDAOImpl.fill(os);
		request.setAttribute("os", os);
		
		return "admin/listOrder.jsp";
	}
}
