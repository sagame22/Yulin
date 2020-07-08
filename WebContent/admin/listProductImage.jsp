<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<%@ page import="com.productimage.model.*"%>
<%@ page import="com.product.model.*"%>
<%
	ProductImageDAOImpl pti = new ProductImageDAOImpl();
    List<ProductImageVO> list = (List)request.getSession().getAttribute("pisSingle");
    List<ProductImageVO> list2 = (List)request.getSession().getAttribute("pisDetail");
%>
<script>
$(function(){
	$(".addFormSingle").submit(function(){
		if(checkEmpty("filepathSingle","圖片文件")){
			$("#filepathSingle").value("");
			return true;
		}
		return false;
	});
	$(".addFormDetail").submit(function(){
		if(checkEmpty("filepathDetail","圖片文件"))
			return true;
		return false;
	});
});

</script>

<title>產品圖片管理</title>


<div class="workingArea">
		<ol class="breadcrumb">
		  <li><a href="admin_category_list">所有分類</a></li>
		  <li><a href="admin_product_list?cid=${p.category.categoryId}">${p.category.name}</a></li>
		  <li class="active">${p.name}</li>
		  <li class="active">產品圖片管理</li>
		</ol>

	<table class="addPictureTable" align="center">
		<tr>
			<td class="addPictureTableTD">
			  <div>
				<div class="panel panel-warning addPictureDiv">
					<div class="panel-heading">新增產品<b class="text-primary"> 單個 </b>圖片</div>
					  <div class="panel-body">
					    	<form method="post" class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
					    		<table class="addTable">
					    			<tr>
					    				<td>請選擇本地圖片 尺寸400X400 為佳</td>
					    			</tr>
					    			<tr>
					    				<td>
					    					<input id="filepathSingle" type="file" name="filepath" />
					    				</td>
					    			</tr>
					    			<tr class="submitTR">
					    				<td align="center">
					    					<input type="hidden" name="type" value="type_single" />
					    					<input type="hidden" name="pid" value="${p.productId}" />
					    					<button type="submit" class="btn btn-success">提 交</button>
					    				</td>
					    			</tr>
					    		</table>
					    	</form>
					  </div>
				  </div>			  
			  	<table class="table table-striped table-bordered table-hover  table-condensed"> 
					<thead>
						<tr class="success">
							<th>ID</th>
							<th>產品單個圖片請縮圖</th>
							<th>删除</th>
						</tr>
					</thead>
					<tbody>
<%@ include file="/page/page1.file" %>
						<c:forEach items="${pisSingle}" var="pi" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
								<td>${pi.imageId}</td>
								<td>
								<a title="點擊查看原圖" href="img/productSingle/${pi.imageId}.jpg"><img height="50px" src="img/productSingle/${pi.imageId}.jpg"></a>	
								</td>
								<td><a deleteLink="true"
									href="admin_productImage_delete?id=${pi.imageId}"><span
										class=" 	glyphicon glyphicon-trash"></span></a></td>
		
							</tr>
						</c:forEach>
					</tbody>	  
				</table>	
<%@ include file="/page/page2.file" %>						
			  </div>			
			</td>
			<td class="addPictureTableTD">
			  <div>
			  	
				<div class="panel panel-warning addPictureDiv">
					<div class="panel-heading">新增產品<b class="text-primary"> 詳情 </b>圖片</div>
					  <div class="panel-body">
					    	<form method="post" class="addFormDetail" action="admin_productImage_add" enctype="multipart/form-data">
					    		<table class="addTable">
					    			<tr>
					    				<td>請選擇本地圖片 寬度790 為佳</td>
					    			</tr>
					    			<tr>
					    				<td>
					    					<input id="filepathDetail"  type="file" name="filepath" />
					    				</td>
					    			</tr>
					    			<tr class="submitTR">
					    				<td align="center">
					    					<input type="hidden" name="type" value="type_detail" />
					    					<input type="hidden" name="pid" value="${p.productId}" />
					    					<button type="submit" class="btn btn-success">提 交</button>
					    				</td>
					    			</tr>
					    		</table>
					    	</form>
					  </div>
				  </div>
				  <table class="table table-striped table-bordered table-hover  table-condensed"> 
						<thead>
							<tr class="success">
								<th>ID</th>
								<th>產品詳情圖片請縮圖</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>
<%@ include file="/page/page3.file" %>
							<c:forEach items="${pisDetail}" var="pi" begin="<%=pageIndex2%>" end="<%=pageIndex2+rowsPerPage2-1%>">
								<tr>
									<td>${pi.imageId}</td>
									<td>
										<a title="點擊查看原圖" href="img/productDetail/${pi.imageId}.jpg"><img height="50px" src="img/productDetail/${pi.imageId}.jpg"></a>
									</td>
									<td><a deleteLink="true"
										href="admin_productImage_delete?id=${pi.imageId}"><span
											class=" 	glyphicon glyphicon-trash"></span></a></td>
			
								</tr>
							</c:forEach>
						</tbody>	  
					</table>					  		
<%@ include file="/page/page4.file" %>
			  </div>			
			</td>
		</tr>
	</table>
	
	

	
</div>

