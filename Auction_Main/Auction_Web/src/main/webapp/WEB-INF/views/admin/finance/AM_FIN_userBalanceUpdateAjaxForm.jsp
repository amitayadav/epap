<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form action="${contextPath}/finance/userBalanceUpdate" method="post" class="form-horizontal" id="userBalanceUpdateFrom" enctype="multipart/form-data">
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
    <div >  
    <div class="radio">
      <input type="radio" name="balanceType" class="balanceType" id="credit" value="C"/>
      <label for="credit" id="lblcredit">
 	  <spring:message code="userbalanceupdateajaxform.lbl.deposit"/>
      </label>
       &nbsp;
      <input type="radio" name="balanceType" class="balanceType" id="debit" value="D"/>
      <label for="debit" id="lbldebit"><spring:message code="userbalanceupdateajaxform.lbl.withdraw"/></label>
      <input type="radio" name="balanceType" class="balanceType" id="advance" value="A"/>
      &nbsp;
      <label for="advance" id="lbladvance"><spring:message code="userbalanceupdateajaxform.lbl.advance"/> </label>
      <input type="radio" name="balanceType" class="balanceType" id="adjustCredit" value="K"/>
       &nbsp;
      <label for="adjustCredit" id="lbladjustCredit"><spring:message code="userbalanceupdateajaxform.lbl.adjustCredit"/></label>
      <input type="radio" name="balanceType" class="balanceType" id="adjustDebit" value="J"/>
       &nbsp;
      <label for="adjustDebit" id="lbladjustDebit"><spring:message code="userbalanceupdateajaxform.lbl.adjustDebit"/>  </label>
    
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
  
  <label id="confirmDialogMsg" class="hidden" ><spring:message code="userbalanceupdateajaxform.dialog.msg"/></label> 
  <div class="form-group text-right">
    <spring:message code="btn.update" var="updateBtn"/>
    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight"   value="${updateBtn}"/>    
    <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal">
      <spring:message code="btn.close"/>
    </button>
  </div>
</form>
<script type="text/javascript">
$(document).ready(function() {
  var maxBalance = 9999999.99;
  $("#userBalanceUpdateFrom").validate({
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
   submitHandler: function(form) {
		  var balance = document.getElementById("balance").value
		  var radioValue = $("input[name='balanceType']:checked").val();
		  if(radioValue  == "C"){
			  lblvalue= $("#lblcredit").html().trim();
		  }else if (radioValue  == "D"){
			  lblvalue=$("#lbldebit").html().trim();
		  }else if(radioValue  == "A"){
			  lblvalue=$("#lbladvance").html().trim();
		  }else if(radioValue  == "K"){
			  lblvalue=$("#lbladjustCredit").html().trim();
		  }else if(radioValue  == "J"){
			  lblvalue=$("#lbladjustDebit").html().trim();
		  }else{
			  lblvalue="";
		  }
		  var newLine = "\r\n"
		  var result = confirm(lblvalue +" "+ balance +" "+newLine+
			$("#confirmDialogMsg").html().trim());
		  if(result){
			  return true;
		  }else{
			  return false;   
		  }
		  form.ajaxSubmit();
        $(form).find("input[type=submit]").attr("disabled",true);
    }, 
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
    } else if(this.value == 'J'){
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