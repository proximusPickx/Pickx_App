package Android_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;


public interface Login_Screen {

	By user_name_tab = MobileBy.AccessibilityId("userName");
	By password_tab= MobileBy.AccessibilityId("userPwd");
	By continue_button = By.id("be.belgacom.mobile.tveverywhere:id/button_continue");
	By user = By.xpath("//*[@text='Email']");
	By user_tab1 = By.xpath("//*[@name='User Name']");
	By pwd_tab1 = By.xpath("//*[@name='Password']");
	By user_tab = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View[2]/android.widget.EditText");
	By pwd_tab = By.xpath("	\r\n" + 
			"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View[2]/android.view.View[3]/android.widget.EditText");
	By pwd = By.xpath("//*[@text='Password']");
	By con_button = By.xpath("//*[@text='Continue']");
	By error_message = By.xpath("//*[@text='The login is invalid.']");
	
	//*[@text='The login is invalid.']
	
}
