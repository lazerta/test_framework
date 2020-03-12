package utils;

import base.BrowserDriver;
import config.Config;
import config.Env;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reporter.TestLogger;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Utility class for all selenium framework
 * @author shawn
 *
 */
public class BrowserUtil {
    /**
     *
     * @param timer time in second
     * @param condition  the condition that is waiting for
     */
    public static void waitFor(int timer, ExpectedCondition<?> condition) {
        WebDriverWait wait = new WebDriverWait(BrowserDriver.driver, timer);
        wait.until(condition);
    }

    /**
     *
     * @param element  a clickable WebElement
     * @see WebElement
     *
     */
    public static void click(WebElement element) {

        TestLogger.log("[click]" + Utility.elementInfo(element));
         element.click();
    }

    /**
     *
     * @param element WebElement
     * @return text present in the element
     * this element uses this method {@link WebElement#getText()}
     */
    public static String getText(WebElement element) {
        return element.getText();
    }


    /**
     *
     * @param time in second
     *    force current thread to sleep for some time
     */
    public static void sleep(int time) {
        TestLogger.log("Sleep for " + time + " seconds");

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param script javascript code
     * @return result of the javascript code
     */
    public static Object runScript(String script) {
        TestLogger.log("[running javascript]  " + Utility.toCode(script));
        return BrowserUtil.getScriptExecutor().executeScript(script);

    }

    /**
     *  wait for page to load to by checking document readyState
     *  if the  element is already load it will return immediately,
     *  otherwise wait for page to load for max  {@linkplain Env#getPageLoadTimeout()}
     *
     *
     */
    public static void waitPageLoad() {
        TestLogger.log("waiting for page to load");
        waitFor(Config.getInstance().getEnv().getPageLoadTimeout(),
                (driver) -> getScriptExecutor().executeScript("return document.readyState").equals("complete"));

    }

    /**
     *
     * @return Executor for javascript
     * @see JavascriptExecutor
     */
    public static JavascriptExecutor getScriptExecutor() {
        return (JavascriptExecutor) BrowserDriver.driver;

    }

    /**
     *
     * @param elements
     * clear input data of web element
     */
    public static void clear(WebElement... elements) {
        Arrays.stream(elements).forEach(WebElement::clear);

    }
}
