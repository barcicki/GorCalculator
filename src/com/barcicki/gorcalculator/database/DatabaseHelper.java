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

	private static final int DATABASE_VERSION = 17;
	private static final String DATABASE_NAME = "gorcalculator";
	
	public static final String PLAYER_KEY_ID = "_id";
	public static final String PLAYER_KEY_PIN = "pin";
	public static final String PLAYER_KEY_NAME = "name";
	public static final String PLAYER_KEY_CLUB = "club";
	public static final String PLAYER_KEY_COUNTRY = "country";
	public static final String PLAYER_KEY_GOR = "gor";
	public static final String PLAYER_KEY_GRADE_VALUE = "grade";
	
	public static final String TOURNAMENT_KEY_ID = "_id";
	public static final String TOURNAMENT_KEY_NAME = "name";
	public static final String TOURNAMENT_KEY_PLAYER_PIN = "pin";
	public static final String TOURNAMENT_KEY_PLAYER_GOR = "gor";
	
	public static final String OPPONENTS_KEY_ID = "_id";
	public static final String OPPONENTS_KEY_TOURNAMENT_ID = "tournament_id";
	public static final String OPPONENTS_KEY_PLAYER_PIN = "pin";
	public static final String OPPONENTS_KEY_PLAYER_GOR = "gor";
	public static final String OPPONENTS_KEY_GAME_RESULT = "result";
	public static final String OPPONENTS_KEY_GAME_HANDICAP = "handicap";
	public static final String OPPONENTS_KEY_GAME_COLOR = "color";
	
	public static final int LIMIT = 50;
	public static final int FIRST_PAGE = 0;
	
	private static final String PLAYER_TABLE_NAME = "players";
	private static final String PLAYER_TABLE_CREATE = 
			"CREATE TABLE " + PLAYER_TABLE_NAME + " (" + 
		    PLAYER_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			PLAYER_KEY_PIN + " INTEGER NOT NULL, " +
			PLAYER_KEY_NAME + " TEXT NOT NULL COLLATE NOCASE, " + 
			PLAYER_KEY_CLUB+ " TEXT NOT NULL COLLATE NOCASE, " + 
			PLAYER_KEY_COUNTRY + " TEXT NOT NULL COLLATE NOCASE, " + 
			PLAYER_KEY_GRADE_VALUE + " INTEGER NOT NULL, " + 
			PLAYER_KEY_GOR + " INTEGER NOT NULL DEFAULT 100" + 
		    ")";
	
	private static final String TOURNAMENTS_TABLE_NAME = "tournaments";
	private static final String TOURNAMENTS_TABLE_CREATE =
			"CREATE TABLE " + TOURNAMENTS_TABLE_NAME + " (" + 
				    TOURNAMENT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					TOURNAMENT_KEY_NAME + " TEXT NOT NULL COLLATE NOCASE, " +
					TOURNAMENT_KEY_PLAYER_PIN + " INTEGER NOT NULL, " + 
					TOURNAMENT_KEY_PLAYER_GOR + " INTEGER NOT NULL " + 
				    ")";
	
	private static final String OPPONENTS_TABLE_NAME = "opponents";
	private static final String OPPONENTS_TABLE_CREATE =
			"CREATE TABLE " + OPPONENTS_TABLE_NAME + " (" + 
				    OPPONENTS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				    OPPONENTS_KEY_TOURNAMENT_ID + " INTEGER NOT NULL, " +
					OPPONENTS_KEY_PLAYER_PIN + " INTEGER NOT NULL, " +
					OPPONENTS_KEY_PLAYER_GOR + " INTEGER NOT NULL, " + 
					OPPONENTS_KEY_GAME_RESULT + " TEXT NOT NULL COLLATE NOCASE, " + 
					OPPONENTS_KEY_GAME_COLOR + " TEXT NOT NULL COLLATE NOCASE, " + 
					OPPONENTS_KEY_GAME_HANDICAP + " INTEGER NOT NULL DEFAULT 0 " + 
				    ")";
	
	private static final String PLAYER_TABLE_SELECT_ALL = 
			"SELECT " + 
					PLAYER_KEY_ID + ", " +
					PLAYER_KEY_PIN + ", " +
					PLAYER_KEY_NAME + ", " + 
					PLAYER_KEY_GRADE_VALUE + ", " + 	
					PLAYER_KEY_GOR + ", " + 	
					PLAYER_KEY_CLUB + ", " + 	
					PLAYER_KEY_COUNTRY + " " + 
			"FROM " + PLAYER_TABLE_NAME;
	
	
	private static final String PLAYER_TABLE_DROP = "DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME;
	private static final String TOURNAMENT_TABLE_DROP = "DROP TABLE IF EXISTS " + TOURNAMENTS_TABLE_NAME;
	private static final String OPPONENTS_TABLE_DROP = "DROP TABLE IF EXISTS " + OPPONENTS_TABLE_NAME;
	

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(PLAYER_TABLE_CREATE);
//		db.execSQL(TOURNAMENTS_TABLE_CREATE);
//		db.execSQL(OPPONENTS_TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < 20) {
			db.execSQL(PLAYER_TABLE_DROP);
