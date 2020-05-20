package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.UiUtils;

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

    @FindBy(xpath = "//span[text()='Ընտրեք հաշիվ']")
    private WebElement chooseAnAccount;

    public void setUserName(String userName) {
        userNameField.sendKeys(userName);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void loginOperation() {
        signInButton.click();
        UiUtils.switchToCurrentTab();
        setUserName(app.getProperty("USERNAME"));
        userNameNextButton.click();
        setPassword(app.getProperty("PASSWORD"));
        passwordNextButton.click();
    }

    public boolean isUserNameFieldDisplayed() {
        return userNameField.isDisplayed();
    }

    public boolean isChooseAnAccountMessageDisplayed() {
        return chooseAnAccount.isDisplayed();
    }
}
