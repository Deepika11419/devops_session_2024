package nagp.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import nagp.pageobjects.LoginPage;

public class BaseTest {

	String fileName;

	public WebDriver driver;

	public LoginPage loginpage;

	/* Method to initialise the webdriver */
	public WebDriver initializeDriver() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "src"
				+ File.separator + "main" + File.separator + "java" + File.separator + "nagp" + File.separator
				+ "Resources" + File.separator + "GlobalData.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		int globalWaitSeconds = Integer.parseInt(prop.getProperty("globalWaitSeconds"));

		/* Chrome Browser */
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			/* Firefox Browser */
		} else if (browserName.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("permissions.default.desktop-notification", 1);
			options.setProfile(profile);
			// WebDriverManager.firefoxdriver().setup();
			options.addArguments("--disable-notifications");
			driver = new FirefoxDriver(options);

			/* Edge Browser */
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--disable-notifications");
			driver = new EdgeDriver(options);
		}

		driver.manage().window().setSize(new Dimension(1440, 900)); /* full screen */
		driver.manage().window().maximize();
		/* Set page load timeout */
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(globalWaitSeconds));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(globalWaitSeconds));

		String testUrl = prop.getProperty("testUrl");
		driver.get(testUrl);

		return driver;

	}

	/* Method to get Json Data to Map */
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		/* String to HashMap Jackson Databind */
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>()

				{
				});
		return data;

	}

	/* Method to get screenshot */
	public String getScreenshot(String testCaseName, String errorDescription, WebDriver driver) throws IOException {

		/* Get the current window */
		String mainWindowHandle = driver.getWindowHandle();

		/* Get all window handles */
		Set<String> allWindowHandles = driver.getWindowHandles();

		/* Switch to the last window handle (usually the child window) */
		for (String windowHandle : allWindowHandles) {
			driver.switchTo().window(windowHandle);
		}

		/* Capture screenshot of the currently focused window */
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		/* Append error description to the file name */
		fileName = testCaseName + "_" + errorDescription.replaceAll("\\s+", "_") + ".png";
		System.out.println(fileName + " in screenshot method");
		String filePath = System.getProperty("user.dir") + File.separator + "CurrentTestResults" + File.separator
				+ fileName;
		System.out.println(filePath + " in screenshot method");
		File destination = new File(filePath);
		FileUtils.copyFile(source, destination);

		/* Switch back to the main window */
		driver.switchTo().window(mainWindowHandle);

		return filePath;

	}

	/* Method to Launch Application */
	@BeforeMethod(alwaysRun = true)
	public LoginPage launchApplication() throws IOException {
		driver = initializeDriver();
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);
		//Assert.assertEquals(pageTitle, "Bus Ticket Booking Online made Easy, Secure with Top Bus Operators - redBus");
		loginpage = new LoginPage(driver);
		return loginpage;
	}

	/* Tear Down Method */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		/* Close the WebDriver instance after each test method */
		if (driver != null) {
			driver.quit();
		}
	}

	/* Method to Extract Brief Error description */
	public String extractBriefErrorDescription(String errorMessage) {
		/*
		 * Implement logic to extract a brief error description from the error message
		 */
		if (errorMessage != null && !errorMessage.isEmpty()) {
			/* Split the error message by space */
			String[] words = errorMessage.trim().split("\\s+");
			StringBuilder briefDescription = new StringBuilder();
			/* Concatenate the first few words */
			int wordLimit = Math.min(2, words.length); /* Extract first 5 words or less */
			for (int i = 0; i < wordLimit; i++) {
				briefDescription.append(words[i]).append(" ");
			}
			return briefDescription.toString().trim();
		}
		return "Error occurred"; /* Return a default message if the error message is empty */
	}

	/* Method to move the results to Current Test Results Folder */
	public void moveResultsToCurrentTestResultsFolder(String copiedFilePath) {
		/* Get the current date and time */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateAndTime = sdf.format(new Date());

		/* Directory paths */
		String currentTestResultsDirectoryPath = System.getProperty("user.dir") + File.separator + "CurrentTestResults";
		String archivedTestResultsDirectoryPath = System.getProperty("user.dir") + File.separator
				+ "ArchivedTestResults";

		/* Move previous test results to archived folder */
		movePreviousTestResultsToArchivedFolder(currentTestResultsDirectoryPath, archivedTestResultsDirectoryPath);

		/* Create a new folder for the current test results */
		String currentTestResultsFolderPath = currentTestResultsDirectoryPath + File.separator + currentDateAndTime;
		new File(currentTestResultsFolderPath).mkdirs();

		/* Move the extent report to the current test results folder */
		String extentReportSourcePath = currentTestResultsDirectoryPath + File.separator + "index.html";
		System.out.println(extentReportSourcePath);
		String extentReportDestPath = currentTestResultsFolderPath + File.separator + "index.html";
		System.out.println(extentReportDestPath);
		File extentReportSourceFile = new File(extentReportSourcePath);
		if (extentReportSourceFile.exists()) {
			try {
				Files.move(extentReportSourceFile.toPath(), Paths.get(extentReportDestPath),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Extent report file not found: " + extentReportSourcePath);
		}

		/* Move the screenshot to the current test results folder */
		String screenshotSourcePath = currentTestResultsDirectoryPath + File.separator + fileName;
		System.out.println(copiedFilePath);
		String screenshotDestPath = currentTestResultsFolderPath + File.separator + fileName;
		System.out.println(screenshotDestPath);
		File screenshotSourceFile = new File(copiedFilePath);
		if (screenshotSourceFile.exists()) {
			try {
				Files.copy(screenshotSourceFile.toPath(), Paths.get(screenshotDestPath),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Screenshot file not found: " + screenshotSourcePath);
		}
	}

	/* Clean "Current test results" folder */
	public void cleanCurrentTestResultsFolder() {
		String currentTestResultsDirectoryPath = System.getProperty("user.dir") + File.separator + "CurrentTestResults";
		File currentTestResultsDirectory = new File(currentTestResultsDirectoryPath);
		File[] files = currentTestResultsDirectory.listFiles();
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
	}

	/* Method to move previous test results to Archived folder */
	private void movePreviousTestResultsToArchivedFolder(String currentTestResultsDirectoryPath,
			String archivedTestResultsDirectoryPath) {
		File currentTestResultsDirectory = new File(currentTestResultsDirectoryPath);
		File[] previousTestResultFolders = currentTestResultsDirectory.listFiles(File::isDirectory);
		if (previousTestResultFolders != null) {
			for (File folder : previousTestResultFolders) {
				String folderName = folder.getName();
				String archivedFolderPath = archivedTestResultsDirectoryPath + File.separator + folderName;
				folder.renameTo(new File(archivedFolderPath));
			}
		}
	}

	/* Copy screenshot to NewLocation */
	public String copyScreenshotToNewLocation(String originalFilePath) throws IOException {
		/* Create a new file path for the copied screenshot */
		Path sourcePath = Paths.get(originalFilePath);
		String userDir = System.getProperty("user.dir");
		String customPath = userDir + File.separator + "screenshot_folder";
		Path destinationDir = Paths.get(customPath);
		try {
			if (!Files.exists(destinationDir)) {
				Files.createDirectories(destinationDir);
				System.out.println("Directory created at: " + destinationDir);
			} else {
				System.out.println("Directory already exists at: " + destinationDir);
			}
		} catch (IOException e) {
			System.err.println("Error creating directory: " + e.getMessage());
			e.printStackTrace();
		}
		Path destinationPath = destinationDir.resolve(sourcePath.getFileName().toString());

		/* Copy the screenshot file to the new location */
		Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

		/* Return the new file path */
		return destinationPath.toString();
	}

}
