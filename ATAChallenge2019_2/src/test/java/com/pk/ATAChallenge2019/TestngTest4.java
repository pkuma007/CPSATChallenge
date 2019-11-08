package com.pk.ATAChallenge2019;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*Test written using Test NG 
 * @author : Pravin Kumar
 * 4. Using TestNG and WebDriver script, open https://www.nseindia.com/ in Chrome
and do the below : (8 Points)
a. In the Equity window enter the company name, reference image attached.
b. Read the following from an Excel file which has a single column having the
company names in it. Read the file and for every company name below
1. Bajaj Finserv Limited
2. Hindustan Unilever
3. Mahindra & Mahindra Limited
4. GAILEnter the company name in the equity text box
c. Click on the magnifying glass or hit enter
d. A new page opens up with the details of the company
e. Fetch and Print the following on the console
 1. Face Value
 2. 52 week high
 3. 52 week low
f. Take screenshot of the page.
*/
public class TestngTest4 {
	private WebDriver driver;
	App a = new App();
	
	@BeforeClass
	public void beforeClass() {
		//Launch Url: https://www.nseindia.com/
		driver = a.launchBrowser("CHROME","https://www.nseindia.com/");
	}
	
	@Test
	void nseindiaChromeTest4() throws IOException, InterruptedException {
		System.out.println("**************************************************");
		WebDriverWait wait=new WebDriverWait(driver, 50);
		String enterCompName=null;
		//Create an object of File class to open xlsx file
		String filePath = "TestData.xlsx";
	    File file = new File(filePath);
	    FileInputStream inputStream = new FileInputStream(file);
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    //Read sheet inside the workbook by its name
	    Sheet sheet = workbook.getSheet("Sheet1");
	    //Find number of rows in excel file
	    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
	    //Create a loop over all the rows of excel file to read it
	    for (int i = 0; i < rowCount+1; i++) {
	        Row row = sheet.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {
	        	enterCompName = row.getCell(j).getStringCellValue();
	        	System.out.println("Equity Name: "+ enterCompName);
				//Enter in Equity Window
				WebElement equityWindow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//input[@id='keyword']")));
				equityWindow.sendKeys(enterCompName);
				//hit Enter
				Thread.sleep(2000);
				equityWindow.sendKeys(Keys.ENTER);
				
				//Wait for Equity Page
				String companyName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//span[@id='companyName']"))).getText();
				Assert.assertTrue(companyName.contains(companyName), "Company "+enterCompName+" Page not appear");
				//Fetch Face Value
				String faceValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "faceValue"))).getText();
				System.out.println("1. Face Value: " + faceValue);
				//Fetch 52 week high
				String high52 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "high52"))).getText();
				System.out.println("2. 52 week high: " + high52);
				//Fetch 52 week low
				String low52 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "low52"))).getText();
				System.out.println("2. 52 week low: " + low52);
				
				//Screenshot
				a.takeScreenShot(driver, "Screenshots\\Test4_SearchedEquity_"+enterCompName+".png");
				
				System.out.println("**************************************************");
			
	            
	        }
	        workbook.close();
	    } 
			
		
	}
	
	
	
	@AfterClass
	public void afterClass() {
		//Close Browser
		driver.quit();
	}
}
