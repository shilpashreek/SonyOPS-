package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPage;
import com.qa.sonyops.pages.CatalogPlayerPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;

@Listeners(TestNGListeners.class)
public class CatalogPlayerPageTest extends BaseClass {
	BaseClass baseclass;
	static LoginPage loginpage;
	static HomePage homepage;
	static CatalogPage catalogpage;
	static CatalogPlayerPage C_playerpage;
	public static String Asset_Title;
	public static String AssetTitleHeader = "null";
	static TestUtil testutil;
	String Alert_msg;
	String Sheetname = "Segment_data";
	String Sheetname_episode = "EpisodeStrataData";

	public CatalogPlayerPageTest() {
		super();
	}

	@BeforeMethod
	public void SetUp() throws Exception {
		baseclass = new BaseClass();
		baseclass.initialisation();
		baseclass.enterURL(BaseClass.bc_url);
		// initialisation("url");
		C_playerpage = new CatalogPlayerPage();
		// C_playerpage.HandlingMultipleWindows();
		// driver.get(prop.getProperty("bc_url"));
		loginpage = new LoginPage();
		homepage = new HomePage();
		catalogpage = new CatalogPage();
		homepage = loginpage.LogintoBC(BaseClass.username, BaseClass.password);
		catalogpage = homepage.SelectCatalogUserRole();
		Thread.sleep(2000);
		catalogpage.SearchForTestAsset(prop.getProperty("TestAsset_Name"));
		TestUtil.Buffering(driver, 40);
		catalogpage.ClickApplyFilters();
		TestUtil.Buffering(driver, 40);
		catalogpage.ApplyCatalogStatus(prop.getProperty("FilterToBeApplied"));
		Thread.sleep(3000);
		Asset_Title = catalogpage.ClickOnFirstTestAsset("Not Started");

	}

	@Test(priority = 1, enabled = true)
	public void ValidateAssetTitleInCatalogPopUp() {
		logger = extent.startTest("ValidateAssetTitleInCatalogPopUp");
		AssetTitleHeader = C_playerpage.GetAssetTitle();
		Assert.assertTrue(AssetTitleHeader.equalsIgnoreCase(Asset_Title),
				"Catalog popup is not displayed for the selected asset");
	}

	@Test(priority = 2, enabled = true)
	public void ValidateStreamingFromCatalogPopUp() throws InterruptedException {
		logger = extent.startTest("ValidateStreamingFromCatalogPopUp");
		boolean b = C_playerpage.CheckStreaming_Catalogplayer();
		Assert.assertTrue(b);
	}

	@Test(priority = 3, enabled = true)
	public void ValidateSegmentCreationPopUpIsDisplayed() throws InterruptedException {
		logger = extent.startTest("ValidateSegmentCreationPopUpIsDisplayed");
		boolean b = C_playerpage.CheckStreaming_Catalogplayer();
		C_playerpage.CreateSegments();
		String Segment_popUp_title = C_playerpage.AddSegmentPopupIsDisplayed();
		Assert.assertEquals(Segment_popUp_title, "Add Segment");
	}

	@Test(priority = 4, enabled = true)
	public void validateMark_inAndMark_outTimeCodes() throws InterruptedException {
		logger = extent.startTest("validateMark_inAndMark_outTimeCodes");
		C_playerpage.CheckStreaming_Catalogplayer();
		C_playerpage.CreateSegments();
		boolean status = C_playerpage.VerifyTimeCodesFromAddSegementPopup();
		Assert.assertTrue(status);
	}

	@Test(priority = 5, enabled = true)
	public void ValidateAlertMessageOnCreatingSegment() throws InterruptedException {
		logger = extent.startTest("ValidateAlertMessageOnCreatingSegment");
		C_playerpage.CheckStreaming_Catalogplayer();
		C_playerpage.CreateSegments();
		Alert_msg = C_playerpage.SelectSegmentTypeAndAddSegment("Music");
		Assert.assertTrue(Alert_msg.contains("New segment added"), "Alert message is displaying wrong message");

	}

	@Test(priority = 6, enabled = true)
	public void ValidateMusicStrataIsLoading() {
		logger = extent.startTest("ValidateMusicStrataIsLoading");
		boolean Music_strata = C_playerpage.NavigateToMusicStrata();
		C_playerpage.CheckTotalSegmentsInMusicStrata();
		Assert.assertTrue(Music_strata, "Music strata is not loading");

	}

