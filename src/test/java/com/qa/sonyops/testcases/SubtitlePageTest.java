package com.qa.sonyops.testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.pages.SubtitlePage;

public class SubtitlePageTest extends BaseClass
{
	LoginPage loginpage;
	HomePage homepage;
	CatalogPage catalogpage;
	SubtitlePage subtitlepage;
	
    public SubtitlePageTest()
    {
    	super();
    }
    
    @BeforeMethod
    public void setup() throws Exception
    {
    	initialisation("bc_url");
    	loginpage=new LoginPage();
    	loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
    	homepage=new HomePage();
    	subtitlepage=homepage.SelectSubtitleUserRole();
    	Thread.sleep(3000);
    	subtitlepage=new SubtitlePage();
    	catalogpage=new CatalogPage();
    	catalogpage.SearchForTestAsset("Test");
    	//subtitlepage.waitForThePageToLoad();
    }
    @Test(priority=1,enabled=false)
    public void ValidateSubtitleStatusFilter() throws Exception
    {
    	List<String> ActualFilterOptoins = subtitlepage.getSubtitleFilterOptions();
    	List<String> ExpectedFilterOptions = subtitlepage.targetSubtitleFilterOptions();
    	Assert.assertEquals(ActualFilterOptoins, ExpectedFilterOptions);
    }
    
    @Test(priority=2 , enabled=false)
    public void GetPendingSubtitleTasks() throws Exception
    {
    	subtitlepage.ApplySubtitleStatus_pending();
    }
    
    @Test(priority=3 ,enabled=false)
    public void SubmitSubtitleStatusAndCaptureAlertMessage() throws Exception
    {
    	subtitlepage.ApplySubtitleStatus_pending();
    	Thread.sleep(3000);
    	subtitlepage.getTotalPageCountFromPagination();
    	String Subtitle_Submitted_Message=subtitlepage.ClickOnConfirmLink();
    	Assert.assertTrue(Subtitle_Submitted_Message.contains("Successfully submitted"));
    }
    
    @Test(priority=4 ,enabled=false)
    public void ValidateSubtitleStatusIsChangedAfterSubmittingStatus() throws Exception
    {
    	boolean Subtitle_status=subtitlepage.getSubtitleStatusAfterSubmitting();
    	Assert.assertTrue(Subtitle_status , "subtitle status is not changed to submitted");
    }
    
    @Test(priority=5 ,enabled=true)
    public void ValiadteEssenceUpload() throws Exception
    {
    	subtitlepage.EssenceUpload();
    }
    
    
    
    @AfterMethod
    public void tearDown()
    {
    	//driver.quit();
    }
}
