import org.testng.annotations.AfterClass;
import setup.DriverFactory;

public class BaseTest {

    @AfterClass
    public void browserTearDown() {
        DriverFactory.quitDriver();
    }
}
