package base;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import config.Config;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import reporter.ExtentManager;
import reporter.ReportTestManager;
import utils.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class BrowserDriver {
    public static WebDriver driver = null;
    private static Config config;
    private static ExtentReports extent;

    protected BrowserDriver() {
    }

    @BeforeSuite
    public void reportSetUp(ITestContext context) throws IOException {
        config = Config.getInstance();
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
        File screenshotFolder = new File(config.getEnv().getScreenshotPath());
        if (screenshotFolder.exists()) {
            FileUtils.cleanDirectory(screenshotFolder);
        }
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        ReportTestManager.startTest(method.getName());
        ReportTestManager.getTest().assignCategory(className);
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
        // driver.manage().deleteAllCookies();


    }

    protected String getStackTrace(Throwable t) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return "<br/>" + sw.toString().replace(System.lineSeparator(), "<br/>");
    }

    public static String takeScreenShot(WebDriver driver, String screenshotName) {
        DateFormat formatter = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
        String name = null;
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            name = screenshotName + "_" + formatter.format(new Date()) + "_.png";
            String location =
                    config.getEnv().getScreenshotPath() + name;
            System.out.println("Screenshot captured");
            FileUtils.copyFile(file, new File(location));


        } catch (IOException e) {
            System.err.println("Exception while taking screenshot " + e.getMessage());
        }

        return name;
    }

    private WebDriver getLocalDriver(Config config) {
        DesiredCapabilities common = new DesiredCapabilities();
        common.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        String windowsDriverPath = Config.resourcePath + "driver" + File.separator + "windows" + File.separator;
        String macDriverPath = Config.resourcePath + "driver" + File.separator + "mac" + File.separator;

        String browserName = config.getBrowser().getName();
        String os = config.getEnv().getOs().toLowerCase();

        if (browserName.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--incognito");
            options.addArguments(config.getBrowser().getOptions());


            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.merge(common);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            Utility.mergeCapacity(config.getEnv().getCapability(), capabilities);

            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", windowsDriverPath + "chromedriver.exe");
                return new ChromeDriver(options);

            }
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.chrome.driver", macDriverPath + "chromedriver");
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
            capabilities.merge(common);
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            Utility.mergeCapacity(config.getEnv().getCapability(), capabilities);


            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", windowsDriverPath + "geckodriver.exe");
                return new FirefoxDriver(options);
            }
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.gecko.driver", macDriverPath + "geckodriver");
                return new FirefoxDriver(options);
            }
        }

        throw new IllegalArgumentException("in valid operating system or browser name");

    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ReportTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ReportTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ReportTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            ReportTestManager.getTest().log(LogStatus.PASS, "Test Passed");

        }
        if (result.getStatus() == ITestResult.FAILURE) {


            String screenShot = takeScreenShot(driver, result.getName());
            ReportTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
            ReportTestManager.getTest().log(LogStatus.FAIL, getMethodArgumentInfo(result));


            screenShot = screenShot.replace('\\', '/');
            System.out.println("screenShot = " + screenShot);

            ReportTestManager.getTest().log(LogStatus.FAIL,
                    ReportTestManager.getTest().addScreenCapture("../screenshots/" + screenShot));


        }
        if (result.getStatus() == 3) {
            ReportTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ReportTestManager.endTest();
        extent.flush();
        driver.quit();
    }

    private String getMethodArgumentInfo(ITestResult result) {
        Object[] parameters = result.getParameters();
        StringBuilder sb = new StringBuilder();
        sb.append("<br/>")
                .append("with following ")
                .append(parameters.length)
                .append(" arguments: ");
        Arrays.stream(parameters).forEach(arg ->
                sb.append("<br/>")
                        .append(arg)
                        .append("<br/>")
                        .append("type: ")
                        .append(arg.getClass().getTypeName())
                        .append("</br>")


        );
        return sb.toString();
    }

    @AfterSuite
    public void generateReport() {
        extent.flush();
        if (extent == null) {
            throw new IllegalStateException("error with test ng xml");
        }
        extent.close();
    }

    private Date getTime(long startMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startMillis);
        return calendar.getTime();
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
