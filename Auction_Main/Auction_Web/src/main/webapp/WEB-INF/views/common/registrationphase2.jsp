<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>
<%@ page import="com.auction.commons.enums.ENUM_NotificationCodes"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountTypeCodes"%>

<c:set var="TYPE_BUYER" value="${ENUM_AccountTypeCodes.BUYER.getType()}"/>
<c:set var="TYPE_SELLER" value="${ENUM_AccountTypeCodes.SELLER.getType()}"/>
<c:set var="TYPE_SHIPPER" value="${ENUM_AccountTypeCodes.SHIPPER.getType()}"/>
<c:set var="TYPE_SHIPPER_AGENT" value="${ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}"/>
<c:set var="CURRENT_TYPE" value="${loginUser.accountTypeCodesBean.accountType}"/>

<style>
  #profileImg-error{padding-top: 10px;clear: both;}
  .file-input{max-width: 195px;}
</style>
<!-- page content -->
<div class="right_col" role="main">
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
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3>
          <spring:message code="regProfile.h3.accountProfile"/>
        </h3>
      </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
          <div class="x_title">
            <p class="text-danger">
              <spring:message code="required.msg"/>
            </p>
            <h2>
              <spring:message code="regProfile.h2.accountProfileRegDetail"/>
            </h2>
            <div class="clearfix"></div>
          </div>
          <div class="x_content">
            <c:if test="${not empty errorStack}">
		      <div class="error-stack">
		        <c:forEach items="${errorStack}" var="error">
		          <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
		        </c:forEach>
		      </div>
		    </c:if>
            <form:form action="${contextPath}/setting/registeraccountprofile" method="post" class="form-horizontal" modelAttribute="AccountProfileBean" id="registrationSecondPhaseFrom" enctype="multipart/form-data">
              <form:hidden path="numOfRatings" value="0" />
              <form:hidden path="ratingTotal" value="0" />
              <form:hidden path="noOfCancellations" value="0" />
              <form:hidden path="offerOrBidCount" value="0" />
              <form:hidden path="executedOfferOrBidCount" value="0" />
              <!-- Place holders -->
              <spring:message code="regProfile.phl.publicName" var="publicNamephl"/>
              <spring:message code="regProfile.phl.firstName" var="firstNamephl"/>
              <spring:message code="regProfile.phl.middleName" var="middleNamephl"/>
              <spring:message code="regProfile.phl.lastName" var="lastNamephl"/>
              <spring:message code="regProfile.phl.companyName" var="companyNamephl"/>
              <spring:message code="regProfile.phl.primaryAddress" var="primaryAddressphl"/>
              <spring:message code="regProfile.phl.secondaryAddress" var="secondaryAddressphl"/>
              <spring:message code="regProfile.phl.nationalId" var="nationalIdphl"/>
              <spring:message code="regProfile.phl.phoneNumber1" var="phoneNumber1phl"/>
              <spring:message code="regProfile.phl.phoneNumber2" var="phoneNumber2phl"/>
              <spring:message code="regProfile.phl.postalAddress" var="postalAddressphl"/>
              <spring:message code="regProfile.phl.contactUs" var="contactUsphl"/>
              <spring:message code="regProfile.phl.bankName" var="bankNamephl"/>
              <spring:message code="regProfile.phl.accountNumber" var="accountNumberphl"/>
              <spring:message code="regProfile.phl.iban" var="ibanphl"/>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b><spring:message code="regProfile.b.enterNameAppearsOnOfficialDocument"/></b>
                </div>
                <div class="form-group">
                 <label class="col-sm-2 col-md-2 control-label nolpad">
                 <span class="required"> * </span>
                  <spring:message code="regProfile.lbl.publicName"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">                  
                  	<p class="read-only">${publicName}</p>
                  </div> 
                  <div class="clearfix"></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                  <span class="required"> * </span>
                  <spring:message code="regProfile.lbl.firstName"/>
                   </label>
                   <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="FName" id="FName" placeholder="${firstNamephl}" maxlength="20"/>
                </div>
                <div class="clearfix"></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.middleName"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="MName" id="MName" placeholder="${middleNamephl}" maxlength="20" />
                </div>
                <div class="clearfix"></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                  <span class="required"> * </span>
                    <spring:message code="regProfile.lbl.lastName"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="LName" id="LName" placeholder="${lastNamephl}" maxlength="20" />
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b><spring:message code="regProfile.lbl.uploadphoto"/></b>
                </div>
                <div class="form-group user-profile-picture-upload">
                  <form:hidden path="profileImage" />
                  <input id="profileImg" data-min-file-count="0" data-show-caption="false" type="file" name="profilePicture"/>
                </div>
                <div class="clearfix"></div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterNameYourBusinessAppearsOfficialDocument"/>
                  </b>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                  <span class="required"> * </span>
                    <spring:message code="regProfile.lbl.companyName"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="businessName" id="businessName" placeholder="${companyNamephl}" maxlength="50"/>
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterSecondaryEmail"/>
                  </b>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                  <span class="required"> * </span>
                    <spring:message code="regProfile.lbl.primaryAddress"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <input type="text" class="form-control" placeholder="${primaryAddressphl}" maxlength="30" value="${emailAddress}" disabled="disabled" />
                </div>
                <div class="clearfix"></div>
                </div>
                <div class="form-group">
                  <small>(<spring:message code="regProfile.lbl.secondaryAddress.small"/>)</small>
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.secondaryAddress"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="emailAddress2" id="emailAddress2" placeholder="${secondaryAddressphl}" maxlength="30" />
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterYourNationalIdNumberOrIqamaNo"/>
                  </b>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                  <span class="required"> * </span>
                    <spring:message code="regProfile.lbl.nationalId"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="governmentId" id="governmentId" placeholder="${nationalIdphl}" maxlength="10" />
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterPhoneNumber"/>
                  </b>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                  <span class="required"> * </span>
                    <spring:message code="regProfile.lbl.phoneNumber1"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="phoneNumber1" id="phoneNumber1" name="phoneNumber1" placeholder="${phoneNumber1phl}" maxlength="10" />
                </div>
                <div class="clearfix"></div>
                </div>
                
                   <div class="form-group">
                   <div class="col-sm-2 col-md-2 control-label nolpad">
                   </div>
                   <div class="col-sm-10 col-md-10 nopad">
	                 	 <input type="button" class="btn btn-verify btn-lg button-left text-hightlight"  value="<spring:message code='otpverification.btn.verifynow'/>" onclick="verifyNowOtp()" id="verifyNow" name="verifyNow"></input>
	                 	 <input type="button" class="btn btn-verify btn-lg button-left text-hightlight"  value="<spring:message code='otpverification.btn.verified'/>"  id="verified" name="verified" disabled="disabled"></input>
	               		 <label class="error mt-5" id="verifyNumberMsg"></label>
	               		 <label class="error mt-5" style="color: #F00;" id="accessPhoneNumber"></label>
	               		  
	               </div>
	              <!-- 	<div>
	              	<label class="col-md-7 control-label" style="color: #F00;"  id="accessPhoneNumber"></label>
	              	</div> -->
	                </div>
	                   
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.phoneNumber2"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="phoneNumber2" id="phoneNumber2" placeholder="${phoneNumber2phl}" maxlength="10" />
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterContactDetails"/>
                  </b>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.postalAddress"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="postalAddress" id="postalAddress" placeholder="${postalAddressphl}" maxlength="50" />
                </div>
                <div class="clearfix"></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.contactUs"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <form:input class="form-control" path="contactUs" id="contactUs" placeholder="${contactUsphl}" maxlength="100" />
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.selectLanguage"/>
                  </b>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.preferredLanguage"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <div class="radio">
                    <form:radiobutton path="preferredLanguage" class="accountType" id="lag_en" value="en"/>
                    <label for="lag_en">
                      <spring:message code="language.english"/>
                    </label>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <form:radiobutton path="preferredLanguage" class="accountType" id="lag_ar" value="ar"/>
                    <label for="lag_ar">
                      <spring:message code="language.arabic"/>
                    </label>
                  </div>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <hr class="hr"/>
                <div class="form-group ">
                  <label class="col-sm-2 col-md-2 control-label nolpad">
                    <spring:message code="regProfile.lbl.notiMethod"/>
                  </label>
                  <div class="col-sm-10 col-md-10 nopad">
                  <div class="checkbox checkbox-warning">
                    
                    <form:hidden path="notificationCodesBean.notificationCode" id="notificationCode" />
                    <input type="checkbox" name="notificationCode_sms" id="sms" />
                    <label for="sms"><spring:message code="regProfile.lbl.sms"/></label>
                    
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    
                    <input type="checkbox" name="notificationCode_email" id="email" />
                    <label for="email"><spring:message code="regProfile.lbl.email"/></label>
                    
                  </div>
                </div>
                <div class="clearfix"></div>
                </div>
              </div>
              <%-- <sec:authorize access="hasAnyRole('ROLE_SELLER','ROLE_BUYER','ROLE_SHIPPER')"> --%>
              <c:if test="${CURRENT_TYPE eq TYPE_BUYER or CURRENT_TYPE eq TYPE_SELLER or CURRENT_TYPE eq TYPE_SHIPPER}">
                <div id="bankDetails">
                  <div class="x_panel">
                    <div class="x_title font-weight-bol">
                      <b><spring:message code="regProfile.b.enterBankInfo"/></b>
                    </div>
                    <form:hidden path="bankDetailsBean.accountId" />
                    <form:hidden path="bankDetailsBean.availableBalance" value="0.00" />
                    <form:hidden path="bankDetailsBean.blockedAmount" value="0.00" />
                    <form:hidden path="bankDetailsBean.advanceBalance" value="0.00" />
                    <form:hidden path="bankDetailsBean.cashposition" value="0.00" />
                    <div class="form-group">
                      <label class="col-sm-2 col-md-2 control-label nolpad">
                      <span class="required"> * </span>
                        <spring:message code="regProfile.lbl.bankName"/>
                      </label>
                      <div class="col-sm-10 col-md-10 nopad">
                      <form:input class="form-control" path="bankDetailsBean.bankName" id="bankName" placeholder="${bankNamephl}" maxlength="30" />
                    </div>
                    <div class="clearfix"></div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 col-md-2 control-label nolpad">
                      <span class="required"> * </span>
                        <spring:message code="regProfile.lbl.accountNumber"/>
                      </label>
                      <div class="col-sm-10 col-md-10 nopad">
                      <form:input class="form-control" path="bankDetailsBean.bankAccountNumber" id="bankAccountNumber" placeholder="${accountNumberphl}" maxlength="16" />
                    </div>
                    <div class="clearfix"></div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 col-md-2 control-label nolpad">
                      <span class="required"> * </span>
                        <spring:message code="regProfile.lbl.iban"/>
                      </label>
                      <div class="col-sm-10 col-md-10 nopad">
                      <form:input class="form-control" path="bankDetailsBean.iban" id="iban" placeholder="${ibanphl}" maxlength="24" />
                    </div>
                    <div class="clearfix"></div>
                    </div>
                  </div>
                </div>
              </c:if>
              <c:if test="${CURRENT_TYPE eq TYPE_SHIPPER or CURRENT_TYPE eq TYPE_SHIPPER_AGENT}">
				  <div id="driverLicense">
				    <div class="x_panel">
				      <div class="x_title font-weight-bol">
				      	<b><spring:message code="regProfile.b.driverLicense" /></b>
				      </div>
				      <form:hidden path="shippersRegistrationInfoBean.accountId"/>
				      <div class="form-group">
				        <spring:message code="regProfile.lbl.driverLicenseExpirationDate" var="driverLicenseExpirationDatePhl"/>
				        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${driverLicenseExpirationDatePhl}</label>
				        <div class="col-sm-10 col-md-10 nopad">
				          <c:choose>
				          	<c:when test="${not empty accountProfileBean.shippersRegistrationInfoBean && not empty accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate }">
				          	  <input class="form-control" name="driverLicenseExpirationDate" id="driverLicenseExpirationDate" placeholder="${driverLicenseExpirationDatePhl}" readonly="readonly" value='<fmt:formatDate dateStyle="long" pattern='yyyy-MM-dd' value="${accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate}"/>'/>
				          	</c:when>
				          	<c:otherwise>
				          	  <input class="form-control" name="driverLicenseExpirationDate" id="driverLicenseExpirationDate" placeholder="${driverLicenseExpirationDatePhl}" readonly="readonly"/>
				          	</c:otherwise>
				          </c:choose>
				        </div>
				        <div class="clearfix"></div>
				      </div>
				      <div class="form-group">
				        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>
				        	<spring:message code="regProfile.lbl.truckModelYear" var="truckModelYear"/>
				        	${truckModelYear}
				        </label>
				        <div class="col-sm-10 col-md-10 nopad">
				          <form:input class="form-control" path="shippersRegistrationInfoBean.truckModelYear" id="truckModelYear" placeholder="${truckModelYear}" readonly="true" value="${accountProfileBean.shippersRegistrationInfoBean.truckModelYear}"/>
				        </div>
				        <div class="clearfix"></div>
				      </div>
				      <div class="form-group">
				        <label class="col-sm-2 col-md-2 control-label nolpad">
				          <span class="required"> * </span><spring:message code="regProfile.lbl.truckType"/>
				        </label>
				        <div class="col-sm-10 col-md-10 nopad">
				          <div class="radio">
				            <form:radiobutton path="shippersRegistrationInfoBean.truckType" class="truckType" id="truckTypeSmall" value="Small" checked="checked"/>
				            <label for="truckTypeSmall">Small</label>
				            &nbsp;&nbsp;&nbsp;&nbsp;
				            <form:radiobutton path="shippersRegistrationInfoBean.truckType" class="truckType" id="truckTypeLarge" value="large" />
				            <label for="truckTypeLarge">Large</label>
				          </div>
				        </div>
				        <div class="clearfix"></div>
				      </div>
				    </div>
				  </div>
			  </c:if>
	          <c:if test="${CURRENT_TYPE eq TYPE_BUYER or CURRENT_TYPE eq TYPE_SELLER or CURRENT_TYPE eq TYPE_SHIPPER or CURRENT_TYPE eq TYPE_SHIPPER_AGENT}">
                <!-- Location Management -->
                <div class="x_panel">
	                <div class="x_title font-weight-bol">
	                  <b><spring:message code="regProfile.b.enterYourLocation"/></b>
	                </div>
	                <div class="form-group">
	                  <input type="button" class="btn btn-info" value="<spring:message code="regProfile.btn.addLocation"/>" style="width: 100%;" onclick="myLocation()" >
	                </div>
	                <div class="form-group">
	                  <table id="locationtable" class="table table-striped table-bordered">
	                    <thead>
	                      <tr>
	                        <th align="center"><spring:message code="regProfile.th.locationName"/></th>
	                        <th width="80" class="text-right"><spring:message code="editProfile.th.latitude"/></th>
	                        <th width="80" class="text-right"><spring:message code="editProfile.th.longitude"/></th>
	                        <th width="125"><spring:message code="editProfile.th.status"/></th>
	                        <th width="40"><spring:message code="regProfile.th.delete"/></th>
	                      </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                  </table>
	                </div>
	              </div>
	              <!--  End Location Management -->
	          </c:if>
	          
	          
              <%-- </sec:authorize> --%>
              <div class="x_panel">
                <div class="text-primary">
                  <b><spring:message code="regProfile.b.note"/></b>
                  <spring:message code="regProfile.div.noteMessage"/>
                </div>
              </div>
              <div class="form-group text-right">
                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="<spring:message code="regProfile.btn.saveProfile"/>" />
                <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value="<spring:message code="regProfile.btn.reset"/>" formnovalidate="formnovalidate" />
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="locationModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-xlg">
    <div class="modal-content">
    </div>
  </div>
