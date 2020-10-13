package org.apparelStore1.test.functional;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.apparelStore1.se.SeleniumWebDriver;
import org.apparelStore1.se.pages.Authentication;
import org.apparelStore1.se.pages.CompleteOrder;
import org.apparelStore1.se.pages.ProductInfo;
import org.apparelStore1.se.pages.Search;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestPurchaseNewProducts extends TestCase
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
		try
		{
			// TODO Auto-generated method stub
			driver.get(TestingProperties.getLoginURL());
			Authentication acc = new Authentication(driver);
			acc.loginEmail.clear();
			acc.loginEmail.sendKeys(testData.get("email"));
			acc.loginPasswd.clear();
			acc.loginPasswd.sendKeys(testData.get("password"));
			try{
				Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_loginDetailsEntered"+ssCtr);
			}catch(IOException ioe){}
			acc.loginSubmit.click();
			Pages.waitForLoad(driver);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			
			if(Pages.pageBody.getAttribute("id").equals("my-account"))
			{
				acc.mainSearch.clear();
				acc.mainSearch.sendKeys(testData.get("search"));
				acc.submitSearch.click();
				
				Search searchPage = new Search(driver);
				Pages.waitForLoad(driver);
				try{
					Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_itemsSearched"+ssCtr);
				}catch(IOException ioe){}
				if(searchPage.items.size() > 0 && searchPage.itemsListElement.size() > 0)
				{
					if(searchPage.items.size() >= 3 && searchPage.itemsListElement.size() >= 3)
					{
						String parentWindow = driver.getWindowHandle();
						for(WebElement item : searchPage.items)
						{
							String url = item.getAttribute("href");
							executor.executeScript("window.open(\""+url+"\")");
						}
							
						Set<String> childWindows = driver.getWindowHandles();
						Iterator<String> i = childWindows.iterator();
						int prodCtr = 0;
						while(i.hasNext())
						{
							String tab = i.next();
							if(!tab.equals(parentWindow))
							{
								driver.switchTo().window(tab);
								Pages.waitForLoad(driver);
								ProductInfo info = new ProductInfo(driver);
								if(prodCtr <= 3)
								{
									info.pageInfoAddToCart.click();
									try{
										Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_itemtoCart"+prodCtr+"_"+ssCtr);
									}catch(IOException ioe){}
									driver.close();
								}
								else
								{
									driver.close();
								}
							}
							prodCtr+=1;
						}
						driver.switchTo().window(parentWindow);
						driver.get(TestingProperties.getSiteURL());
						Pages.waitForLoad(driver);
						searchPage.viewCart.click();
						CompleteOrder order = new CompleteOrder(driver);
						Pages.waitForLoad(driver);
						try{
							Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_showCart"+ssCtr);
						}catch(IOException ioe){}
						order.proceedToCheckOut.click();
						Pages.waitForLoad(driver);
						try{
							Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_checkout1_"+ssCtr);
						}catch(IOException ioe){}
						order.checkOutAddress.click();
						Pages.waitForLoad(driver);
						try{
							Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_checkout2_"+ssCtr);
						}catch(IOException ioe){}
						order.agreeTermsCarrierCheckbox.click();
						order.checkOutCarrier.click();
						Pages.waitForLoad(driver);
						try{
							Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_checkout3_"+ssCtr);
						}catch(IOException ioe){}
						if(testData.get("payby").equalsIgnoreCase("cheque"))
							order.payByCheque.click();
						else
							order.payByBankWire.click();
						order.waitForVisibilityOfDynamicElementByXpath(driver, "//*[@id=\"cart_navigation\"]/button");
						try{
							Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_checkout4_"+ssCtr);
						}catch(IOException ioe){}
						order.checkOut.click();
						Pages.waitForLoad(driver);
						if(driver.getTitle().contains("Order confirmation"))
						{
							try{
								Pages.captureSS(driver, "TestPurchaseNewProd/testBuy_complete"+ssCtr);
							}catch(IOException ioe){}
							Assert.assertTrue(true);
							Reporter.log("Purchasing 3 products was successfully completed");
						}
						else
							Assert.assertTrue(false,"purchase failed");
					}
					else
					{
						Assert.assertTrue(false,"Less than 3 products were found");
					}
				}
			}
			else
			{
				Assert.assertTrue(false, "Login failed");
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
		String sheetName="td-BuyItems";
		return ExcelReader.ReadTestDataFromExcel(filePath, fileName, sheetName);
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		ssCtr = ssCtr+1;
		if(!driver.equals(null))
			driver.quit();
	}
}
