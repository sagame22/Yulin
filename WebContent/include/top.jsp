<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<nav class="top ">
		<a href="${contextPath}">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
			Yulin首頁
		</a>	
		
		
		<c:if test="${!empty member}">
			<a href="login.jsp">${member.name}</a>
			<a href="forelogout">退出</a>		
		</c:if>
		
		<c:if test="${empty member}">
			<a href="login.jsp">請登陸</a>
			<a href="register.jsp">免費註冊</a>		
		</c:if>


		<span class="pull-right">
			<a href="forebought">我的訂單</a>
			<a href="forecart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
			購物車<strong>${cartTotalItemNumber}</strong>件</a>		
		</span>
		
		
</nav>



