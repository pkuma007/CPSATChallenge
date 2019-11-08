package com.pk.ATAChallenge2019;




/*1. Using Junit and WebDriver script, Go To http://agiletestingalliance.org/ in Google
Chrome and do the below.
a. Click on the certification’s menu item
b. Count the number of certification icons visible on the page - colour icons as per
the below image. Total 12. Print the URL every image is pointing to.
c. Confirm if the URL’s are working or not. If the URL is broken highlight that in a
soft Assert
 d. Take a screenshot
 e. Hover on CP-CCT
 f. Take a screenshot after hovering such that it shows the CP-CCT on the stored
screenshot image
*/

import java.util.List;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.assertj.core.api.SoftAssertions;

public class JUnitTest1 {
	private static WebDriver driver;
	private SoftAssertions softAssertions;
	static App a = new App();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Launch Url: http://agiletestingalliance.org/
		driver = a.launchBrowser("CHROME","http://agiletestingalliance.org/");
		
	}
	@AfterClass
	public static void setUpAfterClass() throws Exception {
		//close Browser
		driver.quit();
	}
	
	@Test
	public void agiletestingallianceChromeTest1() throws Exception {
		WebDriverWait wait=new WebDriverWait(driver, 50);
		//Click on the certification’s menu item
		WebElement Certifications = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Certifications']")));
		Certifications.click();
		
		WebElement imageMap = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//map[@name='image-map']")));
		List<WebElement> certList = imageMap.findElements(By.tagName("area"));
		//check 12 certification
		Assert.assertTrue("Certication List is not equal to 12", certList.size()==12);
		//Print URL for all icons
		for(WebElement element: certList) {
			String url = element.getAttribute("href");
			System.out.println("URL For "+ element.getAttribute("title")+" -> "+url);
			//Check URL Working or Not
			HttpURLConnection conn;
			try {
				if(url == null || url.isEmpty()){
					System.out.println("URL is either not configured for anchor tag or it is empty");
	                continue;
	            }
				//Proxy for my Office Network. 
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("genproxy", 8080));
				conn = (HttpURLConnection)(new URL(url).openConnection(proxy));
				conn.setRequestMethod("HEAD");
				conn.connect();
				int respCode = conn.getResponseCode();
				if(respCode >= 400){
				    System.out.println(url+" -> is a broken URL");
				    softAssertions.assertThat(respCode).isEqualTo(200);
				}
				//Checking response code as 200 for success response
				else if(respCode == 200){ 
				    System.out.println(url+" -> is a valid URL");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//Take a screenshot
		a.takeScreenShot(driver, "Screenshots\\Test1_certifications.png");
		
		//Hover on CP-CCT
		for(WebElement element: certList) {
			if(element.getAttribute("title").equalsIgnoreCase("CP-CCT")) {
				//Fetch Coordinate to Hover on cp-cct
				String coords=element.getAttribute("coords");
				String[] coordsArr=coords.split(",");
				int X = Integer.parseInt(coordsArr[0]);
				int Y = Integer.parseInt(coordsArr[1]);
				Actions hover = new Actions(driver);
				WebElement certificationsImg = driver.findElement(By.xpath("//img[@usemap='#image-map']"));
				hover.moveToElement(certificationsImg,X,Y).perform();
				Thread.sleep(1000);
				System.out.println("CP-CCT hover done successfully");
				//Take a screenshot after hover
				a.takeScreenShot(driver, "Screenshots\\Test1_Hover_CP-CCT.png");
				break;
			}
		}
		
    }
	
	@Before
	public void setup() {
		softAssertions = new SoftAssertions();
	}
	@After
    public void tearDown() {
        softAssertions.assertAll();
    }
}
