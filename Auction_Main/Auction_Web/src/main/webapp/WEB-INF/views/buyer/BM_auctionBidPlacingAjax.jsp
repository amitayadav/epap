<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.auction.commons.enums.ENUM_ProductCatalogStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionStatusCodes"%>
     
      <c:if test="${not empty errorStack}">
		  <div class="error-stack">
		    <c:forEach items="${errorStack}" var="error">
		      <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
		    </c:forEach>
		  </div>
	 </c:if>      
<form:form action="${contextPath}/bba/bid/savebuyerbids" method="post" modelAttribute="auctionBuyersBean" id="auctionBuyersFrom" enctype="multipart/form-data">
   
   <form:hidden path="auctionBuyersId" id="auctionBuyersId"/>
   <form:hidden path="auctionSellersBean.auctionSellersId" id="auctionSellersId"/>
   <form:hidden path="auctionSellersBean.offerQuantity" id="offerQuantity"/>
   <form:hidden path="auctionSellersBean.availableQuantity" id="availableQuantity"/>
   <form:hidden path="accountProfileBean.accountId" id="accountId"/>
   <form:hidden path="dailyAuctionsBean.dailyAuctionsId" id="dailyAuctionsId"/>
   <form:hidden path="dailyAuctionsBean.productCatalogBean.productId" id="productId"/>
   <form:hidden path="dailyAuctionsBean.auctionSettingsBean.auctionSettingsId" id="auctionSettingsId"/>
   <form:hidden path="accountProfileBean.bankDetailsBean.accountId" id="bankAccountId"/>
   <form:hidden path="royaltyValue" id="royaltyValue"/>
   <form:hidden path="govTax" id="govTax"/>
   <form:hidden path="buyerShippingCharge" id="buyerShippingCharge"/>
   
   <div class="x_panel">
     <div class="x_title font-weight-bol">
       <b><spring:message code="buyer.auctionbidplacing.lbl.deliverylocationandofferdetails"/></b>
     </div>
     <div class="form-group">
       <label><span class="required"> * </span><spring:message code="buyer.auctionbidplacing.lbl.deliverylocation"/></label>
       <form:select class="form-control" path="accountLocationsBean.locationId" id="accountLocationsBean">
         <form:option value="">
           <spring:message code="buyer.auctionbidplacing.lbl.selectdeliverylocation"/>
         </form:option>
         <c:forEach items="${buyerLocationList}" var="location">
           <form:option value="${location.locationId}">${location.locationName}</form:option>
         </c:forEach>
       </form:select>
     </div>
     <div class="form-group">
       <spring:message code="buyer.auctionbidplacing.lbl.bidquantity" var="bidquantityPhl"/>
       <label><span class="required"> * </span>${bidquantityPhl}</label>
       <c:choose>
       	<c:when test="${auctionBuyersBean.auctionSellersBean.partialAllowed}">
       		<form:input class="form-control" path="bidQuantity" id="bidQuantity" placeholder="${bidquantityPhl}"/>
       	</c:when>
       	<c:otherwise>
       		<form:hidden path="bidQuantity" id="bidQuantity" value="${auctionBuyersBean.auctionSellersBean.offerQuantity}"/>
       		<p class="read-only">${auctionBuyersBean.auctionSellersBean.offerQuantity}</p>
       	</c:otherwise>
       </c:choose>
     </div>
     <div class="form-group">
       <spring:message code="buyer.auctionbidplacing.lbl.bidprice" var="bidpricePhl"/>
       <label><span class="required"> * </span>${bidpricePhl}</label>
       <form:input class="form-control" path="bidPrice" id="auctionBuyersBeanPrice" placeholder="${bidpricePhl}"/>
     </div>
   </div>
   <div class="form-group text-right">
     <spring:message code="btn.update" var="updateBtn"/>
     <spring:message code="btn.save" var="saveBtn"/>
     <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty auctionBuyersBean.auctionBuyersId ? updateBtn : saveBtn}"/>    
     <button type="button" class="btn btn-default" data-dismiss="modal">
       <spring:message code="btn.close"/>
     </button>
   </div>
</form:form>
          
