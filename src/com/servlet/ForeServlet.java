package com.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryVO;
import com.comparator.ProductAllComparator;
import com.comparator.ProductDateComparator;
import com.comparator.ProductPriceComparator;
import com.comparator.ProductReviewComparator;
import com.comparator.ProductSaleCountComparator;
import com.member.model.MemberVO;
import com.order.model.OrderDAOImpl;
import com.order.model.OrderVO;
import com.orderitem.model.OrderItemVO;
import com.product.model.ProductDAOImpl;
import com.product.model.ProductVO;
import com.productimage.model.ProductImageDAOImpl;
import com.productimage.model.ProductImageVO;
import com.propertyvalue.model.PropertyValueVO;
import com.review.model.ReviewVO;



@WebServlet("/foreServlet")
public class ForeServlet extends BaseForeServlet {
	
	private static final long serialVersionUID = 1L;

	public String home(HttpServletRequest request, HttpServletResponse response) {
		List<CategoryVO> cs= new CategoryDAOImpl().list();
		new ProductDAOImpl().fill(cs);
		new ProductDAOImpl().fillByRow(cs);
		request.setAttribute("cs", cs);
		return "home.jsp";
	}
	
	public String register(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//轉譯HTML標籤
		name = HtmlUtils.htmlEscape(name);
		//判斷使用者帳戶名稱是否有使用
		boolean exist = memberDAO.isExist(name);
		if(exist){
			request.setAttribute("msg", "用戶名已被使用");
			return "register.jsp";	
		}
		MemberVO member = new MemberVO();
		member.setName(name);
		member.setPassword(password);
		memberDAO.add(member);
		
		return "@registerSuccess.jsp";	
	}	
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");		
		
		MemberVO member = memberDAO.get(name,password);
		 
