<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes"%>

<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>
<c:set var="BUYER_BID_STATUS_SETTLING" value="${ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus()}"/>
<c:set var="BUYER_BID_STATUS_FINISHED" value="${ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus()}"/>
<style>
    table#manageShippingTable tbody tr td:nth-child(2){color: #8e0000 !important;font-weight: bolder;}
    table#manageShippingTable tbody tr td{text-align:center;}
</style>
<!-- page content -->
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="menu.auction.manageshipping"/></li>
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
                <h3><spring:message code="manageshipping.heading.title"> </spring:message></h3>
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
                    <table id="manageShippingTable" class="table table-striped table-bordered table_dayauction">
                        <thead>
                            <tr>
                                <th width="150px"><spring:message code="manageshipping.th.tradeTime"/></th>
                                <th width="50px"><spring:message code="manageshipping.th.dayAuction"/></th>
                                <th width="50px"><spring:message code="manageshipping.th.auctionOffer"/></th>
                                <th width="50px"><spring:message code="manageshipping.th.sellerLocation"/></th>
                                <th width="50px"><spring:message code="manageshipping.th.auctionBid"/></th>
                                <th width="50px"><spring:message code="manageshipping.th.buyerLocation"/></th>
                                <th width="50px" class="disable-sorting"><spring:message code="manageshipping.confirmdelivery.th.action"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${auctionBuyersBeanList}" var="buyer">
                                <tr>
                                    <td>
                                        <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${buyer.dailyAuctionsBean.beginTime}"/>
                                    </td>
                                    <c:set var="productDescAndContanerSpace" value="<p class='show-product-info'><label>${productDescriptionPhl}:&nbsp;</label>${buyer.dailyAuctionsBean.productCatalogBean.productDescription}</p><p class='show-product-info'><label>${productContainerSpecsPhl}&nbsp;</label>${buyer.dailyAuctionsBean.productCatalogBean.containerSpecs}</p>"/>
                                    <td class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="top" data-original-title="${productDescAndContanerSpace}">${buyer.dailyAuctionsBean.productCatalogBean.productGroupName} - ${buyer.dailyAuctionsBean.productCatalogBean.productName} - ${buyer.dailyAuctionsBean.productCatalogBean.productTypeName}</td>
                                    <td><a id="profileInfo" data-sellerid="${buyer.auctionSellersBean.accountProfileBean.accountId}" class="text-hightlight text-bold text-underline-hyper">${buyer.auctionSellersBean.accountProfileBean.publicName}</a></td>
                                    <td>
                                        <a id="viewLocation" data-latitude="${buyer.auctionSellersBean.accountLocationsBean.latitude}" data-longitude="${buyer.auctionSellersBean.accountLocationsBean.longitude}" class="text-muted" data-toggle="tooltip" data-original-title="${buyer.auctionSellersBean.accountLocationsBean.locationName}"><i class="fa fa-map-marker fa-2"></i></a>
                                    </td>
                                    <td><a id="profileInfo" data-sellerid="${buyer.accountProfileBean.accountId}" class="text-hightlight text-bold text-underline-hyper">${buyer.accountProfileBean.publicName}</a></td>
                                    <td>
                                        <a id="viewLocation" data-latitude="${buyer.accountLocationsBean.latitude}" data-longitude="${buyer.accountLocationsBean.longitude}" class="text-muted" data-toggle="tooltip" data-original-title="${buyer.accountLocationsBean.locationName}"><i class="fa fa-map-marker fa-2"></i></a>
                                    </td>
                                    <td>
                                        <label id="confirmDialogMsg" class="hidden" ><spring:message code="buyer.confirmdelivery.msg"/></label> 
                                        <c:choose>
                                            <c:when test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_SETTLING}">
                                                <a id="confirmDelivery" class="btn btn-lg btn-warning pageContentLoadingAfterConfirm " href="${contextPath}/sproptshp/confirmdelivery/${buyer.auctionBuyersId}" data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmdelivery.button"/>' ><i class="fa fa-truck btn btn-lg"></i></a>
                                            </c:when>
                                            <c:when test="${buyer.buyerBidStatus eq BUYER_BID_STATUS_FINISHED}">
                                                <label  class="label confirmed-delivery btn btn-lg" data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmedcelivery.button"/>' ><i class="fa fa-check btn btn-lg"></i></label>
                                                </c:when>
                                                <c:otherwise>
                                                <label   class="label confirmed-delivery btn btn-lg" data-toggle="tooltip" data-original-title='<spring:message code="buyer.confirmedcelivery.button"/>' ><i class="fa fa-check btn btn-lg"></i></label>
                                                </c:otherwise>
                                            </c:choose>
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
<!-- /page content -->
<div class="clearfix"></div>
<div class="modal fade" id="manageShippingDetails" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
                <h1 class="modal-title"><spring:message code="common.profile.info"/></h1>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer text-right">
                <!--<button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="btn.close"/></button>-->
                                        <button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal">
                    <i class="fa fa-times"></i>&nbsp; <spring:message code="btn.close"/>
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        window.history.pushState("object or string", "EPAP|ManageShipping", contextPath + "/sproptshp/manageshipping");
        languageList.sEmptyTable = '<spring:message code="manageshipping.emptytable"/>';
        $('#manageShippingTable').DataTable({
            language: languageList,
            columnDefs: [{orderable: false, targets: 'disable-sorting'}]
        });

        $("#manageShippingTable").on("click", "tr #viewLocation", function (e) {
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
                    $("#manageShippingDetails .modal-title").text(locationName);
                    $("#manageShippingDetails .modal-body").empty().html(result);
                    $("#manageShippingDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#manageShippingDetails .modal-body").empty().html(e);
                    $("#manageShippingDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        });

        $("#manageShippingTable").on("click", "tr #profileInfo", function (e) {
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
//     	      $("#manageShippingDetails .modal-title").text("Profile Information");
//     	      $("#manageShippingDetails .modal-body").empty().html(result);
//     	      $("#manageShippingDetails").modal({
                    $("#manageShippingDetails .modal-body").empty().html(result);
                    $("#manageShippingDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#manageShippingDetails .modal-body").empty().html(e);
                    $("#manageShippingDetails").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        });
    });
</script>