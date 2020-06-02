package com.qa.sonyops.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.JavascriptUtils;
import com.qa.sonyops.util.TestUtil;

public class SubtitlePage extends BaseClass
{
	CatalogPage catalogpage;
	TestUtil testutil;
	SubtitlePage subtitlepage;
	String TotalPagesCount="null";
	int pageCount = 0;
	static String SubtitleAsset = "null";
	SearchLibraryPage searchpage;
	
@FindBy(id = "InboxSearch")
WebElement SearchField;

@FindBy(xpath = "//select[@id='SPNSubtitleStatus']")
WebElement subtitle_filter;

@FindBy(css = ".waitingWrap")
WebElement Page_load;

@FindBy(css = ".searchBtn.FR.marT5")
WebElement FilterSearch;

@FindBy(id = "displayTotal")
WebElement TotalPageCount;

@FindBy(css = ".linked.linkedItem")
WebElement Confirm_link;

@FindBy(id = "navNext")
WebElement Navigate_To_Next_Page;

@FindBy(id = "alertWrapper")
WebElement Alert;

@FindBy(id = "btnSbmt")
WebElement Subtitle_status_submitBtn;

@FindBy(id="essenceUploadBtnV1")
WebElement Essence_Upload_Btn;

@FindBy(id = "browseBtn")
WebElement Essence_browseBtn;

@FindBy(css = "div#uploadbtn")
WebElement UploadBtn_EssenceTab;
          
     public SubtitlePage()
     {
	   PageFactory.initElements(driver, this);
     }

