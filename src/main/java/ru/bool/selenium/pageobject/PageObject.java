package ru.bool.selenium.pageobject;

import org.openqa.selenium.WebElement;
import ru.bool.annotations.Title;
import ru.bool.selenium.faces.Element;
import ru.bool.selenium.faces.PageFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PageObject {
    protected final Map<String, Element> fieldMap = new HashMap<>();
    protected final Map<String, Section> sectionMap = new HashMap<>();
    private final Logger logger;

    public PageObject() {
        logger = Logger.getLogger(getClass().getName());
    }

    public void init() {
        try {
            processFields(getClass());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processFields(Class clazz) throws IllegalAccessException {
        if (Page.class.equals(clazz))
            return;
        for (Field field : clazz.getDeclaredFields())
            processField(field);
        processFields(clazz.getSuperclass());
    }

    protected void processField(Field field) throws IllegalAccessException {
        Title annotaion = field.getAnnotation(Title.class);
        if (annotaion == null) return;
        String title = annotaion.value();
        if (WebElement.class.isAssignableFrom(field.getType()))
            addField(field, title);
        if (Section.class.isAssignableFrom(field.getType()))
            addSection(field, title);
    }

    private void addSection(Field field, String title) throws IllegalAccessException {
        if (sectionMap.containsKey(title)) {
            logger.info(String.format("Section '%s' already added to map", title));
            return;
        }
        field.setAccessible(true);
        sectionMap.put(title, (Section) field.get(this));
    }

    protected void addField(Field field, String title) throws IllegalAccessException {
        if (fieldMap.containsKey(title)) {
            logger.info(String.format("Field '%s' already added to map", title));
            return;
        }
        field.setAccessible(true);
        fieldMap.put(title, (Element) field.get(this));
    }

    Element getField(String title) {
        Element result = fieldMap.get(title);
        if (result == null)
            logger.info(String.format("Field '%s' not found in class", title));
        return fieldMap.get(title);
    }

    Section getSection(String title) {
        Section result = sectionMap.get(title);
        if (result == null)
            return null;
        PageFactory.initPageObject(result, result.self());
        if (result.self().waitDisplayed())
            return result;
        throw new AssertionError(String.format("Section '%s' is not displayed", title));
    }
}
