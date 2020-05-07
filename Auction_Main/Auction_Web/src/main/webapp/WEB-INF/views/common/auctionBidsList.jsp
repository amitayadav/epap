<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionStatusCodes"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes"%>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ page import="com.auction.commons.util.InternetTiming"%>

<c:set var="AUCTION_STATUS_OPEN" value="${ENUM_AuctionStatusCodes.OPEN.getStatus()}"/>
<c:set var="AUCTION_STATUS_RUNNING" value="${ENUM_AuctionStatusCodes.RUNNING.getStatus()}"/>

<c:set var="OFFER_STATUS_OPEN" value="${ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus()}"/>
<c:set var="OFFER_STATUS_RUNNING" value="${ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()}"/>
<c:set var="OFFER_STATUS_SETTLING" value="${ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus()}"/>
<c:set var="OFFER_STATUS_FINISHED" value="${ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus()}"/>
<c:set var="OFFER_STATUS_CANCELLED" value="${ENUM_AuctionSellerOfferStatusCodes.CANCELLED.getStatus()}"/>
<c:set var="OFFER_STATUS_EXPIRED" value="${ENUM_AuctionSellerOfferStatusCodes.EXPIRED.getStatus()}"/>

<c:set var="BID_STATUS_OPEN" value="${ENUM_AuctionBuyerBidStatusCodes.OPEN.getStatus()}"/>
<c:set var="BID_STATUS_RUNNING" value="${ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus()}"/>
<c:set var="BID_STATUS_CANCELLED" value="${ENUM_AuctionBuyerBidStatusCodes.CANCELLED.getStatus()}"/>

<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>
<!--fahad: remove colon after label-->
<%--<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}&nbsp;:&nbsp;</label><br/>${dailyAuctionView.productDescription}</p><br/><p class='show-product-info'><label>${productContainerSpecsPhl}&nbsp;:&nbsp;</label><br/>${dailyAuctionView.containerSpecs}</p>"/>--%>
<%--<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}&nbsp;</label><br/>${dailyAuctionView.productDescription}</p><br/><p class='show-product-info'><label>${productContainerSpecsPhl}&nbsp;</label><br/>${dailyAuctionView.containerSpecs}</p>"/>--%>
<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}:&nbsp;</label>${dailyAuctionView.productDescription}</p><p class='show-product-info'><label>${productContainerSpecsPhl}:&nbsp;</label>${dailyAuctionView.containerSpecs}</p>"/>

<style>
    #auctionListDetails tr:nth-child(2) td{text-align:center;color: #8e0000 !important;font-weight: bolder; font-size: 20px;}
    #auctionListDetails tr:nth-child(2) td:last-child{color: #FFF !important;}
    /*#auctionListDetails tr:nth-child(2) td:last-child{background-color: #26883f !important; color: #FFF !important;vertical-align: middle !important;}*/
    #auctionListDetails tr:nth-child(3) td{color: #333 !important;}
    #auctionListDetails tr:nth-child(4) td{vertical-align: middle;}
    #auctionListDetails tr:nth-child(4) th:first-child{text-align:left;}
    #auctionListDetails thead tr:nth-child(1) td,
    #auctionListDetails thead tr:nth-child(2) td,
    #auctionListDetails thead tr:nth-child(4) td{vertical-align: middle !important;}
    table#datatable tbody tr td:nth-child(n+3){text-align:center;}

 /*   #auctionBuyerBidsAndViewSellerOffer tr:nth-child(2) td {text-align:center;vertical-align: middle;font-weight: bolder;}
    #auctionBuyerBidsAndViewSellerOffer tr:nth-child(2) td:first-child{text-align: left;}   */
    table#auctionBuyerBidsAndViewSellerOffer tbody tr td:nth-child(n+2){text-align:center;}
    

