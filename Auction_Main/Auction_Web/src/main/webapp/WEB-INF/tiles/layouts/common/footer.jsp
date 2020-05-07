<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>

<footer class="footer-section">
    <div class="container">

        <div class="row">
            <div class="footerbox box1">
                <ul class="footermenu">
                    <li><a href="#"><spring:message code="footer.menu.sitemap"/></a></li>
                    <li><a href="#"><spring:message code="footer.menu.privacy"/></a></li>
                    <li><a href="#"><spring:message code="footer.menu.feedback"/></a></li>
                    <li><a href="#"><spring:message code="footer.menu.contactus"/></a></li>
                </ul>
            </div>
            <div class="footerbox box2">
                <ul class="socials">
                    <li><span><spring:message code="footer.menu.followuson"/></span></li>
                    <li><a href="#"><img src="${contextPath}/resources/images/frontendimage/tw-ic.png" alt="social"/></a></li>
                    <li><a href="#"><img src="${contextPath}/resources/images/frontendimage/fb-ic.png" alt="social"/></a></li>
                    <li><a href="#"><img src="${contextPath}/resources/images/frontendimage/gp-ic.png" alt="social"/></a></li>
                    <li><a href="#"><img src="${contextPath}/resources/images/frontendimage/yt-ic.png" alt="social"/></a></li>
                </ul>
            </div>
        </div>
    </div>


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

    <!-- <div id="caption">
            <div id="bannerMoving"></div> 
       </div> -->


    <p><spring:message code="footer.menu.copyright"/></p>
</footer><!-- Footer /- -->



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


//Websocket programing
    var obj = null;
    var socket = new SockJS((contextPath + "/auctionsdata"));
    socket.onclose = function () {}
    var client = Stomp.over(socket);
    client.debug = null
    client.connect({}, function (frame) {
        client.subscribe(("/wssauctions/refreshBanner"), function (message) {
            movingBanner();
        });

    });

</script>  



