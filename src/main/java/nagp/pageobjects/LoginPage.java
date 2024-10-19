package nagp.pageobjects;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nagp.AbstractComponents.AbstractComponent;

public class LoginPage extends AbstractComponent {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='Account']")
	WebElement accountDropdown;

	By accountDD = By.xpath("//span[text()='Account']");

	@FindBy(xpath = "//li[@id='user_sign_in_sign_up']/span")
	WebElement loginSingupOption;

	By loginSignup = By.xpath("//li[@id='user_sign_in_sign_up']/span");

	@FindBy(xpath = "//i[@class='icon-close']")
	WebElement closeLightBox;

	By closeLightBoxBy = By.xpath("//i[@class='icon-close']");

	@FindBy(xpath = "//iframe[@class='modalIframe']")
	WebElement iframeP;

	@FindBy(xpath = "//div[@class='modalCloseSmall']")
	WebElement close;

	@FindBy(xpath = "//iframe[@title='Sign in with Google Button']")
	WebElement iframeS;

	@FindBy(xpath = "//span[text()='Sign in with Google']")
	 WebElement signInWithGoogle;
	
	By signInWithGoogleBy = By.xpath("//span[text()='Sign in with Google']");

	@FindBy(id = "identifierId")
	WebElement emailField;

	By emailFieldBy = By.id("identifierId");

	@FindBy(name = "Passwd")
	WebElement passwordField;

	@FindBy(xpath = "//div[@class='modalCloseSmall']")
	WebElement close;

	By passwordFieldBy = By.name("Passwd");

	@FindBy(xpath = "//span[text()='Wrong password. Try again or click Forgot password to reset it.']")
	WebElement errorMessage;

	By errorMessageBy = By.xpath("//span[text()='Wrong password. Try again or click Forgot password to reset it.']");

	public String getAccountText() {
		waitForElementToClickable(accountDD);
		String ActualText = accountDropdown.getText();
		return ActualText;
	}

	/* Method for logging into the Redbus Application */
	public LoginData loginApplication(String emailID, String password) throws InterruptedException

	{
		waitForElementToAppear(accountDD);
		accountDropdown.click();
		waitForElementToAppear(loginSignup);
		loginSingupOption.click();
		waitForElementToAppear(closeLightBoxBy);
		driver.switchTo().frame(iframeP);
		Thread.sleep(3000);
		driver.switchTo().frame(iframeS);
		waitForElementToAppear(signInWithGoogleBy);
		signInWithGoogle.click();
		driver.switchTo().defaultContent();
		String mainWindowHandle = driver.getWindowHandle();


		Set<String> windowHandles = driver.getWindowHandles();

		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(driver.getWindowHandle())) {
				driver.switchTo().window(windowHandle);
				System.out.println(windowHandle);
				Thread.sleep(3000);
				driver.manage().window().maximize();
				break;
			}
		}

		Actions actions = new Actions(driver);
		actions.moveToElement(emailField);
		actions.click();
		actions.sendKeys(emailField, emailID).perform();

		actions.sendKeys(Keys.RETURN).perform();
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", passwordField);
		waitForElementToClickable(passwordFieldBy);
		passwordField.sendKeys(password);
		actions.sendKeys(Keys.RETURN).perform();
		driver.switchTo().window(mainWindowHandle);
		Thread.sleep(5000);
		close.click();
		Thread.sleep(5000);

		RedBusLandingPage redbusLandingPage = new RedBusLandingPage(driver);
		MyTrips myTrips = new MyTrips(driver);
		LogOutPage logOutPage = new LogOutPage(driver);
		// Return a custom object that contains references to the above objects
		return new LoginData(redbusLandingPage, myTrips, logOutPage);
	}

	/* Method for error validation when user enter the wrong email ID or password */
	public String errorMessagevalidation(String emailID, String password) throws InterruptedException

	{
		waitForElementToAppear(accountDD);
		accountDropdown.click();
		waitForElementToAppear(loginSignup);
		loginSingupOption.click();
		waitForElementToAppear(closeLightBoxBy);
		driver.switchTo().frame(iframeP);
		driver.switchTo().frame(iframeS);
		waitForElementToAppear(signInWithGoogleBy);
		signInWithGoogle.click();
		driver.switchTo().defaultContent();
		String mainWindowHandle = driver.getWindowHandle();

		Set<String> windowHandles = driver.getWindowHandles();

		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(driver.getWindowHandle())) {
				driver.switchTo().window(windowHandle);
				System.out.println(windowHandle);
				Thread.sleep(3000);
				driver.manage().window().maximize();
				break;
			}
		}

		Actions actions = new Actions(driver);
		actions.moveToElement(emailField);
		actions.click();
		actions.sendKeys(emailField, emailID).perform();

		actions.sendKeys(Keys.RETURN).perform();
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", passwordField);
		waitForElementToClickable(passwordFieldBy);
		passwordField.sendKeys(password);
		actions.sendKeys(Keys.RETURN).perform();
		waitForElementToAppear(errorMessageBy);
		String error = errorMessage.getText();
		driver.switchTo().window(mainWindowHandle);
		return error;
	}

}
