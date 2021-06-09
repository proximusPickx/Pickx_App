package iOS_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public interface Environment_Screen {	
		
		By btnOk = MobileBy.AccessibilityId("OK");
		By btnAllow = MobileBy.AccessibilityId("Allow");
		By btnAllow_tracking = MobileBy.AccessibilityId("Allow Tracking");
		By popUp_screen = MobileBy.xpath("(//XCUIElementTypeOther[@name=\"Vertical scroll bar, 1 page\"])[1]");
		By btnAllow_not_tracking = MobileBy.AccessibilityId("Ask App Not to Track");	
		By prod_radio_button = MobileBy.AccessibilityId("PRD");
		By continue_button = MobileBy.AccessibilityId("Continue");
		By choose_env_bar = MobileBy.AccessibilityId("EnvironmentSelectionViewController-selectEnvironment-label");
		By terms_and_condition_accept_button = MobileBy.AccessibilityId("terms-and-condition-view-controller-accept-button");
		By login_button = MobileBy.AccessibilityId("LoginWelcomeViewController-login-btn");
	}
