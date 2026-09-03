import { expect, Locator, Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { env } from '../config/env';

/**
 * Page Object for the CRM authentication (login) page.
 * URL: {baseUrl}/admin/authentication
 */
export class LoginPage extends BasePage {
  readonly path = '/admin/authentication';

  readonly emailInput: Locator;
  readonly passwordInput: Locator;
  readonly rememberMeCheckbox: Locator;
  readonly loginButton: Locator;
  readonly forgotPasswordLink: Locator;
  readonly errorAlert: Locator;

  constructor(page: Page) {
    super(page);
    this.emailInput = page.locator('#email');
    this.passwordInput = page.locator('#password');
    this.rememberMeCheckbox = page.locator('#remember');
    this.loginButton = page.getByRole('button', { name: 'Login' });
    this.forgotPasswordLink = page.getByRole('link', { name: 'Forgot Password?' });
    this.errorAlert = page.locator('#alerts .alert-danger');
  }

  /** Navigate directly to the login page. */
  async open(): Promise<void> {
    await this.goto(`${env.baseUrl}${this.path}`);
    await expect(this.emailInput).toBeVisible();
  }

  async fillEmail(email: string): Promise<void> {
    await this.emailInput.fill(email);
  }

  async fillPassword(password: string): Promise<void> {
    await this.passwordInput.fill(password);
  }

  async checkRememberMe(): Promise<void> {
    await this.rememberMeCheckbox.check();
  }

  async submit(): Promise<void> {
    await this.loginButton.click();
  }

  /** Fill the credentials and submit the form in one step. */
  async login(email: string, password: string, rememberMe = false): Promise<void> {
    await this.fillEmail(email);
    await this.fillPassword(password);
    if (rememberMe) {
      await this.checkRememberMe();
    }
    await this.submit();
  }

  async getErrorMessage(): Promise<string> {
    await expect(this.errorAlert).toBeVisible();
    return (await this.errorAlert.textContent())?.trim() ?? '';
  }
}
