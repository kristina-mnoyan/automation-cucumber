package cucumber.testng.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.Mailbox;
import utils.CommonUtils;

import static org.assertj.core.api.Assertions.assertThat;


public class SearchMessagesSteps {

    Mailbox mailbox = new Mailbox();

    @When("^the user goes to the \"(.*)\" folder$")
    public void goToTheFolder(String folderName) {

        mailbox.goToFolder(folderName);
    }

    @Then("^the last part of the URL is same as \"(.*)\"$")
    public void verifyUrlWithFolderName(String folderName) {

        String lastPartOfURL = CommonUtils.getLastPartFromCurrentUrl();

        assertThat(CommonUtils.isLastPartFromUrlEqualToFolderName(folderName))
                .overridingErrorMessage("The last part of URL " + lastPartOfURL + " is not equal to " + folderName)
                .isTrue();
    }
}
