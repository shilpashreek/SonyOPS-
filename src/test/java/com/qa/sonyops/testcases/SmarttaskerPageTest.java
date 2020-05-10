package com.qa.sonyops.testcases;

import java.text.ParseException;

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
import com.qa.sonyops.util.TestUtil;

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
	   Thread.sleep(3000);
	   smarttasker=new SmarttaskerPage();
   }
   
   @Test(priority=1 ,enabled=false)
   public void ValidateSmartTaskerDashboardIsDisplaying()
   {
	  boolean SmartTasker_status=smarttasker.CheckSmartTaskerDashboardIsLoading();
	  Assert.assertTrue(SmartTasker_status , "Smarttasker Dashboard is not loading");
   }
   
   @Test(priority=2,enabled=false, description="Verify that enteries are loading under process tab")
   public void ValidateEnteriesUnderProcessTab()
   {
	   boolean ProcessTabEntries = smarttasker.isProcessesDisplaying();
	   Assert.assertTrue(ProcessTabEntries , "No records found");
   }
   
   @Test(priority=3,enabled=false, description = "Verify the status of the checkboxes in processTemplate dropdown")
   public void ValidateCheckBoxStatus() throws InterruptedException
   {
	   boolean checkboxStatus = smarttasker.isProcessTemplatesCheckboxesAreSelected();
	   Assert.assertTrue(checkboxStatus, "Checkboxes are not checked");
   }
   
   @Test(enabled=true)
   public void check() throws Exception
   {
	 String templatename = smarttasker.getWorkflowTemplateName(3);
	 System.out.println(templatename);
	 boolean status = smarttasker.getTemplatesName(templatename);
	 smarttasker.clickOnGoButton();
	 Assert.assertTrue(status , "default workflowtemplate is selected");
	 smarttasker.SelectDateFromCalendar();  
	 smarttasker.selectTemplateName_Right(templatename);
	   
   }
   
   
   
   
   
   
   
   
   
   
   
   
   @Test(priority=2 , enabled=false)
   public void ValidateWorFlowTemplatesAreListingUnderProcessTemplate()
   {
	  boolean Templates_displaying=smarttasker.ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying(); 
	  Assert.assertTrue(Templates_displaying, "Wf templates are not listing under process template");
   }
   
   @Test(priority=3 ,enabled=false)
   public void ValidateAllWorkFlowTemplates() throws Exception
   {
	   smarttasker.ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying();
	   smarttasker.getAllTemplates();
   }
   
   @Test(priority=4 ,enabled=false)
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
