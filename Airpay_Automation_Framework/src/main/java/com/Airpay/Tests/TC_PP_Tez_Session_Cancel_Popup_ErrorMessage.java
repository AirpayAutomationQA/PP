package com.Airpay.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.Airpay.BusinessLogic.AirPay_PaymentPage_BusinessLogic;
import com.Airpay.BusinessLogic.AirPay_Payment_Mode_CreditCard_BusinessLogic;
import com.Airpay.BusinessLogic.AirPay_Payment_Mode_EMI_Credit_Card_BusinessLogic;
import com.Airpay.InitialSetup.Driver_Setup;
import com.Airpay.Utilities.Log;

public class TC_PP_Tez_Session_Cancel_Popup_ErrorMessage extends Driver_Setup{
	public static WebDriver webDriver = null;
	public static String tcID = null;
	
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
			Log.info("Script Starts..");
			AirPay_PaymentPage_BusinessLogic AirPay_Local = new AirPay_PaymentPage_BusinessLogic(driver, TC_ID);
			AirPay_Payment_Mode_EMI_Credit_Card_BusinessLogic EMI = new AirPay_Payment_Mode_EMI_Credit_Card_BusinessLogic(driver, TC_ID);
			AirPay_Payment_Mode_CreditCard_BusinessLogic CreditCard = new AirPay_Payment_Mode_CreditCard_BusinessLogic(driver, TC_ID);					
				
				AirPay_Local.LocalHostDetailPage_ErrorVerify();	
				AirPay_Local.Verify_PaymentPageFields();
				AirPay_Local.Card_Details_Options();
				EMI.TezField();			
				EMI.TezMakepaymentBtn();				
				CreditCard.sessionCancel_errMsg();
				
			Log.info("Scripts Ends....");
		} catch (Exception e) {
			Log.error(e.getMessage());
			System.out.println(e);
		}
	}
	@AfterTest
	public void tearDown()
	{
		if(webDriver != null)
			webDriver.close();
	}
}