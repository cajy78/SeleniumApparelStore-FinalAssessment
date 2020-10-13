package org.apparelStore1.se.pages;

import org.apparelStore1.properties.TestingProperties;
import org.apparelStore1.se.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ProductInfo extends Pages
{
	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "Back to Search")
	public WebElement backToSearch;
	
	@FindBy(how = How.LINK_TEXT, using = "Write a review")
	public WebElement reviewLink;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div/div/div/div/form/div/div[2]/ul/li/div[1]/div[2]/a")
	public WebElement oneStar;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div/div/div/div/form/div/div[2]/ul/li/div[1]/div[3]/a")
	public WebElement twoStar;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div/div/div/div/form/div/div[2]/ul/li/div[1]/div[4]/a")
	public WebElement threeStar;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div/div/div/div/form/div/div[2]/ul/li/div[1]/div[5]/a")
	public WebElement fourStar;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div/div/div/div/form/div/div[2]/ul/li/div[1]/div[6]/a")
	public WebElement fiveStar;
	
	@FindBy(how = How.ID, using = "comment_title")
	public WebElement reviewTitle;
	
	@FindBy(how = How.ID, using = "content")
	public WebElement reviewContent;
	
	@FindBy(how = How.ID, using = "submitNewMessage")
	public WebElement reviewSubmit;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"product\"]/div[2]/div/div/div/h2")
	public WebElement commentAlertHeader;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"product\"]/div[2]/div/div/div/p[1]")
	public WebElement commentAlertContent;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/div/div/div[3]/p[7]/button[1]")
	public WebElement twitterShare;
	
	@FindBy(how = How.NAME, using = "session[username_or_email]")
	public WebElement twitterUName;
	
	@FindBy(how = How.NAME, using = "session[password]")
	public WebElement twitterPwd;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/div/div/div[3]/p[7]/button[2]")
	public WebElement facebookShare;
	
	@FindBy(how = How.ID_OR_NAME, using = "email")
	public WebElement fbEmail;
	
	@FindBy(how = How.ID_OR_NAME, using = "pass")
	public WebElement fbPass;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/div/div/div[3]/p[7]/button[3]")
	public WebElement googleShare;
	
	@FindBy(how = How.ID, using = "identifierId")
	public WebElement gID;
	
	@FindBy(how = How.NAME, using = "password")
	public WebElement gPwd;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"center_column\"]/div/div/div[3]/p[7]/button[4]")
	public WebElement pinterestShare;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"__PWS_ROOT__\"]/div[1]/div/div/div/div[1]/div[1]/div[2]/div[2]/button")
	public WebElement pinLoginClick;
	
	@FindBy(how = How.ID, using = "email")
	public WebElement pinEmail;
	
	@FindBy(how = How.ID, using = "password")
	public WebElement pinPwd;
	
	@FindBy(how = How.NAME, using = "Submit")
	public WebElement pageInfoAddToCart;
	
	public ProductInfo(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
	
	public boolean manufacturerNameExists(WebDriver driver, String data)
	{
		boolean exists = false;
		if(driver.getPageSource().contains(data))
		{
			exists = true;
		}
		return exists;
	}
}
