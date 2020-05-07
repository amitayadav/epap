<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="menu.common.agentMgnt"/></li>
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
        <h3> <spring:message code="agentList.heading"/> </h3>
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
            <div class="x-title">
              <%-- <h2><spring:message code="agentList.box.heading"/></h2> --%>
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
              <div class="table-responsive">
                <table id="datatable" class="table table-striped table-bordered table_dayauction">
                  <thead>
                    <tr>
                      <th width="180"><spring:message code="agentList.table.th.publicname"/></th>
                      <th width="180"><spring:message code="agentList.table.th.email"/></th>
                       <c:if test="${loginUserinAccountType eq 'B'.charAt(0)}">
                       <th width="180"><spring:message code="agentList.table.th.purchaseLimit"/></th>
                       </c:if>
                      <th width="50"><spring:message code="agentList.table.th.privileges"/></th>
                      <th width="100"><spring:message code="agentList.table.th.status"/></th>
                      
                      <th width="40" class="disable-sorting"><spring:message code="agentList.table.th.edit"/></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${agentList}" var="agentDtl" varStatus="count">
                      <c:if test="${not empty agentDtl.loginDetailsByAgentLoginUserid.accountProfileBean && not empty agentDtl.loginDetailsByAgentLoginUserid.accountProfileBean.accountId}">
                      	<tr>
                        <td class="text-left text-hightlight text-bold">${agentDtl.loginDetailsByAgentLoginUserid.publicName}</td>
                        <td class="text-left text-hightlight text-bold">${agentDtl.loginDetailsByAgentLoginUserid.loginUserid}</td>
                        <c:if test="${loginUserinAccountType eq 'B'.charAt(0)}">
                        <td class="text-center text-hightlight text-bold">${agentDtl.purchaseLimit}</td>
                        </c:if>
                        <td class ="text-center text-hightlight text-bold">${agentDtl.privileges}</td>
                        <td class="text-center text-hightlight text-bold">
                        	<spring:message code="status.${agentDtl.loginDetailsByAgentLoginUserid.accountStatusCodesBean.accountStatusCode}"/>
                        </td>
                        <td class="text-center">
                          <button onclick="agentEdit(${agentDtl.agentOwnerId})" class="btn btn-warning btn-lg roylatyCodeBtn" data-toggle="tooltip" data-original-title='<spring:message code="btn.edit"/>'>
                          <i class="fa fa-edit"></i>
                          </button>
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
</div>
<div class="modal fade" id="agentModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title">
          <spring:message code="agentList.modal.title"/>
        </h4>
      </div>
      <div class="modal-body">
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|AgentList", contextPath+"/sbs/agentList");
   languageList.sEmptyTable = '<spring:message code="agentList.emptyTable"/>';
      $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
  });
  function agentEdit(agentOwnerId) {
  
      $.ajax({
          type: "GET",
          async: false,
          cache: false,
          url: (contextPath + "/sbs/getaccountstatus/" + agentOwnerId),
          success: function(result) {
              $("#agentModel .modal-body").empty().html(result);
              $("#agentModel").modal({
                  show: true,
                  backdrop: 'static',
                  keyboard: false
              });
          },
          error: function(e) {
              $("#agentModel .modal-body").empty().html(e);
              $("#agentModel").modal({
                  show: true,
                  backdrop: 'static',
                  keyboard: false
              });
          }
      });
  
  }
</script>