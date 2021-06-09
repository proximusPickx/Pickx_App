package iOS_stepdefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;

import config.Hooks;
import iOS_screens.Connection_Number_Screen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class Connection_Number_Page implements Connection_Number_Screen {

    public static AppiumDriver<MobileElement> driver;
    public CommonUtils commonUtils;

    public Connection_Number_Page() throws IOException {
	driver = Hooks.getDriver();
	commonUtils = new CommonUtils(driver);
    }

    public void handle_connection_number_page() {
	if (commonUtils.displayed(continue_button)) {
	    List<MobileElement> account_num_element_list = commonUtils.findElements(connection_number_text);
	    for (WebElement connection_number : account_num_element_list) {
		if (connection_number.getText().equals(commonUtils.getLineNumber()))
		    connection_number.click();
		break;
	    }
	    driver.findElement(continue_button).click();
	}
    }
}
