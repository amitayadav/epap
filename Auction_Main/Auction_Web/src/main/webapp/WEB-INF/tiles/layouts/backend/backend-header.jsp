<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- top navigation -->
<c:set var="english"><spring:message code="language.english"/></c:set>
<c:set var="arabic"><spring:message code="language.arabic"/></c:set>
    <div class="top_nav">
        <div class="nav_menu">
            <nav>
                <div class="nav toggle">
                    <a id="menu_toggle"><i class="fa fa-bars"></i></a>      
                </div>
                <ul class="nav navbar-nav navbar-right" >
                    <!--fahad: add language button on top of page-->
                    <span class="nav toggle">
                        <li class="language-selection dropdown">
                            <a class="btn btn-lg dropdown-toggle" type="button" id="langSelection" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
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
                    </li>  
                </span> 
                <li class="dropdown user-profile">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <%-- <img src="${contextPath}/resources/images/img.jpg" alt=""> --%>
                        <c:choose>
                            <c:when test="${not empty loginUser && not empty loginUser.accountProfileBean}">
                                <c:choose>
                                    <c:when test="${not empty loginUser.accountProfileBean.profileImage}">
                                        <img src="${contextPath}/setting/profilePictures/${loginUser.accountProfileBean.accountId}/${loginUser.accountProfileBean.profileImage}" alt="${loginUser.accountProfileBean.FName}" class="img-circle profile_img">
                                        ${loginUser.accountProfileBean.FName} ${loginUser.accountProfileBean.LName} (${loginUser.publicName})
                                    </c:when>
                                    <c:otherwise>                
                                        <img src="${contextPath}/resources/images/img.jpg" alt="${loginUser.publicName}" class="img-circle profile_img">
                                        ${loginUser.accountProfileBean.FName} ${loginUser.accountProfileBean.LName} (${loginUser.publicName})
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>                
                                <img src="${contextPath}/resources/images/img.jpg" alt="${loginUser.publicName}" class="img-circle profile_img">
                                ${loginUser.publicName}
                            </c:otherwise>
                        </c:choose>
                        <span class=" fa fa-angle-down"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <c:if test="${not empty loginUser && not empty loginUser.accountProfileBean}">
                            <li><a href="${contextPath}/setting/changepassword" class="pageContentMenu"><spring:message code="backend-header.li.a.changepassword"/></a></li>
                            <li><a href="${contextPath}/setting/editProfile" class="pageContentMenu"><spring:message code="backend-header.li.a.profile"/></a></li>
                            </c:if>
                        <!-- <li>
                          <a href="javascript:;">
                            <span class="badge bg-red pull-right">50%</span>
                            <span>Settings</span>
                          </a>
                          </li>
                          <li><a href="javascript:;">Help</a></li> -->
                        <li><a href="javascript:void(0);" onclick="javascript:formSubmit();"><i class="fa fa-sign-out pull-right"></i> <spring:message code="backend-header.li.a.logout"/></a></li>
                    </ul>
                </li>
                <%-- <li role="presentation" class="dropdown">
                  <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-envelope-o"></i>
                    <span class="badge bg-green">6</span>
                  </a>
                  <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                    <li>
                      <a>
                        <span class="image"><img src="${contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="${contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="${contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="${contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <div class="text-center">
                        <a>
                          <strong>See All Alerts</strong>
                          <i class="fa fa-angle-right"></i>
                        </a>
                      </div>
                    </li>
                  </ul>
                  </li> --%>
            </ul>
        </nav>
        <form action="${contextPath}/auth/logoutme" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </div>
</div>
<!-- /top navigation -->
<script type="text/javascript">
    function formSubmit() {
        if (confirm('<spring:message code="menu.logout.msg"/>')) {
            document.getElementById("logoutForm").submit();
        }
    }

    var internetTimeMiliseconds = ${internetDateTime.getTime()};
    //var internetTimeZone = "${internetTimeZone}";
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
        var currentTime = hours + ':' + minutes + ':' + seconds + ' ' + ampm;
        $("#current-time").text(currentTime);
        internetTimeMiliseconds = internetTimeMiliseconds + 1000;
    }
</script>