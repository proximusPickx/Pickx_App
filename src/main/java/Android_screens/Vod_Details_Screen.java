package Android_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public interface Vod_Details_Screen {

    By close_button = MobileBy
	    .xpath("//*[@resource-id='be.belgacom.mobile.tveverywhere:id/toolbar']/android.widget.ImageButton");
    By player_screen = MobileBy.id("be.belgacom.mobile.tveverywhere:id/meta_thumbnail");
    By play_pause_button = MobileBy.id("be.belgacom.mobile.tveverywhere:id/button_play_pause_toggle");
    By program_title = MobileBy.id("be.belgacom.mobile.tveverywhere:id/vod_meta_title");
    By program_time = MobileBy.id("be.belgacom.mobile.tveverywhere:id/vod_meta_info");
    By download_button = MobileBy.id("be.belgacom.mobile.tveverywhere:id/vod_meta_dtgbutton");
    By synopsis = MobileBy.id("be.belgacom.mobile.tveverywhere:id/vod_meta_synopsis");
    By availability = MobileBy.id("be.belgacom.mobile.tveverywhere:id/vod_meta_availability");
    By not_playable_icon = MobileBy.id("be.belgacom.mobile.tveverywhere:id/imageview_icon_not_playable");

}
