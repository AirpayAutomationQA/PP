package com.Airpay.Utilities;
import java.io.*;
import java.io.FileReader;
import java.util.*;
import java.util.Comparator;
//Code for finding latest file in directory

	public class latestfirst extends MailProjectClass {
		public static void main(String[] args) throws Exception {
			//give path till folder
			
			sendmailmulattach("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report",2,"Testmail","swapnil.pawar@airpay.co.in");
			
		/*
		 File f = new File("C://Users//swapnil.pawar//Desktop//Error_report//MA_Report");
		 
		File[] files = f.listFiles();
		 Arrays.sort(files, (f1, f2) -> {
	         return new Date(f1.lastModified()).compareTo(new Date(f2.lastModified()));
	      });
		System.out.println("Files of the directory: ");
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
			
			System.out.println("file path"+files[i].getPath());
		}
		System.out.println();
		System.out.println("Latest File is: "
				+ files[files.length-1].getName());
		
		System.out.println();
		
		/*
		File file = new File(files[files.length - 1].getPath());
		String filename = file.getPath();
		System.out.println("file path"+filename);
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		System.out.println("File Data");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	*/	
	}}