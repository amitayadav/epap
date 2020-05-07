package com.auction.commons.reportUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import com.auction.commons.util.AuctionLogger;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class ReportManager {
	private static AuctionLogger logger = new AuctionLogger(ReportManager.class);
	public static byte[] generateReport(String reportType, String reportRealPath, Map<String, Object> parameters, List beanData,String language,String selectLanguage) throws Exception {
		try {
			logger.info("ReportManager Call generateReport Method ");
			JRDataSource ds = null;
			if (beanData != null)
				ds = new JRBeanCollectionDataSource(beanData);
			else
				ds = new JREmptyDataSource();

			File reportFilePath = new File(reportRealPath);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
			removeBlankPage(jasperPrint.getPages());
			if(selectLanguage.equals(language)) {
				mirrorLayout( jasperPrint);
			}
			
			
			logger.info("ReportManager Call generateReport Method end");
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("ReportManager Call generateReport Method error " + e);
			throw e;
			
		}
	}

	/**
	 * this method used for Generate report and sub-report also
	 * 
	 */

	public static byte[] generateReportWithSubReport(String reportType, String reportName, String subRepoertName, Map<String, Object> parameters, ArrayList beanData)
			throws Exception {
		try {
			ClassLoader classLoader = ReportManager.class.getClassLoader();
			String reportRealPath = classLoader.getResource("/report/" + reportName + ".jasper").getFile();
			// String subReportPath = classLoader.getResource("/report/");
			// Sub Report File Location Outside Project..*/
			if (subRepoertName != "") {
				parameters.put("SUBREPORT_DIR", classLoader.getResource("/report/" + subRepoertName + ".jasper").getFile());
			}

			DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
			JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.font.name", "Verdana");
			JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
			JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "Verdana");

			JRDataSource ds = null;
			if (beanData != null)
				ds = new JRBeanCollectionDataSource(beanData);
			else
				ds = new JREmptyDataSource();

			File reportFilePath = new File(reportRealPath);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
			removeBlankPage(jasperPrint.getPages());
			mirrorLayout( jasperPrint);
			/*
			 * InputStream in = ReportManager.class.getResourceAsStream("/report/" +reportName+".jasper"); if (reportType.equalsIgnoreCase("PDF")) { byteStream =
			 * JasperRunManager.runReportToPdf(in,parameters,ds); }
			 */

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	// to remove blank pages in report
	private static void removeBlankPage(List<JRPrintPage> pages) {
		for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext();) {
			JRPrintPage page = i.next();
			if (page.getElements().size() == 0)
				i.remove();
		}
	}

	public static String generateReport(String reportType, String reportName, String destPath, Map<String, Object> parameters, ArrayList beanData) {
		JasperPrint jasperPrint = null;
		String destFileName = null;
		try {
			// File reportFilePath = new File("reports/" + reportName +
			// ".jasper");
			ClassLoader classLoader = ReportManager.class.getClassLoader();
			File reportFilePath = new File(classLoader.getResource(reportName + ".jasper").getFile());

			// Sub Report File Location..
			parameters.put("SUBREPORT_DIR", "src/main/resources/");

			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(beanData);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFilePath);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);

			destFileName = destPath + "/" + reportName + "_" + new Date() + "." + destFileName + reportType.toLowerCase();

			if ("HTML".equalsIgnoreCase(reportType)) {
				JasperExportManager.exportReportToHtmlFile(jasperPrint, destFileName);
			} else if ("XLS".equalsIgnoreCase(reportType)) {
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(destFileName));
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				configuration.setOnePagePerSheet(true);
				configuration.setWhitePageBackground(false);
				configuration.setRemoveEmptySpaceBetweenRows(true);
				configuration.setRemoveEmptySpaceBetweenColumns(true);
				exporterXLS.setConfiguration(configuration);
				exporterXLS.exportReport();
			} else if ("PDF".equalsIgnoreCase(reportType)) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				JasperViewer.viewReport(jasperPrint);
			}
			JasperViewer.viewReport(jasperPrint);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return destFileName;
	}
	
	 /**
     * mirror each page layout used direction to RTL
     * @param print
     */
	  public static void mirrorLayout(JasperPrint print) {
	        int pageWidth = print.getPageWidth();
	        for (Object element : print.getPages()) {
	            JRPrintPage page = (JRPrintPage) element;
	            mirrorLayout(page.getElements(), pageWidth);
	        }
	  }
	  
	  /**
	     * mirror a list of elements direction to RTL
	     * @param print
	     */
	        protected static void mirrorLayout(List<?> elements, int totalWidth) {
	            for (Iterator<?> it = elements.iterator(); it.hasNext();) {
	                JRPrintElement element = (JRPrintElement) it.next();
	                int mirrorX = totalWidth - element.getX() - element.getWidth();
	                element.setX(mirrorX);

	                if (element instanceof JRPrintFrame) {
	                    JRPrintFrame frame = (JRPrintFrame) element;
	                    mirrorLayout(frame.getElements(), frame.getWidth());
	                }
	            }
	        }
}
