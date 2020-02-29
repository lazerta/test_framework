package config;

import base.BrowserDriver;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

@Data
public class Config {
    private  static  Config config = null;
    private  Browser browser;
    private Env env;
    public static String resourcePath =
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
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
        // String path = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+
        //         "resources";
        // System.out.println("path = " + path);
        // InputStream in = BrowserDriver.class.getClassLoader().getResourceAsStream("application.yaml");
        try (FileInputStream in = new FileInputStream(resourcePath + "application.yaml")) {
            config = yaml.loadAs(in, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
