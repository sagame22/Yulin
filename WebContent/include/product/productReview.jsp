<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	
<div class="productReviewDiv" >
	<div class="productReviewTopPart">
		<a  href="#nowhere" class="productReviewTopPartSelectedLink">商品詳情</a>
		<a  href="#nowhere" class="selected">累計評價 <span class="productReviewTopReviewLinkNumber">${p.reviewCount}</span> </a>
	</div>
	
		
	<div class="productReviewContentPart">
		<c:forEach items="${reviews}" var="r">
		<div class="productReviewItem">
		
			<div class="productReviewItemDesc">
				<div class="productReviewItemContent">
					${r.content }
				</div>
				<div class="productReviewItemDate"><fmt:formatDate value="${r.reviewDate}" pattern="yyyy-MM-dd"/></div>
			</div>
			<div class="productReviewItemUserInfo">
			
				${r.member.anonymousName}<span class="userInfoGrayPart">（匿名）</span>
			</div>
			
			<div style="clear:both"></div>
		
		</div>
		</c:forEach>
	</div>

</div>
