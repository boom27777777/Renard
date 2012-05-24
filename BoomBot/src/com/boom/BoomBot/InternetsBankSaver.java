package com.boom.BoomBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.boom.BoomBot.InternetsBank;

public class InternetsBankSaver {
	public static void save(ArrayList<InternetsBank> list) {
		new File("BankFiles").mkdir();
		try {
			for (int i = 0; i < list.size(); i++) {
				InternetsBank tempObject = list.get(i);
				String name = tempObject.getUserName();
				System.out.println(name);
				int bal = list.get(i).getBallance();
				PrintWriter write = new PrintWriter(new FileWriter(new File(
						"BankFiles/" + name)));
				write.println("Name=" + name);
				write.println("Balance=" + bal);
				write.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<InternetsBank> load() {
		ArrayList<InternetsBank> tempList = new ArrayList<InternetsBank>();
		String[] read = new File("BankFiles/").list();
		if (read != null) {
			for (int i = 0; i < read.length; ++i) {
				try {
					InternetsBank tempBank = new InternetsBank();
					Scanner MyFile = new Scanner(new File("BankFiles/"
							+ read[i]));
					tempBank.setUserName(MyFile.nextLine().replace("Name=", ""));
					tempBank.setBallance(java.lang.Integer.parseInt(MyFile
							.nextLine().replace("Balance=", "")));
					tempList.add(tempBank);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
		else {
			System.out.println("No Bank Files found");
		}
		return tempList;
	}
}
