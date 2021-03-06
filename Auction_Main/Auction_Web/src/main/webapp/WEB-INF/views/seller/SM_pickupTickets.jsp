<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- page content -->
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="menu.common.pickupTickets"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3><spring:message code="seller.pickupTickets.heading.title"> </spring:message></h3>
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
           <div class="table-responsive">
                <!--fahad: removing id=ShipperTable fixed the Auction Trades table header !!!!-->
            <!--<table id="ShippersTable" class="table table-striped table-bordered">-->
            <table id="sellerPickupTicket" class="table table-striped table-bordered table_dayauction">
              <thead>
                <tr>
                  <th width="30x"><spring:message code="seller.pickupTickets.lbl.ptn"/></th>
                  <th width="150px"><spring:message code="seller.pickupTickets.lbl.tradeTime"/></th>
                  <th width="120px"><spring:message code="seller.pickupTickets.lbl.dayAuction"/></th>
                  <th width="90px"><spring:message code="seller.pickupTickets.lbl.sellerName"/></th>
                  <th width="30px"><spring:message code="seller.pickupTickets.lbl.truckId"/></th>
                  <th width="90px"><spring:message code="seller.pickupTickets.lbl.buyerName"/></th>
                  <th width="30px"><spring:message code="seller.pickupTickets.lbl.purchasedQuantity"/></th>
                  <th width="40px"><spring:message code="seller.pickupTickets.lbl.sellerLocation"/></th>
                  <th width="40px"><spring:message code="seller.pickupTickets.lbl.buyerLocation"/></th>
                  <th width="50px"><spring:message code="seller.pickupTickets.lbl.action"/></th>
                  <th width="40px"><spring:message code="seller.pickupTickets.btn.rateBuyer"/></th>
                </tr>
              </thead>
              <tbody> 
                <c:forEach items="${SellerPickupTicketList}" var="PickupTicketList">
                  <tr>
					   <td class="text-left  text-center">${PickupTicketList.ptn}</td>
                    <td class="text-left  text-center"><fmt:formatDate pattern='EEEE dd MMMM yyyy hh:mm:ss a' value="${PickupTicketList.tradeTime}"/></td>
                    <td class="text-hightlight text-center">${PickupTicketList.dailyAuctionsid.productCatalogBean.productGroupName} - ${PickupTicketList.dailyAuctionsid.productCatalogBean.productTypeName} -${PickupTicketList.dailyAuctionsid.productCatalogBean.productName}</td>
                     <td class="text-hightlight text-center">${PickupTicketList.sellerPublicName}</td>
                     <td class="text-hightlight text-center">
                     <fmt:formatNumber pattern="000000" value="${PickupTicketList.sellerAccountId}" /> -   <fmt:formatNumber pattern="00" value="${PickupTicketList.sellerTruckNumber}" /></td>
                      <td class="text-hightlight text-center">${PickupTicketList.buyerPublicName}</td>
                      <td class="text-hightlight text-center">${PickupTicketList.buyerPurchasedQuantity}</td>
                      <td class="text-hightlight text-center">
                        <a id="viewLocation" data-latitude="${PickupTicketList.sellerLocation.latitude}" data-longitude="${PickupTicketList.sellerLocation.longitude}" class="text-muted" data-toggle="tooltip" data-original-title="${PickupTicketList.sellerLocation.locationName}"><i class="fa fa-map-marker fa-2"></i></a>
                         </td>
                          <td class="text-hightlight text-center">
                               <a id="viewLocation" data-latitude="${PickupTicketList.buyerLocation.latitude}" data-longitude="${PickupTicketList.buyerLocation.longitude}" class="text-muted" data-toggle="tooltip" data-original-title="${PickupTicketList.buyerLocation.locationName}"><i class="fa fa-map-marker fa-2"></i></a>
                             </td>
                      <td class="text-hightlight text-center">
                       <c:if test="${PickupTicketList.action eq 0}">
                       <label class="orange"><spring:message code="pickupTickets.lbl.notconfirmed"/></label>
                       </c:if>
                        <c:if test="${PickupTicketList.action eq 1}">
                       <label ><spring:message code="pickupTickets.lbl.confirmed"/></label>
                       </c:if>
                       </td>
                       <td class="text-center">
                        <c:if test="${PickupTicketList.action eq 1}">
                          <!--<button class="rateSeller btn btn-success"  id="rateSeller"  data-sellerid="${PickupTicketList.sellerId.accountId}" data-buyerid="${PickupTicketList.buyerId.accountId}"   data-dailyauctionsid="${PickupTicketList.dailyAuctionsid.dailyAuctionsId}"><spring:message code="seller.pickupTickets.btn.rateBuyer"/></button>-->
                          <!--<label class="text-hightlight text-center"  id="rateSeller"  data-sellerid="${PickupTicketList.sellerId.accountId}" data-buyerid="${PickupTicketList.buyerId.accountId}"   data-dailyauctionsid="${PickupTicketList.dailyAuctionsid.dailyAuctionsId}"><i class="fa fa-star-half-full btn-lg"></i></label>-->
                          <i class="text-hightlight text-center"  id="rateSeller"  data-sellerid="${PickupTicketList.sellerId.accountId}" data-buyerid="${PickupTicketList.buyerId.accountId}"   data-dailyauctionsid="${PickupTicketList.dailyAuctionsid.dailyAuctionsId}"><i class="fa fa-star btn-lg"></i></i>
                       </c:if>
                       <c:if test="${PickupTicketList.action eq 0}">
                        <!--<label><spring:message code="seller.pickupTickets.btn.rateBuyer"/></label>-->
                        <label><i class="fa fa-star-half-empty btn-lg"></i></label>
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
<div class="modal fade" id="rateSellerModel" tabindex="-1" role="dialog" aria-hidden="true">
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
<div class="modal fade" id="managePickupTicketsShippingDetails" tabindex="-1" role="dialog" aria-hidden="true">
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
languageList.sEmptyTable = '<spring:message code="pickupTickets.lbl.notTickets"/>';
$('#sellerPickupTicket').DataTable({
    language: languageList,
    columnDefs: [{orderable: false, targets: 'disable-sorting'}] ,
    "order": [[ 1, "desc" ]] 
});
$("#sellerPickupTicket").on("click", "tr #rateSeller", function (e) {
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
        url: (contextPath + "/ssa/viewSellerRate"),
        success: function (result) {
            $("#rateSellerModel .modal-content").empty().html(result);
            $("#rateSellerModel").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        },
        error: function (e) {
            $("#rateSellerModel .modal-content").empty().html(e);
            $("#rateSellerModel").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        }
    });
})

   $("#sellerPickupTicket").on("click", "tr #viewLocation", function (e) {
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
               $("#managePickupTicketsShippingDetails .modal-title").text(locationName);
               $("#managePickupTicketsShippingDetails .modal-body").empty().html(result);
               $("#managePickupTicketsShippingDetails").modal({
                   show: true,
                   backdrop: 'static',
                   keyboard: false
               });
           },
           error: function (e) {
               $("#managePickupTicketsShippingDetails .modal-body").empty().html(e);
               $("#managePickupTicketsShippingDetails").modal({
                   show: true,
                   backdrop: 'static',
                   keyboard: false
               });
           }
       });
   });
   </script>
