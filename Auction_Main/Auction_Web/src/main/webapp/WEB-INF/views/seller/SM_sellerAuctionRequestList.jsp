<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="menu.li.sellerAuctionRequest"/></li>
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
                <h3><spring:message code="seller.auctionrequestlist.header"/></h3>
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
                            <%-- <h2><spring:message code="seller.auctionrequestlist.box.header"/></h2> --%>
                            <a href="${contextPath}/ssa/auctionrequest" class="btn btn-info btn-sm pull-right pageContentLoading"><i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="btn.add"/></a>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_panel table-responsive">
							<spring:message code="btn.edit" var="editbtn"/>
							<spring:message code="btn.view" var="viewbtn"/>
                            <table id="datatable" class="table table-striped table-bordered table_dayauction">
                                <thead>
                                    <tr>
                                        <th><spring:message code="seller.auctionrequestlist.table.th.fullname"/></th>
                                        <th><spring:message code="seller.auctionrequestlist.table.th.productname"/></th>
                                        <th><spring:message code="seller.auctionrequestlist.table.th.Status"/></th>
                                        <th width="40" class="disable-sorting">${editbtn}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${auctionRequestBeanList}" var="auctionRequest" varStatus="count">
                                        <tr>
                                            <td>${auctionRequest.accountProfileByCreatedBy.FName} ${auctionRequest.accountProfileByCreatedBy.MName} ${auctionRequest.accountProfileByCreatedBy.LName}</td>
                                            <td>${auctionRequest.productCatalogBean.productGroupName} <b>-</b> ${auctionRequest.productCatalogBean.productName} <b>-</b> ${auctionRequest.productCatalogBean.productTypeName}</td>
                                            <%-- <c:if test="${auctionRequest.status eq 0}">
                                                <c:set var="auctionStatus" value="Rejected" /> </c:if>
                                            <c:if test="${auctionRequest.status eq 1}">
                                                <c:set var="auctionStatus" value="Requested" /> </c:if>
                                            <c:if test="${auctionRequest.status eq 2}">
                                                <c:set var="auctionStatus" value="On Hold" /> </c:if>
                                            <c:if test="${auctionRequest.status eq 3}">
                                                <c:set var="auctionStatus" value="Approved" /> </c:if> --%>
                                            <td class="text-center text-uppercase"><spring:message code="auction.request.status.${auctionRequest.status}"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${auctionRequest.accountProfileBySellerId.accountId eq loginDetailBean.accountProfileBean.accountId and auctionRequest.status eq 2 }">
                                                        <a href="${contextPath}/ssa/auctionrequest/${auctionRequest.requestId}" class="btn btn-warning btn-sm pageContentLoading" data-toggle="tooltip" data-original-title="${editbtn}">
                                                        	<i class="fa fa-edit"></i>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button onclick="auctionRequestView(${auctionRequest.requestId})" class="btn btn-info btn-sm " data-toggle="tooltip" data-original-title="${viewbtn}">
                                                            <i class="fa fa-eye"></i>
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
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
<div class="modal fade" id="auctionRequestViewModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">X</span>
                </button>
                <h4 class="modal-title"><spring:message code="seller.auctionrequestlist.modal.title"/></h4>
            </div>
            <div class="modal-body"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    window.history.pushState("object or string", "EPAP|AuctionrequestList", contextPath+"/ssa/auctionrequest/List");
	  languageList.sEmptyTable = '<spring:message code="seller.auctionrequestlist.emptytable"/>';
	    $('#datatable').DataTable({
	  	  language: languageList,
	  	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
	    });
	});
	
    function auctionRequestView(id) {
        $.ajax({
            type: "GET",
            async: false,
            cache: false,
            url: (contextPath + "/ssa/auctionrequestview/" + id),
            success: function(result) {
                $("#auctionRequestViewModel .modal-body").empty().html(result);
                $("#auctionRequestViewModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            },
            error: function(e) {
                $("#auctionRequestViewModel .modal-body").empty().html(e);
            }
        });
    }
</script>