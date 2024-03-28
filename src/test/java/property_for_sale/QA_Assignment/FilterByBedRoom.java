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

public class FilterByBedRoom {
	WebDriver driver;
	public static final String URL = "https://www.fazwaz.com/property-for-sale/thailand";
	public static final String searchProvinceName = "Pattaya";
	WebDriverWait wait = null;
	int searchBedRoom = 3;

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
		Thread.sleep(2000);
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// Check the search result
		WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"search-result\"]/div[1]/div[1]/h1"));
		searchResult.click();
		Thread.sleep(3000);
		String actualResult = searchResult.getText();
		Assert.assertTrue(actualResult.contains(searchProvinceName));
		System.out.println(actualResult);

		// To click Bed Button
		WebElement bedButton = driver.findElement(By.xpath("//*[@id=\"search-bar\"]/div/div/div[2]"));
		bedButton.click();
		// Choose 3 Bed Room
		WebElement three_bedRoomButton = driver
				.findElement(By.xpath("//*[@id=\"search-bar\"]/div/div/div[2]/div/div[1]/div[4]"));
		three_bedRoomButton.click();
		bedButton.click();

		// To get Result BedRoom
		Thread.sleep(2000);
		WebElement resultBedRoom = driver
				.findElement(By.xpath("//*[@id=\"search-result\"]/div[5]/div[1]/div[2]/div[2]/div[5]/a"));
		String result_BedRoom = resultBedRoom.getText();
		String splitRoomNo = result_BedRoom.substring(0, 1);
		int actualBedRoom = Integer.parseInt(splitRoomNo);
		// System.out.println(actualBedRoom);

		// To check correctly display the Bed Room
		if (searchBedRoom <= actualBedRoom) {
			System.out.println("Correctly display the properties according to Bedroom filters");
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
