import cucumber.api.DataTable;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Также;
import cucumber.api.java.ru.То;
import static ru.bool.cucumber.feature.FeatureRunner.instance;
import ru.bool.selenium.pageobject.Page;
import ru.bool.selenium.pageobject.PageObjectStorage;

import static ru.bool.selenium.pageobject.PageObjectStorage.storage;
import static ru.bool.selenium.webdriver.WebDriverFactory.driver;

/**
 * Created by bool on 08.09.17.
 */
public class BaseSteps {


    @Если("^открываю ссылку \"([^\"]*)\"$")
    public void openUrl(String url) throws Throwable {
        driver().manage().deleteAllCookies();
        driver().get(url);
    }

    @Также("^заполняю поля:$")
    public void fillFields(DataTable params) throws Throwable {
        params.getGherkinRows().forEach(row -> {
            String name = row.getCells().get(0);
            String value = row.getCells().get(1);
            storage().getField(name).sendKeys(value);
        });
    }

    @То("^вижу страницу \"([^\"]*)\"$")
    public void pageDisplayed(String page) throws Throwable {
        storage().switchToPage(page);
    }

    @Также("^жму кнопку \"([^\"]*)\"$")
    public void pressButton(String button) throws Throwable {
        storage().getField(button).click();
    }

    @Дано("^выполняю шаблон \"([^\"]*)\" из функции \"([^\"]*)\":$")
    public void runOutline(String arg0, String arg1, DataTable dataTable) throws Throwable {
        instance().runOutline(arg0,dataTable,arg1);
    }
}
