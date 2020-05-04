package com.qa.sonyops.pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.JavascriptUtils;
import com.qa.sonyops.util.StreamUtil;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;
import com.qa.sonyops.pages.CatalogPage;

@Listeners(TestNGListeners.class)
public class CatalogPlayerPage extends BaseClass
{
	static WebDriverWait wait;
	public static String A_title = "null";
	static TestUtil testutil;
	StreamUtil stream;
	static String Mark_in_time="null";
	static String Mark_Out_Time="null";
	CatalogPlayerPage C_playerpage;
	static int checkboxes=0;
	static int CheckBox_count=0;
	String IngestedDate=CatalogPage.UploadedOn;
	CatalogPage catalogpage;
	
	
	
@FindBy(id = "Catalog_title_holder")
WebElement SelectedAssetTitle;

@FindBy(css = "div#CMP_CatalogPlayerV2Container_dvFrameTime")
WebElement Catalogplayer_TC_IN;

@FindBy(css = "span#CMP_CatalogPlayerV2Container_totalDuration")        //span#CMP_CatalogPlayerV2Container_totalDuration  //.displayTC
WebElement Catalogplayer_TC_OUT;

@FindBy(css = "div#CMP_CatalogPlayerV2Container_btnCMPPlayPause")
WebElement Catalogplayer_play_pause;

@FindBy(css = "div#CMP_CatalogPlayerV2Container_btnCMPStop")
WebElement Catalogplayer_stopBtn;

@FindBy(xpath = "(//span[@class='ui-slider-handle ui-state-default ui-corner-all'])[3]")
WebElement Slider;

@FindBy(css = "div#player_divNoProxy")
WebElement No_Preview;

@FindBy(xpath = "//span[@class='errorMessage']")
WebElement Error_code;

@FindBy(css = "div#CMP_CatalogPlayerV2Container_btnCMPMarkIn")
WebElement Mark_in;

@FindBy(id = "CMP_CatalogPlayerV2Container_btnCMPMarkOut")
WebElement Mark_out;

@FindBy(css = "div#AddSegmentDiv")
WebElement AddSegment_popUp;

@FindBy(xpath = "//div[@class='saveBtn marR15']")
WebElement AddSeg_SaveBtn;

@FindBy(id = "alertWrapper")
WebElement Alert;

@FindBy(css = ".selStrataV2")
WebElement Select_strata;

@FindBy(css = "div#tabMarkerList")
WebElement Markers_list_tab;

@FindBy(css = "div#tabDetails")
WebElement Details_tab;

@FindBy(css = "input#musTitle")
WebElement Music_title;

@FindBy(css = ".markerDetailsTableTD2")
WebElement MarkersTab_title;

@FindBy(xpath = "//div[@title='Click here to View/Edit']")
WebElement Edit_segment;

@FindBy(xpath = "(//input[@name='chkBoxMarker'])[position()=1]")
WebElement First_segmenet_Checkbox;

@FindBy(css = ".saveBtn.marR15.FR")
WebElement SeriesStrata_SaveBtn;

@FindBy(css = "input#filmTitle")
WebElement EditSeg_AlbumTitle;

@FindBy(css = "textarea#txtRemarks")
WebElement Remarks;

@FindBy(css = "div#btnSaveCatalogV2Attrib")
WebElement EditedSeg_SaveBtn;

@FindBy(css = ".popupClose")
WebElement Close_Catalog_popUp;

//to verify popup is closed
@FindBy(xpath = "//div[@id='spnAdv-search']")
WebElement Filter;

@FindBy(css ="div#waitingCatalogPopup")
WebElement Page_load;

@FindBy(xpath = "//div[.='Error']")
WebElement Error_occured_markersList; //error occured page

@FindBy(css = "li#tabEpisode")
WebElement Episode_strata;

@FindBy(css = "input#epiTitlebox")
WebElement Episode_title;

@FindBy(css = "input#segNumber")
WebElement No_of_segments;

@FindBy(css = "textarea#summaryTitlebox")
WebElement Summary;

@FindBy(xpath = "//textarea[@bc_attribute='TG_SYNOPSIS']")
WebElement Synopsis;

@FindBy(css = "textarea#txtKeyWords")
WebElement Keywords;

@FindBy(id = "InboxSearch") 
WebElement Search;

//Initializing Object Repository
public CatalogPlayerPage()
{
	PageFactory.initElements(driver, this);
}

//Method to get asset tile in catalog popup  -Test for Linux Sonyops-Ep 1
public String GetAssetTitle()
{
	A_title = SelectedAssetTitle.getText();
	System.out.println("Title of the asset in catalog popup is" + " " +A_title);
	return A_title;             
}

//alertMessageUCWrapper
public boolean CaptureAlertMessage()
{
	
	WebElement Alert_Message = driver.findElement(By.cssSelector(".alertMessageUCWrapper"));
	boolean b=Alert_Message.isDisplayed();
	Alert_Message.getText();
	System.out.println("Alert message is displayed" +Alert_Message);
	//return Alert_Message.getText(); 
	return b;

}

//Method to Handle multiple tabs
//While allowing ChromE flash to check streaming scenarios
public void HandlingMultipleWindows()
{
	String ParentWindow=driver.getWindowHandle();
	System.out.println("Parent window id is" +" " +ParentWindow);
	testutil=new TestUtil();
	testutil.LaunchNewTab();
	Set<String> All_windows=driver.getWindowHandles();
	for(String childWindows:All_windows) 
	{
		System.out.println(childWindows);
		if(!ParentWindow.equalsIgnoreCase(childWindows))
		{
			driver.switchTo().window(childWindows);
			testutil.AllowChromeFlash();
		}
	}
	driver.switchTo().window(ParentWindow);
	
}

public void WaitTillPlayerLoads()
{
	try
	{
	TestUtil.Initialsising(driver, 80);
	throw new Exception("PlayerNotLoading Exception");
	}
	catch(Exception e)
	{
	e.printStackTrace();
	System.out.println("Did not find asset buffering or initialising");
	}
}

//Method to check asset is streaming
public boolean CheckStreaming_Catalogplayer() throws InterruptedException
{
	stream=new StreamUtil();
	boolean b=stream.CheckIfPlayerisLoading(Catalogplayer_TC_OUT, Slider, Catalogplayer_TC_IN, Catalogplayer_TC_OUT, Catalogplayer_play_pause, Error_code, No_Preview);
	return b;
}

//Method to create segments by mark-in and mark-out
public void CreateSegments() throws InterruptedException
{
	Mark_in_time = stream.GetTimeCode_in(Catalogplayer_TC_IN);
	Mark_in.click();
	Thread.sleep(30000);
	TestUtil.Click(driver, 10, Catalogplayer_play_pause);
	Mark_Out_Time = stream.GetTimeCode_in(Catalogplayer_TC_IN);
	Mark_out.click();
	System.out.println("Mark in time in the player is" +" " +Mark_in_time);
	System.out.println("Mark out time in the player is" +" " +Mark_Out_Time);
	
}

//Method to check Mark-in and Mark-out TimeCodes are same in AddSegment popUp
public boolean VerifyTimeCodesFromAddSegementPopup() throws InterruptedException
{
	boolean timecodes_status=false;
	C_playerpage=new CatalogPlayerPage();
	String markIn = driver.findElement(By.id("txtTimeStart")).getAttribute("value");
	String markout=driver.findElement(By.id("txtTimeEnd")).getAttribute("value");
	if(markIn.equals(CatalogPlayerPage.Mark_in_time) && markout.equals(CatalogPlayerPage.Mark_Out_Time) )
	{
		timecodes_status=true;
		System.out.println("Mark-in and Mark-out timecodes are matching");
	}
	return timecodes_status;

}
//method check if Add Segment PopUp is displayed post clicking on Mark-out
public String AddSegmentPopupIsDisplayed()
{
		WebElement Header = driver.findElement(By.xpath("//div[.='Add Segment']"));
		System.out.println(Header.getText());
		return Header.getText();
}

//Method to select Segment type as Music,save the segment 
//and capture success alert message
public String SelectSegmentTypeAndAddSegment(String option)
{
	WebElement Segment_dropdown=driver.findElement(By.id("selSegmentSrata"));
	Select s=new Select(Segment_dropdown);
	s.selectByVisibleText(option);
	TestUtil.Click(driver, 10, AddSeg_SaveBtn);
	System.out.println("SuccessAlert message on saving segment" +" "
	+Alert.getText());
	return Alert.getText();
}

//Method to select Music stratA from stratA dropDown
public boolean NavigateToMusicStrata()
{
	boolean MusicStrataDisplayed=false;
	testutil.HandlingDropDown(Select_strata, "Music");
	if(Markers_list_tab.isDisplayed() && Details_tab.isDisplayed())
	{
		MusicStrataDisplayed=true;
	}
	return MusicStrataDisplayed;
}

//Method to check Total segments displaying in Music StratA
public int CheckTotalSegmentsInMusicStrata()
{
	List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@id,'c4647')]"));
	System.out.println("Number of segments in music strata are" +" " +checkboxes.size());
	CheckBox_count=checkboxes.size();
	return CheckBox_count; 
}

