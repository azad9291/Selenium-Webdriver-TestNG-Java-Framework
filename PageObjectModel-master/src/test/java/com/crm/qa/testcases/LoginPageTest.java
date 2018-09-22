package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.crm.qa.base.TestBase;
import com.crm.qa.models.LoginTestModel;
import com.crm.qa.pages.FlightFinderPage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

/**
 * The Class LoginPageTest.
 */
public class LoginPageTest extends TestBase {

	/** The login page. */
	LoginPage loginPage;

	/** The fligh finder page. */
	FlightFinderPage flighFinderPage;

	/** The sheet name. */
	String sheetName = "FlighDetails";

	/** The data. */
	private Object[][] data;

	/**
	 * Instantiates a new login page test.
	 */
	public LoginPageTest() {
		super();
	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
		flighFinderPage = new FlightFinderPage();
	}

	/**
	 * Login page title test.
	 */
	@Test(priority = 1, testName = "Verify Login")
	public void loginPageTitleTest() {
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		test.log(Status.INFO, "Login Details entered");
		Assert.assertTrue(flighFinderPage.verifyLoginSuccess(), "Unable to login");
	}

	/**
	 * Login test incorrect pwd.
	 */
	@Test(priority = 2, testName = "IncorrectPasswordlogintest")
	public void loginTestIncorrectPwd() {
		loginPage.login(prop.getProperty("username"), prop.getProperty("incorrectPwd"));
		Assert.assertTrue(loginPage.verifyLogOnPage(), "System allows unauthorized users");
	}

	/**
	 * Gets the CRM test data.
	 *
	 * @return the CRM test data
	 */
	@DataProvider
	public Object[][] getCRMTestData() {
		if (data == null) {
			data = TestUtil.getTestData(sheetName, LoginTestModel.class);
		}

		return data;
	}

	/**
	 * Gets the mismatch data.
	 *
	 * @return the mismatch data
	 */
	@DataProvider
	public Object[][] getMismatchData() {
		if (data == null) {
			data = TestUtil.getTestData(sheetName, LoginTestModel.class);
		}

		return new Object[][] { { data[2][0] } };
	}

	/**
	 * Login and make flight bookings.
	 *
	 * @param model
	 *            the model
	 */
	@Test(priority = 3, dataProvider = "getCRMTestData", testName = "Flight Booking")
	public void loginAndMakeFlightBookings(LoginTestModel model) {
		loginPageTitleTest();
		flighFinderPage.fillFlighDetails(model.departingFrm, model.arrivingIn, model.onDateMonth, model.onDateDay,
				model.returnMonth, model.returnDate);
		flighFinderPage.clickContinue();
		flighFinderPage.enterPassengerDetails(model.fname, model.lname, model.cNum);
		
	}

	/**
	 * Return date lesser than departure date.
	 *
	 * @param model
	 *            the model
	 */
	@Test(priority = 4, dataProvider = "getMismatchData", testName = "Flight Booking Return Date before Departure date")
	public void returnDateLesserThanDepartureDate(LoginTestModel model) {
		loginPageTitleTest();
		flighFinderPage.fillFlighDetails(model.departingFrm, model.arrivingIn, model.onDateMonth, model.onDateDay,
				model.returnMonth, model.returnDate);
		flighFinderPage.goToFlightPage();
	}
}
