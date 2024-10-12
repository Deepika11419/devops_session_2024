package nagp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class RedBusLandingPage extends AbstractComponent {
	WebDriver driver;

	public RedBusLandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='My Account']")
	WebElement myaccountDropdown;
	By myaccoundDropdownBy = By.xpath("//span[text()='My Account']");

	@FindBy(xpath = "//span[text()='Account']")
	WebElement accountDropdown;

	By accountDD = By.xpath("//span[text()='Account']");

	@FindBy(id = "src")
	WebElement FromSource;

	@FindBy(className = "sc-dnqmqq")
	WebElement dropdown;

	@FindBy(xpath = ".//text[contains(text(), 'New Delhi')]")
	WebElement newDelhiOption;
	By dropdownBy = By.className("sc-dnqmqq");

	@FindBy(id = "dest")
	WebElement ToDestination;

	@FindBy(className = "sc-dnqmqq")
	WebElement destinationDropdown;

	@FindBy(xpath = ".//text[contains(text(), 'Chandigarh')]")
	WebElement chandigarhDropdown;

	@FindBy(xpath = "//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[1]/div[2]")
	WebElement actualMonth;

	@FindBy(xpath = "//*[@id=\"onwardCal\"]/div/div")
	WebElement calendarclick;

	@FindBy(xpath = "//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[1]/div[3]")
	WebElement nextMonth;

	@FindBy(xpath = "//button[text()='SEARCH BUSES']")
	WebElement searchBus;
	@FindBy(xpath = "//div[@class='modalCloseSmall']")
	WebElement close;

	public String getMyAccountText() throws InterruptedException {
		Thread.sleep(1000);
		close.click();
		waitForElementToClickable(myaccoundDropdownBy);
		String ActualText = myaccountDropdown.getText();
		return ActualText;
	}

	/* Method to select Source and Destination */
	public void selectSourceDestination() {
		FromSource.sendKeys("New Delhi");
		waitForElementToAppear(dropdownBy);
		newDelhiOption.click();
		ToDestination.sendKeys("Chandigarh");
		waitForElementToAppear(dropdownBy);
		chandigarhDropdown.click();
	}

	/* Method to select the data */
	public void datePicker(String month, String day) throws InterruptedException {
		calendarclick.click();
		while (true) {

			String actual_month = actualMonth.getText();
			System.out.println(actual_month);
			if (actual_month.contains(month)) {
				System.out.println(actual_month);
				break;

			} else {
				nextMonth.click(); /* next month */
			}
		}
		int column_size = 7; /* as per the days (Mon - Sun) */
		int flag = 0;
		int row_size = driver.findElements(By.xpath("//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[3]/div"))
				.size(); /* 8 */
		for (int i = 2; i <= row_size; i++) { /* row */

			for (int j = 1; j <= column_size; j++) { /* column */

				String actual_date = driver
						.findElement(By.xpath(
								"//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[3]/div[" + i + "]/span/div[" + j + "]"))
						.getText();

				if (actual_date.equals(day)) {
					driver.findElement(By.xpath(
							"//*[@id=\"onwardCal\"]/div/div[2]/div/div/div[3]/div[" + i + "]/span/div[" + j + "]"))
							.click();
					flag = 1; /* set flag = 1 */
					Thread.sleep(3000);
					break; /* breaking out of inner loop */
				} else {
					continue;
				}
			}
			if (flag == 1) { /* selection of the date is done */
				break;
			}
		}
	}

	public SearchedBusesLandingPage searchBus() {
		searchBus.click();
		return new SearchedBusesLandingPage(driver);

	}

}
