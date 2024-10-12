package nagp.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import nagp.TestComponents.BaseTest;
import nagp.TestComponents.Retry;
import nagp.pageobjects.LoginData;
import nagp.pageobjects.RedBusLandingPage;

public class LoginTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(LoginTest.class);

	/* Method to login into the application with multiple data */
	@Test(dataProvider = "getData", groups = { "MultipleData" }, retryAnalyzer = Retry.class)
	public void loginApplication(HashMap<String, String> input) throws Throwable {

		try {
			/* Login into Application */
			LoginData loginData = loginpage.loginApplication(input.get("email"), input.get("password"));
			RedBusLandingPage redbuslandingpage = loginData.getRedBusLandingPage();
			String text = redbuslandingpage.getMyAccountText();
			String expectedText = "My Account";
			Assert.assertEquals(text, expectedText, "User is not signed in");
		} catch (AssertionError | InterruptedException e) {
			logger.error("Assertion error occurred during loginApplication method: ", e);
			throw e;

		}
	}


	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "/src/test/java/nagp/data/LoginData.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
}
