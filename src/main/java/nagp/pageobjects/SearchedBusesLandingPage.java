package nagp.pageobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class SearchedBusesLandingPage extends AbstractComponent {
	WebDriver driver;

	public SearchedBusesLandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[text()='Delhi to Chandigarh Bus']")
	WebElement delhiToChandigarhText;

	By delhiToChandigarhTextBy = By.xpath("//h1[text()='Delhi to Chandigarh Bus']");

	@FindBy(xpath = "//div[@class='w-17 fl ']/div[text()='RSRTC Buses']/../following-sibling::div[@class='w-14 fl']/div[@class='button']")
	WebElement busesRSRTC;

	@FindAll({ @FindBy(xpath = "//div[contains(@class, 'travels') and contains(., 'RSRTC')]") })
	List<WebElement> rsrtcBusElements;

	@FindBy(xpath = "(//div[@class='button view-seats fr'])[3]")
	WebElement viewSeat;

	By viewSeatBy = By.xpath("(//div[@class='button view-seats fr'])[3]");

	@FindBy(xpath = "//canvas[@data-type='lower']")
	WebElement canvas;

	By canvasBy = By.xpath("//canvas[@data-type='lower']");

	@FindAll({ @FindBy(xpath = "//span[@class='bpDpName-Lbl']") })
	List<WebElement> spanElements;

	@FindBy(xpath = "//span[@class='fr fare-summary-value']")
	WebElement amountElement;

	@FindBy(xpath = "//div[@class='continue-container w-100 fl m-b-10']//button[@class='button continue inactive']")
	WebElement proceedToBookButton;

	By proceedToBookButtonBy = By
			.xpath("//div[@class='continue-container w-100 fl m-b-10']//button[@class='button continue inactive']");

	/* Method to validate if the Delhi to Chandigarh buses are displayed */
	public String validateDelhiToChandigarhpage() {
		waitForElementToAppear(delhiToChandigarhTextBy);
		String pageTitleofBus = driver.getTitle();
		return pageTitleofBus;
	}

	/* Methods to display list of RSRTC Buses */
	public void displayRSRTCBuses() {
		busesRSRTC.click();
		List<String> busNames = rsrtcBusElements.stream().map(WebElement::getText).collect(Collectors.toList());

		busNames.forEach(System.out::println);
	}

	/* Method to book to seat */
	public void SeatBooking() {
		waitForElementToClickable(viewSeatBy);
		moveToElementAndClick(viewSeat);
		viewSeat.click();
		waitForElementToAppear(canvasBy);
		// Calculate the coordinates assuming the canvas is in the middle of the page
		int xCoordinate = canvas.getSize().getWidth() / 2;
		int yCoordinate = canvas.getSize().getHeight() / 2;
		int button_x = (xCoordinate / 3) * 2;
		int button_y = (yCoordinate / 3) * 2;
		moveToElementAndClickAtPosition(canvas, button_x, button_y);

	}

	/* Method to validate Boarding point */
	public boolean validateBoardingPoint() {
		boolean containsDelhi = false;

		for (WebElement spanElement : spanElements) {
			String text = spanElement.getText();
			if (text.equals("DELHI")) {
				containsDelhi = true;
			}
		}
		return containsDelhi;
	}

	/* Validate to validate Drop point */
	public boolean validateDropPoint() {

		boolean containsChandigarh = false;

		for (WebElement spanElement : spanElements) {
			String text = spanElement.getText();
			if (text.equals("CHANDIGARH")) {
				containsChandigarh = true;
			}
		}
		return containsChandigarh;
	}

	/* Method to validate Fare amount */
	public void validateFareamount() {
		String amountText = amountElement.getText();
		System.out.println("Amount: " + amountText);

	}

	/* Method to click proceed to Book */
	public PassengerDetails clickProceedToBook() {
		waitForElementToClickable(proceedToBookButtonBy);
		// Click on the button
		proceedToBookButton.click();
		return new PassengerDetails(driver);
	}

}
