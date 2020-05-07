<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="changepassword.heading"/></li>
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
                <h3><spring:message code="changepassword.heading"/></h3>
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
        <c:if test="${not empty errorStack}">
            <div class="error-stack">
              <c:forEach items="${errorStack}" var="error">
                <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
              </c:forEach>
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <%-- <div class="x_title">
                        <h2><spring:message code="changepassword.box.heading"/></h2>
                        <div class="clearfix"></div>
                    </div> --%>
                    <div class="x_content">
                       <p class="text-danger"> <spring:message code="required.msg"/></p>
                       <div class="x_panel">
                        <form:form action="${contextPath}/setting/changepassword" method="post"  class="form-horizontal" modelAttribute="loginDetailsBean" id="changePasswordFrom" enctype="multipart/form-data">
                        	<spring:message code="changepassword.lbl.currentpassword" var="currentpassword"/>
                        	<spring:message code="changepassword.lbl.newpassword" var="newpassword"/>
                        	<spring:message code="changepassword.lbl.confirmnewpassword" var="confirmnewpassword"/>
                        	
                            <div class="form-group">
                                
                                <label  class="col-sm-2 col-md-2 control-label nolpad">
                                <span class="required"> * </span>
                                ${currentpassword}
                                </label>
                                <div class="col-sm-10 col-md-10 nopad">
                                <form:password class="form-control editable-field" path="password" id="password" placeholder="${currentpassword}" maxlength="20" />
                            </div>
                            <div class="clearfix"></div>
                            </div>
                            <div class="form-group">
                                
                                <label class="col-sm-2 col-md-2 control-label nolpad">
                                <span class="required"> * </span>
                                ${newpassword}
                                </label>
                                <div class="col-sm-10 col-md-10 nopad">
                                <input type="password" class="form-control editable-field" name="newPassword" id="newPassword" placeholder="${newpassword}" maxlength="20" />
                            </div>
                            <div class="clearfix"></div>
                            </div>
                            <div class="form-group">
                                
                                <label class="col-sm-2 col-md-2 control-label nolpad">
                                <span class="required"> * </span>
                                ${confirmnewpassword}
                                </label>
                                <div class="col-sm-10 col-md-10 nopad">
                                <input type="password" class="form-control editable-field" name="cPassword" id="cPassword" placeholder="${confirmnewpassword}" maxlength="20" />
                            </div>
                            <div class="clearfix"></div>
                            </div>
                            <div class="form-group text-right">
                                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="changepassword.btn.changepassword"/>'/>
                                <%-- <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value='<spring:message code="btn.reset"/>' formnovalidate="formnovalidate" /> --%>
                                 <input type="button" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.cancel"/>' id="changePasswordCancel" />
                            </div>
                   `     </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $("#changePasswordFrom").validate({
            debug: true,
            rules: {
                "password": {
                    "required": true,
                     "maxlength":20
                },
                "newPassword": {
                    "required": true,
                    "passwordpattern": true,
                    "maxlength":20
                },
                "cPassword": {
                    "required": true,
                    "maxlength":20,
                    "equalTo": "#newPassword"
                }
            },
            messages: {
                "password": {
                    "required": '<spring:message code="changepassword.validation.password.required"/>',
                    "maxlength" : '<spring:message code="changepassword.validation.password.maxlength"/>'
                },
                "newPassword": {
                    "required": '<spring:message code="changepassword.validation.newPassword.required"/>',
                    "maxlength" : '<spring:message code="changepassword.validation.newPassword.maxlength"/>',
                    "passwordpattern": '<spring:message code="changepassword.validation.newPassword.passwordpattern"/>'
                },
                "cPassword": {
                    "required": '<spring:message code="changepassword.validation.cPassword.required"/>',
                    "maxlength" : '<spring:message code="changepassword.validation.cPassword.maxlength"/>',
                    "equalTo": '<spring:message code="changepassword.validation.cPassword.equalTo"/>'
                }
            },
            invalidHandler: function(event, validator) {

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
        $("#changePasswordCancel").on("click", function () {
      	  window.location.href = (contextPath + "/setting/dashboard");
      });
    });
</script>