package config;


import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

@Data
public class Config {
    private static Config config = null;
    private Browser browser = new Browser();
    private Env env = new Env();
    public static String resourcePath =
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
                    "resources" + File.separator;
    public static String testResource =
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator +
                    "resources" + File.separator;

    private Config() {
    }


    public static Config getInstance() {
        if (config == null) {
            load();

        }
        return config;
    }

    private synchronized static void load() {
        Yaml yaml = new Yaml();
        try (FileInputStream in = new FileInputStream(resourcePath + "application.yaml")) {
            config = yaml.loadAs(in, Config.class);
        } catch (IOException e) {

            config = new Config();
        }
    }
}
