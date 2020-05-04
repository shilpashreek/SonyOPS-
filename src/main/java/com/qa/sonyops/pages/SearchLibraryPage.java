package com.qa.sonyops.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.StreamUtil;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;


@Listeners(TestNGListeners.class)
public class SearchLibraryPage extends BaseClass {
	TestUtil testutil;
	SearchLibraryPage searchpage;
	boolean Streaming_status;

	@FindBy(css = ".libraryPlayIconHover")   //standalone player play icon
	WebElement Playvideo;

	@FindBy(css = ".popupHeader")
	WebElement PlayerHeader;

	@FindBy(css = ".displayTC") // .displayTC
	WebElement PlayerControl_panel;

	@FindBy(xpath = "(//span[@class='ui-slider-handle ui-state-default ui-corner-all'])[3]")
	WebElement SliderPoint;

	@FindBy(css = ".noProxyAvaliable")
	WebElement No_preview_availabel;

	@FindBy(xpath = "//span[@class='errorMessage']")
	WebElement Error_code;

	@FindBy(css = "div#CMP_SearchPlayerContainer_dvFrameTime")  
	WebElement Standaloneplayer_TC_in;

	@FindBy(css = "span#CMP_SearchPlayerContainer_totalDuration")  
	WebElement Standaloneplayer_Display_Timecode;
	
	@FindBy(css = "div#CMP_SearchPlayerContainer_btnCMPPlayPause")
	WebElement Standaloneplayer_play_pause;

	@FindBy(xpath = "//img[@class='searchInfoIcon FL']")
	WebElement InformationMode_icon;

	@FindBy(css = "div#Metadata")
	WebElement MetaData_tab;

	@FindBy(css = "div#SaveAssetDetails")
	WebElement MetaData_SaveBtn;

	@FindBy(css = "div#AuditTrail")
	WebElement AuditTrail_tab;

	@FindBy(css = "div#Essence")
	WebElement Essence_Tab;

	@FindBy(css = "div#essenceUploadBtnV1")
	WebElement Essence_uploadBtn;

	@FindBy(css = "div#OPSAction")
	WebElement Opsaction_tab;
	
	@FindBy(css = "div#closePFTPopup")
	WebElement Close_Standalone_popUp;
	
	//Asset details player
	@FindBy(css = "div#CMP_AssetPlayerInstance_dvControls")
	WebElement AssetDetailsPlayer_control_panel;
	
	@FindBy(css = "span#CMP_AssetPlayerInstance_totalDuration")
	WebElement AssetDetailsPlayer_totalDuration;
	
	@FindBy(css = "div#CMP_AssetPlayerInstance_dvFrameTime")
	WebElement AssetDetailPlayer_timecode_in;
	
	@FindBy(css = "div#CMP_AssetPlayerInstance_btnCMPPlayPause")
	WebElement AssetDetailPlayer_play_pause;
	
	@FindBy(css = ".cartIcon")
	WebElement Cart;
	
	@FindBy(css = "div#cartCount")
	WebElement Cart_count;
	
	@FindBy(css = "div#alertWrapper")
	WebElement EmptyCart_AlertMsg;
	
	@FindBy(css = ".popupHeader")
	WebElement Cart_page;
	
	@FindBy(css = ".headerSearchBtnV2")
	WebElement Library_search;
	
	@FindBy(css = ".headerSearchTextV2")
	WebElement Lib_Search_TextArea;
	
	@FindBy(css = ".searchBtn")
	WebElement Search_Btn;
	
	@FindBy(xpath = "//div[@title='Expand Channel Filter']")
	WebElement Expand_Filter;
	
	@FindBy(css = ".showmore")
	WebElement Show_more_link;
	
	@FindBy(xpath = "(//input[@name='radio_channel'])[position()=4]")
	WebElement Radio_btn;
	
	@FindBy(css = "div#spanSearchHeader")
	WebElement Filter_result;
	
	@FindBy(xpath = "//div[@id='Search.Sort']")
	WebElement Sort_option;
    
	@FindBy(css = ".searchSortPanel")
	WebElement SortPanel;
	
	@FindBy(css = ".lsView")
	WebElement ListView;
	
