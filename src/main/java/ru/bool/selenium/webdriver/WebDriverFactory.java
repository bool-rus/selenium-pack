package ru.bool.selenium.webdriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.allure.cucumberjvm.AllureReporter;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bool on 08.09.17.
 */
public class WebDriverFactory {
    private final static WebDriverFactory INSTANCE = new WebDriverFactory();
    private final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    private WebDriverFactory() {
        AllureReporter.applyFailureCallback(ScreenshotCallback.class);
    }

    public static RemoteWebDriver driver() {
        return INSTANCE.getDriver();
    }

    private RemoteWebDriver getDriver() {
        RemoteWebDriver result = driver.get();
        if (result != null && result.getSessionId() != null) return result;
        try {
            result = generateWebDriver();
            driver.set(result);
            return result;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private RemoteWebDriver generateWebDriver() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        return new RemoteWebDriver(
                new URL(System.getProperty("selenium.grid.url", "http://localhost:4444/wd/hub/")),
                capability
        );
    }
}
