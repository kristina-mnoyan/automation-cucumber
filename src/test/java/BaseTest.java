import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    WebDriver chromeDriver;

    @BeforeClass
    public void browserSetUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeDriver = new ChromeDriver(chromeOptions);
         WebDriverManager.chromedriver().setup();
        chromeDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterClass
    public void browserTearDown() {
        chromeDriver.quit();
        chromeDriver = null;
    }
}
