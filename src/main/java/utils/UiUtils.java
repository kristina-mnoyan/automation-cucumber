package utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import setup.DriverFactory;

import java.io.File;
import java.util.ArrayList;

@UtilityClass
public class UiUtils {

    private final WebDriver driver = DriverFactory.getInstance();
    private final Actions actions = new Actions(driver);
    private final JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);

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

    @SneakyThrows
    public void getScreenshot(ITestResult iTestResult) {
        File file = ((TakesScreenshot) DriverFactory.getInstance()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("target\\screenshots\\" + CommonUtils.getCurrentTime() + iTestResult.getMethod().getMethodName() + ".png"));
    }
}
