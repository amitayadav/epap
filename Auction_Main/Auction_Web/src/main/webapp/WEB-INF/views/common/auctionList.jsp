<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>

<style>
	table#datatable tbody tr td:nth-child(5), 
	table#datatable tbody tr td:nth-child(6),
	/*table#datatable tbody tr td:nth-child(7){text-align:center;color: #8e0000 !important;font-weight: bolder;}*/
	table#datatable tbody tr td:nth-child(7){text-align:center;color: #761c19 !important;font-weight: bolder;}
</style>

<div class="right_col" role="main">
 <ul class="breadcrumb">
  <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
  <li><spring:message code="menu.auction.dayauction"/></li>
  <li class="todaydatetime">
  	<fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
  	<span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	</span>
  </li>
</ul>
  <div class="clearfix"></div>
  <div class="">
    <div class="page-title">
      <div class="title_center h3">
        <h3><spring:message code="auctionlist.h3.dayauctions"/></h3>
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
    <div class="x_content"> 
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="x_panel auction-list-bordered">

            <div class="x_content">
            <div class="x_panel">
              <div class="table-responsive ">
              <table id="datatable" class="table table-striped table-bordered table_dayauction" >
                <thead>
                  <%-- <tr>
                  	<th colspan="4"><spring:message code="auctionlist.th.dailyauctions"/></th>
                  	<th colspan="2">
                  		<fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${now}"/>
                  	</th>
                  	<th colspan="2" >
                  	</th>
                  </tr> --%>
                  <tr>
                                            <!--fahad: extend auction date column-->
                  <th width="140px"><spring:message code="auctionlist.th.AuctionDate"/></th>
                  <!--fahad: change width of column-->
                    <th width="100px"><spring:message code="auctionlist.th.begintime"/></th>
                    <th width="100px"><spring:message code="auctionlist.th.endtime"/></th>
                    <th width="140px" class="hidden-xs"><spring:message code="auctionlist.th.auctionduration"/></th>
                    <th width="120px"><spring:message code="auctionlist.th.productgroup"/></th>
                    <th width="120px"><spring:message code="auctionlist.th.productname"/></th>
                    <th width="78px"><spring:message code="auctionlist.th.producttype"/></th>
                     <th width ="190px" class="min190"><spring:message code="auctionlist.th.auctionstatus"/></th>
                    <th class="disable-sorting" width="48px"><spring:message code="auctionlist.th.actionseller"/></th>
                  </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
              </div>
              <div class="clearfix"></div>
              </div>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
  </div>

<script type="text/javascript">
var queryString = "";
var btn_auction_sellers = '<spring:message code="btn.auction.sellers"/>';

var auctionStatus = Object();
auctionStatus["status_1"] = '<spring:message code="auction.status.1"/>';
auctionStatus["status_2"] = '<spring:message code="auction.status.2"/>';
auctionStatus["status_3"] = '<spring:message code="auction.status.3"/>';
auctionStatus["status_4"] = '<spring:message code="auction.status.4"/>';
auctionStatus["status_5"] = '<spring:message code="auction.status.5"/>';
auctionStatus["status_6"] = '<spring:message code="auction.status.6"/>';

function getAuctionStatus(key) {
  return auctionStatus[key];
}

