<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>



<title>編輯分類</title>


<script>
$(function(){
	
	$("#editForm").submit(function(){
		if(!checkEmpty("name","分類名稱"))
			return false;

		return true;
	});
});

</script>

<div class="workingArea">

	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分類</a></li>
	  <li class="active">編輯分類</li>
	</ol>

	<div class="panel panel-warning editDiv">
	  <div class="panel-heading">編輯分類</div>
	  <div class="panel-body">
	    	<form method="post" id="editForm" action="admin_category_update"  enctype="multipart/form-data">
	    		<table class="editTable">
	    			<tr>
	    				<td>分類名稱</td>
	    				<td><input  id="name" name="name" value="${c.name}" type="text" class="form-control"></td>
	    			</tr>
	    			<tr>
	    				<td>分類圖片</td>
	    				<td>
	    					<input id="categoryPic" accept="image/*" type="file" name="filepath" />
	    				</td>
	    			</tr>	    			
	    			<tr class="submitTR">
	    				<td colspan="2" align="center">
	    					<input type="hidden" name="id" value="${c.categoryId}">
	    					<button type="submit" class="btn btn-success">提 交</button>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
	  </div>
	</div>	
</div>