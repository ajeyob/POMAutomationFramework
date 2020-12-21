package com.crm.qa.pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.crm.qa.baseClasses.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class ContactsPage extends TestBase {
    public ContactsPage() {
        PageFactory.initElements(driver, this);

    }

    public void createNewContact(String fname,String lname, String category,String status,String description)  {
        clickSafely(ContactsPageObjects.contactsIcon.byObj);
        moveToElementAndClick(ContactsPageObjects.contactsIcon.byObj);
        clickSafely(ContactsPageObjects.newContactButton.byObj);
        //movetoElemment(ContactsPageObjects.contactsPlusButton.byObj);
        //moveToElementAndClick(ContactsPageObjects.contactsPlusButton.byObj);
        sendKeysSafely(ContactsPageObjects.contactFName.byObj,fname);
        sendKeysSafely(ContactsPageObjects.contactLName.byObj,lname);
        clickSafely(ContactsPageObjects.category.byObj);
        if(category.equalsIgnoreCase("Customer")){
            clickSafely(ContactsPageObjects.category_Customer.byObj);
        }
        clickSafely(ContactsPageObjects.status.byObj);
        if(status.equalsIgnoreCase("new")){
            clickSafely(ContactsPageObjects.status_New.byObj);
        }
        sendKeysSafely(ContactsPageObjects.description.byObj,description);
        clickSafely(ContactsPageObjects.contactsSaveButton.byObj);
        waitForElementDisplayed(ContactsPageObjects.bigRedIcon.byObj);
        String Name=getTextSafely(ContactsPageObjects.contactsDetailsNameHeader.byObj);

        printToConsole("Name of the new contact is "+Name);
        if(Name.contains(fname)){
            addToReport("Pass","FirstName displayed correctly in the added Contact");
        }else{
            addToReport("Fail","FirstName is not displayed correctly in the added Contact",true);
        }
        if(Name.contains(lname)){
            addToReport("Pass","LastName displayed correctly in the added Contact",true);
        }else{
            addToReport("Fail","LastName is not displayed correctly in the added Contact",true);
        }

    }


    private enum ContactsPageObjects {

        contactsIcon(By.xpath("//a/i[@class='users icon']")),
        newContactButton(By.xpath("//button[text()='New']")),
        contactsPlusButton(By.xpath("//a/i[@class='users icon']/../following-sibling::button")),
        contactFName(By.xpath("//input[@name='first_name']")),
        contactLName(By.xpath("//input[@name='last_name']")),
        category(By.xpath("//div[@name='category' and @role='listbox']/i")),
        category_Lead(By.xpath("//div[@name='category' and @role='option']/span[text()='Lead']")),
        category_Customer(By.xpath("//div[@name='category' and @role='option']/span[text()='Customer']")),
        category_Contact(By.xpath("//div[@name='category' and @role='option']/span[text()='Contact']")),
        category_Affiliate(By.xpath("//div[@name='category' and @role='option']/span[text()='Affiliate']")),
        status(By.xpath("//div[@name='status' and @role='listbox']")),
        status_New(By.xpath("//div[@name='status' and @role='option']/span[.='New']")),
        status_Active(By.xpath("//div[@name='status' and @role='option']/span[.='Active']")),
        status_Inactive(By.xpath("//div[@name='status' and @role='option']/span[.='Inactive']")),
        status_On_Hold(By.xpath("//div[@name='status' and @role='option']/span[.='On Hold']")),
        status_On_Terminated(By.xpath("//div[@name='status' and @role='option']/span[.='Terminated']")),
        status_Hot(By.xpath("//div[@name='status' and @role='option']/span[.='Hot']")),
        description(By.xpath("//textarea[@name='description']")),
        contactsSaveButton(By.xpath("//button[text()='Save']")),
        contactsDetailsNameHeader(By.xpath("//*[@id='dashboard-toolbar']/div[contains(@class,'ui header item')]")),
        bigRedIcon(By.xpath("//i[@class='large user red icon']"));

        public By byObj;

        ContactsPageObjects(By by) {
            this.byObj = by;
        }

        public By getBy() {
            return this.byObj;
        }

    }
}
