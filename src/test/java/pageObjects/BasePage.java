package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
// In every page we need to call it's constructor to init elementes, so intead of typing it in every page, we seperate it as new page and inititated it and extended the clssses to this parent
	//so Super(driver) will call this parent class constructor
	WebDriver driver;
	
	public BasePage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
}
