package cucumber.testng.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import setup.DriverFactory;
import utils.UiUtils;

public class CucumberHooks extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        super.onTestFailure(iTestResult);
        UiUtils.getScreenshot(iTestResult);
    }

    @Before("@ui")
    public void startBrowser() {
        DriverFactory.getInstance();
    }

    @After("@ui")
    public void browserTearDown() {
//        DriverFactory.quitDriver();
    }
}
