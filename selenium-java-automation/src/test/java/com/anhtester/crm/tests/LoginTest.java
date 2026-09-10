package com.anhtester.crm.tests;

import com.anhtester.crm.base.BaseTest;
import com.anhtester.crm.config.ConfigReader;
import com.anhtester.crm.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test suite for the CRM login feature.
 * Target: {@code https://crm.anhtester.com/admin/authentication}
 */
public class LoginTest extends BaseTest {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid email or password";

    @Test(description = "TC01 - Login succeeds with valid credentials and lands on the dashboard")
    public void loginWithValidCredentials_shouldRedirectToDashboard() {
        loginPage.login(ConfigReader.validEmail(), ConfigReader.validPassword());

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isLoaded(),
                "Expected to land on the dashboard after a valid login");
    }

    @Test(description = "TC02 - Login fails with a valid email but wrong password")
    public void loginWithWrongPassword_shouldShowInvalidCredentialsError() {
        loginPage.login(ConfigReader.validEmail(), "wrong-password-123");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Expected an error alert to be displayed for a wrong password");
        Assert.assertEquals(loginPage.getErrorMessage(), INVALID_CREDENTIALS_MESSAGE);
        Assert.assertTrue(loginPage.isAtLoginPage(),
                "Expected to remain on the login page after a failed login");
    }

    @Test(description = "TC03 - Login fails with an email that is not registered")
    public void loginWithUnregisteredEmail_shouldShowInvalidCredentialsError() {
        loginPage.login("not-registered@example.com", "123456");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Expected an error alert to be displayed for an unregistered email");
        Assert.assertEquals(loginPage.getErrorMessage(), INVALID_CREDENTIALS_MESSAGE);
    }

    @Test(description = "TC04 - Submitting an empty form keeps the user on the login page")
    public void loginWithEmptyCredentials_shouldStayOnLoginPage() {
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isAtLoginPage(),
                "Expected to remain on the login page after submitting empty credentials");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Expected the login form to still be displayed");
    }

    @Test(description = "TC05 - The 'Forgot Password?' link navigates to the forgot-password page")
    public void clickForgotPassword_shouldNavigateToForgotPasswordPage() {
        loginPage.clickForgotPassword();

        loginPage.waitForUrlContains("forgot_password");
        Assert.assertTrue(loginPage.getCurrentUrl().contains("forgot_password"),
                "Expected the URL to navigate to the forgot-password page");
    }
}
