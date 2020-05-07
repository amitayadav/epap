package com.auction.controller.shipper;

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

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.util.AuctionLogger;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.ShipperValidator;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.SellerPicturesBean;
import com.auction.model.bean.ShipperInfoBean;
import com.auction.model.bean.ShipperPicturesBean;
import com.auction.service.AccountProfileService;
import com.auction.service.ShipperInfoService;
import com.auction.service.ShipperPicturesService;

@Controller
@RequestMapping("/shipper")
public class ShipperController {
	
	@Autowired
	private AccountProfileService accountProfileService;
	
	@Autowired
	private ShipperInfoService shipperInfoService;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ShipperValidator   shipperValidator;
	
	@Autowired
	private ShipperPicturesService    shipperPicturesService;
	
	@Value("${auction.selleroffer.picture.size}")
	private long shipperOfferPictureSize;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@RequestMapping("/shipperinfopictures")
	public String shipperInfoAndPictures(HttpServletRequest request, Model model) {
		logger.info("Shipper Controller  Call shipperInfoAndPictures method ");
		Integer accountId = SessionHelper.getAccountProfileId(request);
		if (null != accountId && accountId > 0) {
			AccountProfileBean accountProfileBean = accountProfileService.findById(accountId);
			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("shipperInfoBean", shipperInfoService.findById(accountId));
			model.addAttribute("shipperPicturesBeanList",shipperPicturesService.getByAccountProfileId(SessionHelper.getAccountProfileId(request)));
		}
		logger.info("=== ending  shipperInfoAndPictures method ===");
		return ViewConstant.SHIPPER_INFO_PICTURE;
	}
	
	
	@RequestMapping("/syncshipperinfopictures")
	public String syncshipperInfoAndPictures(HttpServletRequest request, Model model) {
		logger.info("Shipper Controller  Call syncshipperInfoAndPictures method ");
		Integer accountId = SessionHelper.getAccountProfileId(request);
		if (null != accountId && accountId > 0) {
			AccountProfileBean accountProfileBean = accountProfileService.findById(accountId);
			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("shipperInfoBean", shipperInfoService.findById(accountId));
			model.addAttribute("shipperPicturesBeanList",shipperPicturesService.getByAccountProfileId(SessionHelper.getAccountProfileId(request)));
		}
		logger.info("=== ending  syncshipperInfoAndPictures method ===");
		return ViewConstant.NONAJAX_SHIPPER_INFO_PICTURE;
	}
	
	/**
	 * It will store shipper into database.
	 * 
	 * @param shipperInfoBean
	 *            spring will get shipperInfoBean from model attribute of spring.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any request mapping / URL.
	 * @return it will redirect on welcome URL / request mapping.
	 * 
	 */
	@RequestMapping(value = "/syncsaveshipperinfo", method = RequestMethod.POST)
	public ModelAndView saveSyncSellerInfo(@ModelAttribute("shipperInfoBean") ShipperInfoBean shipperInfoBean, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("Shipper Controller  Call saveSyncSellerInfo method ");
		String view = "/shipper/syncshipperinfopictures";
	List<String> errorStack = shipperValidator.validateShipperInfo(shipperInfoBean, request);

		if (null == errorStack || errorStack.size() == 0) {
		shipperInfoService.save(shipperInfoBean);
			redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("shipper.saveshipperinfo.success", null, localeResolver.resolveLocale(request)));
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
		}
		logger.info("=== ending  saveSyncSellerInfo method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
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
	@RequestMapping("/shipperPictureAjax/{accountId}")
	public String sellerPictureAjax(@PathVariable("accountId") Integer accountId, Model model) {
		model.addAttribute("sellerPicturesBean", new SellerPicturesBean());
		return "/shipper/SHM_shipperPictureUploadAjaxForm";
	}
	
	/**
	 * It will store uploaded shipper pictures.
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
	@RequestMapping(value = "/syncuploadshipperpicture", method = RequestMethod.POST)
	public ModelAndView syncuploadshipperpicture(HttpServletRequest request, @RequestParam(value = "shipperPicture", required = false) MultipartFile[] shipperPicture,
			RedirectAttributes redirectAttributes) {
		logger.info("Shipper Controller  Call syncuploadshipperpicture method ");
		boolean isSellerOfferPicture =true;
		if (null != shipperPicture && shipperPicture.length > 0) {
			for (MultipartFile shipperPictur : shipperPicture) {
				Blob blob=null;
		        try{
		       byte[] contents = shipperPictur.getBytes();
		       		blob = BlobProxy.generateProxy(contents);
		       		if(shipperOfferPictureSize < shipperPictur.getSize()) {
		       			isSellerOfferPicture = false;
		       
		       		}
		       }
		       catch(Exception e)
		        {e.printStackTrace();
		       }
				String fileName = shipperPictur.getOriginalFilename();
				if (isSellerOfferPicture) {
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					// fileName = arr[0];
					// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
					fileName = ("shipper_picture_" + new Date().getTime() + "." + arr[1]);
				} else {
					// fileName = fileName.concat("_" + new Date().getTime());
					fileName = ("shipper_picture_" + new Date().getTime());
				}
				//FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_PICTURE_PATH, sellerPicture);
				ShipperPicturesBean shipperPicturesBean = new ShipperPicturesBean();
				shipperPicturesBean.setAccountProfileBean(new AccountProfileBean(SessionHelper.getAccountProfileId(request)));
				shipperPicturesBean.setPictureLocation(fileName);
				shipperPicturesBean.setContents(blob);
				shipperPicturesService.save(shipperPicturesBean);
				}
				isSellerOfferPicture=true;
			}
		}
		logger.info("=== ending  syncuploadshipperpicture method ===");
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.uploadsellerpicture.success", null, localeResolver.resolveLocale(request)));
		RedirectView red = new RedirectView("/shipper/syncshipperinfopictures", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	
	/**
	 * This method is used to remove the particular shipper uploaded picture by picure id
	 * 
	 * @param id
	 *            picture id by remove image
	 * @return true if successfully remove image
	 */
	@RequestMapping("/removepicture/{id}")
	public @ResponseBody String removepicture(@PathVariable("id") Integer id) {
		ShipperPicturesBean shipperPicturesBean = shipperPicturesService.findById(id);
		//FileUtils.deleteFile(sellerPicturesBean.getAccountProfileBean().getAccountId(), CommonConstants.SELLER_PICTURE_PATH, sellerPicturesBean.getPictureLocation());
		shipperPicturesService.delete(new ShipperPicturesBean(id));
		return "true";
	}

}