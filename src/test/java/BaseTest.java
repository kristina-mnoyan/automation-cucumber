import config.PropertyLoader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected PropertyLoader data = new PropertyLoader("src/main/resources/data.properties");


    @AfterClass
    public void browserTearDown() {
//        DriverFactory.quitDriver();
    }
}