</style>
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <!--fahad: fixed springmessage-->
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><a href="${contextPath}/auctions/auctionList"><spring:message code="menu.a.auction"/></a></li>
        <li><a href="${contextPath}/auctions/offers/${dailyAuctionView.dailyAuctionsId}"><spring:message code="auctionbidslist.h3.auctionseller"/></a></li>
        <li><spring:message code="menu.a.auction.buyers.bids"/></li>
        <li class="todaydatetime">
          <button  style="background-color:transparent; border-radius:50%" onclick="buyerBidRefrsh()" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="<spring:message code="auctionbidslist.li.title.refresh"/>"><i class="fa fa-refresh fa-lg" aria-hidden="true"></i></button>&nbsp;&nbsp;&nbsp;&nbsp;
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
            <span id="current-time">
                <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
            </span>
        </li>
    </ul>
    <div class="">
        <div class="page-title">
            <%-- <div class="title_left">
              <h3><spring:message code="auctionbidslist.h3.auctionbid"/></h3>
            </div> --%>
            <div class="title_center h3">
                <h3><spring:message code="auctionlist.h3.dayauction"/></h3>
            </div>
        </div>
        <div class="clearfix"></div>
        <c:if test="${not empty ERROR}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text">
                    <i class="icon fa fa-warning"></i> ${ERROR}
                </h4>
            </div>
        </c:if>
        <c:if test="${not empty SUCCESS}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text">
                    <i class="icon fa fa-check"></i> ${SUCCESS}
                </h4>
            </div>
        </c:if>
           <input type="hidden" id ="dailyAuctionId" value="${dailyAuctionView.dailyAuctionsId}">
        <div class="x_content">
            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_content">
                        <div class="x_panel auction-list-bordered">
                            <div class="table-responsive">
                                <table id="auctionListDetails" class="table table-striped table-bordered table_dayauction">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="auctionbidslist.th.startime"/></th>
                                            <th><spring:message code="auctionbidslist.th.endtime"/></th>
                                            <th colspan="2"><spring:message code="auctionbidslist.th.group"/></th>
                                            <th colspan="2"><spring:message code="auctionbidslist.th.name"/></th>
                                            <th colspan="2"><spring:message code="auctionbidslist.th.type"/></th>
                                            <th colspan="3" id="remaining-time-caption">
                                                <c:choose>
                                                    <c:when test="${dailyAuctionView.auctionStatusCode eq AUCTION_STATUS_OPEN}">
                                                        <spring:message code="auctionbidslist.th.timetostart"/>
                                                    </c:when>
                                                    <c:when test="${dailyAuctionView.auctionStatusCode eq AUCTION_STATUS_RUNNING}">
                                                        <spring:message code="auctionbidslist.th.timetoend"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <%-- <spring:message code="auction.status.${dailyAuctionView.auctionStatusCode}"/> --%>
                                                        <!--fahad: change status message-->
                                                        <%--<spring:message code="auctionofferlist.th.status"/>--%>
                                                        <spring:message code="auctionlist.th.auctionstatus"/>                                                       
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                        </tr>
                                        <tr>
                                            <!--<td>${dailyAuctionView.beginTime}</td>-->
                                            <td> 
                                                <fmt:parseDate value="${dailyAuctionView.beginTime}" type="time" pattern="hh:mm" var="beginTime"/>   
                                                <fmt:formatDate value="${beginTime}"  type="date" pattern="hh:mm"/>
                                            </td>                                            
                                            <!--<td>${dailyAuctionView.endTime}</td>-->
                                            <td> 
                                                <fmt:parseDate value="${dailyAuctionView.endTime}" type="time" pattern="hh:mm" var="endTime"/>   
                                                <fmt:formatDate value="${endTime}"  type="date" pattern="hh:mm"/>
                                            </td>                                            
                                            <td colspan="2" class="show-product-info" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace }">${dailyAuctionView.productGroupName}</td>
                                            <td colspan="2" class="text-hightlight text-underline" data-html="true"  data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${dailyAuctionView.productName}</td>
                                            <td colspan="2" class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${dailyAuctionView.productTypeName}</td>
                                            <td colspan="3" id="remaining-time"></td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="page-title">
                        <div class="title_center">
                            <h3><spring:message code="auctionbidslist.h3.auctionseller"/></h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="x_content">
                        <div class="x_panel auction-offer-list-bordered">
                            <%-- <spring:message code="auctionofferlist.h2.auctionofferlist"/></h2> --%>
                            <sec:authorize access="hasRole('ROLE_SELLER') or hasRole('ROLE_SELLER_AGENT')">
                                <c:choose>
                                    <c:when test="${((AUCTION_STATUS_OPEN eq dailyAuctionView.auctionStatusCode) || (AUCTION_STATUS_RUNNING eq dailyAuctionView.auctionStatusCode))}">
                                        <c:choose>
                                            <c:when test="${(not empty auctionSellerOffersView && not empty auctionSellerOffersView.auctionSellersId && auctionSellerOffersView.auctionSellersId > 0 && auctionSellerOffersView.accountId eq loginUser.accountProfileBean.accountId) &&
                                                            ((OFFER_STATUS_OPEN eq auctionSellerOffersView.sellerOfferStatusCode) || (OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode))}">
                                                    <div class="x-title text-right">
                                                        <c:choose>
                                                            <c:when test="${auctionSellerOffersView.offerPrice > 0}">
                                                                <span id="offer-price-update">
                                                                <c:choose>
                                                                  <c:when test="${(auctionSellerOffersView.offerPrice > 0) && (OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode) || (OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode)}">
                                                                    <button class="btn btn-danger btn-lg" id="decreaseOfferPrice">
                                                                        <i class="fa fa-arrow-down"></i>
                                                                    </button>&nbsp;
                                                                  </c:when>
                                                                <c:otherwise>  <button class="btn btn-danger btn-lg" id="decreasePrice" disabled="disabled">
                                                                <i class="fa fa-arrow-down"></i>
                                                            </button>
                                                    </c:otherwise>
                                                                  </c:choose>
                                                                  
                                                                    <span class="read-only read-only-sm w20">
                                                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${defaultPrice}"/>
                                                                    </span>&nbsp;
                                                                </span>
                                                            </c:when>
                                                        </c:choose>
                                                        <a href="${contextPath}/ssa/offer/${dailyAuctionView.dailyAuctionsId}/${auctionSellerOffersView.auctionSellersId}" class="btn btn-warning btn-lg pageContentLoading" id="offerAndBidBtn">
                                                            <i class="fa fa-edit"></i>&nbsp;&nbsp;<spring:message code="auctionofferlist.div.a.updateOffer"/></a>
                                                        <button class="btn btn-danger btn-lg" id="cancelOffer" data-sellerid="${auctionSellerOffersView.auctionSellersId}">
                                                            <i class="fa fa-times"></i>&nbsp;&nbsp;<spring:message code="auctionofferlist.div.a.cancelOffer"/>
                                                        </button>
                                                        <!--fahad: change place of Take Bids-->
                                                        <sec:authorize access="hasRole('ROLE_SELLER') or hasRole('ROLE_SELLER_AGENT')">
                                                            <label id="confirmDialogMsg" class="hidden" ><spring:message code="auctionbidslist.takebid.msg"/></label> 
                                                            <c:if test="${((AUCTION_STATUS_RUNNING eq dailyAuctionView.auctionStatusCode) && (auctionSellerOffersView.offerPrice  eq 0 ) && (OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode) && (loginUser.accountProfileBean.accountId eq auctionSellerOffersView.accountId))}">
                                                                <td class="isAllowedForTakeBid hidden"><a href="${contextPath}/ssa/offer/takebid/${auctionSellerOffersView.auctionSellersId}" class="fa fa-check btn-lg take-bid pageContentLoadingAfterConfirm" title="<spring:message code="btn.take.bids"/>" data-toggle="tooltip" data-original-title="<spring:message code="btn.take.bids"/>" ><spring:message code="btn.take.bids"/></a></td>
                                                                <!--<i class="fa fa-check text-hightlight btn btn-success"></i> &nbsp;&nbsp;&nbsp;&nbsp;-->                                            
                                                            </c:if>
                                                        </sec:authorize>                                                        
                                                    </div>
                                                    <div class="clearfix"></div>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                </c:choose>              
                            </sec:authorize>
                            <div class="table-responsive">
                                <table id="auctionBuyerBidsAndViewSellerOffer" class="table table-striped table-bordered table_offer">
                                    <thead>
                                        <tr>
                                            <!--fahad: change column widths-->              
                                            <th width="200px"><spring:message code="auctionbidslist.th.sellername"/></th>
                                            <th width="80px"><spring:message code="auctionbidslist.th.offerqty"/></th>
                                            <th width="80px"><spring:message code="auctionbidslist.th.availableqty"/></th>
                                            <th width="80px"><spring:message code="auctionbidslist.th.offerprice"/></th>
                                            <th width="80px"><spring:message code="auctionbidslist.th.location"/></th>
                                            <th width="100px"><spring:message code="pickupTickets.lbl.truckId"/><br><i class="fa fa-truck"></i></th>
                                            <th width="100px"><spring:message code="auctionbidslist.th.offerinfoandpictures"/></th>
                                            <th width="70px"><spring:message code="auctionbidslist.th.partialallowed"/></th>
                                            <th width="70px"><spring:message code="auctionbidslist.th.executedoffers"/></th>
                                            <th width="100px"><spring:message code="auctionbidslist.th.rating"/></th>
                                            <th width="70px"><spring:message code="auctionbidslist.th.cancleper"/></th>
                                            <!--fahad: change auction status of seller-->
                                            <!--<th width="80px"><spring:message code="auctionbidslist.th.status"/></th>--> 
                                            <th width="80px"><spring:message code="auctionofferlist.th.status"/></th> 
                                                <%--<sec:authorize access="hasRole('ROLE_SELLER') or hasRole('ROLE_SELLER_AGENT')">--%>
                                                <%--<c:if test="${((AUCTION_STATUS_RUNNING eq dailyAuctionView.auctionStatusCode) && (auctionSellerOffersView.offerPrice  eq 0 ) && (OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode) && (loginUser.accountProfileBean.accountId eq auctionSellerOffersView.accountId))}">--%>
                                                <!--<th width="90px" class="isAllowedForTakeBid hidden disable-sorting"><spring:message code="btn.take.bids"/></th>-->
                                            <%--</c:if>--%>
                                            <%--</sec:authorize>--%>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%--  <tr>
                                            <td><a id="profileInfo" data-sellerid="${auctionSellerOffersView.accountId}" class="text-link text-capitalize text-hightlight text-underline-hyper">${auctionSellerOffersView.publicName}</a></td>
                                            <td width="90px" class="text-bold text-dark"><fmt:formatNumber value="${auctionSellerOffersView.offerQuantity}"/></td>
                                            <td width="90px" class="text-bold text-dark"><fmt:formatNumber value="${auctionSellerOffersView.availableQuantity}"/></td>
                                            <td width="70px" class="${auctionSellerOffersView.offerPrice > 0 ? '' : 'text-bold text-dark'}">
                                            <td width="70px" class="text-bold text-dark" id="offerPriceOfBidTable">
                                                <c:choose>
                                                    <c:when test="${auctionSellerOffersView.offerPrice > 0}">
                                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${auctionSellerOffersView.offerPrice}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <spring:message code="market"/>
                                                    </c:otherwise>
                                                </c:choose>     
                                                <input type="hidden" id="isofferPriceOfBidTable" value="0">          	
                                            </td>
                                            <td width="80px">
                                                <!--fahad: change location icon, drop text-link-->
                                                <a id="viewLocation" data-latitude="${auctionSellerOffersView.accountLocationsView.latitude}" data-longitude="${auctionSellerOffersView.accountLocationsView.longitude}" class="text-capitalize btn btn-lg text-center" data-toggle="tooltip" data-original-title="${auctionSellerOffersView.accountLocationsView.locationName}">
                                                    <i class="fa fa-map-marker fa-2"></i>
                                                </a>
                                            </td>
                                            <td width="80px"> 
                                                <!--fahad: change truck number direction using pageContext.response.locale-->
                                                <c:choose>
                                                    <c:when test="${pageContext.response.locale eq 'en'}">
                                                        <fmt:formatNumber pattern="000000" value="${sellerAccountId}" /> -   <fmt:formatNumber pattern="00" value="${auctionSellerOffersView.truckNumber}" />
                                                        <fmt:formatNumber pattern="00" value="${auctionSellerOffersView.truckNumber}" /> - <fmt:formatNumber pattern="000000" value="${sellerAccountId}" />

                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatNumber pattern="00" value="${auctionSellerOffersView.truckNumber}" /> - <fmt:formatNumber pattern="000000" value="${sellerAccountId}" />
                                                    </c:otherwise>
                                                </c:choose>    
                                            </td>
                                            <td width="90px">
                                                <!--fahad: change picture icon, drop text-link-->
                                                <spring:message code="btn.img" var="btn_img"/>
                                                <a id="viewPhotos" data-accountid="${auctionSellerOffersView.accountId}" data-productid="${auctionSellerOffersView.productId}" data-sellerid="${auctionSellerOffersView.auctionSellersId}" class="text-capitalize" data-toggle="tooltip" data-original-title="${btn_img}">
                                                    <i class="fa fa-picture-o fa-2"></i>
                                                </a>
                                            </td>
                                            <td width="90px">
                                                <c:choose>
                                                    <c:when test="${auctionSellerOffersView.partialAllowed}">
                                                        <label class="label yes"><i class="fa fa-check"></i></label>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <label class="label no"><i class="fa fa-times"></i></label>
                                                        </c:otherwise>
                                                    </c:choose>
                                            </td>
                                            <td width="50px" class="text-bold text-dark">${auctionSellerOffersView.executedOfferOrBidCount}</td>
                                            <td width="100px" class="text-bold text-dark">
                                                <c:set var="asoRating" value="${Math.round(auctionSellerOffersView.rating)}"/>
                                                <c:if test = "${asoRating eq 0}">
                                                    0
                                                </c:if>
                                                <c:forEach begin="1" end="${asoRating}" >
                                                    <span class="fa fa-star orange"></span>
                                                </c:forEach>
                                                <c:out value="${asoRating}" />
                                            </td>
                                            <td width="60px" class="text-bold text-dark">
                                                 <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0" value="${auctionSellerOffersView.cancelPercentage}"/>
                                                  %
                                                <c:set var="cancelPercentage" value="${Math.round(auctionSellerOffersView.cancelPercentage)}"/>
                                                ${cancelPercentage}%
                                            </td>

                                            <td width="65px"><label class="label text-hightlight auction-offer status-${auctionSellerOffersView.sellerOfferStatusCode}">${auctionSellerOffersView.sellerOfferStatusName}</label></td>
                                            <td width="65px">

                                                <label class="label text-hightlight auction-offer status-${auctionSellerOffersView.sellerOfferStatusCode}">
                                                    <c:if test ="${OFFER_STATUS_OPEN eq auctionSellerOffersView.sellerOfferStatusCode}">
                                                        <spring:message code="auction.seller.offer.status.1"/>
                                                    </c:if>
                                                    <c:if test ="${OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode}">
                                                        <spring:message code="auction.seller.offer.status.2"/>
                                                    </c:if>
                                                    <c:if test ="${OFFER_STATUS_SETTLING  eq auctionSellerOffersView.sellerOfferStatusCode}">
                                                        <spring:message code="auction.seller.offer.status.3"/>
                                                    </c:if>                            
                                                    <c:if test ="${OFFER_STATUS_FINISHED  eq auctionSellerOffersView.sellerOfferStatusCode}">
                                                        <spring:message code="auction.seller.offer.status.4"/>
                                                    </c:if>
                                                    <c:if test ="${OFFER_STATUS_CANCELLED eq auctionSellerOffersView.sellerOfferStatusCode}">
                                                        <spring:message code="auction.seller.offer.status.5"/>
                                                    </c:if>
                                                    <c:if test ="${OFFER_STATUS_EXPIRED eq auctionSellerOffersView.sellerOfferStatusCode}">
                                                        <spring:message code="auction.seller.offer.status.6"/>
                                                    </c:if>
                                                </label>
                                            </td>

                                        </tr> --%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="page-title">
                        <div class="title_center">
                            <h3><spring:message code="auctionbidslist.h3.auctionbid"/></h3>
                        </div>
                    </div>
                    
                     <input type="hidden" id ="auctionOfferBuyerId" value="${auctionBuyersBean.auctionBuyersId}">
                    <div class="clearfix"></div>
                    <div class="x_content">
                        <div class="x_panel auction-bid-list-bordered">
                            <sec:authorize access="hasRole('ROLE_BUYER') or hasRole('ROLE_BUYER_AGENT')">
                                <div class="x-title text-right">
                                    <c:choose>
                                        <c:when test="${((AUCTION_STATUS_OPEN eq dailyAuctionView.auctionStatusCode) || (AUCTION_STATUS_RUNNING eq dailyAuctionView.auctionStatusCode)) && 
                                                        ((OFFER_STATUS_OPEN eq auctionSellerOffersView.sellerOfferStatusCode) || (OFFER_STATUS_RUNNING eq auctionSellerOffersView.sellerOfferStatusCode))}">
                                            <c:choose>
                                                <c:when test="${(not empty auctionBuyersBean && not empty auctionBuyersBean.auctionBuyersId && auctionBuyersBean.auctionBuyersId > 0) &&
                                                                (BID_STATUS_OPEN eq auctionBuyersBean.buyerBidStatus || BID_STATUS_RUNNING eq auctionBuyersBean.buyerBidStatus)}">
                                                    <%-- <c:if test="${AUCTION_STATUS_OPEN eq dailyAuctionView.auctionStatusCode && OFFER_STATUS_OPEN eq auctionSellerOffersView.sellerOfferStatusCode}">
                                              <button class="btn btn-danger btn-lg" id="decreasePrice">
                                                    <i class="fa fa-arrow-down"></i>
                                              </button>&nbsp;
                                            </c:if> --%>
                                                    <span id="changBidPriceMsg"></span>
                                                    <p class="read-only read-only-sm w20">
                                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${defaultPrice}"/>
                                                    </p>&nbsp;
                                                    <button class="btn btn-success btn-lg " id="increasePrice">
                                                        <i class="fa fa-arrow-up"></i></button>
                                                <%--  <c:choose>  
                                                	 <c:when test="${(not empty auctionBuyersBean && not empty auctionBuyersBean.auctionBuyersId && auctionBuyersBean.auctionBuyersId > 0) &&
                                                               ( BID_STATUS_RUNNING eq auctionBuyersBean.buyerBidStatus)}">
                                                    <button class="btn btn-success btn-lg " id="increasePrice">
                                                        <i class="fa fa-arrow-up"></i>
                                                    </button>
                                                    </c:when>
                                                    <c:otherwise>  
                                                    <button class="btn btn-success btn-lg " id="increasePrice" disabled="disabled">
                                                        <i class="fa fa-arrow-up"></i></button>
                                                    </c:otherwise>
                                                 </c:choose> --%>&nbsp;
                                                    <a href="${contextPath}/bba/bid/${auctionSellerOffersView.auctionSellersId}/${auctionBuyersBean.auctionBuyersId}" class="btn btn-warning btn-lg pageContentMenu" id="offerAndBidBtn">
                                                        <i class="fa fa-edit"></i>&nbsp;&nbsp;
                                                        <spring:message code="auctionbidslist.div.a.updateBid"/>
                                                    </a>&nbsp;
                                                    <%-- <a href="${contextPath}/bba/bid/cancel/${auctionSellerOffersView.auctionSellersId}/${auctionBuyersBean.auctionBuyersId}" onclick="return confirm('<spring:message code="common.cancel.buyer.bid" />');" class="btn btn-danger btn-lg">
                                                      <i class="fa fa-remove"></i>&nbsp;&nbsp;
                                                      <spring:message code="auctionbidslist.div.a.cancelBid"/>
                                                    </a> --%>
                                                    <button class="btn btn-danger btn-lg" id="cancelBid" data-buyerid="${auctionBuyersBean.auctionBuyersId}">
                                                        <i class="fa fa-times"></i>&nbsp;&nbsp;<spring:message code="auctionbidslist.div.a.cancelBid"/>
                                                    </button>
                                                </c:when>
                                                <c:when test="${(empty auctionBuyersBean || empty auctionBuyersBean.auctionBuyersId)}">
                                                    <a href="${contextPath}/bba/bid/${auctionSellerOffersView.auctionSellersId}" class="btn btn-info btn-lg pull-right pageContentMenu" id="offerAndBidBtn">
                                                        <i class="fa fa-plus"></i>&nbsp;&nbsp;
                                                        <spring:message code="auctionbidslist.div.a.placeBid"/>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                    <div class="clearfix"></div>
                                </div>
                            </sec:authorize>
                            <!-- <div class="clearfix"></div><hr class="hr"/> -->

                            <div class="x_panel">
                                <div class="table-responsive">
                                    <table id="datatable" class="table table-striped table-bordered table_bid">
                                        <thead>
                                            <tr>
                                                <th class="hidden">Id</th>
                                                <!--fahad: change columns widths-->
                                                <th width="200px"><spring:message code="auctionbidslist.th.buyername"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.bidquantity"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.executedquantity"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.bidprice"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.executedprice"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.minimumQuantity"/></th>
                                                <!--fahad: fix                      <th width ="30px">PartialAllowed</th>-->
                                                <th width="80px"><spring:message code="auctionbidslist.th.partialallowed"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.location"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.executedbids"/></th>
                                                <th width="100px"><spring:message code="auctionbidslist.th.rating"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.cancleper"/></th>
                                                <th width="80px"><spring:message code="auctionbidslist.th.status"/></th>
												 <th class="hidden">bidUpdatedTime</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
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
<div class="modal fade scroll-modal" id="profileViewModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                <h1 class="modal-title"><spring:message code="common.profile.info"/></h1>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer text-right">
                <button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal">
                    <i class="fa fa-times"></i>&nbsp; <spring:message code="btn.close"/>
                </button>            
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="viewOfferDetailsModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
        </div>
    </div>
