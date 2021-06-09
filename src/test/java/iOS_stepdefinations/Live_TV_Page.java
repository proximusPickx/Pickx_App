package iOS_stepdefinations;

import java.io.IOException;

import org.testng.Assert;

import base.iOS_input_properties;
import config.Hooks;
import iOS_screens.Home_Screen;
import iOS_screens.LiveTV_Screen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utils.CommonUtils;

public class Live_TV_Page implements Home_Screen, LiveTV_Screen {
	
    public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;
    public iOS_input_properties inputProperties;
    public Setting_Page settingPage;

    public Live_TV_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
	inputProperties = new iOS_input_properties();
	settingPage = new Setting_Page();
    }

    @Then("^User navigates to live TV page$")
    public void user_navigates_to_live_tv() {
	commonUtils.clickonElement(liveTV_button);
	commonUtils.waitTillVisibility(live_tv_channel_icon, 15);
	Assert.assertTrue(inputProperties.getLiveTvPageTitle().equalsIgnoreCase(commonUtils.getText(live_page_title)));
    }
    
    @Given("^Validate liveTV loading skeleton$")
    public void validate_livetv_loading_skeleton() {
    	Assert.assertTrue(commonUtils.displayed(category_button_container));
    	Assert.assertTrue(commonUtils.displayed(liveTV_skeleton));
    	Assert.assertTrue(commonUtils.displayed(livetv_navigationbar));
    	
    }

}
