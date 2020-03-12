package reporter;


import base.BrowserDriver;
import com.relevantcodes.extentreports.ExtentReports;
import config.Config;
import config.Env;
import org.testng.ITestContext;
import org.testng.Reporter;

import java.io.File;
import java.net.URL;

/**
 * singleton class to manage extent report
 */
public class ExtentManager {
    private static ExtentReports extentReports;
    private static ITestContext context;
    private static Env env = Config.getInstance().getEnv();


    private ExtentManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            init();
        }
        return extentReports;
    }

    /**
     * set up output directory of the test output
     * and add additional system info to the report
     */
    private synchronized static void init() {
        String reportPath = env.getReportOutputDirectory();


        if (reportPath == null) {
            System.out.print("use default path");
            reportPath = context.getOutputDirectory();
        }
        File outputDirectory = new File(reportPath);
        File resultDirectory = new File(outputDirectory.getParentFile(), "html");

        String outputPath = reportPath + File.pathSeparator + File.pathSeparator + "ExtentReport" +
                ".html";
        extentReports = new ExtentReports(outputPath, true);
        Reporter.log("Extent Report Directory" + resultDirectory, true);

        extentReports.addSystemInfo("Host Name", "Tester")
                .addSystemInfo("Environment", "QA")
                .addSystemInfo("User Name", "Team_Three");
        extentReports.addSystemInfo(Config.getInstance().getEnv().getSystemInfo());
        URL resource = BrowserDriver.class.getClassLoader().getResource(("report-config.xml"));

        if (resource == null){
            throw  new IllegalArgumentException("report-config.xml must be located in resources ");
        }
        try {
            extentReports.loadConfig(BrowserDriver.class.getClassLoader().getResource(("report-config.xml")));
        } catch (Exception e) {

            System.err.println(BrowserDriver.class.getClassLoader().getResource(("report-config.xml")));
        }


    }

    public static void setOutputDirectory(ITestContext context) {
        ExtentManager.context = context;

    }
}
