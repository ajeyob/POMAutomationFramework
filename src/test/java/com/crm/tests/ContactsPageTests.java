package com.crm.tests;

import Utils.TestUtil;
import com.crm.qa.baseClasses.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LandingPage;
import com.crm.qa.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ContactsPageTests  extends TestBase {
    LoginPage loginPage;
    HomePage homePage;
    LandingPage landingPage;
    ContactsPage contactsPage;

    public ContactsPageTests(){
        super();
        logger= LogManager.getLogger(ContactsPageTests.class);
    }

    @BeforeTest
    public void initialSetup(){
        loginPage = new LoginPage();
        homePage= new HomePage();
        landingPage=new LandingPage();
        contactsPage=new ContactsPage();
        //v3 extent report
         testReportLog = reports.createTest("ContactsPage Tests", "These tests will test adding a contact functionality");
        //v2 extent report
        // test = report.startTest("Contacts Page tests");

    }

    @Test(dataProvider = "getTestData")
    public void createNewContact(String fname,String lname, String category,String status,String description ){

        loginPage.checkCRMLoginPage();
        loginPage.clickOnLoginButton();
        homePage.enterCredentialsAndSubmit();
        landingPage.checkLandingPage();
        contactsPage.createNewContact(fname,lname,category,status,description);


    }

    @DataProvider
     public Object[][] getTestData(){
        Object[][] data=TestUtil.getTestData("CrmTestData.xlsx","contacts");
       // List<ArrayList<String>> dataAsList=new ArrayList<ArrayList<String>>();

        return data;

    }

    @AfterMethod
    public void closeBrowser(){
        driver.close();
    }

}
