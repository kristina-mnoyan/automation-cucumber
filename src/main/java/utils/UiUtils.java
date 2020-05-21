package utils;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import setup.DriverFactory;

import java.util.ArrayList;

@UtilityClass
public class UiUtils {

    private WebDriver driver = DriverFactory.getDriver();

    public void switchToCurrentTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
    }

    public void clearAndType(WebElement element, String text) {
        String deleteCombination = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        element.sendKeys(deleteCombination + text);
    }
}
