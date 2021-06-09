package iOS_screens;

import org.openqa.selenium.By;


import io.appium.java_client.MobileBy;


public interface Home_Screen {

	By home_button= MobileBy.AccessibilityId("home_tab_bar_item");
	By liveTV_button= MobileBy.AccessibilityId("livetv_tab_bar_item");
	By tvGuide_button= MobileBy.AccessibilityId("tvguide_tab_bar_item");
	By Recordings= MobileBy.AccessibilityId("recordings_tab_bar_item");
	By MyVideos=MobileBy.AccessibilityId("myvideos_tab_bar_item");
	By hero_banner_container = MobileBy.AccessibilityId("home_view_controller_hero_banner");
	By home_category_container = MobileBy.AccessibilityId("home_view_controller_categories");
	By hero_banner_replay_icon = MobileBy.AccessibilityId("replay");
    By hero_banner_channel_logo = By.xpath("//XCUIElementTypeCell[@name=\"home_view_controller_hero_banner\"]/XCUIElementTypeImage");
   // By textview_hero_title = By.id("be.belgacom.mobile.tveverywhere:id/textview_swimlane_title");
   // By textview_hero_metadata = By.id("be.belgacom.mobile.tveverywhere:id/textview_swimlane_metadata");
    By hero_banner_watch_button = By.xpath("//XCUIElementTypeStaticText[@name=\"Watch\"]");
	By HomeScreen_layout = By.xpath("//XCUIElementTypeApplication[@name=\"Proximus Pickx\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable");
    By home_categories = By.id("be.belgacom.mobile.tveverywhere:id/button_category");  
    By swimlane_container = By.xpath("home_view_controller_swimlane_id_<PxTV_Models_SwimLanes_SwimlaneViewModel: 0x282fe6100>");

}
