package com.anhtester.crm.base;

import com.anhtester.crm.driver.DriverFactory;
import com.anhtester.crm.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for every TestNG test. Owns the {@link WebDriver} lifecycle
 * (one fresh browser session per test method) so individual test classes
 * only need to describe behaviour, never driver setup/teardown.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createDriver();
        loginPage = new LoginPage(driver).openPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
