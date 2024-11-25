package magento.testingboard.hooks;

import java.io.IOException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import magento.testingboard.base.BaseTestClass;

public class Hooks extends BaseTestClass{
	
	@Before
	public void launchApp() throws IOException {
		openBrowser();
	}
	
	@After
	public void closeApp() {
		closeDriver();
	}

}
