package pages;

import config.PropertyLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import setup.DriverFactory;

public abstract class BasePage {
    protected static WebDriver driver;

    static {
        driver = DriverFactory.getInstance(System.getProperty("browser", "chrome"));
    }

    protected PropertyLoader app = new PropertyLoader("app.properties");
    protected final String BASE_URL = app.getProperty("BASE_URL");
    protected PropertyLoader data = new PropertyLoader("data.properties");

    protected BasePage() {
        PageFactory.initElements(driver, this);
    }

    public void get() {
        driver.get(BASE_URL);
    }
}
