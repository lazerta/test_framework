package base;

import com.aventstack.extentreports.ExtentReports;
import config.Config;
import lombok.Data;
import lombok.SneakyThrows;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BrowserDriver {
    public static WebDriver driver = null;
    public static Config config = Config.getInstance();
    public static ExtentReports reporter;

    public void setupReporter(ITestContext context) {

    }


    @BeforeMethod
    public void setUp() throws MalformedURLException {


        if (config.getEnv().isCloud()) {
            driver = getCloudDriver(config);
        } else {
            driver = getLocalDriver(config);
        }

        driver.manage().timeouts().implicitlyWait(config.getEnv().getImplicitWaitTime(), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(config.getEnv().getPageLoadTimeout(), TimeUnit.SECONDS);
        driver.get(config.getEnv().getUrl());


    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public static void takeScreenShot(WebDriver driver, String screenshotName) {
        DateFormat formatter = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,
                    new File(config.getEnv().getScreenshotPath() + screenshotName + "_" + formatter.format(date) + ".png"));
            System.out.println("Screenshot captured");
        } catch (IOException e) {
            System.err.println("Exception while taking screenshot " + e.getMessage());
        }


    }

    private WebDriver getLocalDriver(Config config) {
        String windowsDriverPath = Config.resourcePath+"driver"+File.separator+"windows"+File.separator;
        String macDriverPath = Config.resourcePath+"driver"+File.separator+"mac"+File.separator;

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
                System.setProperty("webdriver.chrome.driver", windowsDriverPath + "chromedriver.exe");
                return new ChromeDriver(options);

            }
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.chrome.driver", macDriverPath+"chromedriver");
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
                System.setProperty("webdriver.gecko.driver", windowsDriverPath+"geckodriver.exe");
                return new FirefoxDriver(options);
            }
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.gecko.driver", macDriverPath+"geckodriver");
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
    public void tearDown() {
        driver.quit();
    }
}
