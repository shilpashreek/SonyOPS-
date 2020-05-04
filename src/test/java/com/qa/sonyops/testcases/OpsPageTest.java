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
		initialisation("bc_url");
		loginpage=new LoginPage();
		loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
		homepage=new HomePage();
		opspage=homepage.SelectPFTopsUserRole();
		opspage=new OpsPage();
	
	}
	
	@Test(priority=1 , enabled=false)
	public void ValidateDefaultLandingPage_ForOpsRoleIsShows()
	{
		boolean showsLabel=opspage.GetThelandingPageLabel();
		Assert.assertTrue(showsLabel, "Shows page is not displaying");
	}
	
	@Test(priority=2 , enabled=false)
	public void ValidatePaginationControlsLoading()
	{
		boolean PaginationIconPresent=opspage.checkPageIcon();
		Assert.assertTrue(PaginationIconPresent);
	}
	
	@Test(priority=3, enabled=false)
	public void ValidateDashboardIsLoading()        //if columns are present obviously DashboarD will be loading
	{
		List<String> original_col_list = opspage.getColumnsCountAndName();
		List<String> Expected_col_list = opspage.TargetColumnList();
		Assert.assertEquals(original_col_list, Expected_col_list , "Columns are not matching with expected columns");
	}
	
	@Test(priority=4 ,enabled=false)
	public void ValidateFilterPopUpIsDisplayed()
	{
		boolean filter_status=opspage.ClickOnfilterIcon();
		Assert.assertTrue(filter_status , "Filter popUp is not displaying");
	}
	
	@Test(priority=5 , enabled = false)
	public void ValidateAllFilterOptionsAreDisplayingInFilterPopUp()
	{
		opspage.ClickOnfilterIcon();
		opspage.getAllFilterOptions();
	}
	
	@Test
	public void ValidateSearchResetFunctionality()
	{
		boolean Page_refreshing=opspage.SearchResetFunctionality();
		Assert.assertTrue(Page_refreshing);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	
}
