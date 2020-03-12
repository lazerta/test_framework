package utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

/**
 * some common utility
 */
public class Utility {
    public static void mergeCapacity(Map<String, String> map, DesiredCapabilities capabilities) {
        if (map == null) return;
        map.forEach((key, value) -> capabilities.setCapability(key, map));
    }

    public static String toCode(String str) {
        return "<code> " + str + "</code>";
    }

    public static String toStrong(String str) {
        return "<strong> " + str + "</code>";
    }

    public static String toBr(String str) {
        return "</br> " + str + "</br>";
    }

    public static String elementInfo(WebElement element) {
        return toBr("tag = " + element.getTagName() + toBr("Text =" + element.getText()));
    }

}
