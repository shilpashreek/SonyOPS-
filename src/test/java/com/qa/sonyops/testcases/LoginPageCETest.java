package com.qa.sonyops.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.HomePageCE;
import com.qa.sonyops.pages.LoginPageCE;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

@Listeners(TestNGListeners.class)
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

@Test(priority=0 , enabled=true)
public void ValidateClearLogoisDisplayedinLoginPage()
{
	logger= extent.startTest("ValidateClearLogoisDisplayedinLoginPage");
	//logger=report.createTest("clear logo");
	boolean b = logince.ValidateLogo();
	System.out.println(b);
	Assert.assertTrue(true);
}

@Test(priority=1 , enabled=true)
public void ValidateLoginPageTitle()
{
	logger= extent.startTest("ValidateLoginPageTitle");
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

@Test(priority=2 , dataProvider="getCELoginData" , enabled=true)
public void ValidateLoginToCEportal(String username , String password)
{
	logger= extent.startTest("ValidateLoginToCEportal");
	homece=logince.login(username, password);
	String homepagetile = driver.getTitle();
	System.out.println("Login is successful" +homepagetile);
	Assert.assertEquals(homepagetile, "SonyOPS Clear EDGE", "Home page is not displayed");
}

@Test(priority=3 , enabled=true)
public void ValidateCopyRightTextOnLoginPage()
{
	logger= extent.startTest("ValidateCopyRightTextOnLoginPage");
	//logger=report.createTest("copyRight");
	logince.CopyRighttext();
	//logger.pass("pass");
}

@Test(priority=4 , enabled=true)
public void ValidateLinksOnLoginPage()
{
	//logger=report.createTest("clear links");
	logger= extent.startTest("ValidateLinksOnLoginPage");
	
	logince.GetTotalLinks();
	//logger.pass("testcase pass");
	//logger.fail("testcase failed");
}
@AfterMethod
public void tearDown(ITestResult result) throws Exception
{
		
    // extent.endTest(logger);	
     driver.quit();
  //report.flush();
}
}
