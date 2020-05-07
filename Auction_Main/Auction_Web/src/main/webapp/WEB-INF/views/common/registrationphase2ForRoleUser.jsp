<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.auction.commons.enums.ENUM_NotificationCodes"%>
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
            <form:form action="${contextPath}/setting/registeraccountprofile" method="post" modelAttribute="AccountProfileBean" id="registrationSecondPhaseFrom" enctype="multipart/form-data">
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
                  <b>
                    <spring:message code="regProfile.b.enterNameAppearsOnOfficialDocument"/>
                  </b>
                </div>
                <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.publicName"/>
                  </label>
                  <input type="text" class="form-control" placeholder="${publicNamephl}" maxlength="20" value="${publicName}" disabled="disabled" />
                </div>
                <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.primaryAddress"/>
                  </label>
                  <input type="text" class="form-control" placeholder="${primaryAddressphl}" maxlength="40" value="${emailAddress}" disabled="disabled" />
                </div>
                <%-- <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.firstName"/>
                  </label>
                  <form:input class="form-control" path="FName" id="FName" placeholder="${firstNamephl}" maxlength="20" />
                </div>
                <div class="form-group">
                  <label>
                    <spring:message code="regProfile.lbl.middleName"/>
                  </label>
                  <form:input class="form-control" path="MName" id="MName" placeholder="${middleNamephl}" maxlength="20" />
                </div>
                <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.lastName"/>
                  </label>
                  <form:input class="form-control" path="LName" id="LName" placeholder="${lastNamephl}" maxlength="20" />
                </div> --%>
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
              <%-- <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterNameYourBusinessAppearsOfficialDocument"/>
                  </b>
                </div>
                <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.companyName"/>
                  </label>
                  <form:input class="form-control" path="businessName" id="businessName" placeholder="${companyNamephl}" maxlength="20" />
                </div>
              </div> --%>
              <%-- <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterSecondaryEmail"/>
                  </b>
                </div>
                <div class="form-group">
                  <label>
                    <spring:message code="regProfile.lbl.secondaryAddress"/>
                    (
                    <small>
                      <spring:message code="regProfile.lbl.secondaryAddress.small"/>
                    </small>
                    )
                  </label>
                  <form:input class="form-control" path="emailAddress2" id="emailAddress2" placeholder="${secondaryAddressphl}" maxlength="30" />
                </div>
              </div> --%>
              <%-- <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterYourNationalIdNumberOrIqamaNo"/>
                  </b>
                </div>
                <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.nationalId"/>
                  </label>
                  <form:input class="form-control" path="governmentId" id="governmentId" placeholder="${nationalIdphl}" maxlength="10" />
                </div>
              </div> --%>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterPhoneNumber"/>
                  </b>
                </div>
                <div class="form-group">
                  <span class="required"> * </span>
                  <label>
                    <spring:message code="regProfile.lbl.phoneNumber1"/>
                  </label>
                  <form:input class="form-control" path="phoneNumber1" id="phoneNumber1" placeholder="${phoneNumber1phl}" maxlength="10" />
                </div>
                <%-- <div class="form-group">
                  <label>
                    <spring:message code="regProfile.lbl.phoneNumber2"/>
                  </label>
                  <form:input class="form-control" path="phoneNumber2" id="phoneNumber2" placeholder="${phoneNumber2phl}" maxlength="10" />
                </div>--%>
              </div> 
              <%-- <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.enterContactDetails"/>
                  </b>
                </div>
                <div class="form-group">
                  <label>
                    <spring:message code="regProfile.lbl.postalAddress"/>
                  </label>
                  <form:input class="form-control" path="postalAddress" id="postalAddress" placeholder="${postalAddressphl}" maxlength="50" />
                </div>
                <div class="form-group">
                  <label>
                    <spring:message code="regProfile.lbl.contactUs"/>
                  </label>
                  <form:input class="form-control" path="contactUs" id="contactUs" placeholder="${contactUsphl}" maxlength="100" />
                </div>
              </div> --%>
              <div class="x_panel">
                <div class="x_title font-weight-bol">
                  <b>
                    <spring:message code="regProfile.b.selectLanguage"/>
                  </b>
                </div>
                <div class="form-group">
                  <label>
                    <spring:message code="regProfile.lbl.preferredLanguage"/>
                  </label>
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
                <hr class="hr"/>
                <div class="form-group ">
                  <label>
                    <spring:message code="regProfile.lbl.notiMethod"/>
                  </label>
                  <div class="checkbox checkbox-warning">
                    
                    <form:hidden path="notificationCodesBean.notificationCode" id="notificationCode" />
                    <input type="checkbox" name="notificationCode_sms" id="sms" />
                    <label for="sms"><spring:message code="regProfile.lbl.sms"/></label>
                    
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    
                    <input type="checkbox" name="notificationCode_email" id="email" />
                    <label for="email"><spring:message code="regProfile.lbl.email"/></label>
                    
                  </div>
                </div>
              </div>
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
<script type="text/javascript">

  var NC_SMS_ONLY =" ${ENUM_NotificationCodes.SMS_ONLY.getStatus()}";
  var NC_EMAIL_ONLY = "${ENUM_NotificationCodes.EMAIL_ONLY.getStatus()}";
  var NC_BOTH = "${ENUM_NotificationCodes.BOTH.getStatus()}";
 
  $(document).ready(function() {
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
              /*"FName": {
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
                  maxlength: 20
              },
              */
              "phoneNumber1": {
                  "required": true,
                  digits: true,
                  maxlength: 10,
                  minlength: 10
              },/*
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
              "emailAddress2": {
              	maxlength: 30,
                  emailpattern: [true, false]
              },*/
              "profileImg":{
               	 accept: {type:"image", extension : "png|jpg|jpeg"}
              }
          },
          messages: {
              /*"FName": {
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
              },*/
              "phoneNumber1": {
                  "required": '<spring:message code="regProfile.validation.phoneNo1.required"/>',
                  "minlength": '<spring:message code="regProfile.validation.phoneNo1.minLength"/>',
                  "maxlength":'<spring:message code="regProfile.validation.phoneNo1.maxLength"/>',
                  "digits":'<spring:message code="regProfile.validation.phoneNo1.digits"/>'
              },/*
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
              },*/
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
                error.insertAfter(element.parent(".form-group"));
            }
          },
          submitHandler: function(form) {
        	  form.submit();
              $(form).find("input[type=submit]").attr("disabled",true);
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
  
      $("#clearAll").on("click", function() {
          $("#registrationSecondPhaseFrom").validate().resetForm();
      }); 
      
  });
</script>