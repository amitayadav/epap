<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="com.auction.commons.enums.ENUM_NotificationCodes"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountTypeCodes"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountStatusCodes"%>

<c:set var="TYPE_BUYER" value="${ENUM_AccountTypeCodes.BUYER.getType()}"/>
<c:set var="TYPE_SELLER" value="${ENUM_AccountTypeCodes.SELLER.getType()}"/>
<c:set var="TYPE_SHIPPER" value="${ENUM_AccountTypeCodes.SHIPPER.getType()}"/>
<c:set var="TYPE_SHIPPER_AGENT" value="${ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}"/>
<c:set var="CURRENT_TYPE" value="${loginUser.accountTypeCodesBean.accountType}"/>

<style>
    #profileImg-error{padding-top: 10px;clear: both;}
    .file-input{max-width: 195px;}
</style>

<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="editProfile.h3.title"/></li>
        <li class="todaydatetime">
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
            <span id="current-time">
                <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
            </span>
        </li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3> <spring:message code="editProfile.h3.title"/> </h3>
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
                    <p class="text-danger">
                        <spring:message code="required.msg" />
                    </p>
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
                        <c:if test="${not empty ownerLoginDetailsBean}">
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.ownerdetailsheader" /></b>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="editProfile.lbl.ownerinfo" /></label>
                                    <p class="read-only"><b>${ownerLoginDetailsBean.accountProfileBean.fullName}</b> (${ownerLoginDetailsBean.publicName})</p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </c:if>
                        <form:form action="${contextPath}/setting/saveEditProfile" class="form-horizontal" method="post" modelAttribute="accountProfileBean" id="editProfileFrom" enctype="multipart/form-data">
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updateNameAppearsOnOfficialDocument"/></b>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span><spring:message code="editProfile.lbl.publicName" /></label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${publicName}</p>
                                    </div> 
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.firstName" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${accountProfileBean.FName}</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.middleName" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${accountProfileBean.MName}</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.lastName" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${accountProfileBean.LName}</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.lbl.uploadphoto" /></b>
                                </div>
                                <div class="form-group user-profile-picture-upload">
                                    <c:set var="imgPath" value="${contextPath}/resources/images/user1-128x128.jpg" />
                                    <form:hidden path="profileImage" />
                                    <c:if test="${not empty accountProfileBean && not empty accountProfileBean.profileImage}">
                                        <img src="${contextPath}/setting/profilePictures/${accountProfileBean.accountId}/${accountProfileBean.profileImage}" class="user-profile-picture"/>
                                    </c:if>
                                    <input id="profileImg" data-min-file-count="0" data-show-caption="false" type="file" name="profilePicture"/> 
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updateNameYourBusinessAppearsOfficialDocument"/></b>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.companyName"/>
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${accountProfileBean.businessName}</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updateSecondary.email" /></b>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.primaryAddress"/>
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${emailAddress}</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <spring:message code="editProfile.lbl.secondaryAddress" var="secondaryAddress" />
                                    <small><strong>(<spring:message code="editProfile.lbl.secondaryAddress.small"/>)</strong></small>
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        ${secondaryAddress}
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <form:input class="form-control editable-field" path="emailAddress2" id="emailAddress2" placeholder="${secondaryAddress}" maxlength="40" />
                                    </div>
                                    <div class="clearfix"></div>
                                </div>

                                <%-- <div class="form-group">
                                  
                                  <label>
                                    ${secondaryAddress}
                                    (
                                    <small><spring:message code="editProfile.lbl.secondaryAddress.small"/></small>
                                    ) 
                                  </label>
                                  <form:input class="form-control" path="emailAddress2" id="emailAddress2" placeholder="${secondaryAddress}" maxlength="40" />
                                </div> --%>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updateYourNationalIdNumberOrIqamaNo" /></b>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.nationalId"/>
                                    </label>
                                    <!--fahad: fix governmentId access-->                       
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <%--<form:input class="form-control read-only" path="governmentId" id="governmentId" placeholder="National Id" maxlength="10" disabled="true" />--%>
                                        <p class="read-only">${accountProfileBean.governmentId}</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updatePhoneNumber"/></b>
                                </div>
                                <div class="form-group">
                                    <spring:message code="editProfile.lbl.phoneNumber1" var="phoneNumber1" />
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.phoneNumber1"/>
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <input type="hidden" name="phoneNumber" id="phoneNumber" value="${accountProfileBean.phoneNumber1}"/>
                                        <input type="hidden" name="isverifyNumber" id="isverifyNumber" value="0"/>
                                        <form:input class="form-control editable-field" path="phoneNumber1" id="phoneNumber1" placeholder="${phoneNumber1}" maxlength="10" />
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-2 col-md-2 control-label nolpad">
                                    </div>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <input type="button" class="btn btn-verify btn-lg button-left text-hightlight" id="verifyNow"  name ="verifyNow"  value="<spring:message code='otpverification.btn.verifynow'/>" onclick="verifyNowOtp()" ></input>
                                        <input type="button" class="btn btn-verify btn-lg button-left text-hightlight"  value="<spring:message code='otpverification.btn.verified'/>"  id="verified" name="verified" disabled="disabled"></input>
                                   <label  id="verifyNumberMsg" style="color: red;"></label>
                                      
                                    </div>
                                   
                                    
                                </div>

                                <div class="form-group">
                                    <spring:message code="editProfile.lbl.phoneNumber2" var="phoneNumber2" />
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <spring:message code="editProfile.lbl.phoneNumber2" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <form:input class="form-control editable-field" path="phoneNumber2" id="phoneNumber2" placeholder="${phoneNumber2}" maxlength="10" />
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.updateContactDetails"/></b>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <spring:message code="editProfile.lbl.postalAddress" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <p class="read-only">${accountProfileBean.postalAddress}</p>
                                    </div>     
                                    <!--fahad: fix postal address issue-->
                                    <!--                                        <form:input class="form-control read-only" path="postalAddress" id="postalAddress" placeholder="${postalAddress}" maxlength="50" disabled="true" />
                                                                        </div>-->
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <spring:message code="editProfile.lbl.contactUs" var="contactUs" />
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <spring:message code="editProfile.lbl.contactUs" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <form:input class="form-control editable-field" path="contactUs" id="contactUs" placeholder="${contactUs}" maxlength="100" />
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="x_panel">
                                <div class="x_title font-weight-bol">
                                    <b><spring:message code="editProfile.b.selectLanguage"/></b>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <span class="required"> * </span>
                                        <spring:message code="editProfile.lbl.preferredLanguage" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <div class="radio">
                                            <form:radiobutton path="preferredLanguage" class="accountType" id="lag_en" value="en" />
                                            <label for="lag_en">
                                                <spring:message code="language.english" />
                                            </label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <form:radiobutton path="preferredLanguage" class="accountType" id="lag_ar" value="ar" />
                                            <label for="lag_ar">
                                                <spring:message code="language.arabic" />
                                            </label>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <hr class="hr"/>
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <spring:message code="editProfile.lbl.notiMethod" />
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
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
                                        <div class="checkbox checkbox-warning">
                                            <form:hidden path="notificationCodesBean.notificationCode" id="notificationCode" />
                                            <input type="checkbox" name="notificationCode_sms" id="sms" ${sms} />
                                            <label for="sms">
                                                <spring:message code="editProfile.lbl.sms" />
                                            </label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="checkbox" name="notificationCode_email" id="email" ${email} />
                                            <label for="email">
                                                <spring:message code="editProfile.lbl.email" />
                                            </label>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <hr class="hr" />
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label nolpad">
                                        <spring:message code="editProfile.lbl.landingPage"/>
                                    </label>
                                    <div class="col-sm-10 col-md-10 nopad">
                                        <form:select path="landingPagesBean.landingPageId" class="form-control editable-field">
                                            <form:option value="0"><spring:message code="editProfile.option.none" /></form:option>
                                            <c:forEach items="${landingPagesBean}" var="page">
                                                <form:option value="${page.landingPageId}">${page.landingPageName}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                    <form:hidden path="accountId" id="accountProfileId"/>
                                    <div class="clearfix"></div>
                                </div>
                            </div>

                            <%-- <c:if test="${Agent eq false}"> --%>
                            <c:if test="${CURRENT_TYPE eq TYPE_BUYER or CURRENT_TYPE eq TYPE_SELLER or CURRENT_TYPE eq TYPE_SHIPPER}">
                                <div id="bankDetails">
                                    <div class="x_panel">
                                        <div class="x_title font-weight-bol">
                                            <b><spring:message code="editProfile.b.updateBankInfo" /></b>
                                        </div>
                                        <form:hidden path="bankDetailsBean.accountId" />
                                        <form:hidden path="bankDetailsBean.availableBalance" />
                                        <form:hidden path="bankDetailsBean.blockedAmount" />
                                        <form:hidden path="bankDetailsBean.advanceBalance"/>
                                        <form:hidden path="bankDetailsBean.cashposition"/>
                                        <div class="form-group">
                                            <spring:message code="editProfile.lbl.bankName" var="bankName" />
                                            <label class="col-sm-2 col-md-2 control-label nolpad">
                                                <span class="required"> * </span>
                                                <spring:message code="editProfile.lbl.bankName" />
                                            </label>
                                            <div class="col-sm-10 col-md-10 nopad">
                                                <form:input class="form-control editable-field" path="bankDetailsBean.bankName" id="bankName" placeholder="${bankName}" maxlength="30" />
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="form-group">
                                            <spring:message code="editProfile.lbl.accountNumber" var="accountNumber" />
                                            <label class="col-sm-2 col-md-2 control-label nolpad" >
                                                <span class="required"> * </span>
                                                <spring:message code="editProfile.lbl.accountNumber" />
                                            </label>
                                            <div class="col-sm-10 col-md-10 nopad">
                                                <form:input class="form-control editable-field" path="bankDetailsBean.bankAccountNumber" id="bankAccountNumber" placeholder="${accountNumber}" maxlength="16" />
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 col-md-2 control-label nolpad">
                                                <span class="required"> * </span>
                                                <spring:message code="editProfile.lbl.iban" />
                                            </label>
                                            <div class="col-sm-10 col-md-10 nopad">
                                                <form:input class="form-control editable-field" path="bankDetailsBean.iban" id="iban" placeholder="IBAN" maxlength="24" />
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
                                            <b><spring:message code="editProfile.b.driverLicense" /></b>
                                        </div>
                                        <form:hidden path="shippersRegistrationInfoBean.accountId"/>
                                        <div class="form-group">
                                            <spring:message code="editProfile.lbl.driverLicenseExpirationDate" var="driverLicenseExpirationDatePhl"/>
                                            <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${driverLicenseExpirationDatePhl}</label>
                                            <div class="col-sm-10 col-md-10 nopad">
                                                <p class="read-only"><fmt:formatDate pattern='yyyy-MM' value="${accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate}"/></p>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>
                                                <spring:message code="editProfile.lbl.truckModelYear" var="truckModelYear"/>
                                                ${truckModelYear}
                                            </label>
                                            <div class="col-sm-10 col-md-10 nopad">
                                                <p class="read-only">${accountProfileBean.shippersRegistrationInfoBean.truckModelYear}</p>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 col-md-2 control-label nolpad">
                                                <span class="required"> * </span><spring:message code="editProfile.lbl.truckType"/>
                                            </label>
                                            <div class="col-sm-10 col-md-10 nopad">
                                                <div class="radio">
                                                    <form:radiobutton path="shippersRegistrationInfoBean.truckType" class="accountType" id="truckSmall" value="small" disabled="true"/>
                                                    <label for="truckSmall">Small</label>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <form:radiobutton path="shippersRegistrationInfoBean.truckType" class="accountType" id="truckLarge" value="large" disabled="true"/>
                                                    <label for="truckLarge">Large</label>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${CURRENT_TYPE eq TYPE_BUYER or CURRENT_TYPE eq TYPE_SELLER or CURRENT_TYPE eq TYPE_SHIPPER or CURRENT_TYPE eq TYPE_SHIPPER_AGENT}">
                                <div class="x_panel">
                                    <div class="x_title font-weight-bol">
                                        <b><spring:message code="editProfile.b.updateYourLocation" /></b>
                                    </div>
                                    <div class="form-group">
                                        <!--fahad: change size of add location button--> 
                                      <!--<input type="button" class="btn btn-info btn-block btn-lg" value='<spring:message code="editProfile.btn.addLocation"/>' onclick="myLocation()">--> 
                                        <input type="button" style="height:50px; width:200px" class="btn btn-info btn-block btn-lg" value='<spring:message code="editProfile.btn.addLocation"/>' onclick="myLocation()">
                                    </div>
                                    <div class="table-responsive">
                                        <table id="locationtable" class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th><spring:message code="editProfile.th.locationName.edit"/></th>
                                                    <th width="80" class="text-right"><spring:message code="editProfile.th.latitude"/></th>
                                                    <th width="80" class="text-right"><spring:message code="editProfile.th.longitude"/></th>
                                                    <th width="125"><spring:message code="editProfile.th.status"/></th>
                                                    <th width="40"><spring:message code="editProfile.th.delete"/></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${accountLocationsList}" var="location" varStatus="count">
                                                    <tr id="tr-location-${location.locationId}">
                                                        <td>
                                                            <form:hidden path="newAccountLocation[${count.index}].locationId" id="locationIdHidden" value="${location.locationId}"/>
                                                            <form:input class="form-control editable-field" path="newAccountLocation[${count.index}].locationName" id="locationName" value="${location.locationName}" maxlength="50"/>
                                                        </td>
                                                        <td class="text-right">
                                                            ${location.latitude}
                                                            <form:hidden path="newAccountLocation[${count.index }].latitude" id="latitudeHidden" value="${location.latitude}"/>
                                                        </td>
                                                        <td class="text-right">
                                                            ${location.longitude}
                                                            <form:hidden path="newAccountLocation[${count.index}].longitude" id="longitudeHidden" value="${location.longitude}"/>
                                                        </td>
                                                        <td class="text-center">
                                                            <form:select class="form-control editable-field" path="newAccountLocation[${count.index}].status">
                                                                <c:forEach items="${accountLocationStatusList}" var="locationStatus">
                                                                    <c:choose>
                                                                        <c:when test="${location.status eq locationStatus.key}">
                                                                            <form:option value="${locationStatus.key}" selected="true"><spring:message code="account.location.status.${locationStatus.key}"/></form:option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <form:option value="${locationStatus.key}"><spring:message code="account.location.status.${locationStatus.key}"/></form:option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </form:select>
                                                        </td>
                                                        <td class="text-center pad3">
                                                            <button type="button" onclick="locationDelete(${location.locationId})" class="btn btn-danger btn-xs" data-toggle="tooltip" data-original-title='<spring:message code="editProfile.th.delete"/>'>
                                                                <i class="fa fa-remove"></i>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group text-right">
                                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight button-left" value="<spring:message code="editProfile.btn.saveProfile"/>"/>
                               <%--  <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value="<spring:message code="editProfile.btn.reset"/>" formnovalidate="formnovalidate" />  --%>
                             <input type="button" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.cancel"/>' id="editProfileCancel"  name="editProfileCancel"/>
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
    var isPhoneNumber1validation = true;
    var iseditPhonevalidation=true;
    var NC_SMS_ONLY = " ${ENUM_NotificationCodes.SMS_ONLY.getStatus()}";
    var NC_EMAIL_ONLY = "${ENUM_NotificationCodes.EMAIL_ONLY.getStatus()}";
    var NC_BOTH = "${ENUM_NotificationCodes.BOTH.getStatus()}";
    var PENDING_PHONE_NUMBER = "${ENUM_AccountStatusCodes.PENDING_PHONE_NUMBER.getStatus()}";
    var userAccountStatus =${userAccountStatus};
    function myLocation(locationId) {
        if (undefined === locationId) {
            locationId = null;
        }
        $.ajax({
            type: "GET",
            async: false,
            cache: false,
            url: (contextPath + "/setting/addlocation"),
            data: {locationId: locationId},
            success: function (result) {
                $("#locationModel .modal-content").empty().html(result);
                $("#locationModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            },
            error: function (e) {
                $("#locationModel .modal-content").empty().html(e);
                $("#locationModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            }
        });
    }

    function deleteNewLocationRow(id) {
        if (confirm('<spring:message code="editProfile.alert.deleteMessage"></spring:message>')) {
            $("#tr-" + id).remove();
        }
    }

    function locationDelete(id) {
        if (confirm('<spring:message code="editProfile.alert.deleteMessage"></spring:message>')) {
            $.ajax({
                type: "GET",
                async: false,
                cache: false,
                url: (contextPath + "/setting/deletelocation/" + id),
                success: function (result) {
                    $("#tr-location-" + id).remove();
                },
                error: function (e) {}
            });
        }
    }

   
     $("#phoneNumber2").blur(function (e){
    	 $("#phoneNumber1").blur();
     });
     
    $("#phoneNumber1").blur(function (e) {
        e.preventDefault();
        var isverifyNumber = $("#isverifyNumber").val();
        if (isverifyNumber === "0") {
            var newPhoneNumer = $("#phoneNumber1").val();
            var oldPhoneNumber = $("#phoneNumber").val();
        } else {
            var newPhoneNumer = $("#phoneNumber1").val();
            var oldPhoneNumber = $("#phoneNumber1").val();
        }
        var numbers = /^[0-9]+$/;
        if (newPhoneNumer === oldPhoneNumber) {
            if (userAccountStatus !== Number(PENDING_PHONE_NUMBER)) {
            	   $("#verifyNow").hide();
                  $('#verified').show(); 
            	$("#verifyNow").attr("disabled", true);
                $("#verifyNumberMsg").html("");
                if (isPhoneNumber1validation === false) {
                	isPhoneNumber1validation = true;
                    $("#phoneNumber1").rules("remove");
                    $("#phoneNumber2").blur();
                }
               
            }else{
            	 if (newPhoneNumer === oldPhoneNumber){
            	 }else{
            		 $("#verifyNow").attr("disabled", false);
            		 $("#verifyNow").show(); 
                     $('#verified').hide();
            	 }
            	
            }
        } else {
        	if(iseditPhonevalidation){
        	    if (newPhoneNumer.match(numbers) && newPhoneNumer.length === 10) {
                    isPhoneNumber1validation = false;
                    $("#verifyNow").attr("disabled", false);
                   $("#verifyNow").show(); 
                    $('#verified').hide(); 
                    $("#phoneNumber1").rules("add", {
                        "required": true,
                        digits: true,
                        maxlength: 10,
                        minlength: 10,
                        lessThanEqualTo: 0,
                        messages: {
                            "required": '<spring:message code="regProfile.validation.phoneNo1.required"/>',
                            "minlength": '<spring:message code="regProfile.validation.phoneNo1.minLength"/>',
                            "maxlength": '<spring:message code="regProfile.validation.phoneNo1.maxLength"/>',
                            "digits": '<spring:message code="regProfile.validation.phoneNo1.digits"/>',
                            "lessThanEqualTo": '<spring:message code="otpverification.validation.msg.otp.verify"/>'
                        }
                    });
                }  else {
                	if(iseditPhonevalidation){
                	    $("#verifyNow").attr("disabled", true);
                        $("#verifyNumberMsg").html("");
                        isPhoneNumber1validation = false;
                        $("#phoneNumber1").rules("add", {
                            "required": true,
                            digits: true,
                            maxlength: 10,
                            minlength: 10,
                            lessThanEqualTo: 0,
                            messages: {
                                "required": '<spring:message code="regProfile.validation.phoneNo1.required"/>',
                                "minlength": '<spring:message code="regProfile.validation.phoneNo1.minLength"/>',
                                "maxlength": '<spring:message code="regProfile.validation.phoneNo1.maxLength"/>',
                                "digits": '<spring:message code="regProfile.validation.phoneNo1.digits"/>',
                                "lessThanEqualTo": '<spring:message code="otpverification.validation.msg.otp.verify"/>'
                            }
                        });
                	}else{
                		if(!iseditPhonevalidation){
                			 $("#phoneNumber1").rules("remove");
                		}
                		 
                	}
        	}
       
            
            }
        }
        iseditPhonevalidation=true;
    });
    $("#phoneNumber1").blur();
    function  verifyNowOtp() {
        var phoneNumber = $("#phoneNumber1").val();
        if (phoneNumber !== "") {
            $.ajax({
                type: "GET",
                async: false,
                cache: false,
                url: (contextPath + "/setting/otpverification/" + phoneNumber),
                success: function (result) {
   	  	    	 result = result.trim();
   	  	    	 if(result.indexOf("{\"ERROR\":") > -1){
   	  	    		$("#phoneNumber1").rules("remove");
   	  	    		iseditPhonevalidation =false;
   	  	    		 var data = JSON.parse(result);
   	  	    		 $("#verifyNumberMsg").html("&nbsp;&nbsp;"+data.ERROR.trim());
   	  	    	 $("#phoneNumber1").blur();
   	  	    	 }else{
   	  	    	$("#otpModal .modal-body").empty().html(result);
                $("#otpModal").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
   	  	    	 }
                	
                	
                },
                error: function (e) {
                    $("#otpModal .modal-body").empty().html(e);
                    $("#otpModal").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                }
            });
        } else {
            $("#verifyNumberMsg").html('<spring:message code="editProfile.validation.phoneNo1.required"/>');
        }
    }



    $(document).ready(function () {
        if (Number(PENDING_PHONE_NUMBER) === userAccountStatus) {
        	$("#verifyNow").attr("disabled", false);
        	 $("#verifyNow").show();
            $('#verified').hide(); 
        } else {
        	$("#verifyNow").attr("disabled", true);
        	 $("#verifyNow").hide();
            $('#verified').show();
        }
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
            browseClass: "btn btn-info btn-lg mt5"
        });
        $("#editProfileFrom").validate({
            debug: true,
            rules: {
                "FName": {
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
                    maxlength: 50
                },
                "phoneNumber1": {
                    "required": true,
                    number: true,
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
                "driverLicenseExpirationDate": {
                    "required": true
                },
                "shippersRegistrationInfoBean.truckModelYear": {
                    "required": true
                },
                "shippersRegistrationInfoBean.truckType": {
                    "required": true
                },
                "emailAddress2": {
                    emailpattern: [true, false],
                    maxlength: 40
                },
                "contactUs": {
                    maxlength: 100
                },
                "profileImg": {
                    accept: {
                        type: "image",
                        extension: "png|jpg|jpeg"
                    }
                }
            },
            messages: {
                "FName": {
                    "required": '<spring:message code="editProfile.validation.firstName.required"/>'
                },
                "governmentId": {
                    "required": '<spring:message code="editProfile.validation.govtId.required"/>'
                },
                "LName": {
                    "required": '<spring:message code="editProfile.validation.lastName.required"/>'
                },
                "businessName": {
                    "required": '<spring:message code="editProfile.validation.companyName.required"/>',
                    "maxlength": '<spring:message code="editProfile.validation.companyName.maxlength"/>'
                },
                "phoneNumber1": {
                    "required": '<spring:message code="editProfile.validation.phoneNo1.required"/>',
                    "minlength": '<spring:message code="editProfile.validation.phoneNo1.minLength"/>',
                    "maxlength": '<spring:message code="editProfile.validation.phoneNo1.maxLength"/>'
                           
                },
                "phoneNumber2": {
                    "digits": '<spring:message code="editProfile.validation.phoneNo2.digits"/>',
                    "minlength": '<spring:message code="editProfile.validation.phoneNo2.minLength"/>',
                    "maxlength": '<spring:message code="editProfile.validation.phoneNo2.maxLength"/>'
                },
                "bankDetailsBean.bankName": {
                    "required": '<spring:message code="editProfile.validation.bankName.required"></spring:message>',
                    "minlength": '<spring:message code="editProfile.validation.bankName.minLength"></spring:message>'
                },
                "bankDetailsBean.bankAccountNumber": {
                    "required": '<spring:message code="editProfile.validation.bankAccount.required"></spring:message>',
                    "minlength": '<spring:message code="editProfile.validation.bankAccount.minLength"></spring:message>'
                },
                "bankDetailsBean.iban": {
                    "required": '<spring:message code="editProfile.validation.iban.required"></spring:message>',
                    "minlength": '<spring:message code="editProfile.validation.iban.minLength"></spring:message>'
                },
                "driverLicenseExpirationDate": {
                    "required": '<spring:message code="editProfile.validation.driverLicenseExpirationDate.required"></spring:message>'
                },
                "shippersRegistrationInfoBean.truckModelYear": {
                    "required": '<spring:message code="editProfile.validation.truckModelYear.required"></spring:message>'
                },
                "shippersRegistrationInfoBean.truckType": {
                    "required": '<spring:message code="editProfile.validation.truckType.required"></spring:message>'
                },
                "emailAddress2": {
                    "emailpattern": '<spring:message code="editProfile.validation.secondaryEmail.pattern"></spring:message>'
                },
                "contactUs": {
                    "minlength": '<spring:message code="editProfile.validation.contactUs.minLength"></spring:message>'
                },
                "profileImg": {
                    "accept": '<spring:message code="editProfile.validation.image.accept"></spring:message>'
                }
            },
            /* invalidHandler: function(event, validator) {
             
             }, */
            errorPlacement: function (error, element) {
                if ($(element).attr("type") === "file") {
                    error.insertAfter(element.parents(".file-input"));
                } else {
                    error.insertAfter(element);
                }
            },
            submitHandler: function (form) {
                form.submit();
                $(form).find("input[type=submit]").attr("disabled", true);
            },
            highlight: function (element) {
                if ($(element).attr("type") === "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").addClass("error-file-element");
                } else {
                    $(element).addClass("error-element");
                }
            },
            unhighlight: function (element) {
                if ($(element).attr("type") === "file") {
                    $(element).parents(".file-input").find(".file-drop-disabled").removeClass("error-file-element");
                } else {
                    $(element).removeClass("error-element");
                }
            }
        });
        $("#clearAll").on("click", function () {
            $("#editProfileFrom").validate().resetForm();
        });

        $("#editProfileCancel").on("click", function () {
        	  window.location.href = (contextPath + "/setting/dashboard");
        });


        /*$("#driverLicenseExpirationDate").daterangepicker({
         locale: {format: "YYYY-MM-DD"},
         singleDatePicker: true,
         minView: 1,
         autoclose: true,
         minDate: new Date().toISOString().substring(0, 10),
         startDate: (${not empty accountProfileBean.shippersRegistrationInfoBean && not empty accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate} ? ("<fmt:formatDate pattern='yyyy-MM-dd' value="${accountProfileBean.shippersRegistrationInfoBean.driverLicenseExpirationDate}"/>") : (new Date()))
         });*/

        /*$("#truckModelYear").daterangepicker({
         locale: {format: "YYYY"},
         singleDatePicker: true,
         minView: 1,
         autoclose: true,
         viewMode: "years",
         minViewMode: "years",
         changeMonth: false,
         changeYear: true
         });*/

    });
</script>