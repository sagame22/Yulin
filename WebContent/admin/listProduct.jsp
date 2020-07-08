<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<%@ page import="com.product.model.*"%>
<%
	ProductDAOImpl pt = new ProductDAOImpl();
    List<ProductVO> list = (List<ProductVO>)request.getSession().getAttribute("ps2");
%>
<script>
	$(function() {
		$("#addForm").submit(function() {
			if (!checkEmpty("name", "產品名稱"))
				return false;
// 			if (!checkEmpty("subTitle", "小標"))
// 				return false;
			if (!checkNumber("orignalPrice", "原價格"))
				return false;
			if (!checkNumber("promotePrice", "優惠價格"))
				return false;
			if (!checkInt("stock", "庫存"))
				return false;
			return true;
		});
	});
</script>

<title>產品管理</title>


<div class="workingArea">

	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分類</a></li>
	  <li><a href="admin_product_list?cid=${c.categoryId}">${c.name}</a></li>
	  <li class="active">產品管理</li>
	</ol>



	<div class="listDataTableDiv">
		<table
			class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="bg-primary">
					<th>ID</th>
					<th>圖片</th>
					<th>產品名稱</th>
					<th>產品小標題</th>
					<th width="80px">原價格</th>
					<th width="80px">優惠價格</th>
					<th width="80px">庫存數量</th>
					<th width="80px">圖片管理</th>
					<th width="80px">設置屬性</th>
					<th width="42px">編輯</th>
					<th width="42px">删除</th>
				</tr>
			</thead>
			<tbody>
<%@ include file="/page/page1.file" %>
				<c:forEach items="${ps2}" var="p" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<tr>
						<td>${p.productId}</td>
						<td>
						
						<c:if test="${!empty p.firstProductImage}">
							<img width="40px" src="img/productSingle/${p.firstProductImage.imageId}.jpg">
						</c:if>
						
						</td>
						<td>${p.name}</td>
						<td>${p.subTitle}</td>
						<td>${p.orignalPrice}</td>
						<td>${p.promotePrice}</td>
						<td>${p.stock}</td>
						<td><a href="admin_productImage_list?pid=${p.productId}"><span
								class="glyphicon glyphicon-picture"></span></a></td>
						<td><a href="admin_product_editPropertyValue?id=${p.productId}"><span
								class="glyphicon glyphicon-th-list"></span></a></td>
						
						<td><a href="admin_product_edit?id=${p.productId}"><span
								class="glyphicon glyphicon-edit"></span></a></td>
						<td><a deleteLink="true"
							href="admin_product_delete?id=${p.productId}"><span
								class=" 	glyphicon glyphicon-trash"></span></a></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<%@ include file="/page/page2.file" %>


	<div class="panel panel-warning addDiv">
		<div class="panel-heading">新增產品</div>
		<div class="panel-body">
			<form method="post" id="addForm" action="admin_product_add">
				<table class="addTable">
					<tr>
						<td>產品名稱</td>
						<td><input id="name" name="name" type="text" 
							class="form-control"></td>
					</tr>
					<tr>
						<td>產品小標題</td>
						<td><input id="subTitle" name="subTitle" type="text"
							class="form-control"></td>
					</tr>
					<tr>
						<td>原價格</td>
						<td><input id="orignalPrice" value="99.98" name="orignalPrice" type="text"
							class="form-control"></td>
					</tr>
					<tr>
						<td>優惠價格</td>
						<td><input id="promotePrice"  value="19.98" name="promotePrice" type="text"
							class="form-control"></td>
					</tr>
					<tr>
						<td>庫存</td>
						<td><input id="stock"  value="99" name="stock" type="text"
							class="form-control"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="cid" value="${c.categoryId}">
							<button type="submit" class="btn btn-success">提 交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</div>

<%@include file="../include/admin/adminFooter.jsp"%>
