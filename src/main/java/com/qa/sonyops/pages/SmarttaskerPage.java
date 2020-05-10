package com.qa.sonyops.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.util.JavascriptUtils;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;

@Listeners(TestNGListeners.class)
public class SmarttaskerPage extends BaseClass
{
	TestUtil testutil;
	String Wf_template_name;
	String StartedOn;
	SmarttaskerPage smarttasker;
	
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
	
	@FindBy(xpath= "//*[@id='processExecutionTable']/tbody/tr[*]/td[2]/div[1]")
	WebElement Process_templates;  //workflow template column
	
	@FindBy(xpath = "//div[@id='processTemplatesLeft']/div/descendant::input")
	WebElement ProcessTemplates_Checkboxes;
	
	@FindBy(xpath = "(//input[@class='checkboxes selectAllCheckbox'])[2]")
	WebElement SelectAll_checkbox;
	
	@FindBy(xpath = "//*[@id=\"processExecutionTable\"]/tbody/tr[*]/td[3]")
    WebElement Started_on_column;
	
	@FindBy(xpath = "(//div[@class='go-button-wf'])[2]")
	WebElement Leftpanel_GoBtn;
	
	@FindBy(css = ".dateSelect.FR")
	WebElement Calendar_Icon;
	
	@FindBy(xpath = "(//div[@class = 'wfSmartTaskerTemplatesName'])[2]")
	WebElement ProcessTemplate_right;
	
	@FindBy(xpath = "(//input[@class='checkboxes selectAllCheckbox'])[1]")
	WebElement SelectAll_checkbox_right;
	
	//processTemplatePanel  processTemplatePanel

  public SmarttaskerPage()
  {
	  PageFactory.initElements(driver, this);
  }
 
  //method to verify smartTasker DashboarD is loading
  public boolean CheckSmartTaskerDashboardIsLoading()
  {
	  new WebDriverWait(driver,60)
	  .until(ExpectedConditions.invisibilityOf(Page_loading));
	  return Processes_tab.isDisplayed();
  }
  
  //method to check if processes are displaying
  public boolean isProcessesDisplaying()
  {
	  boolean status=false;
	  try {
		  status=Process_templates.isDisplayed();
	  }catch(Exception e)
	  {
		  System.out.println(e.getMessage());
	  }
	  return status;
  }
  
