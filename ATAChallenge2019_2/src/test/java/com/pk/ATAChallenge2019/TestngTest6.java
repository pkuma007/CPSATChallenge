package com.pk.ATAChallenge2019;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	void nseindiaChromeTest6() throws IOException, InterruptedException {
		System.out.println("**************************************************");
		WebDriverWait wait=new WebDriverWait(driver, 50);
		//a) Hover on Live Market
		//main_livemkt
		//b) Click on Top Ten Gainers / Losers
		//a[contains(text(),'Top Ten Gainers / Losers')]
		
		
		FileOutputStream fos = new FileOutputStream("TopGainersLosers.xlsx");                                 
		
		XSSFWorkbook wkb = new XSSFWorkbook(); 
		//c) Store the Top gainers table values in an excel sheet

		List<WebElement> irows =   driver.findElements(By.xpath("//*[@id='main']/table[1]/tbody/tr"));     
		int iRowsCount = irows.size();     
		List<WebElement> icols =   driver.findElements(By.xpath("//*[@id='main']/table[1]/tbody/tr[1]/th"));     
		int iColsCount = icols.size();     
		
		XSSFSheet sheet1 = wkb.createSheet("TopGainers"); 
		
		for (int i=1;i<=iRowsCount;i++)      
		{      
			XSSFRow excelRow = sheet1.createRow(i);             
			
			for (int j=1; j<=iColsCount;j++)                    
			{           
			    WebElement val= driver.findElement(By.xpath("//*[@id='main']/table[1]/tbody/tr["+i+"]/td["+j+"]"));             
				String a = val.getText();                    
				System.out.print(a);                            
				
				XSSFCell excelCell = excelRow.createCell(j);                      
				//excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);                   
				excelCell.setCellValue(a);   
					  
			}               
		}
		//d) Store all the top losers table values in an excel sheet
		fos.flush();     
		wkb.write(fos);     
		fos.close();  
		
		
		//e) Assert if the percentage change is high to low for each of Top gainers and losers
		//f) Extract the date and time when top gainers and top losers data was taken from the NSE website and compare with the system time
		
		
	}
	
	
	
	@AfterClass
	public void afterClass() {
		//Close Browser
		driver.quit();
	}
}
