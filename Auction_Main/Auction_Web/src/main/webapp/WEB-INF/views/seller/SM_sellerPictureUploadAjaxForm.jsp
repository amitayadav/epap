<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<form action="${contextPath}/seller/syncuploadsellerpicture" method="post" enctype="multipart/form-data" class="normalPageContentLoading" id="frmsellerPicture">
 
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">x</button>
            <h3 class="modal-title"><spring:message code="seller.sellerpictureupload.modal.tital"/></h3>
        </div>
        <div class="modal-body">
            <div class="form-group text-right">
                <button class="btn btn-lg btn-warning button-left" id="uploadNewImage"><i class="fa fa-plus"></i>&nbsp;&nbsp;<b><spring:message code="btn.add"/></b></button>
            </div>
            <div class="seller-picture-container nopad text-center">
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="modal-footer text-right">
            <button type="submit" class="btn btn-success btn-lg button-left text-hightlight text-hightlight"><i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="btn.upload"/></button>
            <button type="button" class="btn btn-danger btn-lg button-right text-hightlight text-hightlight" data-dismiss="modal"><i class="fa fa-remove"></i>&nbsp;&nbsp;<spring:message code="btn.close"/></button>
        </div>
    </div>
</form>
<script type="text/javascript">
    function afterAddEffect() {
        var dataId = $(".seller-picture .seller-picture-details").length;
        if ((dataId != 0) && ("" == $(".seller-picture input[id^=sellerPicture]:last").val())) {
            $(".seller-picture input[id^=sellerPicture]:last").parents(".seller-picture-details").addClass("error-element")
        } else {
            dataId = dataId + 1;
            var sellerPictureDetails = '<div class="seller-picture"><div class="seller-picture-details" data-id="' + dataId + '">' +
                '<button class="remove label label-danger">&times;</button>' +
                '<div class="form-group text-center">' +
                '<input id="sellerPicture' + dataId + '" data-min-file-count="0" data-show-caption="false" class="file" type="file" name="sellerPicture" accept="image/*"/>' +
                '</div>' +
                '</div><label class="error"><spring:message code="seller.sellerpictureupload.validation.selectimage"/></label></div>';
            $(".seller-picture-container").append(sellerPictureDetails);
            $((".seller-picture #sellerPicture" + dataId)).fileinput({
                removeClass: "btn btn-danger btn-sm mt5",
                removeLabel: "",
                showRemove: false,
                browseLabel: "&nbsp;<spring:message code='browse'/>&nbsp;...",
                browseClass: "btn btn-info btn-lg mt5 "
            });
            $((".seller-picture #sellerPicture" + dataId)).on("change", function() {
                $(this).parents(".seller-picture-details").removeClass("error-element");
            });

            $(".seller-picture-details[data-id=" + dataId + "] .remove").on("click", function(e) {
                e.preventDefault();
                if (confirm('<spring:message code="seller.sellerpictureupload.validation.deletemsg"/>')) {
                    $(this).parents(".seller-picture").remove();
                }
            });
        }
    }
    $(document).ready(function() {
        afterAddEffect();
        $("#uploadNewImage").on("click", function(e) {
            e.preventDefault();
            afterAddEffect();
        });
        $.validator.addMethod('filesize', function(value, element, param) {
     	   return this.optional(element) || (element.files[0].size <= param) 
     	});   
 $("#frmsellerPicture").validate({
     debug: true,
     rules: {
         "sellerPicture": {
         	 "required": true,
              	filesize: 4194304,
  	            accept: {
  	                type: "image",
  	                extension: "png|jpg|jpeg|PNG|JPG"
  	            }
  	          
         }
         },
     messages: {
         "sellerPicture": {
         	"required":  '<spring:message code="seller.auctionofferplacing.lbl.validation.selectimage"/>' ,
        	   "accept": '<spring:message code="editProfile.validation.image.accept"></spring:message>',
        	   "filesize":'<spring:message code="seller.sellerpictureupload.file.size"></spring:message>',
         }
     },
     errorPlacement: function (error, element) {
         if ($(element).attr("type") == "file") {
             error.insertAfter(element.parents(".file-input"));
         } else {
             error.insertAfter(element);
         }
     },
     submitHandler: function (form) {
         form.submit();
         $(form).find("input[type=submit]").attr("disabled", true);
     },
     highlight: function (element) {
         if ($(element).attr("type") == "file") {
             $(element).parents(".file-input").find(".file-drop-disabled").addClass("error-file-element");
         } else {
             $(element).addClass("error-element");
         }
     },
     unhighlight: function (element) {
         if ($(element).attr("type") == "file") {
             $(element).parents(".file-input").find(".file-drop-disabled").removeClass("error-file-element");
         } else {
             $(element).removeClass("error-element");
         }
     }
 });
    });
</script>