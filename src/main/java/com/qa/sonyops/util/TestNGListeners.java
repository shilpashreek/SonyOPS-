package com.qa.sonyops.util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.sonyops.base.BaseClass;

public class TestNGListeners extends BaseClass implements ITestListener		
{

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		TestUtil.TakeScreenshot_pass_fail(result.getMethod().getMethodName());
		
	}

	public void onTestFailure(ITestResult result) {
	  System.out.println("Testcase failed");
	  TestUtil.TakeScreenshot_pass_fail(result.getMethod().getMethodName());
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
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
