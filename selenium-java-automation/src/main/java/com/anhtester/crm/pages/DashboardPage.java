package com.anhtester.crm.pages;

import org.openqa.selenium.WebDriver;

/**
 * Page Object for the CRM admin dashboard, landed on after a successful login.
 * URL: {baseUrl}/admin/
 */
public class DashboardPage extends BasePage {

    private static final String PATH = "/admin/";
    private static final String TITLE_FRAGMENT = "Dashboard";

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForUrlContains(PATH);
        waitForTitleContains(TITLE_FRAGMENT);
        return getCurrentUrl().contains(PATH) && getTitle().contains(TITLE_FRAGMENT);
    }
}
