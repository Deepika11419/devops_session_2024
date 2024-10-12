package nagp.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import nagp.TestComponents.BaseTest;
import nagp.TestComponents.Retry;
import nagp.pageobjects.LogOutPage;
import nagp.pageobjects.LoginData;

public class LogOutTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(LogOutTest.class);

	/* Method to validate the logout functionality */
	@Test(retryAnalyzer = Retry.class)
	public void logout() {
		/* Click on the "Sign Out" button */

		try {
			LoginData loginData = loginpage.loginApplication("seleniumtesting840", "testing@11");
			LogOutPage logOutPage = loginData.getLogOutPage();

			logOutPage.clickMyAccountDropdown();
			logOutPage.clickSignOutButton();

			String text = loginpage.getAccountText();
			String expectedText = "Account";
			Assert.assertEquals(text, expectedText, "User is signed in");
		} catch (Exception e) {
			/* Log the error */
			logger.error("An error occurred during logout method: ", e);

		}

	}

}