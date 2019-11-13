package com.pk.ATAChallenge2019;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*Test written using Test NG 
 * @author : Pravin Kumar
 * 6. Use Chrome to do the following
Open https://www.nseindia.com/products.htm
a) Hover on Live Market
b) Click on Top Ten Gainers / Losers
c) Store the Top gainers table values in an excel sheet
d) Store all the top losers table values in an excel sheet
e) Assert if the percentage change is high to low for each of Top gainers and losers
f) Extract the date and time when top gainers and top losers data was taken from
the NSE website and compare with the system time
*/
public class TestngTest6 {
	private WebDriver driver;
	App a = new App();
	
	@BeforeClass
	public void beforeClass() {
		//Launch Url: https://www.nseindia.com/products.htm
		driver = a.launchBrowser("CHROME","https://www.nseindia.com/products.htm");
	}
	
	@Test
	void nseindiaChromeTest6() throws IOException, InterruptedException, ParseException {
		System.out.println("**************************************************");
		WebDriverWait wait=new WebDriverWait(driver, 50);
		//a) Hover on Live Market
		Actions menuHover = new Actions(driver);
		WebElement menuMen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main_livemkt")));
		menuHover.moveToElement(menuMen).build().perform();
		//b) Click on Top Ten Gainers / Losers
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Top Ten Gainers / Losers')]"))).click();
		
		//Create Excel file
		FileOutputStream fos = new FileOutputStream("TopGainersLosers.xlsx");                                 
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		
		//c) Store the Top gainers table values in an excel sheet
		//click Gainers Tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Gainers']"))).click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='topGainers']")));
		
		List<WebElement> irows = driver.findElements(By.xpath("//table[@id='topGainers']/tbody/tr"));     
		List<WebElement> icols = driver.findElements(By.xpath("//table[@id='topGainers']/tbody/tr/th"));     
		int iRowsCount = irows.size();     
		int iColsCount = icols.size();     
		
		XSSFSheet sheet1 = workbook.createSheet("TopGainers"); 
		List<String> percentageChangeGainersList = new ArrayList<String>();
		for (int i=1;i<=iRowsCount;i++)      
		{      
			XSSFRow excelRow = sheet1.createRow(i);             
			
			for (int j=1; j<=iColsCount;j++)                    
			{  
				WebElement val= null;
				if(i==1) {
					val= driver.findElement(By.xpath("//table[@id='topGainers']/tbody/tr["+i+"]/th["+j+"]"));
				}else {
					val= driver.findElement(By.xpath("//table[@id='topGainers']/tbody/tr["+i+"]/td["+j+"]"));
					
					String val1= driver.findElement(By.xpath("//table[@id='topGainers']/tbody/tr[1]/th["+j+"]")).getText();
					if(val1.contains("Change")) percentageChangeGainersList.add(val.getText());
				}
				
			    String a = val.getText();                    
				System.out.println(a);      
				XSSFCell excelCell = excelRow.createCell(j);                      
				//excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);                   
				excelCell.setCellValue(a);   
			}               
		}
		
		
		//d) Store all the top losers table values in an excel sheet
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Losers']"))).click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='topLosers']")));
		irows =   driver.findElements(By.xpath("//table[@id='topLosers']/tbody/tr"));     
		icols =   driver.findElements(By.xpath("//table[@id='topLosers']/tbody/tr/th"));     
		iRowsCount = irows.size();     
		iColsCount = icols.size();     
		
		XSSFSheet sheet2 = workbook.createSheet("TopLosers"); 
		List<String> percentageChangeLosersList = new ArrayList<String>();
		
		for (int i=1;i<=iRowsCount;i++)      
		{      
			XSSFRow excelRow = sheet2.createRow(i);             
			
			for (int j=1; j<=iColsCount;j++)                    
			{           
				WebElement val= null;
				if(i==1) {
					val= driver.findElement(By.xpath("//table[@id='topLosers']/tbody/tr["+i+"]/th["+j+"]"));
				}else {
					val= driver.findElement(By.xpath("//table[@id='topLosers']/tbody/tr["+i+"]/td["+j+"]"));
					String val1= driver.findElement(By.xpath("//table[@id='topLosers']/tbody/tr[1]/th["+j+"]")).getText();
					if(val1.contains("Change")) percentageChangeLosersList.add(val.getText());
				} 
				String a = val.getText();                    
				System.out.println(a);      
				XSSFCell excelCell = excelRow.createCell(j);                      
				//excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);                   
				excelCell.setCellValue(a);   
					  
			}               
		}
		
		fos.flush();     
		workbook.write(fos);     
		fos.close();  
		
		
		//e) Assert if the percentage change is high to low for each of Top gainers and losers
		List<String> unSortedList = percentageChangeGainersList;
		percentageChangeGainersList.sort(Collections.reverseOrder());
		Assert.assertEquals(percentageChangeGainersList, unSortedList);
		
		unSortedList = percentageChangeLosersList;
		percentageChangeLosersList.sort(Collections.reverseOrder());
		Assert.assertEquals(percentageChangeLosersList, unSortedList);
		//f) Extract the date and time when top gainers and top losers data was taken from the NSE Web site and compare with the system time
		String dateTime= driver.findElement(By.id("dataTime")).getText();
		dateTime = dateTime.replaceAll("As on ", "").trim();
		
		//String sDate1="As on Nov 11, 2019 16:00:17 IST";  
		SimpleDateFormat sdf =new SimpleDateFormat("MMM dd, yyyy HH:mm:ss Z");
		
		Date NSEdateTime = sdf.parse(dateTime);
		Date sysDate = sdf.parse(sdf.format(new Date()));
		
		if (NSEdateTime.compareTo(sysDate) > 0) {
            System.out.println("NSEdateTime: "+NSEdateTime+" is after sysDate: "+sysDate);
        } else if (NSEdateTime.compareTo(sysDate) < 0) {
            System.out.println("NSEdateTime: "+NSEdateTime+" is before sysDate: "+sysDate);
        } else if (NSEdateTime.compareTo(sysDate) == 0) {
            System.out.println("NSEdateTime: "+NSEdateTime+" is equal to sysDate: "+sysDate);
        } 
		
	    System.out.println("**************************************************");
		
	}
	
	
	
	@AfterClass
	public void afterClass() {
		//Close Browser
		driver.quit();
	}
}
