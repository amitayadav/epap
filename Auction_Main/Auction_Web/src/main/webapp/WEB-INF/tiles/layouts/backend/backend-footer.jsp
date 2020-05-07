<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- footer content -->
<div class="clearfix"></div>
/footer content
<div class="modal fade" id="largeImageDisplay" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">x</button>
            </div>
            <div class="modal-body">
                <img id="imageDisplay" src="" width="100%"/>
            </div>

        </div>
    </div>
</div> 
<div class="footer">
    <!-- <footer class="footer-section" position="fixed"> -->
    <!--Fahad: id "caption" refers to CSS definition for animating ticket tape-->
   <div class="tcontainer">
    <div class="ticker-wrap">
      <div class="ticker-move">
        <div class="ticker-item">
        <div id="bannerMoving"></div>
         </div>
      </div>
    </div> 
  </div>
  <!--Fahad: id "caption" refers to CSS definition for animating ticket tape-->
<!-- 
 <div id="caption">
         <div id="bannerMoving"></div> 
    </div>
     -->
</div> 
   <script type="text/javascript">
   function  movingBanner() {
	    $.ajax({
            type: "GET",
            async: false,
            cache: false,
            url: (contextPath + "/auth/announcementmovingbanner"),
            success: function (result) {
                var length = result.length;
                var duration;
                if (length < 10)
                    duration = 1 + "s";
                if (length < 75)
                    duration = 10 + "s";
                else if (length < 100)
                    duration = 20 + "s";
                else if (length < 150)
                    duration = 30 + "s";
                else if (length < 350)
                    duration = 50 + "s";
                else if (length < 600)
                    duration = 75 + "s";
                else if (length < 1200)
                    duration = 100 + "s";
                else if (length < 2500)
                    duration = 150 + "s";
                else if (length < 3000)
                    duration = 200 + "s";
                else if (length < 4000)
                    duration = 250 + "s";
                else if (length < 6000)
                    duration = 350 + "s";
                else
                    duration = 400 + "s";
                $(".ticker-move").css("animation-duration", duration);
                document.getElementById("bannerMoving").innerHTML = result;
            },
            error: function (e) {
               
            }
        });
   }
   $(document).ready(function () {
	   movingBanner();
   });
   </script>