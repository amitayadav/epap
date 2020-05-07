<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form action="${contextPath}/sproptrel/saveaddannouncement" method="post" class="form-horizontal"  modelAttribute="announcementBean"  id="announcementForm">
  <p class="text-danger"><spring:message code="required.msg"/></p>
  <div class="form-group"><span class="required"> * </span>
  <spring:message code="announcement.h3.announcement" var="announcement"/>
   ${announcement}
    <form:hidden path="announcementId" id="announcementId"/>
     <form:textarea class="form-control" path="announcement" id="announcement" maxlength="1022"  placeholder="${announcement}"></form:textarea>
  </div>
  
  <div class="form-group text-right">
  <spring:message code="btn.update" var="updateBtn"/>
   <spring:message code="btn.save" var="saveBtn"/>  
    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty announcementBean.announcementId ? updateBtn : saveBtn}"/>    
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>

  </div> 
</form:form>
 <script type="text/javascript">
 $.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            var re = new RegExp(regexp);
	            return this.optional(element) || re.test(value);
	        },
	        "Please check your input."
	);
  $(document).ready(function() {
  	 $("#announcementForm").validate({
   		debug: true,
   	
   		rules: {
   			"announcement" : {
   				"required": true,
   				/* regex:"^[^\s].+[^\s]$" */
  	 		}
   		},
   		 messages:{
   			"announcement" : 
   				{
   					"required": "<spring:message code='announcement.validation.announcement.required'/>",
   					/* "regex": "<spring:message code='announcement.regex.announcement.required'/>", */
   				}
   		}, 
   		invalidHandler: function(event, validator) {
   			
   		},
   		
   		highlight : function(element) {
   			$(element).addClass("error-element");
   		},
   		unhighlight : function(element) {
   			$(element).removeClass("error-element");
   		}
   	});
  });
  
 
 
</script> 