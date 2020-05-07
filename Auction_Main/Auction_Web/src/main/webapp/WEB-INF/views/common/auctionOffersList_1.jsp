<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionStatusCodes"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>
<%@ page import="com.auction.commons.util.DateHelper"%>

<jsp:useBean id="now" class="java.util.Date"/>

<c:set var="OFFER_STATUS_OPEN" value="${ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus()}"/>
<c:set var="OFFER_STATUS_RUNNING" value="${ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()}"/>
<c:set var="OFFER_STATUS_SETTLING" value="${ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus()}"/>
<c:set var="AUCTION_STATUS_OPEN" value="${ENUM_AuctionStatusCodes.OPEN.getStatus()}"/>
<c:set var="AUCTION_STATUS_RUNNING" value="${ENUM_AuctionStatusCodes.RUNNING.getStatus()}"/>
<%--<c:set var="BEGINTIME" value="${DateHelper.getStringToDate(dailyAuctionView.beginTime)}"/>--%>
<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>
<%--<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}&nbsp;</label><br/>${dailyAuctionView.productDescription}</p><br/><p class='show-product-info'><label>${productContainerSpecsPhl}&nbsp;</label><br/>${dailyAuctionView.containerSpecs}</p>"/>--%>
<c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}:&nbsp;</label>${dailyAuctionView.productDescription}</p><p class='show-product-info'><label>${productContainerSpecsPhl}:&nbsp;</label>${dailyAuctionView.containerSpecs}</p>"/>

<style>
    table#dailyAuctionDetails tr:nth-child(2) td{text-align:center;color: #8e0000 !important;font-weight: bolder;font-size: 20px;}
    table#dailyAuctionDetails tr:nth-child(2) td:last-child{color: #fff !important;font-weight: bolder;}
    /*table#dailyAuctionDetails tr:nth-child(2) td:last-child{background-color: #26883f !important;color: #fff !important;vertical-align: middle !important;}*/
    table#dailyAuctionDetails tr:nth-child(3) td{color: #333 !important;}
    table#dailyAuctionDetails tbody tr td:not(:first-child):nth-child(n+1){text-align:center;}
    table#datatable tbody tr td:nth-child(n+3){text-align:center;}
