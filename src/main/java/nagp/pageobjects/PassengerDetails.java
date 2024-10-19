package nagp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class PassengerDetails extends AbstractComponent {
	WebDriver driver;

	public PassengerDetails(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@name='Age_0']")
	WebElement ageInput;

	@FindBy(xpath = "//div[@id='div_22_0']")
	WebElement genderButton;

	@FindBy(id = "201")
	WebElement inputBox;

	@FindBy(className = "GstStateField__StyledUl-rbzf11-1")
	WebElement delhiDropdown;

	@FindBy(xpath = "//input[@id='seatno-06']")
	WebElement phoneNumberInput;

	By phoneNumberInputBy = By.xpath("//input[@id='seatno-06']");

	/* Method to enter the PassengerDetails */
	public PaymentPage enterPassengerDetails(String age, String location, String phoneNum) {
		/* Enter age */
		ageInput.sendKeys(age);
		/* Select toggle button */
		genderButton.click();
		/* Find the input textbox element and enter "delhi" into it */
		inputBox.clear();
		inputBox.sendKeys(location);
		/* Find the dropdown list element and click on it to open the options */
		delhiDropdown.click();
		/* Scroll to phone number input */
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", phoneNumberInput);
		/* Wait for the phone number input to be visible */
		waitForElementToAppear(phoneNumberInputBy);
		/* Enter phone number */
		phoneNumberInput.sendKeys(phoneNum);
		return new PaymentPage(driver);
	}

}
