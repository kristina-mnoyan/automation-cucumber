package helpers;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import setup.DriverFactory;

import java.util.ArrayList;

@UtilityClass
public class UiHelper {

    private WebDriver driver = DriverFactory.getDriver();

    public void switchToCurrentTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
    }
}
