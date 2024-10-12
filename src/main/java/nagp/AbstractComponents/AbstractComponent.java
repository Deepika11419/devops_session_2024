package nagp.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {

	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
	}

	/* Method for wait for an element */
	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	/* Method to wait for element to be clickable */

	public void waitForElementToClickable(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}

	/* Method to move to element and click */

	public void moveToElementAndClick(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();

	}

	/* Method to move to element and click at position */

	public void moveToElementAndClickAtPosition(WebElement element, int x, int y) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element, x, y).click().perform();
	}
}
