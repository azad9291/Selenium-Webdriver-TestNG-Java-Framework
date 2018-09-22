package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

/**
 * The Class LoginPage.
 */
public class LoginPage extends TestBase {

	/** The username. */
	// Page Factory - OR:
	@FindBy(name = "userName")
	WebElement username;

	/** The password. */
	@FindBy(name = "password")
	WebElement password;

	/** The login btn. */
	@FindBy(name = "login")
	WebElement loginBtn;

	/** The sign on logo. */
	@FindBy(xpath = "/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/img")
	WebElement signOnLogo;

	/**
	 * Instantiates a new login page.
	 */
	// Initializing the Page Objects:
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * Login.
	 *
	 * @param uname the uname
	 * @param pwd the pwd
	 */
	// Actions:
	public void login(String uname, String pwd) {
		username.sendKeys(uname);
		password.sendKeys(pwd);
		loginBtn.click();
	}

	/**
	 * Verify log on page.
	 *
	 * @return true, if successful
	 */
	public boolean verifyLogOnPage() {
		return signOnLogo.isDisplayed();
	}

}
