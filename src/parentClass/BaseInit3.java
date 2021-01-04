package parentClass;

//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
//import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseInit3 {
	
	public static Properties sitedata = null;
	public static Properties storage = null;
	public static WebDriver driver = null;
	public static XSSFWorkbook admin= null;
	public static XSSFWorkbook suite= null;
	
	public static ExtentHtmlReporter htmlReports;
	public static ExtentReports report;
	public static ExtentTest test;
		
	//@Parameters("myBrowser")
	public static void startUP() throws IOException
	{
		System.out.println("Successfully entered into startUp method");
		//System.out.println("myBroser parameter value is ==> "+myBrowser);
		FileInputStream fi = null;
		
		//Started loading of SiteData.properties file...
		sitedata = new Properties();
		try {
			fi = new FileInputStream(System.getProperty("user.dir")+"//src//myProperties//SiteData.properties");
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try {
			sitedata.load(fi);	
			System.out.println("sitedata property file has been successfully loaded");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		//Started loading of ObjectStorage.properties file...
		storage = new Properties();
		try {
			fi = new FileInputStream(System.getProperty("user.dir")+"//src//myProperties//ObjectStorage.properties");
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try {
			storage.load(fi);
			System.out.println("storagedata property file has been successfully loaded");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		//Started Initialization process of WebDriver instance for specific browser...
		if(sitedata.getProperty("browser").equalsIgnoreCase("firefox")){
			try {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//Drivers//geckodriver.exe");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			driver = new FirefoxDriver();
			System.out.println("Firefox browser driver has been initialized");
			
		}else if(sitedata.getProperty("browser").equalsIgnoreCase("chrome")){
			try {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
				System.out.println("************************chrome driver path is ==>"+System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			/*
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("incognito");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			*/
			driver = new ChromeDriver();
			System.out.println("chrome browser driver has been initialized");
			
		}else if(sitedata.getProperty("browser").equalsIgnoreCase("ie")){
			try {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//Drivers//IEDriverServer.exe");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			driver = new InternetExplorerDriver();
			System.out.println("IE browser driver has been initialized");
		}
			
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		try {
			//user = new XSSFWorkbook(System.getProperty("user.dir")+"//src//unicodeTech//MyFiles//UserPanel.xlsx");
			FileInputStream AdminExcelFile = new FileInputStream(System.getProperty("user.dir")+"//src//myFiles//AdminPanel.xlsx");
			admin = new XSSFWorkbook(AdminExcelFile);
			//regression = new XSSFWorkbook(System.getProperty("user.dir")+"//src//unicodeTech//MyFiles//RegressionScenarios.xlsx");
			FileInputStream TestSuiteExcelFile = new FileInputStream(System.getProperty("user.dir")+"//src//myFiles//TestSuite.xlsx");
			suite = new XSSFWorkbook(TestSuiteExcelFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Below code is for extent report
		try {
			htmlReports = new ExtentHtmlReporter(System.getProperty("user.dir")+"//Flipkart_Demo_SmokeSuite_Execution_Result.html");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		report = new ExtentReports();
		report.attachReporter(htmlReports);
		htmlReports.config().setReportName("Flikart Website Automation Execution Report");
		htmlReports.config().setTheme(Theme.STANDARD);
		htmlReports.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReports.config().setDocumentTitle("Flikart Website Automation Execution Report");
	}
	
	public static WebElement verifyXpath(String xpath){
		try{
			return driver.findElement(By.xpath(storage.getProperty(xpath)));
		}catch(Throwable t){
			System.out.println("Xpath not found => "+xpath);
			return null;
		}
	}
	
	public static void setValue(String locatorName, String value){
		try{
			driver.findElement(By.xpath(storage.getProperty(locatorName))).sendKeys(value);
		}catch(Throwable t){
			System.out.println("Xpath not found => "+locatorName);
		}
	}
}
