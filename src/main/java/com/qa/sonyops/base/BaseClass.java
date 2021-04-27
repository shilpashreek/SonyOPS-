package com.qa.sonyops.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.qa.sonyops.util.TestUtil;
import com.qa.sonyops.util.WebEventListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseClass {
	public static WebDriver driver;
	public static Properties prop;
	static WebEventListener eventlistener;
	static EventFiringWebDriver e_driver;
	public static Logger log;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String ce_url = "", bc_url = "", url = "", username = "", password = "", browser = "", Flash_url = "";
	String Log4j_ConfigFilePath = "D:/automationPULLprojects/SonyOPS-/src/main/resources/log4j.properties";

	public BaseClass() { // read data from config file

		try {
			prop = new Properties();
			FileInputStream fis;
			fis = new FileInputStream(
					"D:\\automationPULLprojects\\SonyOPS-\\src\\main\\java\\com\\qa\\sonyops\\configuration\\config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Parameters(value = { "ce_url1", "bc_url1", "url1", "username1", "password1", "browser1", "Flash_url1" })
	// @BeforeSuite(alwaysRun=true,enabled=true)
	@BeforeClass(alwaysRun = true, enabled = true)
	public void LoadChromeDataToTempFolder(String ce_url1, String bc_url1, String url1, String username1,
			String password1, String browser1, String Flash_url1) throws Exception {

		ce_url = ce_url1;
		bc_url = bc_url1;
		url = url1;
		username = username1;
		password = password1;
		browser = browser1;
		Flash_url = Flash_url1;

		String directory = System.getProperty("user.dir") + File.separator + "Reports" + File.separator
				+ "SonyOpsReport.html";
		extent = new ExtentReports(directory, true); // passing true means replace if any old report is present
		extent.addSystemInfo("host name", "PFTBLR-RMZ-021 windows");
		extent.addSystemInfo("User Name", "Shilpashree");
		extent.addSystemInfo("Environment", "Production");

		// logger=new ExtentTest();

		File DestFolder = new File("D:\\automationPULLprojects\\SonyOPS-\\temp");
		File SrcFolder = new File("D:\\automationPULLprojects\\SonyOPS-\\chromedata");

		if (DestFolder.exists()) {
			FileUtils.copyDirectory(SrcFolder, DestFolder);
		} else {
			DestFolder.mkdir();
			FileUtils.copyDirectory(SrcFolder, DestFolder);
		}

		// DOMConfigurator.configure("D:\\automationPULLprojects\\SonyOPS-\\src\\main\\resources\\log4j.properties");
		PropertyConfigurator.configure(Log4j_ConfigFilePath);
		log = Logger.getLogger(this.getClass().getName());
		// Logger.getLogger(BaseClass.class.getName());
	}

	// Initialization method-To get the details of my browser
	public void initialisation() {
		// String browserName = prop.getProperty("browser");
		String browserName = browser;
		System.out.println("Selected browser is" + " " + browserName);
		if (browserName.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 1);
			// options.addArguments("--no-sandbox"); //
			options.addArguments("--disable-dev-shm-usage");
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("profile.default_content_setting_values.plugins", 1);
			prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
			prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
			prefs.put("profile.default_content_settings.popups", 1);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			prefs.put("PluginsAllowedForUrls", "https://sonyops.clearhub.tv/BC/Product/Modules/SignIn.aspx");
			options.setExperimentalOption("prefs", prefs);

			// options.addArguments("user-data-dir=C:\\Users\\Manjushree\\Documents\\SonyOPS-\\temp\\User
			// Data");

			// to suppress error logs in console
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_path"));
			driver = new ChromeDriver(options);
		} else if (browserName.equals("FireFox")) {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("geckodriver_path"));
			driver = new FirefoxDriver();
		} else {
			System.out.println("no browser value is given");
		}

		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListenerHandler to register it with
		// EventFiringWebDriver
		eventlistener = new WebEventListener();
		e_driver.register(eventlistener);
		driver = e_driver; // assign EventFiringWebDriver to our main driver

		// log = Logger.getLogger(this.getClass().getName());

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS); // if application is
																									// taking too much
																									// of time then we
																									// have to increase
																									// the time in every
																									// script to avoid
																									// that will create
																									// on util class
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		// driver.get(prop.getProperty(url));
		// driver.get(Portalurl);

	}

	public void enterURL(String portalUrl) {
		driver.get(portalUrl);
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		// extent.close(); //
	}

	@AfterSuite(enabled = true)
	public void DeleteTemporaryFolder() throws Exception {

		File temp = new File("D:\\automationPULLprojects\\SonyOPS-\\temp");
		if (temp.exists()) {
			FileUtils.deleteDirectory(temp);
		}

	}

}
