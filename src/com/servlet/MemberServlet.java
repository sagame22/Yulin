package com.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.member.model.MemberVO;
@WebServlet("/memberServlet")
public class MemberServlet extends BaseBackServlet {

	
	private static final long serialVersionUID = 1L;


	public String add(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		return null;		
	}

	
	public String update(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<MemberVO> member = MemberDAOImpl.list();
		
		request.setAttribute("member", member);
		
		return "admin/listUser.jsp";
	}
}
