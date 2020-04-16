package com.qa.sonyops.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.TestNGListeners;

@Listeners(TestNGListeners.class)
public class CEuploadPage extends BaseClass 
{
   @FindBy(id = "CEUploadPopup_title_holder")
   WebElement CE_popUp_Header;
   
   @FindBy(id = "closePFTPopup")
   WebElement ClosePop_up;
   
   @FindBy(name = "ddlContentType")
   WebElement ContentType;
   
   @FindBy(id = "txtEpisodeNumber")
   WebElement Episode_num;
   
   @FindBy(id = "txtPartNum")
   WebElement Part_num;
   
   @FindBy(id = "txtSOM")
   WebElement som;
   
   @FindBy(name = "ctl00$contentPlaceHolder$ctl00$txtRemarks")
   WebElement Remarks;
   
   @FindBy(xpath = "//div[@class='browseButton']")
   WebElement Browse_button;
   
   @FindBy(id = "btnUpload")
   WebElement Upload_button;
   
   //initialising object repository
   public CEuploadPage()
   {
	   PageFactory.initElements(driver, this);
   }
	
	
	
	
}
