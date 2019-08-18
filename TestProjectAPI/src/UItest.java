import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;

public class UItest {

	public static void main(String[] args) throws IOException {

		// initialize the HtmlReporter
		System.out.println(System.getProperty("user.dir") + "/reports/testReport.html");
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/reports/testReport.html");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Browser", "chrome");

		ExtentTest test = extent.createTest("WebPageTableTest");

		// TODO Auto-generated method stub
		// www.way2automation.com/angularjs-protractor/webtables
		String baseUrl = "http://www.way2automation.com/angularjs-protractor/webtables";

		test.log(Status.INFO, "Chrome Browser launching Successfully");

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/resources/chromedriver.exe");
	
		// below for Firefox browser
		// System.setProperty("webdriver.firefox.marionette",System.getProperty("user.dir")+"/resources/GeckoDriver.exe");
		// driver= new FirefoxDriver();

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(baseUrl);

		String actual = driver.getTitle();
		test.log(Status.INFO, "Actual Title returned :: " + actual);

		String expected = "Protracr practice website - WebTable";
		test.log(Status.INFO, "Expected Title returned:: " + expected);

		if (actual.contentEquals(expected)) {
			test.log(Status.PASS, "Page is loaded. Title validated and it is as expected");
		} else {
			test.log(Status.FAIL, "Page is loaded. Title validation failed");

			String temp = getScreenshot(driver);
			test.fail("Page loading Failed screenshot", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

		if (driver.findElement(By.className("smart-table table table-striped")).isDisplayed()) {
			test.log(Status.PASS, "Web table is displayed successfully on the page");
		} else {
			test.log(Status.FAIL, "Failed to display the webtable on the page");

			String temp = getScreenshot(driver);
			test.fail("webtable missing screenshot", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

		// calling flush writes everything to the log/reporter file
		extent.flush();

		// quit the browser
		driver.quit();
	}

	// method to capture screenshots
	public static String getScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;

		File src = ts.getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/reports/Screenshot/" + System.currentTimeMillis() + ".png";

		File destination = new File(path);

		try {
			Files.copy(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}

		return path;
	}

}
