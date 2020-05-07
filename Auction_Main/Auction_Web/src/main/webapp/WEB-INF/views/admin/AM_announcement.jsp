
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li> <spring:message code="announcement.h3.announcement"/></li>
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
        <h3><spring:message code="announcement.h3.announcement"/></h3>
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
              <button class="btn btn-info btn-lg pull-right" id="addannouncement" data-id="0"><i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="btn.add"/></button>
               &nbsp;&nbsp; 
                                     
              <div class="clearfix"></div>
            </div>
            <div class="x_panel">
            <spring:message code="btn.edit" var="editBtn"/>
              <table id="datatable" class="table table-striped table-bordered table_dayauction">
               <thead>
                   <tr>
                    <th width="20px">#</th>
                    <th><spring:message code="announcement.h3.announcement"/></th>
                    <th width="30px" class="disable-sorting btn-lg">${editBtn}</th>
                   <th width="45px" class="disable-sorting btn-lg"> <spring:message code="btn.remove" />  </th>
                   <th width="45px" class="disable-sorting btn-lg"> <spring:message code="btn.hide"/></th>
                           </tr>
                 
                </thead>
                <tbody>
                  <c:forEach items="${announcementBeanList}" var="announcementlist" varStatus="status">
                    <tr>
                    <td>${status.count}</td>
                    <td class="text-center">${announcementlist.announcement}</td>
                    <td class="text-center">
                        <button data-id="${announcementlist.announcementId}" class="btn btn-warning btn-sm addannouncement"  data-toggle="tooltip" data-original-title="${editBtn}">
                        
                        <i class="fa fa-edit"></i>
                        </button>
                        
                      </td>
                    <td>
                     <button type="button" class="btn btn-danger btn-lg remove"  onclick="deleteItem(${announcementlist.announcementId})"  data-toggle="tooltip" data-original-title='<spring:message code="buyer.deleteannouncement.button"/>'  id="deleteitem" name="deleteitem">
                            <i class="glyphicon glyphicon-remove"></i>
                          </button> 
                            </td>
                            <c:choose>
                          <c:when test="${announcementlist.modifiedimestamp >= modifiedimestamp}">
                                  <td><button  class="btn btn-success"  onclick="hide(${announcementlist.announcementId})"  class="hide" name="hide">
                             <spring:message code="btn.hide"/>
                             </button> 
                             </td>                      
                        		 </c:when>
                             <c:otherwise>
                                <td>
                                <button  class="btn btn-warning"  disabled="disabled" class="show" name="show">
                             <spring:message code="btn.hidden"/> 
                          </button>
                                </td>
                               </c:otherwise>
                             </c:choose>
                         
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
  
  <div class="clearfix"></div>

<div class="modal fade" id=announcementModel tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
	  window.history.pushState("object or string", "EPAP|announcement", contextPath + "/sproptrel/syncannouncement"); 
	 $('#datatable').DataTable({
    	  language: languageList,
    	  columnDefs: [{orderable: false, targets: 'disable-sorting'}]
      });
      
      function announcementForm(id){
      	if(id > 0){
      		$("#announcementModel .modal-title").html("<spring:message code='announcement.h4.announcemen.update'/>");
      	}else{
      		$("#announcementModel .modal-title").html("<spring:message code='announcement.h4.announcemen'/>");
      	}
      	
      	$.ajax({
    	        type: "GET",
    	        async: false,
    	        cache: false,
    	        url: (contextPath+"/sproptrel/addannouncement/"+id),
    	        success: function(result) {
    	        	$("#announcementModel .modal-body").empty().html(result);
    	        	$("#announcementModel").modal({
    	        		show:true,
    	        		backdrop: 'static',
    	          	    keyboard: false
    	              });
    	        	
    	        },error: function(e){
    	        	$("#announcementModel .modal-body").empty().html(e);
    	        	$("#announcementModel").modal({
    	        		show:true,
    	        		backdrop: 'static',
    	          	    keyboard: false
    	              });
    	        }
    	    }); 
      }
      $("#addannouncement").click(function(e){
      	e.preventDefault();
      	announcementForm($(this).data("id"));
      });
      $("#datatable").on("click","tr .addannouncement",function(e){
      	e.preventDefault();
      	announcementForm	($(this).data("id"));
      });
  });
  
  function deleteItem(announcementId){
	  var confirmReply=confirm("<spring:message code="announcement.deleteannouncement.msg"/>");
	  if(confirmReply){
		 
		  $.ajax({
	          type: "POST",
	          async: false,
	          cache: false,
	          data: {
	        	  announcementId: announcementId
	          },
	          url: (contextPath + "/sproptrel/deleteannouncement/"+announcementId),
	          success: function (result) {
	        	  window.location.href = contextPath + "/sproptrel/syncannouncement";
	          },
	          error: function (e) {
	              if (403 == e.status) {
	            	  console.log("error");
	                  
	              }
	              
	          }
	      }); 
	  }
	 
  }
     
  function hide(announcementId){
	  var confirmReply=confirm("<spring:message code="announcement.hide.msg"/>");

	  if(confirmReply){
		  $.ajax({
	          type: "POST",
	          async: false,
	          cache: false,
	          data: {
	        	  announcementId: announcementId
	          },
	          url: (contextPath + "/sproptrel/hideannouncement/"+announcementId),
	          success: function (result) {
	        	
	        	  window.location.href = contextPath + "/sproptrel/syncannouncement";
	        	 
	        	
	          },
	          error: function (e) {
	              if (403 == e.status) {
	            	  console.log("error");
	                  
	              }
	              
	          }
	      }); 
	  }
	 
  }
 
</script>

