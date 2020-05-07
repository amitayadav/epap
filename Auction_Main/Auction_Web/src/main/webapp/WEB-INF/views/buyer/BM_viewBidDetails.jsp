<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
  table#auction-detail-table tr:nth-child(3) td{color: #8e0000 !important;font-size: 13px;font-weight: bolder;}
  table#auction-detail-table tr:nth-child(3) td{text-align: center;}
  
  table#offer-detail-table tr:nth-child(2) td{color: #8e0000 !important;font-size: 13px;font-weight: bolder;}
  table#offer-detail-table tr:nth-child(2) td{text-align: center;}
</style>
<form action="${contextPath}/bba/bid/cancel/${auctionBuyersBean.auctionSellersBean.auctionSellersId}/${auctionBuyersBean.auctionBuyersId} " method="post" enctype="multipart/form-data">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span>
    </button>
    <h4 class="modal-title"><spring:message code="buyer.viewbidDetails.title"/></h4> </div>
  <div class="modal-body">
    <c:if test="${not empty auctionBuyersBean}">
      <div class="table-responsive">
        <table id="auction-detail-table" class="table table-striped table-bordered">
          <thead>
            <tr>
              <th colspan="6"><spring:message code="auctionlist.th.dailyauctions" /></th>
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
              <td><fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.beginTime}" /></td>
              <td><fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.endTime}" /></td>
              <td>${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.productCatalogBean.productGroupName}</td>
              <td>${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.productCatalogBean.productName}</td>
              <td>${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.productCatalogBean.productTypeName}</td>
              <td>
                <label class="label auction status-${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}">
                  <spring:message code="auction.status.${auctionBuyersBean.auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}" /> </label>
              </td>
            </tr>
          </thead>
        </table>
        <br/>
        <table id="offer-detail-table" class="table table-striped table-bordered">
          <thead>
            <tr>
	           	<th colspan="2"><spring:message code="auctionbidslist.th.sellername"/></th>
	           	<th><spring:message code="auctionbidslist.th.partialallowed"/></th>
	           	<th><spring:message code="auctionbidslist.th.offerqty"/></th>
	           	<th><spring:message code="auctionbidslist.th.availableqty"/></th>
	           	<th><spring:message code="auctionbidslist.th.offerprice"/></th>
	           	<th><spring:message code="auctionbidslist.th.offerstatus"/></th>
           </tr>
           <tr>
           	<td colspan="2">
           	  ${auctionBuyersBean.auctionSellersBean.accountProfileBean.publicName}
           	</td>
           	<td>                  		
                <c:choose>
                 <c:when test="${auctionBuyersBean.auctionSellersBean.partialAllowed}">
                   <label class="label yes"><i class="fa fa-check"></i></label>
                 </c:when>
                 <c:otherwise>
                   <label class="label no"><i class="fa fa-times"></i></label>
                 </c:otherwise>
               </c:choose>
           	</td>
           	<td>${auctionBuyersBean.auctionSellersBean.offerQuantity}</td>
           	<td>${auctionBuyersBean.auctionSellersBean.availableQuantity}</td>
           	<td class="${auctionBuyersBean.auctionSellersBean.offerPrice > 0 ? '' : 'text-bold text-dark'}">
           	<c:choose>
           	  <c:when test="${auctionBuyersBean.auctionSellersBean.offerPrice > 0}">
           	  	<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${auctionBuyersBean.auctionSellersBean.offerPrice}"/>
           	  </c:when>
           	  <c:otherwise>
           	  	<spring:message code="market"/>
           	  </c:otherwise>
           	</c:choose>
             </td>
           	<td>
           		<label class="label auction-offer status-${auctionBuyersBean.auctionSellersBean.sellerOfferStatus}">
           		<spring:message code="auction.seller.offer.status.${auctionBuyersBean.auctionSellersBean.sellerOfferStatus}"/></label>
           	</td>
           </tr>
          </thead>
          <tbody> </tbody>
        </table>
      </div>
      <div class="clearfix"></div>
      <div class="x_panel">
        <div class="x_content">
          <div class="form-group">
            <label class="col-md-4 col-sm-4 mt-8"><spring:message code="buyer.auctionbidplacing.lbl.deliverylocation" /></label>
            <div class="col-md-8 col-sm-8 read-only">${auctionBuyersBean.accountLocationsBean.locationName}</div>
            <div class="clearfix"></div>
          </div>
          <div class="form-group">
            <label class="col-md-4 col-sm-4 mt-8"><spring:message code="buyer.auctionbidplacing.lbl.bidquantity" /></label>
            <div class="col-md-8 col-sm-8 read-only">${auctionBuyersBean.bidQuantity}</div>
            <div class="clearfix"></div>
          </div>
          <div class="form-group">
            <label class="col-md-4 col-sm-4 mt-8"><spring:message code="buyer.auctionbidplacing.lbl.bidprice" /></label>
            <div class="col-md-8 col-sm-8 read-only">${auctionBuyersBean.bidPrice}</div>
            <div class="clearfix"></div>
          </div>
        </div>
      </div>
      <label class="text-danger">
        <spring:message code="byuer.cancel.confirm.msg" />
      </label>
    </c:if>
  </div>
  <div class="modal-footer text-right">
    <c:if test="${not empty auctionBuyersBean}">
      <button type="submit" class="btn btn-success btn-lg"> <i class="fa fa-check"></i>&nbsp;&nbsp;
        <spring:message code="btn.cancel" /> </button>
    </c:if>
    <button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal"> <i class="fa fa-remove"></i>&nbsp;&nbsp;
      <spring:message code="btn.close" /> </button>
  </div>
</form>