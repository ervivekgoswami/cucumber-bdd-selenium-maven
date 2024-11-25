package magento.testingboard.testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		// Rerun failed tests from rerun.txt file
		features = { "src/test/resources/features" }, glue = { "magento.testingboard.stepsdefinitions",
				"magento.testingboard.hooks" }, plugin = { "pretty","json:target/cucumber-reports/Cucumber.json",
					    "html:target/cucumber-reports/Cucumber.html", })

public class TestRunner {

}
