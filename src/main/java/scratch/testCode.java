package scratch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class testCode {
    public  static WebDriver driver;

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Drivers/chromedriver");
        driver= new ChromeDriver();
       // implecitWaitTimeOut=Integer.parseInt(prop.getProperty("implecitWaitTimeOut"));
      //  pageLoadTimeOut=Integer.parseInt(prop.getProperty("pageLoadTimeOut"));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://motly-fool.com");
    }
}
