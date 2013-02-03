package com.barcicki.gorcalculator.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Settings {

	public static String FILTER_NAME = "filter_name";
	public static String FILTER_CLUB = "filter_club";
	public static String FILTER_COUNTRY = "filter_country";
	public static String FILTER_GRADE = "filter_grade";
	public static String FILTER_GRADE_MIN = "filter_grade_min";
	public static String FILTER_GRADE_MAX = "filter_grade_max";

	public static String DOWNLOADED_PLAYER_LIST = "downloaded_player_list";
	
	public static final String HINT_FILTER = "hint_filter";
	public static final String HINT_DELETE_OPPONENT = "hint_opponent";
	public static final String HINT_ADD_TOURNAMENT = "hint_tournament";
	public static final String HINT_FIND_PLAYER = "hint_find_player";

	private SharedPreferences mPrefs;

	public Settings(Context context) {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public boolean hasDownloadedPlayerList() {
		return mPrefs.getBoolean(DOWNLOADED_PLAYER_LIST, false);
	}

	public boolean storeDownloadedPlayerList(boolean value) {
		return mPrefs.edit().putBoolean(DOWNLOADED_PLAYER_LIST, value).commit();
	}

	public Bundle getFilters() {
		Bundle filters = new Bundle();

		filters.putString(FILTER_NAME, mPrefs.getString(FILTER_NAME, ""));
		filters.putString(FILTER_CLUB, mPrefs.getString(FILTER_CLUB, ""));
		filters.putInt(FILTER_GRADE_MIN, mPrefs.getInt(FILTER_GRADE_MIN, 0));
		filters.putInt(FILTER_GRADE_MAX,
				mPrefs.getInt(FILTER_GRADE_MAX, Go.STRENGTHS.size() - 1));
		filters.putString(FILTER_COUNTRY, mPrefs.getString(FILTER_COUNTRY, ""));

		return filters;
	}

	public boolean storeFilters(Bundle map) {
		return storeFilters(map.getString(FILTER_NAME),
				map.getString(FILTER_COUNTRY), map.getString(FILTER_CLUB),
				map.getInt(FILTER_GRADE_MIN), map.getInt(FILTER_GRADE_MAX));
	}

	public boolean storeFilters(String name, String country, String club,
			int gradeMin, int gradeMax) {
		return mPrefs.edit().putString(FILTER_NAME, name)
				.putString(FILTER_COUNTRY, country)
				.putString(FILTER_CLUB, club)
				.putInt(FILTER_GRADE_MIN, gradeMin)
				.putInt(FILTER_GRADE_MAX, gradeMax).commit();
	}
	
	public boolean storeHint(String key, boolean value) {
		return mPrefs.edit().putBoolean(key, value).commit();
	}
	
	public boolean getHint(String key) {
		return mPrefs.getBoolean(key, false);
	}

}
