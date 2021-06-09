package Android_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public interface My_Videos_Screen {

    By my_videos_tab_layout = MobileBy.id("be.belgacom.mobile.tveverywhere:id/tablayout_my_videos");
    By my_videos_recordings_option = MobileBy.AccessibilityId("Recordings");
    By my_videos_recordings_playable_tab = MobileBy.id("be.belgacom.mobile.tveverywhere:id/my_videos_filter_playable");
    By my_videos_recordings_recorded_tab = MobileBy.id("be.belgacom.mobile.tveverywhere:id/my_videos_filter_recorded");
    By my_videos_recordings_planned_tab = MobileBy.id("be.belgacom.mobile.tveverywhere:string/filter_planned");
    By my_videos_recordings_programs = MobileBy.xpath(
	    "//*[@resource-id='be.belgacom.mobile.tveverywhere:id/recyclerview_recordings']/android.view.ViewGroup");
    By my_videos_recordings_program_poster = MobileBy
	    .id("be.belgacom.mobile.tveverywhere:id/imageview_recording_thumbnail");
    By my_videos_recordings_program_channel_icon = MobileBy
	    .id("be.belgacom.mobile.tveverywhere:id/imageview_logo_small");
    By my_videos_recordings_program_title = MobileBy.id("be.belgacom.mobile.tveverywhere:id/textview_recording_title");
    By my_videos_recordings_program_other_option = MobileBy
	    .id("be.belgacom.mobile.tveverywhere:id/button_recordings_other_options");
    By my_videos_recordings_program_description = MobileBy
	    .id("be.belgacom.mobile.tveverywhere:id/textview_recorded_description");
    By my_videos_recordings_program_recorded_icon = MobileBy
	    .id("be.belgacom.mobile.tveverywhere:id/imageview_icon_recording");
    By my_videos_continue_watching_option = MobileBy.AccessibilityId("Continue watching");

}
