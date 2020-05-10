package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.pages.SearchLibraryPage;

public class HomePageTest extends BaseClass
{
	BaseClass baseclass;
	static LoginPage loginpage;
	static HomePage homepage;
	static CatalogPage catalogpage;
	static SearchLibraryPage searchlibrary;
	
             public HomePageTest()
             {
            		super();
             }

    @BeforeMethod
    public void Setup()
    {
    	baseclass=new BaseClass();
    	baseclass.initialisation("bc_url");
    	//initialisation("bc_url");
    	loginpage=new LoginPage();
    	homepage=loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
    	
    }
    
    @Test(priority=1,enabled=true)
    public void ValidateHomePageTitle()
    {
    	String Title = homepage.GetHomePageTitle();
    	System.out.println("Title of homepage is" +" " +Title);
    	Assert.assertEquals(Title, "Sony OPS", "Actual title is not matching with the expected title");
    }
    
    @Test(priority=2,enabled=true)
    public void ValidateLandingPageIsLoading()
    {
    	homepage.CheckColumnsInDashboard();
    }
    
    @Test(priority=3,enabled=true)
    public void ValidateUserRoleResult() throws Exception
    {
    	catalogpage=homepage.SelectCatalogUserRole();
    }
    
    @Test(priority=4, enabled=true)
    public void ValidateAssetLibrarySearch()
    {
    	searchlibrary=homepage.SearchTestAsset();
    	String Current_url=homepage.GetPageUrl();
    	Assert.assertTrue(Current_url.contains("PFT.Clear.Search"),"SearchLibrary page is not displayed");
    }
    
    @AfterMethod
    public void tearDown()
    {
    	driver.quit();
    }
}
