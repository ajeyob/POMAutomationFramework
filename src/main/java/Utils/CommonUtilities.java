package Utils;

import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonUtilities {
    public static WebDriver driver;
    Map<String, String> propertiesMap;
    public  Properties properties;
    public CommonUtilities(){
        loadPropertiesFileInConfigFolder("config.properties");
    }
    public CommonUtilities(WebDriver driver){
        this.driver=driver;
        loadPropertiesFileInConfigFolder("config.properties");
    }

   public Properties loadPropertiesFileInConfigFolder(String fileNameInConfigFolder) {
       String path=System.getProperty("user.dir")+ "/src/config/"+fileNameInConfigFolder;
       properties=loadPropertiesFile(path);
       return properties;
   }

    public Properties loadPropertiesFile(String path) {
       try {
           properties = new Properties();
           FileInputStream ip = new FileInputStream(path);
           properties.load(ip);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

        return properties;
    }



}
