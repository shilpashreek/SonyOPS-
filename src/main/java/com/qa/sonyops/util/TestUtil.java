package com.qa.sonyops.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.sonyops.base.BaseClass;

public class TestUtil extends BaseClass
{
	boolean Streaming;
public static long PAGE_LOAD_TIMEOUT = 60;
public static long IMPLICIT_WAIT = 60;

public static String TESTDATA_SHEET_PATH = "C:\\Users\\Manjushree\\Documents\\SonyOPS-\\src\\main\\java\\com\\qa\\sonyops\\testdata\\SonyopsTestData.xlsx";
FileInputStream fis;
static Workbook wb;
Sheet sheet;


//explicit wait method to wait for the element before sending value to the field
public static void Sendkeys(WebDriver driver,int timeout,WebElement element,String value)
{
	new WebDriverWait(driver,timeout).
	until(ExpectedConditions.visibilityOf(element));
	element.sendKeys(value);
}

//explicit wait method to wait for the element before clicking
public static void Click(WebDriver driver,int timeout ,WebElement element)
{
	new WebDriverWait(driver,timeout).
	until(ExpectedConditions.elementToBeClickable(element));
	element.click();
}

//explicitly wait for the element before clear the value
public static void Clear(WebDriver driver , int timeout ,WebElement element)
{
	new WebDriverWait(driver , timeout).
	until(ExpectedConditions.visibilityOf(element));
	element.clear();
}


public static void CheckPresence(WebDriver driver,int timeout,WebElement element)
{
	new WebDriverWait(driver,timeout).
	until(ExpectedConditions.elementToBeSelected(element));
}
//method to wait till invisibility of buffering 
public static void Buffering(WebDriver driver,int timeout)
{
new WebDriverWait(driver,timeout).
until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#waiting")));
}

//method to wait till invisibility of initialising 
public static void Initialsising(WebDriver driver,int timeout)
{
	new WebDriverWait(driver,timeout).
	until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span#CMP_CatalogPlayerV2Container_playerStateDisplayText")));
	
}

//method to switch to frame
public void SwitchToFrame()
{
	driver.switchTo().frame("CEUploadeframe");
}

public void LaunchNewTab()
{
	JavascriptExecutor js = ((JavascriptExecutor)driver);
	js.executeScript("window.open('https://www.google.com/','_blank');");
}

//Method to fetch data from Excel sheet

public Object[][] GetData(String Sheetname)
{
	try {
		//File src=new File(path);
		fis = new FileInputStream(TESTDATA_SHEET_PATH);
		//wb=new HSSFWorkbook(fis);
		wb = WorkbookFactory.create(fis);
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	sheet=wb.getSheet(Sheetname);
	Object[][] data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	for(int i=0 ; i<sheet.getLastRowNum() ; i++)
	{
		for(int j=0 ; j<sheet.getRow(0).getLastCellNum() ; j++)
		{
			data[i][j]=sheet.getRow(i+1).getCell(j).toString();
		}
	}
	return data;
}




//Handling Flash
public void AllowChromeFlash()
{
	driver.get(prop.getProperty("Flash_url"));
	WebElement root1 = driver.findElement(By.tagName("settings-ui"));
	WebElement shadowRoot1 = expandRootElement(root1);
	
	WebElement root2 = shadowRoot1.findElement(By.cssSelector("settings-main"));
	WebElement shadowroot2 = expandRootElement(root2);
	
	WebElement root3 = shadowroot2.findElement(By.cssSelector("settings-basic-page"));
	WebElement shadowroot4 = expandRootElement(root3);

	WebElement root5 = shadowroot4.findElement(By.tagName("settings-privacy-page"));
	WebElement shadowroot6 = expandRootElement(root5);
	
	WebElement root8 = shadowroot6.findElement(By.tagName("site-details"));
	WebElement shadowroot9 = expandRootElement(root8);
	
	WebElement root9 = shadowroot9.findElement(By.id("plugins"));
	WebElement shadowroot10 = expandRootElement(root9);
	
	WebElement FlashDropdown = shadowroot10.findElement(By.id("permission"));
	Select s = new Select(FlashDropdown);
	s.selectByValue("allow");
	
}
public WebElement expandRootElement(WebElement element)
{
	WebElement ele=(WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot", element);
	return ele;
}

//method to get the starting TimeCode of the video
public String GetTimeCode_in()
{
	String StartTime = "null";
	try
	{
	WebElement VideoStart_TC = driver.findElement(By.cssSelector("div#CMP_SearchPlayerContainer_dvFrameTime"));
	StartTime=VideoStart_TC.getText();
	//int StartTime=Integer.valueOf(VideoStart_TC.getText());
	System.out.println(StartTime);
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return StartTime;
}

//method to get total duration of the video
public String GetTotalDurationOfVideo()
{
	String EndTime="null"; 
	try
	{
	WebElement VideoDuration=driver.findElement(By.cssSelector("span#CMP_SearchPlayerContainer_totalDuration"));
	EndTime=VideoDuration.getText();
	//EndTime=Integer.valueOf(VideoDuration.getText());
	System.out.println(EndTime);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return EndTime;

}

//method to click on play/pause player key
public void ClickPlay_pausekey()
{
WebElement play_pause=driver.findElement(By.cssSelector("div#CMP_SearchPlayerContainer_btnCMPPlayPause"));	
TestUtil.Click(driver, 10, play_pause);
}

//method to click on stop player key 
public void ClickStopkey()
{
	WebElement StopKey=driver.findElement(By.cssSelector(".Stop.setLeft"));
	TestUtil.Click(driver, 10, StopKey);
}

public boolean TimecodeComparison(String TC_IN_value, String TC_OUT_value)
{
	boolean Streaming=false;
	String TC_in_before=TC_IN_value;
	String beforePlay_TimeValue[]=TC_in_before.split(":");
    
    String TC_in_after=TC_OUT_value;
    String afterPlay_TimeValue[]= TC_in_after.split(":");
    System.out.println(afterPlay_TimeValue.length);
   
	for(int i=2; i<beforePlay_TimeValue.length; i++)
	{
	    if(!beforePlay_TimeValue[i].equals(afterPlay_TimeValue[i]))
	    {
	    	System.out.println("Video start_time is" +" "+beforePlay_TimeValue[i] +" "  +"and" +" " +"End_time is" +" " +afterPlay_TimeValue[i]);
	        System.out.println("Asset is streaming");
	        //return Streaming;
	        break;
	        
	    }
	    else
	    {
	    	System.out.println("Asset is not streaming"+" "+beforePlay_TimeValue[i] +" " +afterPlay_TimeValue[i]);
	    	System.out.println("no");
	    	break;
	    }
	}
	return Streaming;

}

//Select class util to handle dropdown
public void HandlingDropDown(WebElement element,String value)
{
	Select s = new Select(element);
	s.selectByVisibleText(value);
}

//Method to perform mouse hover action
public void MouseHover(WebElement ele)
{
	Actions act = new Actions(driver);
	act.moveToElement(ele).build().perform();
}

//method to take Screenshot in case of pass or fail of TestCase execution
public static void TakeScreenshot_pass_fail(String testMethodName)
{
	File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	try
	{
		FileUtils.copyFile(src, new File("C:/Users/Manjushree/Documents/SonyOPS-/screenshots/" +testMethodName+ "_"+".png"));
	} catch (IOException e) 
	{
		e.printStackTrace();
	}
}

//Method to TakeScreenshot in case of exception
public static void ScreenShotOnException() throws IOException
{
	File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(src, new File("C:/Users/Manjushree/Documents/SonyOPS-/screenshots/" +System.currentTimeMillis()+ "_" + ".png"));
	
}

public static void Pageloading(WebDriver driver,int timeout)
{
	new WebDriverWait(driver,timeout).
	until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#waitingCatalogPopup")));
}





}

