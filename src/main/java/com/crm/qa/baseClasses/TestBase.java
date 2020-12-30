package com.crm.qa.baseClasses;

import Utils.CommonUtilities;
import Utils.TestUtil;
import Utils.WebEventListener;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.crm.qa.pages.LoginPage;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



public class TestBase extends FrameWorkDefaults{
    public static WebDriver driver;
    public static SoftAssert sftAssrt;
    public static Properties prop;
    public  static EventFiringWebDriver e_driver;
    public static CommonUtilities commonUtils;
    public static WebEventListener eventListener;
    public static WebDriverWait expWait;
    private  static final boolean printToConsole=true;
    public String browserName;
    private boolean remoteExecution;
    public DesiredCapabilities desiredCapabilities;
    public URL hubUrlChrome;
    public URL hubUrlFireFox;

    public TestBase() {
        super();
        logger = LogManager.getLogger(TestBase.class);
        commonUtils=new CommonUtilities();
        prop = commonUtils.loadPropertiesFileInConfigFolder("config.properties");
        sftAssrt= new SoftAssert();
//       if(driver==null){
//           initialization();
//       }

        }
        @BeforeSuite
        public void getProperties(){
            browserName= prop.getProperty("browser");
            remoteExecution=Boolean.parseBoolean(prop.getProperty("remoteExecution").trim());
        }


