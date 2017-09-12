package ru.bool.selenium.decorating;

import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import ru.bool.selenium.faces.Element;
import ru.bool.selenium.pageobject.PageObject;
import ru.bool.selenium.pageobject.Section;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class ByFieldDecorator extends AbstractFieldDecorator implements FieldDecorator {

    private final ElementLocatorFactory factory;

    public ByFieldDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Override
    protected Section decorateSection(PageObject obj, Field field) {
        try {
            Section section = (Section) field.getType().newInstance();
            section.setSelf(createProxy(obj.getClass().getClassLoader(), Element.class, factory.createLocator(field)));
            return section;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Element decorateElement(PageObject obj, Field field) {
        return createProxy(obj.getClass().getClassLoader(), field.getType(), factory.createLocator(field));
    }

    private Element createProxy(ClassLoader loader, Class type, ElementLocator locator) {
        return (Element) Proxy.newProxyInstance(loader, new Class[]{type}, new LocatingElementHandler(locator));
    }

}
