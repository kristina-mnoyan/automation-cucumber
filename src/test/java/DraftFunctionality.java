import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.InboxPage;
import pages.LoginPage;

public class DraftFunctionality extends BaseTest {

    LoginPage loginPage = new LoginPage();
    InboxPage inboxPage = new InboxPage();

    @Test
    public void successfullyLoginToGmailTest() {

        loginPage.get();
        loginPage.loginOperation();

        Assert.assertTrue(inboxPage.isWriteMessageButtonDisplayed());
    }

    @Test(dependsOnMethods = "successfullyLoginToGmailTest")
    public void createDraftMessageTest() {
        inboxPage.writeMessageOperation();
        inboxPage.clickCloseMessagePopUpButton();
        inboxPage.clickDraftFolderButton();

        Assert.assertTrue(inboxPage.isDraftMessageRowDisplayed());
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest"})
    public void sendTheDraftedMessageTest() {

        inboxPage.clickOnDraftMessage();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(inboxPage.getMessageToFieldValue(), data.getProperty("receiver.email.address"), "The email addresses don't match each other");
//        softAssert.assertEquals(inboxPage.getSubjectFieldValue(), randomEmailSubject, "The email subjects don't match each other");
        softAssert.assertEquals(inboxPage.getMessageText(), data.getProperty("message.text"), "The email texts don't match each other");

        softAssert.assertAll();

        inboxPage.clickSendButton();

        Assert.assertFalse(inboxPage.isFolderSubjectsListEmpty());
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest", "sendTheDraftedMessageTest"})
    public void checkSentFolderTest() {

        inboxPage.clickSentFolderButton();

        Assert.assertFalse(inboxPage.isFolderSubjectsListEmpty());
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createDraftMessageTest", "sendTheDraftedMessageTest", "checkSentFolderTest"})
    public void logOutFromTheSystemTest() {

        inboxPage.clickUserInfoButton();

        inboxPage.clickLogOutButton();

//        Assert.assertTrue(loginPage.isUserNameFieldDisplayed());
    }
}

