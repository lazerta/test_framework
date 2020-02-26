package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class Utility {
    public static void mergeCapacity(Map<String, String> map, DesiredCapabilities capabilities) {
        if (map == null) return;
        map.forEach((key, value) -> capabilities.setCapability(key, map));
    }
}
