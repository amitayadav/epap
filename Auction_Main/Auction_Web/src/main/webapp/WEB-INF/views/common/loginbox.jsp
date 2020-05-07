<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="form-group text-center">
	<h3 class="text-error"><spring:message code="loginbox.msg.sessionexpired"/></h3>
	<a href="${pageContext.request.contextPath}/auth/login" id="loginBtn" class="btn btn-warning"><spring:message code="loginbox.lbl.login"/></a>
</div>
<script type="text/javascript">
	setTimeout(function(){document.getElementById("loginBtn").click();},1000);
</script>