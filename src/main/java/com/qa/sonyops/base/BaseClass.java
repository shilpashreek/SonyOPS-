package com.qa.sonyops.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.log4testng.Logger;

import com.qa.sonyops.util.TestUtil;
import com.qa.sonyops.util.WebEventListener;

public class BaseClass 
{
public static WebDriver driver;
public static Properties prop;
static WebEventListener eventlistener;
static EventFiringWebDriver e_driver;



     public BaseClass() 
     {	 //read data from config file

      try {
		  prop=new Properties();
	      FileInputStream fis;
		  fis = new FileInputStream("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\src\\main\\java\\com\\qa\\sonyops\\configuration\\config.properties");
		  prop.load(fis);
	    } catch (FileNotFoundException e)  { 
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
	  
  }
	
	  @BeforeSuite(enabled=true) 
	  public void LoadChromeDataToTempFolder() throws Exception 
	  { 
		  File DestFolder = new File("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\temp"); 
		  File SrcFolder=new File("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\chromedata");
	  
	  if(DestFolder.exists()) 
	  { 
		  FileUtils.copyDirectory(SrcFolder, DestFolder);
	  }else 
	  { 
		  DestFolder.mkdir(); FileUtils.copyDirectory(SrcFolder, DestFolder); }
	  }
	 
     
     //Initialization method-To get the details of my browser
     public void initialisation(String url)
     {
    	String browserName = prop.getProperty("browser");
    	System.out.println("Selected browser is" + " " + browserName);
    	if(browserName.equals("chrome"))
    	{
			  ChromeOptions options = new ChromeOptions();
			  HashMap<String, Object> prefs = new HashMap<String, Object>();
			  prefs.put("profile.default_content_settings.popups", 1);
			  //options.addArguments("--no-sandbox"); //
			  options.addArguments("--disable-dev-shm-usage");
			  prefs.put("credentials_enable_service", false);
			  prefs.put("profile.password_manager_enabled", false);
			  prefs.put("profile.default_content_setting_values.plugins", 1);
			  prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
			  prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player",1); 
			  prefs.put("profile.default_content_settings.popups", 1);
			  prefs.put("PluginsAllowedForUrls", "https://sonyops.clearhub.tv/BC/Product/Modules/SignIn.aspx");
			  options.setExperimentalOption("prefs", prefs);
			     		
    		
    		
    		
    		//options.addArguments("user-data-dir=C:\\Users\\Manjushree\\Documents\\SonyOPS-\\temp\\User Data");
    		
    		
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
     
	
	
	  @AfterSuite(enabled=true)
	  public void DeleteTemporaryFolder() throws Exception 
	  { 
		  File temp=new File("C:\\Users\\Manjushree\\Documents\\SonyOPS-\\temp");
	  if(temp.exists()) 
	  { FileUtils.deleteDirectory(temp); 
	  } 
	  }
	 
	 
   
     
     
}
