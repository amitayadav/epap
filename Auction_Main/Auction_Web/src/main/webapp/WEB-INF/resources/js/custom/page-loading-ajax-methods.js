function loadPageByAjax($link, newURL) {
  $(".tooltip").tooltip("hide");
  if (undefined != currentTimeInterval) {
    clearInterval(currentTimeInterval);
  }
  var url = "";
  if (undefined != newURL) {
    url = newURL;
  } else {
    url = $link.prop("href");
  }
  var loaderContent = '<p class="page-container-loader"><i class="fa fa-spinner fa-pulse loader"></i></p>';
  $pageContainer = $("#page-container");
  $pageContainer.empty().html(loaderContent);
  $.ajax({
    type: "GET",
    async: false,
    cache: false,
    url: (url),
    success: function(result) {
      $pageContainer.empty().html(result);
      $('body').removeClass('modal-open');
      $('.modal-backdrop').remove();
      $(".alert").delay(1000).hide(100);
      //window.history.pushState("object or string", "epap|Dashboard",
      //		contextPath + "/setting/dashboard");
      $("#page-container").off("click", ".pageContentLoading .pageContentLoadingAfterConfirm");
      $(".right_col .breadcrumb li a").off("click");
      $(".right_col .breadcrumb").on("click", "li a:gt(0)", function(e) {
        e.preventDefault();
        loadPageByAjax($(this));
      });
      $("#page-container").on("click", ".pageContentLoading .pageContentLoadingAfterConfirm", function(e) {
        e.preventDefault();
        /*if (undefined != currentTimeInterval) {
        	clearInterval(currentTimeInterval);
        }*/
        loadPageByAjax($(this));
      });
    },
    error: function(e) {
      $pageContainer.empty();
      alert("Error : " + e);
    }
  });
  return false;
}

