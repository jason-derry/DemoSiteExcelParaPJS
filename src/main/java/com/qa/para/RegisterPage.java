package com.qa.para;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage {
	
	
	@FindBy(name = "username")
	private WebElement username;
	
	@FindBy(name = "password")
	private WebElement password;
	
	@FindBy(name = "FormsButton2")
	private WebElement submit;
	
	public void Register(String user, String pass) {
		
		username.sendKeys(user);
		password.sendKeys(pass);
		submit.click();
		
	}	
}