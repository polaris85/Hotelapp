package com.liang.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
 
public class Decompress { 
	private String _location; 
 
	public Decompress(String zipFile, String location) { 
		_location = location;
		_dirChecker(""); 
	} 
 
	public void doUnzip(String inputZipFile, String destinationDirectory)
	        throws IOException {
	    int BUFFER = 2048;
	    
	    ArrayList<String>  zipFiles = new ArrayList<String>();
	    File sourceZipFile = new File(inputZipFile);
	    File unzipDestinationDirectory = new File(destinationDirectory);
	    unzipDestinationDirectory.mkdir();

	    ZipFile zipFile;
	    zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
	    Enumeration zipFileEntries = zipFile.entries();

	    while (zipFileEntries.hasMoreElements()) {
	 
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	        String currentEntry = entry.getName();

	        File destFile = new File(unzipDestinationDirectory, currentEntry);
	        if (currentEntry.endsWith(".zip")) {
	            zipFiles.add(destFile.getAbsolutePath());
	        }

	        File destinationParent = destFile.getParentFile();
	        destinationParent.mkdirs();

	        try {
	            if (!entry.isDirectory()) {
	                BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
	                
	                int currentByte;
	                byte data[] = new byte[BUFFER];
	                FileOutputStream fos = new FileOutputStream(destFile);
	                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);	                
	                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                    dest.write(data, 0, currentByte);
	                }
	                dest.flush();
	                dest.close();
	                is.close();
	            }
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	    }
	    zipFile.close();

	    for (Iterator iter = zipFiles.iterator(); iter.hasNext();) {
	        String zipName = (String)iter.next();
	        doUnzip( zipName, destinationDirectory + File.separatorChar + zipName.substring(0,zipName.lastIndexOf(".zip"))
	        );
	    }
	}	 
 
	private void _dirChecker(String dir) { 
		File f = new File(_location + dir); 
 
		if(!f.isDirectory()) { 
			f.mkdirs(); 
		} 
	} 
}
