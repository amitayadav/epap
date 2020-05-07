<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!-- page content -->
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="admin.epapstatement.heading"/></li>
        <li class="todaydatetime">
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
            <span id="current-time">
                <fmt:formatDate type="both" dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>
            </span>
        </li>
    </ul>
    <div class="x_content">
        <div class="page-title">
            <div class="title_left">
                <h3><spring:message code="admin.epapstatement.heading"/></h3>
            </div>
        </div>

        <div class="clearfix"></div>
        <c:if test="${not empty ERROR}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="btn btn-danger btn-sm button-right text-hightlight" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text"><i class="icon fa fa-warning"></i> ${ERROR}</h4>
            </div>
        </c:if>
        <c:if test="${not empty SUCCESS}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="btn btn-danger btn-sm button-right text-hightlight" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text"><i class="icon fa fa-check"></i> ${SUCCESS}</h4>
            </div>
        </c:if>
        <div class="modal fade" id="epapBalanceModel" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                        </button>
                        <h3 class="modal-title">
                            <br><spring:message code="admin.epapstatement.title"/>
                        </h3>
                    </div>
                    <div class="modal-body">
                    </div>
                </div>
            </div>
        </div>
        <div class="x_panel">
            <form action="${contextPath}/super/epapAccountStatement" method="POST" id="epapaccountstatement" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div class="form-group">
                    <spring:message code="admin.financialstatement.lbl.startDate" var="startDatePhl"/>
                    <spring:message code="admin.financialstatement.lbl.endDate" var="endDatePhl"/>
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
                <br/>
                <div class="form-group text-right">
                    <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="submit"/>'/>
                </div>
            </form>

            <br><button onclick="userBalanceEdit(${loginDtl.accountProfileBean.accountId})" class="btn btn-warning btn-lg"  data-toggle="tooltip" data-original-title="${editBtn}"><i class="fa fa-edit"></i><spring:message code="common.creditdebit.btn.Balance"/> </button>
            <div class="clearfix"></div>
            <br/>
            <c:if test="${empty epapList}">
                <label class="error">
                    <spring:message code="admin.epapstatement.lbl.required"/>
                </label>
            </c:if>
            <c:if test="${not empty epapList}">
                <div class="x_content">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12 ">
                            <div class="x_panel" >
                                <div class="x-title">
                                    <%-- <h2><spring:message code="seller.financialstatement.heading"/></h2> --%>
                                </div>
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-responsive table_dayauction" id="filterData">
                                        <thead>
                                            <tr>
                                                <th><spring:message code="admin.epapstatement.lbl.transactiondatetime"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.transactiondescriptions"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.comments"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.transactionReferenceId"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.transactiondebit"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.transactioncredit"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.advanceBalance"/></th>
                                                <th><spring:message code="admin.epapstatement.lbl.transactionbalance"/></th>
                                           	     <th><spring:message code="admin.epapstatement.lbl.cashPosition"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="totalDebit" value="0"/>
                                            <c:set var="totalCredit" value="0"/>
                                            <c:set var="totalBalance" value="0"/>
                                            <c:forEach items="${epapList}" var="epapList" varStatus="status">
                                                <tr>
                                                    <!--fahad: change dateformatEEEE-->
                                                  <!--<td><fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${epapList.transactionDate}"/></td>-->
                                                    <td><fmt:formatDate pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${epapList.transactionDate}"/></td>
                                                    <td>
                                                        ${epapList.transactionDescription}</td>
                                                    <td>${epapList.comments}</td>
                                                    <td class="text-center">
                                                        <c:if test="${epapList.transactionId !=null}">                        
                                                            ${epapList.transactionId}
                                                        </c:if>
                                                    </td>
                                                    <c:choose>
                                                        <c:when test="${epapList.debitCredit gt 0}">
                                                            <td></td>
                                                            <td class="text-center credit">
                                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${epapList.debitCredit}"/>
                                                            </td>
                                                            <c:set var="totalCredit" value="${totalCredit+epapList.debitCredit}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td class="text-center  debit">
                                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${epapList.debitCredit * -1}"/>
                                                            </td>
                                                            <td></td>
                                                            <c:set var="totalDebit" value="${totalDebit+epapList.debitCredit}"/>
                                                        </c:otherwise>
                                                    </c:choose>        
                                                      <td class="text-hightlight text-center">
                                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ="${epapList.advanceBalance}"/></td>
                                                        <c:set var="totalAdvanceBalance" value="${epapList.advanceBalance}"/>
                                                    
                                                    <td class="text-hightlight text-center">
                                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ="${epapList.balance}"/></td>
                                                        <c:set var="totalBalance" value="${epapList.balance}"/>
                                               
                                               	  <td class="text-hightlight text-center">
                                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ="${epapList.cashposition}"/></td>
                                                        <c:set var="totalCashposition" value="${epapList.cashposition}"/>
                                               
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th class="text-right" colspan="4"> </th>
                                                <th class="text-center debit">
                                                    (<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalDebit ne 0 ? (totalDebit * -1) : totalDebit}"/>)
                                                </th>
                                                <th class="text-center credit" class="text-center">
                                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalCredit}"/>
                                                </th>
                                                
                                                <th class="text-center credit" class="text-center">
                                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalAdvanceBalance}"/>
                                                </th>
                                                
                                                <th class="text-center credit" class="text-center">
                                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalBalance}"/>
                                                </th>
                                                
                                                <th class="text-center credit" class="text-center">
                                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalCashposition}"/>
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
</div>
<script type="text/javascript">
    $(document).ready(function () {
        window.history.pushState("object or string", "EPAP|EpapAccountStatementList", contextPath + "/super/epapAccountStatementList");
        $('#filterData').DataTable({
            fixedHeader: {
                header: true,
                footer: true
            },
            "order": [],
            "lengthMenu": [[10, 25, 50, 75, 100], [10, 25, 50, 75, 100]]
        });
    });
    function userBalanceEdit(accountId) {
        $.ajax({
            type: "GET",
            async: false,
            cache: false,
            url: (contextPath + "/super/getEpapBalance/"+ accountId),
            success: function (result) {
                $("#epapBalanceModel .modal-body").empty().html(result);
                $("#epapBalanceModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            },
            error: function (e) {
                $("#epapBalanceModel .modal-body").empty().html(e);
                $("#epapBalanceModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            }
        });
    }
    $(document).ready(function () {
        var startDateVar = '<c:out value="${startDate}"/>';
        var endDateVar = '<c:out value="${endDate}"/>';
        if (startDateVar == '') {
            startDateVar = new Date().toISOString().substring(0, 10);
        }

        $("#startDate").daterangepicker({
            locale: {
                format: "YYYY-MM-DD"
            },
            singleDatePicker: true,
            startDate: startDateVar,
            // startDate: new Date(new Date().setDate(1)).toISOString().substring(0, 10),
            maxDate: new Date().toISOString().substring(0, 10)


        });

        $('#startDate').on('apply.daterangepicker', function (ev, picker) {
            $("#endDate").daterangepicker({
                locale: {
                    format: "YYYY-MM-DD"
//                format: "DD MMMM YYYY"
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

    });
</script>