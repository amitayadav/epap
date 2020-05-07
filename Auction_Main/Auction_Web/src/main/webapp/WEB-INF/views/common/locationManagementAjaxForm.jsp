<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="english">en</c:set>
<c:set var="arabic">ar</c:set>
<style>
  /* Always set the map height explicitly to define the size of the div
  * element that contains the map. */
  #map {
  	height: 280px;
  	border: 1px solid #ccc;
  }
  /* Optional: Makes the sample page fill the window. */
  #description {
  	font-size: 20px;
 	font-weight: 500;
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
      /*fahad: background color of search box*/
  	 background-color: #faf2cc; 
  	/* background-color: #fff; */
  	font-size: 25px;
  	font-weight: 500;
              color: #002a80;
  	margin-left: 0px;
  	padding: 0 11px 0 13px;
  	text-overflow: ellipsis;
  	width: 280px;
  }  
  #title {
  	color: #fff;
  	background-color: #4d90fe;
  	font-size: 45px;
  	font-weight: 500;
  	padding: 6px 12px;
  }
  #target {
  	width: 345px;
  }
</style>
<c:choose>
  <c:when test="${not empty accountLocation}">
  	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
	  <h4 class="modal-title"><spring:message code="editProfile.btn.UpdateLocation" /></h4>
	</div>
	<div class="modal-body">
		<div class="col-md-12 nopad">
		  <spring:message code="locationmgt.phl.locationmame" var="locationmamephl"/>
		  <spring:message code="locationmgt.phl.latitutde" var="latitutdephl"/>
		  <spring:message code="locationmgt.phl.longitude" var="longitudephl"/>
		  <spring:message code="locationmgt.phl.searchbox" var="searchboxphl"/>
		  <div class="form-group">
		    <label><spring:message code="editProfile.th.locationName"/></label>
		    <p class="read-only text-left">${accountLocation.locationName}</p>
		    <input id="locationNameHidden" type="hidden" value="${accountLocation.locationName}"/>
		  </div>
		  <div class="form-group">
		    <div class="inline-block">
		      <label class="block text-center"><spring:message code="editProfile.th.latitude"/></label>
		      <p class="read-only w120 text-right">${accountLocation.latitude}</p>
		      <input id="latHidden" type="hidden" value="${accountLocation.latitude}"/>
		    </div>&nbsp;&nbsp;
		    <div class="inline-block">
		      <label class="block text-center"><spring:message code="editProfile.th.longitude"/></label>
		      <p class="read-only w120 text-right">${accountLocation.longitude}</p>
		      <input id="logHidden" type="hidden" value="${accountLocation.longitude}"/>
		    </div>
		  </div>
		</div>
		<div class="clearfix"></div>
		<div id="map"></div>
	</div>
	<div class="modal-footer">
	  <div class="form-group text-center">
	    <input type="button" class="btn btn-success btn-lg" value='<spring:message code="editProfile.btn.UpdateLocation"/>' onclick="updateLocation()"/>
	  </div>
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
	      lat = ${accountLocation.latitude};
	      lag = ${accountLocation.longitude};
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
  </c:when>
  <c:otherwise>
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
          <!--fahad: change size of Add Location header-->
	  <h1 class="modal-title">
	    <spring:message code="editProfile.h4.addNewLocation" />
	  </h1>
	</div>
	<div class="modal-body">
		<div class="col-md-12 nopad">
		  <spring:message code="locationmgt.phl.locationmame" var="locationmamephl"/>
		  <spring:message code="locationmgt.phl.latitutde" var="latitutdephl"/>
		  <spring:message code="locationmgt.phl.longitude" var="longitudephl"/>
		  <spring:message code="locationmgt.phl.searchbox" var="searchboxphl"/>
		  <div class="form-group">
		    <label><spring:message code="editProfile.th.locationName"/></label>
		    <input id="locationNameHidden" class="form-control editable-field" type="text" placeholder="${locationmamephl}" maxlength="50"/>
		  </div>
		  <div class="form-group">
		    <div class="inline-block">
		      <label class="block text-center"><spring:message code="editProfile.th.latitude"/></label>
		      <input id="lat" class="form-control w120 text-right" type="text" placeholder="${latitutdephl}" readonly="readonly"/>
		      <input id="latHidden" type="hidden"/>
		    </div>&nbsp;&nbsp;
		    <div class="inline-block">
		      <label class="block text-center"><spring:message code="editProfile.th.longitude"/></label>
		      <input id="log" class="form-control  w120 text-right" type="text" placeholder="${longitudephl}" readonly="readonly"/>
		      <input id="logHidden" type="hidden"/>
		    </div>
		  </div>
		</div>
		<div class="clearfix"></div>
		<input id="pac-input" class="form-control" type="text" placeholder="${searchboxphl}" />
		<div id="map"></div>
	</div>
	<div class="modal-footer">
	  <div class="form-group text-center">
              <!--fahad: change message for add location-->
	    <input type="button" class="btn btn-success btn-lg button-left text-hightlight" value='<spring:message code="editProfile.btn.addLocation2"/>' onclick="addLocation()"/>
	  </div>
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
	      lat = position.coords.latitude;
	      lag = position.coords.longitude;
	      $("#lat").val(lat.toFixed(6));
	      $("#log").val(lag.toFixed(6));
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
	      // Create the search box and link it to the UI element.
	      var input = document.getElementById('pac-input');
	      var searchBox = new google.maps.places.SearchBox(input);
