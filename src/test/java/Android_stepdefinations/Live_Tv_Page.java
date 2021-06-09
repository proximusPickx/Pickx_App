package Android_stepdefinations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.TestException;

import Android_screens.Home_Screen;
import Android_screens.LiveTV_Screen;
import base.Android_input_properties;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class Live_Tv_Page implements Home_Screen, LiveTV_Screen {

    public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;
    public Android_input_properties inputProperties = new Android_input_properties();
    public Setting_Page settingPage;
    public Program_Details_Page programDetailsPage;

    public Live_Tv_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
	settingPage = new Setting_Page();
	programDetailsPage = new Program_Details_Page();
    }

    @Then("^User navigates to live TV page$")
    public void user_navigates_to_live_tv() {
	commonUtils.clickonElement(liveTV_button);
	commonUtils.waitTillVisibility(live_tv_channel_icon, 15);
	Assert.assertTrue(inputProperties.getLiveTvPageTitle().equalsIgnoreCase(commonUtils.getText(page_title)));
    }

    @Given("^Categories are displayed$")
    public void categories_are_displayed() {
	Assert.assertTrue(commonUtils.displayed(category_button_container));
    }

    @Then("^Validate the displayed categories$")
    public void validate_categories() {
	List<String> categoriesDisplayed = new ArrayList<String>();
	int counter = 0;
	String temp = "";
	while (counter < 3) {
	    List<MobileElement> categoryElements = commonUtils.findElements(categories);
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
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getAll()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getMovies()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getSeries()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getEntertainment()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getSports()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getDiscovery()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getKids()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getMusic()));
	Assert.assertTrue(categoriesDisplayed.contains(inputProperties.getNews()));
    }

    @When("^User can see metadata of the program tiles$")
    public void user_can_see_program_metadata() {
	try {
	    commonUtils.findElements(program);
	} catch (Exception e) {
	    throw new TestException(String.format("Programs not found in live TV"));
	}
	Assert.assertTrue(commonUtils.displayed(live_tv_program_poster));
	Assert.assertTrue(commonUtils.displayed(live_tv_channel_icon));
	Assert.assertTrue(commonUtils.displayed(live_tv_progress_bar));
	Assert.assertTrue(commonUtils.displayed(live_tv_program_title));
	Assert.assertTrue(commonUtils.displayed(live_tv_program_time));
    }
    
    @And("^User selects movies category$")
    public void user_selects_movies_category() {
	int counter = 0;
	String temp = "";
	boolean categoryFound = false;
	while (counter < 3) {
	    List<MobileElement> categoryElements = commonUtils.findElements(categories);
	    String lastCategory = categoryElements.get(categoryElements.size() - 1).getText();
	    for (MobileElement category : categoryElements) {
		String categoryName = category.getText();
		if (categoryName.toLowerCase().contains(inputProperties.getMovies())) {
		    category.click();
		    categoryFound = true;
		    break;
		}
	    }
	    if (categoryFound)
		break;
	    if (temp.equals(lastCategory))
		counter++;
	    temp = lastCategory;
	    commonUtils.swipeLeftOverElement(categoryElements.get(categoryElements.size() - 1));
	}
	if (!categoryFound)
	    throw new TestException(String.format("Movies category not found"));
    }
    
    @And("^Verify if the program is unlocked$")
    public void verify_if_the_program_is_unlocked() throws InterruptedException {
	int counter = 0;
	String temp = "";
	boolean lockedProgramFound = false;
	while (counter < 3) {
	    List<MobileElement> programList = commonUtils.findElements(program_description);
	    String lastProgramTitle = programList.get(programList.size() - 1).findElement(live_tv_program_title).getText();
	    for (int i = 0; i < programList.size(); i++) {
		List<MobileElement> icon = new ArrayList<MobileElement>();
		try {
		    icon = programList.get(i).findElements(age_icon);
		} catch (Exception e) {
		    programList = commonUtils.findElements(program_description);
		    icon = programList.get(i).findElements(age_icon);
		}
		if (icon.isEmpty())
		    continue;
		if (!programList.get(i).findElement(live_tv_program_title).getText().equalsIgnoreCase("Locked content"))
		    throw new TestException(String.format("Program not locked even after enabling parental control"));
		programList.get(i).click();
		settingPage.enter_parental_pin_for_programs();
		programList = commonUtils.findElements(program_description);
		String title;
		title = programList.get(i).findElement(live_tv_program_title).getText();
		if (title.equalsIgnoreCase("Locked content"))
		    throw new TestException(
			    String.format("Program didn't unlock even after entering parental control pin"));
		lockedProgramFound = true;
		break;
	    }
	    if (lockedProgramFound)
		break;
	    if (lastProgramTitle.equals(temp))
		counter++;
	    temp = lastProgramTitle;
	    commonUtils.swipeUpOverHomeScreen();
	}
    }
    
    @Then("^Verify the filters are horizontally scrollable$")
    public void verify_the_filters_are_horizontally_scrollable() {
	List<String> categoriesDisplayedBeforeSwipe = new ArrayList<String>();
	List<String> categoriesDisplayedAfterSwipe = new ArrayList<String>();
	List<MobileElement> categoryElements = commonUtils.findElements(categories);
	for (MobileElement category : categoryElements) {
	    categoriesDisplayedBeforeSwipe.add(category.getText());
	}
	commonUtils.swipeLeftOverElement(categoryElements.get(categoryElements.size() - 1));
	categoryElements = commonUtils.findElements(categories);
	for (MobileElement category : categoryElements) {
	    categoriesDisplayedAfterSwipe.add(category.getText());
	}
	if (categoriesDisplayedAfterSwipe.equals(categoriesDisplayedBeforeSwipe))
	    throw new TestException(String.format("Live TV categories are not horizontally scrollable"));
	commonUtils.swipeRightOverElement(categoryElements.get(0));
    }
    
    @And("^Verify respective programs are displayed for each selected category$")
    public void verify_respective_programs_are_displayed_for_each_selected_category() {
	List<String> categoriesDisplayed = new ArrayList<String>();
	int counter = 0;
	String temp = "";
	String previousProgram = "";
	while (counter < 3) {
	    List<MobileElement> categoryElements = commonUtils.findElements(categories);
	    String lastCategory = categoryElements.get(categoryElements.size() - 1).getText();
	    for (MobileElement category : categoryElements) {
		String categoryName = category.getText();
		if (categoryName.contains("("))
		    categoryName = categoryName.split("\\(")[0].trim().toLowerCase();
		if (categoriesDisplayed.contains(categoryName))
		    continue;
		categoriesDisplayed.add(categoryName);
		category.click();
		// Since category name is not displayed for the programs verifying if respective
		// programs are displayed for each category is impossible without backend data.
		// So checking if the program changes when new category is selected
		String currentProgram = commonUtils.getElement(live_tv_program_title).getText();
		if (previousProgram.equals(currentProgram))
		    throw new TestException(
			    String.format("Program list not changed when different category is selected"));
		previousProgram = currentProgram;
	    }
	    if (temp.equals(lastCategory))
		counter++;
	    temp = lastCategory;
	    commonUtils.swipeLeftOverElement(categoryElements.get(categoryElements.size() - 1));
	}
    }

    @Given("^User records and validate episode of live airing from live TV$")
    public void user_records_and_validate_live_episode_from_livetv() {
	select_recordable_item_from_livetv(true);
	Map<String, String> programDetails = programDetailsPage.records_series_program_and_validate_updated_details(true);
	programDetailsPage.close_program_details_page_to_reach_livetv();
	MobileElement program = verify_recording_icon_present_over_program_in_livetv(programDetails);
	program.click();
	programDetailsPage.stop_recording_episode();
	programDetailsPage.delete_recording();
    }
    
    public void select_recordable_item_from_livetv(boolean isSeries) {
	boolean programFound = false;
	List<String> checkedPrograms = new ArrayList<String>();
	for (int i = 0; i < 15; i++) {
	    List<MobileElement> programDescriptionList = commonUtils.findElements(program_description);
	    for (int j = 0; j < programDescriptionList.size(); j++) {
		programDescriptionList = commonUtils.findElements(program_description);
		if (!programDescriptionList.get(j).isDisplayed())
		    continue;
		String programName;
		try {
		    programName = programDescriptionList.get(j).findElement(live_tv_program_title).getText();
		} catch (Exception e) {
		    programDescriptionList = commonUtils.findElements(program_description);
		    programName = programDescriptionList.get(j).findElement(live_tv_program_title).getText();
		}
		if (checkedPrograms.contains(programName))
		    continue;
		checkedPrograms.add(programName);
		programDescriptionList.get(j).click();
		if (programDetailsPage.is_program_to_be_recorded()) {
		    if (programDetailsPage.verify_if_ongoing_program_is_part_of_series() == isSeries) {
			programFound = true;
			break;
		    }
		}
		programDetailsPage.close_program_details_page_to_reach_livetv();
	    }
	    if (programFound)
		break;
	    commonUtils.swipeUpOverHomeScreen();
	}
	if (!programFound)
	    throw new TestException(String.format("Failed to find a series program from live TV for recording"));
    }
    
    public MobileElement verify_recording_icon_present_over_program_in_livetv(Map<String, String> programDetails) {
	boolean programFound = false;
	List<MobileElement> programDescriptionList = commonUtils.findElements(program_description);
	String programName = programDetails.get("title");
	for (MobileElement programDescription : programDescriptionList) {
	    programDescriptionList = commonUtils.findElements(program_description);
	    if (programDescription.findElement(live_tv_program_title).getText().contains(programName)) {
		programFound = true;
		if (programDescription.findElements(recording_icon_in_homescreen).size() < 1)
		    throw new TestException(
			    String.format("Recording icon not displayed under the program tile in live tv page"));
		return programDescription.findElement(live_tv_program_title);
	    }
	}
	if (!programFound)
	    throw new TestException(String.format("Couldn't find " + programName + " in the live TV"));
	return null;
    }
    
    @Then("^User validates series recording of live airing from live TV$")
    public void validates_series_recording_of_live_airing_from_livetv() {
	select_recordable_item_from_livetv(true);
	Map<String, String> programDetails = programDetailsPage
		.records_series_program_and_validate_updated_details(false);
	programDetailsPage.close_program_details_page_to_reach_livetv();
	MobileElement program = verify_recording_icon_present_over_program_in_livetv(programDetails);
	program.click();
	programDetailsPage.stop_recording_series(false);
	programDetailsPage.delete_recording();
    }
    
    @Then("^User validates recording of live airing not part of series from live TV$")
    public void user_validates_recording_of_live_airing_not_part_of_series_from_livetv() {
	// Since movies category has mostly non-series item navigating to movies
	// category
	user_selects_movies_category();
	select_recordable_item_from_livetv(false);
	Map<String, String> programDetails = programDetailsPage
		.records_non_series_program_and_validate_updated_details(true);
	programDetailsPage.close_program_details_page_to_reach_livetv();
	MobileElement program = verify_recording_icon_present_over_program_in_livetv(programDetails);
	program.click();
	programDetailsPage.stop_recording_non_series_item();
	programDetailsPage.delete_recording();
    }
    
    @Given("^Validate liveTV loading skeleton$")
    public void validate_livetv_loading_skeleton() {
    	Assert.assertTrue(commonUtils.displayed(category_button_container));
    	Assert.assertTrue(commonUtils.displayed(liveTV_skeleton));
    	Assert.assertTrue(commonUtils.displayed(toolbar));
    }
}
