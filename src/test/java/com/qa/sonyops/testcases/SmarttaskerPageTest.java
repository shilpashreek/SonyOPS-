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
public class SmarttaskerPageTest extends BaseClass {
	LoginPage loginpage;
	HomePage homepage;
	OpsPage opspage;
	SmarttaskerPage smarttasker;
	BaseClass baseclass;

	public SmarttaskerPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		baseclass = new BaseClass();
		baseclass.initialisation();
		baseclass.enterURL(BaseClass.bc_url);
		loginpage = new LoginPage();
		loginpage.LogintoBC(BaseClass.username, BaseClass.password);
		homepage = new HomePage();
		Thread.sleep(3000);
		opspage = homepage.SelectPFTopsUserRole();
		Thread.sleep(3000);
		opspage = new OpsPage();
		Thread.sleep(3000);
		smarttasker = opspage.ClickOnSmartTaskerTab();
		Thread.sleep(3000);
		smarttasker = new SmarttaskerPage();
	}

	@Test(priority = 1, enabled = true)
	public void ValidateSmartTaskerDashboardIsDisplaying() {
		logger = extent.startTest("ValidateSmartTaskerDashboardIsDisplaying");
		boolean SmartTasker_status = smarttasker.CheckSmartTaskerDashboardIsLoading();
		Assert.assertTrue(SmartTasker_status, "Smarttasker Dashboard is not loading");
	}

	@Test(priority = 2, enabled = true, description = "Verify that enteries are loading under process tab")
	public void ValidateEnteriesUnderProcessTab() {
		logger = extent.startTest("ValidateEnteriesUnderProcessTab");
		boolean ProcessTabEntries = smarttasker.isProcessesDisplaying();
		Assert.assertTrue(ProcessTabEntries, "No records found");
	}

	@Test(priority = 3, enabled = true, description = "Verify the status of the checkboxes in processTemplate dropdown")
	public void ValidateCheckBoxStatus() throws InterruptedException {
		logger = extent.startTest("ValidateCheckBoxStatus");
		boolean checkboxStatus = smarttasker.isProcessTemplatesCheckboxesAreSelected();
		Assert.assertTrue(checkboxStatus, "Checkboxes are not checked");
	}

	@Test(priority = 4, enabled = true, description = "Verify Go button in processTemplates dropdown functionality")
	public void Validate_Go_buttonFunctionality() throws Exception {
		logger = extent.startTest("Validate_Go_buttonFunctionality");
		String templatename = smarttasker.getWorkflowTemplateName(3);
		System.out.println(templatename);
		boolean status = smarttasker.getTemplatesName(templatename);
		smarttasker.clickOnGoButton();
		Assert.assertTrue(status, "default workflowtemplate is selected");
		smarttasker.SelectDateFromCalendar();
		smarttasker.selectTemplateName_Right(templatename);
		boolean barGraph = smarttasker.ProcessExecutionTrendGraph_isDisplayed();
		boolean pieChart = smarttasker.SLAcomplianceGraph_isdisplayed();
		Assert.assertTrue(barGraph && pieChart, "Graphs are not displaying");

	}

	@Test(priority = 5, enabled = true, description = "Smart Tasker - Execution Trend panel-Verify the N level drill down in execution trends")
	public void ValidateVariationInExecutionGraph() throws InterruptedException {
		logger = extent.startTest("ValidateVariationInExecutionGraph");
		smarttasker.selectDateTypeSmartTasker();
		Thread.sleep(3000);
		boolean displayed = smarttasker.ProcessExecutionTrendGraph_isDisplayed();
		Assert.assertTrue(displayed);
		Thread.sleep(3000);
		smarttasker.clickOnBarByIndex(3);
		Thread.sleep(3000);
		try {
			smarttasker.getBarsCountInProcessExecutionGraph();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 2, enabled = false)
	public void ValidateWorFlowTemplatesAreListingUnderProcessTemplate() {
		boolean Templates_displaying = smarttasker.ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying();
		Assert.assertTrue(Templates_displaying, "Wf templates are not listing under process template");
	}

	@Test(priority = 3, enabled = false)
	public void ValidateAllWorkFlowTemplates() throws Exception {
		smarttasker.ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying();
		smarttasker.getAllTemplates();
	}

	@Test(priority = 4, enabled = false)
	public void ValidateSmartTaskerColumns() {
		int Col_count = smarttasker.getColumnTitles();
		Assert.assertEquals(Col_count, 5, "SmartTasker columns are not displaying");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