//Method to select 1 segment,view in edit mode
public boolean ClickEdit_Segment()
{
	boolean EditSegmentStatus=false;
	if(CatalogPlayerPage.CheckBox_count>0)
	{
		if(!First_segmenet_Checkbox.isSelected())
		{
			First_segmenet_Checkbox.click();
			TestUtil.Click(driver, 5, Details_tab);
			TestUtil.Click(driver, 5, Markers_list_tab);
			First_segmenet_Checkbox.click();
			TestUtil.Click(driver, 5, Details_tab);
			EditSegmentStatus=Music_title.isDisplayed();
		}
	}
	return EditSegmentStatus;
}

//method to pass values to the edit segment page
public void FillTheFieldsInEditSegmentPage(String title,String AlbumTitle , String Remarks_value)
{
	TestUtil.Clear(driver, 10, Music_title);
	TestUtil.Sendkeys(driver, 10, Music_title, title);
	TestUtil.Clear(driver, 10, EditSeg_AlbumTitle);
	TestUtil.Sendkeys(driver, 10, EditSeg_AlbumTitle, AlbumTitle);
	TestUtil.Clear(driver, 10, Remarks);
	TestUtil.Sendkeys(driver, 10, Remarks, Remarks_value);
	
}

//Method to save updated changes of segment
public String Save_Edited_segment_Capture_successAlert()
{
	TestUtil.Click(driver, 20, EditedSeg_SaveBtn);
	System.out.println("updated changes" +Alert.getText());
	return Alert.getText();
}
//Segment Edited Successfully