//			db.execSQL(TOURNAMENT_TABLE_DROP);
//			db.execSQL(OPPONENTS_TABLE_DROP);
			onCreate(db);	
		} else {
			db.execSQL(PLAYER_TABLE_DROP);
			db.execSQL(PLAYER_TABLE_CREATE);
		}
	}
	
	public long insertPLayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();
		return insertPlayer(db, player);
	}
	
	public long insertPlayer(SQLiteDatabase db, int pin, String name, String country, String club, int grade, int gor) {
		ContentValues cv = new ContentValues();
		
		cv.put(PLAYER_KEY_PIN, pin);
		cv.put(PLAYER_KEY_NAME, name);
		cv.put(PLAYER_KEY_CLUB, club);
		cv.put(PLAYER_KEY_COUNTRY, country);
		cv.put(PLAYER_KEY_GRADE_VALUE, grade);
		cv.put(PLAYER_KEY_GOR, gor);
		
		return db.insert(PLAYER_TABLE_NAME, PLAYER_KEY_NAME, cv);
	}
	
	private long insertPlayer(SQLiteDatabase db, Player player) {
		ContentValues cv = new ContentValues();
		
		cv.put(PLAYER_KEY_PIN, 1);
		cv.put(PLAYER_KEY_NAME, player.getName());
		cv.put(PLAYER_KEY_CLUB, player.getClub());
		cv.put(PLAYER_KEY_COUNTRY, player.getCountry());
		cv.put(PLAYER_KEY_GRADE_VALUE, player.getGradeValue());
		cv.put(PLAYER_KEY_GOR, player.getGor());
		
		return db.insert(PLAYER_TABLE_NAME, PLAYER_KEY_NAME, cv);
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
			conditions.add(PLAYER_KEY_NAME + " LIKE '%" + name + "%'");
			conditionsApplies = true;
		}
		
		if (club != null) {
			conditions.add(PLAYER_KEY_CLUB + " LIKE '%" + club + "%'");
			conditionsApplies = true;
		}
		
		if (country != null) {
			conditions.add(PLAYER_KEY_COUNTRY + " LIKE '%" + country + "%'");
			conditionsApplies = true;
		}
		
		if (gradeMin > 0 || gradeMax < Player.STRENGTHS.size() - 1) {
			conditions.add(PLAYER_KEY_GRADE_VALUE + " BETWEEN " + gradeMin + " AND " + gradeMax);
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
				result.getInt(result.getColumnIndex(PLAYER_KEY_PIN)),
				result.getString(result.getColumnIndex(PLAYER_KEY_NAME)),
				result.getString(result.getColumnIndex(PLAYER_KEY_CLUB)),
				result.getString(result.getColumnIndex(PLAYER_KEY_COUNTRY)),
				result.getInt(result.getColumnIndex(PLAYER_KEY_GRADE_VALUE)),
				result.getInt(result.getColumnIndex(PLAYER_KEY_GOR)));
	}

	public Player getPlayerByPin(int playerPIN) {
		Player player = null;
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor result = db.rawQuery(PLAYER_TABLE_SELECT_ALL + " WHERE " + PLAYER_KEY_PIN + " = " + playerPIN, null);
		if (result.getCount() > 0) {
			result.moveToFirst();
			player = convertToPlayer(result);
		}
		
		db.close();
		return player;
	}

}
