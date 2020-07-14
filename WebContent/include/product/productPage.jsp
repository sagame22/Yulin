<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<title>官網 ${p.name}</title>
<div class="categoryPictureInProductPageDiv">
	<img class="categoryPictureInProductPage" src="img/category/${p.category.categoryId}.jpg">
</div>

<div class="productPageDiv">

	<%@include file="imgAndInfo.jsp" %>
	
	<%@include file="productReview.jsp" %>
	
	<%@include file="productDetail.jsp" %>
</div>