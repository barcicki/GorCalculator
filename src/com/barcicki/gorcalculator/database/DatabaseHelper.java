package com.barcicki.gorcalculator.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.core.Player;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 6;
	private static final String DATABASE_NAME = "gorcalculator";
	
	public static final String KEY_ID = "_id";
	public static final String KEY_PIN = "pin";
	public static final String KEY_NAME = "name";
	public static final String KEY_CLUB = "club";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_GOR = "gor";
	public static final String KEY_GRADE = "grade";
	
	private static final String PLAYER_TABLE_NAME = "players";
	private static final String PLAYER_TABLE_CREATE = 
			"CREATE TABLE " + PLAYER_TABLE_NAME + " (" + 
		    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			KEY_PIN + " INTEGER NOT NULL, " +
			KEY_NAME + " TEXT NOT NULL, " + 
			KEY_CLUB+ " TEXT NOT NULL, " + 
			KEY_COUNTRY + " TEXT NOT NULL, " + 
			KEY_GRADE + " TEXT NOT NULL, " + 
			KEY_GOR + " INTEGER NOT NULL DEFAULT 100" + 
		    ")";
	private static final String PLAYER_TABLE_SELECT_ALL = 
			"SELECT " + 
					KEY_ID + ", " + 
					KEY_NAME + ", " + 
					KEY_GRADE + ", " + 	
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
		
//		insertPlayer(db, new Player("Artur Barcicki", "Pozn", "PL", "6 kyu", 1483));
//		insertPlayer(db, new Player("Piotr Wysocki", "Pozn", "PL", "2 kyu", 1914));
//		insertPlayer(db, new Player("Grzegorz Sobanski", "Pozn", "PL", "5 kyu", 1573));
//		insertPlayer(db, new Player("Sylwia Barcicka", "Pozn", "PL", "5 kyu", 1600));
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
	
	public long insertPlayer(SQLiteDatabase db, int pin, String name, String country, String club, String grade, int gor) {
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_PIN, pin);
		cv.put(KEY_NAME, name);
		cv.put(KEY_CLUB, club);
		cv.put(KEY_COUNTRY, country);
		cv.put(KEY_GRADE, grade);
		cv.put(KEY_GOR, gor);
		
		return db.insert(PLAYER_TABLE_NAME, KEY_NAME, cv);
	}
	
	private long insertPlayer(SQLiteDatabase db, Player player) {
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_PIN, 1);
		cv.put(KEY_NAME, player.getName());
		cv.put(KEY_CLUB, player.getClub());
		cv.put(KEY_COUNTRY, player.getCountry());
		cv.put(KEY_GRADE, player.getGrade());
		cv.put(KEY_GOR, player.getGor());
		
		return db.insert(PLAYER_TABLE_NAME, KEY_NAME, cv);
	}
	
	
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery(PLAYER_TABLE_SELECT_ALL, null);
		
		result.moveToFirst();
		do {
			players.add(convertToPlayer(result));
		} while (result.moveToNext());
		
		return players;
	}
	
	public Player getRandomPlayer() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery(PLAYER_TABLE_SELECT_ALL + " ORDER BY RANDOM() LIMIT 1", null);
		
		if (result.moveToFirst()) {
			return convertToPlayer(result);
		} else {
			return new Player((int) Calculator.MIN_GOR);
		}
	}
	
	private Player convertToPlayer(Cursor result) {
		return new Player(
				result.getString(result.getColumnIndex(KEY_NAME)),
				result.getString(result.getColumnIndex(KEY_CLUB)),
				result.getString(result.getColumnIndex(KEY_COUNTRY)),
				result.getString(result.getColumnIndex(KEY_GRADE)),
				result.getInt(result.getColumnIndex(KEY_GOR)));
	}

}
