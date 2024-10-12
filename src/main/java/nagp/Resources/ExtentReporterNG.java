package nagp.Resources;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	/* Method to create Extent Report */
	public static ExtentReports getReportObject() {
		/* Directory path */
		String directoryPath = System.getProperty("user.dir") + File.separator + "CurrentTestResults";
		/* File path */
		String filePath = directoryPath + File.separator + "index.html";

		/* Ensure that the directory exists, create it if it doesn't */
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs(); /* Create directories */
		}

		System.out.println("Extent Report Path: " + filePath); /* Print path */

		ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
		reporter.config().setReportName("Red Bus Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Deepika Kaushik");
		return extent;

	}
}
