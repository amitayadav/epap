<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${ratingsLog.logId eq null}">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span></button>
			<h1 class="modal-title"> <spring:message code="rating.seller.lbl"/></h1> 
			<spring:message code="rating.poor.lbl"/>  ,  <spring:message code="rating.excellent.lbl"/>
	 </div>
 </c:if>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<input type="hidden" id="accountSellerId" name="accountSellerId"  value="${accountSellerId}" />
<input type="hidden" id="accountBuyerId" name="accountBuyerId"  value="${accountBuyerId}"/>
<input type="hidden" id="dailyAuctionsId" name="dailyAuctionsId"  value="${dailyAuctionsId}"/>	
<c:choose>
	<c:when test="${ratingsLog.logId eq null}">
		<div class="modal-body">
       		<div class="col-md-12 col-sm-12">
	          <div class="form-group user-rating">
			    	<div class="rating-bar">
						<a class="rating-star-block"><strong class="rating-star fa fa-star-o"   id="s1"><span class="rating-number" >1</span></strong></a>
						<a class="rating-star-block" ><strong class="rating-star fa fa-star-o" id="s2"><span class="rating-number" >2</span></strong></a>
						<a class="rating-star-block" ><strong class="rating-star fa fa-star-o" id="s3"><span class="rating-number" >3</span></strong></a>
						<a class="rating-star-block" ><strong class="rating-star fa fa-star-o"  id="s4"><span class="rating-number">4</span></strong></a>
						<a class="rating-star-block" ><strong class="rating-star fa fa-star-o"  id="s5"><span class="rating-number">5</span></strong></a>
					    <div class="clearfix"></div>
			    	</div>
			    </div>
			    <div class="form-group">
			    	<textarea  class="form-control editable-field"  name="comment"  id="comment" placeholder="<spring:message code="rating.msg.Comments"/>"  rows = "2" cols = "2"></textarea>
			    </div>
		  </div>
		  <div class="clearfix"></div>
		</div>
		<div class="modal-footer text-right">
			<button type="submit" class="btn btn-success btn-lg" id="sellerRate"> <i class="fa fa-check"></i>&nbsp;&nbsp;
				<spring:message code="btn.save" /> 
			</button>
  		</div>
  		<script>
			$(document).ready(function(){
				$(".rating-bar .rating-star-block .rating-star").on("click",function(){
					if($(this).hasClass("fa-star-o")){
						$(this).removeClass("fa-star-o").addClass("fa-star");
						$(this).parents(".rating-star-block").prevUntil().find(".rating-star").removeClass("fa-star-o").addClass("fa-star");
						$(this).parents(".rating-star-block").nextAll().find(".rating-star").removeClass("fa-star");
					}else{
						$(this).parents(".rating-star-block").nextAll().find(".rating-star").removeClass("fa-star").addClass("fa-star-o");
					}
				});
		
				 $('#sellerRate').on('click', function () {
					var selleraccountId= document.getElementById("accountSellerId").value;
					var buyeraccountId= document.getElementById("accountBuyerId").value;
					var dailyAuctionsId= document.getElementById("dailyAuctionsId").value;
					var comment= document.getElementById("comment").value;
					var rateNumber = $(".rating-bar .rating-star-block .rating-star.fa-star").size();
		             $.ajax({
		                 type: "POST",
		                 async: false,
		                 cache: false,
		                 data: {
		                	 selleraccountId:selleraccountId,
		                	 buyeraccountId:buyeraccountId,
		                	 dailyAuctionsId:dailyAuctionsId,
		                	 comment:comment,
		                	 rateNumber:rateNumber
		                 },
		                 url: (contextPath + "/ssa/saveSellerRate"),
		                 success: function (result) {
		                	   $("#rateSellerModel").modal("hide");
		                 },
		                 error: function (e) {
		                     console.log(e);
		                     $("#rateSellerModel").modal("hide");
		                 }
		             });
		         });
				
			});
		</script>
	</c:when>
	<c:otherwise>
		<div class="modal-body">
			<div class="col-md-12 col-sm-12">
				<p class="f25 text-danger"> <spring:message code="rating.seller.msg"/></p>
			</div>
			<div class="col-md-12 col-sm-12">
		          <div class="form-group user-rating">
				    	<div class="rating-bar">
				    		<c:forEach begin="1" end="5" var="idx">
				    			<a class="rating-star-block"><strong class="rating-star fa fa-star${ratingsLog.ratingValue >= idx ? '' : '-o'}"   id="s${idx }"><span class="rating-number" >${idx}</span></strong></a>
				    		</c:forEach>
				    		<div class="clearfix"></div>
				    	</div>
				    </div>
				    <div class="form-group">
			    		<label><spring:message code="rating.lbl.Comments"/></label>
			    		<p  class="read-only-textarea">${ratingsLog.comments }</p>
			    </div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="modal-footer text-right">
			<button type="button" class="text-hightlight btn btn-danger btn-lg" data-dismiss="modal">
				<i class="fa fa-check"></i>&nbsp;&nbsp;<spring:message code="btn.close"/>
			</button>
  		</div>
	</c:otherwise>
</c:choose>

  
 

