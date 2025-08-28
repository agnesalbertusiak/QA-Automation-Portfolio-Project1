package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            File destDir = new File("reports/screenshots");
            destDir.mkdirs();
            File dest = new File(destDir, testName + "_" + time + ".png");
            FileUtils.copyFile(src, dest);
            return dest.getPath();
        } catch (Exception e) {
            return null;
        }
    }
}


