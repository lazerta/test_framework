package config;

import base.BrowserDriver;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author shawn
 *  dummy class for loading configuration
 */
@Data
public final class Env {
    private String os = "windows";
    private String osVersion = "10";
    private boolean isCloud = false;
    private String cloudName;
    private String url = "https://www.google.com";
    private int implicitWaitTime = 0;
    private int pageLoadTimeout = 300;
    private String cloudUsername;
    private String cloudAccessKey;
    private Map<String, String> capability = new HashMap<>();
    private String screenshotPath = System.getProperty("user.dir") + "/screenshots/";
    private String reportOutputDirectory = null;
    private Map<String,String> SystemInfo = new HashMap<>();



}
