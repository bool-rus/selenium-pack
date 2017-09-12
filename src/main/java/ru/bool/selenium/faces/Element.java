package ru.bool.selenium.faces;

import org.openqa.selenium.WebElement;

public interface Element extends WebElement {

    boolean waitForAttributeChanged(String attribute, String previousValue);

    boolean waitDisplayed();

    boolean waitHidden();
}
