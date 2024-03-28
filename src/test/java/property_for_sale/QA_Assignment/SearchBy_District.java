package property_for_sale.QA_Assignment;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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

public class SearchBy_District {
	WebDriver driver;
	public static final String URL = "https://www.fazwaz.com/property-for-sale/thailand";
	public static final String searchDistrictName = "Bang Sue";
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
		WebElement modalNoThanksButton = modalContainer.findElement(By.xpath(".//button[contains(text(),'No Thanks')]"));
		modalNoThanksButton.click();

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
		searchBox.sendKeys(searchDistrictName);
		Thread.sleep(3000);
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(3000);

		// Check the search result
		WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"search-result\"]/div[1]/div[1]/h1"));
		Thread.sleep(2000);
		searchResult.click();
		Thread.sleep(2000);
		String actualResult = searchResult.getText();
		Assert.assertTrue(actualResult.contains(searchDistrictName));
		System.out.println(actualResult);

	}

	@AfterMethod
	public void afterMethod() {
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
