<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="product.description" var="productDescriptionPhl"/>
<spring:message code="product.container.specs" var="productContainerSpecsPhl"/>

<style>
    table#datatable tr th{vertical-align: top;}
	table#datatable tr td{vertical-align: middle;}
	table#datatable tbody tr td:nth-child(4), 
	table#datatable tbody tr td:nth-child(5),
	table#datatable tbody tr td:nth-child(6){text-align:left !important ;color: #8e0000 !important;font-weight: bolder;}
</style>
<div class="visitor-wrapper">
  <div class="">
	  <div class="row">
	    <div class="col-md-12 col-sm-12 col-xs-12">
	      <div class="x_content">
	        <div class="x-title">
	          <h2><spring:message code="header.menu.dayauctions"/></h2>
	          <div class="clearfix"></div>
	        </div>
	        <div class="x_content">
	          <div class="x_panel">
	          	<div class="table-responsive ">
	              <table id="datatable" class="table table-striped table-bordered">
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
	                    <th width="48px"><spring:message code="auctionlist.th.begintime"/></th>
	                    <th width="48px"><spring:message code="auctionlist.th.endtime"/></th>
	                    <th width="55px" class="hidden-xs"><spring:message code="auctionlist.th.auctionduration"/></th>
	                    <th><spring:message code="auctionlist.th.productgroup"/></th>
	                    <th><spring:message code="auctionlist.th.productname"/></th>
	                    <th><spring:message code="auctionlist.th.producttype"/></th>
	                    <th width="65px"><spring:message code="auctionlist.th.auctionstatus"/></th>
	                    <th class="disable-sorting" width="50px"><spring:message code="auctionlist.th.actionseller"/></th>
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
	     <div class="clearfix"></div>
	  </div>
	  <div class="clearfix"></div>
	</div>
</div>
<script type="text/javascript">
var btn_auction_sellers = '<spring:message code="btn.auction.sellers"/>';

var timeStatus = Object();
timeStatus["am"] = '<spring:message code="am"/>';
timeStatus["pm"] = '<spring:message code="pm"/>';

function getTimeStatus(key) {
  return timeStatus[key];
}

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
  return strTime;
}

$(document).ready(function() {
  var auctionTable;
  languageList.sEmptyTable = '<spring:message code="auctionlist.emptytable"/>';
  auctionTable = $('#datatable').DataTable({
    language: languageList,
    columnDefs: [
  	  {orderable: false,targets: 'disable-sorting'},
	  {type: "datetime-moment",targets: 5}
    ],
    order: [[0, 'asc']],
    "ajax": {
      "url": (contextPath + "/home/dailyAuctionViewList"),
      "cache": false,
      "asyc":false,
      "dataSrc": ""
    },
    "columns": [
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
    }, 
	{
      "data": "auctionStatusCode",
      "render": function(data, type, json, meta) {
        return ('<label class="label auction status-' + (json.auctionStatusCode) + '">' + getAuctionStatus(("status_" + json.auctionStatusCode)) + '</label>');
      }
    }, {
      "data": "dailyAuctionsId",
      "render": function(data, type, json, meta) {
        return "<a href=\"" + contextPath + "/auctions/offers/" + json.dailyAuctionsId + "\" class=\"btn btn-warning btn-xs\" data-toggle=\"tooltip\" data-original-title=\"" + btn_auction_sellers + "\"><i class=\"fa fa-arrow-right\"></i></a>";
      }
    }],
    "rowCallback": function(row, data, index) {
      if(data.auctionSellers){
    	$(row).addClass("own-offer");  
      }else if(data.auctionBuyers){
    	$(row).addClass("own-bid");  
      }
      $('td', row).eq(0).addClass('text-center');
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
  
  //Websocket programing
  var socket = new SockJS((contextPath + "/auctionsdata"));
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
});
</script>