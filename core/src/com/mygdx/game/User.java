// User.java
// by Lizhuo You and Yanbo Hou
// the class user is used to create user object to save their score and username
package com.mygdx.game;

import java.io.Serializable;

public class User implements Serializable {
	// simple user class
	private String username;
	private int killNum;
	private int deathTime;

	public User(String username) {
		this.username = username;
		killNum = 0;
		deathTime = 0;
	}

	public String getUsername() {
		return username;
	}

	public int getkillNum() {
		return killNum;
	}

	public void addKillNum(int killNum) {
		this.killNum += killNum;
	}

	public int getDeathTime() {
		return deathTime;
	}

	public void addDeathTime(int deathTime) {
		this.deathTime += deathTime;
	}

	public String killDeathRadio() {
		String ans = (double) killNum / deathTime + "";
		return (ans).substring(0, ans.indexOf(",") + 3);
	}

}
