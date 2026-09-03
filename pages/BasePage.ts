import { Page } from '@playwright/test';

/**
 * Base class shared by every Page Object.
 * Holds the Playwright `Page` handle and common helpers so concrete
 * page objects only need to describe locators and page-specific actions.
 */
export class BasePage {
  protected readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async goto(url: string): Promise<void> {
    await this.page.goto(url);
  }

  async getCurrentUrl(): Promise<string> {
    return this.page.url();
  }

  async getTitle(): Promise<string> {
    return this.page.title();
  }
}
