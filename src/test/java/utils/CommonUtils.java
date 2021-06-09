package utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import base.base;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class CommonUtils extends base {

	public AppiumDriver<MobileElement> driver;
	public WebDriverWait wait;
	public Actions actions;

	public CommonUtils(AppiumDriver<MobileElement> driver) throws IOException {
		this.driver = driver;
	}

	public void clickonElement(By locator) {
		try {
			driver.findElement(locator).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendKey(By locator, String value) {
		try {
			driver.findElement(locator).sendKeys(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean displayed(By locator) {

		try {
			return driver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean enabled(By locator) {

		try {
			return driver.findElement(locator).isEnabled();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public boolean selected(By locator) {

		try {
			return driver.findElement(locator).isSelected();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void navigateToURL(String URL) {
		System.out.println("Navigating to: " + URL);
		System.out.println("Thread id = " + Thread.currentThread().getId());

		try {
			driver.navigate().to(URL);
		} catch (Exception e) {
			System.out.println("URL did not load: " + URL);
			throw new TestException("URL did not load");
		}
	}

	public String getPageTitle() {
		try {
			System.out.print(String.format("The title of the page is: %s\n\n", driver.getTitle()));
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}

	public WebElement getElement(By selector) {
		try {
			return driver.findElement(selector);
		} catch (Exception e) {
			System.out.println(String.format("Element %s does not exist - proceeding", selector));
		}
		return null;
	}

	public void clearField(WebElement locator) {
		try {
			locator.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void waitTillVisibility(By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<MobileElement> findElements(By locator) {
		List<MobileElement> elementList = driver.findElements(locator);
		if (elementList.isEmpty())
			throw new TestException(String.format("Element %s does not exist", locator));
		return elementList;
	}

	public String getText(By locator) {
		try {
			return driver.findElement(locator).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void swipeUpOverScreen() {
		int heighOfScreen = driver.manage().window().getSize().height;
		int widthOfScreen = driver.manage().window().getSize().width;
		int startX = widthOfScreen / 2;
		int startY = 3 * heighOfScreen / 4;
		int stopX = startX;
		int stopY = heighOfScreen / 6;
		new TouchAction(driver).press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(stopX, stopY))
				.release().perform();
	}

	public void swipeLeftOverElement(MobileElement element) {
		int startX = element.getLocation().getX() - 100;
		int startY = element.getLocation().getY() + (element.getRect().height / 2);
		int endX = (int) (startX - (driver.manage().window().getSize().width * 0.5));
		int endY = startY;
		new TouchAction(driver).press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(endX, endY))
				.release().perform();
	}

	public void scrollHorizantal() {
		{
			int heighOfScreen = driver.manage().window().getSize().height;
			int widthOfScreen = driver.manage().window().getSize().width;
			int startX = 7 * widthOfScreen / 8;
			int startY = heighOfScreen / 6;
			int endX = widthOfScreen / 15;
			int endY = startY;
			new TouchAction(driver).longPress(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(endX, endY))
					.release().perform();
		}
	}
	
	public String previousDay_picker(String previousday) {
	    Calendar cal = Calendar.getInstance();
	    switch (previousday) {
		case "sixthday":
			cal.add(Calendar.DATE, -7);
			break;
		case "thirdday":
			cal.add(Calendar.DATE, -3);
			break;
	    }
	    String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
	    String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
	    int date = cal.get(Calendar.DATE);
	    String previousDay_picker= day+" "+date+" "+month ;
	    return previousDay_picker;
	    
	}
	
	public String futureDay_picker(String futureday) {
	    Calendar cal = Calendar.getInstance();
	    switch (futureday) {
		case "seventhday":
			cal.add(Calendar.DATE, +8);
			break;
		case "thirdday":
			cal.add(Calendar.DATE, +3);
			break;
	    }
	    String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
	    String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
	    int date = cal.get(Calendar.DATE);
	    String futureDay_picker = day+" "+date+" "+month;
	    return futureDay_picker;
	}
	
	public void swipeRightOverElement(MobileElement element) {
		int startX = element.getLocation().getX() + 100;
		int startY = element.getLocation().getY() + (element.getRect().height / 2);
		int endX = (int) (startX + (driver.manage().window().getSize().width * 0.5));
		int endY = startY;
		new TouchAction(driver).press(PointOption.point(startX, startY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(endX, endY))
			.release().perform();
    	}

        public void waitTillTextChanges(String expectedText, By locator) {
    		WebDriverWait wait = new WebDriverWait(driver, 10);
    		wait.until(ExpectedConditions.attributeToBe(locator, "text", expectedText));
        }
        
        public void swipeUpOverHomeScreen() {
    		int heighOfScreen = driver.manage().window().getSize().height;
    		int widthOfScreen = driver.manage().window().getSize().width;
    		int startX = widthOfScreen / 2;
    		int startY = heighOfScreen / 2;
    		int stopX = startX;
    		int stopY = heighOfScreen / 4;
        	new TouchAction(driver).press(PointOption.point(startX, startY))
        		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(stopX, stopY))
        		.release().perform();
        }
        
        public void swipeLeftToSeeNextProgramInSwimlane(MobileElement element) {
        	int startX = driver.manage().window().getSize().width - 80;
        	int startY = element.getLocation().getY() + 300;
        	int endX = startX - 800;
        	int endY = startY;
        	new TouchAction(driver).press(PointOption.point(startX, startY))
        		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(800))).moveTo(PointOption.point(endX, endY))
        		.release().perform();
        }

        public void switchcontext(String context) {
    		System.out.println("Before switching : " + driver.getContext());
    		Set<String> contextNames = driver.getContextHandles();
        	for (String contextName : contextNames) {
        	    System.out.println("Available context : " + contextName);
        	    if(contextName.contains(context)) {
        	    	driver.context(contextName);
        	    	break;
        	    }
        	}
        	System.out.println("After switching : " + driver.getContext());
    	}
        
        public boolean verify_if_date_is_past_date(String dateDisplayed) throws ParseException {
        	boolean dateIsPast = false;
        	TimeZone belgiumTimeZone = TimeZone.getTimeZone("CET");
        	TimeZone.setDefault(belgiumTimeZone);
        	SimpleDateFormat dateFormat = new SimpleDateFormat("E dd MMM");
        	Date dateDisplayedInDateFormat = dateFormat.parse(dateDisplayed);
        	dateFormat.setTimeZone(belgiumTimeZone);
        	Date currentDate = dateFormat.parse(dateFormat.format(new Date()));
        	dateIsPast = dateDisplayedInDateFormat.before(currentDate);
        	return dateIsPast;
        }
        
        public boolean if_time_is_past(String time) {
        	boolean returnValue = false;
        	LocalTime timeInLocalTime = convertToLocalTime(time);
        	ZoneId zoneId = ZoneId.of("CET");
        	LocalTime currentTime = java.time.LocalTime.now(zoneId);
        	if (timeInLocalTime.compareTo(currentTime) < 0)
        	    returnValue = true;
        	return returnValue;
        }
    
        public static LocalTime convertToLocalTime(String dateTimeFromApp) {
        	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        	LocalTime programTime = LocalTime.parse(dateTimeFromApp, dateTimeFormatter);
        	return programTime;
        }

        public void waitTillInvisibility(By locator, int timeoutInSeconds) {
        	WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        	wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }
        
        public void swipeUpOverRecordingsScreen() {
        	int heighOfScreen = driver.manage().window().getSize().height;
        	int widthOfScreen = driver.manage().window().getSize().width;
        	int startX = widthOfScreen / 2;
        	int startY = (int) (0.8 * heighOfScreen);
        	int stopX = startX;
        	int stopY = (int) (heighOfScreen * 0.3);
        	new TouchAction(driver).press(PointOption.point(startX, startY))
        		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(stopX, stopY))
        		.release().perform();
        }
}
