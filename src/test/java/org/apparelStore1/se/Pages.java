package org.apparelStore1.se;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apparelStore1.properties.TestingProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

public class Pages
{
	@FindBy(how = How.ID, using = "search_query_top")
	public WebElement mainSearch;
	
	@FindBy(how = How.NAME, using = "submit_search")
	public WebElement submitSearch;
	
	@FindBy(how = How.TAG_NAME, using = "body")
	public static WebElement pageBody;
	
	@FindBy(how = How.CLASS_NAME, using = "page-heading")
	public static WebElement pageHeader;
	
	@FindBy(how = How.CLASS_NAME, using = "sf-with-ul")
	public static List<WebElement> navBarList = new ArrayList<WebElement>();
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"block_top_menu\"]/ul/li[3]/a")
	public static WebElement navBarTShirt;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"header\"]/div[3]/div/div/div[3]/div/a")
	public WebElement viewCart;
	
	public static void waitForLoad(WebDriver driver) 
	{
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getPageLoadTimeout());
        wait.until(pageLoadCondition);
    }
	
	public static WebElement locateDynamicNavBarElement(WebDriver driver, String linkText)
	{
		return driver.findElement(By.linkText(linkText));
	}
	
	public static void waitForNavBarSubElement(WebDriver driver, String linkText)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getExplicitWaitTimeout());
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
	}
	
	public void scrollDown(WebDriver driver, WebElement scrollToElement)
	{
		JavascriptExecutor jse  = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", scrollToElement);
	}
	
	public void waitForPresenceOfDynamicElementByCss(WebDriver driver, String cssSelector)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getExplicitWaitTimeout());
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
	}
	
	public void waitForVisibilityOfDynamicElementByClass(WebDriver driver, String classname)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getExplicitWaitTimeout());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(classname)));
	}
	
	public void waitForVisibilityOfDynamicElementByXpath(WebDriver driver, String xpathValue)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getExplicitWaitTimeout());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValue)));
	}
	
	public static void captureSS(WebDriver driver, String fileName)
		throws IOException
	{
		try{
			String filePath = TestingProperties.getSSFolderPath() + "/" + fileName;
			BufferedImage image = Shutterbug.shootPage(driver,ScrollStrategy.WHOLE_PAGE).getImage();
	    	ImageIO.write(image, "png", new File(filePath+".png"));
		}catch(Exception e)
		{}
	}
}