//	      map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	      map.controls[google.maps.ControlPosition.TOP_RIGHT].push(input);
	      // Bias the SearchBox results towards current map's viewport.
	      map.addListener('bounds_changed', function() {
	          searchBox.setBounds(map.getBounds());
	      });
	      var markers = [];
	      // Listen for the event fired when the user selects a prediction and retrieve
	      // more details for that place.
	      searchBox.addListener('places_changed', function() {
	          var places = searchBox.getPlaces();
	          if (places.length == 0) {
	              return;
	          }
	          // Clear out the old markers.
	          markers.forEach(function(marker) {
	              marker.setMap(null);
	          });
	          markers = [];
	          // For each place, get the icon, name and location.
	          var bounds = new google.maps.LatLngBounds();
	          places.forEach(function(place) {
	              if (!place.geometry) {
	                  return;
	              }
	              var icon = {
	                  url: place.icon,
	                  size: new google.maps.Size(71, 71),
	                  origin: new google.maps.Point(0, 0),
	                  anchor: new google.maps.Point(17, 34),
	                  scaledSize: new google.maps.Size(25, 25)
	              };
	              // Create a marker for each place.
	              markers.push(new google.maps.Marker({
	                  map: map,
	                  icon: icon,
	                  title: place.name,
	                  position: place.geometry.location
	              }));
	  
	              if (place.geometry.viewport) {
	                  // Only geocodes have viewport.
	                  bounds.union(place.geometry.viewport);
	              } else {
	                  bounds.extend(place.geometry.location);
	              }
	          });
	          map.fitBounds(bounds);
	          codeAddress();
	      });
	      google.maps.event.addListener(map, 'click', function(event) {
	          var str = event.latLng;
	          $("#lat").val(str.lat().toFixed(6));
	          $("#log").val(str.lng().toFixed(6));
	          $("#locationNameHidden").focus();
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
	  function codeAddress() {
	      geocoder = new google.maps.Geocoder();
	      var address = document.getElementById("pac-input").value;
	      geocoder.geocode({
	          'address': address
	      }, function(results, status) {
	          if (status == google.maps.GeocoderStatus.OK) {
	             $("#lat").val(results[0].geometry.location.lat().toFixed(6));
	             $("#log").val(results[0].geometry.location.lng().toFixed(6));
	          }
	      });
	  }
	  function addLocation(){
	  	var locationName = $("#locationNameHidden").val();
	  	var latitude = $("#lat").val();
	  	var longitude = $("#log").val();
	  	//$("#locationNameHidden").val(locationName);
	  	$("#latHidden").val(latitude);
	  	$("#logHidden").val(longitude);
	  	if("" == locationName){
	  		alert("<spring:message code="locationmgt.error.enterlocation"/>");
	  	} else if("" == latitude || "" == longitude){
	  		alert("<spring:message code="locationmgt.error.latlongempty"/>");
	  		$('#locationModel').modal('hide');
	  	} else if("" != locationName && "" != latitude && "" != longitude){
	  		$('#locationModel').modal('hide');
	  		var tableRowCount = $("table#locationtable tbody tr").length;
	  		var tableRow = '<tr id="tr-'+tableRowCount+'">';
	  		tableRow+='<td align="left"><input type="text" class="form-control" name="newAccountLocation['+tableRowCount+'].locationName" value=\''+locationName+'\'></td>'; 
	  		tableRow+='<td align="right">'+$("#latHidden").val()+'<input type="hidden" name="newAccountLocation['+tableRowCount+'].latitude" value='+$("#latHidden").val()+'></td>';
	  		tableRow+='<td align="right">'+$("#logHidden").val()+'<input type="hidden" name="newAccountLocation['+tableRowCount+'].longitude" value='+$("#logHidden").val()+'></td>';
	  		tableRow+='<td align="center">';
	  		
	  		tableRow+='<select class="form-control" name="newAccountLocation['+tableRowCount+'].status">';
	  		tableRow+='<c:forEach items="${accountLocationStatusList}" var="locationStatus">';
	  		tableRow+='<option value="${locationStatus.key}"><spring:message code="account.location.status.${locationStatus.key}"/></option>';
	  		tableRow+='</c:forEach>';
	  		tableRow+='</select>';
	  		
	  		tableRow+='</td>';
	  		tableRow+='<td align="center"><button type="button" onclick="deleteNewLocationRow('+tableRowCount+')" class="btn btn-danger btn-xs" data-toggle="tooltip" data-original-title="<spring:message code="editProfile.th.delete"/>"><i class="fa fa-remove"></i></button></td>';
	  		tableRow+='</tr>';
	  		$("#locationtable tbody").append(tableRow);
	  	}else{
	  		alert("<spring:message code="locationmgt.error.createlocation"/>");
	  		$('#locationModel').modal('hide');
	  	}
	  }
	</script>
  </c:otherwise>
</c:choose>

<c:choose>
  <c:when test="${(pageContext.response.locale) eq english}">
    <script src="https://maps.googleapis.com/maps/api/js?key=${googleMapKey}&libraries=places&callback=initAutocomplete&language=en&region=US" async defer></script>
  </c:when>
  <c:otherwise >
    <script src="https://maps.googleapis.com/maps/api/js?key=${googleMapKey}&libraries=places&callback=initAutocomplete&language=ar&region=EG" async defer></script>
  </c:otherwise>
</c:choose>