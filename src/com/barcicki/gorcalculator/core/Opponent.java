package com.barcicki.gorcalculator.core;

import java.util.Observable;

public class Opponent extends Observable {

	final public static float WIN = 1f;
	final public static float LOSS = 0f;
	final public static float JIGO = 0.5f;
	
	final public static int NO_HANDICAP = 0;
	final public static int HANDICAP_1 = 1;
	final public static int HANDICAP_2 = 2;
	final public static int HANDICAP_3 = 3;
	final public static int HANDICAP_4 = 4;
	final public static int HANDICAP_5 = 5;
	final public static int HANDICAP_6 = 6;
	final public static int HANDICAP_7 = 7;
	final public static int HANDICAP_8 = 8;
	final public static int HANDICAP_9 = 9;
	
	final public static int BLACK = 1;
	final public static int WHITE = 0;
	
	private float mResult;
	private int mColor;
	private int mHandicap;
	
	private Player mPlayer;
	
	public Opponent(Player player, float result, int color, int handicap) {
		this.setPlayer(player);
		this.setResult(result);
		this.setColor(color);
		this.setHandicap(handicap);
	}
	
	public Opponent(int gor, float result, int color, int handicap) {
		this.setPlayer(new Player(gor));
		this.setResult(result);
		this.setColor(color);
		this.setHandicap(handicap);
	}

	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player player) {
		mPlayer = player;
		
		setChanged();
		notifyObservers();
	}

	public float getResult() {
		return mResult;
	}

	public void setResult(float result) {
		this.mResult = result;
		setChanged();
		notifyObservers();
	}

	public int getColor() {
		return mColor;
	}

	public void setColor(int color) {
		this.mColor = color;
		setChanged();
		notifyObservers();
	}

	public int getHandicap() {
		return mHandicap;
	}
	
	public int getRelativeHandicap() {
		return getColor() == Opponent.WHITE ? -getHandicap() : getHandicap();
	}

	public void setHandicap(int handicap) {
		this.mHandicap = handicap;
		setChanged();
		notifyObservers();
	}
}
 