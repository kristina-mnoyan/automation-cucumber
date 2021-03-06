package utils;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import setup.DriverFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class CommonUtils {
    private final WebDriver driver = DriverFactory.getInstance();

    public boolean isListEmpty(final List list) {
        return list.isEmpty();
    }

    public List<WebElement> getWebElementListFromPath(final By by) {
        return driver.findElements(by);
    }

    public List<String> getTextListFromWebElementList(final List<WebElement> webElementList) {
        return webElementList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getFutureDateTime(int plusMinutes, String pattern) {
        return DateTimeFormatter
                .ofPattern(pattern, new Locale("hy", "AM"))
                .format(LocalDateTime.now()
                        .plusMinutes(plusMinutes));
    }

    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd - HH mm ss.SSS ").format(new Date());
    }

    public boolean isLastPartFromUrlEqualToFolderName(String folderName) {
        return driver.getCurrentUrl().endsWith(folderName);
    }

    public String getLastPartFromCurrentUrl() {
        return driver.getCurrentUrl().substring(driver.getCurrentUrl().lastIndexOf('#') + 1);
    }
}
