package setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;


@UtilityClass
public class DriverFactory {

    private WebDriver driver;

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
            case "chrome":
            default:
                createChromeDriver();
        }
        driverCommonConfigs();
    }

    private void driverCommonConfigs() {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        driver.quit();
    }
}