  //method to check if all the checkboxes are selected
  public boolean isProcessTemplatesCheckboxesAreSelected() throws InterruptedException
  {
	  boolean checkBoxStatus= false;
	 Thread.sleep(3000);
	 TestUtil.Click(driver, 30, Process_template);
	 List<WebElement> CheckBoxes = driver.findElements(By.xpath("//div[@id='processTemplatesLeft']/div/descendant::input"));
	 int CheckBoxCount = CheckBoxes.size();
	 System.out.println("Size of the checkboxes" +CheckBoxCount);
	 for(int i=1; i<=CheckBoxCount ; i++)
	 {
		 WebElement EachCheckBox = driver.findElement(By.xpath(prop.getProperty("Checkbox_xpath_b")+i+prop.getProperty("Checkbox_xpath_a")));
		 if(EachCheckBox.isSelected())
		 {
			 checkBoxStatus=true;
		 }else {
			 checkBoxStatus=false; 
		 }
	 }
	 
	 if(SelectAll_checkbox.isDisplayed())
	 {
		 checkBoxStatus=true;
	 }else {
		 checkBoxStatus=false;
	 }
	 return checkBoxStatus;
 
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
  
  //method to get templateName and started on date at index 0
  public String getWorkflowTemplateName(int index)
  {
	  List<WebElement> TemplateName = driver.findElements(By.xpath(prop.getProperty("workflowTemplateName")));
	  Wf_template_name=TemplateName.get(index).getText();
	  return Wf_template_name;
  }

  //method to get all the WorkFlowtemplates names from process template dropdown
  public boolean getTemplatesName(String templatename)
  {
	  boolean status=false;
	  smarttasker=new SmarttaskerPage();
	  
	  
	  TestUtil.Click(driver, 20, Process_template);
	  if(SelectAll_checkbox.isSelected())
	  {
	  TestUtil.Click(driver, 10, SelectAll_checkbox);
	  }
	  List<WebElement> CheckBoxes = driver.findElements(By.xpath("//div[@id='processTemplatesLeft']/div/descendant::input"));
	  int CheckBoxCount = CheckBoxes.size();
	  for(int i=1 ; i<CheckBoxCount; i++)
	  {
	  String Templatespath = prop.getProperty("workflowTemplatesNameList");
	  String j = Integer.toString(i);
	  String Template_xpath = Templatespath.replace("#", j);
	  System.out.println(driver.findElement(By.xpath(Template_xpath)).getText());
	  String currentTemplate = driver.findElement(By.xpath(Template_xpath)).getText().toString().trim();
	  if(currentTemplate.equalsIgnoreCase(templatename))
	  {
		  String eachcheckbox = prop.getProperty("xpath_eachCheckbox");
		  String k = Integer.toString(i);
		  String SingleCheckboxpath = eachcheckbox.replace("#", k);
		  WebElement TemplateCheckbox = driver.findElement(By.xpath(SingleCheckboxpath));
		  TestUtil.Click(driver, 20, TemplateCheckbox);
		  status = true;
		  break;
	  }
	  if(status==false) //select default template
	  {
		  String defaultTemplate = prop.getProperty("workflowTemplatesNameList");
		  defaultTemplate=defaultTemplate.replace("#", "1");
		  WebElement defaulttemplatename = driver.findElement(By.xpath(defaultTemplate));
		  TestUtil.Click(driver, 10, defaulttemplatename);
	  }
	  }
	  return status;
  }
  
 public void clickOnGoButton()
 {
	 JavascriptUtils.scrollIntoView(Leftpanel_GoBtn, driver);
	 TestUtil.Click(driver, 10, Leftpanel_GoBtn);
 }
 
 
 //method to get started time of WorkFlowtemplate at index 0
 @SuppressWarnings("deprecation")
public String getStartedTime(int index) throws ParseException
 {
	  List<WebElement> StartedTime = driver.findElements(By.xpath(prop.getProperty("StartedOn")));
	  StartedOn = StartedTime.get(index).getAttribute("title");  //mmm dd yyyy
	  String temp[]=StartedOn.split(" ");  //temp[0]=mmm, temp[1]=dd ,temp[2]=yyyy
	  Date date = new SimpleDateFormat("MMM").parse(temp[0]);
	  int month = date.getMonth()+1;  //date.getMonth gives previous month
	  String FetchedDate = Integer.parseInt(temp[1])+"/"+month+"/"+temp[2];
	  System.out.println(FetchedDate);
	  return FetchedDate;
	  
 }
 
 public void SelectDateFromCalendar() throws Exception
 {
	 smarttasker=new SmarttaskerPage();
	 String temp = smarttasker.getStartedTime(0);    //yyyy mm dd
	 TestUtil.Click(driver, 20, Calendar_Icon);
	 String[] tempDate = temp.split("/");
	 int year=Integer.parseInt(tempDate[2]);
	 int month = Integer.parseInt(tempDate[1]);
	 int day = Integer.parseInt(tempDate[0]);
	 int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
	 int currentMonth = Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
	 
	 int yearDifference; 
	 int monthDifference;
	 String datevalue; String attributeValue;
	 if(year < currentYear)
	 {
		 yearDifference=currentYear-year;
		 for(int i=0 ; i<driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).size() ;i++)
		 {
			 String CalendarElement = driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).getText();
			 if(CalendarElement.equals(String.valueOf("«")))
			 {
				 while(yearDifference!=0)
				 {
					 driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).click();
					 yearDifference=yearDifference-1;
				 }
				 break;
			 }
		 }
	 }else if(year > currentYear)
	 {
		 yearDifference=year-currentYear;
		 for(int i=0 ; i<driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).size() ;i++)
		 {
			 String CalendarElement = driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).getText();
			 if(CalendarElement.equals(String.valueOf("»")))
			 {
				 while(yearDifference!=0)
				 {
					 driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).click();
					 yearDifference=yearDifference-1;
				 }
				 break;
			 }
	     }
     } if(month < currentMonth){
		 monthDifference=currentMonth-month;
		 for(int i=0 ; i<driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).size() ;i++)
		 {
			 String CalendarElement = driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).getText();
			 if(CalendarElement.equals(String.valueOf("‹")))
			 {
				 while(monthDifference!=0)
				 {
					 driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).click();
					 monthDifference=monthDifference-1;
				 }
				 break;
			 }
		 }
	 }else if(month > currentMonth) {
		 monthDifference=month-currentMonth;
		 for(int i=0 ; i<driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).size() ;i++)
		 {
			 String CalendarElement = driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).getText();
			 if(CalendarElement.equals(String.valueOf("›")))
			 {
				 while(monthDifference!=0)
				 {
					 driver.findElements(By.xpath(prop.getProperty("processExecutionCalendarButtons"))).get(i).click();
					 monthDifference=monthDifference-1;
				 }
				 break;
			 }
	     }
     }
     Thread.sleep(2000);
     //iterate through calendar rows
     int row_size = driver.findElements(By.xpath("CalendarRows")).size();
     for(int row=0; row<row_size ;row++)
     {
    	 List<WebElement> columns = driver.findElements(By.xpath("CalendarRows")).get(row).findElements(By.tagName("td"));
    	 for(int col=0; col<columns.size() ; col++)
    	 {
    	 datevalue=columns.get(col).getText().trim();
    	 attributeValue =columns.get(col).getAttribute("class");
    	 if ((!datevalue.isEmpty()) && (!attributeValue.equalsIgnoreCase("emptycell"))
			&& (!attributeValue.equals("day wn")))
    			 {
    		        if(Integer.parseInt(datevalue)==day)
    		        {
    		        	columns.get(col).click();
    		        	String displaydate = driver.findElement(By.id("DisplayDate")).getText();
    		        	//return true;
    		        }else {
    		        	//return false;
    		        }
    			 }
    	  }
     } 
 }
 
 public boolean selectTemplateName_Right(String templatename)
 {
	  boolean status=false;
	  smarttasker=new SmarttaskerPage();
	  
	  TestUtil.Click(driver, 20, ProcessTemplate_right);
	  if(SelectAll_checkbox_right.isSelected())
	  {
	  TestUtil.Click(driver, 10, SelectAll_checkbox_right);
	  }
	  List<WebElement> CheckBoxes = driver.findElements(By.xpath(prop.getProperty("checkboxes_rightside_list")));
	  int CheckBoxCount = CheckBoxes.size();
	  for(int i=1 ; i<CheckBoxCount; i++)
	  {
	  String Templatespath = prop.getProperty("workflowTemplatesListright");
	  String j = Integer.toString(i);
	  String Template_xpath = Templatespath.replace("#", j);
	  System.out.println(driver.findElement(By.xpath(Template_xpath)).getText());
	  String currentTemplate = driver.findElement(By.xpath(Template_xpath)).getText().toString().trim();
	  if(currentTemplate.equalsIgnoreCase(templatename))
	  {
		  String eachcheckbox = prop.getProperty("eachCheckboxXpath_right");
		  String k = Integer.toString(i);
		  String SingleCheckboxpath = eachcheckbox.replace("#", k);
		  WebElement TemplateCheckbox = driver.findElement(By.xpath(SingleCheckboxpath));
		  TestUtil.Click(driver, 20, TemplateCheckbox);
		  status = true;
		  break;
	  }
	  if(status==false) //select default template
	  {
		  String defaultTemplate = prop.getProperty("workflowTemplatesListright");
		  defaultTemplate=defaultTemplate.replace("#", "1");
		  WebElement defaulttemplatename = driver.findElement(By.xpath(defaultTemplate));
		  TestUtil.Click(driver, 10, defaulttemplatename);
	  }
	  }
	  return status;
	 
	 
 }
 
 
 
 
 
}
