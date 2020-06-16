package cucumber.testng.steps;

import config.PropertyLoader;
import io.cucumber.java.en.When;
import net.bytebuddy.utility.RandomString;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.Mailbox;

import static org.assertj.core.api.Assertions.assertThat;

public class DraftMessagesSteps {

    private final String RANDOM_EMAIL_SUBJECT = RandomString.make(10);
    private final PropertyLoader data = new PropertyLoader("data.properties");
    LoginPage loginPage = new LoginPage();
    Mailbox mailbox = new Mailbox();

    @When("the user is logged in the Gmail")
    public void successfullyLoginToGmail() {

        loginPage.get();
        loginPage.loginOperation();

        assertThat(mailbox.isWriteMessageButtonDisplayed())
                .overridingErrorMessage("The Write message button is not displayed")
                .isFalse();
    }

    @When("^the user creates a \"(.*)\" message$")
    public void createDraftMessage(String messageType) {
        mailbox.writeMessageOperation(RANDOM_EMAIL_SUBJECT, messageType);
    }

    @When("the user clicks on the Close button")
    public void clickOnTheCloseButton() {
        mailbox.clickCloseMessagePopUpButton();
    }

    @When("the created message goes to the Draft folder")
    public void verifyTheDraftMessagePresence() {

        mailbox.clickDraftFolderButton();

        assertThat(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT))
                .overridingErrorMessage("The message with '%s' subject doesn't exist", RANDOM_EMAIL_SUBJECT)
                .isTrue();
    }

    @When("the user clicks on the message from the Draft folder")
    public void clickOnTheDraftMessage() {

        mailbox.clickOnDraftMessage(RANDOM_EMAIL_SUBJECT);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(mailbox.getMessageToFieldValue(), data.getProperty("RECEIVER_EMAIL_ADDRESS"), "The email addresses don't match each other");
        softAssert.assertEquals(mailbox.getSubjectFieldValue(), RANDOM_EMAIL_SUBJECT, "The email subjects don't match each other");
        softAssert.assertEquals(mailbox.getMessageText(), data.getProperty("MESSAGE_TEXT"), "The email texts don't match each other");

        softAssert.assertAll();
    }

    @When("clicks on the Send button")
    public void clickSendButton() {

        mailbox.clickSendButton();
        assertThat(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT))
                .overridingErrorMessage("The message with '%s' subject exists", RANDOM_EMAIL_SUBJECT)
                .isFalse();
    }

    @When("the current message is moved to Sent folder")
    public void verifyTheMessagePresenceInTheSentFolder() {

        mailbox.clickSentFolderButton();

        assertThat(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT))
                .overridingErrorMessage("The message with '%s' subject exists", RANDOM_EMAIL_SUBJECT)
                .isTrue();
    }

    @When("the user logs out from the system")
    public void logOutFromTheSystem() {

        mailbox.clickUserInfoButton();
        mailbox.clickLogOutButton();

        assertThat(loginPage.isChooseAnAccountMessageDisplayed())
                .overridingErrorMessage("The user isn't logged out from the system")
                .isTrue();
    }
}
