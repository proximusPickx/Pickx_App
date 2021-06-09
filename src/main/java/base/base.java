package base;


import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class base {
	//static AndroidDriver<AndroidElement> driver;
	protected Properties prop;
	protected FileInputStream fis = new FileInputStream(
			System.getProperty("user.dir") + "/src/test/resources/data.properties");

	public base() throws IOException {
		prop = new Properties();
		prop.load(fis);
	}

	public String getUsername() {
		return prop.getProperty("username");
	}

	public String getPassword() {
		return prop.getProperty("password");
	}
	
	public String getNativeView() {
		return prop.getProperty("native");
	}

	public String getWebView() {
		return prop.getProperty("web");
	}
	
	public String getAndroid_appName() {
		return (String) prop.get("android_appname");
	}
	
	public String getiOS_appName() {
		return (String) prop.get("iOS_appname");
	}
	
	public String getandroid_deviceName() {
		return prop.getProperty("android_deviceName");
	}
	
	public String getiOS_deviceName() {
		return prop.getProperty("iOS_deviceName");
	}
	
	public String getappPackage() {
		return prop.getProperty("appPackage");
	}

	public String getappActivity() {
		return prop.getProperty("appActivity");
	}

	public String getandroid_automationName() {
		return prop.getProperty("android_automationName");
	}
	
	public String getiOS_automationName() {
		return prop.getProperty("iOS_automationName");
	}
	
	public String getRunningPlatformName() {
		return prop.getProperty("runningPlatform");
	}
	
	public String getAppId() {
		return prop.getProperty("appId");
	}
	
	public String getDeviceLanguage() {
		return prop.getProperty("deviceLanguage");
	}
	
	public String getBuildName() {
		return prop.getProperty("buildName");
	}
	
	public String getBrowserstackUsername() {
		return prop.getProperty("browserstackUsername");
	}
	
	public String getBrowserstackAccessKey() {
		return prop.getProperty("browserstackAccessKey");
	}
	
	public String getbrowserStackdeviceName() {
		return prop.getProperty("browserstackdevicename");
	}
	
	public String getGeoLocation() {
		return prop.getProperty("geoLocation");
	}
	
	public String getLineNumber() {
		return prop.getProperty("lineNumber");
	}
	
	public String getOSname() {
		return prop.getProperty("os");
	}
	
	public String getxcodeOrgId() {
		return prop.getProperty("xcodeOrgId");
		
	}
	
	public String getxcodeSigningId() {
		return prop.getProperty("xcodeSigningId");
	}
	
	public String getUdid() {
		return prop.getProperty("udid");
	}
}
