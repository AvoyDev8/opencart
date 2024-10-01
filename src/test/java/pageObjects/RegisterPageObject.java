package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.BasePage;

public class RegisterPageObject extends BasePage {
	
	//constructor initialization
	WebDriver driver;
	public RegisterPageObject(WebDriver driver) {
		super(driver);
	}
	
	
	//web-elements
	
	@FindBy(id="input-firstname")
	WebElement firstNm;
	
	@FindBy(id="input-lastname")
	WebElement lastNm;
	
	@FindBy(id="input-email")
	WebElement emailInput;
	
	@FindBy(id="input-telephone")
	WebElement telephone;
	
	@FindBy(id="input-password")
	WebElement pswd;
	
	@FindBy(id="input-confirm")
	WebElement confPswd;
	
	@FindBy(xpath="//input[@name='newsletter' and @value=1]")
	WebElement newsletterChkBoxk;
	
	@FindBy(xpath="//input[@name='agree' and @value=1]")
	WebElement agreeBtn;
	
	@FindBy(xpath="//input[@type='submit' and @value='Continue']")
	WebElement continueBtn;
	
	@FindBy(css="#content>h1")
	WebElement accCreateMsg;
	
	
	//Action methods
	
	public void setFristName(String fnm) {
		firstNm.sendKeys(fnm);
	}
	
	public void setLastName(String lnm) {
		lastNm.sendKeys(lnm);
	}
	
	public void setEmail(String email) {
		emailInput.sendKeys(email);
	}
	
	public void setTelephone(String phone) {
		telephone.sendKeys(phone);
	}
	
	public void setPasswd(String pwd) {
		pswd.sendKeys(pwd);
	}
	
	public void setConfPasswd(String cPwd) {
		confPswd.sendKeys(cPwd);
	}
	
	public void toggleNewsletter() {
		newsletterChkBoxk.click();
	}
	
	public void acceptTandC() {
		agreeBtn.click();
	}
	
	public void clickSubmitBtn() {
		continueBtn.click();
	}
	
	public String accountCreateSuccessMsg() {
		try {
			return accCreateMsg.getText();
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
}
