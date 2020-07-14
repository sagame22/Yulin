<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


<script>
$(function(){
	
	<c:if test="${!empty msg}">
	$("span.errorMessage").html("${msg}");
	$("div.loginErrorMessageDiv").show();		
	</c:if>
	
	$("form.loginForm").submit(function(){
		if(0==$("#name").val().length||0==$("#password").val().length){
			$("span.errorMessage").html("請輸入帳號密碼");
			$("div.loginErrorMessageDiv").show();			
			return false;
		}
		return true;
	});
	
	$("form.loginForm input").keyup(function(){
		$("div.loginErrorMessageDiv").hide();	
	});
	
	
	
	var left = window.innerWidth/2+162;
	$("div.loginSmallDiv").css("left",left);
})
</script>


<div id="loginDiv" style="position: relative">

	<div class="simpleLogo">
		<a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
	</div>

	
	<img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
	
	<form class="loginForm" action="forelogin" method="post">
		<div id="loginSmallDiv" class="loginSmallDiv">
			<div class="loginErrorMessageDiv">
				<div class="alert alert-danger" >
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				  	<span class="errorMessage"></span>
				</div>
			</div>
				
			<div class="login_acount_text">會員登錄</div>
			<div class="loginInput " >
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-user"></span>
				</span>
				<input id="name" name="name" placeholder="手機/會員名/郵箱" type="text">			
			</div>
			
			<div class="loginInput " >
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-lock"></span>
				</span>
				<input id="password" name="password" type="password" placeholder="密碼" type="text">
			</div>
			
			
			
			<div>
				<a class="notImplementLink" href="#nowhere">忘記登錄密碼</a> 
				<a href="register.jsp" class="pull-right">免費註冊</a> 
			</div>
			<div style="margin-top:20px">
				<button class="btn btn-block redButton" type="submit">登錄</button>
			</div>
		</div>	
	</form>


</div>	