function formatDate(strTime) {
  /*var date = new Date(longDate);
  var hours = (date.getUTCHours()+3);
  var minutes = date.getUTCMinutes();
  var seconds = date.getUTCSeconds()
  var ampm = hours >= 12 ? (getTimeStatus("pm")) : (getTimeStatus("am"));
  hours = hours % 12;
  hours = hours ? hours : 12; // the hour '0' should be '12'
  hours = hours < 10 ? ('0' + hours) : hours;
  minutes = minutes < 10 ? ('0' + minutes) : minutes;
  seconds = seconds < 10 ? ('0' + seconds) : seconds;
  var dmonth = ((date.getUTCMonth() + 1) < 10) ? ("0" + date.getUTCMonth() + 1) : (date.getUTCMonth() + 1);
  var currentDate = (date.getUTCDate() < 10) ? ("0" + date.getUTCDate()) : (date.getUTCDate());
  //var strTime = hours + ':' + minutes + ':' + seconds + ' ' + ampm;
  //return  dmonth + "/" + currentDate + "/" + date.getFullYear() + " " +strTime;
  var strTime = hours + ':' + minutes + ' ' + ampm;
  */

  strTime = (strTime.indexOf("AM") > -1) ? strTime.replace("AM",getTimeStatus("am")) : strTime.replace("PM",getTimeStatus("pm"));
//  strTime = (strTime.indexOf("Tue") > -1) ? strTime.replace("Tue","XXX") : strTime.replace("Tue","XXX");
  return strTime;
}
var auctionTable=null;
var auctionSellerOffer = null;
var auctionBuyerBid = null;
var auctionBuyerBidsAndViewSellerOffer=null;
var dailyAuctionId=null;
$(document).ready(function() {
  window.history.pushState("object or string", "EPAP|AuctionList", contextPath+"/auctions/auctionlist");
  languageList.sEmptyTable = '<spring:message code="auctionlist.emptytable"/>';
  auctionTable = $('#datatable').DataTable({
        language: languageList,
    columnDefs: [
  	  {orderable: false,targets: 'disable-sorting'},
	  {type: "datetime-moment",targets: 5}
    ],
    order: [[0, 'desc']],
    "ajax": {
      "url": (contextPath + "/auctions/dailyAuctionViewList"),
      "cache": false,
      "asyc":false,
      "dataSrc": ""
    },
    
    "columns": [
    	{
    	       "data": "beginDate",
    	       "render": function(data, type, json, meta) {
    	         return formatDate(json.beginDate);
    	       }
    	    },
    {
       "data": "beginTime",
       "render": function(data, type, json, meta) {
         return formatDate(json.beginTime);
       }
    },
    {
       "data": "endTime",
       "render": function(data, type, json, meta) {
         return formatDate(json.endTime);
       }
    },
    {"data": "auctionDuration"},
    {
    	"data": "productGroupName",
    	"render": function(data, type, json, meta) {
    		var productDescAndContanerSpace = '<p class=\'show-product-info\'>'
    						+'<label>${productDescriptionPhl}:&nbsp;</label>'
    						+ json.productDescription+'</p>'
    						+ '<p class=\'show-product-info\'>'
    						+ '<label>${productContainerSpecsPhl}:&nbsp;</label>'
    						+ json.containerSpecs+'</p>';
    		return ('<label class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="'+productDescAndContanerSpace+'">' + json.productGroupName + '</label>');
        }
    },
	{
    	"data": "productName",
    	"render": function(data, type, json, meta) {
    		var productDescAndContanerSpace = '<p class=\'show-product-info\'>'
    						+'<label>${productDescriptionPhl}:&nbsp;</label>'
    						+ json.productDescription+'</p>'
    						+ '<p class=\'show-product-info\'>'
    						+ '<label>${productContainerSpecsPhl}:&nbsp;</label>'
    						+ json.containerSpecs+'</p>';
    		return ('<label class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="'+productDescAndContanerSpace+'">' + json.productName + '</label>');
        }
    },
	{
    	"data": "productTypeName",
    	"render": function(data, type, json, meta) {
    		var productDescAndContanerSpace = '<p class=\'show-product-info\'>'
    						+'<label>${productDescriptionPhl}:&nbsp;</label>'
    						+ json.productDescription+'</p>'
    						+ '<p class=\'show-product-info\'>'
    						+ '<label>${productContainerSpecsPhl}:&nbsp;</label>'
    						+ json.containerSpecs+'</p>';
    		return ('<label class="text-hightlight text-underline" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="'+productDescAndContanerSpace+'">' + json.productTypeName + '</label>');
        }
    }, {
        "data": "auctionStatusCode",
        "render": function(data, type, json, meta) {
          return ('<label class="label auction status-' + (json.auctionStatusCode) + '">' + getAuctionStatus(("status_" + json.auctionStatusCode)) + '</label>');
        }
      },
	 {
      "data": "dailyAuctionsId",
      "render": function(data, type, json, meta) {
        return "<a href=\"" + contextPath + "/auctions/offers/" + json.dailyAuctionsId + "\" class=\"btn btn-info btn-lg pageContentLoading\" data-toggle=\"tooltip\" data-original-title=\"" + btn_auction_sellers + "\"><i class=\"fa fa-arrow-down\"></i></a>";
      }
    }],
    "rowCallback": function(row, data, index) {
      if(data.auctionSellers){
    	$(row).addClass("own-offer");  
      }else if(data.auctionBuyers){
    	$(row).addClass("own-bid");  
      }
      $('td', row).eq(0).addClass('text-left');
      $('td', row).eq(1).addClass('text-center');
      $('td', row).eq(2).addClass('text-center hidden-xs');
      $('td', row).eq(3).addClass('text-center');
      $('td', row).eq(4).addClass('text-center');
      $('td', row).eq(5).addClass('text-center');
      $('td', row).eq(6).addClass('text-bold text-uppercase text-center');
      $('td', row).eq(7).addClass('text-center');
      $('td', row).eq(8).addClass('text-center');
    }
  });
  //Websocket programing start

 /*  var socket = new SockJS((contextPath + "/auctionsdata"));
  socket.onclose = function() {
    alert("disconnected");
  }
  var client = Stomp.over(socket);
  client.debug = null
  client.connect({}, function(frame) {
    client.subscribe(("/wssauctions/refreshUI"), function(message) {    
    	auctionTable.ajax.reload();
    });
  });
   */
 
});
</script>