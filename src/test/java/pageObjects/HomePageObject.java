package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.BasePage;

public class HomePageObject extends BasePage{
	
	WebDriver driver;
	
	public HomePageObject(WebDriver driver) {
		super(driver);
	}
	
	// webelements *****************************************************
	
	@FindBy(xpath = "//span[text()='My Account']")
	WebElement accountBtn;
	
	@FindBy(xpath = "//a[text()='Register']")
	WebElement registerBtn;
	
	@FindBy(xpath="//a[text()='Login']")
	WebElement loginBtn;
	
	
	
	
	//Action methods**********************************************************
	
	public void clickAccountBtn(){
		accountBtn.click();
	}
	
	public void clickRegisterBtn() {
		registerBtn.click();
	}
	
	public void clickLogin() {
		loginBtn.click();
	}

}