		if(null==member){
			request.setAttribute("msg", "帳號密碼錯誤");
			return "login.jsp";	
		}
		request.getSession().setAttribute("member", member);
		return "@forehome";	
	}	
	public String product(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		ProductVO p = productDAO.get(pid);
		
		List<ProductImageVO> productSingleImages = productImageDAO.list(p, ProductImageDAOImpl.type_single);
		List<ProductImageVO> productDetailImages = productImageDAO.list(p, ProductImageDAOImpl.type_detail);
		p.setProductSingleImages(productSingleImages);
		p.setProductDetailImages(productDetailImages);
		
		List<PropertyValueVO> pvs = propertyValueDAO.list(p.getProductId());		
	
		List<ReviewVO> reviews = reviewDAO.list(p.getProductId());
		
		productDAO.setSaleAndReviewNumber(p);

		request.setAttribute("reviews", reviews);

		request.setAttribute("p", p);
		request.setAttribute("pvs", pvs);
		return "product.jsp";		
	}
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("member");
		return "@forehome";	
	}	
	
	public String checkLogin(HttpServletRequest request, HttpServletResponse response) {
		MemberVO user =(MemberVO) request.getSession().getAttribute("member");
		if(null!=user)
			return "%success";
		return "%fail";
	}
	
	public String loginAjax(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");		
		MemberVO member = memberDAO.get(name,password);
		
		if(null==member){
			return "%fail";	
		}
		request.getSession().setAttribute("member", member);
		return "%success";	
	}	
	public String category(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		CategoryVO c = new CategoryDAOImpl().get(cid);
		new ProductDAOImpl().fill(c);
		new ProductDAOImpl().setSaleAndReviewNumber(c.getProducts());		
		
		String sort = request.getParameter("sort");
		if(null!=sort){
		switch(sort){
			case "review":
				Collections.sort(c.getProducts(),new ProductReviewComparator());
				break;
			case "date" :
				Collections.sort(c.getProducts(),new ProductDateComparator());
				break;
				
			case "saleCount" :
				Collections.sort(c.getProducts(),new ProductSaleCountComparator());
				break;
				
			case "price":
				Collections.sort(c.getProducts(),new ProductPriceComparator());
				break;
				
			case "all":
				Collections.sort(c.getProducts(),new ProductAllComparator());
				break;
			}
		}
		
		request.setAttribute("c", c);
		return "category.jsp";		
	}	
	
	public String search(HttpServletRequest request, HttpServletResponse response){
		String keyword = request.getParameter("keyword");
		List<ProductVO> ps= new ProductDAOImpl().search(keyword);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps",ps);
		return "searchResult.jsp";
	}	
	
	public String buyone(HttpServletRequest request, HttpServletResponse response) {
		//取得產品ID和數量加入該產品VO
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		ProductVO p = productDAO.get(pid);
		int oiid = 0;
		//取得會員編號,用會員編號查詢該會員的訂單細項
		MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		//定義一個flag,若是相同產品則改為true,反之
		boolean found = false;
		List<OrderItemVO> ois = orderItemDAO.listByUser(member.getMemberId());
		//將訂單細項取出比對,若產品相同則將產品數量累加
		
		for (OrderItemVO oi : ois) {
			if(oi.getProduct().getProductId()==p.getProductId()){
				oi.setCount(oi.getCount()+num);
				//將訂單明細新增
				orderItemDAO.update(oi);
				found = true;
				//取得該訂單明細的ID
				oiid = oi.getOrderItemId();
				break;
			}
		}		
		//若不是相同產品則new新的細項VO(產品數量/會員ID/產品VO)
		if(!found){
			OrderItemVO oi = new OrderItemVO();
			oi.setMember(member);
			oi.setCount(num);
			oi.setProduct(p);
			oiid = orderItemDAO.add(oi);
		}
		return "@forebuy?oiid="+oiid;
	}
	
	public String addCart(HttpServletRequest request, HttpServletResponse response) {
		//取的產品編號查詢產品VO和所選取的數量
		int pid = Integer.parseInt(request.getParameter("pid"));
		ProductVO p = productDAO.get(pid);
		int num = Integer.parseInt(request.getParameter("num"));
		//取得Session中的會員VO
		MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		boolean found = false;
		//取出會員已新增的訂單明細,判斷產品若相同則數量進行修改
		List<OrderItemVO> ois = orderItemDAO.listByUser(member.getMemberId());
		for (OrderItemVO oi : ois) {
			if(oi.getProduct().getProductId()==p.getProductId()){
				oi.setCount(oi.getCount()+num);
				orderItemDAO.update(oi);
				found = true;
				break;
			}
		}		
		
		//若是新產品則new一個新的細項新增到資料庫
		if(!found){
			OrderItemVO oi = new OrderItemVO();
			oi.setMember(member);
			oi.setCount(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
		}
		return "%success";
	}
	public String buy(HttpServletRequest request, HttpServletResponse response){
		//取得所有訂單明細
		String[] oiids=request.getParameterValues("oiid");
		//創建一個放訂單VO的list
		List<OrderItemVO> ois = new ArrayList<>();
		float total = 0;
		//將取得到訂單明細編號轉為INT,並遍歷取出對應的訂單VO
		for (String strid : oiids) {
			int oiid = Integer.parseInt(strid);
			OrderItemVO oi= orderItemDAO.get(oiid);
			//取出訂單中的產品的價格*數量計算總金額
			total +=oi.getProduct().getPromotePrice()*oi.getCount();
			ois.add(oi);
		}
		//把所有訂單明細和金額存入轉發至結帳頁面
		request.getSession().setAttribute("ois", ois);
		request.setAttribute("total", total);
		return "buy.jsp";
	}	
	
	public String cart(HttpServletRequest request, HttpServletResponse response) {
		//獲取會員編號查詢該會員的訂單明細
		MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		List<OrderItemVO> ois = orderItemDAO.listByUser(member.getMemberId());
		request.setAttribute("ois", ois);
		return "cart.jsp";
	}	
	
	 public String changeOrderItem(HttpServletRequest request, HttpServletResponse response) {
		 	//取出會員編號,若無返回fail重新登錄
	        MemberVO member =(MemberVO) request.getSession().getAttribute("member");
	        if(null==member)
	            return "%fail";
	        //取出商品ID和數量
	        int pid = Integer.parseInt(request.getParameter("pid"));
	        int count = Integer.parseInt(request.getParameter("count"));
	        //取出訂單明細並新增
	        List<OrderItemVO> ois = orderItemDAO.listByUser(member.getMemberId());
	        for (OrderItemVO oi : ois) {
	            if(oi.getProduct().getProductId()==pid){
	                oi.setCount(count);
	                orderItemDAO.update(oi);
	                break;
	            }
	             
	        }      
	        return "%success";
	    }
	 public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response){
			MemberVO member =(MemberVO) request.getSession().getAttribute("member");
			if(null==member)
				return "%fail";

			int oiid = Integer.parseInt(request.getParameter("oiid"));
			List<OrderItemVO> ois = orderItemDAO.listByUser(member.getMemberId());
			for (OrderItemVO oi : ois) {
				if(oi.getOrderItemId()==oiid){
					orderItemDAO.delete(oiid);
					break;
				}
				
			}		
			return "%success";
		}
	 
	 public String createOrder(HttpServletRequest request, HttpServletResponse response){
		 //提交訂單
		 MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		     //取得會員編號和訂單明細
		    List<OrderItemVO> ois= (List<OrderItemVO>) request.getSession().getAttribute("ois");
		    if(ois.isEmpty())
		        return "@login.jsp";
		 
		    String address = request.getParameter("address");
		    String post = request.getParameter("post");
		    String receiver = request.getParameter("receiver");
		    String mobile = request.getParameter("mobile");
		    String userMessage = request.getParameter("userMessage");
		     
		    OrderVO order = new OrderVO();
		    String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +RandomUtils.nextInt(10000);
		 
		    order.setOrderCode(orderCode);
		    order.setAddress(address);
		    order.setPost(post);
		    order.setReceiver(receiver);
		    order.setMobile(mobile);
		    order.setUserMessage(userMessage);
		    order.setOrderDate(new Date());
		    order.setMember(member);
		    order.setStatus(OrderDAOImpl.waitPay);
		    //新增訂單和加入ORDER的PK給多方的orderItem
		    int orderPK = orderDAO.add(order);
		    order.setOrderId(orderPK);
		    
		    float total =0;
		    for (OrderItemVO oi: ois) {
		        oi.setOrder(order);
		        orderItemDAO.update(oi);
		        total+=oi.getProduct().getPromotePrice()*oi.getCount();
		    }
		     
		    return "@forealipay?oid="+orderPK+"&total="+total;
		}
	 
	 public String alipay(HttpServletRequest request, HttpServletResponse response){
		    return "alipay.jsp";
		}
	 
	 public String payed(HttpServletRequest request, HttpServletResponse response) {
		    int oid = Integer.parseInt(request.getParameter("oid"));
		    OrderVO order = orderDAO.get(oid);
		    order.setStatus(OrderDAOImpl.waitDelivery);
		    order.setPayDate(new Date());
		    new OrderDAOImpl().update(order);
		    request.setAttribute("o", order);
		    return "payed.jsp";    
		} 
	 public String bought(HttpServletRequest request, HttpServletResponse response) {
		 	//顯示我的訂單
		    MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		    
		    List<OrderVO> os= orderDAO.list(member.getMemberId(),OrderDAOImpl.delete);
		    orderItemDAO.fill(os);
		     
		    request.setAttribute("os", os);
		     
		    return "bought.jsp";       
		}
	 
	 public String confirmPay(HttpServletRequest request, HttpServletResponse response) {
			int oid = Integer.parseInt(request.getParameter("oid"));
			OrderVO o = orderDAO.get(oid);
			orderItemDAO.fill(o);
			request.setAttribute("o", o);
			return "confirmPay.jsp";		
		}
	 
	 public String orderConfirmed(HttpServletRequest request, HttpServletResponse response) {
			int oid = Integer.parseInt(request.getParameter("oid"));
			OrderVO o = orderDAO.get(oid);
			o.setStatus(OrderDAOImpl.waitReview);
			o.setConfirmDate(new Date());
			orderDAO.update(o);
			return "orderConfirmed.jsp";
		}
	 public String deleteOrder(HttpServletRequest request, HttpServletResponse response){
			int oid = Integer.parseInt(request.getParameter("oid"));
			OrderVO o = orderDAO.get(oid);
			o.setStatus(OrderDAOImpl.delete);
			orderDAO.update(o);
			return "%success";		
		}
	 public String review(HttpServletRequest request, HttpServletResponse response) {
		    int oid = Integer.parseInt(request.getParameter("oid"));
		    OrderVO o = orderDAO.get(oid);
		    orderItemDAO.fill(o);
		    ProductVO p = o.getOrderItems().get(0).getProduct();
		    List<ReviewVO> reviews = reviewDAO.list(p.getProductId());
		    productDAO.setSaleAndReviewNumber(p);
		    request.setAttribute("p", p);
		    request.setAttribute("o", o);
		    request.setAttribute("reviews", reviews);
		    return "review.jsp";       
		}
	 
	 public String doreview(HttpServletRequest request, HttpServletResponse response) {
		    int oid = Integer.parseInt(request.getParameter("oid"));
		    OrderVO o = orderDAO.get(oid);
		    o.setStatus(OrderDAOImpl.finish);
		    orderDAO.update(o);
		    int pid = Integer.parseInt(request.getParameter("pid"));
		    ProductVO p = productDAO.get(pid);
		     
		    String content = request.getParameter("content");
		     
		    content = HtmlUtils.htmlEscape(content);
		 
		    MemberVO member =(MemberVO) request.getSession().getAttribute("member");
		    ReviewVO review = new ReviewVO();
		    review.setContent(content);
		    review.setProduct(p);
		    review.setReviewDate(new Date());
		    review.setMember(member);
		    reviewDAO.add(review);
		     
		    return "@forereview?oid="+oid+"&showonly=true";    
		}
}
