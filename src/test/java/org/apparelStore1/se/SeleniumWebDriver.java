package org.apparelStore1.se;

import org.apparelStore1.properties.TestingProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SeleniumWebDriver
{
	private static WebDriver driver;
	
	public static WebDriver startDesignatedBrowser()
	{
		String browserType = TestingProperties.getBrowserType();
		if(browserType.equals("chrome"))
			driver = chromeDriver();
		else if(browserType.equals("firefox"))
			driver = firefoxDriver();
		else if(browserType.equals("safari"))
			driver = safariDriver();
		
		return driver;
	}
	
	private static WebDriver chromeDriver()
	{
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver");
		ChromeOptions cmo = new ChromeOptions();
		cmo.addArguments("start-maximized");
		driver = new ChromeDriver(cmo);
		return driver;
	}
	
	private static WebDriver firefoxDriver()
	{
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
		driver = new FirefoxDriver();
		return driver;
	}
	
	private static WebDriver safariDriver()
	{
		System.setProperty("webdriver.safari.driver","/usr/bin/safaridriver");
		driver = new SafariDriver();
		return driver;
	}
	
	public static WebDriver getChromeDriver()
	{
		return chromeDriver();
	}
	
	public static WebDriver getSafariDriver()
	{
		return safariDriver();
	}
	
	public static WebDriver getfirefoxDriver()
	{
		return firefoxDriver();
	}
	
	public static WebDriver getIEDriver()
	{
		return null;
	}
	
	public static WebDriver getOperaDriver()
	{
		return null;
	}
}
