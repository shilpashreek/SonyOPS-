package com.qa.sonyops.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
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
	    } 
	catch (FileNotFoundException e)  
	{ 
		e.printStackTrace();
	} 
	catch (IOException e) 
	{
		e.printStackTrace();
	}
	  
  }

     //Initialization method-To get the details of my browser
     public static void initialisation(String url)
     {
    	String browserName = prop.getProperty("browser");
    	System.out.println("Selected browser is" + " " + browserName);
    	if(browserName.equals("chrome"))
    	{
    		System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_path"));
    		driver=new ChromeDriver();
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
     
   
     
     
}
