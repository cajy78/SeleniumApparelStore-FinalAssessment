package org.appareStore1.test.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader
{
	public static Object[][] ReadTestDataFromExcel(String filePath, String fileName, String sheetName) throws IOException
	{
		Object[][] data;
		FileInputStream fin = new FileInputStream(new File(filePath + "/" + fileName));
		//System.out.println(filePath + "/" + fileName);
		Workbook tdBk = new XSSFWorkbook(fin);
		Sheet sheet = tdBk.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		data = new Object[rowCount][1];
		Hashtable<String, String> rec;
		Row keyRow = sheet.getRow(0);
		for(int r = 1; r <= rowCount; r++)
		{
			rec = new Hashtable<String, String>();
			//System.out.println("hashtable created");
			Row dataRow = sheet.getRow(r);
			//System.out.println(dataRow);
			//System.out.println("Last cell num: "+ dataRow.getLastCellNum());
			for(int c = 0; c < dataRow.getLastCellNum(); c++)
			{
				String key = keyRow.getCell(c).getStringCellValue();
				String testData = dataRow.getCell(c).getStringCellValue();
				//System.out.println(key+" "+testData);
				rec.put(key, testData);
				//System.out.println("Email address: " + rec.get("email"));
			}
			data[r-1][0] = rec;
		}
		tdBk.close();
		return data;
	}
}
