<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="categoryMenu">
		<c:forEach items="${cs}" var="c">
			<div cid="${c.categoryId}" class="eachCategory">
				<span class="glyphicon glyphicon-link"></span>
				<a href="forecategory?cid=${c.categoryId}">
					${c.name}
				</a>
			</div>
		</c:forEach>
	</div>  