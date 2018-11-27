package com.qatestlab;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    public static WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "ie":
            case "internet explorer" :
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "mobile":
                WebDriverManager.chromedriver().setup();
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Galaxy Note 3");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(chromeOptions);
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    public static EventFiringWebDriver getConfiguredDriver(String browser) {
        EventFiringWebDriver driver = new EventFiringWebDriver(getDriver(browser));
        driver.register(new EventHandler());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        Reporter.setEscapeHtml(false);

        return driver;
    }
}
