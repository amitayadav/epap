<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountStatusCodes"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountTypeCodes"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>

<c:set var="TYPE_SHIPPER" value="${ENUM_AccountTypeCodes.SHIPPER.getType()}"/>
<c:set var="TYPE_SHIPPER_AGENT" value="${ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}"/>
<c:set var="CURRENT_TYPE" value="${loginDetailsBean.accountTypeCodesBean.accountType}"/>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><a href="${contextPath}/sproptrel/userdetailslist"><spring:message code="userdetaillist.h3.userdetailslist"/></a></li>
    <li><spring:message code="updateuserdetail.h3.edituserdetails"/></li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3>
          <spring:message code="updateuserdetail.h3.edituserdetails"/>
        </h3>
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
            <c:if test="${not empty ownerLoginDetailsBean}">
              <div class="x_panel">
	              <div class="x_title font-weight-bol">
	                <b><spring:message code="updateuserdetail.b.ownerdetails"/></b>
	              </div>
	              <div class="form-group">
	                <label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="updateuserdetail.lbl.ownerinfo"/></label>
	                <div class="col-sm-10 col-md-10 nopad">
	                <p class="read-only"><b>${ownerLoginDetailsBean.accountProfileBean.fullName}</b> (${ownerLoginDetailsBean.publicName})</p>
	              </div>
	               <div class="clearfix"></div>
	              </div>
	            </div>
            </c:if>
            <div class="x_content">
              <form:form  action="${contextPath}/sproptrel/updateUserDetails" method="post" class="form-horizontal" modelAttribute="loginDetailsBean" id="updateUserDetailsFrom"  enctype="multipart/form-data">
                <form:hidden path="accountProfileBean.accountId"/>
                <form:hidden path="loginUserid"/>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b>
                      <spring:message code="updateuserdetail.update.name"/>
                    </b>
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
                    <spring:message code="updateuserdetail.lbl.firstname" var="firstnamePhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${firstnamePhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.FName" id="FName" placeholder="${firstnamePhl}" maxlength="20"/>                    
                  </div>
                  <div class="clearfix"></div>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.middlename" var="middlenamePhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad">${middlenamePhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.MName" id="MName" placeholder="${middlenamePhl}" maxlength="20"/>
                  </div>
                  <div class="clearfix"></div>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.lastname" var="lastnamePhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${lastnamePhl}</label>
					<div class="col-sm-10 col-md-10 nopad">                   
					<form:input class="form-control editable-field" path="accountProfileBean.LName" id="LName" placeholder="${lastnamePhl}" maxlength="20"/>
                  </div>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <c:if test="${not empty loginDetailsBean.accountProfileBean.profileImage }">
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
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.business"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.companyname" var="companynamePhl"/>
                   <label class="col-sm-2 col-md-2 control-label nolpad"> <span class="required"> * </span>${companynamePhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
					<sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                      <form:input class="form-control editable-field" path="accountProfileBean.businessName" id="businessName" placeholder="${companynamePhl}" maxlength="50"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')">
                      <p class="read-only">${loginDetailsBean.accountProfileBean.businessName}</p>
                    </sec:authorize>
                  </div>
                  </div>
                   <div class="clearfix"></div>
                </div>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.secondary"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.primaryaddress" var="primaryaddressPhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${primaryaddressPhl}</label>	                
                    <div class="read-only col-sm-10 col-md-10 nopad">
                    <form:input class="form-control" path="loginUserid" id="loginUserid" placeholder="${primaryaddressPhl}" maxlength="30" disabled="true"/>
                  </div>
                  <div class="clearfix"></div>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.secondaryaddress" var="secondaryaddressPhl"/>
                    (<small><spring:message code="updateuserdetail.samll.secondaryaddress"/></small>)
                    <label class="col-sm-2 col-md-2 control-label nolpad">${secondaryaddressPhl}
                    </label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.emailAddress2" id="emailAddress2" placeholder="${secondaryaddressPhl}" maxlength="30"/>
                  </div>
                  </div>
                   <div class="clearfix"></div>
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
                  <div class="form-group">
                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="updateuserdetail.lbl.royaltycode"/></label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                      <form:select path="accountProfileBean.royaltyCodesBean.royaltyCode" class="form-control editable-field">
                        <form:option value=""><spring:message code="updateuserdetail.select.selectroyalty"/></form:option>
                        <form:options items="${royaltyCodeList}" itemLabel="royaltyValue" itemValue="royaltyCode"/>
                      </form:select>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')">
                      <p class="read-only">${loginDetailsBean.accountProfileBean.royaltyCodesBean.royaltyValue}</p>
                    </sec:authorize>
                  </div>
                  </div>
                   <div class="clearfix"></div>
                </div>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.nationalid"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.nationalid" var="nationalidPhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad">${nationalidPhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                      <form:input class="form-control editable-field" path="accountProfileBean.governmentId" id="governmentId" placeholder="${nationalidPhl}" maxlength="10"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')">
                      <p class="read-only">${loginDetailsBean.accountProfileBean.governmentId}</p>
                    </sec:authorize>
                  </div>
                  </div>
                  <div class="clearfix"></div>
                </div>
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
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.phonenumber2" var="phonenumber2Phl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad">${phonenumber2Phl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.phoneNumber2" id="phoneNumber2" placeholder="${phonenumber2Phl}" maxlength="10"/>
                  </div>
                   <div class="clearfix"></div>
                  </div>
                </div>
                <div class="x_panel">
                  <div class="x_title font-weight-bol">
                    <b><spring:message code="updateuserdetail.update.contact"/></b>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.postaladdress" var="postaladdressPhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad">${postaladdressPhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.postalAddress" id="postalAddress" placeholder="${postaladdressPhl}" maxlength="50"/>
                  </div>
                   <div class="clearfix"></div>
                  </div>
                  <div class="form-group">
                    <spring:message code="updateuserdetail.lbl.contactus" var="contactusPhl"/>
                    <label class="col-sm-2 col-md-2 control-label nolpad">${contactusPhl}</label>
                    <div class="col-sm-10 col-md-10 nopad">
                    <form:input class="form-control editable-field" path="accountProfileBean.contactUs" id="contactUs" placeholder="${contactusPhl}" maxlength="100"/>
                  </div>
                  <div class="clearfix"></div>
                  </div>
                </div>
                <c:if test="${CURRENT_TYPE eq TYPE_SHIPPER or CURRENT_TYPE eq TYPE_SHIPPER_AGENT}">
                  <div id="driverLicense">
				    <div class="x_panel">
				      <div class="x_title font-weight-bol">
				      	<b><spring:message code="editProfile.b.driverLicense" /></b>
				      </div>
				      <form:hidden path="accountProfileBean.shippersRegistrationInfoBean.accountId"/>
				      <div class="form-group">
				        <spring:message code="updateuserdetail.lbl.driverLicenseExpirationDate" var="driverLicenseExpirationDatePhl"/>
				        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${driverLicenseExpirationDatePhl}</label>
				        <div class="col-sm-10 col-md-10 nopad">
				          <c:choose>
				          	<c:when test="${not empty loginDetailsBean.accountProfileBean.shippersRegistrationInfoBean && not empty loginDetailsBean.accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate }">
				          	  <input class="form-control editable-field" name="driverLicenseExpirationDate" id="driverLicenseExpirationDate" placeholder="${driverLicenseExpirationDatePhl}" readonly="readonly" value='<fmt:formatDate dateStyle="long" pattern='MM-yyyy' value="${loginDetailsBean.accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate}"/>'/>
				          	</c:when>
				          	<c:otherwise>
				          	  <input class="form-control editable-field" name="driverLicenseExpirationDate" id="driverLicenseExpirationDate" placeholder="${driverLicenseExpirationDatePhl}" readonly="readonly"/>
				          	</c:otherwise>
				          </c:choose>
				        </div>
				        <div class="clearfix"></div>
				      </div>
				      <div class="form-group">
				        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>
				        	<spring:message code="updateuserdetail.lbl.truckModelYear" var="truckModelYear"/>
				        	${truckModelYear}
				        </label>
				        <div class="col-sm-10 col-md-10 nopad">
				          <form:input class="form-control editable-field" path="accountProfileBean.shippersRegistrationInfoBean.truckModelYear"  id="truckModelYear" placeholder="${truckModelYear}" readonly="true" value="${loginDetailsBean.accountProfileBean.shippersRegistrationInfoBean.truckModelYear}"/>
				        </div>
				        <div class="clearfix"></div>
				      </div>
				      <div class="form-group">
				        <label class="col-sm-2 col-md-2 control-label nolpad">
				          <span class="required"> * </span><spring:message code="updateuserdetail.lbl.truckType"/>
				        </label>
				        <div class="col-sm-10 col-md-10 nopad">
				          <div class="radio">
				            <form:radiobutton path="accountProfileBean.shippersRegistrationInfoBean.truckType" class="truckType" id="truckSmall" value="small" checked="checked" />
				            <label for="truckSmall">Small</label>
				            &nbsp;&nbsp;&nbsp;&nbsp;
				            <form:radiobutton path="accountProfileBean.shippersRegistrationInfoBean.truckType" class="truckType" id="truckLarge" value="large" />
				            <label for="truckLarge">Large</label>
				          </div>
				        </div>
				        <div class="clearfix"></div>
				      </div>
				    </div>
				  </div>
				  </c:if>
				    
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
    		  "accountProfileBean.businessName" : {"required": true,maxlength: 50},
    		  "accountProfileBean.royaltyCodesBean.royaltyCode" : {"required": true},
    		  "accountStatusCodesBean.accountStatusCode" : {"required": true},
            </sec:authorize>
  			"accountProfileBean.FName" : {"required": true , maxlength: 20},
  			"accountProfileBean.MName" : {maxlength: 20},
  			"accountProfileBean.LName" : {"required": true , maxlength: 20},
  			"accountProfileBean.phoneNumber1" : {"required": true,number: true,maxlength: 10,minlength:10},
  			"accountProfileBean.phoneNumber2" : {number: true,maxlength: 10,minlength:10},
  			"accountProfileBean.emailAddress2" : {emailpattern:[true,false], maxlength: 30},
  			"driverLicenseExpirationDate":{
  	        	"required": true
  	        },
  	        "shippersRegistrationInfoBean.truckModelYear":{
  	        	"required": true
  	        },
  	        "shippersRegistrationInfoBean.truckType":{
  	        	"required": true
  	        },
  		},
  		messages:{
  			<sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
  			  "publicName": {"required": "<spring:message code='updateuserdetail.validation.publicName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.publicName.maxlength'/>"},
  			  "accountProfileBean.businessName" : {"required": "<spring:message code='updateuserdetail.validation.businessName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.businessName.maxlength'/>"},
  			  "accountProfileBean.royaltyCodesBean.royaltyCode" : {"required": "<spring:message code='updateuserdetail.validation.royaltyCode.required'/>"},
  			  "accountStatusCodesBean.accountStatusCode" : {"required": "<spring:message code='updateuserdetail.validation.accountStatusCode.required'/>"},
  			</sec:authorize>  			
  			"accountProfileBean.FName" : {"required": "<spring:message code='updateuserdetail.validation.FName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.FName.maxlength'/>"},
  			"accountProfileBean.LName" : {"required": "<spring:message code='updateuserdetail.validation.LName.required'/>","maxlength":"<spring:message code='updateuserdetail.validation.LName.maxlength'/>"},
  			"accountProfileBean.MName" : {"maxlength":"<spring:message code='updateuserdetail.validation.MName.maxlength'/>"},
  			"accountProfileBean.phoneNumber1" : {"required": "<spring:message code='updateuserdetail.validation.phoneNumber1.required'/>","minlength" : "<spring:message code='updateuserdetail.validation.phoneNumber1.minlength'/>", "maxlength" : "<spring:message code='updateuserdetail.validation.phoneNumber1.maxlength'/>",},
  			"accountProfileBean.emailAddress2" : {
  			"emailpattern": "<spring:message code='updateuserdetail.validation.emailAddress2.emailpattern'/>"
  			},
  			 "driverLicenseExpirationDate":{
  	        	"required":'<spring:message code="updateuserdetail.validation.driverLicenseExpirationDate.required"></spring:message>'
  	        },
  	        "shippersRegistrationInfoBean.truckModelYear":{
  	        	"required":'<spring:message code="updateuserdetail.validation.truckModelYear.required"></spring:message>'
  	        },
  	        "shippersRegistrationInfoBean.truckType":{
  	        	"required":'<spring:message code="updateuserdetail.validation.truckType.required"></spring:message>'
  	        },
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
	 $("#driverLicenseExpirationDate").datepicker({
   	  	format: "mm-yyyy",
	    startView: 1,
	    minViewMode: 1,
	    maxViewMode: 2,
	    startDate:"${InternetTiming.getCurrentMonth()}"+"-"+"${InternetTiming.getCurrentYear()}"
	    });

	    $("#truckModelYear").datepicker({        	 
    	    format: "yyyy",
    	    startView: 2,
    	    minViewMode: 2,
    	    endDate:"${InternetTiming.getCurrentYear()}"
	    });
 });
</script>