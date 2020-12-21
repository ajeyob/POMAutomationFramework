package com.crm.qa.pages;

import com.crm.qa.baseClasses.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends TestBase {
    public HomePage(){

        PageFactory.initElements(driver,this);

    }

    public void enterCredentialsAndSubmit(){
        sendKeysSafely(HomePageObjects.userName.byObj,prop.getProperty("crmLogin"));
        sendKeysSafely(HomePageObjects.password.byObj,prop.getProperty("crmPassword"));
        clickSafely(HomePageObjects.loginButton.byObj);


    }


    private enum HomePageObjects{
        userName(By.xpath("//input[@type='text' and @name='email']")),
        password(By.xpath("//input[@type='password' and @name='password']")),
        loginButton(By.xpath("//div[text()='Login']"));

        public By byObj;
        HomePageObjects(By by){
            this.byObj=by;
        }
        public By getBy(){
            return this.byObj;
        }

    }
}
