package com.auction.commons.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static void uploadFile(int accountId, String fileName, String pathName, MultipartFile pictureFile) {
		if (null != pictureFile && !pictureFile.isEmpty()) {
			String path = System.getProperty("catalina.base");
			Properties properties = new Properties();
			String propertyFile = "properties/auction.properties";
			InputStream input = FileUtils.class.getClassLoader().getResourceAsStream(propertyFile);
			try {
				properties.load(input);
				String dirLocation = path + properties.getProperty(pathName).replace(":accountId", Integer.toString(accountId));
				File location = new File(dirLocation);
				if (!location.exists()) {
					location.mkdirs();
				}
				// dirLocation = dirLocation.concat("/");
				byte[] bytes = pictureFile.getBytes();
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dirLocation + new File(fileName)));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static byte[] getFileContent(int accountId, String pathName, String fileName) {
		String path = System.getProperty("catalina.base");
		Properties properties = new Properties();
		String propertyFile = "properties/auction.properties";
		InputStream input = FileUtils.class.getClassLoader().getResourceAsStream(propertyFile);
		byte[] imgBytes = new byte[1024];
		try {
			properties.load(input);
			String dirLocation = path + properties.getProperty(pathName).replace(":accountId", Integer.toString(accountId)).concat(fileName);
			File file = new File(dirLocation);
			if (file.exists()) {
				imgBytes = new byte[(int) file.length()];
				FileInputStream fi = new FileInputStream(file);
				fi.read(imgBytes);
				fi.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return imgBytes;
	}

	public static byte[] getFileContent(Blob contents) {
		try {
			return contents.getBytes(1, (int) contents.length());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	public static void deleteFile(int accountId, String pathName, String fileName) {
		String path = System.getProperty("catalina.base");
		Properties properties = new Properties();
		String propertyFile = "properties/auction.properties";
		InputStream input = FileUtils.class.getClassLoader().getResourceAsStream(propertyFile);
		try {
			properties.load(input);
			String dirLocation = path + properties.getProperty(pathName).replace(":accountId", Integer.toString(accountId)).concat(fileName);
			File file = new File(dirLocation);
			if (file.exists()) {
				file.delete();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}