package com.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.member.model.MemberVO;

@WebFilter("/*")
public class AForeAuthFilter implements Filter{


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String contextPath=request.getServletContext().getContextPath();

		String[] noNeedAuthPage = new String[]{
				"home",
				"homepage",
				"checkLogin",
				"register",
				"loginAjax",
				"login",
				"product",
				"category",
				"search"};

		
		String uri = request.getRequestURI();
		uri =StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
			String method = StringUtils.substringAfterLast(uri,"/fore" );
			
			//不需會員登入的集合noNeedAuthPage和請求過來的(method)匹配,如果不包含則跳轉至登陸頁面
			if(!Arrays.asList(noNeedAuthPage).contains(method)){
				MemberVO member =(MemberVO) request.getSession().getAttribute("member");
				if(null==member){
					response.sendRedirect("login.jsp");
					return; 
				}
			}
			


		}
		
		chain.doFilter(request, response);
	}

}
