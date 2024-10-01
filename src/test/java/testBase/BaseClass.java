package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	
	public static WebDriver driver;
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
		
		if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			if(os.equalsIgnoreCase("Windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("Set your platform before executing ......\n Platform not found");
				return ;
			}
			
			
			switch(browser.toLowerCase()) {
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No Matching Browser"); return;
			}
		}
		else {
			
			switch(browser.toLowerCase()) {
			case "chrome": driver=new ChromeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			case "edge": driver = new EdgeDriver(); break;
			case "safari": driver = new SafariDriver(); break;
			default: System.out.println("Invalid browser name!!!"); return;
			}
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
	
	public String captureScreenshot(String tname) throws IOException {
		
		String timeStmp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot tkSrcShot = (TakesScreenshot) driver;
		File sourceFile = tkSrcShot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenShorts\\"+tname+"_"+timeStmp+".png";
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}
