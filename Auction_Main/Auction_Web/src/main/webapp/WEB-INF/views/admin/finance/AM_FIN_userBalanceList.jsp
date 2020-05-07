<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="userbalancelist.h3.userdetailslist"/></li>
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
        <h3><spring:message code="userbalancelist.h3.userdetailslist"/></h3>
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
        <h4 class="title-text"><i class="icon fa fa-check"></i> ${SUCCESS}
        </h4>
      </div>
    </c:if>
    <div class="x_content">
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="x_panel">
            <div class="x_panel table-responsive">
              <spring:message code="btn.edit" var="editBtn"/>
              <table id="datatable" class="table table-striped table-bordered">
                <thead>
                  <tr>
                    <th><spring:message code="userbalancelist.th.fullname"/></th>
                    <th><spring:message code="userbalancelist.th.primaryemailaddress"/></th>
                    <th width="60"><spring:message code="userbalancelist.th.phonenumber"/></th>
                    <th width="30"><spring:message code="userbalancelist.th.usertype"/></th>
                    <th width="70"><spring:message code="userbalancelist.th.availablebalance"/></th>
                    <th width="70"><spring:message code="userbalancelist.th.blockedbalance"/></th>
                     <th width="70"><spring:message code="userbalancelist.th.advanceBalance"/></th>
                    <th width="40" class="disable-sorting">${editBtn}</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${loginDetailsList}" var="loginDtl" varStatus="count">
                    <c:if test="${not empty loginDtl.accountProfileBean and not empty loginDtl.accountProfileBean.accountId}">
                      <tr>
                        <td>${loginDtl.accountProfileBean.FName}&nbsp;${loginDtl.accountProfileBean.MName}&nbsp;${loginDtl.accountProfileBean.LName}&nbsp;(${loginDtl.publicName})</td>
                        <td>${loginDtl.loginUserid}</td>
                        <td class="text-right">${loginDtl.accountProfileBean.phoneNumber1}</td>
                        <td class="text-bold text-uppercase">${loginDtl.accountTypeCodesBean.accountTypeMeaning}</td>
                        <td class="text-right">
                          <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${loginDtl.accountProfileBean.bankDetailsBean.availableBalance}"/>
                        </td>
                        <td class="text-right">
                          <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${loginDtl.accountProfileBean.bankDetailsBean.blockedAmount}"/>
                        </td>
                        <td class="text-right">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${loginDtl.accountProfileBean.bankDetailsBean.advanceBalance}"/>
                        </td>
                        <td class="text-center">
                          <button onclick="userBalanceEdit(${loginDtl.accountProfileBean.accountId})" class="btn btn-warning btn-sm"  data-toggle="tooltip" data-original-title="${editBtn}"><i class="fa fa-edit"></i></button>
                        </td>
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
<div class="modal fade" id="userBalanceModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h3 class="modal-title">
          <spring:message code="userbalancelist.model.header"/>
        </h3>
      </div>
      <div class="modal-body">
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	 window.history.pushState("object or string", "EPAP|UserBalanceDetailsList", contextPath+ "/finance/userBalanceDetailsList");
  languageList.sEmptyTable = '<spring:message code="userbalancelist.emptytable"/>';
  $('#datatable').DataTable({
    language: languageList,
    columnDefs: [{
      orderable: false,
      targets: 'disable-sorting'
    }]
  });
});

function userBalanceEdit(accountId) {
  $.ajax({
    type: "GET",
    async: false,
    cache: false,
    url: (contextPath + "/finance/getUserBalace/" + accountId),
    success: function(result) {
      $("#userBalanceModel .modal-body").empty().html(result);
      $("#userBalanceModel").modal({
        show: true,
        backdrop: 'static',
        keyboard: false
      });
    },
    error: function(e) {
      $("#userBalanceModel .modal-body").empty().html(e);
      $("#userBalanceModel").modal({
        show: true,
        backdrop: 'static',
        keyboard: false
      });
    }
  });
}
</script>