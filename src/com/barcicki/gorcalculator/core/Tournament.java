package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class Tournament extends Observable implements Observer {
	
	public enum TournamentClass {
		CLASS_A	 	(1f),
		CLASS_B 	(0.75f),
		CLASS_C		(0.5f);
		
		public final float value;
		private TournamentClass(float value) {
			this.value = value;
		}
	};
	
	private static final String TAG = "Tournament";
	
	private String mName;
	private Player mPlayer;
	private ArrayList<Opponent> mOpponents = new ArrayList<Opponent>();
	private TournamentClass mTournamentClass = TournamentClass.CLASS_A;
	
	public Tournament(Player player, TournamentClass category) {
		mPlayer = player;
		mTournamentClass = category;
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
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
	
	public TournamentClass getTournamentClass() {
		return mTournamentClass;
	}
	
	public void setTournamentClass(TournamentClass category) {
		mTournamentClass = category;
		
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
	
	public void addOpponents(ArrayList<Opponent> opponents) {
		for (Opponent o : opponents) {
			mOpponents.add(o);
			o.addObserver(this);
			o.getPlayer().addObserver(this);
		}
		
		Log.d(TAG, "Opponent added");
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
	
	public double getStartingGor() {
		return Math.round(mPlayer.getGor());
	}
	
	public double getFinalGor() {
		double gor = getStartingGor(),
			   change = 0;
		
		for (Opponent opponent : mOpponents) {
			change += getGorChange(opponent);
		}
		
		if (change < -100) {
			change = -100;
		}
		
		return Math.round((gor + change) * 1000) / 1000f;
	}
	
	public double getGorChange(Opponent opponent) {
		
//		Log.d(TAG, "Player: " + mPlayer.getGor());
//		Log.d(TAG, "Opponent: " + opponent.getPlayer().getGor());
//		Log.d(TAG, "Win: " + opponent.getResult());
//		Log.d(TAG, "Handicap: " + opponent.getRelativeHandicap());
//		Log.d(TAG, "Category: " + getTournamentClass());
		
		return Calculator.calculate(mPlayer, opponent, mTournamentClass);		
	}

	@Override
	public void update(Observable observable, Object data) {
		Log.d(TAG, "A child has changed");
		
		setChanged();
		notifyObservers();
	}

}
