package com.boom.BoomBot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

	public static void newLogger(String loggerName, String message,
			String channel, String sender) {
		String time = new java.util.Date().toString();
		String logfile = loggerName;
		String logExtentions = PropLoader.getLogExtention();

		try {
			File errorLog = new File("[" + channel + "]" + logfile + "."
					+ logExtentions);
			FileWriter outFile = new FileWriter(errorLog, true);
			PrintWriter out = new PrintWriter(outFile);
			out.println("[" + channel + "]" + "[" + time + "]" + "[" + sender
					+ "]" + ": " + message);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logFile(String message, String channel, String sender) {
		newLogger(PropLoader.getLogfile(), message, channel, sender);
	}

	public static void commandLog(String message, String channel, String sender) {
		newLogger("Commandlog", message, channel, sender);
	}

	public static void mentionLog(String message, String channel, String sender) {

		newLogger("MentionLog", message, channel, sender);
	}

	public static void suggestionLog(String message, String channel,
			String sender) {
		newLogger("SuggestionLog", message, channel, sender);
	}

	public static void issueLog(String message, String channel, String sender) {
		newLogger("IssueLog", message, channel, sender);
	}
}
