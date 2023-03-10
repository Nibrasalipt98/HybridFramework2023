package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; // for logger

public class BaseClass {

public static WebDriver driver;   // made staic because this class will create driver, additonal extendreport new obj of base clss created and it create 2nd driver. to avoid 2 driver made driver as static
public Logger logger;
public ResourceBundle rb;
public Properties p;
	
	@BeforeClass(groups= {"Master","Sanity","Regression","datadriven"})   //every group need to get added to setup and ter down method
	@Parameters("browser")
	public void setup(String br) throws IOException
	{
		//Loading properties file (Approach1)
		//rb=ResourceBundle.getBundle("config"); // name of properties file without extension
		
		//Loading properties file (Appraoch2)
		FileReader file=new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
		p=new Properties();
		p.load(file);
		
		
		//log4j code
		logger=LogManager.getLogger(this.getClass());      // we are using getClass becuase, for all testcases, only log file will be there, so to differeniate the testcases we need to provide the class nae of test, so this will retrun current class obj and this.getclass will return current classs name
		
		
	//	WebDriverManager.chromedriver().setup();   from selenium 4.6.0 we dont need to use this statement also, as they have integrated with driver=new ChromeDriver(); also webdriver manager dependency also not  required
		if(br.equals("chrome"))
		{
		driver=new ChromeDriver();
		}
		else if(br.equals("edge"))
		{
			driver=new EdgeDriver();
		}
		else
		{
			driver=new FirefoxDriver();
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get(rb.getString("appURL"));   // local app URL
	//	driver.get(p.getProperty("appURL"));   // local app URL  // getting URL from properties file
		
		
		driver.get("https://demo.opencart.com/index.php");   // remote App URL
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Master","Sanity","Regression","datadriven"})
	public void tearDown()
	{
		driver.quit();
	}


	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomeNumber()
	{
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	
	public String randomAlphaNumeric()
	{
		String str=RandomStringUtils.randomAlphabetic(5);
		String num=RandomStringUtils.randomNumeric(3);
		
		return (str+"@"+num);
	}
	
	public String captureScreen(String tname) throws IOException {
       //tname=tsetname
		/*Date dt=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
		String timeStamp=df.format(dt);
		*/
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	}
}