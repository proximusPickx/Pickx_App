package iOS_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public interface Setting_Screen {

	 By settings_button = MobileBy.AccessibilityId("home_view_controller_settings_button");
	 By parental_control = By.xpath("(//XCUIElementTypeCell[@name=\"settings_cell\"])[2]");
	 By logout_button = By.xpath("(//XCUIElementTypeCell[@name=\"settings_cell\"])[5]");
	 By logout_confirmation_message = MobileBy.AccessibilityId("Are you sure you want to log out?");
	 By logout_confirmation_button = MobileBy.AccessibilityId("OK");
	    
}
