package ru.bool.reflection;

import org.openqa.selenium.WebElement;
import ru.bool.selenium.faces.Element;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WaitReloadHandler implements InvocationHandler {
    private final Element delegate;
    private final Element reload;
    private final String attribute;

    public WaitReloadHandler(Element delegate, Element reload, String attribute) {
        this.delegate = delegate;
        this.reload = reload;
        this.attribute = attribute;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(needWait(method))  try {
            String currentValue = reload.getAttribute(attribute);
            Object result = method.invoke(delegate, args);
            reload.waitForAttributeChanged(attribute,currentValue);
            return result;
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
        return method.invoke(delegate, args);
    }

    private boolean needWait(Method method) {
        //TODO: доработать
        return method.getName().contains("set");
    }
}
