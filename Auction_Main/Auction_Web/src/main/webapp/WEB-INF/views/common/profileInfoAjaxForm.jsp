<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.auction.commons.enums.ENUM_ProductCatalogStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!--<div class="col-md-12 col-sm-12 col-xs-12 profile_details">-->
<div class="col-md-12 col-sm-12 col-xs-12 profile_details">
    <div class="well profile_view">
        <!--<div class="col-sm-12">-->
        <div class="col-sm-12">
            <h3 class="brief">${accountProfileBean.FName} ${accountProfileBean.MName} ${accountProfileBean.LName} <i class="ratings">(${accountProfileBean.publicName})</i></h3>
            <!--<div class="col-sm-9 hmin-160 right-devider">-->
            <div class="col-md-9 hmin-160 right-devider">
                <c:if test="${not empty ownerName}">
                    <h4><strong><spring:message code="common.full.ownername" /></strong>: ${ownerName}</h4>
                </c:if>
                <ul class="list-unstyled">
                    <li>
                        <strong class="col-sm-9 detail-label"><i class="fa fa-briefcase"></i>&nbsp;&nbsp;<spring:message code="common.business.name" />: <span class="ratings">${accountProfileBean.businessName}</span></strong>
                        <!--<i class="col-sm-9 col-md-9">${accountProfileBean.businessName}</i>-->
                        <div class="clearfix"></div>
                    </li>        
                    <li>
                        <strong class="col-sm-9 detail-label"><i class="fa fa-envelope-o"></i>&nbsp;&nbsp;<spring:message code="common.email"/>: <span class="ratings">${accountProfileBean.emailAddress2}</span></strong>
                        <!--<span class="col-sm-9 col-md-9">${accountProfileBean.emailAddress2}</span>-->
                        <div class="clearfix"></div>
                    </li>
                    <li>
                        <strong class="col-sm-9 detail-label"><i class="fa fa-phone"></i>&nbsp;&nbsp;<spring:message code="common.phone.number1" />: <span class="ratings">${accountProfileBean.phoneNumber1}</span></strong>
                        <strong class="col-sm-9 detail-label"><i class="fa fa-phone"></i>&nbsp;&nbsp;<spring:message code="common.phone.number2" />: <span class="ratings">${accountProfileBean.phoneNumber2}</span></strong>
                        <%--<c:if test="${not empty accountProfileBean.phoneNumber2}"> / ${accountProfileBean.phoneNumber2}</c:if>--%>
                        <!--                        <span class="col-sm-9 col-md-9">${accountProfileBean.phoneNumber1}
                                                    </span>-->
                        <div class="clearfix"></div>
                    </li>
                    <c:if test="${not empty accountProfileBean.sellerInfoBean.infoLine1}">
                        <li>
                            <strong class="col-sm-9 detail-label"><i class="fa fa-info-circle"></i>&nbsp;&nbsp;
                                <spring:message code="common.info.line.1"/> </strong>
                            <!--fahad: add read-only-textarea class to user profile info text-->
                        <!--<span class="col-sm-12 col-sm-12 read-only-textarea">${accountProfileBean.sellerInfoBean.infoLine1}</span>-->
                            <span class="col-sm-9 col-sm-9 read-only-textarea">${accountProfileBean.sellerInfoBean.infoLine1}</span>
                            <div class="clearfix"></div>
                        </li>
                    </c:if>
                    <c:if test="${not empty accountProfileBean.sellerInfoBean.infoLine2}">
                        <li><strong class="col-sm-12 detail-label"><i class="fa fa-info-circle"></i>&nbsp;&nbsp;<spring:message code="common.info.line.2"/></strong>
                            <%--<spring:message code="common.info.line.2"/>--%>
                            <!--fahad: add read-only-textarea class to user profile info text-->
                        <!--<span class="col-sm-12 col-sm-12 read-only-textarea">${accountProfileBean.sellerInfoBean.infoLine2}</span>-->
                            <span class="col-sm-12 col-sm-12 read-only-textarea">${accountProfileBean.sellerInfoBean.infoLine2}</span>
                            <div class="clearfix"></div>
                        </li>
                    </c:if>
                    <li>
                        <strong class="col-sm-3 detail-label"><i class="fa fa-star"></i>&nbsp;&nbsp;<spring:message code="common.rating" />: &nbsp;&nbsp;
                        <span class="ratings">
                            <span>
                             <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits = "0" value = "${rating}"/></span>&nbsp;&nbsp;
                  			<c:forEach begin="1" end="${rating}" >
   							<span class="fa fa-star"></span>
						</c:forEach>

                        </span></strong>
                        <div class="clearfix"></div>
                    </li>
                </ul>
            </div>
            <div class="col-sm-3 hmin-160 text-center">
                <c:choose>
                    <c:when test="${not empty accountProfileBean && not empty accountProfileBean.profileImage}">
                        <img src="${contextPath}/setting/profilePictures/${accountProfileBean.accountId}/${accountProfileBean.profileImage}" class="user-profile-picture"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${contextPath}/resources/images/img.jpg" alt="" class="user-profile-picture">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<div class="clearfix"></div>
