<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="login_section">
  <div class="login_wrapper">
    <div class="animate form login_form">
      <section class="login_content">
        <form:form action="${contextPath}/auth/loginme" method="post" id="loginForm" modelAttribute="loginDetailsBean" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <h1><spring:message code="login.lbl.login"/></h1>
          <c:if test="${not empty errorStack}">
            <div class="error-stack">
              <c:forEach items="${errorStack}" var="error">
                <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
              </c:forEach>
            </div>
               <c:if test="${not empty SUCCESS}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text"><i class="icon fa fa-check"></i> ${SUCCESS}</h4>
            </div>
        </c:if>
          </c:if>     
          <p class="change_link"><spring:message code="login.lbl.newhere"/> 
            <a href="${contextPath}/auth/registration" class="to_register"><spring:message code="registration.btn.register" /></a>
          </p>
          <div>
            <div class="input-group">
	          <spring:message code="login.plh.emailaddress" var="plhemailaddress"/>
              <span class="input-group-addon pad1016" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
              <form:input class="form-control" path="loginUserid" id="loginUserid" placeholder="${plhemailaddress}" aria-describedby="basic-addon1"/>
            </div>
          </div>
          <div>
            <div class="input-group">
            <spring:message code="login.plh.password" var="plhPassword"/>
              <span class="input-group-addon" id="basic-addon1"><i class="fa fa-key" aria-hidden="true"></i></span>
              <form:password class="form-control" path="password" placeholder="${plhPassword}" aria-describedby="basic-addon1"/>
            </div>
          </div>
          <div>
            <div class="clearfix"></div>
            <input type="submit" id="formSubmit" class="btn btn-default submit" value='<spring:message code="login.btn.login"/>'/>
            <a class="reset_pass" href="${contextPath}/auth/forgotpassword"><spring:message code="login.lbl.forgotpassword"/></a>
          </div>
          <div class="clearfix"></div>
        </form:form>
      </section>
    </div>
  </div>
</div>
<script type="text/javascript">
	$("#loginUserid").focus();
	$("#loginForm").validate({
		rules: {
			"loginUserid" : {"required": true, emailpattern: true},
			"password" : {"required": true },
		},
		messages:{
			"loginUserid" : {
				"required" : 	'<spring:message code="validation.email.required"/>',
				"emailpattern": '<spring:message code="validation.email.emailPattern"/>'
			},
			"password" : {
				"required": '<spring:message code="validation.password.required"/>',
			}
		},
		invalidHandler: function(event, validator) {
			
		},
		errorPlacement: function (error, element) {
  			error.insertAfter(element.parent(".input-group"));   
  		},
		submitHandler: function(form) {
			form.submit();
			$(form).find("input[type=submit]").attr("disabled",true);
		},
		highlight : function(element) {
			$(element).parents(".input-group").addClass("error-element");
		},
		unhighlight : function(element) {
			$(element).parents(".input-group").removeClass("error-element");
		}
	});

</script>