    public void waitForThePageToLoad()
    {
    	try {
    		new WebDriverWait(driver,5).
        	until(ExpectedConditions.invisibilityOf(Page_load));
    	}catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
    
    //method to get all the options from subtitle status filter options
    public List<String> getSubtitleFilterOptions() throws Exception
    {
    	
    	try {
    	subtitlepage=new SubtitlePage();
    	subtitlepage.waitForThePageToLoad();
    	}catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	catalogpage=new CatalogPage();
    	//Thread.sleep(3000);
    	catalogpage.ClickApplyFilters();
    	Select s=new Select(subtitle_filter);
    	List<WebElement> subtitle_status_options = s.getOptions();
    	List<String> SubtitleFilter=new ArrayList<String>();
    	for(WebElement subtitle_status:subtitle_status_options)
    	{
    		SubtitleFilter.add(subtitle_status.getText());
    	}
    	
    	System.out.println(SubtitleFilter);
    	return SubtitleFilter;
    }
    
    //method to compare actual and expected filter options 
    public List<String> targetSubtitleFilterOptions()
    {
    	String SubtitleStatus[] = {"All" , "Pending" , "Completed"};
    	List<String> a=new ArrayList<String>();
    	a.addAll(Arrays.asList(SubtitleStatus));
    	return a;
    }
    
    //method to apply subtitle pending status
    public void ApplySubtitleStatus_pending() throws Exception
    {
    	catalogpage=new CatalogPage();
    	subtitlepage=new SubtitlePage();
    	Thread.sleep(4000);
    	catalogpage.ClickApplyFilters();
    	testutil=new TestUtil();
    	testutil.HandlingDropDown(subtitle_filter, "Pending");
    	TestUtil.Click(driver, 10, FilterSearch);
    	
    }
    
    //method to get total count of pages from subtitle dashboard
    public void getTotalPageCountFromPagination()
    {
        TotalPagesCount=TotalPageCount.getText();
        System.out.println(TotalPagesCount);
        pageCount = Integer.parseInt(TotalPagesCount);
    }
    
    //method to search and click on the confirm link
    public String ClickOnConfirmLink() throws InterruptedException
    {
    	String SuccessAlert = "null";
    	String Title_xpath_b = prop.getProperty("Title_xpath_before"); 
    	String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
    	
    	String Subtitle_statusXpath_b = "//*[starts-with(@id , 'dataContainer_')]/tr[";
    	String Subtitle_statusXpath_A = "]/td[6]/div";
    	
    	//iterate through pages
		for(int i=1 ; i<=pageCount ; i++)   //.linked.linkedItem  xpath("//div[@class='linked linkedItem']")
		  { 
			List<WebElement> Confirm_links = driver.findElements(By.cssSelector(".linked.linkedItem"));
			int Total_confirm_links = Confirm_links.size();
	    	System.out.println("Confirm links in subtitle dashboard" +" " +Total_confirm_links);
			  if(Total_confirm_links>1)
			  {
		          for(i=1; i<=Total_confirm_links ;i++)
		          {
				  WebElement TestAsset=driver.findElement(By.xpath(Title_xpath_b+i+Title_xpath_A));
		          SubtitleAsset = TestAsset.getText();
		          System.out.println("Subtitle is confirmed for the asset" +SubtitleAsset);
		          WebElement SubtitleStatus=driver.findElement(By.xpath(Subtitle_statusXpath_b+i+Subtitle_statusXpath_A));
		          
		        //  if(SubtitleAsset.contains("Test") && SubtitleStatus.getText().equals("Confirm"))
		          if(SubtitleAsset.contains("Test") && SubtitleStatus.isEnabled() )  
		          {
		        	  JavascriptUtils.scrollIntoView(Confirm_link, driver);
		        	  TestUtil.Click(driver, 20, Confirm_link);
		        	  TestUtil.Click(driver, 20, Subtitle_status_submitBtn);
		        	  //subtitlepage=new SubtitlePage();
		          	  //subtitlepage.waitForThePageToLoad();
		        	  SuccessAlert=Alert.getText();
		        	  return SuccessAlert;
		          }
			  }
			  }else  {
				  TestUtil.Click(driver, 10, Navigate_To_Next_Page);
				  Thread.sleep(3000);
				  }
		  }
		return SuccessAlert;
    }
    
    //method to check if subtitle status is changed from confirm to confirmed
    public boolean getSubtitleStatusAfterSubmitting() throws Exception
    {
    	boolean Subtitle_status = false;
    	
    	catalogpage=new CatalogPage();
    	Thread.sleep(3000);
    	catalogpage.ClickApplyFilters();
    	testutil=new TestUtil();
    	testutil.HandlingDropDown(subtitle_filter, "Completed");
    	TestUtil.Click(driver, 10, FilterSearch);
    	Thread.sleep(2000);
    	SearchField.clear();
    	TestUtil.Sendkeys(driver, 20, SearchField, SubtitleAsset);
    	SearchField.sendKeys(Keys.ENTER);
    	Thread.sleep(3000);
    	String Title_xpath_b = prop.getProperty("Title_xpath_before"); 
    	String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
    	
    	String Subtitle_statusXpath_b = "//*[starts-with(@id , 'dataContainer_')]/tr[";
    	String Subtitle_statusXpath_A = "]/td[6]/div";
    	List<WebElement> rows = driver.findElements(By.xpath("//*[starts-with(@id , 'dataContainer_')]/tr"));
    	
    	for(int i=1; i<=rows.size() ; i++)
    	{
    		WebElement title = driver.findElement(By.xpath(Title_xpath_b+i+Title_xpath_A));
    		WebElement SubtitleStatus=driver.findElement(By.xpath(Subtitle_statusXpath_b+i+Subtitle_statusXpath_A));
    		if(title.getText().equals(SubtitleAsset) && SubtitleStatus.getText().equals("Confirmed"))
    		{
    			Subtitle_status=true;
    			return Subtitle_status;
    		}
    	}
    	return Subtitle_status;
    }
    
    //method to click on first test asset
    public void TestAssetwithConfirmedStatus()
    {
    	String Title_xpath_b = prop.getProperty("Title_xpath_before"); 
    	String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
    	
    	String Subtitle_statusXpath_b = "//*[starts-with(@id , 'dataContainer_')]/tr[";
    	String Subtitle_statusXpath_A = "]/td[6]/div";
    	
    	List<WebElement> rows = driver.findElements(By.xpath("//*[starts-with(@id , 'dataContainer_')]/tr"));
    	
    	for(int i=1 ; i<=rows.size() ;i++)
    	{
    		WebElement TestAsset = driver.findElement(By.xpath(Title_xpath_b + i + Title_xpath_A));
    		WebElement status=driver.findElement(By.xpath(Subtitle_statusXpath_b+i+Subtitle_statusXpath_A));
    		if(TestAsset.getText().contains("Test") && status.getText().equals("Confirmed"))
    		{
    			try {
    			TestUtil.Click(driver, 20, TestAsset);
    			}catch(Exception e)
    			{
    				System.out.println(e.getMessage());
    			}
    		}
    	}
    	WebElement EssenceTab=driver.findElement(By.xpath("//div[@id='Essence']"));
    	TestUtil.Click(driver, 30, EssenceTab);
    }
    
    //method validate subtitle player metadata
    public String EssenceUpload() throws Exception
    {
    	catalogpage=new CatalogPage();
    	Thread.sleep(3000);
    	catalogpage.ClickApplyFilters();
    	testutil=new TestUtil();
    	testutil.HandlingDropDown(subtitle_filter, "Completed");
    	TestUtil.Click(driver, 10, FilterSearch);
    	Thread.sleep(3000);
    	subtitlepage=new SubtitlePage();
    	subtitlepage.TestAssetwithConfirmedStatus();
    	TestUtil.Click(driver, 20, Essence_Upload_Btn);
    	String SuccessAlert=subtitlepage.selectEssenceType();
    	return SuccessAlert;
    
    }
    
    //method to fill all mandatory fields ,browse and upload subtitle file
    public String selectEssenceType() throws Exception
    {
    	WebElement EssenceType=driver.findElement(By.xpath("//select[@id='ddlEssenceType']"));
    	WebElement language=driver.findElement(By.xpath("//select[@id='ddlLanguage']"));
        testutil=new TestUtil();
        testutil.HandlingDropDown(EssenceType, "Subtitles");
        Thread.sleep(2000);
        testutil.HandlingDropDown(language, "English");
        Thread.sleep(2000);
    	TestUtil.Click(driver, 20, Essence_browseBtn);
    	Thread.sleep(10000);
    	Runtime.getRuntime().exec("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\AutoITscripts\\EssenceUpload.exe");
    	WebElement Essence_Format=driver.findElement(By.xpath("//select[@class='essFormatDropDown']"));
    	testutil.HandlingDropDown(Essence_Format, "SRT");
    	Thread.sleep(3000);
    	TestUtil.Click(driver, 20, UploadBtn_EssenceTab);
    	Thread.sleep(4000);
    	//Runtime.getRuntime().exec("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\AutoITscripts\\DenyAsperaConnect.exe");
    	Runtime.getRuntime().exec("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\AutoITscripts\\AllowAsperaConnections.exe");
    	try {
    	System.out.println(Alert.getText());
    	return Alert.getText();
    	}catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	return Alert.getText();
    }
    
    
    
    
    
    
    
}
