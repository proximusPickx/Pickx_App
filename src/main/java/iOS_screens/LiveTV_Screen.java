package iOS_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public interface LiveTV_Screen {
	By liveTV_skeleton = By.xpath(
			"//XCUIElementTypeApplication[@name=\"Proximus Pickx\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView");
	By category_button_container = MobileBy.xpath(
			"//XCUIElementTypeApplication[@name=\"Proximus Pickx\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]");
	By livetv_navigationbar = MobileBy.id("livetv_navigationbar");
	By live_tv_channel_icon = By.xpath("(//XCUIElementTypeImage[@name=\"livetv_program_channel_logo\"])[1]");
	By live_page_title = MobileBy.AccessibilityId("Live TV");
	By live_fullscreen = MobileBy.AccessibilityId("player fullscreen");
	By live_loading_indicator = MobileBy.AccessibilityId("In progress");
	By goto_live_icon = By.xpath("//XCUIElementTypeButton[@name=\"LIVE\"]");
	By live_play_button = MobileBy.AccessibilityId("player play");
	By live_pause_button = MobileBy.AccessibilityId("player pause");
	By live_tv_program_title = MobileBy.AccessibilityId("title_in_program_details_page");
	By live_tv_channel_logo = MobileBy.AccessibilityId("channel_logo_in_program_details_page");
	By live_tv_metadata = MobileBy.AccessibilityId("metadata_icons_in_program_details_page");
	By live_tv_replay_id = MobileBy.AccessibilityId("replay");
	By live_tv_HD_id = MobileBy.AccessibilityId("HD");
	By live_tv_airing_time_in_program_details_page = MobileBy.AccessibilityId("airing_time_in_program_details_page");
	By live_tv_record = MobileBy.AccessibilityId("record_button_in_program_details_page");
	By Live_program_description = MobileBy.AccessibilityId("synopsis_in_program_details_page");
	By live_tv_player_close = MobileBy.AccessibilityId("player close");
	By live_now = By.xpath("(//XCUIElementTypeStaticText[@name=\"Now\"])[2]");
	By Now_on_TV = By.xpath("(//XCUIElementTypeStaticText[@name=\"regular_swimlane_cell_title\"])[1]");

}
