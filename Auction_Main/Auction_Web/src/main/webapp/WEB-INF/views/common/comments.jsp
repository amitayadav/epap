<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="com.auction.commons.enums.ENUM_CommentsStatus"%>

<c:set value="<%=ENUM_CommentsStatus.PENDING.getDesc() %>" var="COMMENT_PENDING"/>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="comments.h2.comments"/></li>
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
                <h3><spring:message code="comments.h2.comments"/></h3>
            </div>
        </div>
        <div class="clearfix"></div>
        <c:if test="${not empty ERROR}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text">
					<i class="icon fa fa-warning"></i> ${ERROR}
				</h4>
            </div>
        </c:if>
        <c:if test="${not empty SUCCESS}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 class="title-text">
					<i class="icon fa fa-check"></i> ${SUCCESS}
				</h4>
            </div>
        </c:if>
        <div class="x_content">
            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x-title">
                         <div class="col-sm-10 col-md-10 nopad">
                  <div class="radio">
                  <label class="col-sm-1">                    
                  	<spring:message code="comments.th.commentFilter.title" />
                  </label>
                  <form action="${contextPath}/setting/comments" name="commentForm" id="commentform_id" method="get">
              
                    <c:choose>
                    	<c:when test="${commentFilter eq 'inbox'}">
                    	 <input type="radio" name="commentFilter" value="inbox" id="inbox"  checked="checked" /> 
                    	 <label for="inbox"><spring:message code="comments.th.commentFilter.inbox"/></label> 
                    	</c:when>
                    	<c:otherwise>
                    	 <input type="radio" name="commentFilter" value="inbox" id="inbox" />  
                    	 <label for="inbox"><spring:message code="comments.th.commentFilter.inbox"/></label>
                    	</c:otherwise>
                    </c:choose>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                       <c:choose>
                    	<c:when test="${commentFilter eq 'sent'}">
                    	 <input type="radio" name="commentFilter" value="sent" id="sent" checked="checked"  />
                    	    <label for="sent"><spring:message code="comments.th.commentFilter.sent"/></label>
                    	</c:when>
                    	<c:otherwise>
                    		 <input type="radio" name="commentFilter" value="sent" id="sent"  />
                    		    <label for="sent"><spring:message code="comments.th.commentFilter.sent"/></label>
                    	</c:otherwise>
                    </c:choose>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                     
                     <c:choose>
                    	<c:when test="${commentFilter eq 'both'}">
                    	  <input type="radio" name="commentFilter" value="both"  id="both" checked="checked"  />  
                    	    <label for="both"><spring:message code="comments.th.commentFilter.both"/></label>
                    	</c:when>
                    	<c:otherwise>
                      <input type="radio" name="commentFilter" value="both"  id="both"  />  
                        <label for="both"><spring:message code="comments.th.commentFilter.both"/></label>
                    	</c:otherwise>
                    </c:choose>              
                  </form>                  
                  </div>
                  </div>
                    <button class="btn btn-info btn-sm pull-right" onclick="addComment()"><i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="btn.add"/></button>
                            <div class="clearfix"></div>
                  </div>
                  <div class="x_panel table-responsive">
                        	<spring:message code="btn.edit" var="editPhl"/>
                        	<spring:message code="btn.view" var="viewPhl"/>
                            <table id="datatable" class="table table-striped table-bordered table_dayauction">
                                <thead>
                                    <tr>
                                        <th><spring:message code="comments.th.commentid"/></th>
                                        <th><spring:message code="comments.th.commentby"/></th>
                                        <th><spring:message code="comments.th.commentto"/></th>                            
                                        <th><spring:message code="comments.th.commentdateandtime"/></th>
                                        <th><spring:message code="comments.th.commentstatus"/></th>
                                        <th class="disable-sorting">${viewPhl}/${editPhl}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <c:forEach items="${commentslist}" var="comment" varStatus="status">
                                        <tr>
                                            <td align="center">${status.count}</td>
                                               <td align="center">
                                               
                                            	<c:choose>
                                            		<c:when test="${comment.accountProfileBeanByCreatedBy.accountId > 5}">
                                            			${comment.accountProfileBeanByCreatedBy.fullName}(${comment.accountProfileBeanByCreatedBy.publicName})
                                            		</c:when>
                                            		<c:otherwise>
                                            			<spring:message code="department.${comment.accountProfileBeanByCreatedBy.accountId}"/>
                                            		</c:otherwise>
                                            	</c:choose>
                                            </td>
                                            <td align="center">
                                            	<c:choose>
                                            		<c:when test="${comment.accountProfileBeanByAccountId.accountId > 5}">
                                            			${comment.accountProfileBeanByAccountId.fullName}(${comment.accountProfileBeanByAccountId.publicName})
                                            		</c:when>
                                            		<c:otherwise>
                                            			<spring:message code="department.${comment.accountProfileBeanByAccountId.accountId}"/>
                                            		</c:otherwise>
                                            	</c:choose>
                                            </td>
                                          
                                            <td align="center"><fmt:formatDate pattern='dd/MM/yyyy hh:mm:ss a' value="${comment.createdTimestamp }"/></td>
                                            <td class="text-bold text-uppercase text-center"><label class="label comment-status ${comment.commentStatus}"><spring:message code="comment.stauts.${comment.commentStatus}"/></label></td>
                                            <td class="text-center">
													      <c:choose>
                                                    <c:when test="${comment.commentStatus eq COMMENT_PENDING }">                                                                                                 
                                                         <c:if test="${comment.accountProfileBeanByCreatedBy.accountId eq loginUser.accountProfileBean.accountId }">
                                                               <button  onclick="editOrViewcomment(${comment.commentId})" class="btn btn-warning btn-sm" data-toggle="tooltip" data-original-title="${editPhl}">
                                                        	<i class="fa fa-edit"></i>
                                                        </button>
                                                         </c:if>
                                                         <c:if test="${comment.accountProfileBeanByCreatedBy.accountId ne loginUser.accountProfileBean.accountId }">
                                                               <button onclick="viewcomment(${comment.commentId})" class="btn btn-info btn-sm " data-toggle="tooltip" data-original-title="${viewPhl}">
                                                            <i class="fa fa-eye"></i>
                                                        </button>                                                         
                                                         </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                    	<button onclick="editOrViewcomment(${comment.commentId})" class="btn btn-info btn-sm " data-toggle="tooltip" data-original-title="${viewPhl}">
                                                            <i class="fa fa-eye"></i>
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose> 
                                            </td>
                                        </tr>
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
<div class="modal fade" id="commentModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title"><spring:message code="comments.h4.addcomment"/></h4>
            </div>
            <div class="modal-body">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
  $(document).ready(function() {
	  
	
	  $('.radio :RADIO').change(function() {
		         $( "#commentform_id" ).submit();
   	   });
		 
	 
	  
	  languageList.sEmptyTable = '<spring:message code="comments.emptytable"/>';
      $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
      window.history.pushState("object or string", "EPAP|Comment", contextPath+"/setting/commentsList");
  });
   function addComment(){
	   $.ajax({
	        type: "GET",
	        async: false,
	        cache: false,
	        url: (contextPath+"/setting/addcomment"),
	        success: function(result) {
	        	$("#commentModel .modal-body").empty().html(result);
	        	$("#commentModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
	        },error: function(e){
	        	$("#addAuctionModel .modal-body").empty().html(e);
	        	$("#addAuctionModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
	        }
	    }); 
  } 
   function editOrViewcomment(id){
	   $.ajax({
	        type: "GET",
	        async: false,
	        cache: false,
	        url: (contextPath+"/setting/editcomment/"+id),
	        success: function(result) {
	        	$(".modal-title").text('<spring:message code="comments.h4.updatecomment"/>')
	        	$("#commentModel .modal-body").empty().html(result);
	        	$("#commentModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
	        },error: function(e){
	        	$("#commentModel .modal-body").empty().html(e);
	        	$("#commentModel").modal({
	        		show:true,
	        		backdrop: 'static',
	          	    keyboard: false
	              });
	        }
	    }); 
  }
   function viewcomment(id){
	   
   	   $.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: (contextPath+"/setting/viewcomment/"+id),
        success: function(result) {
        	$(".modal-title").text('<spring:message code="comments.h4.updatecomment"/>')
        	$("#commentModel .modal-body").empty().html(result);
        	$("#commentModel").modal({
        		show:true,
        		backdrop: 'static',
          	    keyboard: false
              });
        },error: function(e){
        	$("#commentModel .modal-body").empty().html(e);
        	$("#commentModel").modal({
        		show:true,
        		backdrop: 'static',
          	    keyboard: false
              });
        }
    }); 
}
</script>