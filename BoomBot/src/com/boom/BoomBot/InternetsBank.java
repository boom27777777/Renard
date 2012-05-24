package com.boom.BoomBot;

public class InternetsBank {
	public String userName;
	public int ballance;
	
	
	public boolean is(String userNick){
		if (userNick.equals(getUserName())){
			return true;
		}
		else {
			return false;
		}
	}
	public void add(int amount) {
		setBallance(getBallance() + amount);
	}
	public void subtract(int amount) {
		setBallance(getBallance() - amount);
	}
	public boolean canPay(int amount) {
		if (getBallance() >= amount) {
			return true;
		}
		else {
			return false; 
		}
	}
	public boolean pay(int amount, InternetsBank paying){
		boolean can = false;
		if (paying.canPay(amount)) {
			can = true;
			paying.subtract(amount);
			add(amount);
		}
		return can;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String user) {
		this.userName = user;
	}
	public int getBallance() {
		return this.ballance;
	}
	public void setBallance(int bal) {
		this.ballance = bal;
	}
}
