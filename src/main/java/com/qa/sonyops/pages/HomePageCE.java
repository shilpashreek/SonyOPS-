package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.sonyops.util.TestUtil;

import coom.qa.sonyops.base.BaseClass;

public class HomePageCE extends BaseClass
{
	static TestUtil testutil;
	
@FindBy(css = "div[class=clearInsideLogo]")
WebElement SonyOpsLogo;

@FindBy(id = "TRANSFER HISTORY")
WebElement TransferHistory_Label;

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

//xpath of Ce title row - //th[2]/../following-sibling::tr/td[2]

//initialising object repository
  public HomePageCE()
  {
	  PageFactory.initElements(driver, this);
  }
  
//actions to be performed
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
  
  public void VerifyRefreshIconIsPresent()
  {
	 System.out.println("RefreshIcon is displayed in homepage" +" " +RefreshIcon.isDisplayed());
	 System.out.println("RefreshIcon is enabled" +" "+RefreshIcon.isEnabled());
	 
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
  
}
