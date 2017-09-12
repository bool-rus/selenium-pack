package ru.bool.selenium.decorating;

import org.openqa.selenium.support.FindBy;
import ru.bool.selenium.faces.Element;
import ru.bool.selenium.pageobject.PageObject;
import ru.bool.selenium.pageobject.Section;

import java.lang.reflect.Field;

public abstract class AbstractFieldDecorator implements FieldDecorator {

    @Override
    public Object decorate(PageObject pageObject,Field field) {
        Class type = field.getType();
        if (Element.class.isAssignableFrom(type))
            return decorateElement(pageObject, field);
        if (Section.class.isAssignableFrom(type))
            return decorateSection(pageObject, field);
        throw new IllegalArgumentException("Field to decoration must be assignable from Element or Section");
    }
    public boolean isDecorable(Field field) {
        return field.getAnnotation(FindBy.class) != null;
    }

    protected abstract Section decorateSection(PageObject value, Field field) ;

    protected abstract Element decorateElement(PageObject value, Field field) ;
}
