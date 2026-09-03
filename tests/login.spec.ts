import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { DashboardPage } from '../pages/DashboardPage';
import { loginData } from '../test-data/login.data';

test.describe('CRM Login', () => {
  let loginPage: LoginPage;
  let dashboardPage: DashboardPage;

  test.beforeEach(async ({ page }) => {
    loginPage = new LoginPage(page);
    dashboardPage = new DashboardPage(page);
    await loginPage.open();
  });

  test('logs in successfully with valid credentials @smoke', async ({ page }) => {
    await loginPage.login(loginData.valid.email, loginData.valid.password);

    await dashboardPage.expectLoaded();
    await expect(page).toHaveURL(/\/admin\/?(\?.*)?$/);
  });

  test('shows an error for a wrong password', async ({ page }) => {
    await loginPage.login(
      loginData.invalid.wrongPassword.email,
      loginData.invalid.wrongPassword.password
    );

    await expect(loginPage.errorAlert).toBeVisible();
    expect(await loginPage.getErrorMessage()).toContain(loginData.messages.invalidCredentials);
    await expect(page).toHaveURL(/\/admin\/authentication\/?$/);
  });

  test('shows an error for an unregistered email', async () => {
    await loginPage.login(
      loginData.invalid.unregisteredEmail.email,
      loginData.invalid.unregisteredEmail.password
    );

    await expect(loginPage.errorAlert).toBeVisible();
    expect(await loginPage.getErrorMessage()).toContain(loginData.messages.invalidCredentials);
  });

  test('keeps the user on the login page when credentials are empty', async ({ page }) => {
    await loginPage.submit();

    await expect(page).toHaveURL(/\/admin\/authentication\/?$/);
    await expect(loginPage.emailInput).toBeVisible();
  });
});
