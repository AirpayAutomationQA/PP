package com.Airpay.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.Airpay.BusinessLogic.AirPay_MA_Panel_Select_Merchant_BusinessLogic;
import com.Airpay.BusinessLogic.AirPay_PaymentPage_BusinessLogic;
import com.Airpay.BusinessLogic.AirPay_Payment_Mode_CreditCard_BusinessLogic;
import com.Airpay.InitialSetup.Driver_Setup;
import com.Airpay.PageObject.AirPay_Payment_MA_Panel_PageObject;
import com.Airpay.Utilities.Log;

public class TC_MA_To_verify_Rupay_card_txn_through_Kotak_pg extends Driver_Setup{
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
			AirPay_MA_Panel_Select_Merchant_BusinessLogic MA_panel = new AirPay_MA_Panel_Select_Merchant_BusinessLogic(driver, TC_ID);				
			MA_panel.MA_Panel_Login();
			MA_panel.Select_Merchant();
			MA_panel.Filter_Merchant();
			MA_panel.Choose_Merchant();		
			MA_panel.Home_LHS_Dashboard(AirPay_Payment_MA_Panel_PageObject.MA_HomeMenu);
			MA_panel.Home_LHS_Dashboard(AirPay_Payment_MA_Panel_PageObject.MerchantMember_NextBtn);
			MA_panel.Home_LHS_Dashboard(AirPay_Payment_MA_Panel_PageObject.MM_PaymentMode_NextBtn);
			MA_panel.Home_LHS_Dashboard(AirPay_Payment_MA_Panel_PageObject.MM_PayMent_Mode_UrlLink);
			MA_panel.VerifyText(AirPay_Payment_MA_Panel_PageObject.MM_URLDetails, "URL Details");
			MA_panel.DetailsLinkClicked(AirPay_Payment_MA_Panel_PageObject.MM_URLDetails, "URL Details");
		   	//MA_panel.Verify_Button_Color(AirPay_Payment_MA_Panel_PageObject.MM_ONOFFCheckBoxSaveCard);			
			MA_panel.Domain_URLFindOut();
			MA_panel.BANK_DetailsFindOut();			
		   	AirPay_PaymentPage_BusinessLogic PAymentPage = new AirPay_PaymentPage_BusinessLogic(driver, TC_ID);		
			AirPay_Payment_Mode_CreditCard_BusinessLogic CreditCard = new AirPay_Payment_Mode_CreditCard_BusinessLogic(driver, TC_ID);
		   	PAymentPage.NavigateToLocalHostPage();
		   	PAymentPage.Card_Details_Options();
		   	CreditCard.Credit_cardProvidingValuesWithValidCardNumber();	
		   	PAymentPage.BankPage_validation();
		   	MA_panel.MA_Panel_Login();
			MA_panel.Home_LHS_Dashboard(AirPay_Payment_MA_Panel_PageObject.MA_HomeMenu);
			MA_panel.Home_LHS_Dashboard(AirPay_Payment_MA_Panel_PageObject.MM_Transaction_History);
			MA_panel.MerchantTransactionID();	
			MA_panel.TransactionRecords();
			/*MA_panel.TransactionHistory();
			MA_panel.TransactionHistoryWithSurchagre();*/
			//MA_panel.Enable_Disable_SaveCardChkBox();		   	
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