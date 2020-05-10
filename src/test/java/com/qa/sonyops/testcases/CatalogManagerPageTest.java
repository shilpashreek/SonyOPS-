package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogManagerPage;
import com.qa.sonyops.pages.CatalogPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;

public class CatalogManagerPageTest extends BaseClass
{
	LoginPage loginpage;
	HomePage homepage;
	CatalogManagerPage c_manager;
	CatalogPage catalogpage;
	BaseClass baseclass;
    public CatalogManagerPageTest()
    {
    	super();
    }
    
    @BeforeMethod
    public void setup() throws Exception
    {
    	baseclass=new BaseClass();
    	baseclass.initialisation("bc_url");
    	//initialisation("bc_url");
    	loginpage=new LoginPage();
    	loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
    	homepage=new HomePage();
    	c_manager=homepage.SelectCatalogManagerRole();
    	
    }
    
    @Test(priority=1 , enabled=false)
    public void ValidateCatalogStatus_AfterCatalogIsDone() throws Exception
    {
    	c_manager.VerifyChangeInCatalogStatus();
    	Thread.sleep(3000);
    	boolean Catalog_status=c_manager.getCurrentCatalogStatus(CatalogPage.AssetTitle, CatalogPage.UploadedOn, "In Progress");
    	Assert.assertTrue(Catalog_status, "Catalog status is not changed to inprogress");
    }
   
    @Test(priority=2 , enabled=true)
    public void ValidateCatalogStatusPopUpAfterClickingOnInprogressLink() throws Exception
    {
    	boolean CatalogStatus_popUp=c_manager.getInProgressAsset();
    	Assert.assertTrue(CatalogStatus_popUp, "CatalogStatus pop-up is not displayed");
    	
    }
    
    @Test(priority=3 , enabled=true)
    public void ValidateCatalogCompleteStatus() throws Exception
    {
    	c_manager.getInProgressAsset();
    	String CatalogCompleteText=c_manager.getTheTextOnCatalogStatusCompletePopUp();
    	Assert.assertEquals(CatalogCompleteText, "Do you want to submit Catalog Complete status?");
    }
    
    
    @Test(priority=4 , enabled= true)
    public void SubmitCatalogStatus_CaptureAlert() throws Exception
    {
    	c_manager.getInProgressAsset();
    	boolean AlertDisplayed=c_manager.submitCatalogStatus();
    	Assert.assertTrue(AlertDisplayed);
    	
    }
    
    @Test(priority=5 ,enabled=true)
    public void ViewCatalogQCStatusPopUpAndCapturePopUpMessage() throws Exception
    {
    	String CatalogQCStatus_PopupText=c_manager.ClickOnCompletedLink();
    	Assert.assertEquals(CatalogQCStatus_PopupText, "Do you want to submit Catalog-QC status?");
    
    }
    //SubmitCatalogQCStatusAndCapturePopUpMessage
    @Test(priority=6 ,enabled = true)
    public void SubmitCatalogQCStatusAndCapturePopUpMessage() throws Exception
    {
    	c_manager.ClickOnCompletedLink();
    	boolean SuccessAlertMessage=c_manager.submitCatalogStatus();
    	Assert.assertTrue(SuccessAlertMessage, "CatalogQC status is not completed");
    }
    
    @AfterMethod
    public void tearDown()
    {
    	driver.quit();
    }
    
    
}