	@FindBy(id = "gridView")
	WebElement GridView_icon;
	
	@FindBy(css = ".gridView")
	WebElement GridView;
	
	public SearchLibraryPage() {
		PageFactory.initElements(driver, this);
	}

	
//Method to click on StandAlone player from library search results	
	public String ClickOnStandalonePlayer() {
		Actions act = new Actions(driver);
		WebElement library = driver.findElement(By.xpath("(//div[@class='thumbHolder searchItemMenu'])[1]"));
		act.moveToElement(library).build().perform();
		TestUtil.Click(driver, 20, Playvideo);
		String ATitle = PlayerHeader.getText();
		System.out.println(ATitle);
		return ATitle;

	}

//method to check if slider is loading
	public boolean CheckSlider() {
		boolean sliderdisplayed = false;
		try {
			WebElement slider = driver
					.findElement(By.xpath("(//span[@class='ui-slider-handle ui-state-default ui-corner-all'])[3]"));
			sliderdisplayed = slider.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sliderdisplayed;
	}

//method to check PlayerHeader is displayed
	public boolean player_header() {
		boolean Player_Header = false;
		try {
			Player_Header = PlayerHeader.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Player_Header;
	}

//method to check if player components are loading 
	public boolean PlayerComponents() {
		boolean PlayerKeys = false;
		try {
			new WebDriverWait(driver,80).
			until(ExpectedConditions.visibilityOf(PlayerControl_panel));
			PlayerKeys = PlayerControl_panel.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PlayerKeys;
	}

//method to check if player is loading No preview
	public boolean NoPreview() {
		boolean No_preview = false;
		try {
			No_preview = No_preview_availabel.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return No_preview;
	}

//method to check if any error code is displayed in player
	public boolean ErrorCodeDisplayed() {
		
		boolean ErrorCode = false;
		try {
			ErrorCode = Error_code.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ErrorCode;
	}

	//method to get the starting TimeCode of the standalone player
		public String GetTimeCode_in()
		{
			String StartTime = "null";
			try
			{
			WebElement VideoStart_TC = driver.findElement(By.cssSelector("div#CMP_SearchPlayerContainer_dvFrameTime"));
			StartTime=VideoStart_TC.getText();
			//int StartTime=Integer.valueOf(VideoStart_TC.getText());
			System.out.println(StartTime);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return StartTime;
		}

		//method to get total duration of the standalone player
		public String GetTotalDurationOfVideo()
		{
			String EndTime="null"; 
			try
			{
			WebElement VideoDuration=driver.findElement(By.cssSelector("span#CMP_SearchPlayerContainer_totalDuration"));
			EndTime=VideoDuration.getText();
			//EndTime=Integer.valueOf(VideoDuration.getText());
			System.out.println(EndTime);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return EndTime;

		}

		//method to click on play/pause player key
		public void ClickPlay_pausekey()
		{
		WebElement Standaloneplayer_play_pause=driver.findElement(By.cssSelector("div#CMP_SearchPlayerContainer_btnCMPPlayPause"));	
		TestUtil.Click(driver, 10, Standaloneplayer_play_pause);
		}

		//method to click on stop player key 
		public void ClickStopkey()
		{
			WebElement StopKey=driver.findElement(By.cssSelector(".Stop.setLeft"));
			TestUtil.Click(driver, 10, StopKey);
		}


	//Method to check streaming of standalone player
	public boolean CheckIfPlayerisLoading() throws InterruptedException {
		
	   boolean Streaming_status = false;
		
		boolean sliderIsDisplayed;
		boolean PlayerHeader;
		boolean PlayerControl_panel;
		boolean No_preview_availabel;
		boolean Error_code_displayed;

		searchpage = new SearchLibraryPage();
		Thread.sleep(2000);
		if (searchpage.PlayerComponents()) {
			System.out.println("player is loading");
			new WebDriverWait(driver, 200)
			.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".playerStateDisplayText")));
			if (searchpage.CheckSlider()) {
				testutil = new TestUtil();
				String TC_before = searchpage.GetTimeCode_in();
				String TC_out = searchpage.GetTotalDurationOfVideo();
				Thread.sleep(30000);
				searchpage.ClickPlay_pausekey();
				String TC_after = searchpage.GetTimeCode_in();
				System.out.println("Total duration of the video" +TC_out);
				Streaming_status=testutil.TimecodeComparison(TC_before, TC_after);
			} 
			
			  else { 
				  System.out.println(Error_code.getText());
			      System.out.println("player is loaded but it is not streaming"); 
			      }
		} 
		
		else if (searchpage.ErrorCodeDisplayed()) {
			System.out.println(Error_code.getText());
		}else if (searchpage.NoPreview()) 
		{
			System.out.println("Player loaded with No preview available screen");
		}else {
			System.out.println("player is not loading");
		}
		return searchpage.CheckSlider();
	}
	
//Method to get metadata details
	public String CheckAssetDetails() {

		WebElement TotalAssetsinLibrary = driver.findElement(By.cssSelector(".AssetDetailsList"));
		System.out.println(TotalAssetsinLibrary.getText());
		return TotalAssetsinLibrary.getText();
}

	
//Method to Verify 'i' icon is displaying , if displaying click on that	
	public boolean ClickInformationModeIcon() {
		int Total_rec_count = 0;
		String BeforeXpath = "//*[@id=\"ctl00_pageContent_ctl00_ctl0";
		String AfterXpath = "_ThumbResultHolder\"]/div/div[2]/div[1]/img";

		List<WebElement> Total_rec = driver.findElements(By.cssSelector(".searchInfoIcon.FL"));
		Total_rec_count = Total_rec.size();
		System.out.println(Total_rec.size());
		if (Total_rec.size() > 0) {
			for (int i = 0; i < Total_rec_count; i++) {
				driver.findElement(By.xpath(BeforeXpath + i + AfterXpath)).click();
				break;
			}
		}

		boolean b = driver.findElement(By.cssSelector("div#AssetDetailsTabs")).isDisplayed();
		return b;
	}

//Method to Navigate to Metadata tab and verify the contents in the tab 	
	public boolean CheckMetaDataTab() throws InterruptedException {
		TestUtil.Click(driver, 20, MetaData_tab);
		Thread.sleep(2000);
		String Metadata_text = driver.findElement(By.id("ctl00_AssetDetailsAttributes")).getText();
		System.out.println(Metadata_text);
		return MetaData_SaveBtn.isDisplayed();
	}

//Method to Navigate to CheckAuditTrial tab and verify the contents in the tab	
	public String CheckAuditTrialTab() throws InterruptedException {
		TestUtil.Click(driver, 20, AuditTrail_tab);
		Thread.sleep(3000);
		String AuditTrialTab_text = driver.findElement(By.id("AssetDetailsMain_contentwrapper")).getText();
		System.out.println(AuditTrialTab_text);
		return AuditTrialTab_text;
	}

//Method to Navigate to Essence tab and verify the contents in the tab	
	public boolean CheckEssenceTab() throws InterruptedException {
		TestUtil.Click(driver, 20, Essence_Tab);
		Thread.sleep(3000);
		String EssenceTabDetails = driver.findElement(By.id("essenceDetails")).getText();
		System.out.println(EssenceTabDetails);
		return Essence_uploadBtn.isDisplayed();
	}

//Method to Navigate to Opsaction tab and verify the contents in the tab	
	public String CheckOpsactionTab() throws InterruptedException {
		TestUtil.Click(driver, 20, Opsaction_tab);
		Thread.sleep(2000);
		String OpsTab_text = driver.findElement(By.id("AssetDetailsMain_mcontentwrapper")).getText();
		System.out.println(OpsTab_text);
		return OpsTab_text;
	}
	
//Method to check if asset is streaming from Asset player popUp
	public boolean streamingFromAssetDetailsPopUp() throws InterruptedException
	{
		StreamUtil stream = new StreamUtil();
		boolean AssetDetailsPlayer_stauts = stream.CheckIfPlayerisLoading(AssetDetailsPlayer_control_panel, SliderPoint, AssetDetailPlayer_timecode_in, AssetDetailsPlayer_totalDuration, AssetDetailPlayer_play_pause, Error_code, No_preview_availabel);
		return AssetDetailsPlayer_stauts;
	}

	
//Method to cart alert message 
	public String ClickOnCartWhenCartCoun_IsZeroAndCaptureAlertMessage()
	{
		String alert_msg="null";
		String Assets_in_cart=Cart_count.getText();
		System.out.println("Total number of assets in cart" + " " +Cart_count.getText());
		if(Assets_in_cart.equals("0"))
		{
		   TestUtil.Click(driver, 20, Cart);
		   alert_msg=EmptyCart_AlertMsg.getText();
		   System.out.println(EmptyCart_AlertMsg.getText());
		   return alert_msg;
		}
		return alert_msg;
	}
	
//Method to add assets to Bin
	public boolean AddAssetsToCart()
	{
		for(int i=1 ; i<5 ; i++)
		{
			String xpath_1="(//input[@name='restoreCheck'])[position()=";
			String xpath_2="]";
			WebElement checkbox=driver.findElement(By.xpath(xpath_1+i+xpath_2));
			if(!checkbox.isSelected())
			{
				checkbox.click();
			}
		}
		 TestUtil.Click(driver, 20, Cart);
		return Cart_page.isDisplayed();
	}
	
	//Method to Verify no. of items added cart and Count in cart page are equal
	public int GetAssetsCountInCart()
	{
		List<WebElement> Cart_Items = driver.findElements(By.cssSelector("div#deleteCartDiv"));
		return Cart_Items.size();
	}
	
	//Method to Verify filter options in SearchLibrary
	public void PerformBlankSearch()
	{
		Library_search.click();
		Lib_Search_TextArea.clear();
		Search_Btn.click();
	}
	
	public void ExpandChannelInLibrary()
	{
		//Expand_Filter.click();
		TestUtil.Click(driver, 20, Expand_Filter);
        
	}
	
	public void ClickOnShowMoreLink()
	{
		TestUtil.Click(driver, 10, Show_more_link);
	}
	
	public int GetFilterOptionsCount() throws InterruptedException
	{
		int Filter_options_count=0;
		
		searchpage=new SearchLibraryPage();
		searchpage.PerformBlankSearch();
		Thread.sleep(2000);
		searchpage.ExpandChannelInLibrary();
		searchpage.ClickOnShowMoreLink();
		List<WebElement> filter = driver.findElements(By.xpath("//ul[@class='ftlc hideFactDiv']//li/descendant::div"));
		Filter_options_count=filter.size();
		System.out.println("Total filters availabel in Search Library is" + " " +Filter_options_count);
		for(WebElement FilterName : filter )
		{
			System.out.println(FilterName.getText());
		}
		return Filter_options_count;
	}
	
	
	//method to apply one filter and check if it is applied
	public String ApplyFilter() throws InterruptedException
	{
		searchpage=new SearchLibraryPage();
		searchpage.PerformBlankSearch();
		Thread.sleep(2000);
		searchpage.ExpandChannelInLibrary();
		searchpage.ClickOnShowMoreLink();
		if(!Radio_btn.isSelected())
		{
			Radio_btn.click();
		}
		Thread.sleep(2000);
		System.out.println("Result Text displayed" +"  " +Filter_result.getText());
		return Filter_result.getText();
	}
	
//method to get sort options in the library
	public boolean CheckSortOptionInLibrary()
	{
		return Sort_option.isDisplayed();
	}
	
	//method to get sort options
	public String getSortOptions()
	{
		String Sort_options = "null";
		testutil=new TestUtil();
		testutil.MouseHover(Sort_option);
		List<WebElement> sortOptions = driver.findElements(By.cssSelector(".searchSortPanel"));
		for(int i=0 ; i<sortOptions.size() ; i++)
		{
			Sort_options = sortOptions.get(i).getText();
			System.out.println(sortOptions.get(i).getText());
		}
	    return Sort_options;
	}
	
	//public void Check if assets are displaying in ListView
	public boolean CheckListView()
	{
		return ListView.isDisplayed();
	}
	
	//public void Check if assets are displaying in GridView
	public boolean gridViewIsDisplaying()
	{
		boolean AssetsInGridView=false;
        TestUtil.Click(driver, 20, GridView_icon);
        if(GridView.isDisplayed())
        {
        	AssetsInGridView=true;
        }
        return AssetsInGridView;
	}
	
	
	
}