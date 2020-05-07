<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="login_section">
	<div class="login_wrapper">
		<div class="animate form login_form">
			<h1 style="color: white;">
				<spring:message code="otpverification.lbl.msg"></spring:message>
			</h1>
			<section class="login_content">
				<h5 style="color: white;">
					<spring:message code="otpverification.lbl.otpmsg"></spring:message>
				</h5>
				<form action="${contextPath}/setting/loginTimeValidateOtp"
					name="loginOtpBox" id="loginOtpBox" method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />


					<div class="input-group">
						<input type="text" name="mobileOtp" style="width: 90%"
							id="mobileOtp" class="form-control" placeholder="Enter the OTP"
							onkeypress="return valueIsNumber(this,event);"
							onpaste="return false;" maxlength="4" /> 
							<span class="input-group-btn">
							<button type="submit" style="margin-left: 2px; margin-top: 7px; width: 90%" class="btn btn-default submit btn-lg button-left text-hightlight" id="verifyOtp">
								<i class="fa fa-check"></i>&nbsp;&nbsp;
								<spring:message code="submit" />
							</button>
						</span>
					</div>


					<div class="clearfix">
						&nbsp;&nbsp;&nbsp;&nbsp; <label class="error hidden" id="otpError">
						</label> <label class="error" id="otpCount"> ${otpCount} </label> <label
							class="error" id="remainingTime"> ${remainingMiunte} </label>
					</div>

				</form>
				<div class="clearfix error">
					<label class="text-hightlight " id="resendMsg"> </label>
				</div>


				<div class="input-group">
					<label id="timer"> </label> <input type="submit" name="resendotp"
						id="resendotp"
						value="<spring:message code='otpverification.lbl.resend'></spring:message>"
						class="btn btn-warning btn-lg text-hightlight" /> <span
						class="text-right"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.cancel"/>' id="loginOtpBoxCancel"
						 name="loginOtpBoxCancel"></i>&nbsp;&nbsp;
					</span>
				</div>
			</section>
		</div>
	</div>
</div>
<script type="text/javascript">
$("#resendotp").attr("disabled", true);
var timerOn = true;
			$(document).ready(function() {
			  timer(60);
			  $("#loginOtpBox").validate({
			    debug: true,
			    rules: {
			      "mobileOtp": {
			        "required": true,
			      },
			    },
			    messages: {
			      "mobileOtp": {
			        "required": '<spring:message code="otpverification.validation.otpmsg.require"/>',
			      },
			    },
			    invalidHandler: function(
			      event, validator) {},
			    errorPlacement: function(
			      error, element) {
			      error.insertAfter(
			        element.parent(
			          ".input-group"));
			    },
			    submitHandler: function(
			      form) {
			      form.submit();
			      $(form).find(
			        "input[type=submit]"
			      ).attr("disabled",
			        true);
			    },
			    highlight: function(element) {
			      $(element).parents(
			          ".input-group")
			        /* .addClass(
			        		"error-element"); */
			    },
			    unhighlight: function(
			      element) {
			      $(element).parents(
			        ".input-group").removeClass(
			        "error-element");
			    }
			  });
			});
				$('#resendotp').on('click', function() {
				  $("#resendotp").attr("disabled",
				    true);
				  $("#otpError").addClass("hidden");
				  $("#otpCount").addClass("hidden");
				  $("#remainingTime").addClass(
				    "hidden");
				  $("#otpError").html("");
				  $("#otpCount").html("");
				  $("#remainingTime").html("");
				  $("#resendMsg").html(
				    '<spring:message code="otpverification.lbl.newOTPSent"/>'
				  );
				  $.ajax({
				    type: "POST",
				    async: false,
				    cache: false,
				    data: {},
				    url: (contextPath +
				      "/setting/reSendOtpLoginTime"
				    ),
				    success: function(result) {
				      timer(60);
				      $("#otpError").removeClass(
				        "hidden");
				      $("#otpError").html(
				        result);
				    },
				    error: function(e) {
				      console.log(e);
				    }
				  });
				});
				$('#loginOtpBoxCancel').on('click',
				  function() {
				    $.ajax({
				      type: "GET",
				      async: false,
				      cache: false,
				      data: {},
				      url: (contextPath +
				        "/auth/logout"),
				      success: function(result) {
				        window.location.href =
				          (contextPath +
				            "/auth/login");
				      },
				      error: function(e) {
				        console.log(e);
				      }
				    });
				  });
				
				function timer(remaining) {
				  var m = Math.floor(remaining / 60);
				  var s = remaining % 60;
				  m = m < 10 ? '0' + m : m;
				  s = s < 10 ? '0' + s : s;
				  //document.getElementById('timer').innerHTML = m + ':' + s;
				  remaining -= 1;
				  if (remaining >= 0 && timerOn) {
				    setTimeout(function() {
				      timer(remaining);
				    }, 1000);
				    return;
				  }
				  if (!timerOn) {
				    return;
				  }
				  $("#resendotp").attr("disabled",
				    false);
				  $("#resendMsg").html("");
				}
</script>