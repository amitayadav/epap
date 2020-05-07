<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="login_section registration-section">
    <!-- <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a> -->
    <div class="login_wrapper">
        <div class="animate form registration_form">
            <section class="login_content">
                <form:form action="${contextPath}/auth/registrationphase1" method="post" id="registrationForm" modelAttribute="loginDetailsBean"  enctype="multipart/form-data">
                    <h1><spring:message code="registration.lbl.createaccount"/></h1>
                    <c:if test="${not empty errorStack}">
                        <div class="error-stack">
                            <c:forEach items="${errorStack}" var="error">
                                <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
                            </c:forEach>
                        </div>
                    </c:if>          
                    <div class="radio">
                        <c:forEach items="${accountTypeCode}" var="bean" varStatus="status">
                            <form:radiobutton path="accountTypeCodesBean.accountType" class="accountType" id="atype_${bean.accountType}" value="${bean.accountType}" onchange="changeRadio('${bean.accountTypeMeaning}');"/>
                            <label for="atype_${bean.accountType}" class="col-md-6 col-sm-6 col-xs-6" style="font-size:24px;color:beige;font-weight: bolder">
                                <spring:message code="account.type.${bean.accountType}"/>
                                <%-- <c:if test= "${bean.accountTypeMeaning eq 'User'}"><spring:message code="account.type.user"/></c:if>
                                <c:if test="${bean.accountTypeMeaning eq 'Buyer'}"><spring:message code="account.type.buyer"/></c:if>
                                <c:if test="${bean.accountTypeMeaning eq 'Seller'}"><spring:message code="account.type.seller"/></c:if>
                                <c:if test="${bean.accountTypeMeaning eq 'Shipper'}"><spring:message code="account.type.shipper"/></c:if>
                                <c:if test="${bean.accountTypeMeaning eq 'Buyer Agent'}"><spring:message code="account.type.buyerAgent"/></c:if>
                                <c:if test="${bean.accountTypeMeaning eq 'Seller Agent'}"><spring:message code="account.type.sellerAgent"/></c:if>
                                <c:if test="${bean.accountTypeMeaning eq 'Shipper Agent'}"><spring:message code="account.type.shipperAgent"/></c:if> --%>
                            </label>
                        </c:forEach>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                    <div>
                        <div class="input-group">
                            <spring:message code="registration.plh.publicName" var="plhPublicName"></spring:message>
                                <span class="input-group-addon pad1016" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
                                <form:input path="publicName" class="form-control" placeholder="${plhPublicName}" aria-describedby="basic-addon1" onblur="checkPublicNameUnique()" id="publicName" maxlength="30"/>
                        </div>
                    </div>
                    <div id="ownerEmailDiv" style="display: none">
                        <div class="input-group">
                            <spring:message code="registration.plh.ownerEmail" var="ownerEmail"></spring:message>
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-envelope" aria-hidden="true"></i></span>
                                <form:input class="form-control" path="ownerEmail"  id="ownerEmail" placeholder="${ownerEmail}" aria-describedby="basic-addon1" maxlength="40"/>
                        </div>
                    </div>
                    <div>
                        <div class="input-group">
                            <spring:message code="registration.plh.primaryEmail" var="plhPrimaryEmail"></spring:message>
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-envelope" aria-hidden="true"></i></span>
                                <form:input  class="form-control" path="loginUserid" id="primaryEmail" placeholder="${plhPrimaryEmail}" aria-describedby="basic-addon1" onblur="checkPrimaryEmailUnique()" maxlength="40"/>
                        </div>
                    </div>
                    <div>
                        <div class="input-group">
                            <spring:message code="registration.plh.password" var="plhPassword"></spring:message>
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-key" aria-hidden="true"></i></span>
                                <form:password class="form-control" path="password" id="password" placeholder="${plhPassword}" aria-describedby="basic-addon1" maxlength="20"/>
                        </div>
                    </div>
                    <div>
                        <div class="input-group">
                            <spring:message code="registration.plh.confirmPassword" var="plhConfirmPassword"></spring:message>
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-key" aria-hidden="true"></i></span>
                                <input type="password" class="form-control" name="confirmPass" id="confirmPass" placeholder="${plhConfirmPassword}" aria-describedby="basic-addon1" maxlength="20"/>
                        </div>
                    </div>
                    <div class="reset_pass">
                        <!--fahad: increase size of register text-->
                        <button type="submit" class="btn btn-lg submit" style="font-size:24px;" id="saveBtn"><spring:message code="registration.btn.register"/></button>
                        <label style="font-size:22px;"><spring:message code="registration.lbl.alreadymember"/></label>
                            <a href="${contextPath}/auth/login" style="font-size:22px;"><spring:message code="login.btn.login"/></a>
                            <!--<button type="submit" class="btn btn-default submit" id="saveBtn"><spring:message code="registration.btn.register"/></button>-->
                            <!--  <a class="btn btn-default submit" href="index.html">Register</a> -->
                    </div>
                    <div class="clearfix"></div>
                </form:form>
            </section>
        </div>
    </div>
