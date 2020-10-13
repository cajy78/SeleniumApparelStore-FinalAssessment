package org.apparelStore1.se.pages;

import java.util.ArrayList;
import java.util.List;

import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Home extends Pages
{	
	@FindBy(how = How.LINK_TEXT, using = "Sign in")
	public WebElement signInLink;
	
	@FindBy(how = How.CLASS_NAME, using = "product_img_link")//"/html/body/div/div[2]/div/div[2]/div/div[1]/ul[1]/li[1]/div/div[2]/h5/a")//"product-name")
	public List<WebElement> homePageProdLink = new ArrayList<WebElement>();
	
	public Home(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
}
