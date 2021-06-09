package Android_stepdefinations;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.TestException;

import Android_screens.Home_Screen;
import Android_screens.LiveTV_Screen;
import Android_screens.Setting_Screen;
import Android_screens.Swimlane_Contents_Screen;
import base.Android_input_properties;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class Home_Page implements Home_Screen, Setting_Screen, Swimlane_Contents_Screen, LiveTV_Screen {

    public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;
    public Program_Details_Page programDetalsPage;
    public Android_input_properties inputProperties;
    public Setting_Page settingPage;

    public Home_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
	inputProperties = new Android_input_properties();
	settingPage = new Setting_Page();
	programDetalsPage = new Program_Details_Page();
    }

    @Given("^User selects ongoing program from home screen$")
    public void user_selects_ongoing_program_from_home_screen() throws Throwable {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	// Swipe over the home screen to find swimlane with live icons
	int counter = 0;
	String temp = null;
	boolean ongoingProgramFound = false;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	// To verify if the user has swiped till the last swimlane the counter value is
	// provided. For verifying if the last swimlane has reached, after each swipe
	// the program title of the last swimlane is compared with that of the previous
	// swipe. And if the titles are same the counter value is incremented. Since
	// there are swimlanes with same title, this comparison is repeated till counter
	// value reaches 3
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    String lastSwimlaneProgramTitle;
	    try {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
			    .findElement(program_title_under_swimlane).getText();
	    } catch (Exception e) {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
			    .findElement(program_title_under_swimlane).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0) {
		    swimlane.findElement(live_icon_in_swimlane).click();
		    ongoingProgramFound = true;
		    break;
		}
	    }
	    if (ongoingProgramFound)
		break;
	    if (lastSwimlaneProgramTitle.equalsIgnoreCase(temp))
		counter++;
	    temp = lastSwimlaneProgramTitle;
	    commonUtils.swipeUpOverScreen();
	}
	if (!ongoingProgramFound)
	    throw new TestException(String.format("Ongoing program not found in the home screen"));
	Assert.assertTrue(programDetalsPage.user_on_program_details_page());
    }

    @Given("^User selects completed replay program from home screen$")
    public void user_selects_completed_replay_program_from_home() throws ParseException {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	int counter = 0;
	String temp = null;
	boolean replayProgramFound = false;
	String lastProgram = null;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	// To verify if the user has swiped till the last swimlane the counter value is
	// provided. For verifying if the last swimlane has reached, after each swipe
	// the program title of the last swimlane is compared with that of the previous
	// swipe. And if the titles are same the counter value is incremented. Since
	// there are
	// swimlanes with same title, this comparison is repeated till counter value
	// reaches 3
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    try {
		lastProgram = swimlaneList.get(swimlaneList.size() - 1).findElement(program_title_under_swimlane)
			.getText();
	    } catch (Exception e) {
		lastProgram = swimlaneList.get(swimlaneList.size() - 2).findElement(program_title_under_swimlane)
			.getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0)
		    continue;
		int counter2 = 0;
		String temp2 = null;
		boolean switchToNextSwimlane = false;
		// Applying same logic of swiping vertically over the screen for swiping
		// horizontally over the swimlane
		while (counter2 < 3) {
		    List<MobileElement> programList = swimlane.findElements(program_in_swimlane);
		    String lastProgramTitle = programList.get(programList.size() - 1)
			    .findElement(program_title_under_swimlane).getText();
		    for (MobileElement program : programList) {
			try {
			    program.findElement(replay_icon);
			} catch (Exception e) {
			    continue;
			}
			String subtitle = null;
			try {
			    subtitle = program.findElement(program_subtitle_under_swimlane).getText();
			} catch (Exception e) {
			    continue;
			}
			// Checking if program is a non VOD asset
			if (!subtitle.contains("|")) {
			    switchToNextSwimlane = true;
			    break;
			}
			// Verifying if the airing is already completed
			if (subtitle.contains("Tomorrow")) {
			    continue;
			} else if (subtitle.contains("Yesterday")) {
			    program.click();
			    replayProgramFound = true;
			    break;
			} else if (subtitle.contains("Today")) {
			    if (commonUtils.if_time_is_past(subtitle.split("\\|")[1].split("-")[1].trim())) {
				program.click();
				replayProgramFound = true;
				break;
			    }
			} else {
			    if (commonUtils.verify_if_date_is_past_date(subtitle.split("\\|")[0].trim())) {
				program.click();
				replayProgramFound = true;
				break;
			    }
			}
		    }
		    if (switchToNextSwimlane || replayProgramFound)
			break;
		    if (lastProgramTitle.equals(temp2))
			counter2++;
		    temp2 = lastProgramTitle;
		    commonUtils.swipeLeftOverElement(programList.get(programList.size() - 1));
		}
		if (replayProgramFound)
		    break;
	    }
	    if (replayProgramFound)
		break;
	    if (lastProgram.equals(temp))
		counter++;
	    temp = lastProgram;
	    commonUtils.swipeUpOverScreen();
	}
	Assert.assertTrue(programDetalsPage.user_on_program_details_page());
    }

    @Given("^User selects VOD asset from home screen$")
    public void user_selects_VOD_asset_from_home_screen() {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	int counter = 0;
	String temp = null;
	boolean vodItemFound = false;
	String lastProgram = null;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	// To verify if the user has swiped till the last swimlane the counter value is
	// provided. For verifying if the last swimlane has reached, after each swipe
	// the program title of the last swimlane is compared with that of the previous
	// swipe. And if the titles are same the counter value is incremented. Since
	// there are
	// swimlanes with same title, this comparison is repeated till counter value
	// reaches 3
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    try {
		lastProgram = swimlaneList.get(swimlaneList.size() - 1).findElement(program_title_under_swimlane)
			.getText();
	    } catch (Exception e) {
		lastProgram = swimlaneList.get(swimlaneList.size() - 2).findElement(program_title_under_swimlane)
			.getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0)
		    continue;
		String subtitle = null;
		try {
		    subtitle = swimlane.findElement(program_subtitle_under_swimlane).getText();
		} catch (Exception e) {
		    continue;
		}
		if (subtitle.contains("|"))
		    continue;
		vodItemFound = true;
		swimlane.findElement(swimlane_program_poster).click();
		break;
	    }

	    if (vodItemFound)
		break;
	    if (lastProgram.equals(temp))
		counter++;
	    temp = lastProgram;
	    commonUtils.swipeUpOverScreen();
	}
	Assert.assertTrue(programDetalsPage.user_on_program_details_page());
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
    	Assert.assertTrue(commonUtils.displayed(hero_banner_channel_logo));
    	Assert.assertTrue(commonUtils.displayed(textview_hero_title));
    	Assert.assertTrue(commonUtils.displayed(textview_hero_metadata));
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
    }
    
    @When("^User can see metadata for programs under Now on TV swimlane$")
    public void user_can_see_metadata_for_programs_under_now_on_tv() {
	int counter = 0;
	String temp = null;
	boolean nowOnTvFound = false;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    String lastSwimlaneProgramTitle;
	    try {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
			.findElement(program_title_under_swimlane).getText();
	    } catch (Exception e) {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
			.findElement(program_title_under_swimlane).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0) {
		    try {
			swimlane.findElement(program_in_swimlane);
		    } catch (Exception e) {
			throw new TestException(String.format("Programs not found under 'Now on TV' swimlane"));
		    }
		    metadata_provided_for_channel_programs(swimlane.findElement(program_in_swimlane), true);
		    nowOnTvFound = true;
		    break;
		}
	    }
	    if (nowOnTvFound)
		break;
	    if (lastSwimlaneProgramTitle.equals(temp))
		counter++;
	    temp = lastSwimlaneProgramTitle;
	    commonUtils.swipeUpOverScreen();
	}
    }

    public void metadata_provided_for_channel_programs(MobileElement program, boolean isLive) {
	try {
	    program.findElement(swimlane_program_poster);
	} catch (Exception e) {
	    throw new TestException(
		    String.format("Program poster not displayed for program under 'Now on TV' swimlane"));
	}
	if (isLive)
	    try {
		program.findElement(live_icon_in_swimlane);
	    } catch (Exception e) {
		throw new TestException(
			String.format("Live label not displayed for program under 'Now on TV' swimlane"));
	    }
	try {
	    program.findElement(channel_icon);
	} catch (Exception e) {
	    throw new TestException(String.format("Channel icon not displayed for program under 'Now on TV' swimlane"));
	}
	try {
	    program.findElement(program_title_under_swimlane);
	} catch (Exception e) {
	    throw new TestException(
		    String.format("Program title not displayed for program under 'Now on TV' swimlane"));
	}
	try {
	    program.findElement(program_subtitle_under_swimlane);
	} catch (Exception e) {
	    throw new TestException(
		    String.format("Program subtitle not displayed for program under 'Now on TV' swimlane"));
	}
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

    @Given("^User selects recommended in movies and series$")
    public void user_selects_recommended_in_movies_and_series() {
    	commonUtils.waitTillVisibility(swimlane_container, 20);
    	// To verify if the last swimlane has reached, using counter value in while loop
    	int counter = 0;
    	String temp = null;
    	String lastswimlane = null;
    	boolean swimlaneFound = false;
    	while (counter < 3) {
    	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane);
    	    try {
    		lastswimlane = swimlaneList.get(swimlaneList.size() - 1).findElement(swimlane_title).getText();
    	    } catch (Exception e) {
    		lastswimlane = swimlaneList.get(swimlaneList.size() - 2).findElement(swimlane_title).getText();
    	    }
    	    for (MobileElement swimlane : swimlaneList) {
    		if (swimlane.findElements(swimlane_title).size() < 1)
    		    continue;
    		if (swimlane.findElement(swimlane_title).getText().toLowerCase()
    			.equalsIgnoreCase(inputProperties.getRecommendedMoviesAndSeriesSwimlane())) {
    		    swimlane.findElement(swimlane_view_all_button);
    		    swimlaneFound = true;
    		    break;
    		}
    	    }
    	    if (swimlaneFound)
    		break;
    	    if (lastswimlane.equals(temp))
    		counter++;
    	    temp = lastswimlane;
    	    commonUtils.swipeUpOverHomeScreen();
    	}
        }
    
    @Given("^User selects recommended in movies and series swimlane$")
    public void user_selects_recommended_in_movies_and_series_swimlane() {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	// To verify if the last swimlane has reached, using counter value in while loop
	int counter = 0;
	String temp = null;
	String lastswimlane = null;
	boolean swimlaneFound = false;
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane);
	    try {
		lastswimlane = swimlaneList.get(swimlaneList.size() - 1).findElement(swimlane_title).getText();
	    } catch (Exception e) {
		lastswimlane = swimlaneList.get(swimlaneList.size() - 2).findElement(swimlane_title).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(swimlane_title).size() < 1)
		    continue;
		if (swimlane.findElement(swimlane_title).getText().toLowerCase()
			.equalsIgnoreCase(inputProperties.getRecommendedMoviesAndSeriesSwimlane())) {
		    swimlane.findElement(swimlane_view_all_button).click();
		    swimlaneFound = true;
		    break;
		}
	    }
	    if (swimlaneFound)
		break;
	    if (lastswimlane.equals(temp))
		counter++;
	    temp = lastswimlane;
	    commonUtils.swipeUpOverHomeScreen();
	}
	Assert.assertTrue(commonUtils.displayed(movies_and_series_page_title));
    }
    
    @Given("^User records and validate episode of live airing from homescreen$")
    public void user_records_and_validate_episode_of_live_airing_from_homescreen() {
	AndroidElement nowOnTv = returnNowOnTvSwimlane();
	if (!select_program_from_homescreen_for_recording(nowOnTv, true, true))
	    throw new TestException(String
		    .format("Live airing which is part of series and can be recorded not found from home screen"));
	Map<String, String> programDetails = programDetalsPage
		.records_series_program_and_validate_updated_details(true);
	programDetalsPage.close_program_details_page_to_reach_homescreen();
	MobileElement program = verify_recording_icon_present_over_program_in_homescreen(programDetails);
	program.click();
	programDetalsPage.stop_recording_episode();
	programDetalsPage.delete_recording();
    }
    
    public AndroidElement returnNowOnTvSwimlane() {
	int counter = 0;
	String temp = null;
	boolean nowOnTvSwimlaneFound = false;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	AndroidElement nowOnTvSwimlane = null;
	// To verify if the user has swiped till the last swimlane the counter value is
	// provided. For verifying if the last swimlane has reached, after each swipe
	// the program title of the last swimlane is compared with that of the previous
	// swipe. And if the titles are same the counter value is incremented. Since
	// there are swimlanes with same title, this comparison is repeated till counter
	// value reaches 3
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    String lastSwimlaneProgramTitle;
	    try {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
			.findElement(program_title_under_swimlane).getText();
	    } catch (Exception e) {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
			.findElement(program_title_under_swimlane).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0) {
		    nowOnTvSwimlane = (AndroidElement) swimlane;
		    nowOnTvSwimlaneFound = true;
		    break;
		}
	    }
	    if (nowOnTvSwimlaneFound)
		break;
	    if (lastSwimlaneProgramTitle.equalsIgnoreCase(temp))
		counter++;
	    temp = lastSwimlaneProgramTitle;
	    commonUtils.swipeUpOverScreen();
	}
	if (!nowOnTvSwimlaneFound)
	    throw new TestException(String.format("Ongoing program not found in the home screen"));
	return nowOnTvSwimlane;
    }
    
    public boolean select_program_from_homescreen_for_recording(AndroidElement swimlaneElement, boolean isSeries,
	    boolean isOngoing) {
	int counter = 0;
	String temp = null;
	boolean programFound = false;
	while (counter < 3) {
	    // Applying same logic of swiping vertically over the screen for swiping
	    // horizontally over the swimlane
	    List<MobileElement> programList = swimlaneElement.findElements(program_in_swimlane);
	    String lastProgramTitle;
	    try {
		lastProgramTitle = programList.get(programList.size() - 1).findElement(program_title_under_swimlane)
			.getText();
	    } catch (Exception e) {
		commonUtils.swipeUpOverHomeScreen();
		lastProgramTitle = programList.get(programList.size() - 1).findElement(program_title_under_swimlane)
			.getText();
	    }
	    // Swipe so that the program tile is completely visible
	    programList.get(0).click();
	    if (programDetalsPage.is_program_to_be_recorded()) {
		if (isOngoing) {
		    if (programDetalsPage.verify_if_ongoing_program_is_part_of_series() == isSeries) {
			programFound = true;
			break;
		    }
		} else {
		    if (programDetalsPage.verify_if_upcoming_program_is_part_of_series() == isSeries) {
			programFound = true;
			break;
		    }
		}
	    }
	    programDetalsPage.close_program_details_page_to_reach_homescreen();
	    if (lastProgramTitle.equals(temp))
		counter++;
	    temp = lastProgramTitle;
	    commonUtils.swipeLeftToSeeNextProgramInSwimlane(programList.get(0));
	}
	return programFound;
    }
    
    public MobileElement verify_recording_icon_present_over_program_in_homescreen(Map<String, String> programDetails) {
	boolean programFound = false;
	List<MobileElement> programsList = commonUtils.findElements(program_in_swimlane);
	for (MobileElement program : programsList) {
	    if (program.findElement(program_title_under_swimlane).getText().contains(programDetails.get("title"))) {
		programFound = true;
		if (program.findElements(recording_icon_in_homescreen).size() < 1)
		    throw new TestException(
			    String.format("Recording icon not displayed in the homescreen poster of the program"));
		return program.findElement(program_title_under_swimlane);
	    }
	}
	if (!programFound)
	    throw new TestException(
		    String.format("Couldn't find " + programDetails.get("title") + " in the home screen"));
	return null;
    }
    
    @Given("^User records and validate live airing not part of series from homescreen$")
    public void record_and_validate_live_airing_not_part_of_series_from_homescreen() {
	AndroidElement nowOnTv = returnNowOnTvSwimlane();
	if (!select_program_from_homescreen_for_recording(nowOnTv, false, true))
	    throw new TestException(String
		    .format("Live airing which is not part of series and can be recorded not found from home screen"));
	Map<String, String> programDetails = programDetalsPage
		.records_non_series_program_and_validate_updated_details(true);
	programDetalsPage.close_program_details_page_to_reach_homescreen();
	MobileElement program = verify_recording_icon_present_over_program_in_homescreen(programDetails);
	program.click();
	programDetalsPage.stop_recording_non_series_item();
	programDetalsPage.delete_recording();
    }
    
    @Given("^User validates series recording of replay airing from homescreen$")
    public void validate_series_recording_of_replay_airing_from_homescreen() throws ParseException {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	int counter = 0;
	String temp = null;
	boolean replayProgramFound = false;
	String lastProgram = null;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	// To verify if the user has swiped till the last swimlane the counter value is
	// provided. For verifying if the last swimlane has reached, after each swipe
	// the program title of the last swimlane is compared with that of the previous
	// swipe. And if the titles are same the counter value is incremented. Since
	// there are
	// swimlanes with same title, this comparison is repeated till counter value
	// reaches 3
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    try {
		lastProgram = swimlaneList.get(swimlaneList.size() - 1).findElement(program_title_under_swimlane)
			.getText();
	    } catch (Exception e) {
		lastProgram = swimlaneList.get(swimlaneList.size() - 2).findElement(program_title_under_swimlane)
			.getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0)
		    continue;
		int counter2 = 0;
		String temp2 = null;
		boolean switchToNextSwimlane = false;
		// Applying same logic of swiping vertically over the screen for swiping
		// horizontally over the swimlane
		while (counter2 < 3) {
		    List<MobileElement> programList = swimlane.findElements(program_in_swimlane);
		    String lastProgramTitle = programList.get(programList.size() - 1)
			    .findElement(program_title_under_swimlane).getText();
		    for (MobileElement program : programList) {
			try {
			    program.findElement(replay_icon);
			} catch (Exception e) {
			    continue;
			}
			String subtitle = null;
			try {
			    subtitle = program.findElement(program_subtitle_under_swimlane).getText();
			} catch (Exception e) {
			    continue;
			}
			// Checking if program is a non VOD asset
			if (!subtitle.contains("|")) {
			    switchToNextSwimlane = true;
			    break;
			}
			// Verifying if the airing is already completed
			if (subtitle.contains("Tomorrow")) {
			    continue;
			} else if (subtitle.contains("Today")) {
			    if (!commonUtils.if_time_is_past(subtitle.split("\\|")[1].split("-")[1].trim()))
				continue;
			} else if (!subtitle.contains("Yesterday")) {
			    if (!commonUtils.verify_if_date_is_past_date(subtitle.split("\\|")[0].trim()))
				continue;
			}
			program.click();
			if (programDetalsPage.is_program_to_be_recorded()) {
			    if (programDetalsPage.verify_if_upcoming_program_is_part_of_series()) {
				replayProgramFound = true;
				break;
			    }
			}
			programDetalsPage.close_program_details_page_to_reach_homescreen();
		    }
		    if (switchToNextSwimlane || replayProgramFound)
			break;
		    if (lastProgramTitle.equals(temp2))
			counter2++;
		    temp2 = lastProgramTitle;
		    commonUtils.swipeLeftOverElement(programList.get(programList.size() - 1));
		}
		if (replayProgramFound)
		    break;
	    }
	    if (replayProgramFound)
		break;
	    if (lastProgram.equals(temp))
		counter++;
	    temp = lastProgram;
	    commonUtils.swipeUpOverScreen();
	}
	Map<String, String> programDetails = programDetalsPage
		.records_completed_series_program_and_validate_updated_details();
	programDetalsPage.close_program_details_page_to_reach_homescreen();
	MobileElement program = verify_recording_icon_present_over_program_in_homescreen(programDetails);
	program.click();
	programDetalsPage.stop_series_recording_of_completed_program_and_validate_updated_details();
    }
    
    @Given("^User validates recording of replay airing not part of series from homescreen$")
    public void validate_replay_airing_recording_not_part_of_series_from_homescreen() throws ParseException {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	int counter = 0;
	String temp = null;
	boolean replayProgramFound = false;
	String lastProgram = null;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	// To verify if the user has swiped till the last swimlane the counter value is
	// provided. For verifying if the last swimlane has reached, after each swipe
	// the program title of the last swimlane is compared with that of the previous
	// swipe. And if the titles are same the counter value is incremented. Since
	// there are
	// swimlanes with same title, this comparison is repeated till counter value
	// reaches 3
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane_container_without_title);
	    try {
		lastProgram = swimlaneList.get(swimlaneList.size() - 1).findElement(program_title_under_swimlane)
			.getText();
	    } catch (Exception e) {
		lastProgram = swimlaneList.get(swimlaneList.size() - 2).findElement(program_title_under_swimlane)
			.getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(live_icon_in_swimlane).size() > 0)
		    continue;
		int counter2 = 0;
		String temp2 = null;
		boolean switchToNextSwimlane = false;
		// Applying same logic of swiping vertically over the screen for swiping
		// horizontally over the swimlane
		while (counter2 < 3) {
		    List<MobileElement> programList = swimlane.findElements(program_in_swimlane);
		    String lastProgramTitle = programList.get(programList.size() - 1)
			    .findElement(program_title_under_swimlane).getText();
		    for (MobileElement program : programList) {
			try {
			    program.findElement(replay_icon);
			} catch (Exception e) {
			    continue;
			}
			String subtitle = null;
			try {
			    subtitle = program.findElement(program_subtitle_under_swimlane).getText();
			} catch (Exception e) {
			    continue;
			}
			// Checking if program is a non VOD asset
			if (!subtitle.contains("|")) {
			    switchToNextSwimlane = true;
			    break;
			}
			// Verifying if the airing is already completed
			if (subtitle.contains("Tomorrow")) {
			    continue;
			} else if (subtitle.contains("Today")) {
			    if (!commonUtils.if_time_is_past(subtitle.split("\\|")[1].split("-")[1].trim()))
				continue;
			} else if (!subtitle.contains("Yesterday")) {
			    if (!commonUtils.verify_if_date_is_past_date(subtitle.split("\\|")[0].trim()))
				continue;
			}
			program.click();
			if (programDetalsPage.is_completed_program_non_series()) {
			    replayProgramFound = true;
			    break;
			}
			programDetalsPage.close_program_details_page_to_reach_homescreen();
		    }
		    if (switchToNextSwimlane || replayProgramFound)
			break;
		    if (lastProgramTitle.equals(temp2))
			counter2++;
		    temp2 = lastProgramTitle;
		    commonUtils.swipeLeftOverElement(programList.get(programList.size() - 1));
		}
		if (replayProgramFound)
		    break;
	    }
	    if (replayProgramFound)
		break;
	    if (lastProgram.equals(temp))
		counter++;
	    temp = lastProgram;
	    commonUtils.swipeUpOverScreen();
	}
	if (!replayProgramFound)
	    throw new TestException(
		    String.format("Couldn't find replayable completed non-series program from home screen"));
    }
    
    @When("^User can see metadata for programs under Recommended swimlane$")
    public void user_can_see_metadata_for_programs_under_recommended_swimlane() {
	int counter = 0;
	String temp = null;
	boolean swimlaneFound = false;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane);
	    String lastSwimlaneProgramTitle;
	    try {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
			.findElement(program_title_under_swimlane).getText();
	    } catch (Exception e) {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
			.findElement(program_title_under_swimlane).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		String swimlaneTitle;
		try {
		    swimlaneTitle = swimlane.findElement(swimlane_title).getText();
		    swimlane.findElement(program_title_under_swimlane).getText();
		} catch (Exception e) {
		    continue;
		}
		if (swimlaneTitle.equalsIgnoreCase(inputProperties.getRecommendedForYou())) {
		    try {
			swimlane.findElement(program_in_swimlane);
		    } catch (Exception e) {
			throw new TestException(
				String.format("Programs not found under 'Recommended for you' swimlane"));
		    }
		    metadata_provided_for_channel_programs(swimlane.findElement(program_in_swimlane), false);
		    swimlaneFound = true;
		    break;
		}
	    }
	    if (swimlaneFound)
		break;
	    if (lastSwimlaneProgramTitle.equals(temp))
		counter++;
	    temp = lastSwimlaneProgramTitle;
	    commonUtils.swipeUpOverScreen();
	}
	if (!swimlaneFound)
	    throw new TestException(String.format("'Recommended for you' swimlane not found"));
    }
    
    @And("^User can see metadata for programs under VOD swimlane$")
    public void user_can_see_metadata_for_programs_under_VOD_swimlane() {
	int counter = 0;
	String temp = null;
	boolean swimlaneFound = false;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane);
	    String lastSwimlaneProgramTitle;
	    try {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
			.findElement(program_title_under_swimlane).getText();
	    } catch (Exception e) {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
			.findElement(program_title_under_swimlane).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		String swimlaneTitle;
		try {
		    swimlaneTitle = swimlane.findElement(swimlane_title).getText();
		    swimlane.findElement(program_title_under_swimlane);
		} catch (Exception e) {
		    continue;
		}
		if (swimlaneTitle.equalsIgnoreCase(inputProperties.getRecommendedMoviesAndSeriesSwimlane())) {
		    try {
			swimlane.findElement(program_in_swimlane);
		    } catch (Exception e) {
			throw new TestException(
				String.format("Programs not found under 'Recommended in Movies & Series' swimlane"));
		    }
		    metadata_provided_for_vod(swimlane.findElement(program_in_swimlane));
		    swimlaneFound = true;
		    break;
		}
	    }
	    if (swimlaneFound)
		break;
	    if (lastSwimlaneProgramTitle.equals(temp))
		counter++;
	    temp = lastSwimlaneProgramTitle;
	    commonUtils.swipeUpOverScreen();
	}
	if (!swimlaneFound)
	    throw new TestException(String.format("'Recommended in Movies & Series' swimlane not found"));
    }
    
    @And("^Verify if metadata provided for VOD items$")
    public void metadata_provided_for_vod(MobileElement program) {
	try {
	    program.findElement(swimlane_program_poster);
	} catch (Exception e) {
	    throw new TestException(
		    String.format("Program poster not displayed for program under 'Now on TV' swimlane"));
	}
	try {
	    program.findElement(program_title_under_swimlane);
	} catch (Exception e) {
	    throw new TestException(
		    String.format("Program title not displayed for program under 'Now on TV' swimlane"));
	}
	try {
	    program.findElement(program_subtitle_under_swimlane);
	} catch (Exception e) {
	    throw new TestException(
		    String.format("Program subtitle not displayed for program under 'Now on TV' swimlane"));
	}
    }
    
    @Then("^User can see metadata for programs under future swimlane$")
    public void user_can_see_metadata_for_programs_under_future_swimlane() {
	int counter = 0;
	String temp = null;
	boolean swimlaneFound = false;
	commonUtils.waitTillVisibility(swimlane_program_poster, 30);
	if (commonUtils.displayed(hero_banner_container))
	    commonUtils.swipeUpOverHomeScreen();
	while (counter < 3) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane);
	    String lastSwimlaneProgramTitle;
	    try {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
			.findElement(program_title_under_swimlane).getText();
	    } catch (Exception e) {
		lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
			.findElement(program_title_under_swimlane).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		String swimlaneTitle;
		try {
		    swimlaneTitle = swimlane.findElement(swimlane_title).getText();
		    swimlane.findElement(program_title_under_swimlane);
		} catch (Exception e) {
		    continue;
		}
		if (swimlaneTitle.equalsIgnoreCase(inputProperties.getComingUpOnTv())) {
		    try {
			swimlane.findElement(program_in_swimlane);
		    } catch (Exception e) {
			throw new TestException(String.format("Programs not found under 'Coming up on TV' swimlane"));
		    }
		    metadata_provided_for_channel_programs(swimlane.findElement(program_in_swimlane), false);
		    swimlaneFound = true;
		    break;
		}
	    }
	    if (swimlaneFound)
		break;
	    if (lastSwimlaneProgramTitle.equals(temp))
		counter++;
	    temp = lastSwimlaneProgramTitle;
	    commonUtils.swipeUpOverScreen();
	}
	if (!swimlaneFound)
	    throw new TestException(String.format("'Coming up on TV' swimlane not found"));
    }
    
    @Given("^User validates recording of future airing not part of series from homescreen$")
    public void validate_recording_of_future_airing_not_part_of_series_from_homescreen() throws ParseException {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	AndroidElement swimlane = user_selects_recommended_in_sports_swimlane();
	if (!select_program_from_homescreen_for_recording(swimlane, false, false))
	    throw new TestException(String.format(
		    "Future airing which is not part of series and can be recorded not found from home screen"));
	Map<String, String> programDetails = programDetalsPage
		.records_non_series_program_and_validate_updated_details(false);
	programDetalsPage.close_program_details_page_to_reach_homescreen();
	MobileElement program = verify_recording_icon_present_over_program_in_homescreen(programDetails);
	program.click();
	programDetalsPage.cancel_recording_non_series_item();
    }
    
    public boolean if_program_is_upcoming_non_series_recordable_program(MobileElement program) {
	boolean returnValue = false;
	program.click();
	if (!programDetalsPage.verify_if_upcoming_program_is_part_of_series()) {
	    if (programDetalsPage.is_program_to_be_recorded()) {
		return true;
	    }
	}
	programDetalsPage.close_program_details_page_to_reach_homescreen();
	return returnValue;
    }
    
    @Given("^User selects recommended in sports swimlane$")
    public AndroidElement user_selects_recommended_in_sports_swimlane() {
	commonUtils.waitTillVisibility(swimlane_container, 20);
	// To verify if the last swimlane has reached, using counter value in while loop
	int counter = 0;
	String temp = null;
	String lastswimlane = null;
	boolean swimlaneFound = false;
	while (counter < 4) {
	    List<MobileElement> swimlaneList = commonUtils.findElements(swimlane);
	    try {
		lastswimlane = swimlaneList.get(swimlaneList.size() - 1).findElement(swimlane_title).getText();
	    } catch (Exception e) {
		lastswimlane = swimlaneList.get(swimlaneList.size() - 2).findElement(swimlane_title).getText();
	    }
	    for (MobileElement swimlane : swimlaneList) {
		if (swimlane.findElements(swimlane_title).size() < 1)
		    continue;
		if (swimlane.findElement(swimlane_title).getText().toLowerCase()
			.equalsIgnoreCase(inputProperties.getRecommendedInSports())) {
		    swimlaneFound = true;
		    return (AndroidElement) swimlane;
		}
	    }
	    if (swimlaneFound)
		break;
	    if (lastswimlane.equals(temp))
		counter++;
	    temp = lastswimlane;
	    commonUtils.swipeUpOverHomeScreen();
	}
	if (!swimlaneFound)
	    throw new TestException(
		    String.format("Couldn't find 'Recommended in All Sports' swimlane from home screen"));
	return null;
    }

    @Given("^Validate homescreen loading skeleton$")
    public void validate_homescreen_loading_skeleton() {
    	Assert.assertTrue(commonUtils.displayed(HomeScreen_layout));
    	Assert.assertTrue(commonUtils.displayed(HomeScreen_layout_full));
    	Assert.assertTrue(commonUtils.displayed(HomeScreen_horizandal_scrollview));	
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
