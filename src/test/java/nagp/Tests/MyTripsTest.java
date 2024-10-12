package nagp.Tests;

import org.testng.annotations.Test;
import nagp.TestComponents.BaseTest;
import nagp.TestComponents.Retry;
import nagp.pageobjects.LoginData;
import nagp.pageobjects.RedBusLandingPage;
import nagp.pageobjects.MyTrips;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class MyTripsTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(MyTripsTest.class);

	/* Method to validate the mytrips page */
	@Test(retryAnalyzer = Retry.class)
	public void myTripsTest() throws Throwable {

		try {
			/* Perform login */
			LoginData loginData = loginpage.loginApplication("deepika91597@gmail.com", "Nav#ya@12345");

			/* Get required page objects from LoginData */
			RedBusLandingPage redbuslandingpage = loginData.getRedBusLandingPage();
			MyTrips myTrips = loginData.getMyTrips();
			String text = redbuslandingpage.getMyAccountText();
			String expectedText = "My Account";
			Assert.assertEquals(text, expectedText, "User is not signed in");

			/* Validation of MyTrips Page */
			myTrips.clickMyAccountDropdownIcon();
			myTrips.clickMyTrips();
			myTrips.switchToNewTab();
			List<String> errorMessages = myTrips.validateErrorMessages();
			if (errorMessages.isEmpty()) {
				/* If there are no error messages, fail the test */
				Assert.fail("Validation failed. No error messages found.");
			} else {
				/* If there are error messages, assert that they match the expected messages */
				Assert.assertEquals(errorMessages.size(), 2, "Number of error messages is not as expected.");
				Assert.assertEquals(errorMessages.get(0), "No upcoming trips.", "First error message is incorrect.");
				Assert.assertEquals(errorMessages.get(1), "Plan one now!", "Second error message is incorrect.");
			}

		} catch (Exception e) {
			logger.error("An error occurred during Mytrips validation: ", e);

		}
	}
}