</div>
<div class="modal fade" id="viewBidDetailsModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
        </div>
    </div>
</div>
<script type="text/javascript">
    var internetTime = ${InternetTiming.getInternetDateTimeAsMiliSeconds()};
    var auctionBeginTime = ${dailyAuctionView.beginLong};
    var auctionEndTime = ${dailyAuctionView.endLong};
    var btn_view = '<spring:message code="btn.view"/>';
    var btn_img = '<spring:message code="btn.img"/>';
    var time_to_end = '<spring:message code="auctionbidslist.th.timetoend"/>';
    var EndTimediffSecond=null;
    var EndTimediffdiffMinute =null;
//var currentTimeInterval = 0;
    var timeStatus = Object();
    timeStatus["am"] = '<spring:message code="am"/>';
    timeStatus["pm"] = '<spring:message code="pm"/>';
    var market = '<spring:message code="market"/>';
    var auctionStatus = Object();
    auctionStatus["status_1"] = '<spring:message code="auction.status.1"/>';
    auctionStatus["status_2"] = '<spring:message code="auction.status.2"/>';
    auctionStatus["status_3"] = '<spring:message code="auction.status.3"/>';
    auctionStatus["status_4"] = '<spring:message code="auction.status.4"/>';
    auctionStatus["status_5"] = '<spring:message code="auction.status.5"/>';
    auctionStatus["status_6"] = '<spring:message code="auction.status.6"/>';

    var isAllowedForTakeBid = parseInt("${loginUser.accountProfileBean.accountId == auctionSellerOffersView.accountId ? 1 : 0}");
    var AUCTION_STATUS = parseInt("${dailyAuctionView.auctionStatusCode}");
    var OFFER_STATUS = parseInt("${auctionSellerOffersView.sellerOfferStatusCode}");
    var BID_STATUS = "${auctionBuyersBean.buyerBidStatus}";

    var AUCTION_STATUS_RUNNING = parseInt("${ENUM_AuctionStatusCodes.RUNNING.getStatus()}");
    var OFFER_STATUS_RUNNING = parseInt("${ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()}");
    var BID_STATUS_RUNNING = parseInt("${ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus()}");

    function getAuctionStatus(key) {
        return auctionStatus[key];
    }

    function checkAuctionRunningTime() {
        var currentTime = internetTime;
        if (auctionBeginTime > internetTime) {
            var diff = Math.abs(auctionBeginTime - internetTime);
            var diffSeconds = (diff / 1000 % 60);
            var diffMinutes = (diff / (60 * 1000) % 60);
            diffSeconds = parseInt(diffSeconds.toString().slice(0, (diffSeconds.toString().indexOf("."))));
            diffMinutes = parseInt(diffMinutes.toString().slice(0, (diffMinutes.toString().indexOf("."))));
            if (diffMinutes > 0) {
                return true;
            } else if (diffSeconds > 5) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    function setBidsRemainingTime() {
        /* var date = new Date(internetTime);
         var hours = (date.getUTCHours() + 3);
         var minutes = date.getUTCMinutes();
         var seconds = date.getUTCSeconds();
         var ampm = hours >= 12 ? (getTimeStatus("pm")) : (getTimeStatus("am"));
         hours = hours % 12;
         hours = hours ? hours : 12; // the hour '0' should be '12'
         hours = hours < 10 ? ('0' + hours) : hours;
         minutes = minutes < 10 ? ('0' + minutes) : minutes;
         seconds = seconds < 10 ? ('0' + seconds) : seconds;
         var currentTime = hours + ':' + minutes + ':' + seconds + ' ' + ampm;
         $("#current-time").text(currentTime); */
        var currentTime = internetTime;
        if (auctionBeginTime > internetTime) {
            //$("#remaining-time").text("Open");
            var diff = Math.abs(auctionBeginTime - internetTime);
            var diffSeconds = (diff / 1000 % 60);
            var diffMinutes = (diff / (60 * 1000) % 60);
            var diffHours = (diff / (60 * 60 * 1000) % 24);
            diffSeconds = parseInt(diffSeconds.toString().slice(0, (diffSeconds.toString().indexOf("."))));
            diffMinutes = parseInt(diffMinutes.toString().slice(0, (diffMinutes.toString().indexOf("."))));
            diffHours = parseInt(diffHours.toString().slice(0, (diffHours.toString().indexOf("."))));
            diffHours = diffHours < 10 ? ('0' + diffHours) : diffHours;
            diffMinutes = diffMinutes < 10 ? '0' + diffMinutes : diffMinutes;
            diffSeconds = diffSeconds < 10 ? '0' + diffSeconds : diffSeconds;
            if (!($("#remaining-time").hasClass("text-hightlight"))) {
                $("#remaining-time").addClass("text-hightlight switch-ltr");
            }
            $("#remaining-time").addClass("auction-status-${dailyAuctionView.auctionStatusCode} switch-ltr");
            $("#remaining-time").text((diffHours > 0 ? (diffHours + " : ") : "") + diffMinutes + " : " + diffSeconds);
        } else if (auctionEndTime > internetTime) {
            //$("#remaining-time").text("Running");
            var diff = Math.abs(auctionEndTime - internetTime);
            var diffSeconds = (diff / 1000 % 60);
            var diffMinutes = (diff / (60 * 1000) % 60);
            var diffHours = (diff / (60 * 60 * 1000) % 24);
            diffSeconds = parseInt(diffSeconds.toString().slice(0, (diffSeconds.toString().indexOf("."))));
            diffMinutes = parseInt(diffMinutes.toString().slice(0, (diffMinutes.toString().indexOf("."))));
            diffHours = parseInt(diffHours.toString().slice(0, (diffHours.toString().indexOf("."))));
            diffHours = diffHours < 10 ? ('0' + diffHours) : diffHours;
            diffMinutes = diffMinutes < 10 ? ('0' + diffMinutes) : diffMinutes;
            diffSeconds = diffSeconds < 10 ? ('0' + diffSeconds) : diffSeconds;
            EndTimediffdiffMinute=diffMinutes;
            EndTimediffSecond=diffSeconds;
            if (!($("#remaining-time").hasClass("text-hightlight"))) {
                $("#remaining-time").addClass("text-hightlight switch-ltr");
            }
            $("#remaining-time").addClass("auction-status-${dailyAuctionView.auctionStatusCode} switch-ltr");
            $("#remaining-time").text((diffHours > 0 ? (diffHours + " : ") : "") + diffMinutes + " : " + diffSeconds);
            $("#remaining-time-caption").text(time_to_end)
        } else {
            //$("#remaining-time").removeClass("text-hightlight");
            if (!($("#remaining-time").hasClass("text-hightlight"))) {
                $("#remaining-time").addClass("text-hightlight switch-ltr");
            }
            $("#remaining-time").addClass("auction-status-${dailyAuctionView.auctionStatusCode} switch-ltr");
            $("#remaining-time").text(getAuctionStatus("status_${dailyAuctionView.auctionStatusCode}").toUpperCase());
            clearInterval(currentTimeInterval);
        }
        internetTime = internetTime + 1000;
    }
    /* if (undefined != currentTimeInterval) {
     clearInterval(currentTimeInterval);
     } */
    clearInterval(currentTimeInterval);
    setBidsRemainingTime();
    currentTimeInterval = setInterval(setBidsRemainingTime, 1000);

    function getTimeStatus(key) {
        return timeStatus[key];
    }

    function formatDate(longDate) {
        var date = new Date(longDate);
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds()
        var ampm = hours >= 12 ? (getTimeStatus("pm")) : (getTimeStatus("am"));
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        minutes = minutes < 10 ? '0' + minutes : minutes;
        seconds = seconds < 10 ? '0' + seconds : seconds;
        var dmonth = ((date.getMonth() + 1) < 10) ? ("0" + date.getMonth() + 1) : (date.getMonth() + 1);
        var currentDate = (date.getDate() < 10) ? ("0" + date.getDate()) : (date.getDate());
        var strTime = hours + ':' + minutes + ':' + seconds + ' ' + ampm;
        return dmonth + "/" + currentDate + "/" + date.getFullYear() + " " + strTime;
    }
    var auctionBidStatus = Object();
    auctionBidStatus["status_1"] = '<spring:message code="auction.buyer.bid.status.1"/>';
    auctionBidStatus["status_2"] = '<spring:message code="auction.buyer.bid.status.2"/>';
    auctionBidStatus["status_3"] = '<spring:message code="auction.buyer.bid.status.3"/>';
    auctionBidStatus["status_4"] = '<spring:message code="auction.buyer.bid.status.4"/>';
    auctionBidStatus["status_5"] = '<spring:message code="auction.buyer.bid.status.5"/>';
    auctionBidStatus["status_6"] = '<spring:message code="auction.buyer.bid.status.6"/>';
    auctionBidStatus["status_7"] = '<spring:message code="auction.buyer.bid.status.7"/>';

    var auctionOfferStatus = Object();
    auctionOfferStatus["offer_status_1"] = '<spring:message code="auction.seller.offer.status.1"/>';
    auctionOfferStatus["offer_status_2"] = '<spring:message code="auction.seller.offer.status.2"/>';
    auctionOfferStatus["offer_status_3"] = '<spring:message code="auction.seller.offer.status.3"/>';
    auctionOfferStatus["offer_status_4"] = '<spring:message code="auction.seller.offer.status.4"/>';
    auctionOfferStatus["offer_status_5"] = '<spring:message code="auction.seller.offer.status.5"/>';
    auctionOfferStatus["offer_status_6"] = '<spring:message code="auction.seller.offer.status.6"/>';


    function getAuctionOfferStatus(key) {
        return auctionOfferStatus[key];
    }
    function getAuctionBidStatus(key) {
        return auctionBidStatus[key];
    }

    function viewLocation(latitude, longitude, locationName) {
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
            	 if(403 == e.status){
              	   window.location.href = (contextPath + "/setting/dashboard");
                 }
            	$("#offerDetailsModal .modal-body").empty().html(e);
                $("#offerDetailsModal").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            }
        });
    }

    function viewProfile(sellerId) {
        $.ajax({
            type: "POST",
            async: false,
            cache: false,
            data: {
                sellerId: sellerId
            },
            url: (contextPath + "/auctions/profileInfo"),
            success: function (result) {
//                fahad: fix arabic title
//                $("#profileViewModel .modal-title").text("Profile Information");
//                $("#profileViewModel .modal-body").empty().html(result);
//                $("#profileViewModel").modal({
                $("#profileViewModel .modal-body").empty().html(result);
                $("#profileViewModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            },
            error: function (e) {
            	 if(403 == e.status){
              	   window.location.href = (contextPath + "/setting/dashboard");
                 }
            	$("#profileViewModel .modal-body").empty().html(e);
                $("#profileViewModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            }
        });
    }

    /* <sec:authorize access="hasRole('ROLE_SELLER') or hasRole('ROLE_SELLER_AGENT')">
     function confirmTakeBid(){
     return (confirm("<spring:message code="auctionbidslist.takebid.msg"/>"));
     }
     </sec:authorize> */
    var auctionTable = null;
    var auctionBuyerBidsAndViewSellerOffer = null;
    var auctionBuyerBid = null;
    var auctionSellerOffer=null;
    var queryString = "/auctions/bids/" +${auctionSellerOffersView.auctionSellersId};
    var auctionSellerId=${auctionSellerOffersView.auctionSellersId};
    var auctionOfferBuyerId=document.getElementById("auctionOfferBuyerId").value;
    var dailyAuctionId=document.getElementById("dailyAuctionId").value;
    $(document).ready(function () {
        $('#offerAndBidBtn').click(function () {
            return checkAuctionRunningTime();
        });
        window.history.pushState("object or string", "EPAP|AuctionList", contextPath + "/auctions/auctionlist");
        languageList.sEmptyTable = '<spring:message code="auctionbidslist.emptytable"/>';
        auctionBuyerBid = $('#datatable').DataTable({
            language: languageList,
            ordering: false,
            columnDefs: [{orderable: false, targets: 'disable-sorting'}],
             /*  order: [[4, 'desc'], [13, 'asc']],   */
         /*   ${auctionSellerOffersView.offerPrice > 0 ? "order: [[4, 'desc'], [0, 'asc']]" : "order: [/*[4, 'desc'], [0, 'asc']]"}, */
            ${"auctionSellerOffersView.offerPrice > 0" && "auctionSellerOffersView.partialAllowed = false" ? "order: [[2,'desc'],[4, 'desc'], [13, 'asc']]" : "order: [[4, 'desc'], [13, 'asc']]"},
         "ajax": {
                "url": (contextPath + "/auctions/buyerbids/${auctionSellerOffersView.auctionSellersId}"),
                "cache": false,
                "asyc": false,
                "dataSrc": ""
            },
            "columns": [{
                    "data": "auctionBuyersId",
                    "render": function (data, type, json, meta) {
                        return parseInt(json.auctionBuyersId);
                    }
                },
                {"data": "publicName",
                    "render": function (data, type, json, meta) {
//                        return "<a id=\"profileInfo\" data-sellerid=\"" + json.accountId + "\" class=\"text-link text-capitalize text-hightlight\">" + json.publicName + "</i></a>";
                        return "<a id=\"profileInfo\" data-sellerid=\"" + json.accountId + "\" class=\"text-capitalize text-hightlight text-underline-hyper\">" + json.publicName + "</i></a>";
                    }
                },
                {"data": "bidQuantity",
                    "render": function (data, type, json, meta) {
//                        return json.bidQuantity.toLocaleString();
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.bidQuantity.toLocaleString() + "</span>";

                    }
                },
                {"data": "executedQuantity",
                    "render": function (data, type, json, meta) {
//                        return json.executedQuantity.toLocaleString();
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.executedQuantity.toLocaleString() + "</span>";
                    }
                },
                {"data": "bidPrice",
                    "render": function (data, type, json, meta) {
//                        return json.bidPrice.toFixed(2);
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.bidPrice.toFixed(2) + "</span>";

                    }
                },
                {"data": "executedPrice",
                    "render": function (data, type, json, meta) {
//                      return json.bidPrice.toFixed(2);
                        if (json.executedPrice === 0) {
                            return "<span id='offerPrice' class='text-bold text-dark'></span>";
                        } else {
                            return "<span id='offerPrice' class='text-bold text-dark'>" + json.executedPrice.toFixed(2) + "</span>";

                        }




                    }
                },
                {"data": "minimumQuantity",
                    "render": function (data, type, json, meta) {
//                        return json.minimumQuantity;
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.minimumQuantity + "</span>";

                    }
                },
                {"data": "partialAllowed",
                    "render": function (data, type, json, meta) {
                        if (json.partialAllowed) {
                            return ("<label class=\"label yes\"><i class=\"fa fa-check\"></i></label>");
                        } else {
                            return ("<label class=\"label no\"><i class=\"fa fa-times\"></i></label>");
                        }
                    }
                },

                {"data": "accountLocationsView.locationName",
                    "render": function (data, type, json, meta) {
                        return "<a id=\"viewLocation\" data-latitude=\"" + json.accountLocationsView.latitude + "\" data-longitude=\"" + json.accountLocationsView.longitude + "\" class=\"text-center text-capitalize btn btn-lg\" data-toggle=\"tooltip\" data-original-title=\"" + json.accountLocationsView.locationName + "\"><i class=\"fa fa-map-marker fa-2\"></i></i></a>";
                    }
                },
                {"data": "executedOfferOrBidCount"},
                {"data": "rating",
                    "render": function (data, type, json, meta) {
//                        return (json.rating.toFixed(0));
//                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.rating.toFixed(0) + "</span>";
                        if (json.rating > 0 && json.rating < 0.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + " " + "</span>";
                        else if (json.rating >= 0.50 && json.rating < 1.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" + "</span>";
                        else if (json.rating >= 1.50 && json.rating < 2.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" + "</span>";


                        else if (json.rating >= 2.50 && json.rating < 3.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" + "</span>";
                        else if (json.rating >= 3.50 && json.rating < 4.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" + "</span>";
                        else if (json.rating >= 4.50 && json.rating <= 5.00)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "</span>";
                        else
                            return "<span id='offerPrice' class='text-bold text-dark'>" + json.rating.toFixed(0) + "</span>";
                    }
                },
                {"data": "cancelPercentage",
                    "render": function (data, type, json, meta) {
//                        return (json.cancelPercentage.toFixed(0));
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.cancelPercentage.toFixed(0) + " %" + "</span>";

                    }
                },
                {"data": "buyerBidStatusCode",
                    "render": function (data, type, json, meta) {
                        return ('<label class="label text-hightlight auction-bid status-' + (json.buyerBidStatusCode) + '">' + getAuctionBidStatus(("status_" + json.buyerBidStatusCode)) + '</label>');
                    }
                },{"data": "bidUpdatedTime"}
                /*,
                 {
                 "data": "buyerBidStatusCode",
                 "render": function(data, type, json, meta) {
                 BID_STATUS = json.buyerBidStatusCode;
                 if (isAllowedForTakeBid && (AUCTION_STATUS == AUCTION_STATUS_RUNNING) && (OFFER_STATUS == OFFER_STATUS_RUNNING) && (BID_STATUS == BID_STATUS_RUNNING)) {
                 return '<a href="${contextPath}/ssa/offer/takebid/'+json.auctionBuyersId+'" class="btn btn-success btn-lg take-bid" title="<spring:message code="btn.take.bids"/>" data-toggle="tooltip" data-original-title="<spring:message code="btn.take.bids"/>" onclick="return confirmTakeBid();"><spring:message code="btn.take.bids"/>&nbsp;&nbsp;<i class="fa fa-check"></i></a>';
                 }
                 return "";
                 }
                 }*/],
            "rowCallback": function (row, data, index) {
                $('td', row).eq(4).addClass('text-bold text-dark');
                if (data.auctionBuyersId == "${auctionBuyersBean.auctionBuyersId}") {
                    $(row).addClass("own-bid");
//                    $(row).attr("data-toggle", "tooltip");
//                    $(row).attr("data-original-title", '<spring:message code="your.bid"/>');
                }
                $('td', row).eq(0).addClass('hidden');
                $('td', row).eq(13).addClass("hidden");
                if ($(".isAllowedForTakeBid").hasClass("hidden")) {
                    $(".isAllowedForTakeBid").removeClass("hidden");
                }
            }
        });
        languageList.sEmptyTable = '<spring:message code="auctionofferlist.emptytable"/>';
        auctionBuyerBidsAndViewSellerOffer = $('#auctionBuyerBidsAndViewSellerOffer').DataTable({
            language: languageList,
            searching: false,
            paging: false, 
            info: false,
            ordering: false,
            columnDefs: [{
                    orderable: false,
                    targets: 'disable-sorting'
                }, {
                    type: "numeric"
                }],
            order: [
                [5, 'desc'],
                [0, 'asc']
            ],
            "ajax": {
            	 "url": (contextPath + "/auctions/offerBuyerBids/${auctionSellerOffersView.auctionSellersId}"),
                "cache": false,
                "asyc": false,
                "dataSrc": ""
            },
            "columns": [{
                    "data": "publicName",
                    "render": function (data, type, json, meta) {
                        return "<a id=\"profileInfo\" data-sellerid=\"" + json.accountId + "\" class=\"text-capitalize text-hightlight text-underline-hyper\">" + json.publicName + "</i></a>";

                    }
                },
                {
                    "data": "offerQuantity",
                    "render": function (data, type, json, meta) {
//                        return json.offerQuantity.toLocaleString();
                        return "<span id='offerPrice' class=\"text-bold text-dark\">" + json.offerQuantity.toLocaleString() + "</span>";
                    }
                },
                {
                    "data": "availableQuantity",
                    "render": function (data, type, json, meta) {
//                        return json.availableQuantity.toLocaleString();
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.availableQuantity.toLocaleString() + "</span>";

                    }
                },
               {
                    "data": "offerPrice",
                    "render": function (data, type, json, meta) {
                       if (json.auctionSellersId > 0 && "${auctionSellersBean.auctionSellersId}" === json.auctionSellersId) {
                            if (json.offerPrice > 0) {
                                $("#offer-price-update").removeClass("hidden");
                            } else {
                                $("#offer-price-update").addClass("hidden");
                            }
                        } 
                        if (json.auctionSellersId > 0 && json.offerPrice > 0) {
//                            return "<span id='offerPrice' class='text-bold text-dark blink_me'>" + json.offerPrice.toFixed(2) + "</span>";
                            return "<span id='offerPriceOfBidTable' class='text-bold text-dark'>" + json.offerPrice.toFixed(2) + "</span>";
                        } else {
                            return "<span id='offerPriceOfBidTable' class='text-bold text-dark'>" + market + "</span>";
                        }
                    }
                }, {
                    "data": "accountLocationsView.locationName",
                    "render": function (data, type, json, meta) {
                        return "<a id=\"viewLocation\" data-latitude=\"" + json.accountLocationsView.latitude + "\" data-longitude=\"" + json.accountLocationsView.longitude + "\" class=\"text-capitalize btn btn-lg text-center text-underline-hyper\" data-toggle=\"tooltip\" data-original-title=\"" + json.accountLocationsView.locationName + "\"><i class=\"fa fa-map-marker fa-2\"></i></a>";
                    }
                }, {
                    "data": "truckNumber",
                    "render": function (data, type, json, meta) {
                        return "<span id='truckNumber' class='text-bold text-dark'>" + appendLeadingZeros(json.ownerAccountId, 6) + "-" + appendLeadingZeros(json.truckNumber, 2) + "</span>";
                    }
                },
                {
                    "data": "auctionSellersId",
                    "render": function (data, type, json, meta) {
//                        return "<a id=\"viewPhotos\" data-accountid=\"" + json.accountId + "\" data-productid=\"" + json.productId + "\" data-sellerId=\"" + json.auctionSellersId + "\" class=\"text-muted\" data-toggle=\"tooltip\" data-original-title=\"" + btn_img + "\"><i class=\"fa fa-picture-o fa-2\"></i></a>";
                        return  "<a id=\"viewPhotos\" data-accountid=\"" + json.accountId + "\" data-productid=\"" + json.productId + "\" data-sellerId=\"" + json.auctionSellersId + "\" class=\"text-muted\" data-toggle=\"tooltip\" data-original-title=\"" + btn_img + "\"><i class=\"fa fa-picture-o fa-2\"></i></a>";
                    }
                }, {
                    "data": "partialAllowed",
                    "render": function (data, type, json, meta) {
                        if (json.partialAllowed) {
                            //return ("<label class=\"label label-success\">"+yes+"</label>");
                            return ("<label class=\"label yes\"><i class=\"fa fa-check\"></i></label>");
                        } else {
                            //return ("<label class=\"label label-danger\">"+no+"</label>");
                            return ("<label class=\"label no\"><i class=\"fa fa-times\"></i></label>");
                        }
                    }
                }, {
                    "data": "executedOfferOrBidCount"
                }, {
                    "data": "rating",
                    "render": function (data, type, json, meta) {
//                        return (json.rating.toFixed(0));
                        if (json.rating > 0 && json.rating < 0.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + " " + "</span>";
                        else if (json.rating >= 0.50 && json.rating < 1.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" + "</span>";
                        else if (json.rating >= 1.50 && json.rating < 2.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" + "</span>";


                        else if (json.rating >= 2.50 && json.rating < 3.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" + "</span>";
                        else if (json.rating >= 3.50 && json.rating < 4.50)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" + "</span>";
                        else if (json.rating >= 4.50 && json.rating <= 5.00)
                            return "<span id='offerPrice' class='text-bold text-dark'>" + "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "<i class='fa fa-star orange'>" + "</i>" +
                                    "</span>";
                        else
                            return "<span id='offerPrice' class='text-bold text-dark'>" + json.rating.toFixed(0) + "</span>";

                    }
                }, {
                    "data": "cancelPercentage",
                    "render": function (data, type, json, meta) {
//                        return (json.cancelPercentage.toFixed(0));
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.cancelPercentage.toFixed(0) + " %" + "</span>";
                    }
                },
                {"data": "sellerOfferStatusName",
                    "render": function (data, type, json, meta) {
                        return ('<label " class="label text-hightlight auction-offer status-' + (json.sellerOfferStatusCode) + '">' + getAuctionOfferStatus(("offer_status_" + json.sellerOfferStatusCode)) + '</label>');
                    }
                }
            ],
            "rowCallback": function (row, data, index) {
            	  $(row).addClass("own-bid");
            }
          
        });
        
     
        
        $("#datatable").on("click", "tr #viewLocation", function (e) {
            e.preventDefault();
            var latitude = $(this).data("latitude");
            var longitude = $(this).data("longitude");
            var locationName = $(this).data("original-title");
            viewLocation(latitude, longitude, locationName);
        });
        
        $("#auctionBuyerBidsAndViewSellerOffer").on("click", "tr #viewLocation", function (e) {
            e.preventDefault();
            var latitude = $(this).data("latitude");
            var longitude = $(this).data("longitude");
            var locationName = $(this).data("original-title");
            viewLocation(latitude, longitude, locationName);
        });
        
        $("#viewLocation").on("click", function (e) {
            e.preventDefault();
            var latitude = $(this).data("latitude");
            var longitude = $(this).data("longitude");
            var locationName = $(this).data("original-title");
            viewLocation(latitude, longitude, locationName);
        });
        $("#profileInfo").on("click", function (e) {
            e.preventDefault();
            var sellerId = $(this).data("sellerid");
            viewProfile(sellerId);
        });
        $("#datatable").on("click", "tr #profileInfo", function (e) {
            e.preventDefault();
            var sellerId = $(this).data("sellerid");
            viewProfile(sellerId);
        });
         $("#auctionBuyerBidsAndViewSellerOffer").on("click", "tr #profileInfo", function (e) {
            e.preventDefault();
            var sellerId = $(this).data("sellerid");
            viewProfile(sellerId);
        });
         $("#auctionBuyerBidsAndViewSellerOffer").on("click", "tr #viewPhotos", function (e) {
             e.preventDefault();
             var accountId = $(this).data("accountid");
             var productId = $(this).data("productid");
             var sellerId = $(this).data("sellerid");
             $.ajax({
                 type: "POST",
                 async: false,
                 cache: false,
                 data: {
                     accountId: accountId,
                     productId: productId,
                     sellerId: sellerId
                 },
                 url: (contextPath + "/auctions/viewPictures"),
                 success: function (result) {
//                     $("#offerDetailsModal .modal-title").text("<spring:message code='account.type.S'/>: <spring:message code='auctionofferlist.th.offerinfoandpictures'/>");
                     $("#offerDetailsModal .modal-title").text("<spring:message code='auctionofferlist.th.offerinfoandpictures'/>");
                     $("#offerDetailsModal .modal-body").empty().html(result);
                     $("#offerDetailsModal").modal({
                         show: true,
                         backdrop: 'static',
                         keyboard: false
                     });
                 },
                 error: function (e) {
                     if (403 == e.status) {
                         window.location.href = (contextPath + "/setting/dashboard");
                     }
                     $("#offerDetailsModal .modal-body").empty().html(e);
                     $("#offerDetailsModal").modal({
                         show: true,
                         backdrop: 'static',
                         keyboard: false
                     });
                 }
             });
         });

    <sec:authorize access = "hasRole('ROLE_SELLER') or hasRole('ROLE_SELLER_AGENT')" >
                            $("#cancelOffer").on("click", function () {
                                var auctionSellersId = $(this).data("sellerid");
                                $.ajax({
                                    type: "POST",
                                    async: false,
                                    cache: false,
                                    data: {
                                        auctionSellersId: auctionSellersId
                                    },
                                    url: (contextPath + "/ssa/offer/viewofferdetails"),
                                    success: function (result) {
                                        $("#viewOfferDetailsModel .modal-content").empty().html(result);
                                        $("#viewOfferDetailsModel").modal({
                                            show: true,
                                            backdrop: 'static',
                                            keyboard: false
                                        });
                                    },
                                    error: function (e) {
                                    	 if(403 == e.status){
                                      	   window.location.href = (contextPath + "/setting/dashboard");
                                         }
                                    	$("#viewOfferDetailsModel .modal-content").empty().html(e);
                                        $("#viewOfferDetailsModel").modal({
                                            show: true,
                                            backdrop: 'static',
                                            keyboard: false
                                        });
                                    }
                                });
                            });
                            $('#decreaseOfferPrice').on('click', function (e) {
                            	e.preventDefault();
                                $btn = $(this);
                                $btn.prop("disabled", true);
                                var isTime=true		
                            	if(undefined !=EndTimediffdiffMinute && EndTimediffdiffMinute == "00") {
											   if(EndTimediffSecond <= 02) {
												   isTime=false;
											   }		
										}
                                if(isTime){
                                $.ajax({
                                    type: "POST",
                                    async: false,
                                    cache: false,
                                    data: {
                                        // action: $(this).attr("id")
                                        action: "decreasePrice"
                                    },
                                    url: (contextPath + "/ssa/offer/changeofferprice/${auctionSellerOffersView.auctionSellersId}"),
                                    success: function (result) {
                                    	setTimeout(function() { $btn.prop("disabled", false) }, 100);
                                        var offerPriceElement = document.getElementById('offerPriceOfBidTable');
                         		   if (undefined != offerPriceElement && null != offerPriceElement) { 
                                    /*       offerPriceElement.innerHTML = result +"0"; */
                                      auctionBuyerBidsAndViewSellerOffer.ajax.reload();
                                          var buyerBidPrice = $(this).data("decreasePrice");
                                    } 
                                    },
                                    error: function (e) {
                                    	 if(403 == e.status){
                                      	   window.location.href = (contextPath + "/setting/dashboard");
                                         }
                                    	console.log(e);
                                    }
                                });
                                }else{
                            	 /*    $btn.prop("disabled", false); */
                                }
                            });
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_BUYER') or hasRole('ROLE_BUYER_AGENT')">
                            $("#increasePrice, #decreasePrice").on("click", function (e) {
                            	e.preventDefault();
                            	   $btn = $(this);
                            	  $btn.prop("disabled", true);
								var isTime=true		
                            	if(undefined !=EndTimediffdiffMinute && EndTimediffdiffMinute == "00") {
											   if(EndTimediffSecond <= 02) {
												   isTime=false;
											   }		
										}
								
                             //  $btn.attr("disabled", true);
                               if (isTime) {
                                $.ajax({
                                    type: "POST",
                                    async: false,
                                    cache: false,
                                    data: {
                                        action: $(this).attr("id")
                                    },
                                    url: (contextPath + "/bba/bid/changebidprice/${auctionBuyersBean.auctionBuyersId}"),
                                    success: function (result) {
                                    	setTimeout(function() { $btn.prop("disabled", false) }, 200);
                                        //$btn.prop("disabled", false);
                                        $("#changBidPriceMsg").empty().html(result);
                                    },
                                    error: function (e) {
                                       if(403 == e.status){
                                    	   window.location.href = (contextPath + "/setting/dashboard");
                                       }
                                        $btn.prop("disabled", false);
                                    }
                                });
                               }else{
                            	   /*  $btn.prop("disabled", false); */
                               }
                            });
                            $("#cancelBid").on("click", function () {
                                var auctionBuyersId = $(this).data("buyerid");
                                $.ajax({
                                    type: "POST",
                                    async: false,
                                    cache: false,
                                    data: {
                                        auctionBuyersId: auctionBuyersId
                                    },
                                    url: (contextPath + "/bba/bid/viewbiddetails"),
                                    success: function (result) {
                                        $("#viewBidDetailsModel .modal-content").empty().html(result);
                                        $("#viewBidDetailsModel").modal({
                                            show: true,
                                            backdrop: 'static',
                                            keyboard: false
                                        });
                                    },
                                    error: function (e) {
                                    	 if(403 == e.status){
                                      	   window.location.href = (contextPath + "/setting/dashboard");
                                         }
                                    	$("#viewBidDetailsModel .modal-content").empty().html(e);
                                        $("#viewBidDetailsModel").modal({
                                            show: true,
                                            backdrop: 'static',
                                            keyboard: false
                                        });
                                    }
                                });
                            });
    </sec:authorize>
                        });
    
    function buyerBidRefrsh(){
    	auctionBuyerBidsAndViewSellerOffer.ajax.reload();
    	auctionBuyerBid.ajax.reload();
   }
    
  
    
    
</script>