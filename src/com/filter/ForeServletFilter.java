package com.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryVO;
import com.member.model.MemberVO;
import com.orderitem.model.OrderItemDAOImpl;
import com.orderitem.model.OrderItemVO;



@WebFilter("/*")
public class ForeServletFilter implements Filter{
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		ServletContext context = request.getServletContext();
		//存入專案路徑
		String contextPath=context.getContextPath();
		context.setAttribute("contextPath", contextPath);
		//將會員編號從Session取出,取得購物車中商品數量
		MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		int cartTotalItemNumber= 0;
		if(null!=member){
			List<OrderItemVO> ois = new OrderItemDAOImpl().listByUser(member.getMemberId());
			for (OrderItemVO oi : ois) {
				cartTotalItemNumber+=oi.getCount();
			}
		}
		//存入商品數量
		request.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		
		List<CategoryVO> cs=(List<CategoryVO>) request.getAttribute("cs");
		if(null==cs){
			cs=new CategoryDAOImpl().list();
			request.setAttribute("cs", cs);			
		}
		//若以fore開頭,擷取拼接成foreServlet轉發,若為foreServlet傳入則直接放行
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri =StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
			String method = StringUtils.substringAfterLast(uri,"/fore" );
			request.setAttribute("method", method);
			req.getRequestDispatcher("/foreServlet").forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}


	
}