<c:if test="${not empty sellerPicturesBeanList}">
    <div class="col-md-12 col-sm-12 col-xs-12 profile_details">
        <div class="well profile_view">
            <div class="col-md-12 gallery">
                <h3 class="brief"><spring:message code="common.info.images"/></h3>
                <c:forEach items="${sellerPicturesBeanList}" var="sellerPicturesBean">
                    <div class="thumbs">
                        <img src="${contextPath}/setting/pictures/${sellerPicturesBean.accountProfileBean.accountId}/${sellerPicturesBean.pictureLocation}"  onclick= "imgClick(this)" />
                    </div>
                </c:forEach>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="clearfix"></div>
</c:if>

<script type="text/javascript">
    function imgClick(element) {
        $("#largeImageDisplay #imageDisplay").attr("src", element.src);
        $("#largeImageDisplay").modal({show: true, backdrop: 'static', keyboard: false});
    }

</script>
<%-- <div class="col-md-9 col-sm-9 col-xs-12">
  <div class="x_panel">
        <c:if test="${not empty ownerName}">
          <div class="form-group">
        <label class="col-md-3 col-sm-3 mt-8">
          <spring:message code="common.full.ownername" />
        </label>
        <p class="col-md-9 col-sm-9 read-only">${ownerName}</p>
        <div class="clearfix"></div>
      </div>
        </c:if>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 mt-8">
        <spring:message code="common.business.name" />
      </label>
      <p class="col-md-9 col-sm-9 read-only">${accountProfileBean.businessName}</p>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 mt-8">
        <spring:message code="common.full.name" />
      </label>
      <p class="col-md-9 col-sm-9 read-only">${accountProfileBean.FName} ${accountProfileBean.MName} ${accountProfileBean.LName}</p>
      <div class="clearfix"></div>
    </div>
    <c:if test="${not empty accountProfileBean.emailAddress2}">
      <div class="form-group">
        <label class="col-md-3 col-sm-3 mt-8">
          <spring:message code="common.email" />
        </label>
        <p class="col-md-9 col-sm-9 read-only">${accountProfileBean.emailAddress2}</p>
        <div class="clearfix"></div>
      </div>
    </c:if>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 mt-8">
        <spring:message code="common.phone.number" />
      </label>
      <p class="col-md-9 col-sm-9 read-only">
        ${accountProfileBean.phoneNumber1}
        <c:if test="${not empty accountProfileBean.phoneNumber2}">${accountProfileBean.phoneNumber2}</c:if>
      </p>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 mt-8">
        <spring:message code="common.rating" />
      </label>
      <p class="col-md-9 col-sm-9 read-only">${accountProfileBean.rating}</p>
      <div class="clearfix"></div>
    </div>
    <c:if test="${not empty accountProfileBean.sellerInfoBean.infoLine1}">
      <div class="form-group">
        <label class="col-md-3 col-sm-3 mt-8">
          <spring:message code="common.info.line.1" />
        </label>
        <p class="col-md-9 col-sm-9 read-only-textarea">${accountProfileBean.sellerInfoBean.infoLine1} </p>
        <div class="clearfix"></div>
      </div>
    </c:if>
    <c:if test="${not empty accountProfileBean.sellerInfoBean.infoLine2}">
      <div class="form-group">
        <label class="col-md-3 col-sm-3 mt-8">
          <spring:message code="common.info.line.2" />
        </label>
        <p class="col-md-9 col-sm-9 read-only-textarea">${accountProfileBean.sellerInfoBean.infoLine2} </p>
        <div class="clearfix"></div>
      </div>
    </c:if>
  </div>
</div>
<div class="col-md-3 col-sm-3 col-xs-12">
  <c:choose>
    <c:when test="${not empty accountProfileBean && not empty accountProfileBean.profileImage}">
      <img src="${contextPath}/setting/profilePictures/${accountProfileBean.accountId}/${accountProfileBean.profileImage}" class="user-profile-picture"/>
    </c:when>
    <c:otherwise>
      <img src="${contextPath}/resources/images/img.jpg" class="user-profile-picture"/>
    </c:otherwise>
  </c:choose>
</div>
<div class="clearfix"></div> --%>
<%-- <c:if test="${not empty sellerPicturesBeanList} ">  --%>