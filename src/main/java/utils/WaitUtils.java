package utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.DriverFactory;

@UtilityClass
public class WaitUtils {
    private final int DEFAULT_TIMEOUT = 20;

    private final WebDriver driver = DriverFactory.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    private JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToDisappear(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForJsLoad() {
        wait
                .until((ExpectedCondition<Boolean>) driver -> javascriptExecutor.executeScript("return document.readyState")
                        .toString()
                        .equals("complete"));
    }

    @SneakyThrows
    public void hardWait(int timeToWait) {
        Thread.sleep(timeToWait);
    }
}
