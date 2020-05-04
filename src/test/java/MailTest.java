import lombok.SneakyThrows;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MailTest extends BaseTest {

    private final String randomEmailSubject = RandomString.make(10);
    private final String receiverEmailAddress = "ns27042020@gmail.com";
    private final String messageText = "Urgent message";

    private WebElement writeButton;
    private List<WebElement> draftMessages;

    private WebElement receiverField;
    private WebElement subjectField;
    private WebElement messageField;

    @Test
    public void loginToGmail() {

        final String userName = "ns9970803@gmail.com";
        final String password = "Admin@123!";
        WebElement nextButton;

        chromeDriver.get("https://www.google.com/intl/hy/gmail/about/#");

        WebElement signInButton = chromeDriver.findElement(By.partialLinkText("Sign in"));
        signInButton.click();

        switchTab();

        WebElement userNameField = chromeDriver.findElement(By.id("identifierId"));
        userNameField.sendKeys(userName);

        nextButton = chromeDriver.findElement(By.id("identifierNext"));
        nextButton.click();

        WebElement passwordField = chromeDriver.findElement(By.name("password"));
        passwordField.click();
        passwordField.sendKeys(password);

        nextButton = chromeDriver.findElement(By.id("passwordNext"));
        nextButton.click();

        writeButton = chromeDriver.findElement(By.xpath("//*[text()='Գրել']"));

        Assert.assertTrue(writeButton.isDisplayed());
    }

    @SneakyThrows
    @Test(dependsOnMethods = "loginToGmail")
    public void createDraftMessage() {

        writeButton.click();

        receiverField = chromeDriver.findElement(By.name("to"));
        receiverField.sendKeys(receiverEmailAddress);

        subjectField = chromeDriver.findElement(By.name("subjectbox"));
        subjectField.sendKeys(randomEmailSubject);

        messageField = chromeDriver.findElement(By.xpath("//div[@aria-label='Հաղորդագրության տեքստը']"));
        messageField.sendKeys(messageText);

        WebElement closeMessagePopUpButton = chromeDriver.findElement(By.xpath("//*[@alt='Փակել']"));
        closeMessagePopUpButton.click();

        Thread.sleep(2000);

        WebElement draftsFolder = chromeDriver.findElement(By.linkText("Սևագրեր"));
        draftsFolder.click();

        draftMessages = chromeDriver.findElements(By.xpath("//*[@class='zA yO']"));

        //TODO will not work if there are other drafts
        Assert.assertFalse(draftMessages.isEmpty());
    }

    @SneakyThrows
    @Test(dependsOnMethods = {"loginToGmail", "createDraftMessage"})
    public void sendTheDraftedMessage() {

        draftMessages.get(0).click();

        receiverField = chromeDriver.findElement(By.xpath("//input[@name='to']"));
        subjectField = chromeDriver.findElement(By.xpath("//*[@name='subject']"));
        messageField = chromeDriver.findElement(By.xpath("//div[@aria-label='Հաղորդագրության տեքստը']"));

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(receiverField.getAttribute("value"), receiverEmailAddress, "The email addresses don't match each other");
        softAssert.assertEquals(subjectField.getAttribute("value"), randomEmailSubject, "The email subjects don't match each other");
        softAssert.assertEquals(messageField.getText(), messageText, "The email texts don't match each other");

        softAssert.assertAll();

        WebElement sendButton = chromeDriver.findElement(By.xpath("//*[text()='Ուղարկել']"));
        sendButton.click();

        Thread.sleep(2000);

        List<String> draftFolderSubjects = chromeDriver.findElements(By.xpath("//span/span[text()='" + randomEmailSubject + "']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertTrue(draftFolderSubjects.isEmpty());
    }

    @Test(dependsOnMethods = {"loginToGmail", "createDraftMessage", "sendTheDraftedMessage"})
    public void checkTheMessageIsSent() {

        WebElement sentFolder = chromeDriver.findElement(By.linkText("Ուղարկված"));
        sentFolder.click();

        List<String> sentFolderSubjects = chromeDriver.findElements(By.xpath("//span/span[text()='" + randomEmailSubject + "']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertFalse(sentFolderSubjects.isEmpty());
    }

    @Test(dependsOnMethods = {"loginToGmail", "createDraftMessage", "sendTheDraftedMessage", "checkTheMessageIsSent"})
    public void logoutFromGmail() {

        WebElement userInfoButton = chromeDriver.findElement(By.xpath("//*[contains(@aria-label, 'Google հաշիվ')]"));
        userInfoButton.click();

        WebElement logOutButton = chromeDriver.findElement(By.xpath("//*[(text()='Դուրս գրվել')]"));
        logOutButton.click();
    }

    private void switchTab() {
        ArrayList<String> browserTabs = new ArrayList<>(chromeDriver.getWindowHandles());
        chromeDriver.switchTo().window(browserTabs.get(0));
        chromeDriver.close();
        chromeDriver.switchTo().window(browserTabs.get(1));
    }
}
