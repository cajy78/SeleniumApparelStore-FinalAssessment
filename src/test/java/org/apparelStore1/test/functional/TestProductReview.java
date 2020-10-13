package org.apparelStore1.test.functional;

import java.io.IOException;
import java.util.Hashtable;

import org.appareStore1.test.data.ExcelReader;
import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.apparelStore1.se.SeleniumWebDriver;
import org.apparelStore1.se.pages.Authentication;
import org.apparelStore1.se.pages.ProductInfo;
import org.apparelStore1.se.pages.Search;
import org.apparelStore1.test.TestCase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestProductReview extends TestCase
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
		driver.get(TestingProperties.getLoginURL());
		// TODO Auto-generated method stub
		String email = testData.get("email");
		String password = testData.get("password");
		String rating = testData.get("Rating");
		String reviewTitle = testData.get("Review Title");
		String reviewComment = testData.get("Review Comment");
		
		Authentication acc = new Authentication(driver);
		acc.loginEmail.clear();
		acc.loginEmail.sendKeys(email);
		acc.loginPasswd.clear();
		acc.loginPasswd.sendKeys(password);
		try{
			Pages.captureSS(driver, "TestReview/testReview_loginDetailsEntered"+ssCtr);
		}catch(IOException ioe){}
		acc.loginSubmit.click();
		
		Pages.waitForLoad(driver);
		
		Actions ma = new Actions(driver);
		ma.moveToElement(Pages.navBarList.get(0)).build().perform();
		Pages.waitForNavBarSubElement(driver, "Blouses");
		try{
			Pages.captureSS(driver, "TestReview/testReview_mouseAction"+ssCtr);
		}catch(IOException ioe){}
		Pages.locateDynamicNavBarElement(driver, "Blouses").click();
		Search result = new Search(driver);
		//System.out.println(result.items.size());
		try{
			Pages.captureSS(driver, "TestReview/testReview_searchRes"+ssCtr);
		}catch(IOException ioe){}
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		//List<String> itemNames = new ArrayList<String>();
		for(WebElement item : result.items){
			//itemNames.add(item.getText());
			//ma.click(item).build().perform();
			//break;
			executor.executeScript("arguments[0].click();", item);
			break;
		}
		/*for(String name : itemNames)
		{
			System.out.println(itemNames.size());
			System.out.println(name);
		}*/
		ProductInfo info = new ProductInfo(driver);
		info.reviewLink.click();
		try{
			Pages.captureSS(driver, "TestReview/testReview_ProductPage"+ssCtr);
		}catch(IOException ioe){}
		Pages.waitForLoad(driver);
		if(rating.equals("1 star"))
		{
			ma.click(info.oneStar).build().perform();
		}else if(rating.equals("2 stars"))
		{
			ma.click(info.twoStar).build().perform();
		}
		else if(rating.equals("3 stars"))
		{
			ma.click(info.threeStar).build().perform();
		}
		else if(rating.equals("4 stars"))
		{
			ma.click(info.fourStar).build().perform();
		}
		else if(rating.equals("5 stars"))
		{
			ma.click(info.fiveStar).build().perform();
		}
		info.reviewTitle.clear();
		info.reviewTitle.sendKeys(reviewTitle);
		info.reviewContent.clear();
		info.reviewContent.sendKeys(reviewComment);
		try{
			Pages.captureSS(driver, "TestReview/testReview_reviewDetailsEntered"+ssCtr);
		}catch(IOException ioe){}
		info.reviewSubmit.click();
		Pages.waitForLoad(driver);
		//System.out.println(info.commentAlertHeader.getText() + " " + info.commentAlertContent.getText());
		boolean testSuccess = false;
		String ratingConfHeader = info.commentAlertHeader.getText();
		String ratingConfContent = info.commentAlertContent.getText();
		try{
			Pages.captureSS(driver, "TestReview/testReview_reviewDetailsSubmitted"+ssCtr);
		}catch(IOException ioe){}
		if(reviewComment.length() < 50)
		{
			if(ratingConfHeader.contains("Error")||ratingConfContent.contains("50 characters")
					||ratingConfHeader.contains("error")||ratingConfContent.contains("Less than 50 characters")
					||ratingConfHeader.contains("please check")||ratingConfContent.contains("less than 50 characters"))
			{
				testSuccess = true;
			}
		}
		else
		{
			if(ratingConfContent.contains("Your comment has been added"))
			{
				testSuccess = true;
			}
		}
		Assert.assertTrue(testSuccess, "No error message to indicate character count less than 50 characters");
		Reporter.log("Comment was successfully added and the number of characters was over 50");
	}

	@DataProvider
	public Object[][] getTestData() throws IOException {
		// TODO Auto-generated method stub
		String filePath = TestingProperties.getTDFilePath();
		String fileName = TestingProperties.getTDFile();
		String sheetName="td-ReviewProd";
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
