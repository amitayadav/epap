<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import = "javax.servlet.*,java.text.*" %>
<%@ page import = "com.auction.commons.enums.ENUM_AuctionStatusCodes" %>
<c:set var="IN_RUNNING" value="<%=ENUM_AuctionStatusCodes.RUNNING %>"></c:set>
<jsp:useBean id="now" class="java.util.Date" />
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>Auction Management</h3>
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
                        <div class="x_title">
                            <h2>List of Auction</h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content table-responsive">
                            <table class="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>Begin Time</th>
                                        <th>End Time</th>
                                         <th>Product Group</th>
                                          <th>Product Name</th>
                                         <th>Product Type</th>
                                        <th>Auction Duration</th>
                                        <th>Auction Status</th>
                                        <th>Minimum Quantity</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${auctionList}" var="auction" varStatus="count">
	                                        <tr>
	                                            <td align="center"><fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${auction.beginTime }"/>  </td>
	                                             <td align="center"><fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${auction.endTime }"/> </td>
	                                            <td align="center">${auction.productCatalogBean.productGroupName}</td>
	                                            <td align="center">${auction.productCatalogBean.productName}</td>
	                                            <td align="center">${auction.productCatalogBean.productTypeName}</td>
	                                            <td align="center">${auction.auctionDuration }</td>
	                                            <td class="text-bold text-uppercase" align="center">${auction.auctionStatusCodesBean.auctionStatusCodeMeaning}</td>
	                                            <td align="center">${auction.minimumQuantity}</td>
	                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div id="sseDiv"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  
	  if (typeof (EventSource) !== "undefined") {
			var source = new EventSource("/auction/buyer/sseTest");
			source.onmessage = function(event) {
				document.getElementById("sseDiv").innerHTML += event.data
						+ " - ";
			};
	} else {
		document.getElementById("sseDiv").innerHTML = "Your browser does not support server-sent events.";
	}
	  
      $('#datatable').DataTable({
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
  });
</script>