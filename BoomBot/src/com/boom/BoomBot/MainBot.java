package com.boom.BoomBot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.jibble.pircbot.PircBot;
import com.boom.BoomBot.Logger;

public class MainBot extends PircBot {

	// Declaring all the persistent variables
	public String owner = PropLoader.getOwner();
	public String pmChannel = PropLoader.getChannel();
	public String botNick = PropLoader.getNick();
	public LinkedList<String> welcomeList = new LinkedList<String>();
	public ArrayList<InternetsBank> bankList = new ArrayList<InternetsBank>();
	public int chatMode = 0;
	public boolean quietMode = false;
	public boolean greetingMode = false;
	public boolean autoVoice = false;
	public boolean idleKick = false;
	public String[] admins = PropLoader.getAdmins();
	public IdleKick idle = new IdleKick();

	// Main bot class
	public MainBot() {
		// Setting bot name
		this.setName(botNick);
	}

	// All the message triggered commands
	public void onMessage(String channel, String sender, String login,
			String hostName, String message) {
		if (idleKick) {
			idle.UpdateUser(sender);
			try {
				for (int i = 0; i < idle.kick().length; ++i) {
					kick(pmChannel, idle.kick()[i], "Was kick for Idling");
				}
			} catch (NullPointerException e) {
			}
		}
		// Setting up chat logging
		Logger.logFile(message, channel, sender);
		// Boolean variable for the quiet mode
		if (!quietMode) {
			if (message.contains(botNick)) {
				message = message.toLowerCase();
				Logger.commandLog(message, channel, sender);
				if (message.contains("help ")) {
					sendMessage(channel, "Pm me \"user cp\" for user options");
				}
				if (message.contains("time ")) {
					String time = new java.util.Date().toString();
					sendMessage(channel, "The time is " + time);
				}
				if (message.contains("thanks ") || message.contains("thx ")
						|| message.contains("thank ")
						&& message.contains("you ")) {
					sendMessage(channel, "No problem " + sender);
				}
				if (message.contains("introduce yourself")) {
					sendMessage(channel, "Hai everybody, my name is " + botNick);
				}
				if (message.contains("hi ") || message.contains("hello ")
						|| message.contains("hai ") || message.contains("hey ")) {
					sendMessage(channel, "Well hello " + sender);
				}
				if (message.contains("how are you")) {
					sendMessage(channel, "Oh well enough");
				}
				if (message.contains("save") && sender == owner) {
					InternetsBankSaver.save(bankList);
				}
				if (message.contains("vodka") && message.contains("please")) {
					sendAction(channel, "Gives some tasty Vodka to " + sender);
				}
			}
			if (message.contains("The main commands are..")) {
				sendMessage(channel, "Shhh Denise!");
			}
			if (message.contains("youtube.com")) {
				Pattern y = Pattern
						.compile("http://.*youtube.*/watch\\\\?(.*)v=([A-Za-z0-9_\\\\-]+)");
				Pattern t = Pattern
						.compile(".*youtube.*/watch\\\\?(.*)v=([A-Za-z0-9_\\\\-]+)");
				String[] tempString = message.split(" ");
				for (int i = 0; i < tempString.length; i++) {
					if (y.matcher(tempString[i]).find()) {
						try {
							System.out.println("Found a youtube link");
							sendMessage(
									channel,
									"Hey that youtube video is: \u000304"
											+ YoutubeLinkReader
													.webRead(tempString[i]));
						} catch (Exception e) {
							sendMessage(channel, "Video Not Found");
							e.printStackTrace();
						}
					} else {
						if (t.matcher(tempString[i]).find()) {
							String url = "http://" + tempString[i];
							try {
								System.out.println("Found a youtube link");
								sendMessage(
										channel,
										"Hey that youtube video is: \u000304"
												+ YoutubeLinkReader
														.webRead(url));
							} catch (Exception e) {
								sendMessage(channel, "Video Not Found");
								e.printStackTrace();
							}
						}
					}
				}
			}

			// Logging all mentions of the owner, and sending them in a PM
			if (message.contains(owner) && sender != botNick && sender != owner) {
				String time = new java.util.Date().toString();
				sendMessage(PropLoader.getOwner(), "[" + time + "]" + "["
						+ channel + "]" + "[" + sender + "]" + ": " + message);
				Logger.mentionLog(message, channel, sender);
			}
		}
	}

