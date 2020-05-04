package pages;

import helpers.UiHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(partialLinkText = "Sign in")
    private WebElement signInButton;

    @FindBy(id = "identifierId")
    private WebElement userNameField;

    @FindBy(id = "identifierNext")
    private WebElement userNameNextButton;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(id = "passwordNext")
    private WebElement passwordNextButton;

    public void setUserName(String userName) {
        userNameField.sendKeys(userName);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void loginOperation() {
        signInButton.click();
        UiHelper.switchToCurrentTab();
        setUserName(app.getProperty("username"));
        userNameNextButton.click();
        setPassword(app.getProperty("password"));
        passwordNextButton.click();
    }

    public boolean isUserNameFieldDisplayed() {
        return userNameField.isDisplayed();
    }
}
