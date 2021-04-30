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

@Listeners(TestNGListeners.class)
public class SearchLibraryPageTest extends BaseClass {
	BaseClass baseclass;
	LoginPage loginpage;
	SearchLibraryPage searchpage;
	static HomePage homepage;
	TestUtil testutil;
	CatalogPlayerPage C_playerpage;
	StreamUtil stream;

	public SearchLibraryPageTest() {
		super();
	}

	@BeforeMethod
	public void SetUp() {
		Logger log = Logger.getLogger(SearchLibraryPageTest.class.getName());
		log.info("*****************Starting testcase execution*****************");
		baseclass = new BaseClass();
		baseclass.initialisation();
		baseclass.enterURL(BaseClass.bc_url);
		// initialisation("url");
		C_playerpage = new CatalogPlayerPage();
		// C_playerpage.HandlingMultipleWindows();
		// driver.get(prop.getProperty("bc_url"));
		loginpage = new LoginPage();
		loginpage.LogintoBC(BaseClass.username, BaseClass.password);
		homepage = new HomePage();
		homepage.SearchTestAsset();
		searchpage = new SearchLibraryPage();

	}

	@Test(priority = 1, enabled = true)
	public void ValidateStandalonePlayerIsDisplaying() {
		logger = extent.startTest("ValidateStandalonePlayerIsDisplaying");
		String AssetTitle = searchpage.ClickOnStandalonePlayer();
		Assert.assertTrue(!AssetTitle.isEmpty());

	}

	@Test(priority = 2, enabled = true)
	public void ValidateStandalonePlayerIsStreaming() throws InterruptedException {
		try {
			logger = extent.startTest("ValidateStandalonePlayerIsStreaming");
			searchpage.ClickOnStandalonePlayer();
			boolean b = searchpage.CheckIfPlayerisLoading();
			Assert.assertTrue(b, "Streaming is not happening");
		} catch (Exception e) {
			log.error("Streaming is not happening due to the following error " + e.getMessage());
		}

	}

	@Test(priority = 3, enabled = true)
	public void ValidateAssetDetailsIsDisplaying() {
		try {
			logger = extent.startTest("ValidateAssetDetailsIsDisplaying");
			String A_details = searchpage.CheckAssetDetails();
			Assert.assertTrue(!A_details.isEmpty());
		} catch (Exception e) {
			log.error("Asset details is not displaying, error as follows " + e.getMessage());

		}
	}

	@Test(priority = 4, enabled = true)
	public void ValidateInformationModeIsDiaplaying() {
		logger = extent.startTest("ValidateInformationModeIsDiaplaying");
		boolean b = searchpage.ClickInformationModeIcon();
		Assert.assertTrue(b, "AssetDetails pop-up is not displaying");
	}

	@Test(priority = 5, enabled = true)
	public void ValidateMetaDataInAssetDetailsPopUp() throws InterruptedException {
		logger = extent.startTest("ValidateMetaDataInAssetDetailsPopUp");
		searchpage.ClickInformationModeIcon();
		boolean b = searchpage.CheckMetaDataTab();
		Assert.assertTrue(b, "Metadata tab is not loading");
	}

	@Test(priority = 6, enabled = true)
	public void ValidateAuditTrialTabDetails() throws InterruptedException {
		logger = extent.startTest("ValidateAuditTrialTabDetails");
		String ExpectedAuditTab_text = "No Audit Trails available for this asset.";
		searchpage.ClickInformationModeIcon();
		String AuditText = searchpage.CheckAuditTrialTab();
		Assert.assertTrue(AuditText.contains(ExpectedAuditTab_text));

	}

	@Test(priority = 7, enabled = true)
	public void ValidateEssenceTabDetails() throws InterruptedException {
		logger = extent.startTest("ValidateEssenceTabDetails");
		searchpage.ClickInformationModeIcon();
		boolean b = searchpage.CheckEssenceTab();
		Assert.assertTrue(b, "EssenceTab is not loading");

	}

