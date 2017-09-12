package ru.bool.selenium.pageobject;

import ru.bool.selenium.faces.Element;

public class Section extends PageObject {
    private Element self;
    public Element self() {
        return self;
    }
    public void setSelf(Element element) {
        self = element;
    }
}
