package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;

@Listeners(TestNGListeners.class)
public class SmarttaskerPage extends BaseClass
{
	TestUtil testutil;
	
	@FindBy(id = "Smart Tasker")
	WebElement SmartTasker_Tab;
	
	@FindBy(xpath = "//div[@id='Smart Tasker Processes']")
	WebElement Processes_tab;
	
	@FindBy(xpath = "//div[@id='Smart Tasker Tasks']")
	WebElement Tasks_tab;
	
	@FindBy(id = "waiting")
	WebElement Page_loading;
	
	@FindBy(xpath = "(//div[@class='wfSmartTaskerTemplatesName'])[1]")
	WebElement Process_template;
	
	@FindBy(css = "div#processTemplatesLeft_contentwrapper")
    WebElement Template_options;	
	
	@FindBy(xpath = "(//div[@id='processTemplatePanel'])[2]")
	WebElement WorkFlow_templates;
	
	//processTemplatePanel  processTemplatePanel
	
	
	
  public SmarttaskerPage()
  {
	  PageFactory.initElements(driver, this);
  }
  
  
  //method to verify smartTasker dashboard is loading
  public boolean CheckSmartTaskerDashboardIsLoading()
  {
	  new WebDriverWait(driver,60)
	  .until(ExpectedConditions.invisibilityOf(Page_loading));
	  return Processes_tab.isDisplayed();
  }
  
  //method to check workflow templates are displaying
  public boolean ClickOnProcessTemplateAndCheckAllTemplatesAreDisplaying()
  {
	  new WebDriverWait(driver,60)
	  .until(ExpectedConditions.invisibilityOf(Page_loading));
	  TestUtil.Click(driver, 20, Process_template);
	  return Template_options.isDisplayed();
  }
  
  //method to get all wF template names
  public void getAllTemplates() throws Exception
  {
	  new WebDriverWait(driver,40)
	  .until(ExpectedConditions.invisibilityOf(Page_loading));
	 
	  System.out.println(WorkFlow_templates.getText());
	 
  }
  
  //method to get column names
  public int getColumnTitles()
  {
	  int SmartTasker_Col_count = 0;
	  String b_xpath= "//*[@id='processes-header']/tbody/tr/td[";
	  String a_xpath= "]";
	 List<WebElement> Columns= driver.findElements(By.xpath("//*[@id='processes-header']/tbody/tr/td"));
	 SmartTasker_Col_count=Columns.size();
	 for(int i=1 ;i<=Columns.size() ; i++)
	 {
		 WebElement col_name = driver.findElement(By.xpath(b_xpath+i+a_xpath));
		 System.out.println(col_name.getText());
	 }
	 return SmartTasker_Col_count;
  }
}
