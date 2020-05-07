<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="right_col" role="main">
  <ul class="breadcrumb">
    <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
    <li><a href="${contextPath}/ssa/auctionrequestlist"><spring:message code="menu.li.sellerAuctionRequest"/></a></li>
    <li><spring:message code="seller.auctionrequest.header"/></li>
  </ul>
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3><spring:message code="seller.auctionrequest.header"/></h3>
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
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x-title">
                        <p class="text-danger"> <spring:message code="required.msg"/></p>
                        <%-- <h2><spring:message code="seller.auctionrequest.box.header"/></h2> --%>
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
                        <form:form action="${contextPath}/ssa/auctionrequest" method="post" class="form-horizontal" modelAttribute="auctionRequestBean" id="auctionRequestFrom" enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label nolpad">
                                <spring:message code="seller.auctionrequest.lbl.selproduct"/>
                                </label>
                                <div class="col-sm-10 col-md-10 nopad"> 
                                <form:select path="productCatalogBean.productId" class="form-control">
                                    <option value=""><spring:message code="seller.auctionrequest.lbl.selproduct"/></option>
                                    <c:forEach items="${productCatalogList}" var="product">
                                        <c:choose>
                                         <c:when test="${productCatalogBean.productId eq product.productId}">
                                                <form:option value="${product.productId}" selected="selected">${product.productName} (${product.productTypeName})</form:option>
                                            </c:when>
                                            <c:otherwise>
                                                <form:option value="${product.productId}">${product.productName} (${product.productTypeName})</form:option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="clearfix"></div>
                            </div>
                            <div class="form-group">
                            	<spring:message code="seller.auctionrequest.lbl.comment" var="commentphl"/>
                                <label class="col-sm-2 col-md-2 control-label nolpad">
                                <span class="required"> * </span>${commentphl}</label>
                                <div class="col-sm-10 col-md-10 nopad">      
                                <form:textarea class="form-control" path="sellerComment" placeholder="${commentphl}" maxlength="500" />
                            </div>
                            <div class="clearfix"></div>
                            </div>
                            <div class="form-group text-right">
                                <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="seller.auctionrequest.btn.request"/>'/>
                                <input type="reset" class="btn btn-danger btn-lg button-right text-hightlight" id="clearAll" value='<spring:message code="btn.reset"/>' formnovalidate="formnovalidate" />
                            </div>
                            <form:hidden path="requestId" />
                            <form:hidden path="createdOn" />
                            <form:hidden path="status" />
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("#auctionRequestFrom").validate({
            debug: true,
            rules: {
                "productCatalogBean.productId": {
                    "required": true
                },
                "sellerComment" :{
                	"required": true,
                	 "maxlength" : 500
                }
            },
            messages: {
                "productCatalogBean.productId": {
                    "required": '<spring:message code="seller.auctionrequest.validation.productId.required"/>'
                },
                "sellerComment":{
                	"required" : '<spring:message code="seller.auctionrequest.validation.sellerComment.required"/>',
                	"maxlength" : '<spring:message code="seller.auctionrequest.validation.sellerComment.maxlength"/>'
                }
            },
            invalidHandler: function(event, validator) {

            },
            /* errorPlacement: function (error, element) {
  				error.insertAfter(element.parent(".form-group"));   
  			},*/  
            /* submitHandler: function(form) {
                //form.submit();
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