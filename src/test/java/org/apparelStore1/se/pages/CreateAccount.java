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

/*
 * Element names in the form in chronological order of display
 * Required fields named as REQ at the end
 * id_gender (radio button)
 * customer_firstname REQ
 * customer_lastname REQ
 * email (should already be populated) REQ
 * passwd REQ
 * dob Selects
 * days, months, years
 * newsletter (checkbox)
 * optin (checkbox)
 * ADDRESS AREA:
 * firstname REQ
 * lastname REQ
 * company
 * address1 REQ
 * address2
 * city REQ
 * id_state (Select) REQ
 * postcode REQ
 * id_country (select) REQ
 * other (textarea)
 * phone
 * phone_mobile REQ
 * alias (address alias to identify the type of address) REQ
 * submitAccount (Button)
 * */

public class CreateAccount extends Pages
{
	@FindBy(how = How.XPATH, using = "//*[@id=\"columns\"]/div[1]")
	public WebElement createAccountHeader;
	
	@FindBy(how = How.NAME, using = "id_gender")
	public List<WebElement> gender = new ArrayList<WebElement>();
	
	@FindBy(how = How.ID, using = "id_gender1")
	public WebElement male;
	
	@FindBy(how = How.ID, using = "id_gender2")
	public WebElement female;
	
	@FindBy(how = How.NAME, using = "customer_firstname")
	public WebElement custFirstName;
	
	@FindBy(how = How.NAME, using = "customer_lastname")
	public WebElement custLastName;
	
	@FindBy(how = How.NAME, using = "email")
	public WebElement email;
	
	@FindBy(how = How.NAME, using = "passwd")
	public WebElement passwd;
	
	@FindBy(how = How.NAME, using = "days")
	public WebElement days;
	
	@FindBy(how = How.NAME, using = "months")
	public WebElement months;
	
	@FindBy(how = How.NAME, using = "years")
	public WebElement years;
	
	@FindBy(how = How.NAME, using = "newsletter")
	public WebElement newsLetterCheckbox;
	
	@FindBy(how = How.NAME, using = "optin")
	public WebElement specialOptionCheckbox;
	
	@FindBy(how = How.NAME, using = "firstname")
	public WebElement addFirstname;
	
	@FindBy(how = How.NAME, using = "lastname")
	public WebElement addLastname;
	
	@FindBy(how = How.NAME, using = "company")
	public WebElement company;
	
	@FindBy(how = How.NAME, using = "address1")
	public WebElement address1;
	
	@FindBy(how = How.NAME, using = "address2")
	public WebElement address2;
	
	@FindBy(how = How.NAME, using = "city")
	public WebElement city;
	
	@FindBy(how = How.NAME, using = "id_state")
	public WebElement state;
	
	@FindBy(how = How.NAME, using = "postcode")
	public WebElement postcode;
	
	@FindBy(how = How.NAME, using = "id_country")
	public WebElement country;
	
	@FindBy(how = How.NAME, using = "other")
	public WebElement other;
	
	@FindBy(how = How.NAME, using = "phone")
	public WebElement phone;
	
	@FindBy(how = How.NAME, using = "phone_mobile")
	public WebElement phoneMobile;
	
	@FindBy(how = How.NAME, using = "alias")
	public WebElement alias;
	
	@FindBy(how = How.NAME, using = "submitAccount")
	public WebElement submit;
	
	public CreateAccount(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TestingProperties.getLazyLoadTimeout()), this);
	}
}
