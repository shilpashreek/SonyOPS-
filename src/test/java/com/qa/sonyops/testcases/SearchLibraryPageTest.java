package com.qa.sonyops.testcases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPlayerPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.pages.SearchLibraryPage;
import com.qa.sonyops.util.StreamUtil;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;

import jdk.internal.jline.internal.Log;

@Listeners(TestNGListeners.class)
public class SearchLibraryPageTest extends BaseClass
{
	LoginPage loginpage;
	SearchLibraryPage searchpage;
	static HomePage homepage;
	TestUtil testutil;
	CatalogPlayerPage C_playerpage;
	StreamUtil stream;

   public SearchLibraryPageTest()
   {
	   super();
   }
   
   
@BeforeMethod
public void SetUp()
{
	Logger log = Logger.getLogger(SearchLibraryPageTest.class.getName());
	log.info("*****************Starting testcase execution*****************");
	initialisation("url");
	C_playerpage=new CatalogPlayerPage();
	C_playerpage.HandlingMultipleWindows();
	driver.get(prop.getProperty("bc_url"));
	loginpage=new LoginPage();
	loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
	homepage=new HomePage();
	homepage.SearchTestAsset();
	searchpage=new SearchLibraryPage();
	
}
 
@Test(priority=1,enabled=true)
public void ValidateStandalonePlayerIsDisplaying()
{
	String AssetTitle = searchpage.ClickOnStandalonePlayer();
	Assert.assertTrue(!AssetTitle.isEmpty());
	
}

@Test(priority=2,enabled=true)
public void ValidateStandalonePlayerIsStreaming() throws InterruptedException
{
	searchpage.ClickOnStandalonePlayer();
	boolean b=searchpage.CheckIfPlayerisLoading();
	Assert.assertTrue(b , "Streaming is not happening");
	
}


@Test(priority=3,enabled=true)
public void ValidateAssetDetailsIsDisplaying()
{
	String A_details = searchpage.CheckAssetDetails();
	Assert.assertTrue(A_details.contains("Test"));
	Assert.assertTrue(A_details.contains("Uploaded On:"));
	Assert.assertTrue(A_details.contains("channel:"), "AssetDetails is not matching expected condition");
	Assert.assertTrue(A_details.contains("Duration"));
	
}

@Test(priority=4,enabled=true)
public void ValidateInformationModeIsDiaplaying()
{
	boolean b = searchpage.ClickInformationModeIcon();
	Assert.assertTrue(b,"AssetDetails pop-up is not displaying");
}

@Test(priority=5,enabled=true)
public void ValidateMetaDataInAssetDetailsPopUp() throws InterruptedException
{
	searchpage.ClickInformationModeIcon();
	boolean b=searchpage.CheckMetaDataTab();
	Assert.assertTrue(b , "Metadata tab is not loading");
}

@Test(priority=6,enabled=true)
public void ValidateAuditTrialTabDetails() throws InterruptedException
{
	String ExpectedAuditTab_text="No Audit Trails available for this asset.";
	searchpage.ClickInformationModeIcon();
	String AuditText=searchpage.CheckAuditTrialTab();
	Assert.assertTrue(AuditText.contains(ExpectedAuditTab_text));
	
}

@Test(priority=7,enabled=true)
public void ValidateEssenceTabDetails() throws InterruptedException
{
	searchpage.ClickInformationModeIcon();
	boolean b=searchpage.CheckEssenceTab();
	Assert.assertTrue(b , "EssenceTab is not loading");
	
}

@Test(priority=8,enabled=true)
public void ValidateOpsactionTab() throws InterruptedException
{
	String ExpectedOpsactionTab="Mark as Delete (Invalid Asset will be hidden in Library)";
	searchpage.ClickInformationModeIcon();
	String OpsactionTabText=searchpage.CheckOpsactionTab();
	Assert.assertTrue(OpsactionTabText.contains(ExpectedOpsactionTab));
}
  
@Test(priority=9, enabled=true)
public void ValidateStreamingFromAssetDetailPopUp() throws InterruptedException
{
	searchpage.ClickInformationModeIcon();
	boolean streaming_status=searchpage.streamingFromAssetDetailsPopUp();
	Assert.assertTrue(streaming_status);
}

@Test(priority=10 , enabled=true)
public void ValidateCart_ErrorMsg_clickedOnEmptyCart()
{
	String AlertMessage=searchpage.ClickOnCartWhenCartCoun_IsZeroAndCaptureAlertMessage();
	Assert.assertTrue(AlertMessage.contains("Select at least one asset"));
}

@Test(priority=11 , enabled=true)
public void ValidateCartPageIsDisplaying()
{
	boolean CartPage=searchpage.AddAssetsToCart();
	Assert.assertTrue(CartPage , "cart page is not displaying");
}

@Test(priority=12 ,enabled = true)
public void ValidateCartCount_WithAssetsAdded()
{
	searchpage.AddAssetsToCart();
	int Cart_count=searchpage.GetAssetsCountInCart();
	Assert.assertEquals(Cart_count, 4 ,"Assets added count is not matching with Cart Count");
	
}

@Test(priority=13,enabled=true)
public void ValidateFilterOptionsInSearchLibrary() throws InterruptedException
{
	int Total_filters=searchpage.GetFilterOptionsCount();
	Assert.assertEquals(Total_filters, 9 , "All the filter options are not displaying");
}

@Test(priority=14 , enabled=true)
public void ValidateContentsCount() throws InterruptedException
{
	String FilterResult=searchpage.ApplyFilter();
	Assert.assertTrue(FilterResult.contains("Results"));
}



public void tearDown()
{
	driver.quit();
}
   
   
   
   
}