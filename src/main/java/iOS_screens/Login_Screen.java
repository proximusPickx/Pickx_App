package iOS_screens;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;


public interface Login_Screen {

	By user_name_tab = MobileBy.xpath("//XCUIElementTypeOther[@name='Email']");
	By password_tab = MobileBy.xpath("//XCUIElementTypeOther[@name='Password']");
	By user_tab1 = MobileBy.xpath("//XCUIElementTypeOther[@name=\"User Name\"]");
	By pwd_tab1 = MobileBy.xpath("//XCUIElementTypeOther[@name=\"ForgeRock Access Management\"]/XCUIElementTypeOther[1]/XCUIElementTypeOther[5]/XCUIElementTypeSecureTextField");
	By user_tab = MobileBy.xpath("//XCUIElementTypeOther[@name='Proximus Login']/XCUIElementTypeOther[3]/XCUIElementTypeTextField");
	By pwd_tab = MobileBy.xpath("//XCUIElementTypeOther[@name='Proximus Login']/XCUIElementTypeOther[5]/XCUIElementTypeSecureTextField");
	By con_button = MobileBy.AccessibilityId("Continue");
	By con_button1 = MobileBy.AccessibilityId("Log in");
	By error_message = MobileBy.AccessibilityId("The login is invalid.");
	//By user_name = By.xpath("//*[@value='User Name']");
	By username1 = MobileBy.xpath("//XCUIElementTypeOther[@name=\"ForgeRock Access Management\"]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeTextField");
	By password1 = MobileBy.xpath("//XCUIElementTypeOther[@name=\"ForgeRock Access Management\"]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeSecureTextField");		
	//By password = By.xpath("//*[@value='Password']");
	By confirm = MobileBy.AccessibilityId("Confirm");
	
}
