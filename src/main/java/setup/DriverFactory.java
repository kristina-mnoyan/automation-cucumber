package setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Log4j
@UtilityClass
public class DriverFactory {

    private WebDriver driver;

    private WebDriver createRemoteDriver() {
        RemoteWebDriver driver;
        try {
            driver = new RemoteWebDriver(new URL("http://192.168.2.70:4444/wd/hub"), new ChromeOptions());
            return driver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private WebDriver createChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    private WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxDriver driver = new FirefoxDriver();
        DesiredCapabilities firefox = DesiredCapabilities.firefox();
        firefox.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        return driver;
    }

    private WebDriver initDriver(String browser) {
        switch (browser) {
            case "firefox":
                driver = createFirefoxDriver();
                break;
            case "remote":
                driver = createRemoteDriver();
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
    }
}
