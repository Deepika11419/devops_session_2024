package nagp.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class PaymentPage extends AbstractComponent {
	WebDriver driver;

	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(className = "trip-subtitle")
	private WebElement dateElement;

	@FindBy(className = "travel-operator-info")
	private WebElement operatorInfoElement;
	
	@FindBy(xpath = "//input[@class='button main-btn gtm-continueBooking']")
	WebElement proceedToPaymentButton;

	@FindBy(className = "travel-title")
	private WebElement titleElement;
	
	/* Method to Click on "Proceed to payment" button */
	
	public void paymenButtonClick()
	{
		proceedToPaymentButton.click();
	}


	/* Method to fetch and validate date text */
	public String validateDateText() {
		String dateText = dateElement.getText();
		return dateText;
	}

	/* Method to validate operator info text */
	public String validateOperatorInfo() {
		String operatorText = operatorInfoElement.getText();
		return operatorText;
	}

	/* Method to fetch and validate operator title */

	public Object[] validateOperatorTitle() {
		String operatorTitle = titleElement.getText();
		MyTrips myTrips = new MyTrips(driver);
		return new Object[] { operatorTitle, myTrips };
	}
}
