package TC.login;

import com.relevantcodes.extentreports.LogStatus;
import extendreport.ExtentManager;
import extendreport.ExtentTestManager;
import login.LoginPO;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.BaseTest;


public class LoginTC extends BaseTest {

	@Test(description = "Gmail Login Demo")
	public void verfiyLoginInGmail_10000() throws Exception {

		LoginPO loginPO = new LoginPO(driver);

        //Step 1: Enter valid email id
        ExtentTestManager.getTest().log(LogStatus.PASS,"Open URL", "Gmail signin page should be open");

        //Step 2: Enter valid email id
		Assert.assertTrue(loginPO.inputText(loginPO.usernameTextBoxID, "jgbakaraniya"), "Entered email id should not be accepted");
		ExtentTestManager.getTest().log(LogStatus.PASS,"Enter valid email id", "Entered email id should be accepted");

		//step 3:Click on next button
		Assert.assertTrue(loginPO.clickOnElement(loginPO.nextButtonID), "User should not be land on enter password page");
		ExtentTestManager.getTest().log(LogStatus.PASS,"Click on next button","User should be land on enter password page");

		//step 4: Enter valid password
		Assert.assertTrue(loginPO.inputText(loginPO.passwordTextBoxXpath, "bagathala2701"), "Entered password should not be accept");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Enter valid password", "Entered password should be accept");

		//step 5:Click on next button
		Assert.assertTrue(loginPO.clickOnElement(loginPO.nextButtonXpath), "User should not be land on gmail inbox page");
		ExtentTestManager.getTest().log(LogStatus.PASS,"Click on next button","User should be land on gmail inbox page");

	}

}
