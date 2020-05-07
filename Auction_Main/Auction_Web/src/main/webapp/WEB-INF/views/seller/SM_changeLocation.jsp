<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.auction.commons.enums.ENUM_AuctionStatusCodes"%>
<%@ page import="com.auction.commons.util.InternetTiming"%>

<form:form action="${contextPath}/spropt/auctions/saveauction" method="post" modelAttribute="auctionSellersBean" class="form-horizontal" >
    <p class="text-danger"><spring:message code="required.msg"/></p>
    <div class="form-group">
     
        <label class="col-sm-4 col-md-4 control-label nolpad">
        <span class="required"> * </span><spring:message code="editProfile.b.updateYourLocation"/></label>
        <div class="col-sm-8 col-md-8 nopad">
        <form:select path="accountLocationsBean.locationId" class="form-control" id="accountLocationsBean">
            <form:option value=""><spring:message code="seller.auctionofferplacing.lbl.selectpickuplocation"/></form:option>
            <c:forEach items="${sellerLocationList}" var="location">
                <form:option value="${location.locationId}">${location.locationName}</form:option>
            </c:forEach>
        </form:select>
    </div>
     <div class="clearfix"></div>
    </div>
    
    <div class="form-group text-right">
    <spring:message code="btn.save" var="saveBtn"/>
        <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${saveBtn}" />
        <button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="btn.close"/></button>
    </div>
</form:form> 