<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="english">en</c:set>
<c:set var="arabic">ar</c:set>
<style>
  /* Always set the map height explicitly to define the size of the div element that contains the map. */
  #map {
  	min-height: 380px;
  }
  /* Optional: Makes the sample page fill the window. */
  #description {
  	font-size: 15px;
 	font-weight: 300;
  }
  #infowindow-content .title {
  	font-weight: bold;
  }
  #infowindow-content {
  	display: none;
  }
  #map #infowindow-content {
  	display: inline;
  }
  .pac-card {
  	margin: 10px 10px 0 0;
  	border-radius: 2px 0 0 2px;
  	box-sizing: border-box;
  	-moz-box-sizing: border-box;
  	outline: none;
  	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  	background-color: #fff;
  }
  #pac-container {
  	padding-bottom: 12px;
  	margin-right: 12px;
  }
  .pac-controls {
  	display: inline-block;
  	padding: 5px 11px;
  }
  .pac-controls label {
  	font-size: 13px;
  	font-weight: 300;
  }
  #pac-input {
  	background-color: #fff;
  	font-size: 15px;
  	font-weight: 300;
  	margin-left: 0px;
  	padding: 0 11px 0 13px;
  	text-overflow: ellipsis;
  	width: 400px;
  }
  #pac-input:focus {
  	border-color: #4d90fe;
  }
  #title {
  	color: #fff;
  	background-color: #4d90fe;
  	font-size: 25px;
  	font-weight: 500;
  	padding: 6px 12px;
  }
  #target {
  	width: 345px;
  }
</style>
<%-- <div class="col-md-12 nopad">
  <spring:message code="locationmgt.phl.locationmame" var="locationmamephl"/>
  <spring:message code="locationmgt.phl.latitutde" var="latitutdephl"/>
  <spring:message code="locationmgt.phl.longitude" var="longitudephl"/>
  <spring:message code="locationmgt.phl.searchbox" var="searchboxphl"/>
  <div class="form-group">
    <label><spring:message code="editProfile.th.locationName" /></label>
    <input id="locationName" class="form-control" type="text" placeholder="${locationmamephl}" maxlength="50"/>
    <input id="locationNameHidden" type="hidden">
  </div>
  <div class="form-group">
    <div class="inline-block">
      <label class="block text-center"><spring:message code="editProfile.th.latitude"/></label>
      <p class="read-only w120 text-right">${latitude}</p>
      <input id="latHidden" type="hidden"/>
    </div>&nbsp;&nbsp;
    <div class="inline-block">
      <label class="block text-center"><spring:message code="editProfile.th.longitude"/></label>
      <p class="read-only w120 text-right">${longitude}</p>
      <input id="logHidden" type="hidden"/>
    </div>
  </div>
</div>
<div class="clearfix"></div> --%>
<div id="map" class="text-center">
	<img alt="loader" src="${contextPath}/resources/images/loading.gif">
</div>
<script>
  // This example adds a search box to a map, using the Google Place Autocomplete
  // feature. People can enter geographical searches. The search box will return a
  // pick list containing a mix of places and predicted search terms.
  
  // This example requires the Places library. Include the libraries=places
  // parameter when you first load the API. For example:
  // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places&language=ar>
  var map;
  var lat = 0;
  var lag = 0;
  function showPosition(position) {
      lat = ${latitude};
      lag = ${longitude};
      var myLatlng = new google.maps.LatLng(lat, lag);
      var map = new google.maps.Map(document.getElementById('map'), {
          center: {
              lat: lat,
              lng: lag
          },
          zoom: 13,
          mapTypeId: 'roadmap'
      });
      var marker = new google.maps.Marker({
          position: myLatlng,
          map: map
      });
  }
  function initAutocomplete() {
      if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(showPosition,showError);
      }
      else {
      	alert('<spring:message code="locationmgt.error.geolocationnotsupported"/>');
      }
  }
  function showError(error) {
     switch (error.code) {
       case error.PERMISSION_DENIED:
         //x.innerHTML = '<spring:message code="locationmgt.error.userdefined"/>';
         alert('<spring:message code="locationmgt.error.userdefined"/>');
         break;
       case error.POSITION_UNAVAILABLE:
         alert('<spring:message code="locationmgt.error.locationunavailable"/>');
         break;
       case error.TIMEOUT:
         //x.innerHTML = '<spring:message code="locationmgt.error.requesttimeout"/>';
         alert('<spring:message code="locationmgt.error.requesttimeout"/>');
         break;
       default:
         //x.innerHTML = '<spring:message code="locationmgt.error.unknownerror"/>';
         alert('<spring:message code="locationmgt.error.unknownerror"/>');
         break;
     }
  }
</script>
<c:choose>
  <c:when test="${(pageContext.response.locale) eq english}">
    <script src="https://maps.googleapis.com/maps/api/js?key=${googleMapKey}&libraries=places&callback=initAutocomplete&language=en&region=US" async defer></script>
  </c:when>
  <c:otherwise >
    <script src="https://maps.googleapis.com/maps/api/js?key=${googleMapKey}&libraries=places&callback=initAutocomplete&language=ar&region=EG" async defer></script>
  </c:otherwise>
</c:choose>