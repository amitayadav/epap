<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ page import="com.auction.commons.util.InternetTiming"%>

<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><a href="${contextPath}/setting/dashboard"><spring:message code="menu.main.dashboard"/></a></li>
        <li><spring:message code="menu.communication.messaging"/></li>
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
                <h3><spring:message code="messaging.heading.title"/></h3>
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
        <div>
            <input type="hidden" id="loginUserAccountId" name="loginUserAccountId" value='${loginUserInfo.accountProfileBean.accountId}'></input>
            <c:choose>
                <c:when test="${not empty loginUserInfo.accountProfileBean.profileImage}">
                    <img id="sendImgPath" src="${contextPath}/setting/profilePictures/${loginUserInfo.accountProfileBean.accountId}/${loginUserInfo.accountProfileBean.profileImage}" style="display: none;" /> 
                </c:when>
                <c:otherwise>          
                    <img id="sendImgPath" src="${contextPath}/resources/images/img.jpg" style="display: none;" />     
                </c:otherwise>
            </c:choose>
        </div>
        <div class="container clearfix chat-messaging">
            <!--fahad: change column to 3 to make sender list less wide-->
            <div class="col-md-3">
                <div class="chat-search" id="chat-search">
                    <input type="text" class="form-control" placeholder="search" id="search"/> <!--  onkeyup="searchFunction()"/> --> 
                </div>
                <div class="chat-users-section" id="chat-users-section">
                    <ul class="chat-users" id="chat-users">
                        <c:forEach items="${loginDetailsBean}" var="loginDetailsBean">
                            <c:choose>
                                <c:when test="${not empty loginDetailsBean.accountProfileBean.profileImage}">
                                    <li class="chat-user" id="chat-user-${loginDetailsBean.accountProfileBean.accountId}" onclick="getPublicName('${loginDetailsBean.accountProfileBean.accountId}', '${loginDetailsBean.publicName}', '${contextPath}/setting/profilePictures/${loginDetailsBean.accountProfileBean.accountId}/${loginDetailsBean.accountProfileBean.profileImage}')">
                                        <span class="pending-message-count active" id="pending-message-count">0</span>
                                        <a href="#">
                                            <img src="${contextPath}/setting/profilePictures/${loginDetailsBean.accountProfileBean.accountId}/${loginDetailsBean.accountProfileBean.profileImage}">
                                            <strong>${loginDetailsBean.publicName}</strong>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>                
                                    <li class="chat-user" id="chat-user-${loginDetailsBean.accountProfileBean.accountId}" onclick="getPublicName('${loginDetailsBean.accountProfileBean.accountId}', '${loginDetailsBean.publicName}', '${contextPath}/resources/images/img.jpg')">
                                        <span class="pending-message-count active"  id="pending-message-count">0</span>
                                        <a href="#">
                                            <img src="${contextPath}/resources/images/img.jpg">
                                            <strong>${loginDetailsBean.publicName}</strong>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="col-md-8 chat-history-section nopad">
                <div id="demo" class="chatting-user"></div>
                <div id = "chat-history-section">
                    <div id="chat-history" class="chat-history">
                        <div id=date-history>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                </div>
                <div class="message-send-section nopad"  id="msgsend">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <input id="picture"  type="file" name="picture"  accept="image/*" class="hidden"/>
                            <label class="attachment-icons" for="picture" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="Attach Image">
                                <i class="fa fa-picture-o"></i>
                            </label>
                            <button class="attachment-icons btn" onclick="startDictation(${loginUserInfo.accountProfileBean.accountId})" id="audio" >
                                <i class="fa fa-microphone " id="addclass"></i>
                            </button>
                            <input type="hidden" value="0 " id="micro"  name="micro">
                            <label class="attachment-icons" id="recordTiming">00:00</label>
                        </div>
                        <textarea class="form-control" name="message-to-send" id="message-to-send" placeholder ="Type your message"   maxlength="250"></textarea>
                        <div class="input-group-addon">
                            <button onclick="saveMessage(${loginUserInfo.accountProfileBean.accountId})" class="btn btn-warning" data-html="true" data-toggle="tooltip" data-placement="bottom" data-original-title="Send Message">
                                <i class="fa fa-paper-plane"></i>
                            </button>
                        </div>
                    </div>
                    <!--  -->
                </div>
            </div>
        </div> 
    </div>
