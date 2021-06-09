package Android_stepdefinations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.TestException;

import base.Android_input_properties;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonUtils;

public class TVguide_Page implements Android_screens.Home_Screen, Android_screens.TVguide_Screen, Android_screens.Program_Details_Screen, Android_screens.LiveTV_Screen {
	public static AppiumDriver<MobileElement> driver;
	public CommonUtils commonUtils;
	public Android_input_properties inputProperties;
	public List<String> displayed_dates;
	public Setting_Page settingPage;
	public Program_Details_Page detailsPage;
	AndroidDriver<MobileElement> androidDriver;

	public TVguide_Page() throws IOException {
		driver = Hooks.getDriver();
		commonUtils = new CommonUtils(driver);
		inputProperties = new Android_input_properties();
		settingPage = new Setting_Page();
		detailsPage = new Program_Details_Page();
	}

	@Given("^User is on the TV guide page$")
	public void user_is_on_tv_guide_page() {
		commonUtils.clickonElement(tvGuide_button);
		commonUtils.waitTillVisibility(tv_guide_program_poster, 20);
		Assert.assertTrue(commonUtils.displayed(tv_guide_program_poster));
	}

	@When("^The user scroll body horizontally$")
	public void user_scroll_boday_horizontally() {
		String beforeScroll_titile = commonUtils.getText(epg_video_title);
		System.out.println("Before horizontal scroll the vidoe title is :" + beforeScroll_titile);
		commonUtils.scrollHorizantal();
		String afterScroll_titile = commonUtils.getText(epg_video_title);
		System.out.println("After horizontal scroll the vidoe title is :" + afterScroll_titile);
		Assert.assertNotEquals(beforeScroll_titile, afterScroll_titile);
	}

	@Then("^User scroll the channel vertically$")
	public void user_scroll_channel_vertically() {
		String beforeScroll_titile = commonUtils.getText(epg_video_title);
		System.out.println("Before vertical scroll the vidoe title is :" + beforeScroll_titile);
		commonUtils.swipeUpOverScreen();
		String afterScroll_titile = commonUtils.getText(epg_video_title);
		System.out.println("After vertical scroll the vidoe title is :" + afterScroll_titile);
		Assert.assertNotEquals(beforeScroll_titile, afterScroll_titile);
	}

	@Given("^The user clicks on the date picker$")
	public void the_user_clicks_on_the_date_picker() {
		commonUtils.clickonElement(date_picker);
		Assert.assertTrue(commonUtils.displayed(date_picker_textview_date));
	}

