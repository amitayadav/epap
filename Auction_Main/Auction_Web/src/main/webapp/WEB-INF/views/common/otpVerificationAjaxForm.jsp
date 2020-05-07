<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:choose>
	<c:when test="${not empty otpCount or not empty remainingMiunte}">
		{"ERROR":"${otpCount} ${remainingMiunte}"}
	</c:when>
	<c:otherwise>
		<div class="row" >
		    <div class="col-md-12 col-sm-12 col-xs-12">
		        <div class="x_panel">
		            <div class="form-row">
		                <h1> <spring:message code="otpverification.lbl.msg"></spring:message></h1>
		                    <label>
		                    <spring:message code="otpverification.lbl.otpmsg"></spring:message>
		                    </label>
		                </div>
		
		                <div class="form-group">
		                    <label class="col-sm-2 col-md-2 control-label nolpad">
		                        <span class="required"> * </span>
		                    <spring:message code="otpverification.lbl.otp"></spring:message>
		                    </label>
		                    <div class="col-sm-10 col-md-10 nopad">
		                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    <input type="hidden" name="accountId" id="accountId" value="${AccountId}"/>
		                    <input type="text" name="mobileOtp"  id="mobileOtp" class="form-control " placeholder="<spring:message code="otpverification.lbl.placeholder"></spring:message>"   onkeypress="return valueIsNumber(this, event);" onpaste="return false;"   maxlength="4"/>
		                    </div>
		                    <div class="clearfix"></div>
		                </div>
		                <div class="form-group">
		                    <label class="col-sm-2 col-md-2 control-label nolpad"><spring:message code="otpverification.lbl.resend"></spring:message></label>
		                    <label class="col-sm-10 col-md-10 nopad padt8"  id="timer"></label> 
		                    <input type="submit" name="resendotp" id="resendotp" value="<spring:message code='otpverification.lbl.resend'></spring:message>"  class="btn btn-warning btn-lg text-hightlight"/>
		                    <div class="clearfix">
		                        &nbsp;&nbsp;&nbsp;&nbsp;
		                        <label class="error hidden" id="otpError"> </label>
		                        <label  class="error" id="otpCount">
		                        ${otpCount}
		                    </label>
		                    <label  class="error" id="remainingTime">
		                        ${remainingMiunte}
		                    </label>
		                    <div class="clearfix">
		                     <label  class ="text-primary " id="resendMsg"> </label>
		                     </div>
		                </div>
		            </div>
		            <div class="text-right">
		                <button type="submit" class="btn btn-success btn-lg button-left text-hightlight" id="verifyOtp"> <i class="fa fa-check"></i>&nbsp;&nbsp;
		                    <spring:message code="btn.save" /> 
		                </button>
		            </div>
		
		        </div>
		    </div>
		</div>
		
		<!-- /page content -->
		<script type="text/javascript">
		    $("#resendotp").attr("disabled", true);
		    var timerOn = true;
		    $(document).ready(function () {
		        timer(60);
		    });
		
		    $('#verifyOtp').on('click', function () {
		        var mobileOtp = document.getElementById("mobileOtp").value;
		        $("#otpError").addClass("hidden");
		        $("#otpError").html("");
		        $("#resendMsg").html("");
		        if ("" !== mobileOtp) {
		            $.ajax({
		                type: "POST",
		                async: false,
		                cache: false,
		                data: {
		                    mobileOtp: mobileOtp
		                },
		                url: (contextPath + "/setting/validateOtp"),
		                success: function (result) {
		                    if ("" === result) {
		                    	$("#verifyNumberMsg").html("");
		                    	$("#resendMsg").html("");
		                        $("#phoneNumber1").rules("remove");
		                        /* $("#verifyNow").attr("disabled", true); */
		                         $("#verifyNow").hide();
		                         $('#verified').show();
		                         $("#accessPhoneNumber").html("");
		                        $("#verifyLater").attr("disabled", true);
		                        $("#phoneNumber1").prop("readonly", true);
		                        $("#otpModal").modal("hide");
		                        document.getElementById("isverifyNumber").value = "1";
		                        $("#phoneNumber1").blur();
		                        $("#verified").blur();
		                        
		                    } else {
		                    	if(result == "invalid"){
		                    		  $("#otpError").removeClass("hidden");
		                             $("#otpError").html('<spring:message code="otpverification.validation.otp.invalid"/>'); 
		                              document.getElementById("mobileOtp").value = "";
		                    	}else if(result == "otpCount"){
		                    		$("#otpError").removeClass("hidden");
			                         $("#verifyNumberMsg").html('<spring:message code="otpverification.validation.otp.countotp"/>'); 
			                        document.getElementById("mobileOtp").value = "";
			                        $("#otpModal").modal("hide");
			                        $("#phoneNumber1").rules("remove");
			                        iseditPhonevalidation=false;
			                        $("#phoneNumber1").blur();
		                    	}
		                    	
		                    	else{
		                    		  $("#otpError").removeClass("hidden");
		                    		  $("#verifyNumberMsg").html('<spring:message code="otpverification.validation.otp.wrongnumber"/>');
		                              document.getElementById("mobileOtp").value = "";
		                              $("#otpModal").modal("hide");
		                    	}
		                      
		                    }
		
		                },
		                error: function (e) {
		                    console.log(e);
		                    $("#otpModal").modal("hide");
		                }
		            });
		        } else {
		            $("#otpError").removeClass("hidden");
		            $("#otpError").html('<spring:message code="otpverification.validation.otpmsg.require"/>');
		        }
		    });
		
		    $('#resendotp').on('click', function () {
		      	$("#resendotp").attr("disabled", true);
		        $("#otpError").addClass("hidden");
		        $("#otpCount").addClass("hidden");
		        $("#remainingTime").addClass("hidden");
		        $("#resendMsg").html('<spring:message code="otpverification.lbl.newOTPSent"/>');
		        $("#otpError").html("");
		        $("#otpCount").html("");
		        $("#remainingTime").html("");
		        
		        $.ajax({
		            type: "POST",
		            async: false,
		            cache: false,
		            data: {},
		            url: (contextPath + "/setting/reSendOtp"),
		            success: function (result) {
		            	if(result == "sendOtp"){
		            		   $("#otpError").removeClass("hidden");
		            		   timer(60);
		                      /*  $("#otpError").html(result); */
		            	}else{
		            		if(result == "countotp"){
		            			 $("#resendMsg").html("");
		            			 $("#otpError").removeClass("hidden");
		                         $("#verifyNumberMsg").html('<spring:message code="otpverification.validation.otp.countotp"/>');
		                         $("#otpModal").modal("hide");
		                         $("#phoneNumber1").rules("remove");
			                        iseditPhonevalidation=false;
			                        $("#phoneNumber1").blur();
		            		}else{
		            			$("#resendMsg").html("");
		           			 	$("#otpError").removeClass("hidden");
		           			   $("#verifyNumberMsg").html('<spring:message code="otpverification.validation.otp.after"/>'+result+'<spring:message code="otpverification.validation.otp.minutes"/>')
		           			  $("#otpModal").modal("hide");
		           			 $("#phoneNumber1").rules("remove");
		                        iseditPhonevalidation=false;
		                        $("#phoneNumber1").blur();
		            		}
		            	}
		             
		            },
		            error: function (e) {
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
		            setTimeout(function () {
		                timer(remaining);
		            }, 1000);
		            return;
		        }
		
		        if (!timerOn) {
		            return;
		        }
		        $("#resendotp").attr("disabled", false);
		        $("#resendMsg").html("");
		    }
		</script>
	</c:otherwise>
</c:choose>