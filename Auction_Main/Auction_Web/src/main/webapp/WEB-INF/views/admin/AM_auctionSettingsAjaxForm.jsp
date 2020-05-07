<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form action="${contextPath}/super/saveauctionsettings" method="post" class="form-horizontal" modelAttribute="auctionSettingsBean" id="auctionSettingsFrom" enctype="multipart/form-data">
   <p class="text-danger"><spring:message code="required.msg"/> </p>
  <div class="form-group">
    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message  code="auctionsettingsajaxform.lbl.currency"/></label>
    <div class="col-sm-10 col-md-10 nopad">
    <form:hidden path="auctionSettingsId" id="auctionSettingsId"/>
    <form:select path="currencyCodesBean.currencyCode" class="form-control">
    	<form:option value="">
    	<spring:message code="auctionsettingsajaxform.select.selectcurrency"/>
    	</form:option>
		<form:options items="${currencyCodesBeanList}" itemLabel="currencyName" itemValue="currencyCode"/>    	
    </form:select>
  </div>
  <div class="clearfix"></div>
</div>
  <div class="form-group">
  <spring:message code="auctionsettingsajaxform.lbl.buyers.vat" var="vatPhl"/>
    <label class="col-sm-2 col-md-2 control-label nolpad">
    <span class="required"> * </span>${vatPhl}
    </label>
    <div class="col-sm-10 col-md-10 nopad">
    <form:input class="form-control" path="vatBuyers" id="vatBuyers" maxlength="5" placeholder="${vatPhl}"/>
  </div>
  <div class="clearfix"></div>
  </div>
  <div class="form-group">
  <spring:message code="auctionsettingsajaxform.lbl.sellers.vat" var="vatPh2"/>
    <label class="col-sm-2 col-md-2 control-label nolpad">
    <span class="required"> * </span>${vatPh2}
    </label>
    <div class="col-sm-10 col-md-10 nopad">
    <form:input class="form-control" path="vatSellers"  id="vatSellers" maxlength="5" placeholder="${vatPhl}"/>
  </div>
  <div class="clearfix"></div>
  </div>
  <div class="form-group text-right">
  	 <spring:message code="btn.save" var="saveBtn"/>
  	  <spring:message code="btn.update" var="updateBtn"/>
    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty auctionSettingsBean.auctionSettingsId ? updateBtn : saveBtn}"/>    
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>
  </div>
</form:form>
<script type="text/javascript">
  $(document).ready(function() {
  	 $("#auctionSettingsFrom").validate({
   		debug: true,
   		rules: {
   			"vatBuyers" : {"required": true, number: true, maxlength: 5, range: [0.00 , 99]},
   			"vatSellers" : {"required":true, number: true, maxlength: 5, range: [0.00 , 99]},
  			"currencyCodesBean.currencyCode" : {"required": true}
   		},
   		messages:{
   			"vatBuyers" : 
   				{
   					"required": "<spring:message code='auctionsettingsajaxform.validation.vat.buyers.required'/>",
   					"number": "<spring:message code='auctionsettingsajaxform.validation.vat.buyers.number'/>",
   					"maxlength": "<spring:message code='auctionsettingsajaxform.validation.vat.buyers.maxlength'/>",
   					"range": "<spring:message code='auctionsettingsajaxform.validation.vat.buyers.range'/>"
   				},
   				"vatSellers" : 
   				{
   					"required": "<spring:message code='auctionsettingsajaxform.validation.vat.sellers.required'/>",
   					"number": "<spring:message code='auctionsettingsajaxform.validation.vat.sellers.number'/>",
   					"maxlength": "<spring:message code='auctionsettingsajaxform.validation.vat.sellers.maxlength'/>",
   					"range": "<spring:message code='auctionsettingsajaxform.validation.vat.sellers.range'/>"
   				},
   			"currencyCodesBean.currencyCode" : 
				{
					"required": "<spring:message code='auctionsettingsajaxform.validation.currencycode.required'/>"
				}
   		},
   		invalidHandler: function(event, validator) {
   			
   		},
   		/* errorPlacement: function (error, element) {
   			error.insertAfter(element.parent(".form-group"));   
   		}, */ 
   	/* 	submitHandler: function(form) {
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