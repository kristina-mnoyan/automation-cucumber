package setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
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

    private void createRemoteDriver() {
        try {
            driver = new RemoteWebDriver(new URL("http://192.168.2.70:4444/wd/hub"), new ChromeOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void createChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(chromeOptions);
    }

    private void createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        DesiredCapabilities firefox = DesiredCapabilities.firefox();
        firefox.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
    }

    private void createEdgeDriver() {
        WebDriverManager.edgedriver().setup();

        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    public void initDriver(String browser) {
        switch (browser) {
            case "edge":
                createEdgeDriver();
                break;
            case "firefox":
                createFirefoxDriver();
                break;
            case "remote":
                createRemoteDriver();
                break;
            case "chrome":
            default:
                createChromeDriver();
        }
        log.info("Started with browser " + browser);
        driverCommonConfigs();
    }

    private void driverCommonConfigs() {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else initDriver("chrome");
        return driver;
    }

    public void quitDriver() {
        driver.quit();
    }
}
