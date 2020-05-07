<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
    table#filterData tr td:nth-child(1),
    table#filterData tr td:nth-child(2)
    {
        text-align: left;
    }

    table#filterData tr td:nth-child(4),
    table#filterData tr td:nth-child(5),
    table#filterData tr td:nth-child(6){
        text-align: right;
    }
</style>
<!-- page content -->
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="admin.vatstatement.heading"/></li>
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
                <h3><spring:message code="admin.vatstatement.heading"/></h3>
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
        <div class="modal fade" id="vatBalanceModel" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <!--<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>-->
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                            
                        </button>
                        <h4 class="modal-title">
                            <br><spring:message code="admin.vatstatement.title"/>
                        </h4>
                    </div>
                    <div class="modal-body">
                    </div>
                </div>
            </div>
        </div>
        <div class="x_panel">
            <form action="${contextPath}/sprfinvat/vatAccountStatement" method="POST" id="vatfinancialstatement" enctype="multipart/form-data">
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

  		 <br><button onclick="userBalanceEdit()" class="btn btn-warning btn-lg"  data-toggle="tooltip" data-original-title="${editBtn}"><i class="fa fa-edit"></i> <spring:message code="common.creditdebit.btn.Balance"/> </button>
            <div class="clearfix"></div>
            <br/>
            <c:if test="${empty vatList}">
                <label class="error">
                    <spring:message code="admin.vatstatement.lbl.required"/>
                </label>
            </c:if>

            <c:if test="${not empty vatList}">
                <div class="x_content">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12 ">
                            <div class="x_panel">
                                <table class="table table-striped table-bordered table_dayauction" id="filterData">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="admin.vatstatement.lbl.transactiondatetime"/></th>
                                            <th><spring:message code="admin.vatstatement.lbl.transactiondescriptions"/></th>
                                          <th><spring:message code="admin.epapstatement.lbl.comments"/></th>
                                            <th><spring:message code="admin.vatstatement.lbl.transactionReferenceId"/></th>
                                            <th><spring:message code="admin.vatstatement.lbl.transactiondebit"/></th>
                                            <th><spring:message code="admin.vatstatement.lbl.transactioncredit"/></th>
                                            <th><spring:message code="admin.vatstatement.lbl.transactionbalance"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="totalDebit" value="0"/>
                                        <c:set var="totalCredit" value="0"/>
                                        <c:set var="totalBalance" value="0"/>
                                        <c:forEach items="${vatList}" var="vatList" varStatus="status">
                                            <tr>
                                                <td><fmt:formatDate pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${vatList.transactionDate}"/></td>
                                                <td>
                                                    ${vatList.transactionDescription}</td>
                                                    <td> ${vatList.comments}</td>
                                                <td class="text-right">${vatList.transactionId}</td>

                                                <c:choose>
                                                    <c:when test="${vatList.debitCredit gt 0}">
                                                        <td></td>
                                                        <td class="text-right credit">
                                                            <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${vatList.debitCredit}"/>
                                                        </td>
                                                        <c:set var="totalCredit" value="${totalCredit+vatList.debitCredit}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="text-right  debit">
                                                            <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${vatList.debitCredit * -1}"/>
                                                        </td>
                                                        <td></td>
                                                        <c:set var="totalDebit" value="${totalDebit+vatList.debitCredit}"/>
                                                    </c:otherwise>
                                                </c:choose>    
                                                <td class="text-hightlight text-right">
                                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value ="${vatList.balance}"/></td>
                                                    <c:set var="totalBalance" value="${vatList.balance}"/>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th class="text-right" colspan="4">
                                                <spring:message code="total"/> : </th>
                                            <th class="text-right debit">
                                                (<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalDebit ne 0 ? (totalDebit * -1) : totalDebit}"/>)
                                            </th>
                                            <th class="text-right credit" class="text-center">
                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalCredit}"/>
                                            </th>
                                            <th class="text-right credit" class="text-center">
                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value = "${totalBalance}"/>
                                            </th>
                                        </tr>
                                    </tfoot>
                                </table>
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
        window.history.pushState("object or string", "EPAP|VatAccountStatementList", contextPath + "/sprfinvat/vatAccountStatementList");
        $('#filterData').DataTable({
            fixedHeader: {
                header: true,
                footer: true
            },
            "order": [],
            "lengthMenu": [[10, 25, 50, 75, 100], [10, 25, 50, 75, 100]]
        });
    });
    function userBalanceEdit() {
        $.ajax({
            type: "GET",
            async: false,
            cache: false,
            url: (contextPath + "/sprfinvat/getVatBalnace/"),
            success: function (result) {
                $("#vatBalanceModel .modal-body").empty().html(result);
                $("#vatBalanceModel").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            },
            error: function (e) {
                $("#vatBalanceModel .modal-body").empty().html(e);
                $("#vatBalanceModel").modal({
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
    });
</script>