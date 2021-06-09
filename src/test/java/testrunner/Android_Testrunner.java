package testrunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


//@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true,
		features={"src/test/java/Android_features"},
		glue={"Android_stepdefinations", "config"},
		tags= "(@Regression or @Home)",
				plugin = {
						"summary",
						"pretty",
						"json:target/cucumber-reports/cucumber.json",
						"html:target/cucumber-reports/cucumber-html-report",
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
						"timeline:test-output-thread/"
						}				
		)
public class Android_Testrunner extends AbstractTestNGCucumberTests {

}

