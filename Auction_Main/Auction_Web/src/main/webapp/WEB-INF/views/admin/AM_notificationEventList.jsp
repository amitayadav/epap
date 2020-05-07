<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="now" class="java.util.Date" />
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="notificationeventlist.h3.notificationeventlist"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul> 
  <div class="page-title">
    <div class="title_left">
      <h3><spring:message code="notificationeventlist.h3.notificationeventlist"/></h3>
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
            <%-- <h2><spring:message code="notificationeventlist.h2.notificationeventlistdetails"/></h2> --%>
            <button class="btn btn-info btn-sm pull-right" id="eventNotificationBtn" data-id="0"><i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="btn.add"/></button>
            <div class="clearfix"></div>
          </div>
          <div class="x_panel">
          <spring:message code="btn.edit" var="editPhl"/>
            <table id="datatable" class="table table-striped table-bordered table_dayauction">
              <thead>
                <tr>
                  <th width="40">#</th>
                  <th><spring:message code="notificationeventlist.th.name"/></th>
                  <th width="40" class="disable-sorting">${editPhl}</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${eventsMeaningBeanList}" var="event" varStatus="status">
                  <tr>
                  	<td>${status.count}</td>
                    <td>${event.eventMeaning}</td>
                    <td class="text-center">
                        <button data-id="${event.eventId}" class="btn btn-warning btn-sm eventNotificationBtn"  data-toggle="tooltip" data-original-title="${editPhl}">
                        <i class="fa fa-edit"></i>
                        </button>
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
  <div class="clearfix"></div>
</div>
<div class="modal fade" id="eventNotificationModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|NotificationEventDetailsList", contextPath+ "/admin/notificationEventDetailsList");
      languageList.sEmptyTable = '<spring:message code="notificationeventlist.emptytable"/>';
      $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
      
      function royaltyCodeForm(id){
      	if(id > 0){
      		$("#eventNotificationModel .modal-title").html("<spring:message code='notificationeventlist.update.model.header'/>");
      	}else{
      		$("#eventNotificationModel .modal-title").html("<spring:message code='notificationeventlist.add.model.header'/>");
      	}
      	
      	$.ajax({
    	        type: "GET",
    	        async: false,
    	        cache: false,
    	        url: (contextPath+"/admin/notificationeventdetails/"+id),
    	        success: function(result) {
    	        	$("#eventNotificationModel .modal-body").empty().html(result);
    	        	$("#eventNotificationModel").modal({
    	        		show:true,
    	        		backdrop: 'static',
    	          	    keyboard: false
    	              });
    	        },error: function(e){
    	        	$("#eventNotificationModel .modal-body").empty().html(e);
    	        	$("#eventNotificationModel").modal({
    	        		show:true,
    	        		backdrop: 'static',
    	          	    keyboard: false
    	              });
    	        }
    	    }); 
      }
      $("#eventNotificationBtn").click(function(e){
      	e.preventDefault();
      	royaltyCodeForm($(this).data("id"));
      });
      $("#datatable").on("click","tr .eventNotificationBtn",function(e){
      	e.preventDefault();
      	royaltyCodeForm($(this).data("id"));
      });
 	 
     /*  $('#eventNotificationModel').modal();   */

  });
</script>
