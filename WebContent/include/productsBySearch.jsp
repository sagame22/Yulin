<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="searchProducts">
	
	<c:forEach items="${ps}" var="p">
		<div class="productUnit" price="${p.promotePrice}">
			<a href="foreproduct?pid=${p.productId}">
				<img class="productImage" src="img/productSingle/${p.firstProductImage.imageId}.jpg">
			</a>
			<span class="productPrice">NT$<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/></span>
			<a class="productLink" href="foreproduct?pid=${p.productId}">
			 ${fn:substring(p.name, 0, 50)}
			</a>
			
			<a class="tmallLink" href="foreproduct?pid=${p.productId}">Yulin專賣</a>

			<div class="show1 productInfo">
				<span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}</span></span>
				<span class="productReview">評價<span class="productReviewNumber">${p.reviewCount}</span></span>
				<span class="wangwang"><img src="img/site/wangwang.png"></span>
			</div>
			
		</div>
	</c:forEach>
	<c:if test="${empty ps}">
		<div class="noMatch">沒有滿足條件的產品<div>
	</c:if>
	
		<div style="clear:both"></div>
</div>