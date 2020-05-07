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
        <h3><spring:message code="buyer.pickupTickets.heading.title"> </spring:message></h3>
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
            <table id="buyerPickupTickets" class="table table-striped table-bordered table_dayauction">
              <thead>
                <tr>
                  <th width="30px"><spring:message code="buyer.pickupTickets.lbl.ptn"/></th>
                  <th width="150px"><spring:message code="buyer.pickupTickets.lbl.tradeTime"/></th>
                  <th width="120px"><spring:message code="buyer.pickupTickets.lbl.dayAuction"/></th>
                  <th width="90px"><spring:message code="buyer.pickupTickets.lbl.sellerName"/></th>
                  <th width="30px"><spring:message code="buyer.pickupTickets.lbl.truckId"/></th>
                  <th width="90px"><spring:message code="buyer.pickupTickets.lbl.buyerName"/></th>
                  <th width="30px"><spring:message code="buyer.pickupTickets.lbl.purchasedQuantity"/></th>
                  <th width="40px"><spring:message code="buyer.pickupTickets.lbl.sellerLocation"/></th>
                  <th width="40px"><spring:message code="buyer.pickupTickets.lbl.buyerLocation"/></th>
                  <th width="50px"><spring:message code="buyer.pickupTickets.lbl.action"/></th>
                </tr>
              </thead>
              <tbody> 
              </tbody>
            </table>
            </div>
            <div class="clearfix"></div>
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

<div class="clearfix"></div>
<div class="modal fade" id="generatePtnNumber" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body text-danger"></div>
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
var  pickupTicketTable=null;
var queryString = "";

$(document).ready(function() {
	languageList.sEmptyTable = '<spring:message code="pickupTickets.lbl.notTickets"/>';
	pickupTicketTable = $('#buyerPickupTickets').DataTable({
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
	      "url": (contextPath + "/bba/pickupTicketsViewList"),
	      "cache": false,
	      "asyc":false,
	      "dataSrc": ""
	    },
	    
	    "columns": [
	    	{
	    	       "data": "ptn",
	    	       "render": function(data, type, json, meta) {
	    	    		 return json.ptn;
	    	       }
	    	    },
	    {
	       "data": "tradeDateTime",
	       "render": function(data, type, json, meta) {
	        return json.tradeDateTime;
	       }
	    },
	    {
	       "data": "dailyAuctionsid",
	       "render": function(data, type, json, meta) {
	          return  ('<label >'+json.dailyAuctionsid.productCatalogBean.productTypeName+' -'+json.dailyAuctionsid.productCatalogBean.productTypeName+'-'+json.dailyAuctionsid.productCatalogBean.productName+'</label>'); 
	       }
	    },
	    {"data": "sellerPublicName",
	    	   "render": function(data, type, json, meta) {
	  	         return json.sellerPublicName;
	  	       }
	    },
	    {
            "data": "truckId",
            "render": function (data, type, json, meta) {
                return "<span id='truckId' class='text-bold text-dark'>" + appendLeadingZeros(json.sellerAccountId, 6) + "-" + appendLeadingZeros(json.sellerTruckNumber, 2) + "</span>";
            }
        },
        {"data": "buyerPublicName",
	    	   "render": function(data, type, json, meta) {
	  	        return  ('<label >'+json.buyerPublicName+'</label>');
	  	       }
	    },
	    {"data": "buyerPurchasedQuantity",
	    	   "render": function(data, type, json, meta) {
	  	       return ('<label class="text-left  text-center text-hightlight">'+json.buyerPurchasedQuantity+'</label>');
	  	       }
	    },
	    {
            "data": "sellerLocation",
            "render": function (data, type, json, meta) {
               return "<a id=\"viewLocation\" data-latitude=\"" + json.sellerLocation.latitude + "\" data-longitude=\"" + json.sellerLocation.longitude + "\" class=\"text-muted\" data-toggle=\"tooltip\" data-original-title=\"" + json.sellerLocation.locationName + "\"><i class=\"fa fa-map-marker fa-2\"></i></a>";
            }
        },
        {
            "data": "buyerLocation",
            "render": function (data, type, json, meta) {
               return "<a id=\"viewLocation\" data-latitude=\"" + json.buyerLocation.latitude + "\" data-longitude=\"" + json.buyerLocation.longitude + "\" class=\"text-muted\" data-toggle=\"tooltip\" data-original-title=\"" + json.buyerLocation.locationName + "\"><i class=\"fa fa-map-marker fa-2\"></i></a>";
            }
        },
        
		 {
	      "data": "action",
	      "render": function(data, type, json, meta) {
	    	if(json.action ==0){
	    		return  ('<label class="orange">'+'<spring:message code="pickupTickets.lbl.notconfirmed"/>'+'</label>');
	    	}else{
	    		return '<spring:message code="pickupTickets.lbl.confirmed"/>';
	    	}
                    
               
	      }
	    }],
	    "rowCallback": function(row, data, index) {
	      $('td', row).eq(0).addClass('text-left  text-center');
	      $('td', row).eq(1).addClass('text-left  text-center');
	      $('td', row).eq(2).addClass('text-hightlight text-center');
	      $('td', row).eq(3).addClass('text-hightlight text-center');
	      $('td', row).eq(4).addClass('text-hightlight text-center');
	      $('td', row).eq(5).addClass('text-hightlight text-center');
	      $('td', row).eq(6).addClass('text-left  text-center text-hightlight');
	      $('td', row).eq(7).addClass('text-hightlight text-center');
	      $('td', row).eq(8).addClass('text-hightlight text-center');
	      $('td', row).eq(9).addClass('text-center');
	    }
	  });
	  
	});


   $("#buyerPickupTickets").on("click", "tr #viewLocation", function (e) {
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
