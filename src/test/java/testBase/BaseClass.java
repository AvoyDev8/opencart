package testBase;

import java.io.FileReader;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	
	public WebDriver driver;
	public Logger logger;
	public Properties prop;
	
	
	@BeforeClass(groups= {"sanaty","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String browser) {
		
		//properties files
		try {
			FileReader fr=new FileReader(".//src//test//resources//config.properties");
			prop=new Properties();
			prop.load(fr);
			
//			System.out.println(prop.getProperty("url"));
		}catch(Exception e) {
			System.out.println("File not found"); return;
		}
		
		
		logger=LogManager.getLogger(this.getClass());
		
		switch(browser.toLowerCase()) {
		case "chrome": driver=new ChromeDriver(); break;
		case "firefox": driver = new FirefoxDriver(); break;
		case "edge": driver = new EdgeDriver(); break;
		case "safari": driver = new SafariDriver(); break;
		default: System.out.println("Invalid browser name!!!"); return;
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();

	}

	@AfterClass(groups= {"sanaty","Regression","Master"})
	public void teardown() {
		driver.quit();
	}
	
	public String randomString(int stringlen) {
		@SuppressWarnings("deprecation")
		String generatedString = RandomStringUtils.randomAlphabetic(stringlen);
		return generatedString;
	}
	
	public String randomNumber(int numSize) {
		@SuppressWarnings("deprecation")
		String randNum = RandomStringUtils.randomNumeric(numSize);
		return randNum;
	}

	public String randomAlphanumeric(int alphaNum) {
		@SuppressWarnings("deprecation")
		String alphaNumeric = RandomStringUtils.randomAlphanumeric(alphaNum);
		return alphaNumeric;
	}
}
