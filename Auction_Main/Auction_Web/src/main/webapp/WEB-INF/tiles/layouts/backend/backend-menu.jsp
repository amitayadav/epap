<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="col-md-3 left_col menu_fixed mCustomScrollbar">
    <div class="left_col scroll-view">
        <div class="navbar nav_title">
            <a href="${contextPath}/setting/dashboard" class="site_title">
              <!--<img src="${contextPath}/resources/images/Webp.net-resizeimage.png" width="92%"/>-->
                <!--fahad: EPAP logo-->
                <img src="${contextPath}/resources/images/EPAP_Logo3.png" width="48%" height="92%"/>      </a>
        </div>
        <div class="clearfix"></div>
        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic"></div>
            <div class="profile_info">
                <span>
                    <spring:message code="menu.span.welcome"/>
                    <!--fahad: fix welcome message-->
                    <!--<span class="text-bold info-text"><spring:message code="account.type.${loginUser.accountTypeCodesBean.accountType}"/>.</span>-->
                    <span class="text-bold info-text"> ${loginUser.accountProfileBean.publicName} </span>
                </span>
                <%-- <h5 class="info-text">
                  <c:choose>
                    <c:when test="${not empty loginUser && not empty loginUser.accountProfileBean}">
                      ${loginUser.accountProfileBean.FName} ${loginUser.accountProfileBean.LName} (${loginUser.publicName})
                    </c:when>
                    <c:otherwise>
                      ${loginUser.publicName} 
                    </c:otherwise>
                  </c:choose>
                </h5> --%>
                <!--fahad: drop owner details in welcome section-->
                <%-- <sec:authorize access=" hasAnyRole('ROLE_SELLER_AGENT','ROLE_BUYER_AGENT','ROLE_SHIPPER_AGENT')">
                     <div class="spacer-tb-5">
                         <spring:message code="menu.ownerdetails"/>
                         <span class="text-bold info-text">${ownerName}</span>
                     </div> 
                 </sec:authorize>--%>
                <sec:authorize access="hasAnyRole('ROLE_SELLER','ROLE_SELLER_AGENT', 'ROLE_BUYER','ROLE_BUYER_AGENT','ROLE_SHIPPER','ROLE_SHIPPER_AGENT')">
                    <c:if test="${not empty loginUser && not empty loginUser.accountProfileBean}">
                        <div id="available_balance_block" class="spacer-tb-5">
                            <span><spring:message code="menu.span.availablebalance"/></span>&nbsp;&nbsp;
                            <span class="text-bold info-text" id="available_balance">
                                <img alt="loader" src="${contextPath}/resources/images/loading.gif" width="25" height="25">
                            </span>
                        </div>
                    </c:if>
                </sec:authorize>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- /menu profile quick info -->
        <!-- <br /> -->
        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <%-- <h3><spring:message code="menu.heading.navigation"/></h3> --%>
                <c:if test="${not empty loginUser && not empty loginUser.accountProfileBean}">
                    <ul class="nav side-menu">

                        <!-- Profile Menu =========================================================================================================================== -->
                        <li>
                            <a>
                                <i class="fa fa-info-circle"></i>
                                <spring:message code="menu.main.profile"/>
                                <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                                <li><a href="${contextPath}/setting/changepassword" class="instant-load"><spring:message code="menu.profile.changePassword"/></a></li>
                                <li><a href="${contextPath}/setting/editProfile" class="instant-load"><spring:message code="menu.profile.editProfile"/></a></li>
                                    <sec:authorize access="hasRole('ROLE_SELLER')">
                                    	<li><a href="${contextPath}/seller/sellerinfopictures"  class="instant-load"><spring:message code="menu.profile.sellerInfoAndPic"/></a></li>
                                        <%-- <li><a id="menusellerinfopictures"  ><spring:message code="menu.profile.sellerInfoAndPic"/></a></li> --%>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_SHIPPER')">
                                    	<li><a href="${contextPath}/shipper/shipperinfopictures"  class="instant-load"><spring:message code="menu.profile.shipperInfoAndPic"/></a></li>
                                        <%-- <li><a id="menusellerinfopictures" href="${contextPath}/shipper/sellerinfopictures"><spring:message code="menu.profile.sellerInfoAndPic"/></a></li> --%>
                                    </sec:authorize>
                            </ul>
                        </li>

                        <!-- ===== Auction Menu =================================================================================================================== -->
                        <li>
                            <a>
                                <i class="fa fa-money"></i>
                                <spring:message code="menu.main.auction" />
                                <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                                <li><a href="${contextPath}/auctions/auctionList" class="instant-load"><spring:message code="menu.auction.dayauction"/></a></li>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')">
                                    <li><a href="${contextPath}/spropt/auctions/auctionlist" class="instant-load"><spring:message code="menu.auction.auctionmanagement"/></a></li>
                                    </sec:authorize>
                                <li><a href="${contextPath}/auctions/auctiontrades" class="instant-load"><spring:message code="menu.auction.auctiontrades"/></a></li>
                                    <sec:authorize access="hasAnyRole('ROLE_SELLER','ROLE_SELLER_AGENT')">
                                        <%-- <li><a href="${contextPath}/ssa/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    <li><a href="${contextPath}/general/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_BUYER','ROLE_BUYER_AGENT')">
                                        <%-- <li><a href="${contextPath}/bba/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    <li><a href="${contextPath}/general/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_SHIPPER','ROLE_SHIPPER_AGENT')">
                                        <%-- <li><a href="${contextPath}/shsha/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    <li><a href="${contextPath}/general/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_FINANCE')">
                                    <li><a href="${contextPath}/finance/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li>  
                                        <%-- 	<li><a href="${contextPath}/general/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_RELATIONS')">
                                    <li><a href="${contextPath}/relation/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li> 
                                        <%-- 	<li><a href="${contextPath}/general/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SHIPPING')">
                                    <li><a href="${contextPath}/shipping/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li> 
                                        <%-- 	<li><a href="${contextPath}/general/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER')">
                                    <li><a href="${contextPath}/super/shipperlist" class="instant-load"><spring:message code="menu.auction.shippers"/></a></li> 
                                        <%-- 	<li><a href="${contextPath}/general/shipperlist"><spring:message code="menu.auction.shippers"/></a></li> --%>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER','ROLE_ADMIN_SHIPPING')">
                                        <li><a href="${contextPath}/sproptshp/asyncPickupTickets" class="instant-load"><spring:message code="menu.common.pickupTickets"/></a></li>
                                        </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_SHIPPING')">
                                    <li><a href="${contextPath}/sproptshp/manageshipping" class="instant-load"><spring:message code="menu.auction.manageshipping"/></a></li>
                                    </sec:authorize>
                            </ul>
                        </li>

                        <!-- Masters Menu ============================================================================================================================ -->
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_RELATIONS', 'ROLE_ADMIN_SHIPPING')">
                            <li>
                                <a>
                                    <i class="fa fa-book"></i>
                                    <spring:message code="menu.main.masters"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                                        <li><a href="${contextPath}/super/royaltycodedetailslist" class="instant-load"><spring:message code="menu.masters.royaltycodesdetails"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_RELATIONS', 'ROLE_ADMIN_SHIPPING')">
                                        <li><a href="${contextPath}/admin/notificationeventdetailslist" class="instant-load"><spring:message code="menu.masters.notificationeventsdetails"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')">
                                        <li><a href="${contextPath}/spropt/productcataloglist" class="instant-load"><spring:message code="menu.masters.productcatalogdetails"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasRole('ROLE_ADMIN_SUPERUSER')">
                                        <li><a href="${contextPath}/super/auctionsettingslist" class="instant-load"><spring:message code="menu.masters.applicationsettings"/></a></li>
                                        </sec:authorize>
                                   		 <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.masters.generalreports"/></a></li>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')">
                                        <li><a href="${contextPath}/spropt/report/systemreports" class="instant-load"><spring:message code="menu.masters.systemreports"/></a></li>
                                        </sec:authorize>
                                        
                                        <sec:authorize access="hasRole('ROLE_ADMIN_FINANCE')">
                                        <li><a href="${contextPath}/finance/report/" class="instant-load"><spring:message code="menu.auction.financereports"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasRole('ROLE_ADMIN_RELATIONS')">
                                        <li><a href="${contextPath}/relation/report" class="instant-load"><spring:message code="menu.auction.relationsreports"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasRole('ROLE_ADMIN_SHIPPING')">
                                        <li><a href="${contextPath}/shipping/report" class="instant-load"><spring:message code="menu.auction.shippingreports"/></a></li>
                                        </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>

                        <!-- ===== User Details Menu ============================================================================================================== -->
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_RELATIONS', 'ROLE_ADMIN_SHIPPING','ROLE_ADMIN_VAT')">
                            <li>
                                <a>
                                    <i class="fa fa-user"></i>
                                    <spring:message code="menu.main.userdetails"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION', 'ROLE_ADMIN_RELATIONS')">
                                        <li><a href="${contextPath}/sproptrel/userdetailslist" class="instant-load"><spring:message code="menu.userdetails.updateuserdetails"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_SHIPPING')">
                                        <li><a href="${contextPath}/sprfinshp/userfinancialstatement" class="instant-load"><spring:message code="menu.userdetails.userfinancialstatement"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_FINANCE')">
                                        <li><a href="${contextPath}/finance/userbalancedetailslist" class="instant-load"><spring:message code="menu.userdetails.userbalancemanagement"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER')">
                                        <li><a href="${contextPath}/super/epapAccountStatement" class="instant-load"><spring:message code="menu.epapa.epapaccountstatement"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER','ROLE_ADMIN_FINANCE','ROLE_ADMIN_VAT')">
                                        <li><a href="${contextPath}/sprfinvat/vatAccountStatement" class="instant-load"><spring:message code="menu.vat.vataccountstatement"/></a></li>
                                        </sec:authorize>
                                </ul>
                            </sec:authorize>            

                            <!-- ===== Sellers Menu =================================================================================================================== -->
                            <sec:authorize access="hasRole('ROLE_SELLER')">
                            <li>
                                <a>
                                    <i class="fa fa-user-secret"></i>
                                    <spring:message code="menu.main.seller"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <li><a href="${contextPath}/stransaction/financialstatement" class="instant-load"><spring:message code="menu.common.financialstatement"/></a></li>
                                    <li><a href="${contextPath}/salesNotice/seller" class="instant-load"><spring:message code="menu.common.salesNotices"/></a></li>
                                    <li><a href="${contextPath}/ssa/asyncPickupTickets" class="instant-load"><spring:message code="menu.common.pickupTickets"/></a></li>
                                    <li><a href="${contextPath}/sbs/agentlist" class="instant-load"><spring:message code="menu.common.agentMgnt"/></a></li>
                                    <li><a href="${contextPath}/sbs/accountrequest" class="instant-load"><spring:message code="menu.common.accountrequests"/></a></li>
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.common.generalreports"/></a></li>
                                </ul>
                            </sec:authorize>

                            <!-- ===== Sellers Agent Menu ============================================================================================================ -->
                            <sec:authorize access="hasRole('ROLE_SELLER_AGENT')">
                            <li>
                                <a>
                                    <i class="fa fa-user-secret"></i>
                                    <spring:message code="menu.main.selleragent"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.common.generalreports"/></a></li>
                                        <sec:authorize access="hasRole('ROLE_SELLER_AGENT_2')">
                                        <li><a href="${contextPath}/stransaction/financialstatement" class="instant-load"><spring:message code="menu.common.financialstatement"/></a></li>
                                        </sec:authorize>
                                      <li><a href="${contextPath}/ssa/asyncPickupTickets" class="instant-load"><spring:message code="menu.common.pickupTickets"/></a></li>
                                </ul>
                            </sec:authorize>
                            <!-- ===== Buyers Menu =================================================================================================================== -->
                            <sec:authorize access="hasRole('ROLE_BUYER')">
                            <li>
                                <a>
                                    <i class="fa fa-user-secret"></i>
                                    <spring:message code="menu.main.buyer"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <li><a href="${contextPath}/btransaction/financialstatement" class="instant-load"><spring:message code="menu.common.financialstatement"/></a></li>
                                    <li><a href="${contextPath}/invoicepurchase/buyer/invoicePurchase" class="instant-load"><spring:message code="menu.common.purchaseInvoices"/></a></li>
                                    <li><a href="${contextPath}/sbs/accountrequest" class="instant-load"><spring:message code="menu.common.accountrequests"/></a></li>
                                    <li><a href="${contextPath}/sbs/agentlist" class="instant-load"><spring:message code="menu.common.agentMgnt"/></a></li>
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.common.generalreports"/></a></li>
                                     <li><a href="${contextPath}/bba/asyncPickupTickets" class="instant-load"><spring:message code="menu.common.pickupTickets"/></a></li>
                                    <li><a href="${contextPath}/bba/confirmdelivery" class="instant-load"><spring:message code="menu.common.confirmdelivery"/></a></li>
                                </ul>
                            </li>
                        </sec:authorize>
                        <!-- ===== Buyers Agent Menu ============================================================================================================= -->
                        <sec:authorize access="hasRole('ROLE_BUYER_AGENT')">
                            <li>
                                <a>
                                    <i class="fa fa-user-secret"></i>
                                    <spring:message code="menu.main.buyeragent"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.common.generalreports"/></a></li>
                                  <li><a href="${contextPath}/bba/asyncPickupTickets" class="instant-load"><spring:message code="menu.common.pickupTickets"/></a></li>
                                    <li><a href="${contextPath}/bba/confirmdelivery" class="instant-load"><spring:message code="menu.common.confirmdelivery"/></a></li>
                                        <sec:authorize access="hasAnyRole('ROLE_BUYER_AGENT_2')">
                                        <li><a href="${contextPath}/btransaction/financialstatement" class="instant-load"><spring:message code="menu.common.financialstatement"/></a></li>
                                        </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>
                        <!-- ===== Shipper Menu ================================================================================================================== -->
                        <sec:authorize access="hasRole('ROLE_SHIPPER')">
                            <li>
                                <a>
                                    <i class="fa fa-user-secret"></i>
                                    <spring:message code="menu.main.shipper"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <li><a href="${contextPath}/shtransaction/financialstatement" class="instant-load"><spring:message code="menu.common.financialstatement"/></a></li>
                                    <li><a href="${contextPath}/sbs/accountrequest" class="instant-load"><spring:message code="menu.common.accountrequests"/></a></li>
                                    <li><a href="${contextPath}/sbs/agentlist" class="instant-load"><spring:message code="menu.common.agentMgnt"/></a></li>
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.common.generalreports"/></a></li>
                                    <li><a href="${contextPath}/shsha/confirmdelivery" class="instant-load"><spring:message code="menu.common.confirmdelivery"/></a></li>
                                </ul>
                            </li>
                        </sec:authorize>
                        <!-- ===== Shipper Agent Menu ============================================================================================================ -->
                        <sec:authorize access="hasRole('ROLE_SHIPPER_AGENT')">
                            <li>
                                <a>
                                    <i class="fa fa-user-secret"></i>
                                    <spring:message code="menu.main.shipperagent"/>
                                    <span class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.common.generalreports"/></a></li>
                                    <li><a href="${contextPath}/shsha/confirmdelivery" class="instant-load"><spring:message code="menu.common.confirmdelivery"/></a></li>
                                        <sec:authorize access="hasRole('ROLE_SHIPPER_AGENT_2')">
                                        <li><a href="${contextPath}/shtransaction/financialstatement" class="instant-load"><spring:message code="menu.common.financialstatement"/></a></li>
                                        </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>

                        <!-- ===== Communication Menu ============================================================================================================ -->
                        <li>
                            <a>
                                <i class="fa fa-comments"></i>
                                <spring:message code="menu.main.communication"/>
                                <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                                <sec:authorize access="!hasAnyRole('ROLE_USER','ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION','ROLE_ADMIN_FINANCE','ROLE_ADMIN_RELATIONS','ROLE_ADMIN_SHIPPING')">
                                    <li><a href="${contextPath}/setting/comments" class="instant-load"><spring:message code="menu.communication.comments"/></a></li>  	
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION','ROLE_ADMIN_FINANCE','ROLE_ADMIN_RELATIONS','ROLE_ADMIN_SHIPPING')">
                                    <li><a href="${contextPath}/admin/commentslist" class="instant-load"><spring:message code="menu.communication.commentsmanagement"/></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="!hasAnyRole('ROLE_USER')">
                                    <li>
                                    	<c:set var="fullRequestURL" value="${contextPath}/commmon/messaging"/>
                                    	<c:choose>
                                    		<c:when test='${requestURL ne  fullRequestURL}'>
                                    			<a href="${contextPath}/commmon/messaging" target="_blank"><spring:message code="menu.communication.messaging"/></a>
                                    		</c:when>
                                    		<c:otherwise>
                                    			<a href="${contextPath}/commmon/messaging"><spring:message code="menu.communication.messaging"/></a>
                                    		</c:otherwise>
                                    	</c:choose>
                                    </li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_RELATIONS')">
                                        <li><a href="${contextPath}/sproptrel/announcement" class="instant-load"><spring:message code="menu.masters.announcement"/></a></li>
                                        </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_USER')">
                                    <li><a href="${contextPath}/common/report/general" class="instant-load"><spring:message code="menu.masters.generalreports"/></a></li>
                                    </sec:authorize>					
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')">
                                    <li><a href="${contextPath}/spropt/adminauctionrequestlist" class="instant-load"><spring:message code="menu.communication.manageauctionrequest"/></a></li>
                                    </sec:authorize>
                                    <%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION','ROLE_ADMIN_FINANCE','ROLE_ADMIN_RELATIONS')">
                                            <li><a href="${contextPath}/setting/notificationeventsubscription"><spring:message code="menu.communication.manageevents"/></a></li>
                                    </sec:authorize> --%>
                                <li><a href="${contextPath}/setting/notificationeventsubscription" class="instant-load"><spring:message code="menu.communication.eventsubscription"/></a></li>		                
                                    <sec:authorize access="hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT')">
                                    <li><a href="${contextPath}/ssa/auctionrequestlist" class="instant-load"><spring:message code="menu.communication.auctionrequest"/></a></li>
                                    </sec:authorize>
                            </ul>
                        </li>

                        <!-- Non-Admins' Menu ============================================================================================= -->
                        <%-- <sec:authorize access="!hasRole('ROLE_ADMIN')">
                          <li>
                            <a>
                              <i class="fa fa-money"></i>
                              <spring:message code="menu.a.auction" />
                              <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                              <li><a href="${contextPath}/auctions/auctionList"><spring:message code="menu.a.auction"/></a></li>
                            </ul>
                          </li>
                        </sec:authorize> --%>

                        <!-- Sellers' & Buyers' Menu ====================================================================================== -->
                        <%-- <sec:authorize access=" hasAnyRole('ROLE_SELLER','ROLE_BUYER','ROLE_SHIPPER','ROLE_SELLER_AGENT','ROLE_BUYER_AGENT','ROLE_SHIPPER_AGENT')">
                          <li>
                            <a>
                              <i class="fa fa-warning"></i>
                              <spring:message code="menu.a.complaints"/>
                              <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                              <li><a href="${contextPath}/setting/complaintsmanagement"><spring:message code="menu.li.complaints"/></a></li>
                            </ul>
                          </li>
                        </sec:authorize> --%>

                        <!-- Sellers' Menu ================================================================================================ -->
                        <%-- <sec:authorize access="hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT')">
                          <li>
                            <a>
                              <i class="fa fa-desktop"></i>
                              <spring:message code="menu.a.application.seller"/>
                              <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                              <sec:authorize access="hasRole('ROLE_SELLER')">
                              <li>
                                <a href="${contextPath}/seller/auctionrequestlist"><spring:message code="menu.li.sellerAuctionRequest"/></a>
                              </li>
                              </sec:authorize>
                              <sec:authorize access="hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT')">
                              <li>
                                <a href="${contextPath}/ssa/sellerinfopictures"><spring:message code="menu.li.sellerInfoAndPic"/></a>
                              </li>
                              </sec:authorize>
                              <sec:authorize access="hasAnyRole('ROLE_SELLER', 'ROLE_SELLER_AGENT_2')">
                              <li>
                                <a href="${contextPath}/stransaction/financialstatement"><spring:message code="menu.li.financialStatement"/></a>
                              </li>
                              </sec:authorize>
                            </ul>
                          </li>
                        </sec:authorize> --%>

                        <!-- Buyers' Menu ================================================================================================ -->
                        <%-- <sec:authorize access="hasAnyRole('ROLE_BUYER','ROLE_BUYER_AGENT_2')">
                          <li>
                            <a>
                              <i class="fa fa-desktop"></i>
                              <spring:message code="menu.a.buyer"/>
                              <span class="fa fa-chevron-down"></span>
                            </a>
                            <ul class="nav child_menu">
                              <li>
                                <a href="${contextPath}/btransaction/financialstatement"><spring:message code="menu.li.financialStatement"/></a>
                              </li>
                            </ul>
                          </li>
                        </sec:authorize> --%>

                        <!-- Admins' Menu =============================================================================================== -->
                        <!-- <sec:authorize access="hasRole('ROLE_ADMIN')">
              <li>
                <a>
                  <i class="fa fa-money"></i>
                            <spring:message code="menu.a.auction"/>
                            <span class="fa fa-chevron-down"></span>
                          </a>
                          <ul class="nav child_menu">
                            <li>
                              <a href="${contextPath}/admin/auctions/auctionlist"><spring:message code="menu.li.auctionMgnt"/></a>
                            </li>
                          </ul>
                        </li>
                        <li>
                          <a>
                            <i class="fa fa-book"></i>
                            <spring:message code="menu.a.master"/>
                            <span class="fa fa-chevron-down"></span>
                          </a>
                          <ul class="nav child_menu">
                            <li>
                              <a href="${contextPath}/admin/royaltycodedetailslist"><spring:message code="menu.li.royaltyCodeDetail"/></a>
                            </li>
                            <li>
                              <a href="${contextPath}/admin/notificationeventdetailslist"><spring:message code="menu.li.notificationEventDetaill"/></a>
                            </li>
                            <li>
                              <a href="${contextPath}/admin/productcataloglist"><spring:message code="menu.li.productCatalogDetaill"/></a>
                            </li>
                            <li>
                              <a href="${contextPath}/admin/adminauctionrequestlist"><spring:message code="menu.li.auctionRequestApproval"/></a>
                            </li>
                          </ul>
                        </li>
                        <li>
                          <a>
                            <i class="fa fa-user"></i>
                            <spring:message code="menu.a.updateUserDetail"/>
                            <span class="fa fa-chevron-down"></span>
                          </a>
                          <ul class="nav child_menu">
                            <li>
                              <a href="${contextPath}/admin/userdetailslist"><spring:message code="menu.li.updateUserDetail"/></a>
                            </li>
                            <li>
                              <a href="${contextPath}/admin/userfinancialstatement"><spring:message code="menu.li.userFinancialStat"/></a>
                            </li>
                            <li>
                              <a href="${contextPath}/admin/userbalancedetailslist"><spring:message code="menu.li.userBalanceMgnt"/></a>
                            </li>
                          </ul>
                        </li>
                        <li>
                          <a>
                            <i class="fa fa-warning"></i>
                            <spring:message code="menu.a.complaints"/>
                            <span class="fa fa-chevron-down"></span>
                          </a>
                          <ul class="nav child_menu">
                            <li>
                              <a href="${contextPath}/admin/complaintslist"><spring:message code="menu.li.complaints"/></a>
                            </li>
                          </ul>
                        </li>
                        <li>
                          <a>
                            <i class="fa fa-cog"></i>
                            <spring:message code="menu.a.application"/>
                            <span class="fa fa-chevron-down"></span>
                          </a>
                          <ul class="nav child_menu">
                            <li>
                              <a href="${contextPath}/admin/auctionsettingslist"><spring:message code="menu.li.applicationSettingDetail"/></a>
                            </li>
                          </ul>
                        </li>
                        <li>
                        </sec:authorize> -->
                    </ul>
                </c:if>
            </div>
        </div>
        <!-- /sidebar menu -->
        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">

            <a href="${contextPath}/setting/dashboard" class="instant-load" data-toggle="tooltip" data-placement="top" title='<spring:message code="menu.main.dashboard"/>'>
                <i class="fa fa-home"></i>
            </a>

            <a href="${contextPath}/setting/editProfile" class="pageContentMenu"  class="instant-load" data-toggle="tooltip" data-placement="top" title='<spring:message code="menu.profile.editProfile"/>'>
                <i class="fa fa-info-circle" aria-hidden="true"></i>
            </a>

            <sec:authorize access="!hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')">
                <a href="${contextPath}/auctions/auctionList" class="pageContentMenu" class="instant-load" data-toggle="tooltip" data-placement="top" title='<spring:message code="menu.main.auction"/>'>
                    <i class="fa fa-money" aria-hidden="true"></i>
                </a>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN_SUPERUSER', 'ROLE_ADMIN_OPERATION')">
                <a href="${contextPath}/admin/auctions/auctionlist" class="pageContentMenu" class="instant-load" data-toggle="tooltip" data-placement="top" title='<spring:message code="menu.li.auctionMgnt"/>'>
                    <i class="fa fa-money" aria-hidden="true"></i>
                </a>
            </sec:authorize>
            <a href="javascript:void(0);" onclick="javascript:formSubmit();" class="instant-load" data-toggle="tooltip" data-placement="top" title='<spring:message code="menu.logout"/>' href="${contextPath}/auth/logout">
                <i class="fa fa-power-off" aria-hidden="true"></i>
            </a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>
