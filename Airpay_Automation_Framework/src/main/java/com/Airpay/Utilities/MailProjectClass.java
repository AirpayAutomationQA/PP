package com.Airpay.Utilities;
import java.io.File;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailProjectClass {

public static void sendmail(String file,String fileName,String EMail_Subject)
{
	
	

    final String username = "qaairpay@gmail.com";
    final String password = "Airpay@1234";
    String to = "swapnil.pawar@airpay.co.in,probal.datta@airpay.co.in,akshay.doshi@airpay.co.in";
    // Sender's email ID needs to be mentioned
    String from = "qaairpay@gmail.com";
    Properties props = new Properties();
   props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    //smtp.office365.com
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(EMail_Subject);
        message.setText("PFA");

        
        
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        messageBodyPart = new MimeBodyPart();
     //    file = "C:/Users/swapnil.pawar/eclipse-workspace/Test/data/App.java";
      //   fileName = "App.java";
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        System.out.println("Sending");

        Transport.send(message);

        System.out.println("Done");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }

public static void sendmailmulattach(String filepath,int no_of_attachment,String EMail_Subject,String CC)
{
	
	

    final String username = "qaairpay@gmail.com";
    final String password = "@irp@Yment";
    String to = "qa@airpay.co.in";
    //String CC ="matt@airpay.co.in,kavitha.vinod@airpay.co.in,chaitanya@airpay.co.in";
    //matt@airpay.co.in,kavitha.vinod@airpay.co.in,chaitanya@airpay.co.in
    // Sender's email ID needs to be mentioned
    String from = "qaairpay@gmail.com";
    Properties props = new Properties();
   props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    //smtp.office365.com
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
        message.setRecipients((Message.RecipientType.CC),InternetAddress.parse(CC));
        message.setSubject(EMail_Subject);
        message.setText("PFA");
        Multipart multipart = new MimeMultipart();
        
        File f = new File(filepath);
		File[] files = f.listFiles();
		Arrays.sort(files, (f1, f2) -> {
	         return new Date(f1.lastModified()).compareTo(new Date(f2.lastModified()));
	      });
		int k=1;
		System.out.println("Files of the directory: ");
		for (int i = 0; i < no_of_attachment; i++) {
			System.out.println("File length"+files.length);
			System.out.println(files[i].getName());
			
			System.out.println("file path"+files[i].getPath());
			
			 MimeBodyPart messageBodyPart = new MimeBodyPart();
		        messageBodyPart = new MimeBodyPart();
		        DataSource source = new FileDataSource(files[files.length - k].getPath());
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(files[files.length - k].getName());
		        multipart.addBodyPart(messageBodyPart);
		        System.out.println("k value---"+k);
		        k++;
		}
		System.out.println();
		System.out.println("Latest File is: "
				+ files[files.length - 1].getName());
		System.out.println("Latest File is: "
				+ files[files.length - 2].getName());
		System.out.println();
        
        
        
       

        message.setContent(multipart);

        System.out.println("Sending");

        Transport.send(message);

        System.out.println("Done");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}