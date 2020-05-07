<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



<!-- page content -->
<div class="right_col" role="main">
    <ul class="breadcrumb">
        <li><spring:message code="menu.main.dashboard"/></li>
        <li class="todaydatetime">
            <fmt:formatDate type="both" dateStyle="long" pattern='EEEE dd MMMM yyyy - ' value="${internetDateTime}"/>
            <span id="current-time"> 
                      <fmt:formatDate type="both"  dateStyle="long" pattern='hh:mm:ss a' value="${internetDateTime}"/>    
            </span>
        </li>
    </ul>
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3><spring:message code="dashboard.lbl.title"> </spring:message></h3>
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
        <div class="x_content">
	       <div class="row">
	         <div class="col-md-12 col-sm-12 col-xs-12">
	            <div class="x_panel">
	              <div class="x_content">
	              	                   
                        <div class="form-group">  
                         
                        	<spring:message code="dashboard.product.chart.view.productName" var="productNamePhl"/>
                        	<spring:message code="dashboard.product.chart.view.productTypeName" var="productTypePhl"/>             
                        	<div class="col-md-6 nolpad">
                        		<label class="form-label"><span class="required"> * </span>${productNamePhl}</label>
                        		<select name="productName" class="form-control-for-dates" id="search1">
                        		   <option value="0">Select </option>
                        			<c:forEach items="${productNameList}" var="productName">
                        				<option >${productName}</option>
                        			</c:forEach>	
                        		</select>
                        	</div>
                        	<div class="col-md-6 nolpad">
                        		<label class="form-label"><span class="required"> * </span>${productTypePhl}</label>
                        		<select name="productTypeName" class="form-control-for-dates" id="search2" >
                        		<option value=0>Select </option>
                        			<c:forEach items="${productTypeNameList}" var="productTypeName">
                        				<option>${productTypeName}</option>
                        			</c:forEach>
                        		</select>
                        	</div>
                        </div>
                        
                        <div class="clearfix"></div><br/>
                        <div ><span id="error" style="color:red;display:none;"><spring:message code="dashboard.product.not.found"> </spring:message></span></div>
              			<div class="form-group text-right">
                			<input type="submit" class="btn btn-success btn-lg button-left text-hightlight" value="Search" id="search"/>
              			</div><div class="clearfix"></div>
                   <hr/> 
                    
                    <div class="row">
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput1" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput2" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput3" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
                    </div>
                    
                    <div class="row">
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput4" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput5" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput6" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
                    </div>	
                    <div class="row">
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput7" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput8" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput9" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
                    </div> 
                    <div class="row">
	                    <div class="col-md-4 norpad">
		                    <figure class="highcharts-figure">
		                    	<div id="chartOutput10" style="height: 400px;"></div> 
		                    </figure>
	                    </div>
	                    
                    </div>    
	              </div>
	            </div>
	          </div>
	        </div> 
	      </div>
    </div>
</div>
<!-- /page content -->

<script type="text/javascript">

var map1= new Map();
var charts =['chartOutput1','chartOutput2','chartOutput3','chartOutput4','chartOutput5','chartOutput6','chartOutput7','chartOutput8','chartOutput9','chartOutput10'];
	
<c:forEach items="${auctionTradeList}" var="auctionTrade">

	if (map1.has('${auctionTrade.productId}') == false) {
		var obj = {
			date: '${auctionTrade.date}',
			price: '${auctionTrade.price}',
			quantity:'${auctionTrade.quantity}'
		};
		var temp=[obj];	
		map1.set('${auctionTrade.productId}',temp);
	} else {
		var temp1 = map1.get('${auctionTrade.productId}');
		var obj = {
				date: '${auctionTrade.date}',
				price: '${auctionTrade.price}',
				quantity:'${auctionTrade.quantity}'
			};
		temp1.push(obj);
		map1.set('${auctionTrade.productId}',temp1);
		}
</c:forEach>


