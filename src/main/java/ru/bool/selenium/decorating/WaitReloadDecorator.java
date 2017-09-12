package ru.bool.selenium.decorating;


import ru.bool.annotations.WaitReload;
import ru.bool.reflection.WaitReloadHandler;
import ru.bool.selenium.faces.Element;
import ru.bool.selenium.pageobject.PageObject;
import ru.bool.selenium.pageobject.Section;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class WaitReloadDecorator extends AbstractFieldDecorator implements FieldDecorator {
    @Override
    protected Section decorateSection(PageObject pageObject, Field field) {
        throw new UnsupportedOperationException("Cannot decorate section");
    }

    @Override
    public boolean isDecorable(Field field) {
        return super.isDecorable(field) &&
                Element.class.isAssignableFrom(field.getType()) &&
                field.getAnnotation(WaitReload.class) != null;
    }

    @Override
    protected Element decorateElement(PageObject pageObject, Field field) {
        try {
            WaitReload waitReload = field.getAnnotation(WaitReload.class);
            Element delegate = (Element) field.get(pageObject);
            Element reloadingElement = (Element) findField(
                    pageObject.getClass(),
                    waitReload.element()
            ).get(pageObject);
            return (Element) Proxy.newProxyInstance(
                    pageObject.getClass().getClassLoader(),
                    new Class[]{field.getType()},
                    new WaitReloadHandler(delegate, reloadingElement, waitReload.attribute())
            );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Field findField(Class clazz , String name) throws NoSuchFieldException {
        if(PageObject.class.equals(clazz))
            throw new NoSuchFieldException("Field not found");
        try{
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return findField(clazz.getSuperclass(),name);
        }
    }
}
