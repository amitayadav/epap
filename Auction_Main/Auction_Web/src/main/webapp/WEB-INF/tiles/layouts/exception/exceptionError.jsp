<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="english"><spring:message code="language.english"/></c:set>
<c:set var="arabic"><spring:message code="language.arabic"/></c:set>
<!DOCTYPE>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="exceptionError.title.EPAPapplicationerror" /></title>
    
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
	  		<link href="${contextPath}/resources/css/custom_en.css" rel="stylesheet">
	  	</c:when>		  		
	  	<c:otherwise >
	  		<link href="${contextPath}/resources/css/custom_ar.css" rel="stylesheet">
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
        <!-- page content -->
        <div class="col-md-12">
          <div class="col-middle">
          	<div class="text-center">
          		<img src="${contextPath}/resources/images/EPAP_Logo3.png" width="300"/>
          	</div><br/>
            <h1 class="text-center"><spring:message code="exceptionError.h1.applicationerror" /></h1>
            <c:if test="${not empty ERROR}">
              <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text"><i class="icon fa fa-warning"></i> ${ERROR}</h4>
              </div>
            </c:if>
            <div class="error-page">
              <h2 class="headline text-theme-color"><i class="fa fa-warning text-theme-color"></i></h2>
              <div class="error-content">
                <h1 class="text-theme-color"><spring:message code="exceptionError.h1.oopssomethingwentwrong" /></h1>
                <p> <spring:message code="exceptionError.p.sorry" /></p>
                <p> <spring:message code="exceptionError.p.help" /></p>
                <div>
                  <p><b><spring:message code="exceptionError.b.url" /></b></p>
                  <pre>${requestedUrl}</pre>
                </div>
                <div>
                  <p><b><spring:message code="exceptionError.b.error" /></b></p>
                  <pre>${exception }</pre>
                </div>
                <div>
                  <p> &nbsp;</p>
                  <b><a id="button" href="#"><spring:message code="exceptionError.a.details" /></a></b>
                  <p> &nbsp;</p>
                  <div id="stacktrace" >
                    <pre> ${exception_stack_trace }</pre>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /page content -->
      </div>
    </div>
    <script>
      $(document).ready(function() {
      	$( "#stacktrace" ).hide();
      	$( "#button" ).click(function(e) {
      		e.preventDefault();
      	    $( "#stacktrace" ).toggle();
      	});
      });
    </script>
  </body>
</html>