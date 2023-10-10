package org.hexagonexample;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/application-test/resources/features",
        plugin = {"pretty", "html:target/cucumber"},
        glue = "org.hexagonexample"
)
public class ApplicationTestRunner {
}
