package org.apparelStore1.test.nonfunctional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.apparelStore1.se.pages.Authentication;
import org.apparelStore1.se.pages.MyAccount;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParallelLoginTests extends TestCase
{
	DesiredCapabilities cap = null;
	private WebDriver driver = null;
	private int ssCtr;
	
	@Parameters({"browser"})
	@BeforeClass
	public void startRequiredBrowser(String browser)
	{
		ssCtr = 0;
		if(browser.equals("chrome"))
		{
			cap = DesiredCapabilities.chrome();
			cap.setPlatform(Platform.ANY);
		}
		else if(browser.equals("firefox"))
		{
			cap = DesiredCapabilities.firefox();
		}
		else if(browser.equals("safari")){
			cap = DesiredCapabilities.safari();
		}
		else
		{
			System.out.println("Invalid Browser Type");
		}
		try{
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
		}catch(MalformedURLException mal){}
		driver.manage().window().maximize();
	}

	//@Parameters({"browser","platform"})
	@Test(dataProvider="getTestData")
	public void runTestCase(Hashtable<String, String> testData)
	{	
		if(!driver.equals(null))
		{
			Boolean loginSuccess = true;
			driver.get(TestingProperties.getLoginURL());
			Authentication acc = new Authentication(driver);
			acc.loginEmail.clear();
			acc.loginEmail.sendKeys(testData.get("email"));
			acc.loginPasswd.clear();
			acc.loginPasswd.sendKeys(testData.get("password"));
			acc.loginSubmit.click();
			
			if(Pages.pageBody.getAttribute("id").equals("my-account"))
			{
				MyAccount myAcc = new MyAccount(driver);
				myAcc.signOutLink.click();
				Pages.waitForLoad(driver);
				if(Pages.pageBody.getAttribute("id").contains("authentication"))
				{
					Assert.assertTrue(true);
					Reporter.log("Logout was successful and the page redirected to the login page; however, no message was displayed"); 
				}
			}
			else if(Pages.pageBody.getAttribute("id").equals("authentication"))
			{
				loginSuccess = false;
				String loginMsg = acc.waitForLoginError(driver);
				if(loginMsg.equals("Invalid email address."))
				{
					Assert.assertTrue(!loginSuccess);
					Reporter.log("An invalid email address was submitted: "+ testData.get("email")+"; however, an error regarding valid email was successfully displayed");
				}
				else if(loginMsg.contains("Authentication failed"))
				{
					Assert.assertTrue(!loginSuccess);
					Reporter.log("Authentication failed with: "+ testData.get("email") + "; however, error message was successfully displayed");
				}
				else
				{
					Assert.assertTrue(loginSuccess, "No error messages were displayed to the user after login failure");
				}
			}
		}
	}
	
	@DataProvider(parallel=true)
	public Object[][] getTestData() throws IOException {
		// TODO Auto-generated method stub
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-ParallelLogin";
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
	
/*	@AfterClass
	public void closeBrowser()
	{
		if(!driver.equals(null))
			driver.quit();
	}*/
}
