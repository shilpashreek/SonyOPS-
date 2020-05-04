package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPlayerPage;
import com.qa.sonyops.util.TestUtil;
import com.qa.sonyops.pages.CatalogPage;

public class CatalogManagerPage extends BaseClass 
{
	CatalogPlayerPage C_playerpage;
	CatalogPage catalogpage;
	String Catalog_title=CatalogPlayerPage.A_title;
	CatalogManagerPage c_manager;
	static String SelectedAsset = "null";
	
	
	@FindBy(id = "InboxSearch") 
	WebElement Search;
	
	@FindBy(css = ".popupHeader")
	WebElement catalogStatus_popUp;
	
	@FindBy(xpath = "//div[.='In Progress']")
	WebElement Inprogress_status;
	
	@FindBy(css = ".roleBorderStatus.UpdateJobStatusTable")
	WebElement CatalogStatus_text;
	
	@FindBy(id = "btnSbmt")
	WebElement catalogStatus_submitBtn;
	
	@FindBy(id = "alertWrapper")
	WebElement Alert;
	
	@FindBy(css = ".CatgStatusCompleted.linkedItem")
	WebElement Completed_status;
	
	@FindBy(id = "CatalogQCStatus_title_holder")
	WebElement CatalogQCStatus_popUp;
	
	public CatalogManagerPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	//method to search test asset
	public void searchTestAsset()
	{
		TestUtil.Sendkeys(driver, 30, Search, CatalogPage.AssetTitle);
		Search.sendKeys(Keys.ENTER);
		
	}
	//method to get catalog status
	public boolean getCurrentCatalogStatus(String Title_to_Compare , String Ingested_date ,String Status_to_compare) throws Exception
	{
		boolean CatalogStatus = false;
		String Title_xpath_b = prop.getProperty("Title_xpath_before");  //title column
		String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
		
		String Status_xpath_b = prop.getProperty("Status_xpath_before"); //status column
		String Status_xpath_A = prop.getProperty("Status_xpath_After"); ////*[starts-with(@id,'dataContainer_')]/tr[
		
		String Uploadedtime_xapth_b= "//*[starts-with(@id,'dataContainer_')]/tr[";  //ingested date and time
		String Uploadedtime_xpath_a= "]/td[4]/div";
		
		List<WebElement> Allrows = driver.findElements(By.xpath("//*[starts-with(@id,'dataContainer_')]/tr"));
		System.out.println("Records loaded in catalogPage is " +Allrows.size());
		if(Allrows.size()>0)
		{
			for(int i=1 ; i<=Allrows.size() ; i++)
			{
				WebElement title = driver.findElement(By.xpath(Title_xpath_b+i+Title_xpath_A));
				WebElement uploadedDate_Time = driver.findElement(By.xpath(Uploadedtime_xapth_b+i+Uploadedtime_xpath_a));
				if(title.getText().equals(Title_to_Compare) && uploadedDate_Time.getText().equals(Ingested_date))
				{
					Thread.sleep(2000);
					WebElement status = driver.findElement(By.xpath(Status_xpath_b+i+Status_xpath_A));
					
					if(status.getText().equals(Status_to_compare))
					{
						CatalogStatus=true;
						return CatalogStatus; 
					}
				}
			}
		}
		return CatalogStatus; 
	}
	
	//method to check if catalog status is changed from not started to InProgress
	public void VerifyChangeInCatalogStatus() throws Exception
	{
		catalogpage=new CatalogPage();
    	catalogpage.ClickApplyFilters();
    	catalogpage.ApplyCatalogStatus("In Progress");
    	c_manager=new CatalogManagerPage();
    	Thread.sleep(3000);
    	c_manager.searchTestAsset();
	}
	
	public boolean getInProgressAsset() throws Exception
	{
		catalogpage=new CatalogPage();
		catalogpage.SearchForTestAsset("test");
		catalogpage.ClickApplyFilters();
		catalogpage.ApplyCatalogStatus("In Progress");
		Thread.sleep(3000);
		c_manager=new CatalogManagerPage();
		Thread.sleep(3000);
		c_manager.selectTestAssetBasedOnCatalogStatus("Test", "In Progress", Inprogress_status);
		return catalogStatus_popUp.isDisplayed();
	
	}
	
	public String selectTestAssetBasedOnCatalogStatus(String Asset_title , String CatalogStatus , WebElement ele)
	{
		
		String Title_xpath_b = prop.getProperty("Title_xpath_before"); 
		String Title_xpath_A = prop.getProperty("Title_xpath_After"); 
		
		String Status_xpath_b = prop.getProperty("Status_xpath_before");
		String Status_xpath_A = prop.getProperty("Status_xpath_After");
		
		List<WebElement> Allrows = driver.findElements(By.xpath("//*[starts-with(@id,'dataContainer_')]/tr"));
		System.out.println("Records loaded in catalogPage is " +Allrows.size());
		
		for(int i=1; i<=Allrows.size() ; i++ )
		{
			WebElement AssetTitle=driver.findElement(By.xpath(Title_xpath_b+i+Title_xpath_A));
			SelectedAsset=AssetTitle.getText();
			WebElement Catalog_status=driver.findElement(By.xpath(Status_xpath_b+i+Status_xpath_A));
			if(AssetTitle.getText().contains(Asset_title) && Catalog_status.getText().equals(CatalogStatus))
			{
				ele.click();
				return SelectedAsset;
			}
		 }
		return SelectedAsset;
	 }
	
	public String getTheTextOnCatalogStatusCompletePopUp()
	{
		String CatalogStatusMessage="null";
		if(catalogStatus_popUp.isDisplayed())
		{
			CatalogStatusMessage=CatalogStatus_text.getText();
			System.out.println(CatalogStatusMessage);
		}
	  return CatalogStatusMessage;
	}
	
	public boolean submitCatalogStatus()
	{
		TestUtil.Click(driver, 20, catalogStatus_submitBtn);
		System.out.println(Alert.getText());
		return Alert.isDisplayed();

	}
	
	public String ClickOnCompletedLink() throws Exception
	{//CatalogManagerPage.SelectedAsset
		catalogpage=new CatalogPage();
		catalogpage.SearchForTestAsset(CatalogManagerPage.SelectedAsset);
		catalogpage.ClickApplyFilters();
		catalogpage.ApplyCatalogStatus("All");
		Thread.sleep(3000);
		c_manager=new CatalogManagerPage();
		Thread.sleep(3000);
		c_manager.selectTestAssetBasedOnCatalogStatus(CatalogManagerPage.SelectedAsset, "Completed", Completed_status);
		if(CatalogQCStatus_popUp.isDisplayed())
		{
			//Do you want to submit Catalog-QC status?
			return CatalogStatus_text.getText();
			
		}
		return CatalogStatus_text.getText();
		
	}
	
}
