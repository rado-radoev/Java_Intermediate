package com.amazonlite.model;

import java.io.File;
import java.io.IOException;

public class FilesFinder {

	public FilesFinder() {
	}

	public static void main(String[] args) throws IOException {
		
		File folder = new File("./src/com/amazonlite/model");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        String fileName = listOfFiles[i].getName().substring(0,listOfFiles[i].getName().indexOf("."));
		        for (ItemType item : ItemType.values()) {
					if (fileName.toUpperCase().equals(item.toString())) {
						System.out.println(fileName);
					}
		        }
		      } 
		    }
	}

}