//Method to capture Error message displayed when segment CheckboX is not checked
//and click on details tab
public String Error_Message_SegmentsNotSelected()
{
	C_playerpage=new CatalogPlayerPage();
	C_playerpage.NavigateToMusicStrata();
	Details_tab.click();
	System.out.println("Following message is displayed when none of the segments checkbox is checked"
			 +" " +Alert.getText());
	return Alert.getText();
}

//Method to Save Series StartA MetaData and Capture Alert message
public String SaveSeriesStrata() throws InterruptedException
{
	String Success_alert="null";
	C_playerpage=new CatalogPlayerPage();
	String Asset_Title=C_playerpage.GetAssetTitle();
	
	if(Asset_Title.contains("Test")){
		Thread.sleep(3000);
		WebElement Save_btn=driver.findElement(By.id("btnSaveCatalogV2Attrib"));
		TestUtil.Click(driver, 30, Save_btn);
		Success_alert=Alert.getText();
		System.out.println("Alert message on saving series strata" +Success_alert);
		return Success_alert;
	}else
	{
		System.out.println("Test asset is not selected");
	}
	return Success_alert;
}

//Method to click on Episode strata tab
public boolean ClickOnEpisodeStrata()
{
	TestUtil.Click(driver, 20, Episode_strata);
	return Episode_title.isDisplayed();
}

//Method to enter values to Episode strata
public void ProvideMandatoryData(String title ,String Seg ,String summary , String synop,String word)
{
	C_playerpage=new CatalogPlayerPage();
	String Asset_Title=C_playerpage.GetAssetTitle();
	
	if(Asset_Title.contains("Test"))
	{
	TestUtil.Clear(driver, 10, Episode_title);
	TestUtil.Sendkeys(driver, 5, Episode_title, title);
	TestUtil.Clear(driver, 5, No_of_segments);
	TestUtil.Sendkeys(driver, 5, No_of_segments, Seg);
	TestUtil.Clear(driver, 5, Summary);
	TestUtil.Sendkeys(driver, 5, Summary, summary);
	TestUtil.Clear(driver, 5, Synopsis);
	TestUtil.Sendkeys(driver, 5, Synopsis, synop);
	JavascriptUtils.scrollIntoView(Keywords, driver);
	TestUtil.Clear(driver, 5, Keywords);
	TestUtil.Sendkeys(driver, 5, Keywords, word);
	}
}

