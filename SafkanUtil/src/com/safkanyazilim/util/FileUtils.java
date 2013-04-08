package com.safkanyazilim.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {
	
	/**
	 * <p>
	 * Static utility method. Given a collection of strings, 
	 * it will iterate over the collection, and write all the objects into
	 * the file whose pathname is given. 
	 * </p>
	 * <p>
	 * First, it writes the number of strings in the collection 
	 * as an int, followed by each string 
	 * written using writeUTF(). The file is meant to be read back using 
	 * readStringsFromFile() method in this class.
	 * </p>
	 * 
	 * 
	 * @param strings the collection containing the strings.
	 * @param pathname the pathname to the file to be written.
	 * @throws IOException if some error condition occurs during the write
	 */
	public static void writeStringsToFile(Collection<String> strings, String pathname) throws IOException {
		DataOutputStream stream = null;
		
		try {
			stream = new DataOutputStream(new FileOutputStream(pathname));
		
			stream.writeInt(strings.size());
		
			for (String string : strings) {
				stream.writeUTF(string);
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * <p>
	 * Static utility method. Given a pathname, it will read a collection of strings
	 * previously written there using the writeStringsToFile() method of this class,
	 * and return them as a collection of strings.
	 * </p>
	 * <p>
	 * As an implementation detail, the collection returned is in fact an ArrayList.
	 * </p>
	 * 
	 * @param pathname The pathname of the file to be read.
	 * @return the collection of strings
	 * @throws IOException if anything goes bad during the read
	 */
	public static Collection<String> readStringsFromFile(String pathname) throws IOException {
		DataInputStream stream = null;
		
		try {
			stream = new DataInputStream(new FileInputStream(pathname));
		
			int size = stream.readInt();
		
			Collection<String> strings = new ArrayList<String>(size);
		
			for (int i = 0; i < size; i++) {
				strings.add(stream.readUTF());
			}
			
			return strings;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
	
	
	/**
	 * <p>
	 * Static utility method. Given a folder path, it will read the filenames under the folder and its subfolders recursively,
	 * and return them as a collection of <code>File</code>s.
	 * </p>
	 * <p>
	 * As an implementation detail, the collection returned is in fact an LinkedList.
	 * </p>
	 * 
	 * @param folderString The path of the folder to be read.
	 * @return the collection of <code>File</code>s
	 */
	public static List<File> getListOfFilesUnderFolder(String folderString) {
	    
        List<File> listOfFiles = new LinkedList<File>();     
        
        File directory = new File(folderString);
        
        if (!directory.isDirectory()) {
        	return Collections.emptyList();         
        }
        
        LinkedList<File> pathsToBeProcessedQueue = new LinkedList<File>();
        pathsToBeProcessedQueue.addAll(Arrays.asList(directory.listFiles()));

        while (!pathsToBeProcessedQueue.isEmpty()) {
            File file = pathsToBeProcessedQueue.pop();

            if (file.isDirectory()) {
                pathsToBeProcessedQueue.addAll(Arrays.asList(file.listFiles()));
                continue;
            }
            
            listOfFiles.add(file);
        }
        
        return listOfFiles;
	}
	
}
