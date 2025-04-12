package magento.testingboard.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTestClass {

	public static WebDriver driver;
	public final static String DIR = System.getProperty("user.dir");
	public final static String propertyFilePath = DIR + "/src/main/resources/config.properties";
	public static Properties props;
	public static final String DRIVER_PATH = DIR + "/drivers/";

	public static Properties loadProperties() {
		props = new Properties();
		System.out.println("The File: " + propertyFilePath);
		FileReader reader = null;
		try {
			reader = new FileReader(propertyFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			props.load(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}

	public static String getConfigProperty(String propertyKey) {
		Properties prop = loadProperties();
		String value = prop.getProperty(propertyKey);
		return value;
	}

	public static WebDriver createBrowserDriver() {
		String browserName = getConfigProperty("browser");
		switch (browserName) {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver.exe");
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", DRIVER_PATH + "geckodriver.exe");
			FirefoxOptions fire = new FirefoxOptions();
			driver = new FirefoxDriver(fire);
			break;
		default:
			System.out.println("Not a valid browser.");
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
