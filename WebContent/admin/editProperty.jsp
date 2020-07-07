<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<title>編輯屬性</title>

<div class="workingArea">
	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分類</a></li>
	  <li><a href="admin_property_list?cid=${p.category.categoryId}">${p.category.name}</a></li>
	  <li class="active">編輯屬性</li>
	</ol>

	<div class="panel panel-warning editDiv">
		<div class="panel-heading">編輯屬性</div>
		<div class="panel-body">
			<form method="post" id="editForm" action="admin_property_update">
				<table class="editTable">
					<tr>
						<td>屬性名稱</td>
						<td><input id="name" name="name" value="${p.name}"
							type="text" class="form-control"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
						<input type="hidden" name="id" value="${p.propertyId}">
						<input type="hidden" name="cid" value="${p.category.categoryId}">
						<button type="submit" class="btn btn-success">提 交</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>