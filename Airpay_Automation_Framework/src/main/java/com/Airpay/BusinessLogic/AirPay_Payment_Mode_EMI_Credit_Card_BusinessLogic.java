package com.Airpay.BusinessLogic;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.Airpay.PageObject.Airpay_PaymentPage_PageObject;
import com.Airpay.Reporting.Extent_Reporting;
import com.Airpay.TestData.Excel_Handling;
import com.Airpay.Utilities.ElementAction;
import com.Airpay.Utilities.Log;

public class AirPay_Payment_Mode_EMI_Credit_Card_BusinessLogic extends Airpay_PaymentPage_PageObject {
	
	public WebDriver driver;
	public String TC_ID = "";
	ElementAction Assert = new ElementAction();
	//Log log = new Log();	
	public AirPay_Payment_Mode_EMI_Credit_Card_BusinessLogic(WebDriver driver, String TC_ID)
	{
		Log.info("AirPay_Payment_Mode_EMI_Credit_Card_BusinessLogic");
		this.driver = driver;
		this.TC_ID=TC_ID;
		//log = new Log();
	}
	
	public void EMI_CardHolderName() throws Exception{
		try{		   		   
			Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, EmiBankNameSelectDropDown, Excel_Handling.Get_Data(TC_ID, "BankName").trim(), "Bank Selected for EMI");					
			Assert.inputText(driver, EMICardNumber, Excel_Handling.Get_Data(TC_ID, "ValidCardNumber").trim(), "EMI Card Number");
			Assert.inputText(driver, EMICardHolderName, Excel_Handling.Get_Data(TC_ID, "BuyerFirstName").trim(), "Card Holder name");
			Assert.inputText(driver, EMICardExpDate,Excel_Handling.Get_Data(TC_ID, "CardExpDate").trim(), "EMI Card Number Exp Date");
			Assert.inputText(driver, EMICardCVVCode,Excel_Handling.Get_Data(TC_ID, "CardCVVCode").trim(), " card Number CVVCode");
			Assert.Clickbtn(driver, EMICardMakePaymtBtn, "EMI Card make payment button");		   
			Extent_Reporting.Log_report_img("Details has been Entered", "Passed", driver);

		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Some fields are not disp", "Failed", driver);   
			Log.error("Test failed due to page is navigating to payment page");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
		
	public void EMI_Invalid_card_Details() throws Exception{
		try{		   		   
			Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, EmiBankNameSelectDropDown, Excel_Handling.Get_Data(TC_ID, "BankName").trim(), "Bank Selected for EMI");					
			Assert.inputText(driver, EMICardNumber, Excel_Handling.Get_Data(TC_ID, "InvalidCardNumber").trim(), "Invalid EMI Card Number");
			Assert.inputText(driver, EMICardHolderName, Excel_Handling.Get_Data(TC_ID, "BuyerFirstName").trim(), "Card Holder name");
			Assert.inputText(driver, EMICardExpDate,Excel_Handling.Get_Data(TC_ID, "CardExpDate").trim(), "EMI Card Number Exp Date");
			Assert.inputText(driver, EMICardCVVCode,Excel_Handling.Get_Data(TC_ID, "CardCVVCode").trim(), " card Number CVVCode");
			Extent_Reporting.Log_report_img("Details has been Entered", "Passed", driver);
			AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
			obj.SurchargeForCommonFunction();
			Assert.waitForPageToLoad(driver);
			Assert.Clickbtn(driver, EMICardMakePaymtBtn, "EMI Card make payment button");		   
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Some fields are not disp", "Failed", driver);   
			Log.error("Test failed due to page is navigating to payment page");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
	public void EMI_Bank_Selection_withEMiType() throws Exception{
		try{
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, EmiBankNameSelectDropDown, Excel_Handling.Get_Data(TC_ID, "BankName").trim(), "Bank Selected for EMI");					
				List<WebElement> radiobtn = driver.findElements(By.xpath(EMIActive_radio_verfiy));
				List<WebElement> Month = driver.findElements(By.xpath(EMIMonthname));			
				for(int i =1;i<radiobtn.size();i++)
				{
					radiobtn.get(i).click();
					String Month_period = Month.get(i).getText();
					Extent_Reporting.Log_report_img("Checking Radio button", "Passed", driver);
					Extent_Reporting.Log_Pass("Radio button checked for the EMI"+Month_period, "Passed");
				}
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("EMI option table not in proper", "Failed", driver);   
			Log.error("EMI Option issue");
			e.printStackTrace();
			//throw new Exception("EMI Option Issue");
		}
	}

	/**
	 * @author parmeshwar Sakole
	 * @Method Name: Card Selection method.
	 * Following method is used Handling Multiple Card options
	 * @throws Exception
	 */
	public static String bankName = null;
	public void EMI_bank_Selection_verification() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, EmiBankNameSelectDropDown, "Select Bank Drop Down" ))
			{         	
				WebElement selectDropBox = driver.findElement(By.xpath(EmiBankNameSelectDropDown));
				Select select =new Select(selectDropBox);
				List<WebElement> optionValue = select.getOptions();
				for(int i =1;i<optionValue.size();i++)
				{				
					WebElement selectDropBox1 = driver.findElement(By.xpath(EmiBankNameSelectDropDown));
					Select select1 =new Select(selectDropBox1);
					Assert.selectDropBoxValue(driver, EmiBankNameSelectDropDown, i, " Bank Name");//(driver, Netbank_DropDown, value[i], value[i+1]+" Bank ");			
					//Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, EmiBankNameSelectDropDown, Excel_Handling.Get_Data(TC_ID, "BankName").trim(), "EMI surcharge");
					bankName =  select1.getFirstSelectedOption().getText();
					Extent_Reporting.Log_report_img(bankName+" Bank Selected", "Passed", driver);					
				} 
				Assert.inputText(driver, EMICardNumber, Excel_Handling.Get_Data(TC_ID, "InvalidCardNumber").trim(), "Invalid EMI Card Number");
				Assert.inputText(driver, EMICardHolderName, Excel_Handling.Get_Data(TC_ID, "BuyerFirstName").trim(), "Card Holder name");
				Assert.inputText(driver, EMICardExpDate,Excel_Handling.Get_Data(TC_ID, "CardExpDate").trim(), "EMI Card Number Exp Date");
				Assert.inputText(driver, EMICardCVVCode,Excel_Handling.Get_Data(TC_ID, "CardCVVCode").trim(), " card Number CVVCode");
				Extent_Reporting.Log_report_img("Details has been Entered", "Passed", driver);
				AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
				obj.SurchargeForCommonFunction();
				Assert.waitForPageToLoad(driver);
				
			}
			else{
				Extent_Reporting.Log_Fail("Bank Name selection issue",	"Failed",driver);
				Log.error("Bank Name selection does not displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" Bank option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
	/**
	 * @author parmeshwar Sakole
	 * @Method Name: Card Selection method.
	 * Following method is used Handling Multiple Card options
	 * @throws Exception
	 */
	public void EMI_Blank_val_RedLineErrNotification() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");
			if(Assert.isElementDisplayed(driver, EMICardMakePaymtBtn, "Make payment button" ))
			{  
				Extent_Reporting.Log_report_img("It's entering without filling any details", "Passed", driver);
				Assert.Clickbtn(driver, EMICardMakePaymtBtn, "EMI Card make payment button");
				List<WebElement> RedLineErr = driver.findElements(By.xpath(EMIRedLineError));
				for(int i =1;i<=RedLineErr.size();i++)
				{
					String fieldBlnakName = driver.findElement(By.xpath("("+EMIRedLineError+"//label)["+i+"]")).getText().trim();				
					Extent_Reporting.Log_Pass("Red Line error is exist for "+fieldBlnakName, "Passed");
					Extent_Reporting.Log_report_img("Red line Error exist", fieldBlnakName, driver);				
				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail("Bank Name selection issue",	"Failed",driver);
				Log.error("Bank Name selection does not displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" Bank option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
		
	public void BharatQRMakePayBtn() throws Exception{
		try{		   		   			
			Extent_Reporting.Log_report_img("Bharat QR option is exist", "Passed", driver);
			AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
			obj.SurchargeForCommonFunctionNotclickplus();
			Assert.Clickbtn(driver, BharatOrMakepaymtBtn, "Bharat QR make payment");		   
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Bharat QR option does not disp", "Failed", driver);   
			Log.error("Bharat QR option issue");
			e.printStackTrace();
			//throw new Exception("Bharat QR option");
		}
	}		
	
	public void AmexZeClick() throws Exception{
		try{		   		   			
			Extent_Reporting.Log_report_img("AmexeZeClick is exist as expected", "Passed", driver);
			Assert.Clickbtn(driver, AmexeZclickMakePaymentBtn, "make payment");	
			Assert.waitForPageToLoad(driver);
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("AmexeZeClick does not disp", "Failed", driver);   
			Log.error("Bharat QR option issue");
			e.printStackTrace();
			//throw new Exception("Bharat QR option");
		}
	}
	
	public void Amazon_pay() throws Exception{
		try{		   		   			
			Extent_Reporting.Log_report_img("Amazon_pay is exist as expected", "Passed", driver);			
			AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
			obj.SurchargeForCommonFunctionNotclickplus();
			Assert.Clickbtn(driver, AmazonMakePaymentBtn, "make payment");	
			Assert.waitForPageToLoad(driver);
			Thread.sleep(10000);
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Amazon_pay does not disp", "Failed", driver);   
			Log.error("Amazon_pay issue");
			e.printStackTrace();
			//throw new Exception("Amazon_pay issue");
		}
	}	
		
	public void Verify_BharatQR_CrossBtnClick() throws Exception{
		try{	
			Thread.sleep(5000);
			if(Assert.isElementDisplayed(driver, BharatQRbarCodeImg, "Bharat QR code"))
			{
				Assert.Verify_Image(driver, BharatQRbarCodeImg, "Bar code");
				Assert.Clickbtn(driver, BharatQRBtnclose, "Bharat QR cross close button");
				Extent_Reporting.Log_report_img("Bharat QR code is exist", "Passed", driver);
			}else{			
				Extent_Reporting.Log_Fail("Bharat QR Popup does not exist","failed",driver);   
				//throw new Exception("Test failed due to local host page not displayed");
			}	   
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Bar code issue", "Failed", driver);   
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}	
		
	public void Verify_BharatQR_BarCodeImg() throws Exception{
		try{	
			Thread.sleep(5000);
			if(Assert.isElementDisplayed(driver, BharatQRbarCodeImg, "Bharat QR code"))
			{
				Assert.Verify_Image(driver, BharatQRbarCodeImg, "Bar code");
				Extent_Reporting.Log_report_img("Bharat QR code is exist", "Passed", driver);
			}else{			
				Extent_Reporting.Log_Fail("Bharat QR Popup does not exist","failed",driver);   
				//throw new Exception("Test failed due to local host page not displayed");
			}	   
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Bar code issue", "Failed", driver);   
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
	
	public void Card_InvalidMesgVerify() throws Exception{
		try{	
			Thread.sleep(10000);
			if(Assert.isElementDisplayed(driver, CardInvalidErrMsgVerify, "Error Message"))
			{
				String errMsg = driver.findElement(By.xpath(CardInvalidErrMsgVerify)).getText();
				System.out.println(errMsg);
				if(errMsg.contains("Transaction Operation Failed - Card No, not valid. Card Number is Invalid")
						||errMsg.contains("Transaction Operation Failed - Card No, not valid. Card Number Verification Failed") 		                    
						||errMsg.contains("Please use a valid debit card issued in india")|| errMsg.contains("Improper Card Name Entered")
						||errMsg.contains("Credit Card Number is Empty")||errMsg.contains("We are sorry but the transaction failed. Try paying using another method")
						|| errMsg.contains("This card is not valid for selected bank.")
						|| errMsg.contains("Cancelled by user. Please try paying using another method?")
						|| errMsg.contains("We are sorry, but the transaction failed. Try using another payment method. If any amount is debited from your account, the same will be credited back to your account in 3-4 working days.")){	
					Extent_Reporting.Log_Pass("Repective Error Message is exist", "Error Msg is:"+errMsg);
					Extent_Reporting.Log_report_img("Respective Error Message is exist", "Passed", driver);		    	 
				}else{
	
					Extent_Reporting.Log_Fail("Respective error Message does not exist", "Failed", driver);
				}
			}else{
				Extent_Reporting.Log_Fail("Respective error Message does not exist", "Failed", driver);
			}
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Respective error Message does not exist", "Failed", driver);
			Log.error("Respective error Message does not exist");
			e.printStackTrace();
			//throw new Exception("Respective error Message does not exist");
		}
	}
	
	public static String errMsg = null;
	public void sessionTimeOut_errMsgBTQR() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Thread.sleep(10000);
			for(int i=1;i<=1;i++)
			{
			 if(Assert.isElementDisplayed(driver, SessionTimer, "Session timer"))
			 {				 
				boolean timerState = driver.findElement(By.xpath(SessionTimer)).isDisplayed();
				System.out.println(timerState);	
				if(timerState==true){					
					Thread.sleep(190000);				
					Assert.waitForPageToLoad(driver);
					WebElement hiddenDiv = driver.findElement(By.xpath(CardInvalidErrMsgVerify));
					errMsg = hiddenDiv.getText(); // does not work (returns "" as expected)
					String script = "return arguments[0].innerText";
					errMsg = (String) ((JavascriptExecutor) driver).executeScript(script, hiddenDiv);			
					System.out.println(errMsg);					
					Assert.waitForPageToLoad(driver);
					if(errMsg.contains("REJECTED. Please try paying using another method?")
							||errMsg.contains("We are sorry but the transaction failed. Try paying using another method?") 		                    
							||errMsg.contains("Please use a valid debit card issued in india")|| errMsg.contains("Improper Card Name Entered")
							||errMsg.contains("FAILED. Please try paying using another method?")||errMsg.contains("We are sorry but the transaction failed. Try paying using another method"))
					{
						Extent_Reporting.Log_Pass("Repective Error Message is exist", "Error Msg is:"+errMsg);
						Extent_Reporting.Log_report_img("Respective Error Message is exist", "Passed", driver);	
						break;
					}
				}else{	
					Extent_Reporting.Log_Fail("Repective Error Message does not exist", "Error Msg is:"+errMsg, driver);
				}
			 }else{
					Extent_Reporting.Log_Fail("Session timer does not exist", "Error Msg is:"+errMsg, driver);
					break;
			 }
			}
		}catch(Exception e){
			Extent_Reporting.Log_Fail("Repective Error Message does not exist", "Error Msg is:"+errMsg, driver);

		}

	
		
	}
	public void sessionTimeOut_errMsg() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Thread.sleep(10000);
			for(int i=1;i<=1;i++)
			{
			 if(Assert.isElementDisplayed(driver, SessionTimer, "Session timer"))
			 {				 
				boolean timerState = driver.findElement(By.xpath(SessionTimer)).isDisplayed();
				System.out.println(timerState);	
				if(timerState==true){					
					Thread.sleep(190000);				
					Assert.waitForPageToLoad(driver);
					WebElement hiddenDiv = driver.findElement(By.xpath(CardInvalidErrMsgVerify));
					errMsg = hiddenDiv.getText(); // does not work (returns "" as expected)
					String script = "return arguments[0].innerText";
					errMsg = (String) ((JavascriptExecutor) driver).executeScript(script, hiddenDiv);			
					System.out.println(errMsg);					
					Assert.waitForPageToLoad(driver);
					if(errMsg.contains("REJECTED. Please try paying using another method?")
							||errMsg.contains("We are sorry but the transaction failed. Try paying using another method?") 		                    
							||errMsg.contains("Please use a valid debit card issued in india")|| errMsg.contains("Improper Card Name Entered")
							||errMsg.contains("FAILED. Please try paying using another method?")||errMsg.contains("We are sorry but the transaction failed. Try paying using another method"))
					{
						Extent_Reporting.Log_Pass("Repective Error Message is exist", "Error Msg is:"+errMsg);
						Extent_Reporting.Log_report_img("Respective Error Message is exist", "Passed", driver);	
						break;
					}
				}else{	
					Extent_Reporting.Log_Fail("Repective Error Message does not exist", "Error Msg is:"+errMsg, driver);
				}
			 }else{
					Extent_Reporting.Log_Fail("Session timer does not exist", "Error Msg is:"+errMsg, driver);
					break;
			 }
			}
		}catch(Exception e){
			Extent_Reporting.Log_Fail("Repective Error Message does not exist", "Error Msg is:"+errMsg, driver);

		}

	}
	
	public void AmexeZeClickNavigation_page() throws Exception {
		try{ 
			Thread.sleep(10000);
			Log.info("Navigating To Net Banking Page");	
			Assert.waitForPageToLoad(driver);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			  String domain = (String) js.executeScript("return document.domain");
			  driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);			 			 
			  if(domain.equals("") || domain.equals("payments.airpay.co.in")||(driver.getPageSource().contains("Error Page Exception"))==true
					  ||(driver.getPageSource().contains("Internal Server Error"))==true|| driver.getTitle().contains("HTTP Status - 400")
					  || driver.getTitle().contains("502 Bad Gateway"))
			  {			 
				  Extent_Reporting.Log_Fail("Its not navigated to Respective Bank as", "Error Snap", driver);
				  Log.error("Its not navigated to Respective Bank as :"+bankName);
				////throw new Exception("Net Banking page issue");
			  }else{
				  Extent_Reporting.Log_Pass("Its Navigated to :"+bankName, "Passed");
				  Extent_Reporting.Log_report_img("Its Navigated to respective Page" , "Passed", driver);
				  Thread.sleep(2000);
			}
		}catch(Exception e)	
			{
				Extent_Reporting.Log_Fail(" Make payment button does not exist for net banking",	"Failed",driver);
				Log.error("Test failed due to card does not exist");
				e.printStackTrace();
				////throw new Exception("Test failed due to local host page not displayed");
			}
		} 
	
	public void VirtualmakePaymentBtnClick() throws Exception{
		try{		   		   			
			Extent_Reporting.Log_report_img("AmexeZeClick is exist as expected", "Passed", driver);
			AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
			obj.SurchargeForCommonFunctionNotclickplus();
			Assert.Clickbtn(driver, virtualAccounttBtn, "make payment");	
			Thread.sleep(2000);
			Assert.waitForPageToLoad(driver);
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("AmexeZeClick does not disp", "Failed", driver);   
			Log.error("Bharat QR option issue");
			e.printStackTrace();
			//throw new Exception("Bharat QR option");
		}
	}	
	
	public void VirtualBankMakePaymentBtnClick() throws Throwable{
		try{		   		   			
			Extent_Reporting.Log_report_img("Virtual Bank Account details", "Passed", driver);
			Assert.Javascriptexecutor_forClick(driver, virtualBankAccountBtn, "make payment");	
			Thread.sleep(2000);
			Assert.waitForPageToLoad(driver);
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Virtual Bank Account details does not disp", "Failed", driver);   
			Log.error("Virtual Bank Account details issue");
			e.printStackTrace();
			//throw new Exception("Virtual Bank Account details issue");
		}
	}	
	
	
	public void VirtualAccountDetails() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Thread.sleep(2000);
			if(Assert.isElementDisplayed(driver, AccountDetails, "Account details"))
			{
				Extent_Reporting.Log_report_img("Virtual Account is exist as expected", "Passed", driver);
				Assert.waitForPageToLoad(driver);			
			}else{
				Extent_Reporting.Log_Fail("Virtual Account details fields does not exist", "Failed", driver);
			}		
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Virtual Account details fields does not exist", "Failed", driver);
			Log.error("Bharat QR option issue");
			e.printStackTrace();
			//throw new Exception("Bharat QR option");
		}
	}
	
	public void TezField() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Thread.sleep(2000);
			if(Assert.isElementDisplayed(driver, TezIDMakepayment, "Tez Id field"))
			{
				Assert.clickButton(driver, TezIDField,  "UPI Id field");
				Assert.inputText(driver, TezIDField, Excel_Handling.Get_Data(TC_ID, "UPI_Address").trim(), "UPI Id field");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, TezDomainName, Excel_Handling.Get_Data(TC_ID, "TezDomain").trim(), "Bank Domain name");
				errMsg = driver.findElement(By.xpath(CardInvalidErrMsgVerify)).getText();
				System.out.println(errMsg);
				Extent_Reporting.Log_report_img("Tez details entered as expectedd", "Passed", driver);
				Assert.waitForPageToLoad(driver);			
			}else{
				Extent_Reporting.Log_Fail("Tez details fields does not exist", "Failed", driver);
			}		
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Tez details fields does not exist", "Failed", driver);
			Log.error("Tez details option issue");
			e.printStackTrace();
			//throw new Exception("Tez details");
		}
	}
	
	public void PaytmField() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Thread.sleep(2000);
			if(Assert.isElementDisplayed(driver, PaytmlogoIcon, "Tez Id field"))
			{
				Assert.clickButton(driver, payTmChaanelInput,  "UPI Id field");
				Assert.inputText(driver, payTmChaanelInput, Excel_Handling.Get_Data(TC_ID, "UPI_Address").trim(), "paytm UPI Id field");
				//Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, TezDomainName, Excel_Handling.Get_Data(TC_ID, "TezDomain").trim(), "Bank Domain name");
				Extent_Reporting.Log_report_img("paytm details entered as expectedd", "Passed", driver);
				Assert.waitForPageToLoad(driver);			
			}else{
				Extent_Reporting.Log_Fail("paytm details fields does not exist", "Failed", driver);
			}		
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("paytm details fields does not exist", "Failed", driver);
			Log.error("paytm details option issue");
			e.printStackTrace();
			//throw new Exception("paytm details");
		}
	}
	
	
	
	
	
	
	public void TezFieldOld() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Thread.sleep(2000);
			if(Assert.isElementDisplayed(driver, TezIDMakepayment, "Tez Id field"))
			{

				Assert.inputText(driver, TezIDField, Excel_Handling.Get_Data(TC_ID, "UPI_Address").trim(), "UPI Id field");
				Assert.selectDropBoxValuebyVisibleTextwithoutClick(driver, TezDomainName, Excel_Handling.Get_Data(TC_ID, "TezDomain").trim(), "Bank Domain name");
				errMsg = driver.findElement(By.xpath(CardInvalidErrMsgVerify)).getText();
				System.out.println(errMsg);
				Extent_Reporting.Log_report_img("Tez details entered as expectedd", "Passed", driver);
				Assert.waitForPageToLoad(driver);			
			}else{
				Extent_Reporting.Log_Fail("Tez details fields does not exist", "Failed", driver);
			}		
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Tez details fields does not exist", "Failed", driver);
			Log.error("Tez details option issue");
			e.printStackTrace();
			//throw new Exception("Tez details");
		}
	}
	
	public void TezMakepaymentBtn() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, TezIDField, "Tez Id field"))
			{
				Assert.Clickbtn(driver, TezIDMakepayment, "Tez make payment button");
				Assert.waitForPageToLoad(driver);			
			}else{
				Extent_Reporting.Log_Fail("Tez make payment button does not exist", "Failed", driver);
			}		
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("Tez make payment button does not exist", "Failed", driver);
			Log.error("Tez details option issue");
			e.printStackTrace();
			//throw new Exception("Tez details");
		}
	}
	
	public void paymentMakepaymentBtn() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			if(Assert.isElementDisplayed(driver, PaytmlogoIcon, "paytm Id field"))
			{
				Assert.Clickbtn(driver, TezIDMakepayment, "paytm make payment button");
				Assert.waitForPageToLoad(driver);			
			}else{
				Extent_Reporting.Log_Fail("paytm make payment button does not exist", "Failed", driver);
			}		
		}catch(Exception e)	
		{
			Extent_Reporting.Log_Fail("paytm make payment button does not exist", "Failed", driver);
			Log.error("paytm details option issue");
			e.printStackTrace();
			//throw new Exception("paytm details");
		}
	}
	
	public void Tez_UPIRedLineError() throws Exception{
		try{
			Assert.waitForPageToLoad(driver);
			Assert.Clickbtn(driver, TezIDMakepayment, "Tez make payment button");
			if(Assert.isElementDisplayed(driver, TezIdFieldErrRedline, "Tez Id field")){
				Assert.isElementDisplayed(driver, TezIdFieldErrRedline, "Tez Id field Red line Error is exist");
				Assert.isElementDisplayed(driver, TezDomainRedLineErr, "Domain Name");
				Extent_Reporting.Log_Pass("UPI Address and Domain Name field red line error is exist as expected", "Passed");
				Extent_Reporting.Log_report_img("Red line error screen print", "Passed", driver);

			}else{
				Extent_Reporting.Log_Fail("UPI Address Red Line Error does not exist", "Failed", driver);
				//throw new Exception("UPI Address Red Line Error does not exist");
			}
		}catch(Exception e) 
		{
			Extent_Reporting.Log_Fail("UPI Address Red Line Error does not exist", "Failed", driver);
			Log.error("UPI Address Red Line Error does not exist");
			e.printStackTrace();
			//throw new Exception("UPI Address Red Line Error does not exist");
		}
	}
	
	public void InValid_UPIAddress_ErrMsg() throws Exception{
		try{
			for(int i=1;i<=10;i++)
			{
				System.out.println(errMsg);
				if(errMsg.isEmpty()==true){	
					try{
						errMsg = driver.findElement(By.xpath(CardInvalidErrMsgVerify)).getText();
						Thread.sleep(10000);
					}catch(Exception e){						
						System.out.println("second"+errMsg);
						Thread.sleep(1000);
					}
				}else{	  
					if(errMsg.contains("REJECTED. Please try paying using another method?")
							||errMsg.contains("We are sorry but the transaction failed. Try paying using another method?") 		                    
							||errMsg.contains("Please use a valid debit card issued in india")|| errMsg.contains("Improper Card Name Entered")
							||errMsg.contains("FAILED. Please try paying using another method?")||errMsg.contains("We are sorry but the transaction failed. Try paying using another method"))
					{
						Extent_Reporting.Log_Pass("Repective Error Message is exist", "Error Msg is:"+errMsg);
						Extent_Reporting.Log_report_img("Respective Error Message is exist", "Passed", driver);	
						break;
					}
				}
			}
		}catch(Exception e){
			Extent_Reporting.Log_Fail("Repective Error Message does not exist", "Error Msg is:"+errMsg, driver);
		}

	}
	
	
	
}