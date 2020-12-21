package com.crm.qa.pages;

import com.crm.qa.baseClasses.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class LandingPage  extends TestBase {
    public LandingPage(){

        PageFactory.initElements(driver,this);

    }

    public void checkLandingPage(){
       String landingPageTitle= driver.getTitle();
       sftAssrt.assertEquals(landingPageTitle,"Cogmento CRM");
       if(isElementDisplayed(LandingPageObjects.landingPageHomeIcon.byObj)){
           printToConsole("Home Icon is displayed successfully");
       }else{
           printToConsole("Home Icon is NOT displayed successfully");

       }

    }


    private enum LandingPageObjects{

        landingPageLogo(By.xpath("//div[@id='top-header-menu']/div[contains(@style,'ABAs')]")),
        landingPageHomeIcon(By.xpath("//a/i[@class='home icon']"));

        public By byObj;
        LandingPageObjects(By by){
            this.byObj=by;
        }
        public By getBy(){
            return this.byObj;
        }

    }
}
