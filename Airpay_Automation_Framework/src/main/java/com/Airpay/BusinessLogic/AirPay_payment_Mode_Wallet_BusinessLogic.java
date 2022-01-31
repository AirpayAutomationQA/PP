package com.Airpay.BusinessLogic;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
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

public class AirPay_payment_Mode_Wallet_BusinessLogic extends Airpay_PaymentPage_PageObject {
	public WebDriver driver;
	public String TC_ID = "";
	ElementAction Assert = new ElementAction();
	Log log = new Log();	
	public AirPay_payment_Mode_Wallet_BusinessLogic(WebDriver driver, String TC_ID)
	{
		Log.info("AirPay_payment_Mode_Wallet_BusinessLogic");
		this.driver = driver;
		this.TC_ID=TC_ID;
		log = new Log();
		
	}
	
	/**
	 * @author parmeshwar Sakole
	 * @Method Name: Card Selection method.
	 * Following method is used Handling Multiple Card options
	 * @throws Exception
	 */
	public void Card_Details_Options() throws Exception {
		try{ 
			Assert.waitForPageToLoad(driver);
			Thread.sleep(2000);
			Log.info("Navigating To Payment Page");	 
			String Card = Excel_Handling.Get_Data(TC_ID, "Payment_Mode").trim();
			Assert.waitForPageToLoad(driver);		
			List<WebElement> Channels = driver.findElements(By.xpath(AirpayChannals));
			int ChannelsCnt = Channels.size();
			System.out.println("Channels count is:"+ChannelsCnt);
			for(int i=0; i<ChannelsCnt;i++)
			{
				WebElement ChannelsName = Channels.get(i);
				String name = ChannelsName.getText();				
				if(name.equalsIgnoreCase(Card)){					
						ChannelsName.click();
						Extent_Reporting.Log_report_img(" payment mode option choosen as: "+name, "Passed", driver);
						break;					
				}								
			}
			Extent_Reporting.Log_report_img("All channels are exist as expected", "Passed", driver);  
		}                     
		catch(Exception e)	
		{
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
	/**
	 * @author parmeshwar Sakole
	 * Following method is used for Wallet Red line error.
	 * @throws Throwable
	 */
	public void WalletDropDown_ErrorVerify() throws Exception {
		try{ 
			Log.info("Navigating To Payment Page");	   
			if(Assert.isElementDisplay(driver, WalletDropDown))
			{ 
				Assert.Clickbtn(driver, WalletMakePaymtBtn, "Wallet Make payement button");
				if(Assert.isElementDisplayed(driver, WalletErrDropDown, "Wallet Drop down Error Red line"))
				{
					Extent_Reporting.Log_Pass("Wallet Drop Down red line error is exist", "Passed");
					Extent_Reporting.Log_report_img("Wallet Error Red Line exist", "Passed", driver);
				}else{
					Extent_Reporting.Log_Fail("Wallet Error Red line does not exist", "Failed", driver);
				}
			}
			else{
				Extent_Reporting.Log_Fail("Wallet page not exist ", "Wallet page not displayed", driver);   
				Log.error("Wallet page not exist ");
				//throw new Exception(" Wallet page not displayed");
			}
		}                     
		catch(Exception e)	
		{
			Log.error("Test failed due to local host page not displayed");
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
	public static String bankName = null;
	public void WalletDropDown_Select() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, "(//*[@class='form-control wallet-options']/option)", "SelectBank Drop Down" ))
			{         	
				List<WebElement> selectDropBox = driver.findElements(By.xpath("(//*[@class='form-control wallet-options']/option)"));
				System.out.println(selectDropBox);		
				for(int i =1;i<selectDropBox.size();i++)
				{	
					Assert.Clickbtn(driver, "(//*[@class='form-control wallet-options']/option)", "Wallet Drop down select");	
					Assert.Clickbtn(driver, "(//*[@class='form-control wallet-options']/option)["+(i+1)+"]", "DropDown selected");
					WebElement selectDropBox1 = driver.findElement(By.xpath("//*[@class='form-control wallet-options']/option[@checked='checked']"));					
					bankName =  selectDropBox1.getText();
					System.out.println(bankName);
					Extent_Reporting.Log_report_img(bankName+"Bank Selected", "Passed", driver);
					AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
					obj.SurchargeForCommonFunctionNotclickplus();
					if(bankName.contains("PAYTM"))
					{
						Assert.Clickbtn(driver, WalletPaytmMakePaymtBtn, "Wallet Make payement button");
						
					}else
					{
					Assert.Clickbtn(driver, WalletMakePaymtBtn, "Wallet Make payement button");
					}
					Assert.waitForPageToLoad(driver);
					BankPage_walletValidation();
					NavigateToLocalHostPage();						
				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}

	public void WalletDropDown_Select_Inline_kit() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, "(//*[@class='form-control wallet-options']/option)", "SelectBank Drop Down" ))
			{         	
				List<WebElement> selectDropBox = driver.findElements(By.xpath("(//*[@class='form-control wallet-options']/option)"));
				System.out.println(selectDropBox);		
				for(int i =1;i<selectDropBox.size();i++)
				{	
					Assert.Clickbtn(driver, "(//*[@class='form-control wallet-options']/option)", "Wallet Drop down select");	
					Assert.Clickbtn(driver, "(//*[@class='form-control wallet-options']/option)["+(i+1)+"]", "DropDown selected");
					WebElement selectDropBox1 = driver.findElement(By.xpath("//*[@class='form-control wallet-options']/option[@checked='checked']"));					
					bankName =  selectDropBox1.getText();
					System.out.println(bankName);
					Extent_Reporting.Log_report_img(bankName+"Bank Selected", "Passed", driver);
					Assert.Clickbtn(driver, WalletMakePaymtBtn, "Wallet Make payement button");
					Assert.waitForPageToLoad(driver);
					BankPage_validation();
					NavigateToLocalHostPageInline_kit();						
				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
	
	
	public void WalletDropDown_Selecting_Email_and_MobileVerifying() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, "(//*[@class='form-control wallet-options']/option)", "SelectBank Drop Down" ))
			{         	
				List<WebElement> selectDropBox = driver.findElements(By.xpath("(//*[@class='form-control wallet-options']/option)"));
				System.out.println(selectDropBox);		
				for(int i =1;i<selectDropBox.size();i++)
				{	
					Assert.Clickbtn(driver, "(//*[@class='form-control wallet-options']/option)", "Wallet Drop down select");	
					Assert.Clickbtn(driver, "(//*[@class='form-control wallet-options']/option)["+(i+1)+"]", "DropDown selected");
					WebElement selectDropBox1 = driver.findElement(By.xpath("//*[@class='form-control wallet-options']/option[@checked='checked']"));					
					bankName =  selectDropBox1.getText();
					System.out.println(bankName);
					Extent_Reporting.Log_report_img(bankName+"Bank Selected", "Passed", driver);
					Assert.Clickbtn(driver, WalletMakePaymtBtn, "Wallet Make payement button");
					Assert.waitForPageToLoad(driver);
					WalletEmail_MobileNumber_verifying();
					NavigateToLocalHostPage();						
				}   		
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
   public static String OtpPass =null;
   public static String OtpPass1 =null;
   public void BankPage_walletValidation() {

		try{ 
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			  String domain = (String) js.executeScript("return document.domain");
			  driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);	
			  Thread.sleep(2000);
			  if(domain.equals("") || domain.equals("payments.airpay.co.in")||driver.getPageSource().contains("Error Page Exception")==true
					  ||driver.getPageSource().contains("Internal Server Error")==true|| driver.getTitle().contains("HTTP Status - 400")
					  || domain.equals("staging-payments.airpay.co.in"))
			  {			  System.out.println("Domain url value"+domain);
				  if(Assert.isElementDisplay(driver, "(//div[@class='popup-body']//following::div[contains(@class,'generic-success')])"))
				  {
					   OtpPass1 =driver.findElement(By.xpath("(//div[@class='popup-body']//following::div[contains(@class,'generic-success')])")).getText().trim();
					  System.out.println("OTP msg value"+OtpPass1);
				  }else{
					  OtpPass1 = "hh";
					  System.out.println("OTP msg value"+OtpPass1);
				  }				  
				  if(OtpPass1.contains("OTP sent to your registered Mobile Number."))
				  {
					  Extent_Reporting.Log_Pass("Its Navigated to OTP:"+bankName, "Passed");					  
					  Extent_Reporting.Log_report_img("Its Navigated to respective bank OTP" , "Passed", driver);
					  Thread.sleep(2000);
				  }else{
					 // Extent_Reporting.Log_Fail("Its not navigated to Respective Bank as", "Error Snap", driver);
					  Extent_Reporting.Log_report_img("Its Navigated to respective bank" , "Passed", driver);

					  Log.error("Its not navigated to Respective Bank as :"+bankName);
				  }
				  
				
			  }else{
				  Extent_Reporting.Log_Pass("Its Navigated to :"+bankName, "Passed");
				  Extent_Reporting.Log_report_img("Its Navigated to respectivebank" , "Passed", driver);
				  Thread.sleep(2000);
			}
		}catch(Exception e)	
			{
				Log.error("Test failed due to card does not exist");
				e.printStackTrace();
				////throw new Exception("Test failed due to local host page not displayed");
			}
		
   }
	public void BankPage_validation() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	
			JavascriptExecutor js = (JavascriptExecutor) driver;
			  String domain = (String) js.executeScript("return document.domain");
			  driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);	
			  Thread.sleep(2000);
			  if(domain.equals("") || domain.equals("payments.airpay.co.in")||driver.getPageSource().contains("Error Page Exception")==true
					  ||driver.getPageSource().contains("Internal Server Error")==true|| driver.getTitle().contains("HTTP Status - 400")
					  || domain.equals("staging-payments.airpay.co.in"))
			  {			  
				  if(Assert.isElementDisplay(driver, "(//div[@class='generic-success'])[1]")){
					   OtpPass =driver.findElement(By.xpath("(//div[@class='generic-success'])[1]")).getText().trim();
					  System.out.println(OtpPass);
				  }else{
					  OtpPass = "hh";
				  }				  
				  if(OtpPass.contains("OTP sent to your registered Mobile Number."))
				  {
					  Extent_Reporting.Log_Pass("Its Navigated to OTP:"+bankName, "Passed");					  
					  Extent_Reporting.Log_report_img("Its Navigated to respective bank OTP" , "Passed", driver);
					  Thread.sleep(2000);
				  }else{
					 // Extent_Reporting.Log_Fail("Its not navigated to Respective Bank as", "Error Snap", driver);
					  Extent_Reporting.Log_report_img("Its Navigated to respective bank" , "Passed", driver);

					  Log.error("Its not navigated to Respective Bank as :"+bankName);
				  }
				  String errMsg = driver.findElement(By.xpath(CardInvalidErrMsgVerify)).getText();
					if(errMsg.contains("REJECTED. Please try paying using another method?")
							||errMsg.contains("We are sorry but the transaction failed. Try paying using another method?") 		                    
							||errMsg.contains("Please use a valid debit card issued in india")|| errMsg.contains("Improper Card Name Entered")
							||errMsg.contains("FAILED. Please try paying using another method?")||errMsg.contains("We are sorry but the transaction failed. Try paying using another method")
							||errMsg.contains("Payment Failed. Please try paying using another method?"))
					{
						Extent_Reporting.Log_Fail("Repective Error Message does not exist", "Error Msg is:"+errMsg, driver);
					}
				////throw new Exception("Net Banking page issue");
			  }else{
				  Extent_Reporting.Log_Pass("Its Navigated to :"+bankName, "Passed");
				  Extent_Reporting.Log_report_img("Its Navigated to respectivebank" , "Passed", driver);
				  Thread.sleep(2000);
			}
		}catch(Exception e)	
			{
				Log.error("Test failed due to card does not exist");
				e.printStackTrace();
				////throw new Exception("Test failed due to local host page not displayed");
			}
		} 
	
	public void WalletEmail_MobileNumber_verifying() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	
			JavascriptExecutor js = (JavascriptExecutor) driver;
			  String domain = (String) js.executeScript("return document.domain");
			  driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);	
			  Thread.sleep(2000);
			  if(domain.equals("") || domain.equals("payments.airpay.co.in")||driver.getPageSource().contains("Error Page Exception")==true
					  ||driver.getPageSource().contains("Internal Server Error")==true|| driver.getTitle().contains("HTTP Status - 400"))
			  {			  
				  if(Assert.isElementDisplay(driver, "(//div[@class='generic-success'])[1]")){
					   OtpPass =driver.findElement(By.xpath("(//div[@class='generic-success'])[1]")).getText().trim();
					  System.out.println(OtpPass);
				  }else{
					  OtpPass = "hh";
				  }				  
				  if(OtpPass.contains("OTP sent to your registered email id and to Mobile Number"))
				  {
					  Extent_Reporting.Log_Pass("Its Navigated to OTP:"+bankName, "Passed");					  
					  Extent_Reporting.Log_report_img("Its Navigated to respective bank OTP" , "Passed", driver);
					  Thread.sleep(2000);
					  if(Assert.isElementDisplayed(driver, WalletMobileNumField, "Mobile Number field")){
						 Extent_Reporting.Log_report_img("Mobile number field is exist as expected", "passed", driver);
						 Assert.isElementDisplayed(driver, WalletEmailIDField, "Email Id field");
					 }else{
						 Extent_Reporting.Log_Fail("Mobile number field does not exist", "Failed", driver);
					 }				  
				  }else{
					 // Extent_Reporting.Log_Fail("Its not navigated to Respective Bank as", "Error Snap", driver);
					  Extent_Reporting.Log_report_img("Its Navigated to respective bank" , "Passed", driver);

					  Log.error("Its not navigated to Respective Bank as :"+bankName);
				  }
				////throw new Exception("Net Banking page issue");
			  }else{
				  Extent_Reporting.Log_Pass("Its Navigated to :"+bankName, "Passed");
				  Extent_Reporting.Log_report_img("Its Navigated to respectivebank" , "Passed", driver);
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
	
	
	/**
	 * @author parmeshwar Sakole
	 * Following method is used for Filling up the local host details page.
	 * @throws Throwable
	 */
	public void LocalHostDetailPage() throws Exception {
		try{ 
			Log.info("Navigating To Local Host page of Payment");	   
			if(Assert.isElementDisplay(driver, BuyerMailId))
			{ 
				Log.debug("Local Host page");
				Assert.inputText(driver, BuyerMailId, Excel_Handling.Get_Data(TC_ID, "BuyerMailID"), "Buyer Mail ID");
				Assert.inputText(driver, BuyerPhoneNumber, Excel_Handling.Get_Data(TC_ID, "BuyerPhoneNumber"), "Buyer Phone Number");
				Assert.inputText(driver, BuyerFirstName, Excel_Handling.Get_Data(TC_ID, "BuyerFirstName"), "Buyer First Name");
				Assert.inputText(driver, BuyerLastName, Excel_Handling.Get_Data(TC_ID, "BuyerLastName"), "Buyer Last Name");
				
				String  string = RandomStringUtils.randomAlphabetic(8);		
				System.out.println("Random 1 = " + string);				
				Assert.inputText(driver, Order_Id, string, "Order_Id");
				//GetOrderID = driver.findElement(By.xpath(Order_Id)).getAttribute("value");				
				//Assert.inputText(driver, Order_Id, Excel_Handling.Get_Data(TC_ID, "Order_Id"), "Order_Id")				
				Assert.inputText(driver, Amount, Excel_Handling.Get_Data(TC_ID, "Amount"), "Amount");
				if(!(Excel_Handling.Get_Data(TC_ID, "ChMode")==null))
				{
					Assert.inputText(driver, ChMode, Excel_Handling.Get_Data(TC_ID, "ChMode"), "ChMode");
				}
				Extent_Reporting.Log_report_img("Local Host page required field filled", "Passed", driver);
				Assert.Clickbtn(driver, payHerebtn, "Pay Here");
				Assert.waitForPageToLoad(driver);
			}
			else{
				Extent_Reporting.Log_Fail("Local Host page not exist ", "Local Host page not displayed", driver);   
				Log.error("Local Host page not successfully displayed");
				//throw new Exception(" Test failed due to local host page not displayed");
			}
		}                     
		catch(Exception e)	
		{
			Log.error("Test failed due to local host page not displayed");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}

	public void NavigateToLocalHostPage() throws Exception {
		try{ 
				Log.info("Navigating To Net Banking Page");	
				Assert.waitForPageToLoad(driver);
				driver.get(Excel_Handling.Get_Data(TC_ID, "PaymentPage_URL").trim());
				Assert.waitForPageToLoad(driver);
				LocalHostDetailPage();	
				Card_Details_Options();
		}catch(Exception e){
			Extent_Reporting.Log_Fail(" Make payment button does not exist for net banking",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}
	
	public void NavigateToLocalHostPageInline_kit() throws Exception {
		try{ 
				Log.info("Navigating To Net Banking Page");	
				Assert.waitForPageToLoad(driver);
				driver.get(Excel_Handling.Get_Data(TC_ID, "PaymentPage_URL").trim());
				Assert.waitForPageToLoad(driver);
				AirPay_PaymentPage_BusinessLogic AirPay_Local = new AirPay_PaymentPage_BusinessLogic(driver, TC_ID);
				AirPay_Local.NavigateToInlineKit();	
				Card_Details_Options();
		}catch(Exception e){
			Extent_Reporting.Log_Fail(" Make payment button does not exist for net banking",	"Failed",driver);
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
	public void Popular_Wallet_bank_verification() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, PopularWalletLogo, "Select Bank Drop Down" ))
			{         	
				List<WebElement> popular1 = driver.findElements(By.xpath(PopularWalletLogo));			
				System.out.println(popular1.size());
				for(int i =0;i<popular1.size();i++)
				{
					String popularbackName = driver.findElement(By.xpath(PopularWalletLogo+"["+(i+1)+"]")).getText();
					driver.findElement(By.xpath(PopularWalletLogo+"["+(i+1)+"]")).click();
					WebElement selectDropBox1 = driver.findElement(By.xpath(WalletDropDownSection));
					Select select1 =new Select(selectDropBox1);
					bankName =  select1.getFirstSelectedOption().getText();					
					if(popularbackName.contains(bankName)){					
						Extent_Reporting.Log_Pass("Popular Bank Logo is exist as "+bankName, "Drop Down Name is exist as "+popularbackName);												
						Extent_Reporting.Log_report_img("Bank Name Drop Down and Popular Logo name is exist", "Passed", driver);
						AirPay_Payment_Mode_Debit_Card_BusinessLogic obj = new AirPay_Payment_Mode_Debit_Card_BusinessLogic(driver, TC_ID); 
						obj.SurchargeForCommonFunctionNotclickplus();
					}else{
						Extent_Reporting.Log_Fail("Popular bank name does not exist", "Failed", driver);					
					}
				}   		
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
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
	public void Popular_Wallet_verification_previous_selectedBank() throws Exception {
		try{ 
			Log.info("Navigating To Net Banking Page");	 
			if(Assert.isElementDisplayed(driver, PopularWalletLogo, "Select Bank DropDown" ))
			{         	
				List<WebElement> popular1 = driver.findElements(By.xpath(PopularWalletLogo));			
				for(int i =0;i<popular1.size();i++)
				{
					String popularbackName = driver.findElement(By.xpath(PopularWalletLogo+"["+(i+1)+"]")).getText();
					driver.findElement(By.xpath(PopularWalletLogo+"["+(i+1)+"]")).click();
					WebElement selectDropBox1 = driver.findElement(By.xpath(WalletDropDownSection));
					Select select1 =new Select(selectDropBox1);
					bankName =  select1.getFirstSelectedOption().getText();					
					if(popularbackName.contains(bankName)){					
				
						Extent_Reporting.Log_Pass("Popular Bank Logo is exist as: "+bankName, "Drop Down Name is exist as: "+popularbackName);												
						Extent_Reporting.Log_report_img("Bank Name Drop Down and Popular Logo name is exist", "Passed", driver);
						Assert.selectDropBoxValue(driver, WalletDropDownSection, i+1, " Bank Name");
						Extent_Reporting.Log_Pass("NetBanking Drop Down selected", "Passed");												
						Extent_Reporting.Log_report_img("Bank Name Drop Down and Popular Logo name is exist", "Passed", driver);

					}else{
						Extent_Reporting.Log_Fail("Popular bank name does not exist", "Failed", driver);					
					}
				}   		
			}
			else{
				Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
				Log.error("Local Host page not successfully displayed");
				//throw new Exception("option does not exist displayed");
			}
		}                     
		catch(Exception e)	
		{
			Extent_Reporting.Log_Fail(" option does not exis",	"Failed",driver);
			Log.error("Test failed due to card does not exist");
			e.printStackTrace();
			//throw new Exception("Test failed due to local host page not displayed");
		}
	}


}

	