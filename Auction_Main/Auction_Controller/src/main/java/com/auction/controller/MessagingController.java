package com.auction.controller;

import java.sql.Blob;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.util.Base64;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.MessageCenterBean;
import com.auction.model.views.MessageCenterView;
import com.auction.service.LoginDetailsService;
import com.auction.service.MessageCenterService;

@Controller
@RequestMapping("/commmon")
public class MessagingController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private MessageCenterService messageCenterService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method is used to display messaging page.
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return messaging page
	 */

	@RequestMapping("/messaging")
	// @RequestMapping(method = RequestMethod.GET)
	public String messaging(Model model, HttpServletRequest request) {
		logger.info("MessagingController Class call messaging method");
		LoginDetailsBean loginUserInfo = SessionHelper.getLoginDetailsBean(request);
		model.addAttribute("loginDetailsBean",
				loginDetailsService.findByAccountStatusCodesAccountStatusCodeAndLoginUseridNot(ENUM_AccountStatusCodes.ACTIVE.getStatus(), loginUserInfo.getLoginUserid()));
		model.addAttribute("loginUserInfo", loginUserInfo);
		logger.info("=== messaging Metohd ending ===");
		return ViewConstant.MESSAGING;
	}

	/**
	 * This method is used to display messaging page.
	 * 
	 * @param request
	 *            HttpServletRequest is for getting session value.
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return messaging page
	 */

	@RequestMapping("/asyMessaging")
	// @RequestMapping(method = RequestMethod.GET)
	public String asyMessaging(Model model, HttpServletRequest request) {
		logger.info("MessagingController Class call asyMessaging method");
		logger.info("=== asyMessaging Metohd ending ===");
		return ViewConstant.MESSAGING;
	}

	/**
	 * This method is used to save messaging
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 *
	 * @param request
	 *            HttpServletRequest is for getting request value.
	 *            
	 * @return same  page
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMessage", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String saveMessage(HttpServletRequest request, @RequestParam(value = "picture", required = false) MultipartFile[] pictures, Model model,
			RedirectAttributes redirectAttributes) {
		logger.info("MessagingController Class call saveMessage method");
		String dateTime = "";
		String result = "";
		String response = "";
		Blob blob = null;
		Integer fromAccountId = Integer.parseInt(request.getParameter("fromAccountId"));
		Integer toAccoutId = Integer.parseInt(request.getParameter("toAccoutId"));
		String message = request.getParameter("messgeValue");
		if (null != pictures && pictures.length > 0) {
			for (MultipartFile Picture : pictures) {
				try {
					byte[] contents = Picture.getBytes();
					blob = BlobProxy.generateProxy(contents);
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		MessageCenterBean bean = new MessageCenterBean();
		AccountProfileBean accountFromAccountId = new AccountProfileBean();
		accountFromAccountId.setAccountId(fromAccountId);
		bean.setAccountProfileByFromAccountId(accountFromAccountId);
		AccountProfileBean accountProfileByToAccountId = new AccountProfileBean();
		accountProfileByToAccountId.setAccountId(toAccoutId);
		bean.setAccountProfileByToAccountId(accountProfileByToAccountId);
		bean.setMessage(message);
		bean.setMessageTimestamp(InternetTiming.getInternetDateTime());
		bean.setAttachment(blob);
		if (blob != null) {
			bean.setAttachmentType("img");
		}
		bean.setReserved(null);
		Integer savedMessagedId = messageCenterService.save(bean);
		dateTime = DateHelper.dateToStringAmPm(InternetTiming.getInternetDateTime());
		if (blob == null) {
			result = "messge";
			response = "{\"fromId\":\"" + fromAccountId + "\", \"msg\":\"" + message + "\",\"date\":\"" + dateTime + "\",\"result\":\"" + result + "\"}";
			logger.info(" saveMessage response" + response);
			simpMessagingTemplate.convertAndSend("/wssauctions/senderReceiverMsg_" + toAccoutId, response);
		} else {
			byte[] imgBytes = null;
			String imgBytesString = "";
			try {
				imgBytes = blob.getBytes(1, (int) blob.length());
				imgBytesString = new String(Base64.encodeBase64(imgBytes), "UTF-8");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			response = "{\"fromId\":\"" + fromAccountId + "\", \"msg\":\"" + message + "\", \"picture\":\"" + imgBytesString + "\",\"date\":\"" + dateTime + "\"}";
			logger.info(" saveMessageAnd Update img response" + response);
			simpMessagingTemplate.convertAndSend("/wssauctions/senderReceiverMsg_" + toAccoutId, response);
		}
		logger.info("=== saveMessage Metohd ending ===");
		return response;
	}

	/**
	 * This method is used to saveAudion
	 * * @param model
	 *            Spring model for passing data controller to view page
	 *
	 * @param request
	 *            HttpServletRequest is for getting request value.
	 *            
	 * @return same  page
	 */
	
	@ResponseBody
	@RequestMapping(value = "/saveAudion", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String saveAudion(HttpServletRequest request, @RequestParam(value = "audio", required = false) MultipartFile[] audio, Model model,
			RedirectAttributes redirectAttributes) {
		logger.info("MessagingController Class call saveAudion method");
		Blob blob = null;
		Integer fromAccountId = Integer.parseInt(request.getParameter("fromAccountId"));
		Integer toAccoutId = Integer.parseInt(request.getParameter("toAccoutId"));
		if (null != audio && audio.length > 0) {
			for (MultipartFile Audio : audio) {
				try {
					byte[] contents = Audio.getBytes();
					blob = BlobProxy.generateProxy(contents);
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		MessageCenterBean bean = new MessageCenterBean();
		AccountProfileBean accountFromAccountId = new AccountProfileBean();
		accountFromAccountId.setAccountId(fromAccountId);
		bean.setAccountProfileByFromAccountId(accountFromAccountId);
		AccountProfileBean accountProfileByToAccountId = new AccountProfileBean();
		accountProfileByToAccountId.setAccountId(toAccoutId);
		bean.setAccountProfileByToAccountId(accountProfileByToAccountId);
		bean.setMessageTimestamp(InternetTiming.getInternetDateTime());
		bean.setMessage("");
		bean.setAttachment(blob);
		bean.setAttachmentType("audio");
		bean.setReserved(null);
		Integer savedMessagedId = messageCenterService.save(bean);
		
		String dateTime = DateHelper.dateToStringAmPm(InternetTiming.getInternetDateTime());
		byte[] audionBytes = null;
		String audionBytesString = "";
		try {
			audionBytes = blob.getBytes(1, (int) blob.length());
			audionBytesString = new String(Base64.encodeBase64(audionBytes), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String response = "{\"fromId\":\"" + fromAccountId + "\", \"msg\":\"" + " " + "\", \"audio\":\"" + audionBytesString + "\",\"date\":\"" + dateTime + "\"}";
		logger.info(" saveaudion response" + response);
		simpMessagingTemplate.convertAndSend("/wssauctions/senderReceiverMsg_" + toAccoutId, response);
		logger.info("=== saveAudion Metohd ending ===");
		return response;

	}

	/**
	 * This method is used to sendReceiver getMessge
	 * 
	 * 	/**
	 * This method is used to saveAudion
	 * * @param model
	 *            Spring model for passing data controller to view page
	 *
	 * @param request
	 *            HttpServletRequest is for getting request  value.
	 *            
	 * @return same  page
	 */
	
	@ResponseBody
	@RequestMapping(value = "/getMessge/sendReceiver", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<MessageCenterView> getMessage(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		logger.info("MessagingController Class call getMessge method  and sendReceiver msg");
		Integer senderAccountId = Integer.parseInt(request.getParameter("senderAccountId"));
		Integer receiverAccountId = Integer.parseInt(request.getParameter("receiverAccountId"));
		List<MessageCenterView> senderMessage = messageCenterService.getAllSenderMessageByAccountId(senderAccountId, receiverAccountId, receiverAccountId, senderAccountId);
		if (senderMessage == null) {
			model.addAttribute("senderMessage", " ");
		} else {
			model.addAttribute("senderMessage", senderMessage);
		}
		logger.info("=== getMessge method  and sendReceiver msg Metohd ending ===");
		return senderMessage;
	}

}