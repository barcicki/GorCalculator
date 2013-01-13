package com.barcicki.gorcalculator.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.core.Player;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 15;
	private static final String DATABASE_NAME = "gorcalculator";
	
	public static final String KEY_ID = "_id";
	public static final String KEY_PIN = "pin";
	public static final String KEY_NAME = "name";
	public static final String KEY_CLUB = "club";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_GOR = "gor";
	public static final String KEY_GRADE_VALUE = "grade";
	
	public static final int LIMIT = 50;
	public static final int FIRST_PAGE = 0;
	
	private static final String PLAYER_TABLE_NAME = "players";
	private static final String PLAYER_TABLE_CREATE = 
			"CREATE TABLE " + PLAYER_TABLE_NAME + " (" + 
		    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			KEY_PIN + " INTEGER NOT NULL, " +
			KEY_NAME + " TEXT NOT NULL COLLATE NOCASE, " + 
			KEY_CLUB+ " TEXT NOT NULL COLLATE NOCASE, " + 
			KEY_COUNTRY + " TEXT NOT NULL COLLATE NOCASE, " + 
			KEY_GRADE_VALUE + " INTEGER NOT NULL, " + 
			KEY_GOR + " INTEGER NOT NULL DEFAULT 100" + 
		    ")";
	private static final String PLAYER_TABLE_SELECT_ALL = 
			"SELECT " + 
					KEY_ID + ", " +
					KEY_PIN + ", " +
					KEY_NAME + ", " + 
					KEY_GRADE_VALUE + ", " + 	
					KEY_GOR + ", " + 	
					KEY_CLUB + ", " + 	
					KEY_COUNTRY + " " + 
			"FROM " + PLAYER_TABLE_NAME;
	private static final String PLAYER_TABLE_DROP = "DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME;
	

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(PLAYER_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(PLAYER_TABLE_DROP);
		onCreate(db);
	}
	
	public long insertPLayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();
		return insertPlayer(db, player);
	}
	
	public long insertPlayer(SQLiteDatabase db, int pin, String name, String country, String club, int grade, int gor) {
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_PIN, pin);
		cv.put(KEY_NAME, name);
		cv.put(KEY_CLUB, club);
		cv.put(KEY_COUNTRY, country);
		cv.put(KEY_GRADE_VALUE, grade);
		cv.put(KEY_GOR, gor);
		
		return db.insert(PLAYER_TABLE_NAME, KEY_NAME, cv);
	}
	
	private long insertPlayer(SQLiteDatabase db, Player player) {
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_PIN, 1);
		cv.put(KEY_NAME, player.getName());
		cv.put(KEY_CLUB, player.getClub());
		cv.put(KEY_COUNTRY, player.getCountry());
		cv.put(KEY_GRADE_VALUE, player.getGradeValue());
		cv.put(KEY_GOR, player.getGor());
		
		return db.insert(PLAYER_TABLE_NAME, KEY_NAME, cv);
	}
	
	public void clearPlayers(SQLiteDatabase db) {
		db.execSQL("DELETE FROM " + PLAYER_TABLE_NAME);
	}
	
	public ArrayList<Player> getPlayers(int page, String name, String club, String country, int gradeMin, int gradeMax) {
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<String> conditions = new ArrayList<String>();
		
		Log.d("Database", "GetPlayers: " + TextUtils.join(" ", new String[] { name, club, country, gradeMin + "", gradeMax + "" }));
		
		boolean conditionsApplies = false;
		
		if (name != null) {
			conditions.add(KEY_NAME + " LIKE '%" + name + "%'");
			conditionsApplies = true;
		}
		
		if (club != null) {
			conditions.add(KEY_CLUB + " LIKE '%" + club + "%'");
			conditionsApplies = true;
		}
		
		if (country != null) {
			conditions.add(KEY_COUNTRY + " LIKE '%" + country + "%'");
			conditionsApplies = true;
		}
		
		if (gradeMin > 0 || gradeMax < Player.STRENGTHS.size() - 1) {
			conditions.add(KEY_GRADE_VALUE + " BETWEEN " + gradeMin + " AND " + gradeMax);
			conditionsApplies = true;
		}
		
		String query;
		
		if (conditionsApplies) {
			query = PLAYER_TABLE_SELECT_ALL + " WHERE (" + TextUtils.join(") AND (", conditions) + ") LIMIT " + LIMIT + " OFFSET " + LIMIT * page;
		} else {
			query = PLAYER_TABLE_SELECT_ALL + " LIMIT " + LIMIT + " OFFSET " + LIMIT * page;
		}
		
		Log.d("Database", query);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery(query, null);
		
		if (result.getCount() > 0) {
			result.moveToFirst();
			do {
				players.add(convertToPlayer(result));
			} while (result.moveToNext());
		}
		
		db.close();
		Log.d("Database", "Found: " + players.size());
		return players;
	}
	
	public ArrayList<Player> getPlayers() {
		return getPlayers(0);
	}
	
	public ArrayList<Player> getPlayers(int page) {
		ArrayList<Player> players = new ArrayList<Player>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery(PLAYER_TABLE_SELECT_ALL + " LIMIT " + LIMIT, null);
		
		if (result.getCount() > 0) {
			result.moveToFirst();
			do {
				players.add(convertToPlayer(result));
			} while (result.moveToNext());
		}
		
		db.close();
		return players;
	}
	
	public Player getRandomPlayer() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery(PLAYER_TABLE_SELECT_ALL + " ORDER BY RANDOM() LIMIT 1", null);
		Player response = new Player((int) Calculator.MIN_GOR);;
		
		if (result.moveToFirst()) {
			response = convertToPlayer(result); 
		} 
		
		db.close();
		return response;
		
	}
	
	private Player convertToPlayer(Cursor result) {
		return new Player(
				result.getInt(result.getColumnIndex(KEY_PIN)),
				result.getString(result.getColumnIndex(KEY_NAME)),
				result.getString(result.getColumnIndex(KEY_CLUB)),
				result.getString(result.getColumnIndex(KEY_COUNTRY)),
				result.getInt(result.getColumnIndex(KEY_GRADE_VALUE)),
				result.getInt(result.getColumnIndex(KEY_GOR)));
	}

	public Player getPlayerByPin(int playerPIN) {
		Player player = null;
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor result = db.rawQuery(PLAYER_TABLE_SELECT_ALL + " WHERE " + KEY_PIN + " = " + playerPIN, null);
		if (result.getCount() > 0) {
			result.moveToFirst();
			player = convertToPlayer(result);
		}
		
		db.close();
		return player;
	}

}
