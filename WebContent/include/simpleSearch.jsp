<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<div >
	<a href="${contextPath}">
		<img id="simpleLogo" class="simpleLogo" src="img/site/YL.png">	
	</a>

	<form action="foresearch" method="post" >	
	<div class="searchDiv pull-right">
		<input type="text" placeholder="" name="keyword">
		<button class="btn" type="submit">搜尋</button>
		<div class="searchBelow">
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="${st.count>=8 and st.count<=11}">
					<span>
						<a href="forecategory?cid=${c.categoryId}">
							${c.name}
						</a>
						<c:if test="${st.count!=11}">				
							<span>|</span>				
						</c:if>
					</span>			
				</c:if>
			</c:forEach>			
		</div>
	</div>
	</form>
	<div style="clear:both"></div>
</div>