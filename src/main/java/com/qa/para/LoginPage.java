package com.qa.para;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	
	@FindBy(name = "username")
	private WebElement usernameinput;
	
	@FindBy(name = "password")
	private WebElement passwordinput;
	
	@FindBy(name = "FormsButton2")
	private WebElement submit;
	
	@FindBy(xpath = "/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b")
	private WebElement loginText;
	
	XSSFRow Row;
	XSSFCell cell;
	
	public void Login(String username, String password, String expected, int row) throws Exception {
		
		FileInputStream file = new FileInputStream(Constants.FILE);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		usernameinput.sendKeys(sheet.getRow(row).getCell(0).getStringCellValue());
		passwordinput.sendKeys(sheet.getRow(row).getCell(1).getStringCellValue());
		submit.click();
		
		// testing logic
		System.out.println(username + " " + password + " " + expected);
		// grab actual result and insert it into spreadsheet
		System.out.println("actual result needs to be printed in row:" + row);
		
		Row = sheet.getRow(row);
		cell = Row.getCell(3);
		if (cell == null) {
			cell = Row.createCell(3);
		}
		cell.setCellValue(loginText.getText());
		
		try {
			assertEquals("login unsuccessful", expected, loginText.getText()); //assertEquals("error message", expected value, actual value);
			
			// write pass to excel sheet
			Row = sheet.getRow(row);
			cell = Row.getCell(4);
			if (cell == null) {
				cell = Row.createCell(4);
			}
			cell.setCellValue("pass!");
		}
		catch (AssertionError e) {
			// write fail to excel sheet
			Row = sheet.getRow(row);
			cell = Row.getCell(4);
			if (cell == null) {
				cell = Row.createCell(4);
			}
			cell.setCellValue("fail!");
			
			Assert.fail("login unsuccessful!"); // Because AssertionError was caught, test no longer fails,
							// therefore we force it to fail after we have written it to excel sheet
		}
	}
}
