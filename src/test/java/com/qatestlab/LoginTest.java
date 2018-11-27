package com.qatestlab;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Random;

public class LoginTest {
    WebDriver driver;
    String browser;
    Random random = new Random();

    @Parameters("browser")
    @BeforeTest
    public void testSetUp(String browser) {
        driver = DriverManager.getConfiguredDriver(browser);
        this.browser = browser;
    }

    @Test
    public void open() {
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        SoftAssert softAssertion = new SoftAssert();
        // Check site-version:
        if (browser.equals("mobile")) {
            softAssertion.assertTrue(driver.findElement(By.xpath("//*[@id=\"_mobile_logo\"]")).isDisplayed());
        } else {
            softAssertion.assertTrue(driver.findElement(By.xpath("//*[@id=\"carousel\"]")).isDisplayed());
        }
        softAssertion.assertAll();
    }

    @Test(dependsOnMethods = {"open"})
    public void order() {
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        // Click "Все товары"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"content\"]/section/a")));
        driver.findElement(By.xpath("//*[@id=\"content\"]/section/a")).click();
        // Select random product:
        List<WebElement> productList = driver.findElements(By.className("h3 product-title"));
        WebElement randomProduct = productList.get(random.nextInt(productList.size()));
        randomProduct.click();
        // Add product to the cart:
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div[1]/div[2]/button")).click();
        // Wait for "Перейти к оформлению":
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/a")));
        driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/a")).click();

    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
