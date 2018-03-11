package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NotebooksPage {
	private WebDriver driver;

	By allFilters = By.cssSelector("#filters .mobileSwitchFiltersOff > div");
	By dropdown = By.cssSelector("#filters form ul li");
	By filterPrijs = By.xpath("//*[text()='Prijs']");
	By priceRangeLow = By.id("priceRangeLow");
	By priceRangeHigh = By.id("priceRangehigh");
	By buttonZoeken = By.xpath("//*[text()='Zoeken']");
	public static By priceExcl = By.xpath("//div[contains(@class,'price priceExcl')]");
	By searchField = By.cssSelector("input[name='search']");
	By buttonSubmitSearch = By.cssSelector("button[type='submit']");
	By checkBOXFirstDisplayed = By.cssSelector("#filters form ul li input");

	public List<WebElement> findCheckBoxFisrtDisplayed() {
		return driver.findElements(checkBOXFirstDisplayed);
	}

	public WebElement findButtomSubmit() {
		return driver.findElement(buttonSubmitSearch);
	}

	public WebElement findSearchField() {
		return driver.findElement(searchField);
	}

	public List<WebElement> priceOnResultPage() {
		return driver.findElements(priceExcl);
	}

	public WebElement findButtonZoeken() {
		return driver.findElement(buttonZoeken);
	}

	public WebElement findpriceRangeHigh() {
		return driver.findElement(priceRangeHigh);
	}

	public WebElement findpriceRangeLow() {
		return driver.findElement(priceRangeLow);
	}

	public WebElement findFilterPrijs() {
		return driver.findElement(filterPrijs);
	}

	public List<WebElement> firstCheckBox() {

		return driver.findElements(dropdown);
	}

	public List<WebElement> findAllFilters() {
		return driver.findElements(allFilters);
	}

	public NotebooksPage(WebDriver driver) {
		this.driver = driver;
	}
}
