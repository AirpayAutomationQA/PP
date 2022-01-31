package com.Airpay.BusinessLogic;


import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.Airpay.PageObject.AirPay_Payment_MA_Panel_PageObject;
import com.Airpay.PageObject.Airpay_PaymentPage_PageObject;

import com.Airpay.Reporting.Extent_Reporting;
import com.Airpay.TestData.Excel_Handling;
import com.Airpay.Utilities.ElementAction;
import com.Airpay.Utilities.Log;

public class AirPay_MA_Panel_Select_Merchant_BusinessLogic extends Airpay_PaymentPage_PageObject {

	public WebDriver driver;
	public String TC_ID = "";
	public static String MA_URL = null;
	ElementAction Assert = new ElementAction();
	//AirPay_Payment_MA_Panel_PageObject MA = new AirPay_Payment_MA_Panel_PageObject();
	//Log log = new Log();	
	public AirPay_MA_Panel_Select_Merchant_BusinessLogic(WebDriver driver, String TC_ID)
	{
		Log.info("AirPay_MA_Panel_Select_Merchant_BusinessLogic");

		this.driver = driver;
		this.TC_ID=TC_ID;
	}
	/**
	 * @author sakole
	 * @throws Exception
	 */
	public void MA_Panel_Login() throws Exception{
		try{
			MA_URL = Excel_Handling.Get_Data(TC_ID, "MA_URL").trim();
			driver.navigate().to(MA_URL);
			Assert.waitForPageToLoad(driver);
			//Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MA_UserID, Excel_Handling.Get_Data(TC_ID, "MA_UserID").trim(), "MA Panel USer ID");
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MA_UserID, "USER ID")){
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MA_UserID, Excel_Handling.Get_Data(TC_ID, "MA_UserID").trim(), "MA Panel USer ID");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MA_PWD, Excel_Handling.Get_Data(TC_ID, "MA_Password").trim(), "");
				Extent_Reporting.Log_report_img("Login Details entered", "Passed", driver);
				Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MA_SubmitBtn, "Sign In button");
				Assert.waitForPageToLoad(driver);
				Thread.sleep(10000);
			}else{

				Extent_Reporting.Log_Fail("MA Panel User field does not exist", "Failed", driver);
			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("MA Panel User field does not exist", "Failed", driver);
			throw new Exception("MA panel page issue");
		}
	}

	public static String MerchantName =null;
	public void Select_Merchant() throws Exception{
		try{			
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_SelectMerchantSymbol, "Select Merchant ID")){			
				MerchantName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_SelectMerchantSymbol)).getAttribute("title").trim();
				if(MerchantName.equalsIgnoreCase("Select Merchant")){
					Extent_Reporting.Log_report_img("Select Merchant", "Passed", driver);
					Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MM_SelectMerchantSymbol , "Select Merchant");									
					Assert.waitForPageToLoad(driver);
					Assert.WaitUntilExist(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Search);
				}else{
					Extent_Reporting.Log_Pass("Merchant already selected", "Passed");
					Extent_Reporting.Log_report_img("Merchant selected as: "+MerchantName, "Passed", driver);
				}				
			}else{

				Extent_Reporting.Log_Fail("MA Panel User field does not exist", "Failed", driver);
			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("MA Panel User field does not exist", "Failed", driver);
			throw new Exception("MA panel page issue");
		}
	}


	public void Filter_Merchant() throws Throwable{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Search, "Merchant Search"))
			{
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Search, Excel_Handling.Get_Data(TC_ID, "Merchant_ID").trim(), "Merchant ID");
				Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Search, "Search Entered");
				Thread.sleep(10000);
				Extent_Reporting.Log_report_img("Merchant ID Entered", "Passed", driver);
				Assert.Javascriptexecutor_forClick(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Search, "Merchant ID ");
				Thread.sleep(10000);
				Assert.waitForPageToLoad(driver);
				Assert.WaitUntilExist(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Select);	
			}else{
				Extent_Reporting.Log_Fail("Merchant ID search field does not exost", "Failed", driver);
			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Merchant ID search field does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");
		}
	}


	public void Choose_Merchant() throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_Select, "Merchant Select")){	
				MerchantName=driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_Merchant_IDName)).getText().trim();
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MM_Merchant_SelectBtn+"["+1+"]", "Select Merchant");
				Assert.waitForPageToLoad(driver);
				Thread.sleep(20000);
				String SelectedMerchant = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_SelectMerchantSymbol)).getText().trim();
				if(MerchantName.equalsIgnoreCase(SelectedMerchant))
				{				
					Extent_Reporting.Log_Pass("Merchant selected as :"+SelectedMerchant, "Expected Merchant AS:"+MerchantName);
					Extent_Reporting.Log_report_img("Merchant selected", "Passed", driver);

				}else{
					Extent_Reporting.Log_Fail("Selected Merchant Name is: "+SelectedMerchant, "Updated Merchant name is:"+MerchantName, driver);
				}				
			}else{
				Extent_Reporting.Log_Fail("Merchant ID search field does not exost", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Merchant ID search field does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void Home_LHS_Dashboard(String MenuOption ) throws Throwable{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, MenuOption, "RespectiveLHS Menu"))
			{	
				Assert.Javascriptexecutor_forClick(driver, MenuOption, "Respective Menu");
				Assert.waitForPageToLoad(driver);
				Extent_Reporting.Log_report_img("Respective link or button", "Passed", driver);			
			}else{
				Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void CLickbtn(String MenuOption, String Option ) throws Throwable{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, MenuOption, "RespectiveLHS Menu"))
			{	
				Assert.Javascriptexecutor_forClick(driver, MenuOption, "Respective Menu");
				Assert.waitForPageToLoad(driver);
				Extent_Reporting.Log_report_img(Option, "Passed", driver);			
			}else{
				Extent_Reporting.Log_Fail(Option+"vdoes not exost", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail(Option+"vdoes not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void AdvancesearchDropDown() throws Throwable{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MA_FilterCardTypeDropDown, "Select card Type"))
			{	
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_FilterCardTypeDropDown, Excel_Handling.Get_Data(TC_ID, "").trim(), "card Type Drop down");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_FilterChannelDropDown, Excel_Handling.Get_Data(TC_ID, "").trim(), "Channel Drop down");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_FilterMCCDropDown, Excel_Handling.Get_Data(TC_ID, "").trim(), "Select MCC drop down");
				Assert.waitForPageToLoad(driver);
				Extent_Reporting.Log_report_img("Respective details filled", "Passed", driver);			
			}else{
				Extent_Reporting.Log_Fail("Drop down data issue might be", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Drop down data issue might be", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void VerifyText(String Xpath, String TextVerify) throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, Xpath, "Respective Text Xpath"))
			{	
				String TextVal = driver.findElement(By.xpath(Xpath)).getText().trim();
				System.out.println(TextVal);
				if(TextVal.equalsIgnoreCase(TextVerify)){
					Extent_Reporting.Log_Pass("Field name is: "+TextVal, "Expected as: "+TextVerify);
					Extent_Reporting.Log_report_img("Respective Link or field is exist", "As expected", driver);			

				}else{
					Extent_Reporting.Log_Fail("Respective text Does not exist as "+TextVal , "Failed", driver);
				}			
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void GetTextButNotCompare(String Xpath) throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, Xpath, "Respective Text Xpath"))
			{	
				TextVal = driver.findElement(By.xpath(Xpath)).getText().trim();
				System.out.println(TextVal);		
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void VerifyTextWithPassingData(String Xpath, String TextVerify) throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, Xpath, "Respective Text Xpath"))
			{


				String TextVal = driver.findElement(By.xpath(Xpath)).getText().trim();
				System.out.println(TextVal);
				if(TextVal.equalsIgnoreCase(TextVerify)){
					Extent_Reporting.Log_Pass("Field name is: "+TextVal, "Expected as: "+TextVerify);
					Extent_Reporting.Log_report_img("Respective Link or field is exist", "As expected", driver);			

				}else{
					Extent_Reporting.Log_Fail("Respective text Does not exist as "+TextVal , "Failed", driver);
				}			
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void NewRuleAddDetails() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, Excel_Handling.Get_Data(TC_ID, "RuleTitleName").trim(), "Title name");
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FieldClick, "MCCField box is");
				Thread.sleep(1000);
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FirstOptionClick, "MCC Firstoption");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, Excel_Handling.Get_Data(TC_ID, "ChannelName"), "Channel name");
				//BankNameEnableDisable();
				//Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, "netbnk", "Channel name");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.CardBankName, Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel"), "Channel name");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox, Excel_Handling.Get_Data(TC_ID, "PTC_Vlaue"), "PTC value");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox, Excel_Handling.Get_Data(TC_ID, "MSF_Value"), "MSF Value");						
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name should be want unique" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name should be want unique", "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void NewRuleAddMerchantDetails() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, Excel_Handling.Get_Data(TC_ID, "RuleTitleName").trim(), "Title name");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, Excel_Handling.Get_Data(TC_ID, "ChannelName").trim(), "Channel name");			
				if((Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("pg"))||(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("emi"))
						||(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("pos")))
				{	
					Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MA_DomesticRadioBtn, "Domestic radio button");
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_CardTypeDropDown, Excel_Handling.Get_Data(TC_ID, "Card_Type").trim(), "Card Type");
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_SchemeTypeDropDown, Excel_Handling.Get_Data(TC_ID, "Scheme_type").trim(), "Scheme Type");					
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");
				}
				else if(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("imps"))
				{					
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");	
				}else
				{				
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.CardBankName, Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel"), "Channel name");
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");
				}
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox, Excel_Handling.Get_Data(TC_ID, "PTC_Vlaue"), "PTC value");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox, Excel_Handling.Get_Data(TC_ID, "MSF_Value"), "MSF Value");						
				Extent_Reporting.Log_report_img("Respective Details entered", "Passed", driver);

			}else{

				Extent_Reporting.Log_Fail("Rule Title Name should be want unique" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name should be want unique", "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void DuplicateMerchantRuleAdd() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, TextVal, "Title name");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, Excel_Handling.Get_Data(TC_ID, "ChannelName").trim(), "Channel name");			
				if((Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("pg"))||(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("emi"))
						||(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("pos")))
				{	
					Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MA_DomesticRadioBtn, "Domestic radio button");
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_CardTypeDropDown, Excel_Handling.Get_Data(TC_ID, "Card_Type").trim(), "Card Type");
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MA_SchemeTypeDropDown, Excel_Handling.Get_Data(TC_ID, "Scheme_type").trim(), "Scheme Type");					
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");
				}
				else if(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim().contains("imps"))
				{					
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");	
				}else
				{				
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.CardBankName, Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel"), "Channel name");
					Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");
				}
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox, Excel_Handling.Get_Data(TC_ID, "PTC_Vlaue"), "PTC value");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox, Excel_Handling.Get_Data(TC_ID, "MSF_Value"), "MSF Value");						
				Extent_Reporting.Log_report_img("Respective Details entered", "Passed", driver);
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name should be want unique" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name should be want unique", "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}



	public void EditSurchargeRuleVerifyPage() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Title name");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, "Channel name");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Amount Greater Than");				
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox,  "Title Name");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox, "PTC value");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox,  "MSF Value");						
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name should be want unique" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name should be want unique", "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void EditConvienceSurchargeRuleVerifyPage() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Title name");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, "Channel name");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Amount Greater Than");				
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox,  "Title Name");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox, "PTC value");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox,  "MSF Value");
				String DataPTCVal= Excel_Handling.Get_Data(TC_ID, "PTC_Vlaue").trim();
				String MSFDataVal= Excel_Handling.Get_Data(TC_ID, "MSF_Value").trim();
				double ptcaddvalue =Double.parseDouble(DataPTCVal);
				double msfaddvalue =Double.parseDouble(MSFDataVal);
				ptcaddvalue++;
				msfaddvalue++;
				System.out.println(ptcaddvalue);
				System.out.println(msfaddvalue);				
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox,Double.toString(ptcaddvalue) , "PTC value");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox,Double.toString(msfaddvalue) , "MSF Value");
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name should be want unique" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name should be want unique", "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void BankNameEnableDisable() throws Exception{
		try{			
			List<WebElement> BankDropCount = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.BankNameDropBox));
			System.out.println("Bank name count: "+BankDropCount );
			for(int i=0;i<BankDropCount.size();i++)
			{				
				boolean bankEnable = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.BankNameDropBox+"["+(i+1)+"]")).isEnabled();
				System.out.println("BankStatus: "+bankEnable);	
				System.out.println("I count:"+ (i+1));
				if(bankEnable==true)
				{					
					Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.BankNameDropBox+"["+i+1+"]", Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim(), "Bank Name drop down");
					break;					
				}
				if(i==BankDropCount.size()-1){
					Extent_Reporting.Log_Fail("Respective Bank Name does not exist", "Failed", driver);
				}			
			}			
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name should be want unique", "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}



	}

	public void RuleCreatedVerify() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.RuleCreatedVerify, "Details Rule button"))
			{				
				String FetchName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.FetchRulename)).getText().trim();
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Edit button");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.RuleCreatedVerify, "Details button");
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.RuleCreatedVerify, "Rule Created");
				if(FetchName.contains(Excel_Handling.Get_Data(TC_ID, "RuleTitleName").trim()))
				{
					Extent_Reporting.Log_report_img("Rule Created successfully", "Passed", driver);
				}else{
					Extent_Reporting.Log_Fail("Rule Title Name did not matched" , "Failed", driver);
				}
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void MerchantRuleCreatedVerify() throws Throwable{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.FetchRulename, "Rule name"))
			{				
				String FetchName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.FetchRulename)).getText().trim();
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Edit button");
				Assert.Javascriptexecutor_forClick(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Edit button");
				if(FetchName.contains(Excel_Handling.Get_Data(TC_ID, "RuleTitleName").trim()))
				{
					Extent_Reporting.Log_report_img("Rule Created successfully", "Passed", driver);
				}else{
					Extent_Reporting.Log_Fail("Rule Title Name did not matched" , "Failed", driver);
				}
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void MerchantRuleCreatedVerifyTest() throws Throwable{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.FetchRulename, "Rule name"))
			{				
				String FetchName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.FetchRulename)).getText().trim();
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Edit button");
				//Assert.Javascriptexecutor_forClick(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Edit button");
				if(FetchName.contains(Excel_Handling.Get_Data(TC_ID, "RuleTitleName").trim()))
				{
					Extent_Reporting.Log_report_img("Rule Created successfully", "Passed", driver);
				}else{
					Extent_Reporting.Log_Fail("Rule Title Name did not matched" , "Failed", driver);
				}
			}else{

				Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void RuleCreatedEditMode() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Details Rule button"))
			{				
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.RuleEditButton, "Rule Edit button");
				Extent_Reporting.Log_report_img("Rule Edit button clicked" , "Failed", driver);				
			}else{
				Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			Extent_Reporting.Log_Fail("Rule Title Name does not exist" , "Failed", driver);
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void RuleAddDetails() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, TextVal, "Title name");
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FieldClick, "MCCField box");
				Thread.sleep(1000);
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FirstOptionClick, "MCC First option");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, Excel_Handling.Get_Data(TC_ID, "ChannelName"), "Channel name");
				//Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, "netbnk", "Channel name");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.CardBankName, Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel"), "Channel name");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.AmountSelectBox, "Greater than", "Amount Greater Than");				
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.AmountEditBox, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal"), "Title Name");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.PTCEditBox, Excel_Handling.Get_Data(TC_ID, "PTC_Vlaue"), "PTC value");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MSFEditBox, Excel_Handling.Get_Data(TC_ID, "MSF_Value"), "MSF Value");						
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void MCC_MultipleChoiceDropDownVerify() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TitleInput, "Respective Text Xpath"))
			{									
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FieldClick, "MCCField box");
				Thread.sleep(1000);
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FirstOptionClick, "MCC First option");
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FieldClick, "MCCField box");
				Thread.sleep(1000);
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_SecondOptionClick, "MCC second option");
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_FieldClick, "MCCField box");
				Thread.sleep(1000);
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MCC_thirdOptionClick, "MCC third option");
				Extent_Reporting.Log_report_img("It's selecting more than one MCC drop box value", "Passed", driver);
				Thread.sleep(1000);				
				List<WebElement> crossCount = driver.findElements(By.xpath("//span[@class='select2-selection__choice__remove']"));
				for(int i=1;i<=crossCount.size();i++)
				{
					driver.findElement(By.xpath("//span[@class='select2-selection__choice__remove']")).click();
				}
				Extent_Reporting.Log_report_img("It's removed MCC drop box value", "Passed", driver);
				Thread.sleep(1000);
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void ErrorNotifications(String xpath, String ErrXpath, String passVal,String ExpectedVal) throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, xpath, "Respective Text Xpath"))
			{									
				Assert.inputText(driver, xpath, passVal, "Title Name");
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.SurchargeSubmitBtn, "Submit button");
				String TextVal = driver.findElement(By.xpath(ErrXpath)).getText().trim();
				System.out.println(TextVal);
				if(TextVal.equalsIgnoreCase(ExpectedVal.trim()))
				{
					Extent_Reporting.Log_Pass("Field name is: "+TextVal, "Expected as: "+ExpectedVal);
					Extent_Reporting.Log_report_img("Respective Message is exist", "As expected", driver);			
				}else{
					Extent_Reporting.Log_Fail("Respective text Does not exist as "+TextVal , "Failed", driver);
				}						
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective Message does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}



	/**
	 * @author parmeshwar Sakole
	 * @Method Name: Card Selection method.
	 * Following method is used Handling Multiple Card options
	 * @throws Exception
	 */
	public void AddGlobalDropDown_Verify() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, "Channel name" ))
			{         	
				WebElement selectDropBox = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.ChannelName));
				Select select =new Select(selectDropBox);
				List<WebElement> optionValue = select.getOptions();
				for(int i =1;i<optionValue.size();i++)
				{				
					WebElement selectDropBox1 = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.ChannelName));
					Select select1 =new Select(selectDropBox1);
					Assert.selectDropBoxValue(driver, AirPay_Payment_MA_Panel_PageObject.ChannelName, i, " Bank Name");//(driver, Netbank_DropDown, value[i], value[i+1]+" Bank ");			
					//Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, Netbank_DropDown, "LAXMI VILAS BANK", " Bank Name");//(driver, Netbank_DropDown, value[i], value[i+1]+" Bank ");			
					bankName =  select1.getFirstSelectedOption().getText();
					Extent_Reporting.Log_report_img(bankName+" Bank Selected", "Passed", driver);

				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail("Drop Down values does not exist ex expected",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Drop Down values does not exist ex expected",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			throw new Exception("Test failed due to local host page not displayed");
		}
	}






	public void DuplicateRuleNameErrorExist(String MesgName) throws Exception{
		try{

			Assert.waitForPageToLoad(driver);
			Thread.sleep(5000);
			WebElement hiddenDiv = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.ExistRuleErrorMsg));
			String errMsg = hiddenDiv.getText(); // does not work (returns "" as expected)
			String script = "return arguments[0].innerText";
			errMsg = (String) ((JavascriptExecutor) driver).executeScript(script, hiddenDiv);			
			System.out.println(errMsg);
			if(errMsg.contains(MesgName))
			{
				Extent_Reporting.Log_Pass("Repective  Message is exist", "Respective Msg is exist as :"+errMsg);
				Extent_Reporting.Log_report_img("Respective Message is exist", "Passed", driver);	

			}else
			{	
				Extent_Reporting.Log_Fail("Repective Message does not exist", "Error Msg is:"+errMsg, driver);
			}	

		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}






	public static String TextVal = null;
	public void GetTextFromWeb(String Xpath) throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, Xpath, "Respective Text Xpath"))
			{	
				TextVal = driver.findElement(By.xpath(Xpath)).getText().trim();
				System.out.println(TextVal);
				if(!TextVal.isEmpty()){
					Extent_Reporting.Log_Pass("Field name is: "+TextVal, "As Expected");
					Extent_Reporting.Log_report_img("Respective Link or field is exist", "As expected", driver);			
				}else{
					Extent_Reporting.Log_Fail("Respective text Does not exist as "+TextVal , "Failed", driver);
				}			
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void InputValue(String Xpath,String HardCodeVal) throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, Xpath, "Respective Text Xpath"))
			{	
				Assert.inputText(driver, Xpath, HardCodeVal, "Value entered");		
			}else{

				Extent_Reporting.Log_Fail("Respective Input field Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}



	public void DetailsLinkClicked(String Xpath, String Deatils) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, Xpath, Deatils+" is"))
			{				
				Assert.Clickbtn(driver, Xpath, Deatils+" ");
				Extent_Reporting.Log_Pass("Field name is: "+Deatils, "Expected as: "+Deatils);
				Extent_Reporting.Log_report_img("Respective Link Clicked", "Passed", driver);								
			}else{				
				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective Link does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");			
		}
	}



	public static String flag = null;
	public void Verify_Button_Color(String Xpath) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, Xpath, "RespectiveButton"))
			{					
				WebElement switchLabel = driver.findElement(By.xpath(Xpath));
				String pseudo = ((JavascriptExecutor)driver)
						.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('right');",switchLabel).toString();		
				System.out.println("sudo:"+pseudo);
				if(pseudo.equalsIgnoreCase("47px")){
					System.out.println("OFF Save Card");
					Assert.Clickbtn(driver, Xpath, "OFF Clicked");
					Thread.sleep(5000);
					Extent_Reporting.Log_Pass("ON Activated", "Passed");
					Extent_Reporting.Log_report_img("ON Activate", "IMG", driver);
					flag= "ON";
				}else if(pseudo.equalsIgnoreCase("0px")){					
					System.out.println("ON Save Card");
					Assert.Clickbtn(driver, Xpath, "ON Clicked");
					Thread.sleep(5000);
					Extent_Reporting.Log_Pass("OFF Activated", "Passed");
					Extent_Reporting.Log_report_img("OFF Activate", "IMG", driver);
					flag = "OFF";
				}else{
					Extent_Reporting.Log_Fail("Save button does not exist", "Failed", driver);
				}						
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}
	
	
	public String Verify_SurchargeFlag_Mode(String Xpath) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, Xpath, "RespectiveButton"))
			{					
				WebElement switchLabel = driver.findElement(By.xpath(Xpath));
				String pseudo = ((JavascriptExecutor)driver)
						.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('right');",switchLabel).toString();		
				System.out.println("sudo:"+pseudo);
				if(pseudo.equalsIgnoreCase("47px"))
				{
					System.out.println("OFF Save Card");
					Thread.sleep(5000);
					Extent_Reporting.Log_Pass("ON Activated", "Passed");
					Extent_Reporting.Log_report_img("ON Activate", "IMG", driver);
					flag= "OFF";
				}else if(pseudo.equalsIgnoreCase("0px"))
				{					
					System.out.println("ON Save Card");
					Thread.sleep(5000);
					Extent_Reporting.Log_Pass("OFF Activated", "Passed");
					Extent_Reporting.Log_report_img("OFF Activate", "IMG", driver);
					flag = "ON";
				}else{
					Extent_Reporting.Log_Fail("Save button does not exist", "Failed", driver);
				}						
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}
			return flag;
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void Verify_Surchagre_OFFVerify(String Xpath) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, Xpath, "RespectiveButton"))
			{					
				WebElement switchLabel = driver.findElement(By.xpath(Xpath));
				String pseudo = ((JavascriptExecutor)driver)
						.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('right');",switchLabel).toString();		
				System.out.println("sudo:"+pseudo);
				if(pseudo.equalsIgnoreCase("47px")){
					System.out.println("OFF Save Card");
					Thread.sleep(5000);
					Extent_Reporting.Log_Pass("OFF Activated", "Passed");
					Extent_Reporting.Log_report_img("OFF Activated", "IMG", driver);
					flag= "ON";
				}else if(pseudo.equalsIgnoreCase("0px")){					
					Extent_Reporting.Log_Fail("Default rule should not be OFF", "Failed", driver);
					Extent_Reporting.Log_report_img("OFF Activate", "IMG", driver);
				}else{
					Extent_Reporting.Log_Fail("Save button does not exist", "Failed", driver);
				}						
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}


	public void Verify_Surchagre_Button(String Xpath) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, Xpath, "RespectiveButton"))
			{					
				WebElement switchLabel = driver.findElement(By.xpath(Xpath));
				String pseudo = ((JavascriptExecutor)driver)
						.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('right');",switchLabel).toString();		
				System.out.println("sudo:"+pseudo);
				if(pseudo.equalsIgnoreCase("0px")){					
					System.out.println("ON Save Card");
					Assert.Clickbtn(driver, Xpath, "ON Clicked");
					Thread.sleep(5000);
					Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MM_Update_surchagrebtn, "Update button");					
					Extent_Reporting.Log_Pass("OFF Activated", "Passed");
					Extent_Reporting.Log_report_img("OFF Activate", "IMG", driver);
					flag = "OFF";
				}else if(pseudo.equalsIgnoreCase("0px")){										
					Extent_Reporting.Log_Fail("Default rule should not be OFF", "Failed", driver);
					Extent_Reporting.Log_report_img("OFF Activate", "IMG", driver);
					flag = "OFF";
				}else{
					Extent_Reporting.Log_Fail("Save button does not exist", "Failed", driver);
				}						
			}else{

				Extent_Reporting.Log_Fail("Respective text Does not exist" , "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective menu does not exost", "Failed", driver);
			throw new Exception("Serch filter does not exist");

		}
	}

	public void SubmitBtnClick() throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_SigleSubmitBtn, "Submit button"))
			{	
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MM_SigleSubmitBtn, "Submit button");
				Assert.waitForPageToLoad(driver);
				Thread.sleep(5000);
				Extent_Reporting.Log_report_img("Submit button", "Passed", driver);			
			}else{
				Extent_Reporting.Log_Fail("Submit button does not exost", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Submit button does not exost", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}



	public void ON_OFF_SaveCrad() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(flag.equalsIgnoreCase("ON"))
			{	
				boolean checkbxselected = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_PP_SaveCardchkbox)).isSelected();
				System.out.println(checkbxselected);
				if(checkbxselected==true)
				{
					Extent_Reporting.Log_Pass("Check box selected", "Passed");
					Extent_Reporting.Log_report_img("Submit button", "Passed", driver);
				}else{
					Extent_Reporting.Log_Fail("Check boc shuld be check", "Failed", driver);
				}

			}else if(flag.equalsIgnoreCase("OFF")){
				boolean checkbxselected = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_PP_SaveCardchkbox)).isSelected();
				if(checkbxselected==false)
				{
					Extent_Reporting.Log_Pass("Check box not selected", "Passed");
					Extent_Reporting.Log_report_img("Submit button", "Passed", driver);

				}else{
					Extent_Reporting.Log_Fail("Check boc shuld not be checked by default", "Failed", driver);
				}				
			}else{
				Extent_Reporting.Log_Fail("Save Card option does not exist", "Failed", driver);
			}
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Save Card option does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}

	public void Enable_Disable_SaveCardChkBox() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(flag.equalsIgnoreCase("ON"))
			{					
				try{
					if(Assert.isElementDisplay(driver, AirPay_Payment_MA_Panel_PageObject.MM_PP_SaveCardchkbox))
					{
						Extent_Reporting.Log_Pass("Save card check box is exist as expected ", "Passed");
						Extent_Reporting.Log_report_img("Save card check box", "Passed", driver);	
					}
				}catch(Exception t){

					Extent_Reporting.Log_Fail("Check box should be present for save Card but its not there", "Failed", driver);				
				}		
			}else if(flag.equalsIgnoreCase("OFF")){
				try{
					if(Assert.isElementDisplay(driver, AirPay_Payment_MA_Panel_PageObject.MM_PP_SaveCardchkbox))
					{
						Extent_Reporting.Log_Fail("Check box should Not be present for Save Card", "Failed", driver);						
					}
				}catch(Exception t){
					Extent_Reporting.Log_Pass("Save card check box is exist as expected ", "Passed");
					Extent_Reporting.Log_report_img("Save card check box", "Passed", driver);

				}					
			}else{
				Extent_Reporting.Log_Fail("Save Card option does not exist", "Failed", driver);
			}
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Save Card option does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}


	public static int Counter;
	public void Domain_URLFindOut() throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails, "URL Detail Table"))
			{					
				List<WebElement> URLRows = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails));
				System.out.println(""+URLRows.size());
				for(int i=1;i<=URLRows.size();i=i+2)
				{
					String DomainName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+i+"]/td[2]")).getText().trim();
					String SuccessURL = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+i+"]/td[3]")).getText().trim();
					String IpnUrl = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+i+"]/td[4]")).getText().trim();
					if(DomainName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "Doamin_name").trim())
							&& SuccessURL.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "SuccessURL").trim())
							&& IpnUrl.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "IpnURL").trim())){

						Extent_Reporting.Log_Pass("Exact URL Row Find out at :"+i+"th Position", "Passed");
						Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+i+"]/td[7]/div[@class='btnlink']/a", "Details Button");
						Counter = i;
						break;						
					}
					if(i==URLRows.size())
					{
						Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
					}
				}
			}else{
				Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}


	public static int Maxval=1;
	public static int Minval=1;
	public void BANK_DetailsFindOut() throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails, "URL Detail Table"))
			{					
				List<WebElement> Bank_URLRows = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"
						+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL));
				System.out.println("Bank Rows: "+Bank_URLRows.size());
				for(int i=1;i<=Bank_URLRows.size();i++)
				{
					String BankName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[1]")).getText().trim();
					System.out.println("bankName:"+BankName);
					String MidType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[2]")).getText().trim();
					System.out.println("bankName:"+MidType);
					String BankStatus = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[3]")).getText().trim();
					System.out.println("bankName:"+BankStatus);
					if(BankName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim())
							&& MidType.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "TransactionTypr").trim())
							&& BankStatus.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "Status").trim()))
					{						
						List<WebElement> priorityElement= driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"
								+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"/td/input"));

						for(int j =1;j<priorityElement.size();j++)
						{						
							String priortival = driver.findElement(By.xpath("((//*[@class='table border_table']/tbody/tr)["+Counter+"]/td[7]/div[@class='btnlink']//following::tbody[1]/tr/td/input)["+j+"]")).getAttribute("value");
							int priIntval = Integer.parseInt(priortival);

							if(Minval>priIntval)
							{
								Minval = priIntval;	
								System.out.println("Minval: "+Minval);

							}
							if(Maxval<priIntval)
							{	
								Maxval = priIntval;	
								System.out.println("Maxval:"+Maxval);
							}
							if(j==priorityElement.size())
							{
								Extent_Reporting.Log_Fail("There is an issue with priority", "failed", driver);
							}																			
						}
						System.out.println("Maxium val: "+Maxval);
						System.out.println("minium val :"+Minval);		
						Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"
								+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"/td/input[@value='"+Maxval+"']", Integer.toString(Maxval+2), "Max val");					
						Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"
								+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"/td/input[@value='"+Minval+"']", Integer.toString(Maxval+1), "Minval");						
						Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td/input",Integer.toString(Minval), "Min val");						
						Extent_Reporting.Log_Pass("Exact URL Row Find out at :"+i+"th Position", "Passed state");
						Thread.sleep(10000);
						break;	

					}
					if(i==Bank_URLRows.size())
					{
						Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
					}									
				}
			}							
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}

	public void MerchantTransactionID() throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_MerchantTrxID, "Merchant Transaction ID"))
			{
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.MM_MerchantTrxID, AirPay_PaymentPage_BusinessLogic.GetOrderID.trim(), "Merchant Transaction ID");
				Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MM_MerchantTrxID, "Merchant Transaction ID");
				Extent_Reporting.Log_report_img("Merchant transaction id", "Passed", driver);
				Extent_Reporting.Log_Pass("Merchant ID Entered as:"+AirPay_PaymentPage_BusinessLogic.GetOrderID, "Passed");
			}else{
				Extent_Reporting.Log_Fail("Transaction Record does not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Transaction Record does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}

	public void TransactionRecords() throws Throwable{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_TransactionRecords, "Transaction records")){
				List<WebElement> transactionHeaders = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_TransactionRecords));					
				Assert.Javascriptexecutor_forClick(driver, AirPay_Payment_MA_Panel_PageObject.MM_TransactionRecords+"["+transactionHeaders.size()+"]/td/a[text()='"+AirPay_PaymentPage_BusinessLogic.GetOrderID+"']","MMTransaction_Clicked");
				if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MMTransactiondetailsPage, "Transaction Details page"))
				{
					Extent_Reporting.Log_Pass("Transaction details page is exist as expected", "Passed");
				}else{
					Extent_Reporting.Log_Fail("Transaction Details Page does not exist", "failed", driver);
				}
			}else{
				Extent_Reporting.Log_Fail("Transaction Records does not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Transaction Record does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}

	public void Bank_DetailsURL(String active, String inactive ) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_BankDetails, "BANK URL Detail Table"))
			{					
				List<WebElement> URLRows = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails));
				System.out.println(""+URLRows.size());
				for(int i=1;i<=URLRows.size();i++)
				{
					String BankName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[1]")).getText().trim();
					String ChannelName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[2]")).getText().trim();
					String MidType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[3]")).getText().trim();
					String Status = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[4]")).getText().trim();

					if(BankName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim())
							&& ChannelName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim())
							&& MidType.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "TransactionTypr").trim())
							&& (Status.equalsIgnoreCase(active)||Status.equalsIgnoreCase(inactive)))
					{

						Extent_Reporting.Log_Pass("Exact URL Row Find out at :"+i+"th Position", "Passed");
						Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[5]/span/a", "Details Button");
						Counter = i;
						break;						
					}
					if(i==URLRows.size())
					{
						Extent_Reporting.Log_Fail("Respective Bank name Status not matched", "Failed", driver);
					}
				}
			}else{
				Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}

	public void Bank_DetailsStatusActiveInactive(String active,String inactive) throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_BankDetails, "BANK URL Detail Table"))
			{					
				List<WebElement> URLRows = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails));
				System.out.println(""+URLRows.size());
				for(int i=1;i<=URLRows.size();i++)
				{
					String BankName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[1]")).getText().trim();
					String ChannelName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[2]")).getText().trim();
					String MidType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[3]")).getText().trim();
					String Status = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[4]")).getText().trim();
					System.out.println(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel"));
					System.out.println(Excel_Handling.Get_Data(TC_ID, "ChannelName"));
					System.out.println(Excel_Handling.Get_Data(TC_ID, "TransactionTypr"));

					if(BankName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim())
							&& ChannelName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "ChannelName").trim())
							&& MidType.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "TransactionTypr").trim())
							&& (Status.equalsIgnoreCase(active)||Status.equalsIgnoreCase(inactive)))
					{

						Extent_Reporting.Log_Pass("Exact URL Row Find out at :"+i+"th Position", "Passed");
						Assert.clickButton(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindBANKDetails+"["+i+"]/td[5]/span/a", "Details Button");
						Counter = i;
						break;						
					}
					if(i==URLRows.size())
					{
						Extent_Reporting.Log_Fail("Respective Bank name Status not matched", "Failed", driver);
					}
				}
			}else{
				Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			}				
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}

	public void BankDetails_Page() throws Exception{
		try{
			Thread.sleep(20000);
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MMBankdetailsPage, "Bank details Page")){			
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MMbankStatus, Excel_Handling.Get_Data(TC_ID, "Status").trim(), "Status Drop Down");			
				Extent_Reporting.Log_Pass("Bank Details Entered", "Passed");
				Extent_Reporting.Log_report_img("Bank Details", "Image", driver);
			}else{
				Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}

	public static String MINADDONE =null;
	public static String MINMINUSONE =null;
	public static String MAXADDONE =null;
	public static String MAXMINUSONE =null;

	public void BankDetails_MinumAndMaxAmt_Val() throws Exception{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.BankURLMiniumVal, "Bank details Page")){						
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.BankURLMiniumVal, Excel_Handling.Get_Data(TC_ID, "MinumAmtVal").trim(), "Minium Amout");
				Assert.inputText(driver, AirPay_Payment_MA_Panel_PageObject.BankURLMaxiumVal, Excel_Handling.Get_Data(TC_ID, "MaxAmtVal").trim(), "Maxium Amout");				
				Extent_Reporting.Log_Pass("Bank Details Entered", "Passed");
				Extent_Reporting.Log_report_img("Bank Details", "Image", driver);
				double MinVal =Double.parseDouble(Excel_Handling.Get_Data(TC_ID, "MinumAmtVal").trim());
				double MaxVal=Double.parseDouble(Excel_Handling.Get_Data(TC_ID, "MaxAmtVal").trim());
				double MinAddOne = MinVal+0.00;
				double MinMinusOne = MinVal-0.01;
				double MaxAddOne = MaxVal+0.01;
				double MaxMinusOne = MaxVal-0.00;
				MINADDONE = String.valueOf(MinAddOne);
				MINMINUSONE= String.valueOf(MinMinusOne);
				MAXADDONE = String.valueOf(MaxAddOne);
				MAXMINUSONE = String.valueOf(MaxMinusOne);			
			}else{
				Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}




	public void URLDetails_Page() throws Exception{
		try{
			Thread.sleep(20000);
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MMURLdetailsPage, "URL details Page")){			
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, AirPay_Payment_MA_Panel_PageObject.MMbankStatus, Excel_Handling.Get_Data(TC_ID, "Status").trim(), "Status Drop Down");			
				Extent_Reporting.Log_Pass("Bank Details Entered", "Passed");
				Extent_Reporting.Log_report_img("Bank Details", "Image", driver);
			}else{
				Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}


	public void BankDetails_PageSaveBtn() throws Exception{
		try{
			//Thread.sleep(20000);
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MMBankSaveBtn, "Bank details Save Button")){							
				Assert.Clickbtn(driver, AirPay_Payment_MA_Panel_PageObject.MMBankSaveBtn, "Save Button");
				Extent_Reporting.Log_Pass("Save Button", "Passed");
				Extent_Reporting.Log_report_img("Save Button Clicked", "Passed", driver);
				Thread.sleep(2000);
				//Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.BankEditSuccMsg, "Edit successfully Done");			
				Thread.sleep(20000);
				Extent_Reporting.Log_report_img("Save Button Clicked", "Passed", driver);
			}else{
				Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}

	public void ConfigurationModifiedSuccess() throws Exception
	{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.URLEditSuccMsg, "Edit successfully Done"))
			{	
				Extent_Reporting.Log_report_img("Modified Successfully", "Passed", driver);
				Extent_Reporting.Log_Pass("Respective message is dispalyed as expected", "Passed");
				Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.URLEditSuccMsg, "Edit successfully Done");			
			}else{
				Extent_Reporting.Log_Fail("Edit successfully not exist", "Failed", driver);

			}			
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Bank details Page does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");			
		}
	}



	public static String bankName = null;
	public void Select_Bank_DropDown_Selection() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, SelectBank_DropDown, "Select Bank Drop Down" ))
			{         	
				WebElement selectDropBox = driver.findElement(By.xpath(Netbank_DropDown));
				Select select =new Select(selectDropBox);
				List<WebElement> optionValue = select.getOptions();
				System.out.println(optionValue.size());
				for(int i =1;i<optionValue.size();i++)
				{								
					WebElement selectDropBox1 = driver.findElement(By.xpath(Netbank_DropDown));
					Select select1 =new Select(selectDropBox1);
					Assert.selectDropBoxValue(driver, Netbank_DropDown, i, " Bank Name");//(driver, Netbank_DropDown, value[i], value[i+1]+" Bank ");			
					Thread.sleep(2000);
					bankName =  select1.getFirstSelectedOption().getAttribute("value").trim();
					Thread.sleep(2000);
					System.out.println("FetchBank Name:"+bankName);
					System.out.println(Excel_Handling.Get_Data(TC_ID, "BankName").trim());
					if(bankName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName").trim()))
					{
						Extent_Reporting.Log_Pass("Bank Name Is exist as: "+bankName, "Passed");
						Extent_Reporting.Log_report_img("Bank Is exist as expected", "Passed", driver);
						break;
					}
					if(i==optionValue.size()-1)
					{
						Extent_Reporting.Log_Fail("Bank should be exist but not exist", "Passed", driver);
						break;

					}
				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			throw new Exception("Test failed due to local host page not displayed");
		}
	}

	public void Select_Bank_DropDown_SelectionNotExist() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, SelectBank_DropDown, "Select Bank Drop Down" ))
			{         	
				WebElement selectDropBox = driver.findElement(By.xpath(Netbank_DropDown));
				Select select =new Select(selectDropBox);
				List<WebElement> optionValue = select.getOptions();
				for(int i =1;i<=optionValue.size();i++)
				{				
					WebElement selectDropBox1 = driver.findElement(By.xpath(Netbank_DropDown));
					Select select1 =new Select(selectDropBox1);
					Assert.selectDropBoxValue(driver, Netbank_DropDown, i, " Bank Name");//(driver, Netbank_DropDown, value[i], value[i+1]+" Bank ");			
					//Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, Netbank_DropDown, Excel_Handling.Get_Data(TC_ID, "BankName").trim(), " Bank Name");//(driver, Netbank_DropDown, value[i], value[i+1]+" Bank ");			
					Thread.sleep(2000);
					bankName =  select1.getFirstSelectedOption().getAttribute("value").trim();
					Thread.sleep(2000);
					System.out.println("Bank Name: "+bankName);
					if(bankName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName").trim())){
						Extent_Reporting.Log_Fail("Bank Is exist but should not be exist", "Passed", driver);
						break;
					}
					if(i==optionValue.size()-1){
						Extent_Reporting.Log_Pass("Respective Bank is disabled as expeccted "+bankName, "Passed");
						Extent_Reporting.Log_report_img("Bank Disabled", "Passed", driver);
						break;
					}
				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			throw new Exception("Test failed due to local host page not displayed");
		}
	}

	public void URL_Details(String active,String inactive) throws Throwable{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails, "URL Detail Table"))
			{					
				List<WebElement> Bank_URLRows = driver.findElements(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"
						+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL));
				System.out.println("Bank Rows: "+Bank_URLRows.size());
				for(int i=1;i<=Bank_URLRows.size();i++)
				{
					String BankName = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[1]")).getText().trim();
					System.out.println("bankName:"+BankName);
					String MidType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[2]")).getText().trim();
					System.out.println("bankName:"+MidType);
					String BankStatus = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[3]")).getText().trim();
					System.out.println("bankName:"+BankStatus);
					if(BankName.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim())
							&& MidType.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "TransactionTypr").trim())
							&& (BankStatus.equalsIgnoreCase(active)|| BankStatus.equalsIgnoreCase(inactive)))
					{
						Extent_Reporting.Log_Pass("Exact URL Row Find out at :"+i+"th Position is", "Passed");
						Assert.Javascriptexecutor_forClick(driver, AirPay_Payment_MA_Panel_PageObject.MM_FindURLDetails+"["+Counter+"]/td[7]/div[@class='btnlink']"+AirPay_Payment_MA_Panel_PageObject.MM_Bank_URL+"["+i+"]/td[5]/span/a", "Edit button");						
						Counter = i;
						break;	
					}
					if(i==Bank_URLRows.size())
					{
						Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
					}									
				}
			}							
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}
	public static String AmtVal = null;
	public void SummaryAmtVerify(String PassedVal) throws Exception{
		try{			
			if(Excel_Handling.Get_Data(TC_ID, "Surcharge_Mode").contains("OFF"))
			{		
			
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.TotAmt, "Passed Amount"))
			{
				Assert.Clickbtn(driver, "//div[@class='sumbtn desksumbtn iplus']", "Amount Plus button");			
				 AmtVal = driver.findElement(By.xpath("//div[@class='main-amount-block show-amnt']//following::span[@id='total_amount']")).getText().trim();
				System.out.println("Amounnt value: "+AmtVal);
				if(AmtVal.contains(PassedVal)){
					Extent_Reporting.Log_report_img("Passed Amount is exist", "Passed", driver);
					Extent_Reporting.Log_Pass("Passed Amount is exist as expected"+AmtVal, "Max and Min Amount");
				}else{
					Extent_Reporting.Log_Fail("Amount didn't match", "Failed", driver);
				}
			}
			}else{
				AmtVal = driver.findElement(By.xpath("//span[@class='final-amount']")).getText().trim();
				Extent_Reporting.Log_report_img("Surcharge not applied", "Passed", driver);
				Extent_Reporting.Log_Pass("Surcharge Amount not applied ", "Total Amount: "+AmtVal);
			}	
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Amount didn't match", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}

	public void TransactionHistory() throws Exception
	{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MMTransactiondetailsPage, "Transaction History"))
			{
				String AirpayID = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMMerchantTransactionID)).getText().trim();
				System.out.println("AirpayID: "+AirpayID);
				System.out.println("AirpayIDActusal: "+AirPay_PaymentPage_BusinessLogic.GetOrderID);
				String  TransactionBank = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMTransacionBank)).getText().trim(); 
				System.out.println("TransactionBank: "+TransactionBank);
				System.out.println("TransactionBankActusal: "+Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim());
				String  TransactionType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMTypeOFTransaction)).getText().trim(); 
				System.out.println("TransactionType: "+TransactionType);
				System.out.println("TransactionTypeActusal: "+Excel_Handling.Get_Data(TC_ID, "BankName").trim());
				String  IssuingBank = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMTransacionIssuingBankName)).getText().trim(); 
				System.out.println("IssuingBank: "+IssuingBank);				
				System.out.println("IssuingBankDActusal: "+Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").trim());
				String  AirPayTransactionID = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMApTransacionCardCategory)).getText().trim(); 
				System.out.println("AirPayTransactionID: "+AirPayTransactionID);
				System.out.println("AirpayIDActusal: "+AirPay_Payment_Mode_CreditCard_BusinessLogic.AirPaytransactionID);				
				if(AirpayID.equalsIgnoreCase(AirPay_PaymentPage_BusinessLogic.GetOrderID))
				{
					Extent_Reporting.Log_Pass("Airpay ID is exist as expected", "Passed");
					if(TransactionBank.contains(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").split("-")[0].trim()))
					{
						Extent_Reporting.Log_Pass("Transaction Bank Name is exist as expected", "Passed");
						if(TransactionType.equalsIgnoreCase(Excel_Handling.Get_Data(TC_ID, "BankName").trim()))
						{
							Extent_Reporting.Log_Pass("Transaction Type is exist as expected", "Passed");
							if(IssuingBank.contains(Excel_Handling.Get_Data(TC_ID, "BankName_MAPanel").split("-")[0].trim()))
							{
								Extent_Reporting.Log_Pass("Issuing Bank Name is exist as expected", "Passed");
								if(AirPayTransactionID.equalsIgnoreCase(AirPay_Payment_Mode_CreditCard_BusinessLogic.AirPaytransactionID))
								{
									Extent_Reporting.Log_Pass("AirPayTransaction ID is exist as expected", "Passed");
									Assert.waitForPageToLoad(driver);
									Thread.sleep(2000);
									Extent_Reporting.Log_report_img("Transaction Details page", "Passed", driver);
								}else{
									Extent_Reporting.Log_Fail("Issuing Bank Type not Matced", "Failed", driver);
								}
							}else{
								Extent_Reporting.Log_Fail("Issuing Bank Type not Matced", "Failed", driver);
							}
						}else{
							Extent_Reporting.Log_Fail("Transaction Type not Matced", "Failed", driver);
						}
					}else{
						Extent_Reporting.Log_Fail("Transaction Bank not Matced", "Failed", driver);
					}
				}else{
					Extent_Reporting.Log_Fail("AirpayID not Matced", "Failed", driver);
				}
			}
		}catch(Exception e){
			Extent_Reporting.Log_Fail("Issue for Transaction History", "Failed", driver);
		}
	}

	public void TransactionHistoryWithSurchagre() throws Exception
	{
		try{
			if(Assert.isElementDisplayed(driver, AirPay_Payment_MA_Panel_PageObject.MMTransactiondetailsPage, "Transaction History"))
			{
				String AirpayID = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMMerchantTransactionID)).getText().trim();
				System.out.println("AirpayID: "+AirpayID);
				System.out.println("AirpayIDActusal: "+AirPay_PaymentPage_BusinessLogic.GetOrderID);
				String  TransactionBank = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMTotalAmount)).getText().split(" ")[1].trim();
				System.out.println("TransactionBank: "+TransactionBank);							
				if(Assert.isElementDisplay(driver, AirPay_Payment_MA_Panel_PageObject.MMSurchagerAmt))
				{
					String  TransactionType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMSurchagerAmt)).getText().split(" ")[1].trim();
					System.out.println("TransactionType: "+TransactionType);
					if(TransactionType.equalsIgnoreCase(AirPay_Payment_Mode_CreditCard_BusinessLogic.confFees))
					{
						Extent_Reporting.Log_Pass("Con fees on payment page :"+AirPay_Payment_Mode_CreditCard_BusinessLogic.confFees, "Con fees on MA panel page as: "+TransactionType);
						Extent_Reporting.Log_report_img("Surcharge amount Transaction is showing as expected ", "Passed", driver);
					}else{
						Extent_Reporting.Log_Fail("Con fees not matched", "Failed", driver);
					}	
				}				
				if(AirpayID.equalsIgnoreCase(AirPay_PaymentPage_BusinessLogic.GetOrderID))
				{
					Extent_Reporting.Log_Pass("Airpay ID is exist as expected", "Passed");
					if(TransactionBank.contains(AirPay_Payment_Mode_CreditCard_BusinessLogic.TotAmt))
					{
						Extent_Reporting.Log_Pass("Total amount on payment page :"+AirPay_Payment_Mode_CreditCard_BusinessLogic.TotAmt, "Total amount on MA panel page as: "+TransactionBank);
						Extent_Reporting.Log_report_img("Surcharge amount Transaction is showing as expected ", "Passed", driver);

					}else{
						Extent_Reporting.Log_Fail("Total amount not matched", "Failed", driver);
					}
				}else{
					Extent_Reporting.Log_Fail("AirpayID not Matced", "Failed", driver);
				}
			}
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Respective URL Does not exist", "Failed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}

	public void FailedTransaction() throws Exception{
		try{
			String  TransactionType = driver.findElement(By.xpath(AirPay_Payment_MA_Panel_PageObject.MMTransactionStatus)).getText().trim();
			System.out.println("TransactionType: "+TransactionType);
			if(TransactionType.contains("Failed"))
			{
				Extent_Reporting.Log_Pass("Transaction status is as :"+TransactionType, "Passed");
				Extent_Reporting.Log_report_img("Surcharge amount Transaction is showing as expected ", "Passed", driver);

			}else{
				Extent_Reporting.Log_Fail("Transaction status is as :"+TransactionType, "Passed", driver);
			}
		}catch(Exception t){
			t.printStackTrace();
			Extent_Reporting.Log_Fail("Transaction status as not expected", "Passed", driver);
			throw new Exception("Submit buttondoes not exist");

		}
	}
}