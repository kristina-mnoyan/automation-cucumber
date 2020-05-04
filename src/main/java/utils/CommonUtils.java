package utils;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import setup.DriverFactory;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommonUtils {
    private WebDriver driver = DriverFactory.getDriver();

    public boolean isListEmpty(List list) {
        return !list.isEmpty();
    }

    public List<WebElement> getWebElementFromPath(By by) {
        return driver.findElements(by);
    }

    public List<String> getStringFromWebElementList(List<WebElement> webElementList) {
        return webElementList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
