package property_for_sale.QA_Assignment;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class SortByPriceHigh_to_Low {
	WebDriver driver;
	public static final String URL = "https://www.fazwaz.com/property-for-sale/thailand";
	public static final String searchProvinceName = "Chon Buri";
	WebDriverWait wait = null;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");

		// Instantiate a ChromeDriver class.
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);
		// Maximize the browser
		driver.manage().window().maximize();

		wait = new WebDriverWait(driver, 5);
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get(URL);
	}

	@Test
	public void searchProvinceName() throws InterruptedException {
		Thread.sleep(5000);
		// wait to display the modal box
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onesignal-slidedown-container")));

		WebElement modalContainer = driver.findElement(By.id("onesignal-slidedown-container"));
		Thread.sleep(2000);
		WebElement modalAcceptButton = modalContainer.findElement(By.xpath(".//button[contains(text(),'No Thanks')]"));
		modalAcceptButton.click();

		// Click Accept button for cookies
		WebElement cookiesContainer = driver.findElement(By.className("group_btn"));
		WebElement AcceptButton = cookiesContainer.findElement(By.xpath(".//button[contains(text(),'Accept')]"));
		AcceptButton.click();

		WebElement searchBox = driver
				.findElement(By.xpath("//input[@placeholder='Search by location, station, project or unit ID']"));
		// Check search box is display
		searchBox.isDisplayed();
		searchBox.sendKeys(Keys.CONTROL + "a");
		searchBox.sendKeys(Keys.BACK_SPACE);

		// Search the Province Name
		searchBox.sendKeys(searchProvinceName);
		Thread.sleep(3000);
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(3000);

		// Check the search result
		WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"search-result\"]/div[1]/div[1]/h1"));
		searchResult.click();
		Thread.sleep(3000);
		String actualResult = searchResult.getText();
		Assert.assertTrue(actualResult.contains(searchProvinceName));
		System.out.println(actualResult);

		// Get the price before sorting in the search result
		Thread.sleep(2000);
		WebElement fristPrice = driver
				.findElement(By.xpath("//*[@id=\"search-result\"]/div[5]/div[1]/div[1]/div[2]/div[4]"));
		String actualPrice = fristPrice.getText();
		String splitOtherInfo = actualPrice.substring(0, 11);
		if (splitOtherInfo.contains(",")) {
			splitOtherInfo = splitOtherInfo.replace(",", "");
		}
		// Check search result price is between Minimum Price and Maximum Price
		Pattern pattern = Pattern.compile("\\d+"); // This pattern matches one or more digits

		// Matcher to find numbers in the input string
		Matcher minMatcher = pattern.matcher(splitOtherInfo);
		int beforeSortingPrice = 0;
		if (minMatcher.find()) {
			// Convert the matched string to integer
			beforeSortingPrice = Integer.parseInt(minMatcher.group());
			//System.out.println("beforeSortingPrice =====" + beforeSortingPrice);

		}
		// Click Sort Button
		Thread.sleep(2000);
		WebElement sortButton = driver.findElement(By.id("searchOrderBySelect"));
		sortButton.click();
		Thread.sleep(2000);
		sortButton.sendKeys(Keys.ARROW_DOWN);
		sortButton.sendKeys(Keys.ARROW_DOWN);
		sortButton.sendKeys(Keys.ARROW_DOWN);
		sortButton.sendKeys(Keys.ENTER);
		
		// Get the price after sorting in the search result
				Thread.sleep(2000);
				WebElement secondPrice = driver
						.findElement(By.xpath("//*[@id=\"search-result\"]/div[5]/div[1]/div[1]/div[2]/div[4]"));
				String second_Price = secondPrice.getText();

				String splitInfo = second_Price.substring(0, 11);
				if (splitInfo.contains(",")) {
					splitInfo = splitInfo.replace(",", "");
				}

				// Matcher to find numbers in the input string
				Matcher maxMatcher = pattern.matcher(splitInfo);
				int afterSortingPrice = 0;
				if (maxMatcher.find()) {
					// Convert the matched string to integer
					afterSortingPrice = Integer.parseInt(maxMatcher.group());
					 //System.out.println("afterSortingPrice =====" + afterSortingPrice);

				}
				if (afterSortingPrice > beforeSortingPrice) {
					System.out.println("Correctly sorting the price High to Low");
				}

	}

	@AfterMethod
	public void afterMethod() {
	}

	@AfterClass
	public void afterClass() {
		 driver.quit();
	}

}
