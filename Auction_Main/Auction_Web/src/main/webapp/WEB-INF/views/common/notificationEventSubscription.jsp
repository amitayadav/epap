<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="notieventsub.box.heading"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul>
  <div class="page-title">
    <div class="title_left">
      <h3><spring:message code="notieventsub.box.heading"/></h3>
    </div>
  </div>
  <div class="clearfix"></div>
  <c:if test="${not empty ERROR}">
    <div class="alert alert-danger alert-dismissible">
      <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
      <h4 class="title-text">
        <i class="icon fa fa-warning"></i> ${ERROR}
      </h4>
    </div>
  </c:if>
  <c:if test="${not empty SUCCESS}">
    <div class="alert alert-success alert-dismissible">
      <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
      <h4 class="title-text">
        <i class="icon fa fa-check"></i> ${SUCCESS}
      </h4>
    </div>
  </c:if>
  <div class="x_content">
    <div class="row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
          <div class="x-title">
            <%-- <h2><spring:message code="notieventsub.box.heading"/></h2> --%>
            <div class="clearfix"></div>
          </div>
          <div class="x_panel">
            <form action="${contextPath}/setting/savenotificationeventsubscription" action="POST" enctype="multipart/form-data">
              <div class="table-responsive">
                <table id="datatable" class="table table-bordered table-hover table_dayauction">
                  <thead>
                    <tr>
                      <th width="40px">#</th>
                      <th><spring:message code="notieventsub.table.th.name"/></th>
                      <th width="60px"><spring:message code="notieventsub.table.th.subscribe"/></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${eventsMeaningBeanList}" var="event" varStatus="status">
                      <tr>
                        <td class="text-center">${status.count}</td>
                        <td>${event.eventMeaning}</td>
                        <td class="text-center">
                          <div class="checkbox checkbox-warning">
                            <input type="checkbox" name="eventMap" id="event-${status.index}" value="${event.eventId}" ${not empty eventMaps && fn:contains(eventMaps,event.eventId)? "checked=checked" : ""} />
                            <label for="event-${status.index}"></label>
                          </div>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="form-group text-right">
                <c:if test="${not empty eventsMeaningBeanList }">
                  <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="Save" />
                </c:if>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="clearfix"></div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
      window.history.pushState("object or string", "EPAP|NotificationeventSubscription", contextPath+"/setting/notificationeventSubscription");
  	   languageList.sEmptyTable = '<spring:message code="notieventsub.emptyTable"/>';
       $('#datatable').DataTable({
     	  language: languageList,
     	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
       });
   });
</script>