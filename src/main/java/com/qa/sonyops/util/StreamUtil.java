package com.qa.sonyops.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.sonyops.base.BaseClass;

public class StreamUtil extends BaseClass 
{
    StreamUtil stream;
    TestUtil testutil;
    
	// method to check if slider is loading
	public boolean CheckSlider(WebElement element) 
	{
		boolean sliderdisplayed = false;
		try {
			sliderdisplayed = element.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sliderdisplayed;
	}

	// method to check if player components are loading
	public boolean PlayerComponents(WebElement element) 
	{
		boolean PlayerKeys = false;
		try {
			new WebDriverWait(driver, 80).until(ExpectedConditions.visibilityOf(element));
			PlayerKeys = element.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PlayerKeys;
	}

	// method to check if player is loading No preview
	public boolean NoPreview(WebElement element) 
	{
		boolean No_preview = false;
		try {
			No_preview = element.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return No_preview;
	}

	// method to check if any error code is displayed in player
	public boolean ErrorCodeDisplayed(WebElement element) 
	{
        boolean ErrorCode = false;
		try {
			ErrorCode = element.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ErrorCode;
	}

	// method to get the starting TimeCode of the standalone player
	public String GetTimeCode_in(WebElement element)
	{
		String StartTime = "null";
		try {
			StartTime = element.getText();
			System.out.println(StartTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StartTime;
	}

	// method to get total duration of the StandAlone player
	public String GetTotalDurationOfVideo(WebElement element) {
		String EndTime = "null";
		try {
			//WebElement VideoDuration = driver.findElement(By.cssSelector("div#CMP_CatalogPlayerV2Container_dvSeekTime"));
			EndTime = element.getText();
			// EndTime=Integer.valueOf(VideoDuration.getText());
			System.out.println(EndTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EndTime;

	}

	// method to click on play/pause player key
	public void ClickPlay_pausekey(WebElement element) 
	{
		TestUtil.Click(driver, 10, element);
	}

	// method to click on stop player key
	public void ClickStopkey(WebElement element) 
	{
		TestUtil.Click(driver, 10, element);
	}

	public boolean CheckIfPlayerisLoading(WebElement PlayerComp ,WebElement slider,WebElement TC_IN,WebElement TC_OUT ,WebElement Play,WebElement error,WebElement preview) throws InterruptedException {

		boolean Streaming_status = false;

		boolean sliderIsDisplayed;
		boolean PlayerHeader;
		boolean PlayerControl_panel;
		boolean No_preview_availabel;
		boolean Error_code_displayed;

		stream=new StreamUtil();
		Thread.sleep(2000);
		if (stream.PlayerComponents(PlayerComp)) {
			System.out.println("player is loading");
			new WebDriverWait(driver, 200)
					.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".playerStateDisplayText")));
			if (stream.CheckSlider(slider)) {
				testutil=new TestUtil();
				String TC_before = stream.GetTimeCode_in(TC_IN);
				String TC_out = stream.GetTotalDurationOfVideo(TC_OUT);
				Thread.sleep(3000);
				stream.ClickPlay_pausekey(Play);
				Thread.sleep(30000);
				stream.ClickPlay_pausekey(Play);
				String TC_after = stream.GetTimeCode_in(TC_IN);
				System.out.println("Total duration of the video" + TC_out);
				Streaming_status = testutil.TimecodeComparison(TC_before, TC_after);
		
			}

			else {
				System.out.println(error.getText());
				System.out.println("player is loaded but it is not streaming");
			}

		}

		else if (stream.ErrorCodeDisplayed(error)) {
			System.out.println(error.getText());
		}

		else if (stream.NoPreview(preview)) {
			System.out.println("Player loaded with No preview available screen");
		}

		else {
			System.out.println("player is not loading");
		}

		// return Streaming_status;
		return stream.CheckSlider(slider);
	}

}
