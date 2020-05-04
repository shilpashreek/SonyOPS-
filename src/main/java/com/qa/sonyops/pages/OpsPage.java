package com.qa.sonyops.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.TestUtil;

public class OpsPage extends BaseClass
{
	TestUtil testutil;
	
	@FindBy(id = "SHOWS")
	WebElement Shows_lable;

	@FindBy(id = "paginationControl")
	WebElement Pagination_control;

	@FindBy(id = "spnAdv-search")
	WebElement Filter_icon;
	
	@FindBy(css = ".searchBtn.FR.marT5")
	WebElement Filter_popUp_Search;

	@FindBy(id = "InboxSearch")
	WebElement SearchField;

	@FindBy(id = "waiting")
	WebElement Page_loading;

	@FindBy(id = "resetSearch")
	WebElement Reset_search;
	
	@FindBy(id="SPNCatalogStatusType")
	WebElement Catalog_status;
	
	@FindBy(id = "SPNSubtitleStatus")
	WebElement Subtitle_status;
	
	@FindBy(id = "SPNPackageStatus")
	WebElement Publish_package_status;
	
	@FindBy(id = "SPNDeliveryStatus")
	WebElement Delivery_status;
	
	@FindBy(id = "Smart Tasker")
	WebElement SmartTasker_tab;

	 //Initializing object Repository
	public OpsPage()
	{
		PageFactory.initElements(driver, this);
	}

	//method to get the label of the landing page
	public boolean GetThelandingPageLabel()
	{
		new WebDriverWait(driver,30)
		.until(ExpectedConditions.invisibilityOf(Page_loading));
		System.out.println(Shows_lable.getText());
		return Shows_lable.isDisplayed();
	}
	
	//method to check if pagination icon is displaying
	public boolean checkPageIcon()
	{
		new WebDriverWait(driver,30)
		.until(ExpectedConditions.invisibilityOf(Page_loading));
		System.out.println(Pagination_control.getText());
		return Pagination_control.isDisplayed();
	
	}
	
	//method to get all the columns
	public List<String> getColumnsCountAndName()
	{
		List<String > ColumnList = new ArrayList<String>();
		List<WebElement> Columns = driver.findElements(By.xpath("//*[@id=\"InboxHeader\"]/table/tbody/tr/th"));
		int Col_count = Columns.size();
		System.out.println("Columns in shows page" +" " +Col_count);
		String b_xpath = "//*[@id=\"InboxHeader\"]/table/tbody/tr/th[";
		String a_xpath = "]";
		System.out.println("Following are the columns in shows tab");
		for(int i=1 ; i<=Col_count ; i++)
		{
			WebElement col_name = driver.findElement(By.xpath(b_xpath+i+a_xpath));
			ColumnList.add(col_name.getText());
			System.out.println(col_name.getText());
		}
		return ColumnList;
	}
	
	public List<String> TargetColumnList()
	{
		String Exp_col_list[] = {"Title","Content Type","Channel","Uploaded On","Catalog","Subtitle","Publish","Delivery","Republish","Republish Status","Republish Count"};
		List<String> Expected_Columns = new ArrayList<String>();
		Expected_Columns.addAll(Arrays.asList(Exp_col_list));
		return Expected_Columns;
    }
	
	//method to verify filter pop-up is displaying
	public boolean ClickOnfilterIcon()
	{
		TestUtil.Click(driver, 30, Filter_icon);
		return Filter_popUp_Search.isDisplayed();
	}
	
	//method to get all the CatalogStatus filter options
	public void getAllFilterOptions()
	{
		testutil=new TestUtil();
		testutil.getDropDownValues(Catalog_status);
		testutil.getDropDownValues(Subtitle_status);
		testutil.getDropDownValues(Publish_package_status);
		testutil.getDropDownValues(Delivery_status);
	}
	//method to check search reset FunctionailtY
	public boolean SearchResetFunctionality()
	{
		
		TestUtil.Click(driver, 20, Reset_search);
		return Page_loading.isDisplayed();
		
	}
	
	//method to click on SmarttaskeR tab
	public SmarttaskerPage ClickOnSmartTaskerTab()
	{
		TestUtil.Click(driver, 30, SmartTasker_tab);
		return new SmarttaskerPage();
	}
	
}
