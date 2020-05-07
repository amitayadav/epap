<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form action="${contextPath}/admin/savenotificationEventDetails" method="post" class="form-horizontal" modelAttribute="eventsMeaningBean" id="notificationEventFrom" enctype="multipart/form-data">
  <p class="text-danger"><spring:message code="required.msg"/></p>
  <div class="form-group">
    <spring:message code="notificationeventajaxform.lbl.eventname" var="eventnamePhl"/>
    <label class="col-sm-3 col-md-3 control-label nolpad">
      <span class="required"> * </span>${eventnamePhl}
    </label>
    <div class="col-sm-9 col-md-9 nopad">
      <form:hidden path="eventId" id="eventId"/>
      <form:textarea class="form-control" path="eventMeaning" id="eventMeaning" maxlength="255"  placeholder="${eventnamePhl}"></form:textarea>
    </div>
    <div class="clearfix"></div>
  </div>
  <div class="form-group text-right">
  <spring:message code="btn.update" var="updateBtn"/>
  <spring:message code="btn.save" var="saveBtn"/>
    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty eventsMeaningBean.eventId ? updateBtn : saveBtn}"/>    
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>
  </div>
</form:form>
<script type="text/javascript">
  $(document).ready(function() {
  	 $("#notificationEventFrom").validate({
   		debug: true,
   		rules: {
   			"eventMeaning" : {"required": true, maxlength: 255}
   		},
   		messages:{
   			"eventMeaning" : {
   				"required": "<spring:message code='notificationeventajaxform.validation.eventMeaning.required'/>",
   				"maxlength": "<spring:message code='notificationeventajaxform.validation.eventMeaning.maxlength'/>"
   			}
   		},
   		invalidHandler: function(event, validator) {
   			
   		},
   		/* errorPlacement: function (error, element) {
   			error.insertAfter(element.parent(".form-group"));   
   		}, */ 
   		/* submitHandler: function(form) {
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