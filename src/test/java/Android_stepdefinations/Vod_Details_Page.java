package Android_stepdefinations;

import java.io.IOException;

import org.testng.Assert;

import Android_screens.Vod_Details_Screen;
import config.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import utils.CommonUtils;

public class Vod_Details_Page implements Vod_Details_Screen {

    public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;

    public Vod_Details_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
    }

    @And("^Metadata properly displayed for playable vod item$")
    public void metadata_properly_displayed_for_playable_vod_item() {
	Assert.assertTrue(commonUtils.displayed(close_button));
	Assert.assertTrue(commonUtils.displayed(player_screen));
	Assert.assertTrue(commonUtils.displayed(play_pause_button));
	Assert.assertTrue(commonUtils.displayed(program_title));
	Assert.assertTrue(commonUtils.displayed(program_time));
	Assert.assertTrue(commonUtils.displayed(download_button));
	Assert.assertTrue(commonUtils.displayed(synopsis));
	Assert.assertTrue(commonUtils.displayed(availability));
    }

    @And("^Metadata properly displayed for non-playable vod item$")
    public void metadata_properly_displayed_for_non_playable_vod_item() {
	Assert.assertTrue(commonUtils.displayed(close_button));
	Assert.assertTrue(commonUtils.displayed(player_screen));
	Assert.assertFalse(commonUtils.displayed(play_pause_button));
	Assert.assertTrue(commonUtils.displayed(program_title));
	Assert.assertTrue(commonUtils.displayed(program_time));
	Assert.assertTrue(commonUtils.displayed(download_button));
	Assert.assertTrue(commonUtils.displayed(synopsis));
	Assert.assertTrue(commonUtils.displayed(availability));
	Assert.assertTrue(commonUtils.displayed(not_playable_icon));
    }

}
