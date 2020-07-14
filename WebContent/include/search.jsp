<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<div class="row">
	<div class="firstPage col-md-3">
	<a href="${contextPath}">
		<img id="logo" src="img/site/YL.png" class="logo">
	</a>
	</div>
	<form action="foresearch" method="post" >	
		<div class="searchDiv col-md-9">
			<input name="keyword" type="text" placeholder="時尚男裝 " class="keyword">
			<button  type="submit" class="btn btn-primary">搜尋</button>
			<div class="searchBelow">
				<c:forEach items="${cs}" var="c" varStatus="st">
					<c:if test="${st.count>=5 and st.count<=8}">
						<span>
							<a href="forecategory?cid=${c.categoryId}">
								${c.name}
							</a>
							<c:if test="${st.count!=8}">				
								<span>|</span>				
							</c:if>
						</span>			
					</c:if>
				</c:forEach>		
			</div>
		</div>
	</form>	
</div>