package org.apparelStore1.se.pages;

import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class CompleteOrder extends Pages
{
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/p[2]/a[1]")
	public WebElement proceedToCheckOut;
	
	@FindBy(how = How.NAME, using = "processAddress")
	public WebElement checkOutAddress;

	@FindBy(how = How.ID_OR_NAME, using = "cgv")
	public WebElement agreeTermsCarrierCheckbox;
	
	@FindBy(how = How.NAME, using = "processCarrier")
	public WebElement checkOutCarrier;
	
	@FindBy(how = How.CLASS_NAME, using = "bankwire")
	public WebElement payByBankWire;
	
	@FindBy(how = How.CLASS_NAME, using = "cheque")
	public WebElement payByCheque;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"cart_navigation\"]/button")
	public WebElement checkOut;
	
	public CompleteOrder(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
}
