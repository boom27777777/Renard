package com.boom.BoomBot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class PropLoader {

	public static String nick;
	public static String channel;
	public static String server;
	public static String logfile;
	public static String owner;
	public static String password;
	public static String LogExtention;
	public static String Admins;


	public static void PropCheck() {
		File config = new File("config.ini");
		if (!config.exists()) {
			System.out.println("Missing config.ini Making one now");
			FileWriter outFile;
			try {
				outFile = new FileWriter(config);
				PrintWriter out = new PrintWriter(outFile);
				out.println("Server=");
				out.println("NickName=");
				out.println("Channels=");
				out.println("LogFile=");
				out.println("LogExtentions=");
				out.println("Owner=");
				out.println("Password=");

				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void PropLoad() {
		Properties prop = new Properties();
		try {
			InputStream config = new FileInputStream("config.ini");
			prop.load(config);
			setNick(prop.getProperty("NickName"));
			setChannel(prop.getProperty("Channels"));
			setServer(prop.getProperty("Server"));
			setLogfile(prop.getProperty("LogFile"));
			setOwners(prop.getProperty("Owner"));
			setPassword(prop.getProperty("Password"));
			setLogExtention(prop.getProperty("LogExtentions"));
			setAdmins(prop.getProperty("Admins"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getLogExtention() {
		return LogExtention;
	}

	public static void setLogExtention(String string) {
		LogExtention = string;
	}

	public static String getOwner() {
		return owner;
	}

	public static void setOwners(String string) {
		owner = string;
	}

	public static String getNick() {
		return nick;
	}

	public static void setNick(String string) {
		nick = string;
	}

	public static String getChannel() {
		return channel;
	}

	public static void setChannel(String string) {
		channel = string;
	}

	public static String getServer() {
		return server;
	}

	public static void setServer(String string) {
		server = string;
	}

	public static String getLogfile() {
		return logfile;
	}

	public static void setLogfile(String string) {
		logfile = string;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String string) {
		password = string;
	}
	public static String[] getAdmins() {
		String[] AdminArray = Admins.split(",");
		return AdminArray;
	}

	public static void setAdmins(String admins) {
		Admins = admins;
	}
}
