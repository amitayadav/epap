<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.auction.commons.enums.ENUM_NotificationCodes"%>
<style>
    #profileImg-error{padding-top: 10px;clear: both;}
    .file-input{max-width: 195px;}
</style>
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="editProfile.h3.title"/></li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3><spring:message code="editProfile.h3.title"/></h3>
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
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                <!--<p class="text-danger"><spring:message code="required.msg" /></p>-->
                    <%-- <div class="x-title">
                      <p class="text-danger"><spring:message code="required.msg" /></p>
                      <h2><spring:message code="editProfile.h2.editProfileDetail" /></h2>
                      <div class="clearfix"></div>
                    </div> --%>
                    <div class="x_content">
                        <c:if test="${not empty errorStack}">
                            <div class="error-stack">
                                <c:forEach items="${errorStack}" var="error">
                                    <p class="error"> <i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error} </p>
                                </c:forEach>
                            </div>
                        </c:if>
                        <form:form action="${contextPath}/setting/saveEditProfile" method="post" modelAttribute="accountProfileBean" id="editProfileFrom" enctype="multipart/form-data">
                            <div class="x_panel">
                                <!--                <div class="x_title font-weight-bol">
                                                  <b><spring:message code="editProfile.b.updateNameAppearsOnOfficialDocument"/></b>
                                                </div>-->
                                <div class="form-group">
                                    <span class="required"> * </span> <label><spring:message code="editProfile.lbl.publicName" /></label>
                                    <div class="read-only nopad">
                                        <input type="text" class="form-control" placeholder='<spring:message code="editProfile.lbl.publicName"/>' maxlength="30" value="${publicName}" disabled="disabled" /> 
                                    </div>                  
                                </div>
                                <div class="form-group">
                                    <span class="required"> * </span>
                                    <label><spring:message code="editProfile.lbl.primaryAddress" /></label>
                                                                        <div class="read-only nopad">

                                    <input type="text" class="form-control" placeholder="Primary Address" maxlength="40" value="${emailAddress}" disabled="disabled" /> 
                                </div>
                                </div>
                                <%-- <div class="form-group">
                                  <span class="required"> * </span>
                                  <label><spring:message code="editProfile.lbl.firstName" /></label>
                                  <form:input class="form-control" path="FName" id="FName" placeholder="First Name" maxlength="20" disabled="true" />
                                </div>
                                <div class="form-group">
                                  <label><spring:message code="editProfile.lbl.middleName" /></label>
                                  <form:input class="form-control" path="MName" id="MName" placeholder="Middle Name" maxlength="20" disabled="true" />
                                </div>
                                <div class="form-group">
                                  <span class="required"> * </span>
                                  <label><spring:message code="editProfile.lbl.lastName" /></label>
                                  <form:input class="form-control" path="LName" id="LName" placeholder="Last Name" maxlength="20" disabled="true" />
                                </div> --%>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.lbl.uploadphoto" /></b>
                                </div>
                                <div class="form-group user-profile-picture-upload editable-field">
                                    <c:set var="imgPath" value="${contextPath}/resources/images/user1-128x128.jpg" />
                                    <form:hidden path="profileImage" />
                                    <c:if test="${not empty accountProfileBean && not empty accountProfileBean.profileImage}">
                                        <img src="${contextPath}/setting/profilePictures/${accountProfileBean.accountId}/${accountProfileBean.profileImage}" class="user-profile-picture"/>
                                    </c:if>
                                    <input id="profileImg" data-min-file-count="0" data-show-caption="false" type="file" name="profilePicture"/> 
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <%-- <div class="x_panel">
                              <div class="x_title font-weight-bol">
                                <b><spring:message code="editProfile.b.updateNameYourBusinessAppearsOfficialDocument"/></b>
                              </div>
                              <div class="form-group">
                                <span class="required"> * </span>
                                <label><spring:message code="editProfile.lbl.companyName" /></label>
                                <form:input class="form-control" path="businessName" id="businessName" placeholder="Company Name" maxlength="20" disabled="true" />
                              </div>
                            </div> --%>
                            <%-- <div class="x_panel">
                              <div class="x_title font-weight-bol">
                                <b><spring:message code="editProfile.b.updateSecondary.email" /></b>
                              </div>
                              <div class="form-group">
                                <spring:message code="editProfile.lbl.secondaryAddress" var="secondaryAddress" />
                                <label>
                                  <spring:message code="editProfile.lbl.secondaryAddress" />
                                  ( <small><spring:message code="editProfile.lbl.secondaryAddress.small" /></small>) 
                                </label>
                                <form:input class="form-control" path="emailAddress2" id="emailAddress2" placeholder="${secondaryAddress}" maxlength="30" />
                              </div>
                            </div> --%>
                            <%-- <div class="x_panel">
                              <div class="x_title font-weight-bol">
                                <b><spring:message code="editProfile.b.updateYourNationalIdNumberOrIqamaNo" /></b>
                              </div>
                              <div class="form-group">
                                <span class="required"> * </span>
                                <label><spring:message code="editProfile.lbl.nationalId" /></label>
                                <form:input class="form-control" path="governmentId" id="governmentId" placeholder="National Id" maxlength="10" disabled="true" />
                              </div>
                            </div> --%>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updatePhoneNumber" /></b>
                                </div>
                                <div class="form-group">
                                    <spring:message code="editProfile.lbl.phoneNumber1" var="phoneNumber1" />
                                    <span class="required"> * </span>
                                    <label><spring:message code="editProfile.lbl.phoneNumber1" /></label>
                                    <form:input class="form-control editable-field" path="phoneNumber1" id="phoneNumber1" placeholder="${phoneNumber1}" maxlength="10" />
                                </div>
                                <%--<div class="form-group">
                                  <spring:message code="editProfile.lbl.phoneNumber2" var="phoneNumber2" />
                                  <label><spring:message code="editProfile.lbl.phoneNumber2" /></label>
                                  <form:input class="form-control" path="phoneNumber2" id="phoneNumber2" placeholder="${phoneNumber2}" maxlength="10" />
                                </div> --%>
                            </div>
                            <%-- <div class="x_panel">
                              <div class="x_title font-weight-bol">
                                <b><spring:message code="editProfile.b.updateContactDetails"/></b>
                              </div>
                              <div class="form-group">
                                <label><spring:message code="editProfile.lbl.postalAddress" /></label>
                                <form:input class="form-control" path="postalAddress" id="postalAddress" placeholder="Postal Address" maxlength="50" disabled="true" />
                              </div>
                              <div class="form-group">
                                <spring:message code="editProfile.lbl.contactUs" var="contactUs" />
                                <label><spring:message code="editProfile.lbl.contactUs" /></label>
                                <form:input class="form-control" path="contactUs" id="contactUs" placeholder="${contactUs}" maxlength="100" />
                              </div>
                            </div> --%>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.selectLanguage" /></b>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="editProfile.lbl.preferredLanguage" /></label>
                                    <div class="radio">
                                        <form:radiobutton path="preferredLanguage" class="accountType" id="lag_en" value="en" />
                                        <label for="lag_en"><spring:message code="language.english" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <form:radiobutton path="preferredLanguage" class="accountType" id="lag_ar" value="ar" />
                                        <label for="lag_ar"><spring:message code="language.arabic" /></label>
                                    </div>
                                </div>
                                <hr class="hr" />
                                <div class="form-group">
                                    <label><spring:message code="editProfile.lbl.notiMethod" /></label>
                                    <c:choose>
                                        <c:when test="${accountProfileBean.notificationCodesBean.notificationCode eq ENUM_NotificationCodes.SMS_ONLY.getStatus()}">
                                            <c:set var="sms" value="checked"></c:set>
                                        </c:when>
                                        <c:when test="${accountProfileBean.notificationCodesBean.notificationCode eq ENUM_NotificationCodes.EMAIL_ONLY.getStatus()}">
                                            <c:set var="email" value="checked"></c:set>
                                        </c:when>
                                        <c:when test="${accountProfileBean.notificationCodesBean.notificationCode eq ENUM_NotificationCodes.BOTH.getStatus()}">
                                            <c:set var="email" value="checked"></c:set>
                                            <c:set var="sms" value="checked"></c:set>
                                        </c:when>
                                    </c:choose>
                                    <div class="form-group">
                                        <div class="checkbox checkbox-warning">
                                            <form:hidden path="notificationCodesBean.notificationCode" id="notificationCode" />
                                            <input type="checkbox" name="notificationCode_sms" id="sms" ${sms} />
                                            <label for="sms"><spring:message code="editProfile.lbl.sms" /></label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="checkbox" name="notificationCode_email" id="email" ${email} />
                                            <label for="email"><spring:message code="editProfile.lbl.email" /></label>
                                        </div>
                                    </div>
                                    <%-- <div class="form-group">
                                      <label><spring:message code="editProfile.lbl.landingPage" /></label>
                                      <form:select path="landingPagesBean.landingPageId" class="form-control">
                                        <form:option value="0">
                                          <spring:message code="editProfile.option.none" />
                                        </form:option>
                                        <c:forEach items="${landingPagesBean}" var="page">
                                          <form:option value="${page.landingPageId}">${page.landingPageName}</form:option>
                                        </c:forEach>
                                      </form:select>
                                    </div> --%>
                                    <form:hidden path="accountId" />
                                </div>
                            </div>
                            <div class="form-group text-right">
                                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="<spring:message code="editProfile.btn.saveProfile"/>"/>
                                <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value="<spring:message code="editProfile.btn.reset"/>" formnovalidate="formnovalidate" /> 
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var NC_SMS_ONLY = " ${ENUM_NotificationCodes.SMS_ONLY.getStatus()}";
    var NC_EMAIL_ONLY = "${ENUM_NotificationCodes.EMAIL_ONLY.getStatus()}";
    var NC_BOTH = "${ENUM_NotificationCodes.BOTH.getStatus()}";
    $(document).ready(function () {
        $(".checkbox :checkbox").change(function () {
            if ($("#sms").is(":checked") && $("#email").is(":checked")) {
                $("#notificationCode").val(NC_BOTH);
            } else if ($("#sms").is(':checked')) {
                $("#notificationCode").val(NC_SMS_ONLY);
            } else if ($("#email").is(':checked')) {
                $("#notificationCode").val(NC_EMAIL_ONLY);
            } else {
                $("#notificationCode").removeAttr("value");
            }
        });
        $("#profileImg").fileinput({
            removeClass: "btn btn-danger btn-sm mt5",
            removeLabel: "",
            showRemove: false,
            browseLabel: '&nbsp;<spring:message code="browse"/>&nbsp;...',
            browseClass: "btn btn-info btn-sm mt5"
        });
        $("#editProfileFrom").validate({
            debug: true,
            rules: {
                /*"FName": {
                 "required": true,
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
                 },*/
                "phoneNumber1": {
                    "required": true,
                    number: true,
                    maxlength: 10,
                    minlength: 10
                },
                /*"phoneNumber2": {
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
                 emailpattern: [true, false],
                 maxlength: 30
                 },
                 "contactUs": {
                 maxlength: 100
                 },*/
                "profileImg": {
                    accept: {
                        type: "image",
                        extension: "png|jpg|jpeg"
                    }
                }
            },
            messages: {
                /*"FName": {
                 "required": '<spring:message code="editProfile.validation.firstName.required"/>'
                 },
                 "governmentId": {
                 "required": '<spring:message code="editProfile.validation.govtId.required"/>'
                 },
                 "LName": {
                 "required": '<spring:message code="editProfile.validation.lastName.required"/>'
                 },
                 "businessName": {
                 "required": '<spring:message code="editProfile.validation.companyName.required"/>'
                 },*/
                "phoneNumber1": {
                    "required": '<spring:message code="editProfile.validation.phoneNo1.required"/>',
                    "minlength": '<spring:message code="editProfile.validation.phoneNo1.minLength"/>',
                    "maxlength": '<spring:message code="editProfile.validation.phoneNo1.maxLength"/>'
                },
                /*"phoneNumber2": {
                 "digits": '<spring:message code="editProfile.validation.phoneNo2.digits"/>',
                 "minlength": '<spring:message code="editProfile.validation.phoneNo2.minLength"/>',
                 "maxlength": '<spring:message code="editProfile.validation.phoneNo2.maxLength"/>'
                 },
                 "bankDetailsBean.bankName": {
                 "required": '<spring:message code="editProfile.validation.bankName.required"></spring:message>',
                 "minlength": '<spring:message code="editProfile.validation.bankName.minLength"></spring:message>',
                 },
                 "bankDetailsBean.bankAccountNumber": {
                 "required": '<spring:message code="editProfile.validation.bankAccount.required"></spring:message>',
                 "minlength": '<spring:message code="editProfile.validation.bankAccount.minLength"></spring:message>',
                 },
                 "bankDetailsBean.iban": {
                 "required": '<spring:message code="editProfile.validation.iban.required"></spring:message>',
                 "minlength": '<spring:message code="editProfile.validation.iban.minLength"></spring:message>',
                 },
                 "emailAddress2": {
                 "emailpattern": '<spring:message code="editProfile.validation.secondaryEmail.pattern"></spring:message>'
                 },
                 "contactUs": {
                 "minlength": '<spring:message code="editProfile.validation.contactUs.minLength"></spring:message>',
                 },*/
                "profileImg": {
                    "accept": '<spring:message code="editProfile.validation.image.accept"></spring:message>'
                }
            },
            /* invalidHandler: function(event, validator) {
             
             }, */
            errorPlacement: function (error, element) {
                if ($(element).attr("type") == "file") {
                    error.insertAfter(element.parents(".file-input"));
                } else {
                    error.insertAfter(element.parent(".form-group"));
                }
            },
            /*  submitHandler: function(form) {
             form.submit();
             $(form).find("input[type=submit]").attr("disabled",true);
             }, */
            highlight: function (element) {
                if ($(element).attr("type") == "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").addClass("error-file-element");
                } else {
                    $(element).addClass("error-element");
                }
            },
            unhighlight: function (element) {
                if ($(element).attr("type") == "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").removeClass("error-file-element");
                } else {
                    $(element).removeClass("error-element");
                }
            }
        });
        $("#clearAll").on("click", function () {
            $("#editProfileFrom").validate().resetForm();
        });
    });
</script>