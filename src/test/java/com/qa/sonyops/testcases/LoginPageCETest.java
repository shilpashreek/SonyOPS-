package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.sonyops.pages.HomePageCE;
import com.qa.sonyops.pages.LoginPageCE;

import coom.qa.sonyops.base.BaseClass;

public class LoginPageCETest extends BaseClass
{
	static BaseClass baseclass;
	static LoginPageCE logince;
	static HomePageCE  homece;
	
  public LoginPageCETest()
  {
	  super();
  }
  
@BeforeMethod
public void initialization()
{
	initialisation();
	logince=new LoginPageCE();
	
}

@Test(priority=0)
public void ValidateClearLogoisDisplayedinLoginPage()
{
	boolean b = logince.ValidateLogo();
	System.out.println(b);
	Assert.assertTrue(true);
}

@Test(priority=1)
public void ValidateLoginPageTitle()
{
	String loginpagetitle = logince.ValidateLoginPageTitle();
	System.out.println(loginpagetitle);
	Assert.assertEquals(loginpagetitle, "Clear EDGE", "LoginPage title is not matching with the expected result");
}

@Test(priority=2)
public void ValidateLoginToCEportal()
{
	homece=logince.login(prop.getProperty("username"), prop.getProperty("password"));
	String homepagetile = driver.getTitle();
	System.out.println("Login is successful" +homepagetile);
	Assert.assertEquals(homepagetile, "SonyOPS Clear EDGE", "Home page is not displayed");
}


@AfterMethod
public void tearDown()
{
  driver.quit();
}
}
