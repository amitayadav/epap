
(function($) {

	"use strict"
	
	/* + Responsive Caret */
	function menu_dropdown_open(){
		var width = $(window).width();
		if($(".menu-navigation .navigation-menu li.ddl-active").length ) {
			if( width > 991 ) {
				$(".menu-navigation .navigation-menu > li").removeClass("ddl-active");
				$(".menu-navigation .navigation-menu li .dropdown-menu").removeAttr("style");
			}
		} else {
			$(".menu-navigation .navigation-menu li .dropdown-menu").removeAttr("style");
		}
	}
	
	/* + Expand Panel Resize */
	function panel_resize(){
		var width = $(window).width();
		if( width > 991 ) {
			if($(".header_s .slidepanel").length ) {
				$(".header_s .slidepanel").removeAttr("style");
			}
		}
	}
	
	/* ## Document Ready */
	$(document).on("ready", function() {
		/* - Menu SubDropdown Toggle */
		if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
			
			$('.navigation-menu li.dropdown').click(function(e){
				if(!$(this).parent().hasClass('active')) {
					$('li.dropdown').removeClass('active');
					$(this).parent().addClass('active');
					e.preventDefault();
				} else {
					return true;
				} 
			});				
		} 
		
		$(".menuswitch").on("click", function() {
			$(".navigation-menu").toggleClass("active");
		});
		
		/* - Menu Responsive Caret */
		$(".ddl-switch").on("click", function() {
			var li = $(this).parent();
			if ( li.hasClass("ddl-active") || li.find(".ddl-active").length !== 0 || li.find(".dropdown-menu").is(":visible") ) {
				li.removeClass("ddl-active");
				li.children().find(".ddl-active").removeClass("ddl-active");
				li.children(".dropdown-menu").slideUp();
			}
			else {
				li.addClass("ddl-active");
				li.children(".dropdown-menu").slideDown();
			}
		});
		
		/* - Sticky Navigation */
		var nav = $('.header_s');
		$(window).scroll(function () {
			if ($(this).scrollTop() > 10) {
				nav.addClass("navigation-fixed animated fadeInDown");
			} else {
				nav.removeClass("navigation-fixed animated fadeInDown");
			}
		});
		
	
		/* - Banner Slider */
		$('.slider-section ul').bxSlider({
			mode: 'fade',
			captions: true,
			auto:false,
			pager: false
		}); 
		
		
		
		$("#accordion-main").accordion({ header: "h3", collapsible: true, active: false });
		$("#accordion-main2").accordion({ header: "h3", collapsible: true, active: false });
		
		
		  $("#btnlang").click(function(){
			$(".lang-opt").toggleClass("active");
		  });
	  
	});	/* - Document Ready /- */
	
	

})(jQuery);