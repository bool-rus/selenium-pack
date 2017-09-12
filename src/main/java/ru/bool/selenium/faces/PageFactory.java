package ru.bool.selenium.faces;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import ru.bool.selenium.decorating.ByFieldDecorator;
import ru.bool.selenium.decorating.FieldDecorator;
import ru.bool.selenium.decorating.WaitReloadDecorator;
import ru.bool.selenium.pageobject.PageObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface PageFactory {
    static void initPageObject(PageObject pageObject, SearchContext context) {
        List<FieldDecorator> decorators = new ArrayList<>();
        decorators.add(new ByFieldDecorator(new DefaultElementLocatorFactory(context)));
        decorators.add(new WaitReloadDecorator());

        List<Field> fields = new ArrayList<>();
        Class<?> proxyIn = pageObject.getClass();
        while (proxyIn != PageObject.class) {
            fields.addAll(Arrays.asList(proxyIn.getDeclaredFields()));
            proxyIn = proxyIn.getSuperclass();
        }

        for(FieldDecorator decorator : decorators)
            for(Field field : fields)
                if(decorator.isDecorable(field))
                    decorator.decorate(pageObject, field);
    }
}
