<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form action="${contextPath}/admin/saveaddnewauction" method="post" modelAttribute="dailyAuctionsBean" id="dailyAuctionsFrom" enctype="multipart/form-data">
    <p class="text-danger"><spring:message code="required.msg"/> </p>
    <div class="form-group">
        <span class="required"> * </span>
        <label><spring:message code="addnewauctionajax.lbl.auctionproduct"/></label>
        <form:select path="productCatalogBean.productId" class="form-control" id="productBox">
            <form:option value=""><spring:message code="addnewauctionajax.select.selectproductname"/></form:option>
            <c:forEach items="${productList}" var="productName">
                <form:option value="${productName.productId}">${productName.productName} (${productName.productTypeName})</form:option>
            </c:forEach>
        </form:select>
    </div>
    <div class="form-group">
        <spring:message code="addnewauctionajax.lbl.auctionbegintime" var="auctionbegintimePhl"/>
        <span class="required"> * </span>
        <label>${auctionbegintimePhl}</label>
        <div class="input-prepend input-group">
            <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
            <form:input class="form-control" path="beginTime" id="beginTime" placeholder="${auctionbegintimePhl}" />
        </div>
    </div>
    <div class="form-group">
     <spring:message code="addnewauctionajax.lbl.auctionduration" var="auctionDurationPhl"/>
        <span class="required"> * </span>
        <label>${auctionDurationPhl}</label>
        <form:input class="form-control" path="auctionDuration" placeholder="${auctionDurationPhl}" readonly="true" />
    </div>
    <%-- <div class="form-group">
    <spring:message code="addnewauctionajax.lbl.minimumquantity" var="minimumQuantityPhl"/>
        <span class="required"> * </span>
        <label>${minimumQuantityPhl}</label>
        <form:input class="form-control" path="minimumQuantity" placeholder="${minimumQuantity}" maxlength="5" />
    </div> --%>
    <div class="form-group text-right">
    <spring:message code="btn.save" var="savePhl"/>
    <spring:message code="btn.close" var="closePhl"/>
        <input type="submit" class="btn btn-success btn-sm button-left text-hightlight" value="${savePhl}" />
        <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal">${closePhl}</button>
    </div>
</form:form>
<script type="text/javascript">
    $(document).ready(function() {
    	 $("#dailyAuctionsFrom").validate({
     		debug: true,
     		rules: {
     			"productCatalogBean.productId" : {"required": true},
     			"beginTime" : {"required": true},
     			"auctionDuration" : {"required": true},
     			/* "minimumQuantity" : {"required": true, maxlength: 5, digits: true} */
     			
     		},
     		messages:{
     			"productCatalogBean.productId" : {
    				"required": "<spring:message code='addnewauctionajax.validation.productId.required'/>"
    			},
    			"beginTime" : {
    				"required": "<spring:message code='addnewauctionajax.validation.beginTime.required'/>"
    			},
    			"auctionDuration" : {
    				"required": "<spring:message code='addnewauctionajax.validation.auctionDuration.required'/>"
    			}
    			/* "minimumQuantity" : {
    				"required": "<spring:message code='addnewauctionajax.validation.minimumQuantity.required'/>",
    				"maxlength": "<spring:message code='addnewauctionajax.validation.minimumQuantity.maxlength'/>",
    				"digits" : "<spring:message code='addnewauctionajax.validation.minimumQuantity.number'/>"
    			} */
     		},
     		invalidHandler: function(event, validator) {
     			
     		},
     		errorPlacement: function (error, element) {
     			if($(element).attr("name") == "beginTime"){
     				error.insertAfter(element.parent(".input-group"));
     			}else{
     				error.insertAfter(element.parent(".form-group"));
     			}
     		}, 
     		/* submitHandler: function(form) {
     			form.submit();
                $(form).find("input[type=submit]").attr("disabled",true);
     		}, */
     		highlight : function(element) {
     			$(element).addClass("error-element");
     		},
     		unhighlight : function(element) {
     			$(element).removeClass("error-element");
     		}
     	});
    	 
    	 $('#beginTime').datetimepicker({
           format: "yyyy-mm-dd hh:00:00",
           minView: 1,
           autoclose: true,
           startDate : new Date()
       });
    });
</script>