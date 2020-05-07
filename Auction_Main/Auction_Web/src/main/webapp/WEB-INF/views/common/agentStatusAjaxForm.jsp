<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page import="com.auction.commons.enums.ENUM_AccountStatusCodes"%>


<form:form action="${contextPath}/sbs/updateaccountstatus" method="post" class="form-horizontal"  modelAttribute="agentOwnerBean" id="agentStatusAjaxForm" enctype="multipart/form-data">
    <form:hidden path="agentOwnerId" />
    <form:hidden path="loginDetailsByAgentLoginUserid.loginUserid" value="${loginDetailsBean.loginUserid}" />
    <p class="text-danger"><spring:message code="required.msg"/></p>

	<div class="form-group">
		<label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="agentstatusajaxform.lbl.firstName" /></label>
		 <div class="col-sm-10 col-md-10 nopad">
		<div class="read-only">${loginDetailsBean.accountProfileBean.FName}</div>
	</div>
	<div class="clearfix"></div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="agentstatusajaxform.lbl.lastName" /></label>
		<div class="col-sm-10 col-md-10 nopad">
		<div class="read-only">${loginDetailsBean.accountProfileBean.LName}</div>
	</div>
	<div class="clearfix"></div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="agentstatusajaxform.lbl.companyName" /></label>
		<div class="col-sm-10 col-md-10 nopad">
		<div class="read-only">${loginDetailsBean.accountProfileBean.businessName}</div>
	</div>
	<div class="clearfix"></div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="agentstatusajaxform.lbl.nationalId" /></label>
		<div class="col-sm-10 col-md-10 nopad">
		<div class="read-only">${loginDetailsBean.accountProfileBean.governmentId}</div>
	</div>
	<div class="clearfix"></div>
	</div>
	<div class="form-group">
        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="agentstatusajaxform.lbl.accstatus"/></label>
        <div class="col-sm-10 col-md-10 nopad">
        <form:select path="loginDetailsByAgentLoginUserid.accountStatusCodesBean.accountStatusCode" id="agentAccountStatusCode" class="form-control editable-field">
            <c:set var="currentAccountStatusCode" value="${agentOwnerBean.loginDetailsByAgentLoginUserid.accountStatusCodesBean.accountStatusCode}"/>
            <c:choose>
           	  <c:when test="${currentAccountStatusCode eq ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()}">
           	    <form:option value=""><spring:message code="agentstatusajaxform.lbl.selstatus"/></form:option>
                <c:forEach items="${accountStatusCodeList}" var="statuscode">
                  <c:choose>
                    <c:when test="${statuscode.accountStatusCode eq ENUM_AccountStatusCodes.APPROVED.getStatus()}">
                      <form:option value="${statuscode.accountStatusCode}"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                    </c:when>
                    <c:otherwise>
                      <form:option value="${statuscode.accountStatusCode}" class="readonly"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </c:when>
              <c:when test="${currentAccountStatusCode eq ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()}">
                <c:forEach items="${accountStatusCodeList}" var="statuscode">
                   <form:option value="${statuscode.accountStatusCode}" class="readonly"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                </c:forEach>
              </c:when>
              <c:when test="${currentAccountStatusCode eq ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()}">
                <c:forEach items="${accountStatusCodeList}" var="statuscode">
                  <c:choose>
                    <c:when test="${statuscode.accountStatusCode eq ENUM_AccountStatusCodes.APPROVED.getStatus()}">
                      <form:option value="${statuscode.accountStatusCode}"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                    </c:when>
                    <c:otherwise>
                      <form:option value="${statuscode.accountStatusCode}" class="readonly"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </c:when>
              <c:when test="${currentAccountStatusCode eq ENUM_AccountStatusCodes.ACTIVE.getStatus()}">
                <c:forEach items="${accountStatusCodeList}" var="statuscode">
                  <c:choose>
                    <c:when test="${statuscode.accountStatusCode eq ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()}">
                      <form:option value="${statuscode.accountStatusCode}"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                    </c:when>
                    <c:otherwise>
                      <form:option value="${statuscode.accountStatusCode}" class="readonly"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </c:when>
           	  <c:otherwise>
           	    <c:forEach items="${accountStatusCodeList}" var="statuscode">
           	      <form:option value="${statuscode.accountStatusCode}" class="readonly"><spring:message code="status.${statuscode.accountStatusCode}"/></form:option>
           	    </c:forEach>
           	  </c:otherwise>
           	</c:choose>
        </form:select>
    </div>
    <div class="clearfix"></div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="agentstatusajaxform.lbl.privileges"/></label>
        <div class="col-sm-10 col-md-10 nopad">
        <form:select path="privileges" class="form-control editable-field">
            <form:option value=""><spring:message code="agentstatusajaxform.lbl.selpriv"/></form:option>
            <c:forEach items="${agentOwnerPriviList}" var="privilege">
                <form:option value="${privilege}">${privilege}</form:option>
            </c:forEach>
        </form:select>
    </div>
     <div class="clearfix"></div>
    </div>
     <c:if test="${agentOwnerBean.loginDetailsByOwnerLoginUserid.accountTypeCodesBean.accountType  eq 'B'.charAt(0)}">
    <div class="form-group">
		<label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="agentstatusajaxform.lbl.purchaseLimit"/></label>
		 <div class="col-sm-10 col-md-10 nopad">
		  <form:input class="form-control"   path="purchaseLimit" id="purchaseLimit" value="${agentOwnerBean.purchaseLimit}" onkeypress="return valueIsNumber(this,event);" onpaste="return false;" maxLength="9"/>
	</div>
	<div class="clearfix"></div>
	</div>
    </c:if>
    <div class="form-group text-right">
        <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="agentstatusajaxform.btn.updstatus"/>'/>       
        <!--fahad: fix button color-->
        <!--<button type="button" class="btn btn-default btn-lg button-right" data-dismiss="modal"><spring:message code="btn.close"/></button>-->
        <button type="button" class="text-hightlight btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal"><spring:message code="btn.close"/></button>
    </div>
    
