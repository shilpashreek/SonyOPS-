package com.qa.sonyops.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import coom.qa.sonyops.base.BaseClass;

public class LoginPageCE extends BaseClass //defining pageafactory or object repository of login page
{

	//webelements
@FindBy(id="x-user")
WebElement Sonyopsusername;

@FindBy(xpath="//div[@id='ContinueText']")
WebElement continuebtn;

@FindBy(name="x-password")  ////div[.='Login']
WebElement Sonyopspassword;

	/*
	 * @FindBy(xpath="//input[@id='0xSignIn']") WebElement Loginbtn;
	 */

@FindBy(xpath = "//input[@name='0xSignIn']")
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

}
