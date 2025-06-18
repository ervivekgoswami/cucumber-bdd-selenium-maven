package magento.testingboard.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.log4testng.Logger;

import magento.testingboard.base.BaseTestClass;
import magento.testingboard.utilities.WebActions;

public class MagentoSignInPage extends WebActions {

	WebDriver driver;
	public static final Logger logger = Logger.getLogger(MagentoSignInPage.class);
	public MagentoSignInPage(WebDriver driver) {
		this.driver = BaseTestClass.driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "email")
	WebElement userEmail_input;

	@FindBy(id = "pass")
	WebElement userPassword_input;

	@FindBy(css = "[class='page-header'] li[class='authorization-link'] a")
	WebElement signIn_button;

	public void enterUserEmail(String email) {
		logger.info("Enter Email: "+ email);
		userEmail_input.sendKeys(email);
	}

	public void enterUserPassword(String password) {
		logger.info("Enter Password: "+ password);
		userPassword_input.sendKeys(password);
	}

	public void clickSignInButton() {
		logger.info("Click SignIn Button");
		signIn_button.click();
	}

}