</style>
<div class="right_col" role="main"> 
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><a href="${contextPath}/auctions/auctionList"><spring:message code="menu.auction.dayauction"/></a></li>
        <li><spring:message code="menu.auction.dayauction.sellers.offers"/></li>
        <li class="todaydatetime">
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="<%=InternetTiming.getInternetDateTime()%>"/>
            <span id="current-time">
                <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="<%=InternetTiming.getInternetDateTime()%>"/>
            </span>
        </li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_center">
                <h3><spring:message code="auctionlist.h3.dayauction"/></h3>
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
                    <div class="x_content">
                        <div class="x_panel auction-list-bordered">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table_dayauction" id="dailyAuctionDetails">
                                    <thead>
                                        <%-- <tr>
                                          <th colspan="2"><spring:message code="auctionlist.th.dailyauctions"/></th>
                                          <th>
                                            <label class="label auction status-${dailyAuctionView.auctionStatusCode}">
                                              <spring:message code="auction.status.${dailyAuctionView.auctionStatusCode}"/>
                                            </label>
                                          </th>
                                          <th colspan="2"><fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${now}"/></th>
                                          <th colspan="2" id="current-time"></th>
                                        </tr> --%>
                                        <tr>
                                            <th><spring:message code="auctionofferlist.th.startime"/></th>
                                            <th><spring:message code="auctionofferlist.th.endtime"/></th>
                                            <th><spring:message code="auctionofferlist.th.group"/></th>
                                            <th><spring:message code="auctionofferlist.th.name"/></th>
                                            <th><spring:message code="auctionofferlist.th.type"/></th>
                                            <!-- <th colspan="3"></th> -->
                                            <th id="remaining-time-caption">
                                                <c:choose>
                                                    <c:when test="${dailyAuctionView.auctionStatusCode eq AUCTION_STATUS_OPEN}">
                                                        <spring:message code="auctionofferlist.th.timetostart"/>
                                                    </c:when>
                                                    <c:when test="${dailyAuctionView.auctionStatusCode eq AUCTION_STATUS_RUNNING}">
                                                        <spring:message code="auctionofferlist.th.timetoend"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <!--fahad: change status message-->
                                                        <%--<spring:message code="auctionofferlist.th.status"/>--%>
                                                        <spring:message code="auctionlist.th.auctionstatus"/>                                                       
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                        </tr>
                                        <!--fahad: test fixing the time display in Arabic -->
                                        <tr>
                                            <!--<td>${dailyAuctionView.beginTime}</td>-->
                                            <td> 
                                                  <!--<td>${dailyAuctionView.beginTime}</td>-->
                                                <%--<fmt:setLocale value="en_US" scope="session"/>--%>
                                                <fmt:parseDate value="${dailyAuctionView.beginTime}" type="time" pattern="hh:mm" var="beginTime"/>   
                                                <fmt:formatDate value="${beginTime}"  type="time" pattern="hh:mm"/>
                                                <%--<fmt:formatDate value="<%=dailyAuctionView.getBeginTime()%>"  type="time" pattern="hh:mm a"/>--%>                                               
                                            </td>
                                            <!--<td>${dailyAuctionView.endTime}</td>-->
                                            <td> 
                                                <!--${dailyAuctionView.endTime}-->

                                                <fmt:parseDate value="${dailyAuctionView.endTime}" type="time" pattern="hh:mm" var="endTime"/>   
                                                <fmt:formatDate value="${endTime}"  type="time" pattern="hh:mm"/>
                                            </td>                                            
                                            <td class="show-product-info" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="${productDescAndContanerSpace }">${dailyAuctionView.productGroupName}</td>
                                            <td class="show-product-info" data-html="true"  data-toggle="tooltip" data-placement="bottom" data-original-title="${productDescAndContanerSpace}">${dailyAuctionView.productName}</td>
                                            <td class="show-product-info" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="${productDescAndContanerSpace}">${dailyAuctionView.productTypeName}</td>                    
                                            <td id="remaining-time"></td>
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
                            <h3><spring:message code="auctionofferlist.th.auctionsellers"/></h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="x_content">
                        <div class="x_panel auction-offer-list-bordered">
                            <div class="x-title text-right">
                                <%-- <spring:message code="auctionofferlist.h2.auctionofferlist"/></h2> --%>
                                <sec:authorize access="hasRole('ROLE_SELLER') or hasRole('ROLE_SELLER_AGENT')">
                                    <c:choose>
                                        <c:when test="${((AUCTION_STATUS_OPEN eq dailyAuctionView.auctionStatusCode) || (AUCTION_STATUS_RUNNING eq dailyAuctionView.auctionStatusCode))}">
                                            <c:choose>
                                                <c:when test="${(not empty auctionSellersBean && not empty auctionSellersBean.auctionSellersId && auctionSellersBean.auctionSellersId > 0) &&
                                                                ((OFFER_STATUS_OPEN eq auctionSellersBean.sellerOfferStatus) || (OFFER_STATUS_RUNNING eq auctionSellersBean.sellerOfferStatus))}">

                                                        <span id="offer-price-update" class="hidden">
                                                            <button class="btn btn-danger btn-lg" id="decreasePrice">
                                                                <i class="fa fa-arrow-down"></i>
                                                            </button>&nbsp;

                                                            <span class="read-only read-only-sm w20">
                                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${defaultPrice}"/>
                                                            </span>&nbsp;

                                                            <%-- <c:if test="${AUCTION_STATUS_OPEN eq dailyAuctionView.auctionStatusCode && OFFER_STATUS_OPEN eq auctionSellersBean.sellerOfferStatus}">
                                                              <button class="btn btn-success btn-lg" id="increasePrice">
                                                                    <i class="fa fa-arrow-up"></i>
                                                              </button>
                                                            </c:if> --%>
                                                        </span>
                                                        <a href="${contextPath}/ssa/offer/${dailyAuctionView.dailyAuctionsId}/${auctionSellersBean.auctionSellersId}" class="btn btn-warning btn-lg pageContentLoading" id="testhref">
                                                            <i class="fa fa-edit"></i>&nbsp;&nbsp;<spring:message code="auctionofferlist.div.a.updateOffer"/>
                                                        </a>
                                                        <button class="btn btn-danger btn-lg" id="cancelOffer" data-sellerid="${auctionSellersBean.auctionSellersId}">
                                                            <i class="fa fa-times"></i>&nbsp;&nbsp;<spring:message code="auctionofferlist.div.a.cancelOffer"/>
                                                        </button>
                                                </c:when>
                                                <c:when test="${(empty auctionSellersBean || empty auctionSellersBean.auctionSellersId)}">
                                                    <a href="${contextPath}/ssa/offer/${dailyAuctionView.dailyAuctionsId}" class="btn btn-info btn-lg pull-right pageContentLoading" id="testhref">
                                                        <i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="auctionofferlist.div.a.placeOffer"/>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose> 
                                    <%-- 
                                                            Issue #32 ISSUES SOLUTION
                                                                        <c:choose>
                                                                            <c:when test="${(not empty auctionSellersBean && not empty auctionSellersBean.auctionSellersId &&  auctionSellersBean.accountProfileBean.accountId == loginUser.accountProfileBean.accountId) &&
                                                                                            ((OFFER_STATUS_SETTLING eq auctionSellersBean.sellerOfferStatus) || (OFFER_STATUS_SETTLING eq auctionSellersBean.sellerOfferStatus))}">


                                                <button class="btn btn-info btn-lg pull-right" onclick="changeSellerLocation(${auctionSellersBean.accountProfileBean.accountId},${dailyAuctionView.dailyAuctionsId})">
                                                    <i class="fa fa-plus"></i>&nbsp;&nbsp;
                                                    <spring:message code="editProfile.b.updateYourLocation"/>
                                                </button> 

                                        </c:when>
                                    </c:choose> --%>

                                    <div class="clearfix"></div>
                                </sec:authorize>
                            </div>
                            <div class="x_panel">
                                <div class="table-responsive">
                                    <table id="datatable" class="table table-striped table-bordered table_offer" id="dailyAuctionDetails">
                                        <thead>
                                            <tr>
                                                <th width="200px" class="hidden">Id</th>
                                                <th><spring:message code="auctionofferlist.th.sellername"/></th>
                                                <th width="60px"><spring:message code="auctionofferlist.th.offerqty"/></th>
                                                <th width="60px"><spring:message code="auctionofferlist.th.availableqty"/></th>
                                                <th class="hidden"><spring:message code="auctionofferlist.th.offerprice"/></th>
                                                <th width="60px"><spring:message code="auctionofferlist.th.offerprice"/></th>
                                                <th width="60px"><spring:message code="auctionofferlist.th.location"/></th>
                                                <!--<th width="120px"><spring:message code="pickupTickets.lbl.truckId"/></th>-->
                                                <th width="120px"><spring:message code="pickupTickets.lbl.truckId"/><br><i class="fa fa-truck"></i></th>
                                                <th width="120px" class="disable-sorting"><spring:message code="auctionofferlist.th.offerinfoandpictures"/></th>
                                                <th width="70px" class="disable-sorting"><spring:message code="auctionofferlist.th.partialallowed"/></th>
                                                <th width="70px"><spring:message code="auctionofferlist.th.history"/></th>
                                                <th width="90px"><spring:message code="auctionofferlist.th.rating"/></th>
                                                <th width="70px"><spring:message code="auctionofferlist.th.cancleper"/></th>
                                                <th width="80px"><spring:message code="auctionofferlist.th.status"/></th>
                                                <th width="80px" class="disable-sorting"><spring:message code="auctionofferlist.th.auctionbuyers"/></th>
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
                <button type="button" class="close btn btn-lg" data-dismiss="modal"><span aria-hidden="true">×</span> </button>
                <h1 class="modal-title text-capitalize"> </h1>         
            </div>
            <div class="modal-body"> </div>
            <div class="modal-footer">
                <button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal">
                    <i class="fa fa-times"></i>&nbsp; <spring:message code="btn.close"/>
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade scroll-modal" id="profileViewModel" tabindex="-1" role="dialog" aria-hidden="true">
    <!--fahad: size of profile info popup-->
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                <!--fahad: change profile title text size-->
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
    <!--fahad: checkit lg or md-->
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
        </div>
    </div>
