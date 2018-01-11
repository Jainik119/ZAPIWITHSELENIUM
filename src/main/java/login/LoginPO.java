package login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import zapi.Util;

public class LoginPO {

	public WebDriver driver;
	public By usernameTextBoxID = By.xpath("//input[@id='identifierId']");
	public By nextButtonID = By.id("identifierNext");
	public By passwordTextBoxXpath = By.xpath("//div[@id='password']/descendant::input");
	public By nextButtonXpath = By.xpath("//div[@id='passwordNext']");

	/**
	 * @param driver
	 * @throws Exception
	 */
	public LoginPO(WebDriver driver) throws Exception {
		this.driver = driver;
	}

	/**
	 * @param webElement
	 * @param inputTextData
	 * @return
	 */
	public boolean inputText(By webElement, String inputTextData) {
		try {
			Util.pause(1000);
			WebElement input = (new WebDriverWait(driver, 30))
					.until(ExpectedConditions.presenceOfElementLocated(webElement));
			input.sendKeys(inputTextData);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param webElement
	 * @return
	 */
	public boolean clickOnElement(By webElement) {
		try {
			WebElement generic_WebL = (new WebDriverWait(driver, 90))
					.until(ExpectedConditions.presenceOfElementLocated(webElement));
			generic_WebL.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
