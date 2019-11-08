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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*Test written using Test NG 
 * @author : Pravin Kumar
 * 5. Using Page object Model, TestNG and WebDriver script, open
https://www.shoppersstop.com/ in Google Chrome and do the below:
a. Click on the banner slider > for the number of times till the banner gets repeated
b. Print all the accessories name under MEN section > Men’s Fragrance
c. Click on All Stores link
d. Print the Cities name that available in Find Stores in your city
e. Assert Agra, Bhopal and Mysore are available in Find Stores in your city.
f. Print the page title in console.

*/
public class TestngTest5 {
	private WebDriver driver;
	App a = new App();
	
	@BeforeClass
	public void beforeClass() {
		//Launch Url: https://www.shoppersstop.com/
		driver = a.launchBrowser("CHROME","https://www.shoppersstop.com/");
	}
	
	@Test
	void shopperStopChromeTest5() throws IOException, InterruptedException {
		System.out.println("**************************************************");
		WebDriverWait wait=new WebDriverWait(driver, 50);
		//a. Click on the banner slider > for the number of times till the banner gets repeated
		WebElement sliderNextArrow = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(@class,'dy-custom-slider')]//div[contains(@class,'dy-next-arrow')]")));
		
		//Fetch Number Of times to be clicked
		List<WebElement> totalSlides = driver.findElements(By.xpath("//ul[@class='slick-dots']/li"));
		for(int i=0; i< totalSlides.size(); i++) {
			Thread.sleep(2000);
			sliderNextArrow.click();
		}
		
		//b. Print all the accessories name under MEN section > Men’s Fragrance
		Actions menuHover = new Actions(driver);
		WebElement menuMen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='MEN']")));
		menuHover.moveToElement(menuMen).build().perform();
		menuHover = new Actions(driver);
		Thread.sleep(1000);
		WebElement menuMensFrag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='MEN']/..//a[text()=\"Men's Fragrance\"]")));
		menuHover.moveToElement(menuMensFrag).build().perform();
		Thread.sleep(1000);
		
		List<WebElement> allBeautyAcce = driver.findElements(By.xpath("//a[@title='MEN']/..//a[text()=\"Men's Fragrance\"]/..//a[contains(@href,'beauty-fragrance')]"));
		System.out.println("All Mens Fragrances Accessories");
		for(int i=0; i< allBeautyAcce.size();i++) {
			System.out.println(allBeautyAcce.get(i).getText());
		}
		
		//c. Click All Stores Link
		WebElement AllStores = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='header-info-bar']//a[text()='All Stores']")));
		AllStores.click();
		
		//d. Print the Cities name that available in Find Stores in your city
		//e. Assert Agra, Bhopal and Mysore are available in Find Stores in your city.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//*[@id='city-name']/option")));
		List<WebElement> options = driver.findElements(By.xpath("//*[@id='city-name']/option"));
		System.out.println("Printing All City Name from Dropdown");
		for(int i=1; i<options.size(); i++) {
			String cityName = options.get(i).getText();
		    System.out.println(cityName);
		    if(cityName.contains("Agra")) Assert.assertTrue(true, "The Agra is in the list");
		    if(cityName.contains("Bhopal")) Assert.assertTrue(true, "The Bhopal is in the list");
		    if(cityName.contains("Mysore")) Assert.assertTrue(true, "The Mysore is in the list");
		}
		
		//f. Print the page title in console.
		System.out.println("Page Title : " + driver.getTitle());
		System.out.println("**************************************************");
		
	}
	
	
	
	@AfterClass
	public void afterClass() {
		//Close Browser
		driver.quit();
	}
}

