package org.apparelStore1.se.pages;

import java.util.ArrayList;
import java.util.List;

import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Search extends Pages
{
	
	@FindBy(how = How.CLASS_NAME, using = "heading-counter")
	public WebElement searchCounter;
	
	@FindBy(how = How.CLASS_NAME, using = "product_img_link") //""//*[@id=\"center_column\"]/ul/li")//""product_img_link")//"product-name")//"product_img_link") //"//*[@id="center_column"]/ul/li")
	public List<WebElement> items = new ArrayList<WebElement>();
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/ul/li")
	public List<WebElement> itemsListElement = new ArrayList<WebElement>();
	
	@FindBy(how = How.CLASS_NAME, using = "fancybox-iframe") //"/html/body/div[2]/div/div/div/div/iframe")
	public WebElement prodFrame;
	
	@FindBy(how = How.CLASS_NAME, using = "fancybox-item fancybox-close") //using = "//*[@class=\"fancybox-frame\"]")//CLASS_NAME, using = "fancybox-item fancybox-close")
	public WebElement prodFrameClose;
	
	@FindBy(how = How.CSS, using="#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.first-item-of-tablet-line.last-item-of-mobile-line > div > div.right-block > div.button-container > a.button.ajax_add_to_cart_button.btn.btn-default")
	public WebElement addToCart;
	
	@FindBy(how = How.CSS, using = "#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > span")
	public WebElement continueShopping;
	
	@FindBy(how = How.CLASS_NAME, using = "cross")
	public WebElement closeInnerWindow;
	
	public Search(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
	
	public void waitForDynamiciFrame(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
	}
	
	public WebElement findDynamicSearchItem(WebDriver driver, String value)
	{
		WebElement item = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[@class='"+value+"']/div/div[2]/div[2]/a[1]"));
		return item;
	}
}
