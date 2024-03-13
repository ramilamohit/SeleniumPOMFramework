package com.Salesforce.automationscripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.Salesforce.base.*;
import com.Salesforce.pages.home.HomePage;
import com.Salesforce.pages.login.CheckyourEmailPage;
import com.Salesforce.pages.login.ForgotPasswordPage;
import com.Salesforce.pages.login.LoginPage;
import com.Salesforce.utilities.Constants;
import com.Salesforce.utilities.PropertiesUtility;

public class LoginAutomationScript extends BaseTest {
	
	protected static Logger SFLoginlog = LogManager.getLogger();
	
	@Test
	public static void TC1ErrormsgEmptyPassword()
	{

		String expError = "Please enter your password.";
		String loginTle = "Login | Salesforce";

		LoginPage loginpage = new LoginPage(driver);
		
		String loginpg = loginpage.getPageTitle();
		loginpage.checkText(loginTle, loginpg, "Login Page title");
		Assert.assertEquals(loginpg, loginTle, "Login Page title");

		String userName=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"username");
		loginpage.enterUsername(userName);
		
		String name = loginpage.getUsername();
		
		SoftAssert sftasrt = new SoftAssert();
		loginpage.checkText(userName, name, "Username text visible");
		sftasrt.assertEquals(name, userName, "Username visible");

		loginpage.enterPassword("");
		loginpage.checkText("", "", "Password txt is empty");
		sftasrt.assertEquals("", "", "Password txt is empty");

		loginpage.ClickLoginButton();
		
		String errormsg = loginpage.getErrormsg();
		loginpage.checkText(expError, errormsg, "Error Message Element");
		sftasrt.assertEquals(errormsg, expError, "Error Message Element");
	
		sftasrt.assertAll();

	}
	


	@Test
	
	public static void TC2HomepageDisplay() throws InterruptedException
	{
		String expLgTitle = "Login | Salesforce"; 
		String expTitle ="Home Page ~ Salesforce - Developer Edition";
		
		
		LoginPage loginpage = new LoginPage(driver);
		
		String lgntitle = loginpage.getPageTitle();
		loginpage.checkText(lgntitle, expLgTitle, "Login Page display");
		Assert.assertEquals(lgntitle, expLgTitle, "Login Page display");

		String userName=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"username");
		String passWord=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"password");
    		
		loginpage.enterUsername(userName);
		loginpage.enterPassword(passWord);

		driver = loginpage.ClickLoginButton();
		Thread.sleep(5000);
		HomePage homePage = new HomePage(driver);
		String hometitle = homePage.getPageTitle();
		homePage.checkText(hometitle, expTitle, "HomePage display");			
		Assert.assertEquals(hometitle, expTitle, "HomePage display");

	}

	@Test
	public static void 	TC3RememberCheckboxUsername() throws InterruptedException
	{
		String expLgTitle = "Login | Salesforce"; 
		String expTitle ="Home Page ~ Salesforce - Developer Edition";
		
	
		LoginPage loginpage = new LoginPage(driver);
		loginpage.waitimplicit(40);
		String lgntitle = loginpage.getPageTitle();
		loginpage.checkText(lgntitle, expLgTitle, "Login Page display");
		Assert.assertEquals(lgntitle, expLgTitle, "Login Page display");
			
		String userName=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"username");
		String passWord=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"password");

		loginpage.enterUsername(userName);
		loginpage.enterPassword(passWord);
		loginpage.checkRemembercheckbox();

		driver = loginpage.ClickLoginButton();
		
		HomePage homePage = new HomePage(driver);
		String hometitle = homePage.getPageTitle();
		homePage.checkText(hometitle, expTitle, "HomePage display");			
		Assert.assertEquals(hometitle, expTitle, "HomePage display");
		
		homePage.clickUsermenuNavigation();
		driver = homePage.clicklogout();
		
		
		String getUserId = loginpage.getUsername();
		
		loginpage.checkText(userName, getUserId, "User TxtBox is populated with valid User Id");
		Assert.assertEquals(getUserId, userName);
		
	}
	

	@Test
	
	public static void TC4AForgotPassword() 
	{
		String expcheckemail = "Check Your Email";
		
		String userName=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,"username");

		LoginPage loginpage = new LoginPage(driver);
		loginpage.enterUsername(userName);
		loginpage.enterPassword("");
		
		driver = loginpage.clickForgotPassword();
		
		ForgotPasswordPage forgotpwdpage = new ForgotPasswordPage(driver);
		forgotpwdpage.enterUsername(userName);
		driver = forgotpwdpage.clickContinue();
		
		CheckyourEmailPage chkemailpg = new CheckyourEmailPage(driver);
		String checkemail = chkemailpg.headerText();
		
		loginpage.checkText(expcheckemail, checkemail, "Check Email Header");
		Assert.assertEquals(checkemail, expcheckemail);

	}
	
	
	@Test
	public static void TC4BErrormsgWrongIdPassword() 
	{
		String expErrorMsg = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		
		LoginPage loginpage = new LoginPage(driver);
		loginpage.enterUsername("121");
		loginpage.enterPassword("22131");

		loginpage.checkRemembercheckbox();

		driver = loginpage.ClickLoginButton();
		
		String actualerrormsg = loginpage.getErrormsg();
		loginpage.checkText(actualerrormsg,expErrorMsg,"Error Message");
		Assert.assertEquals(actualerrormsg, expErrorMsg);

	}
	
	
	}
