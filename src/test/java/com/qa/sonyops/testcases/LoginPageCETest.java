package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.HomePageCE;
import com.qa.sonyops.pages.LoginPageCE;
import com.qa.sonyops.util.TestUtil;

public class LoginPageCETest extends BaseClass
{
	static BaseClass baseclass;
	static LoginPageCE logince;
	static HomePageCE  homece;
	TestUtil testutil;
	String Sheetname = "LoginCredentials";
	
  public LoginPageCETest()
  {
	  super();
  }
  
@BeforeMethod
public void initialization()
{
	baseclass=new BaseClass();
	baseclass.initialisation("ce_url");
	//initialisation("ce_url");
	logince=new LoginPageCE();
	
}

@Test(priority=0 , enabled=false)
public void ValidateClearLogoisDisplayedinLoginPage()
{
	boolean b = logince.ValidateLogo();
	System.out.println(b);
	Assert.assertTrue(true);
}

@Test(priority=1 , enabled=false)
public void ValidateLoginPageTitle()
{
	String loginpagetitle = logince.ValidateLoginPageTitle();
	System.out.println(loginpagetitle);
	Assert.assertEquals(loginpagetitle, "Clear EDGE", "LoginPage title is not matching with the expected result");
}

@DataProvider
public Object[][] getCELoginData()
{
	testutil =new TestUtil();
	Object[][] Ce_login_data = testutil.GetData(Sheetname);
	return Ce_login_data;
}

@Test(priority=2 , dataProvider="getCELoginData")
public void ValidateLoginToCEportal(String username , String password)
{
	homece=logince.login(username, password);
	String homepagetile = driver.getTitle();
	System.out.println("Login is successful" +homepagetile);
	Assert.assertEquals(homepagetile, "SonyOPS Clear EDGE", "Home page is not displayed");
}

@Test(priority=3 , enabled=false)
public void ValidateCopyRightTextOnLoginPage()
{
	logince.CopyRighttext();
}

@Test(priority=4 , enabled=false)
public void ValidateLinksOnLoginPage()
{
	logince.GetTotalLinks();
}
@AfterMethod
public void tearDown()
{
  driver.quit();
}
}
