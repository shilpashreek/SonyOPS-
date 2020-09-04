package com.qa.sonyops.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.util.TestUtil;

public class LoginPageTest extends BaseClass
{
	static BaseClass baseclass;
	static LoginPage loginpage;
	static HomePage homepage;
	TestUtil testutil;
	String Sheetname="LoginCredentials";
	
public LoginPageTest()
{
	super();
}
@BeforeMethod
public void setup()
{
	baseclass=new BaseClass();
	baseclass.initialisation("bc_url");
	//initialisation("bc_url");
	loginpage=new LoginPage();
}

@Test(priority=1,enabled = true)
public void ValidateLoginToBCportal()
{
	logger=extent.startTest("ValidateLoginToBCportal");
	homepage=loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
}

@DataProvider
public Object[][] getLoginData()
{
	testutil=new TestUtil();
	Object[][] Login_data = testutil.GetData(Sheetname);
	return Login_data;
}

@Test(priority=2, dataProvider="getLoginData")
public void ValidateLoginForValidAndInvalidData(String username,String password)
{
	logger=extent.startTest("ValidateLoginForValidAndInvalidData");
	homepage=loginpage.LogintoBC(username, password);
}



@AfterMethod
public void tearDown()
{
	driver.quit();
}
}
