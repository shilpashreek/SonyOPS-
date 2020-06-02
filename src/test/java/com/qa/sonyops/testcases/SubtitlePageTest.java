package com.qa.sonyops.testcases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.sonyops.base.BaseClass;
import com.qa.sonyops.pages.CatalogPage;
import com.qa.sonyops.pages.HomePage;
import com.qa.sonyops.pages.LoginPage;
import com.qa.sonyops.pages.SubtitlePage;
import com.qa.sonyops.util.TestNGListeners;
import com.qa.sonyops.util.TestUtil;
import com.qa.sonyops.util.WebEventListener;

@Listeners(TestNGListeners.class)
public class SubtitlePageTest extends BaseClass
{
	BaseClass baseclass;
	LoginPage loginpage;
	HomePage homepage;
	CatalogPage catalogpage;
	SubtitlePage subtitlepage;
	SubtitlePageTest subtitlepagetest;
	static WebEventListener eventlistener;
	static EventFiringWebDriver e_driver;
	
    public SubtitlePageTest()
    {
    	super();
    }
    
    @BeforeMethod
    public void setup() throws Exception
    {
    	subtitlepagetest = new SubtitlePageTest();
    	subtitlepagetest.initialisation("bc_url");
    	
    	//baseclass=new BaseClass();
    	//baseclass.initialisation("bc_url");
    	//initialisation("bc_url");
    	
		/*
		 * loginpage=new LoginPage(); loginpage.LogintoBC(prop.getProperty("username"),
		 * prop.getProperty("password")); homepage=new HomePage();
		 * subtitlepage=homepage.SelectSubtitleUserRole(); Thread.sleep(3000);
		 * subtitlepage=new SubtitlePage(); catalogpage=new CatalogPage();
		 * catalogpage.SearchForTestAsset("Test");
		 */
    	
    	//subtitlepage.waitForThePageToLoad();
    }
    
    public void initialisation(String url)
    {
    	String browserName = prop.getProperty("browser");
    	System.out.println("Selected browser is" + " " + browserName);
    	if(browserName.equals("chrome"))
    	{
			 ChromeOptions options = new ChromeOptions();
    		 options.addArguments("user-data-dir=C:\\Users\\Manjushree\\Documents\\SonyOPS-\\temp\\User Data");
    		//to suppress error logs in console
    		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
    		
    		System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_path"));
    		driver=new ChromeDriver(options);
    	}
    	else if(browserName.equals("FireFox"))
    	{
    		System.setProperty("webdriver.gecko.driver", prop.getProperty("geckodriver_path"));
    		driver=new FirefoxDriver();
    	}
    	else
    	{
    		System.out.println("no browser value is given");
    	}
    	
    	e_driver = new EventFiringWebDriver(driver);
    	// Now create object of EventListenerHandler to register it with EventFiringWebDriver
    	eventlistener=new WebEventListener();
    	e_driver.register(eventlistener);
    	driver=e_driver;  //assign EventFiringWebDriver to our main driver
    	
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);  //if application is taking too much of time then we have to increase the time in every script to avoid that will create on util class
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty(url));
     
    }
    
    @Test(priority=1,enabled=true)
    public void ValidateSubtitleStatusFilter() throws Exception
    {
    	logger=extent.startTest("ValidateSubtitleStatusFilter");
    	loginpage=new LoginPage();
    	loginpage.LogintoBC(prop.getProperty("username"), prop.getProperty("password"));
    	homepage=new HomePage();
    	subtitlepage=homepage.SelectSubtitleUserRole();
    	Thread.sleep(3000);
    	subtitlepage=new SubtitlePage();
    	catalogpage=new CatalogPage();
    	catalogpage.SearchForTestAsset("Test");
    	
    	
    	List<String> ActualFilterOptoins = subtitlepage.getSubtitleFilterOptions();
    	List<String> ExpectedFilterOptions = subtitlepage.targetSubtitleFilterOptions();
    	Assert.assertEquals(ActualFilterOptoins, ExpectedFilterOptions);
    }
    
    
    @Test(priority=2 ,enabled=true, dependsOnMethods = {"ValidateSubtitleStatusFilter"})
    public void SubmitSubtitleStatusAndCaptureAlertMessage() throws Exception
    {
    	logger=extent.startTest("SubmitSubtitleStatusAndCaptureAlertMessage");
    	subtitlepage=new SubtitlePage();
    	catalogpage=new CatalogPage();
    	catalogpage.SearchForTestAsset("Test");
    	//catalogpage=new CatalogPage();
    	//catalogpage.SearchForTestAsset("Test");
    	subtitlepage.ApplySubtitleStatus_pending();
    	Thread.sleep(3000);
    	subtitlepage.getTotalPageCountFromPagination();
    	String Subtitle_Submitted_Message=subtitlepage.ClickOnConfirmLink();
    	Assert.assertTrue(Subtitle_Submitted_Message.contains("Successfully submitted"));
    }
    
    @Test(priority=3 ,enabled=true , dependsOnMethods = {"ValidateSubtitleStatusFilter","SubmitSubtitleStatusAndCaptureAlertMessage"})
    public void ValidateSubtitleStatusIsChangedAfterSubmittingStatus() throws Exception
    {
    	logger=extent.startTest("ValidateSubtitleStatusIsChangedAfterSubmittingStatus");
    	subtitlepage=new SubtitlePage();
    	catalogpage=new CatalogPage();
    	catalogpage.SearchForTestAsset("Test");
    	
    	boolean Subtitle_status=subtitlepage.getSubtitleStatusAfterSubmitting();
    	Assert.assertTrue(Subtitle_status , "subtitle status is not changed to submitted");
    }
    
    @Test(priority=4,dependsOnMethods= {"ValidateSubtitleStatusFilter"},enabled=true)
    public void ValiadteEssenceUpload() throws Exception
    {
    	logger=extent.startTest("ValiadteEssenceUpload");
    	subtitlepage=new SubtitlePage();
    	catalogpage=new CatalogPage();
    	catalogpage.SearchForTestAsset("Test");
    	
    	String EssenceUpload_successAlert=subtitlepage.EssenceUpload();
    	Assert.assertTrue(EssenceUpload_successAlert.contains("Upload initiated successfully."), "Failed to allow aspera connect");
    	
    }
    
    @AfterMethod
    public void tearDown()
    {
    	driver.quit();
    }
}
