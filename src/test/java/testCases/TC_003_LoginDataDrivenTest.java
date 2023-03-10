package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginDataDrivenTest extends BaseClass{

	//Test
	//dataProviderClass=DataProviders.class  this is used, since DP is present in differenct class
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups= {"datadriven"})
	public void test_LoginDDT(String email, String pwd, String exp)
	{
		logger.info("***** String TC_003_LoginDataDrivenTest ****");
	
		try
		{
		//HomePage -->MyAccount-->Login
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage-->email & password then click
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//Check MyAccount label is present
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountPageExists();
		
		// 4 cases,     valid login  -   reach target page ( test pass), doesnt reach target page ( test fail )
		//              in valid login - reach target page ( test fail ) , doesnt reach target page ( test pass)
		// valid and invalid we enter in excel sheet to while createing test data
	
		if(exp.equals("Valid"))
		{
			if(targetpage ==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.fail();
				//Assert.assertTrue(false);
			}
		}
		
		
		if(exp.equals("Invalid"))
		{
			if(targetpage ==true)
			{
				macc.clickLogout();
				Assert.fail();
				//	Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
				
		logger.info("**** Finished TC_003_LoginDataDrivenTest  ***** ");
	}
	
}