</div>

<div class="modal fade" id="changeSellerLocationModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">
                </h4>
            </div>
            <div class="modal-body">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var internetTime = ${InternetTiming.getInternetDateTimeAsMiliSeconds()};
    var auctionBeginTime = ${dailyAuctionView.beginLong};
    var auctionEndTime = ${dailyAuctionView.endLong};
    var yes = '<spring:message code="yes"/>';
    var no = '<spring:message code="no"/>';
    var btn_auction_buyers = '<spring:message code="btn.auction.buyers"/>';
    var btn_view = '<spring:message code="btn.view"/>';
    var btn_img = '<spring:message code="btn.img"/>';
    var time_to_end = '<spring:message code="auctionofferlist.th.timetoend"/>';
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

    var auctionOfferStatus = Object();
    auctionOfferStatus["offer_status_1"] = '<spring:message code="auction.seller.offer.status.1"/>';
    auctionOfferStatus["offer_status_2"] = '<spring:message code="auction.seller.offer.status.2"/>';
    auctionOfferStatus["offer_status_3"] = '<spring:message code="auction.seller.offer.status.3"/>';
    auctionOfferStatus["offer_status_4"] = '<spring:message code="auction.seller.offer.status.4"/>';
    auctionOfferStatus["offer_status_5"] = '<spring:message code="auction.seller.offer.status.5"/>';
    auctionOfferStatus["offer_status_6"] = '<spring:message code="auction.seller.offer.status.6"/>';

    function getAuctionStatus(key) {
        return auctionStatus[key];
    }

    function getAuctionOfferStatus(key) {
        return auctionOfferStatus[key];
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
    function setOffRemainingTime() {

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
    setOffRemainingTime();
    currentTimeInterval = setInterval(setOffRemainingTime, 1000);

    function getTimeStatus(key) {
        return timeStatus[key];
    }
    function formatDate(longDate) {
        /* var date = new Date(longDate);
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
         return dmonth + "/" + currentDate + "/" + date.getFullYear() + " " + strTime; */
        return "";
    }
    function formatDate(strTime) {
        strTime = (strTime.indexOf("AM") > -1) ? strTime.replace("AM", getTimeStatus("am")) : strTime.replace("PM", getTimeStatus("pm"));
        return strTime;
    }

    function changeSellerLocation(auctionSellersId, dailyAuctionId) {

        $("#changeSellerLocationModel .modal-title").text('Change Location');

        $.ajax({
            type: "GET",
            async: false,
            cache: false,
            url: (contextPath + "/seller/sellerchangelocation/" + auctionSellersId + "/" + dailyAuctionId),
            success: function (result) {
                $("#changeSellerLocationModel .modal-body").empty().html(result);
                $("#changeSellerLocationModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
                $('#changeSellerLocationModel').on('shown.bs.modal', function (e) {
                    $("#productBox").focus();
                });
            },
            error: function (e) {
            	 if(403 == e.status){
                	   window.location.href = (contextPath + "/setting/dashboard");
                   }
                $("#changeSellerLocationModel .modal-body").empty().html(e);
                $("#changeSellerLocationModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            }
        });

    }
    var auctionTable = null;
    var auctionSellerOffer = null;
    var auctionBuyerBid = null;
    var queryString = "/auctions/offers/" +${dailyAuctionView.dailyAuctionsId};
    $(document).ready(function () {
        $('#testhref').click(function () {

            return checkAuctionRunningTime();

        });
        //window.history.pushState("object or string", "EPAP|Dashboard", contextPath+"/setting/dashboard");
        window.history.pushState("object or string", "EPAP|AuctionList", contextPath + "/auctions/auctionlist");
        languageList.sEmptyTable = '<spring:message code="auctionofferlist.emptytable"/>';
        auctionSellerOffer = $('#datatable').DataTable({
            language: languageList,
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
                "url": (contextPath + "/auctions/selleroffers/${dailyAuctionView.dailyAuctionsId}"),
                "cache": false,
                "asyc": false,
                "dataSrc": ""
            },
            "columns": [{
                    "type": "numeric",
                    "data": "auctionSellersId",
                    "render": function (data, type, json, meta) {
                        return parseInt(json.auctionSellersId);
                    }
                }, {
                    "data": "publicName",
                    "render": function (data, type, json, meta) {
//                        return "<a id=\"profileInfo\" data-sellerid=\"" + json.accountId + "\" class=\"text-muted text-link text-capitalize text-hightlight\">" + json.publicName + "</i></a>";
                        return "<a id=\"profileInfo\" data-sellerid=\"" + json.accountId + "\" class=\"text-capitalize text-hightlight text-underline-hyper\">" + json.publicName + "</i></a>";

                    }
                },
                {
                    "data": "offerQuantity",
                    "render": function (data, type, json, meta) {
//                        return json.offerQuantity.toLocaleString();
                        return "<span id='offerPrice' class='text-bold text-dark'>" + json.offerQuantity.toLocaleString() + "</span>";
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
                    "data": "offerPrice"
                }, {
                    "data": "offerPrice",
                    "render": function (data, type, json, meta) {
                        if (json.auctionSellersId > 0 && "${auctionSellersBean.auctionSellersId}" === json.auctionSellersId) {
                            if (json.offerPrice > 0) {
                                $("#offer-price-update").removeClass("hidden");
                            } else {
                                $("#offer-price-update").addClass("hidden");
                            }
                        }
                        if (json.auctionSellersId > 0 && json.offerPrice > 0 ) {
                            return "<span id='offerPrice' class='text-bold text-dark blink_me'>" + json.offerPrice.toFixed(2) + "</span>";
                        } else {
                            return "<span id='offerPrice' class='text-bold text-dark'>" + market + "</span>";
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
                        return ('<label class="label text-hightlight auction-offer status-' + (json.sellerOfferStatusCode) + '">' + getAuctionOfferStatus(("offer_status_" + json.sellerOfferStatusCode)) + '</label>');
                    }
                },
                {
                    "data": "dailyAuctionsId",
                    "render": function (data, type, json, meta) {
                        return "<a href=\"" + contextPath + "/auctions/bids/" + json.auctionSellersId + "\" class=\"btn btn-info btn-lg pageContentLoading\" data-toggle=\"tooltip\" data-original-title=\"" + btn_auction_buyers + "\"><i class=\"fa fa-arrow-down\"></i></a>";
                    }
                }
            ],
            "rowCallback": function (row, data, index) {
                if (data.auctionBuyers) {
                    $(row).addClass("own-bid");
                }
                if (data.auctionSellersId == "${auctionSellersBean.auctionSellersId}") {
                    $(row).addClass("own-offer");
//                    $(row).attr("data-toggle", "tooltip");
//                    $(row).attr("data-original-title", '<spring:message code="your.offer"/>');
                }
                $('td', row).eq(0).addClass("hidden");
                $('td', row).eq(4).addClass("hidden");
                //$('td', row).eq(3).addClass('text-hightlight');
                //$('td', row).eq(5).addClass('text-hightlight');
            }
        });
        //Websocket programing start
        /*
         var socket = new SockJS((contextPath + "/auctionsdata"));
         socket.onclose = function() {
         alert("disconnected");
         }
         var client = Stomp.over(socket);
         client.debug = null
         client.connect({}, function(frame) {
         client.subscribe(("/wssauctions/refreshUI"), function(message) {
         // window.location.reload();
         //auctionSellerOffer.ajax.reload();
         alert("Hi-refreshUI");
         loadPageByAjax(null,(contextPath + "/auctions/offers/"+${dailyAuctionView.dailyAuctionsId}));
         getAvailableBalance();
         });
         client.subscribe(("/wssauctions/refreshOfferUI"), function(message) {
         alert("Hi-refreshOfferUI");
         auctionSellerOffer.ajax.reload();
         getAvailableBalance();
         });
         });
         */
//Websocket programing end



        $("#datatable").on("click", "tr #viewLocation", function (e) {
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
        });
        $("#datatable").on("click", "tr #viewPhotos", function (e) {
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
//                    $("#offerDetailsModal .modal-title").text("<spring:message code='account.type.S'/>: <spring:message code='auctionofferlist.th.offerinfoandpictures'/>");
                    $("#offerDetailsModal .modal-title").text("<spring:message code='auctionofferlist.th.offerinfoandpictures'/>");
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
        });
        $("#datatable").on("click", "tr #profileInfo", function (e) {
            var sellerId = $(this).data("sellerid");
            $.ajax({
                type: "POST",
                async: false,
                cache: false,
                data: {
                    sellerId: sellerId
                },
                url: (contextPath + "/auctions/profileInfo"),
                success: function (result) {
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
        });

        //Increase Decrease offer price
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
        })
        $('#increasePrice, #decreasePrice').on('click', function (e) {
        	e.preventDefault();
            $btn = $(this);
            $btn.prop("disabled", true);
            $.ajax({
                type: "POST",
                async: false,
                cache: false,
                data: {
                    action: $(this).attr("id")
                },
                url: (contextPath + "/ssa/offer/changeofferprice/${auctionSellersBean.auctionSellersId}"),
                success: function (result) {
                    console.log(result);
                    setTimeout(function() { $btn.prop("disabled", false) }, 100);
                },
                error: function (e) {
                	 if(403 == e.status){
                    	   window.location.href = (contextPath + "/setting/dashboard");
                       }
                	console.log(e);
                }
            });
        });
    </sec:authorize>
    });
</script>