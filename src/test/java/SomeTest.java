import cucumber.api.CucumberOptions;
import org.testng.annotations.AfterMethod;
import ru.bool.cucumber.testng.CucumberTestNG;

import static ru.bool.selenium.webdriver.WebDriverFactory.driver;

/**
 * Created by bool on 08.09.17.
 */
@CucumberOptions(features = "classpath:features")
public class SomeTest extends CucumberTestNG {
    @AfterMethod
    public void quitDriver() {
        driver().quit();
    }
}
