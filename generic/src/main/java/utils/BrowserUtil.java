package utils;

import base.BrowserDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BrowserUtil {
    public static void waitFor(int timer, ExpectedCondition<?> condition) {
        WebDriver driver = BrowserDriver.driver;
        WebDriverWait wait = new WebDriverWait(driver, timer);
        wait.until(condition);
    }
    public static void  sleep(int time){
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
