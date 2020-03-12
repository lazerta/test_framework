package config;


import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
/**
 * @author shawn
 *  dummy class for loading configuration
 */
@Data
public class Config {
    private static Config config = null;
    private Browser browser;
    private Env env;
    public static String resourcePath =
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
                    "resources" + File.separator;
    public static String testResource =
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator +
                    "resources" + File.separator;


    public static Config getInstance() {
        if (config == null) {
            load();
        }
        return config;
    }

    /**
     * load application.yaml from src/main/resources and  init with default setting if file is not present
     */
    private synchronized static void load() {
        Yaml yaml = new Yaml();
        try (FileInputStream in = new FileInputStream(resourcePath + "application.yaml")) {
            config = yaml.loadAs(in, Config.class);
            if (config == null) {
                throw new IllegalArgumentException("failed to parse file. Check if it is empty");
            }
        } catch (IOException e) {
            config = new Config();
        }
        if (config.getEnv() ==  null){
            config.setEnv(new Env());
        }
        if (config.getBrowser() == null){
            config.setBrowser(new Browser());
        }

    }
}
