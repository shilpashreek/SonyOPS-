package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.TestNGListeners;

@Listeners(TestNGListeners.class)
public class LoginPageCE extends BaseClass //defining pageafactory or object repository of login page
{

	//webelements
@FindBy(id="x-user")
@CacheLookup
WebElement Sonyopsusername;

@FindBy(xpath="//div[@id='ContinueText']")
@CacheLookup
WebElement continuebtn;

@FindBy(name="x-password")  ////div[.='Login']
@CacheLookup
WebElement Sonyopspassword;

	/*
	 * @FindBy(xpath="//input[@id='0xSignIn']") WebElement Loginbtn;
	 */

@FindBy(css = ".invalidCredentials")
WebElement ErrorMsg_InvalidCredentials;

@FindBy(xpath = "//input[@name='0xSignIn']")
@CacheLookup
WebElement Loginbtn;

@FindBy(xpath="//a[.='Back']")
@CacheLookup
WebElement backbtn;

@FindBy(xpath="//a[.='Forgot Password?']")
@CacheLookup
WebElement forgotpwdpassword;

@FindBy(xpath="//div[@class='clearLogo']")
@CacheLookup
WebElement clearlogo;

@FindBy(xpath="//div[@class='copyrightText']")
@CacheLookup
WebElement copyright;

//initialize the webelements
public LoginPageCE()
{
	PageFactory.initElements(driver, this);   //this-crurent class objects or loginpage.class
}

//different actions available on login page
public String ValidateLoginPageTitle()
{
	return driver.getTitle();
}

public boolean ValidateLogo()
{
	return clearlogo.isDisplayed();
	
}

public HomePageCE login(String username, String password)
{
	Sonyopsusername.sendKeys(username);
	continuebtn.submit();
	Sonyopspassword.sendKeys(password);  //on clicking on login button , user will be landing to Home page, 
	Loginbtn.submit();                  //so return type of this login method should be HomePage class object
	return new HomePageCE();       // returns HomePage object i.e., new HomePage()
}

public String CopyRighttext()
{
	 String CopyrightText = copyright.getText();
	 System.out.println(CopyrightText);
	 return CopyrightText; 
}

public void GetTotalLinks()
{
	List<WebElement> links = driver.findElements(By.xpath("//a"));
	System.out.println("total links present in login page are" + " " +links.size());
	for(int i=1; i<=links.size(); i++)
	{
		System.out.println(links.get(i).getText());
	}
	
}



}