</div>
<script type="text/javascript">
    var isAgentRole = false;
    function changeRadio(userName) {
        if (userName.indexOf("Agent") !== -1) {
            $("#ownerEmailDiv").css("display", "block");
            isAgentRole = true;
        } else {
            $("#ownerEmailDiv").css("display", "none");
            isAgentRole = false;
        }
        $("#registrationForm").validate().resetForm();
    }
    if ($(".accountType").is(":checked")) {
        $(".accountType:checked").trigger("change");
    }
    function checkPublicNameUnique() {
        /* var publicName = $("#publicName").val();
         if(publicName !=""){
         $.ajax({
         type: 'GET',
         url:"${contextPath}/checkPublicNameUnique/"+publicName,
         success: function(result) {
         if(result == true){
         alert("Please Enter Unique Public Name");
         // $("#publicNameUnique").show();
         }
         else{
         //$("#publicNameUnique").hide();
         }
         }
         }); 
         } */

    }
    function checkPrimaryEmailUnique() {
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

    $(document).ready(function () {
        $("#publicName").focus();
        $("#registrationForm").validate({
            debug: true,
            rules: {
                "publicName": {required: true, maxlength: 30},
                "accountTypeCodesBean.accountType": {required: true},
                "ownerEmail": {required: true, emailpattern: true},
                "loginUserid": {required: true, maxlength: 40, emailpattern: true},
                "password": {required: true, maxlength: 20, passwordpattern: true},
                "confirmPass": {required: true, maxlength: 20, equalTo: "#password"}
            },
            //                            fahad: add color and size to error messages
            messages: {
                "publicName": {"required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.publicName.required"/>', "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="validation.publicName.maxLength"/>'},
                "accountTypeCodesBean.accountType": {"required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.accountType.required"/>'},
                "ownerEmail": {"required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.email.required"/>', "emailpattern": '<label style="font-size: 20px; color:orange"><spring:message code="validation.email.emailPattern"/>'},
                "loginUserid": {
                    "required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.email.required"/>',
                    "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="validation.email.maxLength"/>',
                    "emailpattern": '<label style="font-size: 20px; color:orange"> <spring:message code="validation.email.emailPattern"/>'
                },
                "password": {
                    "required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.password.required"/>',
                    "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="validation.password.maxLength"/>',
                    "passwordpattern": '<label style="font-size: 20px; color:orange"><spring:message code="validation.password.passwordPattern"/>'},
                "confirmPass": {"required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.confirmPassword.required"/>', "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="validation.confirmPassword.maxLength"/>', equalTo: '<label style="font-size: 20px; color:orange"><spring:message code="validation.pwdAndConfPwd.match"/>'}
            },
            invalidHandler: function (event, validator) {

            },
            errorPlacement: function (error, element) {
                if ($(element).is(":radio")) {
                    error.insertAfter(element.parent(".radio"));
                } else {
                    error.insertAfter(element.parent(".input-group"));
                }
            },
            submitHandler: function (form) {
                form.submit();
                $(form).find("input[type=submit]").attr("disabled", true);
            },
            highlight: function (element) {
                $(element).addClass("error-element");
            },
            unhighlight: function (element) {
                $(element).removeClass("error-element");
            }
        });
    });
</script>