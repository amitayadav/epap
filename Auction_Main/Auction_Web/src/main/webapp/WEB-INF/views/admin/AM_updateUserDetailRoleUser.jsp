<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountStatusCodes"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><a href="${contextPath}/sproptrel/userdetailslist"><spring:message code="userdetaillist.h3.userdetailslist"/></a></li>
    <li><spring:message code="updateuserdetail.h3.edituserdetails"/></li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3><spring:message code="updateuserdetail.h3.edituserdetails"/></h3>
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
            <p class="text-danger"><spring:message code="required.msg"/></p>
            <%-- <div class="x_title">
              <h2><spring:message code="updateuserdetail.h2.updateserdetails"/></h2>
              <div class="clearfix"></div>
            </div> --%>
            <div class="x_content">
              <form:form  action="${contextPath}/sproptrel/updateUserDetails" method="post" class="form-horizontal" modelAttribute="loginDetailsBean" id="updateUserDetailsFrom"  enctype="multipart/form-data">
                <form:hidden path="accountProfileBean.accountId"/>
                <form:hidden path="loginUserid"/>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.name"/></b>
                  </div>
                  
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.publicname" var="publicnamePhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${publicnamePhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                      <form:input class="form-control editable-field" path="publicName" id="publicName" placeholder="${publicnamePhl}" maxlength="30"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')">
                      <p class="read-only">${loginDetailsBean.publicName}</p>
                    </sec:authorize>
                  </div>
                   <div class="clearfix"></div>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.primaryaddress" var="primaryaddressPhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${primaryaddressPhl}</label>	                
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="loginUserid" id="loginUserid" placeholder="${primaryaddressPhl}" maxlength="30" disabled="true"/>
                  </div>
                   <div class="clearfix"></div>
                  </div>
                  <%-- <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.firstname" var="firstnamePhl"/>
                    <span class="required"> * </span><label>${firstnamePhl}</label>
                    <form:input class="form-control" path="accountProfileBean.FName" id="FName" placeholder="${firstnamePhl}" maxlength="20"/>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.middlename" var="middlenamePhl"/>
                    <label>${middlenamePhl}</label>
                    <form:input class="form-control" path="accountProfileBean.MName" id="MName" placeholder="${middlenamePhl}" maxlength="20"/>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.lastname" var="lastnamePhl"/>
                    <span class="required"> * </span><label>${lastnamePhl}</label>
                    <form:input class="form-control" path="accountProfileBean.LName" id="LName" placeholder="${lastnamePhl}" maxlength="20"/>
                  </div> --%>
                </div>
                <c:if test="${not empty loginDetailsBean.accountProfileBean.profileImage}">
                  <div class="x_panel">
                    <div class="x_title font-weight-bol">
                      <b><spring:message code="updateuserdetail.update.photo"/></b>
                    </div>
                    <div class="form-group">
                      <c:set var="imgPath" value="${contextPath}/resources/images/user1-128x128.jpg"/>
                      <form:hidden path="accountProfileBean.profileImage"/>
                      <img src="${contextPath}/setting/profilePictures/${loginDetailsBean.accountProfileBean.accountId}/${loginDetailsBean.accountProfileBean.profileImage}" class="user-profile-picture"/>
                    </div>
                  </div>
                </c:if>
                <%-- <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.business"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.companyname" var="companynamePhl"/>
                    <span class="required"> * </span><label>${companynamePhl}</label>
                    <form:input class="form-control" path="accountProfileBean.businessName" id="businessName" placeholder="${companynamePhl}" maxlength="20"/>
                  </div>
                </div> --%>
                <%-- <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.secondary"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.secondaryaddress" var="secondaryaddressPhl"/>
                    <label>
                      ${secondaryaddressPhl} (
                      <small>
                        <spring:message code="updateuserdetail.samll.secondaryaddress"/>
                      </small>
                      )
                    </label>
                    <form:input class="form-control" path="accountProfileBean.emailAddress2" id="emailAddress2" placeholder="${secondaryaddressPhl}" maxlength="30"/>
                  </div>
                </div> --%>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.phonenumber"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.phonenumber1" var="phonenumber1Phl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${phonenumber1Phl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.phoneNumber1" id="phoneNumber1" placeholder="${phonenumber1Phl}" maxlength="10"/>
                  </div>
                  <div class="clearfix"></div>
                  </div>
                  <%-- <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.phonenumber2" var="phonenumber2Phl"/>
                    <label>${phonenumber2Phl}</label>
                    <form:input class="form-control" path="accountProfileBean.phoneNumber2" id="phoneNumber2" placeholder="${phonenumber2Phl}" maxlength="16"/>
                  </div>--%>
                </div>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.accountstatus"/></b>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="updateuserdetail.lbl.status"/></label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                        <c:set var="currentAccountStatusCode" value="${loginDetailsBean.accountStatusCodesBean.accountStatusCode}"/>
	                    <form:select path="accountStatusCodesBean.accountStatusCode" class="form-control editable-field" id="accountStatusCode">
	                      <%-- <form:option value=""><spring:message code="updateuserdetail.select.selectstatus"/></form:option> --%>
	                      <%-- <form:options items="${accountStatusCodeList}" itemLabel="accountStatusCodeMeaning" itemValue="accountStatusCode"/> --%>
	                      <c:choose> 
	                        <c:when test="${currentAccountStatusCode eq ENUM_AccountStatusCodes.DELETED.getStatus() || currentAccountStatusCode eq ENUM_AccountStatusCodes.BLOCKED.getStatus() || currentAccountStatusCode eq ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus() || currentAccountStatusCode eq ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()}">
	                          <c:forEach items="${accountStatusCodeList}" var="statuscode">
	                            <c:choose>
	                              <c:when test="${statuscode.accountStatusCode eq ENUM_AccountStatusCodes.ACTIVE.getStatus()}">
	                                <form:option value="${statuscode.accountStatusCode}">
	                                  <spring:message code="status.${statuscode.accountStatusCode}"/>
	                                </form:option>
	                              </c:when>
	                              <c:otherwise>
	                                <form:option value="${statuscode.accountStatusCode}" class="readonly">
	                                  <spring:message code="status.${statuscode.accountStatusCode}"/>
	                                </form:option>
	                              </c:otherwise>
	                            </c:choose>
	                          </c:forEach>
	                        </c:when>
	                        <c:when test="${currentAccountStatusCode eq ENUM_AccountStatusCodes.ACTIVE.getStatus()}">
	                          <c:forEach items="${accountStatusCodeList}" var="statuscode">
	                            <c:choose>
	                              <c:when test="${statuscode.accountStatusCode eq ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus() || statuscode.accountStatusCode eq ENUM_AccountStatusCodes.DELETED.getStatus() || statuscode.accountStatusCode eq ENUM_AccountStatusCodes.ACTIVE.getStatus()}">
	                                <form:option value="${statuscode.accountStatusCode}">
	                                  <spring:message code="status.${statuscode.accountStatusCode}"/>
	                                </form:option>
	                              </c:when>
	                              <c:otherwise>
	                                <form:option value="${statuscode.accountStatusCode}" class="readonly">
	                                  <spring:message code="status.${statuscode.accountStatusCode}"/>
	                                </form:option>
	                              </c:otherwise>
	                            </c:choose>
	                          </c:forEach>
	                        </c:when>
	                        <c:otherwise>
	                          <c:forEach items="${accountStatusCodeList}" var="statuscode">
	                            <form:option value="${statuscode.accountStatusCode}" class="readonly">
	                              <spring:message code="status.${statuscode.accountStatusCode}"/>
	                            </form:option>
	                          </c:forEach>
	                        </c:otherwise>
	                      </c:choose>
	                    </form:select>
	                 </sec:authorize>
	                 <sec:authorize access="hasAnyRole('ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')">
                       <p class="read-only"><spring:message code="status.${loginDetailsBean.accountStatusCodesBean.accountStatusCode}"/></p>
                     </sec:authorize>
                  </div>
                  <div class="clearfix"></div>
                  </div>
                  <%-- <div class="form-group">
                    <span class="required"> * </span>
                    <label>
                      <spring:message code="updateuserdetail.lbl.royaltycode"/>
                    </label>
                    <form:select path="accountProfileBean.royaltyCodesBean.royaltyCode" class="form-control">
                      <form:option value="">
                        <spring:message code="updateuserdetail.select.selectroyalty"/>
                      </form:option>
                      <form:options items="${royaltyCodeList}" itemLabel="royaltyValue" itemValue="royaltyCode"/>
                    </form:select>
                  </div> --%>
                </div>
                <%-- <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.nationalid"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.nationalid" var="nationalidPhl"/>
                    <label>${nationalidPhl}</label>
                    <form:input class="form-control" path="accountProfileBean.governmentId" id="governmentId" placeholder="${nationalidPhl}" maxlength="10"/>
                  </div>
                </div> --%>
                <%-- <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.contact"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.postaladdress" var="postaladdressPhl"/>
                    <label>${postaladdressPhl}</label>
                    <form:input class="form-control" path="accountProfileBean.postalAddress" id="postalAddress" placeholder="${postaladdressPhl}" maxlength="50"/>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.contactus" var="contactusPhl"/>
                    <label>${contactusPhl}</label>
                    <form:input class="form-control" path="accountProfileBean.contactUs" id="contactUs" placeholder="${contactusPhl}" maxlength="100"/>
                  </div>
                </div> --%>
                <div class="form-group text-right">
                  <spring:message code="updateuserdetail.btn.saveprofile" var="saveprofilePhl"/>
                  <spring:message code="updateuserdetail.btn.reset" var="resetPhl"/>
                  <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${saveprofilePhl}"/>
                  <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value="${resetPhl}" formnovalidate="formnovalidate"/>
                </div>
              </form:form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	 var selectedAccountStatusCode="";
	 $("#accountStatusCode").on("change",function(e){
		 e.preventDefault();
		 if($(this).find("option:selected").hasClass("readonly")){
			 $(this).val(selectedAccountStatusCode); 
		 }
		return false; 
	 });
	 $("#accountStatusCode").on("click",function(e){
		 e.preventDefault();
		 selectedAccountStatusCode = $(this).val();
	 });
	 $("#updateUserDetailsFrom").validate({
  		debug: true,
  		rules: {
  			<sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
  			  "publicName" : {"required": true , maxlength: 30},
  			  "accountStatusCodesBean.accountStatusCode" : {"required": true},
            </sec:authorize>
            "accountProfileBean.phoneNumber1" : {"required": true,number: true,maxlength: 10,minlength:10},
  			/*"accountProfileBean.FName" : {"required": true , maxlength: 20},
  			"accountProfileBean.MName" : {maxlength: 20},
  			"accountProfileBean.LName" : {"required": true , maxlength: 20},
  			"accountProfileBean.businessName" : {"required": true,maxlength: 20},
  			"accountProfileBean.phoneNumber2" : {number: true,maxlength: 10,minlength:10},
  			"accountProfileBean.royaltyCodesBean.royaltyCode" : {"required": true},
  			"accountProfileBean.emailAddress2" : {emailpattern:[true,false], maxlength: 30},*/
  		},
  		messages:{
  			<sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
			  "publicName" : {"required": true , maxlength: 30},
			  "accountStatusCodesBean.accountStatusCode" : {"required": "<spring:message code='updateuserdetail.validation.accountStatusCode.required'/>"},
            </sec:authorize>
			"accountProfileBean.phoneNumber1" : {"required": "<spring:message code='updateuserdetail.validation.phoneNumber1.required'/>","minlength" : "<spring:message code='updateuserdetail.validation.phoneNumber1.minlength'/>", "maxlength" : "<spring:message code='updateuserdetail.validation.phoneNumber1.maxlength'/>"},
  			/*"publicName": {"required": "<spring:message code='updateuserdetail.validation.publicName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.publicName.maxlength'/>"},
  			"accountProfileBean.FName" : {"required": "<spring:message code='updateuserdetail.validation.FName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.FName.maxlength'/>"},
  			"accountProfileBean.LName" : {"required": "<spring:message code='updateuserdetail.validation.LName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.LName.maxlength'/>"},
  			"accountProfileBean.MName" : {"maxlength":"<spring:message code='updateuserdetail.validation.MName.maxlength'/>"},
  			"accountProfileBean.businessName" : {"required": "<spring:message code='updateuserdetail.validation.businessName.required'/>"},
  			"accountProfileBean.royaltyCodesBean.royaltyCode" : {"required": "<spring:message code='updateuserdetail.validation.royaltyCode.required'/>"},
  			"accountProfileBean.emailAddress2" : {
  				"emailpattern": "<spring:message code='updateuserdetail.validation.emailAddress2.emailpattern'/>"
  			},*/
  		},
  		invalidHandler: function(event, validator) {
  			
  		},
  		errorPlacement: function (error, element) {
  			error.insertAfter(element);   
  		}, 
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
	 
	$("#clearAll").on("click",function(){
		$("#updateUserDetailsFrom").validate().resetForm(); 
	});
    
 });
</script>