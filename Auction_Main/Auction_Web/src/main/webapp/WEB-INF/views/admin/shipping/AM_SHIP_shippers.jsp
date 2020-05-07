<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
	table#confirmDeliveryTable tbody tr td:nth-child(1){color: #8e0000 !important;font-weight: bolder;}
	table#confirmDeliveryTable tbody tr td:nth-child(2){color: #8e0000 !important;font-weight: bolder;}
	table#confirmDeliveryTable tbody tr td:nth-child(3){color: #8e0000 !important;font-weight: bolder;}
	table#confirmDeliveryTable tbody tr td:nth-child(4){color: #8e0000 !important;font-weight: bolder;}
	table#confirmDeliveryTable tbody tr td:nth-child(5){color: #8e0000 !important;font-weight: bolder;}
	table#confirmDeliveryTable tbody tr td:nth-child(6){color: #8e0000 !important;font-weight: bolder;}
	table#confirmDeliveryTable tbody tr td{text-align:center;}
	table#confirmDeliveryTable tbody tr th:nth-child(6){width:50px !important;}

</style>
<!-- page content -->
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="menu.auction.shippers"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul>
  <div class="x_content">
    <div class="page-title">
      <div class="title_left">
        <h3><spring:message code="shipper.shippers.heading.title"> </spring:message></h3>
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
    <div class="x_panel">
      <div class="x_panel">
        <div class="table-responsive">
          <table id="ShippersTable" class="table table-striped table-bordered table_dayauction">
            <thead>
              <tr>
                <th width="150px"><spring:message code="shipper.lbl.fName"/></th>
                <th width="50px"><spring:message code="shipper.lbl.stype"/></th>
                <th width="50px"><spring:message code="shipper.lbl.mobileNo"/></th>
                <th width="50px"><spring:message code="shipper.lbl.vehicle"/></th>
                <th width="50px"><spring:message code="shipper.lbl.truckModelYear"/></th>               
                <th width="50px"><spring:message code="shipper.lbl.mLocation"/></th>
                <th width="50px"><spring:message code="shipper.lbl.rating"/></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${shipperAndShipperAgentList}" var="logDetail">
                <c:if test="${not empty logDetail.accountProfileBean && not empty logDetail.accountProfileBean.accountId}">
                <tr>
                  <td id="profileInfo" data-shipperid="${logDetail.accountProfileBean.accountId}" class="text-hightlight text-bold text-underline-hyper">${logDetail.accountProfileBean.fullName} (${logDetail.accountProfileBean.publicName})</td>
                  <td class="text-bold text-uppercase text-center"><spring:message code="account.type.${logDetail.accountTypeCodesBean.accountType}"/></td>
                  <td class="text-hightlight text-right">${logDetail.accountProfileBean.phoneNumber1}</td>
                  <td class="text-hightlight text-center text-capitalize">${logDetail.accountProfileBean.shippersRegistrationInfoBean.truckType}</td>
                  <td class="text-hightlight text-center text-capitalize">${logDetail.accountProfileBean.shippersRegistrationInfoBean.truckModelYear}</td>
                  <td class="text-hightlight text-center text-capitalize">
                  	<c:forEach items="${logDetail.accountProfileBean.existingAccountLocationBean}" var="location">
                  	  <a data-latitude="${location.latitude}" data-longitude="${location.longitude}" class="viewLocation btn btn-lg" data-toggle="tooltip" data-original-title="${location.locationName}"><i class="fa fa-map-marker fa-2 text-center"></i></a>&nbsp;&nbsp;
                  	</c:forEach>
                  </td>
                  <td class="text-hightlight text-center">${logDetail.accountProfileBean.rating}</td>
               </c:if>
              </c:forEach>
            </tbody>
          </table>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
  </div>
</div>
<div class="clearfix"></div>
<div class="modal fade" id="viewDetails" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h1 class="modal-title"></h1>
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
<!-- /page content -->
<script type="text/javascript">
$(document).ready(function() {
	 window.history.pushState("object or string", "EPAP|ShipperList", contextPath+"/shipping/shipperList");
  languageList.sEmptyTable = '<spring:message code="buyer.confirmdelivery.emptytable"/>';
  $('#ShippersTable').DataTable({
    language: languageList,
    columnDefs: [{orderable: false, targets: 'disable-sorting'}]
  });

  $("#ShippersTable").on("click", "tr #profileInfo", function(e) {
    e.preventDefault();
    var shipperId = $(this).data("shipperid");
    $.ajax({
 	    type: "POST",
 	    async: false,
 	   cache: false,
 	    data: {
 	    	sellerId: shipperId
 	    },
 	    url: (contextPath + "/auctions/profileInfo"),
 	    success: function(result) {
 	      $("#viewDetails .modal-title").text('<spring:message code="common.profile.info"/>');
 	      $("#viewDetails .modal-body").empty().html(result);
 	      $("#viewDetails").modal({
 	        show: true,
 	        backdrop: 'static',
 	        keyboard: false
 	      });
 	    },
 	    error: function(e) {
 	      $("#viewDetails .modal-title").text('<spring:message code="common.profile.info"/>');
 	      $("#viewDetails .modal-body").empty().html(e);
 	      $("#viewDetails").modal({
 	        show: true,
 	        backdrop: 'static',
 	        keyboard: false
 	      });
 	    }
 	  });
  });
  
  $("#ShippersTable").on("click", "tr .viewLocation", function(e) {
    e.preventDefault();
    var longitude = $(this).data("longitude");
    var latitude = $(this).data("latitude");
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
	    success: function(result) {
	      $("#viewDetails .modal-title").text(locationName);
	      $("#viewDetails .modal-body").empty().html(result);
	      $("#viewDetails").modal({
	        show: true,
	        backdrop: 'static',
	        keyboard: false
	      });
	    },
	    error: function(e) {
	      $("#viewDetails .modal-title").text(locationName);
	      $("#viewDetails .modal-body").empty().html(e);
	      $("#viewDetails").modal({
	        show: true,
	        backdrop: 'static',
	        keyboard: false
	      });
	    }
	  });
  });
});
</script>