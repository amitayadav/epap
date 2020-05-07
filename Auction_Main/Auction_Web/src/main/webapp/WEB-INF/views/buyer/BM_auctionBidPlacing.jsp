<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.auction.commons.enums.ENUM_ProductCatalogStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionStatusCodes"%>
<c:set var="AUCTION_STATUS_OPEN" value="${ENUM_AuctionStatusCodes.OPEN.getStatus()}"/>
<c:set var="AUCTION_STATUS_RUNNING" value="${ENUM_AuctionStatusCodes.RUNNING.getStatus()}"/>

<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>
<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}&nbsp;</label><br/>${auctionBuyersBean.dailyAuctionsBean.productCatalogBean.productDescription}</p><br/><p class='show-product-info'><label>${productContainerSpecsPhl}&nbsp;</label><br/>${auctionBuyersBean.dailyAuctionsBean.productCatalogBean.containerSpecs}</p>"/>

<style>
    table tr:nth-child(2) td,
    table tr:nth-child(3) td{color: #8e0000 !important;font-size: 15px;font-weight: bolder;text-align:center;}
    table#offerDetails tr:nth-child(2) td{color: #8e0000 !important;font-size: 13px;font-weight: bolder;text-align:center;}
    #billDetails p{font-size:14px;}
    #billDetails hr{margin-top: 10px;margin-bottom: 10px;}
</style>
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li>
            <a href="${contextPath}/auctions/auctionList"><spring:message code="menu.a.auction"/></a>
        </li>
        <li>
            <a href="${contextPath}/auctions/offers/${auctionBuyersBean.dailyAuctionsBean.dailyAuctionsId}"><spring:message code="menu.a.auction.sellers.offers"/></a>
        </li>
        <li>
            <a href="${contextPath}/auctions/bids/${auctionBuyersBean.auctionSellersBean.auctionSellersId}"><spring:message code="menu.a.auction.buyers.bids"/></a>
        </li>
        <li>
            <spring:message code="buyer.auctionbidplacing.h3.auctionbuyerbid"/>
        </li>
        <li class="todaydatetime">
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
            <span id="current-time">
                <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
            </span>
        </li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3><spring:message code="buyer.auctionbidplacing.h3.auctionbuyerbid"/></h3>
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
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel auction-list-bordered table-responsive">
                    <table class="table table-striped table-bordered table_dayauction">
                        <thead>
                            <tr>
                                <th colspan="6"><spring:message code="auctionlist.th.dailyauctions"/></th>
                            </tr>
                            <tr>
                                <th><spring:message code="auctionofferlist.th.startime"/></th>
                                <th><spring:message code="auctionofferlist.th.endtime"/></th>
                                <th><spring:message code="auctionofferlist.th.group"/></th>
                                <th><spring:message code="auctionofferlist.th.name"/></th>
                                <th><spring:message code="auctionofferlist.th.type"/></th>
                                <th><spring:message code="auctionbidslist.th.auctionstatus"/></th>
                            </tr>
                            <tr>
                                <td class="text-hightlight">
                                    <fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionBuyersBean.dailyAuctionsBean.beginTime}"/>
                                </td>
                                <td class="text-hightlight">
                                    <fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionBuyersBean.dailyAuctionsBean.endTime}"/>
                                </td>
                                <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${auctionBuyersBean.dailyAuctionsBean.productCatalogBean.productGroupName}</td>
                                <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${auctionBuyersBean.dailyAuctionsBean.productCatalogBean.productName}</td>
                                <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${auctionBuyersBean.dailyAuctionsBean.productCatalogBean.productTypeName}</td>
                                <td>
                                    <label class="label auction-offer status-${auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}">
                                        <spring:message code="auction.seller.offer.status.${auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}"/>
                                    </label>
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
<!--fahad: added table-dauauction for table colors and font size-->
                <div class="x_panel auction-offer-list-bordered table-responsive">
                    <table class="table table-striped table-bordered table_dayauction" id="offerDetails">
                        <thead>
                            <tr>
                                <th colspan="2"><spring:message code="auctionbidslist.th.sellername"/></th>
                                <th><spring:message code="auctionbidslist.th.partialallowed"/></th>
                                <th><spring:message code="auctionbidslist.th.offerqty"/></th>
                                <th><spring:message code="auctionbidslist.th.availableqty"/></th>
                                <th><spring:message code="auctionbidslist.th.offerprice"/></th>
                                <th><spring:message code="auctionbidslist.th.location"/></th>
                                    <%-- <th><spring:message code="auctionbidslist.th.offerstatus"/></th> --%>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="font-20px">${auctionBuyersBean.auctionSellersBean.accountProfileBean.publicName}</span>
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
                                <td><span class="font-20px">${auctionBuyersBean.auctionSellersBean.offerQuantity}</span></td>
                                <td><span class="font-20px">${auctionBuyersBean.auctionSellersBean.availableQuantity}</span></td>
                                <td class="${auctionBuyersBean.auctionSellersBean.offerPrice > 0 ? '' : 'text-bold text-dark'}">
                                    <span class="font-20px">
                                        <c:choose>
                                            <c:when test="${auctionBuyersBean.auctionSellersBean.offerPrice > 0}">
                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${auctionBuyersBean.auctionSellersBean.offerPrice}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <spring:message code="market"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </td>
                                <td class="font-20px">
                                    <a id="viewLocation" data-latitude="${auctionBuyersBean.auctionSellersBean.accountLocationsBean.latitude}" data-longitude="${auctionBuyersBean.auctionSellersBean.accountLocationsBean.longitude}" class="text-link text-capitalize" data-toggle="tooltip" data-original-title="${auctionBuyersBean.auctionSellersBean.accountLocationsBean.locationName}">
                                        <i class="fa fa-map-marker fa-2"></i>
                                    </a>
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>

                <div class="x_panel">
                    <div class="x_panel">
                        <c:if test="${not empty errorStack}">
                            <div class="error-stack">
                                <c:forEach items="${errorStack}" var="error">
                                    <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
                                </c:forEach>
                            </div>
                        </c:if>
                        <p class="text-danger">
                            <spring:message code="required.msg"/>
                        </p>
                        <form:form action="${contextPath}/bba/bid/savebuyerbids" method="post" class="form-horizontal" modelAttribute="auctionBuyersBean" id="auctionBuyersFrom" enctype="multipart/form-data">
                            <form:hidden path="auctionBuyersId" id="auctionBuyersId"/>
                            <form:hidden path="auctionSellersBean.auctionSellersId" id="auctionSellersId"/>
                            <form:hidden path="auctionSellersBean.offerQuantity" id="offerQuantity"/>
                              <form:hidden path="auctionSellersBean.offerPrice" id="offerPrice"/>
                            <form:hidden path="auctionSellersBean.availableQuantity" id="availableQuantity"/>
                            <form:hidden path="accountProfileBean.accountId" id="accountId"/>
                            <form:hidden path="dailyAuctionsBean.dailyAuctionsId" id="dailyAuctionsId"/>
                            <form:hidden path="dailyAuctionsBean.productCatalogBean.productId" id="productId"/>
                            <form:hidden path="dailyAuctionsBean.auctionSettingsBean.auctionSettingsId" id="auctionSettingsId"/>
                            <form:hidden path="accountProfileBean.bankDetailsBean.accountId" id="bankAccountId"/>
                            <form:hidden path="royaltyValue" id="royaltyValue"/>
                            <form:hidden path="vat" id="vat"/>
                            <form:hidden path="buyerShippingCharge" id="buyerShippingCharge"/>
                            <div class="x_title font-weight-bol">
                                <b>
                                    <spring:message code="buyer.auctionbidplacing.lbl.deliverylocationandofferdetails"/>
                                </b>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label nolpad">
                                    <span class="required"> * </span>
                                    <spring:message code="buyer.auctionbidplacing.lbl.deliverylocation"/>
                                </label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:select class="form-control" path="accountLocationsBean.locationId" id="accountLocationsBean">
                                        <form:option value="">
                                            <spring:message code="buyer.auctionbidplacing.lbl.selectdeliverylocation"/>
                                        </form:option>
                                        <c:forEach items="${buyerLocationList}" var="location">
                                            <form:option value="${location.locationId}">${location.locationName}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="form-group">
                                <spring:message code="buyer.auctionbidplacing.lbl.arrangedforshipping" var="arrangedforshippingphl"/>
                                <label class="control-label"><span class="required"> * </span>${arrangedforshippingphl}</label>
                                &nbsp;
                                <div class="radio inline-block">
                                    <form:radiobutton path="arrangedShipping" id="arrangedShippingNo" disabled="true" value="false"/>
                                    <label for="arrangedShippingNo"><spring:message code="no"/></label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="arrangedShipping" id="arrangedShippingYes" disabled="true" value="true"/>
                                    <label for="arrangedShippingYes"><spring:message code="yes"/></label>
                                </div>                  
                                <div class="clearfix"></div>
                            </div>

                            <div class="form-group">
                                <spring:message code="buyer.auctionbidplacing.lbl.bidquantity" var="bidquantityPhl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${bidquantityPhl}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <%--  1 -11-2018 OR 5-11-2018 Order Execution Policies Level 1--%>
                                    <c:choose>
                                        <c:when test="${not empty auctionBuyersBean.auctionBuyersId  and  auctionBuyersBean.auctionBuyersId  gt 0}">
                                            <form:input class="form-control" path="bidQuantity" id="bidQuantity" placeholder="${bidquantityPhl}" value="${auctionBuyersBean.bidQuantity}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/>
                                            <%-- <p class="read-only">${auctionBuyersBean.bidQuantity}</p> --%>
                                        </c:when>
                                        <c:otherwise> 
                                            <%--  1 -11-2018    <form:hidden path="bidQuantity" id="bidQuantity" value="${auctionBuyersBean.auctionSellersBean.offerQuantity}"/> --%>
                                            <form:input class="form-control" path="bidQuantity" id="bidQuantity" value="${auctionBuyersBean.auctionSellersBean.availableQuantity}"   onkeypress="return valueIsNumber(this,event);" onpaste="return false;"  maxLength="7"/> 
                                            <%--  1 -11-2018  <p class="read-only">${auctionBuyersBean.auctionSellersBean.offerQuantity}</p>  --%>
                                        </c:otherwise>
                                    </c:choose> 
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <div class="form-group">
                                <spring:message code="buyer.auctionbidplacing.lbl.bidprice" var="bidpricePhl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${bidpricePhl}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:input class="form-control" path="bidPrice" id="bidPrice" placeholder="${bidpricePhl}" onkeypress="return valueIsPrice(this,event);" onpaste="return false;" maxLength="7"/>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <div class="form-group">
                                <spring:message code="buyer.auctionbidplacing.lbl.partialExecution" var="partialExecutionPhl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${partialExecutionPhl}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <c:choose>
                                        <c:when test="${(AUCTION_STATUS_OPEN eq auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode || AUCTION_STATUS_RUNNING eq auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode) and (not empty auctionBuyersBean.partialAllowed)}">
                                            <div class="radio inline-block">
                                                <form:hidden path="partialAllowed" id="partialAllowed"/>
                                                <input type="radio" name="partialExecution" id="partialExecutionNo"  ${(empty auctionBuyersBean.partialAllowed || !auctionBuyersBean.partialAllowed) ? 'checked="checked"' : ""}/>
                                                <label for="partialExecutionNo"><spring:message code="no"/></label>
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="radio" name="partialExecution" id="partialExecutionYes"  ${(not empty auctionBuyersBean.partialAllowed && auctionBuyersBean.partialAllowed) ? 'checked="checked"' : ""}/>
                                                <label for="partialExecutionYes"><spring:message code="yes"/></label>

                                                <spring:message code="buyer.auctionbidplacing.lbl.minimumquantity" var="minimumQuantityPhl"/>
                                                <label>${minimumQuantityPhl}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                                <form:input class="form-control inline-block w140" path="minimumQuantity" id="minimumQuantity" disabled="true" placeholder="${minimumQuantityPhl}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="radio inline-block">
                                                <form:hidden path="partialAllowed" id="partialAllowed"/>
                                                <input type="radio" name="partialExecution" id="partialExecutionNo"  ${(empty auctionBuyersBean.partialAllowed || !auctionBuyersBean.partialAllowed) ? 'checked="checked"' : ""}/>
                                                <label for="partialExecutionNo"><spring:message code="no"/></label>
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="radio" name="partialExecution" id="partialExecutionYes"  ${(not empty auctionBuyersBean.partialAllowed && auctionBuyersBean.partialAllowed) ? 'checked="checked"' : ""}/>
                                                <label for="partialExecutionYes"><spring:message code="yes"/></label>

                                                <spring:message code="buyer.auctionbidplacing.lbl.minimumquantity" var="minimumQuantityPhl"/>
                                                <label>${minimumQuantityPhl}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                                <form:input class="form-control inline-block w140" path="minimumQuantity" id="minimumQuantity" disabled="true" placeholder="${minimumQuantityPhl}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <%-- <div class="form-group">
                              <spring:message code="seller.auctionofferplacing.lbl.partialExecution" var="partialExecutionPhl"/>
                              <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${partialExecutionPhl}</label>
                              <div class="col-sm-10 col-md-10 nopad">
                              <c:choose>
                                <c:when test="${(AUCTION_STATUS_OPEN eq auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode || AUCTION_STATUS_RUNNING eq auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode) and (not empty auctionBuyersBean.partialAllowed)}">
                                  <div class="checkbox checkbox-warning">
                                    <form:hidden path="partialAllowed" id="partialAllowed"/>
                                    <input type="checkbox" name="bid_partialAllowed" id="bid_partialAllowed" ${not empty auctionBuyersBean.auctionBuyersId ? 'disabled="disabled"' : ""}/>
                                    <label for="bid_partialAllowed"></label>
                                    &nbsp;&nbsp;
                                    <span class="text-bold" id="minimum_quantity">
                                      <spring:message code="buyer.auctionbidplacing.lbl.minimumquantity"/> : &nbsp;&nbsp;
                                      <form:hidden class="form-control w150 inline-block text-normal" path="minimumQuantity" id="minimumQuantity" placeholder="${bidquantityPhl}" value="0" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7" />
                                      <span class="read-only w150">${auctionBuyersBean.minimumQuantity}</span>
                                    </span>
                                  </div>
                                </c:when>
                                <c:otherwise>
                                  <div class="checkbox checkbox-warning">
                                    <form:hidden path="partialAllowed" id="partialAllowed"/>
                                    <input type="checkbox" name="bid_partialAllowed" id="bid_partialAllowed" ${empty auctionBuyersBean.auctionBuyersId ? 'disabled="disabled"' : ""}/>
                                    <label for="bid_partialAllowed"></label>
                                    &nbsp;&nbsp;
                                    <span class="text-bold" id="minimum_quantity">
                                      <spring:message code="buyer.auctionbidplacing.lbl.minimumquantity"/> : &nbsp;&nbsp;
                                      <form:hidden class="form-control w150 inline-block text-normal" path="minimumQuantity" id="minimumQuantity" placeholder="${bidquantityPhl}" value="0" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/>
                                      <span class="read-only w150">0</span>
                                    </span>
                                  </div>
                                </c:otherwise>
                              </c:choose>
                            </div>
                            <div class="clearfix"></div>
                            </div> --%>
                            <div id="billDetails" class="form-group"></div>
                            <div class="form-group text-right">
                                <spring:message code="btn.update" var="updateBtn"/>
                                <spring:message code="btn.save" var="saveBtn"/>
                                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty auctionBuyersBean.auctionBuyersId ? updateBtn : saveBtn}"/>    
                                <%-- <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.reset"/>' id="clearAll" formnovalidate="formnovalidate" /> --%>
                            	 <input type="button" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.cancel"/>' id="auctionBidCancel" />
                            </div>
                        </form:form>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<div class="mb-10"></div>
