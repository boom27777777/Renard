package com.boom.BoomBot;

import java.util.ArrayList;

import org.jibble.pircbot.User;

public class IdleKick {
	private static User[] PersistantUserArray;
	private static ArrayList<UserIdleTime> UserList = new ArrayList<UserIdleTime>();

	public void GenerateUserArray(User[] userArray) {
		PersistantUserArray = userArray;
	}

	public void GenerateUserList() {
		@SuppressWarnings("deprecation")
		int time = new java.util.Date().getHours();
		for (int i = 0; i < PersistantUserArray.length; ++i) {
			UserIdleTime user = new UserIdleTime();
			user.setTime(time);
			user.setUserNick(PersistantUserArray[i].getNick());
			UserList.add(user);
			
		}
	}
	public void UpdateUser(String name){
		@SuppressWarnings("deprecation")
		int time = new java.util.Date().getHours();
		if (UserList.contains(name)){
			for (int i = 0; i < UserList.size(); ++i){ 
				if (UserList.get(i).getUserNick().equals(name)){
					UserList.get(i).setTime(time);
				}
			}
		}
		else {
			UserIdleTime frank = new UserIdleTime();
			frank.setUserNick(name);
			frank.setTime(time);
			UserList.add(frank);
		}
	}
	@SuppressWarnings("null")
	public String[] kick(){
		String[] kickTargets = null;
		@SuppressWarnings("deprecation")
		int time = new java.util.Date().getHours();
		for (int i = 0; i < UserList.size(); ++i){
			 int diff = time - UserList.get(i).getTime();
			 if (diff >= 6){
				 kickTargets[kickTargets.length] = UserList.get(i).getUserNick();
			 }
		}
		return kickTargets;
	}

}
