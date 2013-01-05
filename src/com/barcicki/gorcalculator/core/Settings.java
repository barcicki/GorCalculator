package com.barcicki.gorcalculator.core;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

	public static String FILTER_NAME = "filter_name";
	public static String FILTER_CLUB = "filter_club";
	public static String FILTER_COUNTRY = "filter_country";
	public static String FILTER_GRADE = "filter_grade";
	
	public static String PLAYER = "player";
	
	public SharedPreferences mPrefs;
	public Context mContext;
	
	public Settings(Context context) {
		mContext = context;
		mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public HashMap<String,String> getFilters() {
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put(FILTER_NAME, mPrefs.getString(FILTER_NAME, ""));
		map.put(FILTER_CLUB, mPrefs.getString(FILTER_CLUB, ""));
		map.put(FILTER_GRADE, mPrefs.getString(FILTER_GRADE, ""));
		map.put(FILTER_COUNTRY, mPrefs.getString(FILTER_COUNTRY, ""));
		
		return map;
	}
	
	public boolean storeFilters(HashMap<String, String> map) {
		return storeFilters(
				map.get(FILTER_NAME),
				map.get(FILTER_COUNTRY),
				map.get(FILTER_CLUB),
				map.get(FILTER_GRADE)
		);
	}
	
	public boolean storeFilters(String name, String country, String club, String grade) {
		return mPrefs.edit()
				.putString(FILTER_NAME, name)
				.putString(FILTER_COUNTRY, country)
				.putString(FILTER_CLUB, club)
				.putString(FILTER_GRADE, grade)
				.commit();		
	}
	
	public int getPlayerPIN() {
		return mPrefs.getInt(PLAYER, 0);
	}
	
	public boolean storePlayerPIN(int pin) {
		return mPrefs.edit().putInt(PLAYER, pin).commit();
	}
	
}
