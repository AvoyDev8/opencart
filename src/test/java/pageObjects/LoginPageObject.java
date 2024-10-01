package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.BasePage;

public class LoginPageObject extends BasePage {

	WebDriver driver;

	// constructor initialization
	public LoginPageObject(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "input-email")
	WebElement email;

	@FindBy(id = "input-password")
	WebElement pswd;

	@FindBy(xpath = "//input[@value='Login'and @type='submit']")
	WebElement submitBtn;

	// Action methods
	public void setEmail(String eml) {
		email.sendKeys(eml);
	}

	public void setPassword(String password) {
		pswd.sendKeys(password);
	}

	public void clickSubmitBtn() {
		submitBtn.click();
	}

	

}
