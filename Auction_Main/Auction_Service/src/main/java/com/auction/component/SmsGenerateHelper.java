package com.auction.component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;


@Component
public class SmsGenerateHelper {

	private static final String username="fahadepap";
	private static final  Integer password=123456;
	private static final String sender="EPAPadmin";
	private static final String message="Welcome To Epap ";
	private static final String message1=" is your one time password(OTP).Please enter the OTP to proceed";
	private static final String USER_AGENT = "Mozilla/5.0";
	private static AuctionLogger logger = new AuctionLogger("SmsGenerateHelper");
	public static int generateOtp(int generateOtpNumberRange) {
		logger.info("SmsGenerateHelper call generateOtp method");
		int otpNumber = 0; 
		otpNumber = (int)((Math.random() * 9000)+generateOtpNumberRange); 
		return otpNumber;
	}
	
	public static void sendOtpSms(int otp ,String phoneNumber) throws Exception {
		logger.info("SmsGenerateHelper call sendOtpSms method ");
		BufferedReader br= null;
		String url="https://www.4jawaly.net/api/sendsms.php?username="+username+"&password="+password+"&message="+message+otp+" "+message1+"&numbers="+phoneNumber+"&sender="+sender;
		url = url.replace(" ", "%20");
		//String url="https://www.google.com";
		URL obj = new URL(url);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(false);
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			obj.getAuthority();
		if (200 <= con.getResponseCode() && con.getResponseCode() <= 299) {
			logger.info("SmsGenerateHelper call sendOtpSms method and check getResponseCode "+con.getResponseMessage());
		     br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {
			logger.info("SmsGenerateHelper call sendOtpSms method and check getResponseCode "+con.getResponseMessage());
		    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		StringBuilder	sb = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
		  sb.append(output);
		}
	
	}
	

}
