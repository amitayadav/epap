package com.auction.service.security;

import java.util.Properties;
import java.util.concurrent.Future;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.auction.commons.util.EmailData;

@Service
public class EmailService {

	@Autowired
	private Session mailSession;

	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_FROM = "mail.smtp.from";
	private static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private static final String MAIL_CONTENT_TYPE = "text/html; charset=utf-8";
	
	@Async("EmailTaskExecutor")
	public Future<Boolean> sendEmail(EmailData emailData) {
		try {
			/*System.out.println("Sending email...");*/
			
			Properties props = mailSession.getProperties();
			
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(props.get(MAIL_SMTP_FROM).toString()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress((emailData.getToAddress())));
			message.setSubject(emailData.getSubject());
			message.setSubject(emailData.getSubject(),"utf-8");
			message.setHeader("contentType", "text/html; charset=utf-8");
			message.setContent(emailData.getHtmlContent(), MAIL_CONTENT_TYPE);
			
			Transport transport = mailSession.getTransport(props.get(MAIL_TRANSPORT_PROTOCOL).toString());
			transport.connect(
					props.get(MAIL_SMTP_HOST).toString(),
					Integer.parseInt("" + props.get(MAIL_SMTP_PORT)),
					props.get(MAIL_SMTP_FROM).toString(),
					props.get(MAIL_SMTP_PASSWORD).toString());
			
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			/*System.out.println("Email Sent Successfully!");*/
			
			return new AsyncResult<Boolean>(true);
		} catch (MailException ex) {
			ex.printStackTrace();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return new AsyncResult<Boolean>(false);
	}
	
}