$(document).ready(function() {
      $.ajax({
          url: (contextPath + "/setting/dashboard"),
          success: function(result){
              
			  let k=0;		  
			  			  
        	  for (let [key, value] of map1) {        		  
       			  let i=0;
       			  var date = [];
       			  var price = [];
       			  var quantity = [];
       			  
           		  value.forEach(function(valueSummary) {
	           		               		  
           			var a = valueSummary.date;
           			var part = a.split(' ');
           			var parts = part[0].split('-');           			
           			var mydate = Date.UTC(parts[0], parts[1] - 1, parts[2]);
           			
           			  date.push(mydate);
               		  price.push(parseFloat(valueSummary.price));
               		  quantity.push(Number(valueSummary.quantity));
               		  i = i + 1;
               	  });

	           	let priceData = price.map((price,i) => [date[i], price]);
	           	let quantityData = quantity.map((q, i) => [date[i], q]);	           		  
                 
           		 drawLineChart(charts[k], priceData, quantityData)
           		 k= k+1;
              }		  
          }          
      });  
      
      $("#search").click(chartupdate);   	
  });
	  
  function drawLineChart(id,price, quantity){
		  
		 Highcharts.stockChart(id,{
			        chart: {
			        },
			
			        title:'Product Price-Quantity Chart',
			        rangeSelector: {
			            selected: 0,
			            allButtonsEnabled: true,
		                inputEnabled: true,
		                buttons: [{
		                    type: 'week',
		                    count: 1,
		                    text: '1w'
		                }, {
		                    type: 'month',
		                    count: 1,
		                    text: '1m'
		                }, {
		                    type: 'month',
		                    count: 3,
		                    text: '3m'
		                },{
		                    type: 'year',
		                    count: 1,
		                    text: '1y'
		                }, {
		                    type: 'all',
		                    text: 'All'
		                }]           
			        },
			        
                    navigator:{
                        enabled:false
                        },
                        
			        xAxis: [{
				        type: 'datetime',
				        dateTimeLabelFormats: {		            
				            day: '%e %b<br>%Y'         
				        },
			        	 gridLineWidth:1,
			        	 lineWidth:2,
			         	 title : 'Date'		     	
			      }],
			        
			        yAxis: [{
			        	title: 'Price',
			            height: '50%',
			            offset: 0,
			            tickInterval: 10,
			            lineWidth:2
			            
			        } ,{
			        	
			        	title: 'Quantity',
			            top: '50%',
			            height: '50%',
			            offset: 0,
			            tickInterval: 100,
			            lineWidth:2
				    }],
			        
			
			        series: [{
			        	type: 'line',
				        name: 'Price',
			            data: price,
			            pointStart: Date.UTC(2020, 3, 1),
			            pointEnd: Date.UTC(2020, 3 , 30),
			            pointInterval: 24 * 3600 * 1000,
			            tooltip: {
					        pointFormat: "Price {point.y: .2f}"
				        },
			          
			        },{			        	
			        	type: 'column',
			        	name: 'quantity',
			            data: quantity,
			            yAxis: 1,
			            pointStart: Date.UTC(2020, 3, 1),
			            pointEnd: Date.UTC(2020, 3 , 30),
			            pointInterval: 24 * 3600 * 1000 	                      
				    }]
			    });
	    	}

  function chartupdate() {
	   var Id = $(this).val();
	   var name = document.getElementById("search1").value;
	   var type = document.getElementById("search2").value;
	   if(name == 0 & type ==0){location.reload();}else{
		  $.ajax({
			  type:"POST",
			  url: (contextPath + "/setting/searchProduct?name="+name+"&type="+type),
			
			  success: function(data) {
				  if (data.length>0){
					  document.getElementById("error").style.display='none';
					var map2= new Map();
					
					data.forEach(auctionTrade => {
						if (map2.has(auctionTrade.productId) == false) {
							var obj = {
								date: auctionTrade.date,
								price: auctionTrade.price,
								quantity:auctionTrade.quantity
							};
							var temp=[obj];	
							map2.set(auctionTrade.productId,temp);
						} else {
							var temp1 = map2.get(auctionTrade.productId);
							var obj = {
									date: auctionTrade.date,
									price: auctionTrade.price,
									quantity:auctionTrade.quantity
								};
							temp1.push(obj);
							map2.set(auctionTrade.productId,temp1);
							}
						});
					  let k=0;
					   for (let [key, value] of map2) {        		  
		       			  let i=0;
		       			  var date = [];
		       			  var price = [];
		       			  var quantity = [];
		       			  
		           		  value.forEach(function(valueSummary) {
			           		  
		           			var dates = new Date (valueSummary.date);
		           			var day= dates.getUTCDate();
		           			var month= dates.getUTCMonth();
		           			var year= dates.getUTCFullYear();
		           			           			
		           			var  mydate = Date.UTC(year, month, day);
		           			
		           			  date.push(mydate);
		               		  price.push(parseFloat(valueSummary.price));
		               		  quantity.push(Number(valueSummary.quantity));
		               		  i = i + 1;
		               	  });

			           	let priceData = price.map((price,i) => [date[i], price]);
			           	let quantityData = quantity.map((q, i) => [date[i], q]);	           		  
			
			            charts.forEach((chart)=>{
				            document.getElementById(chart).style.visibility='hidden';
			                
			            });
			            document.getElementById("chartOutput1").style.visibility='';    
		                drawLineChart("chartOutput1", priceData, quantityData)					
					   }
				  }else{
					  charts.forEach((chart)=>{
				            document.getElementById(chart).style.visibility='hidden';			                
			            });
					  document.getElementById("error").style.display='block'; 
			       }
				 
			  }
		  });
	   }
   }

 /*  let parsed_data = function parseData(data) {
	  var map2= new Map();
		
		data.forEach(auctionTrade => {
			if (map2.has(auctionTrade.productId) == false) {
				var obj = {
					date: auctionTrade.date,
					price: auctionTrade.price,
					quantity:auctionTrade.quantity
				};
				var temp=[obj];	
				map2.set(auctionTrade.productId,temp);
			} else {
				var temp1 = map2.get(auctionTrade.productId);
				var obj = {
						date: auctionTrade.date,
						price: auctionTrade.price,
						quantity:auctionTrade.quantity
					};
				temp1.push(obj);
				map2.set(auctionTrade.productId,temp1);
				}
			});
		  let k=0;
		   for (let [key, value] of map2) {        		  
 			  let i=0;
 			  var date = [];
 			  var price = [];
 			  var quantity = [];
 			  
     		  value.forEach(function(valueSummary) {
         		  
     			var dates = new Date (valueSummary.date);
     			var day= dates.getUTCDate();
     			var month= dates.getUTCMonth();
     			var year= dates.getUTCFullYear();
     			           			
     			var  mydate = Date.UTC(year, month, day);
     			
     			  date.push(mydate);
         		  price.push(parseFloat(valueSummary.price));
         		  quantity.push(Number(valueSummary.quantity));
         		  i = i + 1;
         	  });

         	let priceData = price.map((price,i) => [date[i], price]);
         	let quantityData = quantity.map((q, i) => [date[i], q]);
         	
         	return [priceData, quantityData];
	  }; */
</script>
                
