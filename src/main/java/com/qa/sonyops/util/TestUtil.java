package com.qa.sonyops.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import coom.qa.sonyops.base.BaseClass;

public class TestUtil extends BaseClass
{
	
public static long PAGE_LOAD_TIMEOUT = 30;
public static long IMPLICIT_WAIT = 30;

public static String TESTDATA_SHEET_PATH = "D:/PFTprojects/sonyops/src/main/java/com/qa/sonyops/testdata/SonyopsTestData.xlsx";
static Workbook book;
static Sheet sheet;


public void SwitchToFrame()
{
	driver.switchTo().frame("CEUploadeframe");
}

public static Object[][] getTestData(String sheetName)
{
	FileInputStream file = null;
	try 
	{
		file = new FileInputStream(TESTDATA_SHEET_PATH);
	} 
	catch (FileNotFoundException e)
	{
		e.printStackTrace();
	}
	try 
	{
		book = WorkbookFactory.create(file);
	} 
	catch (InvalidFormatException e) 
	{
		e.printStackTrace();
	}
	catch (IOException e)
	{
		e.printStackTrace();
	}
	sheet = book.getSheet(sheetName);
	Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	for (int i = 0; i< sheet.getLastRowNum(); i++)
	{
		for (int k=0; k<sheet.getRow(0).getLastCellNum(); k++)
		{
			data[i][k] = sheet.getRow(i+1).getCell(k).toString();
		}
	}
	
	return data;
	
	
}


	
	
	
}

