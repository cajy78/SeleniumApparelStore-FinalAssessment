package org.apparelStore1.test.functional;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.apparelStore1.se.SeleniumWebDriver;
import org.apparelStore1.se.pages.Home;
import org.apparelStore1.se.pages.ProductInfo;
import org.apparelStore1.se.pages.Search;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSocialShare extends TestCase
{
	private WebDriver driver;
	private int ssCtr;
	
	@BeforeClass
	public void initSSCtr()
	{
		ssCtr = 0;
	}
	
	@BeforeMethod
	public void launchBrowser()
	{
		driver = SeleniumWebDriver.startDesignatedBrowser();
	}
	
	@Test(dataProvider = "getTestData")
	public void runTestCase(Hashtable<String, String> testData)
	{
		try{
			driver.get(TestingProperties.getSiteURL());
			driver.manage().window().maximize();
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			Actions ma = new Actions(driver);
			
			String parentWindow = driver.getWindowHandle();
			String childWindow = null;
			new Home(driver);
			Pages.waitForLoad(driver);
			
			if(testData.get("NavBarMainMenu").equalsIgnoreCase("WOMEN"))
			{
				if(testData.get("NavBarSubMenu").equalsIgnoreCase("t-shirts"))
				{
					ma.moveToElement(Pages.navBarList.get(0)).build().perform();
					Pages.waitForNavBarSubElement(driver, "T-shirts");
					Pages.locateDynamicNavBarElement(driver, "T-shirts").click();
				}
				else if(testData.get("NavBarSubMenu").equalsIgnoreCase("Blouses"))
				{
					ma.moveToElement(Pages.navBarList.get(0)).build().perform();
					Pages.waitForNavBarSubElement(driver, "Blouses");
					Pages.locateDynamicNavBarElement(driver, "Blouses").click();
				}
				else if(testData.get("NavBarSubMenu").equalsIgnoreCase("Casual Dresses"))
				{
					ma.moveToElement(Pages.navBarList.get(0)).build().perform();
					Pages.waitForNavBarSubElement(driver, "Casual Dresses");
					Pages.locateDynamicNavBarElement(driver, "Casual Dresses").click();
				}
				else if(testData.get("NavBarSubMenu").equalsIgnoreCase("Evening Dresses"))
				{
					ma.moveToElement(Pages.navBarList.get(0)).build().perform();
					Pages.waitForNavBarSubElement(driver, "Evening Dresses");
					Pages.locateDynamicNavBarElement(driver, "Evening Dresses").click();
				}
				else if(testData.get("NavBarSubMenu").equalsIgnoreCase("Summer Dresses"))
				{
					ma.moveToElement(Pages.navBarList.get(0)).build().perform();
					Pages.waitForNavBarSubElement(driver, "Summer Dresses");
					Pages.locateDynamicNavBarElement(driver, "Summer Dresses").click();
				}
			}
			else if(testData.get("NavBarMainMenu").equalsIgnoreCase("DRESSES"))
			{
				if(testData.get("NavBarSubMenu").equalsIgnoreCase("Casual Dresses"))
				{
					ma.moveToElement(Pages.navBarList.get(3)).build().perform();
					Pages.waitForNavBarSubElement(driver, "CASUAL DRESSES");
					Pages.locateDynamicNavBarElement(driver, "CASUAL DRESSES").click();
				}
				else if(testData.get("NavBarSubMenu").equalsIgnoreCase("Evening Dresses"))
				{
					ma.moveToElement(Pages.navBarList.get(3)).build().perform();
					Pages.waitForNavBarSubElement(driver, "EVENING DRESSES");
					Pages.locateDynamicNavBarElement(driver, "EVENING DRESSES").click();
				}
				else if(testData.get("NavBarSubMenu").equalsIgnoreCase("Summer Dresses"))
				{
					ma.moveToElement(Pages.navBarList.get(3)).build().perform();
					Pages.waitForNavBarSubElement(driver, "SUMMER DRESSES");
					Pages.locateDynamicNavBarElement(driver, "SUMMER DRESSES").click();
				}
			}
			else if(testData.get("NavBarMainMenu").equalsIgnoreCase("T-SHIRTS"))
			{
				ma.click(Pages.navBarTShirt).build().perform();
			}
			
			Search result = new Search(driver);
			try{
				Pages.captureSS(driver, "TestSocialShare/testSocial_itemSearch"+ssCtr);
			}catch(IOException ioe){}
			if(result.items.size() > 0)
			{
				for(WebElement item : result.items)
				{
					executor.executeScript("arguments[0].click();", item);
					break;
				}
			}
			ProductInfo info = new ProductInfo(driver);
			String share = testData.get("Share");
			if(share.equals("Facebook"))
			{
				info.facebookShare.click();
				Set<String> windowHandles = driver.getWindowHandles();
				Iterator<String> i = windowHandles.iterator();
				while(i.hasNext())
				{
					childWindow = i.next();
					if(!parentWindow.equals(childWindow))
					{
						driver.switchTo().window(childWindow);
						Pages.waitForLoad(driver);
						info.fbEmail.clear();
						info.fbEmail.sendKeys(testData.get("socialid"));
						info.fbPass.clear();
						info.fbPass.sendKeys(testData.get("socialpwd"));
						try{
							Pages.captureSS(driver, "TestSocialShare/testSocial_fbDetails"+ssCtr);
							ssCtr++;
						}catch(IOException ioe){}
						if(driver.getTitle().contains("Facebook"))
							Assert.assertTrue(true);
						else
							Assert.assertTrue(false, "Facebook share option has failed and / or Facebook share element was not located or failed to launch");
						driver.close();
					}
				}
				driver.switchTo().window(parentWindow);
			}
			else if(share.equals("Twitter"))
			{
				info.twitterShare.click();
				Set<String> windowHandles = driver.getWindowHandles();
				Iterator<String> i = windowHandles.iterator();
				while(i.hasNext())
				{
					childWindow = i.next();
					if(!parentWindow.equals(childWindow))
					{
						driver.switchTo().window(childWindow);
						Pages.waitForLoad(driver);
						info.twitterUName.clear();
						info.twitterUName.sendKeys(testData.get("socialid"));
						info.twitterPwd.clear();
						info.twitterPwd.sendKeys(testData.get("socialpwd"));
						try{
							Pages.captureSS(driver, "TestSocialShare/testSocial_twitterDetails"+ssCtr);
							ssCtr++;
						}catch(IOException ioe){}
						if(driver.getTitle().contains("Twitter"))
							Assert.assertTrue(true);
						else
							Assert.assertTrue(false, "Twitter share option has failed and / or Twitter share element was not located or failed to launch");
						driver.close();
					}
				}
				driver.switchTo().window(parentWindow);
			}
			else if(share.equals("Google"))
			{
				info.googleShare.click();
				Set<String> windowHandles = driver.getWindowHandles();
				Iterator<String> i = windowHandles.iterator();
				while(i.hasNext())
				{
					childWindow = i.next();
					if(!parentWindow.equals(childWindow))
					{
						driver.switchTo().window(childWindow);
						Pages.waitForLoad(driver);
						info.gID.clear();
						info.gID.sendKeys(testData.get("socialid"));
						info.gID.submit();
						try{
							Pages.captureSS(driver, "TestSocialShare/testSocial_googleDetails"+ssCtr);
							ssCtr++;
						}catch(IOException ioe){}
						if(driver.getTitle().contains("Google"))
							Assert.assertTrue(true);
						else
							Assert.assertTrue(false, "Google share option has failed and / or Google share element was not located or failed to launch");
						driver.close();
					}
				}
				driver.switchTo().window(parentWindow);
			}
			else if(share.equals("Pinterest"))
			{
				info.pinterestShare.click();
				Set<String> windowHandles = driver.getWindowHandles();
				Iterator<String> i = windowHandles.iterator();
				while(i.hasNext())
				{
					childWindow = i.next();
					if(!parentWindow.equals(childWindow))
					{
						driver.switchTo().window(childWindow);
						Pages.waitForLoad(driver);
						info.pinLoginClick.click();
						Pages.waitForLoad(driver);
						info.pinEmail.clear();
						info.pinEmail.sendKeys(testData.get("socialid"));
						info.pinPwd.clear();
						info.pinPwd.sendKeys(testData.get("socialpwd"));
						try{
							Pages.captureSS(driver, "TestSocialShare/testSocial_pinterestDetails"+ssCtr);
							ssCtr++;
						}catch(IOException ioe){}
						if(driver.getTitle().contains("Pinterest"))
							Assert.assertTrue(true);
						else
							Assert.assertTrue(false, "Pinterest share option has failed and / or Pinterest share element was not located or failed to launch");
						driver.close();
					}
				}
				driver.switchTo().window(parentWindow);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] getTestData() throws IOException {
		// TODO Auto-generated method stub
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-TestSocialShare";
		return ExcelReader.ReadTestDataFromExcel(filePath, fileName, sheetName);
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		ssCtr = ssCtr + 1;
		if(!driver.equals(null))
			driver.close();
	}
}