	@Test(priority = 8, enabled = true)
	public void ValidateOpsactionTab() throws InterruptedException {
		logger = extent.startTest("ValidateOpsactionTab");
		String ExpectedOpsactionTab = "Mark as Delete (Invalid Asset will be hidden in Library)";
		searchpage.ClickInformationModeIcon();
		String OpsactionTabText = searchpage.CheckOpsactionTab();
		Assert.assertTrue(OpsactionTabText.contains(ExpectedOpsactionTab));
	}

	@Test(priority = 9, enabled = true)
	public void ValidateStreamingFromAssetDetailPopUp() throws InterruptedException {
		try {
			logger = extent.startTest("ValidateStreamingFromAssetDetailPopUp");
			searchpage.ClickInformationModeIcon();
			boolean streaming_status = searchpage.streamingFromAssetDetailsPopUp();
			Assert.assertTrue(streaming_status);
		} catch (Exception e) {
			log.error("Streaming is not working from Asset details pop-up");

		}
	}

	@Test(priority = 10, enabled = false)
	public void ValidateCart_ErrorMsg_clickedOnEmptyCart() {
		logger = extent.startTest("ValidateCart_ErrorMsg_clickedOnEmptyCart");
		String AlertMessage = searchpage.ClickOnCartWhenCartCoun_IsZeroAndCaptureAlertMessage();
		Assert.assertTrue(AlertMessage.contains("Select at least one asset"));
	}

	@Test(priority = 11, enabled = true)
	public void ValidateCartPageIsDisplaying() {
		logger = extent.startTest("ValidateCartPageIsDisplaying");
		boolean CartPage = searchpage.AddAssetsToCart();
		Assert.assertTrue(CartPage, "cart page is not displaying");
	}

	@Test(priority = 12, enabled = true)
	public void ValidateCartCount_WithAssetsAdded() {
		logger = extent.startTest("ValidateCartCount_WithAssetsAdded");
		searchpage.AddAssetsToCart();
		int Cart_count = searchpage.GetAssetsCountInCart();
		Assert.assertEquals(Cart_count, 4, "Assets added count is not matching with Cart Count");

	}

	@Test(priority = 13, enabled = true)
	public void ValidateFilterOptionsInSearchLibrary() throws InterruptedException {
		logger = extent.startTest("ValidateFilterOptionsInSearchLibrary");
		int Total_filters = searchpage.GetFilterOptionsCount();
		Assert.assertEquals(Total_filters, 9, "All the filter options are not displaying");
	}

	@Test(priority = 14, enabled = true)
	public void ValidateContentsCount() throws InterruptedException {
		logger = extent.startTest("ValidateContentsCount");
		String FilterResult = searchpage.ApplyFilter();
		Assert.assertTrue(FilterResult.contains("Results"));
	}

	@Test(priority = 15, enabled = true)
	public void ValidateSortOptionsIsDisplaying() {
		logger = extent.startTest("ValidateSortOptionsIsDisplaying");
		boolean Sort_option = searchpage.CheckSortOptionInLibrary();
		Assert.assertTrue(Sort_option);
	}

	@Test(priority = 16, enabled = true)
	public void ValidateSortOptionsInSearchLibrary() {
		logger = extent.startTest("ValidateSortOptionsInSearchLibrary");
		searchpage.CheckSortOptionInLibrary();
		String SortOptions = searchpage.getSortOptions();
		Assert.assertTrue(!SortOptions.isEmpty(), "Sort options are not displaying");
	}

	@Test(priority = 17, enabled = true)
	public void ValidateAssetsAreDisplayingInListView() {
		logger = extent.startTest("ValidateAssetsAreDisplayingInListView");
		boolean List_view = searchpage.CheckListView();
		Assert.assertTrue(List_view);
	}

	@Test(priority = 18, enabled = true)
	public void ValidateAssetsAreDisplayingInGridView() {
		logger = extent.startTest("ValidateAssetsAreDisplayingInGridView");
		boolean Grid_view = searchpage.gridViewIsDisplaying();
		Assert.assertTrue(Grid_view);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
