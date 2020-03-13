package coom.qa.sonyops.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.sonyops.util.TestUtil;
import com.qa.sonyops.util.WebEventListener;

public class BaseClass 
{
public static WebDriver driver;
public static Properties prop;
static WebEventListener eventlistener;


     public BaseClass() 
     {	 //read data from config file
      
	try {
		  prop=new Properties();
	      FileInputStream fis;
		  fis = new FileInputStream("D:\\PFTprojects\\sonyops\\src\\main\\java\\com\\qa\\sonyops\\configuration\\config.properties");
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
     public static void initialisation()
     {
    	String browserName = prop.getProperty("browser");
    	System.out.println("Selected browser is" + " " + browserName);
    	if(browserName.equals("chrome"))
    	{
    		System.setProperty("webdriver.chrome.driver", "C:/Users/shilpashree.k/Downloads/drivers/chromedriver.exe");
    		driver=new ChromeDriver();
    	}
    	else if(browserName.equals("FireFox"))
    	{
    		System.setProperty("webdriver.gecko.driver", "C:/Users/shilpashree.k/Downloads/drivers/geckodriver.exe");
    		driver=new FirefoxDriver();
    	}
    	else
    	{
    		System.out.println("no browser value is given");
    	}
    	
    	eventlistener=new WebEventListener();
    	
    	
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);  //if application is taking too much of time then we have to increase the time in every script to avoid that will create on util class
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));
        
     }
     
   
     
     
}
