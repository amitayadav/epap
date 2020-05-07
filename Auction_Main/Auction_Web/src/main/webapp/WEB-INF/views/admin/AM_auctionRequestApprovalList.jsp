<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="auctionrequestapprovallist.h3.auctionrequestlist"/></li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3>
          <spring:message code="auctionrequestapprovallist.h3.auctionrequestlist"/>
        </h3>
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
              <%-- <h2>
                <spring:message code="auctionrequestapprovallist.h2.listofauctionrequest"/>
              </h2> --%>
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
              <spring:message code="auctionrequestapprovallist.th.edit" var="editPhl"/>
              <table id="datatable" class="table table-striped table-bordered table_dayauction">
                <thead>
                  <tr>
                    <th>
                      <spring:message code="auctionrequestapprovallist.th.fullname"/>
                    </th>
                    <th>
                      <spring:message code="auctionrequestapprovallist.th.productname"/>
                    </th>
                    <th>
                      <spring:message code="auctionrequestapprovallist.th.status"/>
                    </th>
                    <th width="40" class="disable-sorting">${editPhl}</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${auctionRequestBeanList}" var="auctionRequest" varStatus="count">
                    <tr>
                      <%-- <spring:message code="auctionrequestapprovallist.auctionstatus.rejected" var="rejectedPhl"/>
                        <spring:message code="auctionrequestapprovallist.auctionstatus.requested" var="requestedPhl"/>
                        <spring:message code="auctionrequestapprovallist.auctionstatus.onhold" var="onholdPhl"/>
                        <spring:message code="auctionrequestapprovallist.auctionstatus.approved" var="approvedPhl"/> --%>
                      <td>${auctionRequest.accountProfileByCreatedBy.FName} ${auctionRequest.accountProfileByCreatedBy.MName} ${auctionRequest.accountProfileByCreatedBy.LName}</td>
                      <td>${auctionRequest.productCatalogBean.productGroupName} <b>-</b> ${auctionRequest.productCatalogBean.productName} <b>-</b> ${auctionRequest.productCatalogBean.productTypeName}</td>
                      <%-- <c:if test="${auctionRequest.status eq 0}">	<c:set var="auctionStatus" value="${rejectedPhl}"/> </c:if>
                        <c:if test="${auctionRequest.status eq 1}">	<c:set var="auctionStatus" value="${requestedPhl}"/> </c:if>
                        <c:if test="${auctionRequest.status eq 2}">	<c:set var="auctionStatus" value="${onholdPhl}"/> </c:if>
                        <c:if test="${auctionRequest.status eq 3}">	<c:set var="auctionStatus" value="${approvedPhl}"/> </c:if> --%>
                      <td class="text-center text-uppercase">
                        <%-- ${auctionStatus} --%>
                        <spring:message code="auction.request.status.${auctionRequest.status}"/>
                      </td>
                      <td>
                        <button onclick="auctionRequestEdit(${auctionRequest.requestId})" class="btn btn-warning btn-sm "  data-toggle="tooltip" data-original-title="${editPhl}">
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
  </div>
</div>
<div class="modal fade" id="auctionRequestEditModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">X</span>
        </button>
        <h4 class="modal-title">
          <spring:message code="auctionrequestapprovallist.model.header"/>
        </h4>
      </div>
      <div class="modal-body"></div>
    </div>
  </div>
</div>
<script type="text/javascript">

  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|AdminauctionRequestList", contextPath+ "/spropt/adminauctionRequestList");
   	languageList.sEmptyTable = '<spring:message code="auctionrequestapprovallist.emptytable"/>';
    $('#datatable').DataTable({
      language: languageList,
      columnDefs: [{orderable: false, targets: 'disable-sorting'}]
    });
  });
  
  function auctionRequestEdit(id){
   $.ajax({
   	    type: "GET",
   	    async: false,
   	 cache: false,
   	    url: (contextPath+"/spropt/auctionrequestedit/"+id),
   	    success: function(result) {
   	    	$("#auctionRequestEditModel .modal-body").empty().html(result);
   	     	$("#auctionRequestEditModel").modal({
          	  show:true,
          	  backdrop: 'static',
              keyboard: false
            });
   	    },error: function(e){
  			$("#auctionRequestEditModel .modal-body").empty().html(e);
   	    }     
     });
  }
</script>