<sec:authorize access="hasAnyRole('ROLE_SELLER','ROLE_SELLER_AGENT', 'ROLE_BUYER','ROLE_BUYER_AGENT','ROLE_SHIPPER','ROLE_SHIPPER_AGENT')">
    <c:if test="${not empty loginUser && not empty loginUser.accountProfileBean && not empty loginUser.accountProfileBean.accountId}">
        <script type="text/javascript">
            function getAvailableBalance() {
                $.ajax({
                    type: "GET",
                    async: false,
                    cache: false,
                    url: (contextPath + "/auctions/getAvailableBalance"),
                    success: function (result) {
                        if (result == 0) {
                            var val = 0.00;
                            $("#available_balance").text(val.format(2));
                        } else {
                            $("#available_balance").text(result.format(2));
                        }
                    },
                    error: function (e) {
                        $("#available_balance").text("");
                    }
                });
            }
            getAvailableBalance();
        </script>
    </c:if>
</sec:authorize>
<script>
    $(document).ready(function () {
        /* $('#menuProfileChangePassword').on('click', function() {
         
         $.ajax({
         type: "GET",
         async: false,
         cache: false,      
         url: (contextPath + "/setting/changepassword"),
         success: function(result) {
         $("#page-content-id").empty().html(result);      
         },
         error: function(e) {
         
         } 
         
         
         });
         }); 
         
         $('#menusellerinfopictures').on('click', function() {
         
         $.ajax({
         type: "GET",
         async: false,
         cache: false,      
         url: (contextPath + "/seller/sellerinfopictures"),
         success: function(result) {
         $("#page-content-id").empty().html(result);      
         },
         error: function(e) {
         
         } 
         
         
         });
         });  */

    });
</script>