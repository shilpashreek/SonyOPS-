package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;

@Listeners(TestNGListeners.class)
public class CatalogPageTest extends BaseClass
{
	static BaseClass baseclass;
	static LoginPage loginpage;
	static HomePage homepage;
	static CatalogPage catalogpage;
	static TestUtil testutil;
	static String PageCountInitially;
	static String PageCountAfterSearch;
	static String PageCountAfterCatalogStatus;
	static int PageCountInitially_int;
	static int PageCountAfterSearch_int;
	static int PageCountAfterCatalogStatus_int;
	public static String AssetTitle;
	public static String Asset_selected;
	static String AssetLibraryAsset;
	static String Selected_Asset;
	public static String Error_msg;
	
	
  public CatalogPageTest()
  {
	  super();
  }
  
@BeforeMethod
public void SetUp() throws InterruptedException
{
	initialisation("bc_url");
	loginpage=new LoginPage();
	homepage=new HomePage();
	homepage=loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
	catalogpage=homepage.SelectCatalogUserRole();
}

@Test(priority=1,enabled=false)
public void ValidateActiveUserRole()
{
	String role_name = catalogpage.GetCurrentRoleDetails();
	Assert.assertEquals(role_name, "Sony Catalog User");
}

@Test(priority=2,enabled=false)
public void ValidateDashboardLabel()
{
	String Dashboard_name = catalogpage.GetDashboardLabel();
	Assert.assertEquals(Dashboard_name, "CATALOGING");
}

@Test(priority=3,enabled=false)
public void ValidateFilterPopUp() throws InterruptedException
{
	boolean b = catalogpage.ClickApplyFilters();
	Assert.assertTrue(b , "filters popUp is not displayed");
}

@Test(priority=4,enabled=false)
public void ValidateResultsAreDisplayedAccToAppliedFilters() throws InterruptedException
{
	PageCountInitially = catalogpage.GetNumberOfPages();
	PageCountInitially_int=Integer.parseInt(PageCountInitially);
	
	catalogpage.SearchForTestAsset(prop.getProperty("TestAsset_Name"));
	Thread.sleep(2000);
	
	PageCountAfterSearch=catalogpage.GetNumberOfPages();
	PageCountAfterSearch_int=Integer.parseInt(PageCountAfterSearch);
	
	boolean b=catalogpage.ClickApplyFilters();
	Thread.sleep(2000);
	catalogpage.ApplyCatalogStatus(prop.getProperty("FilterToBeApplied"));
	Thread.sleep(2000);
	PageCountAfterCatalogStatus=catalogpage.GetNumberOfPages();
	PageCountAfterCatalogStatus_int=Integer.parseInt(PageCountAfterCatalogStatus);
	
	int ResultCount=catalogpage.FilterResults();
	Thread.sleep(2000);
	System.out.println("Page Count initially" + " " +PageCountInitially + " " +"page count after performing search"  
	 +" " + " " +PageCountAfterSearch +" " +"Pagecount after applying filter" + " " +PageCountAfterCatalogStatus);
	Assert.assertTrue(PageCountAfterSearch_int < PageCountInitially_int && PageCountAfterCatalogStatus_int < PageCountAfterSearch_int , "Filters are not applied");
}

	
@Test(priority=5,enabled=false) 
 public void ValidateCatalogPopUp() throws InterruptedException 
 {
	 catalogpage.SearchForTestAsset(prop.getProperty("TestAsset_Name"));
	 TestUtil.Buffering(driver, 40);
	 catalogpage.ClickApplyFilters(); 
	 TestUtil.Buffering(driver, 40);
	 boolean b =catalogpage.ApplyCatalogStatus(prop.getProperty("FilterToBeApplied"));
	 Assert.assertTrue(b , "Filter is applied successfully"); 
	 TestUtil.Buffering(driver, 40);
	 AssetTitle=catalogpage.ClickOnFirstTestAsset();
	 Assert.assertTrue(AssetTitle.contains("Test"),"Selected Asset doesnot contain test in the title");
}

@Test
public void ValidateAutoSuggestionsIsDisplaying()
{
	catalogpage.ClickOnLibrarySearch("tes");
	int AutoSuggestions=catalogpage.CaptureAutoSuggesstions();
	Assert.assertTrue(AutoSuggestions>0 , "Autosuggestions is not displaying");
}

@Test(enabled=false)
public void ValidateSearchforValidData()
{
	catalogpage.ClickOnLibrarySearch("tes");
	Asset_selected=catalogpage.CheckIfSuggestionListIsdisplaying();
	AssetLibraryAsset=catalogpage.GetAssetTitleFromAssetLibrary();
	Assert.assertEquals(AssetLibraryAsset, Asset_selected , "Asset displayed is not the selected asset");
	
}



@Test(enabled=false)
public void ValidateSearchForInvalidData()
{
	catalogpage.ClickOnLibrarySearch("abc");
	String ActualErrorMsg=catalogpage.CheckIfSuggestionListIsdisplaying();
	Assert.assertTrue(ActualErrorMsg.startsWith("Your search"));
}

@AfterMethod
public void tearDown()
{
	//driver.quit();
}
}