<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<c:choose>
    <c:when test="${not empty commentsBean}">
        <form:form action="${contextPath}/admin/updatecomment" method="post" modelAttribute="commentsBean" id="commentsBeanForm" enctype="multipart/form-data">
            <p class="text-danger"><spring:message code="required.msg"/></p>
            <div class="form-group">
                <label><spring:message code="commentsajax.lbl.commenttext"/></label>
                <%-- <form:textarea class="form-control" path="commentText" disabled="true" rows="4" /> --%>
                <p class="static-form-control read-only-textarea">${commentsBean.commentText}</p>
            </div>
            <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                <div class="form-group">
                    <label><spring:message code="commentsajax.lbl.departmentname"/></label>
                    <p class="static-form-control read-only">

                        <c:choose>
                            <c:when test="${commentsBean.accountProfileBeanByAccountId.accountId > 5}">
                                ${commentsBean.accountProfileBeanByAccountId.fullName}(${commentsBean.accountProfileBeanByAccountId.publicName})
                            </c:when>
                            <c:otherwise>
                                <spring:message code="department.${commentsBean.accountProfileBeanByAccountId.accountId}"/>
                            </c:otherwise>
                        </c:choose>
                        <%-- <c:forEach  items="${accountProfileBeanList}" var="account">
                      <form:option value="${account.accountId}">${account.fullName}(${account.publicName})</form:option>
                </c:forEach>
                        <c:if test="${not empty commentsBean.accountProfileBeanByAccountId && not empty commentsBean.accountProfileBeanByAccountId.accountId}">
                          <spring:message code="department.${commentsBean.accountProfileBeanByAccountId.accountId}"/>
                        </c:if> --%>
                    </p>
                </div>
            </sec:authorize>
            <div class="form-group">
                <span class="required"> * </span>
                <label><spring:message code="commentsajax.lbl.selectcommentstatus"/></label>
                <form:select path="commentStatus" class="form-control">
                    <form:option value=""><spring:message code="commentsajax.select.selectstatus"/></form:option>
                    <c:forEach items="${CommentsStatusList}" var="status">
                        <form:option value="${status.value}"><spring:message code="comment.stauts.${status.value}"/></form:option>
                    </c:forEach>
                </form:select>
            </div>
            <div class="form-group">
                <spring:message code="commentsajax.lbl.feedback" var="feedbackPhl"/>
                <label>${feedbackPhl}</label>
                <form:textarea class="form-control" path="feedback"  placeholder="${feedbackPhl}" maxlength="255" rows="4"/>
            </div>
            <div class="form-group text-right">
                <spring:message code="commentsajax.btn.updatestatus" var="updatestatusPhl"/>
                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${updatestatusPhl}" />
                <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal">
                    <spring:message code="btn.close"/>
                </button>
            </div>
            <form:hidden path="commentId"/>
        </form:form>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#commentsBeanForm").validate({
                    debug: true,
                    rules: {
                        "commentText": {"required": true},
                        "commentStatus": {"required": true},
                        "feedback": {"required": true, maxlength: 255}
                    },
                    messages: {
                                "commentText": {
                            "required": "<spring:message code='commentsajax.validation.commentText.required'/>"
                        },
                                "commentStatus": {
                            "required": "<spring:message code='commentsajax.validation.commentStatus.required'/>"
                        },
                                "feedback": {
                                    "maxlength": "<spring:message code='commentsajax.validation.feedback.maxlength'/>",
                            "required": "<spring:message code='commentsajax.validation.feedback.required'/>"
                        }
                    },
                    invalidHandler: function (event, validator) {

                    },
                    /* errorPlacement: function (error, element) {
                     error.insertAfter(element.parent(".form-group"));   
                     }, */
                    /* submitHandler : function(form) {
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
            });
        </script>
    </c:when>
    <c:otherwise>
        <h3><label class="error">${ERROR}</label></h3>
    </c:otherwise>
</c:choose>