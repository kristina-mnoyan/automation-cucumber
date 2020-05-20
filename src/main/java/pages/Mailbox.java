package pages;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonUtils;
import utils.UiUtils;
import utils.WaitUtils;

import java.util.List;

public class Mailbox extends BasePage {

    @FindBy(xpath = "//*[text()='Գրել']")
    private List<WebElement> writeMessageButton;

    @FindBy(name = "to")
    private WebElement messageToField;

    @FindBy(name = "subjectbox")
    private WebElement subjectField;

    @FindBy(xpath = "//div[@aria-label='Հաղորդագրության տեքստը']")
    private WebElement messageTextField;

    @FindBy(xpath = "//*[@alt='Փակել']")
    private WebElement closeMessagePopUpButton;

    @FindBy(linkText = "Սևագրեր")
    private WebElement draftsFolder;

    @FindBy(xpath = "//*[@class='zA yO']")
    private List<WebElement> draftMessageRows;

    @FindBy(xpath = "//input[@name='to']")
    private WebElement toFieldAfterDraft;

    @FindBy(xpath = "//*[@name='subject']")
    private WebElement subjectFieldAfterDraft;

    @FindBy(xpath = "//*[text()='Ուղարկել']")
    private WebElement sendButton;

    @FindBy(linkText = "Ուղարկված")
    private WebElement sentFolder;

    @FindBy(xpath = "//a[contains(@aria-label, 'Google հաշիվ')]")
    private WebElement userInfoButton;

    @FindBy(xpath = "//*[(text()='Դուրս գրվել')]")
    private WebElement logOutButton;

    @FindBy(xpath = "//div[@aria-label='Ուղարկման այլ տարբերակներ']")
    private WebElement sendInOtherWaysButton;

    @FindBy(xpath = "//div[text()='Պլանավորել ուղարկում']")
    private WebElement scheduledSendingButton;

    @FindBy(xpath = "//div[text()='Ընտրեք ամսաթիվը և ժամը']")
    private WebElement chooseDateAndTimeButton;

    @FindBy(xpath = "//input[@class='hu ks']")
    private WebElement scheduledTimeField;

    @FindBy(xpath = "//input[@class='hu jA']")
    private WebElement scheduledDateField;

    @FindBy(xpath = "//button[text()='Պլանավորել ուղարկում']")
    private WebElement scheduleSendingButton;

    @FindBy(linkText = "Պլանավորված")
    private WebElement scheduledFolder;

    @FindBy(linkText = "Մուտքային")
    private WebElement inboxFolder;

    @FindBy(xpath = "//tr[@class='zA yO']//span[@class='bog']/span")
    private List<WebElement> subjects;

    public void clickWriteMessageButton() {
        writeMessageButton.get(0).click();
    }

    public String getPlannedMailSendingTime(String subject) {
        String xPath = String.format("(//*[text()='%s']//ancestor::tr[@class='zA yO']//child::div[@class='by1 kE'])[2]", subject);
//        return driver.findElement(By.xpath(xPath)).getText();
        return driver.findElement(By.xpath(xPath)).getAttribute("value");
//        return driver.findElement(By.xpath(xPath)).getAttribute("innerHTML");
    }

    public boolean isMailUnread(String subject) {
        String xPath = String.format("//*[text()='%s']//ancestor::tr[@class='zA zE']", subject);
        return driver.findElements(By.xpath(xPath)).size() == 1;
    }

    public void setMessageToField(String messageToValue) {
        messageToField.sendKeys(messageToValue);
    }

    public void setSubjectField(String subject) {
        subjectField.sendKeys(subject);
    }

    public void clickCloseMessagePopUpButton() {
        closeMessagePopUpButton.click();
        WaitUtils.waitForElementToDisappear(closeMessagePopUpButton);
    }

    @SneakyThrows
    public void clickDraftFolderButton() {
        draftsFolder.click();
        Thread.sleep(2000);
    }

    @SneakyThrows
    public void clickSentFolderButton() {
        sentFolder.click();
        Thread.sleep(2000);
    }

    public void clickScheduledFolderButton() {
        scheduledFolder.click();
    }

    public void clickInboxFolderButton() {
        inboxFolder.click();
    }

    public String getMessageToFieldValue() {
        return toFieldAfterDraft.getAttribute("value");
    }

    public String getSubjectFieldValue() {
        return subjectFieldAfterDraft.getAttribute("value");
    }

    public String getMessageText() {
        return messageTextField.getText();
    }

    public void setMessageText(String message) {
        messageTextField.sendKeys(message);
    }

    public void clickSendButton() {
        sendButton.click();
        WaitUtils.waitForElementToDisappear(sendButton);
    }

    public void clickOnDraftMessage() {
        draftMessageRows.get(1).click();
    }

    public boolean isWriteMessageButtonDisplayed() {
        return CommonUtils.isListEmpty(writeMessageButton);
    }

    public boolean isDraftMessageRowDisplayed() {
        return CommonUtils.isListEmpty(draftMessageRows);
    }

    public void writeMessageOperation(String emailSubject) {
        clickWriteMessageButton();
        setMessageToField(data.getProperty("RECEIVER_EMAIL_ADDRESS"));
        setSubjectField(emailSubject);
        setMessageText(data.getProperty("MESSAGE_TEXT"));
    }

    public List<String> subjectTexts() {
        return CommonUtils.getTextListFromWebElementList(subjects);
    }

    public void clickUserInfoButton() {
        userInfoButton.click();
    }

    public void clickLogOutButton() {
        logOutButton.click();
    }

    public void setScheduledTime(String scheduledTime) {
        WaitUtils.waitForElementToBeClickable(scheduledTimeField);
        scheduledTimeField.click();
        UiUtils.clearAndType(scheduledTimeField, scheduledTime);
    }

    public void setScheduledDate(String scheduledDate) {
        WaitUtils.waitForElementToBeClickable(scheduledDateField);
        scheduledDateField.click();
        UiUtils.clearAndType(scheduledDateField, scheduledDate);
    }

    @SneakyThrows
    public void scheduledSendingOperation(String scheduledDate, String scheduledTime) {
        sendInOtherWaysButton.click();
        WaitUtils.waitForElementToBeVisible(scheduledSendingButton);
        scheduledSendingButton.click();
        chooseDateAndTimeButton.click();
        Thread.sleep(2000);
        setScheduledDate(scheduledDate);
        setScheduledTime(scheduledTime);
        WaitUtils.waitForElementToBeClickable(scheduleSendingButton);
        scheduleSendingButton.click();

    }
}
