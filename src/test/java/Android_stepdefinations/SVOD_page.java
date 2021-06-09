package Android_stepdefinations;

import java.io.IOException;

import org.testng.Assert;

import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class SVOD_page implements Android_screens.Swimlane_Contents_Screen {
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;

	  public Home_Page home_page;

	public SVOD_page() throws IOException {
		driver = Hooks.getDriver();
		commonUtils = new CommonUtils(driver);
		home_page = new Home_Page();
	}

    @When("^The user checks the assert available in the svod$")
    public void the_user_checks_the_assert_availablein_the_svod() {
    	Assert.assertTrue(commonUtils.displayed(new_this_week_page_title1));
    	Assert.assertTrue(commonUtils.displayed(movies_page_title1));
    	Assert.assertTrue(commonUtils.displayed(series_page_title1));
    }
    
    @When("^The user sees the locked video content$")
    public void the_user_sees_the_locked_video_content() {
    	home_page.user_selects_recommended_in_movies_and_series();
    	Assert.assertTrue(commonUtils.displayed(view_all));
    	if(commonUtils.displayed(svod_icon_locked)) {		
    	}
    }

    @Then("^The user validates the metadata in locked content$")
    public void The_user_validates_the_metadata() {
    	Assert.assertTrue(commonUtils.displayed(svod_icon_locked));
    	Assert.assertTrue(commonUtils.displayed(swimlane_content_page_program_title));
    	Assert.assertTrue(commonUtils.displayed(episode_subtitle));
    	Assert.assertTrue(commonUtils.displayed(svod_icon_age));
    }

}
