<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="login_section">
  <div class="login_wrapper">
    <div class="animate form login_form">
      <section class="login_content">
        <form action="${contextPath}/auth/forgotpassword" method="post" id="forgotPasswordForm" enctype="multipart/form-data">
          <h1>
            <spring:message code="forgotpassword.btn.forgotpassword"/>
          </h1>
          <c:if test="${not empty errorStack}">
            <div class="error-stack">
              <c:forEach items="${errorStack}" var="error">
                <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
              </c:forEach>
            </div>
          </c:if>
          <div class="input-group">
            <spring:message code="login.plh.emailaddress" var="plhemailaddress"/>
            <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
            <input type="text" class="form-control" name="emailAddress" id="emailAddress" placeholder="${plhemailaddress}" aria-describedby="basic-addon1"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          </div>
          <div class="clearfix"></div>
          <input type="submit" class="btn btn-default submit" value='<spring:message code="submit"/>'/>
          <div class="reset_pass"><spring:message code="registration.lbl.alreadymember"/>
          	<a href="${contextPath}/auth/login"><spring:message code="login.btn.login"/></a>
          </div>
          <div class="clearfix"></div>
        </form>
      </section>
    </div>
  </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
  $("#emailAddress").focus();
  $("#forgotPasswordForm").validate({
    debug: true,
    rules: {
      "emailAddress": {
        "required": true,
        emailpattern: true
      },
    },
    messages: {
      "emailAddress": {
        "required": '<label style="font-size: 20px; color:orange"><spring:message code="validation.email.required"/>',
        "emailpattern": '<label style="font-size: 20px; color:orange"><spring:message code="validation.email.emailPattern"/>'
      },
    },
    invalidHandler: function(event, validator) {},
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