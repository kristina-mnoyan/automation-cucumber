package pages;

import config.PropertyLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import setup.DriverFactory;

public abstract class BasePage {
    protected PropertyLoader app = new PropertyLoader("src/main/resources/app.properties");
    protected PropertyLoader data = new PropertyLoader("src/main/resources/data.properties");

    protected final String BASE_URL = app.getProperty("baseUrl");
    private static WebDriver driver;

    static {
        DriverFactory.initDriver("chrome");
        driver = DriverFactory.getDriver();
    }

    BasePage() {
        PageFactory.initElements(driver, this);
    }

    public void get() {
        driver.get(BASE_URL);
    }

}
