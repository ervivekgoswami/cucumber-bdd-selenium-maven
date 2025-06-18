package magento.testingboard.stepsdefinitions;

import io.cucumber.java.en.When;
import magento.testingboard.pageobjects.InitPageObjects;

public class SignInSteps extends InitPageObjects {
	
	
	@When("User enters credentials {string} and {string}")
	public void user_enters_credentials_and(String email, String password) {
		mSignInPage.enterUserEmail(email);
		mSignInPage.enterUserPassword(password);
	}

	@When("User enters login credentials")
	public void user_enters_credentials() {
		mSignInPage.enterUserEmail(threadTestData.get().get("Email"));
		mSignInPage.enterUserPassword(threadTestData.get().get("Password"));
	}

	@When("User clicks on Sign-In button")
	public void user_clicks_on_sign_in_button() {
		mSignInPage.clickSignInButton();
	}

}
