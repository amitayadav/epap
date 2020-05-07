//jQuery validation plugin's custom methods

//Email Pattern validation
$.validator.addMethod("emailpattern", function(value, element, params) {
	//Required to validate any how.
	if(params == true || params[0]==true && params[1] == true){
		return (/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value));
	}
	//Required to validate when value is not null / not blank / not empty.
	else if(params[0]==true && params[1] == false && value.length > 0){
		return (/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value));
	}else{
		return true;
	}
}, "Please enter valid email address.");

//Password pattern validation
$.validator.addMethod("passwordpattern", function(value, element, param) {          
	return (/^(?=[a-zA-Z])(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-z0-9]{6,20}$/.test(value));
}, "Password must be between 6 to 20 alphanumeric characters, starts with alphabetic character, includes at least 1 number, at least 1 capital letter and at least 1 small letter.");

//File uploading validation
$.validator.addMethod("accept", function(value, element, param) {
	if(element.type == "file" && element.value.length != 0){
		if(element.files[0].type.indexOf(param.type) != -1){
			var index = -1;
			var ext =  param.extension.split("|");
			for(var i in ext){
				if(value.indexOf(ext[i]) != -1){
					index = value.indexOf(ext[i]);
				}
			}
			return (index != -1 ? true : false);
		}
		return false;
	}
	return true;
},"Please enter a value with a valid mimetype.");

//Not equal zero value validation
$.validator.addMethod("notEqualZero", function(value, element, param) {          
	return (param == true && value != 0);
}, '<spring:message code="buyer.auctionbidpalcing.lbl.validation.bidprice.equalzero"/>');

//Less Than Equal To value validation
$.validator.addMethod("lessThanEqualTo", function(value, element, param) {          
	return ((parseFloat(value) <= parseFloat($(param).val())));
}, 'Value {0} must be less than {1}');

/* ---------------------------------------- Custom Validation Functions --------------------------------------------------------------------------*/
function valueIsNumber(ele, e) {
  var keyCode = (e.charCode) ? e.charCode : ((e.which) ? e.which : e.keyCode);
  var ret = ((keyCode == 8 || keyCode == 9 || (e.shiftKey && keyCode == 9) || keyCode == 39 || keyCode == 37 || keyCode == 46 || (keyCode >= 48 && keyCode <= 57)));
  if (!ret) {
	  if(ele.value == ""){
		  return false;
	  }
	   ele.value = parseInt(ele.value.substr(0, ele.maxLength));
  }
  return (ret && ele.value.length < ele.maxLength);
}

function valueIsPrice(ele, e) {
  var keyCode = (e.charCode) ? e.charCode : ((e.which) ? e.which : e.keyCode);
  var ret = ((keyCode == 8 || keyCode == 9 || (e.shiftKey && keyCode == 9) || keyCode == 39 || keyCode == 37 || keyCode == 46 || (keyCode >= 48 && keyCode <= 57)));
  if (!ret) {
 /*   if(ele.value == 0){
    	ele.value = "";
    }*/
    if(ele.value == "" ){
      return false;
    }
    ele.value = parseFloat(ele.value.substr(0, ele.maxLength)).toFixed(2);
  }else{
/*   if(ele.value == 0){
	ele.value = "";
   }*/
   if(keyCode == 48 && ele.value == "" ){
     return false;
   } 
  }
  return (ret && ele.value.length < ele.maxLength);
}
Number.prototype.format = function(n, x) {
    var re = '(\\d)(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
    return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$1,');
};

function formatPriceValue(price){  
	return (price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
}

function appendLeadingZeros(inputNum,leadNumLngth){
 	var outputNum = '0';
	var i;
	for (i = 0; i < leadNumLngth-1; i++) { 
	 outputNum += '0' ;
	}   	 
	outputNum =  outputNum+inputNum;
	return outputNum=outputNum.substr(-leadNumLngth);	 
}
	

 