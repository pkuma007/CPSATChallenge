package com.pk.ATAChallenge2019;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;



/**
 * Main Class for Tests
 *
 */
public class App 
{
	//Launching Browser based on conditions
	public  WebDriver launchBrowser(String browserType, String url) {
		WebDriver driver=null;
		if(browserType.equals("CHROME")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browserType.equals("FIREFOX"))  {
			System.setProperty("webdriver.firefox.driver", "geckodriver.exe");
			Proxy proxy = new Proxy();
		    proxy.setProxyType(Proxy.ProxyType.AUTODETECT);
		    FirefoxOptions options = new FirefoxOptions();
		    options.setProxy(proxy);
			driver = new FirefoxDriver(options);
			
		}else {
			System.out.println("Please provide correct Driver browserType to launch");
		}
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		return driver;
	}
    
	
	
	//Take screenshot
	public void takeScreenShot(WebDriver webdriver, String filePath) throws IOException{
	
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File(filePath);
		FileUtils.copyFile(SrcFile, DestFile);
	}

   
}