	@Given("^The user see locked tiles$")
	public void the_user_see_locked_tiles() {
		commonUtils.waitTillVisibility(epg_video_container, 20);
		// Swipe over the home screen to find swimlane with live icons
		int counter = 0;
		String temp = null;
		boolean ongoingProgramFound = false;
		commonUtils.waitTillVisibility(epg_video_imageview_airing, 30);
		if (commonUtils.displayed(epg_live_now_icon))
			commonUtils.swipeUpOverHomeScreen();
		// To verify if the user has swiped till the last swimlane the counter value is
		// provided. For verifying if the last swimlane has reached, after each swipe
		// the program title of the last swimlane is compared with that of the previous
		// swipe. And if the titles are same the counter value is incremented. Since
		// there are swimlanes with same title, this comparison is repeated till counter
		// value reaches 3
		while (counter < 3) {
			List<MobileElement> swimlaneList = commonUtils.findElements(epg_video_imageview_airing);
			String lastSwimlaneProgramTitle;
			try {
				lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 1)
						.findElement(epg_video_title).getText();
			} catch (Exception e) {
				lastSwimlaneProgramTitle = swimlaneList.get(swimlaneList.size() - 2)
						.findElement(program_title_under_swimlane).getText();
			}
			for (MobileElement swimlane : swimlaneList) {
				if (swimlane.findElements(age_icon).size() > 0) {
					swimlane.findElement(age_icon).click();
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
		// Assert.assertTrue(programDetalsPage.user_on_program_details_page());

	}

	@When("^The user unlocks the tiles by entering a pin$")
	public void the_user_unlocks_the_tiles_by_entering_a_pin() {
	}

	@When("^The user validates the date shown in the date picker$")
	public void the_user_validates_the_date_shown_in_the_date_picker() {
		displayed_dates = new ArrayList<String>();
		int counter = 0;
		while (counter < 2) {
			List<MobileElement> previous_day = commonUtils.findElements(date_picker_textview_date);
			for (MobileElement day_value : previous_day) {
				String value = day_value.getText();
				if (value.equalsIgnoreCase("Today")) {
					commonUtils.swipeUpOverScreen();
				}
				displayed_dates.add(value);
			}
			counter++;
		}
		System.out.println("List of previous week value :" + displayed_dates);
	}

	@Then("^The user check previous and the future day is shown in the date picker$")
	public void the_user_check_previous_and_the_future_day_is_shown_in_the_date_picker() {
		if (displayed_dates.contains(commonUtils.previousDay_picker("sixthday"))) {
			System.out.println(commonUtils.previousDay_picker("sixthday"));
		}

		if (displayed_dates.contains(commonUtils.futureDay_picker("seventhday"))) {
			System.out.println(commonUtils.futureDay_picker("seventhday"));
		}
	}

	@When("^The user validates the program for different dates$")
	public void the_user_validates_the_program_for_different_dates() {
		user_select_the_previous_day_program();
		user_select_today_program();
		user_select_future_day_program();
	}

	@And("^User select the previous day program$")
	public void user_select_the_previous_day_program() {
		String text = commonUtils.previousDay_picker("thirdday");
		;
		driver.findElement(By.xpath("//*[contains(@text,'" + text + "')]")).click();
		Assert.assertTrue(commonUtils.displayed(epg_video_container));
		user_check_for_displayed_program_details();
		Assert.assertTrue(commonUtils.displayed(epg_video_airing_details));
		// Code to check replay icon//
//    	if(!commonUtils.displayed(epg_video_replay_icon)) {
//    		commonUtils.clickonElement(epg_video_imageview_airing);
//    		commonUtils.clickonElement(navigate_back);
//    		commonUtils.displayed(epg_video_replay_icon);
//    	}
//    	else {
//    		commonUtils.displayed(epg_video_replay_icon);
//    	}
	}

	@And("^User check for displayed program details$")
	public void user_check_for_displayed_program_details() {
		Assert.assertTrue(commonUtils.displayed(epg_video_imageview_airing));
		Assert.assertTrue(commonUtils.displayed(epg_video_title));
		Assert.assertTrue(commonUtils.displayed(epg_video_broadcast_time));
//    	if(commonUtils.displayed(epg_date_picker_today)) {
//    		Assert.assertTrue(commonUtils.displayed(epg_live_now_icon));
//    	}
//    	else {
//    		Assert.assertTrue(commonUtils.displayed(epg_video_airing_details));
//    	}

	}

	@And("^User select today program$")
	public void user_select_today_program() {
		the_user_clicks_on_the_date_picker();
		commonUtils.clickonElement(epg_date_picker_today);
		user_check_for_displayed_program_details();
		Assert.assertTrue(commonUtils.displayed(epg_live_now_icon));
		commonUtils.clickonElement(epg_live_now_icon);
		Assert.assertTrue(commonUtils.displayed(epg_live_video_container));
		commonUtils.clickonElement(epg_live_play_pause_icon);
		Assert.assertTrue(commonUtils.displayed(epg_live_mute_button));
		Assert.assertTrue(commonUtils.displayed(epg_live_rewind_button));
		commonUtils.clickonElement(navigate_back);
	}

	@And("^User select future day program$")
	public void user_select_future_day_program() {
		the_user_clicks_on_the_date_picker();
		commonUtils.swipeUpOverScreen();
		String text = commonUtils.futureDay_picker("thirdday");
		;
		driver.findElement(By.xpath("//*[contains(@text,'" + text + "')]")).click();
		Assert.assertTrue(commonUtils.displayed(epg_video_container));
		user_check_for_displayed_program_details();
		Assert.assertTrue(commonUtils.displayed(epg_video_airing_details));
	}

	@Given("^User selects non-series live program from TV guide$")
	public void user_selects_non_series_live_program_from_tv_guide() {
		for (int i = 0; i < 30; i++) {
			select_ongoing_program_from_tv_guide();
			if (commonUtils.displayed(ongoing_program_record_button)) {
				if (!detailsPage.verify_if_ongoing_program_is_part_of_series()) {
					break;
				}
			}
			detailsPage.close_program_details_page_to_reach_tvguide();
			select_next_channel_from_tv_guide();
		}
	}

	@And("^User selects ongoing program from TV guide$")
	public void select_ongoing_program_from_tv_guide() {
		commonUtils.clickonElement(tv_guide_live_icon);
		if (!commonUtils.displayed(player_screen))
			settingPage.enter_parental_pin_for_programs();
		Assert.assertTrue(commonUtils.displayed(player_screen));
	}

	public void select_next_channel_from_tv_guide() {
		List<MobileElement> channelList = commonUtils.findElements(channel_cell);
		Assert.assertFalse(channelList.isEmpty());
		for (int i = 1; i < channelList.size() - 1; i++) {
			if (channelList.get(i).findElements(channel_selected_bar).size() > 0) {
				channelList.get(i + 1).findElement(tv_guide_channel_icon).click();
				break;
			}
		}
	}

	@Then("^User selects series live program from TV guide$")
	public void user_selects_series_live_program_from_tvguide() {
		for (int i = 0; i < 30; i++) {
			select_ongoing_program_from_tv_guide();
			if (commonUtils.displayed(ongoing_program_record_button)) {
				if (detailsPage.verify_if_ongoing_program_is_part_of_series())
					break;
			}
			detailsPage.close_program_details_page_to_reach_tvguide();
			select_next_channel_from_tv_guide();
		}
	}

	@Then("^User selects non-playable live program$")
	public void user_selects_non_playable_live_program() {
		boolean nonPlayableItemFound = false;
		search_channel_with_non_playable_programs();
		for (int i = 0; i < 6; i++) {
			if (!commonUtils.displayed(not_playable_icon_in_tvguide)) {
				commonUtils.swipeRightOverElement(commonUtils.findElements(epg_program_tiles).get(2));
				commonUtils.waitTillVisibility(epg_video_title, 10);
				continue;
			}
			select_ongoing_program_from_tv_guide();
			nonPlayableItemFound = true;
			break;
		}
		Assert.assertTrue(nonPlayableItemFound);
	}

	public void user_selects_yesterday_in_tvguide() {
		Assert.assertTrue(commonUtils.displayed(date_field));
		boolean dateSelected = false;
		if (!commonUtils.getText(date_field).equalsIgnoreCase(inputProperties.getYesterday())) {
			commonUtils.clickonElement(toggle_date_button);
			List<MobileElement> dateLIst = commonUtils.findElements(date_list);
			for (MobileElement date : dateLIst) {
				if (date.getText().equalsIgnoreCase(inputProperties.getYesterday())) {
					date.click();
					Assert.assertTrue(commonUtils.getText(date_field).equalsIgnoreCase(inputProperties.getYesterday()));
					dateSelected = true;
					break;
				}
			}
		}
		if (!dateSelected)
			throw new TestException(String.format("Failed to select yesterday from day selector"));
	}

	@And("^User selects non-series replay program from TV guide$")
	public void user_selects_non_series_replay_program_from_tvguide() {
		user_selects_yesterday_in_tvguide();
		boolean programFound = false;
		for (int i = 0; i < 30; i++) {
			List<MobileElement> programList = commonUtils.findElements(epg_video_title);
			for (int j = 0; j < programList.size(); j++) {
				programList = commonUtils.findElements(epg_video_title);
				programList.get(j).click();
				if (detailsPage.is_program_replayable()) {
					if (!detailsPage.verify_if_completed_program_is_part_of_series()) {
						programFound = true;
						break;
					}
				}
				detailsPage.close_program_details_page_to_reach_tvguide();
			}
			if (programFound)
				break;
			select_next_channel_from_tv_guide();
		}
		Assert.assertTrue(programFound);
	}
	
	@And("^User selects series replay program from TV guide$")
	public void user_selects_series_replay_program_from_tvguide() {
		boolean programFound = false;
		for (int i = 0; i < 30; i++) {
			List<MobileElement> programList = commonUtils.findElements(epg_video_title);
			for (int j = 0; j < programList.size(); j++) {
				programList = commonUtils.findElements(epg_video_title);
				programList.get(j).click();
				if (detailsPage.is_program_replayable()) {
					if (detailsPage.verify_if_completed_program_is_part_of_series()) {
						programFound = true;
						break;
					}
				}
				detailsPage.close_program_details_page_to_reach_tvguide();
			}
			if (programFound)
				break;
			select_next_channel_from_tv_guide();
		}
		Assert.assertTrue(programFound);
	}
	
	@When("^User selects tomorrow in TV guide$")
        public void user_selects_tomorrow_in_tvguide() {
    		Assert.assertTrue(commonUtils.displayed(date_field));
    		boolean dateSelected = false;
    		if (!commonUtils.getText(date_field).equalsIgnoreCase(inputProperties.getTomorrow())) {
    		    commonUtils.clickonElement(toggle_date_button);
    		    List<MobileElement> dateLIst = commonUtils.findElements(date_list);
    		    for (MobileElement date : dateLIst) {
    			if (date.getText().equalsIgnoreCase(inputProperties.getTomorrow())) {
    			    date.click();
    			    Assert.assertTrue(commonUtils.getText(date_field).equalsIgnoreCase(inputProperties.getTomorrow()));
    			    dateSelected = true;
    			    break;
    			}
    		    }
    		}
    		if (!dateSelected)
    		    throw new TestException(String.format("Failed to select tomorrow from day selector"));
        }
        
        @And("^User selects non-series upcoming program from TV guide$")
        public void user_selects_non_series_upcoming_program_from_tvguide() {
    		boolean programFound = false;
    		for (int i = 0; i < 30; i++) {
    		    List<MobileElement> programList = commonUtils.findElements(epg_video_title);
    		    for (int j = 0; j < programList.size(); j++) {
    			programList = commonUtils.findElements(epg_video_title);
    			programList.get(j).click();
    			if (!detailsPage.verify_if_upcoming_program_is_part_of_series()) {
    			    programFound = true;
    			    break;
    			}
    			detailsPage.close_program_details_page_to_reach_tvguide();
    		    }
    		    if (programFound)
    			break;
    		    select_next_channel_from_tv_guide();
    		}
    		Assert.assertTrue(programFound);
        }
                
        @And("^User selects series upcoming program from TV guide$")
        public void user_selects_series_upcoming_program_from_tvguide() {
    		boolean programFound = false;
    		for (int i = 0; i < 30; i++) {
    		    List<MobileElement> programList = commonUtils.findElements(epg_video_title);
    		    for (int j = 0; j < programList.size(); j++) {
    			programList = commonUtils.findElements(epg_video_title);
    			programList.get(j).click();
    			if (detailsPage.verify_if_upcoming_program_is_part_of_series()) {
    			    programFound = true;
    			    break;
    			}
    			detailsPage.close_program_details_page_to_reach_tvguide();
    		    }
    		    if (programFound)
    			break;
    		    select_next_channel_from_tv_guide();
    		}
    		Assert.assertTrue(programFound);
        }
        
        @And("^User selects non-playable upcoming program$")
        public void user_selects_non_playable_upcoming_program() {
    		boolean nonPlayableItemFound = false;
    		search_channel_with_non_playable_programs();
    		for (int count = 0; count < 2; count++) {
    		    List<MobileElement> channelList = commonUtils.findElements(channel_cell);
    		    for (int i = 1; i < channelList.size(); i++) {
    			channelList = commonUtils.findElements(channel_cell);
    			if (!commonUtils.displayed(not_playable_icon_in_tvguide)) {
    			    channelList.get(i).click();
    			    commonUtils.waitTillVisibility(epg_video_title, 10);
    			    continue;
    			}
    			commonUtils.clickonElement(not_playable_icon_in_tvguide);
    			nonPlayableItemFound = true;
    			break;
    		    }
    		    if (nonPlayableItemFound)
    			break;
    		    commonUtils.swipeLeftOverElement(channelList.get(channelList.size() - 1));
    		}
    		Assert.assertTrue(nonPlayableItemFound);
        }
        
        @And("^User searches for channel with non playable programs$")
        public void search_channel_with_non_playable_programs() {
        	commonUtils.clickonElement(search_button);
        	commonUtils.sendKey(search_text_field, inputProperties.getChannelWithNonPlayableItem());
        	androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
        	commonUtils.waitTillVisibility(epg_video_title, 15);
        	Assert.assertTrue(commonUtils.displayed(epg_video_title));
        }
        
        @Then("^User records and validate episode of live airing from EPG$")
        public void user_records_and_validate_live_episode_airing_from_EPG() {
        	select_recordable_live_program_from_epg(true);
        	Map<String, String> programDetails = detailsPage.records_series_program_and_validate_updated_details(true);
        	detailsPage.close_program_details_page_to_reach_tvguide();
        	MobileElement program = verify_recording_icon_present_over_program_in_epg(programDetails);
        	program.click();
        	detailsPage.stop_recording_episode();
        	detailsPage.delete_recording();
        }
        
        public void select_recordable_live_program_from_epg(boolean isSeries) {
        	boolean programFound = false;
        	for (int i = 0; i < 15; i++) {
        	    if (isSeries)
        		user_selects_series_live_program_from_tvguide();
        	    else
        		user_selects_non_series_live_program_from_tv_guide();
        	    if (detailsPage.is_program_to_be_recorded()) {
        		programFound = true;
        		break;
        	    }
        	    detailsPage.close_program_details_page_to_reach_tvguide();
        	    select_next_channel_from_tv_guide();
        	}
        	if (!programFound)
        	    throw new TestException(String.format("Failed to find a series program from live TV for recording"));
        }
        
        public MobileElement verify_recording_icon_present_over_program_in_epg(Map<String, String> programDetails) {
    		boolean programFound = false;
    		List<MobileElement> programCellList = commonUtils.findElements(epg_program_tiles);
    		for (MobileElement programCell : programCellList) {
    		    programCellList = commonUtils.findElements(epg_program_tiles);
    		    String title;
    		    try {
    			title = programCell.findElement(epg_video_title).getText();
    		    } catch (Exception e) {
    			continue;
    		    }
    		    if (title.contains(programDetails.get("title"))) {
    			programFound = true;
    			if (programCell.findElements(epg_recording_icon).size() < 1)
    			    throw new TestException(
    				    String.format("Recording icon not displayed under the program tile in EPG"));
    			return programCell.findElement(epg_video_title);
    		    }
    		}
    		if (!programFound)
    		    throw new TestException(String.format("Couldn't find " + programDetails.get("title") + " in the live TV"));
    		return null;
        }
        
        @Then("^User validates series recording of live airing from EPG$")
        public void validate_series_recording_of_live_airing_from_EPG() {
        	select_recordable_live_program_from_epg(true);
        	Map<String, String> programDetails = detailsPage.records_series_program_and_validate_updated_details(false);
        	detailsPage.close_program_details_page_to_reach_tvguide();
        	MobileElement program = verify_recording_icon_present_over_program_in_epg(programDetails);
        	program.click();
        	detailsPage.stop_recording_series(false);
        	detailsPage.delete_recording();
        }
        	
        @Then("^User validates recording_of_live airing not part of series from EPG$")
        public void user_validates_recording_of_live_airing_not_part_of_series_from_EPG() {
        	select_recordable_live_program_from_epg(false);
        	Map<String, String> programDetails = detailsPage.records_non_series_program_and_validate_updated_details(true);
        	detailsPage.close_program_details_page_to_reach_tvguide();
        	MobileElement program = verify_recording_icon_present_over_program_in_epg(programDetails);
        	program.click();
        	detailsPage.stop_recording_non_series_item();
        	detailsPage.delete_recording();
        }

        @Given("^User validates recording of replay airing not part of series from EPG$")
        public void validate_recording_of_replay_airing_not_part_of_series_from_EPG() {
        	user_selects_yesterday_in_tvguide();
        	boolean programFound = false;
        	for (int i = 0; i < 30; i++) {
        	    List<MobileElement> programList = commonUtils.findElements(epg_video_title);
        	    for (int j = 0; j < programList.size(); j++) {
        		programList = commonUtils.findElements(epg_video_title);
        		programList.get(j).click();
        		if (detailsPage.is_program_replayable()) {
        		    if (!detailsPage.is_completed_program_non_series()) {
        			programFound = true;
        			break;
        		    }
        		}
        		detailsPage.close_program_details_page_to_reach_tvguide();
        	    }
        	    if (programFound)
        		break;
        	    select_next_channel_from_tv_guide();
        	}
        	if (!programFound)
        	    throw new TestException(String.format("Couldn't find replayable completed non-series program from EPG"));
        }
        
        @Then("^User validates recording of future airing part of series from EPG$")
        public void validate_recording_of_future_airing_part_of_series_from_EPG() {
        	user_selects_tomorrow_in_tvguide();
        	select_upcoming_recordable_series_program_from_epg();
        	Map<String, String> programDetails = detailsPage.records_series_program_and_validate_updated_details(true);
        	detailsPage.close_program_details_page_to_reach_tvguide();
        	MobileElement program = verify_recording_icon_present_over_program_in_epg(programDetails);
        	program.click();
        	detailsPage.cancel_recording_episode();
        	detailsPage.records_series_program_and_validate_updated_details(false);
        	detailsPage.close_program_details_page_to_reach_tvguide();
        	verify_recording_icon_present_over_program_in_epg(programDetails);
        	program.click();
        	detailsPage.stop_recording_series(false);
        }
        
        @And("^User selects upcoming recordable series program from EPG$")
        public void select_upcoming_recordable_series_program_from_epg() {
        	boolean programFound = false;
        	for (int i = 0; i < 30; i++) {
        	    List<MobileElement> programList = commonUtils.findElements(epg_video_title);
        	    for (int j = 0; j < programList.size(); j++) {
        		programList = commonUtils.findElements(epg_video_title);
        		programList.get(j).click();
        		if (detailsPage.is_program_to_be_recorded()) {
        		    if (detailsPage.verify_if_upcoming_program_is_part_of_series()) {
        			programFound = true;
        			break;
        		    }
        		}
        		detailsPage.close_program_details_page_to_reach_tvguide();
        	    }
        	    if (programFound)
        		break;
        	    select_next_channel_from_tv_guide();
        	}
        	if (!programFound)
        	    throw new TestException(String.format("Couldn't find recordable upcoming series program from EPG"));
        }
}