//method to save episode StratA 
public String SaveEpisodeStrataDataAndCaptureAlertMessage()
{
	String SuccessAlert="null";
	C_playerpage=new CatalogPlayerPage();
	String TestAssetTitle = C_playerpage.GetAssetTitle();
	if(TestAssetTitle.contains("Test"))
	{	
	WebElement save=driver.findElement(By.xpath("//div[@id='btnSaveCatalogV2Attrib']"));
	TestUtil.Click(driver, 20, save);
	SuccessAlert=Alert.getText();
	System.out.println("Success alert message on saving episode strata" +Alert.getText());
	}
	else
	{
		System.out.println("Test asset is not selected");
	}
	return SuccessAlert;
	
}

//Method to close catalog pop-up
public boolean CloseCatalogPlayerPop_up()
{
	
	TestUtil.Click(driver, 20, Close_Catalog_popUp);
	try {
	new WebDriverWait(driver,30)
	.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='waitingLoader']")));
	}catch(Exception e)
	{
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	return Filter.isDisplayed();
}

//method to select catalogManagerRole
public void navigateToManagerRole() throws Exception
{   
	C_playerpage=new CatalogPlayerPage();
	C_playerpage.CloseCatalogPlayerPop_up();
	HomePage homepage=new HomePage();
	homepage.SelectUserRole("Sony Catalog Manager");
}

//method get test asset title after performing cataloging
public void SearchforAssetOnWhichCatalogIsPerformed()
{
	TestUtil.Clear(driver, 30, Search);
	TestUtil.Sendkeys(driver, 20, Search, A_title);
	Search.sendKeys(Keys.ENTER);
}

//method to change filter status from not started to all
public void ChangeCatalogStatusToAllStatusType() throws Exception
{
	catalogpage=new CatalogPage();
	catalogpage.ClickApplyFilters();
	catalogpage.ApplyCatalogStatus("All");
}
//get catalog status of the TestAsset 
public boolean getCatalogingStatusOfTestAsset()
{
	boolean CatalogStatus = false;
	String Title_xpath_b = prop.getProperty("Title_xpath_before"); 
	String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
	
	String Status_xpath_b = prop.getProperty("Status_xpath_before");
	String Status_xpath_A = prop.getProperty("Status_xpath_After");
	
	String Uploadedtime_xapth_b= "//*[starts-with(@id,'dataContainer_')]/tr[";
	String Uploadedtime_xpath_a= "]/td[4]/div";
	
	List<WebElement> Allrows = driver.findElements(By.xpath("//*[starts-with(@id,'dataContainer_')]/tr"));
	System.out.println("Records loaded in catalogPage is " +Allrows.size());
	if(Allrows.size()>0)
	{	
	for(int i=1; i<=Allrows.size() ; i++)
	{
		//checking if title matches
		WebElement AssetTitle = driver.findElement(By.xpath(Title_xpath_b+i+Title_xpath_A));
		WebElement TestAsset_uploadedTime=driver.findElement(By.xpath(Uploadedtime_xapth_b+ i+Uploadedtime_xpath_a));
		String i_date=TestAsset_uploadedTime.getText();  //can be removed
		System.out.println(i_date);                      //can be removed
		if(AssetTitle.getText().equals(A_title) && TestAsset_uploadedTime.getText().equals(IngestedDate) )
		{
			//check if uploadedOn time matches
			//WebElement TestAsset_uploadedTime=driver.findElement(By.xpath(Uploadedtime_xapth_b+ i+Uploadedtime_xpath_a));
			//if(TestAsset_uploadedTime.getText().equals(CatalogPage.UploadedOn))
			//{
				//get the corresponding status
			WebElement Status = driver.findElement(By.xpath(Status_xpath_b+i+Status_xpath_A));
			if(Status.getText().equals("In Progress"))
			{
				CatalogStatus=true;
				return CatalogStatus;
			}
		// }
	  }
   }
	return CatalogStatus;
   }else{
		String No_records_found = driver.findElement(By.cssSelector(".CatalogingDetailsDB")).getText();
		System.out.println("No records displaying in the dashboard " + " " +No_records_found);
	    }
	return CatalogStatus;
}

}
