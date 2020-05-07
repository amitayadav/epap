package com.auction.controller.test;

import java.io.File;
import java.io.FileOutputStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperUnicodeReportFill {
		   public static void main(String[] args) {
		      String sourceFileName ="D://report/" + 
		         "demo.jasper";
				String defaultPDFFont = "Arial";

	 
		      try {
		    	  JRDataSource ds =   new JREmptyDataSource();
		  		File reportFilePath = new File(sourceFileName);
		  		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFilePath);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, ds);
				File outputFile = new File("D://report/demo.pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(outputFile));
		      } catch (Exception e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      }

		   }
		}

