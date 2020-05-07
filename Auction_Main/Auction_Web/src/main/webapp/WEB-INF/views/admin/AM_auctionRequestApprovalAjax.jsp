<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form action="${contextPath}/spropt/updateAuctionRequest" method="post" modelAttribute="auctionRequestBean" id="auctionRequestForm" enctype="multipart/form-data">
	<p class="text-danger"><spring:message code="required.msg"/></p>
	 <div class="form-group">
	 			<spring:message code="auctionrequestaprovalajax.lbl.productname" var="productNameLbl"/>
                <label>${productNameLbl}</label>
                <p class="read-only">${auctionRequestBean.productCatalogBean.productGroupName} <b>-</b> ${auctionRequestBean.productCatalogBean.productName} <b>-</b> ${auctionRequestBean.productCatalogBean.productTypeName}</p>
                <form:hidden class="form-control" path="productCatalogBean.productName"  placeholder="${productNameLbl}" disabled="true" />
     </div>
	 <div class="form-group">
	 <spring:message code="auctionrequestaprovalajax.phl.sellercomment" var="sellercommentLbl"/>
         <label><spring:message code="auctionrequestaprovalajax.lbl.comment"/> </label>
         <p class="read-only-textarea">${auctionRequestBean.sellerComment}</p>         
         <form:hidden class="form-control" path="sellerComment"  placeholder="${sellercommentLbl}" disabled="true"/>
      </div>
	 
     <div class="form-group">
         <span class="required"> * </span><label><spring:message code="auctionrequestaprovalajax.select.selectstatus"/></label>
			<form:select path="status" class="form-control">
			 <form:option value=""><spring:message code="auctionrequestaprovalajax.select.selectstatus"/></form:option>
  			 <c:forEach items="${auctionStatusList}" var="status">
      		    <form:option value="${status.key}"><spring:message code="auction.request.status.${status.key}"/></form:option>
              </c:forEach>
           </form:select>
     </div>
	 <div class="form-group">
	 <spring:message code="auctionrequestaprovalajax.lbl.feedback" var="feedbackPhl"/>
         <label>${feedbackPhl}</label>
         <form:textarea class="form-control" path="feedback"  placeholder="${feedbackPhl}" maxlength="500"  />
      </div>
	<div class="form-group text-right">
		 <spring:message code="btn.update" var="UpdateBtn"/>
		<input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${UpdateBtn}" />
		<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>
	</div>
	<form:hidden path="requestId"/>
</form:form>
<script type="text/javascript">
	$(document).ready(function() {
		$("#auctionRequestForm").validate({
			debug : true,
			rules : {
				"status" : {"required": true},
				"feedback" : { maxlength: 255}
			},
			messages : {
				"status" : {
					"required" : "<spring:message code='auctionrequestaprovalajax.validation.status.required'/>"
				},
			   "feedback" : {
				   "maxlength": "<spring:message code='auctionrequestaprovalajax.validation.feedback.maxlength'/>",
			   }
			},
			invalidHandler : function(event, validator) {

			},
			/* errorPlacement: function (error, element) {
				error.insertAfter(element.parent(".form-group"));   
			}, */
			/* submitHandler : function(form) {
				form.submit();
                $(form).find("input[type=submit]").attr("disabled",true);
			}, */
			highlight : function(element) {
				$(element).addClass("error-element");
			},
			unhighlight : function(element) {
				$(element).removeClass("error-element");
			}
		});
	});
</script>