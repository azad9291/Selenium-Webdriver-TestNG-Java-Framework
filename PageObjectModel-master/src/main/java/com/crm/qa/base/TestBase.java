package com.crm.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

/**
 * The Class TestBase.
 */
public class TestBase  {
	
	/** The driver. */
	public static WebDriver driver;
	
	/** The properties. */
	public static Properties prop;
	
	/** The event firing web driver. */
	public static EventFiringWebDriver e_driver;
	
	/** The event listener. */
	public static WebEventListener eventListener;
	
	/** The test. */
	protected static ExtentTest test;

    /** The root. */
    private static String root = System.getProperty("user.dir");
     	
	/**
	 * Instantiates a new test base.
	 */
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(root + "/src/main/java/com/crm"
					+ "/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tear down.
	 */
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	/**
	 * Initialization of browser drivers.
	 */
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", root +"/drivers/chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("FF")){
			System.setProperty("webdriver.gecko.driver", root +"/drivers/geckodriver.exe");	
			driver = new FirefoxDriver(); 
		}
		
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}	
	
	/**
	 * Take screenshots.
	 *
	 * @return the file
	 */
	public static File takeScreenshots() {
		  TakesScreenshot ts = (TakesScreenshot) driver ;
		  return ts.getScreenshotAs(OutputType.FILE);
	}

	/**
	 * Creates the test.
	 *
	 * @param extent the Extent instance to be used
	 * @param testName the test name
	 * @return the extent test
	 */
	public static ExtentTest createTest(ExtentReports extent, String testName) {
		test = extent.createTest(testName);
		return test;
	}
}
