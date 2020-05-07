<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form action="${contextPath}/super/saveroyaltycodedetails" method="post" class="form-horizontal"  modelAttribute="royaltyCodesBean"  id="royaltyCodesFrom" enctype="multipart/form-data">
  <p class="text-danger"><spring:message code="required.msg"/></p>
  <div class="form-group">
  <spring:message code="royaltycodeajaxform.lbl.royaltyvalue" var="royaltyvaluebtn"/>
    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${royaltyvaluebtn}</label>
    <div class="col-sm-10 col-md-10 nopad">
    <form:hidden path="royaltyCode" id="royaltyCode"/>
    <form:input class="form-control" path="royaltyValue" id="royaltyValue" maxlength="5" placeholder="${royaltyvaluebtn}"/>
  </div>
  <div class="clearfix"></div>
  </div>
  <div class="form-group text-right">
  <spring:message code="btn.update" var="updateBtn"/>
   <spring:message code="btn.save" var="saveBtn"/>  
    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty royaltyCodesBean.royaltyCode ? updateBtn : saveBtn}"/>    
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>
  </div>
</form:form>
<script type="text/javascript">
  $(document).ready(function() {
  	 $("#royaltyCodesFrom").validate({
   		debug: true,
   		rules: {
   			"royaltyValue" : {
   				"required": true,
				"number": true,
				"maxlength": 5,
				"range": [0.01 , 99]
  	 		}
   		},
   		messages:{
   			"royaltyValue" : 
   				{
   					"required": "<spring:message code='royaltycodeajaxform.validation.royaltyValue.required'/>",
   					"number": "<spring:message code='royaltycodeajaxform.validation.royaltyValue.number'/>",
   					"maxlength": "<spring:message code='royaltycodeajaxform.validation.royaltyValue.maxlength'/>",
   					"range": "<spring:message code='royaltycodeajaxform.validation.royaltyValue.range'/>"
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