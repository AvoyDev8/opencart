package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.BasePage;

public class MyaccountPageObject extends BasePage{

	public MyaccountPageObject(WebDriver driver) {
		super(driver);
	}
	
	
	//webelements
	@FindBy(xpath = "//h2[text()='My Account']")
	WebElement msgHeading;
	
	@FindBy(xpath ="(//a[text()='Logout'])[2]")
	WebElement logout;
	
	
	
	//Actions
	public boolean isMyAccPageExist() {
		try {
			return msgHeading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void clickLogout() {
		logout.click();
	}
}
