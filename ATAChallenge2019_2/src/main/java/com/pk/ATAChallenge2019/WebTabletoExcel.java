package com.pk.ATAChallenge2019;

import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.lang.*; 
import  java.io.*;  


public class WebTabletoExcel  
{ 
	static void webTableToExcel(String excelFileName, String sheetName , WebElement webTableRows) throws IOException  
	{     
	
		WebDriver wb = new FirefoxDriver();          
		wb.navigate().to("http://www.w3schools.com/html/html_tables.asp"); 
		wb.manage().window().maximize();     
		System.out.println(wb.getTitle() +" - WebPage has been launched");   
		
		List<WebElement> irows =   wb.findElements(By.xpath("//*[@id='main']/table[1]/tbody/tr"));     
		int iRowsCount = irows.size();     
		List<WebElement> icols =   wb.findElements(By.xpath("//*[@id='main']/table[1]/tbody/tr[1]/th"));     
		int iColsCount = icols.size();     
		System.out.println("Selected web table has " +iRowsCount+ " Rows and " +iColsCount+ " Columns");     
		System.out.println();      
		
		FileOutputStream fos = new FileOutputStream("D://Software//AutomationPractise//WebTableTOSpreedsheet.xlsx");                                 
		
		XSSFWorkbook wkb = new XSSFWorkbook();       
		XSSFSheet sheet1 = wkb.createSheet(sheetName); 
		
		for (int i=1;i<=iRowsCount;i++)      
		{      
			XSSFRow excelRow = sheet1.createRow(i);             
			
			for (int j=1; j<=iColsCount;j++)                    
			{           
				    WebElement val= wb.findElement(By.xpath("//*[@id='main']/table[1]/tbody/tr["+i+"]/td["+j+"]"));             
					String a = val.getText();                    
					System.out.print(a);                            
					
					XSSFCell excelCell = excelRow.createCell(j);                      
					//excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);                   
					excelCell.setCellValue(a);   
					
					//wkb.write(fos);       
				      
			}               
		}     
		fos.flush();     
		wkb.write(fos);     
		fos.close();     
	}
}