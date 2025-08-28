package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {
    public static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Build absolute path inside Gradle's build folder
            String reportDir = System.getProperty("user.dir") + "/build/reports/extent";
            new File(reportDir).mkdirs();

            String reportPath = reportDir + "/extent-report.html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setDocumentTitle("QA Automation Portfolio Project");
            reporter.config().setReportName("SauceDemo Automation Tests");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}
