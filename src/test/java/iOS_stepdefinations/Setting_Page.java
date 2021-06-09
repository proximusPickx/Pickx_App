package iOS_stepdefinations;

import java.io.IOException;

import org.testng.Assert;

import base.iOS_input_properties;
import config.Hooks;
import iOS_screens.Home_Screen;
import iOS_screens.Setting_Screen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import utils.CommonUtils;

public class Setting_Page implements Setting_Screen, Home_Screen {
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;
	public iOS_input_properties inputProperties;

	public Setting_Page() throws IOException {
		driver = Hooks.getDriver();
		commonUtils = new CommonUtils(driver);
		inputProperties = new iOS_input_properties();
	}

	@Given("^The User is on settings page$")
	public void the_user_is_on_settings_page() throws Throwable {
		commonUtils.clickonElement(settings_button);
		Assert.assertTrue(commonUtils.displayed(parental_control));
	}
}
