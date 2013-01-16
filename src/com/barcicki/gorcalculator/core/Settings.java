package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.barcicki.gorcalculator.core.Opponent.GameColor;
import com.barcicki.gorcalculator.core.Opponent.GameResult;
import com.barcicki.gorcalculator.database.DatabaseHelper;

public class Settings {
	
	private static String TAG = "Settings";

	public static String FILTER_NAME = "filter_name";
	public static String FILTER_CLUB = "filter_club";
	public static String FILTER_COUNTRY = "filter_country";
	public static String FILTER_GRADE = "filter_grade";
	public static String FILTER_GRADE_MIN = "filter_grade_min";
	public static String FILTER_GRADE_MAX = "filter_grade_max";
	
	public static String PLAYER = "player";
	
	public static String OPPONENTS = "opponents";
	public static String OPPONENTS_EMPTY = "[]";
	
	public static String JSON_OPPONENT_GOR = "gor";
	public static String JSON_OPPONENT_PIN = "pin";
	public static String JSON_OPPONENT_WIN = "result";
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
	
	public Bundle getFilters() {
		Bundle filters = new Bundle();
		
		filters.putString(FILTER_NAME, mPrefs.getString(FILTER_NAME, ""));
		filters.putString(FILTER_CLUB, mPrefs.getString(FILTER_CLUB, ""));
		filters.putInt(FILTER_GRADE_MIN, mPrefs.getInt(FILTER_GRADE_MIN, 0));
		filters.putInt(FILTER_GRADE_MAX, mPrefs.getInt(FILTER_GRADE_MAX, Player.STRENGTHS.size() - 1));
		filters.putString(FILTER_COUNTRY, mPrefs.getString(FILTER_COUNTRY, ""));
		
		return filters;
	}
	
	public boolean storeFilters(Bundle map) {
		return storeFilters(
				map.getString(FILTER_NAME),
				map.getString(FILTER_COUNTRY),
				map.getString(FILTER_CLUB),
				map.getInt(FILTER_GRADE_MIN),
				map.getInt(FILTER_GRADE_MAX)
		);
	}
	
	public boolean storeFilters(String name, String country, String club, int gradeMin, int gradeMax) {
		return mPrefs.edit()
				.putString(FILTER_NAME, name)
				.putString(FILTER_COUNTRY, country)
				.putString(FILTER_CLUB, club)
				.putInt(FILTER_GRADE_MIN, gradeMin)
				.putInt(FILTER_GRADE_MAX, gradeMax)
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
					Opponent op = new Opponent(
							p,
							GameResult.valueOf(jo.getString(JSON_OPPONENT_WIN)),
							GameColor.valueOf(jo.getString(JSON_OPPONENT_COLOR)),
							jo.getInt(JSON_OPPONENT_HANDICAP));
					opponents.add(op);
				} else {
					Opponent op = new Opponent(
							jo.getInt(JSON_OPPONENT_GOR),
							GameResult.valueOf(jo.getString(JSON_OPPONENT_WIN)),
							GameColor.valueOf(jo.getString(JSON_OPPONENT_COLOR)),
							jo.getInt(JSON_OPPONENT_HANDICAP));
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
				jo.put(JSON_OPPONENT_GOR, o.getPlayer().getGor());
				jo.put(JSON_OPPONENT_WIN,  o.getResult());
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
