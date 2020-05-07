package com.auction.controller.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Test {

	public String ATTEMPTED;
	public String DELIVERED_ATTEMPTED;
	public String SUBMITTED;
	public String DELIVERED_SUBMITED;
	public String FLUSH;

	public static void main(String[] args) {
		for (int i = 1; i <= 7; i++) {
			System.out.printf("%."+i+"s\n","*****");
		}
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, -1);
		dt = c.getTime();
		
		//Test em = new Test();
		//em.sendEamilToExternalUsers("pratik.s.aegis@gmail.com", "Test email", "Report1-Daily-12-Oct-2017.csv");
		System.out.println(dt);
	}

	public void sendEamilToExternalUsers(String emailTo, String reportName, String attchmentFileName) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));

		try {
			System.out.println("sendEmailToExternalUsers(): started ......");
			System.out.println("sendEmailToExternalUsers(): started ......");

			Properties properties = this.getTestingProperties();

			final String from = "testmail.aegis@gmail.com";
			String to[] = { "parthiv@aegissoftwares.com", "pratik.s.aegis@gmail.com" };
			// String to[] = { "pratik.s.aegis@gmail.com" };

			properties.setProperty("mail.smtp.from", from);

			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, "Aegis@123");
				}
			});

			Message message = new MimeMessage(session);
			Multipart multipart = new MimeMultipart();

			message.setFrom(new InternetAddress(from));

			InternetAddress[] toAddress = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				if (to[i] != null && !to[i].equals("null") && !to[i].equals(""))
					toAddress[i] = new InternetAddress(to[i]);
			}
			message.setRecipients(Message.RecipientType.TO, toAddress);

			// InternetAddress[] ccAddress = new InternetAddress[cc.length];
			/*
			 * for (int i = 0; i < cc.length; i++) { if (cc[i] != null && !cc[i].equals("null") && !cc[i].equals("")) ccAddress[i] = new InternetAddress(cc[i]); }
			 */
			// message.setRecipients(Message.RecipientType.CC, ccAddress);

			message.setSubject("Scheduled " + reportName);

			String messageBody = getExternalEmailBody();
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(("D://MMXReport/" + attchmentFileName));
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attchmentFileName);
			messageBodyPart.setDisposition(Part.ATTACHMENT);
			messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", 587, from, "Aegis@123");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			// Transport.send(message);

			System.out.println("sendEmailToExternalUsers(): Email sent successfully.....");
			System.out.println("sendEmailToExternalUsers(): Email sent successfully.....");
		} catch (Exception e) {
			System.out.println("Exception occured :" + e);
			e.printStackTrace();
		}
	}

	/*
	 * public void sendEamilToInternalUsers(String emailTo, String reportName, String attchmentFileName) { SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	 * format.setTimeZone(TimeZone.getTimeZone("GMT")); try { LOGGER.info("sendEamilToInternalUsers(): started ......");
	 * 
	 * String from = bundle.getString("external_mail_from"); String[] to = emailTo.split(",");
	 * 
	 * Properties properties = System.getProperties(); properties.setProperty("mail.smtp.host", emailHost); // properties.put("mail.smtp.starttls.enable", "true"); //
	 * properties.put("mail.smtp.port", "25");
	 * 
	 * Session session = Session.getDefaultInstance(properties); MimeMessage message = new MimeMessage(session); message.setFrom(new InternetAddress(from));
	 * 
	 * InternetAddress[] toAddress = new InternetAddress[to.length]; for (int i = 0; i < to.length; i++) { if (to[i] != null && !to[i].equals("null") && !to[i].equals(""))
	 * toAddress[i] = new InternetAddress(to[i]); }
	 * 
	 * message.setRecipients(Message.RecipientType.TO, toAddress); message.setSubject("Scheduled " + reportName);
	 * 
	 * String messageBody = getInternalEmailBody(); Multipart multipart = new MimeMultipart(); BodyPart messageBodyPart = new MimeBodyPart();
	 * 
	 * messageBodyPart.setContent(messageBody, "text/html"); multipart.addBodyPart(messageBodyPart); MimeBodyPart attachmentPart = new MimeBodyPart();
	 * System.out.println("File name : "+(bundle.getString("reportFileLocation") + File.separator + attchmentFileName + "csv")); DataSource source = new
	 * FileDataSource(bundle.getString("reportFileLocation") + File.separator + attchmentFileName + "csv"); attachmentPart.setDataHandler(new DataHandler(source));
	 * attachmentPart.setFileName(attchmentFileName); attachmentPart.setDisposition(Part.ATTACHMENT); attachmentPart.setHeader("Content-Transfer-Encoding", "base64");
	 * multipart.addBodyPart(messageBodyPart);
	 * 
	 * message.setContent(multipart);
	 * 
	 * Transport.send(message);
	 * 
	 * LOGGER.info("sendEmailToInternalUsers(): Email sent successfully....."); } catch (Exception e) { LOGGER.error("Exception occured :" + e); e.printStackTrace(); } }
	 */

	public String getExternalEmailBody() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dear All, \n\n");
		sb.append("Kindly take note of the attached report. \n\n");
		sb.append("In case of any queries, kindly write to \n");
		sb.append("<a herf='smsrate@tatacommunications.com'>smsrate@tatacommunications.com</a> \n\n");

		sb.append("Thank you.\n");
		sb.append("Regards, \n");
		sb.append("Tata Communications Mobile Messaging Exchange Team");

		return sb.toString();

	}

	public Properties getTestingProperties() {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.port", "587");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.debug", "true");
		prop.setProperty("mail.smtp.host", "smtp.gmail.com");
		prop.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

		return prop;
	}

	public String getInternalEmailBody() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dear user, \n\n");
		sb.append("Kindly take note of the attached report. \n\n");
		sb.append("In case of any queries, kindly write to \n");
		sb.append("<a herf='smsrate@tatacommunications.com'>smsrate@tatacommunications.com</a> \n\n");

		sb.append("Thank you.\n");
		sb.append("Regards, \n");
		sb.append("Tata Communications Mobile Messaging Exchange Team");

		return sb.toString();
	}
}
