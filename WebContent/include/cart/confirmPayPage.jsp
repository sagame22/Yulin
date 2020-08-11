<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="confirmPayPageDiv">
	<div class="confirmPayImageDiv">
		<img src="img/site/comformPayFlow.png">
		<div  class="confirmPayTime1">
			<fmt:formatDate value="${o.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
		<div  class="confirmPayTime2">
			<fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/> 
		</div>
		<div class="confirmPayTime3">
			<fmt:formatDate value="${o.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
		

	</div>
	<div class="confirmPayOrderInfoDiv">
		<div class="confirmPayOrderInfoText">我已收到貨</div>
	</div>
	<div class="confirmPayOrderItemDiv">
		<div class="confirmPayOrderItemText">訂單訊息</div>
		<table class="confirmPayOrderItemTable">
			<thead>
				<th colspan="2">產品</th>		
				<th width="120px">單價</th>		
				<th width="120px">數量</th>		
				<th width="120px">商品總價 </th>		
				<th width="120px">運費</th>		
			</thead>
			<c:forEach items="${o.orderItems}" var="oi">
				<tr>
					<td><img width="50px" src="img/productSingle_middle/${oi.product.firstProductImage.imageId}.jpg"></td>
					<td class="confirmPayOrderItemProductLink">
						<a href="#nowhere">${oi.product.name}</a>
					</td>
					<td>NT$<fmt:formatNumber type="number" value="${oi.product.orignalPrice}" />元</td>
					<td>1</td>
					<td><span class="conformPayProductPrice">NT$<fmt:formatNumber type="number" value="${oi.product.promotePrice}"/>元</span></td>
					<td><span>快遞 ： 0.00 </span></td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="confirmPayOrderItemText pull-right">
			實付： <span class="confirmPayOrderItemSumPrice">NT$<fmt:formatNumber type="number" value="${o.total}" />元</span>
		</div>
		
		
	</div>
	<div class="confirmPayOrderDetailDiv">
		
		<table class="confirmPayOrderDetailTable">
			<tr>
				<td>訂單編號：</td>
				<td>${o.orderCode} <img width="23px" ></td>
			</tr>
			<tr>
				<td>賣家暱稱：</td>
				<td>Yulin時裝 </td>
			</tr>
			<tr>
				<td>收貨訊息： </td>
				<td>${o.address}，${o.receiver}， ${o.mobile}，${o.post} </td>
			</tr>
			<tr>
				<td>成交時間：</td>
				<td><fmt:formatDate value="${o.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</table>
		
	</div>
	<div class="confirmPayButtonDiv">
		<div class="confirmPayWarning">收到貨物後請確認產品是否正確！</div>
		<a href="foreorderConfirmed?oid=${o.orderId}"><button class="confirmPayButton">確認</button></a>
	</div>
</div>