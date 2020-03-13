package com.qa.sonyops.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import coom.qa.sonyops.base.BaseClass;

public class Plugin_DownloadsPage extends BaseClass
{
@FindBy(id = "PluginDownloads_title_holder")
WebElement AsperaPageHeader;

@FindBy(xpath = "//a")
WebElement AllLinks_in_Plugins_page;

@FindBy(id = "closePFTPopup")
WebElement CloseAsperaPlugin_popUp;

//initialise object repository
public String GetAserpaPluginHeaderText()
{
	String Asp_headerText = AsperaPageHeader.getText();
	System.out.println(Asp_headerText);
	return Asp_headerText;
}

public String GetVisibleTextFromAsperaPage()
{
	WebElement VisibileText = driver.findElement(By.id("pageOverlay"));
	return VisibileText.getText();
}

public void GetCountOfLinks()
{
	List<WebElement> links_in_asperaPage = driver.findElements(By.xpath("//a"));
	System.out.println("total number of counts in AsperaPluginPop-up is" +" " +links_in_asperaPage.size());
	
	//traversing through all the links
	for(WebElement link : links_in_asperaPage)
	{
		System.out.println(link.getText());
	}
	
}

public void CloseAsperaPopup()
{
	CloseAsperaPlugin_popUp.click();
}


}
