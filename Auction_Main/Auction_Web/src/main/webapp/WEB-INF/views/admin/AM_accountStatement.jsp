<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.auction.commons.enums.ENUM_AccountTypeCodes"%>
<style>
    table#accountStatement tr td:nth-child(5) {
        text-align: center;
    }
</style>


<c:set var="ACCOUNT_TYPE_SHIPPER"
       value="${ENUM_AccountTypeCodes.SHIPPER.getType()}" />
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message
                    code="menu.main.dashboard" /></a></li>
        <li><spring:message code="admin.financialstatement.title" /></li>
        <li class="todaydatetime"><fmt:formatDate type="both"
                        dateStyle="long" pattern='EEEE dd MMMM yyyy -   '
                        value="${internetDateTime}" /> <span id="current-time"> <fmt:formatDate
                            type="both" dateStyle="long" pattern='hh:mm:ss a'
                            value="${internetDateTime}" />
            </span></li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>
                    <spring:message code="admin.financialstatement.title" />
                </h3>
            </div>
        </div>
        <div class="clearfix"></div>
        <c:if test="${not empty ERROR}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert"
                        aria-hidden="true">×</button>
                <h4 class="title-text">
                    <i class="icon fa fa-warning"></i> ${ERROR}
                </h4>
            </div>
        </c:if>
        <c:if test="${not empty SUCCESS}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert"
                        aria-hidden="true">×</button>
                <h4 class="title-text">
                    <i class="icon fa fa-check"></i> ${SUCCESS}
                </h4>
            </div>
        </c:if>
        <div class="x_content">
            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <%-- <div class="x_title">
