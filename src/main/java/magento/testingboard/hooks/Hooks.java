package magento.testingboard.hooks;

import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import magento.testingboard.pageobjects.InitPageObjects;

public class Hooks extends InitPageObjects {

	@Before(order = 0)
	public void launchApp() throws IOException {
		createBrowserDriver();
	}

	@Before(order = 1)
	public void initPageElements() {
		initPageObjects();
	}

	@After(order = 1)
	public void takeScraenshotOnFailure(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			final byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Screenshot_" + scenario.getId());
		}
	}

	@After(order = 0)
	public void closeApp() {
		closeDriver();
	}

}
