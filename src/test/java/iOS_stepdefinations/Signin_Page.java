package iOS_stepdefinations;

//import pageObjects.LandingPage;

import java.io.IOException;

import org.testng.Assert;

import config.Hooks;
import iOS_screens.Environment_Screen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

//@RunWith(Cucumber.class)
public class Signin_Page implements Environment_Screen {
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;

	public Signin_Page() throws IOException {
		driver = Hooks.getDriver();
		commonUtils = new CommonUtils(driver);

	}

	@Given("^User opens the PickxTV application$")
	public void user_opens_the_pickxtv_application() throws Throwable {
		String runningPlatform = commonUtils.getRunningPlatformName();
		try {
			if (runningPlatform.equalsIgnoreCase("device")) {
				if (commonUtils.displayed(btnOk)) {
					commonUtils.clickonElement(btnOk);
					commonUtils.clickonElement(btnAllow);
					commonUtils.clickonElement(btnAllow_tracking);
				}

			} else if (runningPlatform.equalsIgnoreCase("browserstack")) {
				if (!commonUtils.displayed(btnOk)) {
					commonUtils.clickonElement(btnAllow);
				} else if (!commonUtils.displayed(btnAllow)) {
					commonUtils.clickonElement(btnOk);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(commonUtils.displayed(choose_env_bar));
	}

	@When("^The User selects the environment$")
	public void the_user_selects_the_environment() throws Throwable {
		commonUtils.clickonElement(prod_radio_button);
		Assert.assertTrue(commonUtils.enabled(continue_button));
		commonUtils.clickonElement(continue_button);
	}

	@Then("^The user accepts the terms and condition$")
	public void the_user_accepts_the_terms_and_condition() {
		if (!commonUtils.displayed(terms_and_condition_accept_button)) {
		} else if (commonUtils.displayed(terms_and_condition_accept_button)) {
			commonUtils.clickonElement(terms_and_condition_accept_button);
		}
	}

	@And("^User select the login option$")
	public void user_select_the_login_option() throws Throwable {
		the_user_accepts_the_terms_and_condition();
		Assert.assertTrue(commonUtils.displayed(login_button));
		commonUtils.clickonElement(login_button);
	}
}
