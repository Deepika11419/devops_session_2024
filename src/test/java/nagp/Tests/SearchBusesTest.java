package nagp.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import nagp.TestComponents.BaseTest;
import nagp.TestComponents.Retry;
import nagp.pageobjects.LoginData;
import nagp.pageobjects.RedBusLandingPage;
import nagp.pageobjects.SearchedBusesLandingPage;

public class SearchBusesTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(SearchBusesTest.class);

	/* Method to Search the Buses */
	@Test(retryAnalyzer = Retry.class)
	public void searchBuses() {
		try {

			/* Login into Application */
			LoginData loginData = loginpage.loginApplication("seleniumtesting840", "testing@11");
			RedBusLandingPage redbuslandingpage = loginData.getRedBusLandingPage();
			String text = redbuslandingpage.getMyAccountText();
			String expectedText = "My Account";
			Assert.assertEquals(text, expectedText, "User is not signed in");
			/* Search for the Buses */
			redbuslandingpage.selectSourceDestination();
			redbuslandingpage.datePicker("May 2024", "4");
			SearchedBusesLandingPage searchedbuseslandingpage = redbuslandingpage.searchBus();
			String pageTitleofBus = searchedbuseslandingpage.validateDelhiToChandigarhpage();
			System.out.println(pageTitleofBus);
			Assert.assertTrue(pageTitleofBus.contains("Delhi to Chandigarh"), "Results Mismatch");

		} catch (InterruptedException e) {
			logger.error("An error occurred during search Buses: ", e);
		}

	}

}
