package Android_stepdefinations;


import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;

import Android_screens.Home_Screen;
import Android_screens.Setting_Screen;
import base.Android_input_properties;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class Setting_Page implements Setting_Screen, Home_Screen {
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;
	public Android_input_properties inputProperties;

	public Setting_Page() throws IOException {
		driver = Hooks.getDriver();
		commonUtils = new CommonUtils(driver);
		inputProperties = new Android_input_properties();
	}

	@Given("^The User is on settings page$")
	public void the_user_is_on_settings_page() throws Throwable {
		commonUtils.clickonElement(settings_button);
		Assert.assertTrue(commonUtils.displayed(parental_control));
	}

	@When("^User login to Parental control$")
	public void user_login_to_parental_control() throws Throwable {
		commonUtils.clickonElement(parental_control);
		enterPinCode(enter_pin_title_text,inputProperties.getenterPinCode_fortheFirsttime(),inputProperties.getpinValue());
		if(commonUtils.displayed(incorrect_pin_text)) {
			enterPinCode(enter_pin_title_text,inputProperties.getenterPinCode_fortheFirsttime(),inputProperties.getnewPin());
		}
		Assert.assertTrue(commonUtils.displayed(parental_control_toggle_id));
	}

	@And("^User turn on Parental control$")
	public void user_turn_on_parental_control() throws Throwable {	
		if (commonUtils.enabled(parental_control_toggle_point_on)) {

		} else {
			commonUtils.clickonElement(parental_control_toggle_id);
			Assert.assertTrue(commonUtils.displayed(parental_control_toggle_point_on));
		}
	}

	@Then("^User turn off Parental control$")
	public void user_turn_off_parental_control() throws Throwable {
		if (commonUtils.enabled(parental_control_toggle_point_on)) {
			commonUtils.clickonElement(parental_control_toggle_id);
			Assert.assertTrue(commonUtils.displayed(parental_control_toggle_point_off));
		} else {

		}
	}
	
    @When("^User open age restriction option$")
    public void user_open_age_restriction_option() throws Throwable {
		commonUtils.clickonElement(age_restriction_toggle_id);
    }

    @And("^User select the age restriction$")
    public void user_select_the_age_restriction() throws Throwable {
    	Thread.sleep(2000);
		Assert.assertTrue(commonUtils.displayed(age_restriction_18_icon));
		if (commonUtils.selected(age_restriction_12_icon)) {
			
		} else {
			commonUtils.clickonElement(age_restriction_12_icon);
		}
    }
    
    @Then("^The selected age is updated accordingly$")
    public void the_selected_age_is_updated_accordingly() throws Throwable {
    	Assert.assertTrue(commonUtils.displayed(selected_age_value));
    }
    
    @When("^User click on change pin code$")
    public void user_click_on_change_pin_code() throws Throwable {
    	commonUtils.clickonElement(parental_control_change_pin);
    	Assert.assertTrue(commonUtils.displayed(enter_pin_title_text));
    	enterPinCode(enter_pin_title_text,inputProperties.getenterPinCode_fortheFirsttime(),inputProperties.getpinValue());
    	if(commonUtils.displayed(incorrect_pin_text)) {
    		enterPinCode(enter_pin_title_text,inputProperties.getenterPinCode_fortheFirsttime(),inputProperties.getnewPin());
		}
    }
    
    @And("^User enters new pin$")
    public void user_enters_new_pin() throws Throwable {
    	Thread.sleep(2000);
    	enterPinCode(enter_pin_title_text,inputProperties.getpinChangeText(),inputProperties.getnewPin());
    }

    @And("^User enter pin again$")
    public void user_enter_pin_again() throws Throwable {
    	Thread.sleep(2000);
    	enterPinCode(enter_pin_title_text,inputProperties.getreconfirmPinChangeText(),inputProperties.getnewPin());
    }

    @Then("^Pin code changed successfully$")
    public void pin_code_changed_successfully() throws Throwable {
    	Assert.assertTrue(commonUtils.displayed(parental_control_change_pin));
    }
    
    @Given("^User click on send feedback option$")
    public void user_click_on_send_feedback_option() throws Throwable {
    	commonUtils.clickonElement(send_feedback);
    	Assert.assertTrue(commonUtils.displayed(feedback_text_box));
    }

    @When("^User enter feedback and email id$")
    public void user_enter_feedback_and_email_id() throws Throwable {
		commonUtils.sendKey(feedback_text_box, inputProperties.getfeedback());
		commonUtils.sendKey(email_text_box, inputProperties.getemail());
    }

    @Then("^user submit the feedback$")
    public void user_submit_the_feedback() throws Throwable {
    	commonUtils.clickonElement(feedback_submit_button);
    	Assert.assertTrue(commonUtils.displayed(send_feedback));
    }
    
    public void enterPinCode(By locator, String message, String pinvalue) {
    	String text = commonUtils.getText(locator);
		if (text.equalsIgnoreCase(message)) {
			String pin = pinvalue;
			for (int i = 0; i < pin.length(); i++) {
				char pinValue = pin.charAt(i);
				switch (pinValue) {
				case '1':
					commonUtils.clickonElement(input_pin_1);
					break;
				case '2':
					commonUtils.clickonElement(input_pin_2);
					break;
				case '3':
					commonUtils.clickonElement(input_pin_3);
					break;
				case '4':
					commonUtils.clickonElement(input_pin_4);
					break;
				case '5':
					commonUtils.clickonElement(input_pin_5);
					break;
				case '6':
					commonUtils.clickonElement(input_pin_6);
					break;
				case '7':
					commonUtils.clickonElement(input_pin_7);
					break;
				case '8':
					commonUtils.clickonElement(input_pin_8);
					break;
				case '9':
					commonUtils.clickonElement(input_pin_9);
					break;
				}

			}
		}
    }
    
    @And("^User select the age restriction as ten$")
    public void user_select_the_age_restriction_as_ten() throws Throwable {
	Thread.sleep(2000);
	Assert.assertTrue(commonUtils.displayed(age_restriction_18_icon));
	if (!commonUtils.selected(age_restriction_10_icon))
	    commonUtils.clickonElement(age_restriction_10_icon);
    }

    @And("^User navigates back to home page$")
    public void user_navigates_back_to_home_page() {
	click_on_back_button();
	Assert.assertTrue(commonUtils.displayed(home_button));
    }

    public void click_on_back_button() {
	commonUtils.clickonElement(back_button);
	commonUtils.clickonElement(back_button);
	commonUtils.clickonElement(back_button);
    }
    
    public void enter_parental_pin_for_programs() {
	commonUtils.displayed(parental_pin_input);
	commonUtils.sendKey(parental_pin_input, inputProperties.getpinValue());
	commonUtils.clickonElement(parental_pin_confirm_button);
	if(commonUtils.displayed(parental_pin_input)) {
	    commonUtils.sendKey(parental_pin_input, inputProperties.getnewPin());
	    commonUtils.clickonElement(parental_pin_confirm_button);
	}
    }
    
}