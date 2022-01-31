package com.Airpay.Tests;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.io.File;
import java.security.Timestamp;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import com.Airpay.BusinessLogic.AirPay_PaymentPage_BusinessLogic;
import com.Airpay.InitialSetup.Driver_Setup;
import com.Airpay.Reporting.Extent_Reporting;
import com.Airpay.TestData.Excel_Handling;
import com.Airpay.Utilities.ElementAction;
import com.Airpay.Utilities.Log;
import com.Airpay.Utilities.MailProjectClass;
import com.Airpay.Utilities.Zipfile;

public class MA_Report2 extends Driver_Setup{
	Zipfile zip = new Zipfile() ;
	public static WebDriver webDriver = null;
	public static String tcID = null;
	ElementAction Assert = new ElementAction();
	  StopWatch stopWatch = new StopWatch();
	//Business Logic Class Object list	
	@Test(priority=1)
	public void setup()
	{
		Log.info("Setup the variable for Test");
		webDriver = driver; 
		tcID = TC_ID;
		Log.info("Setup completed for the variable");
	}
	@Test(priority = 2)
	public void TC_TestCaseName() throws Throwable {
		try {
			
			//driver.get("https://ma.airpay.co.in/");
			String username = "//input[@id='username']";
			String password = "//input[@id='password']";
			String submitbutton ="//input[@id='submitbutton']";
			String topbank ="//*[contains(@class,'bread-crumb') and//*[contains(text(),'top banks')]]";
			String errorlog="//*[contains(@class,'bread-crumb') and//*[contains(text(),'transaction error log')]]";
			boolean topbank1=false;
			boolean errorlog1=false;
			if(Assert.isElementDisplay(driver,username))
			{ 	
				Log.debug("Local Host page");
				Assert.inputText(driver, username, Excel_Handling.Get_Data(TC_ID, "BuyerMailID"), "Ma username");
				Assert.inputText(driver, password, Excel_Handling.Get_Data(TC_ID, "BuyerPhoneNumber"), "MA password");
				
				Extent_Reporting.Log_report_img("Ma page", "Passed", driver);
				Assert.Clickbtn(driver, submitbutton,"Submit button");
				Thread.sleep(30000);
				Assert.waitForPageToLoad(driver);
				int count =1;
				for(int i=0;i<=count;i++)
				{	
					System.out.println("i value======="+i);
				driver.get("https://ma.airpay.co.in/top_banks.php");
				
				Extent_Reporting.Log_report_img("Top Bank", "Passed", driver);
				Assert.waitForPageToLoad(driver);
				if(Assert.isElementDisplay(driver, topbank))
				{
					topbank1=true;
				}
				Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
				ImageIO.write(screenshot.getImage(),"PNG",new File("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report//Top_Bank"+timeStamp+".png"));  
				Thread.sleep(30000);
				WebElement Amztot = driver.findElement(By.xpath("//*[text()='AmazonPay']/following-sibling::td[1]"));
				String amzcount = Amztot.getText();
				WebElement Amzfaile = driver.findElement(By.xpath("//*[text()='AmazonPay']/following-sibling::td[5]"));
				String amzcountfail = Amzfaile.getText();
				
				System.out.println("Failed count"+amzcountfail+"total count"+amzcount);
				
				Double failure_percetage = (Double.valueOf(amzcountfail)/Double.valueOf(amzcount))*100;
				
				System.out.println("Failure Percentage"+failure_percetage);
				
				if(Assert.isElementDisplay(driver, errorlog))
				{
					errorlog1=true;
				}
				System.out.println("Timesstamp"+timeStamp);
				Screenshot screenshot1=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
				ImageIO.write(screenshot1.getImage(),"PNG",new File("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report//Errorlog"+timeStamp+".png")); 
				//Zipfile.compress("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report");
				// MailProjectClass.sendmail("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report.zip", "MA_Report.zip","MA Report-Errorlog/Top_Bank-"+timeStamp);
			if(topbank1 && failure_percetage>=20)
			{
				MailProjectClass.sendmailmulattach("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report",2,"MA Report-Errorlog/Top_Bank-"+timeStamp,"swapnil.pawar@airpay.co.in");
				System.out.println("vill send mail");
			}
			else
			{
				count++;
				System.out.println("Print cpunt========="+count);
			}
				}
			}
			
			
			
			
			
			
			}
			
				  catch (Exception e) {
					  	Log.error(e.getMessage());
					  	System.out.println(e);
				  		}
	}
	@Test(priority=3)
	public void delete()
	{	/*
		File index = new File("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report");
		String[]entries = index.list();
		for(String s: entries){
		    File currentFile = new File(index.getPath(),s);
		    currentFile.delete();
		}
		*/
	}
	@AfterTest
	public void tearDown()
	{
	//	if(webDriver != null)
		//	webDriver.close();
	}
}
