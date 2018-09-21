package com.crm.qa.pages;

import static org.junit.Assert.assertFalse;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.crm.qa.base.TestBase;

public class FlightFinderPage extends TestBase{
	
	//Page Factory - OR:
	@FindBy(xpath="//img[@alt='Mercury Tours']")
	WebElement flightFinderImage;
	
	@FindBy(xpath="//input[@value='roundtrip']")
	WebElement roundTrip;
	
	@FindBy(name="passCount")
	WebElement passengerCount;
	
	@FindBy(name="fromPort")
	WebElement fromPort;
	
	@FindBy(name="fromMonth")
	WebElement onFromMonth;
	
	@FindBy(name="fromDay")
	WebElement fromDate;
	
	@FindBy(name="toPort")
	WebElement arrivingIn;
	
	@FindBy(name="toMonth")
	WebElement returningToMonth;
	
	@FindBy(name="toDay")
	WebElement returningToDay;
	
	@FindBy(xpath="//input[@value='Coach']")
	WebElement economyClass;
	
	@FindBy(name="airline")
	WebElement airLinePreference;
	
	@FindBy(name="findFlights")
	WebElement continueBtn;
	
	@FindBy(name="reserveFlights")
	WebElement continueReserveBtn;
	
	@FindBy(xpath="/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/img")
	WebElement selectFlight;
	
	@FindBy(name="passFirst0")
	WebElement firstName;
	
	@FindBy(name="passLast0")
	WebElement lastName;
	
	@FindBy(name="creditnumber")
	WebElement ccNumber;
	
	@FindBy(name="buyFlights")
	WebElement securePurchaseBtn;
	
	@FindBy(xpath="/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td/img")
	WebElement flightConfirmation;
	
	
		

	//Initializing the Page Objects:
	public FlightFinderPage(){
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyLoginSuccess() {
		return flightFinderImage.isDisplayed();
	}
	
	
	
	public void fillFlighDetails(String departingFrm, String arrivingInPlace , String onDateMonth, int onDateDay,String returnMonth, int returnDate) {
		Select departingFromSelect = new Select(fromPort);
		departingFromSelect.selectByVisibleText(departingFrm);
		new Select(arrivingIn).selectByVisibleText(arrivingInPlace);
		new Select(onFromMonth).selectByVisibleText(onDateMonth);
		new Select(fromDate).selectByVisibleText(Integer.toString(onDateDay));
		new Select(returningToMonth).selectByVisibleText(returnMonth);
		new Select(returningToDay).selectByVisibleText(Integer.toString(returnDate));
	}
	
	
	
	public void clickContinue() {
		continueBtn.click();
		verifySelectFlightPage();
		continueReserveBtn.click();
	}
	
	public void goToFlightPage() {
		continueBtn.click();
		Assert.assertFalse(verifySelectFlightPage(), "Select Flight page is not diaplayed");
	}
	
	public boolean verifySelectFlightPage() {
		return selectFlight.isDisplayed();
	}
	
	//Actions:
		public void enterPassengerDetails(String fName, String lName, String cNum) {
			firstName.sendKeys(fName);
			lastName.sendKeys(lName);
			ccNumber.sendKeys(cNum);
			securePurchaseBtn.click();
		}
		
		public boolean bookingConfirmation() {
			return flightConfirmation.isDisplayed();
		}
}
