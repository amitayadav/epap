<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page import="com.auction.commons.enums.ENUM_CommentsStatus"%>
<form:form action="${contextPath}/setting/addcomment" method="post" modelAttribute="commentsBean" id="commentsFrom" enctype="multipart/form-data">
  <p class="text-danger"><spring:message code="required.msg"/></p> 
  <c:choose>
    <c:when test="${fn:contains(commentsBean.commentStatus, ENUM_CommentsStatus.PENDING.getDesc()) or (empty commentsBean.commentId) }">
      <!-- (commentsBean.commentStatus eq ENUM_commentStatus.PENDING.getDesc()) or (empty commentsBean.commentId)} -->
     
      <div class="form-group">
        <label class="col-sm-2 col-md-2 control-label nolpad">
        	<span class="required"> * </span>
        	<spring:message code="commentsajax.lbl.selectdepartment"/>
        </label>
        <div class="col-sm-10 col-md-10 nopad">
        <form:select path="accountProfileBeanByAccountId.accountId"  class="form-control">
        <%--   <form:option value=""><spring:message code="commentsajax.lbl.selectdepartment"/></form:option> --%>
          <c:forEach items="${departmentList}" var="department">
          	 <form:option value="${department.key}"><spring:message code="department.${department.key}"/></form:option>
           </c:forEach>
           <c:forEach  items="${accountProfileBeanList}" var="account">
          	 <form:option value="${account.accountId}">${account.fullName}(${account.publicName})</form:option>
           </c:forEach>
           </form:select>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group">
        <form:hidden path="commentId"/>
        <label class="col-sm-2 col-md-2 control-label nolpad">
          <span class="required"> * </span>
          <spring:message code="commentsajax.lbl.commenttext"/>
        </label>
        <div class="col-sm-10 col-md-10 nopad">
        <form:textarea path="commentText" class="form-control" maxlength="255" rows="4"/>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group text-right">
        <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="submit"/>'/>
        <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal">
          <spring:message code="btn.close"/>
        </button>
      </div>
     
    </c:when>
    <c:otherwise>
      <div class="form-group">
        <br>
        <label class="form-group col-md-5">
          <spring:message code="commentsajax.lbl.commentid"/>
        </label>
        <div class="col-md-7 static-form-control">${commentsBean.commentId}</div>
        <div class="clearfix"></div>
      </div>
      
    <%--   	<c:choose>
                                            		<c:when test="${commentsBean.accountProfileBeanByAccountId.accountId > 5}">
                                            			</c:when>
                                            		<c:otherwise>
                                            			   <div class="form-group">
        <label class="form-group col-md-5">
          <spring:message code="commentsajax.lbl.departmentname"/>
        </label>
        <div class="col-md-7 static-form-control">	<spring:message code="department.${commentsBean.accountProfileBeanByAccountId.accountId}"/></div>
         <div class="clearfix"></div>
      </div>
                                            			
                                            		
                                            		</c:otherwise>
                                            	</c:choose> --%>
   
      
      <div class="form-group">
        <label class="form-group col-md-5">
          <spring:message code="commentsajax.lbl.commenttext"/>
        </label>
        <div class="col-md-7 static-form-control">${commentsBean.commentText}</div>
        <div class="clearfix"></div>
      </div>
      <div class="form-group">
        <label class="form-group col-md-5">
          <spring:message code="commentsajax.lbl.createddatetime"/>
        </label>
        <div class="col-md-7 static-form-control">
          <fmt:formatDate pattern='EEEE dd MMMM yyyy - hh:mm:ss a' value="${commentsBean.createdTimestamp}"/>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="form-group">
        <label class="form-group col-md-5">
          <spring:message code="commentsajax.lbl.feedback"/>
        </label>
        <div class="col-md-7 static-form-control">${commentsBean.feedback}</div>
        <div class="clearfix"></div>
      </div>
      <div class="form-group">
        <label class="form-group col-md-5">
          <spring:message code="commentsajax.lbl.commentstatus"/>
        </label>
        <div class="col-md-7 static-form-control"><label class="comment-status ${commentsBean.commentStatus}"><spring:message code="comment.stauts.${commentsBean.commentStatus}"/></label></div>
        <div class="clearfix"></div>
      </div>
      <div class="form-group text-right">
        <button type="button" class="btn btn-danger btn-lg button-right text-hightlight" data-dismiss="modal">
          <spring:message code="btn.close"/>
        </button>
      </div>
    </c:otherwise>
  </c:choose>
</form:form>
<script type="text/javascript">
  $(document).ready(function() {
      $("#commentsFrom").validate({
         debug: true,
         rules: {
       	  "departmentsBean.departmentId": {
       		  "required": true,
       	  },
             "commentText": {
                 "required": true,
                 "maxlength": 255
             }
         },
         messages: {
       	  "departmentsBean.departmentId": {
       		  "required": '<spring:message code="commentsajax.validation.department.required"/>',
       	  },
             "commentText": {
                 "required": '<spring:message code="commentsajax.validation.commentText.required"/>',
                 "maxlength" : '<spring:message code="commentsajax.validation.commentText.maxlength"/>'
             }
         },
         invalidHandler: function(event, validator) {
 
         },
         /* errorPlacement: function (error, element) {
         	error.insertAfter(element.parent(".form-group"));   
         }, */
         /* submitHandler: function(form) {
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
</script>