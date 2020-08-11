<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="aliPayPageDiv">
	<div class="aliPayPageLogo">
		<img class="pull-left" src="img/site/simpleLogo.png">
		<div style="clear:both"></div>
	</div>
	
	<div>
		<span class="confirmMoneyText">付款（元）</span>
		<span class="confirmMoney">
		NT$<fmt:formatNumber type="number" value="${param.total}" />元</span>
		
	</div>
	<div>
		<img class="aliPayImg" src="img/site/alipay2wei.png">
	</div>

	
	<div>
		<a href="forepayed?oid=${param.oid}&total=${param.total}"><button class="confirmPay">確認付款</button></a>
	</div>

</div>