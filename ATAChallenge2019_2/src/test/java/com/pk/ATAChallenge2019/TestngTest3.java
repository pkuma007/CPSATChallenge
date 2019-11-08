package com.pk.ATAChallenge2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 * 3. Using TestNG and WebDriver script, open https://www.nseindia.com/ in Firefox and
do the below
a. In the Equity window, reference image attached.
b. Enter the company name Eicher Motors Limited
c. Click on the magnifying glass or hit enter
d. A new page opens up with the details of the company
e. Take screen shot of the searched equity
f. Fetch and Print the following on the console
 1. Face Value
 2. 52 week high
 3. 52 week low
*/
public class TestngTest3 {
	private WebDriver driver;
	App a = new App();
	
	@BeforeClass
	public void beforeClass() {
		//Launch Url: https://www.nseindia.com/
		driver = a.launchBrowser("FIREFOX","https://www.nseindia.com/");
	}
	
	@Test
	void nseindiaFirfoxTest3() throws IOException, InterruptedException {
		System.out.println("**************************************************");
		WebDriverWait wait=new WebDriverWait(driver, 50);
		//Enter in Equity Window
		WebElement equityWindow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//input[@id='keyword']")));
		equityWindow.sendKeys("Eicher Motors Limited");
		//hit Enter
		Thread.sleep(2000);
		equityWindow.sendKeys(Keys.ENTER);
		
		//Wait for Eicher Page
		String companyName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//span[@id='companyName']"))).getText();
		Assert.assertTrue(companyName.contains(companyName), "Company Name Eicher Page not appear");
		//Screenshot
		a.takeScreenShot(driver, "Screenshots\\Test3_SearchedEquity.png");
		//Fetch Face Value
		String faceValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "faceValue"))).getText();
		System.out.println("1. Face Value: " + faceValue);
		//Fetch 52 week high
		String high52 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "high52"))).getText();
		System.out.println("2. 52 week high: " + high52);
		//Fetch 52 week low
		String low52 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "low52"))).getText();
		System.out.println("2. 52 week low: " + low52);
		
		System.out.println("**************************************************");
		
	}
	
	
	
	@AfterClass
	public void afterClass() {
		//Close Browser
		driver.quit();
	}
}