</div>

<script type="text/javascript">
    var receiverId;
    var receiverImgPath;
    var totalSeconds = 0;
    var start_timer_clock = 0;
    var stream, recorder, chunks;
    var senderProflieImg;
    var media = {
        tag: 'audio',
        type: 'audio/ogg',
        ext: '.ogg',
        gUM: {audio: true}
    }

    $(document).ready(function () {
        var notificationIncrement = $(".pending-message-count").html();
        if (notificationIncrement == 0) {
            $("span").removeClass("active");
        }
        var x = document.getElementById('msgsend');
        x.style.visibility = 'hidden';

        $(".chat-messaging .chat-search #search ").on("keyup", function () {
            var currentSearch = $(this).val();
            currentSearch = currentSearch.trim();
            if ("" != currentSearch && currentSearch.length > 0) {
                $(".chat-messaging .chat-users .chat-user strong").each(function (index, element) {
                    if ($(this).text().indexOf(currentSearch) > -1) {
                        $(this).parents("li").removeClass("hidden");
                    } else {
                        $(this).parents("li").addClass("hidden");
                    }
                });
            } else {
                $(".chat-messaging .chat-users .chat-user").removeClass("hidden");
            }
        });

    });



    /* $("#chat-history-section").on( 'scroll', function(){
     console.log('Event Fired');
     console.log($("div").scrollTop() + " px");
     if($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
     alert('end reached');
     }
     }); */




    function getPublicName(receiverAccountId, publicName, userImgPath) {
        stop("ok");
        var y = document.getElementById('msgsend');
        var senderImg = document.getElementById('sendImgPath');
        y.style.visibility = 'visible';
        var notificationIncrement = $(".chat-users #chat-user-" + receiverAccountId + " span").html();
        if (notificationIncrement != 0) {
            $(".chat-users #chat-user-" + receiverAccountId + " span").html(0);
            $(".chat-users #chat-user-" + receiverAccountId + " span").removeClass("active");
        }
        receiverId = receiverAccountId;
        receiverImgPath = userImgPath;
        senderProflieImg = senderImg.src;
        var senderAccountId = document.getElementById("loginUserAccountId").value;
        document.getElementById("demo").innerHTML = "<img src=\"" + userImgPath + "\" class=\"chat-user-icon\" alt=\"avatar\"/>&nbsp;&nbsp;" + publicName;
        $.ajax({
            type: "POST",
            async: false,
            cache: false,
            data: {
                senderAccountId: senderAccountId,
                receiverAccountId: receiverAccountId
            },
            url: (contextPath + "/commmon/getMessge/sendReceiver"),
            success: function (result) {
                $(".chat-messaging .chat-search #search").val("");
                $(".chat-messaging .chat-search #search").trigger("keyup");
                $("#chat-history").html("");
                for (var i = 0; i < result.length; i++) {
                    if (senderAccountId == result[i].fromId) {
                        if (result[i].message.trim() != "") {
                            $("#chat-history").append("<div title=\"" + result[i].messageTimestamp + "\" class=\"message pull-right text-right\"><img class=\"usr-img-pull chat-user-icon\" src=\"" + senderProflieImg + "\" alt=\"avatar\" /><span class=\"sent\">" + result[i].message + "</span></div>");
                            $("#chat-history").append("<span class=\"message pull-right text-right sent\">" + result[i].messageTimestamp + "</span>");
                            var element = document.getElementById("chat-history-section");
                            element.scrollTop = element.scrollHeight;
                        } else if (result[i].attachmentType == "audio") {
                            var audio = "data:audio/mp3;base64," + result[i].attachment;
                            $("#chat-history").append("<div title=\"" + result[i].messageTimestamp + "\" class=\"message pull-right text-right\"><img class=\"usr-img-pull chat-user-icon\" src=\"" + senderProflieImg + "\" alt=\"avatar\" /><span class=\"usr-img-pull\"><audio controls  src=" + audio + "></span></div>");
                            $("#chat-history").append("<span class=\"message pull-right text-right sent\">" + result[i].messageTimestamp + "</span>");
                            var element = document.getElementById("chat-history-section");
                            element.scrollTop = element.scrollHeight;
                        } else {
                            var img = "data:image/png;base64," + result[i].attachment;
                            $("#chat-history").append("<div title=\"" + result[i].messageTimestamp + "\" class=\"message pull-right text-right\"><img class=\"usr-img-pull chat-user-icon\" src=\"" + senderProflieImg + "\" alt=\"avatar\" /><span class=\"usr-img-pull\"><img src=" + img + " class=\"chat-img\"></span></div>");
                            $("#chat-history").append("<span class=\"message pull-right text-right sent\">" + result[i].messageTimestamp + "</span>");
                            var element = document.getElementById("chat-history-section");
                            element.scrollTop = element.scrollHeight;
                        }

                    } else {
                        if (result[i].message.trim() != "") {
                            $("#chat-history").append("<div title=\"" + result[i].messageTimestamp + "\" class=\"message pull-left text-left\"><img class=\"usr-img-pull chat-user-icon\" src=\"" + userImgPath + "\" alt=\"avatar\" /><span class=\"received\">" + result[i].message + "</span></div>");
                            $("#chat-history").append("<span class=\"message pull-left text-left received\">" + result[i].messageTimestamp + "</span>");
                            var element = document.getElementById("chat-history-section");
                            element.scrollTop = element.scrollHeight;
                        } else if (result[i].attachmentType == "audio") {
                            var audio = "data:audio/mp3;base64," + result[i].attachment;
                            $("#chat-history").append("<div title=\"" + result[i].messageTimestamp + "\" class=\"message pull-left text-left\"><img class=\"usr-img-pull chat-user-icon\" src=\"" + userImgPath + "\" alt=\"avatar\" /><span class=\" usr-img-pull\"><audio controls  src=" + audio + "></span></span></div>");
                            $("#chat-history").append("<span class=\"message pull-left text-left received\">" + result[i].messageTimestamp + "</span>");
                            var element = document.getElementById("chat-history-section");
                            element.scrollTop = element.scrollHeight;
                        } else {
                            var img = "data:image/png;base64," + result[i].attachment;
                            $("#chat-history").append("<div title=\"" + result[i].messageTimestamp + "\" class=\"message pull-left text-left\"><img class=\"usr-img-pull chat-user-icon\" src=\"" + userImgPath + "\" alt=\"avatar\" /><span class=\" usr-img-pull\"><img src=" + img + " class=\"chat-img\"></span></div>");
                            $("#chat-history").append("<span class=\"message pull-left text-left received\">" + result[i].messageTimestamp + "</span>");
                            var element = document.getElementById("chat-history-section");
                            element.scrollTop = element.scrollHeight;
                        }
                    }
                }
            }
        });
    }

    function saveMessage(sendLoginId) {
    	
    	$("#message-to-send").focus();
        var check_msg_img = false;
        var formdata = new FormData();
        var toAccoutId = receiverId;
        var fromAccountId = sendLoginId;
        var messgeValue = document.getElementById("message-to-send").value;
        var files = $('#picture')[0].files[0];
        formdata.append("picture", files);
        formdata.append("toAccoutId", receiverId);
        formdata.append("fromAccountId", sendLoginId);
        formdata.append("messgeValue", messgeValue);
        if (messgeValue.trim() != "") {
            check_msg_img = true;
        }
        if (files != null) {
            check_msg_img = true;
            if (4194304 < files.size)
            {
                alert("File size must under 1mb!");
                document.getElementById("picture").value = "";
                return;
            }
        }
        if (check_msg_img == true) {
            $.ajax({
                type: "POST",
                async: false,
                cache: false,
                data: formdata,
                contentType: false,
                processData: false,
                url: (contextPath + "/commmon/saveMessage"),
                success: function (result) {
                    if (result.result == "messge") {
                        $("#chat-history").append("<div title=\"" + " " + "\" class=\"message pull-right text-right\"><img class=\" usr-img-pull chat-user-icon\" src=\"" + senderProflieImg + "\" alt=\"avatar\" /><span class=\"sent\">" + messgeValue + "</span></div>");
                        $("#chat-history").append("<span  class=\"message pull-right text-right sent\">" + result.date + "</span>");
                        var element = document.getElementById("chat-history-section");
                        element.scrollTop = element.scrollHeight;
                    } else {
                        var img = "data:image/png;base64," + result.picture;
                        $("#chat-history").append("<div title=\"" + " " + "\" class=\"message pull-right text-right\"><img class=\"usr-img-pull  chat-user-icon\" src=\"" + senderProflieImg + "\" alt=\"avatar\" /><span class=\"sent pull-right\"><img src=" + img + " class=\"chat-img\"></span></div>");
                        $("#chat-history").append("<span  class=\"message pull-right text-right sent\">" + result.date + "</span>");
                        var element = document.getElementById("chat-history-section");
                        element.scrollTop = element.scrollHeight;
                    }
                    document.getElementById("message-to-send").value = " ";
                    document.getElementById("picture").value = "";

                }
            });
        }
    }

    function startDictation(sendLoginId) {
    	$("#message-to-send" ).focus();
        $("#addclass").addClass("fa fa-microphone-slash");
        var microphoneStart = document.getElementById("micro").value;
        chunks = [];
        var sBrowser, sUsrAg = navigator.userAgent;
        if (sUsrAg.indexOf("Trident") > -1) {
            sBrowser = "Microsoft Internet Explorer";
        } else if (sUsrAg.indexOf("Edge") > -1) {
            sBrowser = "Microsoft Edge";
        } else {
            sBrowser = "unknown";
        }
        navigator.mediaDevices = {};
        var getUserMedia = navigator.mediaDevices || navigator.mediaDevices.enumerateDevices;
        if (microphoneStart != 0) {
            $("#addclass").removeClass("fa fa-microphone-slash").addClass("fa fa-microphone");
            recorder.stop();
            clearInterval(start_timer_clock);
            totalSeconds = 0;
            recordTimingLabel.innerHTML = 00 + ":" + 00;
            $("#micro").val("0");
        } else {
            if (sBrowser != "Microsoft Internet Explorer") {
                navigator.mediaDevices.getUserMedia(media.gUM).then(function (_stream) {
                    $("#micro").val("1");
                    stream = _stream;
                    recorder = new MediaRecorder(stream);
                    start_timer_clock = setInterval(setTime, 1000);
                    recorder.start();
                    recorder.ondataavailable = (function (e) {
                        chunks.push(e.data);
                        var blob = new Blob(chunks, {type: media.type});
                        if (recorder.state == 'inactive') {
                            var formdata = new FormData();
                            var toAccoutId = receiverId;
                            var fromAccountId = sendLoginId;
                            formdata.append("toAccoutId", receiverId);
                            formdata.append("fromAccountId", sendLoginId);
                            formdata.append("audio", blob);
                            $.ajax({
                                type: "POST",
                                async: false,
                                cache: false,
                                data: formdata,
                                contentType: false,
                                processData: false,
                                url: (contextPath + "/commmon/saveAudion"),
                                success: function (result) {
                                    var audio = "data:audio/mp3;base64," + result.audio;
                                    $("#chat-history").append("<div title=\"" + " " + "\" class=\"message pull-right text-right\"><img class=\"usr-img-pull  chat-user-icon\" src=\"" + senderProflieImg + "\" alt=\"avatar\" /><span class=\"sent pull-right\"><audio controls  src=" + audio + "></span></div>");
                                    $("#chat-history").append("<span  class=\"message pull-right text-right sent\">" + result.date + "</span>");
                                    var element = document.getElementById("chat-history-section");
                                    element.scrollTop = element.scrollHeight;
                                }
                            });

                        }

                    })
                })/* catch(function(err) { 
                 $( "#addclass" ).removeClass( "fa fa-microphone-slash" ).addClass( "fa fa-microphone" );
                 console.log(err.name + ": " + err.message); 
                 }); */ // always check for errors at the end.
            } else {
                alert("You are using: " + sBrowser + " " + " not support audio play ");
            }
        }
        /* setTimeout(function() {  
         recorder.stop();
         clearInterval(start_timer_clock);
         totalSeconds = 0;
         $(".attachment-icons").attr("disabled",false);
         }, 100000); */
    }

    function stop(var1) {
        var microphoneStart = document.getElementById("micro").value;
        if (microphoneStart == 1) {
            $("#addclass").removeClass("fa fa-microphone-slash").addClass("fa fa-microphone");
            recorder.stop();
            clearInterval(start_timer_clock);
            totalSeconds = 0;
            recordTimingLabel.innerHTML = 00 + ":" + 00;
            $("#micro").val("0");
        }
    }

    function showGreeting(message) {
        var obj = JSON.parse(message);
        var reId = Number(obj.fromId);
        if (reId == receiverId) {
            if (obj.msg.trim() != "") {
                $("#chat-history").append("<div title=\"" + " " + "\" class=\"message pull-left text-left\"><img class=\"usr-img-pull  chat-user-icon\"  src=\"" + receiverImgPath + "\" alt=\"avatar\" /><span class=\"received\">" + obj.msg + "</span></div>");
                $("#chat-history").append("<span class=\"message pull-left text-left received\">" + obj.date + "</span>");
                var element = document.getElementById("chat-history-section");
                element.scrollTop = element.scrollHeight;
            } else if (obj.audio != null) {
                var audio = "data:audio/mp3;base64," + obj.audio;
                $("#chat-history").append("<div title=\"" + " " + "\" class=\"message pull-left text-left\"><img class=\"usr-img-pull  chat-user-icon\" src=\"" + receiverImgPath + "\" alt=\"avatar\" /><span class=\"usr-img-pull\"><audio controls src=" + audio + "></span></div>");
                $("#chat-history").append("<span class=\"message pull-left text-left received pull-left\">" + obj.date + "</span>");
                var element = document.getElementById("chat-history-section");
                element.scrollTop = element.scrollHeight;
            } else {
                var img = "data:image/png;base64," + obj.picture;
                $("#chat-history").append("<div title=\"" + " " + "\" class=\"message pull-left text-left\"><img class=\"usr-img-pull  chat-user-icon\" src=\"" + receiverImgPath + "\" alt=\"avatar\" /><span class=\"usr-img-pull\"><img src=" + img + " class=\"chat-img\"></span></div>");
                $("#chat-history").append("<span class=\"message pull-left text-left received pull-left\">" + obj.date + "</span>");
                var element = document.getElementById("chat-history-section");
                element.scrollTop = element.scrollHeight;
            }
        } else {
            var notificationIncrement = $(".chat-users #chat-user-" + reId + " span").html();
            if (notificationIncrement == 0) {
                $(".chat-users #chat-user-" + reId + " span").addClass("active");
                notificationIncrement++;
                $(".chat-users #chat-user-" + reId + " span").html(notificationIncrement);
            } else {
                notificationIncrement++;
                $(".chat-users #chat-user-" + reId + " span").html(notificationIncrement);
            }
        }

    }

    //clock timer
    var recordTimingLabel = document.getElementById("recordTiming");

    function setTime() {
        ++totalSeconds;
        recordTimingLabel.innerHTML = (pad(parseInt(totalSeconds / 60)) + ":" + pad(totalSeconds % 60));
    }

    function pad(val) {
        var valString = val + "";
        if (valString.length < 2) {
            return "0" + valString;
        } else {
            return valString;
        }
    }

</script>