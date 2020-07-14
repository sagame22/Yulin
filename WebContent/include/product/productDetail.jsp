<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	
<div class="productDetailDiv" >
	<div class="productDetailTopPart">
		<a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品詳情</a>
		<a href="#nowhere" class="productDetailTopReviewLink">累計評價 <span class="productDetailTopReviewLinkNumber">${p.reviewCount}</span> </a>
	</div>
	
	
	<div class="productParamterPart">
		<div class="productParamter">產品參數：</div>
		
		<div class="productParamterList">
			<c:forEach items="${pvs}" var="pv">
				<span>${pv.property.name}:  ${fn:substring(pv.value, 0, 10)} </span>
			</c:forEach>
		</div>
		<div style="clear:both"></div>
	</div>
	
	<div class="productDetailImagesPart">
			<c:forEach items="${p.productDetailImages}" var="pi">
				<img src="img/productDetail/${pi.imageId}.jpg">
			</c:forEach>
	</div>
</div>

