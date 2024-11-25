package magento.testingboard.base;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseTestClass {
	public static RemoteWebDriver driver;
	public final static String DIR = System.getProperty("user.dir");
	public final static String propertyFilePath = DIR + "/src/main/resources/config.properties";
	public static Properties props;

	public static Properties loadProperties() throws IOException {
		props = new Properties();
		System.out.println("The File: "+ propertyFilePath);
		FileReader reader = new FileReader(propertyFilePath);
		props.load(reader);
		return props;
	}

	public String getConfigProperty(String propertyKey) throws IOException {
		Properties prop = loadProperties();
		String value = prop.getProperty(propertyKey);
		return value;
	}

	public WebDriver openBrowser() throws IOException {
		String browserName = getConfigProperty("browser");
		switch (browserName) {
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "msedge":
			driver = new EdgeDriver();
			break;
		}
		driver.get(getConfigProperty("URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		return driver;
	}
	
	public void closeDriver() {
		driver.close();
		driver.quit();
	}

}
