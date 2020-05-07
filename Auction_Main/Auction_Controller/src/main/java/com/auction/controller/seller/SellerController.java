package com.auction.controller.seller;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountLocationStatus;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.FileUtils;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.SellerValidator;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.SellerInfoBean;
import com.auction.model.bean.SellerPicturesBean;
import com.auction.service.AccountLocationsService;
import com.auction.service.AccountProfileService;
import com.auction.service.SellerInfoService;
import com.auction.service.SellerPicturesService;

@Controller
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerInfoService sellerInfoService;

	@Autowired
	private SellerPicturesService sellerPicturesService;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SellerValidator sellerValidator;
	
	@Autowired
	private AccountLocationsService accountLocationsService;
	
	@Value("${auction.selleroffer.picture.size}")
	private long sellerOfferPictureSize;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	/**
	 * By default method for this controller which will be accessible by /seller request mapping with get method.
	 * 
	 * @return it will return on seller home tiles definition.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return ViewConstant.SELLER_HOME;
	}

	/**
	 * This method will show seller into and picture view page with list of uploaded seller pictures and seller info.
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return it will return on tiles definition to provide appropriate output on browser screen.
	 * 
	 */
	@RequestMapping("/sellerinfopictures")
	public String sellerInfo(HttpServletRequest request, Model model) {
		logger.info("SellerController Call sellerInfo method");
		Integer accountId = SessionHelper.getAccountProfileId(request);
		if (null != accountId && accountId > 0) {
			AccountProfileBean accountProfileBean = accountProfileService.findById(accountId);
			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("sellerInfoBean", sellerInfoService.findById(accountId));
			model.addAttribute("sellerPicturesBeanList", sellerPicturesService.getByAccountProfileId(SessionHelper.getAccountProfileId(request)));
		}
		logger.info("=== ending  sellerInfo method ===");
		return ViewConstant.SELLER_INFO_PICTURE;
	}

	/**
	 * This method is used for return code / HTML output via AJAX for upload seller pictures.
	 * 
	 * @param accountId
	 *            from requested URL spring will get accountId provided in AJAX request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return it will return processed output for upload seller picture view.
	 * 
	 */
	@RequestMapping("/sellerPictureAjax/{accountId}")
	public String sellerPictureAjax(@PathVariable("accountId") Integer accountId, Model model) {
		logger.info("SellerController Call sellerPictureAjax method");
		model.addAttribute("sellerPicturesBean", new SellerPicturesBean());
		logger.info("=== ending  sellerPictureAjax method ===");
		return "/seller/SM_sellerPictureUploadAjaxForm";
	}

	/**
	 * It will store seller into database.
	 * 
	 * @param sellerInfoBean
	 *            spring will get sellerInfoBean from model attribute of spring.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any request mapping / URL.
	 * @return it will redirect on welcome URL / request mapping.
	 * 
	 */
	@RequestMapping(value = "/savesellerinfo", method = RequestMethod.POST)
	public ModelAndView saveSellerInfo(@ModelAttribute("sellerInfoBean") SellerInfoBean sellerInfoBean, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("SellerController Call saveSellerInfo method");
		String view = "/seller/sellerinfopictures";
		List<String> errorStack = sellerValidator.validateSellerInfo(sellerInfoBean, request);

		if (null == errorStack || errorStack.size() == 0) {
			sellerInfoService.save(sellerInfoBean);
			redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.savesellerinfo.success", null, localeResolver.resolveLocale(request)));
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
		}
		logger.info("=== ending  saveSellerInfo method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * It will store uploaded seller pictures.
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param sellerPictures
	 *            it will get physical image files uploaded by seller from request.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any request mapping / URL.
	 * @return it will redirect on welcome URL / request mapping.
	 * 
	 */
	@RequestMapping(value = "/uploadsellerpicture", method = RequestMethod.POST)
	public ModelAndView uploadSellerPicture(HttpServletRequest request, @RequestParam(value = "sellerPicture", required = false) MultipartFile[] sellerPictures,
			RedirectAttributes redirectAttributes) {
		logger.info("SellerController Call uploadSellerPicture method");
		if (null != sellerPictures && sellerPictures.length > 0) {
			for (MultipartFile sellerPicture : sellerPictures) {
				String fileName = sellerPicture.getOriginalFilename();
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					// fileName = arr[0];
					// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
					fileName = ("seller_picture_" + new Date().getTime() + "." + arr[1]);
				} else {
					// fileName = fileName.concat("_" + new Date().getTime());
					fileName = ("seller_picture_" + new Date().getTime());
				}
				FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_PICTURE_PATH, sellerPicture);
				SellerPicturesBean sellerPicturesBean = new SellerPicturesBean();
				sellerPicturesBean.setAccountProfileBean(new AccountProfileBean(SessionHelper.getAccountProfileId(request)));
				sellerPicturesBean.setPictureLocation(fileName);
				sellerPicturesService.save(sellerPicturesBean);
			}
		}
		logger.info("=== ending  uploadSellerPicture method ===");
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.uploadsellerpicture.success", null, localeResolver.resolveLocale(request)));
		RedirectView red = new RedirectView("/seller/sellerinfopictures", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used to remove the particular seller uploaded picture by picure id
	 * 
	 * @param id
	 *            picture id by remove image
	 * @return true if successfully remove image
	 */
	@RequestMapping("/removepicture/{id}")
	public @ResponseBody String removepicture(@PathVariable("id") Integer id) {
		SellerPicturesBean sellerPicturesBean = sellerPicturesService.findById(id);
		//FileUtils.deleteFile(sellerPicturesBean.getAccountProfileBean().getAccountId(), CommonConstants.SELLER_PICTURE_PATH, sellerPicturesBean.getPictureLocation());
		sellerPicturesService.delete(new SellerPicturesBean(id));
		return "true";
	}
	
	
	@RequestMapping("/sellerchangelocation/{auctionSellersId}/{dailyAuctionId}")
	public String sellerChangeLocation(Model model,@PathVariable(value = "auctionSellersId", required = false) Integer auctionSellersId,
			@PathVariable(value = "dailyAuctionId", required = false) Integer dailyAuctionId ,HttpServletRequest request) {
		logger.info("SellerController Call sellerChangeLocation method");
		model.addAttribute("auctionSellersBean", new AuctionSellersBean());
		model.addAttribute("dailyAuctionId","dailyAuctionId");
		model.addAttribute("auctionSellersId","auctionSellersId");
		
		model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(auctionSellersId,
				new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
		//return ViewConstant.SELLER_CHANGE_LOCATION;
		logger.info("=== ending  sellerChangeLocation method ===");
		return "seller/SM_changeLocation";
	}
	
	@RequestMapping(value = "/syncuploadsellerpicture", method = RequestMethod.POST)
	public ModelAndView syncUploadSellerPicture(HttpServletRequest request, @RequestParam(value = "sellerPicture", required = false) MultipartFile[] sellerPictures,
			RedirectAttributes redirectAttributes) {
		logger.info("SellerController Call syncUploadSellerPicture method");
		boolean isSellerOfferPicture =true;
		if (null != sellerPictures && sellerPictures.length > 0) {
			for (MultipartFile sellerPicture : sellerPictures) {
				Blob blob=null;
		        try{
		       byte[] contents = sellerPicture.getBytes();
		       		blob = BlobProxy.generateProxy(contents);
		       		if(sellerOfferPictureSize < sellerPicture.getSize()) {
		       			isSellerOfferPicture = false;
		       			//errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.sellershippercharge.number", null, locale));
		       		}
		       }
		       catch(Exception e)
		        {e.printStackTrace();
		       }
				String fileName = sellerPicture.getOriginalFilename();
				if (isSellerOfferPicture) {
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					// fileName = arr[0];
					// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
					fileName = ("seller_picture_" + new Date().getTime() + "." + arr[1]);
				} else {
					// fileName = fileName.concat("_" + new Date().getTime());
					fileName = ("seller_picture_" + new Date().getTime());
				}
				//FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_PICTURE_PATH, sellerPicture);
				SellerPicturesBean sellerPicturesBean = new SellerPicturesBean();
				sellerPicturesBean.setAccountProfileBean(new AccountProfileBean(SessionHelper.getAccountProfileId(request)));
				sellerPicturesBean.setPictureLocation(fileName);
				sellerPicturesBean.setContents(blob);
				sellerPicturesService.save(sellerPicturesBean);
			}
				isSellerOfferPicture=true;
			}
		}
		logger.info("=== ending  syncUploadSellerPicture method ===");
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.uploadsellerpicture.success", null, localeResolver.resolveLocale(request)));
		RedirectView red = new RedirectView("/seller/syncsellerinfopictures", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method will show seller into and picture view page with list of uploaded seller pictures and seller info.
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return it will return on tiles definition to provide appropriate output on browser screen.
	 * 
	 */
	@RequestMapping("/syncsellerinfopictures")
	public String syncSellerInfoPictures(HttpServletRequest request, Model model) {
		logger.info("SellerController Call syncSellerInfoPictures method");
		Integer accountId = SessionHelper.getAccountProfileId(request);
		if (null != accountId && accountId > 0) {
			AccountProfileBean accountProfileBean = accountProfileService.findById(accountId);
			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("sellerInfoBean", sellerInfoService.findById(accountId));
			model.addAttribute("sellerPicturesBeanList", sellerPicturesService.getByAccountProfileId(SessionHelper.getAccountProfileId(request)));
		}
		
		logger.info("=== ending  syncSellerInfoPictures method ===");
		return ViewConstant.NONAJAX_SELLER_INFO_PICTURE;
	}
	/**
	 * It will store seller into database.
	 * 
	 * @param sellerInfoBean
	 *            spring will get sellerInfoBean from model attribute of spring.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any request mapping / URL.
	 * @return it will redirect on welcome URL / request mapping.
	 * 
	 */
	@RequestMapping(value = "/syncsavesellerinfo", method = RequestMethod.POST)
	public ModelAndView saveSyncSellerInfo(@ModelAttribute("sellerInfoBean") SellerInfoBean sellerInfoBean, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("SellerController Call saveSyncSellerInfo method");
		String view = "/seller/syncsellerinfopictures";
		List<String> errorStack = sellerValidator.validateSellerInfo(sellerInfoBean, request);

		if (null == errorStack || errorStack.size() == 0) {
			sellerInfoService.save(sellerInfoBean);
			redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.savesellerinfo.success", null, localeResolver.resolveLocale(request)));
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
		}
		logger.info("=== ending  saveSyncSellerInfo method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
	
}