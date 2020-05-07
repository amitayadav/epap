<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
    table#datatable tr th{vertical-align: top;}
	table#datatable tr td{vertical-align: middle;}
	table#datatable tr th:nth-child(1),
	table#datatable tr th:nth-child(2),
	table#datatable tr th:nth-child(3){text-align:left  !important;}
	table#datatable tr th:nth-child(4),
	table#datatable tr th:nth-child(5){text-align:center !important;}
	table#datatable tbody tr td:nth-child(3){color: #8e0000 !important;font-weight: bolder;} 
	table#datatable tbody tr td:nth-child(4),
	table#datatable tbody tr td:nth-child(5){text-align:center !important;}
	
</style>
<div class="visitor-wrapper">
  <div class="">
	  <div class="row">
	    <div class="col-md-12 col-sm-12 col-xs-12">
	      <div class="x_content">
	        <div class="x-title">
	          <h2> <spring:message code="header.menu.auctiontrades"/></h2>
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
	                    <th width="50px"><spring:message code="auctiontrades.date"/></th>
	                    <th width="50px"><spring:message code="auctiontrades.time"/></th>
	                    <th width="100px" class="hidden-xs"><spring:message code="auctiontrades.product"/></th>
	                    <th width="50px"><spring:message code="auctiontrades.quantity"/></th>
	                    <th width="50px"><spring:message code="auctiontrades.price"/></th>	                   
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
	  </div>
	  <div class="clearfix"></div>
	</div>
</div>
<script type="text/javascript">

$(document).ready(function() {
  var auctionTradeTable;
  languageList.sEmptyTable = '<spring:message code="auctionlist.emptytable"/>';
  auctionTradeTable = $('#datatable').DataTable({
    language: languageList,
    columnDefs: [{orderable: false, targets: 'disable-sorting'}],
    "lengthMenu": [[25, 50, 75, 100], [25, 50, 75, 100]],
    order: [[0, 'desc'],[1, 'desc']],
    "ajax": {
      "url": (contextPath + "/home/auctiontradesviewlist"),
      "cache": false,
      "asyc":false,
      "dataSrc": ""
    },
    "columns": [
    	{"data": "tradeDate"},
    	{"data": "tradeTime"},
    	{"data": "product"},
    	{"data": "soldQuantity"},
    	{"data": "soldPrice",
    	  "render": function(data, type, json, meta) {
    	            return json.soldPrice.toFixed(2);
    	   }
    	},
    ]
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
    	auctionTradeTable.ajax.reload();
    });
  });
});
</script>