    @BeforeMethod
    public void initialization() throws MalformedURLException {

       if(remoteExecution) {
           if(browserName.equalsIgnoreCase("chrome")){
               hubUrlChrome= new URL("http://localhost:4444/wd/hub");
               desiredCapabilities=DesiredCapabilities.chrome();
               driver= new RemoteWebDriver(hubUrlChrome,desiredCapabilities);

           }else if(browserName.equalsIgnoreCase("firefox")){
               hubUrlFireFox=new URL("http://localhost:4444/wd/hub");
               desiredCapabilities=DesiredCapabilities.firefox();
               driver= new RemoteWebDriver(hubUrlFireFox,desiredCapabilities);
           }
       }else{
        if(browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Drivers/chromedriver");
            driver= new ChromeDriver();
        }else if(browserName.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/Drivers/chromedriver");
            driver= new FirefoxDriver();
        }else{
            System.out.println(" Incorrect Browser Name");
        }
       }



        long explicitWaitTimeout= prop.getProperty("explicitWaitTimeOut")==null ? explicitWait: Integer.parseInt(prop.getProperty("explicitWaitTimeOut"));
        expWait= new WebDriverWait(driver,explicitWaitTimeout);
        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        //______Other Browsers can be setup here________
        implecitWaitTimeOut=Integer.parseInt(prop.getProperty("implecitWaitTimeOut"));
        pageLoadTimeOut=Integer.parseInt(prop.getProperty("pageLoadTimeOut"));
        driver.manage().timeouts().implicitlyWait(implecitWaitTimeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(defaultUrl);



    }
    @AfterMethod
    public void closeBrowser(){
        printToConsole(".........Closing the Browser............");
        driver.close();
    }
    @AfterSuite
    public void tearDown(){
        printToConsole(".........Quiting the Browser............");
        driver.quit();
        reports.flush();
        // report.endTest(test);
        // report.flush();
        sftAssrt.assertAll();
    }

    public void printToConsole(String printTxt){
        if(printToConsole){
            System.out.println(printTxt);
        }
        logger.debug(printTxt);
    }

    public WebElement getElementSafely(By byObj){
        WebElement ele;
        try{
            printToConsole(".......Getting Element......" + byObj.toString());
            expWait.until(ExpectedConditions.presenceOfElementLocated(byObj));
            expWait.until(ExpectedConditions.visibilityOfElementLocated(byObj));
            ele=driver.findElement(byObj);
            return ele;

        }catch (Exception e){
            printToConsole("....... Element " + byObj.toString()+" cound not be located in DOM.......");
            e.printStackTrace();
            System.err.println(e);
            return null;
        }
    }
    public WebElement getElementSafely(WebElement element){
        WebElement ele;
        try{
            printToConsole(".......Getting Element......" + element.getText().toString());
            expWait.until(ExpectedConditions.visibilityOf(element));
            ele=element;
            return ele;

        }catch (Exception e){
            printToConsole("....... Element " + element.toString()+" cound not be located in DOM.......");
            e.printStackTrace();
            System.err.println(e);
            return null;
        }
    }
    public void waitForElementDisplayed(By ByObj)  {
        int counter=0;
        boolean elementDisplayed=false;
        while(counter < 3){
                if(getElementSafely(ByObj).isDisplayed()) {
                    elementDisplayed = true;
                    break;
                }
            counter++;
        }

    }
    public static void addToReport(String status, String description) {
        addToReport( status,  description,false);

    }
    public static void addToReport(String status, String description, boolean takeScreenshot)  {
    try{
    if(status.equalsIgnoreCase("Pass")){
        if (takeScreenshot) {
            String path=new TestUtil().takeScreenshotAndGetPath();
            //for v2
           // test.log(LogStatus.PASS, description+test.addScreenCapture(capture(driver))+ description);
            //for v3
            testReportLog.pass(description, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } else {
            //forv2
            // test.log(LogStatus.PASS, test.addScreenCapture(capture(driver))+ description);
           testReportLog.pass(description);
        }
    }
        if(status.equalsIgnoreCase("Fail")){
            if (takeScreenshot) {
                String path=new TestUtil().takeScreenshotAndGetPath();
                //test.log(LogStatus.FAIL, description);
                testReportLog.pass(description, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            } else {
                //test.log(LogStatus.FAIL, description);
                 testReportLog.pass(description);
            }
        }


    }catch(Exception e){
    e.printStackTrace();
    }

    }

    public static String capture(WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        String path=currentDir + "/test-results/screenshots/" + System.currentTimeMillis() + ".png";
        File destFile= new File(path);
        FileUtils.copyFile(scrFile, destFile);
        return destFile.getAbsolutePath();
    }

    public List<WebElement> getElementsSafely(By byObj){
        List<WebElement> elementList = null;
        try{
            printToConsole("....Getting elements: "+ byObj.toString()+"...............");
            expWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObj));
            expWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byObj));
            elementList=driver.findElements(byObj);
            return  elementList;
        }catch (Exception e){
            printToConsole("....Elements: "+ byObj.toString()+" were not found in DOM...............");
            e.printStackTrace();
            return elementList;
        }

    }
    public void clickSafely(By byObj){
        try{
            printToConsole("....Trying to click on element "+ byObj.toString());
            expWait.until(ExpectedConditions.elementToBeClickable(byObj));
            getElementSafely(byObj).click();
            //Report pass

        }catch(Exception e){
            //Report log as fail
            e.printStackTrace();
        }
    }
    public void clickSafely(WebElement element){
        try{
            expWait.until(ExpectedConditions.elementToBeClickable(element));
            getElementSafely(element).click();
            //Report pass

        }catch(Exception e){
            //Report log as fail
            e.printStackTrace();
        }
    }
    public String getTextSafely(By byObj){
       try{
           printToConsole("......Trying to get text from element: "+ byObj.toString());
           return getElementSafely(byObj).getText().trim();

       }catch (Exception e){
           printToConsole("...... Text from element: "+ byObj.toString()+" was NOT extracted successfully....");
           e.printStackTrace();
           return "";
       }
    }
    public String getTextSafely(WebElement element){
        try{
            printToConsole("......Trying to get text from element: "+ element.getText().toString());
            return getElementSafely(element).getText().trim();

        }catch (Exception e){
            printToConsole("...... Text from element: "+ element.toString()+" was NOT extracted successfully....");
            e.printStackTrace();
            return "";
        }
    }
    public void selectDropDownSafelyByValue(By byObj, String dropDownValue){
        WebElement element = getElementSafely(byObj);
        selectDropDownSafelyByValue(element,dropDownValue);
    }
    public void selectDropDownSafelyByValue(WebElement element, String dropDownValue){
        Select select= new Select(element);
        select.selectByValue(dropDownValue);
    }
    public void selectDropDownSafelyByIndex(By byObj, int index){
        WebElement element = getElementSafely(byObj);
        selectDropDownSafelyByIndex(element,index);
    }
    public void selectDropDownSafelyByIndex(WebElement element, int index){
        Select select= new Select(element);
        select.selectByIndex(index);
    }
    public void selectDropDownSafelyByVisibleText(WebElement element, String visibleText){
        Select select= new Select(element);
        select.selectByVisibleText(visibleText);
    }
    public void selectDropDownSafelyByVisibleText(By byObj, String visibleText){
        WebElement ele= getElementSafely(byObj);
        selectDropDownSafelyByVisibleText(ele,visibleText);
    }
    public void sendKeysSafely(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
    }
    public void sendKeysSafely(By byObj, String text){
        printToConsole("Trying to type :"+text+" in element:"+byObj.toString());
        WebElement ele = getElementSafely(byObj);
        sendKeysSafely(ele, text);
    }
    public boolean isElementDisplayed(By byObj){
        printToConsole("Checking in the element is visible-->"+byObj.toString());
        if(getElementsSafely(byObj).size()==0){
            printToConsole("........Element: "+byObj.toString()+" is not displayed........");
           return false;
        }else{
            printToConsole("........Element: "+byObj.toString()+" is not displayed........");
            return true;
        }
    }
    public boolean isElementDisplayed(WebElement element){

        if(!(getElementSafely(element).isDisplayed())){
            printToConsole("........Element: "+element.getText().toString()+" is not displayed........");
            return false;
        }else{
            printToConsole("........Element: "+element.getText().toString()+" is not displayed........");
            return true;
        }
    }
    public boolean isCheckBoxSelected(By byObj){
        printToConsole("checking if the checkbox is selected:"+byObj.toString());
       return getElementSafely(byObj).isSelected();
    }
    public boolean isCheckBoxSelected(WebElement element){
        printToConsole("checking if the checkbox is selected:"+element.toString());
        return getElementSafely(element).isSelected();
    }
    public boolean isElementEnabled(WebElement element){
        return getElementSafely(element).isEnabled();
    }
    public boolean isElementEnabled(By byObj){
        printToConsole("checking if the element is enabled-->"+byObj.toString());
        return getElementSafely(byObj).isEnabled();
    }
    public void selectCheckbox(By byObj){
        if(!isCheckBoxSelected(byObj)){
          clickSafely(byObj);
        }
    }
    public void unSelectCheckBox(By byObj){
        if(isCheckBoxSelected(byObj)){
            clickSafely(byObj);
        }
    }
    public boolean isRadioButtonSelected(By byObj){
        return getElementSafely(byObj).isSelected();
    }
    public void selectRadioButton(By byObj){
        if(!isRadioButtonSelected(byObj)){
            clickSafely(byObj);
        }
    }
    public void scrollToElement(By byObj){
        JavascriptExecutor jse= (JavascriptExecutor) driver;
        WebElement element=getElementSafely(byObj);
        jse.executeScript("arguments[0].scrollIntoView();",element);
    }
    public void movetoElemmentAndHold(By byObj){
        Actions actions =new Actions(driver);
        Action moveToEle= actions.moveToElement(getElementSafely(byObj)).clickAndHold().build();
        actions.perform();

    }
    public void moveToElementAndClick(By byObj){
        Actions actions= new Actions(driver);
        Action moveToElementAndClick= actions.moveToElement(getElementSafely(byObj)).click().build();
        actions.perform();
    }


}