	// The on join triggered actions
	protected void onJoin(String channel, String sender, String login,
			String hostname) {
		// Sets up bot greeting
		if (greetingMode) {
			if (!welcomeList.contains(sender)) {
				welcomeList.add(sender);
				sendMessage(channel, "Welcome to " + channel + " " + sender);
				LinkedListSaver.save("welcomeLog.db", welcomeList.toString());
			}
		}
		if (autoVoice) {
			voice(channel, sender);
		}
	}

	// The PM triggered actions
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		// PM talk as bot method
		if (sender.equalsIgnoreCase(owner) && !message.contains("admin")
				&& !message.contains("user")) {
			sendMessage(pmChannel, message);
		}
		// Setting up User Control Panel
		if (message.toLowerCase().contains("user")) {
			Logger.commandLog(message, "User CP", sender);
			String userCP = message.replace("user ", "");
			if (userCP.contains("cp")) {
				sendMessage(sender, "[USER CP]:");
				sendMessage(sender, "1. Help");
				sendMessage(sender,
						"2. Request Feature (feature + discription)");
				sendMessage(sender, "3. Report Problem (problem + discription)");
				sendMessage(sender, "4. Generate bank");
				sendMessage(sender, "5. Bank bal");
				sendMessage(sender, "6. Bank Pay (User Nick)");
				sendMessage(sender,
						"(Ex.):user report problem He keeps double posting!");
			}
			if (userCP.contains("help")) {
				sendMessage(
						sender,
						"Hello, I am "
								+ botNick
								+ " and I was coded to be more human like than most bots");
				sendMessage(sender,
						"I have a few features that you may find helpful");
				sendMessage(sender,
						"For example, I can decode YouTube links, try posting one");
				sendMessage(sender,
						"Or I can talk to you if you would like, just ask "
								+ owner);
			}
			if (userCP.contains("request feature")) {
				String tempString = userCP.replace("request feature ", "");
				Logger.suggestionLog(tempString, "Suggestion", sender);
				sendMessage(owner, "Hey we got a new suggestion: " + tempString);
				sendMessage(sender, "Thanks for the help");
			}
			if (userCP.contains("report problem")) {
				String tempString = userCP.replace("report problem ", "");
				Logger.issueLog(tempString, "issue", sender);
				sendMessage(owner, "A problem was reported by: " + sender
						+ ", and the message was: " + tempString);
				sendMessage(sender, "Thanks for the heads up!");
			}
			if (userCP.toLowerCase().contains("generate bank")) {
				InternetsBank newBank = new InternetsBank();
				newBank.setUserName(sender);
				newBank.setBallance(10);
				bankList.add(newBank);
				sendMessage(sender, "Bank generated for " + sender);
			}
			if (userCP.toLowerCase().contains("bal")) {
				for (int i = 0; i < bankList.size(); ++i) {
					if (bankList.get(i).is(sender)) {
						sendMessage(sender, "Your balance is "
								+ bankList.get(i).getBallance() + " Internets.");
					}

				}
			}

			if (userCP.toLowerCase().contains("pay")) {
				InternetsBank paying = new InternetsBank();
				InternetsBank paied = new InternetsBank();
				int amount = 0;
				userCP = userCP.replace("bank ", "");
				userCP = userCP.replace("Bank ", "");
				userCP = userCP.replace("pay ", "");
				userCP = userCP.replace("Pay ", "");
				String[] tempArray = userCP.split(" ");
				for (int i = 0; i < bankList.size(); ++i) {
					if (bankList.get(i).is(sender)) {
						paying = bankList.get(i);
					}
					if (bankList.get(i).is(tempArray[0])) {
						paied = bankList.get(i);
					}
					if (tempArray[1] != null) {
						amount = java.lang.Integer.parseInt(tempArray[1]);
					}
				}
				if (paying.getUserName() != null && paied.getUserName() != null) {
					sendMessage(paying.getUserName(), "paied " + amount
							+ " to " + paied.getUserName());
					sendMessage(paied.getUserName(), "Got " + amount
							+ " Internets from " + paying.getUserName());
					paied.pay(amount, paying);
				}
			}
		}
		// Setting up Admin Control Panel
		if (message.toLowerCase().contains("admin")) {
			for (int i = 0; i < admins.length; ++i) {
				if (sender.equals(admins[i])) {
					Logger.commandLog(message, "Admin CP", sender);
					String adminCP = message.replace("admin", "");
					if (adminCP.contains("cp")) {
						sendMessage(sender, "[ADMIN CP]: ");
						if (quietMode) {
							sendMessage(sender, "1. Quiet Mode ([true]|false)");
						} else {
							sendMessage(sender, "1. Quiet Mode (true|[false])");
						}
						if (greetingMode) {
							sendMessage(sender,
									"2. Greeting Mode([true]|false)");
						} else {
							sendMessage(sender,
									"2. Greeting Mode(true|[false])");
						}
						if (autoVoice) {
							sendMessage(sender, "3. Auto Voice ([true]|false)");
						} else {
							sendMessage(sender, "3. Auto Voice (true|[false])");
						}
						if (idleKick) {
							sendMessage(sender, "4. Kick Idle ([true]|false)");
						} else {
							sendMessage(sender, "4. Kick Idle (true|[false])");
						}
						sendMessage(sender, "4. Kick (User Nick)");
						sendMessage(sender, "5. Ban (User Nick)");
						sendMessage(sender, "6. Op (User Nick)");
						sendMessage(sender, "7. Voice (User Nick)");
						sendMessage(sender,
								"8. Change Bot Nick (New Nick | reset)");
						sendMessage(sender,
								"9. NickServ Register (password + email)");
						sendMessage(sender, "10. Generate Bank (User Nick)");
						sendMessage(sender,
								"11. Set Balance (User Nick + amount)");
						sendMessage(sender, "12. Disconnect");
						sendMessage(sender, "(Ex.):admin quiet mode true");
					}
					if (adminCP.toLowerCase().contains("quiet mode")) {
						String tempString = adminCP.toLowerCase().replace(
								"quiet mode ", "");
						if (tempString.contains("true")) {
							sendMessage(sender, "Quiet mode engaged");
							quietMode = true;
						}
						if (tempString.contains("false")) {
							sendMessage(sender, "Now talking freely");
							quietMode = false;
						}
					}
					if (adminCP.toLowerCase().contains("greeting mode")) {
						String tempString = adminCP.toLowerCase().replace(
								"greeting mode ", "");
						if (tempString.contains("true")) {
							sendMessage(sender, "Now greeting");
							greetingMode = true;
						}
						if (tempString.contains("false")) {
							sendMessage(sender, "Not greeting anymore");
							greetingMode = false;
						}
					}
					if (adminCP.toLowerCase().contains("kick idle")) {
						if (adminCP.toLowerCase().contains("true")) {
							idleKick = true;
							sendMessage(sender, "Now kicking idle users");
						}
						if (adminCP.toLowerCase().contains("false")) {
							idleKick = false;
							sendMessage(sender, "No longer kicking idle users");
						}
					}
					if (adminCP.toLowerCase().contains("kick")
							&& !adminCP.toLowerCase().contains("kick idle")) {
						String tempString = adminCP.replace("kick ", "");
						tempString = tempString.replace("Kick ", "");
						kick(pmChannel, tempString);
					}
					if (adminCP.toLowerCase().contains("ban")) {
						String tempString = adminCP.replace("ban ", "");
						tempString = tempString.replace("Ban ", "");
						ban(pmChannel, tempString);
					}
					if (adminCP.toLowerCase().contains("op")) {
						String tempString = adminCP.replace("op ", "");
						tempString = tempString.replace("Op ", "");
						op(pmChannel, tempString);
					}
					if (adminCP.toLowerCase().contains("voice")) {
						String tempString = adminCP.replace("voice ", "");
						tempString = tempString.replace("Voice ", "");
						voice(pmChannel, tempString);
					}
					if (adminCP.toLowerCase().contains("auto voice")) {
						String tempString = adminCP.toLowerCase().replace(
								"auto voice ", "");
						if (tempString.contains("true")) {
							autoVoice = true;
							sendMessage(sender, "Now auto voicing");
						}
						if (tempString.contains("false")) {
							autoVoice = false;
							sendMessage(sender, "Stoping the auto voicing");
						}
					}
					if (adminCP.toLowerCase().contains("change bot nick")) {
						String tempString = adminCP.toLowerCase().replace(
								"change bot nick ", "");
						if (!tempString.contains("reset")) {
							botNick = tempString;
							changeNick(botNick);
						} else {
							botNick = PropLoader.getNick();
							changeNick(botNick);
						}
					}
					if (adminCP.toLowerCase().contains("generate bank")) {
						String tempString = adminCP.replace("generate bank ",
								"");
						tempString = tempString.replace("Generate Bank ", "");
						tempString = tempString.replace(" ", "");
						InternetsBank newBank = new InternetsBank();
						newBank.setUserName(tempString);
						newBank.setBallance(0);
						bankList.add(newBank);
						sendMessage(sender, "Bank generated for " + tempString);
					}
					if (adminCP.toLowerCase().contains("nickserv register")) {
						String tempString = adminCP.toLowerCase().replace(
								"nickserv register ", "");
						sendMessage("NickServ", "register " + tempString);
					}
					if (adminCP.toLowerCase().contains("set balance")) {
						adminCP = adminCP.replace("set balance ", "");
						adminCP = adminCP.replace("Set Balance ", "");
						String[] tempArray = adminCP.split(" ");
						for (int i1 = 0; i1 < bankList.size(); ++i1) {
							if (bankList.get(i1).is(tempArray[0])) {
								bankList.get(i1).setBallance(
										java.lang.Integer
												.parseInt(tempArray[1]));
								sendMessage(sender, "Changing the balance of "
										+ tempArray[0] + " to " + tempArray[1]);
							}

						}
					}
					if (adminCP.contains("disconnect")) {
						disconnect();

					}
				}
			}
		}
	}

	protected void onMode(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String mode) {
		if (mode.contains("+m")) {
			autoVoice = false;
			sendMessage(channel,
					"Mode set to +m, so I have to stop giving voice. Sorry guys!");
		}
	}

	protected void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		if (oldNick.equals(owner)) {
			owner = newNick;
		}

	}

	protected void onAction(String channel, String sender, String login,
			String hostName, String action) {

		if (action.contains(botNick) && action.contains("rapes")
				|| action.contains(botNick) && action.contains("rape")
				|| action.contains(botNick) && action.contains("ties")
				|| action.contains(botNick) && action.contains("ties up")) {
			sendAction(pmChannel,
					"Escapes and slaps " + sender.replace("~", "")
							+ " upside the head");
		}
	}

	protected void onConnect() {
		idle.GenerateUserArray(getUsers(pmChannel));
		bankList = InternetsBankSaver.load();
		welcomeList = LinkedListSaver.read("welcomeLog.db");
	}

	protected void onDisconnect() {
		InternetsBankSaver.save(bankList);
		LinkedListSaver.save("welcomeLog.db", welcomeList.toString());
	}
}
