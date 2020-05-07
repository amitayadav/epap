<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.auction.commons.constant.CommonConstants"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>
<c:set var="english"><spring:message code="language.english"/></c:set>
<c:set var="arabic"><spring:message code="language.arabic"/></c:set>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="application"/>
<c:set var="internetDateTime" value="<%=InternetTiming.getInternetDateTime()%>" scope="application"/>
<c:set var="internetTimeZone" value="<%=InternetTiming.getApplicationTimeZone()%>" scope="request"/>
<html lang="${(pageContext.response.locale)}" >
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
    <meta name="viewport" content="width=1024">
    <title><tiles:insertAttribute name="page-title"/></title>
    
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="_csrf" content="${_csrf.token}"/>
    
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    
    <link href="${contextPath}/resources/images/EPAP_Logo3.png" rel="shortcut icon"/>
    <link href="${contextPath}/resources/images/EPAP_Logo3.png" rel="icon"/>
    
    <!-- Bootstrap -->
    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/dataTables.bootstrap.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/buttons.bootstrap.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/fixedHeader.bootstrap.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/responsive.bootstrap.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/scroller.bootstrap.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/fileinput.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/daterangepicker.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/bootstrap-datetimepicker.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/bootstrap-datepicker.min.css" rel="stylesheet"/>
    
    <!-- Font Awesome -->
    <link href="${contextPath}/resources/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/font-awesome.icon.min.css" rel="stylesheet"/>
    
    <!-- NProgress -->
    <link href="${contextPath}/resources/css/nprogress.css" rel="stylesheet"/>
    
    <link href="${contextPath}/resources/css/select2.min.css" rel="stylesheet"/>
    
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
    <script src="${contextPath}/resources/js/jquery.validate.min.js"></script>
    <script src="${contextPath}/resources/js/custom/validation-methods.js"></script>
    <script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
    
    <!-- Bootstrap -->
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    
    <%--     <script src="${contextPath}/resources/js/buttons.bootstrap.min.js"></script>
      <script src="${contextPath}/resources/js/buttons.flash.min.js"></script>
      <script src="${contextPath}/resources/js/buttons.html5.min.js"></script> --%>
    <script src="${contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/dataTables.fixedHeader.min.js"></script>
    <script src="${contextPath}/resources/js/dataTables.responsive.min.js"></script>
    <script src="${contextPath}/resources/js/dataTables.scroller.min.js"></script>
    <script src="${contextPath}/resources/js/moment.min.js"></script>
    <script src="${contextPath}/resources/js/datetime-moment.js"></script>
    <script src="${contextPath}/resources/js/moment-timezone.js"></script>
    <script src="${contextPath}/resources/js/moment-timezone-with-data.js"></script>
    <%-- <script src="${contextPath}/resources/js/moment-with-locales.js"></script> --%>
    <script src="${contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
     <script src="${contextPath}/resources/js/bootstrap-datepicker.min.js"></script>
    <%-- <script src="${contextPath}/resources/js/jszip.min.js"></script>
    <script src="${contextPath}/resources/js/pdfmake.min.js"></scrip --%>
    <script src="${contextPath}/resources/js/responsive.bootstrap.js"></script>
    <script src="${contextPath}/resources/js/vfs_fonts.js"></script>
    <script src="${contextPath}/resources/js/fileinput.js"></script>
    <script src="${contextPath}/resources/js/daterangepicker.js"></script>
    <script src="${contextPath}/resources/js/sockjs.min.js"></script>
	<script src="${contextPath}/resources/js/stomp.min.js"></script>
	<script src="${contextPath}/resources/js/select2.min.js"></script>
	
	<!-- Highcharts -->
    <link rel="stylesheet" href="${contextPath}/resources/css/highcharts.css" />
   <!--<script src="${contextPath}/resources/js/highcharts.js"></script>  -->
    <script src="https://code.highcharts.com/stock/highstock.js"></script>
    <script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/stock/modules/data.js"></script>
    
    <script type="text/javascript">
      var contextPath = "${contextPath}";
      var requestURL = "${requestURL}";
      var internetTimeZone = "${internetTimeZone}";
      var selectedLocale = "${(pageContext.response.locale)}";
      var currentTimeInterval = 0;
      var languageList = {};
      //          fahad: correct === error
      if("${CommonConstants.ARABIC}" === "${(pageContext.response.locale)}"){
//      if("${CommonConstants.ARABIC}" == "${(pageContext.response.locale)}"){
    	  languageList = {
   			  /* "sEmptyTable": "", */
//                          fahad: change start to end text
   			  "sInfo": " _START_ إلى _END_ من  _TOTAL_ ",
//   			  "sInfo": "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل",
   			  "sInfoEmpty": "0 إلى 0 من 0",
//   			  "sInfoEmpty": "يعرض 0 إلى 0 من أصل 0 سجل",
   			  "sInfoFiltered": "(منتقاة من مجموع _MAX_ مُدخل)",
   			  "sInfoPostFix": "",
   			  "sInfoThousands": ",",
   			  "sLengthMenu": "أظهر _MENU_ سطور",
   			  "sLoadingRecords": "جار التحميل...",
   			  "sProcessing": "جارٍ التحميل...",
   			  "sSearch": "ابحث:",
   			  "sZeroRecords": "لم يعثر على أية سجلات",
   			  "oPaginate": {
   			    "sFirst": "الأول",
   			    "sPrevious": "السابق",
   			    "sNext": "التالي",
   			    "sLast": "الأخير"
   			  },
   			  "oAria": {
   			    "sSortAscending": ": تفعيل لفرز العمود تصاعدي",
   			    "sSortDescending": ": تفعيل لفرز عمود تنازلي"
   			  }
   			};
      }else{
    	  languageList = {
   			  /* "sEmptyTable": "", */
   			  "sInfo": "Showing _START_ to _END_ of _TOTAL_ entries",
   			  "sInfoEmpty": "Showing 0 to 0 of 0 entries",
   			  "sInfoFiltered": "(filtered from _MAX_ total entries)",
   			  "sInfoPostFix": "",
   			  "sInfoThousands": ",",
   			  "sLengthMenu": "Show _MENU_ entries",
   			  "sLoadingRecords": "Loading...",
   			  "sProcessing": "Processing...",
   			  "sSearch": "Search:",
   			  "sZeroRecords": "No matching records found",
   			  "oPaginate": {
   			    "sFirst": "First",
   			    "sLast": "Last",
   			    "sNext": "Next",
   			    "sPrevious": "Previous"
   			  },
   			  "oAria": {
   			    "sSortAscending": ": activate to sort column ascending",
   			    "sSortDescending": ": activate to sort column descending"
   			  }
   			};
      }
      $(document).ready(function(){
      	$.ajaxSetup({
      	    beforeSend: function(xhr) {
      	    	 var token = $("meta[name='_csrf']").attr("content");
      	    	 var header = $("meta[name='_csrf_header']").attr("content");
      	        xhr.setRequestHeader(header, token);
      	    }
      	});
      	$.fn.dataTable.moment('h:mm A');
      });
    </script>
  </head>
  <body class="nav-md">
    <div class="container body">
      <div class="main-container">
        <c:if test="${not empty PENDING_ACCOUNT_STATUS}">
 		  <div class="pending-account-status">
            <h4 class="title-text"><i class="icon fa fa-warning"></i> ${PENDING_ACCOUNT_STATUS}</h4>
          </div>       
        </c:if>
        <tiles:insertAttribute name="page-menu" />
        <tiles:insertAttribute name="page-header"/>
        <div id="page-container" >        
        	<tiles:insertAttribute name="page-content"/>
        </div>
          <tiles:insertAttribute name="page-footer"/>
      </div>
    </div>
   
    <!-- Smart Resize -->
    <script src="${contextPath}/resources/js/smartresize.js"></script>
   
    <!-- FastClick -->
    <script src="${contextPath}/resources/js/fastclick.js"></script>
   
    <!-- NProgress -->
    <script src="${contextPath}/resources/js/nprogress.js"></script>
   
    <!-- iCheck -->
    <script src="${contextPath}/resources/js/icheck.min.js"></script>
   
    <!-- Custom Theme Scripts -->
    <script src="${contextPath}/resources/js/backend-custom.js"></script>
    <script src="${contextPath}/resources/js/custom/page-loading-ajax-methods.js"></script>   
	
    <script type="text/javascript">
    /* (function IIFE(msg, times) {
        for (var i = 1; i <= times; i++) {
            console.log(msg);
        }
    }("Hello!", 5)); */
    
    $(document).ready(function(){
      	$(".alert").delay(1000).hide(100);      	
      	$("input[type=text], input[type=password], textarea").bind("change",function(){
			$(this).val($.trim($(this).val()));
		});
      	$("html").tooltip({
        	selector: '[data-toggle="tooltip"]',
        	container: 'html',
        });
      });
    </script>
  </body>
</html>