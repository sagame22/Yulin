<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<%@ page import="com.order.model.*"%>
<%
List<OrderVO> list = (List<OrderVO>)request.getAttribute("os");
%>
<script>
$(function(){
	$("button.orderPageCheckOrderItems").click(function(){
		var oid = $(this).attr("oid");
		$("tr.orderPageOrderItemTR[oid="+oid+"]").toggle();
	});
});

</script>

<title>訂單管理</title>


<div class="workingArea">
	<h1 class="label label-info" >訂單管理</h1>
	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover1  table-condensed">
			<thead>
				<tr class="bg-primary">
					<th>ID</th>
					<th>狀態</th>
					<th>金額</th>
					<th width="100px">商品數量</th>
					<th width="100px">賣家名稱</th>
					<th>創建時間</th>
					<th>支付時間</th>
					<th>發貨時間</th>
					<th>確認收貨時間</th>
					<th width="120px">操作</th>
				</tr>
			</thead>
			<tbody>
<%@ include file="/page/page1.file" %>
				<c:forEach items="${os}" var="o" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<tr>
						<td>${o.orderId}</td>
						<td>${o.statusDesc}</td>
						<td>NT$<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2"/></td>
						<td align="center">${o.totalNumber}</td>
						<td align="center">${o.member.name}</td>
						
						<td><fmt:formatDate value="${o.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${o.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${o.confirmDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

						<td>
							<button oid=${o.orderId} class="orderPageCheckOrderItems btn btn-primary btn-xs">查看詳情</button>
							
							<c:if test="${o.status=='waitDelivery'}">
								<a href="admin_order_delivery?id=${o.orderId}">
									<button class="btn btn-primary btn-xs">發貨</button>
								</a>							
							</c:if>
						</td>
					</tr>
					<tr class="orderPageOrderItemTR"  oid=${o.orderId}>
						<td colspan="10" align="center">
							
							<div  class="orderPageOrderItem">
								<table width="800px" align="center" class="orderPageOrderItemTable">
									<c:forEach items="${o.orderItems}" var="oi">
										<tr>
											<td align="left">
												<img width="40px" height="40px" src="img/productSingle/${oi.product.firstProductImage.imageId}.jpg">
											</td>	
											
											<td>
												<a href="foreproduct?pid=${oi.product.productId}">
													<span>${oi.product.name}</span>
												</a>											
											</td>
											<td align="right">
											
												<span class="text-muted">${oi.count}個</span>												
											</td>
											<td align="right">
											
												<span class="text-muted">單價：NT${oi.product.promotePrice}</span>												
											</td>

										</tr>
									</c:forEach>
								
								</table>
							</div>
						
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<%@ include file="/page/page2.file" %>	

	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
