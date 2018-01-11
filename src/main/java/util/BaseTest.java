package util;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import extendreport.ExtentManager;
import extendreport.ExtentTestManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import zapi.Zapi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class BaseTest {

	public String baseUrl = "https://accounts.google.com/signin";
    
	public WebDriver driver;

    @BeforeMethod
    public void launchBrowser(ITestResult result, Method method) {
        getDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        ExtentTestManager.startTest("" + method.getName());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Open Browser and navigate to " + baseUrl, "Browser Name: chrome" );
    }

    @Parameters("jira")
    @AfterMethod
    public void terminateBrowser(ITestResult result, boolean jira) throws Exception{
        if (jira){
            new Zapi().getAndExecuteCycle();
            new Zapi().getAndUpdateStepResult();
        }
        if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed");
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");

        }
        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
        ExtentManager.getReporter().flush();
        this.driver.quit();

    }

  /*  @AfterTest
    public void afterTest(){
        try {
            //new Zapi().getAndExecuteCycle();
            new Zapi().getAndUpdateStepResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.driver.quit();
    }*/

    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\dependancy\\chromedriver.exe");
        this.driver = new ChromeDriver();
        return driver;
    }

}
