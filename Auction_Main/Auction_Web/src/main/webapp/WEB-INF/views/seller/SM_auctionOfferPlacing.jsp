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
<%--<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}&nbsp;</label><br/>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productDescription}</p><br/><p class='show-product-info'><label>${productContainerSpecsPhl}&nbsp;</label><br/>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.containerSpecs}</p>"/>--%>
       <c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}:&nbsp;</label>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productDescription}</p><p class='show-product-info'><label>${productContainerSpecsPhl}:&nbsp;</label>${auctionSellersBean.dailyAuctionsBean.productCatalogBean.containerSpecs}</p>"/>

<!--<style>
    table tr:nth-child(1) th{color: #333 !important;}
    table tr:nth-child(2) td{color: #8e0000 !important;font-size: 13px;font-weight: bolder;text-align:center;}
</style>-->
<style>
    table#dailyAuctionDetails tr:nth-child(2) td{text-align:center;color: #8e0000 !important;font-weight: bolder;font-size: 20px;}
    table#dailyAuctionDetails tr:nth-child(2) td:last-child{color: #8e0000 !important;}
    /*table#dailyAuctionDetails tr:nth-child(2) td:last-child{background-color: #26883f !important;color: #fff !important;vertical-align: middle !important;}*/
    table#dailyAuctionDetails tr:nth-child(3) td{color: #333 !important;}
    table#dailyAuctionDetails tbody tr td:not(:first-child):nth-child(n+1){text-align:center;}
    table#datatable tbody tr td:nth-child(n+3){text-align:center;}
</style>
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><a href="${contextPath}/auctions/auctionList"><spring:message code="menu.auction.dayauction"/></a></li>
        <li>
            <a href="${contextPath}/auctions/offers/${auctionSellersBean.dailyAuctionsBean.dailyAuctionsId}">
                <spring:message code="menu.auction.dayauction.sellers.offers"/>
            </a>
        </li>
        <li><spring:message code="seller.auctionofferplacing.h3.auctionselleroffer"/></li>
        <li class="todaydatetime">
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
            <span id="current-time">
                <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
            </span>
        </li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_center">
                <h3><spring:message code="auctionlist.th.dailyauctions"/></h3>
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
                <div class="x_content x_panel">
                    <div class="auction-list-bordered table-responsive">
                        <table class="table table-striped table-bordered" id="dailyAuctionDetails">
                            <thead>
                                <%-- <tr>
                                  <th colspan="6"><spring:message code="auctionlist.th.dailyauctions"/></th>
                                </tr> --%>
                                <tr>
                                    <th><spring:message code="auctionofferlist.th.startime"/></th>
                                    <th><spring:message code="auctionofferlist.th.endtime"/></th>
                                    <th><spring:message code="auctionofferlist.th.group"/></th>
                                    <th><spring:message code="auctionofferlist.th.name"/></th>
                                    <th><spring:message code="auctionofferlist.th.type"/></th>
                                </tr>
                                <tr>
                                    <!--fahad: fix the date format for Arabic-->
                                    <!--                  <td><span id="todaydatetime">
                                    <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm a' value="${auctionSellersBean.dailyAuctionsBean.beginTime}"/>
                              </span></td>-->
                                    <td><fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionSellersBean.dailyAuctionsBean.beginTime}"/></td>
                                    <td><fmt:formatDate type="both" timeStyle="long" pattern='hh:mm a' value="${auctionSellersBean.dailyAuctionsBean.endTime}"/></td>
                                    <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productGroupName}</td>
                                    <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productName}</td>
                                    <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${auctionSellersBean.dailyAuctionsBean.productCatalogBean.productTypeName}</td>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" id="syncsaveofferdetails">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="page-title">
                    <div class="title_center">
                        <h3><spring:message code="seller.auctionofferplacing.h3.auctionselleroffer"/></h3>
                    </div>
                </div>
                <div class="x_panel">
                    <p class="text-danger"><spring:message code="required.msg"/></p>
                    <c:if test="${not empty errorStack}">
                        <div class="error-stack">
                            <c:forEach items="${errorStack}" var="error">
                                <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
                            </c:forEach>
                        </div>
                    </c:if> 
                    <form:form action="${contextPath}/ssa/offer/syncsaveofferdetails" class="form-horizontal normalPageContentLoading" method="post" modelAttribute="auctionSellersBean" id="auctionSellersOfferPlacingFrom" enctype="multipart/form-data">
                        <form:hidden path="auctionSellersId" id="auctionSellersId"/>
                        <form:hidden path="availableQuantity" id="availableQuantity"/>
                        <form:hidden path="royaltyValue" id="royaltyValue"/>
                        <form:hidden path="vat" id="vat"/>
                        <form:hidden path="accountProfileBean.accountId" id="accountId"/>
                        <form:hidden path="dailyAuctionsBean.dailyAuctionsId" id="dailyAuctionsId"/>
                        <form:hidden path="dailyAuctionsBean.productCatalogBean.productId" id="productId"/>
                        <form:hidden path="sellerOfferInfoBean.sellerOfferInfoId" id="sellerOfferInfoId"/>
                        <form:hidden path="dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode" id="auctionStatusCode"/>
                        <div class="x_panel">
                            <div class="x_title font-weight-bol">
                                <h2><spring:message code="seller.auctionofferplacing.lbl.pickuplocationandofferdetails"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="seller.auctionofferplacing.lbl.pickuplocation"/></label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:select class="form-control" path="accountLocationsBean.locationId" id="accountLocationsBean">
                                        <form:option value=""><spring:message code="seller.auctionofferplacing.lbl.selectpickuplocation"/></form:option>
                                        <c:forEach items="${sellerLocationList}" var="location">
                                            <form:option value="${location.locationId}">${location.locationName}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <form:hidden path="allowShipperSelection" id="allowShipperSelection"/>
                             <c:choose>
    		       <c:when test="${!auctionSellersBean.allowShipperSelection}">
      			   <div class="form-group">
                                <spring:message code="seller.auctionofferplacing.lbl.arrangedforshipping" var="arrangedforshipping"/>
                                <label class="control-label"><span class="required"> * </span>${arrangedforshipping}</label>
                                &nbsp;
                                  <div class="radio inline-block">
                                    <form:radiobutton path="arrangedShipping" id="arrangedShippingNo" value="false" disabled="true"/>
                                    <label for="arrangedShippingNo"> <spring:message code="no"/></label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
            
                                    <form:radiobutton path="arrangedShipping" id="arrangedShippingYes" value="true" disabled="true"/>
                                    <label for="arrangedShippingYes"><spring:message code="yes"/></label>
                                </div>     
                                <div class="clearfix"></div>
                            </div>
                            
                               <div class="form-group arrangedShipping <c:if test="${empty auctionSellersBean.shipperAccountProfileBean || empty auctionSellersBean.shipperAccountProfileBean.accountId}">hidden</c:if>">
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="seller.auctionofferplacing.lbl.selectshipper"/></label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:select class="form-control editable-field" path="shipperAccountProfileBean.accountId" id="shipperAccountProfileBean" disabled="true">
                                        <form:option value=""><spring:message code="seller.auctionofferplacing.lbl.selectshipper"/></form:option>
                                        <c:forEach items="${shipperList}" var="shipper">
                                            <form:option value="${shipper.accountId}">${shipper.fullName} (${shipper.publicName})</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                             
                            <div class="form-group arrangedShipping <c:if test="${empty auctionSellersBean.shipperAccountProfileBean || empty auctionSellersBean.shipperAccountProfileBean.accountId}">hidden</c:if>">
                                <spring:message code="seller.auctionofferplacing.lbl.shipperCharge" var="shipperCharge"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${shipperCharge}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:input class="form-control editable-field" path="sellerShippingCharge" id="sellerShippingCharge" placeholder="${shipperCharge}" onkeypress="return valueIsPrice(this,event);" onpaste="return false;" maxLength="6"  minFractionDigits="2"  maxFractionDigits="2" disabled="true" />
                                </div>
                                <div class="clearfix"></div>
                                <hr class="hr">
                            </div>
                            
    		</c:when>
    				   <c:otherwise>
                            <div class="form-group">
                                <spring:message code="seller.auctionofferplacing.lbl.arrangedforshipping" var="arrangedforshipping"/>
                                <label class="control-label"><span class="required"> * </span>${arrangedforshipping}</label>
                                &nbsp;
                                <div class="radio inline-block">
                                    <form:radiobutton path="arrangedShipping" id="arrangedShippingNo" value="false"/>
                                    <label for="arrangedShippingNo"><spring:message code="no"/></label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="arrangedShipping" id="arrangedShippingYes" value="true"/>
                                    <label for="arrangedShippingYes"><spring:message code="yes"/></label>
                                </div>     
                                <div class="clearfix"></div>
                            </div>
							
                            <div class="form-group arrangedShipping <c:if test="${empty auctionSellersBean.shipperAccountProfileBean || empty auctionSellersBean.shipperAccountProfileBean.accountId}">hidden</c:if>">
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="seller.auctionofferplacing.lbl.selectshipper"/></label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:select class=" form-control editable-field" path="shipperAccountProfileBean.accountId" id="shipperAccountProfileBean">
                                        <form:option value=""><spring:message code="seller.auctionofferplacing.lbl.selectshipper"/></form:option>
                                        <c:forEach items="${shipperList}" var="shipper">
                                            <form:option value="${shipper.accountId}">${shipper.fullName} (${shipper.publicName})</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                             
                            <div class="form-group arrangedShipping <c:if test="${empty auctionSellersBean.shipperAccountProfileBean || empty auctionSellersBean.shipperAccountProfileBean.accountId}">hidden</c:if>">
                                <spring:message code="seller.auctionofferplacing.lbl.shipperCharge" var="shipperCharge"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${shipperCharge}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:input class="form-control editable-field" path="sellerShippingCharge" id="sellerShippingCharge" placeholder="${shipperCharge}" onkeypress="return valueIsPrice(this,event);" onpaste="return false;" maxLength="6"  minFractionDigits="2"  maxFractionDigits="2"/>
                                </div>
                                <div class="clearfix"></div>
                                <hr class="hr">
                            </div>
						</c:otherwise>
					</c:choose>
                            <div class="form-group">
                                <spring:message code="seller.auctionofferplacing.lbl.offerquantity" var="offerquantityPhl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${offerquantityPhl}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <c:choose>
                                        <c:when test="${not empty auctionSellersBean.auctionSellersId and auctionSellersBean.auctionSellersId gt 0}">
                                            <p class="read-only">${auctionSellersBean.offerQuantity}</p>
                                            <form:hidden class="form-control" path="offerQuantity" id="offerQuantity"/>                    	
                                        </c:when>
                                        <c:otherwise>
                                            <form:input class="form-control editable-field" path="offerQuantity" id="offerQuantity" placeholder="${offerquantityPhl}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <div class="form-group">
                                <spring:message code="seller.auctionofferplacing.lbl.partialExecution" var="partialAllowedPhl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${partialAllowedPhl}</label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <c:choose>
                                        <c:when test="${(AUCTION_STATUS_OPEN eq auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode || AUCTION_STATUS_RUNNING eq auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode) and (not empty auctionSellersBean.partialAllowed)}">
                                            <div class="radio inline-block">
                                                <%--  <input type="radio" name="offerPartialExecution" id="offerPartialExecutionNo"  ${(empty auctionSellersBean.partialAllowed || !partialAllowed) ? 'checked="checked"' : ""}/>
                                                 <label for="offerPartialExecutionNo"><spring:message code="no"/></label>
                                                 &nbsp;&nbsp;&nbsp;&nbsp;
                                                 <form:hidden path="partialAllowed" id="partialAllowed"/>
                                                 <input type="radio" name="offerPartialExecution" id="offerPartialExecutionYes"  ${(not empty auctionSellersBean.partialAllowed && partialAllowed) ? 'checked="checked"' : ""}/>
                                                 <label for="offerPartialExecutionYes"><spring:message code="yes"/></label> --%>
                                                <form:radiobutton path="partialAllowed" id="partialAllowedNo" value="false"/>
                                                <label for="partialAllowedNo"><spring:message code="no"/></label>
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <form:radiobutton path="partialAllowed" id="partialAllowedYes" value="true"/>
                                                <label for="partialAllowedYes"><spring:message code="yes"/></label>
                                                <%--   29 -10-2018 changes --  Minimum quantity (MQ) not applicable for sellers. %>
                                                <%--   
                                                  <spring:message code="seller.auctionofferplacing.lbl.minimumQuantity" var="minimumQuantityPhl"/>
                                                  <label>${minimumQuantityPhl}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                                  <form:input class="form-control inline-block w140" path="minimumQuantity" id="minimumQuantity" disabled="true" placeholder="${minimumQuantityPhl}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/> --%>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="radio inline-block">
                                                <form:hidden path="partialAllowed" id="partialAllowed"/>
                                                <%-- 	  <input type="radio" name="offerPartialExecution" id="offerPartialExecutionNo"  ${(empty auctionSellersBean.partialAllowed || !partialAllowed) ? 'checked="checked"' : ""}/>
                                      <label for="offerPartialExecutionNo"><spring:message code="no"/></label>
                                      &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="offerPartialExecution" id="offerPartialExecutionYes" ${(not empty auctionSellersBean.partialAllowed && partialAllowed) ? 'checked="checked"' : ""}/>
                                      <label for="offerPartialExecutionYes"><spring:message code="yes"/></label> --%>
                                                <form:radiobutton path="partialAllowed" id="partialAllowedNo" value="false"/>
                                                <label for="partialAllowedNo"><spring:message code="no"/></label>
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <form:radiobutton path="partialAllowed" id="partialAllowedYes" value="true"/>
                                                <label for="partialAllowedYes"><spring:message code="yes"/></label>
                                                <%--   29 -10-2018 changes --  Minimum quantity (MQ) not applicable for sellers. %>   
                                                <%-- <spring:message code="seller.auctionofferplacing.lbl.minimumQuantity" var="minimumQuantityPhl"/>
                                                <label>${minimumQuantityPhl}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                                <form:input class="form-control inline-block w140" path="minimumQuantity" id="minimumQuantity" disabled="true" placeholder="${minimumQuantityPhl}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="7"/> --%>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <div class="form-group">
                                <spring:message code="seller.auctionofferplacing.lbl.offerprice" var="offerpricePhl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${offerpricePhl}</label>
                                <div class="col-sm-10 col-md-10 nopad">                  
                                    <div class="radio inline-block">
                                        <input type="radio" name="offerPricing" id="highestPrice" />
                                        <label for="highestPrice"><spring:message code="seller.auctionofferplacing.lbl.highestPrice"/></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" name="offerPricing" id="limitPrice" />
                                        <label for="limitPrice"><spring:message code="seller.auctionofferplacing.lbl.limitPrice"/></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <form:input class="form-control inline-block w140" path="offerPrice" id="offerPrice" placeholder="${offerpricePhl}" onkeypress="return valueIsPrice(this,event);" onpaste="return false;" maxLength="7" disabled="true"/>
                                    </div>

                                </div>
                                <div class="clearfix"></div>
                                
                     <div class="form-group">
                <spring:message code="seller.auctionofferplacing.lbl.truckNumber" var="truckNumberPhl"/>
                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${truckNumberPhl}</label>
                <div class="col-sm-10 col-md-10 nopad">
                <form:input class="form-control" path="truckNumber" id="truckNumber" placeholder="${truckNumberPhl}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="2"/>
              </div>
              <div class="clearfix"></div>
              </div>      
                 </div>

                        </div>
                        <div class="x_panel">
                            <div class="x_title font-weight-bol">
                                <h2><spring:message code="seller.auctionofferplacing.lbl.SellerOfferInformation"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <spring:message code="seller.auctionofferplacing.lbl.infoofferline1" var="infoofferline1phl"/>
                            <spring:message code="seller.auctionofferplacing.lbl.infoofferline2" var="infoofferline2phl"/>
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="seller.auctionofferplacing.lbl.infoofferline1"/></label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:textarea class="form-control editable-field font-24px" path="sellerOfferInfoBean.infoLine1" id="infoLine1" rows="3" placeholder="${infoofferline1phl}" maxlength="255" />
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="seller.auctionofferplacing.lbl.infoofferline2"/></label>
                                <div class="col-sm-10 col-md-10 nopad">
                                    <form:textarea class="form-control editable-field" path="sellerOfferInfoBean.infoLine2" id="infoLine2" rows="3" placeholder="${infoofferline2phl}" maxlength="255" />
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        
                         <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title font-weight-bol">
                           <div class="x_title font-weight-bol">
                                <h2> <spring:message code="seller.auctionofferplacing.lbl.SellerOfferPictures"/></h2>
                                <div class="clearfix"></div>
                            </div>
                        <div class="modal-body">
            <div class="form-group text-right">
                <button class="btn btn-lg btn-warning button-left" id="uploadNewImage"><i class="fa fa-plus"></i>&nbsp;&nbsp;<b><spring:message code="btn.add"/></b></button>
            </div>
            <div class="seller-picture-container nopad text-center">
            </div>
            <div class="clearfix"></div>
        </div>
           </div>
              </div>
                 </div>
                    </div>
                    
                              <div class="x_content">
                        <div class="seller-offer-picture-thumbs">
                            <spring:message code="btn.remove" var="removebtn"/>
                            <c:forEach items="${sellerOfferPicturesBeanList}" var="sellerOfferPicturesBean">
                                <div class="seller-offer-picture-thumb" id="img-${sellerOfferPicturesBean.pictureId}">
                                    <img src="${contextPath}/auctions/offerpictures/${sellerOfferPicturesBean.accountProfileBean.accountId}/${sellerOfferPicturesBean.pictureLocation}" onclick= "imgClick(this)" />
                                    <br>
                                    <button type="button" class="btn btn-danger btn-lg remove" data-toggle="tooltip" data-original-title="${removebtn}" onclick="imgDelete(${sellerOfferPicturesBean.pictureId})">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </c:forEach>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    
      
                        <div class="form-group text-right">
                            <spring:message code="btn.update" var="updateBtn"/>
                            <spring:message code="btn.save" var="saveBtn"/>
                            <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty auctionSellersBean.auctionSellersId ? updateBtn : saveBtn}"/>
                            <%-- <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.reset"/>' id="clearAll" formnovalidate="formnovalidate" /> --%>
                        	 <input type="button" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.cancel"/>' id="auctionOfferCancel" />
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <%-- <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title font-weight-bol">
                        <h2><spring:message code="seller.auctionofferplacing.lbl.SellerOfferPictures"/></h2>
                        <button id="btnSellerOfferPictureUpload" class="btn btn-warning btn-lg">
                            <i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;
                            <spring:message code="seller.auctionofferplacing.lbl.uploadpictures"/>
                        </button>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <div class="seller-offer-picture-thumbs">
                            <spring:message code="btn.remove" var="removebtn"/>
                            <c:forEach items="${sellerOfferPicturesBeanList}" var="sellerOfferPicturesBean">
                                <div class="seller-offer-picture-thumb" id="img-${sellerOfferPicturesBean.pictureId}">
                                    <img src="${contextPath}/auctions/offerpictures/${sellerOfferPicturesBean.accountProfileBean.accountId}/${sellerOfferPicturesBean.pictureLocation}" onclick= "imgClick(this)" />
                                    <br>
                                    <button type="button" class="btn btn-danger btn-lg remove" data-toggle="tooltip" data-original-title="${removebtn}" onclick="imgDelete(${sellerOfferPicturesBean.pictureId})">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </c:forEach>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div> --%>
    </div>
