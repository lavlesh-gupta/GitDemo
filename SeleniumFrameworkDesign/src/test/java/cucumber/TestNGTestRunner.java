package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//cucumber->  TestNG, junit
//by default cucumber gives results in encoded format, hence we have to use monochrome=true to get reports in human readable format
//For reporting which format we want we can mention in plugin and add there the path of html report.
@CucumberOptions(features="src/test/java/cucumber",glue="rahulshettyacademy.stepDefinitions",
monochrome=true, tags = "@Regression", plugin= {"html:target/cucumber.html"})
//in built cucumber tags doesn't execute testng codes hence we extend one class which is AbstractTestNGCucumberTests
//If we run junit code then we don't have to extend any class as cucumber runs all junit classes by default
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

	
}
