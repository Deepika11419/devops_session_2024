package nagp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class LogOutPage extends AbstractComponent {
	WebDriver driver;

	public LogOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='My Account']")
	WebElement myAccountDropdown;
	By myAccounrDropdownBy = By.xpath("//span[text()='My Account']");

	@FindBy(xpath = "//span[text()='Sign Out']")
	WebElement signOutButton;

	By signOutButtonBy = By.xpath("//span[text()='Sign Out']");

	@FindBy(className = "loginErr")
	WebElement loginErrorMessage;



	By loginErrorMessageBy = By.className("loginErr");

	/* Method to click My Account dropdown */
	public void clickMyAccountDropdown() throws InterruptedException{
		waitForElementToAppear(myAccounrDropdownBy);
		myAccountDropdown.click();
	}

	/* Method to click Sign Out button */
	public void clickSignOutButton() {
		waitForElementToClickable(signOutButtonBy);
		signOutButton.click();
	}

	/* Method to get and print error message */
	public String getErrorMessageAfterSignOut() {
		waitForElementToAppear(loginErrorMessageBy);
		return loginErrorMessage.getText();
	}
}
