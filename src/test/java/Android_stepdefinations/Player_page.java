package Android_stepdefinations;

import java.io.IOException;

import base.Android_input_properties;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class Player_page {
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;
	public Android_input_properties inputProperties;

	public Player_page() throws IOException {
		driver = Hooks.getDriver();
		commonUtils = new CommonUtils(driver);
		inputProperties = new Android_input_properties();
	}

    @Given("^The user play live airing$")
    public void the_user_play_live_airing() {
        
    }

    @When("^The user see loading page in portrait and horizontal view$")
    public void the_user_see_loading_page_in_portrait_and_horizontal_view() {
        
    }

    @Then("^The user see live airing portrait and horizontal$")
    public void the_user_see_live_airing_portrait_and_horizontal() {
       
    }
}