</form:form>
<script type="text/javascript">
    $(document).ready(function() {
    	var minLimitSpent = 0.00;
        var maxLimitSpent = 999999.99;
    	var selectedAgentAccountStatusCode="";
   	 $("#agentAccountStatusCode").on("change",function(e){
   		 e.preventDefault();
   		 if($(this).find("option:selected").hasClass("readonly")){
   			 $(this).val(selectedAgentAccountStatusCode); 
   		 }
   		return false; 
   	 });
   	 $("#agentAccountStatusCode").on("click",function(e){
   		 e.preventDefault();
   		selectedAgentAccountStatusCode = $(this).val();
   	 });
        $("#agentStatusAjaxForm").validate({
        	 ignore: [],
            debug: true,
            rules: {
                "loginDetailsByAgentLoginUserid.accountStatusCodesBean.accountStatusCode": {
                    "required": true
                },
                "privileges": {
                    "required": true
                },
                "purchaseLimit":{
                	  "number": true,
                	  "required": true,
                	  "min": minLimitSpent,
                      "max": maxLimitSpent
                }
            },
            messages: {
                "loginDetailsByAgentLoginUserid.accountStatusCodesBean.accountStatusCode": {
                    "required": "<spring:message code='agentstatusajaxform.validation.accountStatusCode.required'/>"
                },
                "privileges": {
                    "required": "<spring:message code='agentstatusajaxform.validation.privileges.required'/>"
                },
                "purchaseLimit":{
                	 "number": "<spring:message code='agentstatusajaxform.validation.purchaseLimt.number'/>",
                	 "required": "<spring:message code='agentstatusajaxform.validation.purchaseLimt.required'/>",
                		 "min": "<spring:message code='agentstatusajaxform.validation.purchaseLimit.min'/>"  + minLimitSpent.toFixed(2),
                         "max": "<spring:message code='agentstatusajaxform.validation.purchaseLimit.max'/>" + maxLimitSpent.toFixed(2)
                }
            },
            invalidHandler: function(event, validator) {
            },
          errorPlacement: function (error, element) {
            	//error.insertAfter(element.parent(".form-group"));   
            	error.insertAfter(element);
            }, 
            submitHandler: function(form) {
        	 form.ajaxSubmit();
             $(form).find("input[type=submit]").attr("disabled",true);
         }, 
         highlight: function(element) {
           $(element).addClass("error-element");
         },
         unhighlight: function(element) {
           $(element).removeClass("error-element");
         }
       });
    });
</script>