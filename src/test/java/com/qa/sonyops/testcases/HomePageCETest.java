package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.HomePageCE;
import com.qa.sonyops.pages.LoginPageCE;
import com.qa.sonyops.util.TestNGListeners;

@Listeners(TestNGListeners.class)
public class HomePageCETest extends BaseClass {
	BaseClass baseclass;
	static HomePageCE homece;
	static LoginPageCE logince;

	public HomePageCETest() {
		super();
	}

	@BeforeMethod
	public void setup() {
		baseclass = new BaseClass();
		baseclass.initialisation();
		baseclass.enterURL(BaseClass.ce_url);
		// initialisation("ce_url");
		logince = new LoginPageCE();
		logince.login(BaseClass.username, BaseClass.password);
		homece = new HomePageCE();
	}

	@Test(priority = 1, enabled = true)
	public void ValidateHomePage_isDisplayed() {
		logger = extent.startTest("ValidateHomePage_isDisplayed");
		String Title = homece.GetHomePageTitle();
		log.info("home page title" + Title);
		Assert.assertEquals(Title, "SonyOPS Clear EDGE");
		// log.error("Assert failed as" +Title);
	}

	@Test(priority = 2, enabled = true)
	public void VerifyRefreshIcon() {
		logger = extent.startTest("VerifyRefreshIcon");
		boolean Refresh_icon = homece.VerifyRefreshIconIsPresent();
		Assert.assertTrue(Refresh_icon);
	}

	@Test(priority = 3, enabled = true)
	public void ValidateColumnsinHomePage() {
		logger = extent.startTest("ValidateColumnsinHomePage");
		int Colcount = homece.GetColumns();
		Assert.assertEquals(Colcount, 7, "total columns displaying are not matching with the expected column count");
	}

	@Test(priority = 4, enabled = true)
	public void ValidateTransferHistoryEntries() {
		logger = extent.startTest("ValidateTransferHistoryEntries");
		boolean Dashboard_status = homece.GetRowCount();
		Assert.assertTrue(Dashboard_status);
	}

	@Test(priority = 5, enabled = true)
	public void ValidateHomePageEntriesAreDisplaying() {
		logger = extent.startTest("ValidateHomePageEntriesAreDisplaying");
		boolean Pagination = homece.CheckIfPaginationIsPresent();
		Assert.assertTrue(Pagination);
	}

	@Test(priority = 6, enabled = true)
	public void validateFileUploadPopup() {
		logger = extent.startTest("validateFileUploadPopup");
		boolean b = homece.UploadPopUp();
		Assert.assertTrue(b, "File upload pop-up is not displaying");
		System.out.println("Upload popUp is displaying");

	}

	@Test(priority = 7, enabled = true)
	public void ValidateUploadDetailsPopup() {
		logger = extent.startTest("ValidateUploadDetailsPopup");
		String Clear_file_name = homece.UploadDetails();
		Assert.assertTrue(Clear_file_name.contains("SW--2050"), "Upload details popUp is not displayed");
	}

	@Test(priority = 8, enabled = true)
	public void ValidateLogoutFromCEportal() {
		logger = extent.startTest("ValidateLogoutFromCEportal");
		String Page_Title = homece.LogoutPortal();
		Assert.assertTrue(Page_Title.contains("Clear EDGE"), "portal is not logged out");
	}

	@Test(priority = 9, enabled = true)
	public void ValidateSearchResult_ForInvalidData() {
		logger = extent.startTest("ValidateSearchResult_ForInvalidData");
		String Search_result = homece.SearchResultForInvalidData(); // No data found for transfer history.
		Assert.assertTrue(Search_result.contains("No data found"));
	}

	@Test(priority = 10, enabled = true)
	public void ValidateRefreshFunctionality() throws Exception {
		logger = extent.startTest("ValidateRefreshFunctionality");
		boolean Page_is_getting_refreshed = homece.PageRefresh();
		Assert.assertTrue(Page_is_getting_refreshed);
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

}
