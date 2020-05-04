package com.qa.sonyops.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;

@Listeners(TestNGListeners.class)
public class CatalogPage extends BaseClass
{
	static TestUtil testutil;
	public static String AssetName;
	WebDriverWait wait;
	public static String AssetTitle = "null";
	static int AutoSuggestions_count;
	public static String Selected_Asset;
	public static String Error_msg;
	public static String UploadedOn = "null";
	static String Asset_Title;
	boolean CatalogStatus=false;
	
@FindBy(id = "ddSelectedRole")
WebElement Current_role;

@FindBy(id = "CATALOGING")
WebElement Label;

@FindBy(xpath = "//div[@id='spnAdv-search']")
WebElement Filter;

@FindBy(css = "img#spn-startdate-search_selector")
WebElement StartDate;

@FindBy(css = "img#spn-enddate-search_selector")
WebElement EndDate;

@FindBy(css = ".searchBtn.FR.marT5") 
WebElement Filter_Search;
	 
@FindBy(xpath = "/html/body/div[6]/table/thead/tr[2]/td[2]")      
WebElement Previous_Month;

@FindBy(xpath= "//td[contains(@class,'day selected')")
WebElement TodaysDate_holder;

@FindBy(css = ".spnsmartFilterStatusSelect")
WebElement cat_filter_field;

@FindBy(css = "input#InboxSearch")
WebElement Page_Search_btn;

@FindBy(css = "span#displayTotal")
WebElement No_Of_Total_Pages;           

@FindBy(css = ".headerSearchBtnV2")
WebElement Library_Search;

@FindBy(css = ".headerSearchTextV2")
WebElement SearchTextArea;


public CatalogPage()
{
	PageFactory.initElements(driver, this);
}

public String GetCurrentRoleDetails()
{
	String RoleName = Current_role.getText();
    System.out.println(RoleName);
    return RoleName;
}

public String GetDashboardLabel()
{
	String DashboardName = Label.getText();
	System.out.println(DashboardName);
	return DashboardName; 
}

public boolean ClickApplyFilters() throws InterruptedException
{   
	boolean b= false;
	try {
	TestUtil.Click(driver, 30, Filter);
	b=Filter_Search.isDisplayed();
	}
	catch(Exception e)
	{
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	return b;
}

public void ClickOnStartDate()
{
	StartDate.click();
}

public boolean ApplyCatalogStatus(String status) throws InterruptedException
{
	
    WebElement Catalogstatus = driver.findElement(By.xpath(prop.getProperty("CatalogStatus_xpath")));
    Select s = new Select(Catalogstatus);
    List<WebElement> Status_options = s.getOptions();
    //iterate status options
    for(WebElement cat_status_options:Status_options)
    {
    	System.out.println(cat_status_options.getText());
    }
   s.selectByVisibleText(status);
   Filter_Search.click();
   return cat_filter_field.isDisplayed();
    
}

public void SearchForTestAsset(String AssetName ) throws InterruptedException
{
	new WebDriverWait(driver,30).
	until(ExpectedConditions.visibilityOf(Page_Search_btn));
	for(int i=1;i<3;i++)
	{	
	Page_Search_btn.clear();
	Thread.sleep(2000);
	}
	Page_Search_btn.sendKeys(AssetName);
	Page_Search_btn.sendKeys(Keys.ENTER);
	
}

public int FilterResults() throws InterruptedException
{
	wait=new WebDriverWait(driver,30);
	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".CatgStatusNotStarted[title='Catalog not Started']")));
	List<WebElement> CatalogColumn = driver.findElements(By.cssSelector(".CatgStatusNotStarted[title='Catalog not Started']"));
	int FilterResultCount=CatalogColumn.size();
	System.out.println("Filter Results in current page are" +" " +FilterResultCount);
		
		  for(WebElement AppliedFilterName:CatalogColumn) 
		  { 
			  if(AppliedFilterName.getText().equals("Not Started")) 
			  {
		        System.out.println("Reseults are filtered accordingly"); 
		        break; 
			  }
			  else
			  {
				  System.out.println("Filter results are not sorted accordingly");
			  }
		  
		  }
		 
	return FilterResultCount;
}

