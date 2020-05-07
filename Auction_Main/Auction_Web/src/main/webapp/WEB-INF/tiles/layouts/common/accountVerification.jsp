<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="english"><spring:message code="language.english"/></c:set>
<c:set var="arabic"><spring:message code="language.arabic"/></c:set>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <title>EPAP | Confirmation </title>
    
    <!-- Bootstrap -->
    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>
    
    <!-- Font Awesome -->
    <link href="${contextPath}/resources/css/font-awesome.min.css" rel="stylesheet"/>
    
    <!-- NProgress -->
    <link href="${contextPath}/resources/css/nprogress.css" rel="stylesheet"/>
    
    <!-- Animate.css -->
    <link href="${contextPath}/resources/css/animate.min.css" rel="stylesheet"/>

     <!-- Custom Theme Style -->
    <c:choose>
	  	<c:when test="${(pageContext.response.locale.displayLanguage) eq english}">
	  		<link href="${contextPath}/resources/css/backend-custom_en.css" rel="stylesheet">
	  	</c:when>		  		
	  	<c:otherwise >
	  		<link href="${contextPath}/resources/css/backend-custom_ar.css" rel="stylesheet">
	  	</c:otherwise>
	</c:choose>
    
    <!-- jQuery -->
    <script src="${contextPath}/resources/js/jquery.min.js"></script>
    
    <!-- Bootstrap -->
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>

  </head>
  <body class="nav-md">
	<div class="container body">
	  <div class="main_container">
	    <div class="col-md-12">
	      <div class="col-middle">
	        <div class="text-center">
	          <c:choose>
				<c:when test="${status}">
					<h1 style="color: #333;">${message}</h1>
				</c:when>
				<c:otherwise>
					<h1 style="color: #F00;">${message}</h1>
				</c:otherwise>
			  </c:choose>
			  <a href="${contextPath}/auth/login" class="btn btn-warning btn-lg"> <spring:message code="accountverification.a.clicktologin"/> </a>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
  </body>
</html>