<h2><spring:message code="admin.financialstatement.heading"/></h2>
<div class="clearfix"></div>
</div> --%>
                        <div class="x_panel">
                            <form action="${contextPath}/sprfinshp/userfinancialstatement"
                                  method="POST" id="userfinancialstatement"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}" />
                                <div class="form-group">
                                    <label class="form-label"><span class="required"> * </span>
                                        <spring:message code="admin.financialstatement.lbl.accountId" /></label>
                                    <select class="form-control-for-dates" name="accountId">
                                        <option value=""><spring:message
                                                code="admin.financialstatement.lbl.accountId" /></option>
                                            <c:forEach items="${userList}" var="user">
                                                <c:choose>
                                                    <c:when
                                                        test="${user.accountProfileBean.accountId eq accountId}">
                                                    <option value="${user.accountProfileBean.accountId}"
                                                            selected="selected">${user.accountProfileBean.FName}
                                                        ${user.accountProfileBean.LName} (${user.publicName})</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <option value="${user.accountProfileBean.accountId}">${user.accountProfileBean.FName}
                                                        ${user.accountProfileBean.LName} (${user.publicName})</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <spring:message code="admin.financialstatement.lbl.startDate"
                                                    var="startDatePhl" />
                                    <spring:message code="admin.financialstatement.lbl.endDate"
                                                    var="endDatePhl" />
                                    <div class="col-md-6 nolpad">
                                        <label class="form-label"><span class="required"> * </span>${startDatePhl}</label>
                                        <input type="text" name="startDate" id="startDate"
                                               class="form-control-for-dates" placeholder="${startDatePhl}"
                                               value="${startDate}" readonly="readonly" />
                                    </div>
                                    <div class="col-md-6 norpad">
                                        <label class="form-label"><span class="required"> * </span>${endDatePhl}</label>
                                        <input type="text" name="endDate" id="endDate"
                                               class="form-control-for-dates" placeholder="${endDatePhl}"
                                               value="${endDate}" readonly="readonly" />
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <br />
                                <div class="form-group text-right">
                                    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight"
                                           value='<spring:message code="submit"/>' />
                                </div>
                            </form>
                            <c:if
                                test="${empty transactionList and (not empty startDate or not empty endDate)}">
                                <label class="error"> <spring:message
                                        code="admin.financialstatement.lbl.notransaction" />
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
                            <div class="x-title">
                                <%-- <h2><spring:message code="seller.financialstatement.heading"/></h2> --%>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table_dayauction" id="accountStatement">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="admin.financialstatement.lbl.transactiondatetime" /></th>
                                            <th><spring:message code="admin.financialstatement.lbl.transactiondescriptions" /></th>
                                           <th><spring:message code="admin.financialstatement.lbl.comment" /></th>
										   <th><spring:message code="seller.accountstatement.lbl.transactionReferenceId" /></th>
                                            <th><spring:message code="seller.accountstatement.lbl.transactionProduct" /></th>
                                                <c:if test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                <th><spring:message code="seller.accountstatement.lbl.transactionQuantity" /></th>
                                                <th><spring:message code="seller.accountstatement.lbl.transactionPrice" /></th>
                                                <th><spring:message code="seller.accountstatement.lbl.transactionTotal" /></th>
                                                </c:if>
                                                <c:if test="${userAccountType.accountType  eq 'H'.charAt(0)}">
                                                <th><spring:message code="shipper.accountstatement.lbl.transactionRevenus"/></th>
                                                </c:if>
                                            <th><spring:message code="seller.accountstatement.lbl.transactionCommission" />
                                            </th>
                                            <th><spring:message code="seller.accountstatement.lbl.transactionVat" /></th>
                                                <c:if test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                <th><spring:message code="seller.accountstatement.lbl.transactionShipping" /></th>
                                                </c:if>
                                            <th><spring:message code="admin.financialstatement.lbl.transactiondebit" /></th>
                                            <th><spring:message code="admin.financialstatement.lbl.transactioncredit" /></th>
                                          <th><spring:message code="admin.financialstatement.lbl.advanceBalance" /> </th>
                                            <th><spring:message code="admin.financialstatement.lbl.transactionbalance" /></th>
                                        	
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="totalDebit" value="0" />
                                        <c:set var="totalCredit" value="0" />
                                        <c:set var="totalBalance" value="0" />
                                        <c:set var="Quantity" value="0" />
                                        <c:set var="Price" value="0" />
                                        <c:set var="Total" value="0" />
                                        <c:set var="Commission " value="0" />
                                        <c:set var="Vat " value="0" />
                                        <c:set var="Shipping" value="0" />
                                        <c:forEach items="${transactionList}" var="transaction"
                                                   varStatus="status">
                                            <tr>
                                                <td><fmt:formatDate pattern='EEEE dd MMMM yyyy - hh:mm:ss a'
                                                                value="${transaction.transactionCreation}" /></td>
                                                <td width="350px">${transaction.transactionDescription} <%-- <spring:message code="trcode.${transaction.transactionCodeBean.transactionCode}"/> --%>
                                                </td>
                                                <td width="350px">${transaction.comments}</td>
                                                <td width="50px" class="text-center">${transaction.referenceId eq 0 ?  "" : transaction.referenceId}</td>
                                                <td width="200px">${transaction.product}</td>
                                                <c:if
                                                    test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                    <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits = "0" value ='${transaction.quantity eq 0 ?  "" : transaction.quantity}'/></td>
                                                    <c:set var="Quantity"
                                                           value="${Quantity+transaction.quantity}" />
                                                    <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${transaction.price eq 0 ?  "" : transaction.price}'/></td>
                                                    <c:set var="Price" value="${Price+transaction.price}" />
                                                </c:if>
                                                <td class="text-center"><fmt:formatNumber type="number"
                                                                  minFractionDigits="2" maxFractionDigits="2"
                                                                  value='${transaction.total eq 0 ?  "" : transaction.total}' />

                                                    <c:set var="Total" value="${Total+transaction.total}" /></td>
                                                    <c:choose>
                                                        <c:when test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                        <td class="text-center"><fmt:formatNumber type="number"
                                                                          minFractionDigits="2" maxFractionDigits="2" value='${transaction.commission eq 0 ?  "" : transaction.commission}'/>
                                                            <c:set var="Commission" value="${Commission+transaction.commission}" /></td>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <td></td>
                                                    </c:otherwise>
                                                </c:choose>

                                                <td class="text-center"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${transaction.vat eq 0 ?  "" : transaction.vat}'/></td>
                                                <c:set var="Vat" value="${Vat+transaction.vat}"></c:set>
                                                <c:if
                                                    test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                    <td class="text-center">
                                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ='${transaction.shipping eq 0 ?  "" : transaction.shipping}'/></td>
                                                    <c:set var="Shipping"
                                                           value="${Shipping+transaction.shipping}" />
                                                </c:if>
                                                <td class="text-center  debit"><fmt:formatNumber
                                                        type="number" minFractionDigits="2" maxFractionDigits="2"
                                                        value='${transaction.debit gt 0 ? transaction.debit : ""}' /></td>
                                                    <c:set var="totalDebit"
                                                           value="${totalDebit+transaction.debit}" />
                                                <td class="text-center credit"><fmt:formatNumber
                                                        type="number" minFractionDigits="2" maxFractionDigits="2"
                                                        value='${transaction.credit gt 0 ? transaction.credit : ""}' /></td>
                                                    <c:set var="totalCredit"
                                                           value="${totalCredit+transaction.credit}" />
                                                            <td class="text-center">
                        									  <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${transaction.advanceBalance}"/>
                      											  </td>
                      											    <c:set var="totalAdvanceBalance" value="${transaction.advanceBalance}" />
                                                <td class="text-center"><fmt:formatNumber type="number"
                                                                  minFractionDigits="2" maxFractionDigits="2"
                                                                  value="${transaction.balance}" /></td>
                                                    <c:set var="totalBalance" value="${transaction.balance}" />
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th class="text-left" colspan="5"></th>
                                                <c:if test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                <th class="text-center credit"><fmt:formatNumber
                                                        type="number" minFractionDigits="0" maxFractionDigits="0"
                                                        value="${Quantity}" />
                                                </th>
                                                <th class="text-center credit">
                                                </th>
                                            </c:if>
                                            <th class="text-center credit"><fmt:formatNumber
                                                    type="number" minFractionDigits="2" maxFractionDigits="2"
                                                    value="${Total}" />
                                            </th>
                                            <th class="text-center credit">
                                                <fmt:formatNumber
                                                    type="number" minFractionDigits="2" maxFractionDigits="2"
                                                    value="${Commission}" />
                                            </th>

                                            <th class="text-center credit"><fmt:formatNumber
                                                    type="number" minFractionDigits="2" maxFractionDigits="2"
                                                    value="${Vat}" />
                                            </th>
                                            <c:if test="${userAccountType.accountType  ne 'H'.charAt(0)}">
                                                <th class="text-center credit"><fmt:formatNumber
                                                        type="number" minFractionDigits="2" maxFractionDigits="2"
                                                        value="${Shipping}" />
                                                </th>
                                            </c:if>
                                            <th class="text-center debit">(<fmt:formatNumber
                                                    type="number" minFractionDigits="2" maxFractionDigits="2"
                                                    value="${totalDebit}" />)
                                            </th>
                         <th class="text-center credit"><fmt:formatNumber
                                                    type="number" minFractionDigits="2" maxFractionDigits="2"
                                                    value="${totalCredit}" /></th>
                                                   <th class="text-center credit"><fmt:formatNumber
                                                    type="number" minFractionDigits="2" maxFractionDigits="2"
                                                    value="${totalAdvanceBalance}" /></th>
                                            <th class="text-center"><fmt:formatNumber type="number"
                                                              minFractionDigits="2" maxFractionDigits="2"
                                                              value="${totalBalance}" /></th>
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
    $(document).ready(function () {
        window.history.pushState("object or string", "EPAP|UserFinancialStatement", contextPath + "/sprfinshp/userFinancialStatement");
        var startDateVar = '<c:out value="${startDate}"/>';
        var endDateVar = '<c:out value="${endDate}"/>';
        if (startDateVar == '') {
            startDateVar = new Date(new Date().setDate(1)).toISOString().substring(0, 10);
        }

        $('#accountStatement').DataTable({
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
            startDate: startDateVar,
            maxDate: new Date().toISOString().substring(0, 10)
        });
        $('#startDate').on('apply.daterangepicker', function (ev, picker) {
            $("#endDate").daterangepicker({
                locale: {
                    format: "YYYY-MM-DD"
                },
                singleDatePicker: true,
                minDate: $("#startDate").val(),

            });
        });
        $("#endDate").daterangepicker({
            locale: {
                format: "YYYY-MM-DD"
            },
            singleDatePicker: true,
            //dateLimit: true,
            minDate: $("#startDate").val(),

        });

        $("#userfinancialstatement").validate({
            debug: true,
            rules: {
                "accountId": {
                    "required": true,
                },
                "startDate": {
                    "required": true,
                },
                "endDate": {
                    "required": true,
                }
            },
            messages: {
                "accountId": {
                    "required": '<spring:message code="admin.financialstatement.validation.accountId.required"/>'
                },
                "startDate": {
                    "required": '<spring:message code="admin.financialstatement.validation.startDate.required"/>'
                },
                "endDate": {
                    "required": '<spring:message code="admin.financialstatement.validation.endDate.required"/>'
                }
            },
            invalidHandler: function (event, validator) {

            },
            errorPlacement: function (error, element) {
                error.insertAfter(element.parent(".form-group"));
            },
            /*  submitHandler: function(form) {
             form.submit();
             $(form).find("input[type=submit]").attr("disabled",true);
             }, */
            highlight: function (element) {
                $(element).addClass("error-element");
            },
            unhighlight: function (element) {
                $(element).removeClass("error-element");
            }
        });

        function auctionRequestView(id) {
            $.ajax({
                type: "GET",
                async: false,
                cache: false,
                url: (contextPath + "/seller/auctionrequestview/" + id),
                success: function (result) {
                    $("#auctionRequestViewModel .modal-body").empty().html(result);
                    $("#auctionRequestViewModel").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                },
                error: function (e) {
                    $("#auctionRequestViewModel .modal-body").empty().html(e);
                }
            });
        }
    });

</script>