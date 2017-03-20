package se.wiklund.haderengine.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import se.wiklund.haderengine.Engine;

public class SaveFile {

	private static String COMPANY_NAME;
	private static String APP_NAME;
	
	private HashMap<String, String> configs = new HashMap<>();
	private Properties properties = new Properties();
	private File file;

	public SaveFile(String fileName) {
		if (APP_NAME == null || APP_NAME.isEmpty() || APP_NAME.trim().equalsIgnoreCase("")) {
			System.err.println(Engine.NAME_PREFIX
					+ "You need to set the application name to use save files by calling SaveFile.setApplicationName(\"Application Name\")."
					+ "SaveFile.setCompanyName(\"Company Name\") is optional.");
			return;
		}
		
		boolean companyNameExists = !(COMPANY_NAME == null || COMPANY_NAME.isEmpty() || COMPANY_NAME.trim().equalsIgnoreCase(""));
		String os = System.getProperty("os.name").toUpperCase();
		String dir;
		if (os.contains("WIN")) {
			String ext = "";
			if (companyNameExists)
				ext = "\\" + COMPANY_NAME;
			ext += "\\" + APP_NAME;
			dir = System.getenv("APPDATA") + ext;
		} else if (os.contains("MAC")) {
			String ext = "";
			if (companyNameExists)
				ext = "/" + COMPANY_NAME;
			ext += "/" + APP_NAME;
			dir = System.getProperty("user.home") + "/Library" + ext;
		} else {
			String ext = "";
			if (companyNameExists)
				ext = "/" + COMPANY_NAME;
			ext += "/" + APP_NAME;
			dir = System.getProperty("user.home") + ext;
		}
		
		File dirFile = new File(dir);
		
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		file = new File(dir, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
				saveToFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		load();
	}

	public void load() {
		try {
			InputStream input = new FileInputStream(file);
			properties.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Enumeration<?> names = properties.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			configs.put(name, properties.getProperty(name));
		}
	}
	
	public void saveToFile() {
		try {
			InputStream input = new FileInputStream(file);
			properties.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		properties.putAll(configs);
		
		try {
			OutputStream output = new FileOutputStream(file);
			properties.store(output, null);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setProperty(String key, String value) {
		configs.put(key, value);
	}
	
	public String getProperty(String key, String defaultValue) {
		if (!configs.containsKey(key)) {
			configs.put(key, defaultValue);
		}
		return configs.get(key);
	}

	public static void setCompanyName(String companyName) {
		COMPANY_NAME = companyName;
	}

	public static void setApplicationName(String appName) {
		APP_NAME = appName;
	}
}
