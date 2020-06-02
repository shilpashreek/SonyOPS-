package com.qa.sonyops.util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.sonyops.base.BaseClass;
import com.relevantcodes.extentreports.LogStatus;

public class TestNGListeners extends BaseClass implements ITestListener		
{

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		
		  try { 
			  TestUtil.TakeScreenshot_pass_fail(result.getMethod().getMethodName());
		  } catch (Exception e) 
		  { // TODO Auto-generated catch block
		  e.printStackTrace(); }
		 
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			logger.log(LogStatus.PASS, "TESTCASE IS PASSED" +" "+result.getMethod().getMethodName());
		}
		
	}

	public void onTestFailure(ITestResult result) 
	{
	  //System.out.println("Testcase failed");
	  
		
		/*
		 * try { TestUtil.TakeScreenshot_pass_fail(result.getMethod().getMethodName());
		 * } catch (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		 
	  
	  if(result.getStatus()==ITestResult.FAILURE)
	  {
		  try {
			logger.log(LogStatus.FAIL, "TESTCASE IS FAILED" +" "+result.getMethod().getMethodName());
			  logger.log(LogStatus.FAIL, "TESTCASE IS FAILED" +" " +result.getThrowable());
			  String screenshotPath=TestUtil.TakeScreenshot_pass_fail(result.getMethod().getMethodName());
			  logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
			  logger.log(LogStatus.FAIL, logger.addScreencast(screenshotPath));
		} catch (Exception e) {
              System.out.println("Failed to attach screenshot to EXTENT REPORT" +e.getMessage());
			e.printStackTrace();
		}
	  }
	//extent.flush();
	//extent.endTest(logger);	
	}

	public void onTestSkipped(ITestResult result) {
		if(result.getStatus()==ITestResult.SKIP)
		{
			logger.log(LogStatus.SKIP, "TEST CASE IS SKIPPED" +" "+result.getMethod().getMethodName());
			logger.log(LogStatus.SKIP, "TEST CASE IS SKIPPED" +" " +result.getThrowable());
		}
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
	
}
