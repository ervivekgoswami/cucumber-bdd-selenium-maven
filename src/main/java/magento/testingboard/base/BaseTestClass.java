package magento.testingboard.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTestClass {

	/** Thread local entities */
	public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
	public static ThreadLocal<HashMap<String, String>> threadTestData = new ThreadLocal<>();
	protected static ThreadLocal<HashMap<String, String>> threadValidateData = new ThreadLocal<>();

	public static WebDriver driver;
	public final static String DIR = System.getProperty("user.dir");
	public final static String propertyFilePath = DIR + "/src/main/resources/config.properties";
	public static Properties props;
	public static final String DRIVER_PATH = DIR + "/drivers/";

	public static Properties loadProperties() {
		props = new Properties();
		FileReader reader = null;
		try {
			reader = new FileReader(propertyFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	public static String getConfigProperty(String propertyKey) {
		Properties prop = loadProperties();
		String value = prop.getProperty(propertyKey);
		return value;
	}

	/**
	 * Function to return WebDriver object for the current thread
	 *
	 * @return
	 */
	protected WebDriver getDriver() {
		return threadDriver.get();
	}

	/**
	 * Function to fetch method name of the function, from where this is invoked
	 *
	 * @return MethodName
	 * @author Vivek K
	 */
	protected String getMethodName() {
		return new Throwable().getStackTrace()[1].getMethodName();
	}

	/**
	 * Function to fetch method name of the function, from where this is invoked
	 *
	 * @return ClassName
	 * @author Vivek K
	 */
	protected String getClassName() {
		return new Throwable().getStackTrace()[1].getFileName().replace(".java", "");
	}

	public static void createBrowserDriver() {
		String browserName = getConfigProperty("browser");
		switch (browserName) {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver.exe");
			driver = new ChromeDriver(options);
			threadDriver.set(driver);
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", DRIVER_PATH + "geckodriver.exe");
			FirefoxOptions fire = new FirefoxOptions();
			driver = new FirefoxDriver(fire);
			threadDriver.set(driver);
			break;
		default:
			System.out.println("Not a valid browser.");
		}
		driver.get(getConfigProperty("URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	public void closeDriver() {
		driver.close();
		driver.quit();
	}

}