</div>


<div class="modal fade" id="otpModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title"> <spring:message code="otpverification.lbl.title"/></h4>
            </div>
            <div class="modal-body">
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
  var NC_SMS_ONLY =" ${ENUM_NotificationCodes.SMS_ONLY.getStatus()}";
  var NC_EMAIL_ONLY = "${ENUM_NotificationCodes.EMAIL_ONLY.getStatus()}";
  var NC_BOTH = "${ENUM_NotificationCodes.BOTH.getStatus()}";
  var isPhoneNumber1validation = true;
  $(document).ready(function() {
	  $('#accessPhoneNumber').html("<spring:message code='otpverification.lbl.msg.accesslimit'/>");
	  $('#editPhoneNumber').hide();
	  $('#verified').hide();
	//$("#verifyNumberMsg").html("Please click on verifiynow whether click on  later");
	$("#FName").focus();
  	$('.checkbox :checkbox').change(function() {
        if ($("#sms").is(':checked') && $("#email").is(':checked')) {
            $("#notificationCode").val(NC_BOTH);
        } else if ($("#sms").is(':checked')) {
            $("#notificationCode").val(NC_SMS_ONLY);
        } else if ($("#email").is(':checked')) {
            $("#not`ificationCode").val(NC_EMAIL_ONLY);
        } else {
        	$("#notificationCode").removeAttr("value");
        }
    });
  	$('.checkbox :checkbox').trigger("change");
	  
  	$("#profileImg").fileinput({
          removeClass: "btn btn-danger btn-sm mt5",
          removeLabel: "",
          showRemove: false,
          browseLabel: '&nbsp;<spring:message code="browse"/>&nbsp;...',
          browseClass: "btn btn-info btn-sm mt5"
      });
      $("#registrationSecondPhaseFrom").validate({
          debug: true,
          rules: {
              "FName": {
                  "required": true,
                  maxlength: 20
              },
              "MName": {
                  maxlength: 20
              },
              "governmentId": {
                  "required": true,
                  maxlength: 10
              },
              "LName": {
                  "required": true,
                  maxlength: 20
              },
              "businessName": {
                  "required": true,
                  maxlength: 50
              },
              "phoneNumber1": {
                  "required": true,
                  digits: true,
                  maxlength: 10,
                  minlength: 10
                  
              },
              "phoneNumber2": {
              	digits: true,
                  maxlength: 10,
                  minlength: 10
              },
              "bankDetailsBean.bankName": {
                  "required": true,
                  maxlength: 30
              },
              "bankDetailsBean.bankAccountNumber": {
                  "required": true,
                  number: true,
                  maxlength: 16
              },
              "bankDetailsBean.iban": {
                  "required": true,
                  maxlength: 24
              },
              "driverLicenseExpirationDate":{
              	"required": true
              },
              "shippersRegistrationInfoBean.truckModelYear":{
              	"required": true
              },
              "shippersRegistrationInfoBean.truckType":{
              	"required": true
              },
              "emailAddress2": {
              	maxlength: 40,
                  emailpattern: [true, false]
              },
              "profileImg":{
               	 accept: {type:"image", extension : "png|jpg|jpeg"}
              },
             
          },
          messages: {
              "FName": {
                  "required": '<spring:message code="regProfile.validation.firstName.required"/>',
                  "maxlength": '<spring:message code="regProfile.validation.firstName.maxlength"/>'
              },
              "MName": {
                  "maxlength": '<spring:message code="regProfile.validation.middleName.maxlength"/>'
              },
              "LName": {
                  "required": '<spring:message code="regProfile.validation.lastName.required"/>',
                  "maxlength": '<spring:message code="regProfile.validation.lastName.maxlength"/>'
              },
              "businessName": {
                  "required": '<spring:message code="regProfile.validation.companyName.required"/>',
                  "maxlength": '<spring:message code="regProfile.validation.companyName.maxlength"/>'
              },
              "emailAddress2": {
              	"maxlength" : '<spring:message code="regProfile.validation.secondaryEmail.maxlength"/>',
                  "emailpattern": '<spring:message code="regProfile.validation.secondaryEmail.pattern"/>'
              },
              "governmentId": {
                  "required": '<spring:message code="regProfile.validation.govtId.required"/>',
                  "maxlength": '<spring:message code="regProfile.validation.govtId.maxlength"/>'
              },
              "phoneNumber1": {
                  "required": '<spring:message code="regProfile.validation.phoneNo1.required"/>',
                  "minlength": '<spring:message code="regProfile.validation.phoneNo1.minLength"/>',
                  "maxlength":'<spring:message code="regProfile.validation.phoneNo1.maxLength"/>',
                  "digits":'<spring:message code="regProfile.validation.phoneNo1.digits"/>'
                
              },
              "phoneNumber2": {
                  "digits":  '<spring:message code="regProfile.validation.phoneNo2.digits"/>',
                  "minlength": '<spring:message code="regProfile.validation.phoneNo2.minLength"/>',
                  "maxlength": '<spring:message code="regProfile.validation.phoneNo2.maxLength"/>'
              },
              "bankDetailsBean.bankName": {
                  "required": '<spring:message code="regProfile.validation.bankName.required"/>',
                  "maxlength": '<spring:message code="regProfile.validation.bankName.maxlength"/>'
              },
              "bankDetailsBean.bankAccountNumber": {
                  "required": '<spring:message code="regProfile.validation.bankAccount.required"/>'
              },
              "bankDetailsBean.iban": {
                  "required": '<spring:message code="regProfile.validation.iban.required"/>'
              },
              "driverLicenseExpirationDate":{
              	"required":'<spring:message code="regProfile.validation.driverLicenseExpirationDate.required"></spring:message>'
              },
              "shippersRegistrationInfoBean.truckModelYear":{
              	"required":'<spring:message code="regProfile.validation.truckModelYear.required"></spring:message>'
              },
              "shippersRegistrationInfoBean.truckType":{
              	"required":'<spring:message code="regProfile.validation.truckType.required"></spring:message>'
              },
              "profileImg":{
                	 "accept" : '<spring:message code="regProfile.validation.image.accept"/>'
               }
          },
          
          invalidHandler: function(event, validator) {
  
          },
          errorPlacement: function(error, element) {
          	if($(element).attr("type")=="file"){
                error.insertAfter(element.parents(".file-input"));
            }else{
                error.insertAfter(element);
            }
          },
          submitHandler: function(form) {
              form.submit();
          },
          highlight: function(element) {
          	if($(element).attr("type")=="file"){
               	 $(element).parents(".file-input").find(".file-drop-disabled").addClass("error-file-element");
              }else{
               	 $(element).addClass("error-element");
              }
          },
          unhighlight: function(element) {
          	if($(element).attr("type")=="file"){
               	 $(element).parents(".file-input").find(".file-drop-disabled").removeClass("error-file-element");
              }else{
                   $(element).removeClass("error-element");
              }
          }
         
          
      });
  
   /*    $("#phoneNumber1").blur(function (e) {
    	  if (isPhoneNumber1validation === false) {
              $("#phoneNumber1").rules("remove");
          }else{
        	  $("#phoneNumber1").rules("add", {
				  "required": true,
                  digits: true,
                  maxlength: 10,
                  minlength: 10,
                  lessThanEqualTo:0,
                  messages: {
                	  "required": '<spring:message code="regProfile.validation.phoneNo1.required"/>',
                      "minlength": '<spring:message code="regProfile.validation.phoneNo1.minLength"/>',
                      "maxlength":'<spring:message code="regProfile.validation.phoneNo1.maxLength"/>',
                      "digits":'<spring:message code="regProfile.validation.phoneNo1.digits"/>',
                      "lessThanEqualTo":'<spring:message code="otpverification.validation.otp.verifynow"/>'
                  }
			  });
          }
      });
     */
      
      $("#clearAll").on("click", function() {
          $("#registrationSecondPhaseFrom").validate().resetForm();
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
  function myLocation(){
    $.ajax({
     type: "GET",
     async: false,
     cache: false,
     url: (contextPath + "/setting/addlocation"),
     success: function(result) {
       $("#locationModel .modal-content").empty().html(result);
       $("#locationModel").modal({
           show: true,
           backdrop: 'static',
           keyboard: false
       });
     },
     error: function(e) {
      $("#locationModel .modal-content").empty().html(e);
      $("#locationModel").modal({
          show: true,
          backdrop: 'static',
          keyboard: false
      });
     }
   });
   }
  
  
 function  verifyNowOtp(){
	  $("#verifyNumberMsg").html(""); 
	  $('#accessPhoneNumber').html("<spring:message code='otpverification.lbl.msg.accesslimit'/>");

	 var isVerifyPhoneNumber=false;
	  var numbers = /^[0-9]+$/;
	 var phoneNumber =document.getElementById('phoneNumber1').value;
	if (phoneNumber.match(numbers) && phoneNumber.length == 10)
		{
			isVerifyPhoneNumber=true;
		}else{
			isVerifyPhoneNumber=false;
		}
	 if(false !=isVerifyPhoneNumber){
		 $.ajax({
	  	       type: "GET",
	  	       async: false,
	  	     cache: false,
	  	       url: (contextPath + "/setting/otpverification/"+phoneNumber),
	  	       success: function(result) {
	  	    	 result = result.trim();
	  	    	 if(result.indexOf("{\"ERROR\":") > -1){
	  	    		 var data = JSON.parse(result);
	  	    		 $("#verifyNumberMsg").html("&nbsp;&nbsp;"+data.ERROR.trim());
	  	    	 }else{
	  	    		$("#otpModal .modal-body").empty().html(result);
		  	           $("#otpModal").modal({
		  	               show: true,
		  	               backdrop: 'static',
		  	               keyboard: false
		  	           });
	  	    	 }
	  	         isPhoneNumber1validation =false;
	  	       $("#phoneNumber1").blur();
	  	       },
	  	       error: function(e) {
	  	    	$("#verifyNumberMsg").html(e);
	   	       }
	   	   });
	 }else{
		 $("#verifyNumberMsg").html('<spring:message code="regProfile.validation.phoneNo1.required"/>');
	 }
		
  }
  
		 /* function later(){
			 var isVerifyPhoneNumber=false;
			 var phoneNumber =document.getElementById('phoneNumber1').value;
			  var numbers = /^[0-9]+$/;
				if (phoneNumber.match(numbers) && phoneNumber.length == 10)
				{
					isVerifyPhoneNumber=true;
				}else{
					isVerifyPhoneNumber=false;
				}
			 if(isVerifyPhoneNumber ==true){
				 isPhoneNumber1validation =false;
				 $("#phoneNumber1").rules("remove");
					$("#verifyNumberMsg").html(""); 
				  	$("#phoneNumber1").prop("readonly", true);
		     	  	$("#phoneNumber1").blur();
		     	    	
			 }else{
				 $("#verifyNumberMsg").html('<spring:message code="regProfile.validation.phoneNo1.required"/>'); 
			 }
			
		 } */
  
		 /* function editphonenumber(){
			  $("#verifyNow").attr("disabled", false)
  	    	 $("#verifyLater").attr("disabled", false);
			  $("#phoneNumber1").prop("readonly", false);
			  $('#editPhoneNumber').hide();
			  $("#phoneNumber1").rules("add", {
				  "required": true,
                  digits: true,
                  maxlength: 10,
                  minlength: 10,
                  lessThanEqualTo:0,
                  messages: {
                	  "required": '<spring:message code="regProfile.validation.phoneNo1.required"/>',
                      "minlength": '<spring:message code="regProfile.validation.phoneNo1.minLength"/>',
                      "maxlength":'<spring:message code="regProfile.validation.phoneNo1.maxLength"/>',
                      "digits":'<spring:message code="regProfile.validation.phoneNo1.digits"/>',
                      "lessThanEqualTo":'<spring:message code="otpverification.validation.otp.verifynow"/>'
                  }
			  });
		 } */
		 
  function deleteRow(id){
  	if (confirm('<spring:message code="regProfile.alert.deleteMessage"/>')){
  		$("#tr-"+id).remove();
  	}
  }
  
  function checkAccountNumberUnique(){
  /* var primaryEmail = $("#primaryEmail").val();
  if(primaryEmail !=""){
  	$.ajax({
          type: 'GET',
          url:"contextPath/checkPrimaryEmailUnique/"+primaryEmail,
          success: function(result) {
       	   if(result == "Y"){
       		  // $("#primaryEmailUnique").show();
       		  alert("please Enter Unique Primary Email");
       	   }
       	   else{
       		 // $("#primaryEmailUnique").hide();
       	   }
          }
      }); 
  } */
  }
</script>