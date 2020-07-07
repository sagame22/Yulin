<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<%@ page import="com.category.model.*"%>
<%@ page import="com.property.model.*"%>
<%
	PropertyDAOImpl Cpservice = new PropertyDAOImpl();
    List<PropertyVO> list = (List<PropertyVO>)request.getAttribute("ps");
    pageContext.setAttribute("list",list);
%>
<script>
	$(function() {

		$("#addForm").submit(function() {
			if (checkEmpty("name", "屬性名稱"))
				return true;
			return false;
		});
	});
</script>

<title>屬性管理</title>


<div class="workingArea">

	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分類</a></li>
	  <li><a href="admin_property_list?cid=${c.categoryId}">${c.name}</a></li>
	  <li class="active">屬性管理</li>
	</ol>



	<div class="listDataTableDiv">
		<table
			class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>屬性名稱</th>
					<th>編輯</th>
					<th>刪除</th>
				</tr>
			</thead>
			<tbody>
<%@ include file="/page/page1.file" %>
				<c:forEach items="${ps}" var="p" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

					<tr>
						<td>${p.propertyId}</td>
						<td>${p.name}</td>
						<td><a href="admin_property_edit?id=${p.propertyId}"><span
								class="glyphicon glyphicon-edit"></span></a></td>
						<td><a deleteLink="true"
							href="admin_property_delete?id=${p.propertyId}"><span
								class=" 	glyphicon glyphicon-trash"></span></a></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
<%@ include file="/page/page2.file" %>


	<div class="panel panel-warning addDiv">
		<div class="panel-heading">新增屬性</div>
		<div class="panel-body">
			<form method="post" id="addForm" action="admin_property_add">
				<table class="addTable">
					<tr>
						<td>屬性名稱</td>
						<td><input id="name" name="name" type="text"
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