	@Test(priority = 7, enabled = true)
	public void ValidateEditSegmentPageIsDisplayed() {
		logger = extent.startTest("ValidateEditSegmentPageIsDisplayed");
		C_playerpage.NavigateToMusicStrata();
		C_playerpage.CheckTotalSegmentsInMusicStrata();
		boolean Edit_Segment_page = C_playerpage.ClickEdit_Segment();
		Assert.assertTrue(Edit_Segment_page, "Edit segment page is not displaying");
	}

	@DataProvider
	public Object[][] getSegmentData() {
		logger = extent.startTest("");
		testutil = new TestUtil();
		Object[][] test_data = testutil.GetData(Sheetname); // Segment_data
		return test_data;
	}

	@Test(priority = 8, dataProvider = "getSegmentData", enabled = true)
	public void ValidateEditandSaveSegment(String title, String AlbumName, String Remark) {
		logger = extent.startTest("ValidateEditandSaveSegment");
		C_playerpage.NavigateToMusicStrata();
		C_playerpage.CheckTotalSegmentsInMusicStrata();
		C_playerpage.ClickEdit_Segment();
		C_playerpage.FillTheFieldsInEditSegmentPage(title, AlbumName, Remark);
		Alert_msg = C_playerpage.Save_Edited_segment_Capture_successAlert();
		Assert.assertTrue(Alert_msg.contains("Segment Edited Successfully"));
	}

	@Test(priority = 9, enabled = true)
	public void ValiadteErrorMessage_WhenNoSegSelected() {
		logger = extent.startTest("ValiadteErrorMessage_WhenNoSegSelected");
		String Error_msg = C_playerpage.Error_Message_SegmentsNotSelected();
		Assert.assertTrue(Error_msg.contains("Could not open Details Tab, No segment selected!"));
	}

	// cataloging test cases
	@Test(priority = 10, enabled = true)
	public void Validate_SeriesStrataSave_CaptureAlertMessage() throws InterruptedException {
		logger = extent.startTest("Validate_SeriesStrataSave_CaptureAlertMessage");
		String AlertMessage = C_playerpage.SaveSeriesStrata();
		Assert.assertTrue(AlertMessage.contains("Data Saved Successfully"));
	}

	@Test(priority = 11, enabled = true)
	public void ValidateEpisodeStrataIsDisplaying() {
		logger = extent.startTest("ValidateEpisodeStrataIsDisplaying");
		boolean Episode_tab_displayed = C_playerpage.ClickOnEpisodeStrata();
		Assert.assertTrue(Episode_tab_displayed, "Episode strata is not displaying");
	}

	@DataProvider
	public Object[][] getEpisodeStrataData() {
		testutil = new TestUtil();
		Object[][] Episode_Data = testutil.GetData(Sheetname_episode);
		return Episode_Data;
	}

	@Test(priority = 12, enabled = true, dataProvider = "getEpisodeStrataData")
	public void EnteringDataInEpisodeStrata_Save_CaptureSuccessMsg(String title, String Seg, String summary,
			String synop, String word) {
		logger = extent.startTest("EnteringDataInEpisodeStrata_Save_CaptureSuccessMsg");
		C_playerpage.ClickOnEpisodeStrata();
		C_playerpage.ProvideMandatoryData(title, Seg, summary, synop, word);
		String alert_msg = C_playerpage.SaveEpisodeStrataDataAndCaptureAlertMessage();
		Assert.assertTrue(alert_msg.contains("Data Saved Successfully"));
	}

	@Test(priority = 13, enabled = true)
	public void ValidateClosePopUpFunctionality() {
		logger = extent.startTest("ValidateClosePopUpFunctionality");
		boolean Pop_up_closed = C_playerpage.CloseCatalogPlayerPop_up();
		Assert.assertTrue(Pop_up_closed, "Catalog pop-up is not closed");
	}

	@Test(priority = 14, enabled = false)
	public void ValidateAssetStatusAfterCataloging() throws Exception {
		logger = extent.startTest("ValidateAssetStatusAfterCataloging");
		C_playerpage.CloseCatalogPlayerPop_up();
		C_playerpage.SearchforAssetOnWhichCatalogIsPerformed();
		// C_playerpage.ChangeCatalogStatusToAllStatusType();
		Thread.sleep(3000);
		boolean Catalog_status = C_playerpage.getCatalogingStatusOfTestAsset();
		Assert.assertTrue(Catalog_status, "Test asset status is Not started , catalog is not performed");

	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
