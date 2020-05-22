package utils;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import setup.DriverFactory;

import java.util.ArrayList;

@UtilityClass
public class UiUtils {

    private WebDriver driver = DriverFactory.getDriver();
    private Actions actions = new Actions(driver);
    private JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);

    public void switchToCurrentTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
    }

    public void clearAndType(WebElement element, String text) {
        actions
                .click(element)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys(text)
                .build()
                .perform();
    }

    public void click(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].click()", element);
    }

    public void setColorToTheField(WebElement element) {
        String backgroundColor = element.getCssValue("background-color");
        javascriptExecutor.executeScript("arguments[0].style.backgroundColor = 'rebeccapurple'", element);
        javascriptExecutor.executeScript("arguments[0].style.backgroundColor = '" + backgroundColor + "'", element);
    }

    public void scrollToWebElement(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true)", element);
    }
}
