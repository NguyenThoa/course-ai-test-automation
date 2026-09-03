# CRM Login – Playwright TypeScript Automation Framework

Playwright + TypeScript automation framework using the Page Object Model
(POM), targeting the login feature of the [Perfex CRM demo](https://crm.anhtester.com/admin/authentication).

## Project structure

```
├── config/
│   └── env.ts              # Centralized environment/config reader (.env)
├── pages/
│   ├── BasePage.ts         # Shared base class for all Page Objects
│   ├── LoginPage.ts        # Login page locators & actions
│   └── DashboardPage.ts    # Dashboard page (post-login landing page)
├── test-data/
│   └── login.data.ts       # Test data (valid/invalid credentials, messages)
├── tests/
│   └── login.spec.ts       # Login test scenarios
├── playwright.config.ts    # Playwright configuration
├── .env.example            # Sample environment variables
└── package.json
```

## Setup

```bash
npm install
npx playwright install
```

Copy `.env.example` to `.env` and adjust if needed:

```bash
cp .env.example .env
```

```
BASE_URL=https://crm.anhtester.com
VALID_EMAIL=admin@example.com
VALID_PASSWORD=123456
```

## Running tests

```bash
npm test               # run all tests headless (chromium, firefox, webkit)
npm run test:chromium  # run only on Chromium
npm run test:headed    # run with a visible browser
npm run test:ui        # open Playwright's interactive UI mode
npm run test:debug     # step through tests with the Playwright inspector
npm run report         # open the last HTML report
```

## Test scenarios covered (`tests/login.spec.ts`)

- ✅ Successful login with valid credentials (`admin@example.com` / `123456`), asserting redirect to the dashboard.
- ❌ Login with a wrong password shows the "Invalid email or password" error and stays on the login page.
- ❌ Login with an unregistered email shows the same error.
- ❌ Submitting the form empty keeps the user on the login page.

## Design notes

- **Page Object Model**: every page exposes locators + intention-revealing actions (`login()`, `getErrorMessage()`), keeping test files free of raw selectors.
- **Config-driven**: base URL and credentials come from `config/env.ts`, backed by `.env`, so environments/credentials can be swapped without touching code.
- **Traceability**: `playwright.config.ts` enables trace, screenshot and video capture on failure for easier debugging in CI.
