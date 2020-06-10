import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import setup.DriverFactory;
import utils.UiUtils;

public class BaseTest extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        super.onTestFailure(iTestResult);
        UiUtils.getScreenshot(iTestResult);
    }

    @AfterClass
    public void browserTearDown() {
        DriverFactory.quitDriver();
    }
}
