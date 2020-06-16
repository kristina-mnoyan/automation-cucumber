package cucumber.testng.steps;

import io.cucumber.java.en.When;
import net.bytebuddy.utility.RandomString;
import pages.Mailbox;
import utils.CommonUtils;
import utils.WaitUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduledMessageSteps {

    private final String SCHEDULED_DATE = CommonUtils.getFutureDateTime(4, "dd MMM, y թ.");
    private final String SCHEDULED_TIME = CommonUtils.getFutureDateTime(4, "HH:mm");
    private final String RANDOM_EMAIL_SUBJECT = RandomString.make(10);
    Mailbox mailbox = new Mailbox();

    @When("the user sets specific parameters for scheduled message")
    public void setSpecificTimeToSendTheMessage() {

        mailbox.scheduledSendingOperation(SCHEDULED_DATE, SCHEDULED_TIME);
    }

    @When("the message is sent to Scheduled folder")
    public void verifyScheduledMessageInScheduledFolder() {

        mailbox.clickScheduledFolderButton();
        WaitUtils.hardWait(20000);

        assertThat(mailbox.subjectTexts().contains(RANDOM_EMAIL_SUBJECT))
                .overridingErrorMessage("The message with '%s' subject doesn't exist", RANDOM_EMAIL_SUBJECT)
                .isTrue();
    }

    @When("is sent to recipient after the specific time set")
    public void verifyTheMessageAfterSchedule() {

        mailbox.clickInboxFolderButton();
        WaitUtils.hardWait(250000);

        assertThat(mailbox.isMailUnread(RANDOM_EMAIL_SUBJECT))
                .overridingErrorMessage("The message with '%s' subject doesn't exist", RANDOM_EMAIL_SUBJECT)
                .isTrue();
    }
}
