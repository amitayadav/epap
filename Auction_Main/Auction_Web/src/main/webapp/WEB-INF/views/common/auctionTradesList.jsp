<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
  table#ShippersTable tr th:nth-child(1),
  table#ShippersTable tr th:nth-child(2),
  table#ShippersTable tr th:nth-child(3),
  table#ShippersTable tr th:nth-child(4),
  table#ShippersTable tr th:nth-child(5){
  /*text-align: center;*/
  }
</style>
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li>
      <a href="${contextPath}/setting/dashboard">
        <spring:message code="menu.main.dashboard"/>
      </a>
    </li>
    <li>
      <spring:message code="menu.auction.auctiontrades"/>
    </li>
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
        <h3>
          <spring:message code="auctiontradeslist.h3.auctiontrades"/>
        </h3>
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
          <div class="x_panel">
            <%-- <div class="x_title">
              <h2><spring:message code="admin.financialstatement.heading"/></h2>
              <div class="clearfix"></div>
            </div> --%>
            <div class="x_panel">
            <form action="${contextPath}/auctions/auctiontrades" method="POST" id="userfinancialstatement" enctype="multipart/form-data">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
              <div class="form-group">
                <spring:message code="admin.financialstatement.lbl.startDate" var="startDatePhl"/>
                <spring:message code="admin.financialstatement.lbl.endDate" var="endDatePhl"/>
                <div class="col-md-6 nolpad">
                  <label class="form-label"><span class="required"> * </span>${startDatePhl}</label>
                  <input type="text" name="startDate" id="startDate" class="form-control-for-dates" placeholder="${startDatePhl}" value="${startDate}" readonly="readonly"/>
                </div>
                <div class="col-md-6 norpad">
                  <label class="form-label"><span class="required"> * </span>${endDatePhl}</label>
                  <input type="text" name="endDate" id="endDate" class="form-control-for-dates" placeholder="${endDatePhl}"  value="${endDate}" readonly="readonly"/>
                </div>
              </div>
              <div class="clearfix"></div>
              <br/>
              <div class="form-group text-right">
                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="submit"/>'/>
              </div>
            </form>
            <hr/>
            <div class="table-responsive">
                <!--fahad: removing id=ShipperTable fixed the Auction Trades table header !!!!-->
            <!--<table id="ShippersTable" class="table table-striped table-bordered">-->
            <table id="ShippersTable" class="table table-striped table-bordered table_dayauction">
              <thead>
                <tr>
                  <th width="100px"><spring:message code="auctiontrades.date"/></th>
                  <th width="50px"><spring:message code="auctiontrades.time"/></th>
                  <th width="100px"><spring:message code="auctiontrades.product"/></th>
                  <th width="50px"><spring:message code="auctiontrades.quantity"/></th>
                  <th width="50px"><spring:message code="auctiontrades.price"/></th>
                </tr>
              </thead>
              <tbody> 
                <c:forEach items="${auctionTradeList}" var="auctionTrade">
                  <tr>
                      <!--fahad: change date format-->
                    <!--<td class="text-left"><fmt:formatDate pattern='yyyy-MM-dd' value="${auctionTrade.id.logTimestamp}"/></td>-->
                    <td class="text-left"><fmt:formatDate pattern='EEEE dd MMMM yyyy' value="${auctionTrade.id.logTimestamp}"/></td>
                    <td class="text-left"><fmt:formatDate pattern='hh:mm:ss a' value="${auctionTrade.id.logTimestamp}"/></td>
                   
                    <td class="text-hightlight text-center">${auctionTrade.id.productCatalogBean.productGroupName}-${auctionTrade.id.productCatalogBean.productName}-${auctionTrade.id.productCatalogBean.productTypeName}</td>
                    <td class="text-hightlight text-center text-capitalize">
                    <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits = "0" value ='${auctionTrade.soldQuantity}'/></td>
                    <td class="text-center">
                     <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${auctionTrade.soldPrice}'/></td>
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
    </div>
  </div>
  <div class="clearfix"></div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|AuctionTradesList", contextPath+"/auctions/auctionTrades");
      $("#startDate").daterangepicker({
    	  locale: {
  			format: "YYYY-MM-DD"
  		  },
    	  singleDatePicker: true,
    	  startDate: new Date(new Date().setDate(1)).toISOString().substring(0, 10),
    	  maxDate : new Date().toISOString().substring(0, 10)
  	  });
  
  $('#startDate').on('apply.daterangepicker', function(ev, picker) {
    	 $("#endDate").daterangepicker({
        	 locale: {
          	format: "YYYY-MM-DD"
      	 },
        	 singleDatePicker: true,
        	 minDate : $("#startDate").val(),	 
    	 });
      });
  $("#endDate").daterangepicker({
   locale: {
  		format: "YYYY-MM-DD"
  	  },
   singleDatePicker: true,
   //dateLimit: true,
   minDate : $("#startDate").val(),		  
  });
  languageList.sEmptyTable = '<spring:message code="auctiontradeslist.emptytable"/>';
  $('#ShippersTable').DataTable({
      language: languageList,
      columnDefs: [{orderable: false, targets: 'disable-sorting'}],
      "lengthMenu": [[25, 50, 75, 100], [25, 50, 75, 100]],
      order: [[0, 'desc'],[1, 'desc']],
    });
    
  });
</script>