<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.begintime"/>
  </label>
  <div class="col-md-7 static-form-control">
    <fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${dailyAuctionsBean.beginTime}"/>
  </div>
  <div class="clearfix"></div>
</div>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.endtime"/>
  </label>
  <div class="col-md-7 static-form-control">
    <fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${dailyAuctionsBean.endTime}"/>
  </div>
  <div class="clearfix"></div>
</div>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.productgroup"/>
  </label>
  <div class="col-md-7 static-form-control">${dailyAuctionsBean.productCatalogBean.productGroupName}</div>
  <div class="clearfix"></div>
</div>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.productname"/>
  </label>
  <div class="col-md-7 static-form-control">${dailyAuctionsBean.productCatalogBean.productName}</div>
  <div class="clearfix"></div>
</div>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.producttype"/>
  </label>
  <div class="col-md-7 static-form-control">${dailyAuctionsBean.productCatalogBean.productTypeName}</div>
  <div class="clearfix"></div>
</div>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.auctionduration"/>
  </label>
  <div class="col-md-7 static-form-control">${dailyAuctionsBean.auctionDuration}</div>
  <div class="clearfix"></div>
</div>
<div class="form-group">
  <label class="form-group col-md-5">
    <spring:message code="viewauction.lbl.auctionstatus"/>
  </label>
  <div class="col-md-7 static-form-control">
  	<label class="label auction status-${dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}">
  		<spring:message code="auction.status.${dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}"/>
  	</label>
  </div>
  <div class="clearfix"></div>
</div>
<div class="form-group text-right">
  <button type="button" class="btn btn-default" data-dismiss="modal">
    <spring:message code="btn.close"/>
  </button>
</div>
<script type="text/javascript">
  $(document).ready(function() {});
</script>