<div class="modal fade" id="offerDetailsModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-xlg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title text-capitalize">          
        </h4>
      </div>
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">
          <i class="fa fa-times"></i>&nbsp;&nbsp;
          <spring:message code="btn.close"/>
        </button>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  $(document).ready(function() {
		  
		  var auctionBuyerId = "${auctionBuyersBean.auctionBuyersId}";
		  auctionBuyerId = ("" != auctionBuyerId ? parseInt(auctionBuyerId) : 0);
		  var auctionStatusOpen = ${ENUM_AuctionStatusCodes.OPEN.getStatus()};
		  var auctionStatusRunning = ${ENUM_AuctionStatusCodes.RUNNING.getStatus()};
		  var dailyAuctionStatus = ${auctionBuyersBean.dailyAuctionsBean.auctionStatusCodesBean.auctionStatusCode};
		  
		  var minPrice=0;
		  var maxPrice=9999.99;
		  var minQty = 0;
		  var maxQty = ${auctionBuyersBean.auctionSellersBean.offerQuantity};
		  
		  if(auctionBuyerId > 0){
			  if(dailyAuctionStatus == auctionStatusOpen){
				  minPrice=0.00;
				  maxPrice=9999.99;
				  
			  }else if(dailyAuctionStatus == auctionStatusRunning){
				  minPrice="${auctionBuyersBean.bidPrice}";
				  maxPrice=("" != maxPrice ? parseFloat(maxPrice) : 9999.99);
				  minQty="${auctionBuyersBean.bidQuantity}";
				  maxQty=("" != maxQty ? parseInt(maxQty) : 30000);
			  }
		  }
		  /* $("#auctionBuyersBeanPrice").blur(function(){
			  var availBal = ${auctionBuyersBean.accountProfileBean.bankDetailsBean.availableBalance};
			  var govTax = ${auctionBuyersBean.dailyAuctionsBean.auctionSettingsBean.govTax};	
			  var royaltyVal = ${auctionBuyersBean.dailyAuctionsBean.accountProfileByCreatedbyIdBean.royaltyCodesBean.royaltyValue};
			  var bidPrice = $("#auctionBuyersBeanPrice").val();
			  var bidQty = $("#bidQuantity").val();
			  
			  bidPrice = bidPrice * bidQty;
			  bidPrice = bidPrice + (bidPrice * govTax / 100);
			  bidPrice = bidPrice + (bidPrice * royaltyVal/100);

			  $("#TotalPrice").val(bidPrice);
			  
			  if(bidPrice > availBal){
				 $("#TotalPrice").rules("add", {
			  	    "max"		  : availBal,
			  	     messages        : 
			  	      {
			  	    	"max"    : '<spring:message code="buyer.auctionbidplacing.lbl.totalprice.validation.max"/>',
			  	      }
			  	  });
			  }
		  }); */
		  $("#auctionBuyersFrom").validate({
	          debug: true,
	          rules: {
	        	  "accountLocationsBean.locationId":{
	        		  "required":true,
	        	  },
	        	  "bidQuantity":{
	        		  "required":true,
	        		  "number": true,
	        		  
	        		  "range": [minQty ,maxQty]
	        	  },
	        	  "bidPrice":{
	        		  "required":true,
	        		  "number": true,
	        		  
	        		  "range":[minPrice ,maxPrice]
	        	  },
	          },
	          messages: {
	        	  "accountLocationsBean.locationId":{
	        		  "required":'<spring:message code="buyer.auctionbidplacing.lbl.validation.location.required"/>',
	        	  },
	        	  "bidQuantity":{
	        		  "required":'<spring:message code="buyer.auctionbidpalcing.lbl.validation.bidquantity.require"/>',
	        		  "number":'<spring:message code="buyer.auctionbidplacing.lbl.valication.bidquantity.number"/>',
	        		  "range":'<spring:message code="buyer.auctionbidplacing.lbl.validation.bidquantity.range"/> '+minQty+' <spring:message code="buyer.auctionbidplacing.lbl.between"/> '+maxQty,
	        	  },
	        	  "bidPrice":{
	        		  "required":'<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.require"/>',
	        		  "number":'<spring:message code="buyer.auctionbidpalcing.lbl.validation.bidprice.number"/>',
	        		  "range":'<spring:message code="buyer.auctionbidplacing.lbl.validation.bidprice.range"/> '+minPrice +"&"+ maxPrice,
	        	  },
	          },
	          invalidHandler: function(event, validator) {
	  
	          },
	          errorPlacement: function(error, element) {
	              error.insertAfter(element.parent(".form-group"));
	          },
	       /*    submitHandler: function(form) {
	        	  form.submit();
	              $(form).find("input[type=submit]").attr("disabled",true);
	          }, */
	          highlight: function(element) {
	              $(element).addClass("error-element");
	          },
	          unhighlight: function(element) {
	              $(element).removeClass("error-element");
	          }
	          
	      }); 
		  $("#viewLocation").on("click", function(e){
		     	e.preventDefault();
		     	var latitude=$(this).data("latitude");
		  		var longitude=$(this).data("longitude");
		  		var locationName = $(this).data("original-title");
		  		$.ajax({
		  	       type: "POST",
		  	       async: false,
		  	     cache: false,
		  	       data:{latitude : latitude, longitude : longitude},
		  	       url: (contextPath + "/auctions/offerLocation/"),
		  	       success: function(result) {
		  	    	  $("#offerDetailsModal .modal-title").text(locationName);
		  	           $("#offerDetailsModal .modal-body").empty().html(result);
		  	           $("#offerDetailsModal").modal({
		  	               show: true,
		  	               backdrop: 'static',
		  	               keyboard: false
		  	           });
		  	       },
		  	       error: function(e) {
		  	           $("#offerDetailsModal .modal-body").empty().html(e);
		  	           $("#offerDetailsModal").modal({
		  	               show: true,
		  	               backdrop: 'static',
		  	               keyboard: false
		  	          });
		   	       }
		   	   });
		  });
  });
  });
</script>