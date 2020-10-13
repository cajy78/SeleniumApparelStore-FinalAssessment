package org.apparelStore1.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestingProperties
{
	private static Properties prop;
	private static String siteURL, tdFilePath, tdFile, designatedBrowserType, loginPage, ssFolderPath;
	private static List<String> browserList = new ArrayList<String>();
	
	private static void getPropertiesFile()
	{
		prop = new Properties();
		try
		{
			String projDir = System.getProperty("user.dir");
			FileInputStream fin = new FileInputStream(projDir+"/src/test/java/org/apparelStore1/properties/test.properties");
			prop.load(fin);
			fin.close();
		}
		catch(Exception e)
		{
			System.err.println("An exception has occurred while reading the properties file");
			e.printStackTrace();
		}
	}
	
	public static String getSiteURL()
	{
		getPropertiesFile();
		siteURL = prop.getProperty("website.url");
		return siteURL;
	}
	
	public static String getTDFilePath()
	{
		getPropertiesFile();
		tdFilePath = prop.getProperty("testData.filePath");
		return tdFilePath;
	}
	
	public static String getTDFile()
	{
		getPropertiesFile();
		tdFile = prop.getProperty("testData.fileName");
		return tdFile;
	}
	
	public static String getBrowserType()
	{
		getPropertiesFile();
		designatedBrowserType = prop.getProperty("designatedBrowser.type");
		return designatedBrowserType;
	}
	
	public static String getLoginURL()
	{
		getPropertiesFile();
		loginPage = prop.getProperty("accountLogin.url");
		return loginPage;
	}
	
	public static List<String> getBrowserList()
	{
		getPropertiesFile();
		int ctr = Integer.parseInt(prop.getProperty("browser.count"));
		for(int i = 0; i < ctr; i++)
		{
			browserList.add(prop.getProperty("browser.name"+i));
		}
		return browserList;
	}
	
	public static int getLazyLoadTimeout()
	{
		getPropertiesFile();
		return Integer.parseInt(prop.getProperty("lazyload.timeout"));
	}
	
	public static int getExplicitWaitTimeout()
	{
		getPropertiesFile();
		return Integer.parseInt(prop.getProperty("explicitwait.timeout"));
	}
	
	public static int getPageLoadTimeout()
	{
		getPropertiesFile();
		return Integer.parseInt(prop.getProperty("pageload.timeout"));
	}
	
	public static void incrementElementCtr(int value)
	{
		try{
			int ctr = value + 1;
			String projDir = System.getProperty("user.dir");
			getPropertiesFile();
			prop.setProperty("element.ctr", String.valueOf(ctr));
			prop.store(new FileOutputStream(projDir+"/src/org/apparelStore1/properties/test.properties"), null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void resetElementCtr()
	{
		try{
			getPropertiesFile();
			String projDir = System.getProperty("user.dir");
			prop.setProperty("website.url","http://automationpractice.com/index.php");
			prop.setProperty("accountLogin.url","http://automationpractice.com/index.php?controller=authentication&back=my-account");
			prop.setProperty("testData.filePath","/Users/cajetanfernandes/OneDrive/Documents/Studies and Certs/Selenium 3.0 Training/Assessment Project/");
			prop.setProperty("testData.fileName","Test-Data.xlsx");
			prop.setProperty("lazyload.timeout","10");
			prop.setProperty("explicitwait.timeout","10");
			prop.setProperty("pageload.timeout","60");
			prop.setProperty("element.ctr","0");
			prop.setProperty("#Browser types (set values as chrome, firefox, ie, safari, opera, etc.","");
			prop.setProperty("designatedBrowser.type","firefox");
			prop.setProperty("browser.count","3");
			prop.setProperty("browser.name0","chrome");
			prop.setProperty("browser.name1","firefox");
			prop.setProperty("browser.name2","safari");
			prop.store(new FileOutputStream(projDir+"/src/org/apparelStore1/properties/test.properties"), null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static int getElementCtr()
	{
		getPropertiesFile();
		return Integer.parseInt(prop.getProperty("element.ctr"));
	}
	
	public static String getSSFolderPath()
	{
		getPropertiesFile();
		ssFolderPath = prop.getProperty("screenshot.loc");
		return ssFolderPath;
	}
}
