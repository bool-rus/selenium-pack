package ru.bool.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.bool.annotations.MustDisplayed;
import ru.bool.annotations.Title;
import ru.bool.selenium.pageobject.Page;

/**
 * Created by bool on 08.09.17.
 */
@Title("Главная страница")
public class MainPage extends Page {
    @MustDisplayed
    @Title("выход")
    @FindBy(xpath = ".//a[@href='/logout']")
    WebElement logoutBtn;
}
