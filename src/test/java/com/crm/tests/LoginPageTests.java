package com.crm.tests;

import com.crm.qa.baseClasses.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LandingPage;
import com.crm.qa.pages.LoginPage;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.*;

public class LoginPageTests  extends TestBase {
    LoginPage loginPage;
    HomePage homePage;
    LandingPage   landingPage;

    public LoginPageTests(){
        super();
        logger= LogManager.getLogger(LoginPageTests.class);
    }

    @BeforeTest
    public void initialSetup(){
        loginPage = new LoginPage();
        homePage= new HomePage();
        landingPage=new LandingPage();
        testReportLog = reports.createTest("Login Page  Tests", "These tests will check the login functionality");

    }

    @Test
    public void checkLoginPage(){
        loginPage.checkCRMLoginPage();

    }

    @Test
    public void checkLoginSuccess(){
        loginPage.checkCRMLoginPage();
        loginPage.clickOnLoginButton();
        homePage.enterCredentialsAndSubmit();
        landingPage.checkLandingPage();
    }



}
