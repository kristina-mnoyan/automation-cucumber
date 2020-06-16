package setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.concurrent.TimeUnit;

@UtilityClass
@Log4j
public class DriverFactory {

    private WebDriver driver;

    private WebDriver createChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    private WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        return driver;
    }

    private WebDriver initDriver(String browser) {
        switch (browser) {
            case "firefox":
                driver = createFirefoxDriver();
                break;
            case "chrome":
            default:
                driver = createChromeDriver();
        }
        log.info("Started with browser " + browser);
        driverCommonConfigs();
        return driver;
    }

    private void driverCommonConfigs() {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public WebDriver getInstance(String browser) {
        if (driver != null) {
            return driver;
        } else initDriver(browser);
        return driver;
    }

    public WebDriver getInstance() {
        if (driver != null) {
            return driver;
        } else initDriver("chrome");
        return driver;
    }

    public void quitDriver() {
        driver.quit();
        driver = null;
    }
}
