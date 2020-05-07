<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<main class="site-main">
    <!-- Slider Section -->
    <div class="slider-section">
        <ul class="bxslider">
            <li class="slide-1">
                <div class="sliderbanner-content">
                    <p><spring:message code="body.lbl.buyingandSelling.msg"/></p>
                    <p><spring:message code="body.lbl.agriculturalProductsOnline.msg"/></p>
                    <p>
                        <span><spring:message code="body.lbl.fairPrice.msg"/></span>
                        <span><spring:message code="body.lbl.transparency.msg"/></span>
                        <span><spring:message code="body.lbl.accuracy.msg"/></span>
                        <span><spring:message code="body.lbl.convenience.msg"/></span>
                        <span><spring:message code="body.lbl.security.msg"/></span>
                    </p>
                </div>
            </li>
        </ul>
    </div><!-- Slider Section /- -->	

    <!-- About Section -->
    <div class="about-section">
        <div class="container">
            <div class="section-header">
                <h3><spring:message code="body.sub.title"/></h3>
            </div>
            <div class="row">
                <div class="aboutbox">
                    <img src="${contextPath}/resources/images/frontendimage/about-icon1.png" alt="about"/>
                    <h3><spring:message code="body.title.what"/></h3>
                    <p><spring:message code="body.lbl.what.msg"/></p>
                </div>
                <div class="aboutbox">
                    <img src="${contextPath}/resources/images/frontendimage/about-icon2.png" alt="about"/>
                    <h3><spring:message code="body.title.for"/></h3>
                    <p><spring:message code="body.lbl.for.msg"/></p>
                </div>
                <div class="aboutbox">
                    <img src="${contextPath}/resources/images/frontendimage/about-icon3.png" alt="about"/>
                    <h3><spring:message code="body.title.why"/></h3>
                    <p><spring:message code="body.lbl.why.msg"/></p>
                </div>
            </div>
        </div>
    </div><!-- About Section /- -->	

    <!-- Howwork Section -->
    <div class="howwork-section">
        <div class="container">
            <div class="section-header">
                <h3><spring:message code="body.title.work"/></h3>
            </div>
            <div class="row">
                <div class="howwork-box box1">
                    <div class="howwork-innerbox">
                        <h3><spring:message code="body.lbl.seller"/></h3>
                        <ul>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon1.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.register"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon2.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.login"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon3.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.placeOffer"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon4.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.execute"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon5.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.unload"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon6.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.getPayment"/></li>
                        </ul>
                        <div align="center"><img src="${contextPath}/resources/images/frontendimage/smile.png" alt="icon"/></div>
                    </div>
                </div>
                <div class="howwork-box box2">
                    <div class="howwork-innerbox">
                        <h3><spring:message code="body.lbl.buyer"/></h3>
                        <ul>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon1.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.register"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon2.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.login"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon3.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.placeBid"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon4.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.execute"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon7.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.pickUp"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon6.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.makePayment"/></li>
                        </ul>
                        <div align="center"><img src="${contextPath}/resources/images/frontendimage/smile.png" alt="icon"/></div>
                    </div>
                </div>
                <div class="howwork-box box3">
                    <div class="howwork-innerbox">
                        <h3><spring:message code="body.lbl.shipper"/> </h3>
                        <ul>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon1.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.register"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon2.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.login"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon8.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.getOrder"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon9.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.deliver"/></li>
                            <li><img src="${contextPath}/resources/images/frontendimage/icon6.png" alt="icon"/>&nbsp &nbsp &nbsp<spring:message code="body.lbl.getPayment"/></li>
                        </ul>
                        <!--<ul><li></li></ul>-->
                        <div align="center"><img src="${contextPath}/resources/images/frontendimage/smile.png" alt="icon"/></div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- Howwork Section /- -->

    <!-- Howwork Section -->
    <div class="aboutus-section">
        <div class="container">
            <div class="section-header">
                <h3><spring:message code="body.sub.title2"/></h3>
            </div>
            <div class="aboutus-box">
                <img src="${contextPath}/resources/images/frontendimage/about1.png" alt="about"/>
                <div class="aboutus-content">
                    <h3><spring:message code="body.title.vision"/></h3>
                    <p><spring:message code="body.lbl.vision.msg"/></p>
                </div>
            </div>
            <div class="aboutus-box">
                <img src="${contextPath}/resources/images/frontendimage/about2.png" alt="about"/>
                <div class="aboutus-content">
                    <h3><spring:message code="body.title.mission"/></h3>
                    <p><spring:message code="body.lbl.mission.msg"/></p>
                </div>
            </div>
            <div class="aboutus-box">
                <img src="${contextPath}/resources/images/frontendimage/about3.png" alt="about"/>
                <div class="aboutus-content">
                    <h3><spring:message code="body.title.values"/></h3>
                    <p><spring:message code="body.lbl.values.msg"/></p>
                </div>
            </div>
        </div>
    </div>

    <!-- FAQ Section -->
    <div class="text-center">
        <a  class="btn btn-info btn-lg" href="${contextPath}/home/mcq"><spring:message code="header.menu.mcq"/></a>
    </div>


    <!-- FAQ Section /- -->

</main>	