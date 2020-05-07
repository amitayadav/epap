<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="buyer.financialstatement.title"/></li>
    <li class="todaydatetime">
  	  <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy -   ' value="${internetDateTime}"/>
  	  <span id="current-time">
  		<fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
  	  </span>
    </li>
  </ul>
  <div class="">
    <div class="page-title">
      <div class="title_left">
        <h3><spring:message code="buyer.financialstatement.title"/></h3>
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
              <%-- <h2><spring:message code="buyer.financialstatement.heading"/></h2> --%>
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
              <form action="${contextPath}/btransaction/financialstatement" method="POST" id="userfinancialstatement" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div class="form-group">
                  <spring:message code="buyer.financialstatement.lbl.startDate" var="startDatePhl"/>
                  <spring:message code="buyer.financialstatement.lbl.endDate" var="endDatePhl"/>
                  <div class="col-md-6 nolpad">
                    <label class="form-label"><span class="required"> * </span>${startDatePhl}</label>
                    <input type="text" name="startDate" id="startDate" class="form-control-for-dates" placeholder="${startDatePhl}" value="${startDate}" readonly="readonly"/>
                  </div>
                  <div class="col-md-6 norpad">
                    <label class="form-label"><span class="required"> * </span>${endDatePhl}</label>
                    <input type="text" name="endDate" id="endDate" class="form-control-for-dates" placeholder="${endDatePhl}"  value="${endDate}" readonly="readonly"/>
                  </div>
                </div>
                <div class="clearfix"></div>
                <div class="form-group text-right">
                  <br/>
                  <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="submit"/>'/>&nbsp;&nbsp;
                </div>
              </form>
              <c:if test="${empty transactionList and (not empty startDate or not empty endDate)}">
                <label class="error">
                  <spring:message code="admin.financialstatement.lbl.notransaction"/>
                </label>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
    <c:if test="${not empty transactionList}">
      <div class="x_content">
        <div class="row">
          <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
              <div class="x-title"></div>
              <div class="table-responsive">
              <table class="table table-striped table-bordered table_dayauction" id="buyerAccountStatement">
                  <thead>
                    <tr>
                      <th><spring:message code="admin.financialstatement.lbl.transactiondatetime"/></th>
                      <th><spring:message code="admin.financialstatement.lbl.transactiondescriptions"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionReferenceId"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionProduct"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionQuantity"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionPrice"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionTotal"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionCommission"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionVat"/></th>
                      <th><spring:message code="buyer.accountstatement.lbl.transactionShipping"/></th>
                      <th><spring:message code="admin.financialstatement.lbl.transactiondebit"/></th>
                      <th><spring:message code="admin.financialstatement.lbl.transactioncredit"/></th>
                     <th><spring:message code="admin.financialstatement.lbl.advanceBalance" />
                      <th><spring:message code="admin.financialstatement.lbl.transactionbalance"/></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:set var="totalDebit" value="0"/>
                    <c:set var="totalCredit" value="0"/>
                    <c:set var="totalBalance" value="0"/>
                    <c:set var="Quantity" value="0"/>
                    <c:set var="Price" value="0"/>
                    <c:set var="Total" value="0"/>
                    <c:set var="Commission " value="0"/>
                    <c:set var="Vat " value="0"/>
                    <c:set var="Shipping" value="0"/>
                    <c:forEach items="${transactionList}" var="transaction" varStatus="status">
                      <tr>
                          <!--fahad: date format-->
                        <!--<td><fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${transaction.transactionCreation}"/></td>-->
                        <td><fmt:formatDate pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${transaction.transactionCreation}"/></td>
                        <td width="350px"> ${transaction.transactionDescription}
                       	 <%-- <spring:message code="trcode.${transaction.transactionCodeBean.transactionCode}"/> --%>
                        </td>
                        <td width="50px" class="text-center">${transaction.referenceId eq 0 ?  "" : transaction.referenceId}</td>
                        <td width="200px">${transaction.product}</td>
                        <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits = "0" value ='${transaction.quantity eq 0 ?  "" : transaction.quantity}'/></td>
                        <c:set var="Quantity" value="${Quantity+transaction.quantity}"/>
                         <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = '${transaction.price eq 0 ?  "" : transaction.price}'/></td>
                        <c:set var="Price" value="${Price+transaction.price}"/>
                        <td class="text-center">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = '${transaction.total eq 0 ?  "" : transaction.total}'/></td>
                        <c:set var="Total" value="${Total+transaction.total}"/>
                        <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${transaction.commission eq 0 ?  "" : transaction.commission}'/></td>
                        <c:set var="Commission" value="${Commission+transaction.commission}"/>
                        <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${transaction.vat eq 0 ?  "" : transaction.vat}'/></td>
                        <c:set var="Vat" value="${Vat+transaction.vat}"/>
                        <td class="text-center"> <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${transaction.shipping eq 0 ?  "" : transaction.shipping}'/></td>
                        <c:set var="Shipping" value="${Shipping+transaction.shipping}"/>
                        <td class="text-center  debit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = '${transaction.debit gt 0 ? transaction.debit : ""}'/> </td>
                        <td class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = '${transaction.credit gt 0 ? transaction.credit : ""}'/></td>
                      	 <c:set var="totalDebit" value="${totalDebit+transaction.debit}"/>
                        <c:set var="totalCredit" value="${totalCredit+transaction.credit}"/>
               			 <td class="text-center">
                          <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${transaction.advanceBalance}"/>
                        </td>
                        <c:set var="totalAdvanceBalance" value="${transaction.advanceBalance}"/>
                        <td class="text-center">
                          <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${transaction.balance}"/>
                        </td>
                        <c:set var="totalBalance" value="${transaction.balance}"/>
                      </tr>
                    </c:forEach>
                  </tbody>
                  <tfoot>
                    <tr>
                      <th class="text-center" colspan="4"> </th>
                        <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits = "0" value = "${Quantity}"/>
                      </th>
                        <th class="text-center credit">
                      </th>
                        <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${Total}"/>
                      </th>
                        <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${Commission}"/>
                      </th>
                       <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${Vat}"/>
                      </th>
                       <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${Shipping}"/>
                   
                      <th class="text-center debit">
                        (<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalDebit}"/>)
                      </th>
                      <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalCredit}"/>
                      </th>
                        <th class="text-center credit">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalAdvanceBalance}"/>
                      </th>
                      <th class="text-center">
                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalBalance}"/>
                      </th>
                    </tr>
                  </tfoot>
                </table>
                </div>
            </div>
          </div>
        </div>
      </div>
    </c:if>
  </div>
