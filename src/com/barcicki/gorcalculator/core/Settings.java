package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.barcicki.gorcalculator.database.DatabaseHelper;

public class Settings {
	
	private static String TAG = "Settings";

	public static String FILTER_NAME = "filter_name";
	public static String FILTER_CLUB = "filter_club";
	public static String FILTER_COUNTRY = "filter_country";
	public static String FILTER_GRADE = "filter_grade";
	
	public static String PLAYER = "player";
	
	
	public static String OPPONENTS = "opponents";
	public static String OPPONENTS_EMPTY = "[]";
	
	public static String JSON_OPPONENT_PIN = "pin";
	public static String JSON_OPPONENT_WIN = "win";
	public static String JSON_OPPONENT_COLOR = "color";
	public static String JSON_OPPONENT_HANDICAP = "handicap";
	
	private  SharedPreferences mPrefs;
	private Context mContext;
	private DatabaseHelper mDB;
	
	public Settings(Context context) {
		mContext = context;
		mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		mDB = new DatabaseHelper(context);
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
	
	public Player getStoredPlayer() {
		return mDB.getPlayerByPin(mPrefs.getInt(PLAYER, 0)) ;
	}
	
	public boolean storePlayer(Player player) {
		return mPrefs.edit().putInt(PLAYER, player.getPin()).commit();
	}
	
	public ArrayList<Opponent> getStoredOpponents() {
		ArrayList<Opponent> opponents = new ArrayList<Opponent>();
		String json = mPrefs.getString(OPPONENTS, OPPONENTS_EMPTY);
		Log.d(TAG, "Got Opponent Json: " + json);
		
		try {
			JSONArray idsArray = new JSONArray(json);
			for (int i = 0, l = idsArray.length(); i < l; i++) {
				JSONObject jo = idsArray.getJSONObject(i);
				
				Player p = mDB.getPlayerByPin(jo.getInt(JSON_OPPONENT_PIN));
				if (p != null) {
					Opponent op = new Opponent(p, (float) jo.getDouble(JSON_OPPONENT_WIN), jo.getInt(JSON_OPPONENT_COLOR), jo.getInt(JSON_OPPONENT_HANDICAP));
					opponents.add(op);
				}
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing opponents: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return opponents;
	}
	
	public boolean storeOpponents(ArrayList<Opponent> opponents) {
		JSONArray ja = new JSONArray();
		
		try {
			for (Opponent o : opponents) {
				JSONObject jo = new JSONObject();
				jo.put(JSON_OPPONENT_PIN, o.getPlayer().getPin());
				jo.put(JSON_OPPONENT_WIN, (double) o.getResult());
				jo.put(JSON_OPPONENT_COLOR, o.getColor());
				jo.put(JSON_OPPONENT_HANDICAP, o.getHandicap());
				ja.put(jo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return mPrefs.edit().putString(OPPONENTS, ja.toString()).commit();
	}
	
}
