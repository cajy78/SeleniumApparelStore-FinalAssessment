package org.apparelStore1.test.functional;

import java.io.IOException;
import java.util.Hashtable;

import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.apparelStore1.se.SeleniumWebDriver;
import org.apparelStore1.se.pages.Authentication;
import org.apparelStore1.se.pages.CreateAccount;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestAccountCreation extends TestCase
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
		Boolean accountExists = false, validEmail = true, createSuccess = false, countryErrorDisplayed = false, pincodeErrorDisplayed = false;
		try{
			driver.get(TestingProperties.getLoginURL());
		
			Authentication acc = new Authentication(driver);
			acc.newAccountEmail.clear();
			acc.newAccountEmail.sendKeys(testData.get("email"));
			acc.createAccount.click();
			Pages.waitForLoad(driver);
			try{
				Pages.captureSS(driver, "TestAccountCreation/testCreate_detailsEntered"+ssCtr);
			}catch(IOException ioe){}
			
			String error = "";
			try{
				error = acc.creationError(driver);
			}
			catch(TimeoutException te)
			{
			}
			
			if(error.contains("Invalid email address."))
			{
				try{
					Pages.captureSS(driver, "TestAccountCreation/testCreate_InvalidEmail"+ssCtr);
				}catch(IOException ioe){}
				validEmail = false;
			}
			else if(error.contains("An account using this email address has already been registered. Please enter a valid password or request a new one."))
			{
				try{
					Pages.captureSS(driver, "TestAccountCreation/testCreate_alreadyRegistered"+ssCtr);
				}catch(IOException ioe){}
				accountExists = true;
			}
			else if(!accountExists && validEmail)
			{
				CreateAccount cr = new CreateAccount(driver);
				String dataGender = testData.get("gender");
				if(dataGender.equalsIgnoreCase("Male"))
					cr.gender.get(0).click();
				else
					cr.gender.get(1).click();
				cr.custFirstName.clear();
				cr.custFirstName.sendKeys(testData.get("firstname"));
				cr.custLastName.clear();
				cr.custLastName.sendKeys(testData.get("lastname"));
				cr.passwd.clear();
				cr.passwd.sendKeys(testData.get("password"));
				cr.days.sendKeys(testData.get("dobDay"));
				cr.months.sendKeys(testData.get("dobMonth"));
				cr.years.sendKeys(testData.get("dobYear"));
				cr.company.clear();
				cr.company.sendKeys(testData.get("company"));
				cr.address1.clear();
				cr.address1.sendKeys(testData.get("address1"));
				cr.address2.clear();
				cr.address2.sendKeys(testData.get("address2"));
				cr.city.clear();
				cr.city.sendKeys(testData.get("city"));
				cr.state.sendKeys(testData.get("state"));
				cr.postcode.clear();
				cr.postcode.sendKeys(testData.get("postcode"));
				cr.country.sendKeys(testData.get("country"));
				cr.other.clear();
				cr.other.sendKeys(testData.get("otherdata"));
				cr.phone.clear();
				cr.phone.sendKeys(testData.get("phone"));
				cr.phoneMobile.clear();
				cr.phoneMobile.sendKeys(testData.get("mobile"));
				cr.alias.clear();
				cr.alias.sendKeys(testData.get("alias"));
				try{
					Pages.captureSS(driver, "TestAccountCreation/testCreate_createDetailsEntered"+ssCtr);
				}catch(IOException ioe){}
				cr.submit.click();
				Pages.waitForLoad(driver);
				if(Pages.pageBody.getAttribute("id").equals("my-account"))
				{
					try{
						Pages.captureSS(driver, "TestAccountCreation/testCreate_createAccountCompleted"+ssCtr);
					}catch(IOException ioe){}
					createSuccess = true;
				}
				else if(cr.createAccountHeader.getText().contains("Create your account") && driver.getPageSource().contains("Country is invalid"))
				{
					try{
						Pages.captureSS(driver, "TestAccountCreation/testCreate_createDetailsCountryError"+ssCtr);
					}catch(IOException ioe){}
					countryErrorDisplayed = true;
				}
				else if(cr.createAccountHeader.getText().contains("Create your account") && driver.getPageSource().contains("The Zip/Postal code you've entered is invalid"))
				{
					System.out.println("Zip error if entered");
					try{
						Pages.captureSS(driver, "TestAccountCreation/testCreate_createDetailsZipError"+ssCtr);
					}catch(IOException ioe){}
					pincodeErrorDisplayed = true;
				}
			}
			//Assertions
			if(accountExists)
			{
				Assert.assertTrue(accountExists);
				Reporter.log("An account already exists and the site displayed an error message");
			}
			else if(!validEmail)
			{
				Assert.assertTrue(validEmail,"Email address "+ testData.get("email")+" entered is invalid");
			}
			else if(createSuccess)
			{
				Assert.assertTrue(createSuccess);
				Reporter.log("Account was successfully created");
			}
			else if(createSuccess && !countryErrorDisplayed)
			{
				Assert.assertTrue(countryErrorDisplayed, "An error was not displayed when the required field was left blank");
			}
			else if(createSuccess && !pincodeErrorDisplayed)
			{
				Assert.assertTrue(countryErrorDisplayed, "An error was not displayed when the required field was left blank");
			}
			else if (countryErrorDisplayed)
			{
				Assert.assertTrue(countryErrorDisplayed);
				Reporter.log("An error message was successfully displayed when the country details was left blank");
			}
			else if(pincodeErrorDisplayed)
			{
				Assert.assertTrue(pincodeErrorDisplayed);
				Reporter.log("An error message was successfully displayed when the Zip / Pincode details was invalid");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			SoftAssert sa = new SoftAssert();
			sa.assertTrue(false, "Exception failure");
			sa.assertAll();
		}
	}
	
	@DataProvider
	public Object[][] getTestData() throws IOException
	{
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-CreateAccount";
		return ExcelReader.ReadTestDataFromExcel(filePath, fileName, sheetName);
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		ssCtr = ssCtr+1;
		if(!driver.equals(null))
			driver.close();
	}
}
