package config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import base.base;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.messages.internal.com.google.common.io.Files;

public class Hooks extends base {

	Process process;
	Scenario scenario;

	static AppiumDriver<MobileElement> driver;
	AndroidDriver<MobileElement> androidDriver;
	private static AppiumDriverLocalService service;
	String os = getOSname();

	public Hooks() throws IOException {
		super();
	}

	public static AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	@Before
	public void chooseRunningPlatform(Scenario scenario) throws IOException, InterruptedException {
		String runningPlatform = getRunningPlatformName();
		// String os = getOSname();
		System.out.println("runningPlatform is " + runningPlatform);
		System.out.println("running OS is " + os);
		if (runningPlatform.equalsIgnoreCase("browserstack")) {
			this.scenario = scenario;
			runInBrowserstack(scenario);
		} else if (runningPlatform.equalsIgnoreCase("device")) {
			if (os.equalsIgnoreCase("Android")) {
				android_setupAppium();
			} else if (os.equalsIgnoreCase("iOS")) {
				iOS_setupAppium();
			}
		}
		System.out.println("caps are set.............");
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	}

	public void setBsSessionName(Scenario scenario) {
		String sessionName = scenario.getName();
		System.out.println("Session name :" + sessionName);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\""
				+ sessionName + "\" }}");
	}

	public void android_setupAppium() throws InterruptedException {

		startServer_cmd();

		File appDir = new File("src/App/Android");
		File app = new File(appDir, getAndroid_appName());
		URL url;
		try {
			url = new URL("http://127.0.0.1:4723/wd/hub");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getandroid_deviceName());
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getandroid_automationName());
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getappPackage());
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getappActivity());
			// capabilities.setCapability("chromedriverExecutable",System.getProperty("user.dir")
			// +"/src/test/resources/chromedriver.exe");
			capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("noReset", true);
			androidDriver = new AndroidDriver<>(url, capabilities);
			driver = androidDriver;
		} catch (Exception exp) {
			System.out.println("!Cause is :" + exp.getCause());
			System.out.println("Message is :.." + exp.getMessage());
			exp.printStackTrace();
		}
	}

	public void iOS_setupAppium() throws InterruptedException {

		// startServer_cmd();

		File appDir = new File("src/App/iOS/");
		File app = new File(appDir, getiOS_appName());
		URL url;
		try {
			url = new URL("http://127.0.0.1:4723/wd/hub");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getiOS_deviceName());
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getiOS_automationName());
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			capabilities.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, getxcodeOrgId());
			capabilities.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, getxcodeSigningId());
			capabilities.setCapability(MobileCapabilityType.UDID, getUdid());
			capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			capabilities.setCapability("autoAcceptAlerts", true);
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("noReset", true);
			driver = new IOSDriver<>(url, capabilities);
		} catch (Exception exp) {
			System.out.println("!Cause is :" + exp.getCause());
			System.out.println("Message is :.." + exp.getMessage());
			exp.printStackTrace();
		}
	}

	public void runInBrowserstack(Scenario scenario) throws MalformedURLException {
		String buildName = "build";
		String geoLocation = "browserstack.geoLocation";
		URL url;
		try {
			String decodedCredentials = getBrowserstackUsername() + ":" + getBrowserstackAccessKey();
			url = new URL("http://" + decodedCredentials + "@hub-cloud.browserstack.com/wd/hub");
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setCapability(MobileCapabilityType.APP, getAppId());
			capability.setCapability(MobileCapabilityType.LANGUAGE, getDeviceLanguage());
			capability.setCapability(MobileCapabilityType.DEVICE_NAME, getbrowserStackdeviceName());
			capability.setCapability(geoLocation, getGeoLocation());
			capability.setCapability(buildName, getBuildName());
			if (os.equalsIgnoreCase("Android")) {
				driver = new AndroidDriver<>(url, capability);
			} else if (os.equalsIgnoreCase("iOS")) {
				driver = new IOSDriver<>(url, capability);
			}
			setBsSessionName(scenario);
		} catch (Exception e) {
			System.out.println("!Cause is :" + e.getCause());
			System.out.println("Message is :.." + e.getMessage());
			e.printStackTrace();
		}
	}

//	public void iOS_runInBrowserstack(Scenario scenario) throws MalformedURLException {
//		String buildName = "build";
//		String geoLocation = "browserstack.geoLocation";
//		URL url;
//		try {
//			String decodedCredentials = getBrowserstackUsername() + ":" + getBrowserstackAccessKey();
//			url = new URL("http://" + decodedCredentials + "@hub-cloud.browserstack.com/wd/hub");
//			DesiredCapabilities capability = new DesiredCapabilities();
//			capability.setCapability(MobileCapabilityType.APP, getAppId());
//			capability.setCapability(MobileCapabilityType.LANGUAGE, getDeviceLanguage());
//			capability.setCapability(MobileCapabilityType.DEVICE_NAME, getbrowserStackdeviceName());
//			capability.setCapability(geoLocation, getGeoLocation());
//			capability.setCapability(buildName, getBuildName());
//			driver = new IOSDriver<>(url, capability);
//			setBsSessionName(scenario);
//		} catch (Exception e) {
//			System.out.println("!Cause is :" + e.getCause());
//			System.out.println("Message is :.." + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	public void startServer_cmd() throws InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		try {
			process = runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override \"");
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void startserver() {
		service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File(System.getProperty("user.dir") + "/src/test/resources/node.exe"))
				// .withLogFile(new File(System.getProperty("user.dir") +
				// "\\src\\test\\resources\\logs\\log.txt"))
				.withArgument(GeneralServerFlag.LOCAL_TIMEZONE));
		service.start();

		if (service == null || !service.isRunning()) {
			throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
		}
	}

	public void updateResultToBs(Scenario scenario) {
		String reason = "error";
		if (scenario.getStatus().toString().equalsIgnoreCase("PASSED")) {
			reason = "Passed";
		} else if (scenario.getStatus().toString().equalsIgnoreCase("FAILED")) {
			reason = "Failed";
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ scenario.getStatus() + "\", \"reason\": \"Test " + reason + "\"}}");
	}

	@After(order = 1)
	public void takesScreenshotOnFailure(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			File destinationPath = new File(System.getProperty("user.dir") + "/target/screenshot/" + screenshotName
					+ System.currentTimeMillis() + ".png");
			Files.copy(sourcePath, destinationPath);
			scenario.attach(screenshot, "image/png", screenshotName);
		}
	}

	@After(order = 0)
	public void stopServer() {
		String runningPlatform = getRunningPlatformName();
		if (runningPlatform.equalsIgnoreCase("browserstack")) {
			updateResultToBs(scenario);
		}
		if (driver != null)
			driver.quit();
	}

}