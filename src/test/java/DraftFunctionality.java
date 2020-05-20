import config.PropertyLoader;
import net.bytebuddy.utility.RandomString;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.Mailbox;

public class DraftFunctionality extends BaseTest {

    private final String RANDOM_EMAIL_SUBJECT = RandomString.make(10);
    private final PropertyLoader data = new PropertyLoader("data.properties");
    LoginPage loginPage = new LoginPage();
    Mailbox mailbox = new Mailbox();

    @Test
    public void successfullyLoginToGmailTest() {

        loginPage.get();
        loginPage.loginOperation();

        Assert.assertFalse(mailbox.isWriteMessageButtonDisplayed());
    }

    @Test(dependsOnMethods = "successfullyLoginToGmailTest")
    public void createDraftMessageTest() {

        mailbox.writeMessageOperation(RANDOM_EMAIL_SUBJECT);
        mailbox.clickCloseMessagePopUpButton();
        mailbox.clickDraftFolderButton();

        Assert.assertTrue(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT));
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest"})
    public void sendTheDraftedMessageTest() {

        mailbox.clickOnDraftMessage();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(mailbox.getMessageToFieldValue(), data.getProperty("RECEIVER_EMAIL_ADDRESS"), "The email addresses don't match each other");
        softAssert.assertEquals(mailbox.getSubjectFieldValue(), RANDOM_EMAIL_SUBJECT, "The email subjects don't match each other");
        softAssert.assertEquals(mailbox.getMessageText(), data.getProperty("MESSAGE_TEXT"), "The email texts don't match each other");

        softAssert.assertAll();

        mailbox.clickSendButton();

        Assert.assertFalse(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT));
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest", "sendTheDraftedMessageTest"})
    public void checkSentFolderTest() {

        mailbox.clickSentFolderButton();

        Assert.assertTrue(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT));
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest", "sendTheDraftedMessageTest", "checkSentFolderTest"})
    public void logOutFromTheSystemTest() {

        mailbox.clickUserInfoButton();
        mailbox.clickLogOutButton();

        Assert.assertTrue(loginPage.isChooseAnAccountMessageDisplayed());
    }
}

