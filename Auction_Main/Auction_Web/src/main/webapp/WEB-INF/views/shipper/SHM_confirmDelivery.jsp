<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- page content -->
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="menu.common.confirmdelivery"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3><spring:message code="shipper.confirmdelivery.heading.title"> </spring:message></h3>
      </div>
    </div>

    <div class="clearfix"></div>
 	<c:if test="${not empty ERROR}">
		<div class="alert alert-danger alert-dismissible">
	 		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	 		<h4 class="title-text"><i class="icon fa fa-warning"></i> ${ERROR}</h4>
		</div>
	</c:if>
	<c:if test="${not empty SUCCESS}">
		<div class="alert alert-success alert-dismissible">
 			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
			<h4 class="title-text"><i class="icon fa fa-check"></i> ${SUCCESS}</h4>
		</div>
	</c:if>
    <%-- <div class="row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
          <div class="x-title">
            <h2><spring:message code="dashboard.lbl.title"> </spring:message></h2>
            <div class="clearfix"></div>
          </div>
          <div class="x_content">
          </div>
        </div>
      </div>
    </div> --%>
  </div>
</div>
<!-- /page content -->
<script type="text/javascript">
  $(document).ready(function() {
  window.history.pushState("object or string", "EPAP|Confirmdelivery", contextPath+ "/shsha/confirmdelivery/List");
  });
  
  </script>