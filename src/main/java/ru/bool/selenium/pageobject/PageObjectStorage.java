package ru.bool.selenium.pageobject;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import ru.bool.annotations.Title;
import ru.bool.selenium.faces.Element;
import ru.bool.selenium.faces.PageFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static ru.bool.selenium.webdriver.WebDriverFactory.driver;

/**
 * Created by bool on 08.09.17.
 */
public class PageObjectStorage {
    private static final Map<String, Class<? extends Page>> pageMap = new HashMap<>();
    private static final ThreadLocal<PageObjectStorage> INSTANCES = new ThreadLocal<>();
    private final Stack<PageObject> pageStack = new Stack<>();

    static {
        String rootPackage = System.getProperty("project.package", "ru.bool");
        ClassLoader classLoader = PageObjectStorage.class.getClassLoader();
        ClassFinder classFinder = new ResourceLoaderClassFinder(
                new MultiLoader(classLoader),
                classLoader
        );
        classFinder.getDescendants(Page.class, rootPackage).stream().
                filter(c -> c.getAnnotation(Title.class) != null).
                forEach(PageObjectStorage::addClass);
    }

    private static void addClass(Class clazz) {
        Title annotation = (Title) clazz.getAnnotation(Title.class);
        String title = annotation.value();
        if (pageMap.containsKey(title))
            throw new RuntimeException(
                    String.format("Title '%s' used by more than one pages: '%s' and '%s'", title, pageMap.get(title), clazz)
            );
        pageMap.put(title, clazz);
    }

    private PageObjectStorage() {

    }

    public static PageObjectStorage storage() {
        PageObjectStorage result = INSTANCES.get();
        if (result != null) return result;
        result = new PageObjectStorage();
        INSTANCES.set(result);
        return result;
    }

    public void switchToPage(String title) {
        try {
            Page result = pageMap.get(title).newInstance();
            PageFactory.initPageObject(result,driver());
            pageStack.clear();
            pageStack.push(result);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToSection(String title) {
        if(pageStack.isEmpty())
            throw new IllegalStateException("Object stack is empty");
        Section section = pageStack.peek().getSection(title);
        if(section == null) {
            pageStack.pop();
            switchToSection(title);
        } else {
            pageStack.push(section);
        }
    }

    public Element getField(String title) {
        if(pageStack.isEmpty())
            throw new IllegalStateException("Object stack is empty");
        Element result = pageStack.peek().getField(title);
        if(result != null)
            return result;
        pageStack.pop();
        return getField(title);
    }

}
