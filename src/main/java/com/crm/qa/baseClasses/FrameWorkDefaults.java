package com.crm.qa.baseClasses;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FrameWorkDefaults {
    public long implecitWaitTimeOut=20;
    public  long pageLoadTimeOut=20;
    public String defaultUrl="https://www.google.com";
    public static long explicitWait=20;

    public static ExtentTest testReportLog;
    public static ExtentReports reports;
    public static ExtentHtmlReporter htmlReporter;

    public static ExtentReports report ;
    public static ExtentTest test;
    public static  Logger logger;


    @BeforeSuite
    public static void startTest()
    {

        //FOr Version2
      // report = new ExtentReports(System.getProperty("user.dir")+"/test-results/ExtentReportResults-v2-"+System.currentTimeMillis()+".html", true);

//For Version 3
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss");

        String formattedDate = myDateObj.format(myFormatObj);
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-results/ExtentReportResults-v3-"+formattedDate+".html");
        //htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-results/ExtentReportResults-v3-"+System.currentTimeMillis()+".html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

    }

}
