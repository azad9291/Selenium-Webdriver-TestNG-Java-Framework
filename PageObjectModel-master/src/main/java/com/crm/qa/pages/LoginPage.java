package com.crm.qa.pages;

import org.eclipse.jetty.util.annotation.Name;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase{
	
	//Page Factory - OR:
	@FindBy(name="userName")
	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(name="login")
	WebElement loginBtn;
	
	@FindBy(xpath="/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/img")
	WebElement signOnLogo;
	
	
	//Initializing the Page Objects:
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public void login(String uname, String pwd) {
		username.sendKeys(uname);
		password.sendKeys(pwd);
		loginBtn.click();
	}
	
	public boolean verifyLogOnPage() {
		return signOnLogo.isDisplayed();
	}
	
//	public String validateLoginPageTitle(){
//		return driver.getTitle();
//	}
//	
//	public boolean validateCRMImage(){
//		return crmLogo.isDisplayed();
//	}
//	
//	public HomePage login(String un, String pwd){
//		username.sendKeys(un);
//		password.sendKeys(pwd);
//		//loginBtn.click();
//		    	JavascriptExecutor js = (JavascriptExecutor)driver;
//		    	js.executeScript("arguments[0].click();", loginBtn);
//		    	
//		    	
//		    
//		
//		return new HomePage();
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
