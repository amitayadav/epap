<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="userdetaillist.h3.userdetailslist"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3><spring:message code="userdetaillist.h3.userdetailslist"/></h3>
      </div>
    </div>
    <div class="clearfix"></div>
    <c:if test="${not empty ERROR}">
      <div class="alert alert-danger alert-dismissible">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4 class="title-text"><i class="icon fa fa-warning"></i> ${ERROR}</h4>
      </div>
    </c:if>
    <c:if test="${not empty SUCCESS}">
      <div class="alert alert-success alert-dismissible">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4 class="title-text"><i class="icon fa fa-check"></i> ${SUCCESS}</h4>
      </div>
    </c:if>
    <div class="x_content">
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="x_panel">
            <%-- <div class="x_title">
              <h2><spring:message code="userdetaillist.h2.listofuserdetails"/></h2>
              <div class="clearfix"></div>
            </div> --%>
            <div class="x_panel table-responsive">
              <spring:message code="btn.edit" var="editBtn"/>
              <table id="datatable" class="table table-striped table-bordered table_dayauction">
                <thead>
                  <tr>
                    <th><spring:message code="userdetaillist.th.fullname"/></th>
                    <th><spring:message code="userdetaillist.th.primaryemailaddress"/></th>
                    <th width="50"><spring:message code="userdetaillist.th.usertype"/></th>
                    <th width="180"><spring:message code="userdetaillist.th.status"/></th>
                    <th width="40" class="disable-sorting">${editBtn}</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${loginDetailsList}" var="loginDtl" varStatus="count">
                    <c:if test="${not empty loginDtl.accountProfileBean && not empty loginDtl.accountProfileBean.accountId}">
                      <tr>
                        <td>${loginDtl.accountProfileBean.FName}&nbsp;${loginDtl.accountProfileBean.MName}&nbsp;${loginDtl.accountProfileBean.LName} (${loginDtl.publicName})</td>
                        <td>${loginDtl.loginUserid}</td>
                        <td class="text-bold text-uppercase text-center"><spring:message code="account.type.${loginDtl.accountTypeCodesBean.accountType}"/></td>
                        <td class="text-bold text-uppercase text-center"><spring:message code="status.${loginDtl.accountStatusCodesBean.accountStatusCode}"/></td>
                        <td class="text-center"><a href="${contextPath}/sproptrel/updateUserDetails/${loginDtl.accountProfileBean.accountId}" class="btn btn-warning btn-sm pageContentLoading"  data-toggle="tooltip" data-original-title="${editBtn}"><i class="fa fa-edit"></i></a></td>
                      </tr>
                    </c:if>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|Userdetailslist", contextPath+ "/sproptrel/userdetailslist");
	  languageList.sEmptyTable = '<spring:message code="userdetaillist.emptytable"/>';
      $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
  });
</script>