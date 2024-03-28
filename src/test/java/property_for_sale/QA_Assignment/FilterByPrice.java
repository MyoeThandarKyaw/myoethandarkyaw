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

public class FilterByPrice {
	WebDriver driver;
	public static final String URL = "https://www.fazwaz.com/property-for-sale/thailand";
	public static final String searchProvinceName = "Chiang Mai";
	public static final String minPrice = "K200 Lakhs";
	public static final String maxPrice = "K400 Lakhs";
	WebDriverWait wait = null;
	int min_Price = 0;
	int max_Price = 0;
	int expected_Price = 0;

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
		WebElement modalNoThanksButton = modalContainer.findElement(By.xpath(".//button[contains(text(),'No Thanks')]"));
		modalNoThanksButton.click();

		// Click Accept for cookies
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
		Thread.sleep(2000);
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// Check the search result
		WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"search-result\"]/div[1]/div[1]/h1"));
		searchResult.click();
		Thread.sleep(2000);
		String actualResult = searchResult.getText();
		Assert.assertTrue(actualResult.contains(searchProvinceName));
		//System.out.println(actualResult);

		// To click Price Button
		WebElement priceButton = driver.findElement(By.className("search-menu__item"));
		priceButton.click();
		
		//Choose Minimum price
		WebElement minButton = driver.findElement(By.xpath("//input[@placeholder='No Min']"));
		minButton.click();
		Thread.sleep(2000);
		minButton.sendKeys(minPrice);
		minButton.sendKeys(Keys.ENTER);

		//Choose Maximum price
		WebElement maxButton = driver.findElement(By.xpath("//input[@placeholder='No Max']"));
		maxButton.click();
		Thread.sleep(2000);
		maxButton.sendKeys(maxPrice);
		maxButton.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		driver.findElement(By.className("box-search__load")).click();

		Thread.sleep(2000);

		WebElement displayPrice = driver
				.findElement(By.xpath("//*[@id=\"search-result\"]/div[5]/div[1]/div[1]/div[2]/div[4]"));
		String actualPrice = displayPrice.getText();
		//System.out.println(actualPrice);
		
		
		//Check search result price is between Minimum Price and Maximum Price
		Pattern pattern = Pattern.compile("\\d+"); // This pattern matches one or more digits

		// Matcher to find numbers in the input string
		Matcher minMatcher = pattern.matcher(minPrice);

		if (minMatcher.find()) {
			// Convert the matched string to integer 
			min_Price = Integer.parseInt(minMatcher.group());
			// System.out.println(min_Price);

		}
		Matcher maxMatcher = pattern.matcher(maxPrice);
		if (maxMatcher.find()) {
			// Convert the matched string to integer 
			max_Price = Integer.parseInt(maxMatcher.group());

		}

		Matcher actualPriceMatcher = pattern.matcher(actualPrice);
		if (actualPriceMatcher.find()) {
			// Convert the matched string to integer
			expected_Price = Integer.parseInt(actualPriceMatcher.group());
			// System.out.println(expected_Price);

		}

		if (expected_Price >= min_Price && expected_Price <= max_Price) {
			System.out.println("Correctly display the properties bettween the price range");
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
