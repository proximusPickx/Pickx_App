package Android_stepdefinations;

import java.io.IOException;

import org.testng.Assert;

import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class My_Videos_Page implements Android_screens.Home_Screen, Android_screens.My_Videos_Screen {

	public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;

    public My_Videos_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
    }

    @Given("^User is on my videos page$")
    public void user_is_on_my_videos_page() {
	commonUtils.clickonElement(my_videos_button);
	commonUtils.waitTillVisibility(my_videos_tab_layout, 20);
	Assert.assertTrue(commonUtils.displayed(my_videos_tab_layout));
    }

    @When("^User selects Recordings tab in my videos$")
    public void user_selects_Recordings_tab_in_my_videos() {
	commonUtils.clickonElement(my_videos_recordings_option);
	commonUtils.waitTillVisibility(my_videos_recordings_playable_tab, 20);
	Assert.assertTrue(commonUtils.displayed(my_videos_recordings_playable_tab));
    }

    @And("^User selects Playable tab in recordings of my videos$")
    public void user_selects_playable_tab_in_recordings_of_my_videos() {
	commonUtils.clickonElement(my_videos_recordings_playable_tab);
    }

    @And("^Validate recordings tab in my videos$")
    public void validate_recordings_tab_in_my_videos() {
	Assert.assertTrue(commonUtils.selected(my_videos_recordings_option));
	Assert.assertTrue(commonUtils.displayed(my_videos_recordings_playable_tab));
	Assert.assertTrue(commonUtils.displayed(my_videos_recordings_recorded_tab));
	Assert.assertTrue(commonUtils.displayed(my_videos_recordings_planned_tab));
    }

    @And("^Validate playable tab in recordings of my videos$")
    public void validate_playable_tab_in_recordings_of_my_videos() {
	try {
	    commonUtils.findElements(my_videos_recordings_programs);
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_poster));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_channel_icon));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_title));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_other_option));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_description));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_recorded_icon));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Then("^User selects Recorded tab in recordings of my videos$")
    public void user_selects_recorded_tab_in_recordings_of_my_videos() {
	commonUtils.clickonElement(my_videos_recordings_recorded_tab);
    }

    @And("^Validate recorded tab in recordings of my videos$")
    public void validate_recorded_tab_in_recordings_of_my_videos() {
	try {
	    commonUtils.findElements(my_videos_recordings_programs);
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_poster));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_channel_icon));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_title));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_other_option));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_description));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_recorded_icon));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Then("^User selects Planned tab in recordings of my videos$")
    public void user_selects_planned_tab_in_recordings_of_my_videos() {
	commonUtils.clickonElement(my_videos_recordings_planned_tab);
    }

    @And("^Validate planned tab in recordings of my videos$")
    public void validate_planned_tab_in_recordings_of_my_videos() {
	try {
	    commonUtils.findElements(my_videos_recordings_programs);
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_poster));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_channel_icon));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_title));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_other_option));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_description));
	    Assert.assertTrue(commonUtils.displayed(my_videos_recordings_program_recorded_icon));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Then("^User selects Continue Watching tab in my videos$")
    public void user_selects_continue_watching_tab_in_my_videos() {
	commonUtils.clickonElement(my_videos_continue_watching_option);
    }

}
