# Stockbit Android Automation Framework

Android Automation Test Framework using **Appium 2 + UiAutomator2 + Cucumber-JVM + JUnit 5 + PicoContainer + Allure**.

## 📋 Table of Contents

- [Requirements](#-requirements)
- [Project Structure](#-project-structure)
- [Setup Guide](#-setup-guide)
- [Running Tests](#-running-tests)
- [Allure Reports](#-allure-reports)
- [Configuration](#-configuration)
- [Writing Tests](#-writing-tests)

---

## 🔧 Requirements

### Software Requirements

| Tool | Version | Download Link |
|------|---------|---------------|
| Java JDK | 21+ | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/) |
| Maven | 3.8+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| Node.js | 18+ | [Node.js](https://nodejs.org/) |
| Appium | 2.x | Install via npm |
| Android Studio | Latest | [Android Studio](https://developer.android.com/studio) |
| Allure CLI | 2.x | Install via npm or brew |

### Test Environment Requirement - How to Setup will be shown at next section

1. **Java**: Ensure `JAVA_HOME` is set and Java is in PATH
   ```bash
   java -version
   ```

2. **Maven**: Ensure Maven is installed
   ```bash
   mvn -version
   ```

3. **Node.js**: Required for Appium
   ```bash
   node -version
   npm -version
   ```

4. **Android SDK**: Set `ANDROID_HOME` environment variable
   - Windows: `C:\Users\<username>\AppData\Local\Android\Sdk`
   - macOS: `~/Library/Android/sdk`
   - Linux: `~/Android/Sdk`

   Add to PATH:
   - `%ANDROID_HOME%\platform-tools`
   - `%ANDROID_HOME%\tools`
   - `%ANDROID_HOME%\emulator`

---

## 🚀 Setup Guide

### Step 1: Install Appium 2

```bash
# Install Appium globally
npm install -g appium

# Verify installation
appium -v

# Install UiAutomator2 driver
appium driver install uiautomator2

# List installed drivers
appium driver list --installed
```

### Step 2: Setup Android Emulator or Device

**Option A: Using Android Emulator**
1. Open Android Studio
2. Go to Tools → Device Manager
3. Create a new virtual device (recommended: Pixel 4 with API 30+)
4. Start the emulator

**Option B: Using Physical Device**
1. Enable Developer Options on your Android device
2. Enable USB Debugging
3. Connect device via USB
4. Verify connection: `adb devices`

### Step 3: Install Project Dependencies

```bash
# Navigate to project directory
cd stockbit-qa-android-halid

# Install Maven dependencies
mvn clean install -DskipTests
```

### Step 4: Install Allure CLI (for reports)

```bash
# Option 1: Using npm
npm install -g allure-commandline

# Option 2: Using Homebrew (macOS)
brew install allure

# Option 3: Using Scoop (Windows)
scoop install allure

## If Scoop not installed yet run this via powershell
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
irm get.scoop.sh | iex

# Verify installation
allure --version
```

---

## 📁 Project Structure

```
stockbit-qa-android-halid/
├── pom.xml                                 # Maven configuration
├── README.md                               # This documentation
└── src/
    └── test/
        ├── java/
        │   └── com/stockbit/automation/
        │       ├── config/
        │       │   └── AppiumConfig.java       # Configuration loader
        │       ├── context/
        │       │   └── TestContext.java        # Shared test context (DI)
        │       ├── drivers/
        │       │   └── DriverManager.java      # Driver lifecycle management
        │       ├── hooks/
        │       │   └── Hooks.java              # Cucumber hooks
        │       ├── pages/
        │       │   ├── BasePage.java           # Base page with common methods
        │       │   ├── HomePage.java           # Home page object
        │       │   └── LoginPage.java          # Login page object
        │       ├── runners/
        │       │   └── TestRunner.java         # JUnit 5 test runner
        │       └── steps/
        │           ├── AppLaunchSteps.java     # App launch step definitions
        │           ├── LoginSteps.java         # Login step definitions
        │           └── NavigationSteps.java    # Navigation step definitions
        └── resources/
            ├── features/
            │   ├── app_launch.feature          # App launch scenarios
            │   ├── login.feature               # Login scenarios
            │   └── navigation.feature          # Navigation scenarios
            ├── mda-1.0.13-15.apk              # Application under test
            ├── allure.properties               # Allure configuration
            ├── config.properties               # Test configuration
            ├── cucumber.properties             # Cucumber configuration
            ├── junit-platform.properties       # JUnit Platform configuration
            └── logback-test.xml               # Logging configuration
```

---

## ▶️ Running Tests

### Start Appium Server

```bash
# Start Appium server (in a separate terminal)
appium

# Or start with verbose logging
appium --log-level debug
```

### Run All Tests

```bash
# Run all tests
mvn clean test

# Run with specific tag
mvn clean test -Dcucumber.filter.tags="@smoke"

# Run specific feature
mvn clean test -Dcucumber.features="src/test/resources/features/app_launch.feature"
```

### Run from IDE

1. Open the project in IntelliJ IDEA or Eclipse
2. Right-click on `TestRunner.java`
3. Select "Run 'TestRunner'"
4. Override the intended tag in this configuration:
   @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@yourtaghere")

### Run Tests by Tag

```bash
# Run smoke tests only
mvn test -Dcucumber.filter.tags="@smoke"

# Run login tests
mvn test -Dcucumber.filter.tags="@login"

# Run critical tests
mvn test -Dcucumber.filter.tags="@critical"

# Exclude debug tests
mvn test -Dcucumber.filter.tags="@smoke and not @debug"
```
---

## 📊 Allure Reports

### Generate and View Report

```bash
# After running tests, generate Allure report
mvn allure:report

# Serve the report (opens in browser)
mvn allure:serve

# Or use Allure CLI
allure serve target/allure-results
```

### Report Features

- **Overview Dashboard**: Test execution summary
- **Suites**: Test cases grouped by feature files
- **Graphs**: Pass/Fail trends and duration charts
- **Timeline**: Test execution timeline
- **Categories**: Failure categorization
- **Behaviors**: BDD-style test organization
- **Packages**: Java package-based organization

---

## ⚙️ Configuration

### config.properties

Edit `src/test/resources/config.properties` to customize:

```properties
# Appium Server
appium.server.url=http://127.0.0.1:4723

# Device (change for your device/emulator)
device.name=emulator-5554

# Timeouts
implicit.wait=10
explicit.wait=15
```

### Common Device Names

| Device Type | device.name |
|-------------|-------------|
| Default Emulator | `emulator-5554` |
| Second Emulator | `emulator-5556` |
| Physical Device | Run `adb devices` to get the ID |

---

## ✍️ Writing Tests

### Create Feature File

```gherkin
@smoke @your_tag
Feature: Your Feature Name
  As a user
  I want to do something
  So that I achieve a goal

  Background:
    Given the app is launched

  Scenario: Your test scenario
    When I perform an action
    Then I should see the expected result
```

### Create Step Definition

```java
public class YourSteps {
    private final TestContext testContext;

    public YourSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I perform an action")
    public void iPerformAnAction() {
        // Implementation
    }
}
```

### Create Page Object

```java
public class YourPage extends BasePage {
    
    @AndroidFindBy(id = "com.app:id/element_id")
    private WebElement yourElement;

    public YourPage(AndroidDriver driver) {
        super(driver);
    }

    public void performAction() {
        click(yourElement);
    }
}
```

---

## 🏷️ Available Tags

| Tag | Description |
|-----|-------------|
| `@smoke` | Smoke test suite |
| `@login` | Login-related tests |
| `@navigation` | Navigation tests |
| `@launch` | App launch tests |
| `@critical` | Critical path tests |
| `@negative` | Negative test cases |
| `@debug` | Debug/investigation tests |

---

## 🔍 Troubleshooting

### Common Issues

1. **Appium server not running**
   ```bash
   # Start Appium server first
   appium
   ```

2. **Device not found**
   ```bash
   # Check connected devices
   adb devices
   ```

3. **App not installed**
   - Ensure `mda-1.0.13-15.apk` exists in `src/test/resources/`
   - Check the `app.path` in config.properties

4. **Element not found**
   - Use Appium Inspector to verify locators
   - Increase implicit/explicit wait times

### Useful ADB Commands

```bash
# List connected devices
adb devices

# Install APK manually
adb install mda-1.0.13-15.apk

# Uninstall app
adb uninstall com.app.mda

# Get current activity
adb shell dumpsys activity | grep mFocusedActivity

# Take screenshot
adb exec-out screencap -p > screenshot.png
```

---

## 📚 Tech Stack

| Component      | Technology       |
|----------------|------------------|
| Language       | Java 21          |
| Build Tool     | Maven            |
| Automation     | Appium 2.x       |
| Android Driver | UiAutomator2     |
| BDD Framework  | Cucumber-JVM 7.x |
| Test Runner    | JUnit 5          |
| DI Container   | PicoContainer    |
| Reporting      | Allure 2.x       |
| Logging        | Logback          |

---

## 🎥 Recording Test Evidence

To record test execution:

1. **Using ADB screenrecord**:
   ```bash
   adb shell screenrecord /sdcard/test_run.mp4
   # Run tests
   # Press Ctrl+C to stop recording
   adb pull /sdcard/test_run.mp4
   ```

2. **Using screen recording software**:
   - OBS Studio
   - Windows Game Bar (Win + G)
   - macOS Screenshot app

---

## 👤 Author

Stockbit QA Automation Assessment

---

## 📄 License

This project is for assessment purposes only.
