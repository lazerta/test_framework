package base;

import config.Config;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utility;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserDriver {
    public static WebDriver driver = null;


    @BeforeMethod
    public void setUp() throws MalformedURLException {
        Config config = Config.getInstance();
        if (config.getEnv().isCloud()) {
            driver = getCloudDriver(config);
            return;
        }
        driver = getLocalDriver(config);

    }

    private WebDriver getLocalDriver(Config config) {
        String browserName = config.getBrowser().getName();
        String os = config.getEnv().getOs().toLowerCase();

        if (browserName.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--incognito");
            options.addArguments(config.getBrowser().getOptions());


            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            Utility.mergeCapacity(config.getEnv().getCapability(), capabilities);

            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/drivers/windows/chromedriver.exe");
                return new ChromeDriver(options);

            }
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/drivers/mac/chromedriver");
                return new ChromeDriver(options);
            }
        }
        if (browserName.equalsIgnoreCase("firefox")) {

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--private");
            options.addArguments(config.getBrowser().getOptions());
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            Utility.mergeCapacity(config.getEnv().getCapability(), capabilities);


            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/drivers/windows/geckodriver.exe");
                return new FirefoxDriver(options);
            }
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/drivers/mac/geckodriver");
                return new FirefoxDriver(options);
            }
        }

        throw new IllegalArgumentException("in valid operating system or browser name");

    }

    private WebDriver getCloudDriver(Config config) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities(config.getEnv().getCapability());
        caps.setCapability("browser", config.getBrowser().getName());
        caps.setCapability("browser_version", config.getBrowser().getVersion());
        caps.setCapability("os", config.getEnv().getOs());
        caps.setCapability("os_version", config.getEnv().getOsVersion());

        if (config.getEnv().getCloudName().equalsIgnoreCase("saucelabs")) {
            return new RemoteWebDriver(new URL("http://" + config.getEnv().getCloudUsername() + ":" + config.getEnv().getCloudAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), caps);
        }
        if (config.getEnv().getCloudName().equalsIgnoreCase("Browserstack")) {
            return new RemoteWebDriver(new URL("http://" + config.getEnv().getCloudUsername() + ":" + config.getEnv().getCloudAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), caps);
        }

        throw new IllegalArgumentException("cloud driver of name: " + config.getEnv().getCloudName() + " is not " +
                "supported");


    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
