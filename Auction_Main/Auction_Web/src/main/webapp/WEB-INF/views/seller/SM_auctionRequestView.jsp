<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="form-group">
    <label class="form-group col-md-5"><spring:message code="seller.auctionRequestView.lbl.productname"/></label>
    <div class="col-md-7 static-form-control">${auctionRequestBean.productCatalogBean.productGroupName} - ${auctionRequestBean.productCatalogBean.productName} - ${auctionRequestBean.productCatalogBean.productTypeName}</div>
    <div class="clearfix"></div>
</div>
<div class="form-group">
    <label class="form-group col-md-5"><spring:message code="seller.auctionRequestView.lbl.comment"/></label>
    <div class="col-md-7 static-form-control">${auctionRequestBean.sellerComment}</div>
    <div class="clearfix"></div>
</div>
<div class="form-group">
    <label class="form-group col-md-5"><spring:message code="seller.auctionRequestView.lbl.status"/></label>
    <%-- <c:if test="${auctionRequestBean.status eq 0}">
        <c:set var="auctionStatus" value="Rejected" /> </c:if>
    <c:if test="${auctionRequestBean.status eq 1}">
        <c:set var="auctionStatus" value="Requested" /> </c:if>
    <c:if test="${auctionRequestBean.status eq 2}">
        <c:set var="auctionStatus" value="On Hold" /> </c:if>
    <c:if test="${auctionRequestBean.status eq 3}">
        <c:set var="auctionStatus" value="Approved" /> </c:if> --%>
    <div class="col-md-7 static-form-control">
    	${auctionStatus}
    <spring:message code="auction.request.status.${auctionRequestBean.status}"/>
    </div>
    <div class="clearfix"></div>
</div>
<div class="form-group">
    <label class="form-group col-md-5"><spring:message code="seller.auctionRequestView.lbl.feedback"/></label>
    <div class="col-md-7 static-form-control">${auctionRequestBean.feedback}</div>
</div>
<div class="form-group text-right">
    <button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="seller.auctionRequestView.btn.close"/></button>
</div>

<script type="text/javascript">
      $(document).ready(function() {});
</script>
