package com.boom.BoomBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class LinkedListSaver {
	public static void save(String fileName, String saveTarget) {
		try {
			File newFile = new File(fileName);
			FileWriter outFile = new FileWriter(newFile);
			PrintWriter out = new PrintWriter(outFile);
			out.println(saveTarget);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<String> read(String fileName) {
		Scanner MyFile;
		LinkedList<String> list = new LinkedList<String>();
		try {
			MyFile = new Scanner(new File(fileName));
			while (MyFile.hasNext()) {
				String tempString = MyFile.nextLine().replace("[", "");
				String tempString2 = tempString.replace("]", "");
				String[] tempArray = tempString2.split(", ");
				for (int i = 0; i < tempArray.length; i++) {
					list.add(tempArray[i].toString());
				}
			}
			MyFile.close();
		} catch (FileNotFoundException e) {
			File newFile = new File(fileName);
			try {
				newFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("file " + fileName
					+ " not found, making one now");

		}
		return list;
	}
}
