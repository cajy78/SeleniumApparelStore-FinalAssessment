package org.apparelStore1.test.functional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.apparelStore1.se.SeleniumWebDriver;
import org.apparelStore1.se.pages.Authentication;
import org.apparelStore1.se.pages.MyAccount;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestAccountLogins extends TestCase
{
	public ExtentHtmlReporter htmlReporter = null;
	public ExtentReports extent = null;
	public ExtentTest logger = null;
	private WebDriver driver;
	private int ssCtr;
	
	 @BeforeTest
	 public void startReport(){
	     htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/LoginLogoutTest_ExtentReport.html");
	     extent = new ExtentReports();
	     extent.attachReporter(htmlReporter);
	     extent.setSystemInfo("Host Name", "SoftwareTesting");
	     extent.setSystemInfo("Environment", "Automation Testing");
	     extent.setSystemInfo("User Name", "TestEngineer");
		 htmlReporter.config().setDocumentTitle("Apparel Store Login Tests");
		 htmlReporter.config().setReportName("Selenium Regression Test Suite");
		 htmlReporter.config().setTheme(Theme.STANDARD);		
	 }
	 
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
		logger = extent.createTest("User Account Login Test");
		Boolean loginSuccess = true;
		driver.get(TestingProperties.getLoginURL());
	
		Authentication acc = new Authentication(driver);
		acc.loginEmail.clear();
		acc.loginEmail.sendKeys(testData.get("email"));
		acc.loginPasswd.clear();
		acc.loginPasswd.sendKeys(testData.get("password"));
		try{
			Pages.captureSS(driver, "TestLogin/testLogin_detailsEntered"+ssCtr);
		}catch(IOException ioe){}
		acc.loginSubmit.click();
		Pages.waitForLoad(driver);
		
		if(Pages.pageBody.getAttribute("id").equals("my-account"))
		{
			loginSuccess = true;
			MyAccount myAcc = new MyAccount(driver);
			try{
				Pages.captureSS(driver, "TestLogin/testLogin_LoginSuccess"+ssCtr);
			}catch(IOException ioe){}
			if(myAcc.signOutLink.isDisplayed())
			{
				logger.createNode("Sign Out link is displayed");
				Assert.assertTrue(loginSuccess);
				Reporter.log("Login was successfully done and My Account page along with signout link was displayed to the user");
			}
			myAcc.signOutLink.click();
		}
		else if(Pages.pageBody.getAttribute("id").equals("authentication"))
		{
			loginSuccess = false;
			String loginMsg = acc.waitForLoginError(driver);
			try{
				Pages.captureSS(driver, "TestLogin/testLogin_LoginFail"+ssCtr);
			}catch(IOException ioe){}
			
			if(loginMsg.equals("Invalid email address."))
			{
				logger.createNode("Invalid email address error message is displayed");
				Assert.assertTrue(!loginSuccess);
				Reporter.log("An invalid email address was submitted: "+ testData.get("email")+"; however, an error regarding valid email was successfully displayed");
			}
			else if(loginMsg.contains("Authentication failed"))
			{
				logger.createNode("Authentication failed error message is displayed");
				Assert.assertTrue(!loginSuccess);
				Reporter.log("Authentication failed with: "+ testData.get("email") + "; however, error message was successfully displayed");
			}
			else
			{
				logger.createNode("No error messages were displayed after user login failure");
				Assert.assertTrue(loginSuccess, "No error messages were displayed to the user after login failure");
			}
		}
	}

	@Test(dataProvider = "getTestData")
	public void logOutTest(Hashtable<String, String> testData)
	{
		logger = extent.createTest("User Account Logout Test");
		Boolean loginSuccess = true;
		driver.get(TestingProperties.getLoginURL());
		Authentication acc = new Authentication(driver);
		acc.loginEmail.clear();
		acc.loginEmail.sendKeys(testData.get("email"));
		acc.loginPasswd.clear();
		acc.loginPasswd.sendKeys(testData.get("password"));
		try{
			Pages.captureSS(driver, "TestLogin/testLogout_detailsEntered"+ssCtr);
		}catch(IOException ioe){}
		acc.loginSubmit.click();
		Pages.waitForLoad(driver);
		
		if(Pages.pageBody.getAttribute("id").equals("my-account"))
		{
			MyAccount myAcc = new MyAccount(driver);
			myAcc.signOutLink.click();
			Pages.waitForLoad(driver);
			try{
				Pages.captureSS(driver, "TestLogin/testLogout_logoutSuccess"+ssCtr);
			}catch(IOException ioe){}
			if(Pages.pageBody.getAttribute("id").contains("authentication"))
			{
				logger.createNode("Logout was successful and the page redirected to the login page; however, no message was displayed");
				Assert.assertTrue(true);
				Reporter.log("Logout was successful and the page redirected to the login page; however, no message was displayed"); 
			}
		}
		else if(Pages.pageBody.getAttribute("id").equals("authentication"))
		{
			loginSuccess = false;
			String loginMsg = acc.waitForLoginError(driver);
			try{
				Pages.captureSS(driver, "TestLogin/testLogout_loginFail"+ssCtr);
			}catch(IOException ioe){}
			if(loginMsg.equals("Invalid email address."))
			{
				logger.createNode("Invalid email address error message is displayed");
				Assert.assertTrue(!loginSuccess);
				Reporter.log("An invalid email address was submitted: "+ testData.get("email")+"; however, an error regarding valid email was successfully displayed");
			}
			else if(loginMsg.contains("Authentication failed"))
			{
				logger.createNode("Authentication failed error message is displayed");
				Assert.assertTrue(!loginSuccess);
				Reporter.log("Authentication failed with: "+ testData.get("email") + "; however, error message was successfully displayed");
			}
			else
			{
				logger.createNode("No error messages were displayed after user login failure");
				Assert.assertTrue(loginSuccess, "No error messages were displayed to the user after login failure");
			}
		}
	}
	
	public String getScreenshot(WebDriver driver, String ScreenshotName) throws IOException{
		//for that we need to reference another jar files 
		//one for the extent report and another is for snapshot
		//please go to google driver and unzip common-io jars and reference it in the project
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source= ts.getScreenshotAs(OutputType.FILE);
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String destination = System.getProperty("user.dir")+"/Screenshots/" + ScreenshotName + dateName + ".png"; 
		//added common-io jars
		FileUtils.copyFile(source, new File(destination));
		return destination;
	}
	
	@DataProvider
	public Object[][] getTestData() throws IOException
	{
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-Login";
		return ExcelReader.ReadTestDataFromExcel(filePath, fileName, sheetName);
	}
	
	@AfterMethod
	public void closeBrowser(ITestResult result) throws IOException
	{
		ssCtr = ssCtr+1;
		if(result.getStatus() == ITestResult.FAILURE){
			//log the results
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed ", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed ", ExtentColor.RED));
			logger.fail("Test Case Failed Snapshot is below - " + logger.addScreenCaptureFromPath(getScreenshot(driver,result.getName())));
		}
		else if(result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped ", ExtentColor.ORANGE));
		}
		else if(result.getStatus() == ITestResult.SUCCESS){
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed ", ExtentColor.GREEN));
		}
		if(!driver.equals(null))
			driver.quit();
	}
	
	@Override
	public void closeBrowser() {
		// TODO Auto-generated method stub
	}
	
	@AfterTest
	
	public void stopTest(){
		extent.flush(); //at the end of all tests - it will append the test results to the htmlreport when flushed
	}
}
