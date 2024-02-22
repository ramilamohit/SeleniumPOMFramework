package com.Salesforce.base;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.Salesforce.utilities.Constants;
import com.Salesforce.utilities.ExtentReportsUtility;
import com.Salesforce.utilities.PropertiesUtility;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(com.Salesforce.utilities.SFListener.class)
public class BaseTest 
{
	protected static WebDriver driver =null;
	protected static Logger BaseTestlog = LogManager.getLogger();
	protected static ExtentReportsUtility extentReport=ExtentReportsUtility.getInstance();

	@BeforeMethod
	@Parameters("browsername")
	public void SFBeforeMethod(@Optional("chrome") String name)
	{
		BaseTestlog.info("---------------------SFBeforeMethod Executed----------------------------");

		launchBrowser(name);
		String url=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"url");
		goToURL(url);		
	}
	
	@AfterMethod
	public void SFAfterMethod()
	{
		BaseTestlog.info("---------------------SFAfterMethod Executed----------------------------");
		
		Set<String> handles = driver.getWindowHandles();	
		if(handles.size()==1)
		{
		closeBrowser();	
		}
		else if(handles.size()>1)
		{
			quitBrowsers();
		}
		
	}
	public static Set<String> getWindows()
	{
		Set<String> handles = driver.getWindowHandles();
		return handles;
	}
	
	
	public  void launchBrowser(String browserName)
	{
		switch(browserName.toLowerCase()) {
		case "chrome":
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
			
		case "firefox":
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
			
		case "edge":
			
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			break;
			
		case "opera":
			
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			driver.manage().window().maximize();
			break;
			
		default:
			BaseTestlog.info("Unsupported Browser: " + browserName);						
		}
	}
	
	public static void goToURL(String url)
	{
		driver.get(url);
		BaseTestlog.info(url + " is entered");	
		
	}
	
	
	public static void takescreenshot(String filepath)
	{
		TakesScreenshot screenCapture = (TakesScreenshot)driver;
		File src = screenCapture.getScreenshotAs(OutputType.FILE);
		File destination = new File(filepath);
		try {
			Files.copy(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseTestlog.info("Screenshot is not captured");
			extentReport.logTestInfo("Screenshot is not captured");

		}
		
	}
	
	public static void takescreenshot(WebElement element, String filepath)
	{
		File src = element.getScreenshotAs(OutputType.FILE);
		File destination = new File(filepath);
		try {
			Files.copy(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseTestlog.info("Screenshot is not captured");
			extentReport.logTestInfo("Screenshot is not captured");

		}
	}
	
	public static void closeBrowser() {
		driver.close();
		BaseTestlog.info("browser instance closed");
		extentReport.logTestInfo("browser instance closed");
		driver=null;
	}
	
	public static void quitBrowsers()
	{
		driver.quit();
		BaseTestlog.info("browser instances closed");
		extentReport.logTestInfo("browser instances closed");
		driver=null;
	}

	/*	public static void loginSalesforce() 
		{
			String expLgTitle = "Login | Salesforce"; 
			String expTitle ="Home Page ~ Salesforce - Developer Edition";
		    
			String userName=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"username");
			String passWord=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"password");

			String lgntitle = getTitle(driver);
			
			checkText(expLgTitle,lgntitle,"Login Page display");
				
			WebElement userid = driver.findElement(By.id("username"));
			enterText(userid,userName,"username");
			
			WebElement passwordtxt = driver.findElement(By.id("password"));
			enterText(passwordtxt,passWord,"password");

			WebElement btn = driver.findElement(By.id("Login"));
			clickElement(btn, "login");			
			
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.titleContains("Home"));
							
			String strtitle = getTitle(driver);
			BaseTestlog.info(strtitle);
			extentReport.logTestInfo(strtitle);
			checkText(expTitle,strtitle,"HomePage display");		

		}
		public static void logoutSalesforce() 
		{
			
			String expLgTitle = "Login | Salesforce"; 	
			
			WebElement usernavbtn = driver.findElement(By.xpath("//div[@id='userNavButton']"));
			if(usernavbtn.isDisplayed())
			{
				clickElement(usernavbtn, "User Navigation");
			}				
			
			WebElement logoutlink = driver.findElement(By.linkText("Logout"));
	

			if(logoutlink.isDisplayed())
			{
				clickElement(logoutlink, "Logout Link");
			
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.titleContains("Login"));
				
				String lgntitle = getTitle(driver);

				checkText(expLgTitle,lgntitle,"Login Page display");			
				BaseTestlog.info("Logout Test has passed");
				extentReport.logTestInfo("Logout Test has passed");

			}
		}
	
	*/	
		

}

