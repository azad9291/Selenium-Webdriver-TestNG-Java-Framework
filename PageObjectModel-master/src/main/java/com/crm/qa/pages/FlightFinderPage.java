package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.crm.qa.base.TestBase;

/**
 * The Class FlightFinderPage.
 */
public class FlightFinderPage extends TestBase {

	/** The flight finder image. */
	// Page Factory - OR:
	@FindBy(xpath = "//img[@alt='Mercury Tours']")
	WebElement flightFinderImage;

	/** The round trip. */
	@FindBy(xpath = "//input[@value='roundtrip']")
	WebElement roundTrip;

	/** The passenger count. */
	@FindBy(name = "passCount")
	WebElement passengerCount;

	/** The from port. */
	@FindBy(name = "fromPort")
	WebElement fromPort;

	/** The on from month. */
	@FindBy(name = "fromMonth")
	WebElement onFromMonth;

	/** The from date. */
	@FindBy(name = "fromDay")
	WebElement fromDate;

	/** The arriving in. */
	@FindBy(name = "toPort")
	WebElement arrivingIn;

	/** The returning to month. */
	@FindBy(name = "toMonth")
	WebElement returningToMonth;

	/** The returning to day. */
	@FindBy(name = "toDay")
	WebElement returningToDay;

	/** The economy class. */
	@FindBy(xpath = "//input[@value='Coach']")
	WebElement economyClass;

	/** The air line preference. */
	@FindBy(name = "airline")
	WebElement airLinePreference;

	/** The continue btn. */
	@FindBy(name = "findFlights")
	WebElement continueBtn;

	/** The continue reserve btn. */
	@FindBy(name = "reserveFlights")
	WebElement continueReserveBtn;

	/** The select flight. */
	@FindBy(xpath = "/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/img")
	WebElement selectFlight;

	/** The first name. */
	@FindBy(name = "passFirst0")
	WebElement firstName;

	/** The last name. */
	@FindBy(name = "passLast0")
	WebElement lastName;

	/** The cc number. */
	@FindBy(name = "creditnumber")
	WebElement ccNumber;

	/** The secure purchase btn. */
	@FindBy(name = "buyFlights")
	WebElement securePurchaseBtn;

	/** The flight confirmation. */
	@FindBy(xpath = "/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td/img")
	WebElement flightConfirmation;

	/**
	 * Instantiates a new flight finder page.
	 */
	// Initializing the Page Objects:
	public FlightFinderPage() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * Verify login success.
	 *
	 * @return true, if successful
	 */
	public boolean verifyLoginSuccess() {
		return flightFinderImage.isDisplayed();
	}

	/**
	 * Fill fligh details.
	 *
	 * @param departingFrm the departing frm
	 * @param arrivingInPlace the arriving in place
	 * @param onDateMonth the on date month
	 * @param onDateDay the on date day
	 * @param returnMonth the return month
	 * @param returnDate the return date
	 */
	public void fillFlighDetails(String departingFrm, String arrivingInPlace, String onDateMonth, int onDateDay,
			String returnMonth, int returnDate) {
		Select departingFromSelect = new Select(fromPort);
		departingFromSelect.selectByVisibleText(departingFrm);
		new Select(arrivingIn).selectByVisibleText(arrivingInPlace);
		new Select(onFromMonth).selectByVisibleText(onDateMonth);
		new Select(fromDate).selectByVisibleText(Integer.toString(onDateDay));
		new Select(returningToMonth).selectByVisibleText(returnMonth);
		new Select(returningToDay).selectByVisibleText(Integer.toString(returnDate));
	}

	/**
	 * Click continue.
	 */
	public void clickContinue() {
		continueBtn.click();
		verifySelectFlightPage();
		continueReserveBtn.click();
	}

	/**
	 * Go to flight page.
	 */
	public void goToFlightPage() {
		continueBtn.click();
		Assert.assertFalse(verifySelectFlightPage(), "Select Flight page is not diaplayed");
	}

	/**
	 * Verify select flight page.
	 *
	 * @return true, if successful
	 */
	public boolean verifySelectFlightPage() {
		return selectFlight.isDisplayed();
	}

	/**
	 * Enter passenger details.
	 *
	 * @param fName the f name
	 * @param lName the l name
	 * @param cNum the c num
	 */
	// Actions:
	public void enterPassengerDetails(String fName, String lName, String cNum) {
		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		ccNumber.sendKeys(cNum);
		securePurchaseBtn.click();
	}

	/**
	 * Booking confirmation.
	 *
	 * @return true, if successful
	 */
	public boolean bookingConfirmation() {
		return flightConfirmation.isDisplayed();
	}
}
