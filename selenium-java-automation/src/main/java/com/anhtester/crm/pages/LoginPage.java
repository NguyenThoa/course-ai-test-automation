package com.anhtester.crm.pages;

import com.anhtester.crm.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the CRM authentication (login) page.
 * URL: {baseUrl}/admin/authentication
 */
public class LoginPage extends BasePage {

    private static final String PATH = "/admin/authentication";

    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By rememberMeCheckbox = By.id("remember");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By forgotPasswordLink = By.linkText("Forgot Password?");
    private final By errorAlert = By.cssSelector("#alerts .alert-danger");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Navigate directly to the login page and wait for it to be ready. */
    public LoginPage openPage() {
        open(ConfigReader.baseUrl() + PATH);
        waitForVisible(emailInput);
        return this;
    }

    public LoginPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public LoginPage checkRememberMe() {
        click(rememberMeCheckbox);
        return this;
    }

    public void clickLogin() {
        click(loginButton);
    }

    /** Fills the credentials and submits the form in one step. */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    public boolean isErrorMessageDisplayed() {
        return isVisible(errorAlert);
    }

    public String getErrorMessage() {
        return getText(errorAlert);
    }

    public boolean isLoginButtonDisplayed() {
        return isVisible(loginButton);
    }

    public boolean isAtLoginPage() {
        return getCurrentUrl().contains(PATH);
    }
}
