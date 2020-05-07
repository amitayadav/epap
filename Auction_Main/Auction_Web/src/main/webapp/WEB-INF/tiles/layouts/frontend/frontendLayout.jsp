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
    <div>
        <c:if test="${not empty errorStack}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text"><i class="icon fa fa-warning"></i> ${errorStack}</h4>
            </div>
        </c:if>
    </div>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Meta, title, CSS, favicons, etc. -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="author" content="">

        <!-- 	<meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1"> 
            <meta name="author" content=""> -->
        <title><tiles:insertAttribute name="page-title"></tiles:insertAttribute></title>

            <!-- Tell the browser to be responsive to screen width -->
            <meta name="_csrf" content="${_csrf.token}"/>

        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <link href="${contextPath}/resources/images/EPAP_Logo3.png" rel="shortcut icon"/>
        <link href="${contextPath}/resources/images/EPAP_Logo3.png" rel="icon"/>

        <!-- Bootstrap -->
        <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/dataTables.bootstrap.css" rel="stylesheet"/>
        <link href="${contextPath}/resources/css/responsive.bootstrap.css" rel="stylesheet"/>
        <link href="${contextPath}/resources/css/scroller.bootstrap.css" rel="stylesheet"/>

        <!-- Font Awesome -->
        <link href="${contextPath}/resources/css/font-awesome.min.css" rel="stylesheet">

        <!-- NProgress -->
        <link href="${contextPath}/resources/css/nprogress.css" rel="stylesheet">

        <!-- Animate.css -->
        <link href="${contextPath}/resources/css/animate.min.css" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800" rel="stylesheet">

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
        <script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
        <!-- Bootstrap -->
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
        <script src="${contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
        <script src="${contextPath}/resources/js/dataTables.fixedHeader.min.js"></script>
        <script src="${contextPath}/resources/js/dataTables.responsive.min.js"></script>
        <script src="${contextPath}/resources/js/dataTables.scroller.min.js"></script>
        <script src="${contextPath}/resources/js/moment.min.js"></script>
        <script src="${contextPath}/resources/js/datetime-moment.js"></script>
        <script src="${contextPath}/resources/js/moment-timezone.js"></script>
        <script src="${contextPath}/resources/js/moment-timezone-with-data.js"></script>
        <script src="${contextPath}/resources/js/sockjs.min.js"></script>
        <script src="${contextPath}/resources/js/stomp.min.js"></script> 
        <!--  new changes 30-11-2019 -->
        <%-- <script src="${contextPath}/resources/js/frontendjs/jquery-1.12.4.min.js"></script> --%>
        <script src="${contextPath}/resources/js/frontendjs/lib.js"></script> 
        <script src="${contextPath}/resources/js/frontendjs/functions.js"></script>	
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
        <!-- Add _csrf header and content into every ajax call --> 
        <script type="text/javascript">
       var contextPath = "${contextPath}";
       var selectedLocale = "${(pageContext.response.locale)}";
       var languageList = {};
       if ("${CommonConstants.ARABIC}" == "${(pageContext.response.locale)}") {
           languageList = {
               /* "sEmptyTable": "", */
               "sInfo": "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل",
               "sInfoEmpty": "يعرض 0 إلى 0 من أصل 0 سجل",
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
       } else {
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
       $(document).ready(function () {
           $(".alert").delay(8000).hide(100);
           $.ajaxSetup({
               beforeSend: function (xhr) {
                   var token = $("meta[name='_csrf']").attr("content");
                   var header = $("meta[name='_csrf_header']").attr("content");
                   xhr.setRequestHeader(header, token);
               }
           });
           $.fn.dataTable.moment('h:mm A');
       });
        </script>
    </head>
    <body>
        <tiles:insertAttribute name="page-header"/>
        <tiles:insertAttribute name="page-menu"/>
        <tiles:insertAttribute name="page-content"/>
        <tiles:insertAttribute name="page-footer"/>
    </body>
</html>