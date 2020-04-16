package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;


@Listeners(TestNGListeners.class)
public class HomePageCE extends BaseClass
{
	static TestUtil testutil;
	
@FindBy(css = "div[class=clearInsideLogo]")
WebElement SonyOpsLogo;

@FindBy(id = "TRANSFER HISTORY")
WebElement TransferHistory_Label;

@FindBy(css = "div#PaginationControlContainer" )
WebElement PaginationIcon;

@FindBy(css = "input#InboxSearch")  
WebElement SearchField;

@FindBy(css = "div.searchResetBtn")
WebElement ResetSearch;

@FindBy(css = "div.uploadButton")
WebElement UploadButton;

@FindBy(css = "div.refreshButton")
WebElement RefreshIcon;

@FindBy(xpath = "//div[@class='headerLinksWrap blue']")
WebElement ActiveUserName;

@FindBy(xpath = "//div[@title='Plug-in Downloads']")
WebElement Plug_inDowloadIcon;

@FindBy(xpath = "//div[@class='headerLogoutBtn']")
WebElement LogoutBtn;

//pagination objects

@FindBy(xpath = "//div[@class='firstBtn']")
WebElement FirstButton;

@FindBy(xpath = "//div[@class='prevBtn']")
WebElement PreviousBtn;

@FindBy(xpath = "//div[contains(@class,'nextBtn')]")
WebElement NextBtn;

@FindBy(xpath = "//div[contains(@class,'lastBtn')]")
WebElement LastBtn;

@FindBy(css = "div.headerLogoutBtn")
WebElement Logout_Button;

@FindBy(css = ".waitingWrap")
WebElement Page_load;

//xpath of Ce title row - //th[2]/../following-sibling::tr/td[2]

//initialising object repository
  public HomePageCE()
  {
	  PageFactory.initElements(driver, this);
  }
  
//actions to be performed
  public String GetHomePageTitle()
  {
	String HomePageTitle= driver.getTitle();
	System.out.println(HomePageTitle);
	return HomePageTitle;
  }
  
  public boolean CheckSonyOPsLogo()
  {
	  boolean SonyLogo = SonyOpsLogo.isDisplayed();
	  return SonyLogo;
  }
  
  public void CheckForTransferHistoryLabel()
  {
	  boolean TransferHistory = TransferHistory_Label.isDisplayed();
	  String TransferHistoryHeader = TransferHistory_Label.getText();
	  System.out.println("TransferHistory label displayed" +" " +TransferHistory);
	  System.out.println(TransferHistoryHeader);
  }
  
  public boolean VerifyRefreshIconIsPresent()
  {
	 System.out.println("RefreshIcon is displayed in homepage" +" " +RefreshIcon.isDisplayed());
	 System.out.println("RefreshIcon is enabled" +" "+RefreshIcon.isEnabled());
	 return RefreshIcon.isDisplayed();
	 
  }
  
  public boolean Aspera_PluginIsDisplayed()
  {
	  boolean b=Plug_inDowloadIcon.isDisplayed();
	  return b;
  }
  
  public boolean  Aspera_PluginIsEnabled() 
  {
	  boolean b = Plug_inDowloadIcon.isEnabled();
	  return b;
  }
  
  public Plugin_DownloadsPage ClickAsperaPluginIcon()
  {
	  Plug_inDowloadIcon.click();
	  return new Plugin_DownloadsPage();
  }
  
  public CEuploadPage ClickOnUpload()
  {
	  UploadButton.click();
	  return new CEuploadPage();
  }
  
  public void UploadDetailsLink()
  {
	  List<WebElement> AssetLink = driver.findElements(By.xpath("//th[2]/../following-sibling::tr/td[2]"));
	  System.out.println("Transfer history entries in First Page" +AssetLink.size());
	  
	  //traversing the list
	  for(int i=0; i<AssetLink.size(); i++)
	  {
		  if(i==3)
		  {
		   AssetLink.get(i).click();
		   break;
		  }
		 
	  }
	 testutil=new TestUtil(); 
	 testutil.SwitchToFrame();
	  
  }
  
  public int GetColumns()
  {
	  String ColBeforeXpath = "//*[@id='gTransferHistory']/tbody/tr[1]/th[";
	  String ColAfterXpath = "]";
	  List<WebElement> col = driver.findElements(By.xpath("//*[@id='gTransferHistory']/tbody/tr[1]/th"));
	  System.out.println("Number of columns in Home page are" +col.size());
	  System.out.println("Column values are");
	  for(int i=1 ; i<=col.size() ; i++)
	  {
		  WebElement ColName = driver.findElement(By.xpath(ColBeforeXpath + i + ColAfterXpath));
		  System.out.println(ColName.getText());
	  }
	  
	  return col.size();
  
  }
  
  public boolean GetRowCount()   
  {
	 boolean Rows_displayed=false;
	List<WebElement> rows = driver.findElements(By.xpath("//*[@id='gTransferHistory']/tbody/tr"));
	int totalRows = rows.size();
	System.out.println("total number of rows present in the Dashboard are" +totalRows);
	if(totalRows>0)
	{
		Rows_displayed=true;
	}
	else
	{
		System.out.println("dashboard is not loading");
	}
	return Rows_displayed;
  }
  
  public boolean CheckIfPaginationIsPresent()
  {
	  return PaginationIcon.isDisplayed();
  }
  
  public String UploadDetails()
  {
	  WebElement title = driver.findElement(By.xpath("(//td[@class='linkTitle SearchTextHighlight'])[4]"));
	  title.click();
	  String ClearFileName = driver.findElement(By.cssSelector("label#lClearFileName")).getText();
	  System.out.println("Upload Details pop-up is displayed and the clearfile name is" + " " +ClearFileName);
	  return ClearFileName;
  }
  
  public boolean UploadPopUp()
  {
	  UploadButton.click();
	  testutil=new TestUtil(); 
	  testutil.SwitchToFrame();
	  return driver.findElement(By.id("InboxDate_selector")).isDisplayed();
  }
  
  public String LogoutPortal()
  {
	  LogoutBtn.click();
	 return driver.getTitle();
  }
  
  public String SearchResultForInvalidData()
  {
	  SearchField.sendKeys("abc");
	  SearchField.sendKeys(Keys.ENTER);
	  System.out.println(driver.findElement(By.xpath("//td[.='No data found for transfer history.']")).getText());
	  return driver.findElement(By.cssSelector(".dataGridRow")).getText();
  }
  
  public boolean PageRefresh() throws Exception
  {
	   TestUtil.Click(driver, 20, RefreshIcon);
	   return Page_load.isDisplayed();
	   
		  
  }
  
}
