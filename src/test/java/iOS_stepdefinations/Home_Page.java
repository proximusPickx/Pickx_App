package iOS_stepdefinations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.TestException;

import Android_stepdefinations.Program_Details_Page;
import Android_stepdefinations.Setting_Page;
import base.iOS_input_properties;
import config.Hooks;
import iOS_screens.Home_Screen;
import iOS_screens.Login_Screen;
import iOS_screens.Setting_Screen;
import iOS_screens.Swimlane_Contents_Screen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class Home_Page implements Login_Screen, Home_Screen, Setting_Screen, Swimlane_Contents_Screen{
	
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;
	    public Setting_Page settingPage;
	    public Program_Details_Page programDetalsPage;
	    public iOS_input_properties inputProperties;
	
	public Home_Page() throws IOException
	{
		 driver = Hooks.getDriver();
		 commonUtils = new CommonUtils(driver);
			settingPage = new Setting_Page();
			inputProperties = new iOS_input_properties();
			programDetalsPage = new Program_Details_Page();

	}
	
	   @Given("^Hero content is present on the home screen$")
	    public void hero_content_is_present_on_the_home_screen() throws Throwable {
	    	try {
	    		Assert.assertTrue(commonUtils.displayed(hero_banner_container));
	    	} catch (Exception e) {
	    	    throw new TestException(String.format("Hero banner not found"));
	    	}
	    }
	   
	    @When("^User can see hero banner content are present$")
	    public void user_can_see_hero_banner_content_are_present() throws Throwable {
	    	//Assert.assertTrue(commonUtils.displayed(hero_banner_channel_logo));
	    	//Assert.assertTrue(commonUtils.displayed(textview_hero_title));
	    	//Assert.assertTrue(commonUtils.displayed(textview_hero_metadata));
	    	Assert.assertTrue(commonUtils.displayed(hero_banner_watch_button));
	    	//Assert.assertTrue(commonUtils.displayed(hero_banner_icon_container));
	    }
	    
	    @When("^User can see bottom navigation menu items$")
	    public void user_can_see_bottom_navigation_menu_items() throws Throwable {
		Assert.assertTrue(commonUtils.displayed(home_button));
		Assert.assertTrue(commonUtils.displayed(liveTV_button));
		Assert.assertTrue(commonUtils.displayed(tvGuide_button));
		Assert.assertTrue(commonUtils.displayed(Recordings));
		Assert.assertTrue(commonUtils.displayed(settings_button));
		Assert.assertTrue(commonUtils.displayed(MyVideos));
	    }
	    
	    @Given("^Home screen categories are displayed$")
	    public void home_screen_categories_are_displayed() throws Throwable {
	    	Assert.assertTrue(commonUtils.displayed(home_category_container));
	    }
	    
	    @When("^The user validates the displayed categories on the homepage$")
	    public void user_validates_the_displayed_categories_in_homepage() throws Throwable {
	    	List<String> categoriesDisplayed = new ArrayList<String>();
	    	int counter = 0;
	    	String temp = "";
	    	while (counter < 3) {
	    	    List<MobileElement> categoryElements = commonUtils.findElements(home_categories);
	    	    System.out.println("categoryElements"+ categoryElements);
	    	    String lastCategory = categoryElements.get(categoryElements.size() - 1).getText();
	    	    for (MobileElement category : categoryElements) {
	    		String categoryName = category.getText();
	    		if (categoryName.contains("("))
	    		    categoryName = categoryName.split("\\(")[0].trim();
	    		categoriesDisplayed.add(categoryName.toLowerCase());
	    	    }
	    	    if (temp.equals(lastCategory))
	    		counter++;
	    	    temp = lastCategory;
	    	    commonUtils.swipeLeftOverElement(categoryElements.get(categoryElements.size() - 1));
	    	}
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getMovies()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getSeries()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getEntertainment()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getSports()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getDiscovery()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getKids()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getMusic()));
		Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getNews()));
	    }
	    
	    @Given("^Validate homescreen loading skeleton$")
	    public void validate_homescreen_loading_skeleton() throws Throwable {
	    	Assert.assertTrue(commonUtils.displayed(HomeScreen_layout));
	    	Assert.assertTrue(commonUtils.displayed(home_category_container));
	    	user_can_see_bottom_navigation_menu_items();	
	    }
	    
	    @Given("^User see locked Hero banner$")
	    public void user_see_locked_hero_banner() {
	    	Assert.assertTrue(commonUtils.displayed(hero_banner_container));
	    	commonUtils.displayed(svod_icon_locked);
	    }

	    @When("^Tiles are properly locked $")
	    public void tiles_are_properly_locked() {
	    	Assert.assertTrue(commonUtils.displayed(swimlane_content_page_program_title));
	    	Assert.assertTrue(commonUtils.displayed(episode_subtitle));
	    	Assert.assertTrue(commonUtils.displayed(svod_icon_age));
	    }

	    @Then("^User unlocks the locked hero banner$")
	    public void user_unlocks_the_locked_hero_banner() {
	    	commonUtils.clickonElement(hero_banner_container);
	    	settingPage.enter_parental_pin_for_programs();
	    	Assert.assertTrue(commonUtils.displayed(hero_banner_watch_button));
	    }

}
