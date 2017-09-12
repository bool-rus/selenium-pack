package ru.bool.selenium.decorating;

import ru.bool.selenium.pageobject.PageObject;

import java.lang.reflect.Field;

public interface FieldDecorator {
    Object decorate(PageObject object, Field field);
    boolean isDecorable(Field field);
}
