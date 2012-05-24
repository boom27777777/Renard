package com.boom.BoomBot;
public class BotIni {
private static int stop = 0;
	public static void main(String[] args) throws Exception {
		PropLoader.PropCheck();
		PropLoader.PropLoad();
		String servername = PropLoader.getServer();
		String channels = PropLoader.getChannel();
		final String server = servername;

		MainBot bot = new MainBot();

		bot.setVerbose(true);
		
		try {
			System.out.println("Attempting Connection with " + server);
		bot.connect(server);
		} catch (java.net.ConnectException e){
			System.out.println("Start up failed, Did you mistype the URL in the config.ini");
			stop = 1;
		}
		bot.identify(PropLoader.getPassword());
		bot.joinChannel(channels);

		while (!bot.isConnected() && stop == 0) {
			try {
				bot.reconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
