package com.auction.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_CommentsStatus;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.CommentsBean;
import com.auction.model.bean.EventsMeaningBean;
import com.auction.service.CommentsService;
import com.auction.service.EventsMeaningService;

@Controller
@RequestMapping("/admin")
public class AdminCommonController {

	private static final String inbox = "inbox";
	private static final String sent = "sent";
	private static final String both = "both";

	@Autowired
	private CommentsService commentsService;

	@Autowired
	private EventsMeaningService eventsMeaningService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	/**
	 * This method is used to display the Admin Home  page of application
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return admin home  page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return ViewConstant.ADMIN_HOME;
	}

	/* Comment Management */
	@RequestMapping(value = "/commentslist", method = { RequestMethod.GET, RequestMethod.POST })
	public String commentsList(HttpServletRequest request, Model model) {
		logger.info("Admin Common Controller Call commentsList method");
		String commentFilter = request.getParameter("commentFilter");
		if (null != commentFilter && commentFilter.equalsIgnoreCase(inbox)) {
			model.addAttribute("commentslist", commentsService.getByInboxAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(sent)) {
			model.addAttribute("commentslist", commentsService.getBySentAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(both)) {
			if (ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType() == SessionHelper.getAccountType(request, false)) {
				model.addAttribute("commentslist", commentsService.findAll());
			} else {
				model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
			}
		} else {
			if (ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType() == SessionHelper.getAccountType(request, false)) {
				model.addAttribute("commentslist", commentsService.findAll());
			} else {
				model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
			}
			commentFilter = both;

		}
		logger.info("=== Ending  commentsList  method ===");
		model.addAttribute("commentFilter", commentFilter);
		return ViewConstant.ADMIN_COMMENTS_LIST;
	}

	/* Comment Management NON AjAX */
	@RequestMapping(value = "/commentsList", method = { RequestMethod.GET, RequestMethod.POST })
	public String syncCommentsList(HttpServletRequest request, Model model) {
		logger.info("Admin Common Controller Call syncCommentsList method");
		String commentFilter = request.getParameter("commentFilter");
		if (null != commentFilter && commentFilter.equalsIgnoreCase(inbox)) {
			model.addAttribute("commentslist", commentsService.getByInboxAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(sent)) {
			model.addAttribute("commentslist", commentsService.getBySentAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
		} else if (null != commentFilter && commentFilter.equalsIgnoreCase(both)) {
			if (ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType() == SessionHelper.getAccountType(request, false)) {
				model.addAttribute("commentslist", commentsService.findAll());
			} else {
				model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
			}
		} else {
			if (ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType() == SessionHelper.getAccountType(request, false)) {
				model.addAttribute("commentslist", commentsService.findAll());
			} else {
				model.addAttribute("commentslist", commentsService.getByAccountProfileByAccountId(SessionHelper.getAccountProfileId(request)));
			}
			commentFilter = both;

		}
		logger.info("=== Ending  syncCommentsList  method ===");
		model.addAttribute("commentFilter", commentFilter);
		return ViewConstant.NONAJAX_ADMIN_COMMENTS_LIST;
	}

	/**
	 * This method is used To Edit Comment *
	 * 
	 * @param commentId
	 *            selecting commentId
	 * 
	 * @return AM_commentsAjax page
	 */
	@RequestMapping("/editcomment/{commentId}")
	public String editComment(@PathVariable("commentId") Integer commentId, Model model, HttpServletRequest request) {
		logger.info("Admin Common Controller Call editComment method");
		CommentsBean commentsBean = commentsService.findById(commentId);
		if (null != commentsBean && null != commentsBean.getCommentId() && commentsBean.getCommentId() > 0) {

			if (ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType() != SessionHelper.getAccountType(request, false)
					&& commentsBean.getAccountProfileBeanByAccountId().getAccountId() != SessionHelper.getAccountProfileId(request)) {
				model.addAttribute("ERROR", messageSource.getMessage("admincontroller.editcomment.privileges.error", null, localeResolver.resolveLocale(request)));
			} else {

				Map<Short, String> CommentsStatusList = new HashMap<Short, String>(0);
				for (ENUM_CommentsStatus val : ENUM_CommentsStatus.values()) {
					CommentsStatusList.put(val.getStatus(), val.getDesc());
				}
				model.addAttribute("CommentsStatusList", CommentsStatusList);
				model.addAttribute("commentsBean", commentsBean);
			}
		} else {
			model.addAttribute("ERROR", messageSource.getMessage("admincontroller.editcomment.error", null, localeResolver.resolveLocale(request)));
		}
		logger.info("=== Ending  editComment  method ===");
		return "/admin/AM_commentsAjax";
	}

	/**
	 * This method is used To Update Comment *
	 * 
	 * @param CommentsBean
	 *            selecting CommentsBean
	 * 
	 * @return commentslist page
	 */
	@RequestMapping(value = "/updatecomment", method = RequestMethod.POST)
	public ModelAndView updateComment(@ModelAttribute("commentsBean") CommentsBean commentsBean, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("Admin Common Controller Call updateComment method");
		String view = "/admin/commentslist";

		CommentsBean currentCommentsBean = commentsService.findById(commentsBean.getCommentId());

		currentCommentsBean.setCommentStatus(commentsBean.getCommentStatus());
		currentCommentsBean.setFeedback(commentsBean.getFeedback());
		currentCommentsBean.setUpdatedTimestamp(InternetTiming.getInternetDateTime());
		currentCommentsBean.setAccountProfileBeanByUpdatedBy(new AccountProfileBean(SessionHelper.getAccountProfileId(request)));

		commentsService.save(currentCommentsBean);
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("admincontroller.updatecomment.success", null, localeResolver.resolveLocale(request)));
		logger.info("=== Ending  updateComment  method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/* Notification Event Master */
	@RequestMapping("/notificationeventdetailslist")
	public String notificationeventdetailslist(Model model) {
		logger.info("Admin Common Controller Call notificationeventdetailslist method");
		model.addAttribute("eventsMeaningBeanList", eventsMeaningService.findAll());
		logger.info("=== Ending  notificationeventdetailslist method ===");
		return ViewConstant.ADMIN_NOTIFFICATION_EVENT_LIST;
	}

	/* Notification Event Master non ajax */
	@RequestMapping("/notificationEventDetailsList")
	public String notificationEventDetailsList(Model model) {
		logger.info("Admin Common Controller Call notificationEventDetailsList  method");
		model.addAttribute("eventsMeaningBeanList", eventsMeaningService.findAll());
		logger.info("=== Ending  notificationEventDetailsList method ===");
		return ViewConstant.NONAJAX_ADMIN_NOTIFFICATION_EVENT_LIST;
	}

	/**
	 * This method is used To Notification Event Details * @param eventId selecting
	 * eventId
	 * 
	 * @return AM_notificationEventAjaxForm page
	 */
	@RequestMapping("/notificationeventdetails/{eventId}")
	public String notificationeventdetails(@PathVariable(value = "eventId", required = false) Integer eventId, Model model) {
		logger.info("Admin Common Controller Call notificationeventdetails  method");
		if (null != eventId && eventId > 0) {
			model.addAttribute("eventsMeaningBean", eventsMeaningService.findById(eventId));
		} else {
			model.addAttribute("eventsMeaningBean", new EventsMeaningBean());
		}
		logger.info("=== Ending  notificationeventdetails  method ===");
		return "/admin/AM_notificationEventAjaxForm";
	}

	/**
	 * This method is used To Save Notification Event Details *
	 * 
	 * @param EventsMeaningBean
	 *            selecting EventsMeaningBean
	 * 
	 * @return notificationeventdetailslist page
	 */
	@RequestMapping(value = "/savenotificationEventDetails", method = RequestMethod.POST)
	public ModelAndView savenotificationEventDetails(@ModelAttribute("eventsMeaningBean") EventsMeaningBean eventsMeaningBean, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		logger.info("Admin Common Controller Call savenotificationEventDetails  method");
		if (null != eventsMeaningBean) {
			if (null != eventsMeaningBean.getEventId()) {
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.savenotificationeventdetails.success.updated", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.savenotificationeventdetails.success.added", null, localeResolver.resolveLocale(request)));
			}
			eventsMeaningService.save(eventsMeaningBean);
		}
		logger.info("=== Ending  savenotificationEventDetails  method ===");
		RedirectView red = new RedirectView("/admin/notificationeventdetailslist", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
}