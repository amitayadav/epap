<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><spring:message code="seller.sellerinfopicture.header"/></li>
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
        <h3>
          <spring:message code="seller.sellerinfopicture.header"/>
        </h3>
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
    <div class="row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
          <div class="x-title">
            <%-- <h2><spring:message code="seller.sellerinfopicture.model.title"/></h2> --%>
            <div class="clearfix"></div>
          </div>
          <div class="x_panel">
            <c:if test="${not empty errorStack}">
			  <div class="error-stack">
			    <c:forEach items="${errorStack}" var="error">
			      <p class="error"><i class="fa fa-hand-o-right" aria-hidden="true"></i> ${error}</p>
			    </c:forEach>
			  </div>
			</c:if>
            <form:form action="${contextPath}/seller/syncsavesellerinfo" class="form-horizontal normalPageContentLoading" method="post" modelAttribute="sellerInfoBean" id="sellerInfoFrom" enctype="multipart/form-data">
              <spring:message code="seller.sellerinfopicture.sellerinfo1" var="sellerinfo1phl"/>
              <spring:message code="seller.sellerinfopicture.sellerinfo2" var="sellerinfo2phl"/>
              <div class="form-group">
                <form:hidden path="sellerId" value="${accountProfileBean.accountId}" />
                <form:hidden path="accountProfileBean.accountId" value="${accountProfileBean.accountId}" />
                <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>
                ${sellerinfo1phl}
                </label>
                 <div class="col-sm-10 col-md-10 nopad">
                <form:textarea class="form-control" path="infoLine1" rows="3" placeholder="${sellerinfo1phl}" maxlength="255" />
              </div>
              <div class="clearfix"></div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 col-md-2 control-label nolpad">
                ${sellerinfo2phl}
                </label>
                 <div class="col-sm-10 col-md-10 nopad">
                <form:textarea class="form-control" path="infoLine2" rows="3" placeholder="${sellerinfo2phl}" maxlength="255" />
              </div>
              <div class="clearfix"></div>
              </div>
              <div class="form-group text-right">
                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="btn.save"/>'/>
                <%-- <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value='<spring:message code="btn.reset"/>' formnovalidate="formnovalidate" /> --%>
               <input type="button" class="btn btn-danger btn-lg button-right text-hightlight" value='<spring:message code="btn.cancel"/>' id="changePasswordCancel" />
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
        <div class="x_title">
          <h2><spring:message code="seller.sellerinfopicture.model.title"/></h2>
          <button onclick="sellerPictureUpload(${accountProfileBean.accountId});" class="btn btn-warning btn-lg">
            <i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;
            <spring:message code="seller.sellerinfopicture.uploadpictures"/>
          </button>
          <div class="clearfix"></div>
        </div>
        <c:if test="${not empty sellerPicturesBeanList}">
          <div class="x_panel">
          <div class="seller-picture-thumbs">
          	<spring:message code="btn.remove" var="removebtn"/>
            <c:forEach items="${sellerPicturesBeanList}" var="sellerPicturesBean">
              <div class="seller-picture-thumb" id="img-${sellerPicturesBean.pictureId}">
                <img src="${contextPath}/setting/pictures/${sellerPicturesBean.accountProfileBean.accountId}/${sellerPicturesBean.pictureLocation}" onclick= "imgClick(this)" />
                <br>
                <button type="button" class="btn btn-danger btn-lg remove" data-toggle="tooltip" data-original-title="${removebtn}" onclick="imgDelete(${sellerPicturesBean.pictureId})">
                  <span class="glyphicon glyphicon-remove"></span>
                  <%-- <spring:message code="btn.remove"/> --%>
                </button>
              </div>
            </c:forEach>
            <div class="clearfix"></div>
          </div>
        </div>
        </c:if>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="sellerPictureModel" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-xlg"></div>
</div>


<script type="text/javascript">
  $(document).ready(function() {
      $("#sellerInfoFrom").validate({
          debug: true,
          rules: {
              "infoLine1": {
                  "required": true,
                   maxlength: 255
              },
              "infoLine2": {
                  maxlength: 255
              },
          },
          messages: {
              "infoLine1": {
                  "required": '<spring:message code="seller.sellerinfopicture.validation.info1.required"/>',
                  "maxlength" : '<spring:message code="seller.sellerinfopicture.validation.info1.maxlength"/>'
              },
              "infoLine2" : {
              	"maxlength" : '<spring:message code="seller.sellerinfopicture.validation.info2.maxlength"/>'
              },
          },
          invalidHandler: function(event, validator) {
  
          },
          errorPlacement: function(error, element) {
              error.insertAfter(element);
          },
          submitHandler: function(form) {
              form.submit();
              $(form).find("input[type=submit]").attr("disabled",true);
          }, 
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
//Offer Picture Zoom Click event.
  function imgClick(element){
    $("#largeImageDisplay #imageDisplay").attr("src",element.src);
    $("#largeImageDisplay").modal({show: true,backdrop: 'static',keyboard: false});  
}
  function sellerPictureUpload(accountId) {
      $.ajax({
          type: "GET",
          async: false,
          cache: false,
          url: (contextPath + "/seller/sellerPictureAjax/" + accountId),
          success: function(result) {
              $("#sellerPictureModel .modal-dialog").empty().html(result);
              $("#sellerPictureModel").modal({
                  show: true,
                  backdrop: 'static',
                  keyboard: false
              });
          },
          error: function(e) {
              $("#sellerPictureModel .modal-dialog").empty().html(e);
              $("#sellerPictureModel").modal({
                  show: true,
                  backdrop: 'static',
                  keyboard: false
              });
          }
      });
  }
  function imgDelete(id){
  if(confirm('<spring:message code="seller.sellerinfopicture.validation.deletemsg"/>'))
  	 $.ajax({
           type: "GET",
          // async: false,
           cache: false,
           url: (contextPath + "/seller/removepicture/" + id),
           success: function(result) {
          	 $("#img-"+id).remove();
           },
           error: function(e) {
          	 $("#img-"+id).remove();
           }
       });
  }
</script>