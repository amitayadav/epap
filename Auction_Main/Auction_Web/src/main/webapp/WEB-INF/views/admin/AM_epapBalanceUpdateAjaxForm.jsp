<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<form action="${contextPath}/super/updateEpapBalance" method="POST" class="form-horizontal" id="epapBalanceUpdateFrom" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  <p class="text-danger"><spring:message code="required.msg"/></p>

  <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
    <spring:message code="userbalancelist.th.bankname"/>
    </label>
    <div class="col-sm-9 col-md-9 nopad">   
    <p class="read-only">${bankDetails.bankName}</p>
  </div>
  <div class="clearfix"></div>
  </div>

  <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
    <spring:message code="userbalancelist.th.accountnumber"/>
    </label>
    <div class="col-sm-9 col-md-9 nopad">   
    <p class="read-only">${bankDetails.bankAccountNumber}</p>
  </div>
  <div class="clearfix"></div>
  </div>

  <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
    <spring:message code="userbalancelist.th.iban"/>
    </label>
    <div class="col-sm-9 col-md-9 nopad">   
    <p class="read-only">${bankDetails.iban}</p>
  </div>
  <div class="clearfix"></div>
  </div>

    <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
    <spring:message code="userbalanceupdateajaxform.lbl.availablebalance"/>
    </label>
    <div class="col-sm-9 col-md-9 nopad">   
    <input type="hidden" name="loginUserid" value="${loginUserid}"/>
    <p class="read-only"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${bankDetails.availableBalance}"/></p>
  </div>
  <div class="clearfix"></div>
  </div>
  
  <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
	<spring:message code="userbalanceupdateajaxform.lbl.advanceBalance"/> 
    </label>
    <div class="col-sm-9 col-md-9 nopad">   
    <input type="hidden" name="loginUserid" value="${loginUserid}"/>
    <p class="read-only"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${bankDetails.advanceBalance}"/></p>
  </div>
  <div class="clearfix"></div>
  </div>
  
   <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
	<spring:message code="userbalanceupdateajaxform.lbl.cashPosition"/>
    </label>
    <div class="col-sm-9 col-md-9 nopad">   
    <input type="hidden" name="loginUserid" value="${loginUserid}"/>
    <p class="read-only"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${bankDetails.cashposition}"/></p>
  </div>
  <div class="clearfix"></div>
  </div>
  
  <div class="form-group">
    <label class="col-sm-3 col-md-3 control-label nolpad">
      <span class="required"> * </span>
      <spring:message code="userbalanceupdateajaxform.lbl.balanceoperation"/>
    </label>
    <div class="col-sm-9 col-md-9 nopad">  
    <div class="radio">
      <input type="radio" name="balanceType" class="balanceType" id="credit" value="C"/>
      <label for="credit">
      <spring:message code="userbalanceupdateajaxform.lbl.credit"/>
      </label>
      &nbsp;&nbsp;&nbsp;&nbsp; 
      <input type="radio" name="balanceType" class="balanceType" id="debit" value="D"/>
      <label for="debit"><spring:message code="userbalanceupdateajaxform.lbl.debit"/></label>
    </div>
    </div>
    <div class="clearfix"></div>
      </div>
  <div class="form-group">
  <spring:message code="userbalanceupdateajaxform.lbl.amount" var="balancePhl"/>
    <label class="col-sm-3 col-md-3 control-label nolpad">
    <span class="required"> * </span>
     ${balancePhl}
    </label>
    <div class="col-sm-9 col-md-9 nopad">       
    <input type="text"  class="form-control" name="balance" id="balance" placeholder="${balancePhl}" onkeypress="return valueIsPrice(this,event);" onPaste="return false;" maxLength="9"/>
  </div>
   <div class="clearfix"></div>
  </div>
     <div class="form-group">
		<spring:message code="userbalanceupdateajaxform.lbl.comment" var="comment"/>
    <label class="col-sm-3 col-md-3 control-label nolpad">
   ${comment}
    </label>
    <div class="col-sm-9 col-md-9 nopad">       
    <input type="text"  class="form-control" name="comments" id="comments" placeholder="${comment}" maxLength="244"/>
  </div>
      <div class="clearfix"></div>
    </div>
  <div class="form-group text-right">
    <spring:message code="btn.update" var="updateBtn"/>
    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${updateBtn}"/>    
    <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal">
      <spring:message code="btn.close"/>
    </button>
  </div>
</form>
<script type="text/javascript">
$(document).ready(function() {
	  var maxBalance = 9999999.99;
	  $("#epapBalanceUpdateFrom").validate({
	    debug: true,
	    rules: {
	      "balance": {
	        "required": true,
	        //"digits": false,
	        number: true,
	        "min": 0.01,
	        "max": maxBalance
	      },
	      "balanceType": {
	        "required": true
	      }
	    },
	    messages: {
	      "balance": {
	        "required": "<spring:message code='userbalanceupdateajaxform.validation.amount.required'/>",
	       //"digits": "<spring:message code='userbalanceupdateajaxform.validation.amount.number'/>",
	         "number": "<spring:message code='userbalanceupdateajaxform.validation.amount.number'/>",
	        "min": "<spring:message code='userbalanceupdateajaxform.validation.amount.min'/>",
	        "max": ("<spring:message code='userbalanceupdateajaxform.validation.amount.max'/> "+maxBalance.toFixed(2))
	      },
	      "balanceType": {
	        "required": "<spring:message code='userbalanceupdateajaxform.validation.balanceType.required'/>"
	      }
	    },
	    invalidHandler: function(event, validator) {},
	    errorPlacement: function(error, element) {
	      if ($(element).is(":radio")) {
	        error.insertAfter(element.parent(".radio"));
	      } else {
	        error.insertAfter(element);
	      }
	    },
	 /*  submitHandler: function(form) {
	    	form.submit();
	        $(form).find("input[type=submit]").attr("disabled",true);
	    }, */
	    highlight: function(element) {
	      $(element).addClass("error-element");
	    },
	    unhighlight: function(element) {
	      $(element).removeClass("error-element");
	    }
	  });
	  $('input[type=radio][name=balanceType]').change(function() {
	    if (this.value == 'D') {
	      $("#balance").rules("remove", "range");
	      $("#balance").rules("add", {
	        range: [0.01, parseFloat("${bankDetails.availableBalance}")],
	        messages: {
	          range: "<spring:message code='userbalanceupdateajaxform.validation.amount.range'/>"
	        }
	      });
	    } else {
	      $("#balance").rules("remove", "range");
	    }
	  });
	});


</script>