package utils;

import base.BrowserDriver;
import config.Config;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class BrowserUtil {
    // private static WebDriver driver = BrowserDriver.driver;

    public static void waitFor(int timer, ExpectedCondition<?> condition) {
        WebDriverWait wait = new WebDriverWait(BrowserDriver.driver, timer);
        wait.until(condition);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Object runScript(String script) {
        return BrowserUtil.getScriptExecutor().executeScript(script);

    }

    public static Object runScript(String script, Object... object) {
        return BrowserUtil.getScriptExecutor().executeScript(script, object);

    }

    public static void waitPageLoad() {

        waitFor(Config.getInstance().getEnv().getPageLoadTimeout(),
                (driver) -> runScript("return document.readyState").equals("complete"));

    }

    public static JavascriptExecutor getScriptExecutor() {
        return (JavascriptExecutor) BrowserDriver.driver;

    }

    public static void clear(WebElement... elements) {
        Arrays.stream(elements).forEach(WebElement::clear);

    }
}
