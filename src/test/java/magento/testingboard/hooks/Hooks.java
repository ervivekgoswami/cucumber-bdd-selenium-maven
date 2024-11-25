package magento.testingboard.hooks;

import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import magento.testingboard.base.BaseTestClass;

public class Hooks extends BaseTestClass {

	@Before
	public void launchApp() throws IOException {
		openBrowser();
	}

	@After(order = 1)
	public void takeScraenshotOnFailure(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			final byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
		}
	}

	@After(order = 0)
	public void closeApp() {
		closeDriver();
	}

}
