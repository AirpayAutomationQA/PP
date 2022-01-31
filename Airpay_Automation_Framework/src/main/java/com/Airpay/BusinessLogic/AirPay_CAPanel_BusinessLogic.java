package com.Airpay.BusinessLogic;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Airpay.PageObject.Airpay_CAPanel_PageObject;
import com.Airpay.Reporting.Extent_Reporting;
import com.Airpay.TestData.Excel_Handling;
import com.Airpay.Utilities.ElementAction;
import com.Airpay.Utilities.Log;

public class AirPay_CAPanel_BusinessLogic extends Airpay_CAPanel_PageObject {
	public WebDriver driver;
	public String TC_ID = "";
	ElementAction Assert = new ElementAction();
	public AirPay_CAPanel_BusinessLogic(WebDriver driver, String TC_ID)
	{
		Log.info("AirPay_payment_Mode_Wallet_BusinessLogic");
		this.driver = driver;
		this.TC_ID=TC_ID;
	}
	
	
	public void CA_Panel_Login() throws Exception{
		try{
			
			String MA_URL = Excel_Handling.Get_Data(TC_ID, "CA_URL").trim();
			driver.navigate().to(MA_URL);
			Assert.waitForPageToLoad(driver);
			//Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MA_UserID, Excel_Handling.Get_Data(TC_ID, "MA_UserID").trim(), "MA Panel USer ID");
			if(Assert.isElementDisplayed(driver, CAloginID, "USER ID")){
				Assert.inputText(driver, CAloginID, Excel_Handling.Get_Data(TC_ID, "CA_UserID").trim(), "CA Panel USer ID");
				Assert.inputText(driver, CAPwd, Excel_Handling.Get_Data(TC_ID, "CA_PWD").trim(), "Ca panel PWD");
				Extent_Reporting.Log_report_img("Login Details entered", "Passed", driver);
				Assert.clickButton(driver,CASubmitBtn, "Sign In button");
				Assert.waitForPageToLoad(driver);
				Thread.sleep(10000);
			}else{
				Extent_Reporting.Log_Fail("CA Panel User field does not exist", "Failed", driver);
			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("CA Panel User field does not exist", "Failed", driver);
			throw new Exception("CA panel page issue");
		}
	}
	
	public void Verify_PaymentPageFields() throws Exception {
		try{ 
			Log.info("Navigating To Payment Page");	
			Assert.waitForPageToLoad(driver);
			Thread.sleep(2000);
			if(Assert.isElementDisplayed(driver, LogoutIcon, "DashBoard" ))
			{         	
				//Assert.Verify_Image(driver, ImgLogo, "Airpay Logo");
				//Assert.isElementDisplayed(driver, airPayFavIcon, "Airpay Fav icon");
				Extent_Reporting.Log_report_img("Respective Details is exist", "Passed", driver);
			}else{
				Extent_Reporting.Log_Fail("Logo  page does not exis",	"Failed",driver);
				Log.error("Logo page not successfully displayed");
				throw new Exception("Logo does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" Logo does not exist",	"Failed",driver);
			Log.error(" Logo does not exist does not exist");
			e.printStackTrace();
			throw new Exception("Test failed due to Logo does not displayed");
		}
	}
	
	public void CA_Panel_Logout() throws Exception
	{
		try{
			Thread.sleep(5000);
			if(Assert.isElementDisplayed(driver, LogoutIcon, "Logout")){
				Assert.clickButton(driver, LogoutIcon, "profile iocn");
				Extent_Reporting.Log_report_img("Profile icon", "Passed", driver);
				Assert.Clickbtn(driver, CALogOutBtn, "Log out button");
				Assert.isElementDisplayed(driver, CAloginID, "USER ID");
				Extent_Reporting.Log_report_img("Successfully logout", "Passed", driver);

			}else{
				Extent_Reporting.Log_Fail("Profile icon not displayed", "Failed", driver);			
			}		
		}catch(Exception e)	{
			Extent_Reporting.Log_Fail(" Profile logo does not exist",	"Failed",driver);
			Log.error("Profile Logo does not exist does not exist");
			e.printStackTrace();
			throw new Exception("Test failed due to Profile Logo does not displayed");
		}
	}	
	
	public void Verify_All_HeaderTabs() throws Exception {
		try{ 
			Log.info("Navigating To Payment Page");	
			Assert.waitForPageToLoad(driver);		
			List<WebElement> Channels = driver.findElements(By.xpath(HeaderTabsVerify));
			int ChannelsCnt = Channels.size();
			System.out.println("Channels count is:"+ChannelsCnt);
			for(int i=0; i<ChannelsCnt;i++)
			{
				WebElement ChannelsName = Channels.get(i);
				String name = ChannelsName.getText();
				ChannelsName.click();
				Extent_Reporting.Log_Pass("Tab Name is :" +name, "Passed");			
				Extent_Reporting.Log_report_img("Header Tab", "ScreenShot", driver);
			}
			Extent_Reporting.Log_report_img("All Header Tab are exist as expected", "Passed", driver);        
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Tab does not exist",	"Failed",driver);
			Log.error("Tab does not exist");
			e.printStackTrace();
			throw new Exception("Tab does not exist");
		}
	}
	
	public void CAPanel_HeaderTabs() throws Exception {
		try{ 
			Log.info("Navigating To Payment Page");	
			Assert.waitForPageToLoad(driver);		
			List<WebElement> Channels = driver.findElements(By.xpath(HeaderTabsVerify));
			int ChannelsCnt = Channels.size();
			System.out.println("Channels count is:"+ChannelsCnt);
			for(int i=0; i<ChannelsCnt;i++)
			{
				WebElement ChannelsName = Channels.get(i);
				String name = ChannelsName.getText().trim();
				if(Excel_Handling.Get_Data(TC_ID, "CAHeaderTabs").trim().contains(name)){
					ChannelsName.click();
					Extent_Reporting.Log_Pass("Tab Name is :" +name, "Passed");			
					Extent_Reporting.Log_report_img("Header Tab", "ScreenShot", driver);
				}
				if(i==ChannelsCnt){
					Extent_Reporting.Log_Fail("Check the datasheet for CA paenl Header tab", "Failed", driver);
				}
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Tab does not exist",	"Failed",driver);
			Log.error("Tab does not exist");
			e.printStackTrace();
			throw new Exception("Tab does not exist");
		}
	}
	
	
	public void CApanelProfileVerify() throws Exception{
		try{
			
			if(Assert.isElementDisplayed(driver, AAdharField, "Aadhar Field")){
				driver.findElement(By.xpath(AAdharField)).clear();
				driver.findElement(By.xpath(PANField)).clear();
				Extent_Reporting.Log_Pass("Both Field Cleared", "Passed");
				Assert.Clickbtn(driver, ProfileSubmtBtn, "Profile Submit button");
			}
			
		}catch(Exception e)	
			{
				Extent_Reporting.Log_Fail("Tab does not exist",	"Failed",driver);
				Log.error("Tab does not exist");
				e.printStackTrace();
				throw new Exception("Tab does not exist");
			}
	     }
	
public void CAProfileErMsg() throws Exception{
		try{
			
			if(Assert.isElementDisplayed(driver, ProfileCAErrorMsg, "Aadhar Field or PAN Number Error")){			
				Extent_Reporting.Log_Pass("Error Message is exist as expected", "Passed");
				Extent_Reporting.Log_report_img("Error Message", "Passed", driver);
				Assert.inputText(driver, AAdharField, "28834", "Invalid Aadhar cad");
				Assert.inputText(driver, PANField, "cdds", "Invalid PAN card");
				Assert.Clickbtn(driver, ProfileSubmtBtn, "Profile Submit button");
				Assert.isElementDisplayed(driver, AadharCadInvalidErMsg, "Invalid aadhar error message");
				Assert.isElementDisplayed(driver, PanCadInvalidErMsg, "Invalid PAN Card Error Message ");
				Extent_Reporting.Log_Pass("Repective error message is exist as expected", "pASSED");
				Extent_Reporting.Log_report_img("Error Message", "Passed", driver);
			}
			
		}catch(Exception e)	
			{
				Extent_Reporting.Log_Fail("Tab does not exist",	"Failed",driver);
				Log.error("Tab does not exist");
				e.printStackTrace();
				throw new Exception("Tab does not exist");
			}
	     }
	
}