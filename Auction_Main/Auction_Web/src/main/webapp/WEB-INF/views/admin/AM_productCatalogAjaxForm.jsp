<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.auction.commons.enums.ENUM_ProductCatalogStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:choose>
  <c:when test="${not empty VIEWONLY && VIEWONLY}">
   <div class="form-group">
      <label class="form-group col-md-5"><spring:message code="productcatalogajaxform.lbl.groupname"/></label>        
      <div class="col-md-7 static-form-control">${productCatalogBean.productGroupName}</div>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="form-group col-md-5"><spring:message code="productcatalogajaxform.lbl.productname"/></label>
      <div class="col-md-7 static-form-control">${productCatalogBean.productName}</div>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="form-group col-md-5"><spring:message code="productcatalogajaxform.lbl.typename"/></label>        
      <div class="col-md-7 static-form-control">${productCatalogBean.productTypeName}</div>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="form-group col-md-5"><spring:message code="productcatalogajaxform.lbl.description"/></label>        
      <div class="col-md-7 static-form-control">${productCatalogBean.productDescription}</div>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="form-group col-md-5"><spring:message code="productcatalogajaxform.lbl.containerspecifications"/></label>
      <div class="col-md-7 static-form-control">${productCatalogBean.containerSpecs}</div>
      <div class="clearfix"></div>
    </div>
    <div class="form-group">
      <label class="form-group col-md-5"><spring:message code="productcatalogajaxform.lbl.status"/></label>
      <p class="col-md-7 static-form-control">
       <spring:message code="prodcut.status.${productCatalogBean.status}"/>
      </p>
    </div>
    <div class="form-group text-right">
      <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>
    </div>
  </c:when>
  <c:otherwise>
    <form:form action="${contextPath}/spropt/saveproductcatalog" method="post" class="form-horizontal" modelAttribute="productCatalogBean" id="productCatalogFrom" enctype="multipart/form-data">
       <p class="text-danger"><spring:message code="required.msg"/></p>
        <div class="form-group">
        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>
        <spring:message code="productcatalogajaxform.lbl.groupname"/>
        </label>
        <div class="col-sm-10 col-md-10 nopad">
        <form:hidden path="productId" id="productId"/>
        <form:hidden path="productGroupName" id="productGroupName"/>
        <c:if test="${not empty productGroupNameList}">
          <select name="productGroupNameBox" class="form-control" id="productGroupNameBox">
            <option value=""><spring:message code="productcatalogajaxform.lbl.selectgroupname"/></option>
            <c:forEach items="${productGroupNameList}" var="groupName">
              <c:choose>
                <c:when test="${groupName eq productCatalogBean.productGroupName}">
                  <option value="${groupName}" selected="selected">${groupName}</option>
                </c:when>
                <c:otherwise>
                  <option value="${groupName}">${groupName}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
            <option value="other"><spring:message code="productcatalogajaxform.select.option.other"/></option>
          </select>
        </c:if>
        <spring:message code="productcatalogajaxform.input.productgroupname" var="productgroupnamePhl"/>
        <input type="text" class="form-control ${not empty productGroupNameList ? 'hidden' : ''}" name="productGroupNameTxt" id="productGroupNameTxt" maxlength="20" placeholder="${productgroupnamePhl}"/>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group">
      <spring:message code="productcatalogajaxform.lbl.productname" var="productnamePhl"/>
        <label class="col-sm-2 col-md-2 control-label nolpad">
        <span class="required"> * </span>${productnamePhl}
        </label>
        <div class="col-sm-10 col-md-10 nopad">
        <form:input class="form-control" path="productName" id="productName" maxlength="20" placeholder="${productnamePhl}"/>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 col-md-2 control-label nolpad">
        <span class="required"> * </span><spring:message code="productcatalogajaxform.lbl.typename"/></label>        
         <div class="col-sm-10 col-md-10 nopad">
        <form:hidden path="productTypeName" id="productTypeName"/>
        <c:if test="${not empty productTypeNameList}">
          <select name="productTypeNameBox" class="form-control" id="productTypeNameBox">
            <option value=""><spring:message code="productcatalogajaxform.select.selecttypename"/></option>
            <c:forEach items="${productTypeNameList}" var="typeName">
              <c:choose>
                <c:when test="${typeName eq productCatalogBean.productTypeName}">
                  <option value="${typeName}" selected="selected">${typeName}</option>
                </c:when>
                <c:otherwise>
                  <option value="${typeName}">${typeName}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
            <option value="other"><spring:message code="productcatalogajaxform.select.option.other"/></option>
          </select>
        </c:if>
        <spring:message code="productcatalogajaxform.select.producttypename" var="producttypenamePhl"/>
        <input type="text" class="form-control ${not empty productTypeNameList ? 'hidden' : ''}" name="productTypeNameTxt" id="productTypeNameTxt" maxlength="20" placeholder="${producttypenamePhl}"/>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group">
      <spring:message code="productcatalogajaxform.lbl.description" var="descriptionPhl"/>
        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${descriptionPhl}</label>        
        <div class="col-sm-10 col-md-10 nopad">
        <form:textarea class="form-control" path="productDescription" id="productDescription" maxlength="255"  placeholder="${descriptionPhl}"></form:textarea>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group">
       <spring:message code="productcatalogajaxform.lbl.containerspecifications" var="containerspecificationsPhl"/>
        <label class="col-sm-2 col-md-2 control-label nolpad"><span class="required"> * </span>${containerspecificationsPhl}</label>  
        <div class="col-sm-10 col-md-10 nopad">      
        <form:textarea class="form-control" path="containerSpecs" id="containerSpecs" maxlength="255"  placeholder="${containerspecificationsPhl}"></form:textarea>
      </div>
      <div class="clearfix"></div>
      </div>
      <div class="form-group">
        <form:select path="status" class="form-control" >
            <form:option value=""><spring:message code="productcatalogajaxform.select.selectproductstatus"/></form:option>
            <c:forEach items="${productStatusList}" var="productStatus">
                  <form:option value="${productStatus.key}" ><spring:message code="prodcut.status.${productStatus.key}"/></form:option>
            </c:forEach>
          </form:select>
      </div>
      <div class="form-group text-right">
      <spring:message code="btn.update" var="updateBtn"/>
        <spring:message code="btn.save" var="saveBtn"/>
        <input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="${not empty productCatalogBean.productId ? updateBtn : saveBtn}"/>    
        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="btn.close"/></button>
      </div>
    </form:form>
    <script type="text/javascript">
      $(document).ready(function() {
      
      $("#productGroupNameBox").on("change",function(){
	    if($(this).val().toLowerCase() == "other"){
	      //$("#productGroupNameBox").addClass("hidden");
	      $("#productGroupNameTxt").removeClass("hidden");
	      $("#productGroupNameTxt").focus();
	      $("#productGroupNameTxt").rules("add", {
	  	      required        : true,
	  	      maxlength		  : 20,
	  	      messages        : 
	  	      {
	  	          required    : "<spring:message code='productcatalogajaxform.validation.productGroupNameTxt.required'/>",
	  	          maxlength	  : "<spring:message code='productcatalogajaxform.validation.productGroupNameTxt.maxlength'/>"
	  	      }
	  	  });
	    }else{
	      //$("#productGroupNameBox").removeClass("hidden");
	      $("#productGroupNameTxt").addClass("hidden");
	      $("#productGroupNameTxt").rules("remove");
	      $("#productGroupName").val($(this).val());
	    }
	    $("#productCatalogFrom").validate().resetForm();
      });
      
      $("#productGroupNameTxt").on("blur",function(){
    	$("#productGroupName").val($(this).val()); 
      });
      
      $("#productTypeNameBox").on("change",function(){
  	    if($(this).val().toLowerCase() == "other"){
  	      //$("#productGroupNameBox").addClass("hidden");
  	      $("#productTypeNameTxt").removeClass("hidden");
  	      $("#productTypeNameTxt").focus();
  	      $("#productTypeNameTxt").rules("add", {
	  	      required        : true,
	  	      maxlength		  : 20,
	  	      messages        : 
	  	      {
	  	          required    : "<spring:message code='productcatalogajaxform.validation.productTypeNameTxt.required'/>",
	  	          maxlength	  : "<spring:message code='productcatalogajaxform.validation.productTypeNameTxt.maxlength'/>"
	  	      }
	  	  });
  	    }else{
  	      //$("#productGroupNameBox").removeClass("hidden");
  	      $("#productTypeNameTxt").addClass("hidden");
  	      $("#productTypeNameTxt").rules("remove");
  	      $("#productTypeName").val($(this).val());
  	    }
  	    $("#productCatalogFrom").validate().resetForm();
      });
      
      $("#productTypeNameTxt").on("blur",function(){
      	$("#productTypeName").val($(this).val()); 
        });
      
      	 $("#productCatalogFrom").validate({
       		debug: true,
       		rules: {
       			"productName" : {"required": true, maxlength: 20},
       			"productGroupNameBox" : {"required": ($("#productGroupNameTxt").hasClass("hidden"))},
       			//"productGroupNameTxt" : {"required": ("other" == $("#productGroupNameBox option:selected").val()), maxlength: 20},
       			"productTypeNameBox" : {"required": ($("#productTypeNameTxt").hasClass("hidden"))},
       			//"productTypeNameTxt" : {"required": ("other" == $("#productTypeNameBox option:selected").val()), maxlength: 20},
       			"productDescription" : {"required": true, maxlength: 255},
       			"status" : {"required": true},
       			"containerSpecs" : {"required": true, maxlength: 255}
       		},
       		messages:{
       			"productName" : {
      				"required": "<spring:message code='productcatalogajaxform.validation.productName.required'/>",
      				"maxlength": "<spring:message code='productcatalogajaxform.validation.productName.maxlength'/>"
      			},
      			"productGroupNameBox" : {
      				"required": "<spring:message code='productcatalogajaxform.validation.productGroupNameTxt.required'/>"
      			},
      			"status" : {
      				"required": "<spring:message code='productcatalogajaxform.validation.status.required'/>",
      			},
      			"productTypeNameBox" : {
      				"required": "<spring:message code='productcatalogajaxform.validation.productTypeNameTxt.required'/>"
      			},
      			"productDescription" : {
      				"required": "<spring:message code='productcatalogajaxform.validation.productDescription.required'/>",
      				"maxlength": "<spring:message code='productcatalogajaxform.validation.productDescription.maxlength'/>"
      			},
      			"containerSpecs" : {
      				"required": "<spring:message code='productcatalogajaxform.validation.containerSpecs.required'/>",
      				"maxlength": "<spring:message code='productcatalogajaxform.validation.containerSpecs.maxlength'/>"
      			}
       		},
       		invalidHandler: function(event, validator) {
       			
       		},
       		/* submitHandler: function(form) {
       			form.submit();
                $(form).find("input[type=submit]").attr("disabled",true);
       		}, */
       		highlight : function(element) {
       			$(element).addClass("error-element");
       		},
       		unhighlight : function(element) {
       			$(element).removeClass("error-element");
       		}
       	});
      	 
      	<c:if test="${empty productGroupNameList}">
      	  $("#productGroupNameTxt").rules("add", {
	  	      required        : true,
	  	      maxlength		  : 20,
	  	      messages        : 
	  	      {
	  	          required    : "<spring:message code='productcatalogajaxform.validation.productGroupNameTxt.required'/>",
	  	          maxlength	  : "<spring:message code='productcatalogajaxform.validation.productGroupNameTxt.maxlength'/>"
	  	      }
	  	  });
      	</c:if>
      	<c:if test="${empty productTypeNameList}">
	      	$("#productTypeNameTxt").rules("add", {
		  	      required        : true,
		  	      maxlength		  : 20,
		  	      messages        : 
		  	      {
		  	          required    : "<spring:message code='productcatalogajaxform.validation.productTypeNameTxt.required'/>",
		  	          maxlength	  : "<spring:message code='productcatalogajaxform.validation.productTypeNameTxt.maxlength'/>"
		  	      }
		  	  });
      	</c:if>
      });
    </script>
  </c:otherwise>
</c:choose>