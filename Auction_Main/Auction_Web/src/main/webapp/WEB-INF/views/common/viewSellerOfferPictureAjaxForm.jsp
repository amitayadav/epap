<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.auction.commons.enums.ENUM_ProductCatalogStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-md-12 col-sm-12 col-xs-12 nopad">
    <div class="x_panel">
        <spring:message code="btn.remove" var="removebtn" />	
        <div class="form-group">
            <label class="col-md-2 col-sm-2 mt-8"><spring:message code="viewSellerOfferPicture.sellerofferinfo1"/></label>
            <p class="col-md-10 col-sm-10 read-only-textarea font-20px">${infoLine1}</p>
            <div class="clearfix"></div>
        </div>
        <c:if test="${not empty infoLine2}">
            <div class="form-group">
                <label class="col-md-2 col-sm-2 mt-8"><spring:message code="viewSellerOfferPicture.sellerofferinfo2"/></label>
                <p class="col-md-10 col-sm-10 read-only-textarea font-20px">${infoLine2}</p>
                <div class="clearfix"></div>
            </div>
        </c:if>
    </div>
    <c:if test="${not empty sellerOfferPicturesBeanList}">
        <div class="x_panel">
            <div class="x_title">
                <div class="seller-offer-picture-thumbs">
                    <c:forEach items="${sellerOfferPicturesBeanList}" var="sellerOfferPicturesBean">
                        <div class="seller-offer-picture-thumb" id="img-${sellerOfferPicturesBean.pictureId}">
                            <img src="${contextPath}/auctions/offerpictures/${sellerOfferPicturesBean.accountProfileBean.accountId}/${sellerOfferPicturesBean.pictureLocation}" onclick= "imgClick(this)" />
                        </div>
                    </c:forEach>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<div class="clearfix"></div>
<script type="text/javascript">
    function imgClick(element) {
        $("#largeImageDisplay #imageDisplay").attr("src", element.src);
        $("#largeImageDisplay").modal({show: true, backdrop: 'static', keyboard: false});
    }
</script>