package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.MyaccountPageObject;
import testBase.BaseClass;

public class LoginTestCase extends BaseClass {

	@Test(groups= "sanity")
	public void verify_login() {

		logger.info("************* Starting TC *******************");

		HomePageObject hp = new HomePageObject(driver);
		hp.clickAccountBtn();
		hp.clickLogin();

		logger.info("************* Login with cred *******************");

		LoginPageObject lp = new LoginPageObject(driver);
		lp.setEmail(prop.getProperty("email"));
		lp.setPassword(prop.getProperty("password"));
		lp.clickSubmitBtn();

		logger.info("************* Called My account page *******************");
		MyaccountPageObject mcc = new MyaccountPageObject(driver);
		
		Assert.assertEquals(mcc.isMyAccPageExist(), true);

	}
}
