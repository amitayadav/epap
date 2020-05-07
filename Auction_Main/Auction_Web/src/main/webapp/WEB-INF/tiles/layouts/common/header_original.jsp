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

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
          <!--<a class="navbar-brand" href="${contextPath}/home"><img src="${contextPath}/resources/images/Webp.net-resizeimage.png" /></a>-->
            <a class="navbar-brand" href="${contextPath}/home"><img src="${contextPath}/resources/images/EPAP_Logo2.png" width="68px" /></a>      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <ul class="nav navbar-nav navbar-right hidden-xs">
                <!--fahad: lbl not btn-->
                <li class="loginbtn"><a href="${contextPath}/auth/login"><spring:message code="login.lbl.login"/></a></li>
                <!--<li class="loginbtn"><a href="${contextPath}/auth/login"><spring:message code="login.btn.login"/></a></li>-->
                <li class="regbtn"><a href="${contextPath}/auth/registration"><spring:message code="registration.btn.register"/></a></li>
                <li class="language-selection dropdown">
                    <a class="btn btn-default btn-warning dropdown-toggle" type="button" id="langSelection" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        <c:choose>
                            <c:when test="${(pageContext.response.locale.displayLanguage) eq english}">
                                <spring:message code="language.english"/>
                            </c:when>		  		
                            <c:otherwise >
                                <spring:message code="language.arabic"/>
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
                </li>
                <!--fahad: adjust date and time on homepage-->
                <li class="homedateandtime">&nbsp;
                    <span id="current-time"> 
                        <fmt:formatDate type="both"  dateStyle="long" pattern='hh:mm a' value="${internetDateTime}"/>    
                    </span>
                    &nbsp;<fmt:formatDate type="both" dateStyle="long" pattern=' dd,yyyy ' value="${internetDateTime}"/>
                </li>

            </ul>
            <div class="clearfix"></div>

        </div>





        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="${fn:endsWith(reqURI, '/home') ? 'active' : ''}"><a href="${contextPath}/home"><spring:message code="header.menu.main"/></a></li>
                    <%-- <li><a href="#"><spring:message code="header.menu.auctions"/></a></li> --%>
                <li class="nav-item dropdown ${((fn:endsWith(reqURI, '/home/dayauctions')) or (fn:containsIgnoreCase(reqURI, '/home/auctiontrades'))) ? 'active' : ''}">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="header.menu.auctions"/>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li class="${fn:endsWith(reqURI, '/home/dayauctions') ? 'active' : ''}"><a class="dropdown-item" href="${contextPath}/home/dayauctions"><spring:message code="header.menu.dayauctions"/></a></li>
                        <li class="${fn:endsWith(reqURI, '/home/auctiontrades') ? 'active' : ''}"><a class="dropdown-item" href="${contextPath}/home/auctiontrades"><spring:message code="header.menu.auctiontrades"/></a></li>
                        <!-- <li role="separator" class="divider"></li>
                        <li class="dropdown-header">Nav header</li>
                        <li><a class="dropdown-item" href="#">Another action</a></li> -->
                    </ul>
                </li>
                <li><a href="#"><spring:message code="header.menu.auctionparticipants"/></a></li>
                <li><a href="#"><spring:message code="header.menu.prices"/></a></li>
                <li><a href="#"><spring:message code="header.menu.aboutus"/></a></li>
                <li class="loginbtn visible-xs"><a href="${contextPath}/auth/login"><spring:message code="login.btn.login"/></a></li>
                <li class="regbtn visible-xs"><a href="${contextPath}/auth/registration"><spring:message code="registration.btn.register"/></a></li>
                <li class="lanbtn visible-xs"><a href="#">Language: عربي</a></li>
            </ul>


            <div class="searchbox pull-right hidden-xs">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon2">
                    <span class="input-group-addon" id="basic-addon2"><a href="#"><i class="fa fa-search" aria-hidden="true"></i></a></span>
                </div>
            </div>
        </div>
        <!--/.nav-collapse -->
        <div class="searchbox visible-xs">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon2">
                <span class="input-group-addon" id="basic-addon2"><a href="#"><i class="fa fa-search" aria-hidden="true"></i></a></span>
            </div>
        </div>
    </div>
</nav>
<script type="text/javascript">
    function formSubmit() {
        if (confirm('<spring:message code="menu.logout.msg"/>')) {
            document.getElementById("logoutForm").submit();
        }
    }

    var internetTimeMiliseconds = ${internetDateTime.getTime()};
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

    internetTimeMiliseconds = internetTimeMiliseconds;
    if ($("#current-time").length) {
        setCurrentTime();
        setInterval(setCurrentTime, 1000);
    }
</script>