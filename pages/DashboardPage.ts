import { expect, Locator, Page } from '@playwright/test';
import { BasePage } from './BasePage';

/**
 * Page Object for the CRM admin dashboard, landed on after a successful login.
 * URL: {baseUrl}/admin/
 */
export class DashboardPage extends BasePage {
  readonly path = '/admin/';

  readonly userProfileIcon: Locator;

  constructor(page: Page) {
    super(page);
    this.userProfileIcon = page.locator('#header-user-profile, .navbar-cs-icon-profile').first();
  }

  /** Waits until the browser has navigated to the dashboard URL. */
  async waitForLoaded(): Promise<void> {
    await this.page.waitForURL(/\/admin\/?(\?.*)?$/);
  }

  async isLoggedIn(): Promise<boolean> {
    return /\/admin\/?(\?.*)?$/.test(this.page.url());
  }

  async expectLoaded(): Promise<void> {
    await this.waitForLoaded();
    await expect(this.page).toHaveTitle(/Dashboard/i);
  }
}
