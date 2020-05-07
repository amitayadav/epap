<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
     <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="seller.sellerSalesNotices.title"/></li>
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
        <h3><spring:message code="seller.sellerSalesNotices.heading"/></h3>
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
   <div class="x_content">
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="x_panel">
            <div class="x-title">
              <%-- <h2><spring:message code="buyer.financialstatement.heading"/></h2> --%>
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
              <form action="${contextPath}/salesNotice/seller" method="POST" id="salesNotices" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div class="form-group">
                   <spring:message code="seller.sellerSalesNotices.lbl.startDate" var="startDatePhl"/>
                  <spring:message code="seller.sellerSalesNotices.lbl.endDate" var="endDatePhl"/>
                  <div class="col-md-6 nolpad">
                    <label class="form-label"><span class="required"> * </span>${startDatePhl}</label>
                    <input type="text" name="startDate" id="startDate" class="form-control-for-dates" placeholder="${startDatePhl}" value="${startDate}" readonly="readonly"/>
                  </div>
                  <div class="col-md-6 norpad">
                    <label class="form-label"><span class="required"> * </span>${endDatePhl}</label>
                    <input type="text" name="endDate" id="endDate" class="form-control-for-dates" placeholder="${endDatePhl}"  value="${endDate}" readonly="readonly"/>
                  </div>
                </div>
                <div class="clearfix"></div>
                <div class="form-group text-right">
                  <br/>
                  <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="submit"/>'/>&nbsp;&nbsp;
                </div>
              </form>
              <c:if test="${empty sellerSalesNoticesViewList and (not empty startDate or not empty endDate)}">
                <label class="error">
                  <spring:message code="sellersalesNotices.lbl.notransaction"/>
                </label>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
    <c:if test="${not empty sellerSalesNoticesViewList}">
      <div class="x_content">
        <div class="row">
          <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
              <div class="x-title"></div>
              <div class="table-responsive">
              <table class="table table-striped table-bordered table_dayauction" id="datatable">
                  <thead>
                    <tr>
                       <th><spring:message code="common.lbl.date"/></th>
                      <th> <spring:message code="common.lbl.Invoice"/></th>
                    </tr>
                  </thead>
                  <tbody>
                   
                    <c:forEach items="${sellerSalesNoticesViewList}" var="salesNoticesViewList" varStatus="status">
                      <tr>
                         <td>${salesNoticesViewList.transactionCreation}</td>
                         <td><a href="${contextPath}/report/salesNoticeReport/${salesNoticesViewList.transactionId}.pdf" target="_BLANK" 
				          class="btn btn-primary"  data-toggle="tooltip" data-original-title="PDF">PDF</a>
                       </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                  
                </table>
                </div>
            </div>
          </div>
        </div>
      </div>
    </c:if>
  </div>
</div>
 
 
<script type="text/javascript">
  $(document).ready(function() {
	  $('#datatable').DataTable();
      window.history.pushState("object or string", "EPAP|salesNotice", contextPath+"/salesNotice/seller/salesNotice");
      	var startDateVar =  '<c:out value="${startDate}"/>';
		var endDateVar =  '<c:out value="${endDate}"/>';
		if (startDateVar == '') {		 
			  startDateVar=new Date(new Date().setDate(1)).toISOString().substring(0, 10);
	 	} 
	 
      $("#startDate").daterangepicker({
    	  locale: {
  			format: "YYYY-MM-DD"
  		  },
    	  singleDatePicker: true,
    	  startDate:startDateVar,
    	  maxDate :new Date().toISOString().substring(0, 10)
	  });
      $('#startDate').on('apply.daterangepicker', function(ev, picker) {
    	   	 $("#endDate").daterangepicker({
    	       	 locale: {
    	         	format: "YYYY-MM-DD"
    	     	 },
    	       	 singleDatePicker: true,
    	       	 minDate : $("#startDate").val(),	 
    	   	 });
    	     });
      $("#endDate").daterangepicker({
    	  locale: {
      		format: "YYYY-MM-DD"
  		  },
    	  singleDatePicker: true,
    	  //dateLimit: true,
    	  minDate : $("#startDate").val(),		  
	  });
      
      $("#salesNotices").validate({
          debug: true,
          rules: {
              "startDate": {
                  "required": true,
              },
              "endDate": {
                  "required": true,
              }
          },
          messages: {
              "startDate": {
                  "required": '<spring:message code="seller.sellerSalesNotices.validation.startDate.required"/>'
              },
              "endDate": {
                  "required": '<spring:message code="seller.sellerSalesNotices.validation.endDate.required"/>'
              }
          },
          invalidHandler: function(event, validator) {

          },
          errorPlacement: function(error, element) {
			error.insertAfter(element.parent(".form-group"));
          },
         /*  submitHandler: function(form) {
        	  form.submit();
              $(form).find("input[type=submit]").attr("disabled",true);
          }, */
          highlight: function(element) {
			$(element).addClass("error-element");
          },
          unhighlight: function(element) {
		    $(element).removeClass("error-element");
          }
      });
  });

</script>