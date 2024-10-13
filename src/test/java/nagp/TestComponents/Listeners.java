package nagp.TestComponents;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import nagp.Resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	String briefErrorDescription;
	String copiedFilePath;
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); /* Thread safe */
	private ThreadLocal<String> currentTestMethodName = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test suite started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test suite finished: " + context.getName());
		extent.flush(); /* Flush the Extent Reports object to write the information to the report */
		//moveResultsToCurrentTestResultsFolder( copiedFilePath); /* call the method to move the results to Current Test Results Folder */
		//cleanCurrentTestResultsFolder();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test method started: " + result.getMethod().getMethodName());
		currentTestMethodName.set(result.getMethod().getMethodName()); /* Set the current test method name */
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); // Set the extent test instance
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test method passed: " + result.getMethod().getMethodName());
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test method failed: " + result.getMethod().getMethodName());
		extentTest.get().fail(result.getThrowable());

		try {
			/* Retrieve the WebDriver instance from the test class using reflection */
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String filePath = null;

		try {
			/* Capture screenshot and add it to the Extent Report */
			String errorMessage = result.getThrowable().getMessage();
			System.out.println(errorMessage + "in listener");
			briefErrorDescription = extractBriefErrorDescription(errorMessage);
			System.out.println(briefErrorDescription + "in listener");

			filePath = getScreenshot(result.getMethod().getMethodName(), briefErrorDescription, driver);

			copiedFilePath = copyScreenshotToNewLocation(filePath);

			/* Print the copied file path to debug */
			System.out.println("Copied screenshot file path: " + copiedFilePath);
			/* Print the filePath to debug */
			System.out.println("Screenshot file path: " + filePath);
			extentTest.get().addScreenCaptureFromPath(copiedFilePath, result.getMethod().getMethodName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test method skipped: " + result.getMethod().getMethodName());
	}
}
