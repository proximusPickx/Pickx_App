package iOS_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public interface Chromecase_screen {
	By chromecast_button = MobileBy.AccessibilityId("home_view_controller_chromecast_button");
	By device_recyclerview = By.xpath("//XCUIElementTypeApplication[@name=\"Proximus Pickx\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell");
	//By chromecast_device_icon = By.id("be.belgacom.mobile.tveverywhere:id/imageview_cast_icon");
	By chromecast_device_name = MobileBy.AccessibilityId("Hall TV");
	By chromecast_connected_to_text = MobileBy.AccessibilityId("Connected to");
	By chromecast_connected_device_name = MobileBy.AccessibilityId("Hall TV");
	By chromecast_disconnect_button = By.xpath("//XCUIElementTypeButton[@name=\"Disconnect\"]");
	By chromecast_cancel_button = By.xpath("//XCUIElementTypeButton[@name=\"Cancel\"]");
	By chromecast_icon_insideCasePage = MobileBy.AccessibilityId("cast_disconnect");
	By chromecast_available_device_text = MobileBy.AccessibilityId("Choose a device");
	
}
