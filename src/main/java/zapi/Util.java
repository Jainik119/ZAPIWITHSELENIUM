package zapi;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

/**
 * @author Jainik.Bakaraniya
 * @version 1.0
 * @since 03/01/2018 (dd/mm/yyyy)
 */
public class Util {
	
	public WebDriver driver;
	
	/**
	 * default constructor
	 */
	public Util() {	}
	
	/**
	 * @param driver
	 * driver initialization
	 */
	public Util(WebDriver driver) {	this.driver = driver;}
	
	/**
	 * @param milliseconds
	 * this is use for the pause or wait
	 */
	public static void pause(Integer milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

