package com.qa.sonyops.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.JavascriptUtils;
import com.qa.sonyops.util.TestNGListeners;


@Listeners(TestNGListeners.class)
public class LoginPage extends BaseClass 
{
	@FindBy(name = "x-login-id")
	WebElement Username_sonybc;

	@FindBy(id = "loginContinue")
	WebElement Continue_button;

	@FindBy(id = "x-pwd")
	WebElement Password_sonybc;

	@FindBy( id = "loginSubmit")
	WebElement Login_btn;

	public LoginPage()
	{
		PageFactory.initElements(driver, this);
	}

	public HomePage LogintoBC(String username,String password )
	{
		JavascriptUtils.drawBorder(Username_sonybc, driver);
		Username_sonybc.sendKeys(username);
		JavascriptUtils.drawBorder(Username_sonybc, driver);
		JavascriptUtils.flash(Continue_button, driver);
		Continue_button.click();
		JavascriptUtils.drawBorder(Password_sonybc, driver);
		Password_sonybc.sendKeys(password);
		JavascriptUtils.flash(Login_btn, driver);
		JavascriptUtils.drawBorder(Login_btn, driver);
		Login_btn.click();
		System.out.println("Homepage title is" + " "+driver.getTitle());
		Assert.assertTrue(driver.getTitle().contains("Sony OPS"));
		return new HomePage(); 
		
		
	}
}
