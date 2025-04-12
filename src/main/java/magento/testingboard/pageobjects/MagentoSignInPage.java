package magento.testingboard.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import magento.testingboard.base.BaseTestClass;
import magento.testingboard.utilities.WebActions;

public class MagentoSignInPage extends WebActions {

	WebDriver driver;

	public MagentoSignInPage(WebDriver driver) {
		this.driver = BaseTestClass.driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "email")
	WebElement userEmail_input;

	@FindBy(id = "pass")
	WebElement userPassword_input;

	@FindBy(id = "send2")
	WebElement signIn_button;

	public void enterUserEmail(String email) {
		userEmail_input.sendKeys(email);
	}

	public void enterUserPassword(String password) {
		userPassword_input.sendKeys(password);
	}

	public void clickSignInButton() {
		signIn_button.click();
	}

}