<div class="modal fade" id="offerDetailsModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-xlg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title text-capitalize">          
                </h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal">
                    <i class="fa fa-times"></i>&nbsp;&nbsp;
                    <spring:message code="btn.close"/>
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var yes = '<spring:message code="yes"/>';
        var no = '<spring:message code="no"/>';
        $("#accountLocationsBean").focus();
        /*$("#bid_partialAllowed").on("change", function() {
         if ($(this).is(":checked")) {
         $("#partialAllowed").val(0);
         $(this).next().text(no);
         $("#bid_partialAllowed").attr("checked", true);
         $("#bid_partialAllowed").attr("disabled", true);
         //$("#minimum_quantity").addClass("hidden");
         } else {
         $("#partialAllowed").val(0);
         $(this).next().text(no);
         $("#bid_partialAllowed").attr("checked", true);
         $("#bid_partialAllowed").attr("disabled", true);
         ///$("#minimum_quantity").addClass("hidden");
         }
         });*/
        $("#bid_partialAllowed").trigger("change");
        var auctionBuyerId = "${auctionBuyersBean.auctionBuyersId}";
        auctionBuyerId = ("" !== auctionBuyerId ? parseInt(auctionBuyerId) : 0);
        var auctionStatusOpen = parseInt("${ENUM_AuctionStatusCodes.OPEN.getStatus()}");
        var auctionStatusRunning = parseInt("${ENUM_AuctionStatusCodes.RUNNING.getStatus()}");
        var dailyAuctionStatus = parseInt("${auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode}");
        var minPrice = 0.01;
        var maxPrice = 999999.99;
        var maxBidPrice = 999999.99;
        var maxQty = ${auctionBuyersBean.auctionSellersBean.availableQuantity};

        
        if($("#offerPrice").val()!= 0){
        	maxBidPrice = ${auctionBuyersBean.auctionSellersBean.offerPrice};
        }
       
        if (($("#partialAllowed").val() === 0)) {
            if (auctionBuyerId > 0) {
                minQty = parseInt("${auctionBuyersBean.auctionSellersBean.offerQuantity}");
                maxQty = parseInt("${auctionBuyersBean.auctionSellersBean.offerQuantity}");
                minPrice = parseFloat("${auctionBuyersBean.bidPrice}");
                maxPrice = 999999.99;
            } else {
                minQty = parseInt("${auctionBuyersBean.auctionSellersBean.offerQuantity}");
                maxQty = parseInt("${auctionBuyersBean.auctionSellersBean.offerQuantity}");
                minPrice = 0.01;
                maxPrice = 999999.99;
            }
        } else if (($("#partialAllowed").val() === 1)) {
            if (auctionBuyerId > 0) {
                minQty = parseInt("${auctionBuyersBean.bidQuantity}");
                minQty = ("" !== minQty ? parseInt(minQty) : 1);
                maxQty = parseInt("${auctionBuyersBean.auctionSellersBean.offerQuantity}");
                minPrice = parseFloat("${auctionBuyersBean.bidPrice}");
                maxPrice = 999999.99;
            } else {
                minQty = 1;
                maxQty = parseInt("${auctionBuyersBean.auctionSellersBean.offerQuantity}");
                minPrice = 0.01;
                maxPrice = 999999.99;
            }
        }

        $("#auctionBuyersFrom").validate({
            debug: true,
            rules: {
                "accountLocationsBean.locationId": {
                    "required": true
                },
                "bidQuantity": {
                    "required": true,
                    "digits": true,
                    "max":maxQty
                    /*  "min": minQty */
                },
                "bidPrice": {
                    "required": true,
                    "number": true,
                    //"range":[minPrice ,maxPrice]
                    "notEqualZero": true,
                    "min": minPrice,
                    "max": maxBidPrice
                }
            },
            messages: {
                "accountLocationsBean.locationId": {
                    "required": '<spring:message code="buyer.auctionbidplacing.lbl.validation.location.required"/>'
                },
                "bidQuantity": {
                    "required": '<spring:message code="buyer.auctionbidpalcing.lbl.validation.bidquantity.require"/>',
                    "digits": '<spring:message code="buyer.auctionbidplacing.lbl.valication.bidquantity.number"/>',
                    "max":'<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.min"/>'
                    /* "min": '<spring:message code="buyer.auctionbidplacing.lbl.validation.bidquantity.min"/> ' + minQty, */
                },
                "bidPrice": {
                    "required": '<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.require"/>',
                    "number": '<spring:message code="buyer.auctionbidpalcing.lbl.validation.bidprice.number"/>',
                    //"range":'<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.range"/> '+minPrice, /*+"&"+ maxPrice*/
                    "notEqualZero": '<spring:message code="buyer.auctionbidpalcing.lbl.validation.bidprice.equalzero"/>',
                    "min": '<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.min"/> ' + (minPrice.toFixed(2)),
                    "max": '<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.max"/> ' + (maxBidPrice.toFixed(2))
                }
            },
            invalidHandler: function (event, validator) {},
            errorPlacement: function (error, element) {
                if ($(element).is(":radio")) {
                    error.insertAfter(element.parent(".radio"));
                } else {
                    error.insertAfter(element);
                }
            },
            /*  submitHandler: function(form) {
             form.submit();
             $(form).find("input[type=submit]").attr("disabled",true);
             }, */
            highlight: function (element) {
                $(element).addClass("error-element");
            },
            unhighlight: function (element) {
                $(element).removeClass("error-element");
            }
        });

        //Hide/Show and Enable / Disable shipping element as per operation add / update.
        $("#arrangedShippingYes").prop("checked", false);
        $("#arrangedShippingNo").prop("checked", true);
        /*if($("#shipperAccountProfileBean option:selected").val().length > 0){
         $("#arrangedShippingYes").prop("checked",true);
         $("#arrangedShippingNo").prop("checked",false);
         //$(".arrangedShipping").removeClass("hidden");
         $("#shipperAccountProfileBean").rules("add", {
         "required":true,
         messages : 
         {
         "required":'<spring:message code="seller.auctionofferplacing.lbl.validation.shipperselection.required"/>',
         }
         });
         $("#sellerShippingCharge").rules("add", {
         "required":true,
         "number":true,
         messages : 
         {
         "required":'<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.required"/>',
         "number": '<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.number"/>',
         }
         });
         }else{
         $("#arrangedShippingYes").prop("checked",false);
         $("#arrangedShippingNo").prop("checked",true);
         $(".arrangedShipping").removeClass("hidden").addClass("hidden");
         $("#sellerShippingCharge").rules("remove");
         }*/

        //Hide/Show and Enable / Disable shipping element as per change event.
        $("input[name='arrangedShipping']").on("change", function () {

            if ($("#arrangedShippingYes").is(":checked")) {
                /*$("#shipperAccountProfileBean").rules("add", {
                 "required":true,
                 messages : 
                 {
                 "required":'<spring:message code="seller.auctionofferplacing.lbl.validation.shipperselection.required"/>',
                 }
                 });*/

                /*$("#sellerShippingCharge").rules("add", {
                 "required":true,
                 "number":true,
                 messages : 
                 {
                 "required":'<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.required"/>',
                 "number": '<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.number"/>',
                 }
                 });*/
                //$(".arrangedShipping").removeClass("hidden");
            } else if ($("#arrangedShippingNo").is(":checked")) {
                //$(".arrangedShipping").removeClass("hidden").addClass("hidden");
                //$("#shipperAccountProfileBean").rules("remove");
                //$("#sellerShippingCharge").rules("remove");
            }
        });
        $("input[name='arrangedShipping']").trigger("change");

        //Hide/Show and Enable / Disable Partial Execution element as per change event.
        $("input[name='partialExecution']").on("change", function () {
            if ($("#partialExecutionYes").is(":checked")) {
                $("#partialAllowed").val(1);
                $("#minimumQuantity").prop("disabled", false);
                $("#minimumQuantity").prop("readonly", false);
                $("#minimumQuantity").rules("add", {
                    "required": true,
                    "digits": true,
                    "lessThanEqualTo": "#bidQuantity",
                    messages:
                            {
                                "required": '<spring:message code="buyer.auctionbidpalcing.lbl.validation.minimumQuantity.required"/>',
                                "digits": '<spring:message code="buyer.auctionbidpalcing.lbl.validation.minimumQuantity.number"/>',
                                "lessThanEqualTo": '<spring:message code="buyer.auctionbidpalcing.lbl.validation.minimumQuantity.lessthanofferquantity"/>'
                            }
                });
            } else if ($("#partialExecutionNo").is(":checked")) {
                $("#partialAllowed").val(0);
                $("#minimumQuantity").rules("remove");
                $("#minimumQuantity").prop("disabled", true);
                $("#minimumQuantity").prop("readonly", true);
                $("#minimumQuantity").val("${auctionSellersBean.minimumQuantity}");
                /* $("#minimumQuantity").val(""); */
            }
        });
        $("input[name='partialExecution']").trigger("change");

        $("#viewLocation").on("click", function (e) {
            e.preventDefault();
            var latitude = $(this).data("latitude");
            var longitude = $(this).data("longitude");
            var locationName = $(this).data("original-title");
            $.ajax({
                type: "POST",
                async: false,
                cache: false,
                data: {
                    latitude: latitude,
                    longitude: longitude
                },
                url: (contextPath + "/auctions/offerLocation/"),
                success: function (result) {
                    $("#offerDetailsModal .modal-title").text(locationName);
                    $("#offerDetailsModal .modal-body").empty().html(result);
                    $("#offerDetailsModal").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#offerDetailsModal .modal-body").empty().html(e);
                    $("#offerDetailsModal").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        });
        $("#bidPrice, #bidQuantity").blur(function (e) {
            e.preventDefault();
            var bidPrice = $("#bidPrice").val();
            var bidQuantity = $("#bidQuantity").val();
            if ((bidPrice !== 0 && bidPrice !== "") && (bidQuantity !== 0 && bidQuantity !== "")) {
                var offerPrice = parseFloat("${auctionBuyersBean.auctionSellersBean.offerPrice}");
                var vat = parseFloat("${auctionBuyersBean.vat}");
                var royalty = parseFloat("${auctionBuyersBean.royaltyValue}");
                var shipping = parseFloat("${auctionBuyersBean.buyerShippingCharge}");
                var gross = 0.00;
                if (offerPrice !== 0 && bidPrice > offerPrice) {
                    gross = offerPrice * bidQuantity;
                } else {
                    gross = bidPrice * bidQuantity;
                }
                var royaltyCal = gross * royalty / 100;
                var vatCal = gross * vat / 100;
                var total = gross + vatCal + royaltyCal + shipping;
                $("#billDetails").html("");
//	   fahad: bill details formatting
//	   $("#billDetails").append('<hr/><p class="text-success mb-0"><label class="w120 text-right"><spring:message code="buyer.auctionbidplacing.lbl.GrossPurchase"/></label>' + "<b>&nbsp;&nbsp;:&nbsp;&nbsp;" +formatPriceValue(gross.toFixed(2)) + '</b></p>');      
                $("#billDetails").append('<hr/><p class="text-success mb-0"><label class="w120 text-right"><spring:message code="buyer.auctionbidplacing.lbl.GrossPurchase"/></label>' + "<b style='font-size:20px'>:&nbsp;&nbsp;" + formatPriceValue(gross.toFixed(2)) + '</b></p>');
                $("#billDetails").append('<p class="text-success mb-0"><label class="w120 text-right"><spring:message code="buyer.auctionbidplacing.lbl.Commission"/> (' + royalty + '%)</label>' + "<b style='font-size:20px'>:&nbsp;&nbsp;" + formatPriceValue(royaltyCal.toFixed(2)) + '</b></p>');
                $("#billDetails").append('<p class="text-success mb-0"><label class="w120 text-right"><spring:message code="buyer.auctionbidplacing.lbl.VAT"/> (' + vat + '%)</label>' + "<b style='font-size:20px'>:&nbsp;&nbsp;" + formatPriceValue(vatCal.toFixed(2)) + '</b></p>');
                $("#billDetails").append('<p class="text-success mb-0"><label class="w120 text-right"><spring:message code="buyer.auctionbidplacing.lbl.ShippingCharge"/></label>' + "<b style='font-size:20px'>:&nbsp;&nbsp;" + formatPriceValue(shipping.toFixed(2)) + '</b></p>');
                $("#billDetails").append('<hr/><p class="text-success mb-0"><label class="w120 text-right"><spring:message code="buyer.auctionbidplacing.lbl.Total"/></label>' + "<b style='font-size:20px'>:&nbsp;&nbsp;" + formatPriceValue(total.toFixed(2)) + '</b></p>');
            } else {
                if (bidPrice === 0 || bidPrice === "") {
                    $("#bidPrice").val("");
                }
                if (bidQuantity === 0 || bidQuantity === "") {
                    $("#bidQuantity").val("");
                }
                $("#billDetails").html("");
            }
        });
        $("#bidPrice").blur();
        $("#clearAll").on("click", function () {
            $("#auctionBuyersFrom").validate().resetForm();
            $("#bidQuantity").trigger("blur");
        });
        $("#auctionBidCancel").on("click",function (){
        	  window.location.href = (contextPath + "/auctions/syncbids/${auctionBuyersBean.auctionSellersBean.auctionSellersId}");
        });
    });
</script>