<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
  table#offer-detail-table tr:nth-child(3) th{color: #8e0000 !important;font-size: 13px;font-weight: bolder;background: #eaeaea !important;}
  table#offer-detail-table tr:nth-child(3) th{text-align: center;}
</style>
<form action="${contextPath}/ssa/offer/synccancel/${auctionSellersBean.dailyAuctionsBean.dailyAuctionsId}/${auctionSellersBean.auctionSellersId}" class="normalPageContentLoading" method="post" enctype="multipart/form-data">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span>
    </button>
    <h4 class="modal-title"><spring:message code="seller.viewOfferDetails.title"/></h4> </div>
  <div class="modal-body">
    <c:if test="${not empty auctionSellersBean}">
      <div class="table-responsive">
        <table id="offer-detail-table" class="table table-striped table-bordered">
          <thead>
            <tr>
              <th colspan="7"><spring:message code="auctionlist.th.dailyauctions" /></th>
            </tr>
            <tr>
              <th><spring:message code="auctionofferlist.th.startime" /></th>
              <th><spring:message code="auctionofferlist.th.endtime" /></th>
              <th><spring:message code="auctionofferlist.th.group" /></th>
              <th><spring:message code="auctionofferlist.th.name" /></th>
              <th><spring:message code="auctionofferlist.th.type" /></th>
              <th><spring:message code="auctionofferlist.th.status" /></th>
            </tr>
            <tr>
              <th><fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionSellersBean.dailyAuctionsBean.beginTime}" /></th>
              <th><fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionSellersBean.dailyAuctionsBean.endTime}" /></th>
              <th>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productGroupName}</th>
              <th>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productName}</th>
              <th>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productTypeName}</th>
              <th>
                <label class="label auction status-${auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}">
                  <spring:message code="auction.status.${auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}" /> </label>
              </th>
            </tr>
          </thead>
          <tbody> </tbody>
        </table>
      </div>
      <div class="clearfix"></div>
      <div class="x_panel">
        <div class="x_content">
          <div class="form-group">
            <label class="col-md-4 col-sm-4 mt-8"><spring:message code="seller.auctionofferplacing.lbl.pickuplocation" /></label>
            <p class="col-md-8 col-sm-8 read-only">${auctionSellersBean.accountLocationsBean.locationName}</p>
            <div class="clearfix"></div>
          </div>
          <div class="form-group">
            <label class="col-md-4 col-sm-4 mt-8"><spring:message code="seller.auctionofferplacing.lbl.offerquantity" /></label>
            <p class="col-md-8 col-sm-8 read-only">
              <fmt:formatNumber value="${auctionSellersBean.offerQuantity}"/>
            </p>
            <div class="clearfix"></div>
          </div>
          <div class="form-group">
            <label class="col-md-4 col-sm-4 mt-8"><spring:message code="seller.auctionofferplacing.lbl.offerprice" /></label>
            <p class="col-md-8 col-sm-8 read-only">
              <c:choose>
                <c:when test="${auctionSellersBean.offerPrice eq 0}">
                  <strong><spring:message code="seller.auctionofferplacing.lbl.highestPrice"/></strong>
                </c:when>
                <c:otherwise>${auctionSellersBean.offerPrice}</c:otherwise>
              </c:choose>
            </p>
            <div class="clearfix"></div>
          </div>
        </div>
      </div>
      <c:choose>
                <c:when test="${auctionSellersBean.offerQuantity eq auctionSellersBean.availableQuantity}">
                 <div class="form-group">
                <h1> 
                 <label class="text-danger">
        			<spring:message code="seller.cancel.confirm.cancel.msg" />
     			 </label>
     			 </h1>
     			 </div>
                </c:when>
                 <c:otherwise>
                   <div class="form-group">
              <h1> <label class="text-danger">
					<spring:message code="seller.cancel.confirm.cancel.notmsg" />
     			 </label>
     			 </h1>
     			 </div>
     			 </c:otherwise>
              </c:choose>
      
      
      <label class="text-danger">
        <spring:message code="seller.cancel.confirm.msg" />
      </label>
    </c:if>
  </div>
  <div class="modal-footer text-right">
    <c:if test="${not empty auctionSellersBean}">
      <button type="submit" class="btn btn-success btn-lg"> <i class="fa fa-check"></i>&nbsp;&nbsp;
        <spring:message code="btn.cancel" /> </button>
    </c:if>
    <button type="button"  class="btn btn-danger btn-lg" data-dismiss="modal"> <i class="fa fa-remove"></i>&nbsp;&nbsp;
      <spring:message code="btn.close" /> </button>
  </div>
</form>
