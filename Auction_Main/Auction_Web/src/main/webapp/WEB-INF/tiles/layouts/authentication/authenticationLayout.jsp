<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application" />
<c:set var="english"><spring:message code="language.english"/></c:set>
<c:set var="arabic"><spring:message code="language.arabic"/></c:set>
<c:set var="internetDateTime" value="<%=InternetTiming.getInternetDateTime()%>" scope="application"/>
<!DOCTYPE html>
<html lang="${(pageContext.response.locale)}">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><tiles:insertAttribute name="page-title"></tiles:insertAttribute></title>
	
	<!-- Tell the browser to be responsive to screen width -->
	<meta name="_csrf" content="${_csrf.token}"/>
	
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<!--logoepap-->
	<link href="${contextPath}/resources/images/EPAP_Logo3.png" rel="shortcut icon"/>
    <link href="${contextPath}/resources/images/EPAP_Logo3.png" rel="icon"/>
    
    <!-- Bootstrap -->
    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="${contextPath}/resources/css/font-awesome.min.css" rel="stylesheet">
    
    <!-- NProgress -->
    <link href="${contextPath}/resources/css/nprogress.css" rel="stylesheet">
    
    <!-- Animate.css -->
    <link href="${contextPath}/resources/css/animate.min.css" rel="stylesheet">

    <!-- <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800" rel="stylesheet"> -->
	
	  <!--   new changes 30-11-2019  *//-->
	
	<!-- Standard Favicon -->
	<link rel="icon" type="image/x-icon" href="${contextPath}/resources/images/frontendimage/favicon.ico" />
	
	<!-- Fonts -->
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700&display=swap" rel="stylesheet"> 
	
	<!-- Library -->
	<link href="${contextPath}/resources/css/frontendcss/lib.css" rel="stylesheet">
	<!-- Custom - Common CSS -->
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/frontendcss/navigation.css">
	
	  <!--   new changes 30-11-2019  end *//-->
	
    <!-- Custom Theme Style -->
    
       <c:choose>
	  	<c:when test="${(pageContext.response.locale.displayLanguage) eq english}">
	  		<link href="${contextPath}/resources/css/custom_en.css" rel="stylesheet">
	  			<link  href="${contextPath}/resources/css/frontendcss/style-en.css" rel="stylesheet">
	  	</c:when>		  		
	  	<c:otherwise >
	  		<link href="${contextPath}/resources/css/custom_ar.css" rel="stylesheet">
	  		<link  href="${contextPath}/resources/css/frontendcss/style-ar.css"  rel="stylesheet">
	  	</c:otherwise>
	</c:choose>
    
    <!-- jQuery -->
    <script src="${contextPath}/resources/js/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/jquery.validate.min.js"></script>
    <script src="${contextPath}/resources/js/custom/validation-methods.js"></script>
    
    <!-- Bootstrap -->
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
      <script src="${contextPath}/resources/js/sockjs.min.js"></script>
	<script src="${contextPath}/resources/js/stomp.min.js"></script>

    <script type="text/javascript">
    	var contextPath = "${contextPath}";
       	$(document).ready(function(){
       		$.ajaxSetup({
       		    beforeSend: function(xhr) {
       		    	 var token = $("meta[name='_csrf']").attr("content");
       		    	 var header = $("meta[name='_csrf_header']").attr("content");
       		        xhr.setRequestHeader(header, token);
       		    }
       		});
       	});
	</script>
    
  </head>
  <body class="login">
  	<c:if test="${not empty ERRORS}">
		<div class="alert alert-danger alert-dismissible">
	 		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	 		<c:forEach items="${ERRORS}" var="e">
	 			<h4 class="title-text spacer-btm-5"><i class="icon fa fa-warning"></i> ${e}</h4>
	 		</c:forEach>
		</div>
	</c:if>
    <c:if test="${not empty ERROR}">
		<div class="alert alert-danger alert-dismissible">
	 		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	 		<h4 class="title-text"><i class="icon fa fa-warning"></i> ${ERROR}</h4>
		</div>
		<c:remove var="ERROR"/>
	</c:if>
	<c:if test="${not empty SUCCESS}">
		<div class="alert alert-success alert-dismissible">
 			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
			<h4 class="title-text"><i class="icon fa fa-check"></i> ${SUCCESS}</h4>
		</div>
	</c:if>
	<tiles:insertAttribute name="page-header"/>
	<tiles:insertAttribute name="page-menu"/>
	<tiles:insertAttribute name="page-content"/>
	<tiles:insertAttribute name="page-footer"/>
	
    <script type="text/javascript">
    	$(document).ready(function(){
    		$(".alert").delay(8000).hide(100);
    		
    		$("input[type=text], input[type=password], textarea").on("change",function(){
    			$(this).val($.trim($(this).val()));
    		});
    	});
    </script>
  </body>
</html>