/**
 * 
 */
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.MyaccountPageObject;
import testBase.BaseClass;
import utilities.DataProviders;

/**
 * @author Avoy Kumar Roy
 */
public class DDTLogin extends BaseClass {

	@Test(dataProvider = "loginData", dataProviderClass = DataProviders.class)
	public void loginTest(String email, String password, String isValid) {
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
