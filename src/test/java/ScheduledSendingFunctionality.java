import lombok.SneakyThrows;
import net.bytebuddy.utility.RandomString;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.Mailbox;
import utils.CommonUtils;
import utils.WaitUtils;

@Listeners(BaseTest.class)
public class ScheduledSendingFunctionality extends BaseTest {

    private final String SCHEDULED_DATE = CommonUtils.getFutureDateTime(4, "dd MMM, y թ.");
    private final String SCHEDULED_TIME = CommonUtils.getFutureDateTime(4, "HH:mm");
    private final String RANDOM_EMAIL_SUBJECT = RandomString.make(10);
    LoginPage loginPage = new LoginPage();
    Mailbox mailbox = new Mailbox();

    @Test
    public void successfullyLoginToGmailTest() {

        loginPage.get();
        loginPage.loginOperation();

        Assert.assertFalse(mailbox.isWriteMessageButtonDisplayed());
    }

    @SneakyThrows
    @Test(dependsOnMethods = "successfullyLoginToGmailTest")
    public void createScheduledMessageTest() {

        mailbox.writeMessageOperation(RANDOM_EMAIL_SUBJECT);
        mailbox.scheduledSendingOperation(SCHEDULED_DATE, SCHEDULED_TIME);
        mailbox.clickScheduledFolderButton();
        WaitUtils.hardWait(15000);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT));

        softAssert.assertAll();
    }

    @SneakyThrows
    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createScheduledMessageTest"})
    public void sendScheduledMessageTest() {
        mailbox.clickInboxFolderButton();
        WaitUtils.hardWait(240000);

        Assert.assertTrue(mailbox.isMailUnread(RANDOM_EMAIL_SUBJECT));
    }

    @Test(dependsOnMethods = {"successfullyLoginToGmailTest", "createScheduledMessageTest", "sendScheduledMessageTest"})
    public void logOutFromTheSystemTest() {

        mailbox.clickUserInfoButton();
        mailbox.clickLogOutButton();

        Assert.assertTrue(loginPage.isChooseAnAccountMessageDisplayed());
    }
}