public String GetNumberOfPages()
{
	String CountWhenPageLoads=No_Of_Total_Pages.getText();
	return CountWhenPageLoads;
}
public String ClickOnFirstTestAsset(String filter)
{
	String Title_xpath_b = prop.getProperty("Title_xpath_before"); 
	String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
	
	String Status_xpath_b = prop.getProperty("Status_xpath_before");
	String Status_xpath_A = prop.getProperty("Status_xpath_After");
	
	String Uploadedtime_xapth_b= "//*[starts-with(@id,'dataContainer_')]/tr[";
	String Uploadedtime_xpath_a= "]/td[4]/div";
	
	List<WebElement> Allrows = driver.findElements(By.xpath("//*[starts-with(@id,'dataContainer_')]/tr"));
	System.out.println("Records loaded in catalogPage is " +Allrows.size());
	for(int i =1; i<=Allrows.size() ; i++)
	{
		WebElement Status_Column = driver.findElement(By.xpath(Status_xpath_b + i + Status_xpath_A));
		if(Status_Column.getText().equals(filter))
		{
			WebElement Title_column= driver.findElement(By.xpath(Title_xpath_b + i + Title_xpath_A ));
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			AssetTitle = Title_column.getText();
			//adding to store time //UploadedOn
			WebElement TestAsset_uploadedTime=driver.findElement(By.xpath(Uploadedtime_xapth_b + i + Uploadedtime_xpath_a));
			UploadedOn = TestAsset_uploadedTime.getText();
			System.out.println("Test file ingested date and time" + " " +UploadedOn);
			System.out.println(AssetTitle);
			Title_column.click();
			break;
		}
	}
 return AssetTitle;
	
}

//this method will click on search icon and pass search text to the search field
public void ClickOnLibrarySearch(String SearchText)
{
	TestUtil.Click(driver, 20, Library_Search);
	TestUtil.Sendkeys(driver, 5, SearchTextArea, SearchText);
	
}


public int CaptureAutoSuggesstions()
{
	List<WebElement> AutoSuggestions = driver.findElements(By.xpath(prop.getProperty("suggestion_list")));
	TestUtil.TakeScreenshot_pass_fail(this.getClass().getName());
	for(WebElement SuggestionList : AutoSuggestions)
	{
		System.out.println(SuggestionList.getText());
	}
	int AutoSuggestions_count=AutoSuggestions.size();
	return AutoSuggestions_count;
	
}


public String CheckIfSuggestionListIsdisplaying()
{
	String Selected_Asset=null;
	String Error_msg=null;
	
	List<WebElement> AutoSuggestions = driver.findElements(By.xpath(prop.getProperty("suggestion_list")));
	int AutoSuggestions_count=AutoSuggestions.size();
	System.out.println("total suggestions for searched keyword is" +" " +AutoSuggestions_count);
	boolean Suggestions_disaplyed = AutoSuggestions.size()>0;
	System.out.println(Suggestions_disaplyed);
	if(AutoSuggestions.size()>0)
	{
		System.out.println("Suggestions are as follows");
		for(WebElement suggestion_name:AutoSuggestions)
		{
			System.out.println(suggestion_name.getText());
		}
		for(int i=0; i<AutoSuggestions.size(); i++)
		{
			System.out.println(AutoSuggestions.get(i).getText());
			if(AutoSuggestions.get(i).getText().contains("Test"))  
			{
				Selected_Asset=AutoSuggestions.get(i).getText();
				System.out.println("Asset selected from suggestion list is" + " " +Selected_Asset);
				AutoSuggestions.get(i).click();
				break;
			}
		}
	}else 
	 { 
		System.out.println("Autosuggestions are not displaying");
		driver.findElement(By.xpath("//div[@class='searchBtn']")).click();
		Error_msg=driver.findElement(By.cssSelector(".searchIncorrect")).getText();
		System.out.println(Error_msg);
		return Error_msg;
	 }
	return Selected_Asset;
	
}


public String GetAssetTitleFromAssetLibrary()
{
	String Search_result=driver.findElement(By.xpath("(//div[@class='libFieldWrapper lib_MAINTITLE'])[2]")).getText();
	System.out.println(Search_result);
	return Search_result;
	
}

}

