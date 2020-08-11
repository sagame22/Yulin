<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="reviewDiv">
	<div class="reviewProductInfoDiv">
		<div class="reviewProductInfoImg"><img width="400px" height="400px" src="img/productSingle/${p.firstProductImage.imageId}.jpg"></div>
		<div class="reviewProductInfoRightDiv">
			<div class="reviewProductInfoRightText">
				${p.name}
			</div>
			<table class="reviewProductInfoTable">
				<tr>
					<td width="75px">價格:</td>
					<td><span class="reviewProductInfoTablePrice">NT$<fmt:formatNumber type="number" value="${p.orignalPrice}" /></span> 元 </td>
				</tr>
				<tr>
					<td>配送</td>
					<td>快遞: 0元</td>
				</tr>
				<tr>
					<td>月銷量:</td>
					<td><span class="reviewProductInfoTableSellNumber">${p.saleCount}</span> 件</td>
				</tr>
			</table>
			
			<div class="reviewProductInfoRightBelowDiv">
				<span class="reviewProductInfoRightBelowImg"><img1 src="img/site/reviewLight.png"></span>
				<span class="reviewProductInfoRightBelowText" >現在查看的是 您所購買的商品訊息
				<fmt:formatDate value="${o.orderDate}" pattern="yyyy年MM月dd"/>下單購買了此商品 </span>
			
			</div>
		</div>
		<div style="clear:both"></div>
	</div>
	<div class="reviewStasticsDiv">
		<div class="reviewStasticsLeft">
				<div class="reviewStasticsLeftTop"></div>
				<div class="reviewStasticsLeftContent">累計評價 <span class="reviewStasticsNumber"> ${p.reviewCount}</span></div>
				<div class="reviewStasticsLeftFoot"></div>
		</div>
		<div class="reviewStasticsRight">
			<div class="reviewStasticsRightEmpty"></div>
			<div class="reviewStasticsFoot"></div>
		</div>
	</div>		
	
	<c:if test="${param.showonly==true}">
	<div class="reviewDivlistReviews">
		<c:forEach items="${reviews}" var="r">
			<div class="reviewDivlistReviewsEach">
				<div class="reviewDate"><fmt:formatDate value="${r.reviewDate}" pattern="yyyy-MM-dd"/></div>
				<div class="reviewContent">${r.content}</div>
				<div class="reviewUserInfo pull-right">${r.member.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
			</div>
		</c:forEach>
	</div>
	</c:if>
	
	<c:if test="${param.showonly!=true}">
		<div class="makeReviewDiv">
		<form method="post" action="foredoreview">
			<div class="makeReviewText">請提供你的建議,是給我們的最大支持！</div>
			<table class="makeReviewTable">
				<tr>
					<td class="makeReviewTableFirstTD">評價商品</td>
					<td><textarea name="content"></textarea></td>
				</tr>
			</table>
			<div class="makeReviewButtonDiv">
				<input type="hidden" name="oid" value="${o.orderId}">
				<input type="hidden" name="pid" value="${p.productId}">
				<button class="btn" type="submit">提 交</button>
			</div>
		</form>
		</div>	
	</c:if>

</div>