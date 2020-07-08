<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<title>編輯產品屬性值</title>
<style>
.wrong{
color:red;
background:url(<%=request.getContextPath()%>/img/site/xx.png) no-repeat ;
background-position: 85% 10%;
}
.right{
background:url(<%=request.getContextPath()%>/img/site/oo.png) no-repeat  ;
background-position: 85% 10%;
}
</style>
<script>
$(function(){
	
	$("input").each(function(){
		$(this).on("keyup",function(){
			var value = $(this).val();
			var page = "admin_product_updatePropertyValue";
			var pvid = $(this).attr("pvid");
			var parentSpan = $(this).parent();
			$.post(
				    page,
				    {"value":value,"pvid":pvid},
				    function(result){
				    	if("success"==result){
				    		parentSpan.parent().addClass("wrong");
				    		parentSpan.parent().addClass("right");
			    	    	}
				    	else{
				    		parentSpan.parent().addClass("right");
				    		parentSpan.parent().addClass(" wrong");
			    	    	}
			});
		});
	});
});

</script>

<div class="workingArea">
	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分類</a></li>
	  <li><a href="admin_product_list?cid=${p.category.categoryId}">${p.category.name}</a></li>
	  <li class="active">${p.name}</li>
	  <li class="active">編輯產品屬性</li>
	</ol>
	
	<div class="editPVDiv">
		<c:forEach items="${pvs}" var="pv" varStatus="status">
			<div class="eachPV form-group">
				<div style="text-align:center">　
				<span style="padding-right:40px;" class="pvName" >${pv.property.name}</span>
				<span class="pvValue　message"　>
				<input size=6 class='pvValue form-control ' pvid="${pv.propertyValId}" type="text" value="${pv.value}">
				 </span></div>
			</div>
		</c:forEach>
	<div style="clear:both"></div>	
	</div>
	
</div>

