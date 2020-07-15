<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	

<div class="categoryMenu">
	
		<c:forEach items="${cs}" var="c" varStatus="st">
			<c:if test="${st.count<=4}">
				<span>
				<a href="forecategory?cid=${c.categoryId}">
					${c.name}
				</a></span>			
			</c:if>
		</c:forEach>
	</div>
