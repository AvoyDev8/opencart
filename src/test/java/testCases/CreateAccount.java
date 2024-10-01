package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObject;
import testBase.BaseClass;

public class CreateAccount extends BaseClass {

	@Test(groups= {"sanaty","Regression","Master"})
	public void verify_account_regist() {
		
		logger.info("************* Starting TC *******************");
		
		HomePageObject hp = new HomePageObject(driver);
		hp.clickAccountBtn();
		hp.clickRegisterBtn();

		logger.info("************* Registration form fillup start *******************");
		RegisterPageObject regPage = new RegisterPageObject(driver);
		regPage.setFristName(randomString(5));
		regPage.setLastName(randomString(5));
		regPage.setEmail(randomAlphanumeric(5) + "@clirnet.com");
		regPage.setTelephone(randomNumber(10));

		final String genPass = randomAlphanumeric(8);
		regPage.setPasswd(genPass);
		regPage.setConfPasswd(genPass);

		regPage.toggleNewsletter();
		regPage.acceptTandC();
		regPage.clickSubmitBtn();

		logger.info("************* Validating the successmessage *******************");
		Assert.assertEquals(regPage.accountCreateSuccessMsg(), "Your Account Has Been Created!");
	
		logger.info("************* End of TC *******************");
	}

}