</div>

<script type="text/javascript">
  $(document).ready(function() {
      window.history.pushState("object or string", "EPAP|Comment", contextPath+"/btransaction/financialStatement");
      //$('#datatable').DataTable();
      	var startDateVar =  '<c:out value="${startDate}"/>';
		var endDateVar =  '<c:out value="${endDate}"/>';
		if (startDateVar == '') {		 
			  startDateVar=new Date(new Date().setDate(1)).toISOString().substring(0, 10);
	 	} 
		   $('#buyerAccountStatement').DataTable({
			   searching: false,
	            paging: false, 
	            info: false,
	            ordering: false,
	        });
	 
      $("#startDate").daterangepicker({
    	  locale: {
  			format: "YYYY-MM-DD"
  		  },
    	  singleDatePicker: true,
    	  startDate:startDateVar,
    	  maxDate :new Date().toISOString().substring(0, 10)
	  });
      $('#startDate').on('apply.daterangepicker', function(ev, picker) {
    	   	 $("#endDate").daterangepicker({
    	       	 locale: {
    	         	format: "YYYY-MM-DD"
    	     	 },
    	       	 singleDatePicker: true,
    	       	 minDate : $("#startDate").val(),	 
    	   	 });
    	     });
      $("#endDate").daterangepicker({
    	  locale: {
      		format: "YYYY-MM-DD"
  		  },
    	  singleDatePicker: true,
    	  //dateLimit: true,
    	  minDate : $("#startDate").val(),		  
	  });
      
      $("#userfinancialstatement").validate({
          debug: true,
          rules: {
              "startDate": {
                  "required": true,
              },
              "endDate": {
                  "required": true,
              }
          },
          messages: {
        	  "accountId": {
                  "required": '<spring:message code="buyer.financialstatement.validation.accountId.required"/>'
              },
              "startDate": {
                  "required": '<spring:message code="buyer.financialstatement.validation.startDate.required"/>'
              },
              "endDate": {
                  "required": '<spring:message code="buyer.financialstatement.validation.endDate.required"/>'
              }
          },
          invalidHandler: function(event, validator) {

          },
          errorPlacement: function(error, element) {
			error.insertAfter(element.parent(".form-group"));
          },
         /*  submitHandler: function(form) {
        	  form.submit();
              $(form).find("input[type=submit]").attr("disabled",true);
          }, */
          highlight: function(element) {
			$(element).addClass("error-element");
          },
          unhighlight: function(element) {
		    $(element).removeClass("error-element");
          }
      });
  });
  function auctionRequestView(id) {
      $.ajax({
          type: "GET",
          async: false,
          cache: false,
          url: (contextPath + "/seller/auctionrequestview/" + id),
          success: function(result) {
              $("#auctionRequestViewModel .modal-body").empty().html(result);
              $("#auctionRequestViewModel").modal({
                  show: true,
                  backdrop: 'static',
                  keyboard: false
              });
          },
          error: function(e) {
              $("#auctionRequestViewModel .modal-body").empty().html(e);
          }
      });
  }
</script>