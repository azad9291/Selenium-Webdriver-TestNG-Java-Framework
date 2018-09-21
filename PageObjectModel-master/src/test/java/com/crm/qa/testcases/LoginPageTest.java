package com.crm.qa.testcases;

import java.util.List;

import org.eclipse.jetty.util.log.Log;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.crm.qa.base.TestBase;
import com.crm.qa.models.LoginTestModel;
import com.crm.qa.pages.FlightFinderPage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class LoginPageTest extends TestBase{
	LoginPage loginPage;
	FlightFinderPage flighFinderPage;
	String sheetName = "FlighDetails";
	private Object[][] data;
	
	public LoginPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPage();	
		flighFinderPage = new FlightFinderPage();
	}
	
	//@Test(priority=1, testName="Verify Login")
	public void loginPageTitleTest(){
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue( flighFinderPage.verifyLoginSuccess(), "Unable to login");
	}
	
	//@Test(priority=2, testName="IncorrectPasswordlogintest")
	public void loginTestIncorrectPwd(){
		loginPage.login(prop.getProperty("username"), prop.getProperty("incorrectPwd"));
		Assert.assertTrue(loginPage.verifyLogOnPage(), "System allows unauthorized users");
	}
	
	@DataProvider
	public Object[][] getCRMTestData(){
		if(data == null) {
			data = TestUtil.getTestData(sheetName, LoginTestModel.class);
		}
		
		return data;
	}
	
	@DataProvider
	public Object[][] getMismatchData() {
		if(data == null) {
			data = TestUtil.getTestData(sheetName, LoginTestModel.class);
		}
		
		return new Object[][] {{data[2][0]}};
	}
	
	@Test(priority=3, dataProvider="getCRMTestData", testName="Flight Booking")
	public void validateCreateNewContact(LoginTestModel model) {//String departingFrm, String arrivingIn, String onDateMonth, String onDateDay,String returnMonth, String returnDate,String fName, String lName, String cNum){
		loginPageTitleTest();
		flighFinderPage.fillFlighDetails(model.departingFrm, model.arrivingIn, model.onDateMonth, model.onDateDay, model.returnMonth, model.returnDate);
		flighFinderPage.clickContinue();
		flighFinderPage.enterPassengerDetails(model.fname, model.lname, model.cNum);
	}
	
	@Test(priority=4, dataProvider="getMismatchData", testName="Flight Booking Return Date before Departure date")
	public void returnDateMismatch(LoginTestModel model) {
		loginPageTitleTest();
		flighFinderPage.fillFlighDetails(model.departingFrm, model.arrivingIn, model.onDateMonth, model.onDateDay, model.returnMonth, model.returnDate);
		flighFinderPage.goToFlightPage();
	}
}
