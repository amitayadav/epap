<%@page import="com.auction.commons.util.InternetTiming"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="javax.servlet.http.HttpUtils.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="internetTimeZone" value="<%=InternetTiming.getApplicationTimeZone()%>" scope="request"/>
<c:set var="english"><spring:message code="language.english"/></c:set>
<c:set var="arabic"><spring:message code="language.arabic"/></c:set>
<c:set var="reqURI" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<header class="header_s">
    <div class="menu-block">
        <div class="menu-navigation">
            <div class="container">
                <div class="top-header">
                    <div class="logo-block">
                        <a class="navbar-brand" href="${contextPath}/home"><img src="${contextPath}/resources/images/frontendimage/logo.jpg" alt="logo"/></a>
                    </div>
                    <div class="login-panel">
                        <a  class="btn-login" href="${contextPath}/auth/login"><spring:message code="login.lbl.login"/></a>
                        <a class="btn-register" href="${contextPath}/auth/registration"><spring:message code="registration.lbl.register"/></a>							
                        <span class="dropdown">
                            <a class="btn btn-default btn-warning dropdown-toggle" type="button" id="langSelection" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                <c:choose>
                                    <c:when test="${(pageContext.response.locale.displayLanguage) eq english}"><spring:message code="language.english"/>
                                    </c:when>		  		
                                    <c:otherwise ><spring:message code="language.arabic"/>
                                    </c:otherwise>
                                </c:choose>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="langSelection">
                                <c:choose>
                                    <c:when test="${(pageContext.response.locale.displayLanguage) eq english}">
                                        <li><a href="?lang=ar"><spring:message code="language.arabic"/></a></li>
                                        </c:when>		  		
                                        <c:otherwise >
                                        <li><a href="?lang=en"><spring:message code="language.english"/></a></li>
                                        </c:otherwise>
                                    </c:choose>
                            </ul>
                        </span>

                        <span class="homedateandtime">
                            <fmt:formatDate type="both" dateStyle="long" pattern=' EEEE dd MMMM yyyy' value="${internetDateTime}"/>                      
                        </span>

                    </div>
                </div>
            </div>	
            <div class="menurow">
                <div class="container">
                    <a class="menuswitch" href="#">
                        <span class="menuline bar1"></span>
                        <span class="menuline bar2"></span>
                        <span class="menuline bar3"></span>
                    </a>
                    <div class="menu-collapse">
                        <ul class="navigation-menu">
                            <li class="${fn:endsWith(reqURI, '/home') ? 'active' : ''}"><a href="${contextPath}/home"><spring:message code="header.menu.main"/></a></li>
                            <!-- <li class="dropdown">
                                    <i class="ddl-switch"></i>
                                    <a href="#!">Auctions</a>
                                    <ul class="dropdown-menu">
                                            <li><a href="#!">Day Auctions</a></li>
                                            <li><a href="#!">Auction Trades</a></li>
                                    </ul>
                            </li> -->
                            <li class="nav-item dropdown ${((fn:endsWith(reqURI, '/home/dayauctions')) or (fn:containsIgnoreCase(reqURI, '/home/auctiontrades'))) ? 'active' : ''}">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <spring:message code="header.menu.auctions"/>
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li class="${fn:endsWith(reqURI, '/home/dayauctions') ? 'active' : ''}"><a class="dropdown-item" href="${contextPath}/home/dayauctions"><spring:message code="header.menu.dayauctions"/></a></li>
                                    <li class="${fn:endsWith(reqURI, '/home/auctiontrades') ? 'active' : ''}"><a class="dropdown-item" href="${contextPath}/home/auctiontrades"><spring:message code="header.menu.auctiontrades"/></a></li>
                                    <!-- <li role="separator" class="divider"></li>
                                    <li class="dropdown-header">Nav header</li>
                                    <li><a class="dropdown-item" href="#">Another action</a></li> -->
                                </ul>
                            </li>
                            <%-- <li><a href="#!"><spring:message code="header.menu.auctionparticipants"/></a></li> --%>
                            <li><a href="#!"><spring:message code="header.menu.prices"/></a></li>
                            <li><a href="#!"><spring:message code="header.menu.learn"/></a></li>
                            <li class="${fn:endsWith(reqURI, '/home/mcq') ? 'active' : ''}"><a href="${contextPath}/home/mcq"><spring:message code="header.menu.faq"/></a></li>
                            <li><a href="#!"><spring:message code="header.menu.aboutus"/></a></li>
                            <li><a href="#!"><spring:message code="header.menu.contact"/></a></li>
                        </ul>
                        <!-- <form class="search-inline">
                                <input class="form-control" type="text" placeholder="" aria-label="Search">
                                <i class="fa fa-search" aria-hidden="true"></i>
                        </form> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<script type="text/javascript">
    function formSubmit() {
                if (confirm('<spring:message code="menu.logout.msg"/>')) {
            document.getElementById("logoutForm").submit();
        }
    }

            var internetTimeMiliseconds = ${internetDateTime.getTime()};
            console.log("date"+internetTimeMiliseconds);
            var internetTimeZone = "${internetTimeZone}";
    var timeStatus = Object();
            timeStatus["am"] = '<spring:message code="am"/>';
            timeStatus["pm"] = '<spring:message code="pm"/>';

    function getTimeStatus(key) {
        return timeStatus[key];
    }

    function setCurrentTime() {
        var date = new Date(internetTimeMiliseconds);
        var hours = 0;
        var minutes = 0;
        var seconds = 0;
        if (internetTimeZone == "Asia/Kolkata" || internetTimeZone == "Asia/Calcutta") {
            var hours = (date.getHours());
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
        } else {
            var hours = (date.getUTCHours() + 3);
            var minutes = date.getUTCMinutes();
            var seconds = date.getUTCSeconds()
        }

        var ampm = hours >= 12 ? (getTimeStatus("pm")) : (getTimeStatus("am"));
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        hours = hours < 10 ? ('0' + hours) : hours;
        minutes = minutes < 10 ? ('0' + minutes) : minutes;
        seconds = seconds < 10 ? ('0' + seconds) : seconds;
        var currentTime = hours + ':' + minutes + ' ' + ampm;
        $("#current-time").text(currentTime);
        internetTimeMiliseconds = internetTimeMiliseconds + 1000;
    }

//    internetTimeMiliseconds = internetTimeMiliseconds;
    if ($("#current-time").length) {
        setCurrentTime();
        setInterval(setCurrentTime, 1000);
    }
</script>