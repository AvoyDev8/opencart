package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

/**
 * author: Avoy Kumar Roy
 */
public class ExtentManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String reportNm;
	
	public void onStart(ITestContext context) {

		String timeStmp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		//Report name
		reportNm = "Test_Report_"+timeStmp+".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+"Test_Report_"+timeStmp);  // specifying the file location
		
		sparkReporter.config().setDocumentTitle("Open cart Automation");
		sparkReporter.config().setReportName("Functional testing of open cart");
		sparkReporter.config().setTheme(Theme.DARK);
/* ****************************************************************************************** */		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Open Cart");
		extent.setSystemInfo("Module", "Frontend");
		extent.setSystemInfo("Tester Name", "Avoy Kumar Roy");
		extent.setSystemInfo("Environment", "Stg");
/* ********************************************************************************************* */		
		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("OS", os);
		
		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGrp = context.getCurrentXmlTest().getIncludedGroups();
		if(!includeGrp.isEmpty()) {
			extent.setSystemInfo("Groups", includeGrp.toString());
		}
				
	}

	public void onTestStart(ITestResult result) {
		// 
	}

	public void onTestSuccess(ITestResult result) {
		// on successfull execution
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS,result.getName()+" got executed successfully..");
	}

	public void onTestFailure(ITestResult result) {
		// on Failure of any test 
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			
			String imgPath = new BaseClass().captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void onTestSkipped(ITestResult result) {
		// on Test skip
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}


	public void onFinish(ITestContext context) {
		// on Test finsish
		extent.flush();
		
		String pathOfReport = System.getProperty("user.dir")+"\\reports\\"+reportNm;
		File file = new File(pathOfReport);
		
		try {
			Desktop.getDesktop().browse(file.toURI());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//Email setup 
		
		/* try {
			URL url = new URL("file:\\"+System.getProperty("user.dir")+"\\reports\\"+reportNm);
			
			ImageHtmlEmail eml = new ImageHtmlEmail();
			eml.setDataSourceResolver(new DataSourceUrlResolver(url));
			eml.setHostName("smtp.googlemail.com");
			eml.setSmtpPort(465);
			eml.setAuthenticator(new DefaultAuthenticator("xyz@gmail.com", "password12345"));
			eml.setSSLOnConnect(true);
			eml.setFrom("avoy@gmail.com");
			eml.setSubject("xyz subject");
			eml.setMessage("PFA.......");
			eml.setTo("to@gmail.com");
			eml.attach(url+" pls check the attachment");
			eml.send();
		}
		catch(Exception e) {
			e.printStackTrace();
		} */
		
	}

}