function submitPageByAjax($formurl, $formdata) {
  $(".tooltip").tooltip("hide");
  if (undefined != currentTimeInterval) {
    clearInterval(currentTimeInterval);
  }
  var loaderContent = '<p class="page-container-loader"><i class="fa fa-spinner fa-pulse loader"></i></p>';
  $pageContainer = $("#page-container");
  $pageContainer.empty().html(loaderContent);
  $.ajax({
    type: "POST",
    async: false,
    cache: false,
    data: $formdata,
    url: $formurl,
    success: function(result) {
      $("#page-container").empty().html(result);
      $('body').removeClass('modal-open');
      $('.modal-backdrop').remove();
      $(".alert").delay(1000).hide(100);
      $(".right_col .breadcrumb li a").off("click");
      $(".right_col .breadcrumb").on("click", "li a:gt(0)", function(e) {
        e.preventDefault();
        loadPageByAjax($(this));
      });
      $("#page-container").on("click", ".pageContentLoading .pageContentLoadingAfterConfirm", function(e) {
        e.preventDefault();
        /*if (undefined != currentTimeInterval) {
        	clearInterval(currentTimeInterval);
        }*/
        loadPageByAjax($(this));
      });
    },
    error: function(e) {
      $("#page-container").empty();
      alert("Error : " + e);
    },
  });
  return false;
}
$(document).ready(function() {
  $("#sidebar-menu").on("click", ".child_menu a.instant-load", function(e) {
    e.preventDefault();
    loadPageByAjax($(this));
  });
  $(".right_col .breadcrumb li a").off("click");
  $(".right_col .breadcrumb").on("click", "li a:gt(0)", function(e) {
    e.preventDefault();
    loadPageByAjax($(this));
  });
  $("body").on("click", ".pageContentMenu, .pageContentLoading", function(e) {
    e.preventDefault();
    var elementid = $(this).prop("id");
    /*		if(undefined != elementid && null != elementid && elementid=="testhref"){
    			if( checkAuctionRunningTime()){
    				loadPageByAjax($(this));
    			}
    		}else{
    			loadPageByAjax($(this));
    		}*/
    loadPageByAjax($(this));
  });
  $("body").on("click", ".pageContentLoadingAfterConfirm", function(e) {
    var confirmReply = confirm($("#confirmDialogMsg").html().trim());
    if (confirmReply) {
      e.preventDefault();
      loadPageByAjax($(this));
    }
    return false;
  });
  $("body").on("submit", "#page-container form:not('.normalPageContentLoading')", function(e) {
    e.preventDefault();
    submitPageByAjax($(this).attr('action'), $(this).serializeArray());
  });
  
  //Websocket programing
   var  obj=null;
  var socket = new SockJS((contextPath + "/auctionsdata"));
  socket.onclose = function() {}
  var client = Stomp.over(socket);
  client.debug = null
  client.connect({}, function(frame) {
  client.subscribe(("/wssauctions/refreshUI"), function(message) {
      if (undefined != auctionTable && null != auctionTable) {
        auctionTable.ajax.reload();
      } else {
    	  if(auctionSellerId == message.body){
    		  if (undefined != queryString && null != queryString && "" != queryString) {
        		  loadPageByAjax(null, (contextPath + queryString));
        	  }
    	  }else{
    		  if (undefined != auctionSellerOffer && null != auctionSellerOffer) {
    			  auctionSellerOffer.ajax.reload();
    		  }
    	  }
      }
      if("buyer" == message.body){
    	  if (undefined != auctionSellerOffer && null != auctionSellerOffer) {
			  auctionSellerOffer.ajax.reload();
		  }	if(undefined != auctionBuyerBidsAndViewSellerOffer && null != auctionBuyerBidsAndViewSellerOffer ){
	    		auctionBuyerBidsAndViewSellerOffer.ajax.reload();
	    	}if (undefined != auctionBuyerBid && null != auctionBuyerBid) {
	      	  auctionBuyerBid.ajax.reload();
	        }
      }
      if(dailyAuctionId == message.body){
    	  if (undefined != queryString && null != queryString && "" != queryString) {
    		  loadPageByAjax(null, (contextPath + queryString));
    	  }
      }
      getAvailableBalance();
    });
  	client.subscribe(("/wssauctions/refreshOfferUI"), function(message) {
       	obj = JSON.parse(message.body);
    if (undefined != auctionSellerOffer && null != auctionSellerOffer) {
    	auctionSellerOffer.ajax.reload();
      } else if(auctionSellerId == obj.auctionSellerId  && obj.quantity ==0){
    	  if (undefined != queryString && null != queryString && "" != queryString) {
    		loadPageByAjax(null, (contextPath + queryString));
    	  }
      }else if(undefined != auctionBuyerBid && null != auctionBuyerBid){
			if(auctionOfferBuyerId == obj.auctionOfferBuyerId){
				  if (undefined != queryString && null != queryString && "" != queryString) {
			    		loadPageByAjax(null, (contextPath + queryString));
			    	  }
			}else{
				auctionBuyerBid.ajax.reload();
			}
	    	  }
    		if(undefined != auctionBuyerBidsAndViewSellerOffer && null != auctionBuyerBidsAndViewSellerOffer ){
    		auctionBuyerBidsAndViewSellerOffer.ajax.reload();
    	}else{
    		if(auctionSellerId == obj.auctionSellerId && obj.quantity ==0){
    			 if (undefined != queryString && null != queryString && "" != queryString) {
    		    		loadPageByAjax(null, (contextPath + queryString));
    		      }
    		}
    	}
      getAvailableBalance();
    });
  		client.subscribe(("/wssauctions/refreshBidUI"), function(message) {
      if (undefined != auctionBuyerBid && null != auctionBuyerBid) {
    	  auctionBuyerBid.ajax.reload();
      }else if (undefined != auctionSellerOffer && null != auctionSellerOffer) {
    	  auctionSellerOffer.ajax.reload();
      }else if (undefined != queryString && null != queryString && "" != queryString) {
    	  loadPageByAjax(null, (contextPath + queryString));
      }
      getAvailableBalance();
    });
    client.subscribe(("/wssauctions/refreshOfferPrice"), function(message) {
    	var offerPriceElement = document.getElementById('offerPriceOfBidTable');
    	  if (undefined != offerPriceElement && null != offerPriceElement) {
    		  if(offerPriceElement.innerText == "0.50"){
    	    		 loadPageByAjax(null, (contextPath + queryString));
    	    	 }else{
    	    		  auctionBuyerBidsAndViewSellerOffer.ajax.reload();
    	    	 }  
      }
     else {
        if (undefined != auctionSellerOffer && null != auctionSellerOffer) {
        	auctionSellerOffer.ajax.reload();
        } else if (undefined != queryString && null != queryString && "" != queryString) {
        	loadPageByAjax(null, (contextPath + queryString));
        }
      }
    });
	
    /**
     * moving Banner  Websocket
     */
    client.subscribe(("/wssauctions/refreshBanner"), function(message) {
    	movingBanner();
     });
    
    var  loginAccountId= document.getElementById("loginUserAccountId").value;
    client.subscribe(("/wssauctions/senderReceiverMsg_"+loginAccountId), function(message) {
    	 showGreeting(message.body);
      });

    
  });
});
/*
 //IIFE - Immediately Invoked Function Expression
 (function(loadPage) {

 // The global jQuery object is passed as a parameter
 loadPage(window.jQuery, window, document);

 }(function($, window, document) {

 // The $ is now locally scoped 

 // Listen for the jQuery ready event on the document
 $(function() {

 // The DOM is ready!

 });

 // The rest of the code goes here!

 }
 ));



 // IIFE - Immediately Invoked Function Expression
 (function(yourcode) {

 // The global jQuery object is passed as a parameter
 yourcode(window.jQuery, window, document);

 }(function($, window, document) {

 // The $ is now locally scoped 

 // Listen for the jQuery ready event on the document
 $(function() {

 // The DOM is ready!

 });

 // The rest of the code goes here!

 }
 ));
 */