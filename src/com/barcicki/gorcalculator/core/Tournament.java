package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class Tournament extends Observable implements Observer {
	
	public static int CATEGORY_A = 0;
	public static int CATEGORY_B = 1;
	public static int CATEGORY_C = 2;
	
	public static Map<Integer, Float> CATEGORIES = new HashMap<Integer, Float>(); 
	
	private static String TAG = "Tournament";
	
	private Player mPlayer;
	private ArrayList<Opponent> mOpponents = new ArrayList<Opponent>();
	private int mCategory = CATEGORY_A;
	
	static {
		CATEGORIES.put(CATEGORY_A, 1.0f);
		CATEGORIES.put(CATEGORY_B, 0.75f);
		CATEGORIES.put(CATEGORY_C, 0.5f);
	}
	
	public Tournament(Player player, int category) {
		mPlayer = player;
		mCategory = category;
	}
	
	public Player getPlayer() {
		return mPlayer;
	}
	
	public void setPlayer(Player player) {
		
		if (mPlayer != null) {
			mPlayer.deleteObserver(this);
		}
		
		mPlayer = player;
		player.addObserver(this);
		
		Log.d(TAG, "Player changed");
		setChanged();
		notifyObservers();
	}
	
	public int getCategory() {
		return mCategory;
	}
	
	public void setCategory(int category) {
		mCategory = category;
		
		Log.d(TAG, "Category changed");
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<Opponent> getOpponents() {
		return mOpponents;
	}
	
	public void addOpponent(Opponent opponent) {
		mOpponents.add(opponent);
		opponent.addObserver(this);
		opponent.getPlayer().addObserver(this);
		
		Log.d(TAG, "Added opponent");
		setChanged();
		notifyObservers();
	}
	
	public void removeOpponent(Opponent opponent) {
		if (mOpponents.remove(opponent)) {
			opponent.deleteObserver(this);
			opponent.getPlayer().deleteObserver(this);
			
			Log.d(TAG, "Removed opponent");
			setChanged();
			notifyObservers();
		}
	}
	
	public float getStartingGor() {
		return Math.round(mPlayer.getGor());
	}
	
	public float getFinalGor() {
		float gor = getStartingGor();
		
		for (Opponent opponent : mOpponents) {
			gor += getGorChange(opponent);
		}
		
		return Math.round(gor * 1000) / 1000f;
	}
	
	public float getGorChange(Opponent opponent) {
		float category = CATEGORIES.get(mCategory);
		
//		Log.d(TAG, "Player: " + mPlayer.getGor());
//		Log.d(TAG, "Opponent: " + opponent.getPlayer().getGor());
//		Log.d(TAG, "Win: " + opponent.getResult());
//		Log.d(TAG, "Handicap: " + opponent.getRelativeHandicap());
//		Log.d(TAG, "Category: " + category);
		
		return Calculator.calculateRatingChange(mPlayer.getGor(), opponent.getPlayer(). getGor(), opponent.getResult(), opponent.getRelativeHandicap(), category);		
	}

	@Override
	public void update(Observable observable, Object data) {
		Log.d(TAG, "A child has changed");
		
		setChanged();
		notifyObservers();
	}

}
