package reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import lombok.extern.slf4j.Slf4j;


/**
  OB: extentTestMap holds the information of thread ids and ExtentTest instances.
  ExtentReports instance created by calling getReporter() method from ExtentManager.
  At startTest() method, an instance of ExtentTest created and put into extentTestMap with current thread id.
  At endTest() method, test ends and ExtentTest instance got from extentTestMap via current thread id.
  At getTest() method, return ExtentTest instance in extentTestMap by using current thread id.
 */
import java.util.HashMap;
import java.util.Map;

public class ReportTestManager {
    private static Map<Integer, ExtentTest> map = new HashMap<>();
    private static ExtentReports report = ExtentManager.getInstance();
    private static ExtentReports updateReport = ExtentManager.getInstance();

    public static synchronized ExtentTest getTest() {
        return map.get((int) Thread.currentThread().getId());
    }

    public static synchronized void endTest() {
        report.endTest(map.get((int) Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTestClass(String className, String testName) {
        return startTest(className, testName, "");
    }
    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = report.startTest(testName, desc);
        map.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest startTest(String className, String testName, String desc) {
        ExtentTest test = updateReport.startTest(testName, desc);
        map.put((int) Thread.currentThread().getId(), test);
        return test;
    }

}
