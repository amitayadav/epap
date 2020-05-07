<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>
<c:set var="BUYER_BID_STATUS_SETTLING" value="${ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus()}"/>
<c:set var="BUYER_BID_STATUS_FINISHED" value="${ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus()}"/>
<style>
    table#confirmDeliveryTable tbody tr td:nth-child(2){color: #8e0000 !important;font-weight: bolder;}
    table#confirmDeliveryTable tbody tr td{text-align:center;}
    table#confirmDeliveryTable tbody tr th:nth-child(6){width:50px !important;}
</style>
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="menu.common.confirmdelivery"/></li>
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
                <h3>
                    <spring:message code="buyer.confirmdelivery.heading.title"> </spring:message>
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
            <div class="x_panel">
                <div class="table-responsive ">
                    <table id="confirmDeliveryTable" class="table table-striped table-bordered table_dayauction">
                        <thead>
                            <tr>
                                <th width="150px"><spring:message code="buyer.confirmdelivery.th.tradeTime"/></th>
                                <th width="50px"><spring:message code="buyer.confirmdelivery.th.dayAuction"/></th>
                                <th width="50px"><spring:message code="buyer.confirmdelivery.th.auctionOffer"/></th>
                                <th width="50px"><spring:message code="buyer.confirmdelivery.th.sellerLocation"/></th>
                                <th width="50px"><spring:message code="buyer.confirmdelivery.th.buyerLocation"/></th>
                                <th width="50px" class="disable-sorting"><spring:message code="buyer.confirmdelivery.th.action"/></th>
                          		<th  width="50px"><spring:message code="buyer.confirmdelivery.btn.rateSeller"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${auctionBuyersBeanList}" var="buyer">
                                <tr>
                                 <td class="text-hightlight">
                                  <c:choose>
                                            <c:when test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_SETTLING}">
                                               <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${buyer.actualStartTime}"/>   	  
                                                </c:when>
                                                <c:when test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_FINISHED}">
                                                <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${buyer.actualEndTime}"/>
                                                </c:when>
                                            </c:choose>
                                    </td>
                                    <c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}:&nbsp;</label>${buyer.dailyAuctionsBean.productCatalogBean.productDescription}</p><p class='show-product-info'><label>${productContainerSpecsPhl}:&nbsp;</label>${buyer.dailyAuctionsBean.productCatalogBean.containerSpecs}</p>"/>
                                    <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${buyer.dailyAuctionsBean.productCatalogBean.productGroupName} - ${buyer.dailyAuctionsBean.productCatalogBean.productName} - ${buyer.dailyAuctionsBean.productCatalogBean.productTypeName}</td>
                                    <td><a id="profileInfo" data-sellerid="${buyer.auctionSellersBean.accountProfileBean.accountId}" class="text-muted text-link text-capitalize text-hightlight text-underline-hyper">${buyer.auctionSellersBean.accountProfileBean.publicName}</a></td>
                                    <td>
                                        <a id="viewLocation" data-latitude="${buyer.auctionSellersBean.accountLocationsBean.latitude}" data-longitude="${buyer.auctionSellersBean.accountLocationsBean.longitude}" class="text-muted" data-toggle="tooltip" data-original-title="${buyer.auctionSellersBean.accountLocationsBean.locationName}"><i class="fa fa-map-marker fa-2"></i></a>
                                    </td>
                                    <td>
                                        <a id="viewLocation" data-latitude="${buyer.accountLocationsBean.latitude}" data-longitude="${buyer.accountLocationsBean.longitude}" class="text-muted" data-toggle="tooltip" data-original-title="${buyer.accountLocationsBean.locationName}"><i class="fa fa-map-marker fa-2"></i></a>
                                    </td>
                                    <td>
                                        <label id="confirmDialogMsg" class="hidden" ><spring:message code="buyer.confirmdelivery.msg"/></label> 
                                        <c:choose>
                                            <c:when test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_SETTLING}">
                                                <%-- <a id="confirmDelivery" class="btn btn-sm btn-warning " href="${contextPath}/bba/confirmdelivery/${buyer.auctionBuyersId}" data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmdelivery.button"/>' onclick="return confirmDelivery();"><i class="fa fa-lock"></i>&nbsp;&nbsp;<spring:message code="btn.confirm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a> --%>                 		
                                                <a id="confirmDelivery" class="btn btn-lg pageContentLoadingAfterConfirm " href="${contextPath}/bba/confirmdelivery/${buyer.auctionBuyersId}" data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmdelivery.button"/>' ><i class="fa fa-truck fa-2" aria-hidden="true"></i></a>                	                 	  
                                                </c:when>
                                                <c:when test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_FINISHED}">
                                                <!--<label  class="btn btn-lg orange" data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmedcelivery.button"/>' ><label class="fa fa-check-square-o btn btn-lg"></label></label>-->
                                                <label  data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmedcelivery.button"/>' ><i class="fa fa-check btn btn-lg btn-warning"></i></label>
                                                </c:when>
                                                <c:otherwise>
                                                <label   data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmedcelivery.button"/>' ><i class="fa fa-check btn btn-lg btn-warning"></i></label>
                                                </c:otherwise>
                                            </c:choose>
                                    </td>
                                    <td>
                                      <c:if test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_SETTLING}">
                                      <label><spring:message code="buyer.confirmdelivery.btn.rateSeller2"/></label>
                                          
                                     </c:if>
                                     <c:if test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_FINISHED}">
                                         <!--<button class="rateSeller btn btn-success"  id="rateBuyer"  data-sellerid="${buyer.auctionSellersBean.accountProfileBean.accountId}" data-buyerid="${buyer.accountProfileBean.accountId}" data-dailyauctionsid="${buyer.dailyAuctionsBean.dailyAuctionsId}"><spring:message code="buyer.confirmdelivery.btn.rateSeller"/></button>-->
                                         <button class="fa fa-star-half-empty"  id="rateBuyer"  data-sellerid="${buyer.auctionSellersBean.accountProfileBean.accountId}" data-buyerid="${buyer.accountProfileBean.accountId}" data-dailyauctionsid="${buyer.dailyAuctionsBean.dailyAuctionsId}"></button>
                                     </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="rateBuyerModel" tabindex="-1" role="dialog" aria-hidden="true">
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
<div class="clearfix"></div>
<div class="modal fade" id="viewDeliveryDetails" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                <h1 class="modal-title"><spring:message code="common.profile.info"/></h1>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer text-right">
<!--                <button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="btn.close"/></button>-->              
                <button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal">
                    <i class="fa fa-times"></i>&nbsp; <spring:message code="btn.close"/>
                </button>                
            </div>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<script type="text/javascript">
	var queryString = "";
    $(document).ready(function () {
    	window.history.pushState("object or string", "EPAP|Comment", contextPath + "/bba/sysaConfirmDelivery");
        languageList.sEmptyTable = '<spring:message code="buyer.confirmdelivery.emptytable"/>';
        $('#confirmDeliveryTable').DataTable({
            language: languageList,
            columnDefs: [{orderable: false, targets: 'disable-sorting'}]
        });

        $("#confirmDeliveryTable").on("click", "tr #viewLocation", function (e) {
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
                    $("#viewDeliveryDetails .modal-title").text(locationName);
                    $("#viewDeliveryDetails .modal-body").empty().html(result);
                    $("#viewDeliveryDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#viewDeliveryDetails .modal-body").empty().html(e);
                    $("#viewDeliveryDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        });

        $("#confirmDeliveryTable").on("click", "tr #profileInfo", function (e) {
            e.preventDefault();
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
//   	      $("#viewDeliveryDetails .modal-title").text("Profile Information");
//   	      $("#viewDeliveryDetails .modal-body").empty().html(result);
//   	      $("#viewDeliveryDetails").modal({
                    $("#viewDeliveryDetails .modal-body").empty().html(result);
                    $("#viewDeliveryDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#viewDeliveryDetails .modal-body").empty().html(e);
                    $("#viewDeliveryDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        });
    });
        
    $("#confirmDeliveryTable").on("click", "tr #rateBuyer", function (e) {
    	e.preventDefault();
    	   var sellerid = $(this).data("sellerid");
           var buyerid = $(this).data("buyerid");
           var dailyAuctionsId =$(this).data("dailyauctionsid");
        $.ajax({
            type: "POST",
            async: false,
            cache: false,
            data: {
            	sellerid : sellerid,
            	buyerid : buyerid,
            	dailyAuctionsId:dailyAuctionsId
            },
            url: (contextPath + "/bba/viewBuyerRate"),
            success: function (result) {
                $("#rateBuyerModel .modal-content").empty().html(result);
                $("#rateBuyerModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            },
            error: function (e) {
                $("#rateBuyerModel .modal-content").empty().html(e);
                $("#rateBuyerModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            }
        });
    })
    /*   function confirmDelivery(){
     var confirmReply=confirm("<spring:message code="buyer.confirmdelivery.msg"/>");
     if(confirmReply){
     $(".tooltip").tooltip("hide");
     loadPageByAjax($(this));
     return true;
     }
     return false;
     } */
</script>