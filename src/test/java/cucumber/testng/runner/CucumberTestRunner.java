package cucumber.testng.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-reports",
                "json:target/cucumber-reports/CucumberTests.json",
                "testng:target/cucumber-reports/CucumberTests.xml"},
        monochrome = true,
        tags = "",
        glue = "cucumber.testng",
        features = "src/test/resources/features"
)

public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
