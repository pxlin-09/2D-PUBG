// Menu.java
// by Alex Lin, Lizhuo You and Yanbo Hou
// this is the data base behind the loading page of the game, contains login, intro and game start
package com.mygdx.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Menu {
	// sprites on the menu
	private String login = "login.png";
	private String start = "start.png";
	private String bg = "background.jpg";
	private String loading = "loading.jpg";
	private String menu = "menu.png";
	// contain all the user has recorded
	private List<User> users = new ArrayList<User>();
	// file of introduction
	private final File intro = new File("intro.txt");

	public Menu() {
		init();
	}

	// read from the txt file to get all the data before
	public void init() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("users.txt"));
			users.add((User)ois.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// save all the user in the arraylist
	public void saveUser() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("users.txt"));
			oos.writeObject(users);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<String> showIntro() {
		BufferedReader br = null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(intro));
			String str = "";
			while ((str = br.readLine()) != null) {
				lines.add(str);
//				System.out.println(str);
			}
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	public List<User> getUsers() {
		return users;
	}

	public String getLogin() {
		return login;
	}

	public String getStart() {
		return start;
	}

	public String getBg() {
		return bg;
	}

	public String getLoading() {
		return loading;
	}

	public String getMenu() {
		return menu;
	}
}
