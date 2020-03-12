package config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 *  dummy class for loading configuration
 */
@Data
public final class Browser {
    private String name = "chrome";
    private String version = "80.0.3987.116";
    private List<String> options = new ArrayList<>();

}
