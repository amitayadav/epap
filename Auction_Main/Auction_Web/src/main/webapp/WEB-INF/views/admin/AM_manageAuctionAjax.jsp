<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionStatusCodes"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>
<form:form action="${contextPath}/spropt/auctions/saveauction" method="post" class="form-horizontal" modelAttribute="dailyAuctionsBean" id="dailyAuctionsFrom" enctype="multipart/form-data">
    <p class="text-danger"><spring:message code="required.msg"/></p>
    <div class="form-group">
        <form:hidden path="dailyAuctionsId" id="dailyAuctionsId"/>
        <label class="col-sm-3 col-md-3 control-label nolpad">
            <span class="required"> * </span><spring:message code="editauctionajax.lbl.auctionproduct"/></label>
        <div class="col-sm-9 col-md-9 nopad">
            <form:select path="productCatalogBean.productId" class="form-control" id="productBox">
                <form:option value=""><spring:message code="editauctionajax.lbl.selectproductname"/></form:option>
                <c:forEach items="${productList}" var="productName">
                    <form:option value="${productName.productId}">${productName.productName} (${productName.productTypeName})</form:option>
                </c:forEach>
            </form:select>
        </div>
        <div class="clearfix"></div>
    </div>
    <c:if test="${not empty dailyAuctionsBean.dailyAuctionsId}">
        <div class="form-group">
            <label class="col-sm-3 col-md-3 control-label nolpad"><span class="required"> * </span><spring:message code="editauctionajax.lbl.auctionstatus"/></label>
            <div class="col-sm-9 col-md-9 nopad">
                <form:select path="auctionStatusCodesBean.auctionStatusCode" class="form-control" id="auctionStatusCode">
                    <form:option value=""><spring:message code="editauctionajax.select.selectauctionstatus"/></form:option>
                    <c:forEach items="${auctionStatusList}" var="status">
                        <c:choose>
                            <c:when test="${status.auctionStatusCode eq ENUM_AuctionStatusCodes.CANCELLED.getStatus()}">
                                <form:option value="${status.auctionStatusCode}">${status.auctionStatusCodeMeaning}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option value="${status.auctionStatusCode}" class="readonly">${status.auctionStatusCodeMeaning}</form:option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>
            <div class="clearfix"></div>
        </div>
    </c:if>
    <div class="form-group">
        <spring:message code="editauctionajax.lbl.auctionbegintime" var="auctionbegintimePhl"/>
        <fmt:formatDate pattern="yyyy-mm-dd hh:00:00" value="${dailyAuctionsBean.beginTime}" var="auctionBeginDateTime"/>
        <label class="col-sm-3 col-md-3 control-label nolpad" ><span class="required"> * </span>${auctionbegintimePhl}</label>
        <div class="col-sm-9 col-md-9 nopad">
            <div class="input-prepend input-group">
                <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
                    <form:input class="form-control" path="beginTime" id="beginTime" placeholder="${auctionbegintimePhl}" readonly="true"/>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="form-group">
        <spring:message code="editauctionajax.lbl.auctionduration" var="auctiondurationPhl"/>
        <label class="col-sm-3 col-md-3 control-label nolpad"><span class="required"> * </span>${auctiondurationPhl}</label>
        <div class="col-sm-9 col-md-9 nopad">
            <form:input class="form-control" path="auctionDuration" placeholder="${auctiondurationPhl}" readonly="true" />
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="form-group text-right">
        <spring:message code="btn.save" var="saveBtn"/>
        <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${saveBtn}" />
        <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal"><spring:message code="btn.close"/></button>
    </div>
</form:form>
<script type="text/javascript">
    function getUTCMiliseconds(miliSeconds) {
        var now = new Date(miliSeconds);
        var time = now.getTime();
        var offset = now.getTimezoneOffset();
        offset = offset * 60000;
        var newDt = (time - offset);
        var finalDate = (new Date(((time - offset) + (1000 * 60 * 60 * 3))));
        console.log(finalDate);
        return finalDate;
    }
    $(document).ready(function () {
        var selectedAuctionStatusCode = "";
        $("#auctionStatusCode").on("change", function (e) {
            e.preventDefault();
            if ($(this).find("option:selected").hasClass("readonly")) {
                $(this).val(selectedAuctionStatusCode);
            }
            return false;
        });
        $("#auctionStatusCode").on("click", function (e) {
            e.preventDefault();
            selectedAuctionStatusCode = $(this).val();
        });
        $("#dailyAuctionsFrom").validate({
            debug: true,
            rules: {
                "productCatalogBean.productId": {"required": true},
                //"auctionStatusCodesBean.auctionStatusCode": {"required": true},
                "beginTime": {"required": true},
                "auctionDuration": {"required": true},
            },
            messages: {
                "productCatalogBean.productId": {
                    "required": "<spring:message code='editauctionajax.validation.productId.required'/>"
                },
                "beginTime": {
                    "required": "<spring:message code='editauctionajax.validation.beginTime.required'/>"
                },
                "auctionDuration": {
                    "required": "<spring:message code='editauctionajax.validation.auctionDuration.required'/>"
                }
            },
            invalidHandler: function (event, validator) {

            },
            errorPlacement: function (error, element) {
                if ($(element).attr("name") == "beginTime") {
                    error.insertAfter(element.parents(".input-group"));
                } else {
                    error.insertAfter(element);
                }
            },
            /* submitHandler: function(form) {
             form.submit();
             $(form).find("input[type=submit]").attr("disabled",true);
             }, */
            highlight: function (element) {
                $(element).addClass("error-element");
            },
            unhighlight: function (element) {
                $(element).removeClass("error-element");
            }
        });

    <c:if test="${not empty dailyAuctionsBean.dailyAuctionsId}">
        $("#auctionStatusCode").rules("add", {
            required: true,
            messages: {required: "<spring:message code='editauctionajax.validation.auctionStatusCode.required'/>", }
        });
    </c:if>
        function getStartDate(isDefaultDate) {
            var date = new Date(parseInt("${InternetTiming.getInternetDateTimeAsMiliSeconds()}"));
            if (internetTimeZone == "Asia/Kolkata" || internetTimeZone == "Asia/Calcutta") {
                date.setHours(date.getHours() + 1);
            } else {
                date.setUTCHours(date.getUTCHours() + 1);
            }
            if ((!isDefaultDate) && ("" != $("#dailyAuctionsId").val() && "" != $("#dailyAuctionsId").val() && $("#dailyAuctionsId").val() > 0)) {
                date = (new Date(parseInt("${dailyAuctionsBean.beginTime.getTime()}")));
                return date;
            }
            //t.setHours(t.getHours()+1);
            //date = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(), (date.getUTCHours()+3), date.getUTCMinutes(), date.getUTCSeconds());
            return date;
        }

        var timezone = 0;
        if (internetTimeZone == "Asia/Kolkata" || internetTimeZone == "Asia/Calcutta") {
            timezone = +5.30;
        } else {
            timezone = +3;
        }
        $('#beginTime').datetimepicker({
            format: "yyyy-mm-dd hh:00:00",
            minView: 1,
            autoclose: true,
            startDate: getStartDate(false),
            timezone: timezone,
            setDate: getStartDate(true)
        });
    });
</script>