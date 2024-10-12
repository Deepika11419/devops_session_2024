1.My Framework have different folder..
Different packages are there in src/main/java
	Abstract components--> Contain the common methods
	PageObjects-->Locators and methods for various pages
	Resources--> Contains ExtentRrporterNG,globalData.properties
src/test/java
	nagp.data--> Contains DataReader,log4j2,LoginData
	nagp.TestComponents--> BaseTest,Listeners,Retry
	nagp.Tests--> Different Tests
testSuits--> Contains Different testNG xml
ErrorVlidationsGrouping--Error Handelling Group which have error scenario
ListenerWholeSuite-- Run the Full Regression Suite
MultipleDataVlidayion--Run the test with multiple data
ParallelRunner--Run the tests in Parallel

/* i have made all the settings you just need to run any of the testNG xml to see the results. 
Enclosing the videos as well for better understanding */