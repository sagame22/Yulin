<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<%@ page import="com.member.model.*"%>
<%
    List<MemberVO> list = (List<MemberVO>)request.getAttribute("member");
	pageContext.setAttribute("list",list);
%>
<script>
</script>

<title>用戶管理</title>


<div class="workingArea">
	<h1 class="label label-info" >用戶管理</h1>

	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>用戶名稱</th>
				</tr>
			</thead>
<%@ include file="/page/page1.file" %>			
			<tbody>
				<c:forEach items="${member}" var="m" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
				<tr>
					<td>${m.id}</td>
					<td>${m.name}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<%@ include file="/page/page2.file" %>	
	
	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
