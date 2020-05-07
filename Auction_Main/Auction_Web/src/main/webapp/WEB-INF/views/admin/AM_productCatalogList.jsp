<%@page import="com.auction.commons.enums.ENUM_ProductCatalogStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="productcataloglist.h3.productcataloglist"/></li>
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
        <h3><spring:message code="productcataloglist.h3.productcataloglist"/></h3>
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
              <%-- <h2><spring:message code="productcataloglist.h2.productcatalogdetaillist"/></h2> --%>
              <button class="btn btn-info btn-sm pull-right" id="productCatalogBtn" data-id="0"><i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="btn.add"/></button>
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
            <spring:message code="btn.edit" var="editBtn"/>
            <spring:message code="btn.view" var="viewBtn"/>
              <table id="datatable" class="table table-striped table-bordered table_dayauction">
                <thead>
                  <tr>
                    <th width="20px">#</th>
                     <th><spring:message code="productcataloglist.th.groupname"/></th>
                    <th><spring:message code="productcataloglist.th.productname"/></th>
                    <th><spring:message code="productcataloglist.th.typename"/></th>
                    <th width="60px"><spring:message code="productcataloglist.th.status"/></th>
                    <th width="40px" class="disable-sorting">${viewBtn}</th>
                    <th width="40px" class="disable-sorting">${editBtn}</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${productCatalogBeanList}" var="productCatalog" varStatus="status">
                    <tr>
                      <td>${status.count}</td>
                       <td>${productCatalog.productGroupName}</td>
                      <td>${productCatalog.productName}</td>
                      <td>${productCatalog.productTypeName}</td>
                      <td class="text-center text-uppercase text-bold">
                      	<c:if test="${productCatalog.status eq 1}"><label class="label label-success"><spring:message code="productcataloglist.th.status.approved"/></label></c:if>
                      	<c:if test="${productCatalog.status eq 2}"><label class="label label-warning"><spring:message code="productcataloglist.th.status.pending"/></label></c:if>
                      	<c:if test="${productCatalog.status eq 3}"><label class="label label-danger"><spring:message code="productcataloglist.th.status.suspended"/></label></c:if>                      	
                      </td>
                      <td class="text-center">
                        <button data-id="${productCatalog.productId}" class="btn btn-info btn-sm productCatalogViewBtn"  data-toggle="tooltip" data-original-title="${viewBtn}">
                        <i class="fa fa-eye"></i>
                        </button>
                      </td>                      
                      <td class="text-center">
                        <button data-id="${productCatalog.productId}" class="btn btn-warning btn-sm productCatalogBtn"  data-toggle="tooltip" data-original-title="${editBtn}">
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
<div class="modal fade" id="productCatalogViewModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title"><spring:message code="productcataloglist.model.detail"/></h4>
      </div>
      <div class="modal-body"></div>
    </div>
  </div>
</div>
<div class="modal fade" id="productCatalogModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body"></div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|ProductCatalogList", contextPath+ "/spropt/productCatalogList");
	  languageList.sEmptyTable = '<spring:message code="productcataloglist.emptytable"/>';
      $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
      
      function productCatalogForm(id){
      	if(id > 0){
      		$("#productCatalogModel .modal-title").html("<spring:message code='productcataloglist.model.update'/>");
      	}else{
      		$("#productCatalogModel .modal-title").html("<spring:message code='productcataloglist.model.add'/>");
      	}
      	
      	$.ajax({
   	        type: "GET",
   	        async: false,
   	     cache: false,
   	        url: (contextPath+"/spropt/productcatalog/"+id),
   	        success: function(result) {
   	        	$("#productCatalogModel .modal-body").empty().html(result);
   	        	$("#productCatalogModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
   	        },error: function(e){
   	        	$("#productCatalogModel .modal-body").empty().html(e);
   	        	$("#productCatalogModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
   	        }
   	     }); 
      }
      
      $("#productCatalogBtn").click(function(e){
      	e.preventDefault();
      	productCatalogForm($(this).data("id"));
      });
      
      $("#datatable").on("click","tr .productCatalogViewBtn",function(e){
      	e.preventDefault();
      	var id = $(this).data("id");
      	$.ajax({
    	    type: "GET",
    	    data: {"VIEWONLY":true},
    	    async: false,
    	    cache: false,
    	    url: (contextPath+"/spropt/productcatalog/"+id),
    	    success: function(result) {
    	    	$("#productCatalogViewModel .modal-body").empty().html(result);
    	     	$("#productCatalogViewModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
    	    },error: function(e){
				$("#productCatalogViewModel .modal-body").empty().html(e);
    	        $("#productCatalogViewModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
    	    }    
    	 }); 
      });
      $("#datatable").on("click","tr .productCatalogBtn",function(e){
       	e.preventDefault();
       	productCatalogForm($(this).data("id"));
      });
  });
</script>
