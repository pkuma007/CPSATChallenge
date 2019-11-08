package com.pk.ATAChallenge2019;

import com.pk.ATAChallenge2019.App;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
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
 * 2. Using TestNG and WebDriver script, open https://www.nseindia.com/ in Google
Chrome and do the below
a. Using FindElements method of webdriver get the advances, Declines and
Unchanged numbers from the maket watch window - reference image
b. Print the Minimum number
e.g. Unchanged 0
*/
public class TestngTest2 {
	private WebDriver driver;
	App a = new App();
	
	@BeforeClass
	public void beforeClass() {
		//Launch Url: https://www.nseindia.com/
		driver = a.launchBrowser("CHROME","https://www.nseindia.com/");
	}
	
	@Test
	void nseindiaChromeTest2() {
		WebDriverWait wait=new WebDriverWait(driver, 50);
		System.out.println("**************************************************");
		System.out.println("Market Wath Windows Details: ");
		//get Advances 
		String advances = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//li[@id='advances']/span"))).getText();
		System.out.println("Advances Value: " + advances);
		//get declines 
		String declines = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//li[@id='declines']/span"))).getText();
		System.out.println("Declines Value: " + declines);
		//get Unchanged 
		String unchanged = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//li[@id='unchanged']/span"))).getText();
		System.out.println("Unchanged Value: " + unchanged);
		int numbers[]= {Integer.parseInt(advances),Integer.parseInt(declines),Integer.parseInt(unchanged)};
		Arrays.sort(numbers);
		int min = numbers[0];
		if(min==Integer.parseInt(advances)) System.out.println("Minimum Number: Advances "+min);
		if(min==Integer.parseInt(declines)) System.out.println("Minimum Number: Declines "+min);
		if(min==Integer.parseInt(unchanged)) System.out.println("Minimum Number: Unchanged "+min);
		
		System.out.println("**************************************************");
		
		
	}
	
	
		
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