</div>
<div class="modal fade" id="sellerPictureModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-xlg"></div>
</div>
<script type="text/javascript">

function afterAddEffect() {
    var dataId = $(".seller-picture .seller-picture-details").length;
    if ((dataId != 0) && ("" == $(".seller-picture input[id^=sellerPicture]:last").val())) {
        $(".seller-picture input[id^=sellerPicture]:last").parents(".seller-picture-details").addClass("error-element")
    } else {
        dataId = dataId + 1;
        var sellerPictureDetails = '<div class="seller-picture"><div class="seller-picture-details" data-id="' + dataId + '">' +
            '<button class="remove label label-danger">&times;</button>' +
            '<div class="form-group text-center">' +
            '<input id="sellerPicture' + dataId + '" data-min-file-count="0" data-show-caption="false" class="file" type="file" name="sellerPicture" accept="image/*"/>' +
            '</div>' +
            '</div><label class="error"><spring:message code="seller.sellerpictureupload.validation.selectimage"/></label></div>';
      		$(".seller-picture-container").append(sellerPictureDetails);
        	$((".seller-picture #sellerPicture" + dataId)).fileinput({
            removeClass: "btn btn-danger btn-sm mt5",
            removeLabel: "",
            showRemove: false,
            browseLabel: "&nbsp;<spring:message code='browse'/>&nbsp;...",
            browseClass: "btn btn-info btn-lg mt5 "
        });
        $((".seller-picture #sellerPicture" + dataId)).on("change", function() {
            $(this).parents(".seller-picture-details").removeClass("error-element");
        });

        $(".seller-picture-details[data-id=" + dataId + "] .remove").on("click", function(e) {
            e.preventDefault();
            if (confirm('<spring:message code="seller.sellerpictureupload.validation.deletemsg"/>')) {
                $(this).parents(".seller-picture").remove();
            }
        });
    }
}
    $(document).ready(function () {
    	  afterAddEffect();
        window.history.pushState("object or string", "EPAP|AuctionList", contextPath + "/auctions/auctionlist");
        var auctionStatusOpen =${ENUM_AuctionStatusCodes.OPEN.getStatus()};
        var auctionStatusRunning =${ENUM_AuctionStatusCodes.RUNNING.getStatus()};
        var dailyAuctionStatus =${auctionSellersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode};
        var auctionSellersId = "${auctionSellersBean.auctionSellersId}";
        auctionSellersId = ("" != auctionSellersId ? parseInt(auctionSellersId) : 0);

        var minQty = 1;
        var maxQty = 9999999;
        var minPrice = 0.01;
        var maxPrice = 999999.99;
        var minShipperPrice = 0.0;
        var maxShipperPrice = 9999.99;
        //Calculating minimum quantity and maximum quantity.
        //Calculating minimum offer price and maximum offer price.
        if (($("#partialAllowed").val() == 0)) {
            if (auctionSellersId > 0) {
                minQty = "${auctionSellersBean.offerQuantity}";
                minQty = ("" != minQty ? parseInt(minQty) : 1);
                maxQty = (minQty == 1 ? maxQty : minQty);
                minPrice = parseFloat("${auctionSellersBean.offerPrice}");
                maxPrice = parseFloat("${auctionSellersBean.offerPrice}");
            } else {
                minQty = 1;
                maxQty = 9999999;
                minPrice = 0.01;
                maxPrice = 999999.99;
            }
        } else if (($("#partialAllowed").val() == 1)) {
            if (auctionSellersId > 0) {
                if (dailyAuctionStatus == auctionStatusOpen) {
                    minQty = 1;
                    maxQty = 9999999;
                    minPrice = parseFloat("${auctionSellersBean.offerPrice}");
                    maxPrice = parseFloat("${auctionSellersBean.offerPrice}");
                } else if (dailyAuctionStatus == auctionStatusRunning) {
                    minQty = 1;
                    maxQty = 9999999;
                    minPrice = parseFloat("${auctionSellersBean.offerPrice}");
                    maxPrice = parseFloat("${auctionSellersBean.offerPrice}");
                }
            } else {
                minQty = 1;
                maxQty = 9999999;
                minPrice = 0.01;
                maxPrice = 999999.99;
            }
        }
      
        $("#uploadNewImage").on("click", function(e) {
            e.preventDefault();
            afterAddEffect();
        });
        $.validator.addMethod('filesize', function(value, element, param) {
     	   return this.optional(element) || (element.files[0].size <= param) 
     	});   

       /*  $("#auctionSellersOfferPlacingFrom").validate({
            debug: true,
            rules: {
                "sellerPicture": {
                	 "required": true,
                     	filesize: 4194304,
         	            accept: {
         	                type: "image",
         	                extension: "png|jpg|jpeg|PNG|JPG"
         	            }
         	          
                }
                },
            messages: {
                "sellerPicture": {
                	"required":  '<spring:message code="seller.auctionofferplacing.lbl.validation.selectimage"/>' ,
               	   "accept": '<spring:message code="editProfile.validation.image.accept"></spring:message>',
               	   "filesize":'<spring:message code="seller.sellerpictureupload.file.size"></spring:message>',
                }
            },
            errorPlacement: function (error, element) {
                if ($(element).attr("type") == "file") {
                    error.insertAfter(element.parents(".file-input"));
                } else {
                    error.insertAfter(element);
                }
            },
            submitHandler: function (form) {
                form.submit();
                $(form).find("input[type=submit]").attr("disabled", true);
            },
            highlight: function (element) {
                if ($(element).attr("type") == "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").addClass("error-file-element");
                } else {
                    $(element).addClass("error-element");
                }
            },
            unhighlight: function (element) {
                if ($(element).attr("type") == "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").removeClass("error-file-element");
                } else {
                    $(element).removeClass("error-element");
                }
            }
        }); */
        
        //Binding select2 plugin which element has select2 class.
        $('.select2').select2();

        $("#clearAll").on("click", function () {
            $("#auctionSellersOfferPlacingFrom").validate().resetForm();
        });
        $("#auctionOfferCancel").on("click", function () {
        	  window.location.href = (contextPath + "/auctions/syncoffers/${auctionSellersBean.dailyAuctionsBean.dailyAuctionsId}");
        });
        
        //Form Validations.
        $("#auctionSellersOfferPlacingFrom").validate({
            ignore: [],
            debug: true,
            rules: {
                "accountLocationsBean.locationId": {
                    "required": true,
                },
                "arrangedShipping": {
                    "required": true,
                },
                "offerQuantity": {
                    "required": true,
                    "digits": true,
                    "min": minQty,
                    "max": maxQty
                },
                "offerPartialExecution": {
                    "required": true,
                },
                "offerPricing": {
                    "required": true,
                },
                "sellerOfferInfoBean.infoLine1": {
                    "required": true,
                    "maxlength": 255
                },
                "sellerOfferInfoBean.infoLine2": {
                    "maxlength": 255
                },
                "truckNumber":{
                	"required": true,
                    "digits": true,
                    "notEqualZero":true,
                },
                "sellerPicture": {
               	// "required": true,
                    	filesize: 4194304,
        	            accept: {
        	                type: "image",
        	                extension: "png|jpg|jpeg|PNG|JPG"
        	            }
               },
            },
            messages: {
                "accountLocationsBean.locationId": {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.location.required"/>',
                },
                "arrangedShipping": {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.arrangedforshipping.required"/>',
                },
                "offerQuantity": {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerQuantity.required"/>',
                    "digits": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerQuantity.number"/>',
                    "min": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerQuantity.minimum"/> ' + minQty,
                    "max": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerQuantity.maximum"/> ' + maxQty
                },
                "partialExecution": {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.partialexecution.required"/>',
                },
                "offerPricing": {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.partialexecution.required"/>',
                },
                "sellerOfferInfoBean.infoLine1": {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.info1.required"/>',
                    "maxlength": '<spring:message code="seller.auctionofferplacing.lbl.validation.info1.maxlength"/>'
                },
                "sellerOfferInfoBean.infoLine2": {
                    "maxlength": '<spring:message code="seller.auctionofferplacing.lbl.validation.info2.maxlength"/>'
                },
                "truckNumber":{
                "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.truckNumber.required"/>',
                "digits": '<spring:message code="seller.auctionofferplacing.lbl.valication.truckNumber.number"/>',
                "notEqualZero":'<spring:message code="seller.auctionofferplacing.lbl.validation.truckNumber.equalzero"/>',
             
                },
                "sellerPicture": {
                	"required":  '<spring:message code="seller.auctionofferplacing.lbl.validation.selectimage"/>' ,
               	   "accept": '<spring:message code="editProfile.validation.image.accept"></spring:message>',
               	   "filesize":'<spring:message code="seller.sellerpictureupload.file.size"></spring:message>',
                },
			},
            invalidHandler: function (event, validator) {

            },
            errorPlacement: function (error, element) {
            	  if ($(element).attr("type") == "file") {
                      error.insertAfter(element.parents(".file-input"));
                  } else {
                      error.insertAfter(element);
                  }
                if ($(element).is(":radio")) {
                    error.insertAfter(element.parent(".radio"));
                } else if ($(element).hasClass("select2")) {
                    error.insertAfter($(element).next("span"));
                } else {
                    error.insertAfter(element);
                }
            },
          
            submitHandler: function (form) {
                form.submit();
                $(form).find("input[type=submit]").attr("disabled", true);
            },
            
            highlight: function (element) {
                if ($(element).hasClass("select2")) {
                    $("span[aria-labelledby='" + ("select2-" + $(element).prop("id") + "-container") + "']").addClass("error-element");
                } else {
                    $(element).addClass("error-element");
                }  if ($(element).attr("type") == "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").addClass("error-file-element");
                } else {
                    $(element).addClass("error-element");
                }
            },
            unhighlight: function (element) {
                if ($(element).hasClass("select2")) {
                    $("span[aria-labelledby='" + ("select2-" + $(element).prop("id") + "-container") + "']").removeClass("error-element");
                } else {
                    $(element).removeClass("error-element");
                } if ($(element).attr("type") == "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").removeClass("error-file-element");
                } else {
                    $(element).removeClass("error-element");
                }
            }
        });

        //Hide/Show and Enable / Disable shipping element as per operation add / update. 
        if ($("#shipperAccountProfileBean option:selected").val().length > 0) {
            $("#arrangedShippingYes").prop("checked", true);
            $("#arrangedShippingNo").prop("checked", false);
            $(".arrangedShipping").removeClass("hidden");
            $("#shipperAccountProfileBean").rules("add", {
                "required": true,
                messages:
                        {
                            "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.shipperselection.required"/>',
                        }
            });
            $("#sellerShippingCharge").rules("add", {
                "required": true,
                "number": true,
                messages:
                        {
                            "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.required"/>',
                            "number": '<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.number"/>',
                        }
            });
        } else {
            $("#arrangedShippingYes").prop("checked", false);
            $("#arrangedShippingNo").prop("checked", true);
            $(".arrangedShipping").removeClass("hidden").addClass("hidden");
            $("#sellerShippingCharge").rules("remove");
        }

        //Hide/Show and Enable / Disable shipping element as per change event.
        $("input[name='arrangedShipping']").on("change", function () {
            if ($("#arrangedShippingYes").is(":checked")) {
                $("#shipperAccountProfileBean").rules("add", {
                    "required": true,
                    messages:
                            {
                                "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.shipperselection.required"/>',
                            }
                });

                $("#sellerShippingCharge").rules("add", {
                    "required": true,
                    "number": true,
                    messages:
                            {
                                "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.required"/>',
                                "number": '<spring:message code="seller.auctionofferplacing.lbl.validation.sellershippercharge.number"/>',
                            }
                });
                $(".arrangedShipping").removeClass("hidden");
            } else if ($("#arrangedShippingNo").is(":checked")) {
                $(".arrangedShipping").removeClass("hidden").addClass("hidden");
                $("#shipperAccountProfileBean").rules("remove");
                $("#sellerShippingCharge").rules("remove");
            }
        });
        $("input[name='arrangedShipping']").trigger("change");

        //Settomg for Hide/Show and Enable / Disable Partial Execution element as per operation add / update.
        if ("" != $("#partialAllowed").val() && 1 == $("#partialAllowed").val()) {
            $("#minimumQuantity").rules("add", {
                "required": true,
                "digits": true,
                "lessThanEqualTo": "#offerQuantity",
                messages:
                        {
                            "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.minimumQuantity.required"/>',
                            "digits": '<spring:message code="seller.auctionofferplacing.lbl.validation.minimumQuantity.number"/>',
                            "lessThanEqualTo": '<spring:message code="seller.auctionofferplacing.lbl.validation.minimumQuantity.lessthanofferquantity"/>',
                        }
            });
        }

        //Hide/Show and Enable / Disable Partial Execution element as per change event.
        $("input[name='offerPartialExecution']").on("change", function () {
            if ($("#offerPartialExecutionYes").is(":checked")) {
                $("#partialAllowed").val(1);
                $("#minimumQuantity").prop("disabled", false);
                $("#minimumQuantity").prop("readonly", false);
                $("#minimumQuantity").val(0);
                $("#minimumQuantity").rules("add", {
                    "required": true,
                    "digits": true,
                    "lessThanEqualTo": "#offerQuantity",
                    messages:
                            {
                                "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.minimumQuantity.required"/>',
                                "digits": '<spring:message code="seller.auctionofferplacing.lbl.validation.minimumQuantity.number"/>',
                                "lessThanEqualTo": '<spring:message code="seller.auctionofferplacing.lbl.validation.minimumQuantity.number"/>',
                            }
                });
            } else if ($("#offerPartialExecutionNo").is(":checked")) {
                $("#partialAllowed").val(0);
                $("#minimumQuantity").prop("disabled", true);
                $("#minimumQuantity").prop("readonly", true);
                $("#minimumQuantity").val("${auctionSellersBean.minimumQuantity}");
                $("#minimumQuantity").rules("remove");
            }
        });
        $("input[name='offerPartialExecution']").trigger("change");

        //Setting Offer price element components as per operation type add / update.  
        if ($("#offerPrice").val() <= 0) {
            $("#highestPrice").prop("checked", true);
            $("#limitPrice").prop("checked", false);
            $("#offerPrice").prop("readonly", false);
            $("#offerPrice").prop("disabled", false);
            $("#offerPrice").val((""));
        } else if ($("#offerPrice").val() > 0) {
            $("#highestPrice").prop("checked", false);
            $("#limitPrice").prop("checked", true);
            $("#offerPrice").prop("readonly", false);
            $("#offerPrice").prop("disabled", false);
            $("#offerPrice").rules("add", {
                "required": true,
                "number": true,
                //"range": [minPrice ,maxPrice],
                "notEqualZero": true,
                "min": minPrice,
                "max": maxPrice,
                messages: {
                    "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.required"/>',
                    "number": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.number"/>',
                    "notEqualZero": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.equalzero"/>',
                    "min": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.min"/> ' + minPrice.toFixed(2),
                    "max": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.max"/> ' + maxPrice.toFixed(2)
                }
            });
        }

        //Setting Offer price element components as per changing value.
        $("input[name=offerPricing]").on("change", function () {
            if ($("#highestPrice").is(":checked")) {
                $("#offerPrice").rules("remove");
                $("#offerPrice").removeClass("error-element");
                $("#offerPrice").prop("readonly", true);
                $("#offerPrice").prop("disabled", true);
               $("#offerPrice").val((""));
            } else if ($("#limitPrice").is(":checked")) {
                $("#offerPrice").prop("readonly", false);
                $("#offerPrice").prop("disabled", false);
                if (auctionSellersId > 0){
                	 $("#offerPrice").val(${auctionSellersBean.offerPrice}); 
                }
                $("#offerPrice").rules("add", {
                    "required": true,
                    "number": true,
                    //"range": [minPrice ,maxPrice],
                    "notEqualZero": true,
                    "min": minPrice,
                    "max": maxPrice,
                    messages:
                            {
                                "required": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.required"/>',
                                "number": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.number"/>',
                                "notEqualZero": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.equalzero"/>',
                                "min": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.min"/> ' + minPrice.toFixed(2),
                                "max": '<spring:message code="seller.auctionofferplacing.lbl.validation.offerPrice.max"/> ' + maxPrice.toFixed(2)
                            }
                });
            }
        });
        $("input[name=offerPricing]").trigger("change");
         
	
        //Offer Picture Upload Btn Click event.
        $("#btnSellerOfferPictureUpload").on("click", function (e) {
            e.preventDefault();
            $("#syncsaveofferdetails").hide();
            $.ajax({
                type: "GET",
                async: false,
                cache: false,
                data: {
                    productId: $("#productId").val(),
                    dailyAuctionsId: $("#dailyAuctionsId").val(),
                    auctionSellersId: $("#auctionSellersId").val()
                },
                url: (contextPath + "/ssa/offer/sellerOfferPictureAjax"),
                success: function (result) {
                    $("#sellerPictureModel .modal-dialog").empty().html(result);
                    $("#sellerPictureModel").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#sellerPictureModel .modal-dialog").empty().html(e);
                    $("#sellerPictureModel").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        });
        $("select#accountLocationsBean").focus();
    });
//Offer Picture Zoom Click event.
    function imgClick(element) {
        $("#largeImageDisplay #imageDisplay").attr("src", element.src);
        $("#largeImageDisplay").modal({show: true, backdrop: 'static', keyboard: false});
    }

    function imgDelete(id) {
        if (confirm('<spring:message code="seller.sellerinfopicture.validation.deletemsg"/>')) {
            $.ajax({
                type: "GET",
                // async: false,
                cache: false,
                url: (contextPath + "/ssa/offer/removepicture/" + id),
                success: function (result) {
                    $("#img-" + id).remove();
                },
                error: function (e) {
                    $("#img-" + id).remove();
                }
            });
        }
    }
    function formatDate(strTime) {
        strTime = (strTime.indexOf("AM") > -1) ? strTime.replace("AM", getTimeStatus("am")) : strTime.replace("PM", getTimeStatus("pm"));
        return strTime;
    }
</script>