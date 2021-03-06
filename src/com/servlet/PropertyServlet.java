package com.servlet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryVO;
import com.property.model.*;


@WebServlet("/propertyServlet")

public class PropertyServlet extends BaseBackServlet {

	private static final long serialVersionUID = 1L;

	//多方新增要取得1方的PK
	public String add(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		CategoryVO c = categoryDAOImpl.get(cid);
		
		String name=null;
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PropertyVO p = new PropertyVO();
		p.setCategory(c);
		p.setName(name);
		propertyDAOImpl.add(p);
		return "@admin_property_list?cid="+cid;
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		PropertyVO p = propertyDAOImpl.get(id);
		propertyDAOImpl.delete(id);
		return "@admin_property_list?cid="+p.getCategory().getCategoryId();
	}

	//查詢用PK查詢該條資料轉發至修改頁面
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		PropertyVO p = propertyDAOImpl.get(id);
		request.setAttribute("p", p);
		return "admin/editProperty.jsp";		
	}

	
	public String update(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		CategoryVO c = categoryDAOImpl.get(cid);

		
		int id = Integer.parseInt(request.getParameter("id"));
		String name=null;
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PropertyVO p = new PropertyVO();
		p.setCategory(c);
		p.setPropertyId(id);
		p.setName(name);
		propertyDAOImpl.update(p);
		return "@admin_property_list?cid="+p.getCategory().getCategoryId();
	}

	
	public String list(HttpServletRequest request, HttpServletResponse response) {
		String scid = request.getParameter("cid");
		if(scid!=null) {
		int cid = Integer.parseInt(scid);
		//把選定的分類VO和該分類底下的屬性存入
		CategoryVO c = categoryDAOImpl.get(cid);
		List<PropertyVO> ps = propertyDAOImpl.list(cid);
		request.getSession().setAttribute("ps", ps);
		request.getSession().setAttribute("c", c);
		}
		return "admin/listProperty.jsp";
	}


	
}
