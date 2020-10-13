package org.apparelStore1.se.pages;

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

public class Authentication extends Pages{

	@FindBy(how = How.NAME, using = "email_create")
	public WebElement newAccountEmail;
	
	@FindBy(how = How.ID, using = "SubmitCreate")
	public WebElement createAccount;
	
	@FindBy(how = How.NAME, using = "email")
	public WebElement loginEmail;
	
	@FindBy(how = How.NAME, using = "passwd")
	public WebElement loginPasswd;
	
	@FindBy(how = How.NAME, using = "SubmitLogin")
	public WebElement loginSubmit;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"create_account_error\"]/ol/li")
	private WebElement createErrorElement;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/div[1]/ol/li")
	private WebElement loginErrorElement;
	
	public Authentication(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
	
	public String creationError(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getExplicitWaitTimeout());
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"create_account_error\"]/ol/li")));
		//WebElement createErrorElement = driver.findElement(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
		return createErrorElement.getText();
	}
	
	public String waitForLoginError(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestingProperties.getExplicitWaitTimeout());
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li")));
		//WebElement createErrorElement = driver.findElement(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
		return loginErrorElement.getText();
	}
}
