package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ExtentManager;
import utils.TestUtils;

import java.lang.reflect.Method;

public class BaseTest {
    protected static ExtentReports extent;
    protected ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void startReport() {
        ConfigReader.initProperties();
        extent = ExtentManager.getInstance();
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String xmlBrowser, Method method) {
        // Priority: -Dbrowser (Gradle) > testng.xml param > config.properties
        String sysBrowser = System.getProperty("browser");
        String br = (sysBrowser != null && !sysBrowser.isBlank())
                ? sysBrowser
                : (xmlBrowser != null && !xmlBrowser.isBlank()
                ? xmlBrowser
                : ConfigReader.get("browser"));

        WebDriver drv = DriverFactory.initDriver(br);
        driver.set(drv);

        drv.get(ConfigReader.get("url"));

        ExtentTest extentTest = extent.createTest(method.getDeclaringClass().getSimpleName() + " :: " + method.getName());
        test.set(extentTest);
        log().info("Launching " + br + " and navigating to " + ConfigReader.get("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String name = result.getMethod().getMethodName();
        if (result.getStatus() == ITestResult.FAILURE) {
            String path = TestUtils.takeScreenshot(driver(), name);
            if (path != null) {
                log().fail(result.getThrowable());
                log().addScreenCaptureFromPath(path);
            } else {
                log().fail(result.getThrowable());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            String path = TestUtils.takeScreenshot(driver(), name);
            if (path != null) log().pass("Passed").addScreenCaptureFromPath(path);
            else log().pass("Passed");
        } else {
            log().skip("Skipped: " + result.getThrowable());
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) extent.flush();
    }

    protected WebDriver driver() { return driver.get(); }
    protected ExtentTest log() { return test.get(); }
}
