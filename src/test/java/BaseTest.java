import lombok.SneakyThrows;
import org.testng.annotations.AfterClass;
import setup.DriverFactory;

public class BaseTest {

    @SneakyThrows
    @AfterClass
    public void browserTearDown() {
//        Thread.sleep(200000);
        DriverFactory.quitDriver();
    }
}
