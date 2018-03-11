package com.bintime.test_task;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import junit.framework.Assert;
import pages.NotebooksPage;
import util.TestBaseClass;

public class SearchTest extends TestBaseClass {

	@Test
	public void searchTestMethod() throws InterruptedException {
		// restricted filter to click
		String prijs = "Prijs";
		String sortering = "Sortering";
		NotebooksPage np = new NotebooksPage(driver);
		List<WebElement> allFilters = np.findAllFilters();

		WebElement filter;
		boolean filterValid = true;
		while (filterValid) {
			int rand = (int) (Math.random() * 16) + 1; // random filter
			filter = allFilters.get(rand);
			String randomFilter = filter.getText();
			if (randomFilter.equals(prijs) || randomFilter.equals(sortering)) {
				continue;
			}
			filter.click();
			filterValid = false;
			;
		}

		String strCheckedCheckbox = "0";
		List<WebElement> firstCheckboxList = np.firstCheckBox(); // list of
																	// labels on
																	// checkboxes
		List<WebElement> firstVisibleCheckboxList = np.findCheckBoxFisrtDisplayed();// actual
																					// checkboxes
		// clicking on first visible checkbox from filter section
		for (WebElement check : firstVisibleCheckboxList) {
			if (check.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(check).click().build().perform();
				break;
			}
		}
		// getting label with expected amountof results
		for (WebElement web : firstCheckboxList) {
			if (web.isDisplayed()) {
				strCheckedCheckbox = web.getText();
				break;
			}
		}
		// getting a value from braces
		Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(strCheckedCheckbox);
		while (m.find()) {
			strCheckedCheckbox = m.group(1);
		}

		Actions clickOnZoeken = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(np.findButtonZoeken()));
		clickOnZoeken.moveToElement(element);
		clickOnZoeken.perform();
		// scrolling into view
		int elementPosition = element.getLocation().getY() - 100;
		String js = String.format("window.scroll(0, %s)", elementPosition);
		((JavascriptExecutor) driver).executeScript(js);
		element.click();

		WebDriverWait wait2 = new WebDriverWait(driver, 10);
		WebElement title = wait2.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".container .title")));
		String titleResult = title.getText();
		boolean result = false;
		if (titleResult.contains(strCheckedCheckbox)) {
			result = true;
		}
		Assert.assertTrue(result);
	}

	@Test
	public void setMinimumAndMaximumPrice() {
		NotebooksPage np = new NotebooksPage(driver);
		np.findFilterPrijs().click();
		WebElement lowPriceField = np.findpriceRangeLow();
		int minimum = 1000;
		lowPriceField.clear();
		lowPriceField.sendKeys("1000");
		WebElement highPriceField = np.findpriceRangeHigh();
		highPriceField.clear();
		highPriceField.sendKeys("5000");
		np.findButtonZoeken().click();

		List<WebElement> priceList = np.priceOnResultPage();

		String[] massString = new String[priceList.size()];
		int index = 0;
		boolean resultNotLower1000 = true;
		// getting strings with price value
		for (WebElement priceValue : priceList) {
			System.out.println(priceValue.getText());
			String temp = priceValue.getText();
			temp = temp.substring(0, 4);
			massString[index] = temp;
			index++;
		}
		Integer[] intarray = new Integer[massString.length];
		int i = 0;
		// checking if resulting cost corresponds to 1000 minimum value
		for (String str : massString) {
			intarray[i] = Integer.parseInt(str.trim());
			if (intarray[i] < minimum) {
				resultNotLower1000 = false;
			}
			i++;
		}

		Assert.assertTrue(resultNotLower1000);

	}

	@Test(dataProvider = "searchItems")
	public void searchItemsTest(String searchItem) throws InterruptedException {
		String currentURL = "https://www.centralpoint.nl/";
		driver.get(currentURL);

		NotebooksPage nPage = new NotebooksPage(driver);
		WebElement searchFieldOnMain = nPage.findSearchField();
		searchFieldOnMain.click();
		searchFieldOnMain.sendKeys(searchItem);

		nPage.findButtomSubmit().click();
		Thread.sleep(3000);

		String urlAfterLoad = driver.getCurrentUrl();
		// checking that url actually changed after providing id of an item
		boolean result = false;
		if (!urlAfterLoad.equals(currentURL)) {
			result = true;
		}
		Assert.assertTrue(result);

	}

	@DataProvider(name = "searchItems")
	public static Object[][] searchItems() {
		return new Object[][] { { "J153289" }, { "MQ3D2ZD/A" }, { "L36852-H2436-M101" }, { "1WZ03EA#ABH" },
				{ "875839-425" }, { "C5J91A#B19" }, { "FM32SD45B/10" }, { "204446-101" }, { "GV-N710D3-1GL" },
				{ "02G-P4-6150-KR" } };
	}

}
