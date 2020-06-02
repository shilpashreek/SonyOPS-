package com.qa.sonyops.testcases;


import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.pages.OpsPage;
import com.qa.sonyops.util.TestNGListeners;

@Listeners(TestNGListeners.class)
public class OpsPageTest extends BaseClass	
{
	BaseClass baseclass;
	LoginPage loginpage;
	HomePage homepage;
	OpsPage opspage;
	public OpsPageTest()
	{
		super();
	}

	@BeforeMethod
	public void Setup() throws Exception
	{
		baseclass=new BaseClass();
		baseclass.initialisation("bc_url");
		//initialisation("bc_url");
		loginpage=new LoginPage();
		loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
		homepage=new HomePage();
		opspage=homepage.SelectPFTopsUserRole();
		opspage=new OpsPage();
	
	}
	
	@Test(priority=1 , enabled=true)
	public void ValidateDefaultLandingPage_ForOpsRoleIsShows()
	{
		logger=extent.startTest("ValidateDefaultLandingPage_ForOpsRoleIsShows");
		boolean showsLabel=opspage.GetThelandingPageLabel();
		Assert.assertTrue(showsLabel, "Shows page is not displaying");
	}
	
	@Test(priority=2 , enabled=true)
	public void ValidatePaginationControlsLoading()
	{
		logger=extent.startTest("ValidatePaginationControlsLoading");
		boolean PaginationIconPresent=opspage.checkPageIcon();
		Assert.assertTrue(PaginationIconPresent);
	}
	
	@Test(priority=3, enabled=true)
	public void ValidateDashboardIsLoading()        //if columns are present obviously DashboarD will be loading
	{
		logger=extent.startTest("ValidateDashboardIsLoading");
		List<String> original_col_list = opspage.getColumnsCountAndName();
		List<String> Expected_col_list = opspage.TargetColumnList();
		Assert.assertEquals(original_col_list, Expected_col_list , "Columns are not matching with expected columns");
	}
	
	@Test(priority=4 ,enabled=true)
	public void ValidateFilterPopUpIsDisplayed()
	{
		logger=extent.startTest("ValidateFilterPopUpIsDisplayed");
		boolean filter_status=opspage.ClickOnfilterIcon();
		Assert.assertTrue(filter_status , "Filter popUp is not displaying");
	}
	
	@Test(priority=5 , enabled = true)
	public void ValidateAllFilterOptionsAreDisplayingInFilterPopUp()
	{
		logger=extent.startTest("ValidateAllFilterOptionsAreDisplayingInFilterPopUp");
		opspage.ClickOnfilterIcon();
		opspage.getAllFilterOptions();
	}
	
	@Test(priority=6,enabled=true)
	public void ValidateSearchResetFunctionality()
	{
		logger=extent.startTest("ValidateSearchResetFunctionality");
		boolean Page_refreshing=opspage.SearchResetFunctionality();
		Assert.assertTrue(Page_refreshing);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	
}
