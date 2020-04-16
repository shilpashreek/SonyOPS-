package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;


@Listeners(TestNGListeners.class)
public class HomePage extends BaseClass 
{
	SearchLibraryPage searchlibrary;
	
	@FindBy(css = "div#paginationControl")
	WebElement Pagination;
	
	@FindBy(css = "div#spnAdv-search")
	WebElement Status_Filter;
	
	@FindBy(css = "input#InboxSearch")
	WebElement Dashboard_Search;
	
	@FindBy(css = "div#SearchV2Button")
	WebElement AssetLibrary_search;
	
	@FindBy(css = ".headerSearchTextV2")
	WebElement AssetLib_SearchField;
	
	@FindBy(css = "div#ActualSSRSReportButton")
	WebElement Reports;
	
	@FindBy(xpath = "//span[@class='username linked']")
	WebElement UserName_Link;
	
	public HomePage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public String GetHomePageTitle()
	{
		String HomepageTitle = driver.getTitle();
		return HomepageTitle;
	}
	
	public void CheckColumnsInDashboard() 
	{
		String BeforeXpath="//*[@id='InboxHeader']/table/tbody/tr/th[";
		String AfterXpath = "]";
		List<WebElement> Columns = driver.findElements(By.xpath(prop.getProperty("Column_xpath")));
		int Col_Count = Columns.size();
		System.out.println("Column count in dashboard is" + " " +Col_Count + " "  +"and following are the Columns");
		for(int i=1; i<=Col_Count ; i++)
		{
			WebElement Col_Name = driver.findElement(By.xpath(BeforeXpath + i + AfterXpath));
			System.out.println(Col_Name.getText());
			if(Col_Name.getText().equals("Subtitle"))
			{
				System.out.println("Column name" +Col_Name.getText() +" is found at position" + " " +i);
				Assert.assertEquals(i, 6 , " Subtitle column is not found at expected position");
			}
		}
	}
	
	public CatalogPage SelectCatalogUserRole() throws InterruptedException
	{
		WebElement User_role = driver.findElement(By.xpath(prop.getProperty("UserRole_xpath")));
		Actions act = new Actions(driver);
		act.moveToElement(User_role).click().build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath(prop.getProperty("SonyCatalog_xpath"))).click();
		return new CatalogPage();
	}
	
	public SearchLibraryPage SearchTestAsset()
	{
		TestUtil.Click(driver, 20, AssetLibrary_search);
		TestUtil.Sendkeys(driver, 10, AssetLib_SearchField, "Test");
		driver.findElement(By.xpath("//div[@class='searchBtn']")).click();
		return new SearchLibraryPage();
	}
	
	public String GetPageUrl()
	{
		System.out.println(driver.getCurrentUrl());
		return driver.getCurrentUrl();
		
	}
	
}
