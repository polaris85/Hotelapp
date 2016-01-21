package com.liang.model;

import java.util.HashMap;

import com.liang.hotelicon.MainActivity;
import com.liang.hotelicon.MenuViewFragment;


public class DataManager {
	
	public static MainActivity 		mainActivity;
	public static MenuViewFragment 	menuViewFragment;
	public static int 				languageType;
	public static boolean			loginFlag;
	
	public static String			session_id;
	public static String			username;
	public static String			password;
	
	public static HashMap<String, String> selectedCoupon;
	
	public DataManager() {	
		mainActivity = null;
		loginFlag = false;
		session_id = "";
	}
}
