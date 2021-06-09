package iOS_stepdefinations;

import java.io.IOException;

import org.testng.Assert;

import config.Hooks;
import iOS_screens.Home_Screen;
import iOS_screens.Login_Screen;
import iOS_screens.Setting_Screen;
import iOS_stepdefinations.Setting_Page;
import iOS_stepdefinations.Signin_Page;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;


//@RunWith(Cucumber.class)
public class Login_Page implements Login_Screen, Home_Screen, Setting_Screen {

	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;
	protected Connection_Number_Page ios_connectionPage;
	public Setting_Page setting_page;
	public Signin_Page signin_Page;

	public Login_Page() throws IOException
	{
		 driver = Hooks.getDriver();
		 commonUtils = new CommonUtils(driver);
		 ios_connectionPage = new Connection_Number_Page();
			setting_page = new Setting_Page();
			signin_Page = new Signin_Page();

	}
	
	@And("^User is on the login page$")
    public void User_is_on_the_login_page() throws Throwable {
		//commonUtils.switchcontext(commonUtils.getWebView());
		Assert.assertTrue(commonUtils.displayed(username1));
		//commonUtils.clickonElement(user_tab);
    }
	
	
    @And("^The User enters credentials$")
    public void the_user_enters_credentials() throws Throwable {
    	commonUtils.clickonElement(username1);
    	commonUtils.sendKey(username1,commonUtils.getUsername());
    	commonUtils.clickonElement(password1);
    	commonUtils.sendKey(password1,commonUtils.getPassword());
    	commonUtils.clickonElement(confirm);
    	if (!commonUtils.displayed(home_button))
    	    ios_connectionPage.handle_connection_number_page();
    }
    
    @Then("^The user log out from the app$")
    public void the_user_log_out_from_the_app() {
    	try {
			setting_page.the_user_is_on_settings_page();
		} catch (Throwable e) {
			e.printStackTrace();
		}
    	commonUtils.clickonElement(logout_button);
    	Assert.assertTrue(commonUtils.displayed(logout_confirmation_message));
    	commonUtils.clickonElement(logout_confirmation_button);
    }
    
    @And("^invalid \"([^\"]*)\" and \"([^\"]*)\" is entered$")
    public void invalid_something_and_something_is_entered(String username, String password) throws Throwable {
    	if(!commonUtils.displayed(home_button)) {
		}
		else if(commonUtils.displayed(home_button)) {
			the_user_log_out_from_the_app();
		}
    	signin_Page.user_select_the_login_option();
    	commonUtils.clickonElement(user_tab);
    	commonUtils.sendKey(user_tab,username);
    	commonUtils.clickonElement(pwd_tab);
    	commonUtils.sendKey(pwd_tab,password);
    	commonUtils.clickonElement(con_button);
    }
    
    @Then("^User successfully log in to the Pickx app$")
    public void user_successfully_login_to_the_pickx_app() throws Throwable {
    	Assert.assertTrue(commonUtils.displayed(home_button));
    }
    
    @Then("^User should get an error message$")
    public void user_should_get_an_error_message() throws Throwable {
    	Assert.assertTrue(commonUtils.displayed(error_message));
    }
    
    @Then("^User logged in to the Pickx app$")
    public void user_logged_in_to_thepickx_app() throws Throwable {
		if(!commonUtils.displayed(home_button)) {
			signin_Page.user_select_the_login_option();
			the_user_enters_credentials();
		}	
		user_successfully_login_to_the_pickx_app();
    }
    
    @When("^The user login as epic user$")
    public void the_user_login_as_epic_user() throws Throwable {
    	signin_Page.user_select_the_login_option();
    	User_is_on_the_login_page();
    	the_user_enters_credentials();
    	user_successfully_login_to_the_pickx_app();
    	Assert.assertFalse(commonUtils.displayed(Recordings));
    }
    
}
