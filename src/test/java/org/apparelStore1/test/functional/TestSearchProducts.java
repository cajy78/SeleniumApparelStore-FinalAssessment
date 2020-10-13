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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSearchProducts extends TestCase
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
		driver.get(TestingProperties.getSiteURL());
		Home homePage = new Home(driver);
		homePage.mainSearch.clear();
		homePage.mainSearch.sendKeys(testData.get("SearchParameter"));
		homePage.submitSearch.click();
		
		boolean expectedNumberFound = false;
		Search searchPage = new Search(driver);
		try{
			Pages.captureSS(driver, "TestSearchProd/testgenericSearch_item"+ssCtr);
			ssCtr++;
		}catch(IOException ioe){}
		try{
		if(String.valueOf(searchPage.items.size()).contains(testData.get("expectedValue")))
			expectedNumberFound = true;
		}
		catch(Exception e)
		{
			expectedNumberFound = false;
		}
		Assert.assertTrue(expectedNumberFound, "Expected number of items for "+testData.get("SearchParameter")+" was not found in the search result.");
		Reporter.log("");
	}
	
	@Test(dataProvider = "getTestData")
	public void searchManufacturer(Hashtable<String, String> testData)
	{
		boolean manufacturer = false, manufacturerNameFoundInInfo = false;
		try
		{
			driver.get(TestingProperties.getSiteURL());
			Pages.waitForLoad(driver);
			Home homePage = new Home(driver);
			homePage.mainSearch.clear();
			homePage.mainSearch.sendKeys(testData.get("Manufacturer"));
			homePage.submitSearch.click();
				
			Search searchPage = new Search(driver);
			Pages.waitForLoad(driver);
			try{
				Pages.captureSS(driver, "TestSearchProd/testSearch_itemsSearched"+ssCtr);
			}catch(IOException ioe){}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			
			if(searchPage.items.size() > 0 && searchPage.itemsListElement.size() > 0)
			{
				String parentWindow = driver.getWindowHandle();
				for(WebElement item : searchPage.itemsListElement)
				{
					if(item.getText().contains(testData.get("Manufacturer")))
					{
						manufacturer = true;
					}
				}
				for(WebElement item : searchPage.items)
				{
					String url = item.getAttribute("href");
					executor.executeScript("window.open(\""+url+"\")");
				}
					
				Set<String> childWindows = driver.getWindowHandles();
				Iterator<String> i = childWindows.iterator();
				while(i.hasNext())
				{
					String tab = i.next();
					if(!tab.equals(parentWindow))
					{
						driver.switchTo().window(tab);
						Pages.waitForLoad(driver);
						ProductInfo info = new ProductInfo(driver);
						try{
							Pages.captureSS(driver, "TestSearchProd/testSearch_item"+ssCtr);
							ssCtr++;
						}catch(IOException ioe){}
						manufacturerNameFoundInInfo = info.manufacturerNameExists(driver, testData.get("Manufacturer"));
						driver.close();
					}
				}
				if(manufacturer || manufacturerNameFoundInInfo)
				{
					Assert.assertTrue(manufacturer, "Products were found, but none of them displayed the manufacturer name");
				}
			}
			else
			{
				Assert.assertTrue(manufacturer, "Products by manufacturer, "+testData.get("Manufacturer")+" were not found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] getTestData() throws IOException
	{
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-SearchProd";
		return ExcelReader.ReadTestDataFromExcel(filePath, fileName, sheetName);
	}

	@AfterMethod
	public void closeBrowser()
	{
		ssCtr = ssCtr + 1;
		if(!driver.equals(null))
			driver.quit();
	}
	
	@AfterTest
	public void completeTest()
	{
		System.out.println("After test method called");
		//TestingProperties.resetElementCtr();
	}
}
