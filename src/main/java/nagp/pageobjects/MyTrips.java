package nagp.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class MyTrips extends AbstractComponent {
	WebDriver driver;

	public MyTrips(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "i.icon.icon-down.icon_secondary_item_more")
	WebElement myaccountDropdownicon;

	By myaccountDropdowniconBy = By.cssSelector("i.icon.icon-down.icon_secondary_item_more");

	@FindBy(xpath = "//span[text()='My Trips']")
	WebElement myTrips;

	By myTripsBy = By.xpath("//span[text()='My Trips']");

	@FindBy(id = "uperrMsg1")
	WebElement uperrMsg1;

	By upperMsg1By = By.id("uperrMsg1");

	@FindBy(id = "uperrMsg2")
	WebElement uperrMsg2;

	@FindBy(xpath = "//div[@class='modalCloseSmall']")
	WebElement close;

	By upperMsg2By = By.id("uperrMsg2");

	/* Method to click on My Account dropdown icon */
	public void clickMyAccountDropdownIcon() {
		Thread.sleep(2000);
		close.click();
		waitForElementToClickable(myaccountDropdowniconBy);
		myaccountDropdownicon.click();
	}

	/* Method to click on My Trips */
	public void clickMyTrips() {
		waitForElementToClickable(myTripsBy);
		myTrips.click();
	}

	/* Method to switch to new tab */
	public void switchToNewTab() {
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
	}

	/* Method to validate the error messages */
	public List<String> validateErrorMessages() {

		List<String> errorMessages = new ArrayList<>();

		waitForElementToAppear(upperMsg1By);
		String message1 = uperrMsg1.getText();
		waitForElementToAppear(upperMsg2By);
		String message2 = uperrMsg2.getText();

		errorMessages.add(message1);
		errorMessages.add(message2);
		return errorMessages;
	}
}
