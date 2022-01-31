 package com.Airpay.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestNG;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.Airpay.Reporting.Report_Setup;
import com.Airpay.TestData.Excel_Handling;


public class Create_TestNGXML extends Constants {	
	MailProjectClass mail =new MailProjectClass();
	Zipfile zip =new Zipfile();
	private static XSSFWorkbook workbook;
	private static FileInputStream fis = null;
	public  static int  Sheetcount ;
	public static String SheetnameTest ="";
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";
	public static String Url;
	public File f;
	public List<XmlInclude> constructIncludes (String... methodNames) {
        List<XmlInclude> includes = new ArrayList<XmlInclude> ();
        for (String eachMethod : methodNames) {
            includes.add (new XmlInclude (eachMethod));
        }
        return includes;
    }
	
	@SuppressWarnings("deprecation")
	@Test     
    public void createXMLfile () throws Exception {
		
		Runtime.getRuntime().exec(Constants.deleteAllTempFileBatchlocation);		
		killProcessRunning("IEDriverServer.exe");
		killProcessRunning("iexplore.exe *32");
		killProcessRunning("iexplore.exe");
		killProcessRunning("ALM-Client.exe");
		//killProcessRunning("chromedriver.exe");	
		//killProcessRunning("chrome.exe");
		killProcessRunning("scalc.exe");	
    	//calling out the excel datasheet instance to get all the "Y" data for setting up the testngxml
		// Excel sheet 1 st one.........................................	 		
		fis = new FileInputStream(new File(Constants.datasheetPath+"Datasheet.xlsx"));
		workbook = new XSSFWorkbook(fis);	
		Sheetcount= workbook.getNumberOfSheets();
		Excel_Handling excel = new Excel_Handling();	
		
		for(int k=0;k<Sheetcount;k++)
		{					
			SheetnameTest = workbook.getSheetName(k);
			System.out.println(SheetnameTest);		
			excel.ExcelReader(Constants.datasheetPath+"Datasheet.xlsx", SheetnameTest, Constants.datasheetPath+"Datasheet_Result.xlsx", SheetnameTest);
			try {
				excel.getExcelDataAll(SheetnameTest, "Execute", "Y", "TC_ID");	
				if(excel.TestData.isEmpty())
				{
					continue;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		        @SuppressWarnings({ "rawtypes", "static-access" })
		Map<String, HashMap> map = excel.TestData;              
        for (String key: map.keySet()){
        	//creation of the testng xml based on parameters/data
        	TestNG testNG = new TestNG();
        	XmlSuite suite = new XmlSuite ();
            suite.setName (new Common_Functions_old().GetXMLTagValue(Constants.configPath+"Config.xml", "Regression_Suite_Name"));
	        if(Integer.parseInt(Excel_Handling.Get_Data(key, "Browser_Instance"))>1){
	        	suite.setParallel("tests");
        	 	suite.setThreadCount(Integer.parseInt(Excel_Handling.Get_Data(key, "Browser_Instance")));
	        	for(int i=1;i<=Integer.parseInt(Excel_Handling.Get_Data(key, "Browser_Instance"));i++){
	        		XmlTest test = new XmlTest (suite);        		
	        		test.setName (key+"_Instance_"+i);
	    	        test.setPreserveOrder(false);
	    	        test.addParameter("browserType", Excel_Handling.Get_Data(key, "Browser_Type"));
	    	        test.addParameter("tcID", key);
	    	        Url = Excel_Handling.Get_Data(key, "PaymentPage_URL");
	    	        if(Url=="")
	    	        {
	    	        	test.addParameter("appURL", new Common_Functions_old().GetXMLTagValue(Constants.configPath+"Config.xml", "AppUrl")); 	        
	    	        }else
	    	        {
	    	        	test.addParameter("appURL", Excel_Handling.Get_Data(key, "PaymentPage_URL"));
	    	        }
	    	       // test.addParameter("appURL", new Common_Functions_old().GetXMLTagValue(Constants.configPath+"Config.xml", "AppUrl")); 	        
	        		test.addParameter("temp", "temp"+i);	        		
	        		XmlClass testClass = new XmlClass ();
	        		testClass.setName ("com.Airpay.Tests."+Excel_Handling.Get_Data(key, "Class_Name"));	        	
	    	        test.setXmlClasses (Arrays.asList (new XmlClass[] { testClass}));
	        	}        		
        	}else{
        		XmlTest test = new XmlTest (suite);
            	test.setName (key);            	
    	        test.setPreserveOrder (true);
    	        test.addParameter("browserType", Excel_Handling.Get_Data(key, "Browser_Type"));
    	        test.addParameter("tcID", key);
    	        //test.addParameter("appURL", new Common_Functions_old().GetXMLTagValue(Constants.configPath+"Config.xml", "AppUrl")); 	        
    	        Url = Excel_Handling.Get_Data(key, "PaymentPage_URL");
    	        if(Url=="")
    	        {
    	        	test.addParameter("appURL", new Common_Functions_old().GetXMLTagValue(Constants.configPath+"Config.xml", "AppUrl")); 	        
    	        }else
    	        {
    	        	test.addParameter("appURL", Excel_Handling.Get_Data(key, "PaymentPage_URL"));
    	        }
    	        XmlClass testClass = new XmlClass ();
        		testClass.setName ("com.Airpay.Tests."+Excel_Handling.Get_Data(key, "Class_Name"));
    	        test.setXmlClasses (Arrays.asList (new XmlClass[] { testClass}));
        		
        	}
	        List<String> suites = new ArrayList<String>();
	        final File f1 = new File(Create_TestNGXML.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        f = new File(f1+"\\testNG.xml");
	        f.createNewFile();
	        FileWriter fw = new FileWriter(f.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(suite.toXml());
	        bw.close();
	        
	        suites.add(f.getPath());
	        
	        testNG.setTestSuites(suites);
	        com.Airpay.Reporting.Report_Setup.InitializeReport(key);
	        testNG.run();
        	}
	        Report_Setup.extent.endTest(Report_Setup.test);
        
	        f.delete();
        } 
		
        Report_Setup.extent.flush();    
	
    }
	/*
	@AfterSuite
	public void sendreport()
	{	String trim_path = zip.removeLastCharacter(snapshotsPath,1);
	String zip_path = zip.removeLastCharacter(snapshotsPath,3);
	System.out.println("trimpath-------------"+trim_path);
	System.out.println("zippath-------------"+zip_path);
	//zip.compress(trim_path);
		//zip.compress(snapshotsPath);
		//C:/Users/shishir.shah/git/AirpayAutomationProject_Updated/Airpay_Automation_Framework/AirPayTestData/Result/Graphical Reporting/HTML
		File f = new File(trim_path);
		File[] files = f.listFiles();
		Arrays.sort(files, new Comparator()
		{
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.lastModified()).compareTo(
						f2.lastModified());
		}

			@Override
			public int compare(Object o1, Object o2) 
			{
				// TODO Auto-generated method stub
				return 0;
			}
		});
		System.out.println("Files of the directory: ");
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
		System.out.println();
		System.out.println("Latest File is: "
				+ files[files.length - 1].getName());
		System.out.println();
		String file = zip_path+files[files.length - 1].getName();
		//String filepath = snapshotsPath+".zip";
		//files[files.length - 1].getName()
		mail.sendmail(trim_path,files[files.length - 1].getName());
	}
	*/
	public boolean killProcessRunning(String serviceName) throws Exception {
		boolean flag = false;
		try
		{
			
			Process p = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(serviceName)) {
					Runtime.getRuntime().exec(KILL+serviceName);
					flag= true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}



}
