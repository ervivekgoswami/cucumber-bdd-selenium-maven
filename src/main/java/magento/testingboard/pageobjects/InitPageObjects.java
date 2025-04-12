package magento.testingboard.pageobjects;

import magento.testingboard.base.BaseTestClass;

public class InitPageObjects extends BaseTestClass {

	public static MagentoHomePage mHomePage;
	public static MagentoSignInPage mSignInPage;

	public void initPageObjects() {
		mHomePage = new MagentoHomePage(driver);
		mSignInPage = new MagentoSignInPage(driver);
	}

}
