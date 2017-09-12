package ru.bool.selenium.webdriver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.Augmenter;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.cucumberjvm.callback.OnFailureCallback;
import ru.yandex.qatools.allure.events.MakeAttachmentEvent;
import static ru.bool.selenium.webdriver.WebDriverFactory.driver;

/**
 * Created by bool on 09.09.17.
 */
public class ScreenshotCallback implements OnFailureCallback {
    @Override
    public Object call() {
        TakesScreenshot takesScreenshot = (TakesScreenshot) new Augmenter().augment(driver());
        byte[] bytes = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        Allure.LIFECYCLE.fire(new MakeAttachmentEvent(bytes, "screenshot", "image/jpeg"));
        return "screenshot attached";
    }
}
