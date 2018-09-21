package com.crm.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

public class TestBase implements ITestListener  {
	
	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	protected ITestResult result;
	ExtentHtmlReporter htmlReporter;
    protected ExtentReports extent;
    public ExtentTest test;
    private static String root = System.getProperty("user.dir");
     	
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
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
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

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		test.log(Status.INFO, result.getMethod().getMethodName() + "test is started");
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		  System.out.println("on test success");
		  test.log(Status.PASS, result.getMethod().getMethodName() + "test is passed");
		  TakesScreenshot ts = (TakesScreenshot) driver;
		  File src = ts.getScreenshotAs(OutputType.FILE);
		  try {
			   FileUtils.copyFile(src, new File(root +"/screenshots/success/"+ result.getMethod().getMethodName() + ".png"));
			   ExtentTest file = test.addScreenCaptureFromPath(root +"/screenshots/success/" + result.getMethod().getMethodName() + ".png");
			   test.log(Status.PASS, result.getMethod().getMethodName() + "test passed" + file);
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	 }
	
	 @Override
	 public void onTestFailure(ITestResult result) {
	  System.out.println("on test failure");
	  test.log(Status.FAIL, result.getMethod().getMethodName() + "test is failed");
	  TakesScreenshot ts = (TakesScreenshot) driver;
	  File src = ts.getScreenshotAs(OutputType.FILE);
	  try {
	   FileUtils.copyFile(src, new File(root +"/screenshots/failed/"+ result.getMethod().getMethodName() + ".png"));
	   ExtentTest file = test.addScreenCaptureFromPath(root +"/screenshots/failed/" + result.getMethod().getMethodName() + ".png");
	   test.log(Status.FAIL, result.getMethod().getMethodName() + "test is failed" + file);
	   test.log(Status.FAIL, result.getMethod().getMethodName() + "test is failed" + result.getThrowable().getMessage());
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
		 
	 @Override
	 public void onTestSkipped(ITestResult result) {
	  test.log(Status.SKIP, result.getMethod().getMethodName() + "test is skipped");
	 }
	 
	 @Override
	 public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	  System.out.println("on test sucess within percentage");
	 }
	 
	 
	 @Override
	 public void onFinish(ITestContext context) {
		extent.flush();
	 }
	 
	@Override
	public void onStart(ITestContext context) {
		String dirName = root +"/test-output/"+  "Tests_executed_on_"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/";
		File directory = new File(dirName);
		
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		htmlReporter = new ExtentHtmlReporter(dirName + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + " reports.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
	}
}
