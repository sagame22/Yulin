package com.servlet;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryVO;
import com.uti.tool.WriteImg;

@WebServlet("/categoryServlet")
@MultipartConfig
public class CategoryServlet extends BaseBackServlet {
	
	protected CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();
	private static final long serialVersionUID = 1L;


	public String add(HttpServletRequest request, HttpServletResponse response) {	
		//取得上傳檔案的輸入流和圖檔名稱
		int id=0;
		InputStream is = super.parseUpload(request);
		String name;
		CategoryVO c = new CategoryVO();
		
		//圖檔名存入category
		try {
			name = new String(request.getParameter("cname").getBytes("ISO-8859-1"),"UTF-8");
			c.setName(name);
			id = categoryDAOImpl.add(c);
		} catch (UnsupportedEncodingException e) {
		}
		
		//圖片寫入硬碟
		String saveDirectory = "img/category";	
		String realPath = getServletContext().getRealPath(saveDirectory);
		File fsaveDirectory = new File(realPath);
		if (!fsaveDirectory.exists())
			 fsaveDirectory.mkdirs();
		//第一個file找到圖檔資料夾路徑,第二個file再創一個圖檔放進第一個file
		File file = new File(fsaveDirectory,id+".jpg");
		WriteImg.writeImg(is, file);
	
		return "@admin_category_list";
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("categoryId"));
		categoryDAOImpl.delete(id);
		return "@admin_category_list";
	}

	
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("categoryId"));
		CategoryVO c = categoryDAOImpl.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";		
	}

	
	public String update(HttpServletRequest request, HttpServletResponse response) {
		//取得上傳檔案的輸入流和圖檔名稱
				int id=0;
				InputStream is = super.parseUpload(request);
				String name;
				CategoryVO c = new CategoryVO();
				
				//圖檔名存入category
				try {
					name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
					id = Integer.parseInt(request.getParameter("id"));
					c.setName(name);
					c.setCategoryId(id);
					categoryDAOImpl.update(c);
				} catch (UnsupportedEncodingException e) {
				}
				
				//圖片寫入硬碟
				String saveDirectory = "img/category";	
				String realPath = getServletContext().getRealPath(saveDirectory);
				File fsaveDirectory = new File(realPath);
				if (!fsaveDirectory.exists())
					 fsaveDirectory.mkdirs();
				//第一個file找到圖檔資料夾路徑,第二個file再創一個圖檔放進第一個file
				File file = new File(fsaveDirectory,id+".jpg");
				WriteImg.writeImg(is, file);
		
		return "@admin_category_list";

	}

	
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<CategoryVO> list = categoryDAOImpl.list();
		
		request.setAttribute("thecs", list);
		
		return "admin/listCategory.jsp";
	}
		
}
