<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<%@ page import="com.category.model.*"%>
<%
	CategoryDAOImpl ct = new CategoryDAOImpl();
    List<CategoryVO> list = ct.list();
    pageContext.setAttribute("list",list);
%>
<script>
$(function(){
	
	$("#addForm").submit(function(){
		if(!checkEmpty("name","分類名稱"))
			return false;
		if(!checkEmpty("categoryPic","分類圖片"))
			return false;
		return true;
	});
});

</script>

<title>分類管理</title>


<div class="workingArea">
	<h1 class="label label-info" >分類管理</h1>
	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>圖片</th>
					<th>分類名稱</th>
 					<th>屬性管理</th> 
<!-- 					<th>產品管理</th> -->
					<th>編輯</th>
					<th>刪除</th>
				</tr>
			</thead>
			<tbody>
<%@ include file="/page/page1.file" %>
				<c:forEach items="${list}" var="c" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
				<tr>
					<td>${c.categoryId}</td>
					<td><img height="40px" src="<%=request.getContextPath()%>/img/category/${c.categoryId}.jpg"></td>
					<td>${c.name}</td>
					 	
 					<td><a href="admin_property_list?cid=${c.categoryId}"><span class="glyphicon glyphicon-th-list"></span></a></td>					 
					<td><a href="admin_category_edit?categoryId=${c.categoryId}"><span class="glyphicon glyphicon-edit"></span></a></td>
					<td><a deleteLink="true" href="admin_category_delete?categoryId=${c.categoryId}"><span class=" 	glyphicon glyphicon-trash"></span></a></td>
 <!--					<td><a href="admin_product_list?cid=${c.categoryId}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>					  --> 
	
				</tr>
				</c:forEach>
				

			</tbody>
		</table>
	</div>
	
<%@ include file="/page/page2.file" %>

	
	
	<div class="panel panel-warning addDiv">
	  <div class="panel-heading">新增分類</div>
	  <div class="panel-body">
	    	<form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data" >
	    		<table class="addTable">
	    			<tr>
	    				<td>分類名稱</td>
	    				<td><input  id="name" name="cname" type="text" class="form-control" ></td>
	    			</tr>
	    			<tr>
	    				<td>分類圖片</td>
	    				<td>
	    				
	    				<input id="categoryPic" type="file" name="filepath"
				onchange="document.getElementById('blah').src = window.URL.createObjectURL(this.files[0]);
     					  var img = document.querySelector('#blah');
     					  img.style.visibility = 'visible';">
	    				 
	    				</td>
	    			</tr>
	    			<tr class="submitTR">
	    				<td colspan="2" align="center">
	    					<button type="submit" class="btn btn-success">提 交</button>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>

	  </div>
	</div>
	
</div>


<%@include file="../include/admin/adminFooter.jsp"%>