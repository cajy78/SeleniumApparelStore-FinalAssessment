package org.apparelStore1.test;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public abstract class TestCase
{
	@BeforeMethod
	public abstract void launchBrowser();
	
	@AfterMethod
	public abstract void closeBrowser();
	
	@Test
	public abstract void runTestCase(Hashtable<String, String> testData);
	
	@DataProvider
	public abstract Object[][] getTestData() throws IOException;
}
