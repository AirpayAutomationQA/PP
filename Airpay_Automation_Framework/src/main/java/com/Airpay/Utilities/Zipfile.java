package com.Airpay.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipfile extends Constants{
    public static void compress(String dirPath) {
        final Path sourceDir = Paths.get(dirPath);
        String zipFileName = dirPath.concat(".zip");
        System.out.println("zip file name"+zipFileName);
        try {
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String removeLastCharacter(String str,int i) {
    	   String result = null;
    	   if ((str != null) && (str.length() > 0)) {
    	      result = str.substring(0, str.length() - i);
    	   }
    	   return result;
    	}
    
    public static void main(String arg[])
    {
    	String path = "C:\\Users\\shishir.shah\\git\\AirpayAutomationProject_Updated\\Airpay_Automation_Framework\\AirPayTestData\\Result\\Graphical Reporting\\2019\\12\\19";
    	
    	System.out.println("snapshot----------"+snapshotsPath);
    	String trim_path = removeLastCharacter(snapshotsPath,1);
    	System.out.println("Trim string"+trim_path);
    	compress(path);
    }
}