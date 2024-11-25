package magento.testingboard.stepsdefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import magento.testingboard.base.BaseTestClass;
import magento.testingboard.pageobjects.MagentoHomePage;

public class CreateAccountSteps extends BaseTestClass {

	MagentoHomePage homePage;

	public CreateAccountSteps() {
		homePage = new MagentoHomePage(driver);
	}

	@Given("User launches magento testing board application")
	public void user_launches_magento_testing_board_application() throws IOException {
//		openBrowser();
	}

	@When("User clicks on create new account link on homepage")
	public void user_clicks_on_create_new_account_link_on_homepage() {
		Assert.assertEquals(homePage.verifyCreatAccountLinkDisplayed(), true);
		homePage.clickOnCreatAccountLink();
	}

	@When("User enters personal information as {string} and {string}")
	public void user_enters_personal_information_as_and(String fName, String lName) {
		homePage.enterFirstName(fName);
		homePage.enterLastName(lName);
	}

	@When("User enters Sign-in Information as {string} and {string}")
	public void user_enters_sign_in_information_as_and(String email, String password) {
		homePage.enterEmail(email);
		homePage.enterPassword(password);
		homePage.reEnterPassword(password);
	}

	@When("User clicks on creat account button")
	public void user_clicks_on_creat_account_button() throws InterruptedException {
		homePage.clickCreateAccountButton();

	}

	@Then("Verify user is logged into application successfully")
	public void verify_user_is_logged_into_application_successfully() throws InterruptedException {
		String welcomeUsertext = homePage.getWelcomeText();
		System.out.println("Welcome Text: "+ welcomeUsertext);
		Assert.assertEquals(welcomeUsertext.contains("Welcome"), true);
	}

	@When("User sign out from account")
	public void user_sign_out_from_account() throws Exception {
		homePage.selectCustomerMenuOption("Sign Out");
	}

	@Then("User is landed on homepage")
	public void user_is_landed_on_homepage() {
		Assert.assertEquals(homePage.verifyCreatAccountLinkDisplayed(), true);
	}
	
	@When("User clicks on sign in link on homepage")
	public void user_clicks_on_sign_in_link_on_homepage() throws Exception {
		homePage.clickOnPageHeaderLinks("Sign In");
	}

}
