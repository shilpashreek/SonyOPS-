package com.qa.sonyops.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import coom.qa.sonyops.base.BaseClass;

public class LoginPageCE extends BaseClass //defining pageafactory or object repository of login page
{

	//webelements
@FindBy(name="x-username")
WebElement username;

@FindBy(xpath="//div[@id='ContinueText']")
WebElement continuebtn;

@FindBy(name="x-password")  ////div[.='Login']
WebElement password;

@FindBy(xpath="//input[@class='loginButton']")
WebElement Loginbtn;

@FindBy(xpath="//a[.='Back']")
WebElement backbtn;

@FindBy(xpath="//a[.='Forgot Password?']")
WebElement forgotpwdpassword;

@FindBy(xpath="//div[@class='clearLogo']")
WebElement clearlogo;

@FindBy(xpath="//div[@class='copyrightText']")
WebElement copyright;

//initialize the webelements
public LoginPageCE()
{
	PageFactory.initElements(driver, this);   //this-cuurent class objects or loginpage.class
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

public HomePageCE login(String un, String pwd)
{
	username.sendKeys(un);
	continuebtn.click();
	password.sendKeys(pwd);  //on clicking on login button , user will be landing to Home page, 
	Loginbtn.click();        //so return type of this login method should be HomePage class object
	return new HomePageCE();       // return new HomePage()
}

public String CopyRighttext()
{
	return copyright.getText();
	
}

}
