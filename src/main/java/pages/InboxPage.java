package pages;

import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonUtils;

import java.util.List;

public class InboxPage extends BasePage {

    private final String randomEmailSubject = RandomString.make(10);
    private List<WebElement> draftFolderSubjects = CommonUtils.getWebElementFromPath(By.xpath(String.format("//span/span[text()='%s']", randomEmailSubject)));

    @FindBy(xpath = "//*[text()='Գրել']")
    private List<WebElement> writeMessageButton;

    @FindBy(name = "to")
    private WebElement messageToField;

    @FindBy(name = "subjectbox")
    private WebElement subjectField;

    @FindBy(xpath = "//div[@aria-label='Հաղորդագրության տեքստը']")
    private WebElement messageTextField;

    @FindBy(xpath = "//img[contains(@aria-label, 'Պահել և փակել')]")
    private WebElement closeMessagePopUpButton;

    @FindBy(xpath = "//a[contains(@aria-label, 'Սևագրեր')]")
    private WebElement draftsFolder;

    @FindBy(xpath = "//tr[@class='zA yO']")
    private List<WebElement> draftMessageRows;

    @FindBy(xpath = "//input[@name='to']")
    private WebElement toFieldAfterDraft;

    @FindBy(xpath = "//input[@name='subject']")
    private WebElement subjectFieldAfterDraft;

    @FindBy(xpath = "//div[contains(@aria-label, 'Ուղարկել')]")
    private WebElement sendButton;

    @FindBy(xpath = "//a[contains(@aria-label, 'Ուղարկված')]")
    private WebElement sentFolder;

    @FindBy(xpath = "//a[contains(@aria-label, 'Google հաշիվ')]")
    private WebElement userInfoButton;

    @FindBy(xpath = "//a[contains(text(), 'Դուրս գրվել')]")
    private WebElement logOutButton;

    public InboxPage() {
    }

    public void clickWriteMessageButton() {
        writeMessageButton.get(0).click();
    }

    public void setMessageToField(String messageToValue) {
        messageToField.sendKeys(messageToValue);
    }

    public void setSubjectField(String subject) {
        subjectField.sendKeys(subject);
    }

    public void clickCloseMessagePopUpButton() {
        closeMessagePopUpButton.click();
    }

    public void clickDraftFolderButton() {
        draftsFolder.click();
    }

    public void clickSentFolderButton() {
        sentFolder.click();
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
    }

    public void clickOnDraftMessage() {
        draftMessageRows.get(0).click();
    }

    public boolean isWriteMessageButtonDisplayed() {
        return CommonUtils.isListEmpty(writeMessageButton);
    }

    public boolean isDraftMessageRowDisplayed() {
        return CommonUtils.isListEmpty(draftMessageRows);
    }

    public void writeMessageOperation() {
        clickWriteMessageButton();
        setMessageToField(data.getProperty("receiver.email.address"));
        setSubjectField(randomEmailSubject);
        setMessageText(data.getProperty("message.text"));
    }

    public boolean isFolderSubjectsListEmpty() {
        return CommonUtils.isListEmpty(CommonUtils.getStringFromWebElementList(draftFolderSubjects));
    }

    public void clickUserInfoButton() {
        userInfoButton.click();
    }

    public void clickLogOutButton() {
        logOutButton.click();
    }
}
