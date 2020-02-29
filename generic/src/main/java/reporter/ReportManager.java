package reporter;


import base.BrowserDriver;
import com.relevantcodes.extentreports.ExtentReports;
import config.Config;
import config.Env;
import org.testng.ITestContext;
import org.testng.Reporter;

import java.io.File;

public class ReportManager {
    private static ExtentReports reporter;
    private static ITestContext context;
    private static Env env = Config.getInstance().getEnv();
    private static String separator = System.getProperty("file.separator");


    private ReportManager() {
    }

    public static ExtentReports getInstance() {
        if (reporter == null) {
            init();
        }
        return reporter;
    }

    private synchronized static void init() {
        String reportPath = env.getReportOutputDirectory();


        if (reportPath == null) {
            System.out.print("use default path");
            reportPath = context.getOutputDirectory();
        }
        File outputDirectory = new File(reportPath);
        File resultDirectory = new File(outputDirectory.getParentFile(), "html");

        String outputPath = reportPath + File.pathSeparator + "Extent-Report" + File.pathSeparator + "ExtentReport" +
                ".html";
        reporter = new ExtentReports(outputPath, true);
        Reporter.log("Extent Report Directory" + resultDirectory, true);
        reporter.addSystemInfo("Host Name", "Tester")
                .addSystemInfo("Environment", "QA")
                .addSystemInfo("User Name", "Team_Three");
        reporter.loadConfig(BrowserDriver.class.getClassLoader().getResource(("report-config.xml")));

    }
    public static void setOutputDirectory(ITestContext context){
        ReportManager.context = context;

    }
}
