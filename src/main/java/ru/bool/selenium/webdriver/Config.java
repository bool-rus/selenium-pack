package ru.bool.selenium.webdriver;

/**
 * Created by bool on 08.09.17.
 */
public interface Config {
    Long WAIT_TIME = Long.valueOf(System.getProperty("driver.wait-time","10"));
}
