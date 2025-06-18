package magento.testingboard.utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import magento.testingboard.base.BaseTestClass;

public class WebActions extends BaseTestClass {
	
	public static final Logger logger = Logger.getLogger(WebActions.class);
	
	public void clickOnElementWithText(String elementText, List<WebElement> elementList) throws Exception {
		int noOfElements = elementList.size();
		System.out.println("Element With Text " + elementText + " are " + noOfElements);
		int textCollector = 0;
		for (int i = 0; i < noOfElements; i++) {
			String currentElementText = elementList.get(i).getText().trim();
//			System.out.println("Element Text found" + currentElementText + " at index " + i);
			logger.info("Element Text found" + currentElementText + " at index " + i);
			if (currentElementText.equals(elementText)) {
				logger.info("Cicking on Element With Text " + elementText + " at index " + noOfElements);
//				System.out.println("Cicking on Element With Text " + elementText + " at index " + noOfElements);
				elementList.get(i).click();
				textCollector++;
				break;
			}
		}
		if (textCollector == 0) {
//			throw new Exception("Element with text: " + elementText + " not found");
			logger.error("Element with text: " + elementText + " not found");
		}
	}

	public void waitForElement(WebElement element, int timeUnit) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeUnit));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementText(WebElement element, String text, int timeUnit) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeUnit));
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

}
