package com.Airpay.PageObject;

public class Airpay_CAPanel_PageObject {

	
	//*************************CA Panel Login *******************************************************
	
			public static final  String  CAloginID = "//input[@name='loginid']";
			public static final  String  CAPwd = "//input[@name='password']";			
			public static final  String  CASubmitBtn = "//input[@id='submitform']";					
			public static final String LogoPaymentPage = "//*[@src='resources/images/airpay-payment-processing-services-logo.png' or @class='mer-logo-txt']";
			public static final String ImgLogo = "(//img)[1]";			
			public static final  String  LogoutIcon = "//*[@class='dropdown-toggle']";	
			public static final  String  CALogOutBtn = "//*[@class='consumerbut']";
			
			public static final String AAdharField ="//*[@id='aadhar']";
			public static final String PANField ="//*[@id='pan']";
			public static final String ProfileSubmtBtn ="//*[@id='profile-submit-button']";
			public static final String ProfileCAErrorMsg ="(//*[@id='aadhar']//following::span[@class='help-block with-errors'])[1]";
			public static final String AadharCadInvalidErMsg ="//*[text()='Aadhar number is invalid']";
			public static final String PanCadInvalidErMsg ="//*[text()='PAN number is invalid']";

			
			
			
			
			
			
			
			
			
			public static final  String  HeaderTabsVerify = "//ul[@class='r-tabs-nav']/li";
			
			public static final  String  payHerebtn= "//button[@type='submit']";
	
	
}
