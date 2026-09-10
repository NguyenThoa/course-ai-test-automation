# CRM Login – Selenium + TestNG Automation Framework

Java automation framework using Selenium WebDriver, TestNG and the Page
Object Model, targeting the login feature of the
[Perfex CRM demo](https://crm.anhtester.com/admin/authentication).

## Project structure

```
├── src/main/java/com/anhtester/crm/
│   ├── config/ConfigReader.java     # Reads config.properties (base URL, credentials, browser...)
│   ├── driver/DriverFactory.java    # Creates a WebDriver per browser via WebDriverManager
│   └── pages/
│       ├── BasePage.java            # Shared WebDriverWait helpers (no Thread.sleep)
│       ├── LoginPage.java           # Login page locators & actions
│       └── DashboardPage.java       # Post-login landing page
├── src/test/java/com/anhtester/crm/
│   ├── base/BaseTest.java           # @BeforeMethod/@AfterMethod WebDriver lifecycle
│   └── tests/LoginTest.java         # 5 login test cases
├── src/test/resources/config.properties
├── testng.xml
├── pom.xml
└── mvnw / mvnw.cmd                  # Maven Wrapper — no local Maven install required
```

## Requirements

- JDK 17+
- Google Chrome installed (default browser; driver binary is fetched
  automatically by WebDriverManager — no manual chromedriver setup needed)

Maven itself is **not** required — this project ships with the Maven
Wrapper (`mvnw` / `mvnw.cmd`), which downloads the pinned Maven version on
first run.

## Configuration

`src/test/resources/config.properties`:

```properties
base.url=https://crm.anhtester.com
valid.email=admin@example.com
valid.password=123456
browser=chrome
headless=false
explicit.wait.seconds=10
```

Any property can be overridden from the command line, e.g.:

```bash
./mvnw test -Dbrowser=firefox -Dheadless=true
```

## Running the tests

```bash
# Windows
mvnw.cmd test

# macOS / Linux
./mvnw test
```

This runs `testng.xml`, which executes `LoginTest`.

## Test cases (`LoginTest`)

| # | Test | Expected result |
|---|------|------------------|
| TC01 | Login with valid credentials (`admin@example.com` / `123456`) | Redirected to the dashboard (`/admin/`, title contains "Dashboard") |
| TC02 | Login with the valid email but a wrong password | "Invalid email or password" error is shown; user stays on the login page |
| TC03 | Login with an unregistered email | Same "Invalid email or password" error is shown |
| TC04 | Submit the form with empty email/password | User stays on the login page; the login form is still displayed |
| TC05 | Click "Forgot Password?" | Navigates to the forgot-password page (`/admin/authentication/forgot_password`) |

## Design notes

- **Page Object Model**: pages expose only intention-revealing actions
  (`login()`, `getErrorMessage()`, `isLoaded()`); locators never leak into
  test classes.
- **No `Thread.sleep`**: every wait goes through `WebDriverWait` /
  `ExpectedConditions` in `BasePage`.
- **WebDriverManager**: browser driver binaries are resolved and cached
  automatically, matching the installed browser version.
- **Config-driven**: base URL, credentials, browser and headless mode all
  come from `config.properties` (overridable via `-D` system properties),
  so environments/browsers can be swapped without touching code.
