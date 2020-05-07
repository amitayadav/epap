<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="login_section">
  <div class="login_wrapper">
    <div class="animate form login_form">
      <section class="login_content">
        <form:form action="${contextPath}/auth/resetpassword" method="post" id="loginForm" modelAttribute="loginDetailsBean" enctype="multipart/form-data">
          <form:hidden path="accountStatusCodesBean.accountStatusCode"/>
          <form:hidden path="accountTypeCodesBean.accountType"/>
          <form:hidden path="accountProfileBean.accountId"/>
          
          <!-- Placeholder Internationalization -->
          <spring:message code="login.plh.emailaddress" var="loginUseridPlh"/>
          <spring:message code="resetpassword.plh.govermentid" var="governmentIdPlh"/>
          <spring:message code="resetpassword.plh.publicname" var="publicNamePlh"/>
          <spring:message code="resetpassword.plh.newpassword" var="passwordPlh"/>
          <spring:message code="resetpassword.plh.confirmpassword" var="confirmPassPlh"/>
          
          <h1>
            <spring:message code="resetpassword.h1.resetpassword"/>
          </h1>
          <div>
            <div class="input-group">
              <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
              <form:input class="form-control" path="loginUserid" id="loginUserid" placeholder="${loginUseridPlh}" aria-describedby="basic-addon1" maxlength="30" />
            </div>
          </div>
          <c:choose>
            <c:when test="${ not empty accountId}">
              <div>
                <div class="input-group">
                  <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
                  <form:input class="form-control" path="accountProfileBean.governmentId" id="governmentId" placeholder="${governmentIdPlh}" aria-describedby="basic-addon1" maxlength="30" />
                </div>
              </div>
            </c:when>
            <c:otherwise>
              <div>
                <div class="input-group">
                  <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
                  <form:input class="form-control" path="publicName" id="publicName" placeholder="${publicNamePlh}" aria-describedby="basic-addon1" maxlength="30" />
                </div>
              </div>
            </c:otherwise>
          </c:choose>
          <div>
            <div class="input-group">
              <span class="input-group-addon" id="basic-addon1"><i class="fa fa-key" aria-hidden="true"></i></span>
              <form:password class="form-control" path="password" id="password" placeholder="${passwordPlh}" aria-describedby="basic-addon1" maxlength="20" />
            </div>
          </div>
          <div>
            <div class="input-group">
              <span class="input-group-addon" id="basic-addon1"><i class="fa fa-key" aria-hidden="true"></i></span>
              <input type="password" class="form-control" name="confirmPass" id="confirmPass" placeholder="${confirmPassPlh}" aria-describedby="basic-addon1" />
            </div>
          </div>
          <div>
            <div class="clearfix"></div>
            <input type="submit" class="btn btn-default submit" value='<spring:message code="resetpassword.btn.reset"/>'/>
          </div>
          <div class="clearfix"></div>
        </form:form>
      </section>
    </div>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {

        $("#loginForm").validate({
            debug: true,
            rules: {
                "loginUserid": {
                    required: true,
                    maxlength: 30,
                    emailpattern: true
                },
                "password": {
                    required: true,
                    maxlength: 20,
                    passwordpattern: true
                },
                "accountProfileBean.governmentId": {
                    required: true,
                    maxlength: 30
                },
                "publicName": {
                    required: true,
                    maxlength: 30
                },
                "confirmPass": {
                    required: true,
                    maxlength: 20,
                    equalTo: "#password"
                }
            },
            messages: {
                "loginUserid": {
                    "required": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.loginUserid.required"/>',
                    "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.loginUserid.maxlength"/>',
                    "emailpattern": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.loginUserid.emailpattern"/>'
                },
                "password": {
                    "required": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.password.required"/>',
                    "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.password.maxlength"/>',
                    "passwordpattern": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.password.passwordpattern"/>'},
                  "confirmPass": {
                        "required": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.confirmPass.required"/>',
                        "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.confirmPass.maxlength"/>',
                        equalTo: '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.confirmPass.equalTo"/>'
                },
                "accountProfileBean.governmentId": {
                    "required": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.governmentId.required"/>',
                    "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.governmentId.maxlength"/>'
                },
                "publicName": {
                    "required": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.publicName.required"/>',
                    "maxlength": '<label style="font-size: 20px; color:orange"><spring:message code="resetpassword.validation.publicName.maxlength"/>'
                }
            },
            invalidHandler: function(event, validator) {

            },
            errorPlacement: function(error, element) {
                error.insertAfter(element.parent(".input-group"));
            },
            submitHandler: function(form) {
                form.submit();
                $(form).find("input[type=submit]").attr("disabled",true);
            },
            highlight: function(element) {
                $(element).parents(".input-group").addClass("error-element");
            },
            unhighlight: function(element) {
                $(element).parents(".input-group").removeClass("error-element");
            }
        });
    });
</script>