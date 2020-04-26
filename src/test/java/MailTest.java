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

    private final String userName = "ns9970803@gmail.com";
    private final String password = "Admin@123!";
    private final String randomEmailSubject = RandomString.make(10);
    private final String receiverEmailAddress = "ns27042020@gmail.com";
    private final String messageText = "Urgent message";


    @Test
    public void successfullyLoginToGmailTest() {

        chromeDriver.get("https://www.google.com/intl/hy/gmail/about/#");

        WebElement signInButton = chromeDriver.findElement(By.cssSelector("ul.h-c-header__cta-list.header__nav--ltr > li:nth-child(2) > a"));
        signInButton.click();

        ArrayList<String> tabs2 = new ArrayList<>(chromeDriver.getWindowHandles());
        chromeDriver.switchTo().window(tabs2.get(0));
        chromeDriver.close();
        chromeDriver.switchTo().window(tabs2.get(1));

        WebElement userNameField = chromeDriver.findElement(By.id("identifierId"));
        userNameField.sendKeys(userName);

        WebElement userNameNextButton = chromeDriver.findElement(By.id("identifierNext"));
        userNameNextButton.click();

        WebElement passwordField = chromeDriver.findElement(By.name("password"));
        passwordField.click();
        passwordField.sendKeys(password);

        WebElement passwordNextButton = chromeDriver.findElement(By.id("passwordNext"));
        passwordNextButton.click();

        List<WebElement> writeMessageButton = chromeDriver.findElements(By.xpath("//*[text()='Գրել']"));

        Assert.assertEquals(writeMessageButton.size(), 1);
    }

    @Test(dependsOnMethods = "successfullyLoginToGmailTest")
    public void createDraftMessageTest() {

        List<WebElement> writeMessageButton = chromeDriver.findElements(By.xpath("//*[text()='Գրել']"));
        writeMessageButton.get(0).click();

        WebElement messageToField = chromeDriver.findElement(By.name("to"));
        messageToField.sendKeys(receiverEmailAddress);

        WebElement subjectField = chromeDriver.findElement(By.name("subjectbox"));
        subjectField.sendKeys(randomEmailSubject);

        WebElement messageTextField = chromeDriver.findElement(By.xpath("//div[@aria-label='Հաղորդագրության տեքստը']"));
        messageTextField.sendKeys(messageText);

        WebElement closeMessagePopUpButton = chromeDriver.findElement(By.xpath("//img[contains(@aria-label, 'Պահել և փակել')]"));
        closeMessagePopUpButton.click();

        WebElement draftsFolder = chromeDriver.findElement(By.xpath("//a[contains(@aria-label, 'Սևագրեր')]"));
        draftsFolder.click();

        List<WebElement> draftMessageRows = chromeDriver.findElements(By.xpath("//tr[@class='zA yO']"));

        Assert.assertFalse(draftMessageRows.isEmpty());
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest"})
    public void sendTheDraftedMessageTest() {

        List<WebElement> draftMessageRows = chromeDriver.findElements(By.xpath("//tr[@class='zA yO']"));
        draftMessageRows.get(0).click();

        WebElement toFieldAfterDraft = chromeDriver.findElement(By.xpath("//input[@name='to']"));
        WebElement subjectFieldAfterDraft = chromeDriver.findElement(By.xpath("//input[@name='subject']"));
        WebElement messageTextFieldAfterDraft = chromeDriver.findElement(By.xpath("//div[@aria-label='Հաղորդագրության տեքստը']"));

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(toFieldAfterDraft.getAttribute("value"), receiverEmailAddress, "The email addresses don't match each other");
        softAssert.assertEquals(subjectFieldAfterDraft.getAttribute("value"), randomEmailSubject, "The email subjects don't match each other");
        softAssert.assertEquals(messageTextFieldAfterDraft.getText(), messageText, "The email texts don't match each other");

        softAssert.assertAll();

        WebElement sendButton = chromeDriver.findElement(By.xpath("//div[contains(@aria-label, 'Ուղարկել')] "));
        sendButton.click();

        List<String> draftFolderSubjects = chromeDriver.findElements(By.xpath("//span/span[text()='" + randomEmailSubject + "']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertTrue(draftFolderSubjects.isEmpty());
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest", "sendTheDraftedMessageTest"})
    public void checkSentFolderTest() {

        WebElement sentFolder = chromeDriver.findElement(By.xpath("//a[contains(@aria-label, 'Ուղարկված')]"));
        sentFolder.click();

        List<String> sentFolderSubjects = chromeDriver.findElements(By.xpath("//span/span[text()='" + randomEmailSubject + "']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertFalse(sentFolderSubjects.isEmpty());
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest", "sendTheDraftedMessageTest", "checkSentFolderTest"})
    public void logOutFromTheSystemTest() {

        WebElement userInfoButton = chromeDriver.findElement(By.xpath("//a[contains(@aria-label, 'Google հաշիվ')]"));
        userInfoButton.click();

        WebElement logOutButton = chromeDriver.findElement(By.xpath("//a[contains(text(), 'Դուրս գրվել')]"));
        logOutButton.click();
    }
}

