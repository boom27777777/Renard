package com.boom.BoomBot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class YoutubeLinkReader {

	public static String html;
	public static String ytOut;

	public static String webRead(String Url) throws Exception {
		try {
		URL y = new URL(Url);
		URLConnection t = y.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				t.getInputStream()));
		Tidy tidy = new Tidy();
		tidy.setShowErrors(0);
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		Document doc = tidy.parseDOM(in, null);
		String titleText = doc.getElementsByTagName("title").item(0)
				.getFirstChild().getNodeValue();
		html = titleText;
		if (html != null) {
			ytOut = "\"" + html.replace(" - YouTube", "") + "\"";
		} else {
			ytOut = "uh... Nevermind";
		}
		in.close();
		}
		catch (java.lang.Error e){
			e.printStackTrace();}
		catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		}
		return ytOut;
	}

	public static String getHtml() {
		return html;
	}

	public static void setHtml(String string) {
		html = string;
	}
}
