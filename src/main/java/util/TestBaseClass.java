package util;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBaseClass {
	public static WebDriver driver;

	@BeforeClass
	public void setUp() throws IOException {
		// set property for driver; could be set in System properties directly

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");

		driver = new ChromeDriver(options);

		driver.get("https://www.centralpoint.nl/notebooks-laptops/");
		driver.manage().window().maximize();

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		driver = null;
	}
}
