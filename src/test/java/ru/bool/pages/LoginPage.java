package ru.bool.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.bool.annotations.Title;
import ru.bool.selenium.pageobject.Page;

/**
 * Created by bool on 08.09.17.
 */
@Title("Авторизация")
public class LoginPage extends Page {

    @Title("Логин")
    @FindBy(xpath=".//input[@name='j_username']")
    WebElement loginField;

    @Title("Пароль")
    @FindBy(xpath = ".//input[@name='j_password']")
    WebElement passwordField;

    @Title("войти")
    @FindBy(xpath = ".//span[@name='Submit']//button")
    WebElement doLogin;
}
