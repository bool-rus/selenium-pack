package ru.bool.selenium.pageobject;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.bool.annotations.MustDisplayed;
import ru.bool.annotations.Title;
import ru.bool.selenium.faces.Element;

import java.lang.reflect.Field;

import static org.testng.Assert.assertTrue;

/**
 * Created by bool on 08.09.17.
 */
public class Page extends PageObject {
    protected String classTitle;

    @Override
    public void init() {
        Title annotation = getClass().getAnnotation(Title.class);
        if(annotation != null)
            classTitle = annotation.value();
        else
            classTitle = "UNKNOWN";
        super.init();
    }

    protected void checkVisible(Field field) throws IllegalAccessException {
        field.setAccessible(true);
        String assertMsg = String.format("No such page: %s", classTitle);
        Element element = (Element) field.get(this);
        try {
            assertTrue(element.waitDisplayed(), assertMsg);
        } catch (NoSuchElementException e) {
            throw new AssertionError(assertMsg);
        }
    }

    @Override
    protected void processField(Field field) throws IllegalAccessException {
        super.processField(field);
        if (field.getAnnotation(MustDisplayed.class) != null)
            checkVisible(field);
    }


}
