package Android_stepdefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Android_screens.Swimlane_Contents_Screen;
import Android_screens.Vod_Details_Screen;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import utils.CommonUtils;

public class Swimlane_Contents_Page implements Swimlane_Contents_Screen, Vod_Details_Screen {

    public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;

    public Swimlane_Contents_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
    }

    public void user_selects_new_this_week_category() {
	List<MobileElement> categories = commonUtils.findElements(category);
	Assert.assertTrue(categories.size() > 0);
	// Since no texts are available for the categories, using index position
	categories.get(0).click();
	Assert.assertTrue(commonUtils.displayed(new_this_week_page_title));
    }

    public void user_selects_movies_category() {
	List<MobileElement> categories = commonUtils.findElements(category);
	Assert.assertTrue(categories.size() > 0);
	// Since no texts are available for the categories, using index position
	categories.get(1).click();
	Assert.assertTrue(commonUtils.displayed(movies_page_title));
    }

    @And("^User selects a playable vod movie$")
    public void user_selects_playable_vod_movie() {
	user_selects_movies_category();
	boolean movieSelected = false;
	// Since the movie categories have different sub-categories which are dynamic,
	// selecting the first category in each page till the program list is displayed
	for (int i = 0; i < 5; i++) {
	    if (commonUtils.displayed(swimlane_content_page_program_title)) {
		movieSelected = select_vod(true, false);
		break;
	    } else {
		commonUtils.findElements(category).get(0).click();
	    }
	}
	Assert.assertTrue(movieSelected);
    }

    public boolean select_vod(boolean playableVod, boolean isSeries) {
	boolean programSelected = false;
	String temp = "";
	int count = 0;
	By locator = programs_in_movies;
	if (isSeries)
	    locator = programs_in_series;
	while (count < 3) {
	    List<MobileElement> programList = commonUtils.findElements(locator);
	    String lastProgram = "";
	    try {
		lastProgram = programList.get(programList.size() - 1).findElement(swimlane_content_page_program_title)
			.getText();
	    } catch (Exception e) {
		programList = commonUtils.findElements(locator);
		lastProgram = programList.get(programList.size() - 3).findElement(swimlane_content_page_program_title)
			.getText();
	    }
	    for (WebElement program : programList) {
		if (program.findElements(non_playable_icon).size() > 0) {
		    if (playableVod)
			continue;
		} else if (!playableVod) {
		    continue;
		}
		program.click();
		Assert.assertTrue(commonUtils.displayed(program_title));
		programSelected = true;
		break;
	    }
	    if (programSelected)
		break;
	    if (temp.equals(lastProgram))
		count++;
	    temp = lastProgram;
	    commonUtils.swipeUpOverHomeScreen();
	}
	return programSelected;
    }

    @Then("^User navigates back to swimlane content page$")
    public void navigates_back_to_swimlane_content_page() {
	boolean swimlaneContentPageDisplayed = false;
	// Since the categories have different sub-categories which are dynamic,
	// pressing the back button till the main category list is displayed
	for (int i = 0; i < 7; i++) {
	    if (commonUtils.displayed(movies_and_series_page_title)) {
		swimlaneContentPageDisplayed = true;
		break;
	    } else {
		commonUtils.clickonElement(swimlane_content_page_close_button);
	    }
	}
	Assert.assertTrue(swimlaneContentPageDisplayed);
    }

    public void user_selects_series_category() {
	List<MobileElement> categories = commonUtils.findElements(category);
	Assert.assertTrue(categories.size() > 0);
	// Since no texts are available for the categories, using index position
	categories.get(2).click();
	Assert.assertTrue(commonUtils.displayed(series_page_title));
    }

    @And("^User selects a non-playable vod item$")
    public void user_selects_non_playable_vod_item() {
	user_selects_new_this_week_category();
	boolean vodSelected = false;
	// Since the movie categories have different sub-categories which are dynamic,
	// selecting the first category in each page till the program list is displayed
	for (int i = 0; i < 5; i++) {
	    if (commonUtils.displayed(swimlane_content_page_program_title)) {
		vodSelected = select_vod(false, false);
		break;
	    } else {
		commonUtils.findElements(category).get(0).click();
	    }
	}
	Assert.assertTrue(vodSelected);
    }

    @And("^User selects a playable vod series$")
    public void user_selects_playable_vod_series() {
	user_selects_series_category();
	boolean serieSelected = false;
	// Since the series categories have different sub-categories which are dynamic,
	// selecting the first category in each page till the program list is displayed
	for (int i = 0; i < 5; i++) {
	    if (commonUtils.displayed(episode_subtitle)) {
		serieSelected = select_vod(true, true);
		break;
	    } else {
		commonUtils.findElements(category).get(0).click();
	    }
	}
	Assert.assertTrue(serieSelected);
    }

}
