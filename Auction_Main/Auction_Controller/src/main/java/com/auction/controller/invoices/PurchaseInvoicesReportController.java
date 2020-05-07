package com.auction.controller.invoices;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.auction.commons.reportUtils.ReportManager;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.views.PurchaseInvoicesView;
import com.auction.service.BuyerAndBuyerAgentInvoiceService;

@Controller
@RequestMapping("/report")
public class PurchaseInvoicesReportController {
	@Autowired
	private BuyerAndBuyerAgentInvoiceService  buyerAndBuyerAgentInvoiceService;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private MessageSource messageSource;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
/** To print BuyerPurchaswinvoice report */

	@RequestMapping(value={"/BuyerPurchaswInvoiceReport/{transactionId}"}, produces = {MediaType.APPLICATION_PDF_VALUE})
	@ResponseBody
	public byte[] BuyerPurchaswInvoiceReport(Model model,@PathVariable("transactionId") Integer transactionId,HttpServletRequest request,HttpServletResponse response){
		logger.info("PurchaseInvoicesReportController Call BuyerPurchaswInvoiceReport method");
		ServletContext context = request.getServletContext();
		Locale locale = localeResolver.resolveLocale(request);
		Integer others = 0;
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ("filename=buyerPurchaseInvoice_"+transactionId+".pdf"));
		String reportLogoImage = context.getRealPath("/WEB-INF/resources/images/reportImg/report_logo.png");
		String language= messageSource.getMessage("report.language", null, locale);
		String  selectLanguage =messageSource.getMessage("report.selectLanguage", null, locale); 
		List<PurchaseInvoicesView> purchaseInvoicesViewList = buyerAndBuyerAgentInvoiceService.getBuyerPurchaseInvoicesReport(transactionId);
		Map<String,Object> parameterMaps = new HashMap<String,Object>();
		parameterMaps.put("REPORT_LOGO_IMAGE",reportLogoImage);
		parameterMaps.put("PORTALAR",messageSource.getMessage("report.lbl.portalar.ar", null, locale));
		parameterMaps.put("PORTAL",messageSource.getMessage("report.lbl.portal", null, locale));
		parameterMaps.put("INVOICE",messageSource.getMessage("report.lbl.invoice", null, locale));
		parameterMaps.put("SELLER_NAME",messageSource.getMessage("report.lbl.seller", null, locale));
		parameterMaps.put("BUYER_NAME",messageSource.getMessage("report.lbl.buyer", null, locale));
		parameterMaps.put("SHIPPER_NAME",messageSource.getMessage("report.lbl.buyershipper", null, locale));
		parameterMaps.put("DATE",messageSource.getMessage("report.lbl.date", null, locale));
		parameterMaps.put("ITEM",messageSource.getMessage("report.lbl.item", null, locale));
		parameterMaps.put("REF_NO",messageSource.getMessage("report.lbl.refnumber", null, locale));
		parameterMaps.put("UNIT_PRICE",messageSource.getMessage("report.lbl.price", null, locale));
		parameterMaps.put("QUANTITY",messageSource.getMessage("report.lbl.quantity", null, locale));
		parameterMaps.put("AMOUNT",messageSource.getMessage("report.lbl.amount", null, locale));
		parameterMaps.put("SUB_TOTAL",messageSource.getMessage("report.lbl.subtotal", null, locale));
		parameterMaps.put("COMMISSION",messageSource.getMessage("report.lbl.commission", null, locale));
		parameterMaps.put("SHIPPING",messageSource.getMessage("report.lbl.shipping", null, locale));
		parameterMaps.put("VAT",messageSource.getMessage("report.lbl.vat", null, locale));
		parameterMaps.put("OTHER",messageSource.getMessage("report.lbl.other", null, locale));
		parameterMaps.put("TOTAL",messageSource.getMessage("report.lbl.total", null, locale));
		parameterMaps.put("ACCOUNTING",messageSource.getMessage("report.lbl.accounting", null, locale));
		parameterMaps.put("MANAGEMENT",messageSource.getMessage("report.lbl.management", null, locale));
		model.addAttribute("others",others);
		String reportRealPath = context.getRealPath("/WEB-INF/report/buyerPurchaseInvoicesjrxml.jasper");
		logger.info("PurchaseInvoicesReportController Call BuyerPurchaswInvoiceReportn Realpath..." +reportRealPath);
		byte[] byteReport = null;
		try {
			byteReport = ReportManager.generateReport("PDF", reportRealPath, parameterMaps, purchaseInvoicesViewList,language,selectLanguage);
			
		} catch (Exception e) {
			e.printStackTrace();
		
			}
		logger.info("=== Ending BuyerPurchaswInvoiceReport method ===");
		return byteReport;

	
	}
	
	
	
	
	
}

