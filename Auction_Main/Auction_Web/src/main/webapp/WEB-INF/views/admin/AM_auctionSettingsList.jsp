<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="auctionsettingslist.h3.applicationsettingslist"/></li>
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
        <h3><spring:message code="auctionsettingslist.h3.applicationsettingslist"/> </h3>
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
          <div class="x_panel">
            <div class="x-title">
              <%-- <h2><spring:message code="auctionsettingslist.h2.applicationsettingdetailslist"/></h2> --%>
              <button class="btn btn-info btn-sm pull-right" id="auctionSettingBtn" data-id="0"><i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="btn.add"/></button>
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
            <spring:message code="btn.edit" var="editPhl"/>
              <table id="datatable" class="table table-striped table-bordered table_dayauction">
                <thead>
                  <tr>
                    <th width="20px">#</th>
                    <th><spring:message code="auctionsettingslist.th.currency"/></th>
                    <th><spring:message code="auctionsettingslist.th.vatbuyers"/></th>
                      <th><spring:message code="auctionsettingslist.th.vatsellers"/></th>
                    <th width="40" class="disable-sorting">${editPhl}</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${auctionSettingsBeanList}" var="auctionSettings" varStatus="status">
                    <tr>
                      <td>${status.count}</td>
                      <td>${auctionSettings.currencyCodesBean.currencyName}(${auctionSettings.currencyCodesBean.currencyCode})</td>
                      <td class="text-center">${auctionSettings.vatBuyers}</td>
                      <td class="text-center">${auctionSettings.vatSellers}</td>
                      <td class="text-center">
                        <button data-id="${auctionSettings.auctionSettingsId}" class="btn btn-warning btn-sm auctionSettingBtn"  data-toggle="tooltip" data-original-title="${editPhl}">
                        <i class="fa fa-edit"></i>
                        </button>
                      </td>
                    </tr>
                  </c:forEach>
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
<div class="modal fade" id="auctionSettingModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|AuctionSettingsList", contextPath+ "/super/auctionSettingsList");
	  languageList.sEmptyTable = '<spring:message code="auctionsettingslist.emptytable"/>';
      $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
      
      function royaltyCodeForm(id){
      	if(id > 0){
      		$("#auctionSettingModel .modal-title").html("<spring:message code='auctionsettingslist.model1.header'/>");
      	}else{
      		$("#auctionSettingModel .modal-title").html("<spring:message code='auctionsettingslist.model2.header'/>");
      	}
      	
      	$.ajax({
    	        type: "GET",
    	        async: false,
    	        cache: false,
    	        url: (contextPath+"/super/auctionsettings/"+id),
    	        success: function(result) {
    	        	$("#auctionSettingModel .modal-body").empty().html(result);
    	        	$("#auctionSettingModel").modal({
    	        		show:true,
    	        		backdrop: 'static',
    	          	    keyboard: false
    	              });
    	        },error: function(e){
    	        	$("#auctionSettingModel .modal-body").empty().html(e);
    	        	$("#auctionSettingModel").modal({
    	        		show:true,
    	        		backdrop: 'static',
    	          	    keyboard: false
    	              });
    	        }
    	    }); 
      }
      $("#auctionSettingBtn").click(function(e){
      	e.preventDefault();
      	royaltyCodeForm($(this).data("id"));
      });
      $("#datatable").on("click","tr .auctionSettingBtn",function(e){
      	e.preventDefault();
      	royaltyCodeForm($(this).data("id"));
      });
  });
</script>
