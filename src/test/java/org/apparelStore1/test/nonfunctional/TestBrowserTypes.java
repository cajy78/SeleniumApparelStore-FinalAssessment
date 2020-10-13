package org.apparelStore1.test.nonfunctional;

import java.io.IOException;
import java.util.Hashtable;

import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.SeleniumWebDriver;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestBrowserTypes extends TestCase
{
	private WebDriver driver;
	
	@Test(dataProvider = "getTestData")
	public void runTestCase(Hashtable<String, String> testData)
	{
		boolean testComplete = false;
		String browserName = testData.get("Browser");
		if(browserName.equals("Chrome"))
		{
			try
			{
				driver = SeleniumWebDriver.getChromeDriver();
				driver.get(TestingProperties.getSiteURL());
				testComplete = true;
			}
			catch(Exception e)
			{
				testComplete = false;
			}
		}
		else if(browserName.equals("Firefox"))
		{
			try
			{
				driver = SeleniumWebDriver.getfirefoxDriver();
				driver.get(TestingProperties.getSiteURL());
				testComplete = true;
			}
			catch(Exception e)
			{
				testComplete = false;
			}
		}
		else if(browserName.equals("Safari"))
		{
			try
			{
				driver = SeleniumWebDriver.getSafariDriver();
				driver.get(TestingProperties.getSiteURL());
				testComplete = true;
			}
			catch(Exception e)
			{
				testComplete = false;
			}
		}
		else if(browserName.equals("Internet Explorer"))
		{
			try
			{
				driver = SeleniumWebDriver.getIEDriver();
				driver.get(TestingProperties.getSiteURL());
				testComplete = true;
			}
			catch(Exception e)
			{
				testComplete = false;
			}
		}
		else if(browserName.equals("Opera"))
		{
			try
			{
				driver = SeleniumWebDriver.getOperaDriver();
				driver.get(TestingProperties.getSiteURL());
				testComplete = true;
			}
			catch(Exception e)
			{
				testComplete = false;
			}
		}
		
		Assert.assertTrue(testComplete, "Failed accessing website with "+browserName);
	}

	@DataProvider
	public Object[][] getTestData() throws IOException {
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-BrowserType";
		return ExcelReader.ReadTestDataFromExcel(filePath, fileName, sheetName);
	}

	@Override
	public void launchBrowser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeBrowser() {
		// TODO Auto-generated method stub
		
	}

}
