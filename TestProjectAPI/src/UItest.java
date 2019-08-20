import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

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

		if (driver.findElement(By.xpath("//*[@class='smart-table table table-striped']")).isDisplayed()) {
			test.log(Status.PASS, "Web table is displayed successfully on the page");
		} else {
			test.log(Status.FAIL, "Failed to display the webtable on the page");

			String temp = getScreenshot(driver);
			test.fail("webtable missing screenshot", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
		// click on add user button
		driver.findElement(By.xpath("//*[@class='btn btn-link pull-right']")).click();

		adduserdetails(driver, "FName1", "LName1", "User1", "Pass1", "Company AAA", "Admin", "admin@mail.com",
				"082555");

		// verify the added user
		searchUsername(driver, test, "FName1");
		// click on add user button to enter second user
		driver.findElement(By.xpath("//*[@class='btn btn-link pull-right']")).click();
		adduserdetails(driver, "FName2", "LName2", "User2", "Pass2", "Company BBB", "Customer", "cusotmer@mail.com",
				"083444");
		searchUsername(driver, test, "FName2");

		// calling flush writes everything to the log/reporter file
		extent.flush();

		// quit the browser
		driver.quit();
	}

	/**
	 * @param driver
	 * @param phoneNo
	 * @param email
	 * @param RoleIDDropdown
	 * @param CmpRadioBtnName
	 * @param pwd
	 * @param uname
	 * @param lname
	 * @param fname
	 */
	private static void adduserdetails(WebDriver driver, String fname, String lname, String uname, String pwd,
			String CmpRadioBtnName, String RoleIDDropdown, String email, String phoneNo) {
		driver.findElement(By.xpath("//input[@name='FirstName']")).clear();
		driver.findElement(By.xpath("//input[@name='FirstName']")).sendKeys(fname);
		driver.findElement(By.xpath("//input[@name='LastName']")).clear();
		driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys(lname);
		driver.findElement(By.xpath("//input[@name='UserName']")).clear();
		driver.findElement(By.xpath("//input[@name='Password']")).clear();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(50) + 1;// to generate unique
														// name
		String unamex = uname + randomInt;
		driver.findElement(By.xpath("//input[@name='UserName']")).sendKeys(unamex);
		driver.findElement(By.xpath("//input[@name='Password']")).sendKeys(pwd);

		
		//String cmpxpath = "//label[contains(text(),'" + CmpRadioBtnName + "')]/input";
		//here cmpxpath should be able to give right radio button 
		//eg: //label[contains(text(),'Company AAA')]/input
		//driver.findElement(By.xpath(cmpxpath)).click();

		Select sel = new Select(driver.findElement(By.name("RoleId")));
		sel.selectByVisibleText(RoleIDDropdown);
		
		driver.findElement(By.xpath("//input[@name='Email']")).clear();
		driver.findElement(By.xpath("//input[@name='Mobilephone']")).clear();
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='Mobilephone']")).sendKeys(phoneNo);
		// click save button
		driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
	}

	// verifying if the new row is present with added user
	public static void searchUsername(WebDriver driver, ExtentTest test, String username) throws IOException {
		boolean isFound = false;
		List<WebElement> rows = driver.findElements(By.cssSelector("tbody > tr"));
		for (WebElement row : rows) {
			if (row.findElement(By.xpath("(//td[@class='smart-table-data-cell'])[1]")).getText().equals(username)) {
				isFound = true;
				break;
			}
		}
		if (isFound) {
			test.log(Status.PASS, "New user added to the page successfully");
		} else {
			test.log(Status.FAIL, "Failed to addnew user added to the page");

			String temp = getScreenshot(driver);
			test.fail("webtable missing screenshot", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
		// return isFound;
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
