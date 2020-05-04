package com.qa.sonyops.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.pages.OpsPage;
import com.qa.sonyops.pages.SmarttaskerPage;
import com.qa.sonyops.util.TestNGListeners;

@Listeners(TestNGListeners.class)
public class SmarttaskerPageTest extends BaseClass 
{
	LoginPage loginpage;
	HomePage homepage;
	OpsPage opspage;
	SmarttaskerPage smarttasker;
   public SmarttaskerPageTest()
   {
	   super();
   }
   
   @BeforeMethod
   public void setUp() throws Exception
   {
	   initialisation("bc_url");
	   loginpage=new LoginPage();
	   loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
	   homepage=new HomePage();
	   Thread.sleep(3000);
	   opspage=homepage.SelectPFTopsUserRole();
	   Thread.sleep(3000);
	   opspage=new OpsPage();
	   Thread.sleep(3000);
	   smarttasker=opspage.ClickOnSmartTaskerTab();
	   smarttasker=new SmarttaskerPage();
   }
   
   @Test(priority=1 ,enabled=true)
   public void ValidateSmartTaskerDashboardIsDisplaying()
   {
	  boolean SmartTasker_status=smarttasker.CheckSmartTaskerDashboardIsLoading();
	  Assert.assertTrue(SmartTasker_status , "Smarttasker Dashboard is not loading");
   }
   
   @Test(priority=2 , enabled=true)
   public void ValidateWorFlowTemplatesAreListingUnderProcessTemplate()
   {
	  boolean Templates_displaying=smarttasker.ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying(); 
	  Assert.assertTrue(Templates_displaying, "Wf templates are not listing under process template");
   }
   
   @Test(priority=3 ,enabled=true)
   public void ValidateAllWorkFlowTemplates() throws Exception
   {
	   smarttasker.ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying();
	   smarttasker.getAllTemplates();
   }
   
   @Test(priority=4 ,enabled=true)
   public void ValidateSmartTaskerColumns()
   {
	   int Col_count = smarttasker.getColumnTitles();
	   Assert.assertEquals(Col_count,5, "SmartTasker columns are not displaying");
   }
   
   @AfterMethod
   public void tearDown()
   {
	   driver.quit();
   }
}
