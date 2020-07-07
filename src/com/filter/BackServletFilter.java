package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

@WebFilter("/*")
public class BackServletFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		//取出webapp專案名稱
		String contextPath=request.getServletContext().getContextPath();
		//取出URI
		String uri = request.getRequestURI();
		
		//把URI去掉webapp名
		uri =StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/admin_")){
			//重新拼接成XXXServlet送出
			String servletPath = StringUtils.substringBetween(uri,"_", "_") + "Servlet";
			//取出最後名稱存入req送給servlet去執行相對應的方法
			String method = StringUtils.substringAfterLast(uri,"_" );
			request.setAttribute("method", method);
			request.getRequestDispatcher("/" + servletPath).forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

}
