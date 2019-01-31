package com.qa.para;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.PageFactory;

@RunWith(Parameterized.class)
public class ExcelParaTest {
	
	WebDriver driver;
	static XSSFSheet sheet;
	FileInputStream file;
	XSSFWorkbook workbook;
	FileOutputStream fileOut;
	XSSFRow Row;
	XSSFCell cell;

	@Parameters
	public static Collection<Object[]> data() throws IOException {
		FileInputStream file = new FileInputStream(Constants.FILE);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Object[][] ob = new Object[sheet.getPhysicalNumberOfRows()-1][4];
		
//		Reading
		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
				ob[rowNum-1][0] = sheet.getRow(rowNum).getCell(0).getStringCellValue();
				ob[rowNum-1][1] = sheet.getRow(rowNum).getCell(1).getStringCellValue();
				ob[rowNum-1][2] = sheet.getRow(rowNum).getCell(2).getStringCellValue();
				ob[rowNum-1][3] = rowNum;
			}
		workbook.close();
		return Arrays.asList(ob);
		}
	
	private String username;
	private String password;
	private String expected;
	private int row;
	
	public ExcelParaTest(String username, String password, String expected, int row) {
		this.username = username;
		this.password = password;
		this.expected = expected;
		this.row = row;
	}
	
	@Before
	public void setup() throws Exception {
		System.setProperty("phantomjs.binary.path", Constants.PHANTOMJS);
		driver = new PhantomJSDriver();
		file = new FileInputStream(Constants.FILE);
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheetAt(0);
	}
	
	@After
	public void teardown() throws Exception {
		driver.quit();		
		fileOut = new FileOutputStream(Constants.FILE);
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		file.close();
	}
	
	@Test
	public void TestRegisterLogin() throws Exception {
		
		driver.get(Constants.REGISTER);
		RegisterPage regPage = PageFactory.initElements(driver, RegisterPage.class);
		regPage.Register(username, password);
		driver.get(Constants.LOGIN);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.Login(username, password, expected, row);
				
	}
}
