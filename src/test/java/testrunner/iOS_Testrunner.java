package testrunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


//@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true,
		features={"src/test/java/iOS_feature"},
		glue={"iOS_stepdefinations", "config"},
				tags= "(@Home12 or ~@login)",
				plugin = {
						"summary",
						"pretty",
						"json:target/cucumber-reports/cucumber.json",
						"html:target/cucumber-reports/cucumber-html-report",
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
						"timeline:test-output-thread/"
						}
		
						//tags = {"@Smoke","@Regression"}
		)
public class iOS_Testrunner extends AbstractTestNGCucumberTests {

}

