package org.apparelStore1.se.pages;

import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class MyAccount extends Pages
{

	@FindBy(how = How.XPATH, using="//*[@id=\"center_column\"]/h1")
	public WebElement headerTest;
	
	@FindBy(how = How.LINK_TEXT, using = "Sign out")
	public WebElement signOutLink;
	
	public MyAccount(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
}