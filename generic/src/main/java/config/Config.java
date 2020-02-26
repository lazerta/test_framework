package config;

import base.BrowserDriver;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

@Data
public class Config {
    private static Config config = null;
    private Browser browser;
    private Env env;

    private Config() {
    }



    public static Config getInstance() {
        if (config == null) {
            Yaml yaml = new Yaml();
            InputStream in = BrowserDriver.class.getClassLoader().getResourceAsStream("application.yaml");
            config = yaml.loadAs(in, Config.class);
        }
        return config;
    }
}
