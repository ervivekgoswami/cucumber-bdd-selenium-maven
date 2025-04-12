package magento.testingboard.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import magento.testingboard.base.BaseTestClass;
import magento.testingboard.utilities.WebActions;

public class MagentoHomePage extends WebActions {

	WebDriver driver;
	public MagentoHomePage(WebDriver driver) {
		this.driver = BaseTestClass.driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".page-header ul[class='header links'] li a[href*='customer/account']")
	WebElement headers_link;

	@FindBy(linkText = "Create an Account")
	WebElement creatAccount_link;
	
	@FindBy(css = "[class='panel header'] li a")
	List<WebElement> header_links;
	
	@FindBy(id = "firstname")
	WebElement firstName_input;
	
	@FindBy(name = "lastname")
	WebElement lastName_input;
	
	@FindBy(name = "email")
	WebElement email_input;
	
	@FindBy(id = "password")
	WebElement password_input;
	
	@FindBy(id = "password-confirmation")
	WebElement rePassword_input;
	
	@FindBy(css = "button[class='action submit primary']")
	WebElement createAccount_button;
	
	//button[class='action submit primary']
	@FindBy(css = "li[class='greet welcome'] span")
	WebElement welcome_text;
	
	@FindBy(css = "button[class='action switch']")
	WebElement userProfileOption_button;
	
	
	@FindBy(css = "[class=\"customer-menu\"] li a")
	List<WebElement> cutomerMenuOptions_link;

	public boolean verifyCreatAccountLinkDisplayed() {
		waitForElement(creatAccount_link, 10);
		return creatAccount_link.isDisplayed();
	}
	
	public void clickOnPageHeaderLinks(String linkText) throws Exception {
		clickOnElementWithText(linkText, header_links);
	}
	
	
	
	public void clickOnCreatAccountLink() {
		creatAccount_link.click();
	}
	
	public void clickOnSignInLink() {
		creatAccount_link.click();
	}


	public void enterFirstName(String fName) {
		firstName_input.sendKeys(fName);
	}

	public void enterLastName(String lName) {
		lastName_input.sendKeys(lName);
	}

	public void enterEmail(String email) {
		email_input.sendKeys(email);
	}

	public void enterPassword(String pass) {
		password_input.sendKeys(pass);
	}
	
	public void reEnterPassword(String rePass) {
		rePassword_input.sendKeys(rePass);
	}
	
	public void clickCreateAccountButton() {
		createAccount_button.click();
	}
	
	public String getWelcomeText() throws InterruptedException {
		waitForElement(welcome_text, 50);
		Thread.sleep(2000);
		String welcomeText = welcome_text.getText();
		return welcomeText;
	}
	
	public void selectCustomerMenuOption(String menuOption) throws Exception {
		userProfileOption_button.click();
		clickOnElementWithText(menuOption, cutomerMenuOptions_link);
	}

}
