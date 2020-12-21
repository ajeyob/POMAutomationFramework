package com.crm.qa.pages;

import Utils.CommonUtilities;
import com.crm.qa.baseClasses.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    public LoginPage(){
        super();
        PageFactory.initElements(driver,this);

    }


    public void checkCRMLoginPage(){
        driver.get(prop.getProperty("crmUrl"));
        String title="#1 Free CRM customer relationship management software cloud";
        sftAssrt.assertEquals(driver.getTitle(),title);
        if(driver.getTitle().equals(title)) {
            //Title is correct report
            printToConsole("The expected title matched with the actual title");
        }
        if(isElementDisplayed(LoginPageObjects.crmLogo.byObj)){
            printToConsole("Crm Logo is displayed correctly on the Login Page");
        }


    }

    public void clickOnLoginButton(){
        clickSafely(LoginPageObjects.loginBtn.byObj);

    }


    private enum LoginPageObjects{

        crmLogo(By.xpath("//div[@class='rd-navbar-brand']/a[@title='free crm home']")),
        loginBtn(By.xpath("//span[text()='Log In']"));

        public By byObj;
        LoginPageObjects(By by){
            this.byObj=by;
        }
        public By getBy(){
            return this.byObj;
        }

    }
}
